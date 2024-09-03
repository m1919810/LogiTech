package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.Machines.FinalFeature;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class FinalStackMachine extends StackMachine{
    public FinalStackMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                        Material progressItem, int energyConsumption, int energyBuffer, double efficiency) {
        super(category, item, recipeType, recipe, progressItem, energyConsumption, energyBuffer, efficiency);
        AddUtils.addGlow(getProgressBar());
        this.CRAFT_PROVIDER= FinalFeature.STORAGE_READER;
    }
    public int getCraftLimit(Block b, BlockMenu inv){

        return (int)(this.efficiency*this.CRAFT_PROVIDER.getPusher(Settings.OUTPUT,inv,this.MACHINE_SLOT).getAmount());
    }
}
