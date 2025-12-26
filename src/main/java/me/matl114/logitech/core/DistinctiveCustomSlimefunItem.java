package me.matl114.logitech.core;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.DistinctiveItem;
import java.util.List;
import org.bukkit.inventory.ItemStack;

/**
 * abstracts of should-compare special Slimefun items
 */
public abstract class DistinctiveCustomSlimefunItem extends CustomSlimefunItem implements DistinctiveItem {
    public DistinctiveCustomSlimefunItem(
            ItemGroup itemGroup,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            List<ItemStack> displayInfo) {
        super(itemGroup, item, recipeType, recipe, displayInfo);
    }

    public DistinctiveCustomSlimefunItem(
            ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
}
