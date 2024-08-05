package me.matl114.logitech;

import com.google.common.base.Charsets;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import me.matl114.logitech.Utils.Debug;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.reader.StreamReader;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;

public class ConfigLoader {
    public static void load(Plugin plugin) {
        ConfigLoader.plugin=plugin;
        init();
        //final File scAddonFile = new File(plugin.getDataFolder(), "language.yml");
        //copyFile(scAddonFile, "language");
        LANGUAGE=loadInternalConfig("language");   //new Config(plugin,"language.yml");
        InputStream st=plugin.getClass().getResourceAsStream("/language.yml");
    }
    public static Plugin plugin;
    public static Config LANGUAGE;
    public static File NULL_FILE;
    public static void init() {
        NULL_FILE=new File(plugin.getDataFolder(), "configure.yml");
    }
    private static void copyFile(File file, String name) {
        if (!file.exists()) {
            try {
                Files.copy(plugin.getClass().getResourceAsStream("/"+ name + ".yml"), file.toPath());
            } catch (IOException e) {
                Debug.logger("无法加载默认配置文件 " + name + ".yml, Error: " + e.getMessage());
            }
        }
    }
    private static Config loadInternalConfig(String name){
        FileConfiguration config = new YamlConfiguration();
        try{
            config.load((Reader)( new InputStreamReader(plugin.getClass().getResourceAsStream("/"+ name + ".yml"), Charsets.UTF_8)));
        }catch (Throwable e){
            Debug.logger("failed to load internal config " + name + ".yml, Error: " + e.getMessage());
            return null;
        }
        return new Config(null,config);
    }
}
