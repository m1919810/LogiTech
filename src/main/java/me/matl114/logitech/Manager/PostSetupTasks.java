package me.matl114.logitech.Manager;

import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.core.Registries.RecipeSupporter;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class PostSetupTasks {
    //在服务器启动之后执行的初始化
    public static boolean startPostRegister=false;
    public static boolean recipeSupportorInit=false;
    public static HashSet<Runnable> registerTasks=new LinkedHashSet<>();
    public static void addPostRegisterTask(Runnable t) {
        registerTasks.add(t);
    }
    public static void schedulePostRegister(){
        startPostRegister=true;
        Debug.logger("START ADDON POSTREGISTER TASKS");
        RecipeSupporter.init();
        for(Runnable t : registerTasks){
            t.run();
        }
    }
}
