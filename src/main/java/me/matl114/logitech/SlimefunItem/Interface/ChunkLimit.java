package me.matl114.logitech.SlimefunItem.Interface;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.BukkitUtils;
import me.matl114.logitech.Utils.DataCache;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.function.Consumer;

public interface ChunkLimit {
    public HashMap<Chunk, Location> getRecords();
    default <T extends SlimefunItem> boolean onChunkPlace(Location location,Class<T> instance){
        Chunk chunk = location.getChunk();
        Location loc=getRecords().get(chunk);
        if(loc==null||loc.equals(location)) {
            getRecords().put(chunk, location);
            return true;
        }else {
            SlimefunItem item=DataCache.getSfItem(loc);
            if(instance.isInstance(item)){
                return false;
            }else{
                getRecords().put(chunk, location);
                return true;
            }
        }
    }
    default <T extends SlimefunItem> boolean checkChunkPlace(Location location,Class<T> instance){
        Chunk chunk = location.getChunk();
        Location loc=getRecords().get(chunk);
        if(loc==null||loc.equals(location)) {
            return true;
        }else {
            SlimefunItem item= DataCache.getSfItem(loc);
            if(instance.isInstance(item)){
                return false;
            }else{
                return true;
            }
//            if(item!=slimefunItem) {
//                return true;
//            }else{
//                return false;
//            }
        }
    }
    default boolean onChunkBreak(Location location){
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
