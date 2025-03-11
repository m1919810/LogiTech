package me.matl114.logitech.Listeners;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.ProtectionModule;
import me.matl114.logitech.Listeners.ProtectionModule.StorageWorldProtection;
import me.matl114.logitech.manager.PostSetupTasks;
import me.matl114.logitech.core.Cargo.SpaceStorage.StorageSpace;
import me.matl114.logitech.utils.Debug;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ProtectionManager {
    public static Plugin plugin;
    public static PluginManager manager;
    public static void registerProtection(Plugin plugin,PluginManager manager) {
        ProtectionManager.plugin = plugin;
        ProtectionManager. manager = manager;
        if(StorageSpace.ENABLED){
            register(STORAGESPACE_PROTECTION_MODULE);
        }
    }
    public static void register(ProtectionModule module) {
        PostSetupTasks.addPostRegisterTask(()->{
            try {
                Slimefun.getProtectionManager().registerModule(manager, plugin.getName(), (pl) -> module);
                Debug.logger("成功注册粘液保护模块 %s".formatted(module.getName()));
            }catch (Throwable e) {
                Debug.logger("LOADING PROTECTION MODULE FAILED %s".formatted(module.getName()));
                e.printStackTrace();
            }
        });
    }
    public static ProtectionModule STORAGESPACE_PROTECTION_MODULE=new StorageWorldProtection();
}
