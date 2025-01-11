package me.matl114.logitech.core.Machines.ManualMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Manager.PostSetupTasks;
import me.matl114.logitech.core.Machines.Abstracts.AbstractManual;

import me.matl114.logitech.core.Registries.RecipeSupporter;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ImportRecipes;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ManualCrafter extends AbstractManual implements ImportRecipes {
    public  List<ItemStack> displayedMemory = null;
    protected final RecipeType[] craftType;
    public ManualCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          int energybuffer, int energyConsumption,
                          RecipeType... craftType) {
        super(category,item,recipeType,recipe,energybuffer,energyConsumption,null);
        this.craftType = craftType;
        this.machineRecipeSupplier=()->{
            if(this.craftType==null||this.craftType.length<=0){
                return new ArrayList<>();
            }
            else {
                List<MachineRecipe> recipes = new ArrayList<>();
                for(RecipeType rt : this.craftType){
                    if(rt!=null)
                        recipes.addAll(RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(rt));
                }
                return recipes;
            }
        };
        PostSetupTasks.addPostRegisterTask(()->{
            getDisplayRecipes();
        });
    }
}
