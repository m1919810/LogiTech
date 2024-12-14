package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Interface.MenuTogglableBlock;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.TickerClass.Ticking;
import me.matl114.logitech.Utils.WorldUtils;
import me.matl114.matlib.Utils.Reflect.MethodAccess;
import me.matl114.matlib.Utils.Reflect.ReflectUtils;
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
import java.lang.reflect.Method;
import java.sql.Ref;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TimerBlockEntity extends AbstractMachine implements MenuTogglableBlock {
    @Override
    public void constructMenu(BlockMenuPreset preset) {
        IntStream.range(0,9).forEach(i->preset.addItem(i, ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler()));
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
        if(icon==null||(icon.getType()!=Material.RED_STAINED_GLASS_PANE)||icon.getType()!=Material.GREEN_STAINED_GLASS_PANE){
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
    public TimerBlockEntity(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energybuffer, int energyConsumption) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
        if(TickTask==null){
            TickTask=new BukkitRunnable() {
                public void run() {
                    tick();
                }
            };
            SchedulePostRegister.addPostRegisterTask(()->{
                Schedules.launchSchedules(TickTask,10,false,1);
            });
        }
    }
    private static BukkitRunnable TickTask;
    private static int RANGE=7;
    private static MethodAccess getHandleMethodAccess=MethodAccess.ofName("getHandle");
    private static MethodAccess getPositionMethodAccess=MethodAccess.ofName("getPosition");
    private static MethodAccess getNMSMethodAccess=MethodAccess.ofName("getNMS");
    private static MethodAccess getCraftWorldHandleMethodAccess=MethodAccess.ofName("getHandle");
    private static MethodAccess getNMSBlockType=MethodAccess.ofName("b");

    private static MethodAccess getBlockEntityMethodAccess=new MethodAccess((obj)->{
        var a= ReflectUtils.getMethodsByName(obj.getClass(),"c_");
        return a==null?null:a.getFirstValue();
    });
    private static Class tileEntityClass;
    private static MethodAccess getBlockEntityTypeMethodAccess=new MethodAccess((obj)->{
        List<Class> cls= ReflectUtils. getAllSuperClassRecursively(obj.getClass()).stream().filter(s->"TileEntity".equals(s.getSimpleName())).toList();
        Class clazz=cls.get(cls.size()-1);
        //errorOut.accept(clazz);
        tileEntityClass=clazz;
        var re= Arrays.stream(clazz.getDeclaredMethods()).filter(m->m.getParameterTypes().length==0).filter(m->m.getReturnType()!=boolean.class).filter(s->s.getReturnType().getSimpleName().endsWith("Types")).toList();
        //errorOut.accept(re);
        return re.isEmpty()?null:re.get(0);
    });
    private static Class iTileEntityInterface;
    private static MethodAccess getTickerMethodAccess=new MethodAccess((obj)->{
        Method[] methods=iTileEntityInterface.getDeclaredMethods();
        Method a= Arrays.stream(methods).filter((m)->"a".equals(m.getName())).filter(m->m.getParameterTypes().length==3).findFirst().get();
        //errorOut.accept("get info", Arrays.stream(a.getParameterTypes()).toList(),a.getReturnType());
        return a;
    });
    private static Class blockEntityTickerInterface;
    private static MethodAccess tickMethodAccess=new MethodAccess((obj)->{
        Method[] a=blockEntityTickerInterface.getDeclaredMethods();
        var re= Arrays.stream(a).filter((m)->m.getParameterTypes().length==4).filter(m->"tick".equals(m.getName())).toList();
        return re.isEmpty()?null:re.get(0);
    });
    private static boolean running=false;
    private static int TIMER=50;
    private static Consumer<String> errorOut=(s)->{};

    static void tick(){
        if(running){
            return;
        }
        running=true;
        try{
            var iter=TIMED_MACHINES.entrySet().iterator();
            while (iter.hasNext()){
                var entry=iter.next();
                Location machine=entry.getKey();
                if(DataCache.getSfItem(machine) instanceof TimerBlockEntity){
                    List<Runnable> tickTasks=new ArrayList<>(entry.getValue().size());
                    Set<Location> tickLocations;
                    synchronized (lock){
                        tickLocations=new HashSet<>(entry.getValue());
                    }
                    Set<Block> blocks=new HashSet<>();
                    tickLocations.forEach(t->blocks.add(t.getBlock()));
                    Schedules.launchSchedules(()->{
                        blocks.forEach((bb)->{
                            getHandleMethodAccess.invokeCallback((levelAccess)->{
                                getPositionMethodAccess.invokeCallback((blockPos)->{
                                    getNMSMethodAccess.invokeCallback((state)->{
                                        getNMSBlockType.invokeCallback((block)->{
                                            if(iTileEntityInterface==null){
                                                List<Class> interfaces=ReflectUtils. getAllInterfacesRecursively(block.getClass());
                                                var val= interfaces.stream().filter(s->s.getSimpleName().endsWith("TileEntity")).findFirst();
                                                if(val.isPresent()){
                                                    iTileEntityInterface=val.get();
                                                }else {
                                                    return;
                                                }
                                            }
                                            if(iTileEntityInterface.isInstance(block)){
                                                getBlockEntityMethodAccess.invokeCallback((blockEntity)->{
                                                    if(blockEntity!=null){
                                                        getBlockEntityTypeMethodAccess.invokeCallback((type)->{
                                                            getCraftWorldHandleMethodAccess.invokeCallback((world)->{
                                                                try{
                                                                    var ticker= getTickerMethodAccess.invokeIgnoreFailure(block,world,state,type);
                                                                    if(ticker!=null){
                                                                        if(blockEntityTickerInterface==null){
                                                                            List<Class> interfaces=ReflectUtils. getAllInterfacesRecursively(ticker.getClass());
                                                                            blockEntityTickerInterface= interfaces.stream().filter(s->s.getSimpleName().endsWith("Ticker")).findFirst().get();
                                                                        }
                                                                        tickTasks.add(()->{
                                                                            try{
                                                                                tickMethodAccess.invoke(ticker,world,blockPos,state,blockEntity);
                                                                            }catch (Throwable e){
                                                                            }
                                                                        });
                                                                    }
                                                                    errorOut.accept("successfully invoke!");
                                                                }catch (Throwable e){
                                                                    errorOut.accept("failed");
                                                                    e.printStackTrace();
                                                                }
                                                            },()->{errorOut.accept("failed getCraftWorldHandle");},bb.getWorld());
                                                        },()->{errorOut.accept("failed getBlockEntityType");},blockEntity);
                                                    }else {
                                                        errorOut.accept("not a tile Entity");
                                                    }
                                                },()->{errorOut.accept("failed getBlockEntityMethod");},levelAccess,blockPos);
                                            }else{
                                                removeLocation(machine,bb.getLocation());
                                                errorOut.accept("not a instance of TileEntity");
                                            }
                                        },()->{errorOut.accept("failed getNMSBlockType");},state);

                                    },()->{errorOut.accept("failed getNMS");},bb);
                                },()->{errorOut.accept("failed getPositionMethod");},bb);
                            },()->{errorOut.accept("failed getHandle");},bb);
                        });
                        for(int i=0;i<TIMER;++i){
                            for(Runnable tickTask:tickTasks){
                                tickTask.run();
                            }
                        }
                    },0,true,0);
                }else {
                    iter.remove();
                }
            }
        }finally {
            running=false;
        }
    }
    public static void addLocation(Location loc){
        if(!DataCache.hasData(loc)){
            synchronized (lock){
                TIMED_MACHINES.keySet().stream().filter(l->l.getWorld()==loc.getWorld()).filter(l->inRange(loc,l,RANGE)).forEach(l->{
                    TIMED_MACHINES.computeIfAbsent(l,(le)->new HashSet<>()).add(loc);
                });
            }
        }
    }
    public static void removeLocation(Location machine,Location loc){
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




    Set<Location> MACHINES_RUNNED=ConcurrentHashMap.newKeySet();
    static HashMap<Location,Set<Location>> TIMED_MACHINES=new HashMap<>();
    static byte[] lock=new byte[0];
    private final String TICK_LOC="t";
    public void registerTick(SlimefunItem that){
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
                        if(data.getData(TICK_LOC)==null){
                            data.setData(TICK_LOC,String.valueOf(tickCount%20));
                        }
                        int tick=DataCache.getCustomData(data,TICK_LOC,0);
                        if(!TIMED_MACHINES.containsKey(loc)|| tickCount%20==tick){
                            initLocation(loc);
                        }

                        if(tickCount%4==tick%4){
                            BlockMenu menu=data.getBlockMenu();
                            if(menu!=null&&getStatus(menu)[0]){
                                doParticle(loc);
                            }
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
    private List<Vector> dv;
    private boolean isOnCubeEdge(int i, int j, int k) {
        // 判断是否在前后面的边
        boolean onFrontBack = (i == -RANGE || i == RANGE+1) && (j == -RANGE || j == RANGE+1) ;
        // 判断是否在上下面的边
        boolean onTopBottom = (j == -RANGE || j == RANGE+1) && (k == -RANGE || k == RANGE+1) ;
        // 判断是否在左右面的边
        boolean onLeftRight = (k == -RANGE || k == RANGE+1) && (i >= -RANGE || i <= RANGE+1);

        // 只要满足任何一条边的条件，即为边框上的点
        return onFrontBack || onTopBottom || onLeftRight;
    }
    {
        List<Vector> list=new ArrayList<>();
        for (int i=-RANGE;i<=RANGE+1;++i){
            for(int j=-RANGE;j<=RANGE+1;++j){
                for(int k=-RANGE;k<=RANGE+1;++k){
                    if(isOnCubeEdge(i,j,k)){
                        list.add(new Vector(i,j,k));
                    }
                }
            }
        }
        dv=Collections.unmodifiableList(list);
    }
    private void doParticle(Location loc){
        CompletableFuture.runAsync(()->{
            dv.forEach((v)->{
                Location particleLocation=loc.clone().add(v);
                particleLocation.getWorld().spawnParticle(Particle.WAX_OFF,particleLocation,0,0.0,0.0,0.0,1,null,true);
            });
        });
    }


    public void initLocation(Location loc){
        CompletableFuture.runAsync(()->{
            HashSet<Location> tickableBlocks=new HashSet<>();
            for(int x=-RANGE;x<=RANGE;x++){
                for(int y=-RANGE;y<=RANGE;y++){
                    for(int z=-RANGE;z<=RANGE;z++){
                        Location testLocation=loc.clone().add(x,y,z);
                        if(DataCache.hasData(testLocation)){
                            continue;
                        }
                        Block block=testLocation.getBlock();
                        if(WorldUtils.isEntityBlock(block.getType())){
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
