package me.matl114.logitech.Listeners;

import me.matl114.logitech.Utils.Debug;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak1(BlockBreakEvent e) {
        Debug.logger("lowest called");
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak2(BlockBreakEvent e) {
        Debug.logger("low called");
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak3(BlockBreakEvent e) {
        Debug.logger("normal called");
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak4(BlockBreakEvent e) {
        Debug.logger("high called");
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak5(BlockBreakEvent e) {
        Debug.logger("highest called");
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak6(BlockBreakEvent e) {
        Debug.logger("monitor called");
    }
}
