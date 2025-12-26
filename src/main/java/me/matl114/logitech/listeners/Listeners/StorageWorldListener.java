package me.matl114.logitech.listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import me.matl114.logitech.core.Cargo.SpaceStorage.StorageSpace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class StorageWorldListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (StorageSpace.ENABLED
                && StorageSpace.STORAGE_WORLD == event.getBlock().getWorld()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (StorageSpace.ENABLED
                && StorageSpace.STORAGE_WORLD == event.getBlock().getWorld()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (StorageSpace.ENABLED) {
            event.blockList().removeIf((block) -> StorageSpace.STORAGE_WORLD == block.getWorld());
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (StorageSpace.ENABLED) {
            event.blockList().removeIf((block) -> StorageSpace.STORAGE_WORLD == block.getWorld());
        }
    }

    @EventHandler
    public void onSfBlocPlace(SlimefunBlockPlaceEvent event) {
        if (StorageSpace.ENABLED
                && StorageSpace.STORAGE_WORLD == event.getBlockPlaced().getWorld()) {
            event.setCancelled(true);
        }
    }
}
