package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.Machines.AbstractSequenceProcessor;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class SequenceConstructor extends AbstractSequenceProcessor {
    public SequenceConstructor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                               ItemStack progressItem, int energyConsumption, int energyBuffer,
                               LinkedHashMap<Object, Integer> customRecipes) {
        super(category,item,recipeType,recipe,progressItem,energyConsumption,energyBuffer,customRecipes);
    }
}
