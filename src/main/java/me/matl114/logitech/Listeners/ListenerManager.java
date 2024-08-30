package me.matl114.logitech.Listeners;


import me.matl114.logitech.Listeners.Listeners.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public class ListenerManager {
    public static void registerListeners(Plugin plugin,PluginManager manager){
        ListenerManager.plugin=plugin;
        ListenerManager.manager=manager;
      // register(testBlockBreakListener);
        register(EFFECT_CLEAR_MILK_LISTENER);
        register(MULTIBLOCK_REDIRECT);
        register(PORTAL_REDIRECT);
        register(POTION_CLEAR_DEATH);
        register(PLAYER_QUIT_HANDLER);
    }
    public static Plugin plugin;
    public static PluginManager manager;
    public static void register(Listener listener){
        manager.registerEvents(listener,plugin);
    }
    public static final Listener testBlockBreakListener=new BlockBreakListener();
    public static final Listener EFFECT_CLEAR_MILK_LISTENER=new MilkListener();
    public static final Listener MULTIBLOCK_REDIRECT=new BlockMenuRedirect();
    public static final Listener PORTAL_REDIRECT=new PortalTeleport();
    public static final Listener POTION_CLEAR_DEATH=new PotionClearOnDeath();
    public static final Listener PLAYER_QUIT_HANDLER=new PlayerQuiteListener();
}
