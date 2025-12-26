package me.matl114.logitech.core.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import java.util.HashMap;
import java.util.HashSet;
import me.matl114.logitech.core.Registries.RecipeSupporter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpecialTypeCrafter extends SpecialCrafter {
    @Override
    public HashMap<SlimefunItem, RecipeType> getRecipeTypeMap() {
        return RecipeSupporter.CUSTOM_RECIPETYPES;
    }

    public boolean advanced() {
        return false;
    }

    public SpecialTypeCrafter(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            Material progressItem,
            int ticks,
            int energyConsumption,
            int energyBuffer,
            HashSet<RecipeType> blackList) {
        super(category, item, recipeType, recipe, progressItem, ticks, energyConsumption, energyBuffer, blackList);
    }
}
