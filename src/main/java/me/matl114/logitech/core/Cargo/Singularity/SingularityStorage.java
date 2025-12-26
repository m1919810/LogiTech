package me.matl114.logitech.core.Cargo.Singularity;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import java.util.ArrayList;
import java.util.List;
import me.matl114.logitech.core.Items.SpecialItems.Singularity;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.utils.UtilClass.PdcClass.AbstractStorageType;
import me.matl114.logitech.utils.UtilClass.StorageClass.StorageType;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SingularityStorage extends StorageType {
    public SingularityStorage() {
        super();
    }

    public static final String ITEM_DISPLAY_PREFIX = AddUtils.resolveColor("&x&E&B&3&3&E&B压缩物品: &f");
    public static final String CORRUPTED_STORAGE_PREFIX =
            AddUtils.resolveColor("&x&E&B&3&3&E&B压缩物品: &f&c该存储由于NBT过大而损坏,请不要从存储交互接口中移出,否则物品可能丢失!");
    public static final String AMOUNT_DISPLAY_PREFIX = AddUtils.resolveColor("&x&E&B&3&3&E&B压缩数量: &f");
    public static final NamespacedKey KEY_ITEM = AddUtils.getNameKey("sin_item");
    public static final NamespacedKey KEY_AMOUNT = AddUtils.getNameKey("sin_amount");
    protected static final int MAX_AMOUNT = 2147483647;

    public boolean isStorage(ItemMeta meta) {
        return meta.getPersistentDataContainer().has(KEY_AMOUNT)
                && meta.getPersistentDataContainer().has(KEY_ITEM);
    }

    public boolean canStorage(ItemMeta meta) {
        String it = CraftUtils.parseSfId(meta);
        if (it == null) return false;
        return SlimefunItem.getById(it) instanceof Singularity;
    }

    public boolean canStorage(SlimefunItem sfitem) {
        return sfitem instanceof Singularity;
    }

    public int getStorageMaxSize(ItemMeta meta) {
        return MAX_AMOUNT;
    }

    public void setStorage(ItemMeta meta, ItemStack item) {
        if (item != null) {
            clearStorage(meta);
            meta.getPersistentDataContainer().set(KEY_AMOUNT, PersistentDataType.INTEGER, 0);
            ItemStack tmp = AddUtils.getCleaned(item);
            tmp.setAmount(1);
            meta.getPersistentDataContainer().set(KEY_ITEM, AbstractStorageType.TYPE, tmp);
            // check length
            PersistentDataContainer container =
                    meta.getPersistentDataContainer().get(KEY_ITEM, PersistentDataType.TAG_CONTAINER);
            boolean corruptedStorage = false;
            if (container != null && container.has(AbstractStorageType.ITEM, PersistentDataType.BYTE_ARRAY)) {
                byte[] bytes = container.get(AbstractStorageType.ITEM, PersistentDataType.BYTE_ARRAY);
                if (bytes != null && bytes.length > 4_194_304) {
                    // remove data too large item
                    meta.getPersistentDataContainer().remove(KEY_ITEM);
                    corruptedStorage = true;
                }
            }
            List<String> loreToAdd = corruptedStorage
                    ? List.of(CORRUPTED_STORAGE_PREFIX, AMOUNT_DISPLAY_PREFIX + item.getAmount())
                    : List.of(
                            ITEM_DISPLAY_PREFIX + CraftUtils.getItemName(item),
                            AMOUNT_DISPLAY_PREFIX + item.getAmount());
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                lore.addAll(loreToAdd);
                meta.setLore(lore);
            } else {
                meta.setLore(loreToAdd);
            }
        }
    }

    public void clearStorage(ItemMeta meta) {
        meta.getPersistentDataContainer().remove(KEY_ITEM);
        meta.getPersistentDataContainer().remove(KEY_AMOUNT);
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (int i = 0; i < lore.size(); ) {
                if (lore.get(i).startsWith(ITEM_DISPLAY_PREFIX)) {
                    lore.remove(i);
                } else if (lore.get(i).startsWith(AMOUNT_DISPLAY_PREFIX)) {
                    lore.remove(i);
                } else {
                    i++;
                }
            }
            meta.setLore(lore);
        }
    }

    public ItemStack getStorageContent(ItemMeta meta) {
        return meta.getPersistentDataContainer().get(KEY_ITEM, AbstractStorageType.TYPE);
    }

    public int getStorageAmount(ItemMeta meta) {
        try {
            Integer e = meta.getPersistentDataContainer().get(KEY_AMOUNT, PersistentDataType.INTEGER);
            if (e == null) return 0;
            return e;
        } catch (Throwable e) {
            return 0;
        }
    }

    public void onStorageAmountWrite(ItemMeta meta, int amount) {
        meta.getPersistentDataContainer().set(KEY_AMOUNT, PersistentDataType.INTEGER, amount);
    }

    public void onStorageDisplayWrite(ItemMeta meta, int amount) {

        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        if (lore.size() > 0) {
            lore.set(lore.size() - 1, AddUtils.concat(AMOUNT_DISPLAY_PREFIX, String.valueOf(amount)));
        } else {
            lore.add(AddUtils.concat(AMOUNT_DISPLAY_PREFIX, String.valueOf(amount)));
        }
        meta.setLore(lore);
    }

    public void updateStorageDisplay(ItemMeta meta, ItemStack item, int amount) {}
}
