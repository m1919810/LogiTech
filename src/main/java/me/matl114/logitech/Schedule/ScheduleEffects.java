package me.matl114.logitech.Schedule;

import me.matl114.logitech.Schedule.PersistentEffects.AbstractEffect;
import me.matl114.logitech.Schedule.PersistentEffects.CustomEffects;
import me.matl114.logitech.Schedule.PersistentEffects.PlayerEffects;
import me.matl114.logitech.Utils.Debug;
import org.bukkit.Bukkit;
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
    private static final HashMap<UUID, ConcurrentHashMap<AbstractEffect,PlayerEffects>> EFFECTS = new HashMap<>();
    //when player login after server start ,they may contains effect attributes
    public static void initAllEffects(Player p) {
        int len=REGISTERED_EFFECTS.size();
        for(AbstractEffect effect : REGISTERED_EFFECTS) {
            effect.initEffect(p);
        }
    }
    //should be syc
    public static void clearEffect(Player p, AbstractEffect effect) {
         PlayerEffects eff= EFFECTS.get(p).get(effect);
         if(eff != null) {
             eff.finish();
         }
    }
    public static void clearAllEffects(Player p) {
        ConcurrentHashMap<AbstractEffect,PlayerEffects> effects = EFFECTS.get(p.getUniqueId());
        Set<AbstractEffect> effectSet = effects.keySet();
        for(AbstractEffect effect : effectSet) {
            PlayerEffects eff= effects.get(effect);
            if(eff!=null)
                eff.finish();
        }
    }
    public static void clearEffectsOnDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        ConcurrentHashMap<AbstractEffect,PlayerEffects> effects = EFFECTS.get(p.getUniqueId());
        Set<AbstractEffect> effectSet = effects.keySet();
        for(AbstractEffect effect : effectSet) {
            PlayerEffects eff= effects.get(effect);
            if(eff!=null){
                eff.getType().onDeathEvent(e);
                if(eff.getType().onDeathClear()){
                    eff.finish();
                }
            }
        }
    }

    public static boolean hasEffects(Player p,AbstractEffect effect) {
        return EFFECTS.get(p).containsKey(effect);
    }
    public static void addEffect(Player player, PlayerEffects effect){
        addEffect(player,effect,(player1 -> {return true;}));
    }
    public static boolean addEffect(Player player, PlayerEffects effect, Function<Player,Boolean> predicate) {

        if (EFFECTS.containsKey(player.getUniqueId())) {
            ConcurrentHashMap<AbstractEffect,PlayerEffects> effects = EFFECTS.get(player.getUniqueId());
            PlayerEffects old = effects.get(effect.getType());
            if(predicate.apply(player)){
                if(old==null) {
                    effects.put(effect.getType(), effect);
                }else {
                    old.extend(effect);
                }
                return true;
            }else {
                return false;
            }
        }else{

            ConcurrentHashMap<AbstractEffect,PlayerEffects> effects = new ConcurrentHashMap<>();
            if(predicate.apply(player)){
                effects.put(effect.getType(), effect);
                EFFECTS.put(player.getUniqueId(),effects);
                return true;
            }
            EFFECTS.put(player.getUniqueId(),effects);
            return false;
        }
    }
    public static void scheduleEffects(){
        for (Player player : Bukkit.getOnlinePlayers()){
            if (EFFECTS.containsKey(player.getUniqueId())) {
                ConcurrentHashMap<AbstractEffect,PlayerEffects> effects = EFFECTS.get(player.getUniqueId());

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
                EFFECTS.put(player.getUniqueId(),new ConcurrentHashMap<>());
            }
        }
    }
}
