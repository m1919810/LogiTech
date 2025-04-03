package me.matl114.logitech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import lombok.Getter;
import me.matl114.logitech.core.AddGroups;
import me.matl114.logitech.core.AddItem;
import me.matl114.logitech.core.AddSlimefunItems;
import me.matl114.logitech.core.Cargo.SpaceStorage.StorageSpace;
import me.matl114.logitech.core.Cargo.Storages;
import me.matl114.logitech.core.Depends.DependencyInfinity;
import me.matl114.logitech.core.Depends.DependencyNetwork;
import me.matl114.logitech.core.Depends.SupportedPluginManager;
import me.matl114.logitech.core.Registries.AddDepends;
import me.matl114.logitech.core.Registries.CustomEffects;
import me.matl114.logitech.core.Registries.MultiBlockTypes;
import me.matl114.logitech.listeners.ListenerManager;
import me.matl114.logitech.listeners.ProtectionManager;
import me.matl114.logitech.manager.EquipmentFUManager;
import me.matl114.logitech.manager.RadiationRegionManager;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.CommandClass.LogitechMain;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.matlib.core.AddonInitialization;
import me.matl114.matlib.utils.command.commandGroup.AbstractMainCommand;
import net.guizhanss.guizhanlibplugin.updater.GuizhanUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MyAddon extends JavaPlugin implements SlimefunAddon {
    public static boolean testmod=false;
    public static boolean clearConfig=false;
    public static boolean testmode(){
        return testmod;
    }
    private static MyAddon instance;
    private static PluginManager manager;

    public static MyAddon getInstance() {
        return instance;
    }
    public static PluginManager getManager() {
        return manager;
    }
    public static String username;
    public static String repo;
    public static String branch;
    private static AbstractMainCommand command;
    private static AddonInitialization matlibInstance;
    @Getter
    public static SupportedPluginManager supportedPluginManager;
    static{
        username="m1919810";
        repo="LogiTech";
        branch="master";
    }
    @Override
    public void onEnable() {
        instance =this;
        if (!PaperLib.isPaper()) {
            getLogger().log(Level.WARNING, "#######################################################");
            getLogger().log(Level.WARNING, "");
            getLogger().log(Level.WARNING, "自 25/2/1 起 LogiTech");
            getLogger().log(Level.WARNING, "转为 Paper 插件, 你必须要使用 Paper");
            getLogger().log(Level.WARNING, "或其分支才可使用 LogiTech.");
            getLogger().log(Level.WARNING, "立即下载 Paper: https://papermc.io/downloads/paper");
            getLogger().log(Level.WARNING, "");
            getLogger().log(Level.WARNING, "#######################################################");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        matlibInstance=new AddonInitialization(this,"LOGITECH")
                .displayName("逻辑工艺")
                .onEnable()
                .cast();
        manager=getServer().getPluginManager();
        checkVersion();
        // 从 config.yml 中读取插件配置
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            tryUpdate();
        }
            // 你可以在这里添加自动更新功能
//        }if(cfg.getBoolean("options.test")||testmod) {
//            testmod = true;
//            Debug.logger("Addon is running on TEST MODE");
//        }
        ConfigLoader.load(this);
        Language.loadConfig(ConfigLoader.LANGUAGE);
        try{
            DependencyNetwork.init();
        }catch (Throwable e){
            Debug.logger("在加载软依赖 NETWORKS时出现错误! 出现版本不匹配的附属,禁用附属相应内容");
            Debug.logger(e);
        }
        try{
            DependencyInfinity.init();
        }catch (Throwable e){
            Debug.logger("在加载软依赖 INFINITY时出现错误! 出现版本不匹配的附属,禁用附属相应内容");
            Debug.logger(e);
        }
        try{
            supportedPluginManager=new SupportedPluginManager();
        }catch (Throwable e){
            Debug.logger("在加载插件兼容时出现错误");
            Debug.logger(e);
        }
        Debug.logger("软依赖检测完毕");
        AddGroups.registerGroups(this);
        Debug.logger("物品组加载完毕");
        Debug.logger("自定义物品加载完毕");
        //物品注册
        AddItem.registerItemStack();
        Debug.logger("物品模板加载完毕");
        //粘液物品注册
        AddSlimefunItems.registerSlimefunItems();
        Debug.logger("粘液物品注册完毕");
        //世界配置
        StorageSpace.setup();
        Debug.logger("空间存储世界配置完毕");
        //注册关于依赖的相关内容
        AddDepends.setup(this);
        Debug.logger("依赖注册完毕");
        Schedules.setupSchedules(this);
        Debug.logger("计划线程设立完毕");
        //载入监听器
        ListenerManager.registerListeners(getInstance(),getManager());

        //载入粘液保护模块
        ProtectionManager.registerProtection(getInstance(),getManager());

        Debug.logger("监听器注册完毕");
        //加载bs工具
        DataCache.setup();
        //注册存储类型
        Storages.setup();
        //注册多方块服务
        MultiBlockService.setup();
        //注册多方块类型
        MultiBlockTypes.setup();
        //加载自定义效果机制
        CustomEffects.setup();
        //加载辐射机制
        RadiationRegionManager.setup();
        //加载装备监听管理器
        new EquipmentFUManager();
        //加载配方工具
        CraftUtils.setup();
        //加载世界操作工具
        WorldUtils.setup();
        //加载Bukkit操作工具
        BukkitUtils.setup();
        //加载货运工具
        TransportUtils.setup();
        //加载容器impl工具
        ContainerUtils.setup();
        Debug.logger("指令注册完毕");
//        command = new AddonCommand(this);
//        command.register();
        command=new LogitechMain().registerCommand(this);
        //注册
        Debug.logger("附属特性注册完毕");
        //test

    }
    public void tryUpdate() {
        if ( getDescription().getVersion().startsWith("Build")) {
            GuizhanUpdater.start(this, getFile(), username, repo, branch);
        }
    }
    public void checkVersion(){
        if (!getServer().getPluginManager().isPluginEnabled("GuizhanLibPlugin")) {
            getLogger().log(Level.SEVERE, "本插件需要 鬼斩前置库插件(GuizhanLibPlugin) 才能运行!");
            getLogger().log(Level.SEVERE, "从此处下载: https://50l.cc/gzlib");
            getLogger().log(Level.SEVERE, "当出现该报错时,作者对一切后续的报错不负责");
            getServer().getPluginManager().disablePlugin(this);
            throw new RuntimeException("Dependency not found");
        }
//        try {
//            if( !Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_20) ){
//                getLogger().log(Level.SEVERE, "本插件需要在MC 1.20/1.20.x的版本运行");
//                getLogger().log(Level.SEVERE, "当出现该报错时,作者对一切后续的报错不负责");
//            }else{
//                Debug.logger("MC最低版本检测通过");
//            }
//        } catch (NoClassDefFoundError | NoSuchFieldError e) {
//            for (int i = 0; i < 20; i++) {
//                getLogger().severe("你需要更新 Slimefun4 才能进行版本检测！");
//            }
//        }


    }
    @Override
    public void onDisable() {
        matlibInstance.onDisable();
        // 禁用插件的逻辑...
        Schedules.onDisableSchedules(this);
        Bukkit.getScheduler().cancelTasks(this);
    }

    @Override
    public String getBugTrackerURL() {
        // 你可以在这里返回你的问题追踪器的网址，而不是 null
        return null;
    }
    public String getWikiURL(){
        return "https://github.com/m1919810/LogiTech/wiki/{0}";
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
