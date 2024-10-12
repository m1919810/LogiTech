package me.matl114.logitech.Listeners.Listeners;

import me.matl114.logitech.SlimefunItem.Cargo.SpaceStorage.StorageSpace;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;
import java.util.List;

public class StorageWorldListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(StorageSpace.ENABLED&& StorageSpace.STORAGE_WORLD==event.getBlock().getWorld()){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(StorageSpace.ENABLED&& StorageSpace.STORAGE_WORLD==event.getBlock().getWorld()){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if(StorageSpace.ENABLED){
            event.blockList().removeIf((block)->StorageSpace.STORAGE_WORLD==block.getWorld());
        }
    }
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if(StorageSpace.ENABLED){
            event.blockList().removeIf((block)->StorageSpace.STORAGE_WORLD==block.getWorld());
        }
    }

}
