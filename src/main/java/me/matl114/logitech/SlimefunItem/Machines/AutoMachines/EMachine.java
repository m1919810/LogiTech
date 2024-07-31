package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.Machines.AbstractProcessor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class EMachine extends AbstractProcessor {


    public EMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                    Material processbar, int energyConsumption, int energyBuffer,
                    LinkedHashMap<Object, Integer> customRecipes) {
        super(category,item,recipeType,recipe,processbar,energyConsumption,energyBuffer,customRecipes);

    }
    public void addInfo(ItemStack stack){
        super.addInfo(stack);
    }
    public ItemStack getProgressBar() {
        return progressbar;
    }

}
