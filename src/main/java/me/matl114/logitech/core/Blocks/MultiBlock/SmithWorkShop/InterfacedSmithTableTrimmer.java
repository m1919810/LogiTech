package me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop;

import me.matl114.matlib.Utils.Algorithm.Pair;
import me.matl114.matlib.Utils.Algorithm.Triplet;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingInventory;

public interface InterfacedSmithTableTrimmer {
    //can have multi output/like unequip/return empty trimmer
    //return the Triple<resultOutput,cost of material 3,time of processing>
    public Triplet<ItemStack[],Integer,Integer> getTrimmedResult(SmithingInventory inventory);
    public RecipeChoice getTrimmerRecipeChoice();
    public RecipeChoice getBaseRecipeChoice();
    public RecipeChoice getExtraRecipeChoice();
}
