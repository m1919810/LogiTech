package me.matl114.logitech.Utils.UtilClass.PdcClass;

import de.jeff_media.morepersistentdatatypes.DataType;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class AbstractLocation implements PersistentDataType<PersistentDataContainer, Location> {
   // public final static AbstractLocation TYPE = new AbstractLocation();
    public static final NamespacedKey AMOUNT= AddUtils.getNameKey("data");
    public Class<PersistentDataContainer> getPrimitiveType(){
        return PersistentDataContainer.class;
    }
    public Class<Location> getComplexType(){
        return Location.class;
    }

    public PersistentDataContainer toPrimitive(@Nonnull Location complex, @Nonnull PersistentDataAdapterContext context) {
        final PersistentDataContainer container = context.newPersistentDataContainer();
        container.set(AMOUNT, DataType.LOCATION, complex);
        return container;
    }

    @Override
    public Location fromPrimitive( PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        return primitive.get(AMOUNT, DataType.LOCATION);
    }
}
