package me.matl114.logitech.SlimefunItem.Items;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class CustomProps extends CustomSlimefunItem {
    public CustomProps(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(itemGroup, item, recipeType, recipe);
    }
    public CustomProps(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> items){
        super(itemGroup, item, recipeType, recipe);
        setDisplayRecipes(items);
    }
    /**
     * used for clickAction items
     */
    public abstract void onClickAction(PlayerRightClickEvent event);
    public void preRegister(){
        super.preRegister();
        addItemHandler((ItemUseHandler) this::onClickAction);
    }
}
