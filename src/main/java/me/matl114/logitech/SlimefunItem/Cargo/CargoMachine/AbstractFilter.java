package me.matl114.logitech.SlimefunItem.Cargo.CargoMachine;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.Blocks.AbstractBlock;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.Algorithms.DynamicArray;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.TransportUtils;
import me.matl114.logitech.Utils.UtilClass.CargoClass.CargoConfigs;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.TickerClass.Ticking;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFilter extends AbstractBlock implements Ticking {
    public AbstractFilter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public ItemStack CLEAN_ITEM_OFF=new CustomItemStack(SlimefunItems.TRASH_CAN,"&3开启输入槽清空模式","&7当输入槽物品无法匹配输出槽白名单时,会清空输入槽","&7当前状态: &c关闭");
    public ItemStack CLEAN_ITEM_ON=new CustomItemStack(SlimefunItems.TRASH_CAN,"&3关闭输入槽清空模式","&7当输入槽物品无法匹配输出槽白名单时,会清空输入槽","&7当前状态: &a开启");
    public abstract int[] getInputSlots();
    public abstract int[] getOutputSlots();
    public abstract int[] getOutputWLSlot();
    public abstract int[] getInputBorders();
    public abstract int[] getBorders();
    public abstract int[] getOutputBorders();
    public abstract ItemStack[] getOutputBordersItem();
    public void constructMenu(BlockMenuPreset preset){
        int[] border=getInputBorders();
        int len=border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getInputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=getBorders();
        len=border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i],ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=getOutputBorders();
        len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i],getOutputBordersItem()[i],ChestMenuUtils.getEmptyClickHandler());
        }
    }
    public void newMenuInstance(BlockMenu menu, Block b){
        SlimefunBlockData data=DataCache.safeLoadBlock(menu.getLocation());
        String clean=DataCache.getCustomString(data,KEY_CLEAN,"no");
        if(clean.length()==1){
            menu.replaceExistingItem(getTrashSlot(),CLEAN_ITEM_ON);
        }else {
            menu.replaceExistingItem(getTrashSlot(),CLEAN_ITEM_OFF);
        }
        menu.addMenuClickHandler(getTrashSlot(),((player, i, itemStack, clickAction) -> {
            SlimefunBlockData datas=DataCache.safeLoadBlock(menu.getLocation());
            String cleancode=DataCache.getCustomString(data,KEY_CLEAN,"no");
            if(cleancode.length()==1){
                DataCache.setCustomString(datas,KEY_CLEAN,"no");
                menu.replaceExistingItem(getTrashSlot(),CLEAN_ITEM_OFF);
            }else {
                DataCache.setCustomString(datas,KEY_CLEAN,"y");
                menu.replaceExistingItem(getTrashSlot(),CLEAN_ITEM_ON);
            }
            return false;
        }));
    }

   // public abstract void updateMenu(BlockMenu blockMenu, Block block, Settings mod);
    public abstract int getTrashSlot();
    protected String KEY_CLEAN="filterclean";
    public void tick(Block b,BlockMenu inv,SlimefunBlockData data ,int tickCount){
        int[] inputSlots = getInputSlots();
        int[] outputSlots = getOutputSlots();
        int[] outputWLSlot = getOutputWLSlot();
        int len=inputSlots.length;
        String code= DataCache.getCustomString(data,KEY_CLEAN,"no");
        ItemStack stack;
        DynamicArray<ItemConsumer> wlCounters=new DynamicArray<>(ItemConsumer[]::new,outputSlots.length,(i)-> CraftUtils.getConsumer(inv.getItemInSlot(outputWLSlot[i])));
        for(int i=0;i<len;i++){
            int[] nowInputSlot=new int[]{inputSlots[i]};
            stack=inv.getItemInSlot(inputSlots[i]);
            if(stack==null||stack.getType().isAir()){
                continue;
            }
            ItemCounter stackCounter=CraftUtils.getCounter(stack);
            ItemConsumer consumer;
            boolean hasTransported=false;
            for(int j=0;j<outputSlots.length-1;j++){
                consumer = wlCounters.get(j);
                if(consumer==null){
                    continue;
                }
                if(CraftUtils.matchItemCore(consumer,stackCounter,false)){
                    TransportUtils.transportItem(inv,nowInputSlot,inv,new int[]{outputSlots[j]}, CargoConfigs.getDefaultConfig(),false,null,CraftUtils.getpusher);
                    hasTransported=true;
                }
            }
            if(!hasTransported){
                if(code.length()==1){
                    inv.replaceExistingItem(inputSlots[i],null,false);
                }else{
                    TransportUtils.transportItem(inv,nowInputSlot,inv,new int[]{outputSlots[outputSlots.length-1]},CargoConfigs.getDefaultConfig(),false,null,CraftUtils.getpusher);
                }
            }
        }
        if(code.length()==1){
            inv.replaceExistingItem(outputSlots[outputSlots.length-1],null,false);
        }
    }
    public void preRegister(){
        super.preRegister();
        registerBlockMenu(this);
        registerTick(this);
    }
}
