package me.matl114.logitech.Listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.implementation.items.tools.PickaxeOfContainment;
import me.matl114.logitech.core.Blocks.AbstractSpawner;
import me.matl114.logitech.core.Items.SpecialItems.EntityFeat;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.DataCache;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class SpawnerListener implements Listener {
    private Random rand=new Random();
    private final EntityType[] entityTypes=EntityType.values();
    protected int chance=50;
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void onSpawnerBreak(BlockBreakEvent event) {
        //drop entity feat
        Block b=event.getBlock();
        if(event.isDropItems()&& b.getType()== Material.SPAWNER){
            if(rand.nextInt(100)<=chance){
                Location loc= event.getBlock().getLocation();
                EntityType entityType=entityTypes[rand.nextInt(entityTypes.length)];
                if(entityType.isSpawnable())
                    loc.getWorld().dropItemNaturally(loc, EntityFeat.getItemFromEntityType(entityType));
            }
        }
    }
    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = false)
    public void onStopSpawnerPickaxeBreak(BlockBreakEvent event){
        Block b=event.getBlock();
        if(b.getType()== Material.SPAWNER){
            if(DataCache.getSfItem(b.getLocation()) instanceof AbstractSpawner as){
                if(CraftUtils.parseSfItem( event.getPlayer().getInventory().getItemInMainHand()) instanceof PickaxeOfContainment){
                    AddUtils.sendMessage(event.getPlayer(), "&c你不能使用刷怪笼之镐挖掘本附属的刷怪笼!");
                    event.setCancelled(true);
                }
            }
        }
    }
}
