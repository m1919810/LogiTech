package me.matl114.logitech.core.Cargo.Config;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.DistinctiveCustomSlimefunItem;
import org.bukkit.inventory.ItemStack;

public class ChipCard extends DistinctiveCustomSlimefunItem {
    public ChipCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(itemGroup, item, recipeType, recipe);
    }
}
