package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.SlimefunItem.Machines.AbstractEnergyProcessor;
import me.matl114.logitech.SlimefunItem.Machines.AbstractEnergyProvider;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;

public class EGenerator extends AbstractEnergyProcessor {
    protected final int[] INPUT_SLOT=new int[]{
            19,20
    };
    protected final int[] OUTPUT_SLOT=new int[]{
            24,25
    };
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public EGenerator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material progressItem,
                      int energyBuffer, int energyProvider, List<Pair<Object,Integer>> customRecipe) {
        super(category,item,recipeType,recipe,progressItem,energyBuffer,energyProvider,customRecipe);
    }
}
