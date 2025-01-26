package me.matl114.logitech.core.Items.Equipments;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import me.matl114.logitech.Utils.UtilClass.EquipClass.EquipmentFU;
import me.matl114.logitech.core.Items.Abstracts.MaterialItem;
import org.bukkit.inventory.ItemStack;

@Getter
public class EquipmentFUItem extends MaterialItem {
    private final EquipmentFU fUnit;
    private final int energyConsumption;
    public EquipmentFUItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, EquipmentFU fUnit, int energyConsumption) {
        super(itemGroup, item, recipeType, recipe);
        this.fUnit = fUnit;
        this.energyConsumption = energyConsumption;
    }
}
