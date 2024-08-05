package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.Machines.AbstractAdvancedProcessor;
import me.matl114.logitech.Utils.RecipeSupporter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AdvanceCrafter extends AbstractAdvancedProcessor {
    public List<ItemStack> displayedMemory = null;
    protected final RecipeType[] craftType;
    protected final ItemStack progressBar;
    public AdvanceCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material progressItem,
                         int energybuffer, int energyConsumption,
                          RecipeType... craftType) {
        super(category,item,recipeType,recipe,progressItem,energybuffer,energyConsumption,null);
        this.progressBar = new ItemStack(progressItem);
        this.craftType = craftType;
    }

    @Override
    public ItemStack getProgressBar() {
        return progressBar;
    }

    public List<MachineRecipe> getMachineRecipes() {
        //this should not be null ,unless you are sb
        if(this.machineRecipes == null||this.machineRecipes.isEmpty()||this.craftType!=null) {
            if(this.craftType.length<=0){
                return new ArrayList<>();
            }
            else if(this.craftType.length==1){
                this.machineRecipes = RecipeSupporter.getStackedRecipes(this.craftType[0]);

            }else{
                this.machineRecipes=new ArrayList<>();
                for(RecipeType rt : this.craftType){
                    if(rt!=null)
                        this.machineRecipes.addAll(RecipeSupporter.getStackedRecipes(rt));
                }
            }
            if(this.machineRecipes==null) {
                this.machineRecipes = new ArrayList<>();
            }
        }
        return this.machineRecipes;
    }
}
