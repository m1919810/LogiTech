package me.matl114.logitech.listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.core.Items.Equipments.LaserGun;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class LaserGunOnHeadListener implements Listener {
    @EventHandler
    public void onPlayerSneakWithLaser(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack stack=player.getInventory().getItem(EquipmentSlot.HEAD);
        if(stack==null||stack.getType()!= Material.PLAYER_HEAD){
            return;
        }
        SlimefunItem item=SlimefunItem.getByItem(stack);
        if(item instanceof LaserGun lg){
            event.setCancelled(true);
            lg.onLaser(player,stack,true,false);
        }
    }
}
