package me.matl114.logitech.SlimefunItem.Blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

public class MultiBlockPart extends AbstractBlock{
    public final String BLOCKID;
    public MultiBlockPart(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,String blockId) {
        super(itemGroup, item, recipeType, recipe);
        this.BLOCKID = blockId;
    }
    public String getPartId(){
        return BLOCKID;
    }
}
