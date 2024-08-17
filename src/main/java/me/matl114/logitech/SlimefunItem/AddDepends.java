package me.matl114.logitech.SlimefunItem;

import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumWorkbench;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.AdvanceRecipeCrafter;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualCrafter;
import me.matl114.logitech.Utils.*;
import org.bukkit.Material;

/**
 * only enable when certain addon are enable
 */
public class AddDepends {
    public static void registerSlimefunItems(SlimefunAddon plugin){
        try{
            MANUAL_INF=
                    new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_INF, RecipeType.NULL, AddUtils.NULL_RECIPE.clone(),
                            1919,810, InfinityWorkbench.TYPE).register();
        }catch (Throwable e){
            Debug.logger("generate an exception while loading soft depends");
            e.printStackTrace();
        }
        try{

            MANUAL_MOB=new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_MOB, RecipeType.NULL, AddUtils.NULL_RECIPE.clone(),
                    1919,810, MobDataInfuser.TYPE).register();
        }catch (Throwable e){
            Debug.logger("generate an exception while loading soft depends");
            e.printStackTrace();
        }
        try{
            MANUAL_NTWBENCH=
                    new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_NTWBENCH, RecipeType.NULL, AddUtils.NULL_RECIPE.clone(),
                            1919,810, NetworkQuantumWorkbench.TYPE, NTWEP_WORKBENCH_TYPE).register();
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
    public static boolean hasInfiniteExpansion;
    public static boolean hasNetwork;
    public static boolean hasNetworkExpansion;
    public static RecipeType MOBDATA_TYPE;
    public static RecipeType INFINITYWORKBENCH_TYPE;
    public static RecipeType NTWQTWORKBENCH_TYPE;
    public static SlimefunItem NTWEP_WORKBENCH;
    public static RecipeType NTWEP_WORKBENCH_TYPE;
    public static RecipeType VOIDHARVEST   ;
    public static RecipeType SINGULARITY_CONSTRUCTOR;

    public static SlimefunItem MANUAL_INF;
    public static SlimefunItem MANUAL_MOB;
    public static SlimefunItem MANUAL_NTWBENCH;
    public static SlimefunItem AUTO_INF;
}
