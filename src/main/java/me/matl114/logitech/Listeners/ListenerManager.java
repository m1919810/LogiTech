package me.matl114.logitech.Listeners;


import me.matl114.logitech.Listeners.Listeners.*;
import me.matl114.logitech.Unittest;
import me.matl114.logitech.Utils.Debug;
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
        register(PLAYER_QUIT_HANDLER);
        if(Debug.isTest(Unittest.SFDATA_TEST)){
            register(CHUNK_LOAD_TEST);
        }
        register(VANILLACRAFT_ALLOW);
        register(ENTITYFEAT_LISTENER);
    }
    public static Plugin plugin;
    public static PluginManager manager;
    public static void register(Listener listener){
        manager.registerEvents(listener,plugin);
    }
    public static final Listener testBlockBreakListener=new BlockBreakListener();
    public static final Listener EFFECT_CLEAR_MILK_LISTENER=new MilkListener();
    public static final Listener MULTIBLOCK_REDIRECT=new BlockOpenListener();
    public static final Listener PORTAL_REDIRECT=new PortalTeleport();
    public static final Listener POTION_CLEAR_DEATH=new PotionClearOnDeath();
    public static final Listener PLAYER_QUIT_HANDLER=new PlayerQuiteListener();
    public static final Listener CHUNK_LOAD_TEST=new ChunkTestListener();
    public static final Listener VANILLACRAFT_ALLOW=new CraftingListener();
    public static final Listener ENTITYFEAT_LISTENER=new SpawnerListener();
}
