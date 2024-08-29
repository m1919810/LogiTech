package me.matl114.logitech.Depends;

import me.matl114.logitech.Utils.Debug;

import java.lang.reflect.Method;

public class NetWorkQuantumMethod {
    static Method getAmount=null;
    static  Boolean hasFailedAmount=false;
    static Method getLimit=null;
    static Boolean hasFailedLimit=false;
    static Method setAmount=null;
    static Boolean hasFailedset=false;
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
                Debug.debug("invoke failed amount");
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
                Debug.debug("invoke failed amount");
                hasFailedset=true;
            }
        }
        return setAmount;
    }
}
