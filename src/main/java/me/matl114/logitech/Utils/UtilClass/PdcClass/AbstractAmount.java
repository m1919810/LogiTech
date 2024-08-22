package me.matl114.logitech.Utils.UtilClass.PdcClass;

import de.jeff_media.morepersistentdatatypes.DataType;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class AbstractAmount implements PersistentDataType<PersistentDataContainer, Integer> {
    //public final static AbstractAmount TYPE = new AbstractAmount("data");
//    public final static AbstractAmount X = new AbstractAmount("x");
//    public final static AbstractAmount Y = new AbstractAmount("y");
//    public final static AbstractAmount Z = new AbstractAmount("z");
    public AbstractAmount(String s) {
        AMOUNT=AddUtils.getNameKey(s);
    }
    public final NamespacedKey AMOUNT;
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
