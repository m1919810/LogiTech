package me.matl114.logitech.Listeners;


import me.matl114.logitech.Listeners.Listeners.BlockMenuRedirect;
import me.matl114.logitech.Listeners.Listeners.MilkListener;
import me.matl114.logitech.Listeners.Listeners.PortalTeleport;
import me.matl114.logitech.Listeners.Listeners.PotionClearOnDeath;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    public static void registerListeners(Plugin plugin,PluginManager manager){
        ListenerManager.plugin=plugin;
        ListenerManager.manager=manager;
      // register(testBlockBreakListener);
        register(EFFECT_CLEAR_MILK_LISTENER);
        register(MULTIBLOCK_REDIRECT);
        register(PORTAL_REDIRECT);
        register(POTION_CLEAR_DEATH);
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
}
