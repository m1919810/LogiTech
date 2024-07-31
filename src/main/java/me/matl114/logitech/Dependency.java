package me.matl114.logitech;


import com.ytdd9527.networks.expansion.setup.ExpansionItems;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.SlimefunItem.AddDepends;
import me.matl114.logitech.Utils.Debug;
import org.bukkit.Bukkit;

public class Dependency {
    public static boolean hasInfiniteExpansion;
    public static boolean hasNetwork;
    public static boolean hasNetworkExpansion;
    static {
        hasInfiniteExpansion = Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion");
        hasNetwork = Bukkit.getPluginManager().isPluginEnabled("Networks");
        if(hasNetwork) {
            try{
                SlimefunItem a= ExpansionItems.NETWORK_EXPANSION_WORKBENCH;
                hasNetworkExpansion =true;
            }catch(Throwable e){
                hasNetworkExpansion =false;
                Debug.logger("Error while loading soft depend Network Expansion: ");
                e.printStackTrace();
                Debug.logger("已禁用网络拓展相关内容 ");
            }
        }

    }
    public static void init(){
        if(hasInfiniteExpansion){
            Debug.logger("已检测到无尽附属");
        }
        if(hasNetwork){
            Debug.logger("已检测到网络附属");
        }
        if(hasNetworkExpansion){
            Debug.logger("已检测到网络拓展");
        }
    }
    public static void setup(SlimefunAddon plugin){
        AddDepends.registerSlimefunItems(plugin);
    }
}
