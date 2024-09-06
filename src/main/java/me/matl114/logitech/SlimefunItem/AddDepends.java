package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import jdk.jshell.execution.Util;
import me.matl114.logitech.SlimefunItem.Items.MyVanillaItem;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.AdvanceRecipeCrafter;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualCrafter;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.Utils.*;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * only enable when certain addon are enable
 */
public class AddDepends {
    public static void setup(SlimefunAddon plugin){
        AddDepends.registerSlimefunItems(plugin);
    }
    public static void registerSlimefunItems(SlimefunAddon plugin){
        try{
            if(hasInfiniteExpansion)
            MOBDATA_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.MOBDATA_MANUAL, BugCrafter.TYPE,
                    AddSlimefunItems.recipe(null,null,AddItem.BUG,AddItem.BUG,null,null,
                            null,AddItem.BUG,AddItem.LCRAFT,AddItem.LCRAFT,AddItem.BUG,null,
                            null,AddItem.LFIELD,"DATA_INFUSER","DATA_INFUSER",AddItem.LFIELD,null,
                            null,AddItem.LFIELD,"DATA_INFUSER","DATA_INFUSER",AddItem.LFIELD,null,
                            null,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,null
                            ),600_000,20_000,MOBDATA_TYPE)
                    .register();
        }catch (Throwable e){
            Debug.logger("AN ERROR OCCURED WHILE REGISTERING ITEM: MOBDATA_MANUAL,ITEM DISABLED");
            e.printStackTrace();
        }
        try{
            if(hasInfiniteExpansion)
            INFINITY_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.INFINITY_MANUAL,BugCrafter.TYPE,
                    AddSlimefunItems.recipe(null,null,null,null,null,null,
                            AddItem.ABSTRACT_INGOT,"REINFORCED_PLATE","REINFORCED_PLATE","REINFORCED_PLATE","REINFORCED_PLATE",AddItem.ABSTRACT_INGOT,
                            AddItem.ABSTRACT_INGOT,"MACHINE_PLATE","INFINITE_INGOT","INFINITE_INGOT","MACHINE_PLATE",AddItem.ABSTRACT_INGOT,
                            AddItem.ABSTRACT_INGOT,"MACHINE_PLATE","INFINITY_FORGE","INFINITY_FORGE","MACHINE_PLATE",AddItem.ABSTRACT_INGOT,
                            AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.LFIELD,AddItem.LFIELD,AddItem.LFIELD,AddItem.ABSTRACT_INGOT),
                    100_000_000,2_500_000,INFINITYWORKBENCH_TYPE)
                    .register();
        }catch (Throwable e){
            Debug.logger("AN ERROR OCCURED WHILE REGISTERING ITEM: INFINITY_MANUAL,ITEM DISABLED");
            e.printStackTrace();
        }
        try{
            if(hasNetwork)
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
            Debug.logger("AN ERROR OCCURED WHILE REGISTERING ITEM: NTWWORKBENCH_MANUAL,ITEM DISABLED");
            e.printStackTrace();
        }
        try{
            if(hasInfiniteExpansion)
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
            Debug.logger("AN ERROR OCCURED WHILE REGISTERING ITEM: INFINITY_AUTOCRAFT,ITEM DISABLED");
            e.printStackTrace();
        }
        try {
            if(hasNetwork)
                NTW_STORAGE_DISPLAY=new MyVanillaItem(AddGroups.SINGULARITY,SlimefunItem.getById("NTW_QUANTUM_STORAGE_8").getItem().clone(),"NTW_QUANTUM_STORAGE_DISPLAY",RecipeType.ENHANCED_CRAFTING_TABLE,
                     AddSlimefunItems.recipe(AddItem.STORAGE_SINGULARITY,"NTW_QUANTUM_STORAGE_1",AddItem.STORAGE_SINGULARITY,
                             "NTW_QUANTUM_STORAGE_1",AddItem.IOPORT,"NTW_QUANTUM_STORAGE_1",
                             AddItem.STORAGE_SINGULARITY,"NTW_QUANTUM_STORAGE_1",AddItem.STORAGE_SINGULARITY)   )
                        .setDisplayRecipes(
                                Utils.list(
                                        AddUtils.getInfoShow("&f机制 - &c终极合成",
                                                "&c特性 ",
                                                "&7终极合成是本附属某些终极机器中的机制",
                                                "&c具体说明请看\"版本与说明\"分类中的信息",
                                                "&7直白了说是让物品存储中的物品直接参与合成/进程",
                                                "&7从而打破槽位限制",
                                                "&7该物品是终极机器支持的物品之一",
                                                "&a建议:不推荐直接将量子存储放入终极机器,不方便且会有更高卡顿!"),
                                        null,
                                        AddUtils.getInfoShow("&7机制 -&c 物品存储",
                                                "&7本物品可以存储某种物品",
                                                "&7本物品的最大存储量为2147483647",
                                                "&7当该物品被放入终极机器的槽位中时,",
                                                "&7可以代理其内部存储的物品&e直接参与合成!",
                                                "&7产出的物品也可以&e直接进入内部存储的物品中!",
                                                "&e本物品是网络附属里的支持物品,直接置于地上即可使用"),null,
                                        AddUtils.getInfoShow("&7机制 -&c 报错",
                                                "&e如果网络版本不兼容或使用了错误的测试版,会造成报错",
                                                "&7如果使用量子存储进行合成时,出现合成不成功的情况",
                                                "&7很可能这是数据损坏的存储或者错误版本导致的存储不兼容",
                                                "&e请立刻将其移出槽位",
                                                "&7并判断是存储数据损坏问题还是网络版本问题",
                                                "&7若累计报错达到一定数目,将会禁用该兼容"),null
                                )
                        ).register();

        }catch (Throwable e){
                Debug.logger("AN ERROR OCCURED WHILE REGISTERING ITEM: NTW_STORAGE_DISPLAY,ITEM DISABLED");
                e.printStackTrace();
        }
    }
    public static boolean hasInfiniteExpansion=false;
    public static boolean hasNetwork=false;
    public static boolean hasNetworkExpansion=false;
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
    public static MyVanillaItem NTW_STORAGE_DISPLAY;
    public static  Class NETWORKSQUANTUMSTORAGE;
    public static NamespacedKey NTWQUANTUMKEY;
}
