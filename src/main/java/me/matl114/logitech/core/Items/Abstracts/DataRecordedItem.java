package me.matl114.logitech.core.Items.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import java.util.List;
import javax.annotation.Nonnull;
import me.matl114.logitech.core.DistinctiveCustomSlimefunItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

/**
 * item that contains important data in pdc, should always match pdc here
 */
public class DataRecordedItem extends DistinctiveCustomSlimefunItem {
    public DataRecordedItem(
            ItemGroup itemGroup,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            List<ItemStack> displayInfo) {
        super(itemGroup, item, recipeType, recipe, displayInfo);
    }

    public DataRecordedItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public boolean canStack(@Nonnull ItemMeta var1, @Nonnull ItemMeta var2) {

        PersistentDataContainer container1 = var1.getPersistentDataContainer();
        PersistentDataContainer container2 = var2.getPersistentDataContainer();
        return (container1.equals(container2));
    }
}
