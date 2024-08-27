package me.matl114.logitech.SlimefunItem.Cargo.CargoMachine;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.Cargo.Config.ChipCardCode;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.MathUtils;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChipCopier extends AbstractSyncTickCargo{
    protected final int[] BORDER=new int[]{
            3,4,5,12,14,21,22,23
    };
    protected final int[] INPUT_BORDER=new int[]{
            0,2,9,11,18,19,20
    };
    protected final int[] OUTPUT_BORDER=new int[]{
            6,8,15,17,24,25,26
    };
    protected final int[] SAMPLE_SLOT=new int[]{
            10
    };

    protected final int[] INFO_SLOT=new int[]{
            1,7
    };
    protected final ItemStack[] INFO_ITEMS=new ItemStack[]{
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6样板芯片槽"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a输入/输出槽")
    };
    protected final int STATUS_SLOT=13;
    protected int[] SLOTS = {
           16
    };
    public int[] getInputSlots(){
        return SLOTS;
    }
    public int[] getOutputSlots(){
        return SLOTS;
    }
    public ChipCopier(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public ItemStack getInfoItem( int tickCount){
        int ticks=tickCount%32;
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a信息"
                , AddUtils.concat("&7当前读取编码位数: ",String.valueOf(ticks)));
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=INPUT_BORDER;
        len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getInputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=OUTPUT_BORDER;
        len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getOutputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=INFO_SLOT;
        len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i],INFO_ITEMS[i],ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(STATUS_SLOT,getInfoItem(0),ChestMenuUtils.getEmptyClickHandler());

    }
    public void newMenuInstance(BlockMenu inv,Block b ){

    }
    public void updateMenu(BlockMenu inv, Block b, Settings mod){

    }
    public void syncTick(Block b, BlockMenu inv, SlimefunBlockData data, int synTickCount){
        ItemStack it1=inv.getItemInSlot(SAMPLE_SLOT[0]);
        if(it1==null)return;
        ItemStack it2=inv.getItemInSlot(SLOTS[0]);
        if(it2==null)return;
        ItemMeta meta1=it1.getItemMeta();
        if(meta1!=null&& ChipCardCode.isConfig(meta1)){
            ItemMeta meta2=it2.getItemMeta();
            if(meta2!=null&&ChipCardCode.isConfig(meta2)){
                int code1=ChipCardCode.getConfig(meta1);
                int code2=ChipCardCode.getConfig(meta2);
                synTickCount=synTickCount%32;
                int pos= code1&MathUtils.getBitPos(synTickCount);
                int pos2=code1&pos;
                if(pos2==0){
                    ChipCardCode.setConfig(meta2, code2&(~pos));
                }else{
                    ChipCardCode.setConfig(meta2, code2|pos);
                }
                it2.setItemMeta(meta2);
            }
        }
    }
    public void onBreak(BlockBreakEvent e,BlockMenu inv){
        super.onBreak(e,inv);
        if(inv!=null){
            inv.dropItems(inv.getLocation(),SAMPLE_SLOT);
        }
    }
}
