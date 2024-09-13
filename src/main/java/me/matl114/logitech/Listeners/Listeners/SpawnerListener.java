package me.matl114.logitech.Listeners.Listeners;

import me.matl114.logitech.SlimefunItem.Items.EntityFeat;
import org.bukkit.Location;
import org.bukkit.Material;
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
        if(event.getBlock().getType()== Material.SPAWNER){
            if(rand.nextInt(100)<=chance){
                Location loc= event.getBlock().getLocation();
                EntityType entityType=entityTypes[rand.nextInt(entityTypes.length)];
                if(entityType.isSpawnable())
                    loc.getWorld().dropItemNaturally(loc, EntityFeat.getItemFromEntityType(entityType));
            }
        }
    }
//    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
//    public
}
