package me.matl114.logitech.SlimefunItem.Cargo.Singularity;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.SlimefunItem.Items.Singularity;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.PdcClass.AbstractStorageType;
import me.matl114.logitech.Utils.UtilClass.StorageClass.ItemStorageCache;
import me.matl114.logitech.Utils.UtilClass.StorageClass.StorageType;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SingularityProxy extends StorageType {
    public final static NamespacedKey KEY_LOC = AddUtils.getNameKey("cache_loc");
    public SingularityProxy() {
        super();
    }

    //TODO 完善该类
   // public final static
    protected static final int MAX_AMOUNT=2147483000;
    public boolean isStorage(ItemMeta meta) {
        //only when this loc is in cache map
        return getCache(meta)!=null;
    }
    public boolean canStorage(ItemMeta meta) {
        //该位置禁止
        return false;
    }
    public boolean canStorage(SlimefunItem sfitem){
        return sfitem instanceof Singularity;
    }
    public ItemStorageCache getCache(ItemMeta meta) {
        String locstr= meta.getPersistentDataContainer().get(KEY_LOC,PersistentDataType.STRING);
        if(locstr==null) return null;
        Location loc= DataCache.locationFromString(locstr);
        if(loc==null) return null;
        else return ItemStorageCache.getCache(loc);
    }
    public int getStorageMaxSize(ItemMeta meta) {
        return MAX_AMOUNT;
    }
    public void setLink(ItemMeta meta,Location loc) {
        //可以强行设置！但是只会在手动情况调用
        clearStorage(meta);
    }
    public void setStorage(ItemMeta meta, ItemStack item ) {
        //not allowed to set remote storage
        throw new NotImplementedException("NetworkQuantumStorage's content shouldn't be cleared in this method");
    }
    public void clearStorage(ItemMeta meta) {

    }
    public ItemStack getStorageContent(ItemMeta meta) {
        return  meta.getPersistentDataContainer().get(KEY_LOC,AbstractStorageType.TYPE);
    }
    public int getStorageAmount(ItemMeta meta) {
        try{
            Integer e= meta.getPersistentDataContainer().get(KEY_LOC,PersistentDataType.INTEGER);
            if(e==null) return 0;
            return e;
        }catch(Throwable e){
            return 0;
        }
    }
    public void onStorageAmountWrite(ItemMeta meta, int amount) {
        meta.getPersistentDataContainer().set(KEY_LOC, PersistentDataType.INTEGER,amount);
    }
    public void onStorageDisplayWrite(ItemMeta meta, int amount) {



    }

    public void updateStorageDisplay(ItemMeta meta,ItemStack item, int amount){

    }
}
