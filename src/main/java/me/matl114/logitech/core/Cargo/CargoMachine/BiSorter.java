package me.matl114.logitech.core.Cargo.CargoMachine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BiSorter extends AbstractSorter {
    protected int slotNumber = 2;
    protected int[] BORDER = new int[] {1, 3, 5, 7, 10, 12, 13, 14, 16, 18, 19, 20, 21, 23, 24, 25, 26};
    protected int[] INPUT_SLOT = new int[] {11, 15};
    protected int[] INPUTW_SLOT = new int[] {9, 17};
    protected int[] OUTPUT_SLOT = new int[] {22};
    protected int[] INFO_SLOT = new int[] {0, 2, 6, 8};
    protected ItemStack[] INFO_ITEM = new ItemStack[] {
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽0", "&7设置白名单"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽0", "&7货运输入~"),
        // new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a信息","&7下方为货运输出槽","&7上方为芯片槽"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6输入槽1", "&7货运输入~"),
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6白名单槽1", "&7设置白名单"),
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
        return 4;
    }

    public int getInfoSlot() {
        return 13;
    }

    public BiSorter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe, 2);
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
