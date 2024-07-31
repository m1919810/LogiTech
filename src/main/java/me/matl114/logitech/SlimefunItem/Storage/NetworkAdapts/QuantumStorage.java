package me.matl114.logitech.SlimefunItem.Storage.NetworkAdapts;

import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumStorage;
import io.github.sefiraat.networks.utils.Keys;
import io.github.sefiraat.networks.utils.datatypes.DataTypeMethods;
import io.github.sefiraat.networks.utils.datatypes.PersistentQuantumStorageType;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.SlimefunItem.Storage.StorageType;
import me.matl114.logitech.Utils.CraftUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuantumStorage extends StorageType {
    public QuantumStorage() {
        super();
    }

    @Override
    public boolean isStorage(ItemMeta meta) {
        return  (DataTypeMethods.hasCustom(meta, Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE));
    }

    @Override
    public boolean canStorage(ItemMeta meta) {
        return SlimefunItem.getById(CraftUtils.parseSfId(meta)) instanceof NetworkQuantumStorage;
    }

    @Override
    public boolean canStorage(SlimefunItem item) {
        return item instanceof NetworkQuantumStorage;
    }
    public QuantumCache getQuantumCache(ItemMeta meta) {
        return DataTypeMethods.getCustom(meta, Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE);
    }
    @Override
    public int getStorageMaxSize(ItemMeta meta) {
        QuantumCache cache=getQuantumCache(meta);
        return cache.getLimit();
    }

    @Override
    public void setStorage(ItemMeta meta, ItemStack content) {
//        DataTypeMethods.setCustom(meta, Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE, cache);
//        cache.addMetaLore(itemMeta);
//        itemToDrop.setItemMeta(itemMeta);
        throw new NotImplementedException("NetworkQuantumStorage's content shouldn't be set in this method");
    }

    @Override
    public void setStorageAmount(ItemMeta meta, int amount) {
        QuantumCache cache=getQuantumCache(meta);
        cache.setAmount(amount);
        DataTypeMethods.setCustom(meta,Keys.QUANTUM_STORAGE_INSTANCE, PersistentQuantumStorageType.TYPE,cache);
    }

    @Override
    public int getStorageAmount(ItemMeta meta) {
        return (int)(getQuantumCache(meta).getAmount());
    }

    @Override
    public ItemStack getStorageContent(ItemMeta meta) {
        return getQuantumCache(meta).getItemStack();
    }

    @Override
    public void clearStorage(ItemMeta meta) {
        throw new NotImplementedException("NetworkQuantumStorage's content shouldn't be cleared in this method");
    }

    @Override
    public void updateStorageDisplay(ItemMeta meta, ItemStack item, int amount) {
        QuantumCache cache=getQuantumCache(meta);
        cache.setItemStack(item);
        cache.setAmount(amount);
        cache.updateMetaLore(meta);
    }

    @Override
    public void updateStorageAmountDisplay(ItemMeta meta, int amount) {
        QuantumCache cache=getQuantumCache(meta);
        cache.setAmount(amount);
        cache.updateMetaLore(meta);
    }
}
