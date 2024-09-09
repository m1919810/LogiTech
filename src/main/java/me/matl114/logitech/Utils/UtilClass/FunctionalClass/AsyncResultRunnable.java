package me.matl114.logitech.Utils.UtilClass.FunctionalClass;

import me.matl114.logitech.Schedule.Schedules;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AsyncResultRunnable<T extends Object> extends BukkitRunnable implements Runnable {
    AtomicBoolean hasDone =new AtomicBoolean(false);
    public T result;
    public abstract T result();
    public void run(){
        result = result();
        hasDone = new AtomicBoolean(true);
    }
    public boolean hasDone(){
        return hasDone.get();
    }
    public T waitForDone(boolean isSync){
        if(Bukkit.isPrimaryThread()&&isSync){
            return result();
        }else {
            Schedules.launchSchedules(AsyncResultRunnable.this,0,isSync,0);
            while(!hasDone()){
                try{
                    Thread.sleep(1);
                }catch(InterruptedException e){

                }
            }
            return result;
        }
    }
}
