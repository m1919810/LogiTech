package me.matl114.logitech.utils.UtilClass.EffectClass;

import me.matl114.logitech.manager.EffectTickManager;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class PlayerEffects {
    public final AbstractEffect TYPE;
    public int duration;
    public int level;
    public boolean pendingMove;
    public PlayerEffects(AbstractEffect type,int time,int level) {
        this.TYPE = type;
        this.duration = time;
        this.level = level;
        this.pendingMove = false;
    }
    public final boolean isFinished(){
        return duration <= 0 || this.pendingMove;
    }
    public final void finish(){
        this.duration=0;
    }
    public final void halt(){
        this.pendingMove=true;
    }
    public void tick(Player p){

        duration--;
        TYPE.tickEffect(p,level);
    }
    public void end(Player p){
        TYPE.removeEffect(p,level);
        this.pendingMove = true;
    }
    public boolean isPendingMove(){
        return pendingMove;
    }
    public void start(Player p){
        TYPE.aquireEffect(p,level);
    }
    public AbstractEffect getType(){
        return TYPE;
    }
    public String getEffectId(){
        return TYPE.getEffectId();
    }
    public void extend(PlayerEffects eff){

        this.duration=eff.duration;
        this.level=eff.level;
    }
    public int getLeftTime(){
        return duration;
    }
    public static void grantEffect(AbstractEffect type, Player p, int level, int time, Function<Player,Boolean> predicate){
        PlayerEffects eff = new PlayerEffects(type,time,level);
        EffectTickManager.addEffect(p,eff,predicate);
    }
    public static void grantEffect(AbstractEffect type, Player p, int level, int time){
        PlayerEffects eff = new PlayerEffects(type,time,level);
        EffectTickManager.addEffect(p,eff);
    }

}
