package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class AbstractEnergyCharger extends AbstractEnergyMachine {
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
            "&7当启用懒惰模式时,只有电力剩余不足50%的机器会被充能","&7这会大幅度的减少充能耗时");
    protected final ItemStack LAZY_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换懒惰模式","&7当前状态: &a开启",
            "&7当启用懒惰模式时,只有电力剩余不足50%的机器会被充能","&7这会大幅度的减少充能耗时");

    public AbstractEnergyCharger(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                               int energybuffer){
        super(category, item, recipeType, recipe, energybuffer, 0,EnergyNetComponentType.CONSUMER);
    }

    protected ItemStack getInfoShow(int charge,int machine,int errors){
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6信息","&7已存储: %dJ/%dJ".formatted(charge,energybuffer),
                "&7范围用电器数目: %d".formatted(machine),
                "&7充电报错数目: %d".formatted(errors));
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
    public abstract Collection<SlimefunBlockData> getChargeRange(BlockMenu menu,Block block,SlimefunBlockData data);
    protected boolean isChargeable(SlimefunItem that){
        return true;
    }
    @Override
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        Location loc=menu.getLocation();
        int charge=this.getCharge(loc,data);
        int energyConsumer=0;
        int errorMachine=0;
        ItemStack lazymodItem= menu.getItemInSlot(getLazySlot());
        boolean lazymod= lazymodItem==null||lazymodItem.getType()!=Material.RED_STAINED_GLASS_PANE;
        Collection<SlimefunBlockData> allDatas=getChargeRange(menu,b,data); //DataCache.getAllSfItemInChunk(loc.getWorld(),loc.getBlockX()>>4,loc.getBlockZ()>>4);
        if(allDatas!=null&&!allDatas.isEmpty()){
            Location testLocation;
            for (SlimefunBlockData sf : allDatas) {
                SlimefunItem item=SlimefunItem.getById(sf.getSfId());

                if(item!=null&&isChargeable(item)&&item instanceof EnergyNetComponent ec){
                    if(ec.getEnergyComponentType()== EnergyNetComponentType.CONSUMER){
                        if(!sf.isDataLoaded()){
                            DataCache.requestLoad(sf);
                            continue;
                        }
                        energyConsumer++;
                        if(charge>0){
                            testLocation=sf.getLocation();
                            try{
                                int testCharge=ec.getCharge(testLocation,sf);
                                int buffer=ec.getCapacity();
                                int left=buffer-testCharge;
                                if(left>0&&(!lazymod||left>=testCharge)){
                                    int add=Math.min(left,charge);
                                    ec.setCharge(testLocation,add+testCharge);
                                    charge-=add;
                                }
                            }catch (Throwable e){
                                errorMachine++;
                            }
                        }
                    }
                }
            }
        }
        this.setCharge(loc,charge);
        if(menu.hasViewer()){
            menu.replaceExistingItem(getInfoSlot(),getInfoShow(charge,energyConsumer, errorMachine));
        }
    }
}
