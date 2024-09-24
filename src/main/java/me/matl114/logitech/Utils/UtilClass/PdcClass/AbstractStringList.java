package me.matl114.logitech.Utils.UtilClass.PdcClass;


import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbstractStringList implements PersistentDataType<PersistentDataContainer, List<String>>{
        public static final PersistentDataType<PersistentDataContainer, List<String>> TYPE = new AbstractStringList();

        public AbstractStringList() {
        }

        @Nonnull
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }
        private final Class clazz=(new ArrayList<String>()).getClass();
        @Nonnull
        public Class<List<String>> getComplexType() {

            return (Class<List<String>>)clazz;
        }

        @Nonnull
        public PersistentDataContainer toPrimitive(@Nonnull List<String> complex, @Nonnull PersistentDataAdapterContext context) {
            PersistentDataContainer container = context.newPersistentDataContainer();

            for(int i = 0; i < complex.size(); ++i) {
                NamespacedKey key = AddUtils.getNameKey(String.valueOf(i)) ;
                container.set(key, STRING, (String)complex.get(i));
            }

            return container;
        }

        @Nonnull
        public List<String> fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
            List<String> strings = new ArrayList();
            Iterator var4 = primitive.getKeys().iterator();

            while(var4.hasNext()) {
                NamespacedKey key = (NamespacedKey)var4.next();
                strings.add((String)primitive.get(key, STRING));
            }

            return strings;
        }

}
