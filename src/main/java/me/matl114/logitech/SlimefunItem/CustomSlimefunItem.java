package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.DistinctiveItem;
import me.matl114.logitech.SlimefunItem.Machines.RecipeDisplay;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class CustomSlimefunItem extends SlimefunItem implements RecipeDisplay , DistinctiveItem {
    public List<ItemStack> displayedMemory;
    public List<ItemStack> originalMemory;
    public CustomSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        this(itemGroup, item, recipeType, recipe,new ArrayList<>());
    }
    public CustomSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,List<ItemStack> displayInfo){
        super(itemGroup, item, recipeType, recipe);
        if (displayInfo != null) {
            this.originalMemory = displayInfo;
        }else{
            this.originalMemory = new ArrayList<>();
        }
    }
    public boolean canStack(@Nonnull ItemMeta var1, @Nonnull ItemMeta var2){
        PersistentDataContainer container1 = var1.getPersistentDataContainer();
        PersistentDataContainer container2 = var2.getPersistentDataContainer();
        return (container1==null)||(container1.equals(container2));
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
        this.addWikiPage("Overview");
    }

}
