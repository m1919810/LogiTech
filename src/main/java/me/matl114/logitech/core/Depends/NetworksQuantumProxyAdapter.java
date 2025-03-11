package me.matl114.logitech.core.Depends;

import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.core.Cargo.Storages;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.Debug;
import me.matl114.logitech.utils.UtilClass.StorageClass.LocationProxy;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class NetworksQuantumProxyAdapter extends NetworksQuantumStorageAdapter implements LocationProxy {
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
        Debug.logger("AN ERROR OCCURED IN NETWORK_QUANTUM_PROXY,STORAGE TYPE MAY BE DISABLED %d/%d".formatted(ExceptionTimes, 100));
        Debug.logger(e);
        ExceptionTimes++;
        if(ExceptionTimes>100){
            Storages.disableNetworkProxy();
            disableStorageType(this);
        }
    }
    public void updateLocation(Location loc){
        //Method method=NetWorkQuantumMethod.getSyncBlock(INSTANCE);
        try{
            QuantumCache cache=(QuantumCache)cacheMap.get(loc);
            if(cache!=null){
                NetWorkQuantumMethod.syncBlockAccess.invoke(INSTANCE,loc,cache);
               // method.invoke(null,loc,cache);
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
        //Method set=NetWorkQuantumMethod.getSetAmountMethod(cache);
        try{
            NetWorkQuantumMethod.getSetAmountAccess.invoke(cache,amount);
            //set.invoke(cache, amount);
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
