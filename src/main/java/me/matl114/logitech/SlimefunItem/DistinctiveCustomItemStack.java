package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.DistinctiveItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class DistinctiveCustomItemStack extends CustomSlimefunItem implements DistinctiveItem {
    public DistinctiveCustomItemStack(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayInfo) {
        super(itemGroup, item, recipeType, recipe, displayInfo);
    }

    public DistinctiveCustomItemStack(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public boolean canStack(@Nonnull ItemMeta var1, @Nonnull ItemMeta var2){

        PersistentDataContainer container1 = var1.getPersistentDataContainer();
        PersistentDataContainer container2 = var2.getPersistentDataContainer();
        return (container1==null)||(container1.equals(container2));

    }
}
