package me.matl114.logitech.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {
    public static  Object invokeGetRecursively(Object target, Settings mod, String declared){
        return invokeGetRecursively(target,target.getClass(),mod,declared);
    }
    public static  Object invokeGetRecursively(Object target, Class clazz, Settings mod, String decleared){
//        if(Debug.debug){
//            Debug.debug("try invoke ",clazz);
//        }
        try{
            switch (mod){
                case FIELD:
//                    if(clazz.getName().endsWith("AbstractMachineBlock")){
//                        Debug.debug("try print this");
//                        Field[] fields=clazz.getDeclaredFields();
//                        for(Field f:fields){
//                            Debug.debug(f.getName());
//                        }
//                    }
                    //Debug.debug("start find field ",decleared);
                    Field _hasType=clazz.getDeclaredField(decleared);
                   // Debug.debug("find field");
                    _hasType.setAccessible(true);
                  //  Debug.debug("Access true");
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
            return invokeGetRecursively(target,clazz,mod,decleared);
        }
    }
    public static boolean invokeSetRecursively(Object target,  String declared,Object value){
        return invokeSetRecursively(target,target.getClass(),declared,value);
    }
    public static boolean invokeSetRecursively(Object target,Class clazz, String decleared,Object value){
        try{
            Field _hasType=clazz.getDeclaredField(decleared);
            _hasType.setAccessible(true);
             _hasType.set(target,value);
             return true;
        }catch (Throwable e){
        }
        clazz=clazz.getSuperclass();
        if(clazz==null){
            return false;
        }else {
            return invokeSetRecursively(target,clazz,decleared,value);
        }
    }
    public static Object invokeMethodRecursively(Object target,Class clazz,Settings mod,String declared,Object ... args){
        return null;

    }
}
