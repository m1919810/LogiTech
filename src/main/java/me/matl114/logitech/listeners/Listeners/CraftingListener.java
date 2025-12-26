package me.matl114.logitech.listeners.Listeners;

import java.util.Locale;
import me.matl114.logitech.MyAddon;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ShapedRecipe;

public class CraftingListener implements Listener {
    public static String AddonName = MyAddon.getInstance().getName().toLowerCase(Locale.ROOT);

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onCraft(CraftItemEvent e) {
        if (e.getRecipe() instanceof ShapedRecipe sr) {
            NamespacedKey key = sr.getKey();
            if (AddonName.equals(key.getNamespace())) {
                e.setResult(Event.Result.ALLOW);
                e.getInventory().setResult(sr.getResult());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPrepareCraft(PrepareItemCraftEvent e) {
        if (e.getRecipe() instanceof ShapedRecipe sr) {
            NamespacedKey key = sr.getKey();
            if (AddonName.equals(key.getNamespace())) {
                e.getInventory().setResult(sr.getResult());
            }
        }
    }
}
