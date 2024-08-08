package me.matl114.logitech.SlimefunItem.Blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AbstractBlock extends CustomSlimefunItem {
    public AbstractBlock(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public void addInfo(ItemStack stack){

    }
}
