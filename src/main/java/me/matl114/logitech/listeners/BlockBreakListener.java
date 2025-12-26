package me.matl114.logitech.listeners;

import me.matl114.logitech.utils.Debug;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BlockBreakListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreak1(BlockBreakEvent e) {
        e.setCancelled(true);
        Debug.logger("lowest called");
        Debug.logger(e.getPlayer().getEquipment().getItemInMainHand().getType().toString());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak2(BlockBreakEvent e) {
        if (e.isCancelled()) {
            Debug.logger("this event is cancelled");
        }
        Debug.logger("low called");
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockBreak3(BlockBreakEvent e) {
        Debug.logger("normal called");
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak4(BlockBreakEvent e) {
        Debug.logger("high called");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak5(BlockBreakEvent e) {
        Debug.logger("highest called");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak6(BlockBreakEvent e) {
        Debug.logger("monitor called");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.getEntity().setHealth(e.getEntity().getMaxHealth());
    }

    @EventHandler
    public void onPlayerPortal(EntityPortalEnterEvent e) {
        if (e.getEntity() instanceof Player) {
            Debug.logger("loc at ", e.getLocation());
        }
    }

    @EventHandler
    public void onPlayerDeath2(PlayerDeathEvent e) {
        e.getEntity().getLastDamageCause().getDamageSource();
    }
}
