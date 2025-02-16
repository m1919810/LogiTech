package me.matl114.logitech.core.Machines.Electrics;

import city.norain.slimefun4.utils.MathUtil;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.ErrorReport;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Algorithms.AtomicCounter;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.MathUtils;
import me.matl114.logitech.core.Interface.EnergyProvider;
import me.matl114.logitech.core.Interface.MenuTogglableBlock;
import me.matl114.logitech.core.Machines.Electrics.AbstractEnergyMachine;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractEnergyCollector extends AbstractEnergyMachine implements EnergyProvider, MenuTogglableBlock {
    protected final int[] INPUT_SLOTS=new int[0];
    protected final int[] OUTPUT_SLOTS=new int[0];
    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    protected final int[] BORDER=new int[]{
            0,1,2,3,4,5,6,7,9,10,11,12,14,15,16,17,18,19,20,21,22,23,24,25,26
    };
    protected final int INFO_SLOT=13;
    protected final int LAZY_SLOT=8;
    protected int getLazySlot(){
        return LAZY_SLOT;
    }
    protected int getInfoSlot(){
        return INFO_SLOT;
    }
    protected final ItemStack LAZY_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换懒惰模式","&7当前状态: &c关闭",
            "&7当启用懒惰模式时,只有自身电力不满时","&7才会尝试收集发电机电力");
    protected final ItemStack LAZY_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换懒惰模式","&7当前状态: &a开启",
            "&7当启用懒惰模式时,只有自身电力不满时","&7才会尝试收集发电机电力");

    public AbstractEnergyCollector(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                 int energybuffer){
        super(category, item, recipeType, recipe, energybuffer, 0, EnergyNetComponentType.GENERATOR);
    }

    protected ItemStack getInfoShow(int charge,int machine,int errors){
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6信息","&7已存储: %sJ/%sJ".formatted(AddUtils.formatDouble(charge),AddUtils.formatDouble(this.energybuffer)),
                "&7范围发电机数目: %d/%d(max)".formatted(machine, getMaxCollectAmount()),
                "&7发电机报错数目: %d".formatted(errors));
    }
    public boolean isBorder(int i){
        return i!=getLazySlot();
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for (int i=0;i<len;++i){
            if(isBorder(border[i])){
                preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
            }
        }
        preset.addItem(getInfoSlot(),getInfoShow(0,0,0),ChestMenuUtils.getEmptyClickHandler());
    }
    @Override
    public boolean[] getStatus(BlockMenu inv) {
        ItemStack itemStack=inv.getItemInSlot(getLazySlot());
        if(itemStack!=null&&itemStack.getType()==Material.GREEN_STAINED_GLASS_PANE){
            return new boolean[]{true};
        }else {
            return new boolean[]{false};
        }
    }

    @Override
    public void toggleStatus(BlockMenu inv, boolean... result) {
        if(result[0]){
            inv.replaceExistingItem(getLazySlot(),LAZY_ITEM_ON);
        }else {
            inv.replaceExistingItem(getLazySlot(),LAZY_ITEM_OFF);
        }
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        ItemStack icon=menu.getItemInSlot(getLazySlot());
        if(icon==null||icon.getType()!=Material.RED_STAINED_GLASS_PANE){
            menu.replaceExistingItem(getLazySlot(),LAZY_ITEM_ON);
        }
        menu.addMenuClickHandler(getLazySlot(),((player, i, itemStack, clickAction) -> {
            boolean t=getStatus(menu)[0];
            toggleStatus(menu,!t);
            return false;
        }));
    }

    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
    }
    public abstract Collection<SlimefunBlockData> getCollectRange(BlockMenu menu, Block block, SlimefunBlockData data);
    protected boolean isCollectable(SlimefunItem that){
        return true;
    }
    protected EnergyNetProvider getEnergyProvider(SlimefunItem item){
        if(item!=null&& isCollectable(item)&&item instanceof EnergyNetProvider ec){
            return ec;
        }else return null;
    }
    public abstract int getMaxCollectAmount();

    @Override
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        Location loc=menu.getLocation();
        final AtomicCounter charge=new AtomicCounter(this.getCharge(loc,data),this.energybuffer) ;
        AtomicInteger energyProvider= new AtomicInteger(0);
        AtomicInteger errorMachine=new AtomicInteger(0);
        boolean lazymod= getStatus(menu)[0];
        Collection<SlimefunBlockData> allDatas= getCollectRange(menu,b,data);
        if(allDatas!=null&&!allDatas.isEmpty()){
            List< CompletableFuture<Boolean>> gens=new ArrayList<>();
            for (SlimefunBlockData sf : allDatas) {
                SlimefunItem item=SlimefunItem.getById(sf.getSfId());
                EnergyNetProvider ec= getEnergyProvider(item);
                if(ec!=null){
                    //懒惰模式且满了
                    if(lazymod&&(charge.enough())){
                        break;
                    }
                    if(!sf.isDataLoaded()){
                        DataCache.requestLoad(sf);
                        continue;
                    }else if(sf.isPendingRemove()){
                        continue;
                    }
                    Location testLocation=sf.getLocation();
                    if(loc.equals(testLocation)){
                        continue;
                    }
                    CompletableFuture<Boolean> future1=CompletableFuture.supplyAsync(()->{
                        int more=0;
                        boolean tickGenerator=false;
                        if (ec.isChargeable()&&(!lazymod||!charge.enough())) {
                            int stored=ec.getCharge(testLocation,sf);
                            ec.setCharge(testLocation,0);
                            more+=charge.safeIncrement(stored);
                        }
                        if(!lazymod||!charge.enough()){
                            int energy = ec.getGeneratedOutput(testLocation,sf);
                            tickGenerator=true;
                            //尝试加入新energy
                            more=MathUtils.safeAdd(more,charge.safeIncrement(energy));
                        }
                        if(more>0&&ec.isChargeable()){
                            ec.setCharge(testLocation,more);
                        }
                        return tickGenerator;
                    }).exceptionally(ex->{
                        errorMachine.getAndIncrement();
                        new ErrorReport<>(ex, testLocation, item);return null;
                    });
                    gens.add(future1);
                    future1.thenApply((more)->{
                        if(more==null)return null;
                        if(more&&ec.willExplode(testLocation,sf)){
                            DataCache.removeBlockData(testLocation);
                            Slimefun.runSync(() -> {
                                testLocation.getBlock().setType(Material.LAVA);
                                testLocation.getWorld().createExplosion(testLocation, 0F, false);
                            });
                        }
                        return null;
                    }).exceptionally(ex->{
                        new ErrorReport<>(ex, testLocation, item);return null;
                    });
                    if(energyProvider.incrementAndGet() >= getMaxCollectAmount()){
                        break;
                    }
                }
            }
            if(!gens.isEmpty()){
                CompletableFuture.allOf(gens.toArray(CompletableFuture[]::new)).thenRun(()->{
                    this.setCharge(loc, charge.get());

                    if(menu.hasViewer()){
                        menu.replaceExistingItem(getInfoSlot(),getInfoShow(charge.get(),energyProvider.get(), errorMachine.get()));
                    }
                });
            }else{
                if(menu.hasViewer()){
                    menu.replaceExistingItem(getInfoSlot(),getInfoShow(charge.get(),energyProvider.get(), errorMachine.get()));
                }
            }
        }

    }

    public void tickAsync(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        Location loc=menu.getLocation();
        final AtomicCounter charge=new AtomicCounter(this.getCharge(loc,data),this.energybuffer) ;
        int energyProvider=0;
        AtomicInteger errorMachine=new AtomicInteger(0);
        boolean lazymod= getStatus(menu)[0];
        Collection<SlimefunBlockData> allDatas= getCollectRange(menu,b,data);
        if(allDatas!=null&&!allDatas.isEmpty()){
            List< CompletableFuture<Boolean>> gens=new ArrayList<>();
            for (SlimefunBlockData sf : allDatas) {
                SlimefunItem item=SlimefunItem.getById(sf.getSfId());
                EnergyNetProvider ec= getEnergyProvider(item);
                if(ec!=null){
                    //懒惰模式且满了
                    if(lazymod&&(charge.enough())){
                        break;
                    }
                    if(!sf.isDataLoaded()){
                        DataCache.requestLoad(sf);
                        continue;
                    }else if(sf.isPendingRemove()){
                        continue;
                    }
                    Location testLocation=sf.getLocation();
                    if(loc.equals(testLocation)){
                        continue;
                    }
                    CompletableFuture<Boolean> future1=CompletableFuture.supplyAsync(()->{
                        int more=0;
                        boolean tickGenerator=false;
                        if (ec.isChargeable()&&(!lazymod||!charge.enough())) {
                            int stored=ec.getCharge(testLocation,sf);
                            ec.setCharge(testLocation,0);
                            more+=charge.safeIncrement(stored);
                        }
                        if(!lazymod||!charge.enough()){
                            int energy = ec.getGeneratedOutput(testLocation,sf);
                            tickGenerator=true;
                            //尝试加入新energy
                            more=MathUtils.safeAdd(more,charge.safeIncrement(energy));
                        }
                        if(more>0&&ec.isChargeable()){
                            ec.setCharge(testLocation,more);
                        }
                        return tickGenerator;
                    }).exceptionally(ex->{
                        errorMachine.getAndIncrement();
                        new ErrorReport<>(ex, testLocation, item);return null;
                    });
                    gens.add(future1);
                    future1.thenApply((more)->{
                        if(more==null)return null;
                        if(more&&ec.willExplode(testLocation,sf)){
                            DataCache.removeBlockData(testLocation);
                            Slimefun.runSync(() -> {
                                testLocation.getBlock().setType(Material.LAVA);
                                testLocation.getWorld().createExplosion(testLocation, 0F, false);
                            });
                        }
                        return null;
                    }).exceptionally(ex->{
                        new ErrorReport<>(ex, testLocation, item);return null;
                    });
                    energyProvider+=1;
                    if(energyProvider>= getMaxCollectAmount()){
                        break;
                    }
                }
            }
            if(!gens.isEmpty()){
                CompletableFuture.allOf(gens.toArray(CompletableFuture[]::new)).join();
            }
        }
        this.setCharge(loc, charge.get());

        if(menu.hasViewer()){
            menu.replaceExistingItem(getInfoSlot(),getInfoShow(charge.get(),energyProvider, errorMachine.get()));
        }
    }
}
