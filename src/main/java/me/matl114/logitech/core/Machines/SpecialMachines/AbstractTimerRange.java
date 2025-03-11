package me.matl114.logitech.core.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.Getter;
import lombok.Setter;
import me.matl114.logitech.manager.PostSetupTasks;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.core.Interface.MenuTogglableBlock;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.utils.DataCache;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public abstract class AbstractTimerRange  extends AbstractMachine implements MenuTogglableBlock {
    public void constructMenu(BlockMenuPreset preset) {
        IntStream.range(0,9).forEach(i->{if(i!=PARTICLE_SLOT)preset.addItem(i, ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());});
    }
    private ItemStack PARTICLE_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&a点击切换粒子效果","&7当前状态: &c关");
    private ItemStack PARTICLE_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a点击切换粒子效果","&7当前状态: &a开");
    private int PARTICLE_SLOT=4;
    @Override
    public boolean[] getStatus(BlockMenu inv) {
        ItemStack stack=inv.getItemInSlot(PARTICLE_SLOT);
        if(stack!=null&&stack.getType()==Material.GREEN_STAINED_GLASS_PANE){
            return new boolean[]{true};
        }else {
            return new boolean[]{false};
        }
    }
    @Override
    public void toggleStatus(BlockMenu inv, boolean... result) {
        if(result[0]){
            inv.replaceExistingItem(PARTICLE_SLOT, PARTICLE_ON);
        }else {
            inv.replaceExistingItem(PARTICLE_SLOT, PARTICLE_OFF);
        }
    }
    @Override
    public void newMenuInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        ItemStack icon=blockMenu.getItemInSlot(PARTICLE_SLOT);
        if(icon==null||(icon.getType()!=Material.RED_STAINED_GLASS_PANE&&icon.getType()!=Material.GREEN_STAINED_GLASS_PANE)){
            blockMenu.replaceExistingItem(PARTICLE_SLOT,PARTICLE_OFF);
        }
        blockMenu.addMenuClickHandler(PARTICLE_SLOT,((player, i, itemStack, clickAction) -> {
            boolean t=getStatus(blockMenu)[0];
            toggleStatus(blockMenu,!t);
            return false;
        }));
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }
    @Override
    public void process(Block b, BlockMenu preset, SlimefunBlockData data) {

    }
    /**
     * constructor of abstractMachines will keep Collections of MachineRecipes,will register energyNetwork params,
     * will set up menu by overriding constructMenu method
     *
     * @param category
     * @param item
     * @param recipeType
     * @param recipe
     * @param energybuffer
     * @param energyConsumption
     */
    public AbstractTimerRange(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,int range,int timer,int period, int energybuffer, int energyConsumption) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
        this.Range =range;
        this.TIMER=timer;
        if(TickTask==null){
            TickTask=new BukkitRunnable() {
                public void run() {
                    tickInRange();
                }
            };
            PostSetupTasks.addPostRegisterTask(()->{
                Schedules.launchSchedules(TickTask,10,false,period);
            });
        }
        initFrame();

    }
    @Setter
    private boolean synchronizeTask=true;
    private BukkitRunnable TickTask;
    @Getter
    private int Range ;

    private boolean running=false;
    private int TIMER;
    private static Consumer<String> errorOut=(s)->{};

    public void tickInRange(){
        if(running){
            return;
        }
        running=true;
        try{
            var iter=TIMED_MACHINES.entrySet().iterator();
            while (iter.hasNext()){
                var entry=iter.next();
                Location machine=entry.getKey();
                if(DataCache.getSfItem(machine) ==this){
                    List<Runnable> tickTasks=new ArrayList<>(entry.getValue().size());
                    Set<Location> tickLocations;
                    synchronized (lock){
                        tickLocations=new HashSet<>(entry.getValue());
                    }
                    Schedules.execute(()->{
                        tickLocations.forEach(l->{
                            Runnable task=getTickTask(l,machine);
                            if(task!=null){
                                tickTasks.add(task);
                            }
                        });
                        for (int i=0;i<TIMER;++i){
                            for (Runnable a:tickTasks){
                                a.run();
                            }
                        }
                    },synchronizeTask);
                }else {
                    iter.remove();
                }
            }
        }catch(Throwable anyThingIgnored){

        }
        finally {
            running=false;
        }
    }
    public abstract Runnable getTickTask(Location location,Location center);
    public final void addLocation(Location loc){
        if(DataCache.getMenu(loc)==null){
            synchronized (lock){
                TIMED_MACHINES.keySet().stream().filter(l->l.getWorld()==loc.getWorld()).filter(l->inRange(loc,l,getRange())).filter(l->DataCache.getSfItem(l)==AbstractTimerRange.this).forEach(l->{
                    TIMED_MACHINES.computeIfAbsent(l,(le)->new HashSet<>()).add(loc);
                });
            }
        }
    }
    public void removeLocation(Location machine,Location loc){
        synchronized (lock){
            var mp= TIMED_MACHINES.get(machine);
            if(mp!=null){
                mp.remove(loc);
            }
        }
    }
    static boolean inRange(Location loc, Location newLoc, int distance) {
        return Math.abs(loc.getX() - newLoc.getX()) <= distance &&
                Math.abs(loc.getY() - newLoc.getY()) <= distance &&
                Math.abs(loc.getZ() - newLoc.getZ()) <= distance;
    }




    Set<Location> MACHINES_RUNNED= ConcurrentHashMap.newKeySet();
    HashMap<Location,Set<Location>> TIMED_MACHINES=new HashMap<>();
    byte[] lock=new byte[0];
    private final String TICK_LOC="t";
    public final void registerTick(SlimefunItem that){
        that.addItemHandler(
                new BlockTicker() {
                    int tickCount=0;
                    public boolean isSynchronized() {
                        return false;
                    }

                    @ParametersAreNonnullByDefault
                    public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
                        Location loc=b.getLocation();
                        if(MACHINES_RUNNED.contains(loc)){
                            return;
                        }else {
                            MACHINES_RUNNED.add(loc);
                        }

                        int tick=DataCache.getCustomData(data,TICK_LOC,tickCount%20);
                        if(!TIMED_MACHINES.containsKey(loc)|| tickCount%20==tick){
                            initLocation(loc);
                        }
                        BlockMenu menu=data.getBlockMenu();
                        process(b,menu,data);

                        if(menu!=null&&getStatus(menu)[0]){
                            doParticle(loc);
                        }
                    }
                    @Override
                    public void uniqueTick() {
                        MACHINES_RUNNED.clear();
                        tickCount++;
                    }
                }
        );
    }

    private List<org.bukkit.util.Vector> dv;
    private boolean isOnCubeEdge(int i, int j, int k) {
        // 判断是否在前后面的边
        boolean onFrontBack = (i == -Range || i == Range +1) && (j == -Range || j == Range +1) ;
        // 判断是否在上下面的边
        boolean onTopBottom = (j == -Range || j == Range +1) && (k == -Range || k == Range +1) ;
        // 判断是否在左右面的边
        boolean onLeftRight = (k == -Range || k == Range +1) && (i == -Range || i == Range +1);

        // 只要满足任何一条边的条件，即为边框上的点
        return onFrontBack || onTopBottom || onLeftRight;
    }
    private void initFrame(){
        List<org.bukkit.util.Vector> list=new ArrayList<>();
        for (int i = -Range; i<= Range +1; ++i){
            for(int j = -Range; j<= Range +1; ++j){
                for(int k = -Range; k<= Range +1; ++k){
                    if(isOnCubeEdge(i,j,k)){
                        list.add(new Vector(i,j,k));
                    }
                }
            }
        }
        dv=Collections.unmodifiableList(list);
    }
    public Particle getParticle(){
        return Particle.WAX_OFF;
    }
    private void doParticle(Location loc){
        CompletableFuture.runAsync(()->{
            dv.forEach((v)->{
                Location particleLocation=loc.clone().add(v);
                particleLocation.getWorld().spawnParticle(getParticle(),particleLocation,0,0.0,0.0,0.0,1,null,false);
            });
        });
    }

    public abstract boolean blockPredicate(Block block);

    private void initLocation(Location loc){
        CompletableFuture.runAsync(()->{
            HashSet<Location> tickableBlocks=new HashSet<>();
            for(int x = -Range; x<= Range; x++){
                for(int y = -Range; y<= Range; y++){
                    for(int z = -Range; z<= Range; z++){
                        Location testLocation=loc.clone().add(x,y,z);
                        Block block=testLocation.getBlock();
                        if(blockPredicate(block)){
                            tickableBlocks.add(block.getLocation());
                        }
                    }
                }
            }
            synchronized(lock){
                TIMED_MACHINES.computeIfAbsent(loc, k->new HashSet<>()).addAll(tickableBlocks);
            }
        });
    }
}
