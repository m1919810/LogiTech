package me.matl114.logitech.Schedule.PersistentEffects;

import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CustomEffects {
    public static void registerEffect(){

    }
    public static AbstractEffect ANTI_GRAVITY= new AbstractEffect("ANTIGRAVITY") {
        public void removeEffect(Player p,int level) {
            p.setGravity(true);
            p.setCustomNameVisible(true);
        }
        public void tickEffect(Player p,int level) {
        }
        public void aquireEffect(Player p,int level) {
            p.setGravity(false);
        }
    };
    public static AbstractEffect SOLAR_BURN= new AbstractEffect("SOLAR_BURN") {
        public void removeEffect(Player p,int level) {
        }
        public void tickEffect(Player p,int level) {
            p.addPotionEffect(new PotionEffect( PotionEffectType.DARKNESS,140,1,false ),true);
            p.addPotionEffect(new PotionEffect( PotionEffectType.WITHER,140,1,false ),true);
            p.setFireTicks(200);
            p.damage(level==1?1:50);
        }
        public void aquireEffect(Player p,int level) {
        }
        public void onDeathEvent(PlayerDeathEvent e) {
            e.setDeathMessage(AddUtils.resolveColor( "%s &6在超新星的烈焰中化为灰烬".formatted(e.getEntity().getName())));
        }
    };
}
