package me.matl114.logitech.SlimefunItem.Cargo.StorageMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.inventory.ItemStack;

public class InputPort extends AbstractIOPort {
    protected static final int[] OUTPUT_SLOT=new int[]{
            8,17,26,35,44,53
    };
    protected static final int[] INPUT_SLOT=new int[]{
            0,1,2,3,4,5,6,
            9,10,11,12,13,14,15,
            18,19,20,21,22,23,24,
            27,28,29,30,31,32,33,
            36,37,38,39,40,41,42,
            45,46,47,48,49,50,51
    };
    protected static final int[] BORDER=new int[]{
            34,43,52
    };
    public int getDisplaySlot(){
        return 25;
    }
    public int getStorageSlot(){
        return 16;
    }
    protected static int INFO_SLOT=7;

    public InputPort(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          int energybuffer, int energyConsumption){
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
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
