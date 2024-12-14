package me.matl114.logitech.Listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import me.matl114.logitech.SlimefunItem.Machines.SpecialMachines.TimerBlockEntity;
import me.matl114.logitech.Utils.WorldUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class TickBlockListener implements Listener {
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onPlace(BlockPlaceEvent event) {
        if(WorldUtils.isEntityBlock( event.getBlockPlaced().getType())){
            TimerBlockEntity.addLocation(event.getBlockPlaced().getLocation());
        }
    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onPlacerPlace(BlockPlacerPlaceEvent event){
        if(WorldUtils.isEntityBlock( event.getBlock().getType())){
            TimerBlockEntity.addLocation(event.getBlock().getLocation());
        }
    }
}
