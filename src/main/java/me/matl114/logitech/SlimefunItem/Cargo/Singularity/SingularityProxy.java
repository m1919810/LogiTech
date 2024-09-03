package me.matl114.logitech.SlimefunItem.Cargo.Singularity;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.SlimefunItem.Items.Singularity;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.PdcClass.AbstractStorageType;
import me.matl114.logitech.Utils.UtilClass.StorageClass.ItemStorageCache;
import me.matl114.logitech.Utils.UtilClass.StorageClass.LocationProxy;
import me.matl114.logitech.Utils.UtilClass.StorageClass.StorageType;
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
    protected static final int MAX_AMOUNT=2147483000;
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
            cache.updateMenu(cache.getBlockMenu());
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
            cache.setSave(true);
            cache.updateMenu(cache.getBlockMenu());
        }
    }
    public boolean isStorage(ItemMeta meta) {
        //only when this loc is in cache map
        return getCache(meta)!=null;
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
        throw new NotImplementedException("NetworkQuantumStorage's content shouldn't be cleared in this method");
    }
    public void clearStorage(ItemMeta meta) {
        throw new NotImplementedException("NetworkQuantumStorage's content shouldn't be cleared in this method");
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
