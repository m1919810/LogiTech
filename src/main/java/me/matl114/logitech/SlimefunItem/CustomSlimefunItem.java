package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.matl114.logitech.SlimefunItem.Machines.RecipeDisplay;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomSlimefunItem extends SlimefunItem implements RecipeDisplay {
    public List<ItemStack> displayedMemory;
    public CustomSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        this(itemGroup, item, recipeType, recipe,new ArrayList<>());
    }
    public CustomSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,List<ItemStack> displayInfo){
        super(itemGroup, item, recipeType, recipe);
        if (displayInfo != null) {
            this.displayedMemory = displayInfo;
        }else{
            this.displayedMemory = new ArrayList<>();
        }
    }
    public void addInfo(ItemStack stack){

    }
    public List<MachineRecipe> provideDisplayRecipe(){
        return new ArrayList<>();
    }
    public final List<ItemStack> getDisplayRecipes() {
        if(displayedMemory==null||displayedMemory.isEmpty()) {
            displayedMemory=_getDisplayRecipes();
        }
        return displayedMemory;
    }
    public CustomSlimefunItem addHandler(ItemHandler handler) {
        this.addItemHandler(handler);
        return this;
    }
    public CustomSlimefunItem setDisplayRecipes(List<ItemStack> displayRecipes) {
        this.displayedMemory = displayRecipes;
        return this;
    }
    public CustomSlimefunItem setOutput(Object obj){
        this.recipeOutput= AddUtils.resolveItem(obj);
        return this;
    }
    public SlimefunItem register(){
        if(AddSlimefunItems.INSTANCE!=null){
            register(AddSlimefunItems.INSTANCE);
        }else{
            Debug.logger("找不到附属实例!  注册信息: "+this.toString());
        }
        return this;
    }
    public void preRegister(){
        super.preRegister();
        addInfo(this.getItem());

    }
    public void postRegister(){
        super.postRegister();
    }

}
