package me.matl114.logitech.SlimefunItem.Cargo.StorageMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.inventory.ItemStack;

public class IOPort extends AbstractIOPort{
    protected static final int[] OUTPUT_SLOT=new int[]{
            5,6,7,8,
            14,15,16,17,
            23,24,25,26,
            32,33,34,35,
            41,42,43,44,
            50,51,52,53
    };
    protected static final int[] INPUT_SLOT=new int[]{
            0,1,2,3,
            9,10,11,12,
            18,19,20,21,
            27,28,29,30,
            36,37,38,39,
            45,46,47,48
    };
    protected static final int[] BORDER=new int[]{
            31,40,49
    };
    public int getDisplaySlot(){
        return 22;
    }
    public int getStorageSlot(){
        return 13;
    }
    protected static int INFO_SLOT=4;

    public IOPort(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(category, item, recipeType, recipe);
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }

    public  void addInfo(ItemStack item){

    }
    public  void constructMenu(BlockMenuPreset preset){
        //空白背景 禁止点击
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        //空白边框
        preset.addItem(getDisplaySlot(), ITEM_DISPLAY_NULL, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INFO_SLOT, INFO_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.setSize(54);
    }
}
