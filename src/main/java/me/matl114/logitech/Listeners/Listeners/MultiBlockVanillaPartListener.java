package me.matl114.logitech.Listeners.Listeners;

import com.xzavier0722.mc.plugin.slimefun4.storage.callback.IAsyncReadCallback;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.events.ExplosiveToolBreakBlocksEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.WitherProof;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class MultiBlockVanillaPartListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH,ignoreCancelled = true)
    public void onMultiPartBreak(BlockBreakEvent event){
        Location loc = event.getBlock().getLocation();
        if(DataCache.getSfItem(loc)==null){
            MultiBlockService.handleVanillaBlockBreak(loc);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void onMultiPartBreakByExplosiveTools(ExplosiveToolBreakBlocksEvent event){
        for (Block block : event.getAdditionalBlocks()) {
            if(DataCache.getSfItem(block.getLocation())==null){
                MultiBlockService.handleVanillaBlockBreak(block.getLocation());
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e) {
        for(Block b:e.blockList()){
            if(DataCache.getSfItem(b.getLocation())==null){
                MultiBlockService.handleVanillaBlockBreak(b.getLocation());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent e) {
        for(Block b:e.blockList()){
            if(DataCache.getSfItem(b.getLocation())==null){
                MultiBlockService.handleVanillaBlockBreak(b.getLocation());
            }
        }
    }
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        if(!(e.getEntity() instanceof Player)&& MultiBlockService.safeGetStatus(e.getBlock().getLocation())!=0){
            e.setCancelled(true);
        }
    }
    @EventHandler( priority =EventPriority.LOW,ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent e) {
        if (MultiBlockService.safeGetStatus(e.getBlock().getLocation())!=0) {
            e.setCancelled(true);
        } else {
            for (Block b : e.getBlocks()) {
                if (MultiBlockService.safeGetStatus(e.getBlock().getLocation())!=0
                        || (b.getRelative(e.getDirection()).getType() == Material.AIR
                        && MultiBlockService.safeGetStatus(b.getRelative(e.getDirection()).getLocation())!=0)) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void onPistonRetract(BlockPistonRetractEvent e) {
        if (MultiBlockService.safeGetStatus(e.getBlock().getLocation())!=0) {
            e.setCancelled(true);
        } else if (e.isSticky()) {
            for (Block b : e.getBlocks()) {
                if (MultiBlockService.safeGetStatus(e.getBlock().getLocation())!=0
                        || (b.getRelative(e.getDirection()).getType() == Material.AIR
                        && MultiBlockService.safeGetStatus(b.getRelative(e.getDirection()).getLocation())!=0)) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }
        //MultiBlock will not meet this problem
//    @EventHandler(ignoreCancelled = true)
//    public void onLiquidFlow(BlockFromToEvent e) {
//        Block block = e.getToBlock();
//        Material type = block.getType();
//
//        // Check if this Material can be destroyed by fluids
//        if (SlimefunTag.FLUID_SENSITIVE_MATERIALS.isTagged(type)) {
//            // Check if this Block holds any data
//            if (StorageCacheUtils.hasBlock(block.getLocation())) {
//                e.setCancelled(true);
//            }
//            return;
//        }
//        BlockStateSnapshotResult state = PaperLib.getBlockState(block, false);
//        // Check the skull if it had lost its data, but name still remained.
//        if (state.getState() instanceof Skull) {
//            Skull skull = (Skull) state.getState();
//
//            if (skull.hasOwner() && Objects.equals(skull.getOwningPlayer().getName(), "CS-CoreLib")) {
//                e.setCancelled(true);
//            }
//        }
//    }

//    @EventHandler
//    public void onBucketUse(PlayerBucketEmptyEvent e) {
//        // Fix for placing water on player heads
//        Location l = e.getBlockClicked().getRelative(e.getBlockFace()).getLocation();
//
//        if (StorageCacheUtils.hasBlock(l)) {
//            e.setCancelled(true);
//        }
//    }


}
