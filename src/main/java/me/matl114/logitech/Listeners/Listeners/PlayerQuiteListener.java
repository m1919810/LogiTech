package me.matl114.logitech.Listeners.Listeners;

import me.matl114.logitech.Schedule.Task;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.function.Consumer;

public class PlayerQuiteListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        for (Consumer<PlayerQuitEvent> handler:handlers){
            handler.accept(e);
        }
    }
    static HashSet<Consumer<PlayerQuitEvent>> handlers = new HashSet<>();
    public static void addHandler(Consumer<PlayerQuitEvent> handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
    }
}
