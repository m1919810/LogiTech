package me.matl114.logitech.SlimefunItem.Storage.Singularity;

import de.jeff_media.morepersistentdatatypes.DataType;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class AbstractStorageAmount implements PersistentDataType<PersistentDataContainer, Integer> {
    public final static AbstractStorageAmount TYPE = new AbstractStorageAmount();
    public static final NamespacedKey AMOUNT= AddUtils.getNameKey("data");
    public Class<PersistentDataContainer> getPrimitiveType(){
        return PersistentDataContainer.class;
    }
    public Class<Integer> getComplexType(){
        return Integer.class;
    }
    @Override
    @Nonnull
    public PersistentDataContainer toPrimitive(@Nonnull Integer complex, @Nonnull PersistentDataAdapterContext context) {
        final PersistentDataContainer container = context.newPersistentDataContainer();
        container.set(AMOUNT, DataType.INTEGER, complex);
        return container;
    }

    @Override
    @Nonnull
    public Integer fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        return primitive.get(AMOUNT, DataType.INTEGER);
    }

}
