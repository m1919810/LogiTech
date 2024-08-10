package me.matl114.logitech.Utils;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class WorldUtils {
    public static void setBlock(Location loc, Material material) {
        loc.getBlock().setType(material);

    }
    public static void setAir(Location loc) {
        loc.getBlock().setType(Material.AIR);
    }
    public static void setBlock(Block block, Material material) {

    }
    public static void removeSlimefunBlock(Location loc, boolean force) {
        SlimefunItem sfItem = StorageCacheUtils.getSfItem(loc);
        if (sfItem != null) {
            if(!force){
                var blockData = StorageCacheUtils.getBlock(loc);
                if (blockData != null && blockData.isPendingRemove()) {
                    return;
                }
                for (ItemStack item : sfItem.getDrops()) {
                    if (item != null && !item.getType().isAir()) {
                        loc.getWorld().dropItemNaturally(loc, item);
                    }
                }
            }
            Slimefun.getDatabaseManager().getBlockDataController().removeBlock(loc);
        }
    }


}
