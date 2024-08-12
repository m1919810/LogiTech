package me.matl114.logitech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.matl114.logitech.Listeners.ListenerManager;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.AddGroups;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.AddSlimefunItems;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockTypes;
import me.matl114.logitech.SlimefunItem.Storage.Storages;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;

public class MyAddon extends JavaPlugin implements SlimefunAddon {
    private static MyAddon instance;
    private static PluginManager manager;

    public static MyAddon getInstance() {
        return instance;
    }
    public static PluginManager getManager() {
        return manager;
    }
    @Override
    public void onEnable() {
        instance =this;
        manager=getServer().getPluginManager();
        // 从 config.yml 中读取插件配置
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // 你可以在这里添加自动更新功能
        }
        ConfigLoader.load(this);
        Language.loadConfig(ConfigLoader.LANGUAGE);
        Dependency.init();
        Debug.logger("依赖检测完毕");
        AddGroups.registerGroups(this);
        Debug.logger("物品组加载完毕");
        Debug.logger("自定义物品加载完毕");
        AddItem.registerItemStack();
        Debug.logger("物品模板加载完毕");
        AddSlimefunItems.registerSlimefunItems();
        Debug.logger("粘液物品注册完毕");
        //注册关于依赖的相关内容
        Dependency.setup(this);
        Debug.logger("依赖注册完毕");
        Schedules.setupSchedules(this);
        Debug.logger("计划线程设立完毕");
        ListenerManager.registerListeners(getInstance(),getManager());
        Debug.logger("监听器注册完毕");
        //注册存储类型
        Storages.setup();
        //注册多方块服务
        MultiBlockService.setup();
        //注册多方块类型
        MultiBlockTypes.setup();
        //加载配方工具
        CraftUtils.setup();

        //注册
        Debug.logger("附属特性注册完毕");
    }

    @Override
    public void onDisable() {
        // 禁用插件的逻辑...
        Schedules.onDisableSchedules(this);
    }

    @Override
    public String getBugTrackerURL() {
        // 你可以在这里返回你的问题追踪器的网址，而不是 null
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        /*
         * 你需要返回对你插件的引用。
         * 如果这是你插件的主类，只需要返回 "this" 即可。
         */
        return this;
    }

}
