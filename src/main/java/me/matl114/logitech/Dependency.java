package me.matl114.logitech;


import com.ytdd9527.networks.expansion.setup.ExpansionItemStacks;
import io.github.mooy1.infinityexpansion.items.blocks.Blocks;
import io.github.mooy1.infinityexpansion.items.machines.Machines;
import io.github.mooy1.infinityexpansion.items.mobdata.MobData;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumWorkbench;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.AddDepends;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.ReflectUtils;
import me.matl114.logitech.Utils.Settings;
import org.bukkit.Bukkit;

public class Dependency {

    static {
        AddDepends.hasInfiniteExpansion = Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion");
        AddDepends.hasNetwork = Bukkit.getPluginManager().isPluginEnabled("Networks");
        if(AddDepends.hasNetwork) {
            try{
                AddDepends.NTWEP_WORKBENCH= SlimefunItem.getByItem(ExpansionItemStacks.NETWORK_EXPANSION_WORKBENCH);
                AddDepends.NTWEP_WORKBENCH_TYPE=(RecipeType) ReflectUtils.invokeGetRecursively(AddDepends.NTWEP_WORKBENCH,Settings.FIELD,"TYPE");
                //NTWEP_WORKBENCH_TYPE= ExpansionItems.NETWORK_EXPANSION_WORKBENCH.TYPE;
                AddDepends.hasNetworkExpansion =true;
            }catch(Throwable e){
                Debug.logger("generate an exception while loading softDepends NTWEXPANSION_WORKBENCH, don't worry, that's not a big deal");
                AddDepends.hasNetworkExpansion =false;
            }
            try{
                AddDepends.NTWQTWORKBENCH_TYPE= NetworkQuantumWorkbench.TYPE;
            }catch(Throwable e){
                Debug.logger("generate an exception while loading softDepends NTW_QUANTUM_WORKBENCH, don't worry, that's not a big deal");

                AddDepends.hasNetwork =false;
            }
        }
        if(AddDepends.hasInfiniteExpansion){
            boolean hasErr=false;
            try{
                AddDepends.MOBDATA_TYPE=(RecipeType) ReflectUtils.invokeGetRecursively(SlimefunItem.getByItem(MobData.INFUSER), Settings.FIELD,"TYPE");
            }catch(Throwable e){
                Debug.logger("generate an exception while loading softDepends , don't worry, that's not a big deal");

                hasErr=true;
            }
            try{
                AddDepends.INFINITYWORKBENCH_TYPE=(RecipeType) ReflectUtils.invokeGetRecursively(SlimefunItem.getByItem(Blocks.INFINITY_FORGE), Settings.FIELD,"TYPE");
            }catch(Throwable e){
                Debug.logger("generate an exception while loading softDepends, don't worry, that's not a big deal");

                hasErr=true;
            }
            try{
                AddDepends.SINGULARITY_CONSTRUCTOR=(RecipeType) ReflectUtils.invokeGetRecursively(SlimefunItem.getByItem(Machines.SINGULARITY_CONSTRUCTOR), Settings.FIELD,"TYPE");
            }catch(Throwable e){
                Debug.logger("generate an exception while loading softDepends, don't worry, that's not a big deal");
                hasErr=true;
            }
            if(hasErr){
                AddDepends.hasInfiniteExpansion=false;
            }
        }

    }
    public static void init(){
        if(AddDepends.hasInfiniteExpansion){
            Debug.logger("已检测到无尽附属");
        }
        if(AddDepends.hasNetwork){
            Debug.logger("已检测到网络附属");
        }
        if(AddDepends.hasNetworkExpansion){
            Debug.logger("已检测到网络拓展");
        }
    }
    public static void setup(SlimefunAddon plugin){
        AddDepends.registerSlimefunItems(plugin);
    }
}
