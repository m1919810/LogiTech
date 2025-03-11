package me.matl114.logitech.utils.UtilClass.StorageClass;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Predicate;

public abstract class StorageType {
    protected StorageType(){
        register(this);
    }
    private static HashSet<StorageType> storageTypes = new HashSet<StorageType>();
    protected static void register(StorageType storage){
        storageTypes.add(storage);
    }
    protected static void unregister(StorageType storage){
        storageTypes.remove(storage);
    }
    /**
     * used when identifing now storage type
     * @param meta
     * @return
     */
    public static StorageType getStorageType(ItemMeta meta){
        return getStorageType(meta,(storageType -> true));
    }
    public static StorageType getStorageType(ItemMeta meta, Predicate<StorageType> filter){
        for(StorageType type : storageTypes){
            if(filter.test(type)&&type.isStorage(meta)){
                return type;
            }
        }return null;
    }

    /**
     * used when initializing empty storage item
     * ,find possible type
     * may be deprecated
     * @param meta
     * @return
     */
    public static StorageType getPossibleStorageType(ItemMeta meta){
        return getPossibleStorageType(meta,(storageType -> true));
    }
    public static StorageType getPossibleStorageType(ItemMeta meta, Predicate<StorageType> filter){
        if(meta==null)return null;
        for(StorageType type : storageTypes){
            if(filter.test(type)  &&type.canStorage(meta)){
                return type;
            }
        }return null;
    }

    /**
     * used when initializing empty storage item
     * ,find possible type based on SFItemTYPE
     * @param meta
     * @return
     */
    public static StorageType getPossibleStorageType(SlimefunItem meta){
        return getPossibleStorageType(meta,(storageType -> true));
    }
    public static StorageType getPossibleStorageType(SlimefunItem meta, Predicate<StorageType> filter){
        if(meta==null)return null;
        for(StorageType type : storageTypes){
            if(filter.test(type)&&  type.canStorage(meta)){
                return type;
            }
        }return null;
    }
    public static boolean disableStorageType(StorageType storageType){
        return storageTypes.remove(storageType);
    }
    public static Iterator<StorageType> getStorageTypes(){
        return storageTypes.iterator();
    }

    /**
     *asked when there are storage of sth
     * @param meta
     * @return
     */
    public abstract boolean isStorage(ItemMeta meta);

    /**
     * asked when no storage exists but may storage sth
     * @param meta
     * @return
     */
    public abstract boolean canStorage(ItemMeta meta);
    public abstract boolean canStorage(SlimefunItem item);
    public abstract void setStorage(ItemMeta meta, ItemStack content);
    public abstract ItemStack getStorageContent(ItemMeta meta);
    public abstract void clearStorage(ItemMeta meta);
    public abstract int getStorageAmount(ItemMeta meta);
    public abstract int getStorageMaxSize(ItemMeta meta);
    public abstract void onStorageAmountWrite(ItemMeta meta, int amount);
    public abstract void onStorageDisplayWrite(ItemMeta meta, int amount);
    public boolean isStorageProxy(){
        return false;
    }
}
