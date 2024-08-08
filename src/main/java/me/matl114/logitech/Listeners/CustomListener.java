package me.matl114.logitech.Listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CustomListener implements Listener {
    public CustomListener() {

    }
    public CustomListener register() {
        ListenerManager.register(this);
        return this;
    }
}
