package me.matl114.logitech.Listeners.Listeners;

import me.matl114.logitech.Manager.Schedules;
import me.matl114.matlib.Utils.WorldUtils;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class HypInvOpenListener implements Listener {
    private static final Map<Inventory, TileState> openedInventory = new HashMap<Inventory, TileState>();
    public static void openHypInv(Player player, Inventory inv, TileState tileState) {
        player.openInventory(inv);
        //open before putting this,because open may follow a close Event
        openedInventory.put(inv, tileState);
        //launch monitor task
        Schedules.launchSchedules(new BukkitRunnable() {
            @Override
            public void run() {
                if(!WorldUtils.isTileEntityStillValid(tileState)){
                    // will trigger CloseEvent, openedInventory Map will be removed here
                    //inv close require run on Main Thread
                    inv.close();
                    this.cancel();
                }
            }
        },0,true,1);
    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent e) {
        openedInventory.remove(e.getInventory());
    }
    @EventHandler(ignoreCancelled = false,priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        TileState tileState = openedInventory.get(e.getInventory());
        if(tileState != null && !WorldUtils.isTileEntityStillValid(tileState)){
            e.setCancelled(true);
            //execute close at next tick
            Schedules.launchSchedules();
        }
    }
}
