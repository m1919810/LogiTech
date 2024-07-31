package me.matl114.logitech.SlimefunItem.Storage.Singularity;

import de.jeff_media.morepersistentdatatypes.DataType;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class AbstractStorageType implements PersistentDataType<PersistentDataContainer, ItemStack> {
    public final static AbstractStorageType TYPE = new AbstractStorageType();
    public static final NamespacedKey ITEM= AddUtils.getNameKey("data");
    public Class<PersistentDataContainer> getPrimitiveType(){
        return PersistentDataContainer.class;
    }
    public Class<ItemStack> getComplexType(){
        return ItemStack.class;
    }
    @Override
    @Nonnull
    public PersistentDataContainer toPrimitive(@Nonnull ItemStack complex, @Nonnull PersistentDataAdapterContext context) {
        final PersistentDataContainer container = context.newPersistentDataContainer();
        if(complex.getAmount()==1){
        container.set(ITEM, DataType.ITEM_STACK, complex);
        }
        else{
            complex=complex.clone();
            complex.setAmount(1);
            container.set(ITEM, DataType.ITEM_STACK, complex);

        }
        return container;
    }

    @Override
    @Nonnull
    public ItemStack fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        final ItemStack item = primitive.get(ITEM, DataType.ITEM_STACK);
        return item;
    }

}
