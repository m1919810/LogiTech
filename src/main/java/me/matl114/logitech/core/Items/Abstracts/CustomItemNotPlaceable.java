package me.matl114.logitech.core.Items.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.matl114.logitech.core.CustomSlimefunItem;
import me.matl114.logitech.core.Registries.AddHandlers;
import me.matl114.logitech.utils.WorldUtils;
import org.bukkit.inventory.ItemStack;

/**
 * abstracts of weapons and armors
 */
public class CustomItemNotPlaceable extends CustomSlimefunItem implements NotPlaceable {
    public CustomItemNotPlaceable(
            ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        super.preRegister();
        if (WorldUtils.isBlock(getItem().getType())) {
            addItemHandler(AddHandlers.stopPlacementHandler);
            addItemHandler(AddHandlers.stopPlaceerHandler);
        }
    }

    //    @Override
    //    public boolean canStack(@NotNull ItemMeta itemMeta, @NotNull ItemMeta itemMeta1) {
    //        return false;
    //    }
}
