


package me.matl114.logitech.Listeners.Listeners;

import me.matl114.logitech.manager.EffectTickManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class MilkListener implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();

        if (e.getItem().getType() == Material.MILK_BUCKET) {
            EffectTickManager.clearAllEffects(e.getPlayer());
        }
    }
}
