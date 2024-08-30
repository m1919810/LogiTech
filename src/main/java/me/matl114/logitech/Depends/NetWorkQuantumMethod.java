package me.matl114.logitech.Depends;

import me.matl114.logitech.Utils.Debug;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;

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
    public static Method getAmountMethod(Object t){
        if(getAmount==null&&!hasFailedAmount){
            try{
                getAmount=t.getClass().getDeclaredMethod("getAmount");
                getAmount.setAccessible(true);
            }catch(NoSuchMethodException e){
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
            }catch(NoSuchMethodException e){
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
            }catch(NoSuchMethodException e){
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
            }catch(NoSuchMethodException e){
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
            }catch(NoSuchMethodException e){
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
            }catch (NoSuchMethodException e){
                Debug.debug("invoke failed updateMetaLore");
                hasFailedupdateMetaLore=true;
            }
        }
        return updateMetaLore;
    }
}
