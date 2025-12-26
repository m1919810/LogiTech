package me.matl114.logitech.core.Items.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import java.util.List;
import me.matl114.logitech.core.Registries.AddHandlers;
import me.matl114.logitech.utils.WorldUtils;
import org.bukkit.inventory.ItemStack;

public abstract class CustomItemWithHandler<T extends ItemHandler> extends FunctionalItem {
    public CustomItemWithHandler(
            ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public CustomItemWithHandler(
            ItemGroup itemGroup,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            List<ItemStack> displayes) {
        super(itemGroup, item, recipeType, recipe);
        setDisplayRecipes(displayes);
    }

    public abstract T[] getItemHandler();

    public void preRegister() {
        super.preRegister();
        if (WorldUtils.isBlock(getItem().getType())) {
            addItemHandler(AddHandlers.stopPlacementHandler);
            addItemHandler(AddHandlers.stopPlaceerHandler);
        }
        addItemHandler(getItemHandler());
    }
}
