package me.matl114.logitech.Schedule;

import java.util.HashSet;

public class ScheduleSave {
    public static HashSet<Task> FINAL_SAVES = new HashSet<>();
    public static HashSet<Task> PERIODIC_SAVES = new HashSet<>();
    public static void onFinalSave(){
        for (Task task : PERIODIC_SAVES) {
            task.run();
        }
        for (Task task : FINAL_SAVES) {
            task.run();
        }
        return;
    }
    public static void onPeriodicSave(){
        for (Task task : PERIODIC_SAVES) {
            task.run();
        }
        return;
    }

    /**
     * will periodically save every 5 miniuates and when server off
     * @param task
     */
    public static void addPeriodicTask(Task task){
        PERIODIC_SAVES.add(task);
    }

    /**
     * will save when server off
     * @param task
     */
    public static void addFinalTask(Task task){
        FINAL_SAVES.add(task);
    }
}
