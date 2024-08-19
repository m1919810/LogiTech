package me.matl114.logitech.Depends;

import de.jeff_media.morepersistentdatatypes.DataType;
import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.sefiraat.networks.utils.Keys;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class MockQuantumStorageType implements PersistentDataType<PersistentDataContainer, QuantumCache>{
    public static final PersistentDataType<PersistentDataContainer, QuantumCache> TYPE = new MockQuantumStorageType();
    public static final NamespacedKey ITEM = Keys.newKey("item");
    public static final NamespacedKey AMOUNT = Keys.newKey("amount");
    public static final NamespacedKey MAX_AMOUNT = Keys.newKey("max_amount");
    public static final NamespacedKey VOID = Keys.newKey("void");
    public static final NamespacedKey SUPPORTS_CUSTOM_MAX_AMOUNT = Keys.newKey("supports_custom_max_amount");

    public MockQuantumStorageType() {
    }

    @Nonnull
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Nonnull
    public Class<QuantumCache> getComplexType() {
        return QuantumCache.class;
    }

    @Nonnull
    public PersistentDataContainer toPrimitive(@Nonnull QuantumCache complex, @Nonnull PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();
        container.set(ITEM, DataType.ITEM_STACK, complex.getItemStack());
        container.set(AMOUNT, DataType.LONG, complex.getAmount());
        container.set(MAX_AMOUNT, DataType.INTEGER, complex.getLimit());
        container.set(VOID, DataType.BOOLEAN, complex.isVoidExcess());
        container.set(SUPPORTS_CUSTOM_MAX_AMOUNT, DataType.BOOLEAN, complex.supportsCustomMaxAmount());
        return container;
    }

    @Nonnull
    public QuantumCache fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        ItemStack item = (ItemStack)primitive.get(ITEM, DataType.ITEM_STACK);
        long amount = (Long)primitive.get(AMOUNT, DataType.LONG);
        int limit = (Integer)primitive.get(MAX_AMOUNT, DataType.INTEGER);
        boolean voidExcess = (Boolean)primitive.get(VOID, DataType.BOOLEAN);
        boolean supportsCustomMaxAmount = (Boolean)primitive.get(SUPPORTS_CUSTOM_MAX_AMOUNT, DataType.BOOLEAN);
        return new QuantumCache(item, amount, limit, voidExcess, supportsCustomMaxAmount);
    }
}
