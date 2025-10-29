package me.matl114.logitech.core.Depends;

import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentQuantumStorageType;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.core.Registries.AddDepends;
import me.matl114.logitech.core.Cargo.Storages;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.StorageClass.StorageType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NetworksQuantumStorageAdapter extends StorageType {
    int ExceptionTimes=0;
    public NetworksQuantumStorageAdapter() {
        super();
    }
    @Override
    public boolean isStorage(ItemMeta meta) {
        return  (DataTypeMethods.hasCustom(meta, Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE));
    }
    @Override
    public boolean canStorage(ItemMeta meta) {
        //我们不帮网络存储设置类型 。
        //该方法只在设置类型的时候被调用
        return false;
//        if(AddDepends.NETWORKSQUANTUMSTORAGE==null){
//            return false;
//        }
//        String id=CraftUtils.parseSfId(meta);
//        if(id==null){
//            return false;
//        }
//        return AddDepends.NETWORKSQUANTUMSTORAGE.isInstance(SlimefunItem.getById(id));
    }

    @Override
    public boolean canStorage(SlimefunItem item) {
        //我们不帮网络存储设置类型 。
        return false;
//        if(AddDepends.NETWORKSQUANTUMSTORAGE==null){
//            return false;
//        }
//        return AddDepends.NETWORKSQUANTUMSTORAGE.isInstance(item);
    }
    public QuantumCache getQuantumCache(ItemMeta meta) {
        try{
           QuantumCache cache= DataTypeMethods.getCustom(meta, AddDepends.NTWQUANTUMKEY, PersistentQuantumStorageType.TYPE);
           return cache;
        }catch (Throwable e){
            disableNetworkQuantum(e);
            return null;
        }
    }
    public void disableNetworkQuantum(Throwable e){
        Debug.logger("AN ERROR OCCURED IN NETWORK_QUANTUM_STORAGE, STORAGE TYPE MAY BE DISABLED %d/%d".formatted(ExceptionTimes, 100));
        Debug.logger(e);
        ExceptionTimes++;
        if(ExceptionTimes>100){
            Storages.disableNetworkStorage();
            disableStorageType(this);
        }
    }
    public int getStorageMaxSize(QuantumCache cache){
        if(cache==null){return 0;}
        //Method amount=NetWorkQuantumMethod. getLimitMethod(cache);
        try{
            Number num=  NetWorkQuantumMethod.getLimitAccess.invoke(cache);// (Integer)amount.invoke(cache);
            return num.intValue();
        }catch (Throwable e){
            disableNetworkQuantum(e);
            return 0;
        }
    }
    @Override
    public int getStorageMaxSize(ItemMeta meta) {
        QuantumCache cache= getQuantumCache(meta);
        return getStorageMaxSize(cache);
    }

    @Override
    public void setStorage(ItemMeta meta, ItemStack content) {
        //我们不帮网络存储设置类型 。
//        DataTypeMethods.setCustom(meta, Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE, cache);
//        cache.addMetaLore(itemMeta);
//        itemToDrop.setItemMeta(itemMeta);
        throw new NotImplementedException("NetworkQuantumStorage's content shouldn't be set");
    }

    public void setAmount(QuantumCache cache, int amount) {
        if(cache==null){return;}
            //Method set=NetWorkQuantumMethod.getSetAmountMethod(cache);
        try{
            NetWorkQuantumMethod.getSetAmountAccess.invoke(cache,amount);
            //set.invoke(cache, amount);
        }catch (Throwable e){
            disableNetworkQuantum(e);
            return ;
        }
    }
    @Override
    public void onStorageAmountWrite(ItemMeta meta, int amount) {
        QuantumCache cache=getQuantumCache(meta);
        setAmount(cache, amount);
        DataTypeMethods.setCustom(meta,Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE,cache);
    }

    @Override
    public int getStorageAmount(ItemMeta meta) {
        QuantumCache cache= getQuantumCache(meta);
        return getStorageAmount(cache);
    }
    public int getStorageAmount(QuantumCache cache){
        if(cache==null){return 0;}
        //Method amount=  NetWorkQuantumMethod. getAmountMethod(cache);

        try{
            Number res= NetWorkQuantumMethod.getAmountAccess.invoke(cache);
            return res instanceof Long r? MathUtils.fromLong(r) : (Integer) res;
        }catch (Throwable e){
            disableNetworkQuantum(e);
            return 0;
        }
    }

    @Override
    public ItemStack getStorageContent(ItemMeta meta) {
        QuantumCache cache= getQuantumCache(meta);
        return getStorageContent(cache);
    }
    public ItemStack getStorageContent(QuantumCache cache){
        if(cache==null){
            return null;}
       // Method getItem=NetWorkQuantumMethod.getItemStackMethod(cache);
        try{
            return NetWorkQuantumMethod.getItemStackAccess.invoke(cache); //(ItemStack) getItem.invoke(cache);
        }catch (Throwable e){
            disableNetworkQuantum(e);
            return null;
        }
    }

    @Override
    public void clearStorage(ItemMeta meta) {
        throw new NotImplementedException("NetworkQuantumStorage's content shouldn't be cleared");
    }



    @Override
    public void onStorageDisplayWrite(ItemMeta meta, int amount) {
        var cache=getQuantumCache(meta);
        if(cache==null){return;}
        //Method set;
        try{
            NetWorkQuantumMethod.updateMetaLoreAccess.invoke(cache,meta);
//            set=NetWorkQuantumMethod.getUpdateMetaLore(cache);
//            set.invoke(cache, meta);
        }catch (Throwable e){
            disableNetworkQuantum(e);
        }
    }
}
