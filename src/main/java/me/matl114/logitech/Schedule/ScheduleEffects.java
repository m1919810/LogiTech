package me.matl114.logitech.Schedule;

import me.matl114.logitech.Schedule.PersistentEffects.AbstractEffect;
import me.matl114.logitech.Schedule.PersistentEffects.CustomEffects;
import me.matl114.logitech.Schedule.PersistentEffects.PlayerEffects;
import me.matl114.logitech.Utils.Debug;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ScheduleEffects {
    public static AbstractEffect registerEffect(AbstractEffect effect) {
        REGISTERED_EFFECTS.add(effect);
        return effect;
    }
    public static final HashSet<AbstractEffect> REGISTERED_EFFECTS = new HashSet<>(){{

    }};
    static {
        CustomEffects.registerEffect();
    }
    private static final HashMap<UUID, HashMap<AbstractEffect,PlayerEffects>> EFFECTS = new HashMap<>();
    //when player login after server start ,they may contains effect attributes
    private static void initAllEffects(Player p) {
        int len=REGISTERED_EFFECTS.size();
        for(AbstractEffect effect : REGISTERED_EFFECTS) {
            effect.initEffect(p);
        }
    }
    /**
     * 该操作会自动把自己变成同步的
     * @param p
     */
    public static void clearEffect(Player p, AbstractEffect effect) {
        Schedules.launchSchedules(()->{
         PlayerEffects eff= EFFECTS.get(p).get(effect);
         if(eff != null) {
             eff.finish();
         }},0,true,0);
    }

    /**
     * 该操作只能在同步线程中调用
     * @param p
     */
    public static void clearAllEffects(Player p) {
        HashMap<AbstractEffect,PlayerEffects> effects = EFFECTS.get(p.getUniqueId());
        Set<AbstractEffect> effectSet = effects.keySet();
        for(AbstractEffect effect : effectSet) {
            PlayerEffects eff= effects.get(effect);
            if(eff!=null)
                eff.finish();
        }
    }

    /**
     * 该操作只能在death监听器中调用
     * @param e
     */
    public static void clearEffectsOnDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        synchronized (lock){
            HashMap<AbstractEffect,PlayerEffects> effects = EFFECTS.get(p.getUniqueId());
            if(effects==null)return;
            Set<AbstractEffect> effectSet = effects.keySet();
            for(AbstractEffect effect : effectSet) {
                PlayerEffects eff= effects.get(effect);
                if(eff!=null){
                    eff.getType().onDeathEvent(e,eff.level);
                    if(eff.getType().onDeathClear()){
                        eff.finish();
                    }
                }
            }
        }
    }

    public static boolean hasEffects(Player p,AbstractEffect effect) {
        return EFFECTS.get(p).containsKey(effect);
    }


    /**
     * 该操作只能在同步线程中调用!
     * @param player
     * @param effect
     * @return
     */
    public static void addEffect(Player player, PlayerEffects effect){
        addEffect(player,effect,(player1 -> true));
    }
    /**
     * 该操作只能在同步线程中调用!
     * @param player
     * @param effect
     * @return
     */
    private static byte[] lock = new byte[0];
    public static void addEffect(Player player, PlayerEffects effect, Function<Player,Boolean> predicate) {
        synchronized(lock){
            if (EFFECTS.containsKey(player.getUniqueId())) {
                HashMap<AbstractEffect,PlayerEffects> effects = EFFECTS.get(player.getUniqueId());
                PlayerEffects old = effects.get(effect.getType());
                if(predicate.apply(player)){
                    if(old==null) {
                        effects.put(effect.getType(), effect);
                    }else {
                        old.extend(effect);
                    }
                    Schedules.launchSchedules(()-> {
                        effect.start(player);
                    },0,true,0);
                }
            }else{
                HashMap<AbstractEffect,PlayerEffects> effects = new HashMap<>();
                if(predicate.apply(player)){
                    effects.put(effect.getType(), effect);
                    EFFECTS.put(player.getUniqueId(),effects);
                    Schedules.launchSchedules(()-> {
                        effect.start(player);
                    },0,true,0);
                    return;
                }
                EFFECTS.put(player.getUniqueId(),effects);
            }
        }
    }

    /**
     * 该runnable只能在Schedules的effect线程运行
     */

    public static void scheduleEffects(){
    HashMap<AbstractEffect,PlayerEffects> effects;

        for (Player player : Bukkit.getOnlinePlayers()){
            synchronized(lock){
                if (EFFECTS.containsKey(player.getUniqueId())) {
                    effects = EFFECTS.get(player.getUniqueId());
                    if(effects.isEmpty())continue;
                    for(Map.Entry<AbstractEffect,PlayerEffects> entry : effects.entrySet()){
                        PlayerEffects effect = entry.getValue();
                        if(effect.isFinished()){
                            effect.end(player);

                            effects.remove(entry.getKey());

                        }else {
                            effect.tick(player);
                        }
                    }
                }else{
                    initAllEffects(player);
                    EFFECTS.put(player.getUniqueId(),new HashMap<>());
                }
            }
        }
    }

}
