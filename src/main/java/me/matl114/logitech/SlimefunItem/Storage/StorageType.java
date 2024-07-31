package me.matl114.logitech.SlimefunItem.Storage;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;

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
        for(StorageType type : storageTypes){
            if(type.isStorage(meta)){
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
        if(meta==null)return null;
        for(StorageType type : storageTypes){
            if(type.canStorage(meta)){
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
        if(meta==null)return null;
        for(StorageType type : storageTypes){
            if(type.canStorage(meta)){
                return type;
            }
        }return null;
    }
    public abstract boolean isStorage(ItemMeta meta);
    public abstract boolean canStorage(ItemMeta meta);
    public abstract boolean canStorage(SlimefunItem item);
    public abstract void setStorage(ItemMeta meta, ItemStack content);
    public abstract ItemStack getStorageContent(ItemMeta meta);
    public abstract void clearStorage(ItemMeta meta);
    public abstract int getStorageAmount(ItemMeta meta);
    public abstract void setStorageAmount(ItemMeta meta, int amount);
    public abstract int getStorageMaxSize(ItemMeta meta);
    public abstract void updateStorageAmountDisplay(ItemMeta meta, int amount);
    public abstract void updateStorageDisplay(ItemMeta meta,ItemStack item, int amount);
}
