package me.matl114.logitech;

import com.google.common.base.Charsets;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

public class ConfigLoader {
    public static boolean TESTMODE = MyAddon.testmode();
    public static void load(Plugin plugin) {
        ConfigLoader.plugin=plugin;
        init();
        //final File scAddonFile = new File(plugin.getDataFolder(), "language.yml");
        //copyFile(scAddonFile, "language");
        INNERCONFIG=loadInternalConfig("config");
        if(INNERCONFIG.getBoolean("options.test")) {
            MyAddon.testmod=true;
            TESTMODE=true;
            Debug.debug("Addon is running on TEST MODE");
        }
        LANGUAGE=loadInternalConfig("language");   //new Config(plugin,"language.yml");
        MACHINES=loadExternalConfig("machines");
        INNER_MACHINES=loadInternalConfig("addon-machines");
    }
    public static Plugin plugin;
    public static Config INNERCONFIG;
    public static Config LANGUAGE;
    public static Config MACHINES;
    public static Config INNER_MACHINES;
    public static File NULL_FILE;
    public static void init() {
        NULL_FILE=new File(plugin.getDataFolder(), "configure.yml");
    }
    public static void copyFile(File file, String name) {
        if(TESTMODE){
            try{
                Files.delete(file.toPath());
            }catch(Throwable e){
                Debug.logger("[TEST MODE] FAILED TO DELETE FILE: "+file.getAbsolutePath());
            }
        }
        if (!file.exists()) {
            try {
                Files.copy(plugin.getClass().getResourceAsStream("/"+ name + ".yml"), file.toPath());
            } catch (Throwable e) {
                Debug.logger("创建配置文件时找不到相关默认配置文件,即将生成空文件");
                try{
                    Files.createDirectories(file.toPath().getParent());
                    Files.createFile(file.toPath());
                }catch (IOException e1){
                    Debug.logger("创建空配置文件失败!");
                }
            }

        }
    }
    public static Config loadInternalConfig(String name){
        FileConfiguration config = new YamlConfiguration();
        try{
            config.load((Reader)( new InputStreamReader(plugin.getClass().getResourceAsStream("/"+ name + ".yml"), Charsets.UTF_8)));
        }catch (Throwable e){
            Debug.logger("failed to load internal config " + name + ".yml, Error: " + e.getMessage());
            return null;
        }
        return new Config(null,config);
    }
    public static Config loadExternalConfig(String name){
        FileConfiguration config = new YamlConfiguration();
        final File cfgFile = new File(plugin.getDataFolder(), "%s.yml".formatted(name));
        copyFile(cfgFile, name);
        return new Config(plugin, "%s.yml".formatted(name));
    }





}
