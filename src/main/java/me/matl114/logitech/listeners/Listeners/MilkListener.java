package me.matl114.logitech.listeners.Listeners;

import me.matl114.logitech.manager.EffectTickManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class MilkListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.MILK_BUCKET) {
            EffectTickManager.clearAllEffects(e.getPlayer());
        }
    }
}
