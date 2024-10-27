package me.matl114.logitech.SlimefunItem.Machines.Electrics;

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
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Interface.EnergyProvider;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.MathUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class AbstractEnergyCollector extends AbstractEnergyMachine implements EnergyProvider{
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
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for (int i=0;i<len;++i){
            if(border[i]!=getLazySlot()){
                preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
            }
        }
        preset.addItem(getInfoSlot(),getInfoShow(0,0,0),ChestMenuUtils.getEmptyClickHandler());
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        if(menu.getItemInSlot(getLazySlot())==null){
            menu.replaceExistingItem(getLazySlot(),LAZY_ITEM_ON);
        }
        menu.addMenuClickHandler(getLazySlot(),((player, i, itemStack, clickAction) -> {
            if(itemStack!=null&&itemStack.getType()==Material.GREEN_STAINED_GLASS_PANE){
                menu.replaceExistingItem(getLazySlot(),LAZY_ITEM_OFF);
            }else {
                menu.replaceExistingItem(getLazySlot(),LAZY_ITEM_ON);
            }
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
        int charge=this.getCharge(loc,data);
        int energyProvider=0;
        int errorMachine=0;
        ItemStack lazymodItem= menu.getItemInSlot(getLazySlot());
        boolean lazymod= lazymodItem==null||lazymodItem.getType()!=Material.RED_STAINED_GLASS_PANE;
        Collection<SlimefunBlockData> allDatas= getCollectRange(menu,b,data);
        if(allDatas!=null&&!allDatas.isEmpty()){
            Location testLocation;
            EnergyNetProvider ec;
            HashMap<SlimefunBlockData,EnergyNetProvider> tickedGenerators=new HashMap<>();
            for (SlimefunBlockData sf : allDatas) {
                SlimefunItem item=SlimefunItem.getById(sf.getSfId());
                if((ec= getEnergyProvider(item))!=null){
                    //懒惰模式且满了
                    if(lazymod&&(charge>this.energybuffer)){
                        break;
                    }
                    if(!sf.isDataLoaded()){
                        DataCache.requestLoad(sf);
                        continue;
                    }else if(sf.isPendingRemove()){
                        continue;
                    }
                    testLocation=sf.getLocation();
                    if(loc.equals(testLocation)){
                        continue;
                    }
                    try{
                        int energy = ec.getGeneratedOutput(testLocation,sf);
                        //尝试加入新energy
                        charge =Math.min(MathUtils.safeAdd(charge, energy),this.energybuffer);
                        if (ec.isChargeable()) {
                            int stored=ec.getCharge(testLocation,sf);
                            int transfered=Math.min(stored,this.energybuffer-charge);
                            charge+=transfered;
                            ec.setCharge(testLocation, stored-transfered);
                        }
                        tickedGenerators.put(sf,ec);
                        energyProvider+=1;
                    }catch (Exception | LinkageError throwable) {
                        errorMachine+=1;
                        new ErrorReport<>(throwable, testLocation, item);
                    }
                    if(energyProvider>= getMaxCollectAmount()){
                        break;
                    }
                }
            }
            Schedules.launchSchedules(
                    ()->{
                        for(Map.Entry<SlimefunBlockData,EnergyNetProvider> entry: tickedGenerators.entrySet()){
                            try{
                                Location location=entry.getKey().getLocation();
                                if (entry.getValue().willExplode(location,entry.getKey())) {
                                    DataCache.removeBlockData(location);
                                    Slimefun.runSync(() -> {
                                        location.getBlock().setType(Material.LAVA);
                                        location.getWorld().createExplosion(location, 0F, false);
                                    });
                                }
                            }catch (Exception | LinkageError throwable) {
                                new ErrorReport<>(throwable, entry.getKey().getLocation(), SlimefunItem.getById(entry.getKey().getSfId()));
                            }
                        }
                    },0,false,0
            );
        }
        this.setCharge(loc, charge);

        if(menu.hasViewer()){
            menu.replaceExistingItem(getInfoSlot(),getInfoShow(charge,energyProvider, errorMachine));
        }
    }
}
