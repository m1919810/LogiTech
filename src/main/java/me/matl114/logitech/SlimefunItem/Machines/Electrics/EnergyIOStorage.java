package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.matl114.logitech.SlimefunItem.Interface.EnergyProvider;
import org.bukkit.inventory.ItemStack;

public class EnergyIOStorage extends EnergyStorage implements EnergyProvider {
    public EnergyIOStorage(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         int energybuffer){
        super(category, item, recipeType, recipe, energybuffer, EnergyNetComponentType.GENERATOR);
    }
}
