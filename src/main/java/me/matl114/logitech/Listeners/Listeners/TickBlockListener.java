package me.matl114.logitech.Listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import me.matl114.logitech.SlimefunItem.Machines.SpecialMachines.TimerBlockEntity;
import me.matl114.logitech.SlimefunItem.Machines.SpecialMachines.TimerRandomtick;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.WorldUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.world.StructureGrowEvent;

import javax.xml.crypto.Data;
import java.util.List;

public class TickBlockListener implements Listener {
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onPlace(BlockPlaceEvent event) {
        tryAddBlockEntity(event.getBlock());
        tryAddRandomTick(event.getBlock());

    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onPlacerPlace(BlockPlacerPlaceEvent event){
        tryAddBlockEntity(event.getBlock());
        tryAddRandomTick(event.getBlock());
    }
    public void tryAddBlockEntity(Block block){
        if(TimerBlockEntity.isEnable()&& TimerBlockEntity.getInstance().blockPredicate(block)){
            TimerBlockEntity.getInstance().addLocation(block.getLocation());
        }
    }
    public void tryAddRandomTick(Block block){
        if(TimerRandomtick.isEnable()&& TimerRandomtick.getInstance().blockPredicate(block)){
            TimerRandomtick.getInstance().addLocation(block.getLocation());
        }
    }
    public void tryAddRandomTick(BlockState block){
        //Debug.logger("new state at %s, new state type".formatted(DataCache.locationToDisplayString(block.getLocation())),block.getType());
        if(TimerRandomtick.isEnable()&& TimerRandomtick.getInstance().typePredicate(block.getType())){
            TimerRandomtick.getInstance().addLocation(block.getLocation());
        }
    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onPlantGrow(StructureGrowEvent event){
        List<BlockState> blocks=event.getBlocks();
        blocks.forEach(this::tryAddRandomTick);
    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onPlantGrow(BlockGrowEvent event){
        tryAddRandomTick(event.getNewState());
    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onPlantGrow(BlockSpreadEvent event){
        tryAddRandomTick(event.getNewState());
    }
}
