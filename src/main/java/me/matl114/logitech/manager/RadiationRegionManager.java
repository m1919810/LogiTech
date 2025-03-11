package me.matl114.logitech.manager;

import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.UtilClass.EffectClass.PlayerEffects;
import me.matl114.logitech.core.Registries.CustomEffects;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RadiationRegionManager {
    public static void setup(){

    }
    protected static final byte[] lock = new byte[0];
    protected static final LinkedHashMap<Location,BukkitRunnable> RADIATION_THREAD=new LinkedHashMap<>();
    static{
        ScheduleSave.addFinalTask(()->{
            for(BukkitRunnable it:RADIATION_THREAD.values()){
                it.cancel();
            }
        });
    }
    public static boolean removeNearRadiation(Location loc,double radius){
        synchronized(lock){
            Iterator<Map.Entry<Location,BukkitRunnable>> it=RADIATION_THREAD.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<Location,BukkitRunnable> entry=it.next();
                Location loc2 = entry.getKey();
                if(loc2!=null&&loc2.getWorld()==loc.getWorld()&&loc2.distance(loc)<radius){
                    BukkitRunnable it2 = entry.getValue();
                    it2.cancel();
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean removeRadiation(Location loc){
        BukkitRunnable t;
        synchronized(lock){
            t=RADIATION_THREAD.remove(loc);
        }
        if(t!=null){
            t.cancel();
            return true;
        }else {
            return false;
        }
    }
    public static void addRadiation(Location loc, int range,int period,int speed,int level){
        removeRadiation(loc);
        BukkitRunnable task=new BukkitRunnable(){
            public int radiation=period;
            Location center=loc.clone();
            public void run(){
                if(radiation>0){
                    --radiation;
                    runRadiation(center,range,level);
                }else {
                    cancel();
                }
            }
        };
        synchronized (lock){
            RADIATION_THREAD.put(loc,task);
        }
        Schedules.launchSchedules(task,speed,true,speed);
    }

    /**
     * must run in sync thread
      * @param loc
     * @param range
     * @param level
     */
    public static void runRadiation(Location loc,int range,int level){
        Collection<Entity> entities=loc.getWorld().getNearbyEntities(loc,
                range,range,range
                ,(entity -> entity instanceof Player));
        for(Entity entity:entities){
            if(entity instanceof  Player player){
                AddUtils.sendMessage(player,"&e警告!您已进入危险的辐射区!");
                PlayerEffects.grantEffect(CustomEffects.RADIATION,
                        player,level,60,(player1 -> true));
            }
        }
    }
}
