package me.matl114.logitech.utils;

import lombok.Getter;
import me.matl114.matlib.Utils.Reflect.FieldAccess;
import me.matl114.matlib.Utils.Reflect.MethodAccess;
import me.matl114.matlib.Utils.Reflect.ReflectUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class NMSUtils {
    public static final MethodAccess<?> getHandleMethodAccess=MethodAccess.ofName("getHandle");
    public static final MethodAccess<?> getPositionMethodAccess=MethodAccess.ofName("getPosition");
    public static final MethodAccess<?> doRandomTickAccess=MethodAccess.ofName("randomTick");
    public static final MethodAccess<?> getNMSMethodAccess=MethodAccess.ofName("getNMS");
    public static final MethodAccess<?> getCraftWorldHandleMethodAccess=MethodAccess.ofName("getHandle");
    public static final MethodAccess<?> getNMSBlockType=MethodAccess.ofName("b");

    public static final MethodAccess<?> getBlockEntityMethodAccess=new MethodAccess((obj)->{
        var a= ReflectUtils.getMethodsByName(obj.getClass(),"c_");
//        Debug.logger(a);
        return a==null?null:a.getA();
    });
    @Getter
    private static Class tileEntityClass;
    public static final MethodAccess<?> getBlockEntityTypeMethodAccess=new MethodAccess((obj)->{
        List<Class> cls= ReflectUtils.getAllSuperClassRecursively(obj.getClass()).stream().filter(s->"TileEntity".equals(s.getSimpleName())).toList();
        Class clazz=cls.get(cls.size()-1);
        //errorOut.accept(clazz);
        tileEntityClass=clazz;
        var re= Arrays.stream(clazz.getDeclaredMethods()).filter(m->m.getParameterTypes().length==0).filter(m->m.getReturnType()!=boolean.class).filter(s->s.getReturnType().getSimpleName().endsWith("Types")).toList();
        //errorOut.accept(re);
        return re.isEmpty()?null:re.get(0);
    });

    public static Class iTileEntityInterface;
    public static final MethodAccess<?> getTickerMethodAccess=new MethodAccess((obj)->{
        Method[] methods=iTileEntityInterface.getDeclaredMethods();
        Method a= Arrays.stream(methods).filter((m)->"a".equals(m.getName())).filter(m->m.getParameterTypes().length==3).findFirst().get();
        //errorOut.accept("get info", Arrays.stream(a.getParameterTypes()).toList(),a.getReturnType());
        return a;
    });

    public static Class blockEntityTickerInterface;
    public static final MethodAccess<?> tickMethodAccess=new MethodAccess((obj)->{
        Method[] a=blockEntityTickerInterface.getDeclaredMethods();
        var re= Arrays.stream(a).filter((m)->m.getParameterTypes().length==4).filter(m->"tick".equals(m.getName())).toList();
        return re.isEmpty()?null:re.get(0);
    });
    public static final MethodAccess<?> randomTickAccess=new MethodAccess((o -> {

        List<Method> methods=ReflectUtils.getAllMethodsRecursively(o.getClass());
        return methods.stream().filter(m->m.getParameterTypes().length==3).filter(m->m.getName().equals("b"))
                .filter(m->m.getParameterTypes()[2].getName().endsWith("RandomSource")).findFirst().get();

    })).printError(true);
    public static final FieldAccess randomSourceAccess=new FieldAccess((o)->{
        List<Field> fields=ReflectUtils.getAllFieldsRecursively(o.getClass());
        var a=fields.stream().filter(m-> !Modifier.isPrivate( m.getModifiers())).filter(m->m.getType().getName().endsWith("RandomSource")).toList();
       // Debug.logger(a);
        return a.get(0);
    });
    //public static final MethodAccess player
}
