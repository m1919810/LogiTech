package me.matl114.logitech.Utils;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.BlockDataController;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.events.AndroidMineEvent;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.AndroidInstance;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Schedule.Schedules;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

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
    public static void removeSlimefunBlock(Location loc, boolean force){
        removeSlimefunBlock(loc,null,force);
    }
    public static void removeSlimefunBlock(Location loc,Player player, boolean force) {
       if(force){
           CONTROLLER.removeBlock(loc);
           return;
       }else {
           BlockBreakEvent breakEvent =
                   new BlockBreakEvent(loc.getBlock(),player);
           Bukkit.getPluginManager().callEvent(breakEvent);


           if (breakEvent.isCancelled()) {
               return ;
           }
           CONTROLLER.removeBlock(loc);
       }
    }
    public static void removeSlimefunBlockSafe(Location loc){
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
            BlockBreakEvent breakEvent =
                    new BlockBreakEvent(block,player);
            Bukkit.getPluginManager().callEvent(breakEvent);
            if (breakEvent.isCancelled()) {
                return false;
            }
            breakEvent.setDropItems(false);
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
        if( DataCache.getSfItem(loc)!=null){

            CONTROLLER.removeBlock(loc);
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
     */
    public static void createSlimefunBlockSync(Location loc,Player player,SlimefunItem item,Material material) {
        Block block=loc.getBlock();

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

    /**
     * no need to sync
     * @param start
     * @param end
     * @param type
     * @param count
     */
    public static void spawnLineParticle(Location start, Location end, Particle type, int count){
        if(count<=0)return;
        World world= start.getWorld();
        if(end.getWorld()!=world)return;
        if(count==1){
            world.spawnParticle(type,start,0);
        }else {
            Location walk=start.clone();
            double dx=(end.getX()-start.getX())/(count-1);
            double dy=(end.getY()-start.getY())/(count-1);
            double dz=(end.getZ()-start.getZ())/(count-1);
            for(int i=0;i<count;++i){
                world.spawnParticle(type,walk,0);
                walk.add(dx,dy,dz);
            }
        }

    }
    private static final ItemStack effectivePickaxe = new ItemStack(Material.DIAMOND_PICKAXE);

//    public ItemStack[] simulateKill(){
//        Player player;
//    }
    //should be without movement
    public static int executeOnSameEntity(Entity entity, Consumer<Entity> execution){
        if(entity==null)return 0;
        return entity.getLocation().getChunk().getWorld().getNearbyEntities(entity.getLocation(),0.5,0.5,0.5,(entity1 -> {
            if(entity1!=null&&entity.getUniqueId().equals( entity1.getUniqueId())){
                execution.accept(entity1);
                return true;
            }
            return false;
        })).size();
    }


}
