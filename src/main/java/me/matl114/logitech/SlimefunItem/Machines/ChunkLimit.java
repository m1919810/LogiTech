package me.matl114.logitech.SlimefunItem.Machines;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.BukkitUtils;
import me.matl114.logitech.Utils.DataCache;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.function.Consumer;

public interface ChunkLimit {
    public HashMap<Chunk, Location> getRecords();
    default boolean onChunkPlace(Location location, SlimefunItem slimefunItem){
        Chunk chunk = location.getChunk();
        Location loc=getRecords().get(chunk);
        if(loc==null||loc.equals(location)) {
            getRecords().put(chunk, location);
            return true;
        }else {
            SlimefunItem item= DataCache.getSfItem(loc);
            if(item!=slimefunItem) {
                getRecords().put(chunk, location);
                return true;
            }else{
                return false;
            }
        }
    }
    default boolean checkChunkPlace(Location location,SlimefunItem slimefunItem){
        Chunk chunk = location.getChunk();
        Location loc=getRecords().get(chunk);
        if(loc==null||loc.equals(location)) {
            return true;
        }else {
            SlimefunItem item= DataCache.getSfItem(loc);
            if(item!=slimefunItem) {
                return true;
            }else{
                return false;
            }
        }
    }
    default boolean onChunkBreak(Location location, SlimefunItem slimefunItem){
        Chunk chunk = location.getChunk();
        Location loc=getRecords().remove(chunk);
        return true;
    }
    default void onChunkReachLimit(Location location, SlimefunItem slimefunItem, Consumer<String> outputStream){
        BukkitUtils.executeSync(()->{
            location.getWorld().strikeLightningEffect(location);
            outputStream.accept(AddUtils.resolveColor("&c不能在一个区块放置超过1个[%s]!".formatted(slimefunItem.getItem().getItemMeta().getDisplayName())));
            DataCache.removeBlockData(location);
        });
    }
}
