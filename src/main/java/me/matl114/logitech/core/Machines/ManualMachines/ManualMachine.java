package me.matl114.logitech.core.Machines.ManualMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import java.util.List;
import java.util.function.Supplier;
import me.matl114.logitech.core.Machines.Abstracts.AbstractManual;
import me.matl114.logitech.manager.PostSetupTasks;
import me.matl114.logitech.utils.UtilClass.RecipeClass.ImportRecipes;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

public class ManualMachine extends AbstractManual implements ImportRecipes {
    public List<ItemStack> displayedMemory = null;

    public ManualMachine(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            int energybuffer,
            int energyConsumption,
            Supplier<List<MachineRecipe>> machineRecipeSupplier) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption, null);
        this.machineRecipeSupplier = machineRecipeSupplier;
        // 开服之后加载配方
        PostSetupTasks.addPostRegisterTask(() -> {
            getDisplayRecipes();
        });
    }

    public void registerDefaultRecipes() {}
}
