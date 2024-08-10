package me.matl114.logitech.SlimefunItem.Machines.ManualMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.SlimefunItem.Machines.AbstractManual;

import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.RecipeSupporter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ManualMachine extends AbstractManual {
    public  List<ItemStack> displayedMemory = null;
    protected final Supplier<List<MachineRecipe>> machineRecipeSupplier;
    public ManualMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         int energybuffer, int energyConsumption,
                         Supplier<List<MachineRecipe>> machineRecipeSupplier) {
        super(category,item,recipeType,recipe,energybuffer,energyConsumption,null);
        this.machineRecipeSupplier = machineRecipeSupplier;
        //开服之后加载配方
        SchedulePostRegister.addPostRegisterTask(()->{
            getDisplayRecipes();
        });
    }
    public void registerDefaultRecipes(){
    }
    public List<MachineRecipe> getMachineRecipes() {
        //this should not be null ,unless you are sb
        if(this.machineRecipes == null||this.machineRecipes.isEmpty()||this.machineRecipeSupplier!=null) {
            this.machineRecipes=new ArrayList<>();
            this.machineRecipes.addAll(this. machineRecipeSupplier.get());

        }
        return this.machineRecipes;
    }
}
