package me.matl114.logitech.core.Cargo.CargoMachine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class OctaFilter extends AbstractFilter {
    protected final int[] INPUT_BORDER = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    protected final int[] INPUT_SLOTS = new int[] {9, 10, 11, 12, 13, 14, 15, 16, 17};
    protected final int[] BORDER = new int[] {18, 19, 20, 21, 22, 23, 24, 25, 26};

    protected final int[] OUTPUT_BORDER = new int[] {36, 37, 38, 39, 40, 41, 42, 43, 44};
    protected final ItemStack[] OUTPUT_BORDERS_ITEM = new ItemStack[] {
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6槽位0", "&7上方为输出槽0", "&7下方为白名单槽0"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6槽位1", "&7上方为输出槽1", "&7下方为白名单槽1"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6槽位2", "&7上方为输出槽2", "&7下方为白名单槽2"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6槽位3", "&7上方为输出槽3", "&7下方为白名单槽3"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6默认槽位", "&7上方为默认输出槽", "&7下方为输入槽清空模式切换按钮"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6槽位4", "&7上方为输出槽4", "&7下方为白名单槽4"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6槽位5", "&7上方为输出槽5", "&7下方为白名单槽5"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6槽位6", "&7上方为输出槽6", "&7下方为白名单槽6"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6槽位7", "&7上方为输出槽7", "&7下方为白名单槽7")
    };
    protected final int[] OUTPUT_SLOTS = new int[] {27, 28, 29, 30, 32, 33, 34, 35, 31};
    protected final int[] OUTPUT_WLSLOTS = new int[] {45, 46, 47, 48, 50, 51, 52, 53};
    protected final int TRASH_SLOT = 49;

    public OctaFilter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    public int[] getOutputWLSlot() {
        return OUTPUT_WLSLOTS;
    }

    public int getTrashSlot() {
        return TRASH_SLOT;
    }

    public int[] getInputBorders() {
        return INPUT_BORDER;
    }

    public int[] getBorders() {
        return BORDER;
    }

    public int[] getOutputBorders() {
        return OUTPUT_BORDER;
    }

    public ItemStack[] getOutputBordersItem() {
        return OUTPUT_BORDERS_ITEM;
    }
}
