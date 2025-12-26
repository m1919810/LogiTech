package me.matl114.logitech.core.Cargo.CargoMachine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class OctaSorter extends AbstractSorter {
    protected int slotNumber = 8;
    protected int[] BORDER = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 40, 45, 46, 47, 48, 49, 50, 51, 52, 53};
    protected int[] INPUT_SLOT = new int[] {11, 20, 29, 38, 42, 33, 24, 15};
    protected int[] INPUTW_SLOT = new int[] {9, 18, 27, 36, 44, 35, 26, 17};
    protected int[] OUTPUT_SLOT = new int[] {31};
    protected int[] INFO_SLOT = new int[] {
        // 0,2,6,8,36,38,42,44
        10, 19, 28, 37, 43, 34, 25, 16,
        12, 21, 30, 39, 41, 32, 23, 14
    };
    protected ItemStack[] INFO_ITEM = new ItemStack[] {
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽0", "&7设置白名单"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽1", "&7设置白名单"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽2", "&7设置白名单"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽3", "&7设置白名单"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽4", "&7设置白名单"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽5", "&7设置白名单"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽6", "&7设置白名单"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽7", "&7设置白名单"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽0", "&7货运输入~"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽1", "&7货运输入~"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽2", "&7货运输入~"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽3", "&7货运输入~"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽4", "&7货运输入~"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽5", "&7货运输入~"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽6", "&7货运输入~"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽7", "&7货运输入~"),
    };

    public int[] getInputSlots() {
        return INPUT_SLOT;
    }

    public int[] getInputWLSlot() {
        return INPUTW_SLOT;
    }

    public int[] getOutputSlots() {
        return OUTPUT_SLOT;
    }

    public int getChipSlot() {
        return 13;
    }

    public int getInfoSlot() {
        return 22;
    }

    public OctaSorter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 8);
    }

    public void constructMenu(BlockMenuPreset preset) {
        int[] border = BORDER;
        int len = border.length;
        for (int i = 0; i < len; i++) {
            preset.addItem(border[i], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        border = INFO_SLOT;
        len = border.length;
        for (int i = 0; i < len; i++) {
            preset.addItem(border[i], INFO_ITEM[i], ChestMenuUtils.getEmptyClickHandler());
        }
    }
}
