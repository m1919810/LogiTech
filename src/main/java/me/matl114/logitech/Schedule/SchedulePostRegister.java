package me.matl114.logitech.Schedule;

import me.matl114.logitech.Utils.RecipeSupporter;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class SchedulePostRegister {
    //在服务器启动之后执行的初始化
    public static HashSet<Task> registerTasks=new LinkedHashSet<>();
    public static void addPostRegisterTask(Task t) {
        registerTasks.add(t);
    }
    public static void schedulePostRegister(){
        RecipeSupporter.init();


        for(Task t : registerTasks){
            t.run();
        }
    }
}
