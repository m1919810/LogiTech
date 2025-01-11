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
    public static Method getAmountMethod(Object t){
        if(getAmount==null&&!hasFailedAmount){
            try{
                getAmount=t.getClass().getDeclaredMethod("getAmount");
                getAmount.setAccessible(true);
            }catch(Throwable e){
                Debug.debug("invoke failed amount");
                hasFailedAmount=true;
            }
        }
        return getAmount;
    }
    public static Method getLimitMethod(Object t){
        if(getLimit==null&&!hasFailedLimit){
            try{
                getLimit=t.getClass().getDeclaredMethod("getLimit");
                getLimit.setAccessible(true);
            }catch(Throwable e){
                Debug.debug("invoke failed limit");
                hasFailedLimit=true;
            }
        }
        return getLimit;
    }
    public static Method getSetAmountMethod(Object t){
        if(setAmount==null&&!hasFailedset){
            try{
                setAmount=t.getClass().getDeclaredMethod("setAmount",int.class);
                setAmount.setAccessible(true);
            }catch(Throwable e){
                Debug.debug("invoke failed setamount");
                hasFailedset=true;
            }
        }
        return setAmount;
    }
    public static Method getItemStackMethod(Object t){
        if(getItemStack==null&&!hasFailedgetItemStack){
            try{
                getItemStack=t.getClass().getSuperclass().getDeclaredMethod("getItemStack");
                getItemStack.setAccessible(true);
            }catch(Throwable e){
                Debug.debug("invoke failed getItemStack");
                Debug.debug(t.getClass().getSuperclass().getName());
                Method[] a=t.getClass().getSuperclass().getDeclaredMethods();
                for(Method m:a){
                    Debug.debug(m.getName());
                }
                hasFailedgetItemStack=true;
            }
        }
        return getItemStack;
    }
    public static Method getSetItemStackMethod(Object t){
        if(setItemStack==null&&!hasFailedsetItemStack){
            try {
                setItemStack=t.getClass().getDeclaredMethod("setItemStack",ItemStack.class);
                getItemStack.setAccessible(true);
            }catch(Throwable e){
                Debug.debug("invoke failed setItemStack");
                hasFailedsetItemStack=true;
            }
        }
        return setItemStack;
    }
    public static Method getUpdateMetaLore(Object t){
        if(updateMetaLore==null&&!hasFailedupdateMetaLore){
            try {
                updateMetaLore=t.getClass().getDeclaredMethod("updateMetaLore", ItemMeta.class);
                updateMetaLore.setAccessible(true);
            }catch (Throwable e){
                Debug.debug("invoke failed updateMetaLore");
                hasFailedupdateMetaLore=true;
            }
        }
        return updateMetaLore;
    }
    //TODO  get cache map
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
    public static Method getSyncBlock(SlimefunItem itemInstance){
        if(syncBlock==null&&!hasFailedSyncBlock){
            try{
                syncBlock=itemInstance.getClass().getDeclaredMethod("syncBlock", Location.class, QuantumCache.class);
                syncBlock.setAccessible(true);

            }catch(Throwable e){
                Debug.debug("invoke failed SyncBlock");
                hasFailedSyncBlock=true;
            }
        }
        return syncBlock;
    }
}
