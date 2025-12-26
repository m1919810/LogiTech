package me.matl114.logitech.core.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import java.util.List;
import me.matl114.logitech.core.Machines.Abstracts.AbstractSequenceProcessor;
import org.bukkit.inventory.ItemStack;

public class SequenceConstructor extends AbstractSequenceProcessor {
    public SequenceConstructor(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            ItemStack progressItem,
            int energyConsumption,
            int energyBuffer,
            List<Pair<Object, Integer>> customRecipes) {
        super(category, item, recipeType, recipe, progressItem, energyConsumption, energyBuffer, customRecipes);
    }
}
