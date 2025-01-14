package me.matl114.logitech.core;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.Interface.RecipeDisplay;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomSlimefunItem extends SlimefunItem implements RecipeDisplay{
    public List<ItemStack> displayedMemory;
    public List<ItemStack> originalMemory;
    protected boolean checkCanStack=true;
    public CustomSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        this(itemGroup, item, recipeType, recipe,new ArrayList<>());
    }
    public CustomSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,List<ItemStack> displayInfo){
        super(itemGroup, item!=null?item:AddItem.RESOLVE_FAILED, recipeType, recipe);
        if (displayInfo != null) {
            this.originalMemory = new ArrayList<>(displayInfo);
        }else{
            this.originalMemory = new ArrayList<>();
        }
    }

    public void addInfo(ItemStack stack){

    }
    public List<MachineRecipe> provideDisplayRecipe(){
        return new ArrayList<>();
    }
    public final List<ItemStack> getDisplayRecipes() {
        if(displayedMemory==null||displayedMemory.isEmpty()) {
            displayedMemory=_getDisplayRecipes(originalMemory);
        }
        return displayedMemory;
    }
    public CustomSlimefunItem addHandler(ItemHandler handler) {
        this.addItemHandler(handler);
        return this;
    }
    public CustomSlimefunItem setDisplayRecipes(List<ItemStack> displayRecipes) {
        this.originalMemory = displayRecipes;
        return this;
    }
    public CustomSlimefunItem addDisplayRecipe(ItemStack stack) {
        if(originalMemory==null||originalMemory.isEmpty()) {
            originalMemory = new ArrayList<>();
        }
        this.originalMemory.add(stack);
        return this;
    }
    public CustomSlimefunItem setOutput(Object obj){
        this.recipeOutput= AddUtils.resolveItem(obj);
        return this;
    }
    public CustomSlimefunItem register(){
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
        this.addWikiPage("");
    }

}
