package me.matl114.logitech.Depends;

import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.SlimefunItem.AddDepends;
import me.matl114.logitech.SlimefunItem.AddSlimefunItems;
import me.matl114.logitech.SlimefunItem.Cargo.Storages;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.StorageClass.LocationProxy;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Method;
import java.util.Map;

public class NetworksQuantumProxy extends NetworksAdaptQuantumStorage implements LocationProxy {
    public final static NamespacedKey KEY_LOC = AddUtils.getNameKey("cache_loc");
    public final static SlimefunItem INSTANCE=SlimefunItem.getById("NTW_QUANTUM_STORAGE_1");
    public final Map cacheMap ;
    {
        if(INSTANCE==null){
            disableNetworkQuantum(new Exception("SlimefunItem INSTANCE NTW_QUANTUN_STORAGE_1 not found!"));
            throw new  NotImplementedException("NetworksQuantumStorage Instance not found");
        }
        cacheMap=NetWorkQuantumMethod.getCacheMap(INSTANCE);
    }
    public boolean isStorage(ItemMeta meta) {
        //check lock
        return getQuantumCache(meta)!=null;
    }
    public QuantumCache getQuantumCache(ItemMeta meta) {
        try{
            Location loc=getLocation(meta);
            if(loc==null) return null;
            else {
                Object obj=cacheMap.get(loc);
                if(obj==null)return null;
                return (QuantumCache)cacheMap.get(loc);
            }
        }catch (Throwable e){
            disableNetworkQuantum(e);
            return null;
        }
    }
    public ItemStack getItemStack(Location loc) {
        return getStorageContent ((QuantumCache)cacheMap.get(loc) );
    }
    public int getAmount(Location loc) {
        return getStorageAmount((QuantumCache)cacheMap.get(loc) );
    }
    public Location getLocation(ItemMeta meta){
        String locstr= meta.getPersistentDataContainer().get(KEY_LOC, PersistentDataType.STRING);
        if(locstr==null) return null;
        return DataCache.locationFromString(locstr);
    }
    public void setAmount(Location loc,int amount){
        setAmount((QuantumCache)cacheMap.get(loc),amount);
    }
    public  void disableNetworkQuantum(Throwable e){
        Debug.logger("AN ERROR OCCURED IN NETWORK_QUANTUM_PROXY, STORAGE TYPE DISABLED");
        Debug.logger(e);
        ExceptionTimes++;
        if(ExceptionTimes>60){
            Storages.disableNetworkProxy();
            disableStorageType(this);
        }
    }
    public void updateLocation(Location loc){
        Method method=NetWorkQuantumMethod.getSyncBlock(INSTANCE);
        try{
            QuantumCache cache=(QuantumCache)cacheMap.get(loc);
            if(cache!=null){
                method.invoke(null,loc,cache);
            }
        }catch(Throwable e){
            disableNetworkQuantum(e);
            return;
        }
    }
    public int getMaxAmount(Location loc){
        return getStorageMaxSize((QuantumCache)cacheMap.get(loc) );
    }
    public void onStorageAmountWrite(ItemMeta meta, int amount) {
        QuantumCache cache=getQuantumCache(meta);
        if(cache==null){return;}
        Method set=NetWorkQuantumMethod.getSetAmountMethod(cache);
        try{
            set.invoke(cache, amount);
        }catch (Throwable e){
            disableNetworkQuantum(e);
        }
        //DataTypeMethods.setCustom(meta, Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE,cache);
    }

    public void updateStorageDisplay(ItemMeta meta, ItemStack item, int amount) {


    }
    public void onStorageDisplayWrite(ItemMeta meta, int amount) {
        Location loc=getLocation(meta);
        if(loc==null) return ;
        updateLocation(loc);
    }
    public boolean isStorageProxy(){
        return  true;
    }
}
