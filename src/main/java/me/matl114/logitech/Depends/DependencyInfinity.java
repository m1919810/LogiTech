package me.matl114.logitech.Depends;

import io.github.mooy1.infinityexpansion.items.blocks.Blocks;
import io.github.mooy1.infinityexpansion.items.machines.Machines;
import io.github.mooy1.infinityexpansion.items.mobdata.MobData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.AddDepends;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.ReflectUtils;
import me.matl114.logitech.Utils.Settings;
import org.bukkit.Bukkit;

public class DependencyInfinity {
    public static void  init(){
        if(AddDepends.hasInfiniteExpansion){
            Debug.logger("已检测到无尽附属");
        }
    }
    static {
        AddDepends.hasInfiniteExpansion = Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion");
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
            try{
                AddDepends.VOIDHARVEST=(RecipeType) ReflectUtils.invokeGetRecursively(SlimefunItem.getByItem(Machines.VOID_HARVESTER), Settings.FIELD,"TYPE");
            }catch(Throwable e){
                Debug.logger("generate an exception while loading softDepends, don't worry, that's not a big deal");
                hasErr=true;
            }
            if(hasErr){
                AddDepends.hasInfiniteExpansion=false;
            }
        }

    }

}
