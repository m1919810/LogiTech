package me.matl114.logitech.Schedule;

import me.matl114.logitech.Schedule.PersistentEffects.AbstractEffect;
import me.matl114.logitech.Schedule.PersistentEffects.CustomEffects;
import me.matl114.logitech.Schedule.PersistentEffects.PlayerEffects;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

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
    public static final HashMap<UUID, List<PlayerEffects>> EFFECTS = new HashMap<>();
    //when player login after server start ,they may contains effect attributes
    public static void initAllEffects(Player p) {
        int len=REGISTERED_EFFECTS.size();
        for(AbstractEffect effect : REGISTERED_EFFECTS) {
            effect.initEffect(p);
        }
    }
    public static void clearAllEffects(Player p) {
        List<PlayerEffects> effects = EFFECTS.get(p.getUniqueId());
        int len=effects.size();
        for(int i=0;i<len;i++) {
            effects.get(i).end(p);
        }
        effects.clear();
    }
    public static void addEffect(Player player, PlayerEffects effect) {
        if (EFFECTS.containsKey(player.getUniqueId())) {
            List<PlayerEffects> effects= EFFECTS.get(player.getUniqueId());
            String id=effect.getEffectId();
            for(int i=0;i<effects.size();i++) {
                if (effects.get(i).getEffectId().equals(id)) {
                    effects.get(i).extendTime(effect.getLeftTime());
                    return;
                }
            }
            effects.add(effect);
        }else{

            EFFECTS.put(player.getUniqueId(),new ArrayList<>(){{add(effect);}});
        }
    }
    public static void scheduleEffects(){
        for (Player player : Bukkit.getOnlinePlayers()){
            if (EFFECTS.containsKey(player.getUniqueId())) {
                List<PlayerEffects> effects = EFFECTS.get(player.getUniqueId());
                int len=effects.size();
                Iterator<PlayerEffects> iterator = effects.iterator();
                while (iterator.hasNext()){
                    PlayerEffects effect = iterator.next();
                    if(effect.isFinished()){
                        effect.end(player);
                        iterator.remove();
                    }else {
                        effect.tick(player);
                    }
                }
            }else{

                initAllEffects(player);
                EFFECTS.put(player.getUniqueId(),new ArrayList<>());
            }
        }
    }
}
