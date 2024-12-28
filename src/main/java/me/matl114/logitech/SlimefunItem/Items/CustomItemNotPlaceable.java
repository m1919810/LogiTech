package me.matl114.logitech.SlimefunItem.Items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.AddHandlers;
import me.matl114.logitech.SlimefunItem.DistinctiveCustomSlimefunItem;
import me.matl114.logitech.Utils.WorldUtils;
import org.bukkit.inventory.ItemStack;

public class CustomItemNotPlaceable extends DistinctiveCustomSlimefunItem {
    public CustomItemNotPlaceable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    @Override
    public  void preRegister(){
        super.preRegister();
        if(WorldUtils.isBlock(getItem().getType())){
            addItemHandler(AddHandlers.stopPlacementHandler);
            addItemHandler(AddHandlers.stopPlaceerHandler);
        }

    }

}
