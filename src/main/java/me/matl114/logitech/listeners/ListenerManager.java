package me.matl114.logitech.listeners;


import me.matl114.logitech.listeners.Listeners.*;
import me.matl114.logitech.core.Cargo.SpaceStorage.StorageSpace;
import me.matl114.logitech.Unittest;
import me.matl114.logitech.utils.Debug;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    public static void registerListeners(Plugin plugin,PluginManager manager){
        ListenerManager.plugin=plugin;
        ListenerManager.manager=manager;
       //register(testBlockBreakListener);
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
        register(LASER_ON_HEAD_LISTENER);
        register(SUPERSPONGE_DRY_LISTENER);
        if(StorageSpace.ENABLED){
            register(STORAGESPACE_PROTECTION);
        }
        register(BLOCKMENU_PROTECT_LISTENER);
        register(SLIMEFUN_BLOCKLIMIT_LISTENER);
        register(TICKERBLOCK_PLACE_LISTENER);
        register(MULTIBLOCK_BREAK_LISTENER);
        register(SMITH_INTERFACE_LISTENER);
        register(COMBAT_LISTENER);
        register(HYP_LISTENER);
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
    public static final Listener LASER_ON_HEAD_LISTENER=new LaserGunOnHeadListener();
    public static final Listener SUPERSPONGE_DRY_LISTENER=new SuperSpongeDryListener();
    public static final Listener STORAGESPACE_PROTECTION=new StorageWorldListener();
    public static final Listener BLOCKMENU_PROTECT_LISTENER=new BlockMenuClickProtectListener();
    public static final Listener SLIMEFUN_BLOCKLIMIT_LISTENER=new SlimefunBlockPlaceLimitListener();
    public static final Listener TICKERBLOCK_PLACE_LISTENER=new TickBlockListener();
    public static final Listener MULTIBLOCK_BREAK_LISTENER=new MultiBlockVanillaPartListener();
    public static final Listener SMITH_INTERFACE_LISTENER=new SmithInterfaceListener();
    public static final Listener COMBAT_LISTENER=new CombatListener();
    public static final Listener HYP_LISTENER = new HyperLinkListener();
}
