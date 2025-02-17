package me.matl114.logitech.core.Depends;

import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.Utils.Debug;
import me.matl114.matlib.Utils.Reflect.MethodAccess;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class NetWorkQuantumMethod {
    static Method getAmount=null;
    static  boolean hasFailedAmount=false;
    static Method getLimit=null;
    static boolean hasFailedLimit=false;
    static Method setAmount=null;
    static boolean hasFailedset=false;
    static Method getItemStack=null;
    static boolean hasFailedgetItemStack=false;
    static Method setItemStack=null;
    static boolean hasFailedsetItemStack=false;
    static Method updateMetaLore=null;
    static boolean hasFailedupdateMetaLore=false;
    static Map  NetworkCacheMap=null;
    static boolean hasFailedCachemap=false;
    static Method syncBlock=null;
    static boolean hasFailedSyncBlock=false;
    public static final MethodAccess<? extends Number> getAmountAccess=MethodAccess.ofName("getAmount").printError(true);
    public static final MethodAccess<? extends Number> getLimitAccess=MethodAccess.ofName("getLimit").printError(true);
    //todo refactor this part of code
    public static final MethodAccess<?> getSetAmountAccess=new MethodAccess<>(t-> {
        try {
            return t.getClass().getDeclaredMethod("setAmount",int.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }).printError(true);
    //should we use MethodHandle?
    //public static final MethodHandle getItemStackHandle= MethodHandles.privateLookupIn()
    //so we just use reflection first
    public static final MethodAccess<ItemStack> getItemStackAccess=new MethodAccess<ItemStack>(t->{
        try {
            return t.getClass().getSuperclass().getDeclaredMethod("getItemStack");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }).printError(true);
    public static final MethodAccess<?> setItemStackAccess=MethodAccess.ofName("setItemStack",ItemStack.class).printError(true);
    public static final MethodAccess<?> updateMetaLoreAccess=MethodAccess.ofName("updateMetaLore",ItemMeta.class).printError(true);
    public static final MethodAccess<?> syncBlockAccess=MethodAccess.ofName("syncBlock",Location.class,QuantumCache.class).printError(true);;



    public static Map getCacheMap(SlimefunItem itemInstance){
        if(NetworkCacheMap==null&&!hasFailedCachemap){
            try{
                Field field= itemInstance.getClass().getDeclaredField("CACHES");
                field.setAccessible(true);
                NetworkCacheMap=(Map)field.get(itemInstance);
            }catch (Throwable e){
                Debug.debug("invoke failed getCacheMap");
                hasFailedCachemap=true;
            }
        }
        return NetworkCacheMap;
    }
}
