package me.matl114.logitech.Listeners.Listeners;

import me.matl114.logitech.Listeners.BlockBreakListener;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.matlib.Utils.Inventory.InventoryRecords.InventoryRecord;
import me.matl114.matlib.Utils.WorldUtils;
import org.bukkit.Material;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class HyperLinkListener implements Listener {
    private static final Map<Inventory, InventoryRecord> openedInventory = new HashMap<>();
    public static void openHypInv(Player player, InventoryRecord record) {
        Inventory inv = record.inventory();
        if(inv==null || !record.canPlayerOpen(player)) return;
        player.openInventory(inv);
        AddUtils.sendMessage(player, "&a成功打开该容器!");
        //open before putting this,because open may follow a close Event
        openedInventory.put(inv, record);
        //launch monitor task
        Schedules.launchSchedules(new BukkitRunnable() {
            @Override
            public void run() {
                if(!record.stillValid() || inv.getViewers().isEmpty()){
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
    @EventHandler(ignoreCancelled = false,priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getWhoClicked().getOpenInventory().getTopInventory();
        if(onInventory(inv)){
            e.setCancelled(true);
        }
    }
    @EventHandler(ignoreCancelled = false,priority = EventPriority.NORMAL)
    public void onInventoryDrag(InventoryDragEvent e) {
        Inventory inv = e.getWhoClicked().getOpenInventory().getTopInventory();
        if(onInventory(inv)){
            e.setCancelled(true);
        }
    }
    public boolean onInventory(Inventory inv){
        InventoryRecord tileState = openedInventory.get(inv);
        if(tileState != null && !tileState.stillValid()){
            //execute close at next tick
            Schedules.launchSchedules(inv::close,0,true,0);
            return true;
        }
        return false;
    }
}
