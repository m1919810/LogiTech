package me.matl114.logitech.SlimefunItem;

import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumWorkbench;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.AdvanceRecipeCrafter;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.EMachine;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualCrafter;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualMachine;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.Utils.*;
import org.bukkit.Material;

/**
 * only enable when certain addon are enable
 */
public class AddDepends {
    public static void registerSlimefunItems(SlimefunAddon plugin){
        try{
            MOBDATA_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.MOBDATA_MANUAL, BugCrafter.TYPE,
                    AddSlimefunItems.recipe(null,null,AddItem.BUG,AddItem.BUG,null,null,
                            null,AddItem.BUG,AddItem.LCRAFT,AddItem.LCRAFT,AddItem.BUG,null,
                            null,AddItem.LFIELD,"DATA_INFUSER","DATA_INFUSER",AddItem.LFIELD,null,
                            null,AddItem.LFIELD,"DATA_INFUSER","DATA_INFUSER",AddItem.LFIELD,null,
                            null,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,null
                            ),600_000,20_000,MOBDATA_TYPE)
                    .register();
        }catch (Throwable e){
            Debug.logger("generate an exception while loading soft depends");
            e.printStackTrace();
        }
        try{

            INFINITY_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.INFINITY_MANUAL,BugCrafter.TYPE,
                    AddSlimefunItems.recipe(null,null,null,null,null,null,
                            AddItem.ABSTRACT_INGOT,"REINFORCED_PLATE","REINFORCED_PLATE","REINFORCED_PLATE","REINFORCED_PLATE",AddItem.ABSTRACT_INGOT,
                            AddItem.ABSTRACT_INGOT,"MACHINE_PLATE","INFINITE_INGOT","INFINITE_INGOT","MACHINE_PLATE",AddItem.ABSTRACT_INGOT,
                            AddItem.ABSTRACT_INGOT,"MACHINE_PLATE","INFINITY_FORGE","INFINITY_FORGE","MACHINE_PLATE",AddItem.ABSTRACT_INGOT,
                            AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.LFIELD,AddItem.LFIELD,AddItem.LFIELD,AddItem.ABSTRACT_INGOT),
                    100_000_000,2_500_000,INFINITYWORKBENCH_TYPE)
                    .register();
        }catch (Throwable e){
            Debug.logger("generate an exception while loading soft depends");
            e.printStackTrace();
        }
        try{
            NTWWORKBENCH_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.NTWWORKBENCH_MANUAL,BugCrafter.TYPE,
                    AddSlimefunItems.recipe(null,null,null,null,null,null,
                            null,"NTW_BRIDGE","ADVANCED_CIRCUIT_BOARD","ADVANCED_CIRCUIT_BOARD","NTW_BRIDGE",null,
                            null,"NTW_CONTROLLER","NTW_QUANTUM_WORKBENCH","NTW_QUANTUM_WORKBENCH","NTW_CONTROLLER",null,
                            null,AddItem.ABSTRACT_INGOT,"NTW_QUANTUM_WORKBENCH","NTW_QUANTUM_WORKBENCH",AddItem.ABSTRACT_INGOT,null,
                            null,AddItem.ABSTRACT_INGOT,AddItem.LCRAFT,AddItem.LDIGITIZER,AddItem.ABSTRACT_INGOT,null,
                            null,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,null),
                    0,0,NTWQTWORKBENCH_TYPE,NTWEP_WORKBENCH_TYPE)
                    .register();
        }catch (Throwable e){
            Debug.logger("generate an exception while loading soft depends");
            e.printStackTrace();
        }
        try{
            INFINITY_AUTOCRAFT =new AdvanceRecipeCrafter(AddGroups.BASIC, AddItem.INFINITY_AUTOCRAFT,BugCrafter.TYPE,
                  AddSlimefunItems.recipe(AddItem.LFIELD,"INFINITE_INGOT",AddItem.DIMENSIONAL_SHARD,AddItem.DIMENSIONAL_SHARD,"INFINITE_INGOT",AddItem.LFIELD,
                            "INFINITE_INGOT",AddItem.LCRAFT,"2VOID_INGOT","2VOID_INGOT",AddItem.LMOTOR,"INFINITE_INGOT",
                            AddItem.DIMENSIONAL_SHARD,"2VOID_INGOT","INFINITY_FORGE","INFINITY_FORGE","2VOID_INGOT",AddItem.DIMENSIONAL_SHARD,
                            AddItem.DIMENSIONAL_SHARD,"2VOID_INGOT","INFINITY_FORGE","INFINITY_FORGE","2VOID_INGOT",AddItem.DIMENSIONAL_SHARD,
                            "INFINITE_INGOT",AddItem.LSCHEDULER,"2VOID_INGOT","2VOID_INGOT",AddItem.LDIGITIZER,"INFINITE_INGOT",
                            AddItem.LFIELD,"INFINITE_INGOT",AddItem.DIMENSIONAL_SHARD,AddItem.DIMENSIONAL_SHARD,"INFINITE_INGOT",AddItem.LFIELD)
                    , Material.STONE,150_000,1_500_000,15,INFINITYWORKBENCH_TYPE)
                    .register();
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

    public static SlimefunItem MOBDATA_MANUAL;
    public static SlimefunItem INFINITY_MANUAL;
    public static SlimefunItem NTWWORKBENCH_MANUAL;
    public static SlimefunItem INFINITY_AUTOCRAFT;
}
