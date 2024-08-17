package me.matl114.logitech.SlimefunItem;

import com.ytdd9527.networks.expansion.core.item.machine.manual.ExpansionWorkbench;
import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.items.machines.Machines;
import io.github.mooy1.infinityexpansion.items.mobdata.MobData;
import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumWorkbench;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Dependency;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.AdvanceRecipeCrafter;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualCrafter;
import me.matl114.logitech.Utils.*;
import org.bukkit.Material;

/**
 * only enable when certain addon are enable
 */
public class AddDepends {
    public static void registerSlimefunItems(SlimefunAddon plugin){

    }
    public static SlimefunItem MANUAL_INF;
    public static SlimefunItem MANUAL_MOB;
    public static SlimefunItem MANUAL_NTWBENCH;
    public static SlimefunItem AUTO_INF;
    static{
        try{
            MANUAL_INF=
                    new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_INF, RecipeType.NULL, AddUtils.NULL_RECIPE.clone(),
                            1919,810, InfinityWorkbench.TYPE).register();
        }catch (Throwable e){
            Debug.logger("generate an exception while loading soft depends");
            e.printStackTrace();
        }
        try{
            RecipeType mobInfuserType= (RecipeType) ReflectUtils.invokeRecursively(SlimefunItem.getByItem(MobData.INFUSER), Settings.FIELD,"TYPE");
            MANUAL_MOB=new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_MOB, RecipeType.NULL, AddUtils.NULL_RECIPE.clone(),
                    1919,810, MobDataInfuser.TYPE).register();
        }catch (Throwable e){
            Debug.logger("generate an exception while loading soft depends");
            e.printStackTrace();
        }
        try{
            MANUAL_NTWBENCH=
                    new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_NTWBENCH, RecipeType.NULL, AddUtils.NULL_RECIPE.clone(),
                            1919,810, NetworkQuantumWorkbench.TYPE,Dependency.hasNetworkExpansion? ExpansionWorkbench.TYPE:null).register();
        }catch (Throwable e){
            Debug.logger("generate an exception while loading soft depends");
            e.printStackTrace();
        }
        try{
            AUTO_INF=
                    new AdvanceRecipeCrafter(AddGroups.MATERIAL, AddItem.AUTO_INF, RecipeType.NULL, AddUtils.NULL_RECIPE.clone()
                            , Material.RESPAWN_ANCHOR,1919,810,8, InfinityWorkbench.TYPE).register();
        }catch (Throwable e){
            Debug.logger("generate an exception while loading soft depends");
            e.printStackTrace();
        }
    }
}
