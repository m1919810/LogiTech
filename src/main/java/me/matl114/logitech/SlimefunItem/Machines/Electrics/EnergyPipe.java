package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import me.matl114.logitech.SlimefunItem.Interface.DirectionalBlock;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.matl114.logitech.Utils.UtilClass.FunctionalClass.Counter;
import me.matl114.logitech.Utils.UtilClass.TickerClass.Ticking;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
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

public class EnergyPipe extends AbstractMachine implements DirectionalBlock {
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
    protected final int MAX_TRANSFER;
    protected final double TRANSFER_COST;
    public void constructMenu(BlockMenuPreset preset){
    }
    public void newMenuInstance(BlockMenu menu, Block block){
    }
    public void updateMenu(BlockMenu menu, Block block, Settings mod){}
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<MachineRecipe>();
    }
    public void process(Block b, BlockMenu menu, SlimefunBlockData data){}
    public  EnergyPipe(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,int maxTransfer,double transferCost){
        super(category, item, recipeType, recipe, 0,0);
        this.MAX_TRANSFER=maxTransfer;
        this.TRANSFER_COST=transferCost;
    }
    public void addInfo(ItemStack stack){
        stack.setItemMeta( AddUtils.addLore(stack,"&7最大电力传输速率: %sJ/t".formatted(AddUtils.formatDouble(MAX_TRANSFER)),"&7能量传输损耗率: %s%%".formatted(AddUtils.formatDouble(100*this.TRANSFER_COST))).getItemMeta());
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
                        EnergyPipe.this.tick(b, null,data,tickCounter);

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
    protected final HashMap<Location,Location> POINTING_RECORD=new HashMap<>();
    @Override
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        Location loc=b.getLocation();
        Counter<Location> counter=PIP_DIRECTION.computeIfAbsent(loc,(s)->{return new Counter<Location>(0,null);});
        Directions dir= getDirection(0,data);
        Location toLocation=POINTING_RECORD.computeIfAbsent(loc,dir::relate);

        if(DataCache.getSfItem(toLocation) instanceof EnergyNetComponent ec){
            if(ec.isChargeable()){
                counter.setCounter(ticker);
                counter.setValue(toLocation);
                bfssearchPipNet(loc,toLocation,ticker);
            }
        }
        if((toLocation=(counter.read(ticker)))!=null){
            Location fromLocation= dir.remote(loc,-1);
            if(!fromLocation.equals(toLocation)&& DataCache.getSfItem(toLocation) instanceof EnergyNetComponent ec1&&DataCache.getSfItem(fromLocation) instanceof EnergyNetComponent ec2){
                if(ec1.isChargeable()&&ec2.isChargeable()){
                    int fromCharge=ec2.getCharge(fromLocation);
                    int toCharge=ec1.getCharge(toLocation);
                    int transfer=Math.min( Math.min(fromCharge,MAX_TRANSFER), ((int)(((double)(ec1.getCapacity()-toCharge))/(1-TRANSFER_COST))));

                    ec2.setCharge(fromLocation,fromCharge-transfer);
                    ec1.setCharge(toLocation,toCharge+(int)(((double)transfer)*(1-TRANSFER_COST)) );
                }
            }
        }
    }
    public void bfssearchPipNet(Location originLocation,Location value,int timeStamp){
        Set<Location> registeredLocations=PIP_DIRECTION.keySet();

        Deque<Location> SEARCH_QUEUE=new ArrayDeque<>();
        SEARCH_QUEUE.addLast(originLocation);
        POINTING_RECORD.put(originLocation,value);
        Location testLocation;
        Counter<Location> testCounter;
        while(!SEARCH_QUEUE.isEmpty()){
            Location location=SEARCH_QUEUE.removeFirst();
            for(Directions direction:Directions.nonnullValues()){
                testLocation=direction.relate(location);
                if(location.equals(POINTING_RECORD.getOrDefault(testLocation,null))){
                    //may need sf item test
                    if((testCounter=PIP_DIRECTION.get(testLocation))!=null){
                        //这个节点已经被人访问过了,跳过
                        if(testCounter.read(timeStamp,0)!=null){
                            testCounter.setValue(value);
                        }else{
                            testCounter.setValue(value);
                            testCounter.setCounter(timeStamp);
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
