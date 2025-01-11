package me.matl114.logitech.core.Depends;


import io.github.sefiraat.networks.slimefun.network.NetworkQuantumWorkbench;
import io.github.sefiraat.networks.utils.Keys;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.Registries.AddDepends;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.ReflectUtils;
import me.matl114.logitech.Utils.Settings;
import org.bukkit.Bukkit;

public class DependencyNetwork {

    static {
        AddDepends.hasNetwork = Bukkit.getPluginManager().isPluginEnabled("Networks");
        if(AddDepends.hasNetwork) {
            try{
                //load classes
                AddDepends.NETWORKSQUANTUMSTORAGE= NetworkQuantumWorkbench.class;
            }catch (Throwable e){
                Debug.logger("generate an exception while loading softDepends NTWQUANTUMWORKBENCHCLASS, don't worry, that's not a big deal");
                AddDepends.hasNetwork = false;
            }
            try{
                //load classes
                AddDepends.NTWQUANTUMKEY = Keys.QUANTUM_STORAGE_INSTANCE;
            }catch (Throwable e){
                Debug.logger("generate an exception while loading softDepends NTWQUANTUMKEY, don't worry, that's not a big deal");
                AddDepends.hasNetwork = false;
            }

            try{
                AddDepends.NTWEP_WORKBENCH=SlimefunItem.getById("NTW_EXPANSION_WORKBENCH");
                AddDepends.NTWEP_WORKBENCH_TYPE=(RecipeType) ReflectUtils.invokeGetRecursively(AddDepends.NTWEP_WORKBENCH,Settings.FIELD,"TYPE");
                AddDepends.hasNetworkExpansion =true;
            }catch (Throwable e1){
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

    }
    public static void init(){
        if(AddDepends.hasNetwork){
            Debug.logger("已检测到网络附属");
        }
        if(AddDepends.hasNetworkExpansion){
            Debug.logger("已检测到网络拓展");
        }
    }

}
