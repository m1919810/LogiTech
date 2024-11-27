package me.matl114.logitech.SlimefunItem;

import com.google.common.collect.Multimap;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.SlimefunRegistry;
import io.github.thebusybiscuit.slimefun4.core.handlers.GlobalItemHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import jdk.jshell.execution.Util;
import me.matl114.logitech.ConfigLoader;
import me.matl114.logitech.Items.CustomHead;
import me.matl114.logitech.SlimefunItem.Items.MyVanillaItem;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.AdvanceRecipeCrafter;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualCrafter;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ConstItemStack;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ConstSlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

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
                            null,AddItem.BUG,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.BUG,null,
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
                            AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
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
                            null,"NTW_BRIDGE",AddItem.LFIELD,AddItem.LFIELD,"NTW_BRIDGE",null,
                            null,AddItem.ABSTRACT_INGOT,"NTW_QUANTUM_WORKBENCH","NTW_QUANTUM_WORKBENCH",AddItem.ABSTRACT_INGOT,null,
                            null,AddItem.ABSTRACT_INGOT,"NTW_QUANTUM_WORKBENCH","NTW_QUANTUM_WORKBENCH",AddItem.ABSTRACT_INGOT,null,
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
                  AddSlimefunItems.recipe(AddItem.LFIELD,AddItem.LFIELD,"INFINITE_INGOT","INFINITE_INGOT",AddItem.LFIELD,AddItem.LFIELD,
                          AddItem.LFIELD,AddItem.LCRAFT,"VOID_INGOT","VOID_INGOT",AddItem.LMOTOR,AddItem.LFIELD,
                          "INFINITE_INGOT","VOID_INGOT","INFINITY_FORGE","INFINITY_FORGE","VOID_INGOT","INFINITE_INGOT",
                          "INFINITE_INGOT","VOID_INGOT","INFINITY_FORGE","INFINITY_FORGE","VOID_INGOT","INFINITE_INGOT",
                          AddItem.LFIELD,AddItem.LMOTOR,"VOID_INGOT","VOID_INGOT",AddItem.LCRAFT,AddItem.LFIELD,
                            AddItem.LFIELD,AddItem.LFIELD,"INFINITE_INGOT","INFINITE_INGOT",AddItem.LFIELD,AddItem.LFIELD)
                    , Material.STONE,150_000,1_500_000,8,INFINITYWORKBENCH_TYPE)
                    .register();
        }catch (Throwable e){
            Debug.logger("AN ERROR OCCURED WHILE REGISTERING ITEM: INFINITY_AUTOCRAFT,ITEM DISABLED");
            e.printStackTrace();
        }
        try {
            if(hasNetwork)
                NTW_STORAGE_DISPLAY=new MyVanillaItem(AddGroups.SINGULARITY,SlimefunItem.getById("NTW_QUANTUM_STORAGE_8").getItem().clone(),"NTW_QUANTUM_STORAGE_DISPLAY",RecipeType.ENHANCED_CRAFTING_TABLE,
                     AddSlimefunItems.recipe(AddItem.ABSTRACT_INGOT,"NTW_QUANTUM_STORAGE_1",AddItem.ABSTRACT_INGOT,
                             AddItem.ABSTRACT_INGOT,AddItem.IOPORT,AddItem.ABSTRACT_INGOT,
                             AddItem.STORAGE_SINGULARITY,AddItem.ABSTRACT_INGOT,AddItem.STORAGE_SINGULARITY)   )
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
        try{
            if(hasInfiniteExpansion){
                SlimefunItem instance=SlimefunItem.getById("INFINITY_CROWN");
//                SlimefunItemStack new_infinity_helmet=new SlimefunItemStack("INFINITY_CROWN",CustomHead.INF_HELMET.getItem(),
//                        AddUtils.color("无尽头盔"));
                try{
                    Config cfg=(Config) ReflectUtils.invokeGetRecursively(Slimefun.getItemTextureService(),Settings.FIELD,"config");
                    cfg.setValue(instance.getId(),Integer.valueOf(0));
                }catch (Throwable e){

                }
                SlimefunItemStack it= (SlimefunItemStack) ReflectUtils.invokeGetRecursively(instance,Settings.FIELD,"itemStackTemplate");
                Object locked=ReflectUtils.invokeGetRecursively(it,Settings.FIELD,"locked");
                ReflectUtils.invokeSetRecursively(it,"locked",Boolean.valueOf(false));
                it.setType(Material.PLAYER_HEAD);
                ItemMeta s= it.getItemMeta();
                if(s instanceof SkullMeta smt){
                    smt.setOwnerProfile(((SkullMeta)CustomHead.INF_HELMET.getItem().getItemMeta()).getOwnerProfile());
                }
                s.addAttributeModifier(Attribute.GENERIC_ARMOR,new AttributeModifier(UUID.nameUUIDFromBytes(new byte[]{1,1,4,5}),"logitech_armor",6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                s.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,new AttributeModifier(UUID.nameUUIDFromBytes(new byte[]{1,9,1,9}),"logitech_armor_toughness",6.0, AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.HEAD));
                s.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,new AttributeModifier(UUID.nameUUIDFromBytes(new byte[]{8,1,0,0}),"logitech_knockback_resistence",0.2, AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.HEAD  ));
                s.setCustomModelData(null);
                s.setDisplayName(AddUtils.resolveColor( AddUtils.color("无尽头盔")));
                it.setItemMeta(s);
                ReflectUtils.invokeSetRecursively(it,"locked",locked);
                ReflectUtils.invokeSetRecursively(instance,"itemStackTemplate",new ConstSlimefunItemStack(it));
                ReflectUtils.invokeSetRecursively(instance,"recipeOutput",new ConstSlimefunItemStack(it));
                try{
                    ItemHandler handler=AddHandlers.stopPlacementHandler;
                    ((Map)ReflectUtils.invokeGetRecursively(instance,Settings.FIELD,"itemHandlers")).put(handler.getIdentifier(),handler);
                    if (handler instanceof GlobalItemHandler) {
                        SlimefunRegistry registry = Slimefun.getRegistry();
                        registry.getGlobalItemHandlers(handler.getIdentifier()).add(handler);
                    }
                    //TODO figure out why it doesn't work
                }catch (Throwable e){

                }

            }
        }catch (Throwable e){
            Debug.logger("AN ERROR OCCURED WHILE CHANGING ITEM: INFINITY_CROW,CHANGE DISABLED");
            e.printStackTrace();
        }
        try{
            if(hasInfiniteExpansion){
                Class infinityMobDataClass=SlimefunItem.getById("MOB_SIMULATION_CHAMBER").getClass();
                Constructor constructor=ReflectUtils.getSuitableConstructor(infinityMobDataClass,
                       ItemGroup.class,SlimefunItemStack.class,RecipeType.class,ItemStack[].class,int.class,int.class);
                AddItem.INF_MOBSIMULATION.setItemMeta(  AddUtils.addLore(AddItem.INF_MOBSIMULATION, AddUtils.speedDisplay(64),AddUtils.energyPerSecond(800)).getItemMeta());
                INFINITY_MOBSIMNULATOR=(SlimefunItem) constructor.newInstance(AddGroups.BASIC,AddItem.INF_MOBSIMULATION,INFINITYWORKBENCH_TYPE,
                        AddSlimefunItems.recipe(null,"MACHINE_PLATE","MACHINE_PLATE","MACHINE_PLATE","MACHINE_PLATE",null,
                                null,"VOID_INGOT",AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,"VOID_INGOT",null,
                                null,"VOID_INGOT","MOB_SIMULATION_CHAMBER","MOB_SIMULATION_CHAMBER","VOID_INGOT",null,
                                null,"VOID_INGOT","MOB_SIMULATION_CHAMBER","MOB_SIMULATION_CHAMBER","VOID_INGOT",null,
                                null,"INFINITE_INGOT",AddItem.LENGINE,AddItem.LENGINE,"INFINITE_INGOT",null,
                                "INFINITE_INGOT","INFINITE_INGOT","INFINITE_INGOT","INFINITE_INGOT","INFINITE_INGOT","INFINITE_INGOT"),800,1);

                INFINITY_MOBSIMNULATOR.register(plugin);
            }
        }catch (Throwable e){

        }
        try{
            if(hasInfiniteExpansion){
                Class infinityGeoMiner=SlimefunItem.getById("GEO_QUARRY").getClass();
                Constructor constructor=ReflectUtils.getSuitableConstructor(infinityGeoMiner,
                        ItemGroup.class,SlimefunItemStack.class,RecipeType.class,ItemStack[].class);
                AddItem.INF_GEOQUARRY.setItemMeta( AddUtils.addLore(  AddItem.INF_GEOQUARRY,AddUtils.speedDisplay(64),AddUtils.energyPerSecond(4500)).getItemMeta());
                INFINITY_GEOQURRY=(SlimefunItem) constructor.newInstance(AddGroups.BASIC,AddItem.INF_GEOQUARRY,INFINITYWORKBENCH_TYPE,
                        AddSlimefunItems.recipe(AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                                "MYTHRIL","VOID_INGOT","VOID_INGOT","VOID_INGOT","VOID_INGOT","MYTHRIL",
                                "MYTHRIL","VOID_INGOT","INFINITE_MACHINE_CIRCUIT","INFINITE_MACHINE_CIRCUIT","VOID_INGOT","MYTHRIL",
                                "MYTHRIL","VOID_INGOT","GEO_QUARRY","GEO_QUARRY","VOID_INGOT","MYTHRIL",
                                "MYTHRIL","VOID_INGOT","VOID_INGOT","VOID_INGOT","VOID_INGOT","MYTHRIL",
                                AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT));
                ReflectUtils.invokeSetRecursively(INFINITY_GEOQURRY,"ticksPerOutput",6);
                ReflectUtils.invokeSetRecursively(INFINITY_GEOQURRY,"energyPerTick",4500);
                INFINITY_GEOQURRY.register(plugin);
            }
        }catch (Throwable e){

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
    public static SlimefunItem INFINITY_MOBSIMNULATOR;
    public static SlimefunItem INFINITY_GEOQURRY;
    public static MyVanillaItem NTW_STORAGE_DISPLAY;
    public static  Class NETWORKSQUANTUMSTORAGE;
    public static NamespacedKey NTWQUANTUMKEY;
}
