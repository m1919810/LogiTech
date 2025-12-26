package me.matl114.logitech.core.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AdvanceCrafter {
    public List<ItemStack> displayedMemory = null;
    protected final RecipeType[] craftType;

    public AdvanceCrafter(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            Material progressItem,
            int energybuffer,
            int energyConsumption,
            RecipeType... craftType) {
        // super(category,item,recipeType,recipe,progressItem,energybuffer,energyConsumption,null);
        this.craftType = craftType;
    }
}
