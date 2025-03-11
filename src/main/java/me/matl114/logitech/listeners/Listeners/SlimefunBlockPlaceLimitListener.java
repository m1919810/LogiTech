package me.matl114.logitech.listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.MyAddon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.function.Consumer;

public class SlimefunBlockPlaceLimitListener implements Listener {
    static HashMap<SlimefunItem, Consumer<SlimefunBlockPlaceEvent>> eventHandlers = new HashMap<>();
    @EventHandler
    public void onBlockPlace(SlimefunBlockPlaceEvent event) {
        SlimefunItem item=event.getSlimefunItem();
        if(item.getAddon()== MyAddon.getInstance()){
            if(eventHandlers.containsKey(item)){
                eventHandlers.get(item).accept(event);
            }
        }
    }
    public static void registerBlockLimit(SlimefunItem item, Consumer<SlimefunBlockPlaceEvent> handler) {
        eventHandlers.put(item, handler);
    }
}
