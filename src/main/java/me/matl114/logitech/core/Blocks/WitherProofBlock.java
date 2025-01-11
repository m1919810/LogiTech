package me.matl114.logitech.core.Blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.WitherProof;
import org.bukkit.block.Block;
import org.bukkit.entity.Wither;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class WitherProofBlock extends AbstractBlock implements WitherProof {
    public WitherProofBlock(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public void onAttack(@Nonnull Block var1, @Nonnull Wither var2){

    }
}
