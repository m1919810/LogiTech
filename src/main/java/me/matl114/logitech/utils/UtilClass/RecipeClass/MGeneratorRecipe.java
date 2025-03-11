package me.matl114.logitech.utils.UtilClass.RecipeClass;

import org.bukkit.inventory.ItemStack;

public class MGeneratorRecipe extends StackMachineRecipe{
    public MGeneratorRecipe(StackMachineRecipe r){
        super(r.getTicks(),r.getInput(),r.getOutput());
    }
    public MGeneratorRecipe(int ticks, ItemStack[] input, ItemStack[] output){
        super(ticks,input,output);
    }
}
