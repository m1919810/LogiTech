package me.matl114.logitech.core.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.Interface.DirectionalBlock;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.Settings;
import me.matl114.logitech.utils.UtilClass.CargoClass.Directions;
import me.matl114.logitech.utils.UtilClass.FunctionalClass.Counter;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractPipe extends AbstractMachine implements DirectionalBlock {
    protected final int[] INPUT_SLOTS=new int[0];
    protected final int[] OUTPUT_SLOTS=new int[0];
    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    public String[] savedKeys=new String[]{
            "face_dir"
    };
    public int[] DIRECTION_SLOTS=new int[]{-1};
    public String[] getSaveKeys(){
        return savedKeys;
    }
    public int[] getDirectionSlots(){
        return DIRECTION_SLOTS;
    }
    public boolean canModify(){
        return false;
    }
    public void constructMenu(BlockMenuPreset preset){
    }
    public void newMenuInstance(BlockMenu menu, Block block){
    }
    public void updateMenu(BlockMenu menu, Block block, Settings mod){}

    public void process(Block b, BlockMenu menu, SlimefunBlockData data){}
    public  AbstractPipe(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(category, item, recipeType, recipe, 0,0);
    }

    public void registerBlockMenu(SlimefunItem item){
        //不用menu
        //this.createPreset(item,item.getItemName(),this::constructMenu);
        //handle blockPlaceEvent
        handleBlock(item);
    }

    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        PIP_DIRECTION.remove(e.getBlock().getLocation());
        POINTING_RECORD.remove(e.getBlock().getLocation());
    }

    @Override
    public void onPlace(BlockPlaceEvent e, Block b) {
        super.onPlace(e, b);
        BlockData data1=b.getBlockData();
        if(data1 instanceof Directional dp){
            Location loc=e.getBlockAgainst().getLocation();
            Vector vector=b.getLocation().clone().subtract(loc).toVector();
            Directions directions=Directions.fromVector(vector);
            if(directions!=null&&directions!=Directions.NONE){
                dp.setFacing(directions.toBlockFace());
                b.setBlockData(data1,true);
            }
            DataCache.setCustomData(b.getLocation(),getSaveKeys()[0], Directions.fromBlockFace(dp.getFacing()).toInt());
        }
    }

    public void registerTick(SlimefunItem item){
        item.addItemHandler(
                new BlockTicker() {
                    int tickCounter=0;
                    public boolean isSynchronized() {
                        return isSync();
                    }

                    @ParametersAreNonnullByDefault
                    public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
                        //BlockMenu menu = BlockStorage.getInventory(b);
                        AbstractPipe.this.tick(b, null,data,tickCounter);

                    }

                    @Override
                    public void uniqueTick() {
                        super.uniqueTick();
                        this.tickCounter++;
                    }
                }
        );
    }
    protected final HashMap<Location, Counter<Location>> PIP_DIRECTION=new HashMap<>();
    protected final ConcurrentHashMap<Location,Location> POINTING_RECORD=new ConcurrentHashMap<>();
    protected final boolean avalEnd(Location loc){
        return DataCache.getSfItem(loc)!=this&&avalibleDestination(loc);
    }
    @Override
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        Location loc=b.getLocation();
        Counter<Location> counter=PIP_DIRECTION.computeIfAbsent(loc,(s)->{return new Counter<Location>(0,null);});
        Directions dir= getDirection(0,data);
        final Location toLocation=POINTING_RECORD.computeIfAbsent(loc,dir::relate);
        if(avalEnd(toLocation)){
            counter.setCounter(ticker);
            counter.setValue(toLocation);
            CompletableFuture.runAsync(()->{
                bfssearchPipNet(loc,toLocation,ticker);
            });
        }
        Location transferedTo;
        //因为异步处理,将置信度设置为延迟2个timestamp
        //一般地 这是恒定不变的
        if((transferedTo=(counter.read(ticker,2)))!=null){
            Location fromLocation= dir.remote(loc,-1);
            if(!fromLocation.equals(transferedTo)){
                transfer(fromLocation,transferedTo);
            }
        }
    }
    public abstract boolean avalibleDestination(Location toLocation);
    public abstract void transfer(Location from, Location to);
    public void bfssearchPipNet(Location originLocation,Location value,int timeStamp){
        //Set<Location> registeredLocations=PIP_DIRECTION.keySet();
        Deque<Location> SEARCH_QUEUE=new ArrayDeque<>();
        SEARCH_QUEUE.addLast(originLocation);
        POINTING_RECORD.put(originLocation,value);
        Location testLocation;
        Counter<Location> testCounter;
        while(!SEARCH_QUEUE.isEmpty()){
            Location location=SEARCH_QUEUE.removeFirst();
            for(Directions direction:Directions.nonnullValues()){
                testLocation=direction.relate(location);
                if(location.equals(POINTING_RECORD.get(testLocation))){
                    //may need sf item test
                    if((testCounter=PIP_DIRECTION.get(testLocation))!=null){
                        //这个节点已经被人访问过了,跳过
                        if(testCounter.read(timeStamp,0)!=null){
                            testCounter.setValue(value);
                        }else{
                            testCounter.updateValue(value,timeStamp);
//                            testCounter.setValue(value);
//                            testCounter.setCounter(timeStamp);
                            SEARCH_QUEUE.addLast(testLocation);
                        }
                    }else{
                        POINTING_RECORD.remove(testLocation);
                    }
                }
            }
        }
    }
}
