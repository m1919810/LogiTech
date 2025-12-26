package me.matl114.logitech.utils;

import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtils {
    public static Object invokeGetRecursively(Object target, Settings mod, String declared) {
        return invokeGetRecursively(target, target.getClass(), mod, declared);
    }

    public static Object invokeGetRecursively(Object target, Class clazz, Settings mod, String decleared) {
        //        if(Debug.debug){
        //            Debug.debug("try invoke ",clazz);
        //        }
        try {
            switch (mod) {
                case FIELD:
                    //                    if(clazz.getName().endsWith("AbstractMachineBlock")){
                    //                        Debug.debug("try print this");
                    //                        Field[] fields=clazz.getDeclaredFields();
                    //                        for(Field f:fields){
                    //                            Debug.debug(f.getName());
                    //                        }
                    //                    }
                    // Debug.debug("start find field ",decleared);
                    Field _hasType = clazz.getDeclaredField(decleared);
                    // Debug.debug("find field");
                    _hasType.setAccessible(true);
                    //  Debug.debug("Access true");
                    return _hasType.get(target);
                case METHOD:
                    Method _hasMethod = clazz.getDeclaredMethod(decleared);

                    _hasMethod.setAccessible(true);
                    return _hasMethod.invoke(target);
            }
        } catch (Throwable e) {
        }
        clazz = clazz.getSuperclass();
        if (clazz == null) {
            return null;
        } else {
            return invokeGetRecursively(target, clazz, mod, decleared);
        }
    }

    public static boolean invokeSetRecursively(Object target, String declared, Object value) {
        return invokeSetRecursively(target, target.getClass(), declared, value);
    }

    public static boolean invokeSetRecursively(Object target, Class clazz, String decleared, Object value) {
        try {
            Field _hasType = clazz.getDeclaredField(decleared);
            _hasType.setAccessible(true);
            _hasType.set(target, value);
            return true;
        } catch (Throwable e) {
        }
        clazz = clazz.getSuperclass();
        if (clazz == null) {
            return false;
        } else {
            return invokeSetRecursively(target, clazz, decleared, value);
        }
    }

    public static Object invokeMethodRecursively(
            Object target, Class clazz, Settings mod, String declared, Object... args) {
        return null;
    }

    public static void printAllDeclared(Class clazz, Settings mod) {
        if (mod == Settings.FIELD) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Debug.logger(f.getName());
            }
        } else {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method m : methods) {
                m.setAccessible(true);
                Debug.logger(m.getName());
            }
        }
    }

    public static Pair<Field, Class> getDeclaredFieldsRecursively(Class clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return new Pair(field, clazz);
        } catch (Throwable e) {
            clazz = clazz.getSuperclass();
            if (clazz == null) {
                return null;
            } else {
                return getDeclaredFieldsRecursively(clazz, fieldName);
            }
        }
    }

    public static List<Field> getAllDeclaredFieldsRecursively(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        if (clazz == null) {
            return fieldList;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            fieldList.add(f);
            try {
                f.setAccessible(true);
            } catch (Throwable e) {
                continue;
            }
        }
        fieldList.addAll(getAllDeclaredFieldsRecursively(clazz.getSuperclass()));
        return fieldList;
    }

    public static Pair<Method, Class> getDeclaredMethodsRecursively(
            Class clazz, String fieldName, Class[] parameterTypes) {
        try {
            Method field = clazz.getDeclaredMethod(fieldName, parameterTypes);
            field.setAccessible(true);
            return new Pair(field, clazz);
        } catch (Throwable e) {
            clazz = clazz.getSuperclass();
            if (clazz == null) {
                return null;
            } else {
                return getDeclaredMethodsRecursively(clazz, fieldName, parameterTypes);
            }
        }
    }

    public static Pair<Method, Class> getSuitableMethod(Class clazz, String methodName, Class[] parameterTypes) {
        try {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method m : methods) {
                Class[] params = m.getParameterTypes();
                if (!methodName.equals(m.getName())) {
                    continue;
                }
                boolean match = true;
                if (params.length == parameterTypes.length) {
                    int len = params.length;
                    for (int i = 0; i < len; i++) {
                        if (params[i] == parameterTypes[i] || params[i].isAssignableFrom(parameterTypes[i])) {
                            continue;
                        } else {
                            match = false;
                        }
                    }
                } else {
                    match = false;
                }
                if (match) {
                    m.setAccessible(true);
                    Debug.debug("found method ", m);
                    return new Pair<>(m, clazz);
                }
            }
        } catch (Throwable e) {
        }
        clazz = clazz.getSuperclass();
        if (clazz == null) {
            return null;
        }
        return getSuitableMethod(clazz, methodName, parameterTypes);
    }

    public static Constructor getSuitableConstructor(Class clazz, Class... parameterTypes) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor c : constructors) {
            Class[] params = c.getParameterTypes();
            boolean match = true;
            if (params.length == parameterTypes.length) {
                int len = params.length;
                for (int i = 0; i < len; i++) {
                    if (params[i] == parameterTypes[i] || params[i].isAssignableFrom(parameterTypes[i])) {

                    } else match = false;
                }
            } else {
                match = false;
            }
            if (match) {
                c.setAccessible(true);
                return c;
            }
        }
        return null;
    }

    public static boolean isExtendedFrom(Class clazz, String s) {
        if (clazz == null) {
            return false;
        } else {
            if (clazz.toString().endsWith(s)) {
                return true;
            } else {
                return isExtendedFrom(clazz.getSuperclass(), s);
            }
        }
    }
}
