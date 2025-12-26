package me.matl114.logitech.listeners.Listeners;

import me.matl114.logitech.core.Interface.MenuBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;

public class BlockMenuClickProtectListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onBlockMenuClick(InventoryClickEvent e) {
        InventoryHolder inv =
                e.getWhoClicked().getOpenInventory().getTopInventory().getHolder();
        if (inv instanceof BlockMenu blockMenu) {
            if (blockMenu.getPreset() instanceof MenuBlock.AdvancedBlockMenuPreset abm) {
                abm.handleOriginClick(blockMenu, e);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockMenuDragClick(InventoryDragEvent e) {
        InventoryHolder inv =
                e.getWhoClicked().getOpenInventory().getTopInventory().getHolder();
        if (inv instanceof BlockMenu blockMenu) {
            if (blockMenu.getPreset() instanceof MenuBlock.AdvancedBlockMenuPreset abm) {
                abm.handleDragEvent(blockMenu, e);
            }
        }
    }
}
