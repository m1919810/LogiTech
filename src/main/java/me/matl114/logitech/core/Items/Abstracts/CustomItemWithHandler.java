package me.matl114.logitech.core.Items.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class CustomItemWithHandler<T extends ItemHandler> extends CustomItemNotPlaceable {
    public CustomItemWithHandler(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(itemGroup, item, recipeType, recipe);
    }
    public CustomItemWithHandler(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayes){
        super(itemGroup, item, recipeType, recipe);
        setDisplayRecipes(displayes);
    }
    public abstract T[] getItemHandler();
    public void preRegister(){
        super.preRegister();
        addItemHandler(getItemHandler());
    }
}
