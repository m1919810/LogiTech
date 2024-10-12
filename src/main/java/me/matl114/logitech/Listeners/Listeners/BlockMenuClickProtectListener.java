package me.matl114.logitech.Listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.SlimefunItem.Cargo.StorageMachines.AbstractIOPort;
import me.matl114.logitech.SlimefunItem.Machines.MenuBlock;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class BlockMenuClickProtectListener implements Listener {
    @EventHandler
    public void onBlockMenuClick(InventoryClickEvent e) {
        if(e.getClick()== ClickType.DOUBLE_CLICK){
            InventoryHolder inv=e.getWhoClicked().getOpenInventory().getTopInventory().getHolder();
            if(inv instanceof BlockMenu blockMenu){
                if(blockMenu.getPreset() instanceof MenuBlock.AdvancedBlockMenuPreset abm){
                    e.setCancelled(true);
                }
            }
        }
    }
}
