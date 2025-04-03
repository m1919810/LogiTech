package me.matl114.logitech.core.Items.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.DistinctiveCustomSlimefunItem;
import me.matl114.matlib.utils.CraftUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * functional items ,probably rechargable item/usable item/weapon
 * should check pdc,lore enchant attribute
 */
public class FunctionalItem extends DistinctiveCustomSlimefunItem {
    public FunctionalItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayInfo) {
        super(itemGroup, item, recipeType, recipe, displayInfo);
    }
    public FunctionalItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    @Override
    public boolean canStack(@NotNull ItemMeta var1, @NotNull ItemMeta var2) {
        //most of them are weapons
        return var1.getPersistentDataContainer().equals(var2.getPersistentDataContainer()) && CraftUtils.matchLoreField(var1,var2) && CraftUtils.matchEnchantmentsFields(var1,var2) && me.matl114.logitech.utils.CraftUtils.matchAttrbuteField(var1,var2);
    }
}
