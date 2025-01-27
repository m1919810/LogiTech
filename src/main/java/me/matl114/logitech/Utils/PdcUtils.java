package me.matl114.logitech.Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PdcUtils {
//
    @Nonnull
    public static PersistentDataContainer getOrCreateTag(PersistentDataContainer container, NamespacedKey key) {
        PersistentDataContainer re=null;
        if(container.has(key, PersistentDataType.TAG_CONTAINER)) {
            re=container.get(key, PersistentDataType.TAG_CONTAINER);
        }
        if(re==null) {
            re=container.getAdapterContext().newPersistentDataContainer();
        }
        return re;
    }
    @Nullable
    public static PersistentDataContainer getTag(PersistentDataContainer container, NamespacedKey key) {
        return container.get(key, PersistentDataType.TAG_CONTAINER);
    }
    public static void setTag(PersistentDataContainer container, NamespacedKey key, PersistentDataContainer tag ) {
        container.set(key,PersistentDataType.TAG_CONTAINER,tag);
    }
    public static <T extends Object> T getOrDefault(PersistentDataContainer container, NamespacedKey key,PersistentDataType<T,T> type,T defaultValue) {
        if(container.has(key, type)) {
            return container.get(key, type);
        }else {
            return defaultValue;
        }
    }
}
