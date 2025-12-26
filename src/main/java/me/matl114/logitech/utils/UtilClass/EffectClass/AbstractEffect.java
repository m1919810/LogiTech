package me.matl114.logitech.utils.UtilClass.EffectClass;

import me.matl114.logitech.manager.EffectTickManager;
import me.matl114.logitech.utils.AddUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public abstract class AbstractEffect {
    String id;

    public AbstractEffect(String name) {
        this.id = AddUtils.idDecorator(name);
    }

    public void initEffect(Player p) {

        removeEffect(p, 1);
    }

    public abstract void aquireEffect(Player p, int level);

    public abstract void removeEffect(Player p, int level);

    public abstract void tickEffect(Player p, int level);

    public String getEffectId() {
        return id;
    }

    public AbstractEffect reigster() {
        return EffectTickManager.registerEffect(this);
    }

    public boolean onDeathClear() {
        return true;
    }

    public void onDeathEvent(PlayerDeathEvent e, int level) {}
}
