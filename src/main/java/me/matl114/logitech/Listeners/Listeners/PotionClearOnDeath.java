package me.matl114.logitech.Listeners.Listeners;

import me.matl114.logitech.Schedule.PersistentEffects.PlayerEffects;
import me.matl114.logitech.Schedule.ScheduleEffects;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.UUID;

public class PotionClearOnDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        ScheduleEffects.clearEffectsOnDeath(e);
    }
}
