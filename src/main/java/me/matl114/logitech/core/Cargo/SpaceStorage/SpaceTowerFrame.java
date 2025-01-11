package me.matl114.logitech.core.Cargo.SpaceStorage;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.Blocks.AbstractBlock;
import org.bukkit.inventory.ItemStack;

public class SpaceTowerFrame extends AbstractBlock {
    public SpaceTowerFrame(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
}
