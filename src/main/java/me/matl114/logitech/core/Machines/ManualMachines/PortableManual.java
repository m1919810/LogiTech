package me.matl114.logitech.core.Machines.ManualMachines;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.AddSlimefunItems;
import me.matl114.logitech.core.Items.Abstracts.CustomProps;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PortableManual extends CustomProps {
    public PortableManual(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onClickAction(PlayerRightClickEvent event) {
        event.cancel();
        Player player = event.getPlayer();
        if (AddSlimefunItems.ADV_MANUAL instanceof AdvancedManual adv) {
            adv.openManualGui(player);
        }
    }
}
