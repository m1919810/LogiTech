package me.matl114.logitech.Schedule;

import java.util.HashSet;

public class ScheduleSave {
    public static HashSet<Runnable> FINAL_SAVES = new HashSet<>();
    public static HashSet<Runnable> PERIODIC_SAVES = new HashSet<>();
    public static void onFinalSave(){
        for (Runnable task : PERIODIC_SAVES) {
            task.run();
        }
        for (Runnable task : FINAL_SAVES) {
            task.run();
        }
        return;
    }
    public static void onPeriodicSave(){
        for (Runnable task : PERIODIC_SAVES) {
            task.run();
        }
        return;
    }

    /**
     * will periodically save every 5 miniuates and when server off
     * @param task
     */
    public static void addPeriodicTask(Runnable task){
        PERIODIC_SAVES.add(task);
    }

    /**
     * will save when server off
     * @param task
     */
    public static void addFinalTask(Runnable task){
        FINAL_SAVES.add(task);
    }
}
