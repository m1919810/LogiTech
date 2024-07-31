package me.matl114.logitech.Schedule.PersistentEffects;

import me.matl114.logitech.Schedule.ScheduleEffects;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.entity.Player;

public abstract class AbstractEffect {
    String id;
    public AbstractEffect(String name) {
        this.id = AddUtils.idDecorator(name);
    }
    public  void initEffect(Player p){

        removeEffect(p,1);
    }

    public abstract void aquireEffect(Player p,int level);

    public abstract void removeEffect(Player p,int level);

    public abstract void tickEffect(Player p,int level);

    public String getEffectId() {
        return id;
    }
    public AbstractEffect reigster(){
        return ScheduleEffects.registerEffect(this);
    }

}
