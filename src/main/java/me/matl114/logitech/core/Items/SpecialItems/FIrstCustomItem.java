package me.matl114.logitech.core.Items.SpecialItems;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.matl114.logitech.core.AddItem;
import me.matl114.logitech.core.DistinctiveCustomSlimefunItem;
import org.bukkit.inventory.ItemStack;

public class FIrstCustomItem extends DistinctiveCustomSlimefunItem {
    public FIrstCustomItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void preRegister(){
        super.preRegister();
        addItemHandler((ItemUseHandler)this::onItemUseRightClick);
    }
    private void onItemUseRightClick(PlayerRightClickEvent event) {
        event.getPlayer().giveExpLevels(1);
        event.getPlayer().setItemInHand(AddItem.MATL114.clone());
    }

}
