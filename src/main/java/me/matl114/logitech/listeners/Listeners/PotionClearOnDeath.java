package me.matl114.logitech.listeners.Listeners;

import me.matl114.logitech.manager.EffectTickManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PotionClearOnDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        EffectTickManager.clearEffectsOnDeath(e);
    }
}
