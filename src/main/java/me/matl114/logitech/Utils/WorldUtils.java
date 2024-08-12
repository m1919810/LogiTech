package me.matl114.logitech.Utils;

import com.xzavier0722.mc.plugin.slimefun4.storage.callback.IAsyncReadCallback;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Schedule.Schedules;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class WorldUtils {
    public static SlimefunAddon INSTANCE= MyAddon.getInstance();
    public static void setBlock(Location loc, Material material) {
        loc.getBlock().setType(material);

    }
    public static void setAir(Location loc) {
        loc.getBlock().setType(Material.AIR);
    }
    public static void setBlock(Block block, Material material) {

    }
    public static void removeSlimefunBlock(Location loc, boolean force) {
        var controller=Slimefun.getDatabaseManager().getBlockDataController();

       controller.getBlockDataAsync(loc, new IAsyncReadCallback<SlimefunBlockData>() {
           public boolean runOnMainThread() {
               return false;
           }
           public void onResult(SlimefunBlockData blockData) {
               //非强制移除,掉落物品
               blockData.setPendingRemove(true);
               if(!force){
                   StorageCacheUtils.executeAfterLoad(blockData,()->{
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
                       controller.removeBlock(loc);
                   },false);
                   return;
               }
               controller.removeBlock(loc);
           }

       });
    }



}
