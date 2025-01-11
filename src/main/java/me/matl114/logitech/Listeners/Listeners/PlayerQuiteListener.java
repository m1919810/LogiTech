package me.matl114.logitech.Listeners.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.function.Consumer;

public class PlayerQuiteListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        for (Consumer<Player> handler:handlers){
            handler.accept(p);
        }
    }
    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Player p = e.getPlayer();
        for (Consumer<Player> handler:handlers){
            handler.accept(p);
        }
    }
    static HashSet<Consumer<Player>> handlers = new HashSet<>();
    public static void addHandler(Consumer<Player> handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
    }
}
