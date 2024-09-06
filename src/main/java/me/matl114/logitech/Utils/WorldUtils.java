package me.matl114.logitech.Utils;

import com.xzavier0722.mc.plugin.slimefun4.storage.callback.IAsyncReadCallback;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.BlockDataController;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.StorageType;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Schedule.Schedules;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Set;

public class WorldUtils {
    public static SlimefunAddon INSTANCE= MyAddon.getInstance();
    public static final BlockDataController CONTROLLER=Slimefun.getDatabaseManager().getBlockDataController();
    public static void setBlock(Location loc, Material material) {
        loc.getBlock().setType(material);

    }
    public static void setAir(Location loc) {
        loc.getBlock().setType(Material.AIR);
    }
    public static void setBlock(Block block, Material material) {

    }
    /**
     * this method no need to run sync
     * @param loc
     * @param force
     */
    public static void removeSlimefunBlock(Location loc, boolean force) {
       if(force){
           CONTROLLER.removeBlock(loc);
           return;
       }else {
           CONTROLLER.getBlockDataAsync(loc, new IAsyncReadCallback<SlimefunBlockData>() {
               public boolean runOnMainThread() {
                   return false;
               }
               public void onResult(SlimefunBlockData blockData) {
                   //非强制移除,掉落物品
                   blockData.setPendingRemove(true);
                   if(!force){
                       if (blockData != null && blockData.isPendingRemove()) {
                           return;
                       }
                       SlimefunItem sfItem=SlimefunItem.getById(blockData.getSfId());
                       Schedules.launchSchedules(Schedules.getRunnable(()->{
                           for (ItemStack item : sfItem.getDrops()) {
                               if (item != null && !item.getType().isAir()) {
                                   loc.getWorld().dropItemNaturally(loc, item);
                               }
                           }

                       }),1,true,0);
                       CONTROLLER.removeBlock(loc);
                       return;
                   }
                   CONTROLLER.removeBlock(loc);
               }
             }
           );
           return;
       }
    }

    public static void moveSlimefunBlock(Location loc, boolean force) {

    }

    /**
     * put arg hasSync to decide whether to create new sunc thread
     * @param loc
     * @param player
     * @param item
     * @param material
     * @param force
     * @param hasSync
     */
    public static boolean createSlimefunBlock(Location loc,Player player,SlimefunItem item,Material material,boolean force,boolean hasSync){
        Block block = loc.getBlock();
        if(!force&&player!=null){
            if (!item.canUse(player, false)) {
                return false;
            }
            var placeEvent = new SlimefunBlockPlaceEvent(player, item.getItem(), block, item);
            Bukkit.getPluginManager().callEvent(placeEvent);

            if (placeEvent.isCancelled()) {
                return false ;
            }else if(!hasPermission(player,loc, Interaction.PLACE_BLOCK)){
                return false ;
            }
        }
        if(hasSync){
            createSlimefunBlockSync(loc,player,item,material);
        }else {
            Schedules.launchSchedules(
                    ()->    createSlimefunBlockSync(loc, player, item, material),0,true,0
            );
        }
        return true;
    }
    /**
     * this method must run sync
     * @param loc
     * @param item
     * @param material
     * @param force
     */
    public static void createSlimefunBlockSync(Location loc,Player player,SlimefunItem item,Material material) {
        Block block=loc.getBlock();
        if(StorageCacheUtils.getSfItem(loc)!=null){
            CONTROLLER.removeBlock(loc);
        }
        block.setType(material);
        if (Slimefun.getBlockDataService().isTileEntity(block.getType())) {
            Slimefun.getBlockDataService().setBlockData(block, item.getId());
        }
        CONTROLLER.createBlock(loc, item.getId());
    }
    public static boolean hasPermission( Player player, @Nonnull Block location, @Nonnull Interaction... interactions) {
        if(player==null)return true;
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(player, location, interaction)) {
                return false;
            }
        }
        return true;
    }
    public static boolean hasPermission( Player player, @Nonnull Location location, @Nonnull Interaction... interactions) {
        if(player==null)return true;
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(player, location, interaction)) {
                return false;
            }
        }
        return true;
    }





}
