package me.matl114.logitech.core.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMultiThreadProcessor;
import me.matl114.logitech.core.Registries.RecipeSupporter;
import me.matl114.logitech.manager.PostSetupTasks;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

public class MTMachine extends AbstractMultiThreadProcessor {
    public MTMachine(
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

    public MTMachine(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            ItemStack processbar,
            int energyConsumption,
            int energyBuffer,
            Supplier<List<MachineRecipe>> machineRecipeSupplier) {
        super(category, item, recipeType, recipe, processbar, energyConsumption, energyBuffer, null);
        this.machineRecipeSupplier = machineRecipeSupplier;
        PostSetupTasks.addPostRegisterTask(() -> {
            getMachineRecipes();
        });
    }

    public MTMachine(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            ItemStack processbar,
            int energyConsumption,
            int energyBuffer,
            RecipeType... recipeTypes) {
        this(category, item, recipeType, recipe, processbar, energyConsumption, energyBuffer, () -> {
            if (recipeTypes == null || recipeTypes.length == 0) {
                return new ArrayList<MachineRecipe>();
            } else {
                List<MachineRecipe> mr = new ArrayList<>();
                for (RecipeType rt : recipeTypes) {
                    if (rt != null) {
                        List<MachineRecipe> rep = RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(rt);
                        if (rep == null) rep = new ArrayList<>();
                        mr.addAll(rep);
                    }
                }
                return mr;
            }
        });
    }
}
