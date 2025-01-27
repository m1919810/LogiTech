package me.matl114.logitech.core.Items.Equipments;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop.InterfacedSmithTableTrimmer;
import me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop.SmithInterfaceProcessor;
import me.matl114.logitech.core.Items.Abstracts.MaterialItem;
import me.matl114.matlib.Utils.Algorithm.Triplet;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingInventory;

import java.util.function.Function;

@Deprecated //not completed yet
public class DisplayUseTrimmerLogic extends MaterialItem implements InterfacedSmithTableTrimmer {
    public DisplayUseTrimmerLogic(ItemGroup itemGroup, SlimefunItemStack item, ItemStack[] displayUse,RecipeChoice trim,RecipeChoice base, Function<SmithingInventory,ItemStack[]> trimmerLogic) {
        super(itemGroup, item, SmithInterfaceProcessor.INTERFACED_SMITH_TRIM, displayUse);
    }

    @Override
    public Triplet<ItemStack[],Integer,Integer> getTrimmedResult(SmithingInventory inventory) {
        return null;
    }

    @Override
    public RecipeChoice getTrimmerRecipeChoice() {
        return null;
    }

    @Override
    public RecipeChoice getBaseRecipeChoice() {
        return null;
    }

    @Override
    public RecipeChoice getExtraRecipeChoice() {
        return null;
    }

}
