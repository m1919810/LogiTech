package me.matl114.logitech.core.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.tasks.TickerTask;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.ChunkPosition;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Listeners.Listeners.SlimefunBlockPlaceLimitListener;
import me.matl114.logitech.core.Interface.ChunkLimit;
import me.matl114.logitech.core.Interface.MenuTogglableBlock;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.matlib.Utils.Reflect.FieldAccess;
import me.matl114.matlib.Utils.Reflect.MethodAccess;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class TimerSlimefun extends AbstractMachine implements ChunkLimit, MenuTogglableBlock {

    private ItemStack PARTICLE_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&a点击切换粒子效果","&7当前状态: &c关");
    private ItemStack PARTICLE_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a点击切换粒子效果","&7当前状态: &a开");
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
    public TimerSlimefun(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energybuffer, int energyConsumption) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
    }

    @Override
    public void constructMenu(BlockMenuPreset preset) {
        IntStream.range(1,9).forEach(i->{if(i!=PARTICLE_SLOT){ preset.addItem(i, ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());}});
    }
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
    public void newMenuInstance(@Nonnull BlockMenu menu, @Nonnull Block block) {
        if(!onChunkPlace(menu.getLocation(),TimerSlimefun.class)){
            onChunkReachLimit(menu.getLocation(),this,(str)->{menu.getLocation().getWorld().getNearbyEntities(menu.getLocation(),10,10,10,(e)->{
                if(e instanceof Player player){
                    player.sendMessage(str);
                }
                return false;
            });});
            return;
        }
        ItemStack icon=menu.getItemInSlot(PARTICLE_SLOT);
        if(icon==null||(icon.getType()!=Material.RED_STAINED_GLASS_PANE&&icon.getType()!=Material.GREEN_STAINED_GLASS_PANE)){
            menu.replaceExistingItem(PARTICLE_SLOT,PARTICLE_OFF);
        }
        menu.addMenuClickHandler(PARTICLE_SLOT,((player, i, itemStack, clickAction) -> {
            boolean t=getStatus(menu)[0];
            toggleStatus(menu,!t);
            return false;
        }));

    }
    protected final int REPORT_SLOT=4;
    protected final int PARTICLE_SLOT=8;
    protected final int MAX_MACHINE_PER_CHUNK=114514;
    protected ItemStack getInfoShow(int total,int async,int timer,long nanoTime){
        if(total>=0){
            return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a运行信息",
                    "&7加速机器总量: %s/%s(max)".formatted(String.valueOf(total),String.valueOf(MAX_MACHINE_PER_CHUNK)),
                    "&7异步并行数: %s".formatted(String.valueOf(async)),
                    "&7加速倍率: %sx".formatted(String.valueOf(timer)),
                    "&7共计耗时: %.2f&ams".formatted(nanoTime / 1_000_000.0));
        }else {
            return new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&a运行信息",
                    "&c该区块内含有过多的机器! %s/%s(max)".formatted(String.valueOf(-total),String.valueOf(MAX_MACHINE_PER_CHUNK)));
        }
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
    public void process(Block b, BlockMenu inv, SlimefunBlockData data) {

    }
    protected FieldAccess.AccessWithObject<Map<ChunkPosition, Set<Location>>> tickingLocationsAccess=FieldAccess.ofName("tickingLocations").ofAccess(Slimefun.getTickerTask());
    protected MethodAccess reportError=MethodAccess.ofName(TickerTask.class,"reportErrors", Location.class,SlimefunItem.class, Throwable.class);
    protected HashSet<Class> blacklistedMachines=new HashSet<>(){{
        add(TimerSlimefun.class);
        add(TimerBlockEntity.class);
        try{
            add(io.github.sefiraat.networks.slimefun.network.NetworkController.class);
        }catch (NoClassDefFoundError ignored){
        }
    }};
    protected HashSet<Location> RUNNING_MACHINES=new HashSet<>();
    public void registerTick(SlimefunItem item){
        item.addItemHandler(
                new BlockTicker() {
                    public boolean isSynchronized() {
                        return false;
                    }
                    int tickCount=0;
                    @ParametersAreNonnullByDefault
                    public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
                        Location loc=b.getLocation();
                        if(RUNNING_MACHINES.contains(loc)){
                            return;
                        }else {
                            RUNNING_MACHINES.add(loc);
                        }
                        BlockMenu menu = data.getBlockMenu();
                        int timer=64;
                        ChunkPosition position=new ChunkPosition(loc);
                        tickingLocationsAccess.get((locations)->{
                            Set<Location> tickingLocations;
                            synchronized (locations){
                                tickingLocations=new HashSet<>( locations.get(position));
                            }
                            List<CompletableFuture> waitingThreads=new ArrayList<>();
                            long startTime=System.nanoTime();
                            tickingLocations.forEach((l)->tickLocation(l,waitingThreads,timer));
                            CompletableFuture.allOf( waitingThreads.toArray(CompletableFuture[]::new)).join();
                            long totalTime=System.nanoTime()-startTime;
                            CompletableFuture.runAsync(()->{
                                if(menu.hasViewer()){
                                    menu.replaceExistingItem(REPORT_SLOT,getInfoShow(tickingLocations.size(),waitingThreads.size(),timer,totalTime));
                                }
                                if(getStatus(menu)[0]){
                                    doParticle(loc);
                                }

                            });

                        });
                    }
                    @Override
                    public void uniqueTick() {
                        RUNNING_MACHINES.clear();
                        tickCount++;
                    }
                }
        );
    }
    private List<org.bukkit.util.Vector> dv;
    {
        var a=new ArrayList<Vector>();
        for(int i=0;i<20;++i){
            double radian = Math.toRadians(18*i);
            a.add(new Vector(0.5*Math.cos(radian)+0.5,0.7,0.5*Math.sin(radian)+0.5));
        }
        dv=Collections.unmodifiableList(a);
    }
    protected void doParticle(Location loc){
        CompletableFuture.runAsync(()->{
            dv.forEach((v)->{
                Location particleLocation=loc.clone().add(v);
                particleLocation.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME,particleLocation,0,0.0,0.0,0.0,1,null,true);
            });
        });
    }

    private void tickLocation(@Nonnull Location l, List<CompletableFuture> threads,int timer) {
        SlimefunBlockData blockData = DataCache.safeLoadBlock(l);
        if (blockData != null && blockData.isDataLoaded() && !blockData.isPendingRemove()) {
            SlimefunItem item = SlimefunItem.getById(blockData.getSfId());
            if (item != null &&!blacklistedMachines.contains(item.getClass()) && item.getBlockTicker() != null) {
                if (item.isDisabledIn(l.getWorld())) {
                    return;
                }
                try {
                    if (item.getBlockTicker().isSynchronized()) {
                        item.getBlockTicker().update();
                        Slimefun.runSync(() -> {
                            if (!blockData.isPendingRemove()) {
                                Block b = l.getBlock();
                                    this.tickBlockTimer(l, b, item, blockData,timer);

                            }
                        });
                    } else {
                        threads.add(CompletableFuture.runAsync(()->{
                            item.getBlockTicker().update();
                            Block b = l.getBlock();
                            this.tickBlockTimer(l, b, item, blockData,timer);
                        }));
                    }

                } catch (Exception var8) {
                }
            }

        }
    }
    @ParametersAreNonnullByDefault
    private void tickBlockTimer(Location l, Block b, SlimefunItem item, SlimefunBlockData data,int timer) {
        try {
            for(int i=0;i<timer;++i){
                item.getBlockTicker().tick(b, item, data);
            }
        } catch (LinkageError | Exception var11) {
            reportError.invokeCallback((nul)->{},()->{},Slimefun.getTickerTask(),l,item,var11);
        }
    }
    //公用一个表
    private static HashMap<Chunk,Location> RECORDS = new HashMap<>();

    @Override
    public HashMap<Chunk, Location> getRecords() {
        return RECORDS;
    }

    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        this.onChunkBreak(e.getBlock().getLocation());
    }

    @Override
    public boolean canUse(@Nonnull Player p, boolean sendMessage) {
        if( !super.canUse(p, sendMessage)){
            return false;
        }
        if( p.hasPermission("logitech.shell.test")){
            return true;
        }else {
            if (sendMessage) {
                Slimefun.getLocalization().sendMessage(p, "messages.no-permission", true);
            }
            return false;
        }


    }

    @Override
    public void preRegister() {
        super.preRegister();
        SlimefunBlockPlaceLimitListener.registerBlockLimit(this,(event)->{

            Location loc=event.getBlockPlaced().getLocation();

            if(!onChunkPlace(loc,TimerSlimefun.class)){
                onChunkReachLimit(loc,this,(str)-> {
                    if (event.getPlayer() != null) {
                        event.getPlayer().sendMessage(str);
                    }
                });
                event.setCancelled(true);
            }
        });
    }


}
