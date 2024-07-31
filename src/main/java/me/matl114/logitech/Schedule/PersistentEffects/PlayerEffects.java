package me.matl114.logitech.Schedule.PersistentEffects;

import me.matl114.logitech.Schedule.ScheduleEffects;
import org.bukkit.entity.Player;

public class PlayerEffects {
    public final AbstractEffect TYPE;
    public int duration;
    public int level;
    public PlayerEffects(AbstractEffect type,int time,int level) {
        this.TYPE = type;
        this.duration = time;
        this.level = level;
    }
    public boolean isFinished(){
        return duration <= 0;
    }
    public void tick(Player p){

        duration--;
        TYPE.tickEffect(p,level);
    }
    public void end(Player p){

        TYPE.removeEffect(p,level);
    }
    public void start(Player p){

        ScheduleEffects.addEffect(p,this);
        TYPE.aquireEffect(p,level);
    }
    public String getEffectId(){
        return TYPE.getEffectId();
    }
    public void extendTime(int time){
        duration =duration>time?duration:time;
    }
    public int getLeftTime(){
        return duration;
    }
    public static void grantEffect(AbstractEffect type,Player p,int level,int time){
        new PlayerEffects(type,time,level).start(p);
    }

}
