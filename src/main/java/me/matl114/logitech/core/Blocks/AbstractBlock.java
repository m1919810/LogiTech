package me.matl114.logitech.core.Blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.CustomSlimefunItem;
import me.matl114.logitech.core.DistinctiveCustomSlimefunItem;
import me.matl114.logitech.core.Interface.MenuBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.inventory.ItemStack;

/**
 * abstracts of unclassified sf blocks
 */
public class AbstractBlock extends CustomSlimefunItem implements MenuBlock {
    public AbstractBlock(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public void addInfo(ItemStack stack){

    }
    public void constructMenu(BlockMenuPreset preset){

    }
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public void preRegister() {
        super.preRegister();
        this.handleBlock(this);
    }


}
