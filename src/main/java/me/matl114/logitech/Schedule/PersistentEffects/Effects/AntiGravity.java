package me.matl114.logitech.Schedule.PersistentEffects.Effects;

import me.matl114.logitech.Schedule.PersistentEffects.AbstractEffect;
import org.bukkit.entity.Player;

public class AntiGravity extends AbstractEffect {

    public AntiGravity() {
        super("ANTIGRAVITY");
    }
    public void removeEffect(Player p,int level) {
        p.setGravity(true);

        p.setCustomNameVisible(true);
    }
    public void tickEffect(Player p,int level) {

    }
    public void aquireEffect(Player p,int level) {
        p.setGravity(false);


    }
}
