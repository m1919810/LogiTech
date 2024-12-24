package me.matl114.logitech.SlimefunItem.Items.Logisuit;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.DistinctiveCustomSlimefunItem;
import me.matl114.logitech.SlimefunItem.Items.CustomArmorPiece;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LogisuitEquipment extends CustomArmorPiece {
    public LogisuitEquipment(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayInfo) {
        super(itemGroup, item, recipeType, recipe);
    }
}
