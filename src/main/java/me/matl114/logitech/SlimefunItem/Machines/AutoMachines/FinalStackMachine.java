package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FinalStackMachine extends StackMachine{
    public FinalStackMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                        Material progressItem, int energyConsumption, int energyBuffer, double efficiency) {
        super(category, item, recipeType, recipe, progressItem, energyConsumption, energyBuffer, efficiency);
        AddUtils.addGlow(getProgressBar());
        this.efficiency=efficiency;
    }
}
