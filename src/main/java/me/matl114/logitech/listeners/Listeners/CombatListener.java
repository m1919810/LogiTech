package me.matl114.logitech.listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import java.util.HashSet;
import me.matl114.logitech.core.AddSlimefunItems;
import me.matl114.logitech.manager.Schedules;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class CombatListener implements Listener {
    HashSet<Material> AXE_MATERIAL = new HashSet<>() {
        {
            for (Material material : Material.values()) {
                if (material.toString().endsWith("_AXE")) {
                    add(material);
                }
            }
        }
    };
    HashSet<Material> SHIELD_MATERIAL = new HashSet<>() {
        {
            for (Material material : Material.values()) {
                if (material.toString().endsWith("SHIELD") && material.isItem()) {
                    add(material);
                }
            }
        }
    };

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAttackUsingShield(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (e.getDamager() instanceof LivingEntity entity) {
                EntityEquipment eequipment = entity.getEquipment();
                if (eequipment != null
                        && (AXE_MATERIAL.contains(eequipment.getItemInMainHand().getType())
                                || AXE_MATERIAL.contains(
                                        eequipment.getItemInOffHand().getType()))) {
                    ItemStack stackInUse = p.getItemInUse();
                    if (stackInUse != null
                            && SHIELD_MATERIAL.contains(stackInUse.getType())
                            && SlimefunItem.getByItem(stackInUse) == AddSlimefunItems.UNBREAKING_SHIELD) {
                        Material type = stackInUse.getType();
                        Schedules.launchSchedules(
                                () -> {
                                    if (p.hasCooldown(type) && p.getCooldown(type) > 0) {
                                        p.setCooldown(type, 0);
                                    }
                                },
                                0,
                                true,
                                0);
                    }
                }
            }
        }
    }
}
