package me.matl114.logitech.Schedule;


import me.matl114.logitech.Utils.Debug;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Schedules {
    private static BukkitRunnable autoSaveThread;
    private static BukkitRunnable effectThread;
    public static Plugin plugin;
    public static void setupSchedules(Plugin plugin){
        Schedules.plugin=plugin;
        autoSaveThread = new BukkitRunnable() {
            @Override
            public void run() {
                ScheduleSave.onPeriodicSave();
            }
        };
        int periodAutoSave=5 * 60 * 20;
        autoSaveThread.runTaskTimerAsynchronously(plugin, periodAutoSave, periodAutoSave);

        effectThread = new BukkitRunnable() {
            public void run() {
                ScheduleEffects.scheduleEffects();
            }
        };
        int periodEffect=20;
        effectThread.runTaskTimerAsynchronously(plugin, 200, periodEffect);

        BukkitRunnable postRegisterTask = new BukkitRunnable() {
            public void run() {
                SchedulePostRegister.schedulePostRegister();
            }
        };
        int delayPostRegister=4;
        postRegisterTask.runTaskLaterAsynchronously(plugin, delayPostRegister);
    }
    public static void onDisableSchedules(Plugin plugin){
        Debug.logger("保存附属数据中");
        ScheduleSave.onFinalSave();
        Debug.logger("附属数据保存完成");
    }
    public static void launchSchedules(BukkitRunnable thread ,int delay,boolean isSync){
        launchSchedules(thread,delay,isSync,-1);
    }
    public static void launchSchedules(BukkitRunnable thread ,int delay,boolean isSync,int period){
        if(period<=0){
            if(isSync){
                thread.runTaskLater(plugin, delay);
            }else{
                thread.runTaskLaterAsynchronously(plugin,delay);
            }
        }else{
            if(isSync){
                thread.runTaskTimer(plugin, delay, period);
            }else{
                thread.runTaskTimerAsynchronously(plugin, delay,period);
            }
        }
    }
    public static BukkitRunnable getRunnable(Runnable runnable){
        return new BukkitRunnable() {
            public void  run(){
                runnable.run();
            }
        };
    }
}
