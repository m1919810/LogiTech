package me.matl114.logitech.SlimefunItem.Items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import org.bukkit.inventory.ItemStack;

public abstract class ChargableProps extends CustomProps implements Rechargeable {
    public ChargableProps(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(itemGroup, item, recipeType, recipe);
    }
    public void addInfo(ItemStack stack){
        super.addInfo(stack);
        setItemCharge(stack,0.0f);
    }
    public abstract float getMaxItemCharge(ItemStack var1);
}
