package me.matl114.logitech.core.Cargo.CargoMachine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BiFilter extends AbstractFilter{
    protected final int[] INPUT_BORDER=new int[]{
            0,1,2,3,4,5,6,7,8
    };
    protected final int[] INPUT_SLOTS=new int[]{
            9,10,11,12,13,14,15,16,17
    };
    protected final int[] BORDER=new int[]{
            18,19,20,21,22,23,24,25,26,27,29,30,32,33,35,36,38,39,41,42,44,45,47,48,50,51,53
    };

    protected final int[] OUTPUT_BORDER=new int[]{
            37,40,43
    };
    protected final ItemStack[] OUTPUT_BORDERS_ITEM=new ItemStack[]{
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6槽位0","&7上方为输出槽0","&7下方为白名单槽0"),
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6默认槽位","&7上方为默认输出槽","&7下方为输入槽清空模式切换按钮"),
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6槽位0","&7上方为输出槽0","&7下方为白名单槽0")
    };
    protected final int[] OUTPUT_SLOTS=new int[]{
            28,34,31
    };
    protected final int[] OUTPUT_WLSLOTS=new int[]{
            46,52
    };
    protected final int TRASH_SLOT=49;
    public BiFilter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    public int[] getOutputWLSlot(){
        return OUTPUT_WLSLOTS;
    }
    public int getTrashSlot(){
        return TRASH_SLOT;
    }
    public int[] getInputBorders(){
        return INPUT_BORDER;
    }
    public int[] getBorders(){
        return BORDER;
    }
    public int[] getOutputBorders(){
        return OUTPUT_BORDER;
    }
    public ItemStack[] getOutputBordersItem(){
        return OUTPUT_BORDERS_ITEM;
    }

}
