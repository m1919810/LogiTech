package me.matl114.logitech.Listeners.Listeners;

import me.matl114.logitech.SlimefunItem.Interface.MenuBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
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
