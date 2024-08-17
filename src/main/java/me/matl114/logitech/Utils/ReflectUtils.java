package me.matl114.logitech.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {
    public static  Object invokeRecursively(Object target,Settings mod,String declared){
        return invokeRecursively(target,target.getClass(),mod,declared);
    }
    public static  Object invokeRecursively(Object target,Class clazz,Settings mod,String decleared){
        try{
            switch (mod){
                case FIELD:
                    Field _hasType=clazz.getDeclaredField(decleared);
                    _hasType.setAccessible(true);
                    return  _hasType.get(target);
                case METHOD:
                    Method _hasMethod=clazz.getDeclaredMethod(decleared);

                    _hasMethod.setAccessible(true);
                    return _hasMethod.invoke(target);
            }
        }catch (Throwable e){
        }
        clazz=clazz.getSuperclass();
        if(clazz==null){
            return null;
        }else {
            return invokeRecursively(target,clazz,mod,decleared);
        }
    }
    public static Object invokeMethodRecursively(Object target,Class clazz,Settings mod,String declared,Object ... args){
        return null;
    }
}
