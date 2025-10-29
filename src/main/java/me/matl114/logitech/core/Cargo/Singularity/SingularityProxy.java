package me.matl114.logitech.core.Cargo.Singularity;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.StorageClass.ItemStorageCache;
import me.matl114.logitech.utils.UtilClass.StorageClass.LocationProxy;
import me.matl114.logitech.utils.UtilClass.StorageClass.StorageType;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SingularityProxy extends StorageType implements LocationProxy {
    public final static NamespacedKey KEY_LOC = AddUtils.getNameKey("cache_loc");
    public SingularityProxy() {
        super();
    }

   // public final static
    protected static final int MAX_AMOUNT=2147483647;
    public ItemStorageCache getCache(ItemMeta meta) {

        Location loc= getLocation(meta);
        if(loc==null) return null;
        else return ItemStorageCache.getCache(loc);
    }
    public ItemStack getItemStack(Location loc) {
        ItemStorageCache cache=ItemStorageCache.getCache(loc);
        if(cache==null) {
            return null;
        }else return cache.getItem();
    }
    public int getAmount(Location loc) {
        ItemStorageCache cache=ItemStorageCache.getCache(loc);
        if(cache==null) {
            return 0;
        }else return cache.getStorageAmount();
    }
    public void setAmount(Location loc,int amount){
        ItemStorageCache cache=ItemStorageCache.getCache(loc);
        if(cache!=null){
            cache.setAmount(amount);
            cache.updateItemStack();
        }else{
            throw new RuntimeException("AN ERROR OCCURS WHILE ATTEMPTING TO SET AMOUNT OF NULL CACHE,MAYBE SOME PLAYER IS TRYING TO DUPE !");
        }
    }
    public int getMaxAmount(Location loc) {
        return MAX_AMOUNT;
    }
    public Location getLocation(ItemMeta meta) {
        String locstr= meta.getPersistentDataContainer().get(KEY_LOC, PersistentDataType.STRING);
        if(locstr==null) return null;
        return DataCache.locationFromString(locstr);
    }
    public void updateLocation(Location loc) {
        ItemStorageCache cache=ItemStorageCache.getCache(loc);
        if(cache!=null){
            //do we actually need this?
            cache.syncLocation(loc);
        }
    }
    public boolean isStorage(ItemMeta meta) {
        //only when this loc is in cache map
        ItemStorageCache cache=getCache(meta);
        return cache!=null&&!cache.getDeprecated();
        //废弃后不会删除 但是无法读取 就只能卡在这
        //防止有人移走存储目标然后再使用它读取卡bug
       // return getCache(meta)!=null;
    }
    public boolean canStorage(ItemMeta meta) {
        //该位置禁止
        return false;
    }
    public boolean canStorage(SlimefunItem sfitem){
        return false;
    }

    public int getStorageMaxSize(ItemMeta meta) {
        return MAX_AMOUNT;
    }
    public void setStorage(ItemMeta meta, ItemStack item ) {
        //not allowed to set remote storage
        throw new NotImplementedException("SingularityProxy's content shouldn't be set");
    }
    public void clearStorage(ItemMeta meta) {
        throw new NotImplementedException("SingularityProxy's content shouldn't be cleared");
    }
    public ItemStack getStorageContent(ItemMeta meta) {
        Location loc= getLocation(meta);
        if(loc==null) return null;
        else return getItemStack(loc);
    }
    public int getStorageAmount(ItemMeta meta) {
        Location loc= getLocation(meta);
        if(loc==null) return 0;
        else return getAmount(loc);
    }
    public void onStorageAmountWrite(ItemMeta meta, int amount) {
       Location loc= getLocation(meta);
       if(loc!=null)setAmount(loc,amount);
    }
    public void onStorageDisplayWrite(ItemMeta meta, int amount) {
        Location loc= getLocation(meta);
        if(loc!=null)updateLocation(loc);
    }
    public boolean isStorageProxy(){
        return true;
    }
}
