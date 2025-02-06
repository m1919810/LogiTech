package me.matl114.logitech.core;

import com.google.common.base.Preconditions;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemDropHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.Language;
import me.matl114.logitech.Manager.EquipmentFUManager;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Manager.RadiationRegionManager;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.Utils.UtilClass.EquipClass.EquipmentFU;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.MultiCraftingOperation;
import me.matl114.logitech.core.Blocks.*;
import me.matl114.logitech.core.Blocks.MultiBlock.*;
import me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop.*;
import me.matl114.logitech.core.Items.Abstracts.*;
import me.matl114.logitech.core.Items.Equipments.DisplayUseTrimmerLogicLate;
import me.matl114.logitech.core.Items.Equipments.EquipmentFUItem;
import me.matl114.logitech.core.Items.SpecialItems.*;
import me.matl114.logitech.core.Registries.*;
import me.matl114.logitech.core.Blocks.MultiBlockCore.MultiPart;
import me.matl114.logitech.core.Cargo.CargoMachine.*;
import me.matl114.logitech.core.Cargo.SpaceStorage.SpaceStorageCard;
import me.matl114.logitech.core.Cargo.SpaceStorage.SpaceTower;
import me.matl114.logitech.core.Cargo.SpaceStorage.SpaceTowerFrame;
import me.matl114.logitech.core.Cargo.StorageMachines.IOPort;
import me.matl114.logitech.core.Cargo.StorageMachines.Storage;
import me.matl114.logitech.core.Cargo.Transportation.*;
import me.matl114.logitech.core.Cargo.WorkBench.ChipCopier;
import me.matl114.logitech.core.Cargo.Config.ChipCard;
import me.matl114.logitech.core.Cargo.Config.ChipCardCode;
import me.matl114.logitech.core.Cargo.Config.ConfigCard;
import me.matl114.logitech.core.Cargo.WorkBench.CargoConfigurator;
import me.matl114.logitech.core.Cargo.WorkBench.ChipBiConsumer;
import me.matl114.logitech.core.Cargo.WorkBench.ChipConsumer;
import me.matl114.logitech.core.Items.Equipments.LaserGun;
import me.matl114.logitech.core.Items.Equipments.TrackingArrowLauncher;
import me.matl114.logitech.core.Machines.AutoMachines.*;
import me.matl114.logitech.core.Machines.Electrics.*;
import me.matl114.logitech.core.Machines.ManualMachines.*;
import me.matl114.logitech.core.Machines.SpecialMachines.*;
import me.matl114.logitech.core.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.core.Cargo.StorageMachines.InputPort;
import me.matl114.logitech.core.Cargo.StorageMachines.OutputPort;
import me.matl114.logitech.core.Cargo.TestStorageUnit;
import me.matl114.logitech.core.Machines.WorkBenchs.EWorkBench;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.Algorithms.PairList;
import me.matl114.logitech.Utils.UtilClass.CommandClass.CommandShell;
import me.matl114.logitech.Utils.UtilClass.FunctionalClass.AsyncResultRunnable;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.MenuClass.MenuFactory;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.matlib.Implements.Slimefun.core.CustomRecipeType;
import me.matl114.matlib.Utils.Algorithm.InitializeSafeProvider;
import me.matl114.matlib.Utils.Algorithm.Triplet;
import me.matl114.matlib.Utils.Inventory.CleanItemStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SpongeAbsorbEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.potion.PotionEffectType;

import static io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType.*;
import static me.matl114.logitech.core.AddGroups.*;
import static me.matl114.logitech.Utils.AddUtils.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * register main
 */
@SuppressWarnings("all")
public class AddSlimefunItems {
    public static void registerSlimefunItems() {
        Debug.logger("注册附属物品...");
        Debug.logger("注册附属机器...");
        CRAFTTYPE_MANUAL_RECIPETYPE.put(CRAFT_MANUAL,BukkitUtils.VANILLA_CRAFTTABLE);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(ENHANCED_CRAFT_MANUAL,ENHANCED_CRAFTING_TABLE);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(MAGIC_WORKBENCH_MANUAL,MAGIC_WORKBENCH);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(ANCIENT_ALTAR_MANUAL,ANCIENT_ALTAR);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(ARMOR_FORGE_MANUAL,ARMOR_FORGE);
        if(TYPE){
            FINAL_STACKMACHINE.setRecipe(LAZY_STACKMACHINE);
            FINAL_STACKMACHINE.setRecipeType(ENHANCED_CRAFTING_TABLE);
            FINAL_STACKMGENERATOR.setRecipe(LAZY_STACKMGENERATOR);
            FINAL_STACKMGENERATOR.setRecipeType(ENHANCED_CRAFTING_TABLE);
        }
    }
    public static final ItemStack[] LAZY_RECIPE=new ItemStack[]{null,null,null,null,new ItemStack(Material.DIRT),null,null,null,null};
    public static final ItemStack[] LAZY_STACKMACHINE=recipe("ADVANCED_CIRCUIT_BOARD","ENERGIZED_CAPACITOR","ADVANCED_CIRCUIT_BOARD","ELECTRIC_MOTOR","ELECTRIC_FURNACE_3","ELECTRIC_MOTOR",
            "HEATING_COIL","ELECTRIC_MOTOR","HEATING_COIL");
    public static final ItemStack[] LAZY_STACKMGENERATOR=recipe("ADVANCED_CIRCUIT_BOARD","ENERGIZED_CAPACITOR","ADVANCED_CIRCUIT_BOARD","ELECTRIC_MOTOR","ELECTRIC_ORE_GRINDER_3","ELECTRIC_MOTOR",
            "HEATING_COIL","ELECTRIC_MOTOR","HEATING_COIL");
    public static final SlimefunAddon INSTANCE = MyAddon.getInstance();
    public static SlimefunItem register(SlimefunItem item){
        item.register(INSTANCE);
        return item;
    }
    protected static boolean TYPE=false;
    protected static Object mkP(Object v1,Object v2){
        return new Pair(v1,v2);
    }
    protected static Object[] mkl(Object ... v){
        return Arrays.stream(v).toArray();
    }
    protected static int[] CHOOSEN_SLOT=new int[]{
       
    };
    public static ItemStack[] recipe(Object ... v){
        if(!TYPE||v.length<=9)
            return Arrays.stream(v).map(AddUtils::resolveItem).toArray(ItemStack[]::new);
        else{
            int len=v.length;
            ItemStack[] res = new ItemStack[9];
            int index=0;
            int delta=len/9;
            for(int i=0;i<9;++i){
                if(index>=len)res[i]=null;
                else{
                    res[i]=resolveItem(v[index]);
                }
                index+=delta-1+i%2;
            }
            return res;
        }
    }
    protected static RecipeType COMMON_TYPE=TYPE? ENHANCED_CRAFTING_TABLE: BugCrafter.TYPE;
    protected static <T extends Object> List<T> list(T ... input){
        return Arrays.asList(input);
    }
    protected static <T extends Object,Z extends Object> Pair<T,Z> pair(T v1,Z v2){
        return new Pair(v1,v2);
    }
    public static ItemStack setC(ItemStack it,int amount){
        return new CustomItemStack(it,amount);
    }
    protected static ItemStack[] nullRecipe(){
        return NULL_RECIPE.clone();
    }
//    private static HashMap<Object,Integer> mrecipe(Object ... v){
//        int len=v.length;
//        return new PairList<>((len+1)/2){{
//            for(int i=0;i<len;i+=2){
//                Object v1=v[i];
//                Integer v2=(Integer)v[i+1];
//                put(v1,v2);
//            }
//        }};
//    }
    private static PairList<Object,Integer> mkMp(Object ... v){
        int len=v.length;
        return new PairList<>(){{
            for(int i=0;i<len;i+=2){
                Object v1=v[i];
                Integer v2=(Integer)v[i+1];
                put(v1,v2);
            }
        }};
    }
    //items
    public static final CustomRecipeType STARSMELTERY=new CustomRecipeType(
            getNameKey("star_smeltery"),
            new CustomItemStack(AddItem.STAR_SMELTERY, AddItem.STAR_SMELTERY.getDisplayName(),
                    "", "&c在%s中锻造!".formatted(Language.get("Machines.STAR_SMELTERY.Name")))
    );
    public static HashMap<SlimefunItem,RecipeType> CRAFTTYPE_MANUAL_RECIPETYPE=new HashMap<>();
    public static final SlimefunItem MATL114=new MaterialItem(MATERIAL, AddItem.MATL114,
            AddDepends.INFINITYWORKBENCH_TYPE!=null?AddDepends.INFINITYWORKBENCH_TYPE:COMMON_TYPE,
            recipe(AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,
                    AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,
                    AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,
                    AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG)
            ).register();
    public static final SlimefunItem BUG= new MaterialItem(MATERIAL,AddItem.BUG,NULL,
            nullRecipe(),list(getInfoShow("&f获取方式","&7会出现在一些隐蔽地方...",
            "&7当你出现疑问,为什么这个物品找不到时",
            "&7你可能需要多看看\"逻辑工艺 版本与说明\"分类(物理意义)")))
            .register();

    public static final SlimefunItem TRUE=new MaterialItem(MATERIAL,AddItem.TRUE_,NULL,
            formatInfoRecipe(AddItem.BOOL_GENERATOR,Language.get("Machines.BOOL_GENERATOR.Name")))
            .register();
    public static final SlimefunItem FALSE=new MaterialItem(MATERIAL,AddItem.FALSE_,NULL,
            formatInfoRecipe(AddItem.BOOL_GENERATOR,Language.get("Machines.BOOL_GENERATOR.Name")))
            .register();
    public static final SlimefunItem LOGIGATE=new MaterialItem(MATERIAL,AddItem.LOGIGATE,ENHANCED_CRAFTING_TABLE,
            recipe("SILVER_INGOT","REDSTONE_TORCH","SILVER_INGOT",
                        "REDSTONE",null,"REDSTONE",
                    "SILVER_INGOT","REDSTONE_TORCH","SILVER_INGOT"))
            .register().setOutput(setC(AddItem.LOGIGATE,3));
    public static final SlimefunItem LOGIC=new MaterialItem(MATERIAL,AddItem.LOGIC,NULL,
            formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")))
            .register();
    public static final SlimefunItem NOLOGIC=new MaterialItem(MATERIAL,AddItem.NOLOGIC,NULL,
            formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")))
            .register();



        //generated
    public static final SlimefunItem EXISTE=new MaterialItem(MATERIAL,AddItem.EXISTE,NULL,
                formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem UNIQUE=new MaterialItem(MATERIAL,AddItem.UNIQUE,NULL,
            formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem PARADOX=new MaterialItem(MATERIAL,AddItem.PARADOX,NULL,
            formatInfoRecipe(AddItem.LVOID_GENERATOR,Language.get("Machines.LVOID_GENERATOR.Name")),null)
            .register();

    public static final SlimefunItem LENGINE=new MaterialItem(MATERIAL,AddItem.LENGINE,COMMON_TYPE,
            recipe(AddItem.LOGIC,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LOGIC,
                    AddItem.ABSTRACT_INGOT,"ELECTRIC_MOTOR",AddItem.LOGIC,AddItem.LOGIC,"ELECTRIC_MOTOR",AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,"ELECTRIC_MOTOR",AddItem.LOGIC,AddItem.LOGIC,"ELECTRIC_MOTOR",AddItem.ABSTRACT_INGOT,
                    "REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT"),null)
            .register().setOutput(AddItem.LENGINE);


    public static final SlimefunItem LFIELD=new MaterialItem(MATERIAL,AddItem.LFIELD,COMMON_TYPE,
            recipe("SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT",
                    null,AddItem.EXISTE,null,AddItem.EXISTE,null,AddItem.EXISTE,
                    AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,
                    AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,
                    null,AddItem.UNIQUE,null,AddItem.UNIQUE,null,AddItem.UNIQUE,
                    "SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT"),null)
            .register().setOutput(setC(AddItem.LFIELD,17));



    public static final SlimefunItem LSCHEDULER=new MaterialItem(MATERIAL,AddItem.LSCHEDULER,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.LFIELD,
                    AddItem.LFIELD,"ANDROID_INTERFACE_ITEMS","ANDROID_MEMORY_CORE","ANDROID_MEMORY_CORE","ANDROID_INTERFACE_ITEMS", AddItem.LFIELD,
                    AddItem.ABSTRACT_INGOT,"ANDROID_MEMORY_CORE","PROGRAMMABLE_ANDROID",setC(AddItem.LENGINE,1),"ANDROID_MEMORY_CORE",AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,"ANDROID_MEMORY_CORE",setC(AddItem.LENGINE,1),"PROGRAMMABLE_ANDROID","ANDROID_MEMORY_CORE",AddItem.ABSTRACT_INGOT,
                    AddItem.LFIELD,"ANDROID_INTERFACE_ITEMS","ANDROID_MEMORY_CORE","ANDROID_MEMORY_CORE","ANDROID_INTERFACE_ITEMS",AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.LFIELD),null)
            .register();
    public static final SlimefunItem LCRAFT=new MaterialItem(MATERIAL,AddItem.LCRAFT,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,"CARGO_MOTOR","CARGO_MOTOR",AddItem.LFIELD, AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,"CRAFTING_MOTOR","CRAFTING_MOTOR", AddItem.LFIELD,AddItem.LFIELD,
                    "CARGO_MOTOR","CRAFTING_MOTOR","ENHANCED_AUTO_CRAFTER",setC(AddItem.LENGINE,1),"CRAFTING_MOTOR","CARGO_MOTOR",
                    "CARGO_MOTOR","CRAFTING_MOTOR", setC(AddItem.LENGINE,1),"ENHANCED_AUTO_CRAFTER","CRAFTING_MOTOR","CARGO_MOTOR",
                    AddItem.LFIELD, AddItem.LFIELD,"CRAFTING_MOTOR","CRAFTING_MOTOR",AddItem.LFIELD,AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,"CARGO_MOTOR","CARGO_MOTOR",AddItem.LFIELD,AddItem.LFIELD),null)
            .register().setOutput(setC(AddItem.LCRAFT,8));
    public static final SlimefunItem LBOOLIZER=new MaterialItem(MATERIAL,AddItem.LBOOLIZER,COMMON_TYPE,
            recipe(AddItem.TRUE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_,
                    AddItem.FALSE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.FALSE_,AddItem.TRUE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.FALSE_,AddItem.TRUE_, AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.FALSE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.TRUE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_),null)
            .register();
    public static final SlimefunItem LMOTOR=new MaterialItem(MATERIAL,AddItem.LMOTOR,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,"ELECTRIC_MOTOR","ELECTRIC_MOTOR",AddItem.LFIELD,AddItem.LFIELD,
                    AddItem.LFIELD,"ELECTRO_MAGNET",setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),"ELECTRO_MAGNET",AddItem.LFIELD,
                    "ELECTRIC_MOTOR",setC(AddItem.LBOOLIZER,1),"ELECTRIC_MOTOR",AddItem.LENGINE,setC(AddItem.LBOOLIZER,1),"ELECTRIC_MOTOR",
                    "ELECTRIC_MOTOR",setC(AddItem.LBOOLIZER,1),AddItem.LENGINE,"ELECTRIC_MOTOR",setC(AddItem.LBOOLIZER,1),"ELECTRIC_MOTOR",
                    AddItem.LFIELD,"ELECTRO_MAGNET",setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),"ELECTRO_MAGNET",AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,"ELECTRIC_MOTOR","ELECTRIC_MOTOR",AddItem.LFIELD,AddItem.LFIELD),null)
            .register().setOutput(setC(AddItem.LMOTOR,4));
    public static final SlimefunItem LDIGITIZER=new MaterialItem(MATERIAL,AddItem.LDIGITIZER,COMMON_TYPE,
            recipe(AddItem.UNIQUE,AddItem.LFIELD,AddItem.LOGIGATE,AddItem.LOGIGATE,AddItem.LFIELD,AddItem.UNIQUE
                    ,AddItem.LFIELD,setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1)
                    ,AddItem.LFIELD,AddItem.LOGIGATE,setC(AddItem.LBOOLIZER,1),AddItem.PARADOX,AddItem.PARADOX
                    ,setC(AddItem.LBOOLIZER,1),AddItem.LOGIGATE,AddItem.LOGIGATE,setC(AddItem.LBOOLIZER,1),AddItem.PARADOX
                    ,AddItem.PARADOX,setC(AddItem.LBOOLIZER,1),AddItem.LOGIGATE,AddItem.LFIELD,setC(AddItem.LBOOLIZER,1)
                    ,setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),AddItem.LFIELD,AddItem.UNIQUE
                    ,AddItem.LFIELD,AddItem.LOGIGATE,AddItem.LOGIGATE,AddItem.LFIELD,AddItem.UNIQUE),null)
            .register();
    public static final SlimefunItem LIOPORT=new MaterialItem(MATERIAL,AddItem.LIOPORT,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,"HOPPER","HOPPER",AddItem.LFIELD, AddItem.LFIELD,
                    AddItem.LFIELD,"GPS_TRANSMITTER","CARGO_NODE","CARGO_NODE", "GPS_TRANSMITTER",AddItem.LFIELD,
                    "HOPPER","CARGO_NODE_INPUT","CARGO_NODE_OUTPUT", "CARGO_NODE_OUTPUT_ADVANCED","CARGO_NODE_INPUT","HOPPER",
                    "HOPPER","CARGO_NODE_INPUT", AddItem.LENGINE,"CARGO_NODE_OUTPUT_ADVANCED","CARGO_NODE_INPUT","HOPPER",
                    AddItem.LFIELD, "GPS_TRANSMITTER","CARGO_NODE","CARGO_NODE","GPS_TRANSMITTER",AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,"HOPPER","HOPPER",AddItem.LFIELD, AddItem.LFIELD),null)
            .register().setOutput(setC(AddItem.LIOPORT,2));
    public static final SlimefunItem LPLATE=new MaterialItem(MATERIAL,AddItem.LPLATE,SMELTERY,
            recipe(setC(AddItem.ABSTRACT_INGOT,2),"BATTERY","POTATO",AddItem.CHIP_INGOT),null)
            .register();
    //TODO 修改输出数量
    public static final SlimefunItem DIMENSIONAL_SHARD=new AbstractGeoResource(MATERIAL, AddItem.DIMENSIONAL_SHARD,
            recipe(null,AddItem.END_MINER,null,null,getInfoShow("&f获取方式","&7在末地大部分群系","或风袭沙丘或蘑菇岛开采","&7或者在本附属的矿机中获取")),
            1, new HashMap<>(){{
        put(Biome.END_BARRENS,1);
        put(Biome.END_HIGHLANDS,1);
        put(Biome.THE_END,1);
        put(Biome.SMALL_END_ISLANDS,1);
        put(Biome.WINDSWEPT_GRAVELLY_HILLS,1);
        put(Biome.MUSHROOM_FIELDS,1);
    }}) .registerGeo();
    public static final SlimefunItem DIMENSIONAL_SINGULARITY=new MaterialItem(MATERIAL,AddItem.DIMENSIONAL_SINGULARITY,NULL,
            formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),null)
            .register();
    public static final SlimefunItem STAR_GOLD=new MaterialItem(MATERIAL,AddItem.STAR_GOLD,NULL    ,
            recipe(null,AddItem.END_MINER,null,null,getInfoShow("&f获取方式","&7在本附属的矿机中获取")),null)
            .register();
    public static final SlimefunItem STAR_GOLD_INGOT=new MaterialItem(MATERIAL,AddItem.STAR_GOLD_INGOT,SMELTERY,
            recipe(setC(AddItem.STAR_GOLD,5),setC(AddItem.DIMENSIONAL_SHARD,11)),null)
            .register();

    public static final SlimefunItem WORLD_FEAT=new MaterialItem(MATERIAL,AddItem.WORLD_FEAT,COMMON_TYPE,
            recipe(null,"STONE","PODZOL","PODZOL","GRASS_BLOCK",null,
                    "STONE","PODZOL","STONE","GRASS_BLOCK","PODZOL","GRASS_BLOCK",
                    "PODZOL","STONE","GRASS_BLOCK","STONE","GRASS_BLOCK","PODZOL",
                    "PODZOL","GRASS_BLOCK","STONE","GRASS_BLOCK","STONE","PODZOL",
                    "GRASS_BLOCK","PODZOL","GRASS_BLOCK","STONE","PODZOL","STONE",
                    null,"GRASS_BLOCK","PODZOL","PODZOL","STONE",null),null)
            .register();
    public static final SlimefunItem NETHER_FEAT=new MaterialItem(MATERIAL,AddItem.NETHER_FEAT,COMMON_TYPE,
            recipe(null,"MAGMA_BLOCK","CRIMSON_NYLIUM","CRIMSON_NYLIUM","OBSIDIAN",null,
                    "MAGMA_BLOCK","CRIMSON_NYLIUM","MAGMA_BLOCK","OBSIDIAN","CRIMSON_NYLIUM","OBSIDIAN",
                    "CRIMSON_NYLIUM","MAGMA_BLOCK","OBSIDIAN","MAGMA_BLOCK","OBSIDIAN","CRIMSON_NYLIUM",
                    "CRIMSON_NYLIUM","OBSIDIAN","MAGMA_BLOCK","OBSIDIAN","MAGMA_BLOCK","CRIMSON_NYLIUM",
                    "OBSIDIAN","CRIMSON_NYLIUM","OBSIDIAN","MAGMA_BLOCK","CRIMSON_NYLIUM","MAGMA_BLOCK",
                    null,"OBSIDIAN","CRIMSON_NYLIUM","CRIMSON_NYLIUM","MAGMA_BLOCK",null),null)
            .register();
    public static final SlimefunItem END_FEAT=new MaterialItem(MATERIAL,AddItem.END_FEAT,COMMON_TYPE,
            recipe(null,"CHORUS_FLOWER","CHORUS_FRUIT","CHORUS_FRUIT","END_STONE",null,
                    "CHORUS_FLOWER","CHORUS_FRUIT","CHORUS_FLOWER","END_STONE","CHORUS_FRUIT","END_STONE",
                    "CHORUS_FRUIT","CHORUS_FLOWER","END_STONE","CHORUS_FLOWER","END_STONE","CHORUS_FRUIT",
                    "CHORUS_FRUIT","END_STONE","CHORUS_FLOWER","END_STONE","CHORUS_FLOWER","CHORUS_FRUIT",
                    "END_STONE","CHORUS_FRUIT","END_STONE","CHORUS_FLOWER","CHORUS_FRUIT","CHORUS_FLOWER",
                    null,"END_STONE","CHORUS_FRUIT","CHORUS_FRUIT","CHORUS_FLOWER",null),null)
            .register();
    public static final SlimefunItem ENTITY_FEATURE=new EntityFeat(MATERIAL,AddItem.ENTITY_FEAT,NULL,
            recipe(null,addGlow( new ItemStack(Material.IRON_PICKAXE)),null,null,getInfoShow("&f获取方式","&7当 挖掘任意刷怪笼方块时 ","&750%额外掉落随机种类的生物特征"),null,
                    null,new ItemStack(Material.SPAWNER),null))
            .register();
    public static final SlimefunItem HYPER_LINK=new HypLink(SPACE,AddItem.HYPER_LINK,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT,
                    AddItem.PARADOX,AddItem.DIMENSIONAL_SHARD,AddItem.PARADOX,
                    AddItem.ABSTRACT_INGOT,AddItem.BUG,AddItem.ABSTRACT_INGOT))
            .register();


    public static final SlimefunItem METAL_CORE=new MaterialItem(MATERIAL,AddItem.METAL_CORE,NULL,
            formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),null)
            .register();
    public static final SlimefunItem SMELERY_CORE=new MaterialItem(MATERIAL,AddItem.SMELERY_CORE,NULL,
            formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),null)
            .register();
    public static final SlimefunItem MASS_CORE=new MaterialItem(MATERIAL,AddItem.MASS_CORE,NULL,
            formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),null)
            .register();
    public static final SlimefunItem TECH_CORE=new MaterialItem(MATERIAL,AddItem.TECH_CORE,NULL,
            formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),null)
            .register();
    public static final SlimefunItem LSINGULARITY=new MaterialItem(MATERIAL,AddItem.LSINGULARITY,NULL,
            formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem ATOM_INGOT=new MaterialItem(MATERIAL,AddItem.ATOM_INGOT,NULL,
            formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();


    //alloy
    public static final SlimefunItem CHIP_INGOT=new MaterialItem(MATERIAL,AddItem.CHIP_INGOT,SMELTERY,
            recipe("2SILVER_INGOT","2REINFORCED_ALLOY_INGOT","4IRON_INGOT",
                    setC(SlimefunItems.COPPER_INGOT,4),"2SILICON","4ALUMINUM_INGOT"))
            .register();
    public static final SlimefunItem ABSTRACT_INGOT=new MaterialItem(MATERIAL,AddItem.ABSTRACT_INGOT,SMELTERY,
            recipe(AddItem.TRUE_,AddItem.EXISTE,AddItem.LOGIC,AddItem.NOLOGIC,AddItem.UNIQUE,AddItem.FALSE_,null,null,null))
            .register().setOutput(setC(AddItem.ABSTRACT_INGOT,4));
    public static final SlimefunItem PALLADIUM_INGOT=new MaterialItem(MATERIAL,AddItem.PALLADIUM_INGOT,NULL,
            formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem PLATINUM_INGOT=new MaterialItem(MATERIAL,AddItem.PLATINUM_INGOT,NULL,
            formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem MOLYBDENUM=new MaterialItem(MATERIAL,AddItem.MOLYBDENUM,NULL,
            formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem CERIUM=new MaterialItem(MATERIAL,AddItem.CERIUM,NULL,
            formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem CADMIUM_INGOT=new MaterialItem(MATERIAL,AddItem.CADMIUM_INGOT,NULL,
            formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem MENDELEVIUM=new MaterialItem(MATERIAL,AddItem.MENDELEVIUM,NULL,
            formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem DYSPROSIUM=new MaterialItem(MATERIAL,AddItem.DYSPROSIUM,NULL,
            formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem BISMUTH_INGOT=new MaterialItem(MATERIAL,AddItem.BISMUTH_INGOT,NULL,
            formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem ANTIMONY_INGOT=new MaterialItem(MATERIAL,AddItem.ANTIMONY_INGOT,NULL,
            formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem THALLIUM=new MaterialItem(MATERIAL,AddItem.THALLIUM,NULL,
            formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem HYDRAGYRUM=new MaterialItem(MATERIAL,AddItem.HYDRAGYRUM,NULL,
            formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem BORON=new MaterialItem(MATERIAL,AddItem.BORON,NULL,
            formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem BISILVER=new MaterialItem(MATERIAL,AddItem.BISILVER,STARSMELTERY,
            recipe(setC(AddItem.BISMUTH_INGOT,4),setC(AddItem.PARADOX,20),"4SILVER_INGOT",
                    "4BILLON_INGOT"),null)
            .register();
    public static final SlimefunItem PAGOLD=new MaterialItem(MATERIAL,AddItem.PAGOLD,STARSMELTERY,
            recipe(setC(AddItem.PALLADIUM_INGOT,2),setC(AddItem.PARADOX,20),
                    setC(AddItem.PLATINUM_INGOT,2),"7GOLD_22K"
                    ),null)
            .register();

    public static final SlimefunItem PDCECDMD=new MaterialItem(MATERIAL,AddItem.PDCECDMD,AddSlimefunItems.STARSMELTERY,
            recipe("18PLUTONIUM",setC(AddItem.CERIUM,64),setC(AddItem.CADMIUM_INGOT,64),
                    setC(AddItem.MENDELEVIUM,48),setC(AddItem.LSINGULARITY,1)
                    ),null)
            .register();
    public static final SlimefunItem HGTLPBBI=new MaterialItem(MATERIAL,AddItem.HGTLPBBI,AddSlimefunItems.STARSMELTERY,
            recipe(setC(AddItem.HYDRAGYRUM,64),setC(AddItem.THALLIUM,64),
                    "64LEAD_INGOT",setC(AddItem.BISILVER,12),setC(AddItem.LSINGULARITY,1)),null)
            .register();
    public static final SlimefunItem REINFORCED_CHIP_INGOT=new MaterialItem(MATERIAL,AddItem.REINFORCED_CHIP_INGOT,AddSlimefunItems.STARSMELTERY,
            recipe(setC(AddItem.STAR_GOLD_INGOT,40),setC(AddItem.CHIP_INGOT,16),
                    setC(AddItem.ATOM_INGOT,32),setC(AddItem.PAGOLD,4),
                    setC(AddItem.CADMIUM_INGOT,8),setC(AddItem.BISILVER,2)
                    ),null)
            .register();



    public static final SlimefunItem SPACE_PLATE=new MaterialItem(MATERIAL,AddItem.SPACE_PLATE,STARSMELTERY,
            recipe(setC(AddItem.STAR_GOLD_INGOT,48),setC(AddItem.PARADOX,24),
                    setC(AddItem.ATOM_INGOT,64),setC(AddItem.LFIELD,32),AddItem.REINFORCED_CHIP_INGOT
                    ),null)
            .register();
    public static final SlimefunItem VIRTUAL_SPACE=new MaterialItem(MATERIAL,AddItem.VIRTUAL_SPACE,COMMON_TYPE,
            recipe(AddItem.SPACE_PLATE,AddItem.BISILVER,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.BISILVER,AddItem.SPACE_PLATE,
                    AddItem.BISILVER,AddItem.LDIGITIZER,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.LDIGITIZER,AddItem.BISILVER,
                    "GPS_TRANSMITTER_4",AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,"GPS_TRANSMITTER_4",
                    AddItem.LIOPORT,AddItem.PAGOLD,AddItem.CHIP_CORE,AddItem.CHIP_CORE,AddItem.PAGOLD,AddItem.LIOPORT,
                    AddItem.BISILVER,AddItem.LDIGITIZER,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.LDIGITIZER,AddItem.BISILVER,
                    AddItem.SPACE_PLATE,AddItem.BISILVER,setC(AddItem.ATOM_INGOT,8),setC(AddItem.ATOM_INGOT,8),AddItem.BISILVER,AddItem.SPACE_PLATE),null)
            .register().setOutput(setC(AddItem.VIRTUAL_SPACE,8));

    public static final SlimefunItem REDSTONE_ENGINE=new MaterialItem(VANILLA,AddItem.REDSTONE_ENGINE,ENHANCED_CRAFTING_TABLE,
            recipe("TNT","SLIME_BLOCK","ANVIL",
                    "OBSERVER","STICKY_PISTON","STICKY_PISTON",
                    "REDSTONE_TORCH",AddItem.LOGIGATE,"REPEATER"),null)
            .register();

    public static final SlimefunItem SAMPLE_HEAD=new AbstractBlock(SPECIAL,AddItem.SAMPLE_HEAD,NULL,
            formatInfoRecipe(AddItem.HEAD_ANALYZER,Language.get("Machines.HEAD_ANALYZER.Name")))
            .register();
    public static final SlimefunItem PLAYER_IDCARD=new PlayerIdCard(SPECIAL,AddItem.PLAYER_IDCARD,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.LPLATE,null,AddItem.LPLATE,"PLAYER_HEAD",AddItem.HYPER_LINK,"PLAYER_HEAD",
                    AddItem.LPLATE,null,AddItem.LPLATE),null)
            .register();
    public static final SlimefunItem SAMPLE_SPAWNER=new AbstractSpawner(FUNCTIONAL,AddItem.SAMPLE_SPAWNER,NULL,
            formatInfoRecipe(AddItem.ENTITY_FEAT,Language.get("Items.ENTITY_FEAT.Name")))
            .register();
    public static final SlimefunItem CHIP=new ChipCard(ADVANCED,AddItem.CHIP,NULL,
            formatInfoRecipe(AddItem.CHIP_MAKER,Language.get("Machines.CHIP_MAKER.Name")))
            .register();
    public static final SlimefunItem CHIP_CORE=new MaterialItem(ADVANCED,AddItem.CHIP_CORE,NULL,
            formatInfoRecipe(AddItem.CHIP_MAKER,Language.get("Machines.CHIP_MAKER.Name")),null)
            .register();

    public static final SlimefunItem LOGIC_CORE=new MaterialItem(MATERIAL,AddItem.LOGIC_CORE,NULL,
            formatInfoRecipe(AddItem.FINAL_SEQUENTIAL,Language.get("Machines.FINAL_SEQUENTIAL.Name")),null)
            .register();
    public static final SlimefunItem FINAL_FRAME=new MultiPart(MATERIAL,AddItem.FINAL_FRAME,NULL,
            formatInfoRecipe(AddItem.FINAL_SEQUENTIAL,Language.get("Machines.FINAL_SEQUENTIAL.Name")),"final.frame"){
            public boolean redirectMenu(){
                return false;
            }
    }
            .register();

    public static final SlimefunItem STACKFRAME=new MaterialItem(MATERIAL,AddItem.STACKFRAME,COMMON_TYPE,
            recipe(AddItem.LIOPORT,setC(AddItem.ATOM_INGOT,8),"GPS_TRANSMITTER_4","GPS_TRANSMITTER_4",setC(AddItem.ATOM_INGOT,8),AddItem.LMOTOR,
                    setC(AddItem.ATOM_INGOT,8),AddItem.BISILVER,setC(AddItem.STAR_GOLD_INGOT,1),setC(AddItem.STAR_GOLD_INGOT,1),AddItem.BISILVER,setC(AddItem.ATOM_INGOT,8),
                    AddItem.PAGOLD,setC(AddItem.STAR_GOLD_INGOT,1),AddItem.LSINGULARITY,AddItem.LSCHEDULER,setC(AddItem.STAR_GOLD_INGOT,1),AddItem.PAGOLD,
                    AddItem.PAGOLD,setC(AddItem.STAR_GOLD_INGOT,1),AddItem.LSCHEDULER,AddItem.SPACE_PLATE,setC(AddItem.STAR_GOLD_INGOT,1),AddItem.PAGOLD,
                    setC(AddItem.ATOM_INGOT,8),AddItem.BISILVER,setC(AddItem.STAR_GOLD_INGOT,1),setC(AddItem.STAR_GOLD_INGOT,1),AddItem.BISILVER,setC(AddItem.ATOM_INGOT,8),
                    AddItem.LDIGITIZER,setC(AddItem.ATOM_INGOT,8),AddItem.CHIP_CORE,AddItem.CHIP_CORE,setC(AddItem.ATOM_INGOT,8),AddItem.LCRAFT),null)
            .register().setOutput(setC(AddItem.STACKFRAME,64));
    public static final SlimefunItem LASER=new MaterialItem(ADVANCED,AddItem.LASER,COMMON_TYPE,
            recipe(AddItem.HYDRAGYRUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.HYDRAGYRUM,
                    AddItem.ANTIMONY_INGOT,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.ANTIMONY_INGOT,
                    AddItem.BORON,AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,AddItem.BORON,
                    AddItem.BORON,AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,AddItem.BORON,
                    AddItem.ANTIMONY_INGOT,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.ANTIMONY_INGOT,
                    AddItem.HYDRAGYRUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.HYDRAGYRUM),null)
            .register();
    public static final SlimefunItem VIRTUALWORLD=new MaterialItem(MATERIAL,AddItem.VIRTUALWORLD,NULL,
            formatInfoRecipe(AddItem.FINAL_CONVERTOR,Language.get("Machines.FINAL_CONVERTOR.Name")),null)
            .register();


    public static final SlimefunItem WITHERPROOF_REDSTONE=new WitherProofBlock(VANILLA,AddItem.WITHERPROOF_REDSTONE,ENHANCED_CRAFTING_TABLE,
            recipe("REDSTONE_BLOCK","LEAD_INGOT","REDSTONE_BLOCK","LEAD_INGOT","WITHER_PROOF_OBSIDIAN","LEAD_INGOT",
                    "REDSTONE_BLOCK","LEAD_INGOT","REDSTONE_BLOCK"))
            .register().setOutput(setC(AddItem.WITHERPROOF_REDSTONE,4));
    public static final SlimefunItem WITHERPROOF_REDS=new WitherProofBlock(VANILLA,AddItem.WITHERPROOF_REDS,ENHANCED_CRAFTING_TABLE,
            recipe("REDSTONE","LEAD_INGOT","REDSTONE","LEAD_INGOT","WITHER_PROOF_OBSIDIAN","LEAD_INGOT",
                    "REDSTONE","LEAD_INGOT","REDSTONE"))
            .register().setOutput(setC(AddItem.WITHERPROOF_REDS,4));
    public static final MyVanillaItem MACE_ITEM=new InitializeSafeProvider <>(MyVanillaItem.class, ()->{
        if(AddItem.MACE_ITEM!=null){
            var re= new MyVanillaItem(TOOLS_FUNCTIONAL, AddItem.MACE_ITEM,"LOGITECH_MACE_RECIPE",BukkitUtils.VANILLA_CRAFTTABLE,
                    recipe(AddItem.METAL_CORE,AddItem.BUG,AddItem.METAL_CORE,
                            null,AddItem.STAR_GOLD_INGOT,null,
                            null,AddItem.STAR_GOLD_INGOT,null));
            //todo we will add this to TOOLS_FUNCTIONAL later after we reconstruct TOOLS_FUNCTIONAL!
            return re.register();
        }
        return null;
    }).v();
    public static final SlimefunItem UNBREAKING_SHIELD=new MaterialItem(TOOLS_FUNCTIONAL,AddItem.UNBREAKING_SHIELD,BukkitUtils.VANILLA_CRAFTTABLE,
            recipe("IRON_BLOCK","DAMASCUS_STEEL_INGOT","IRON_BLOCK","IRON_BLOCK","OBSIDIAN","IRON_BLOCK",
            null,"IRON_BLOCK",null)).register();
    public static final MyVanillaItem SUPER_COBALT_PICKAXE = new MyVanillaItem(TOOLS_FUNCTIONAL,AddItem.SUPER_COBALT_PICKAXE,"SUPER_COBALT_PICKAXE", SmithInterfaceProcessor.INTERFACED_SMITH_UPDATE,recipe(AddItem.ABSTRACT_INGOT,"COBALT_PICKAXE","3DIAMOND",null,null,null,null,null,null))
            .register();



    //machines
    public static final SlimefunItem BOOL_GENERATOR=new BoolGenerator(BASIC,AddItem.BOOL_GENERATOR,ENHANCED_CRAFTING_TABLE,
            recipe("OBSERVER","REDSTONE","OBSERVER",
                    "REDSTONE_TORCH","SILICON","REDSTONE_TORCH",
                    "FERROSILICON","ENERGY_CONNECTOR","FERROSILICON"),Material.RECOVERY_COMPASS,6)
            .register();
    public static final SlimefunItem LOGIC_REACTOR=new LogicReactor(BASIC,AddItem.LOGIC_REACTOR,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.LOGIGATE,"COMPARATOR",AddItem.LOGIGATE,
                    "HEATING_COIL" , null,"HEATING_COIL",
                    "IRON_INGOT","SMALL_CAPACITOR","IRON_INGOT"
                    ),Material.COMPARATOR,3)
            .register();
    public static final SlimefunItem BUG_CRAFTER=new BugCrafter(BASIC,AddItem.BUG_CRAFTER,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.LOGIGATE,AddItem.ABSTRACT_INGOT,
                    AddItem.CHIP_INGOT,AddItem.BUG,AddItem.CHIP_INGOT,
                    AddItem.ABSTRACT_INGOT,"MEDIUM_CAPACITOR",AddItem.ABSTRACT_INGOT),10_000,1_00,7)
            .register();
    public static final  SlimefunItem FURNACE_FACTORY=new MTMachine(BASIC, AddItem.FURNACE_FACTORY,COMMON_TYPE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_FURNACE","ELECTRIC_FURNACE",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_FURNACE","ELECTRIC_FURNACE",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT),
            addGlow( new ItemStack(Material.FLINT_AND_STEEL)),60,6_400,
            ()->{
                List<MachineRecipe> recipelist=new ArrayList<>();
                List<MachineRecipe> rp= RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_FURNACE);
                for (MachineRecipe rps:rp){
                    recipelist.add(MachineRecipeUtils.stackFrom(0,rps.getInput(),rps.getOutput()));
                }
                return recipelist;
            })
            .register();
    public static final  SlimefunItem PRESSOR_FACTORY=new MTMachine(BASIC, AddItem.PRESSOR_FACTORY,COMMON_TYPE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_PRESS","ELECTRIC_PRESS",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_PRESS","ELECTRIC_PRESS",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT), addGlow( new ItemStack(Material.PISTON))
            ,120,64_00,()->{
                HashSet<SlimefunItem> hasInCompressor=new HashSet<>();
                List<MachineRecipe> recipes=new ArrayList<>();
                List<MachineRecipe> elecpress=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(RecipeType.COMPRESSOR);
                for (MachineRecipe recipe:elecpress){
                    if(recipe.getOutput()[0].getType()!=Material.COBBLESTONE)
                    recipes.add(MachineRecipeUtils.stackFrom(3,recipe.getInput(),recipe.getOutput()));
                }
                elecpress=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(RecipeType.PRESSURE_CHAMBER);
                for (MachineRecipe recipe:elecpress){
                    recipes.add(MachineRecipeUtils.stackFrom(4,recipe.getInput(),recipe.getOutput()));
                }
                List<MachineRecipe> compressorRecipes=RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItem.getById("ELECTRIC_PRESS"),new ArrayList<>());
                for(MachineRecipe recipe:compressorRecipes){
                    ItemStack stack=recipe.getOutput()[0];
                    SlimefunItem stackItem=SlimefunItem.getByItem(stack);
                    if(stackItem!=null&&stackItem.getRecipeType()== RecipeType.COMPRESSOR){
                        continue;
                    }
                    recipes.add(recipe);
                }
                return recipes;
            })
            .register();
    public static final  SlimefunItem GRIND_FACTORY=new MTMachine(BASIC, AddItem.GRIND_FACTORY,COMMON_TYPE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_ORE_GRINDER_3","ELECTRIC_ORE_GRINDER_3",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_ORE_GRINDER_3","ELECTRIC_ORE_GRINDER_3",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT), addGlow( new ItemStack(Material.DIAMOND_CHESTPLATE))
            ,125,64_00,()->{
        List<MachineRecipe> recipelist=new ArrayList<>();
        List<MachineRecipe> rp=RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItem.getById("ELECTRIC_ORE_GRINDER_3"),new ArrayList<>());
        for (MachineRecipe rps:rp){
            recipelist.add(MachineRecipeUtils.stackFromMachine(rps));
        }
        return recipelist;
    })
            .register();
    public static final  SlimefunItem SMELTRY=new AEMachine(BASIC, AddItem.SMELTRY,COMMON_TYPE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem. ABSTRACT_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem. ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem. ABSTRACT_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem. ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT),
            Material.STONE,100,12_800,
            ()->{

                List<MachineRecipe> recipelist=new ArrayList<>();
                List<MachineRecipe> rp=RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItem.getById("ELECTRIC_SMELTERY"),new ArrayList<>());
                for (MachineRecipe rps:rp){
                    recipelist.add(MachineRecipeUtils.stackFrom(0,rps.getInput(),rps.getOutput()));
                }
                return recipelist;
            }){
        {
            this.USE_HISTORY=false;
        }
    }
            .register();
    public static final  SlimefunItem ENDFRAME_MACHINE=new EMachine(VANILLA, AddItem.ENDFRAME_MACHINE,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.PARADOX,AddItem.LENGINE,AddItem.PARADOX,
                    AddItem.END_FEAT,AddItem.DIMENSIONAL_SHARD,AddItem.END_FEAT,
                    AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.ABSTRACT_INGOT), Material.STONE,324,2_000,
            mkMp(
                    mkP(mkl(setC(AddItem.END_FEAT,1),"64END_STONE"),mkl("END_PORTAL_FRAME")),6,
                        mkP(mkl(AddItem.STAR_GOLD,"END_PORTAL_FRAME"),mkl(AddItem.PORTAL_FRAME)),6,
                        mkP(mkl(setC(AddItem.STAR_GOLD_INGOT,3),"16QUARTZ_BLOCK"),mkl(AddItem.SOLAR_REACTOR_FRAME)),6,
                        mkP(mkl(setC(AddItem.LPLATE,2),"16GLASS"),mkl(AddItem.SOLAR_REACTOR_GLASS)),6,
                        mkP(mkl(AddItem.STACKFRAME,AddItem.TRUE_),mkl(AddItem.STACKMACHINE)),6,
                        mkP(mkl(AddItem.STACKFRAME,AddItem.FALSE_),mkl(AddItem.STACKMGENERATOR)),6,
                        mkP(mkl(AddItem.STACKFRAME,"4ENERGY_REGULATOR"),mkl(AddItem.ENERGY_AMPLIFIER)),6,
                        mkP(mkl(AddItem.SPACE_PLATE,AddItem.MASS_CORE),mkl(AddItem.TRANSMUTATOR_FRAME)),6,
                        mkP(mkl(AddItem.SPACE_PLATE,setC(AddItem.LFIELD,24)),mkl(setC(AddItem.TRANSMUTATOR_GLASS,2))),6,
                        mkP(mkl(setC(AddItem.ATOM_INGOT,16),setC(AddItem.BISILVER,2)),mkl(AddItem.TRANSMUTATOR_ROD)),6,
                        mkP(mkl(setC(AddItem.VIRTUALWORLD,4),setC(AddItem.HGTLPBBI,3)),mkl(AddItem.FINAL_STACKMACHINE)),6,
                        mkP(mkl(setC(AddItem.VIRTUALWORLD,4),setC(AddItem.PDCECDMD,3)),mkl(AddItem.FINAL_STACKMGENERATOR)),6
            ))
            .register();

    public static final  SlimefunItem DUST_EXTRACTOR=new MTMachine(BASIC, AddItem.DUST_EXTRACTOR,COMMON_TYPE,
            recipe(AddItem.LPLATE,setC(AddItem.TECH_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.MASS_CORE,1),AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,4),setC(AddItem.CHIP_CORE,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.ATOM_INGOT,4),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4ELECTRIC_ORE_GRINDER_3","4ELECTRIC_ORE_GRINDER_3",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,"4ELECTRIC_GOLD_PAN_3","4ELECTRIC_GOLD_PAN_3",AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4ELECTRIC_DUST_WASHER_3","4ELECTRIC_DUST_WASHER_3",AddItem.LMOTOR,AddItem.LPLATE,
                    setC(AddItem.PAGOLD,1),setC(AddItem.TECH_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.MASS_CORE,1),setC(AddItem.PAGOLD,1)),
            new ItemStack(Material.LANTERN),1800,86400,
                mkMp(
                        mkP(mkl("64COBBLESTONE"),mkl(randItemStackFactory(
                                new PairList<>(){{
                                    put("64IRON_DUST",1);
                                    put("64GOLD_DUST",1);
                                    put("64COPPER_DUST",1);
                                    put("64TIN_DUST",1);
                                    put("64ZINC_DUST",1);
                                    put("64ALUMINUM_DUST",1);
                                    put("64SILVER_DUST",1);
                                    put("64MAGNESIUM_DUST",1);
                                    put("64LEAD_DUST",1);
                                }}
                        ))),0
                )
            )
            .register();
    public static final  SlimefunItem INGOT_FACTORY=new MTMachine(BASIC, AddItem.INGOT_FACTORY,COMMON_TYPE,
            recipe(AddItem.LPLATE,setC(AddItem.METAL_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.SMELERY_CORE,1),AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,4),setC(AddItem.CHIP_CORE,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.ATOM_INGOT,4),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",AddItem.LMOTOR,AddItem.LPLATE,
                    setC(AddItem.PAGOLD,1),setC(AddItem.METAL_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.SMELERY_CORE,1),setC(AddItem.PAGOLD,1)),
            new ItemStack(Material.LANTERN),2400,129600,
//            mkMp(
//                    mkP(mkl("64IRON_DUST"),mkl("64IRON_INGOT")),0,
//                    mkP(mkl("64GOLD_DUST"),mkl("64GOLD_INGOT")),0,
//                    mkP(mkl("64COPPER_DUST"),mkl("64COPPER_INGOT")),0,
//                    mkP(mkl("64TIN_DUST"),mkl("64TIN_INGOT")),0,
//                    mkP(mkl("64ZINC_DUST"),mkl("64ZINC_INGOT")),0,
//                    mkP(mkl("64ALUMINUM_DUST"),mkl("64ALUMINUM_INGOT")),0,
//                    mkP(mkl("64SILVER_DUST"),mkl("64SILVER_INGOT")),0,
//                    mkP(mkl("64MAGNESIUM_DUST"),mkl("64MAGNESIUM_INGOT")),0,
//                    mkP(mkl("64LEAD_DUST"),mkl("64LEAD_INGOT")),0
//
//
//            )
            ()->{
                List<MachineRecipe> recipes=new ArrayList<>();
                List<MachineRecipe> recipes2=RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItem.getById("ELECTRIC_INGOT_FACTORY"),new ArrayList<>());
                for(MachineRecipe recipe : recipes2) {
                    ItemStack[] input=new ItemStack[recipe.getInput().length];
                    for (int i=0;i<input.length;++i){
                        input[i]=recipe.getInput()[i].clone();
                        input[i].setAmount(input[i].getAmount()*64);
                    }
                    ItemStack[] output =new ItemStack[recipe.getOutput().length];
                    for(int i=0;i<output.length;++i){
                        output[i]=recipe.getOutput()[i].clone();
                        if(CraftUtils.parseSfId(output[i])=="GOLD_4K"){
                            output[i]=new ItemStack(Material.GOLD_INGOT);
                        }
                        output[i].setAmount(output[i].getAmount()*64);
                    }
                    recipes.add(MachineRecipeUtils.stackFrom(0,input,output));
                }
                return recipes;
            }
    )
            .register();
    public static final  SlimefunItem INGOT_CONVERTOR=new MTMachine(BASIC, AddItem.INGOT_CONVERTOR,COMMON_TYPE,
            recipe(AddItem.LPLATE,AddItem.TECH_CORE,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.MASS_CORE,AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,4),AddItem.CHIP_CORE,AddItem.CHIP_CORE,setC(AddItem.ATOM_INGOT,4),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4ENHANCED_AUTO_CRAFTER","4ENHANCED_AUTO_CRAFTER",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,"4REINFORCED_FURNACE","4REINFORCED_FURNACE",AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4ELECTRIC_INGOT_FACTORY","4ELECTRIC_INGOT_FACTORY",AddItem.LMOTOR,AddItem.LPLATE,
                    AddItem.PAGOLD,AddItem.TECH_CORE,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.MASS_CORE,AddItem.PAGOLD),new ItemStack(Material.BEACON),2400,129600,
            mkMp(
                    mkP(mkl("64GOLD_INGOT"),mkl("64GOLD_4K")),0,
                    mkP(mkl("64GOLD_4K"),mkl("64GOLD_INGOT")),0,
                    mkP(mkl("16COPPER_INGOT"),mkl(new ItemStack(Material.COPPER_INGOT,16))),0,
                    mkP(mkl(new ItemStack(Material.COPPER_INGOT,16)),mkl("16COPPER_INGOT")),0,
                    mkP(mkl("9GOLD_DUST"),mkl("GOLD_24K")),0,
                    mkP(mkl("8STEEL_INGOT"),mkl("4DAMASCUS_STEEL_INGOT")),0,
                    mkP(mkl("4DAMASCUS_STEEL_INGOT"),mkl("8STEEL_INGOT")),0,
                    mkP(mkl("4CARBONADO"),mkl("8CARBON_CHUNK")),0,
                    mkP(mkl("8CARBON_CHUNK"),mkl("4CARBONADO")),0,
                    mkP(mkl("8QUARTZ"),mkl("2SILICON")),0
            ))
            .register();
    public static final  SlimefunItem CRAFTER=new SpecialCrafter(BASIC, AddItem.CRAFTER, ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT, AddItem.NOLOGIC, AddItem.ABSTRACT_INGOT, AddItem.LBOOLIZER, AddItem.LCRAFT, AddItem.LBOOLIZER,
                    AddItem.ABSTRACT_INGOT, AddItem.NOLOGIC, AddItem.ABSTRACT_INGOT), Material.BOOK, 0, 180, 7200) {
        @Override
        public HashMap<SlimefunItem, RecipeType> getRecipeTypeMap() {
            return CRAFTTYPE_MANUAL_RECIPETYPE;
        }
        public boolean advanced(){
            return true;
        }
    }
            .register();
    public static final  SlimefunItem CONVERTOR=new EMachine(BASIC, AddItem.CONVERTOR,COMMON_TYPE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT),
            Material.STONE,500,24_000,
            mkMp(mkP(mkl("STONE"),mkl("DIRT")),3,
                    mkP(mkl("DIRT"),mkl("GRASS_BLOCK")),3,
                    mkP(mkl("GRASS_BLOCK"),mkl("PODZOL")),3,
                    mkP(mkl("PODZOL"),mkl("MYCELIUM")),3,
                    mkP(mkl("MYCELIUM"),mkl("ROOTED_DIRT")),3,
                    mkP(mkl("ROOTED_DIRT"),mkl("STONE")),3,
                    mkP(mkl("OBSIDIAN"),mkl("NETHERRACK")),3,
                    mkP(mkl("NETHERRACK"),mkl("CRIMSON_NYLIUM")),3,
                    mkP(mkl("CRIMSON_NYLIUM"),mkl("WARPED_NYLIUM")),3,
                    mkP(mkl("WARPED_NYLIUM"),mkl("MAGMA_BLOCK")),3,
                    mkP(mkl("MAGMA_BLOCK"),mkl("BASALT")),3,
                    mkP(mkl("BASALT"),mkl("OBSIDIAN")),3,
                    mkP(mkl("END_STONE"),mkl("ENDER_EYE")),3,
                    mkP(mkl("ENDER_EYE"),mkl("CHORUS_FRUIT")),3,
                    mkP(mkl("CHORUS_FRUIT"),mkl("CHORUS_FLOWER")),3,
                    mkP(mkl("CHORUS_FLOWER"),mkl("CHORUS_PLANT")),3,
                    mkP(mkl("CHORUS_PLANT"),mkl("ENDER_PEARL")),3,
                    mkP(mkl("ENDER_PEARL"),mkl("END_STONE")),3
                    ))
            .register();

    public static final  SlimefunItem VIRTUAL_KILLER=new VirtualKiller(BASIC, AddItem.VIRTUAL_KILLER,COMMON_TYPE,
            recipe(AddItem.BISILVER,AddItem.BISILVER,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.BISILVER,AddItem.BISILVER,
                    AddItem.BISILVER,null,"2PROGRAMMABLE_ANDROID_3_BUTCHER","2PROGRAMMABLE_ANDROID_3_BUTCHER",null,AddItem.BISILVER,
                    AddItem.BISILVER,null,"2PROGRAMMABLE_ANDROID_3_FISHERMAN","2PROGRAMMABLE_ANDROID_3_FISHERMAN",null,AddItem.BISILVER,
                    AddItem.BISILVER,null,"2PROGRAMMABLE_ANDROID_3_BUTCHER","2PROGRAMMABLE_ANDROID_3_BUTCHER",null,AddItem.BISILVER,
                    AddItem.BISILVER,null,"2PROGRAMMABLE_ANDROID_3_FISHERMAN","2PROGRAMMABLE_ANDROID_3_FISHERMAN",null,AddItem.BISILVER,
                    AddItem.BISILVER,AddItem.BISILVER,AddItem.LENGINE,AddItem.LENGINE,AddItem.BISILVER,AddItem.BISILVER), 3000,300,
            1)
            .register();
    public static final  SlimefunItem LVOID_GENERATOR=new TestGenerator(ENERGY, AddItem.LVOID_GENERATOR,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LFIELD,
                    AddItem.LPLATE,null,null,null,null,AddItem.LPLATE,
                    AddItem.ABSTRACT_INGOT,null,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,null,AddItem.ABSTRACT_INGOT,
                    AddItem.LPLATE,null,null,null,null,AddItem.LPLATE,
                    AddItem.LFIELD,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LFIELD),2333,180,
            1000,1145)
            .register();
    public static final  SlimefunItem SIMU_LVOID=new SimulateTestGenerator(ENERGY, AddItem.SIMU_LVOID,COMMON_TYPE,
            recipe(null,AddItem.LVOID_GENERATOR,AddItem.LVOID_GENERATOR,AddItem.LVOID_GENERATOR,AddItem.LVOID_GENERATOR,null,
                    AddItem.LVOID_GENERATOR,AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE,AddItem.LVOID_GENERATOR,
                    AddItem.LVOID_GENERATOR,AddItem.LPLATE,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.LPLATE,AddItem.LVOID_GENERATOR,
                    AddItem.HGTLPBBI,AddItem.LPLATE,AddItem.VIRTUAL_SPACE,AddItem.VIRTUAL_SPACE,AddItem.LPLATE,AddItem.HGTLPBBI,
                    AddItem.PDCECDMD,AddItem.SPACE_PLATE,setC(AddItem.TRANSMUTATOR_ROD,2),setC(AddItem.TRANSMUTATOR_ROD,2),AddItem.SPACE_PLATE,AddItem.PDCECDMD,
                    setC(AddItem.TRANSMUTATOR_ROD,2),setC(AddItem.TRANSMUTATOR_ROD,2),setC(AddItem.TRANSMUTATOR_ROD,2),setC(AddItem.TRANSMUTATOR_ROD,2),setC(AddItem.TRANSMUTATOR_ROD,2),setC(AddItem.TRANSMUTATOR_ROD,2))
            , 2333,180,1000,1145)
            .register().setOutput(setC(AddItem.SIMU_LVOID,5));
    public static final  SlimefunItem ENERGY_TRASH=new EnergyTrash(ENERGY, AddItem.ENERGY_TRASH,ANCIENT_ALTAR,
            recipe(AddItem.BUG,"ENERGY_REGULATOR",AddItem.BUG,"ENERGY_CONNECTOR","TRASH_CAN_BLOCK","ENERGY_CONNECTOR",
                    AddItem.BUG,"ENERGIZED_CAPACITOR",AddItem.BUG), 100_000_000)
            .register();
    public static final  SlimefunItem OPPO_GEN=new BiReactor(ENERGY, AddItem.OPPO_GEN,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.FALSE_,AddItem.TRUE_,AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.TRUE_,AddItem.FALSE_,AddItem.ABSTRACT_INGOT,null,
                    AddItem.ABSTRACT_INGOT,AddItem.LBOOLIZER,AddItem.LENGINE,AddItem.LENGINE,AddItem.LBOOLIZER,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.LBOOLIZER,AddItem.LBOOLIZER,AddItem.LBOOLIZER,AddItem.LBOOLIZER,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT), 50_000,5_000,10_000)
            .register();
    public static final  SlimefunItem ARC_REACTOR=new EGenerator(ENERGY, AddItem.ARC_REACTOR,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.PAGOLD,null,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.CHIP_CORE,AddItem.CHIP_CORE,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.SMELERY_CORE,AddItem.TECH_CORE,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.MASS_CORE,AddItem.METAL_CORE,AddItem.LSINGULARITY,AddItem.PAGOLD,
                    AddItem.BISILVER,AddItem.BISILVER,AddItem.BISILVER,AddItem.BISILVER,AddItem.BISILVER,AddItem.BISILVER), Material.BEACON,2_500_000,333_333,
            mkMp(
                mkP(    mkl(AddItem.PAGOLD),mkl("GOLD_INGOT")) ,6000,
                    mkP(    mkl(AddItem.BISILVER),mkl(AddItem.ABSTRACT_INGOT)) ,4800
            ))
            .register();

    public static final  SlimefunItem CHIP_REACTOR=new ChipReactor(ENERGY, AddItem.CHIP_REACTOR,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.SPACE_PLATE,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.SPACE_PLATE,null,
                    AddItem.SPACE_PLATE,AddItem.PDCECDMD,AddItem.LASER,AddItem.LASER,AddItem.PDCECDMD,AddItem.SPACE_PLATE,
                    AddItem.TECH_CORE,AddItem.CHIP_CORE,AddItem.ADVANCED_CHIP_MAKER,AddItem.ADVANCED_CHIP_MAKER,AddItem.CHIP_CORE,AddItem.TECH_CORE,
                    AddItem.TECH_CORE,AddItem.HGTLPBBI,AddItem.LASER,AddItem.LASER,AddItem.HGTLPBBI,AddItem.TECH_CORE,
                    AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT), 200_000_000,0.1,300)
            .register();
    public static final SlimefunItem ENERGY_AMPLIFIER=new EnergyAmplifier(ENERGY,AddItem.ENERGY_AMPLIFIER,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),1_000_000_000,2.0)
            .register();
    public static final  SlimefunItem ENERGY_PIPE=new EnergyPipe(ENERGY, AddItem.ENERGY_PIPE,ENHANCED_CRAFTING_TABLE,
            recipe("COPPER_INGOT","SILVER_INGOT","COPPER_INGOT","COPPER_INGOT","SILVER_INGOT","COPPER_INGOT",
                    "COPPER_INGOT","SILVER_INGOT","COPPER_INGOT"), 100_000,0.10f)
            .register();
    public static final  SlimefunItem ENERGY_PIPE_PLUS=new EnergyPipe(ENERGY, AddItem.ENERGY_PIPE_PLUS,COMMON_TYPE,
            recipe(null,AddItem.ENERGY_PIPE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ENERGY_PIPE,null,
                    null,AddItem.ENERGY_PIPE,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.ENERGY_PIPE,null,
                    null,AddItem.ENERGY_PIPE,AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.ENERGY_PIPE,null,
                    null,AddItem.ENERGY_PIPE,AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.ENERGY_PIPE,null,
                    null,AddItem.ENERGY_PIPE,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.ENERGY_PIPE,null,
                    null,AddItem.ENERGY_PIPE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ENERGY_PIPE,null),1_000_000_000,0.0f)
            .register().setOutput(setC(AddItem.ENERGY_PIPE_PLUS,6));
    public static final  SlimefunItem ENERGY_STORAGE_NONE=new EnergyStorage(ENERGY, AddItem.ENERGY_STORAGE_NONE,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.NOLOGIC,AddItem.ABSTRACT_INGOT,AddItem.NOLOGIC,"ENERGY_CONNECTOR",AddItem.NOLOGIC,
                    AddItem.ABSTRACT_INGOT,AddItem.NOLOGIC,AddItem.ABSTRACT_INGOT), 1_000_000_000, EnergyNetComponentType.NONE)
            .register();
    public static final  SlimefunItem ENERGY_STORAGE_IN=new EnergyStorage(ENERGY, AddItem.ENERGY_STORAGE_IN,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.LOGIC,AddItem.ABSTRACT_INGOT,AddItem.LOGIC,"CARBONADO_EDGED_CAPACITOR",AddItem.LOGIC,
                    AddItem.ABSTRACT_INGOT,AddItem.LOGIC,AddItem.ABSTRACT_INGOT), 1_000_000_000,EnergyNetComponentType.CONSUMER)
            .register();
    public static final  SlimefunItem ENERGY_STORAGE_IO=new EnergyIOStorage(ENERGY, AddItem.ENERGY_STORAGE_IO,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.NOLOGIC,"ENERGIZED_CAPACITOR",AddItem.NOLOGIC,
                    AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.ABSTRACT_INGOT),2_000_000)
            .register();

    public static final  SlimefunItem ADJ_COLLECTOR=new AdjacentEnergyCollector(ENERGY, AddItem.ADJ_COLLECTOR,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.LOGIC,AddItem.ABSTRACT_INGOT,AddItem.LOGIC,"ENERGY_REGULATOR",AddItem.LOGIC,
                    AddItem.ABSTRACT_INGOT,"SMALL_CAPACITOR",AddItem.ABSTRACT_INGOT), 1_000_000)
            .register();
    public static final  SlimefunItem ADJ_COLLECTOR_PLUS=new AdjacentEnergyCollector(ENERGY, AddItem.ADJ_COLLECTOR_PLUS,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ADJ_COLLECTOR,"ENERGIZED_CAPACITOR",AddItem.ADJ_COLLECTOR,"ENERGIZED_CAPACITOR",null,"ENERGIZED_CAPACITOR",
                    AddItem.ADJ_COLLECTOR,"ENERGIZED_CAPACITOR",AddItem.ADJ_COLLECTOR),1_000_000_000)

            .register()
            .setOutput(setC(AddItem.ADJ_COLLECTOR_PLUS,2));
    public static final  SlimefunItem LINE_COLLECTOR=new LineEnergyCollector(ENERGY, AddItem.LINE_COLLECTOR,COMMON_TYPE,
            recipe("ENERGY_REGULATOR","ENERGY_CONNECTOR",AddItem.LPLATE,AddItem.LPLATE,"ENERGY_CONNECTOR","ENERGY_REGULATOR",
                    "ENERGY_CONNECTOR",AddItem.LMOTOR,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LMOTOR,"ENERGY_CONNECTOR",
                    AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.ADJ_COLLECTOR,AddItem.ADJ_COLLECTOR,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.ADJ_COLLECTOR,AddItem.ADJ_COLLECTOR,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,
                    "ENERGY_CONNECTOR",AddItem.LENGINE,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LENGINE,"ENERGY_CONNECTOR",
                    "ENERGY_REGULATOR","ENERGY_CONNECTOR",AddItem.LPLATE,AddItem.LPLATE,"ENERGY_CONNECTOR","ENERGY_REGULATOR"), 16_000_000)
            .register();
    public static final  SlimefunItem LINE_COLLECTOR_PLUS=new LineEnergyCollector(ENERGY, AddItem.LINE_COLLECTOR_PLUS,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.LINE_COLLECTOR,"ENERGIZED_CAPACITOR",AddItem.LINE_COLLECTOR,"ENERGIZED_CAPACITOR",null,"ENERGIZED_CAPACITOR",
                    AddItem.LINE_COLLECTOR,"ENERGIZED_CAPACITOR",AddItem.LINE_COLLECTOR),2_000_000_000)

            .register()
            .setOutput(setC(AddItem.LINE_COLLECTOR_PLUS,2));
    public static final  SlimefunItem ADJ_CHARGER=new AdjacentEnergyCharger(ENERGY, AddItem.ADJ_CHARGER,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.NOLOGIC,AddItem.ABSTRACT_INGOT,AddItem.NOLOGIC,"CHARGING_BENCH",AddItem.NOLOGIC,
                    AddItem.ABSTRACT_INGOT,"SMALL_CAPACITOR",AddItem.ABSTRACT_INGOT), 1_000_000)
            .register();
    public static final  SlimefunItem ADJ_CHARGER_PLUS=new AdjacentEnergyCharger(ENERGY, AddItem.ADJ_CHARGER_PLUS,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ADJ_CHARGER,"CARBONADO_EDGED_CAPACITOR",AddItem.ADJ_CHARGER,"CARBONADO_EDGED_CAPACITOR",null,"CARBONADO_EDGED_CAPACITOR",
                    AddItem.ADJ_CHARGER,"CARBONADO_EDGED_CAPACITOR",AddItem.ADJ_CHARGER),1_000_000_000)

            .register()
            .setOutput(setC(AddItem.ADJ_CHARGER_PLUS,2));
    public static final  SlimefunItem LINE_CHARGER=new LineEnergyCharger(ENERGY, AddItem.LINE_CHARGER,COMMON_TYPE,
            recipe("ENERGY_REGULATOR","ENERGY_CONNECTOR",AddItem.LPLATE,AddItem.LPLATE,"ENERGY_CONNECTOR","ENERGY_REGULATOR",
                    "ENERGY_CONNECTOR",AddItem.LDIGITIZER,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.LDIGITIZER,"ENERGY_CONNECTOR",
                    AddItem.LPLATE,AddItem.STAR_GOLD_INGOT,AddItem.ADJ_CHARGER,AddItem.ADJ_CHARGER,AddItem.STAR_GOLD_INGOT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.STAR_GOLD_INGOT,AddItem.ADJ_CHARGER,AddItem.ADJ_CHARGER,AddItem.STAR_GOLD_INGOT,AddItem.LPLATE,
                    "ENERGY_CONNECTOR",AddItem.LDIGITIZER,AddItem.LENGINE,AddItem.LENGINE,AddItem.LDIGITIZER,"ENERGY_CONNECTOR",
                    "ENERGY_REGULATOR","ENERGY_CONNECTOR",AddItem.LPLATE,AddItem.LPLATE,"ENERGY_CONNECTOR","ENERGY_REGULATOR"),1_000_000)
            .register()
            .setOutput(AddItem.LINE_CHARGER);

    public static final  SlimefunItem LINE_CHARGER_PLUS=new LineEnergyCharger(ENERGY, AddItem.LINE_CHARGER_PLUS,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.LINE_CHARGER,"CARBONADO_EDGED_CAPACITOR",AddItem.LINE_CHARGER,"CARBONADO_EDGED_CAPACITOR",null,"CARBONADO_EDGED_CAPACITOR",
                    AddItem.LINE_CHARGER,"CARBONADO_EDGED_CAPACITOR",AddItem.LINE_CHARGER),1_000_000_000)

            .register()
            .setOutput(setC(AddItem.LINE_CHARGER_PLUS,2));
    public static final  SlimefunItem CHUNK_CHARGER=new ChunkEnergyCharger(ENERGY, AddItem.CHUNK_CHARGER,COMMON_TYPE,
            recipe("4ENERGY_REGULATOR","8ENERGY_CONNECTOR",AddItem.LPLATE,AddItem.LPLATE,"8ENERGY_CONNECTOR","4ENERGY_REGULATOR",
                    "8ENERGY_CONNECTOR",setC(AddItem.LSINGULARITY,4),setC(AddItem.STAR_GOLD_INGOT,2),setC(AddItem.STAR_GOLD_INGOT,2),setC(AddItem.LSINGULARITY,4),"8ENERGY_CONNECTOR",
                    AddItem.LPLATE,setC(AddItem.STAR_GOLD_INGOT,2),setC(AddItem.ATOM_INGOT,16),setC(AddItem.ATOM_INGOT,16),setC(AddItem.STAR_GOLD_INGOT,2),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LDIGITIZER,AddItem.LSCHEDULER,AddItem.LSCHEDULER,AddItem.LDIGITIZER,AddItem.LPLATE,
                    "8ENERGY_CONNECTOR",AddItem.LDIGITIZER,AddItem.TECH_CORE,AddItem.TECH_CORE,AddItem.LDIGITIZER,"8ENERGY_CONNECTOR",
                    "4ENERGY_REGULATOR","8ENERGY_CONNECTOR",AddItem.LPLATE,AddItem.LPLATE,"8ENERGY_CONNECTOR","4ENERGY_REGULATOR"),1_000_000_000)
            .register();
    public static final  SlimefunItem SPECIAL_CRAFTER=new SpecialTypeCrafter(BASIC, AddItem.SPECIAL_CRAFTER,COMMON_TYPE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.LOGIC,AddItem.LOGIC,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,
                    AddItem.LFIELD,AddItem.LDIGITIZER,AddItem.LPLATE,AddItem.LPLATE,AddItem.LMOTOR,AddItem.LFIELD,
                    AddItem.LOGIC,AddItem.LPLATE,AddItem.LCRAFT,AddItem.LIOPORT,AddItem.LPLATE,AddItem.LOGIC,
                    AddItem.LOGIC,AddItem.LPLATE,AddItem.LSCHEDULER,AddItem.LCRAFT,AddItem.LPLATE,AddItem.LOGIC,
                    AddItem.LFIELD,AddItem.LMOTOR,AddItem.LPLATE,AddItem.LPLATE,AddItem.LDIGITIZER,AddItem.LFIELD,
                    AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.LOGIC,AddItem.LOGIC,AddItem.LFIELD,AddItem.ABSTRACT_INGOT)
            , Material.NETHER_STAR,0,300,20_000,new HashSet<>(){{
                if(AddDepends.INFINITYWORKBENCH_TYPE!=null)
                    add(AddDepends.INFINITYWORKBENCH_TYPE);
                if(AddDepends.VOIDHARVEST!=null)
                    add(AddDepends.VOIDHARVEST);
            }})
            .register();
    public static final  SlimefunItem STAR_SMELTERY=new AEMachine(ADVANCED, AddItem.STAR_SMELTERY,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LFIELD,
                    AddItem.STAR_GOLD_INGOT,setC(AddItem.LPLATE,1),"ELECTRIC_INGOT_FACTORY_3","ELECTRIC_INGOT_FACTORY_3",setC(AddItem.LPLATE,1),AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.LMOTOR,"CARBONADO_EDGED_FURNACE","CARBONADO_EDGED_FURNACE",AddItem.LMOTOR,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.LMOTOR,"CARBONADO_EDGED_FURNACE","CARBONADO_EDGED_FURNACE",AddItem.LMOTOR,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,setC(AddItem.LPLATE,1),"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",setC(AddItem.LPLATE,1),AddItem.STAR_GOLD_INGOT,
                    AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LFIELD), Material.STONE,18_000,180_000, (List<Pair<Object, Integer>>) null ){
        int recipetime=120;
        {
            STARSMELTERY.relatedTo((input,out)->{
                this.machineRecipes.add(MachineRecipeUtils.stackFrom(recipetime,input,new ItemStack[]{out}));
            },(input,out)->{
                this.machineRecipes.removeIf(recipe->recipe.getOutput().length>=1&&CraftUtils.matchItemStack(recipe.getOutput()[0],out,true));
            });
        }
        public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
            if(flow==ItemTransportFlow.WITHDRAW){
                return this.getOutputSlots();
            }else {
                if(item==null||item.getType().isAir()||item.getType().getMaxStackSize()==1){
                    return this.getInputSlots();
                }
                int[] inputSlot=getInputSlots();
                ItemCounter push=CraftUtils.getCounter(item);
                ItemStack itemInSlot;
                for(int i =0;i<inputSlot.length;++i){
                    itemInSlot=menu.getItemInSlot(inputSlot[i]);
                    if(itemInSlot==null||itemInSlot.getType()!=item.getType()){
                        continue;
                    }
                    if(CraftUtils.matchItemStack(itemInSlot,push,false)){
                        return new int[]{inputSlot[i]};
                    }
                }
                return inputSlot;
            }
        }
    }
            .register();
    public static final  SlimefunItem SEQ_CONSTRUCTOR=new SequenceConstructor(BASIC, AddItem.SEQ_CONSTRUCTOR,COMMON_TYPE,
            recipe(setC(AddItem.STAR_GOLD,1),AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,setC(AddItem.STAR_GOLD,1),
                    AddItem.ABSTRACT_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.STAR_GOLD_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.LBOOLIZER,null,AddItem.LENGINE,AddItem.LENGINE,null,AddItem.LBOOLIZER,
                    AddItem.LBOOLIZER,AddItem.LIOPORT,AddItem.LSCHEDULER,AddItem.LDIGITIZER,AddItem.LIOPORT,AddItem.LBOOLIZER,
                    AddItem.ABSTRACT_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LCRAFT,AddItem.LCRAFT,AddItem.STAR_GOLD_INGOT,AddItem.ABSTRACT_INGOT,
                    setC(AddItem.STAR_GOLD,1),AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,setC(AddItem.STAR_GOLD,1)),new ItemStack(Material.NETHERITE_BLOCK) ,1280,8848,
            mkMp(
                    //重金核心铜铁金 下界  重金核心-------
                    //镁铅锡锌 冶炼核心-------
                    //银铝 红石 钻石 科技核心-------
                    //煤炭 青金石 石英 绿宝石 物质核心-------
                    mkP(mkl("512COPPER_INGOT","512IRON_INGOT","512GOLD_INGOT","512NETHERITE_INGOT"),mkl(AddItem.METAL_CORE)),3,
                    mkP(mkl("512MAGNESIUM_INGOT","512LEAD_INGOT","512TIN_INGOT","512ZINC_INGOT"),mkl(AddItem.SMELERY_CORE)),3,
                    mkP(mkl("512SILVER_INGOT","512ALUMINUM_INGOT","512DIAMOND","512REDSTONE"),mkl(AddItem.TECH_CORE)),3,
                    mkP(mkl("512COAL","512LAPIS_LAZULI","512QUARTZ","512EMERALD"),mkl(AddItem.MASS_CORE)),3,
                    mkP(mkl(setC(AddItem.DIMENSIONAL_SHARD,1024)),mkl(AddItem.DIMENSIONAL_SINGULARITY)),3,
                    mkP(mkl(AddItem.METAL_CORE,AddItem.SMELERY_CORE,AddItem.TECH_CORE,AddItem.MASS_CORE),mkl(AddItem.EASYSTACKMACHINE)),3
            ))
            .register();




    public static final  SlimefunItem CHIP_MAKER=new EMachine(ADVANCED, AddItem.CHIP_MAKER,COMMON_TYPE,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.LENGINE,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.LENGINE,AddItem.STAR_GOLD_INGOT,
                    null,AddItem.STAR_GOLD_INGOT,AddItem.LOGIC,AddItem.NOLOGIC,AddItem.STAR_GOLD_INGOT,null,
                    AddItem.LPLATE,null,AddItem.LOGIC,AddItem.NOLOGIC,null,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LBOOLIZER,AddItem.LDIGITIZER,AddItem.LDIGITIZER,AddItem.LBOOLIZER,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LBOOLIZER,AddItem.LSCHEDULER,AddItem.LSCHEDULER,AddItem.LBOOLIZER,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE), Material.POTATO,0,0,
            mkMp(
                mkP(mkl(setC(AddItem.CHIP_INGOT,6),AddItem.EXISTE),mkl( ChipCardCode.CHIP_0)),4,
                    mkP(mkl(setC(AddItem.CHIP_INGOT,6),AddItem.UNIQUE),mkl( ChipCardCode.CHIP_1)),4,
                    mkP(mkl(AddItem.LSCHEDULER,ChipCardCode.CHIP_FINAL),mkl(setC(AddItem.CHIP_CORE,2))),4
            )).register();

    public static final SlimefunItem CHIP_CONSUMER=new ChipConsumer(ADVANCED,AddItem.CHIP_CONSUMER,COMMON_TYPE,
            recipe(AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,1),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LBOOLIZER,1),AddItem.ABSTRACT_INGOT,
                    AddItem.CHIP_INGOT,AddItem.LFIELD,setC(AddItem.LOGIGATE,1),AddItem.LDIGITIZER,AddItem.LFIELD,AddItem.CHIP_INGOT,
                    AddItem.CHIP_INGOT,AddItem.LFIELD,AddItem.LDIGITIZER,setC(AddItem.LOGIGATE,1),AddItem.LFIELD,AddItem.CHIP_INGOT,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,1),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LBOOLIZER,1),AddItem.ABSTRACT_INGOT,
                    AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE),4_000,500)
            .register();

    public static final  SlimefunItem CHIP_BICONSUMER=new ChipBiConsumer(ADVANCED, AddItem.CHIP_BICONSUMER,COMMON_TYPE,
            recipe(AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LDIGITIZER,1),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LDIGITIZER,1),AddItem.ABSTRACT_INGOT,
                    AddItem.LPLATE,AddItem.LFIELD,setC(AddItem.LOGIGATE,1),setC(AddItem.UNIQUE,1),AddItem.LFIELD,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LFIELD,setC(AddItem.EXISTE,1),setC(AddItem.LOGIGATE,1),AddItem.LFIELD,AddItem.LPLATE,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LDIGITIZER,1),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LDIGITIZER,1),AddItem.ABSTRACT_INGOT,
                    AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.LENGINE),8_000,1_000)
            .register();
    public static final SlimefunItem EASYSTACKMACHINE=new StackMachine(BASIC,AddItem.EASYSTACKMACHINE,NULL,
            formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),Material.IRON_PICKAXE,
            20_000,4_000_000,0.5)
            .register();
    public static final SlimefunItem STACKMACHINE=new StackMachine(ADVANCED,AddItem.STACKMACHINE,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),Material.IRON_PICKAXE,
            2_000,40_000_000,2.0)
            .register();
    //
    public static final  SlimefunItem ADVANCED_CHIP_MAKER=new ChipCopier(ADVANCED, AddItem.ADVANCED_CHIP_MAKER,COMMON_TYPE,
            recipe(null,AddItem.REINFORCED_CHIP_INGOT,AddItem.CHIP_MAKER,AddItem.CHIP_MAKER,AddItem.REINFORCED_CHIP_INGOT,null,
                    null,AddItem.REINFORCED_CHIP_INGOT,AddItem.CHIP_MAKER,AddItem.CHIP_MAKER,AddItem.REINFORCED_CHIP_INGOT,null,
                    AddItem.HGTLPBBI,AddItem.LPLATE,AddItem.LASER,AddItem.LASER,AddItem.LPLATE,AddItem.HGTLPBBI,
                    AddItem.BISILVER,AddItem.SPACE_PLATE,setC(AddItem.LSINGULARITY,1),setC(AddItem.LSINGULARITY,1),AddItem.SPACE_PLATE,AddItem.BISILVER,
                    AddItem.BISILVER,AddItem.SPACE_PLATE,setC(AddItem.LSINGULARITY,1),setC(AddItem.LSINGULARITY,1),AddItem.SPACE_PLATE,AddItem.BISILVER,
                    AddItem.PDCECDMD,AddItem.LPLATE,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.LPLATE,AddItem.PDCECDMD))
            .register();

    public static final  SlimefunItem ITEM_OP=new ItemOperator(TOOLS_FUNCTIONAL, AddItem.ITEM_OP,ENHANCED_CRAFTING_TABLE,
            recipe("NAME_TAG","CRYING_OBSIDIAN","NAME_TAG","AUTO_DISENCHANTER_2","AUTO_ANVIL_2","AUTO_ENCHANTER_2",
                    "ENCHANTING_TABLE","ANVIL","SMITHING_TABLE"), 0,0)
            .register();

    public static final  SlimefunItem TNT_GEN=new TntGenerator(VANILLA, AddItem.TNT_GEN,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.LOGIGATE,"OBSERVER",AddItem.LOGIGATE,"STICKY_PISTON",AddItem.REDSTONE_ENGINE,"STICKY_PISTON",
                    AddItem.LOGIGATE,"NOTE_BLOCK",AddItem.LOGIGATE))
            .register();
    public static final SlimefunItem BEDROCK_BREAKER=new CustomProps(VANILLA, AddItem.BEDROCK_BREAKER, ENHANCED_CRAFTING_TABLE,
            recipe("TNT", "LEVER", "TNT", "PISTON", AddItem.LOGIGATE, "PISTON",
                    AddItem.NOLOGIC, AddItem.BUG, AddItem.NOLOGIC), null) {
        protected ItemConsumer CONSUMED=CraftUtils.getConsumer(new ItemStack(Material.PISTON));
        @Override
        public void onClickAction(PlayerRightClickEvent event) {
            Optional<Block> b=event.getClickedBlock();
            if(b.isPresent()&&event.getPlayer()!=null){
                Block block=b.get();
                Location loc=block.getLocation();
                    //不是sf方块
                if(WorldUtils.hasPermission(event.getPlayer(),loc,Interaction.BREAK_BLOCK,Interaction.PLACE_BLOCK)&&canUse(event.getPlayer(),false)){
                    SlimefunItem item;
                    if((item=DataCache.getSfItem(loc))==null||item==LOGIC_CORE||item==ENDFRAME_MACHINE||item==ENDDUPE_MG){
                        if(WorldUtils.consumeItem(event.getPlayer(),CONSUMED)){
                            if(WorldUtils.breakVanillaBlockByPlayer(block,event.getPlayer(),true,false)){
                                if(item!=null){
                                    WorldUtils.removeSlimefunBlock(loc,event.getPlayer(),false);
                                }
                                sendMessage(event.getPlayer(),"&a已成功破坏方块");
                            }else{
                                sendMessage(event.getPlayer(),"&c抱歉,您没有在这里破坏方块的权限");
                            }
                        }else{

                            sendMessage(event.getPlayer(),"&c所需物品不足!");

                        }
                    }else{
                        sendMessage(event.getPlayer(),"&c该方块是粘液方块!不能被该道具破坏");
                    }
                }else{
                    sendMessage(event.getPlayer(),"&c抱歉,您没有在这里使用该物品的权限");
                }

            }
            event.cancel();
        }
    }
            .register();
    public static final SlimefunItem SUPERSPONGE=new CustomProps(VANILLA,AddItem.SUPERSPONGE,ENHANCED_CRAFTING_TABLE,
            recipe("SPONGE","PISTON","SPONGE",AddItem.WITHERPROOF_REDS,AddItem.REDSTONE_ENGINE,AddItem.WITHERPROOF_REDS,
                    "WET_SPONGE","PISTON","WET_SPONGE")){
        protected final int SEARCH_RANGE=10;
        protected final Set<Player> COOL_DOWN=ConcurrentHashMap.newKeySet();
        public void onClickAction(PlayerRightClickEvent event){
            Player p=event.getPlayer();
            if(p!=null){
                if(COOL_DOWN.contains(p)){
                    sendMessage(p,"&c物品冷却中");
                }else{

                    Location loc=p.getLocation();
                    if(WorldUtils.hasPermission(p,loc,Interaction.INTERACT_BLOCK,Interaction.PLACE_BLOCK)&&canUse(p,true)){
                        ItemStack stack= event.getItem();
                        stack.setAmount(stack.getAmount()-1);
                        forceGive(p,AddItem.SUPERSPONGE_USED,1);
                        final HashSet<Block> liquids=new HashSet<>();
                        final HashSet<Block> blockInLiquids=new HashSet<>();
                        final HashSet<Block> forceLiquids=new HashSet<>();
                        Schedules.launchSchedules(()->{
                            COOL_DOWN.add(p);
                            try{
                                int dx=loc.getBlockX();
                                int dy=loc.getBlockY();
                                int dz=loc.getBlockZ();
                                sendMessage(p,"&a开始搜索");
                                for(int i=-SEARCH_RANGE;i<=SEARCH_RANGE;i++){
                                    for(int j=-SEARCH_RANGE;j<=SEARCH_RANGE;j++){
                                        for(int k=-SEARCH_RANGE;k<=SEARCH_RANGE;k++){
                                            Block checkBlock=loc.getWorld().getBlockAt(dx+i,dy+j,dz+k);
                                            if(checkBlock!=null){
                                                if(WorldUtils.isLiquid( checkBlock)){
                                                    liquids.add(checkBlock);
                                                }
                                                else if(WorldUtils.isWaterLogged(checkBlock)){
                                                    blockInLiquids.add(checkBlock);
                                                }else if(WorldUtils.BLOCK_MUST_WATERLOGGED.contains(checkBlock.getType())){
                                                    forceLiquids.add(checkBlock);
                                                }
                                            }

                                        }
                                    }
                                }
                                if(!liquids.isEmpty()||!blockInLiquids.isEmpty()||!forceLiquids.isEmpty()){
                                    sendMessage(p,"&a搜索完成,正在吸取液体");
                                    BukkitUtils.executeSync(()->{
                                        List<BlockState> blocksToBeChanged=new ArrayList<>(liquids.size()+blockInLiquids.size()+2);
                                        for(Block b:liquids){
                                            blocksToBeChanged.add(WorldUtils.getBlockStateNoSnapShot(b));
                                        }
                                        for(Block b:blockInLiquids){
                                            blocksToBeChanged.add(WorldUtils.getBlockStateNoSnapShot(b));
                                        }
                                        SpongeAbsorbEvent spongeAbsorbEvent=new SpongeAbsorbEvent(loc.getBlock(),blocksToBeChanged);
                                        Bukkit.getPluginManager().callEvent(spongeAbsorbEvent);
                                        if(spongeAbsorbEvent.isCancelled()){
                                            sendMessage(p,"&c抱歉,你没有在这里吸取液体的权限");
                                        }else {
                                            for(Block b:liquids){
                                                b.setType(Material.AIR);
                                            }
                                            for(Block b:blockInLiquids){
                                                BlockData data=b.getBlockData();
                                                if(data instanceof Waterlogged wl){
                                                    wl.setWaterlogged(false);
                                                    b.setBlockData(data,true);
                                                }
                                            }

                                            sendMessage(p,"&a成功移除液体!");
                                        }
                                        if(!forceLiquids.isEmpty()){
                                            for(Block b:forceLiquids){
                                                if(WorldUtils.testVanillaBlockBreakPermission(b,p,false)){
                                                    b.setType(Material.AIR);
                                                }
                                            }
                                            sendMessage(p,"&a成功移除海草和海带!");
                                        }
                                    });
                                }else{
                                    sendMessage(p,"&c附近没有剩余的液体");
                                }
                            }finally {
                                COOL_DOWN.remove(p);
                            }
                        },0,false,0);
                    }
                    else{
                        sendMessage(p,"&c抱歉,你没有在这里吸水的权限");
                    }

                }
            }
            event.cancel();
        }
    }
            .register();
    public static final SlimefunItem SUPERSPONGE_USED=new MaterialItem(FUNCTIONAL,AddItem.SUPERSPONGE_USED,NULL,NULL_RECIPE.clone())
            .register();
    public static final  SlimefunItem VIRTUAL_EXPLORER=new VirtualExplorer(VANILLA, AddItem.VIRTUAL_EXPLORER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,"NETHERITE_INGOT","NETHERITE_INGOT","NETHERITE_INGOT","NETHERITE_INGOT",null,
                    "NETHERITE_INGOT","ELYTRA",AddItem.LSINGULARITY,AddItem.LIOPORT,"ELYTRA","NETHERITE_INGOT",
                    "NETHERITE_INGOT","COBALT_PICKAXE",AddItem.VIRTUAL_SPACE,AddItem.LSINGULARITY,"COBALT_PICKAXE","NETHERITE_INGOT",
                    "NETHERITE_INGOT","SLIME_LEGGINGS",AddItem.LPLATE,AddItem.LPLATE,"SLIME_LEGGINGS","NETHERITE_INGOT",
                    "NETHERITE_INGOT","SLIME_BOOTS","STEEL_THRUSTER","STEEL_THRUSTER","SLIME_BOOTS","NETHERITE_INGOT"), 12_500,1250)
            .register();

    //Material Generators
    public static final SlimefunItem MAGIC_STONE=new SMGenerator(GENERATORS, AddItem.MAGIC_STONE,ENHANCED_CRAFTING_TABLE,
            recipe("IRON_PICKAXE","LAVA_BUCKET","IRON_PICKAXE",
                    "PISTON",AddItem.LOGIGATE,"PISTON",
                    "COBALT_PICKAXE","WATER_BUCKET","COBALT_PICKAXE"),16,500,33,
            randItemStackFactory(
                    mkMp("24COBBLESTONE",75,
                            "2COAL",5,
                            "2REDSTONE",5,
                            "IRON_INGOT",5,
                            "4LAPIS_LAZULI",4,
                            "GOLD_INGOT",3,
                            "DIAMOND",2,
                            "EMERALD",1
                    )
            ))
            .register();
    public static final SlimefunItem BOOL_MG = new MMGenerator(GENERATORS, AddItem.BOOL_MG, COMMON_TYPE,
            recipe(null,AddItem.FALSE_,AddItem.LFIELD,AddItem.LFIELD,AddItem.FALSE_,null,
                    AddItem.TRUE_,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.TRUE_,
                    AddItem.LFIELD,AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,1),setC(AddItem.LOGIGATE,1),AddItem.ABSTRACT_INGOT,AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.ABSTRACT_INGOT,setC(AddItem.LOGIGATE,1),setC(AddItem.LBOOLIZER,1),AddItem.ABSTRACT_INGOT,AddItem.LFIELD,
                    AddItem.TRUE_,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.TRUE_,
                    null,AddItem.FALSE_,AddItem.LFIELD,AddItem.LFIELD,AddItem.FALSE_,null), 6, 1919, 256,
            new PairList<>(){{
                put(mkl(AddItem.TRUE_),mkl(setC(AddItem.TRUE_,114514)));
                put(mkl(AddItem.FALSE_),mkl(setC(AddItem.FALSE_,1919810)));
                put(mkl(AddItem.LBOOLIZER),mkl(setC(AddItem.LBOOLIZER,1)));
            }})
            .register();

    public static final SlimefunItem OVERWORLD_MINER=new SMGenerator(GENERATORS, AddItem.OVERWORLD_MINER,ENHANCED_CRAFTING_TABLE,
            recipe("REINFORCED_PLATE",AddItem.MAGIC_STONE,"REINFORCED_PLATE",
                    AddItem.UNIQUE,AddItem.LENGINE,AddItem.EXISTE
                    ,AddItem.WORLD_FEAT,AddItem.MAGIC_STONE,AddItem.WORLD_FEAT
            ),12,2_500,400,
            randItemStackFactory(
                    mkMp("32COBBLESTONE",40,
                            "3COAL",9,
                            "3REDSTONE",9,
                            "3IRON_INGOT",9,
                            "6LAPIS_LAZULI",9,
                            "3GOLD_INGOT",9,
                            "3DIAMOND",8,
                            "3EMERALD",7
                    )
            ))
            .register();
    public static final SlimefunItem NETHER_MINER=new SMGenerator(GENERATORS, AddItem.NETHER_MINER,COMMON_TYPE,
            recipe(null,setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),null,
                    null,AddItem.LPLATE,AddItem.OVERWORLD_MINER,AddItem.OVERWORLD_MINER,AddItem.LPLATE,null,
                    null,AddItem.LPLATE,AddItem.LENGINE,AddItem.LENGINE,AddItem.LPLATE,null,
                    null,AddItem.NETHER_FEAT,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.NETHER_FEAT,null
                  ),10,3_000,600,
            randItemStackFactory(
                    mkMp(
                            "32NETHERRACK",30,
                            "6QUARTZ",40,
                            "16MAGMA_BLOCK",7,
                            "16BLACKSTONE",7,
                            "16BASALT",7,
                            "8NETHER_WART",7,
                            "4ANCIENT_DEBRIS",2
                    )
            ))
            .register();
    public static final SlimefunItem END_MINER=new SMGenerator(GENERATORS, AddItem.END_MINER,COMMON_TYPE,
            recipe(null,AddItem.LMOTOR,AddItem.LFIELD,AddItem.LFIELD,AddItem.LMOTOR,null,
                    null,AddItem.LPLATE,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.LPLATE,null,
                    null,AddItem.LPLATE,AddItem.NETHER_MINER,AddItem.NETHER_MINER,AddItem.LPLATE,null,
                    null,AddItem.LPLATE,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.LPLATE,null,
                    AddItem.END_FEAT,AddItem.LENGINE,AddItem.END_FEAT,AddItem.END_FEAT,AddItem.LENGINE,AddItem.END_FEAT
            ),8,6_000,1000,
            randItemStackFactory(
                    mkMp(
                            "32END_STONE",150,
                            "10OBSIDIAN",100,
                            "2CHORUS_FLOWER",30,
                            "SHULKER_SHELL",50,
                            "DRAGON_BREATH",40,
                            AddItem.DIMENSIONAL_SHARD,20,
                            AddItem.STAR_GOLD,10
                    )
            ))
            .register();
    public static final SlimefunItem DIMENSION_MINER=new SMGenerator(GENERATORS, AddItem.DIMENSION_MINER,COMMON_TYPE  ,
            recipe("GPS_TRANSMITTER_2",AddItem.STAR_GOLD,AddItem.LFIELD,AddItem.LFIELD,AddItem.STAR_GOLD,"GPS_TRANSMITTER_2",
                    AddItem.DIMENSIONAL_SHARD,AddItem.STAR_GOLD,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.STAR_GOLD,AddItem.DIMENSIONAL_SHARD,
                    AddItem.LFIELD,null,AddItem.NETHER_MINER,AddItem.END_MINER,null,AddItem.LFIELD,
                    AddItem.LFIELD,null,AddItem.LENGINE,AddItem.LENGINE,null,AddItem.LFIELD,
                    AddItem.DIMENSIONAL_SHARD,AddItem.LIOPORT,AddItem.LENGINE,AddItem.LENGINE,AddItem.LIOPORT,AddItem.DIMENSIONAL_SHARD,
                    "GPS_TRANSMITTER_2",AddItem.STAR_GOLD,AddItem.LFIELD,AddItem.LFIELD,AddItem.STAR_GOLD,"GPS_TRANSMITTER_2"),6,18_000,1_800,
            randItemStackFactory(
                    mkMp(
                            "64COBBLESTONE",1,
                            "64END_STONE",1,
                            "64NETHERRACK",1
                    )
            ),

            randItemStackFactory(
                    mkMp(
                            AddItem.DIMENSIONAL_SHARD,3,
                            AddItem.STAR_GOLD,1
                    )
            ),
            randItemStackFactory(
                    mkMp(
                            "12COAL",8,
                            "16REDSTONE",8,
                            "12IRON_INGOT",8,
                            "16LAPIS_LAZULI",8,
                            "12GOLD_INGOT",8,
                            "12DIAMOND",8,
                            "12EMERALD",8,
                            "16QUARTZ",8,
                            "4ANCIENT_DEBRIS",8
                    )
            ))
            .register();
    //TODO 完成这块

    public static final SlimefunItem MAGIC_PLANT = new MMGenerator(GENERATORS, AddItem.MAGIC_PLANT, ENHANCED_CRAFTING_TABLE,
            recipe(Material.DIAMOND_HOE,"WATER_BUCKET",Material.DIAMOND_HOE,
                    "OBSERVER",AddItem.LOGIGATE,"OBSERVER",
                    Material.BONE_BLOCK,"PISTON",Material.BONE_BLOCK), 45, 1_000, 33,
            new PairList<>(){{
                put(mkl("WHEAT_SEEDS"),mkl("WHEAT","WHEAT_SEEDS"));
                put(mkl("CARROT"),mkl("2CARROT"));
                put(mkl("POTATO"),mkl("2POTATO"));
                put(mkl("PUMPKIN_SEEDS"),mkl("PUMPKIN","PUMPKIN_SEEDS"));
                put(mkl("SUGAR_CANE"),mkl("2SUGAR_CANE"));
                put(mkl("OAK_SAPLING"),mkl("OAK_SAPLING","2OAK_LOG","OAK_LEAVES"));
            }})
            .register();
    public static final SlimefunItem OVERWORLD_PLANT = new MMGenerator(GENERATORS, AddItem.OVERWORLD_PLANT, ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.LENGINE,AddItem.MAGIC_PLANT,AddItem.LENGINE,
                    "TREE_GROWTH_ACCELERATOR","CROP_GROWTH_ACCELERATOR","TREE_GROWTH_ACCELERATOR"
                    ,AddItem.WORLD_FEAT,AddItem.MAGIC_PLANT,AddItem.WORLD_FEAT
            ), 9, 2_500,400,
            new PairList<>(){{
                put(mkl("COCOA_BEANS"),mkl("6COCOA_BEANS"));
                put(mkl("MELON_SEEDS"),mkl("3MELON","3MELON_SEEDS"));
                put(mkl("PUMPKIN_SEEDS"),mkl("3PUMPKIN","3PUMPKIN_SEEDS"));
                put(mkl("BEETROOT_SEEDS"),mkl("8BEETROOT","6BEETROOT_SEEDS"));
                put(mkl("WHEAT_SEEDS"),mkl("8WHEAT","6WHEAT_SEEDS"));
                put(mkl("APPLE"),mkl("9APPLE"));
                put(mkl("BROWN_MUSHROOM"),mkl("6BROWN_MUSHROOM"));
                put(mkl("RED_MUSHROOM"),mkl("6RED_MUSHROOM"));
                put(mkl("DEAD_BUSH"),mkl("6DEAD_BUSH","2STICK"));
                put(mkl("CARROT"),mkl("8CARROT"));
                put(mkl("POTATO"),mkl("8POTATO"));
                put(mkl("SWEET_BERRIES"),mkl("6SWEET_BERRIES"));
                put(mkl("GLOW_BERRIES"),mkl("6GLOW_BERRIES"));
                put(mkl("SUGAR_CANE"),mkl("6SUGAR_CANE"));
                put(mkl("BAMBOO"),mkl("6BAMBOO"));
                put(mkl("CACTUS"),mkl("6CACTUS"));
                put(mkl("DANDELION"),mkl("6DANDELION"));
                put(mkl("POPPY"),mkl("3POPPY"));
                put(mkl("BLUE_ORCHID"),mkl("9BLUE_ORCHID"));
                put(mkl("ALLIUM"),mkl("9ALLIUM"));
                put(mkl("AZURE_BLUET"),mkl("9AZURE_BLUET"));
                put(mkl("RED_TULIP"),mkl("9RED_TULIP"));
                put(mkl("ORANGE_TULIP"),mkl("9ORANGE_TULIP"));
                put(mkl("WHITE_TULIP"),mkl("9WHITE_TULIP"));
                put(mkl("PINK_TULIP"),mkl("9PINK_TULIP"));
                put(mkl("OXEYE_DAISY"),mkl("9OXEYE_DAISY"));
                put(mkl("CORNFLOWER"),mkl("9CORNFLOWER"));
                put(mkl("LILY_OF_THE_VALLEY"),mkl("9LILY_OF_THE_VALLEY"));
                put(mkl("WITHER_ROSE"),mkl("6WITHER_ROSE"));
                put(mkl("SUNFLOWER"),mkl("6SUNFLOWER"));
                put(mkl("LILAC"),mkl("6LILAC"));
                put(mkl("ROSE_BUSH"),mkl("6ROSE_BUSH"));
                put(mkl("PEONY"),mkl("6PEONY"));
                put(mkl("MOSS_BLOCK"),mkl("4MOSS_BLOCK"));
                put(mkl("OAK_SAPLING"),mkl("12OAK_LOG","6APPLE","6OAK_LEAVES","OAK_SAPLING","6STICK"));
                put(mkl("BIRCH_SAPLING"),mkl("12BIRCH_LOG","6APPLE","6BIRCH_LEAVES","BIRCH_SAPLING","6STICK"));
                put(mkl("SPRUCE_SAPLING"),mkl("12SPRUCE_LOG","6APPLE","6SPRUCE_LEAVES","SPRUCE_SAPLING","6STICK"));
                put(mkl("DARK_OAK_SAPLING"),mkl("12DARK_OAK_LOG","6APPLE","6DARK_OAK_LEAVES","DARK_OAK_SAPLING","6STICK"));
                put(mkl("JUNGLE_SAPLING"),mkl("12JUNGLE_LOG","6APPLE","6JUNGLE_LEAVES","JUNGLE_SAPLING","6STICK"));
                put(mkl("ACACIA_SAPLING"),mkl("12ACACIA_LOG","6APPLE","6ACACIA_LEAVES","ACACIA_SAPLING","6STICK"));
                put(mkl("MANGROVE_PROPAGULE"),mkl("12MANGROVE_LOG","6MANGROVE_LEAVES","MANGROVE_PROPAGULE"));
                put(mkl(Material.CHERRY_SAPLING),mkl("12CHERRY_LOG","6CHERRY_LEAVES",new ItemStack(Material.CHERRY_SAPLING,1)));
            }})
            .register();
    public static final SlimefunItem NETHER_PLANT = new MMGenerator(GENERATORS, AddItem.NETHER_PLANT, COMMON_TYPE,
            recipe(null,setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.OVERWORLD_PLANT,AddItem.LOGIGATE,AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.LOGIGATE,"TREE_GROWTH_ACCELERATOR",AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.NETHER_FEAT,"MEDIUM_CAPACITOR","MEDIUM_CAPACITOR",AddItem.NETHER_FEAT,null
            ), 9,3_000,600,
            new PairList<>(){{
                put(mkl("NETHER_WART"),mkl("12NETHER_WART"));
                put(mkl("WEEPING_VINES"),mkl("12WEEPING_VINES"));
                put(mkl("TWISTING_VINES"),mkl("12TWISTING_VINES"));
                put(mkl("CRIMSON_ROOTS"),mkl("12CRIMSON_ROOTS"));
                put(mkl("WARPED_ROOTS"),mkl("12WARPED_ROOTS"));
                put(mkl("NETHER_SPROUTS"),mkl("12NETHER_SPROUTS"));
                put(mkl("CRIMSON_FUNGUS"),mkl("6CRIMSON_FUNGUS","18CRIMSON_STEM","6SHROOMLIGHT","12NETHER_WART_BLOCK"));
                put(mkl("WARPED_FUNGUS"),mkl("6WARPED_FUNGUS","18WARPED_STEM","6SHROOMLIGHT","12WARPED_WART_BLOCK"));

            }})
            .register();
    public static final SlimefunItem END_PLANT = new MMGenerator(GENERATORS, AddItem.END_PLANT, COMMON_TYPE,
            recipe(null,AddItem.LFIELD,setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),AddItem.LFIELD,null,
                    null,AddItem.ABSTRACT_INGOT,"CROP_GROWTH_ACCELERATOR_2","TREE_GROWTH_ACCELERATOR",AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.OVERWORLD_PLANT,AddItem.NETHER_PLANT,AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.ABSTRACT_INGOT,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.ABSTRACT_INGOT,null,
                    AddItem.END_FEAT,AddItem.LENGINE,AddItem.END_FEAT,AddItem.END_FEAT,AddItem.LENGINE,AddItem.END_FEAT
            ), 9,6_000,1000,
            new PairList<>(){{
                put(mkl("MELON_SEEDS"),mkl("3MELON","3MELON_SEEDS"));
                put(mkl("PUMPKIN_SEEDS"),mkl("3PUMPKIN","3PUMPKIN_SEEDS"));
                put(mkl("WHEAT_SEEDS"),mkl("9WHEAT","6WHEAT_SEEDS"));
                put(mkl("CARROT"),mkl("9CARROT"));
                put(mkl("POTATO"),mkl("9POTATO"));
                put(mkl("SUGAR_CANE"),mkl("9SUGAR_CANE"));
                put(mkl("OAK_SAPLING"),mkl("9OAK_SAPLING","18OAK_LOG","6APPLE","9OAK_LEAVES","6STICK"));
                put(mkl("NETHER_WART"),mkl("12NETHER_WART"));
                put(mkl("CRIMSON_FUNGUS"),mkl("6CRIMSON_FUNGUS","18CRIMSON_STEM","6SHROOMLIGHT","12NETHER_WART_BLOCK"));
                put(mkl("WARPED_FUNGUS"),mkl("6WARPED_FUNGUS","18WARPED_STEM","6SHROOMLIGHT","12WARPED_WART_BLOCK"));
                put(mkl("CHORUS_FLOWER"),mkl("6CHORUS_FLOWER","24CHORUS_FRUIT"));
                put(mkl("KELP"),mkl("9KELP"));
                put(mkl("LILY_PAD"),mkl("9LILY_PAD"));
                put(mkl("SEA_PICKLE"),mkl("9SEA_PICKLE"));
                put(mkl("TUBE_CORAL_BLOCK"),mkl("6TUBE_CORAL_BLOCK"));
                put(mkl("BRAIN_CORAL_BLOCK"),mkl("6BRAIN_CORAL_BLOCK"));
                put(mkl("BUBBLE_CORAL_BLOCK"),mkl("6BUBBLE_CORAL_BLOCK"));
                put(mkl("FIRE_CORAL_BLOCK"),mkl("6FIRE_CORAL_BLOCK"));
                put(mkl("HORN_CORAL_BLOCK"),mkl("6HORN_CORAL_BLOCK"));
                put(mkl("TUBE_CORAL"),mkl("6TUBE_CORAL"));
                put(mkl("BRAIN_CORAL"),mkl("6BRAIN_CORAL"));
                put(mkl("BUBBLE_CORAL"),mkl("6BUBBLE_CORAL"));
                put(mkl("FIRE_CORAL"),mkl("6FIRE_CORAL"));
                put(mkl("HORN_CORAL"),mkl("6HORN_CORAL"));
                put(mkl("TUBE_CORAL_FAN"),mkl("6TUBE_CORAL_FAN"));
                put(mkl("BRAIN_CORAL_FAN"),mkl("6BRAIN_CORAL_FAN"));
                put(mkl("BUBBLE_CORAL_FAN"),mkl("6BUBBLE_CORAL_FAN"));
                put(mkl("FIRE_CORAL_FAN"),mkl("6FIRE_CORAL_FAN"));
                put(mkl("HORN_CORAL_FAN"),mkl("6HORN_CORAL_FAN"));
            }})
            .register();
    public static final SlimefunItem STONE_FACTORY = new MMGenerator(GENERATORS, AddItem.STONE_FACTORY, COMMON_TYPE,
            recipe(AddItem.LPLATE,setC(AddItem.METAL_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.SMELERY_CORE,1),AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,4),setC(AddItem.CHIP_CORE,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.ATOM_INGOT,4),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4PROGRAMMABLE_ANDROID_3_BUTCHER","4PROGRAMMABLE_ANDROID_3_BUTCHER",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,setC(AddItem.OVERWORLD_MINER,4),setC(AddItem.OVERWORLD_MINER,4),AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4PROGRAMMABLE_ANDROID_3_BUTCHER","4PROGRAMMABLE_ANDROID_3_BUTCHER",AddItem.LMOTOR,AddItem.LPLATE,
                    setC(AddItem.PAGOLD,1),setC(AddItem.METAL_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.SMELERY_CORE,1),setC(AddItem.PAGOLD,1)), 1, 129_600, 2400,
            new PairList<>(){{
                put(mkl("COBBLESTONE"),mkl(   randAmountItemFactory(new ItemStack(Material.COBBLESTONE),514,1919)));
                put(mkl("NETHERRACK"),mkl(randAmountItemFactory(new ItemStack(Material.NETHERRACK),514,1919)));
                put(mkl("END_STONE"),mkl(randAmountItemFactory(new ItemStack(Material.END_STONE),514,1919)));
                put(mkl("GRANITE"),mkl(randAmountItemFactory(new ItemStack(Material.GRANITE),114,514)));
                put(mkl("DIORITE"),mkl(randAmountItemFactory(new ItemStack(Material.DIORITE),114,514)));
                put(mkl("ANDESITE"),mkl(randAmountItemFactory(new ItemStack(Material.ANDESITE),114,514)));
                put(mkl("STONE"),mkl(randAmountItemFactory(new ItemStack(Material.STONE),114,514)));
                put(mkl("TUFF"),mkl(randAmountItemFactory(new ItemStack(Material.TUFF),114,514)));
            }})
            .register();
    public static final SlimefunItem REDSTONE_MG=new SMGenerator(VANILLA, AddItem.REDSTONE_MG,COMMON_TYPE,
            recipe(null,"REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK",null,
                    "REDSTONE_BLOCK",setC(AddItem.NOLOGIC,1),AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,setC(AddItem.NOLOGIC,1),"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",AddItem.ABSTRACT_INGOT,setC(AddItem.REDSTONE_ENGINE,1),setC(AddItem.REDSTONE_ENGINE,1),AddItem.ABSTRACT_INGOT,"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",AddItem.ABSTRACT_INGOT,setC(AddItem.REDSTONE_ENGINE,1),setC(AddItem.REDSTONE_ENGINE,1),AddItem.ABSTRACT_INGOT,"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",setC(AddItem.NOLOGIC,1),AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,setC(AddItem.NOLOGIC,1),"REDSTONE_BLOCK",
                    null,"REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK",null),1,10000,250,
            randItemStackFactory(
                    mkMp(
                            randItemStackFactory(mkMp("PISTON",1,"STICKY_PISTON",1,"OBSERVER",1,"HOPPER",1,"CHISELED_BOOKSHELF",1,"LIGHT_WEIGHTED_PRESSURE_PLATE",1)),1,
                            randItemStackFactory(mkMp("REDSTONE_TORCH",1,"REPEATER",1,"COMPARATOR",1,"LEVER",1,"OAK_PRESSURE_PLATE",1,"STONE_PRESSURE_PLATE",1)),1,
                            randItemStackFactory(mkMp("NOTE_BLOCK",1, "REDSTONE_LAMP",1, "TARGET",1, "REDSTONE_BLOCK",1, "TRIPWIRE_HOOK",1,"JUKEBOX",1 )),1,
                            randItemStackFactory(mkMp("DISPENSER",1,"DROPPER",1, "DAYLIGHT_DETECTOR",1, "LECTERN",1, "LIGHTNING_ROD",1,"HEAVY_WEIGHTED_PRESSURE_PLATE",1)),1,
                            randItemStackFactory(mkMp("SLIME_BALL",1,"HONEY_BLOCK",1,"TNT",1,"REDSTONE",1,"OAK_BUTTON",1,"STONE_BUTTON",1,"BELL",1,"TRAPPED_CHEST",1,Material.COMPOSTER,1)),1)
            ))
            .register();
    public static final SlimefunItem TNT_MG=new SMGenerator(VANILLA, AddItem.TNT_MG,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.LOGIGATE,"CRAFTING_TABLE",AddItem.LOGIGATE,AddItem.REDSTONE_ENGINE,"STICKY_PISTON",AddItem.REDSTONE_ENGINE,
                    AddItem.LENGINE,"CRAFTING_TABLE",AddItem.LENGINE),4,3600,233,
            "TNT")
            .register();
    public static final SlimefunItem DUPE_MG=new MMGenerator(VANILLA, AddItem.DUPE_MG,COMMON_TYPE,
            recipe("TRIPWIRE_HOOK",AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.NOLOGIC,"TRIPWIRE_HOOK",
                    "TRIPWIRE_HOOK",null,null,null,null,"TRIPWIRE_HOOK",
                    "TRIPWIRE_HOOK",AddItem.REDSTONE_ENGINE,null,null,AddItem.REDSTONE_ENGINE,"TRIPWIRE_HOOK",
                    "WHITE_CARPET",AddItem.REDSTONE_ENGINE,null,null,AddItem.REDSTONE_ENGINE,"WHITE_CARPET",
                    "WHITE_CARPET","STICKY_PISTON","OBSERVER","OBSERVER","STICKY_PISTON","WHITE_CARPET",
                    null,"WHITE_CARPET","WHITE_CARPET","WHITE_CARPET","WHITE_CARPET",null),1,1000,116,
            new PairList<>(){{
                put(mkl("STRING"),mkl("16STRING"));
                put(mkl("RAIL"),mkl("16RAIL"));
                put(mkl("POWERED_RAIL"),mkl("16POWERED_RAIL"));
                put(mkl("DETECTOR_RAIL"),mkl("16DETECTOR_RAIL"));
                put(mkl("ACTIVATOR_RAIL"),mkl("16ACTIVATOR_RAIL"));
                put(mkl(Material.BLACK_CARPET),mkl("32BLACK_CARPET"));
                put(mkl(Material.RED_CARPET),mkl("32RED_CARPET"));
                put(mkl(Material.ORANGE_CARPET),mkl("32ORANGE_CARPET"));
                put(mkl(Material.YELLOW_CARPET),mkl("32YELLOW_CARPET"));
                put(mkl(Material.LIME_CARPET),mkl("32LIME_CARPET"));
                put(mkl(Material.WHITE_CARPET),mkl("32WHITE_CARPET"));
                put(mkl(Material.CYAN_CARPET),mkl("32CYAN_CARPET"));
                put(mkl(Material.BLUE_CARPET),mkl("32BLUE_CARPET"));
                put(mkl(Material.GRAY_CARPET),mkl("32GRAY_CARPET"));
                put(mkl(Material.BROWN_CARPET),mkl("32BROWN_CARPET"));
                put(mkl(Material.GREEN_CARPET),mkl("32GREEN_CARPET"));
                put(mkl(Material.LIGHT_BLUE_CARPET),mkl("32LIGHT_BLUE_CARPET"));
                put(mkl(Material.MAGENTA_CARPET),mkl("32MAGENTA_CARPET"));
                put(mkl(Material.PINK_CARPET),mkl("32PINK_CARPET"));
                put(mkl(Material.PURPLE_CARPET),mkl("32PURPLE_CARPET"));
                put(mkl(Material.GRAY_CARPET),mkl("32GRAY_CARPET"));
                put(mkl(Material.LIGHT_GRAY_CARPET),mkl("32LIGHT_GRAY_CARPET"));
                put(mkl(Material.MOSS_CARPET),mkl("32MOSS_CARPET"));

            }})
            .register();
    public static final SlimefunItem ENDDUPE_MG=new MMGenerator(VANILLA, AddItem.ENDDUPE_MG,COMMON_TYPE,
            recipe(null,"SAND","ANVIL","ANVIL","SAND",null,
                    null,AddItem.REDSTONE_ENGINE,"END_PORTAL_FRAME","END_PORTAL_FRAME",AddItem.REDSTONE_ENGINE,null,
                    "OBSERVER",AddItem.REDSTONE_ENGINE,"END_PORTAL_FRAME","END_PORTAL_FRAME",AddItem.REDSTONE_ENGINE,"OBSERVER",
                    "OBSERVER",AddItem.DUPE_MG,"END_PORTAL_FRAME","END_PORTAL_FRAME",AddItem.DUPE_MG,"OBSERVER",
                    "END_STONE",AddItem.LFIELD,setC(AddItem.DIMENSIONAL_SHARD,2),setC(AddItem.DIMENSIONAL_SHARD,2),AddItem.LFIELD,"END_STONE",
                    null,"END_STONE",AddItem.LFIELD,AddItem.LFIELD,"END_STONE",null),3,1000,116,
            new PairList<>(){{
                put(mkl("SAND"),mkl("8SAND"));
                put(mkl("RED_SAND"),mkl("8RED_SAND"));
                put(mkl("ANVIL"),mkl("ANVIL"));
                put(mkl("CHIPPED_ANVIL"),mkl("CHIPPED_ANVIL"));
                put(mkl("DAMAGED_ANVIL"),mkl("DAMAGED_ANVIL"));
                put(mkl("GRAVEL"),mkl("8GRAVEL"));
                put(mkl("SNOW"),mkl("4SNOW"));
                put(mkl("POINTED_DRIPSTONE"),mkl("4POINTED_DRIPSTONE"));
                put(mkl(Material.BLUE_CONCRETE_POWDER),mkl("32BLUE_CONCRETE_POWDER"));
                put(mkl(Material.RED_CONCRETE_POWDER),mkl("32RED_CONCRETE_POWDER"));
                put(mkl(Material.YELLOW_CONCRETE_POWDER),mkl("32YELLOW_CONCRETE_POWDER"));
                put(mkl(Material.WHITE_CONCRETE_POWDER),mkl("32WHITE_CONCRETE_POWDER"));
                put(mkl(Material.BROWN_CONCRETE_POWDER),mkl("32BROWN_CONCRETE_POWDER"));
                put(mkl(Material.LIME_CONCRETE_POWDER),mkl("32LIME_CONCRETE_POWDER"));
                put(mkl(Material.GREEN_CONCRETE_POWDER),mkl("32GREEN_CONCRETE_POWDER"));
                put(mkl(Material.LIGHT_BLUE_CONCRETE_POWDER),mkl("32LIGHT_BLUE_CONCRETE_POWDER"));
                put(mkl(Material.MAGENTA_CONCRETE_POWDER),mkl("32MAGENTA_CONCRETE_POWDER"));
                put(mkl(Material.GRAY_CONCRETE_POWDER),mkl("32GRAY_CONCRETE_POWDER"));
                put(mkl(Material.BLACK_CONCRETE_POWDER),mkl("32BLACK_CONCRETE_POWDER"));
                put(mkl(Material.PURPLE_CONCRETE_POWDER),mkl("32PURPLE_CONCRETE_POWDER"));
                put(mkl(Material.ORANGE_CONCRETE_POWDER),mkl("32ORANGE_CONCRETE_POWDER"));
                put(mkl(Material.LIGHT_GRAY_CONCRETE_POWDER),mkl("32LIGHT_GRAY_CONCRETE_POWDER"));
                put(mkl(Material.PINK_CONCRETE_POWDER),mkl("32PINK_CONCRETE_POWDER"));
                put(mkl(Material.CYAN_CONCRETE_POWDER),mkl("32CYAN_CONCRETE_POWDER"));


            }})
            .register();
    public static final SlimefunItem BNOISE_MAKER = new BNoiseMaker(VANILLA, AddItem.BNOISE_MAKER, ENHANCED_CRAFTING_TABLE,
            recipe(
                    "NOTE_BLOCK",  AddItem.BUG, AddItem.BUG,
                    AddItem.BUG,  AddItem.BUG, "NOTE_BLOCK",
                    AddItem.BUG, "NOTE_BLOCK",  AddItem.BUG
            )).register();
    public static final SlimefunItem BNOISE_HEAD = new BNoiseHead(VANILLA, AddItem.BNOISE_HEAD, RecipeType.NULL,
            formatInfoRecipe(AddItem.BNOISE_MAKER,Language.get("Generators.BNOISE_MAKER.Name"))
            ).register();
    public static final SlimefunItem REVERSE_GENERATOR = new MMGenerator(GENERATORS, AddItem.REVERSE_GENERATOR, COMMON_TYPE,
            recipe(null,AddItem.SPACE_PLATE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.SPACE_PLATE,null,
                    null,AddItem.SPACE_PLATE,AddItem.LOGIC_REACTOR,AddItem.LOGIC_REACTOR,AddItem.SPACE_PLATE,null,
                    null,AddItem.PAGOLD,AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.PAGOLD,null,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    null,AddItem.PAGOLD,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.PAGOLD,null), 1, 1_000_000, 24_000,
            new PairList<>(){{
                put(mkl(AddItem.LOGIGATE),mkl(setC(AddItem.LOGIGATE,64)));
                put(mkl(AddItem.LOGIC),mkl(setC(AddItem.NOLOGIC,64)));
                put(mkl(AddItem.NOLOGIC),mkl(setC(AddItem.LOGIC,64)));
                put(mkl(AddItem.EXISTE),mkl(setC(AddItem.UNIQUE,64)));
                put(mkl(AddItem.UNIQUE),mkl(setC(AddItem.EXISTE,64)));
                put(mkl(AddItem.BUG),mkl(setC(AddItem.BUG,3)));
                put(mkl("IRON_INGOT"),mkl(setC(AddItem.ABSTRACT_INGOT,32)));
                put(mkl(AddItem.ABSTRACT_INGOT),mkl("4096IRON_INGOT"));
            }})
            .register();
    public static final SlimefunItem VIRTUAL_MINER = new MMGenerator(GENERATORS, AddItem.VIRTUAL_MINER, COMMON_TYPE,
            recipe(AddItem.BISILVER,setC(AddItem.TECH_CORE,1),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.SMELERY_CORE,1),AddItem.BISILVER,
                    AddItem.LDIGITIZER,AddItem.BISILVER,null,null,AddItem.BISILVER,AddItem.LDIGITIZER,
                    AddItem.BISILVER,AddItem.LMOTOR,AddItem.VIRTUAL_SPACE,AddItem.DIMENSION_MINER,AddItem.LMOTOR,AddItem.BISILVER,
                    AddItem.BISILVER,AddItem.LENGINE,AddItem.DIMENSION_MINER,AddItem.VIRTUAL_SPACE,AddItem.LENGINE,AddItem.BISILVER,
                    AddItem.LIOPORT,AddItem.BISILVER,null,null,AddItem.BISILVER,AddItem.LIOPORT,
                    AddItem.BISILVER,setC(AddItem.MASS_CORE,1),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.METAL_CORE,1),AddItem.BISILVER), 1, 129_600, 5400,
            new PairList<>(){{
                put(mkl(AddItem.WORLD_FEAT),mkl(randItemStackFactory(
                        mkMp("128COAL",8,
                                "128REDSTONE",8,
                                "128IRON_INGOT",8,
                                "256LAPIS_LAZULI",8,
                                "128DIAMOND",8,
                                "128EMERALD",8)
                )));
                put(mkl(AddItem.NETHER_FEAT),mkl(randItemStackFactory(
                        mkMp("48NETHERITE_INGOT",1,
                                "192QUARTZ",1,
                                "64MAGMA_BLOCK",1,
                                "64OBSIDIAN",1,
                                "64ANCIENT_DEBRIS",1,
                                "12NETHER_ICE",1)
                )));
                put(mkl(AddItem.END_FEAT),mkl(randItemStackFactory(
                        mkMp("8DRAGON_BREATH",2,
                       // "4CHORUS_FLOWER",2,
                                "16ENDER_EYE",2,
                                "16ENDER_PEARL",2,
                                "12BUCKET_OF_OIL",2,
                                setC(AddItem.STAR_GOLD,20),2,
                                setC(AddItem.DIMENSIONAL_SHARD,36),2
                        )
                )));
            }})
            .register();
    public static final SlimefunItem VIRTUAL_PLANT = new MMGenerator(GENERATORS, AddItem.VIRTUAL_PLANT, COMMON_TYPE,
            recipe(AddItem.BISILVER,setC(AddItem.TECH_CORE,1),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.SMELERY_CORE,1),AddItem.BISILVER,
                    AddItem.LDIGITIZER,AddItem.BISILVER,null,null,AddItem.BISILVER,AddItem.LDIGITIZER,
                    AddItem.BISILVER,setC(AddItem.LMOTOR,1),setC(AddItem.CHIP_CORE,1),AddItem.END_PLANT,setC(AddItem.LMOTOR,1),AddItem.BISILVER,
                    AddItem.BISILVER,setC(AddItem.LENGINE,1),setC(AddItem.VIRTUAL_SPACE,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.LENGINE,1),AddItem.BISILVER,
                    AddItem.LIOPORT,AddItem.BISILVER,null,null,AddItem.BISILVER,AddItem.LIOPORT,
                    AddItem.BISILVER,setC(AddItem.MASS_CORE,1),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.METAL_CORE,1),AddItem.BISILVER), 1, 129_600, 5400,
            new PairList<>(){{
                put(mkl("COCOA_BEANS"),mkl("9COCOA_BEANS"));
                put(mkl("MELON_SEEDS"),mkl("3MELON","3MELON_SEEDS"));
                put(mkl("PUMPKIN_SEEDS"),mkl("3PUMPKIN","3PUMPKIN_SEEDS"));
                put(mkl("BEETROOT_SEEDS"),mkl("9BEETROOT","6BEETROOT_SEEDS"));
                put(mkl("WHEAT_SEEDS"),mkl("9WHEAT","6WHEAT_SEEDS"));
                put(mkl("BROWN_MUSHROOM"),mkl("9BROWN_MUSHROOM"));
                put(mkl("RED_MUSHROOM"),mkl("9RED_MUSHROOM"));
                put(mkl("DEAD_BUSH"),mkl("9DEAD_BUSH","6STICK"));
                put(mkl("CARROT"),mkl("9CARROT"));
                put(mkl("POTATO"),mkl("9POTATO"));
                put(mkl("DEAD_BUSH"),mkl("9DEAD_BUSH","6STICK"));
                put(mkl("SWEET_BERRIES"),mkl("9SWEET_BERRIES"));
                put(mkl("GLOW_BERRIES"),mkl("9GLOW_BERRIES"));
                put(mkl("SUGAR_CANE"),mkl("9SUGAR_CANE"));
                put(mkl("CACTUS"),mkl("9CACTUS"));
                put(mkl("DANDELION"),mkl("9DANDELION"));
                put(mkl("POPPY"),mkl("3POPPY"));
                put(mkl("BLUE_ORCHID"),mkl("9BLUE_ORCHID"));
                put(mkl("ALLIUM"),mkl("9ALLIUM"));
                put(mkl("AZURE_BLUET"),mkl("9AZURE_BLUET"));
                put(mkl("RED_TULIP"),mkl("9RED_TULIP"));
                put(mkl("ORANGE_TULIP"),mkl("9ORANGE_TULIP"));
                put(mkl("WHITE_TULIP"),mkl("9WHITE_TULIP"));
                put(mkl("PINK_TULIP"),mkl("9PINK_TULIP"));
                put(mkl("OXEYE_DAISY"),mkl("9OXEYE_DAISY"));
                put(mkl("CORNFLOWER"),mkl("9CORNFLOWER"));
                put(mkl("LILY_OF_THE_VALLEY"),mkl("9LILY_OF_THE_VALLEY"));
                put(mkl("WITHER_ROSE"),mkl("6WITHER_ROSE"));
                put(mkl("SUNFLOWER"),mkl("6SUNFLOWER"));
                put(mkl("LILAC"),mkl("6LILAC"));
                put(mkl("ROSE_BUSH"),mkl("6ROSE_BUSH"));
                put(mkl("PEONY"),mkl("6PEONY"));
                put(mkl("OAK_SAPLING"),mkl("18OAK_LOG","6APPLE","9OAK_LEAVES","9OAK_SAPLING","6STICK"));
                put(mkl("BIRCH_SAPLING"),mkl("18BIRCH_LOG","6APPLE","9BIRCH_LEAVES","9BIRCH_SAPLING","6STICK"));
                put(mkl("SPRUCE_SAPLING"),mkl("18SPRUCE_LOG","6APPLE","9SPRUCE_LEAVES","9SPRUCE_SAPLING","6STICK"));
                put(mkl("DARK_OAK_SAPLING"),mkl("18DARK_OAK_LOG","6APPLE","9DARK_OAK_LEAVES","9DARK_OAK_SAPLING","6STICK"));
                put(mkl("JUNGLE_SAPLING"),mkl("18JUNGLE_LOG","6APPLE","9JUNGLE_LEAVES","9JUNGLE_SAPLING","6STICK"));
                put(mkl("ACACIA_SAPLING"),mkl("18ACACIA_LOG","6APPLE","9ACACIA_LEAVES","9ACACIA_SAPLING","6STICK"));
                put(mkl("MANGROVE_PROPAGULE"),mkl("18MANGROVE_LOG","9MANGROVE_LEAVES","9MANGROVE_PROPAGULE"));
                put(mkl(Material.CHERRY_SAPLING),mkl("18CHERRY_LOG","9CHERRY_LEAVES",new ItemStack(Material.CHERRY_SAPLING,9)));
                put(mkl("NETHER_WART"),mkl("12NETHER_WART"));
                put(mkl("NETHER_SPROUTS"),mkl("12NETHER_SPROUTS"));
                put(mkl("CRIMSON_FUNGUS"),mkl("6CRIMSON_FUNGUS","18CRIMSON_STEM","6SHROOMLIGHT","12NETHER_WART_BLOCK"));
                put(mkl("WARPED_FUNGUS"),mkl("6WARPED_FUNGUS","18WARPED_STEM","6SHROOMLIGHT","12WARPED_WART_BLOCK"));
                put(mkl("CHORUS_FLOWER"),mkl("6CHORUS_FLOWER","24CHORUS_FRUIT"));
                put(mkl("LILY_PAD"),mkl("9LILY_PAD"));
                put(mkl("KELP"),mkl("9KELP"));
            }})
            .register();
    public static final SlimefunItem STACKMGENERATOR=new StackMGenerator(GENERATORS, AddItem.STACKMGENERATOR,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),1,40_000_000,2_000,2)
            .register();

    //TODO register recips
    //TODO 平衡性调整
    //TODO 虚拟世界 等生成器 有了
    //发电机 123
    //更多的智能货运
    //垃圾提 ok
    //终极路线
    //高级芯片之类的? 高级芯片机 ok
    //补充说明 堆叠机器 ok
    //正反发电机 ok
    //方舟反应堆 ok
    //百度
    //吃掉实体
    //红石控制的货运
    //芯片控制的货运
    //红石tnt生成器
    //终极机器 物质重构器
    //终极机器 逆机器
    //,物质重构机，小型多方块结构 射线 出逻辑核心
    //最终发展路线 。快捷->圆石生成器->逻辑核心->概念同化体->终极堆叠,逆机器

    //multiblock
    public static final SlimefunItem PORTAL_CORE=new PortalCore(SPACE,AddItem.PORTAL_CORE,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,AddItem.DIMENSIONAL_SHARD,AddItem.DIMENSIONAL_SHARD,AddItem.LFIELD,AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,"GPS_TRANSMITTER_2","GPS_TRANSMITTER_2",AddItem.STAR_GOLD_INGOT,AddItem.LFIELD,
                    AddItem.DIMENSIONAL_SHARD,"GPS_TRANSMITTER_2",AddItem.NOLOGIC,AddItem.HYPER_LINK,"GPS_TRANSMITTER_2",AddItem.DIMENSIONAL_SHARD,
                    AddItem.DIMENSIONAL_SHARD,"GPS_TRANSMITTER_2",AddItem.LENGINE,AddItem.LDIGITIZER,"GPS_TRANSMITTER_2",AddItem.DIMENSIONAL_SHARD,
                    AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,"GPS_TRANSMITTER_2","GPS_TRANSMITTER_2",AddItem.STAR_GOLD_INGOT,AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,AddItem.DIMENSIONAL_SHARD,AddItem.DIMENSIONAL_SHARD,AddItem.LFIELD,AddItem.LFIELD),"portal.core",
            MultiBlockTypes.PORTAL_TYPE)
            .register();
    public static final SlimefunItem PORTAL_FRAME=new MultiPart(SPACE,AddItem.PORTAL_FRAME,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"portal.part")
            .register();
    public static final SlimefunItem SOLAR_REACTOR=new SolarReactorCore(SPACE,AddItem.SOLAR_REACTOR,COMMON_TYPE,
            recipe(AddItem.LPLATE,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.LPLATE,
                    AddItem.STAR_GOLD_INGOT,AddItem.TECH_CORE,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.MASS_CORE,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,"GPS_TRANSMITTER_4",AddItem.SMELERY_CORE,AddItem.METAL_CORE,"GPS_TRANSMITTER_4",AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,"GPS_TRANSMITTER_4",AddItem.MASS_CORE,AddItem.TECH_CORE,"GPS_TRANSMITTER_4",AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.METAL_CORE,AddItem.CHIP_CORE,AddItem.CHIP_CORE,AddItem.SMELERY_CORE,AddItem.STAR_GOLD_INGOT,
                    AddItem.LPLATE,AddItem.STAR_GOLD_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem.STAR_GOLD_INGOT,AddItem.LPLATE)
            ,"solar.core",MultiBlockTypes.SOLAR_TYPE,80_000,2_000_000,
            mkMp(mkP(   mkl(AddItem.METAL_CORE)  ,
                            mkl(
                                    setC(AddItem.STAR_GOLD_INGOT,18),
                                    probItemStackFactory( AddItem.LSINGULARITY,73),
                                    randAmountItemFactory(AddItem.STAR_GOLD,39,87),
                                    randAmountItemFactory(AddItem.ATOM_INGOT,63,99),
                                    randItemStackFactory(
                                            Utils.list(setC(AddItem.PALLADIUM_INGOT,4),setC(AddItem.PLATINUM_INGOT,4),setC(AddItem.CADMIUM_INGOT,4),setC(AddItem.BISMUTH_INGOT,4)),
                                            Utils.list(37,29,13,21)
                                    )
                            )
                    ),75,
                    mkP(   mkl(AddItem.SMELERY_CORE)  ,

                            mkl(
                                    setC(AddItem.ABSTRACT_INGOT,64),
                                    probItemStackFactory( AddItem.LSINGULARITY,12),
                                    randAmountItemFactory(AddItem.DIMENSIONAL_SHARD,99,127),
                                    randAmountItemFactory(AddItem.STAR_GOLD,49,83),
                                    randAmountItemFactory(AddItem.ATOM_INGOT,37,51),
                                    randItemStackFactory(
                                            Utils.list(setC(AddItem.PALLADIUM_INGOT,4),setC(AddItem.PLATINUM_INGOT,4),setC(AddItem.CADMIUM_INGOT,4),setC(AddItem.BISMUTH_INGOT,4)),
                                            Utils.list(2,59,13,24)
                                    )
                            )
                    ),75,
                    mkP(   mkl(AddItem.MASS_CORE)  ,

                            mkl(
                                    setC(AddItem.BUG,32),
                                    probItemStackFactory( AddItem.LSINGULARITY,97),
                                    randAmountItemFactory(AddItem.DIMENSIONAL_SHARD,73,127),
                                    randAmountItemFactory(AddItem.STAR_GOLD,39,63),
                                    randAmountItemFactory(AddItem.ATOM_INGOT,12,39),

                                    randItemStackFactory(
                                            Utils.list(setC(AddItem.PALLADIUM_INGOT,4),setC(AddItem.PLATINUM_INGOT,4),setC(AddItem.CADMIUM_INGOT,4),setC(AddItem.BISMUTH_INGOT,4)),
                                            Utils.list(27,22,15,45)
                                    )
                            )

                    ),75,
                    mkP(   mkl(AddItem.TECH_CORE)  ,

                            mkl(
                                    setC(AddItem.LPLATE,64),
                                    setC( AddItem.LSINGULARITY,2),
                                    randAmountItemFactory(AddItem.STAR_GOLD_INGOT,23,40),
                                    randAmountItemFactory(AddItem.ATOM_INGOT,92,127)
                            )

                    ),75
            )).setDisplayRecipes(
                    Utils.list(
                            getInfoShow("&f机制",
                                    "&7该机器需要搭建超新星外壳方可运行",
                                    "&7搭建成功后需要保证外壳内部(除机器顶部)不包含任何非空气方块",
                                    "&7才可以成功启动多方块机器",
                                    "&7在此你可以使用[%s]等远程访问工具打开内部的界面".formatted(Language.get("Items.HYPER_LINK.Name")),
                                    "&7或者开启自动构建模式让机器自行启动"),null,
                            getInfoShow("&f机制",
                                    "&7该机器在构建/待机/运行时候拥有相同的电力消耗量",
                                    "&7当电力不足时会强制关机",
                                    "&7注意:为了防止意外发生,当重启/使用不安全的远程传输时,请确认机器所在区块的加载状态",
                                    "&7并确保能源网络在加载范围内并且能给机器提供足够电力"),null,
                            getInfoShow("&f机制",
                                    "&7机器自动关闭且&e进程未结束时&c会发生爆炸",
                                    "&7机器爆炸会在四周生成爆炸强度为80的爆炸,并伴有多方块结构1/3损坏",
                                    "&7机器会在以下条件自动关闭",
                                    "&7- 电力不足",
                                    "&7- 人为的破坏多方块框架(挖掘,或者数据清除)",
                                    "&7- 手动关机而多方块不处于\"自动构建\"模式",
                                    "&7- 超新星特效丢失或者距离源位置超过1格(&a不会发起爆炸)",
                                    "&7服务器重启时多方块会安全关闭,并在重启后自动恢复"
                            ),null
                    )
            )
            .register();
    public static final SlimefunItem SOLAR_REACTOR_FRAME=new MultiPart(SPACE,AddItem.SOLAR_REACTOR_FRAME,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"solar.frame")
            .register();
    public static final SlimefunItem SOLAR_REACTOR_GLASS=new MultiPart(SPACE,AddItem.SOLAR_REACTOR_GLASS,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"solar.glass")
            .register();
    public static final SlimefunItem SOLAR_INPUT=new MultiIOPort(SPACE,AddItem.SOLAR_INPUT,ANCIENT_ALTAR,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.HYPER_LINK,AddItem.PARADOX,
                    AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT),"solar.frame",true,true)
            .register();
    public static final SlimefunItem SOLAR_OUTPUT=new MultiIOPort(SPACE,AddItem.SOLAR_OUTPUT,ANCIENT_ALTAR,
            recipe(AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.HYPER_LINK,AddItem.STAR_GOLD_INGOT,
                    AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX),"solar.frame",false,false)
            .register() ;
    public static final SlimefunItem TRANSMUTATOR_FRAME=new MultiPart(ADVANCED,AddItem.TRANSMUTATOR_FRAME,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"nuclear.frame")
            .register();
    public static final SlimefunItem TRANSMUTATOR_GLASS=new MultiPart(ADVANCED,AddItem.TRANSMUTATOR_GLASS,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"nuclear.glass")
            .register();
    public static final SlimefunItem TRANSMUTATOR_ROD=new MultiPart(ADVANCED,AddItem.TRANSMUTATOR_ROD,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"nuclear.rod")
            .register();
    public static final  SlimefunItem TRANSMUTATOR=new Transmutator(ADVANCED, AddItem.TRANSMUTATOR,COMMON_TYPE,
            recipe(setC(AddItem.TECH_CORE,64),AddItem.TRANSMUTATOR_ROD,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.TRANSMUTATOR_ROD,setC(AddItem.MASS_CORE,64),
                    AddItem.TRANSMUTATOR_ROD,setC(AddItem.CHIP_CORE,4),setC(AddItem.LSINGULARITY,4),setC(AddItem.LSINGULARITY,4),setC(AddItem.CHIP_CORE,4),AddItem.TRANSMUTATOR_ROD,
                    AddItem.SPACE_PLATE,setC(AddItem.PAGOLD,4),"16NUCLEAR_REACTOR","16NUCLEAR_REACTOR",setC(AddItem.PAGOLD,4),AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,setC(AddItem.PAGOLD,4),"16NUCLEAR_REACTOR","16NUCLEAR_REACTOR",setC(AddItem.PAGOLD,4),AddItem.SPACE_PLATE,
                    AddItem.TRANSMUTATOR_ROD,setC(AddItem.CHIP_CORE,4),setC(AddItem.BISILVER,4),setC(AddItem.BISILVER,4),setC(AddItem.CHIP_CORE,4),AddItem.TRANSMUTATOR_ROD,
                    setC(AddItem.METAL_CORE,64),AddItem.TRANSMUTATOR_ROD,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.TRANSMUTATOR_ROD,setC(AddItem.SMELERY_CORE,64)), "nuclear.core",
            MultiBlockTypes.NUCLEAR_REACTOR,750_000,20_000_000,
            mkMp(mkP(   mkl(setC(AddItem.ATOM_INGOT,64) )  ,
                        mkl(
                                setC(AddItem.ATOM_INGOT,128),
                                randItemStackFactory(
                                        Utils.list(
                                                randAmountItemFactory(AddItem.PALLADIUM_INGOT,37,53),
                                                randAmountItemFactory(AddItem.PLATINUM_INGOT,27,50),
                                                randAmountItemFactory(AddItem.CADMIUM_INGOT,34,64),
                                                randAmountItemFactory(AddItem.BISMUTH_INGOT,40,54)
                                        ),
                                        Utils.list(1,1,1,1)
                                ),
                                randItemStackFactory(
                                        Utils.list(
                                                randAmountItemFactory(AddItem.MOLYBDENUM,1,4),
                                                randAmountItemFactory(AddItem.CERIUM,1,4),
                                                randAmountItemFactory(AddItem.MENDELEVIUM,1,4),
                                                randAmountItemFactory(AddItem.DYSPROSIUM,1,4),
                                                randAmountItemFactory(AddItem.MOLYBDENUM,1,4),
                                                randAmountItemFactory(AddItem.ANTIMONY_INGOT,1,4),
                                                randAmountItemFactory(AddItem.THALLIUM,1,4),
                                                randAmountItemFactory(AddItem.HYDRAGYRUM,1,4),
                                                randAmountItemFactory(AddItem.BORON,1,4)
                                        ),
                                        Utils.list(1,1,1,1,1,1,1,1,1)
                                )
                        )

                    ),8000,
                    mkP(   mkl(setC(AddItem.BISILVER,2) )  ,

                            mkl(
                                    setC(AddItem.ATOM_INGOT,48),
                                    setC(AddItem.PARADOX,64),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(AddItem.PALLADIUM_INGOT,15,61),
                                                    randAmountItemFactory(AddItem.PLATINUM_INGOT,13,49),
                                                    randAmountItemFactory(AddItem.CADMIUM_INGOT,23,57),
                                                    randAmountItemFactory(AddItem.BISMUTH_INGOT,12,64)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(AddItem.MOLYBDENUM,5,23),
                                                    randAmountItemFactory(AddItem.CERIUM,11,21),
                                                    randAmountItemFactory(AddItem.MENDELEVIUM,7,17),
                                                    randAmountItemFactory(AddItem.DYSPROSIUM,3,24),
                                                    randAmountItemFactory(AddItem.MOLYBDENUM,10,14),
                                                    randAmountItemFactory(AddItem.ANTIMONY_INGOT,6,11),
                                                    randAmountItemFactory(AddItem.THALLIUM,9,10),
                                                    randAmountItemFactory(AddItem.HYDRAGYRUM,2,8),
                                                    randAmountItemFactory(AddItem.BORON,1,9)
                                            ),
                                            Utils.list(15,14,13,12,11,10,9,8,7)
                                    )
                            )

                    ),10000,
                    mkP(   mkl(setC(AddItem.PAGOLD,2) )  ,

                            mkl(
                                    setC(AddItem.ATOM_INGOT,48),
                                    setC(AddItem.PARADOX,64),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(AddItem.PALLADIUM_INGOT,17,43),
                                                    randAmountItemFactory(AddItem.PLATINUM_INGOT,11,36),
                                                    randAmountItemFactory(AddItem.CADMIUM_INGOT,15,53),
                                                    randAmountItemFactory(AddItem.BISMUTH_INGOT,23,49)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(AddItem.MOLYBDENUM,1,9),
                                                    randAmountItemFactory(AddItem.CERIUM,3,24),
                                                    randAmountItemFactory(AddItem.MENDELEVIUM,7,12),
                                                    randAmountItemFactory(AddItem.DYSPROSIUM,12,15),
                                                    randAmountItemFactory(AddItem.MOLYBDENUM,23,26),
                                                    randAmountItemFactory(AddItem.ANTIMONY_INGOT,1,3),
                                                    randAmountItemFactory(AddItem.THALLIUM,13,19),
                                                    randAmountItemFactory(AddItem.HYDRAGYRUM,11,23),
                                                    randAmountItemFactory(AddItem.BORON,6,9)
                                            ),
                                            Utils.list(12,11,10,9,8,7,15,14,13)
                                    )
                            )

                    ),10000,
                    mkP(   mkl(setC(AddItem.PLATINUM_INGOT,12) )  ,

                            mkl(
                                    setC(AddItem.STAR_GOLD_INGOT,128),
                                    setC(AddItem.PARADOX,64),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(AddItem.PALLADIUM_INGOT,23,53),
                                                    randAmountItemFactory(AddItem.PLATINUM_INGOT,32,40),
                                                    randAmountItemFactory(AddItem.CADMIUM_INGOT,23,35),
                                                    randAmountItemFactory(AddItem.BISMUTH_INGOT,13,58)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(AddItem.MOLYBDENUM,4,22),
                                                    randAmountItemFactory(AddItem.CERIUM,6,9),
                                                    randAmountItemFactory(AddItem.MENDELEVIUM,8,11),
                                                    randAmountItemFactory(AddItem.DYSPROSIUM,1,20),
                                                    randAmountItemFactory(AddItem.MOLYBDENUM,2,17),
                                                    randAmountItemFactory(AddItem.ANTIMONY_INGOT,10,14),
                                                    randAmountItemFactory(AddItem.THALLIUM,11,24),
                                                    randAmountItemFactory(AddItem.HYDRAGYRUM,13,19),
                                                    randAmountItemFactory(AddItem.BORON,7,17)
                                            ),
                                            Utils.list(9,8,7,15,14,13,12,11,10)
                                    )
                            )

                    ),10000,
                    mkP(   mkl(setC(AddItem.CADMIUM_INGOT,12) )  ,

                            mkl(
                                    setC(AddItem.STAR_GOLD_INGOT,128),
                                    setC(AddItem.PARADOX,64),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(AddItem.PALLADIUM_INGOT,17,43),
                                                    randAmountItemFactory(AddItem.PLATINUM_INGOT,13,50),
                                                    randAmountItemFactory(AddItem.CADMIUM_INGOT,17,55),
                                                    randAmountItemFactory(AddItem.BISMUTH_INGOT,20,34)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(AddItem.MOLYBDENUM,6,21),
                                                    randAmountItemFactory(AddItem.CERIUM,6,17),
                                                    randAmountItemFactory(AddItem.MENDELEVIUM,7,13),
                                                    randAmountItemFactory(AddItem.DYSPROSIUM,7,31),
                                                    randAmountItemFactory(AddItem.MOLYBDENUM,12,14),
                                                    randAmountItemFactory(AddItem.ANTIMONY_INGOT,10,24),
                                                    randAmountItemFactory(AddItem.THALLIUM,7,22),
                                                    randAmountItemFactory(AddItem.HYDRAGYRUM,14,29),
                                                    randAmountItemFactory(AddItem.BORON,11,23)
                                            ),
                                            Utils.list(1,1,1,1,1,1,1,1,1)
                                    )
                            )

                    ),10000
            )
            ).setDisplayRecipes(
                    Utils.list(
                            getInfoShow("&f机制",
                                    "&7该机器需要搭建元素嬗变机结构方可运行",
                                    "&7该多方块为可变高度多方块机器",
                                    "&7点击开启投影只会显示5层机器",
                                    "&7在顶层之下重复搭建投影的第4层即可增高多方块机器",
                                    "&7最多可重复搭建10层(投影中的第四层计入数量)",
                                    "&a每多搭建一层该机器并行处理数x2(即机器快一倍)"),null,
                            getInfoShow("&f机制",
                                    "&7该机器在构建/待机/运行时候拥有相同的电力消耗量",
                                    "&7当电力不足时会强制关机",
                                    "&7注意:为了防止意外发生,当重启/使用不安全的远程传输时,请确认机器所在区块的加载状态",
                                    "&7并确保能源网络在加载范围内并且能给机器提供足够电力"),null,
                            getInfoShow("&f机制",
                                    "&7机器自动关闭且&e进程未结束时&c会发生&c熔毁",
                                    "&7熔毁会形成范围40的1级辐射圈持续约1小时",
                                    "&7并伴有所有\"原子合金燃料棒\"损坏",
                                    "&7机器会在以下条件自动关闭",
                                    "&7- 电力不足",
                                    "&7- 人为的破坏多方块框架(挖掘,或者数据清除)",
                                    "&7- 手动关机而多方块不处于\"自动构建\"模式",
                                    "&7服务器重启时多方块会安全关闭,并在重启后自动恢复"
                            ),null
                    )
            )
            .register();
    public static final  SlimefunItem TIMER_RD=new TimerRandomtick(ADVANCED, AddItem.TIMER_RD,COMMON_TYPE,
            recipe(setC(AddItem.CHIP_CORE,4),setC(AddItem.BISILVER,4),setC(AddItem.LSINGULARITY,3),setC(AddItem.LSINGULARITY,3),setC(AddItem.BISILVER,4),setC(AddItem.CHIP_CORE,4),
                    setC(AddItem.STAR_GOLD_INGOT,3),setC(AddItem.LSCHEDULER,2),setC(AddItem.TECH_CORE,4),setC(AddItem.TECH_CORE,4),setC(AddItem.LSCHEDULER,2),setC(AddItem.STAR_GOLD_INGOT,3),
                    setC(AddItem.STAR_GOLD_INGOT,3),setC(AddItem.WORLD_FEAT,3),"6TREE_GROWTH_ACCELERATOR","6TREE_GROWTH_ACCELERATOR",setC(AddItem.WORLD_FEAT,3),setC(AddItem.STAR_GOLD_INGOT,3),
                    setC(AddItem.STAR_GOLD_INGOT,3),setC(AddItem.NETHER_FEAT,3),"3ANIMAL_GROWTH_ACCELERATOR","3ANIMAL_GROWTH_ACCELERATOR",setC(AddItem.NETHER_FEAT,3),setC(AddItem.STAR_GOLD_INGOT,3),
                    setC(AddItem.STAR_GOLD_INGOT,3),setC(AddItem.END_FEAT,3),"6CROP_GROWTH_ACCELERATOR_2","6CROP_GROWTH_ACCELERATOR_2",setC(AddItem.END_FEAT,3),setC(AddItem.STAR_GOLD_INGOT,3),
                    setC(AddItem.CHIP_CORE,4),setC(AddItem.BISILVER,4),setC(AddItem.LENGINE,2),setC(AddItem.LENGINE,2),setC(AddItem.BISILVER,4),setC(AddItem.CHIP_CORE,4)), 0,0)
            .register();
    public static final  SlimefunItem TIMER_BLOCKENTITY=new TimerBlockEntity(ADVANCED, AddItem.TIMER_BLOCKENTITY,COMMON_TYPE,
            recipe(setC(AddItem.LSINGULARITY,6),AddItem.DIMENSIONAL_SINGULARITY,AddItem.VIRTUAL_SPACE,AddItem.VIRTUAL_SPACE,AddItem.DIMENSIONAL_SINGULARITY,setC(AddItem.LSINGULARITY,6),
                    AddItem.DIMENSIONAL_SINGULARITY,setC(AddItem.MOLYBDENUM,32),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.MOLYBDENUM,32),AddItem.DIMENSIONAL_SINGULARITY,
                    AddItem.DIMENSIONAL_SINGULARITY,AddItem.LASER,AddItem.TECH_CORE,AddItem.TECH_CORE,AddItem.LASER,AddItem.DIMENSIONAL_SINGULARITY,
                    AddItem.DIMENSIONAL_SINGULARITY,AddItem.LASER,AddItem.TECH_CORE,AddItem.TECH_CORE,AddItem.LASER,AddItem.DIMENSIONAL_SINGULARITY,
                    AddItem.DIMENSIONAL_SINGULARITY,setC(AddItem.DYSPROSIUM,32),setC(AddItem.LSCHEDULER,2),setC(AddItem.LSCHEDULER,2),setC(AddItem.DYSPROSIUM,32),AddItem.DIMENSIONAL_SINGULARITY,
                    setC(AddItem.LSINGULARITY,6),AddItem.DIMENSIONAL_SINGULARITY,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.DIMENSIONAL_SINGULARITY,setC(AddItem.LSINGULARITY,6)),0,0)
            .register();

    public static final  SlimefunItem TIMER_SF=new TimerSlimefun(FUNCTIONAL, AddItem.TIMER_SF,NULL,
            formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),0,0)
            .register();
//    public static final  SlimefunItem TIMER_SF_SEQ=new TimerSequentialSlimefun(ADVANCED, AddItem.TIMER_SF_SEQ,NULL,
//            formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),0,0)
//            .register();

    //
    //manuals
    public static final SlimefunItem MANUAL_CORE=new MaterialItem(MANUAL,AddItem.MANUAL_CORE,ENHANCED_CRAFTING_TABLE,
            recipe("SAND","COBBLESTONE","GRAVEL",
                    "SAND","COBBLESTONE","GRAVEL",
                    "SAND","COBBLESTONE","GRAVEL"),null)
            .register()
            .setOutput(setC(AddItem.MANUAL_CORE,3));
    public static final SlimefunItem CRAFT_MANUAL=new ManualCrafter(MANUAL,AddItem.CRAFT_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,"CRAFTING_TABLE",AddItem.BUG,
                    null,"CRAFTING_TABLE",null,
                    null,null,null),0,0, BukkitUtils.VANILLA_CRAFTTABLE)
            .register();
    public static final SlimefunItem FURNACE_MANUAL=new ManualCrafter(MANUAL,AddItem.FURNACE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.MANUAL_CORE,"HEATING_COIL",AddItem.MANUAL_CORE,
                    "HEATING_COIL","FURNACE","HEATING_COIL",
                    AddItem.BUG,AddItem.BUG,AddItem.BUG),0,0,BukkitUtils.VANILLA_FURNACE)
            .register();
    public static final SlimefunItem ENHANCED_CRAFT_MANUAL=new ManualCrafter(MANUAL,AddItem.ENHANCED_CRAFT_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe("COBBLESTONE","COBBLESTONE","COBBLESTONE",
                    "COBBLESTONE","CRAFTING_TABLE","COBBLESTONE",
                    "COBBLESTONE","REDSTONE","COBBLESTONE"),0,0,ENHANCED_CRAFTING_TABLE)
            .register();
    public static final SlimefunItem GRIND_MANUAL=new ManualCrafter(MANUAL,AddItem.GRIND_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.BUG,null,
                    null,"OAK_FENCE",null,
                    null,"DISPENSER",null),0,0,GRIND_STONE)
            .register();
    public static final SlimefunItem ARMOR_FORGE_MANUAL=new ManualCrafter(MANUAL,AddItem.ARMOR_FORGE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.BUG,null,
                    null,"ANVIL",null,
                    null,"DISPENSER",null),0,0,ARMOR_FORGE)
            .register();
    public static final SlimefunItem ORE_CRUSHER_MANUAL=new ManualCrafter(MANUAL,AddItem.ORE_CRUSHER_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.BUG,null,
                    null,"OAK_FENCE",null,
                    "IRON_INGOT","DISPENSER","IRON_INGOT"),0,0,ORE_CRUSHER)
            .register();
    public static final SlimefunItem COMPRESSOR_MANUAL=new ManualCrafter(MANUAL,AddItem.COMPRESSOR_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.MANUAL_CORE,null,
                    null,"OAK_FENCE",null,
                    "PISTON","DISPENSER","PISTON"),0,0,COMPRESSOR)
            .register();
    public static final SlimefunItem PRESSURE_MANUAL=new ManualCrafter(MANUAL,AddItem.PRESSURE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe("GLASS","CAULDRON","GLASS",
                    "PISTON",AddItem.MANUAL_CORE,"PISTON",
                    "PISTON","DISPENSER","PISTON"),0,0,PRESSURE_CHAMBER)
            .register();
    public static final SlimefunItem MAGIC_WORKBENCH_MANUAL=new ManualCrafter(MANUAL,AddItem.MAGIC_WORKBENCH_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(
                    "COBBLESTONE",AddItem.MANUAL_CORE,"COBBLESTONE",
                    "BOOKSHELF","CRAFTING_TABLE","DISPENSER",
                    "COBBLESTONE",AddItem.MANUAL_CORE,"COBBLESTONE"),0,0,MAGIC_WORKBENCH)
            .register();
    public static final SlimefunItem ORE_WASHER_MANUAL=new RandOutManulCrafter(MANUAL,AddItem.ORE_WASHER_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,"DISPENSER",AddItem.BUG,
            null,"OAK_FENCE",null,
                    AddItem.BUG,"CAULDRON",AddItem.BUG),0,0,ORE_WASHER)
            .register();
    public static final SlimefunItem GOLD_PAN_MANUAL=new RandOutManulCrafter(MANUAL,AddItem.GOLD_PAN_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,AddItem.MANUAL_CORE,AddItem.BUG,
                   null,"OAK_TRAPDOOR",null,
                    AddItem.BUG,"CAULDRON",AddItem.BUG),0,0,GOLD_PAN)
            .register();
    public static final SlimefunItem ANCIENT_ALTAR_MANUAL=new ManualCrafter(MANUAL,AddItem.ANCIENT_ALTAR_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe("ANCIENT_PEDESTAL","ANCIENT_PEDESTAL","ANCIENT_PEDESTAL",
                    "ANCIENT_PEDESTAL","ANCIENT_ALTAR","ANCIENT_PEDESTAL",
                    "ANCIENT_PEDESTAL","ANCIENT_PEDESTAL","ANCIENT_PEDESTAL"),0,0,ANCIENT_ALTAR)
            .register();
    public static final SlimefunItem SMELTERY_MANUAL=new ManualCrafter(MANUAL,AddItem.SMELTERY_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,"NETHER_BRICK_FENCE",null,
                    "NETHER_BRICKS","DISPENSER","NETHER_BRICKS",
                    AddItem.BUG,AddItem.BUG,AddItem.BUG),0,0,SMELTERY)
            .register();
    public static final SlimefunItem CRUCIBLE_MANUAL=new ManualMachine(MANUAL,AddItem.CRUCIBLE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe("TERRACOTTA",AddItem.MANUAL_CORE,"TERRACOTTA",
                    "TERRACOTTA",AddItem.BUG,"TERRACOTTA",
                    "TERRACOTTA","FLINT_AND_STEEL","TERRACOTTA"),0,0,()->{
                        return RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.ELECTRIFIED_CRUCIBLE.getItem(),new ArrayList<>());
                })
            .register();
    public static final SlimefunItem PULVERIZER_MANUAL=new ManualMachine(MANUAL,AddItem.PULVERIZER_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,"ELECTRIC_ORE_GRINDER",null,
                    "LEAD_INGOT",AddItem.BUG,"LEAD_INGOT",
                    "MEDIUM_CAPACITOR","HEATING_COIL","MEDIUM_CAPACITOR"),0,0,()->{
        //keep a question,if we get 铸锭机 recipe.
                        return RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.ELECTRIC_INGOT_PULVERIZER.getItem(),new ArrayList<>());
            })
            .register();
    public static final SlimefunItem MULTICRAFTTABLE_MANUAL=new ManualCrafter(MANUAL,AddItem.MULTICRAFTTABLE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,AddItem.MANUAL_CORE,"OUTPUT_CHEST",
                    AddItem.MANUAL_CORE,AddItem.ANCIENT_ALTAR_MANUAL,"CRAFTING_TABLE",
                    "BOOKSHELF","CRAFTING_TABLE","DISPENSER"),0,0,ENHANCED_CRAFTING_TABLE,MAGIC_WORKBENCH,ANCIENT_ALTAR,SMELTERY)
            .register();
    public static final SlimefunItem TABLESAW_MANUAL=new ManualMachine(MANUAL,AddItem.TABLESAW_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,
                    "SMOOTH_STONE_SLAB","STONECUTTER","SMOOTH_STONE_SLAB",
                    AddItem.MANUAL_CORE,"IRON_BLOCK",AddItem.MANUAL_CORE),0,0,()->{
                        return RecipeSupporter.MULTIBLOCK_RECIPES.get(SlimefunItems.TABLE_SAW.getItem());
                 })
            .register();
    public static final SlimefunItem COMPOSTER=new ManualMachine(MANUAL,AddItem.COMPOSTER,ENHANCED_CRAFTING_TABLE,
            recipe("OAK_SLAB",AddItem.MANUAL_CORE,"OAK_SLAB",
                    "OAK_SLAB",AddItem.BUG,"OAK_SLAB",
                    "OAK_SLAB","CAULDRON","OAK_SLAB"),0,0,()->{
                return RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.COMPOSTER.getItem(),new ArrayList<>());
            })
            .register();
    public static final SlimefunItem MULTIMACHINE_MANUAL=new ManualMachine(MANUAL,AddItem.MULTIMACHINE_MANUAL,COMMON_TYPE,
            recipe(null,null,AddItem.LFIELD,AddItem.LFIELD,null,null,
                    null,AddItem.LFIELD,"HEATED_PRESSURE_CHAMBER","HEATED_PRESSURE_CHAMBER",AddItem.LFIELD,null,
                    AddItem.LFIELD,"FOOD_COMPOSTER",AddItem.NOLOGIC,AddItem.NOLOGIC,"FOOD_COMPOSTER",AddItem.LFIELD,
                    AddItem.LFIELD,"FOOD_FABRICATOR",AddItem.NOLOGIC,AddItem.NOLOGIC,"FOOD_FABRICATOR",AddItem.LFIELD,
                    null,AddItem.LFIELD,"HEATED_PRESSURE_CHAMBER","HEATED_PRESSURE_CHAMBER",AddItem.LFIELD,null,
                    null,null,AddItem.LFIELD,AddItem.LFIELD,null,null),0,0,()->{
                List<MachineRecipe> recipelist=new ArrayList<>();
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.HEATED_PRESSURE_CHAMBER.getItem(),new ArrayList<>()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.AUTO_DRIER.getItem(),new ArrayList<>()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.REFINERY.getItem(),new ArrayList<>()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.FOOD_COMPOSTER.getItem(),new ArrayList<>()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.FOOD_FABRICATOR.getItem(),new ArrayList<>()));
                return recipelist;
            })
            .register();
    public static final SlimefunItem MULTIBLOCK_MANUAL=new MultiBlockManual(MANUAL,AddItem.MULTIBLOCK_MANUAL,COMMON_TYPE,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.STAR_GOLD_INGOT,
                    AddItem.BUG,AddItem.LFIELD,"OAK_FENCE","OAK_FENCE",AddItem.LFIELD,AddItem.BUG,
                    AddItem.BUG,AddItem.LCRAFT,AddItem.LSINGULARITY,AddItem.CHIP_CORE,AddItem.LCRAFT,AddItem.BUG,
                    AddItem.BUG,AddItem.WORLD_FEAT,AddItem.VIRTUAL_SPACE,AddItem.MANUAL_CORE,AddItem.WORLD_FEAT,AddItem.BUG,
                    AddItem.BUG,AddItem.LFIELD,"DISPENSER","DISPENSER",AddItem.LFIELD,AddItem.BUG,
                    AddItem.STAR_GOLD_INGOT,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.STAR_GOLD_INGOT),0,0,null)
            .register();
    public static final SlimefunItem FAKE_UI=new MaterialItem(MANUAL,AddItem.FAKE_UI,ENHANCED_CRAFTING_TABLE,
            recipe("COBBLESTONE","GLASS_PANE","COBBLESTONE","COBBLESTONE","GLASS_PANE","COBBLESTONE",
                    "COBBLESTONE","GLASS_PANE","COBBLESTONE"),List.of(getInfoShow("&f机制 - &7伪装","&7该物品可以在快捷多方块结构模拟器中","&7填充多方块模拟部分的空白","&7多方块模拟检测会忽视该物品")))
            .register().setOutput(setC(AddItem.FAKE_UI,4));
    public static final ReplaceCard REPLACE_CARD=(ReplaceCard) (new ReplaceCard(MANUAL,AddItem.REPLACE_CARD,NULL,
            formatInfoRecipe(AddItem.CARD_MAKER,Language.get("Manuals.CARD_MAKER.Name")), ReplaceCard.ReplaceType.MATERIAL)
            .register());
    public static final ReplaceCard REPLACE_SF_CARD=(ReplaceCard) new ReplaceCard(MANUAL,AddItem.REPLACE_SF_CARD,NULL,
            formatInfoRecipe(AddItem.CARD_MAKER,Language.get("Manuals.CARD_MAKER.Name")), ReplaceCard.ReplaceType.SLIMEFUN)
            .register();
    public static final  SlimefunItem CARD_MAKER=new EWorkBench(MANUAL, AddItem.CARD_MAKER,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.MANUAL_CORE,AddItem.MANUAL_CORE,AddItem.MANUAL_CORE,AddItem.MANUAL_CORE,"CRAFTING_TABLE",AddItem.MANUAL_CORE,
                    AddItem.MANUAL_CORE,AddItem.MANUAL_CORE,AddItem.MANUAL_CORE),0,0,64,
            ()->{
                List<MachineRecipe> shapedRecipesVanilla=RecipeSupporter.PROVIDED_SHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE);
                List<MachineRecipe> cardRecipe=new ArrayList<>();
                cardRecipe.addAll(RecipeSupporter.UNUSTACKABLE_ITEM_RECIPES);

                for(MachineRecipe rp:shapedRecipesVanilla){
                    if(rp.getOutput()[0].getType().getMaxStackSize()==1){
                        cardRecipe.add(MachineRecipeUtils.shapeFrom(-1,rp.getInput(),recipe(REPLACE_CARD.getReplaceCard(rp.getOutput()[0].getType()))));
                    }
                }
                HashSet<RecipeType> supportedType=new HashSet<>(){{
                    add(ENHANCED_CRAFTING_TABLE);
                    add(RecipeType.MAGIC_WORKBENCH);
                    add(RecipeType.ANCIENT_ALTAR);
                    add(RecipeType.SMELTERY);
                }};
                for(SlimefunItem item: Slimefun.getRegistry().getAllSlimefunItems()){
                    if(item.getRecipeOutput().getType().getMaxStackSize()==1&& supportedType.contains( item.getRecipeType())&&item.getRecipe().length<=9){
                        cardRecipe.add(MachineRecipeUtils.shapeFrom(-1,Arrays.copyOf(item.getRecipe(), 9),recipe(REPLACE_SF_CARD.getReplaceCard(item))));
                    }
                }
                return cardRecipe;
            }){
        private List<MachineRecipe> unreplacedRecipe=null;
        private List<MachineRecipe> getUnreplacedRecipe(){
            if(unreplacedRecipe==null){
                List<MachineRecipe> targetRecipe = new ArrayList<>();
                int size=machineRecipes.size();
                for (int i=0;i<size;++i) {
                    targetRecipe.add(MachineRecipeUtils.shapeFrom(machineRecipes.get(i).getTicks(),
                            machineRecipes.get(i).getInput(),
                            new ItemStack[]{CRAFT_PROVIDER.get(Settings.INPUT,machineRecipes.get(i).getOutput()[0],-1).getItem()} ));
                }
                unreplacedRecipe=targetRecipe;
            }
            return unreplacedRecipe;
        }
        {
            CRAFT_PROVIDER= FinalFeature.MANUAL_CARD_READER;
        }
        @Override
        public List<MachineRecipe> provideDisplayRecipe(){
            List<MachineRecipe> machineRecipes =  getUnreplacedRecipe();
            List<MachineRecipe> targetRecipe = new ArrayList<>();
            int size=machineRecipes.size();
            for (int i=0;i<size;++i) {
                targetRecipe.add(MachineRecipeUtils.stackFrom(machineRecipes.get(i).getTicks(),
                        Utils.array(getInfoShow("&f有序配方合成","&7请在配方显示界面或者机器界面查看")),
                        machineRecipes.get(i).getOutput() ));
            }
            return targetRecipe;
        }
        @Override
        public MenuFactory getRecipeMenu(Block b, BlockMenu inv){

            return MenuUtils.createMRecipeListDisplay(getItem(),getUnreplacedRecipe(),null,(MenuUtils.RecipeMenuConstructor)(itemstack, recipes, backhandler,optional,history)->{
                return MenuUtils.createMRecipeDisplay(itemstack,recipes,backhandler,optional).addOverrides(8,LAZY_ONECLICK).addHandler(8,((player, i, itemStack, clickAction) -> {
                    moveRecipe(player,inv,recipes, clickAction.isRightClicked());
                    return false;
                }));
            });
        }
    }
            .setDisplayRecipes(
                    Utils.list(
                            getInfoShow("&f配方说明",
                                    "&7你将会在这里的配方中找到所有可以在原版工作台中合成的物品的替代卡配方",
                                    "&7其配方读取于原版合成配方",
                                    "&a你还会找到一些由@haiman添加的其他不可堆叠物品的替代卡配方",
                                    "&7如各种桶,下界合金工具等等")
                    )
            )
            .register();
    public static final SlimefunItem ADV_MANUAL=new AdvancedManual(MANUAL,AddItem.ADV_MANUAL,COMMON_TYPE,
            recipe(setC(AddItem.BUG,4),AddItem.ENHANCED_CRAFT_MANUAL,AddItem.ENHANCED_CRAFT_MANUAL,AddItem.ENHANCED_CRAFT_MANUAL,AddItem.ENHANCED_CRAFT_MANUAL,setC(AddItem.BUG,4),
                    AddItem.SMELTERY_MANUAL,AddItem.LFIELD,AddItem.LENGINE,AddItem.LENGINE,AddItem.LFIELD,AddItem.FURNACE_MANUAL,
                    AddItem.SMELTERY_MANUAL,AddItem.LFIELD,setC(AddItem.MANUAL_CORE,16),setC(AddItem.MANUAL_CORE,16),AddItem.LFIELD,AddItem.FURNACE_MANUAL,
                    AddItem.SMELTERY_MANUAL,AddItem.LFIELD,setC(AddItem.MANUAL_CORE,16),setC(AddItem.MANUAL_CORE,16),AddItem.LFIELD,AddItem.FURNACE_MANUAL,
                    AddItem.SMELTERY_MANUAL,AddItem.LFIELD,AddItem.LENGINE,AddItem.LENGINE,AddItem.LFIELD,AddItem.FURNACE_MANUAL,
                    setC(AddItem.BUG,4),AddItem.ANCIENT_ALTAR_MANUAL,AddItem.ANCIENT_ALTAR_MANUAL,AddItem.ANCIENT_ALTAR_MANUAL,AddItem.ANCIENT_ALTAR_MANUAL,setC(AddItem.BUG,4)))
            .register();


    //cargo items
    public static final SlimefunItem CARGO_PART=new MaterialItem(CARGO,AddItem.CARGO_PART,ENHANCED_CRAFTING_TABLE,
            recipe("CARGO_NODE","CARGO_NODE_OUTPUT","CARGO_NODE"
                    ,AddItem.FALSE_,null,AddItem.TRUE_,
                    "CARGO_NODE","CARGO_NODE_INPUT","CARGO_NODE"),null)
            .register().setOutput(setC(AddItem.CARGO_PART,64));
    public static final SlimefunItem CARGO_CONFIG=new ConfigCard(CARGO,AddItem.CARGO_CONFIG,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.TRUE_,AddItem.CARGO_PART,AddItem.TRUE_,
                    AddItem.NOLOGIC,AddItem.BUG,AddItem.LOGIC,
                    AddItem.FALSE_,AddItem.CARGO_PART,AddItem.FALSE_))
            .register().setOutput(setC(AddItem.CARGO_CONFIG,32));
    public static final SlimefunItem CARGO_CONFIGURATOR=new CargoConfigurator(CARGO,AddItem.CARGO_CONFIGURATOR,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,AddItem.ABSTRACT_INGOT,AddItem.BUG,AddItem.ABSTRACT_INGOT,AddItem.BUG,AddItem.ABSTRACT_INGOT,
                    "OAK_PLANKS","CARTOGRAPHY_TABLE","OAK_PLANKS"))
            .register();
    public static final SlimefunItem CONFIGURE=new ConfigCopier(CARGO,AddItem.CONFIGURE,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,ChipCardCode.CHIP_0,AddItem.ABSTRACT_INGOT,AddItem.BUG,AddItem.CARGO_PART,AddItem.BUG,
                    AddItem.ABSTRACT_INGOT,ChipCardCode.CHIP_1,AddItem.ABSTRACT_INGOT))
            .register();
    public static final SlimefunItem ADV_TRASH=new TrashCan(CARGO,AddItem.ADV_TRASH,ENHANCED_CRAFTING_TABLE,
            recipe("PORTABLE_DUSTBIN",AddItem.LOGIC,"PORTABLE_DUSTBIN",AddItem.LOGIC,"FLINT_AND_STEEL",AddItem.LOGIC,
                    "PORTABLE_DUSTBIN",AddItem.LOGIC,"PORTABLE_DUSTBIN"))
            .register();
    public static final  SlimefunItem STORAGE=new Storage(CARGO, AddItem.STORAGE,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"CHEST",AddItem.BUG,"CHEST",
                    null,null,null),Storage.COMMON_INPUT_SLOT,Storage.COMMON_OUTPUT_SLOT)
            .register();
    public static final SlimefunItem STORAGE_INPUT=new Storage(CARGO  ,AddItem.STORAGE_INPUT,ENHANCED_CRAFTING_TABLE,
            recipe(null,"HOPPER",null,"CHEST",AddItem.BUG,"CHEST",
                    null,null,null),Storage.COMMON_INPUT_SLOT,new int[0])
            .register();
    public static final SlimefunItem STORAGE_OUTPUT=new Storage(CARGO,AddItem.STORAGE_OUTPUT,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"CHEST",AddItem.BUG,"CHEST",
                    null,"HOPPER",null),new int[0],Storage.COMMON_OUTPUT_SLOT)
            .register();
    public static final SlimefunItem CARGO_PIP=new CargoPipe(CARGO,AddItem.CARGO_PIP,ENHANCED_CRAFTING_TABLE,
            recipe("SOLDER_INGOT","HOPPER","SOLDER_INGOT","SOLDER_INGOT","BRASS_INGOT","SOLDER_INGOT",
                    "SOLDER_INGOT","HOPPER","SOLDER_INGOT"))
            .register().setOutput(setC(AddItem.CARGO_PIP,2));
    public static final SlimefunItem SIMPLE_CARGO=new AdjacentCargo(CARGO,AddItem.SIMPLE_CARGO,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,"HOPPER",AddItem.ABSTRACT_INGOT,
                    null,AddItem.CARGO_PART ,null,
                    "IRON_INGOT","IRON_INGOT","IRON_INGOT"),
            list(getInfoShow("&f机制","")))
            .register();
    public static final SlimefunItem REMOTE_CARGO=new RemoteCargo(CARGO,AddItem.REMOTE_CARGO,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.HYPER_LINK,AddItem.ABSTRACT_INGOT
                    ,"CARGO_NODE",AddItem.CARGO_PART,"CARGO_NODE",
                    AddItem.PARADOX,AddItem.PARADOX,AddItem.PARADOX),
            null)
            .register();
    public static final SlimefunItem LINE_CARGO=new LineCargo(CARGO,AddItem.LINE_CARGO,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,"HOPPER",AddItem.ABSTRACT_INGOT,AddItem.NOLOGIC,AddItem.CARGO_PART,AddItem.NOLOGIC,
                    "IRON_INGOT","HOPPER","IRON_INGOT"),null)
            .register();
    public static final  SlimefunItem BISORTER=new BiSorter(CARGO, AddItem.BISORTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.CARGO_PART,AddItem.CARGO_PART,AddItem.PARADOX,null,
                    setC(AddItem.PARADOX,1),AddItem.LBOOLIZER,"HOPPER","HOPPER",AddItem.LBOOLIZER,setC(AddItem.PARADOX,1),
                    setC(AddItem.PARADOX,1),AddItem.LBOOLIZER,AddItem.LSCHEDULER,"HOPPER",AddItem.LBOOLIZER,setC(AddItem.PARADOX,1),
                    null,AddItem.PARADOX,AddItem.CARGO_PART,AddItem.CARGO_PART,AddItem.PARADOX,null))
            .register();
    public static final SlimefunItem QUARSORTER=new QuarSorter(CARGO,AddItem.QUARSORTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.LFIELD,AddItem.LFIELD,AddItem.PARADOX,null,
                    setC(AddItem.PARADOX,1),AddItem.CARGO_PART,"2HOPPER","HOPPER",AddItem.CARGO_PART,setC(AddItem.PARADOX,1),
                    setC(AddItem.PARADOX,1),AddItem.CARGO_PART,AddItem.BISORTER,"2HOPPER",AddItem.CARGO_PART,setC(AddItem.PARADOX,1),
                    null,AddItem.PARADOX,AddItem.LFIELD,AddItem.LFIELD,AddItem.PARADOX,null
                    ))
            .register();
    public static final SlimefunItem OCTASORTER=new OctaSorter(CARGO,AddItem.OCTASORTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,null,
                    setC(AddItem.PARADOX,1),"HOPPER",setC(AddItem.LBOOLIZER,1),AddItem.LSCHEDULER,"HOPPER",setC(AddItem.PARADOX,1),
                    setC(AddItem.PARADOX,1),"HOPPER",AddItem.QUARSORTER,setC(AddItem.LBOOLIZER,1),"HOPPER",setC(AddItem.PARADOX,1),
                    null,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,null))
            .register();
    public static final  SlimefunItem BIFILTER=new BiFilter(CARGO, AddItem.BIFILTER,COMMON_TYPE   ,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,"CHEST","CHEST",AddItem.PARADOX,null,
                    AddItem.PARADOX,AddItem.LOGIGATE,AddItem.LOGIGATE,AddItem.LOGIGATE,AddItem.LOGIGATE,AddItem.PARADOX,
                    AddItem.PARADOX,AddItem.LOGIGATE,"HOPPER","HOPPER",AddItem.LOGIGATE,AddItem.PARADOX,
                    null,AddItem.PARADOX,AddItem.ADV_TRASH,AddItem.ADV_TRASH,AddItem.PARADOX,null))
            .register();
    public static final SlimefunItem QUARFILTER=new QuarFilter(CARGO,AddItem.QUARFILTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.CARGO_PART,AddItem.CARGO_PART,AddItem.PARADOX,null,
                    AddItem.PARADOX,AddItem.LFIELD,AddItem.LOGIGATE,"HOPPER",AddItem.LFIELD,AddItem.PARADOX,
                    AddItem.PARADOX,AddItem.LFIELD,AddItem.BIFILTER,AddItem.LOGIGATE,AddItem.LFIELD,AddItem.PARADOX,
                    null,AddItem.PARADOX,AddItem.CARGO_PART,AddItem.CARGO_PART,AddItem.PARADOX,null))
            .register();
    public static final SlimefunItem OCTAFILTER=new OctaFilter(CARGO,AddItem.OCTAFILTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,null,
                    AddItem.PARADOX,AddItem.LBOOLIZER,"HOPPER","HOPPER",AddItem.LBOOLIZER,AddItem.PARADOX,
                    AddItem.PARADOX,AddItem.LBOOLIZER,AddItem.ADV_TRASH,AddItem.QUARFILTER,AddItem.LBOOLIZER,AddItem.PARADOX,
                    null,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,null))
            .register();
    public static final SlimefunItem STORAGE_OPERATOR=new StorageOperator(SINGULARITY,AddItem.STORAGE_OPERATOR,MAGIC_WORKBENCH,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.LIOPORT,AddItem.PARADOX,
                    AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT))
            .register();
    public static final SlimefunItem ADV_ADJACENT_CARGO=new AdjacentCargoPlus(CARGO,AddItem.ADV_ADJACENT_CARGO,MAGIC_WORKBENCH,
            recipe(AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX,AddItem.ABSTRACT_INGOT,AddItem.SIMPLE_CARGO,AddItem.ABSTRACT_INGOT,
                    AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX),null)
            .register();
    public static final SlimefunItem ADV_REMOTE_CARGO=new RemoteCargoPlus(CARGO,AddItem.ADV_REMOTE_CARGO,MAGIC_WORKBENCH,
            recipe(AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX,AddItem.ABSTRACT_INGOT,AddItem.REMOTE_CARGO,AddItem.ABSTRACT_INGOT,
                    AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX),null)
            .register();
    public static final SlimefunItem ADV_LINE_CARGO=new LineCargoPlus(CARGO,AddItem.ADV_LINE_CARGO,MAGIC_WORKBENCH,
            recipe(AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX,AddItem.ABSTRACT_INGOT,AddItem.LINE_CARGO,AddItem.ABSTRACT_INGOT,
                    AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX),null)
            .register();
    public static final  SlimefunItem REDSTONE_ADJACENT_CARGO=new RedstoneAdjacentCargo(CARGO, AddItem.REDSTONE_ADJACENT_CARGO,MAGIC_WORKBENCH,
            recipe(AddItem.ADV_ADJACENT_CARGO,AddItem.PARADOX,AddItem.ADV_ADJACENT_CARGO,AddItem.LOGIGATE,AddItem.REDSTONE_ENGINE,AddItem.LOGIGATE,
                    AddItem.ADV_ADJACENT_CARGO,AddItem.LOGIGATE,AddItem.ADV_ADJACENT_CARGO), null)
            .register().setOutput(setC(AddItem.REDSTONE_ADJACENT_CARGO,4));
    public static final SlimefunItem CHIP_ADJ_CARGO=new ChipAdjacentCargo(CARGO,AddItem.CHIP_ADJ_CARGO,MAGIC_WORKBENCH,
            recipe(AddItem.ADV_ADJACENT_CARGO,AddItem.PARADOX,AddItem.ADV_ADJACENT_CARGO,AddItem.LOGIGATE,AddItem.CHIP_CORE,AddItem.LOGIGATE,
                    AddItem.ADV_ADJACENT_CARGO,AddItem.LOGIGATE,AddItem.ADV_ADJACENT_CARGO),null)
            .register().setOutput(setC(AddItem.CHIP_ADJ_CARGO,4));
    public static final SlimefunItem RESETTER=new StorageCleaner(SINGULARITY,AddItem.RESETTER,ENHANCED_CRAFTING_TABLE,
            recipe("CRAFTING_TABLE",AddItem.ABSTRACT_INGOT,"CRAFTING_TABLE",AddItem.ABSTRACT_INGOT,"TRASH_CAN_BLOCK",AddItem.ABSTRACT_INGOT,
                    "CRAFTING_TABLE",AddItem.ABSTRACT_INGOT,"CRAFTING_TABLE"))
            .register();
    public static final SlimefunItem STORAGE_SINGULARITY=new Singularity(SINGULARITY, AddItem.STORAGE_SINGULARITY, ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT,
            AddItem.PARADOX,"CHEST",AddItem.PARADOX,
            AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT))
            .register();
    public static final SlimefunItem QUANTUM_LINK=new QuantumLink(SINGULARITY,AddItem.QUANTUM_LINK,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.PARADOX,AddItem.DIMENSIONAL_SHARD,AddItem.PARADOX,AddItem.DIMENSIONAL_SHARD,AddItem.LSINGULARITY,AddItem.DIMENSIONAL_SHARD,
                    AddItem.ABSTRACT_INGOT,AddItem.BUG,AddItem.ABSTRACT_INGOT))
            .register();
    public static final  SlimefunItem INPORT=new InputPort(SINGULARITY, AddItem.INPORT,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,"CHEST","CHEST",AddItem.PARADOX,null,
                    null,"CHEST",AddItem.UNIQUE,AddItem.EXISTE,"CHEST",null,
                    null,"CHEST",AddItem.LOGIC,AddItem.NOLOGIC,"CHEST",null,
                    null,AddItem.PARADOX,"CHEST","CHEST",AddItem.PARADOX,null
                    ))
            .register();
    public static final  SlimefunItem OUTPORT=new OutputPort(SINGULARITY, AddItem.OUTPORT,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null,
                    null,"CHEST",AddItem.EXISTE,AddItem.UNIQUE,"CHEST",null,
                    null,"CHEST",AddItem.NOLOGIC,AddItem.LOGIC,"CHEST",null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null
                    ))
            .register();
    public static final SlimefunItem IOPORT=new IOPort(SINGULARITY,AddItem.IOPORT,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null,
                    null,"CHEST",AddItem.PARADOX,AddItem.PARADOX,"CHEST",null,
                    null,"CHEST",AddItem.PARADOX,AddItem.PARADOX,"CHEST",null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null
            ))
            .register();




//TODO add descriptions to these shits


    //special
    public static final SlimefunItem HEAD_ANALYZER= new HeadAnalyzer(SPECIAL,AddItem.HEAD_ANALYZER,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"PLAYER_HEAD",BUG,"PLAYER_HEAD",null,null,null)
    ).register();
    public static final SlimefunItem RECIPE_LOGGER=new RegisteryLogger(SPECIAL,AddItem.RECIPE_LOGGER,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"CRAFTING_TABLE",BUG,Material.WRITABLE_BOOK,null,null))
            .register();
    public static final SlimefunItem LASER_GUN= new LaserGun(TOOLS_FUNCTIONAL, AddItem.LASER_GUN, SmithInterfaceProcessor.INTERFACED_CRAFTTABLE,
            recipe(AddItem.STAR_GOLD_INGOT, AddItem.LSINGULARITY, AddItem.STAR_GOLD_INGOT, "ENERGIZED_CAPACITOR", AddItem.LASER, "ENERGIZED_CAPACITOR",
                    AddItem.STAR_GOLD_INGOT, AddItem.LSINGULARITY, AddItem.STAR_GOLD_INGOT))
            .register();
    public static final SlimefunItem TRACE_ARROW=new TrackingArrowLauncher(TOOLS_FUNCTIONAL,AddItem.TRACE_ARROW,SmithInterfaceProcessor.INTERFACED_CRAFTTABLE,
            recipe(AddItem.LSINGULARITY,"EXPLOSIVE_BOW",AddItem.LSINGULARITY,"ENERGIZED_CAPACITOR",AddItem.ATOM_INGOT,"ENERGIZED_CAPACITOR",
                    AddItem.LSINGULARITY,"ICY_BOW",AddItem.LSINGULARITY))
            .register();
    public static final SlimefunItem RTP_RUNE=new CustomItemWithHandler<ItemDropHandler>(SPACE,AddItem.RTP_RUNE,ANCIENT_ALTAR,
            recipe("ANCIENT_RUNE_EARTH","ANCIENT_RUNE_ENDER","ANCIENT_RUNE_FIRE",AddItem.HYPER_LINK,AddItem.DIMENSIONAL_SINGULARITY,AddItem.HYPER_LINK,
                    "ANCIENT_RUNE_WATER","ANCIENT_RUNE_ENDER","ANCIENT_RUNE_AIR"),null) {
        HashSet<String> BANNED_WORLD_NAME=new HashSet<>(){{
           add("logispace" );
        }};
        protected ItemSetting<Boolean> rtpChangeWorld = createForce("rtp-change-world",false);
        protected ItemSetting<Integer> rtpRange2pow =createForce("rtp-max-amount",14);
        public void addInfo(ItemStack item){
            item.setItemMeta(addLore(item, "&7世界范围: "+(rtpChangeWorld.getValue()? "随机,50%概率不改变世界":"当前世界"),
                    "&7坐标范围: 2^(max(10+丢出数量,%s))".formatted(String.valueOf(10+rtpRange2pow.getValue()))).getItemMeta());
        }
        int DELAY_BEFORE_TP=60;
        int RETRY_TIME=5;
        public boolean onDrop(PlayerDropItemEvent var1, Player var2, Item var3){
            if(isItem(var3.getItemStack())){
                if(canUse(var2,true)){
                    Schedules.launchSchedules(
                            ()-> {
                                if(!var3.isValid())return;
                                ItemStack stack=var3.getItemStack();
                                int amount=stack.getAmount();
                                stack.setAmount(0);
                                var3.setItemStack(stack);
                                Location center= var3.getLocation();
                                center.getWorld().strikeLightningEffect(center);
                                int ranges=1<<(10+Math.min(amount/2,rtpRange2pow.getValue()));
                                sendMessage(var2,"&a即将开始随机传送,传送范围: "+ranges);
                                BukkitUtils.executeAsync(
                                        ()->{
                                            onRtp(var2,center,ranges,RETRY_TIME);
                                        }
                                );
                            }
                            ,30,true,0 );
                }
                return true;
            }
            return false;
        }
        public Random rand=new Random();
        public double validCoord(double origin,double min,double max){
            return Math.max(min,Math.min(origin,max));
        }
        public void onRtp(Player player,Location center,int range,int leftTime){
            AsyncResultRunnable<Boolean> effectResult=new AsyncResultRunnable<Boolean>() {
                @Override
                public Boolean result() {
                    return onEffect(player);
                }
            };
            CountDownLatch latch=effectResult.runThreadBackground();
            Location loc=onRandomLocationFind(center,range);
            //预加载的过程中执行例子效果
            try{
                latch.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(!effectResult.getResult()){
                return;
            }
            if(loc!=null){
                Schedules.launchSchedules(()->{
                    player.teleport(loc);
                    sendTitle(player,"&a传送成功!","");
                },0,true,0);
            }
            else{
                if(leftTime>0){
                    sendTitle(player,"&c传送失败!剩余次数%d次".formatted(leftTime),"&e将在三秒后重新传送");
                    Schedules.launchSchedules(()->{
                        onRtp(player,center,range,leftTime-1);
                    },50,false,0);
                }else{
                    sendTitle(player,"&c传送失败!","");
                }
            }
        }
        int EFFECT_RANGE=3;
        int EFFECT_PERIOD=4;
        public Location onRandomLocationFind(Location center,int range){
            World world;
            //不切换世界
            if(!rtpChangeWorld.getValue() &&rand.nextInt(20)%2==0){
                world=center.getWorld();
            }else{
                do{
                    world=Bukkit.getWorlds().get(rand.nextInt(Bukkit.getWorlds().size()));
                }while (BANNED_WORLD_NAME.contains(world.getName()));
            }
            WorldBorder border=world.getWorldBorder();
            Location worldcenter=border.getCenter();
            double size=border.getSize()/2;
            double minX=worldcenter.getX()-size;
            double maxX=worldcenter.getX()+size;
            double minZ=worldcenter.getZ()-size;
            double maxZ=worldcenter.getZ()+size;
            Location newLocation=new Location(world,validCoord(center.getBlockX()+rand.nextDouble(-range,range),minX,maxX),
                    rand.nextDouble(world.getMinHeight()+16, world.getMaxHeight()-16),
                    validCoord(center.getBlockZ()+rand.nextDouble(-range,range),minZ,maxZ));
            int x=newLocation.getBlockX()>>4;
            int z=newLocation.getBlockZ()>>4;
            //执行预加载 提前生成3x3范围
            Schedules.launchRepeatingSchedule((i)->{

                newLocation.getWorld().getChunkAt(x-1+i%3,z-1+i/3,true);


            },1,false,6,9);
            while(true){
                if(newLocation.getBlock().getType().isAir()){
                    return newLocation;
                }
                newLocation.add(0,-1,0);

                if(newLocation.getY()<world.getMinHeight()+16){
                    return null;
                }
            }

        }
        public boolean onEffect(Player player){
            HashSet<Location> originLocations=new HashSet<>();
            Location loc=player.getLocation();
            World world=player.getWorld();
            for(int i=-89;i<89;i+=2){
                for(int j=0;j<4;++j){
                    Location testLoc=loc.clone();
                    testLoc.setYaw(testLoc.getYaw()+45+j*90 );
                    testLoc.setPitch(i);
                    originLocations.add(testLoc);
                }
            }
            HashSet<Location> effectLocations=new HashSet<>(originLocations);
            AtomicBoolean hasCancelled=new AtomicBoolean(false);
            Schedules.asyncWaithRepeatingSchedule((i)->{
                if(!hasCancelled.get() ){
                    if(player.getWorld()!=world||player.getLocation().distance(loc)>0.25){
                        hasCancelled.set(true);
                        sendTitle(player,"&c传送已被取消!","&e您的位置移动了!");
                        return;
                    }


                    if((DELAY_BEFORE_TP-EFFECT_PERIOD*i) %20<EFFECT_PERIOD){
                        sendTitle(player,"&a即将传送,请勿移动!","&e倒计时 %d 秒".formatted((DELAY_BEFORE_TP-EFFECT_PERIOD*i) /20));
                    }
                    for(Location location:originLocations){
                        Location testLoc2=location.clone();
                        testLoc2.setYaw(location.getYaw()+(i*90*EFFECT_PERIOD)/DELAY_BEFORE_TP);
                        effectLocations.add(testLoc2);
                    }
                    for(Location location:effectLocations){
                        Location particlePosition=location.clone().add(location.getDirection().multiply(3));
                        world.spawnParticle(Particle.SOUL_FIRE_FLAME,particlePosition,0,0.0,0.0,0.0,1,null,true);
                    }
                }
            },0,false,EFFECT_PERIOD,DELAY_BEFORE_TP/EFFECT_PERIOD);
            return !hasCancelled.get();
        }
        public ItemDropHandler[] getItemHandler(){
            return new ItemDropHandler[]{this::onDrop};
        }
    }
            .register().setOutput(setC(AddItem.RTP_RUNE,2));
    public static final SlimefunItem ANTIGRAVITY=new ChargableProps(SPACE, AddItem.ANTIGRAVITY, RecipeType.ANCIENT_ALTAR,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.LSINGULARITY,AddItem.STAR_GOLD_INGOT,"ANCIENT_RUNE_ENDER",AddItem.DIMENSIONAL_SINGULARITY,"ANCIENT_RUNE_ENDER",
                    AddItem.STAR_GOLD_INGOT,AddItem.LSINGULARITY,AddItem.STAR_GOLD_INGOT)) {
        private final float maxCharge=1_296_000.0f;
        private final float energyConsumption=640.0f;
        @Override
        public float getMaxItemCharge(ItemStack var1) {
            return maxCharge;
        }
        @Override
        public void onClickAction(PlayerRightClickEvent event) {
            ItemStack item=event.getItem();
            float itemCharge;
            if(item==null||item.getType().isAir()){
                return;
            }
            if( (itemCharge=getItemCharge(item))>=energyConsumption){
                setItemCharge(item,itemCharge-energyConsumption);
                event.getPlayer().addPotionEffect(PotionEffectType.LEVITATION.createEffect(160,255));
            }else{
                sendMessage(event.getPlayer(),concat("&c电力不足! ",String.valueOf(itemCharge),"J/",String.valueOf(energyConsumption),"J"));
            }
        }
    }
            .register();
    public static final SlimefunItem SPACE_CARD=new SpaceStorageCard(SPACE,AddItem.SPACE_CARD,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.BISILVER,AddItem.STAR_GOLD_INGOT,AddItem.BISILVER,AddItem.LSINGULARITY,AddItem.BISILVER,
                    AddItem.STAR_GOLD_INGOT,AddItem.BISILVER,AddItem.STAR_GOLD_INGOT))
            .register();
    public static final  SlimefunItem SPACETOWER_FRAME=new SpaceTowerFrame(SPACE, AddItem.SPACETOWER_FRAME,COMMON_TYPE,
            recipe(AddItem.BISILVER,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.BISILVER,
                    AddItem.STAR_GOLD_INGOT,AddItem.WORLD_FEAT,AddItem.VIRTUAL_SPACE,AddItem.VIRTUAL_SPACE,AddItem.WORLD_FEAT,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,setC(AddItem.ATOM_INGOT,16),AddItem.STACKFRAME,AddItem.STACKFRAME,setC(AddItem.ATOM_INGOT,16),AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,setC(AddItem.ATOM_INGOT,16),AddItem.STACKFRAME,AddItem.STACKFRAME,setC(AddItem.ATOM_INGOT,16),AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.WORLD_FEAT,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.WORLD_FEAT,AddItem.STAR_GOLD_INGOT,
                    AddItem.BISILVER,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.BISILVER))
            .register().setOutput(setC(AddItem.SPACETOWER_FRAME,16));
    public static final  SlimefunItem SPACETOWER=new SpaceTower(SPACE, AddItem.SPACETOWER,COMMON_TYPE,
            recipe(AddItem.DIMENSIONAL_SINGULARITY,AddItem.LIOPORT,AddItem.LENGINE,AddItem.LENGINE,AddItem.LIOPORT,AddItem.DIMENSIONAL_SINGULARITY,
                    AddItem.LIOPORT,AddItem.SPACE_PLATE,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.SPACE_PLATE,AddItem.LIOPORT,
                    AddItem.SPACE_PLATE,AddItem.DIMENSIONAL_SINGULARITY,AddItem.LSCHEDULER,AddItem.LSCHEDULER,AddItem.DIMENSIONAL_SINGULARITY,AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,AddItem.DIMENSIONAL_SINGULARITY,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.DIMENSIONAL_SINGULARITY,AddItem.SPACE_PLATE,
                    AddItem.LIOPORT,AddItem.SPACE_PLATE,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.SPACE_PLATE,AddItem.LIOPORT,
                    AddItem.DIMENSIONAL_SINGULARITY,AddItem.LIOPORT,AddItem.VIRTUAL_SPACE,AddItem.VIRTUAL_SPACE,AddItem.LIOPORT,AddItem.DIMENSIONAL_SINGULARITY),0,0)
            .register();
    public static final SlimefunItem RADIATION_CLEAR=new CustomProps(SPECIAL, AddItem.RADIATION_CLEAR, RecipeType.NULL,
            NULL_RECIPE.clone(), null) {
                public void onClickAction(PlayerRightClickEvent event) {
                    Optional<Block> b= event.getClickedBlock();
                    if(b.isPresent()){
                        if(RadiationRegionManager.removeNearRadiation(b.get().getLocation(),40)){
                            sendMessage(event.getPlayer(),"&a成功清除附近40格内的辐射区");
                        }else {
                            sendMessage(event.getPlayer(),"&c附近没有辐射区!");
                        }
                    }
                }
            }
            .register();
    public static final SlimefunItem ANTIMASS_CLEAR=new CustomProps(SPECIAL, AddItem.ANTIMASS_CLEAR, RecipeType.NULL,
            NULL_RECIPE.clone(), null) {
        public void onClickAction(PlayerRightClickEvent event) {
            Player p = event.getPlayer();
            if(p.isOp()||p.getGameMode() == GameMode.CREATIVE){
                if(p.isSneaking()){
                    AddUtils.sendMessage(p,"&a输入一种材料以替换&c当前世界&a所有逻辑核心,输入取消以取消");
                    AddUtils.asyncWaitPlayerInput(p,(str)->{
                        try{
                            Material material = WorldUtils.getBlock(  Material.getMaterial(str.toUpperCase(Locale.ROOT)) );
                            Preconditions.checkNotNull(material );
                            HashSet<SlimefunBlockData> data = new HashSet<>();
                            HashSet<Location> locations = new HashSet<>();
                            Slimefun.getDatabaseManager().getBlockDataController().getAllLoadedChunkData().forEach(c->data.addAll(c.getAllBlockData()));
                            data.stream().filter(d->d.getSfId().equals(LOGIC_CORE.getId())).map(SlimefunBlockData::getLocation).filter(l->l.getWorld()==p.getWorld()).forEach((l)->{
                                locations.add(l);
                                l.getBlock().setType(material);
                            });
                            Schedules.launchSchedules(()->{
                                locations.stream().forEach(Slimefun.getDatabaseManager().getBlockDataController()::removeBlock);
                            },0,false,0);
                            AddUtils.sendMessage(p,"&a已替换材质,并删除原有的逻辑核心");
                        }catch  (Throwable e){
                            AddUtils.sendMessage(p,"&a已取消");
                        }
                    });
                }else{
                    if(ANTIMASS instanceof SpreadBlock sb){
                        sb.getSpreadOwner().clear();
                        sb.getSpreadTicker().clear();
                        WorldUtils.abortCreatingQueue();
                        HashSet<SlimefunBlockData> data = new HashSet<>();
                        Slimefun.getDatabaseManager().getBlockDataController().getAllLoadedChunkData().forEach(c->data.addAll(c.getAllBlockData()));Set<Location> location =  data.stream().filter(d->d.getSfId().equals(ANTIMASS.getId())).map(SlimefunBlockData::getLocation).collect(Collectors.toSet());
                        location.forEach(Slimefun.getDatabaseManager().getBlockDataController()::removeBlock);

                        sendMessage(event.getPlayer(),"&a反概念物质已成功清除");
                        Schedules.launchSchedules(()->{
                            location.forEach(sb::createResultAt);
                        },30,false,0);
                    }


                }
            }else{
                AddUtils.sendMessage(p,"&c你没有权限使用这个道具");
            }

        }
    }
            .register();
    public static final SlimefunItem HOLOGRAM_REMOVER=new CustomProps(SPECIAL, AddItem.HOLOGRAM_REMOVER, ENHANCED_CRAFTING_TABLE,
            recipe(null, null, null, AddItem.BUG, "DIAMOND_SWORD", AddItem.BUG, null, null, null), null) {
        protected final HashSet<Player> COOLDOWNS=new HashSet();
        @Override
        public void onClickAction(PlayerRightClickEvent event) {
            Player p=event.getPlayer();
            if(p!=null){
                if(WorldUtils.hasPermission(p,p.getLocation().getBlock(), Interaction.INTERACT_BLOCK)){
                    if(COOLDOWNS.contains(p)){
                        sendMessage(p,"&c物品冷却中!");
                    }else{
                        MultiBlockService.removeUnrecordedHolograms(p.getLocation(),20);
                        sendMessage(p,"&a成功清除!进入5s(100gt)冷却");
                        COOLDOWNS.add(p);
                        Schedules.launchSchedules(()->COOLDOWNS.remove(p),100,true,0);
                    }
                }else{
                    sendMessage(p,"&c你没有权限在这里使用该道具");
                }
            }
            event.cancel();
        }
    }
            .register();
    public static final SlimefunItem MULTIBLOCKBUILDER=new MultiBlockBuilder(SPECIAL,AddItem.MULTIBLOCKBUILDER,NULL,
            NULL_RECIPE.clone())
            .register();
    //final
    public static final SlimefunItem ANTIMASS=new SpreadBlock(BEYOND,AddItem.ANTIMASS,STARSMELTERY,
            recipe(setC(AddItem.LOGIC_CORE,9),setC(AddItem.VIRTUAL_SPACE,64),"64ENERGIZED_CAPACITOR",setC(AddItem.PDCECDMD,16),AddItem.FINAL_FRAME),LOGIC_CORE,Material.COMMAND_BLOCK,Material.SCULK)
            .register();
    public static final  SlimefunItem FINAL_LASER=new Laser(BEYOND, AddItem.FINAL_LASER,COMMON_TYPE,
            recipe(setC(AddItem.SPACE_PLATE,2),setC(AddItem.HGTLPBBI,1),setC(AddItem.TECH_CORE,4),setC(AddItem.TECH_CORE,4),setC(AddItem.HGTLPBBI,1),setC(AddItem.SPACE_PLATE,2),
                    setC(AddItem.SPACE_PLATE,2),setC(AddItem.HGTLPBBI,1),setC(AddItem.TECH_CORE,4),setC(AddItem.TECH_CORE,4),setC(AddItem.HGTLPBBI,1),setC(AddItem.SPACE_PLATE,2),
                    setC(AddItem.LSINGULARITY,2),setC(AddItem.LASER,2),setC(AddItem.VIRTUAL_SPACE,1),setC(AddItem.VIRTUAL_SPACE,1),setC(AddItem.LASER,2),setC(AddItem.LSINGULARITY,2),
                    setC(AddItem.LSINGULARITY,2),setC(AddItem.LASER,2),setC(AddItem.VIRTUAL_SPACE,1),setC(AddItem.VIRTUAL_SPACE,1),setC(AddItem.LASER,2),setC(AddItem.LSINGULARITY,2),
                    setC(AddItem.PAGOLD,4),setC(AddItem.PDCECDMD,1),setC(AddItem.LASER,2),setC(AddItem.LASER,2),setC(AddItem.PDCECDMD,1),setC(AddItem.PAGOLD,4),
                    null,setC(AddItem.PAGOLD,4),setC(AddItem.LSINGULARITY,1),setC(AddItem.LSINGULARITY,1),setC(AddItem.PAGOLD,4),null), 8_000_000,1_200_000,"final.sub")
            .register();
    public static final SlimefunItem FINAL_BASE=new MultiPart(BEYOND,AddItem.FINAL_BASE,STARSMELTERY,
            recipe(setC(AddItem.LOGIC_CORE,8),AddItem.BISILVER),"final.base")
            .register();
    public static final SlimefunItem FINAL_ALTAR=new FinalAltarCore(BEYOND,AddItem.FINAL_ALTAR,STARSMELTERY,
            recipe(setC(AddItem.HGTLPBBI,4),AddItem.FINAL_BASE,setC(AddItem.PDCECDMD,4)),"final.core")
            .register();
    public static final  SlimefunItem FINAL_SEQUENTIAL=new FinalSequenceConstructor(BEYOND, AddItem.FINAL_SEQUENTIAL,COMMON_TYPE,
            recipe(setC(AddItem.STACKFRAME,64),AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.PDCECDMD,setC(AddItem.STACKFRAME,64),
                    AddItem.PDCECDMD,setC(AddItem.SPACE_PLATE,2),AddItem.STORAGE_SINGULARITY,AddItem.STORAGE_SINGULARITY,setC(AddItem.SPACE_PLATE,2),AddItem.PDCECDMD,
                    setC(AddItem.SPACE_PLATE,2),AddItem.LIOPORT,AddItem.SEQ_CONSTRUCTOR,AddItem.SEQ_CONSTRUCTOR,AddItem.LIOPORT,setC(AddItem.SPACE_PLATE,2),
                    setC(AddItem.SPACE_PLATE,2),setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.LSINGULARITY,64),setC(AddItem.LSINGULARITY,64),setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.SPACE_PLATE,2),
                    AddItem.HGTLPBBI,setC(AddItem.SPACE_PLATE,2),setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.SPACE_PLATE,2)
                    ,AddItem.HGTLPBBI, setC(AddItem.STACKFRAME,64),AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.HGTLPBBI,setC(AddItem.STACKFRAME,64)), addGlow( new ItemStack(Material.FIRE_CHARGE)),            10240,114514,
            mkMp(
                    mkP(mkl(setC(AddItem.MASS_CORE,256),setC(AddItem.SMELERY_CORE,256),setC(AddItem.HGTLPBBI,256),
                            setC(AddItem.LSINGULARITY,128),setC(AddItem.VIRTUAL_SPACE,128),setC(AddItem.PDCECDMD,256),
                            setC(AddItem.TECH_CORE,256),setC(AddItem.METAL_CORE,256)),mkl(setC(AddItem.FINAL_FRAME,3))),2,
            mkP(mkl(setC(AddItem.LSINGULARITY,512),"1919810IRON_DUST","1919810GOLD_DUST","1919810COPPER_DUST",
                    "1919810TIN_DUST","1919810SILVER_DUST","1919810LEAD_DUST",
                    "1919810ALUMINUM_DUST","1919810ZINC_DUST","1919810MAGNESIUM_DUST"),mkl(AddItem.LOGIC_CORE)),2,
            mkP(mkl("512COPPER_INGOT","512IRON_INGOT","512GOLD_INGOT","512NETHERITE_INGOT"),mkl(AddItem.METAL_CORE)),0,
            mkP(mkl("512MAGNESIUM_INGOT","512LEAD_INGOT","512TIN_INGOT","512ZINC_INGOT"),mkl(AddItem.SMELERY_CORE)),0,
            mkP(mkl("512SILVER_INGOT","512ALUMINUM_INGOT","512DIAMOND","512REDSTONE"),mkl(AddItem.TECH_CORE)),0,
            mkP(mkl("512COAL","512LAPIS_LAZULI","512QUARTZ","512EMERALD"),mkl(AddItem.MASS_CORE)),0,
            mkP(mkl(setC(AddItem.DIMENSIONAL_SHARD,1024)),mkl(AddItem.DIMENSIONAL_SINGULARITY)),0

            ))
            .register();
    public static final SlimefunItem FINAL_STONE_MG=new FinalMGenerator(BEYOND, AddItem.FINAL_STONE_MG,COMMON_TYPE,
            recipe(setC(AddItem.STACKFRAME,64),AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.HGTLPBBI,AddItem.HGTLPBBI,setC(AddItem.STACKFRAME,64),
                    AddItem.PDCECDMD,setC(AddItem.MASS_CORE,64),setC(AddItem.SPACE_PLATE,4),setC(AddItem.SPACE_PLATE,4),setC(AddItem.MASS_CORE,64),AddItem.HGTLPBBI,
                    AddItem.PDCECDMD,setC(AddItem.SPACE_PLATE,4),setC(AddItem.NETHER_FEAT,16),setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.SPACE_PLATE,4),AddItem.HGTLPBBI,
                    AddItem.HGTLPBBI,setC(AddItem.SPACE_PLATE,4),AddItem.FINAL_FRAME,setC(AddItem.WORLD_FEAT,16),setC(AddItem.SPACE_PLATE,4),AddItem.PDCECDMD,
                    AddItem.HGTLPBBI,setC(AddItem.MASS_CORE,64),setC(AddItem.SPACE_PLATE,4),setC(AddItem.SPACE_PLATE,4),setC(AddItem.MASS_CORE,64),AddItem.PDCECDMD,
                    setC(AddItem.STACKFRAME,64),AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.PDCECDMD,AddItem.PDCECDMD,setC(AddItem.STACKFRAME,64)),1,1_440_000,57_600,
            new PairList<>(){{
                put(mkl("COBBLESTONE"),mkl("114514COBBLESTONE"));
                put(mkl("NETHERRACK"),mkl("114514NETHERRACK"));
                put(mkl("END_STONE"),mkl("114514END_STONE"));
                put(mkl("GRANITE"),mkl("114514GRANITE"));
                put(mkl("DIORITE"),mkl("114514DIORITE"));
                put(mkl("ANDESITE"),mkl("114514ANDESITE"));
                put(mkl("STONE"),mkl("114514STONE"));
            }}
            )
            .register();

    public static final SlimefunItem FINAL_MANUAL=new FinalManual(BEYOND,AddItem.FINAL_MANUAL,COMMON_TYPE,
            recipe(setC(AddItem.STACKFRAME,64),AddItem.CRAFT_MANUAL,AddItem.FURNACE_MANUAL,AddItem.ENHANCED_CRAFT_MANUAL,AddItem.GRIND_MANUAL,setC(AddItem.STACKFRAME,64),
                    AddItem.ORE_CRUSHER_MANUAL,null,setC(AddItem.LASER,64),setC(AddItem.LASER,64),null,AddItem.TABLESAW_MANUAL,
                    AddItem.PRESSURE_MANUAL,setC(AddItem.VIRTUAL_SPACE,64),setC(AddItem.MANUAL_CORE,64),setC(AddItem.LSINGULARITY,64),setC(AddItem.VIRTUAL_SPACE,64),AddItem.COMPRESSOR_MANUAL,
                    AddItem.CRUCIBLE_MANUAL,setC(AddItem.VIRTUAL_SPACE,64),AddItem.FINAL_FRAME,AddItem.FINAL_FRAME,setC(AddItem.VIRTUAL_SPACE,64),AddItem.SMELTERY_MANUAL,
                    AddItem.GOLD_PAN_MANUAL,null,setC(AddItem.LASER,64),setC(AddItem.LASER,64),null,AddItem.ANCIENT_ALTAR_MANUAL,
                    setC(AddItem.STACKFRAME,64),AddItem.MAGIC_WORKBENCH_MANUAL,AddItem.COMPOSTER,AddItem.ARMOR_FORGE_MANUAL,AddItem.ORE_WASHER_MANUAL,setC(AddItem.STACKFRAME,64)),0,0)
            .register();


    public static final  SlimefunItem FINAL_CONVERTOR=new FinalConvertor(BEYOND, AddItem.FINAL_CONVERTOR,COMMON_TYPE,
            recipe(AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.LOGIC_CORE,AddItem.LOGIC_CORE,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,AddItem.PDCECDMD,AddItem.END_FEAT,AddItem.END_FEAT,AddItem.PDCECDMD,AddItem.SPACE_PLATE,
                    AddItem.LOGIC_CORE,AddItem.PDCECDMD,AddItem.NETHER_FEAT,AddItem.NETHER_FEAT,AddItem.PDCECDMD,AddItem.LOGIC_CORE,
                    AddItem.LOGIC_CORE,AddItem.PDCECDMD,AddItem.WORLD_FEAT,AddItem.WORLD_FEAT,AddItem.PDCECDMD,AddItem.LOGIC_CORE,
                    AddItem.SPACE_PLATE,AddItem.PDCECDMD,AddItem.FINAL_FRAME,AddItem.FINAL_FRAME,AddItem.PDCECDMD,AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.LOGIC_CORE,AddItem.LOGIC_CORE,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE), 840_000_000,100_000_000,
            randItemStackFactory(
                    Utils.list(
                            probItemStackFactory(AddItem.VIRTUALWORLD,50),
                           setC(  AddItem.STAR_GOLD_INGOT,64),
                         setC(AddItem.ATOM_INGOT,64),
                            setC(AddItem.LSINGULARITY,64),
                            randItemStackFactory(
                                    Utils.list(AddItem.HGTLPBBI,AddItem.PDCECDMD,AddItem.ANTIMASS),
                                    Utils.list(4,4,1)
                            ),
                            probItemStackFactory(AddItem.VIRTUAL_SPACE,50)
                    ),
                    Utils.list(
                            1,1,1,1,2,1
                    )
            ))
            .register();
    public static final  SlimefunItem RAND_EDITOR=new RandomEditor(BEYOND, AddItem.RAND_EDITOR,COMMON_TYPE,
            recipe(AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.FINAL_FRAME,AddItem.FINAL_FRAME,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,AddItem.HGTLPBBI,AddItem.ATTR_OP,AddItem.ATTR_OP,AddItem.HGTLPBBI,AddItem.SPACE_PLATE,
                    AddItem.LOGIC_CORE,AddItem.HGTLPBBI,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.HGTLPBBI,AddItem.LOGIC_CORE,
                    AddItem.LOGIC_CORE,AddItem.HGTLPBBI,AddItem.ANTIMASS,AddItem.ANTIMASS,AddItem.HGTLPBBI,AddItem.LOGIC_CORE,
                    AddItem.SPACE_PLATE,AddItem.HGTLPBBI,AddItem.FINAL_CONVERTOR,AddItem.FINAL_CONVERTOR,AddItem.HGTLPBBI,AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.FINAL_FRAME,AddItem.FINAL_FRAME,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE), 200_000_000,25_000_000)
            .register();
    public static final  SlimefunItem FINAL_STACKMACHINE=new FinalStackMachine(BEYOND, AddItem.FINAL_STACKMACHINE,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")), Material.STONE,100,400_000_000,32)
            .register();
    public static final SlimefunItem FINAL_STACKMGENERATOR=new FinalStackMGenerator(BEYOND, AddItem.FINAL_STACKMGENERATOR,NULL,
            formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),1,400_000_000,100,
            16)
            .register();
    public static final  SlimefunItem FINAL_CRAFT=new SpecialCrafter(BEYOND, AddItem.FINAL_CRAFT,STARSMELTERY,
            recipe(setC(AddItem.PDCECDMD,3),setC(AddItem.VIRTUALWORLD,11),setC(AddItem.HGTLPBBI,3),setC(AddItem.CRAFTER,16)), Material.CRAFTING_TABLE,0,18_000, 7_200_000){
        {
            CRAFT_PROVIDER=FinalFeature.STORAGE_AND_LOCPROXY_READER;
            MACHINE_PROVIDER=FinalFeature.STORAGE_READER;
        }
        @Override
        public void registerTick(SlimefunItem item){
            this.addItemHandler(FinalFeature.FINAL_SYNC_TICKER);
        }
        @Override
        public HashMap<SlimefunItem, RecipeType> getRecipeTypeMap() {
            return CRAFTTYPE_MANUAL_RECIPETYPE;
        }
        public boolean advanced(){
            return true;
        }
        public int getCraftLimit(Block b,BlockMenu menu){
            return super.getCraftLimit(b,menu)*4;
        }
    }
            .register();


    public static final  SlimefunItem SMITH_WORKSHOP=new SmithingWorkshop(TOOLS_FUNCTIONAL, AddItem.SMITH_WORKSHOP,COMMON_TYPE,
            recipe(AddItem.BISILVER,AddItem.SPACE_PLATE,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.SPACE_PLATE,AddItem.BISILVER,
                    AddItem.BISILVER,setC(AddItem.SMELERY_CORE,2),AddItem.LSCHEDULER,AddItem.LSCHEDULER,setC(AddItem.SMELERY_CORE,2),AddItem.BISILVER,
                    setC(AddItem.PAGOLD,2),setC(AddItem.SMELERY_CORE,2),setC(AddItem.LSINGULARITY,2),setC(AddItem.LSINGULARITY,2),setC(AddItem.SMELERY_CORE,2),setC(AddItem.PAGOLD,2),
                    setC(AddItem.PAGOLD,2),setC(AddItem.SMELERY_CORE,2),setC(AddItem.LSINGULARITY,2),setC(AddItem.LSINGULARITY,2),setC(AddItem.SMELERY_CORE,2),setC(AddItem.PAGOLD,2),
                    AddItem.BISILVER,setC(AddItem.SMELERY_CORE,2),AddItem.LENGINE,AddItem.LENGINE,setC(AddItem.SMELERY_CORE,2),AddItem.BISILVER,
                    AddItem.BISILVER,setC(AddItem.ATOM_INGOT,8),"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",setC(AddItem.ATOM_INGOT,8),AddItem.BISILVER), "smith.core")
            .setDisplayRecipes(
                    Utils.list(
                            getInfoShow("&f机制",
                                    "&7该机器需要搭建锻铸工坊的结构方可运行",
                                    "&7该多方块投影中海晶灯部分需放置\"工坊接口\"",
                                    "&7其余方块均为原版方块",
                                    "&7对于铜质组成部分,任意生锈状态/打蜡状态的同类型方块均可识别",
                                    "&a每多搭建一层该机器并行处理数x2(即机器快一倍)"),null,
                            getInfoShow("&f机制",
                                    "&7任意\"工坊接口\"类型的机器需要在该多方块",
                                    "&7中方可执行其功效",
                                    "&7"
                            ),null
                    )
            )
            .register();
    public static final  SlimefunItem SMITH_INTERFACE_NONE=new SmithingInterface(TOOLS_FUNCTIONAL, AddItem.SMITH_INTERFACE_NONE,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ATOM_INGOT,AddItem.ATOM_INGOT,AddItem.ATOM_INGOT,AddItem.ATOM_INGOT,AddItem.TECH_CORE,AddItem.ATOM_INGOT,
                    AddItem.ATOM_INGOT,AddItem.ATOM_INGOT,AddItem.ATOM_INGOT),0,0,false)
            .register();
    public static final  SlimefunItem SMITH_INTERFACE_CRAFT=new SmithInterfaceProcessor(TOOLS_FUNCTIONAL, AddItem.SMITH_INTERFACE_CRAFT,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.BUG,AddItem.STAR_GOLD_INGOT,"CRAFTING_TABLE",AddItem.SMITH_INTERFACE_NONE,"CRAFTING_TABLE",
                    AddItem.STAR_GOLD_INGOT,AddItem.CARGO_PIP,AddItem.STAR_GOLD_INGOT),24_000,1200)
            .register();

    public static final  SlimefunItem ATTR_OP=new AttributeOperator(TOOLS_FUNCTIONAL, AddItem.ATTR_OP,MAGIC_WORKBENCH,
            recipe("AUTO_DISENCHANTER_2",AddItem.LENGINE,"AUTO_DISENCHANTER_2",AddItem.STAR_GOLD_INGOT,"BOOK_BINDER",AddItem.STAR_GOLD_INGOT,
                    "AUTO_ENCHANTER_2",AddItem.LENGINE,"AUTO_ENCHANTER_2"), 1200,120)
            .register();
    public static final  SlimefunItem CUSTOM_CHARGER=new SmithInterfaceCharger(TOOLS_FUNCTIONAL, AddItem.CUSTOM_CHARGER,ENHANCED_CRAFTING_TABLE,
            recipe("ENERGIZED_CAPACITOR",AddItem.TECH_CORE,"ENERGIZED_CAPACITOR","CHARGING_BENCH",AddItem.SMITH_INTERFACE_NONE,"CHARGING_BENCH",
                    AddItem.LINE_CHARGER_PLUS,AddItem.ENERGY_PIPE_PLUS,AddItem.LINE_CHARGER_PLUS), 64_000_000,4000)
            .register();
    public static final SlimefunItem FU_BASE=new MaterialItem(TOOLS_FUNCTIONAL,AddItem.FU_BASE,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.TECH_CORE,AddItem.LPLATE,
                    AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.ABSTRACT_INGOT),Utils.list(
                            getInfoShow("&f介绍","&7用于合成子分类\"功能单元\"中的物品","&7功能单元是一种&e类似附魔的组件型物品","&7安装在装备上可以给玩家带来增益")
    ))
            .register();
    public static final SlimefunItem AMPLIFY_BASE=new MaterialItem(TOOLS_SUBGROUP_1,AddItem.AMPLIFY_BASE,ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,AddItem.PAGOLD,AddItem.BUG,AddItem.PAGOLD,AddItem.HGTLPBBI,AddItem.PAGOLD,
                    AddItem.BUG,AddItem.PAGOLD,AddItem.BUG),null)
            .register()
            .setOutput(setC(AddItem.AMPLIFY_BASE,6));
    public static final SWAmplifyComponent SWAMP_SPEED=(SWAmplifyComponent)new SWAmplifyComponent(TOOLS_SUBGROUP_1,AddItem.SWAMP_SPEED,SmithInterfaceProcessor.INTERFACED_CRAFTTABLE,
            recipe(AddItem.ATOM_INGOT,AddItem.ATOM_INGOT,AddItem.ATOM_INGOT,AddItem.ATOM_INGOT,AddItem.AMPLIFY_BASE,AddItem.ATOM_INGOT,
                    AddItem.ATOM_INGOT,AddItem.ATOM_INGOT,AddItem.ATOM_INGOT))
            .register();
    public static final SWAmplifyComponent SWAMP_RANGE=(SWAmplifyComponent)new SWAmplifyComponent(TOOLS_SUBGROUP_1,AddItem.SWAMP_RANGE,SmithInterfaceProcessor.INTERFACED_CRAFTTABLE,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.AMPLIFY_BASE,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.DIMENSIONAL_SINGULARITY,AddItem.STAR_GOLD_INGOT))
            .register();
    public static final SlimefunItem DISPLAY_FU_USE=new DisplayUseTrimmerLogicLate(TOOLS_SUBGROUP_2,AddItem.DISPLAY_FU_USE,
            recipe(AddItem.DISPLAY_FU_USE_1,AddItem.DISPLAY_FU_USE_2,AddItem.DISPLAY_FU_USE_3,
                    null,null,null,null,null,null),
            ()->{
                return EquipmentFUItem.getEntries().stream().map(SlimefunItem::getItem).collect(Collectors.toSet());
            },()->{
                HashSet<Material> set = new HashSet<>();
                EquipmentFUItem.getEntries().stream().map(EquipmentFUItem::getFUnit).map(EquipmentFU::getCanEquipedMaterial).forEach(set::addAll);
                return new RecipeChoice.MaterialChoice( set.stream().toList() );
            },()->{
                HashSet<ItemStack> set = new HashSet<>();
                EquipmentFUItem.getEntries().stream().map(EquipmentFUItem::getFUnit).forEach(fu->set.addAll(fu.getEquipCostable()));
                return new RecipeChoice.ExactChoice(set.stream().toList());
            },(smith)->{
                ItemStack item = smith.getItem(0);
                if( SlimefunItem.getByItem(item) instanceof EquipmentFUItem fu){
                    ItemStack equipment =  smith.getItem(1);
                    ItemStack cost = smith.getItem(2);
                    if(equipment!=null && cost!=null && fu.getFUnit().canEquipedTo(equipment,cost)){
                        ItemStack copied = equipment.clone();
                        EquipmentFU fuu = fu.getFUnit();
                        var returnedLevel= EquipmentFUManager.getManager().addEquipmentFU(copied, fuu,1);
                        if(returnedLevel<=0){
                            return Triplet.of(new ItemStack[]{copied},fuu.getEquipCost(copied,cost),fuu.getEquipTimeCost(copied,cost));
                        }
                    }
                }
                return null;
            }
            )
            .register();
    public static final SlimefunItem DISPLAY_REMOVE_FU=new MaterialItem(TOOLS_SUBGROUP_2,AddItem.DISPLAY_REMOVE_FU,SmithInterfaceProcessor.INTERFACED_GRIND,
            recipe(null,AddItem.DISPLAY_REMOVE_FU_1,null,
                    null,AddItem.DISPLAY_REMOVE_FU_2,null,
                    null,null,null),null){
            @Override
            public void load(){
                super.load();
                SmithInterfaceProcessor.registerGrindstoneLogic((grind)->{
                    ItemStack item1 = grind.getItem(0);
                    ItemStack item2 = grind.getItem(1);
                    if(item1!=null&&item1.getAmount()==1&& item2!=null && SlimefunItem.getByItem(item2) instanceof EquipmentFUItem fu){
                        int level = EquipmentFUManager.getManager().getEquipmentFULevel(item1,fu.getFUnit());
                        int amount = item2.getAmount();
                        if(level>=amount){
                            return ()->{
                                ItemStack nowitem = grind.getItem(0);
                                Preconditions.checkNotNull(nowitem);
                                int removedLeft = EquipmentFUManager.getManager().removeEquipmentFU(nowitem,fu.getFUnit(),amount);
                                int actuallyRemoved = amount-removedLeft;
                                return new MultiCraftingOperation(new ItemGreedyConsumer[]{CraftUtils.getGreedyConsumerAsAmount(fu.getItem(),actuallyRemoved)},2*actuallyRemoved);
                            };
                        }
                    }
                    return null;
                });
            }
        }
            .register();
    public static final SlimefunItem DEMO_FU=new EquipmentFUItem(TOOLS_SUBGROUP_2,AddItem.DEMO_FU,NULL,
            formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")), EFUImplements.DEMO)
            .register();




    public static final SlimefunItem TMP1=new MaterialItem(FUNCTIONAL,AddItem.TMP1,NULL,
            NULL_RECIPE.clone())
            .register();
    public static final SlimefunItem RESOLVE_FAILED=new MaterialItem(FUNCTIONAL, AddItem.RESOLVE_FAILED,NULL,
            NULL_RECIPE.clone(),null)
            .register();
    public static final SlimefunItem SHELL=new CustomProps(FUNCTIONAL, AddItem.SHELL, RecipeType.NULL,
            NULL_RECIPE.clone(), null) {
        @Override
        public void onClickAction(PlayerRightClickEvent event) {
            CommandShell.setup(event.getPlayer(),"0");
        }
    }
            .register();



//    public static final SlimefunItem CUSTOM1=register(new FIrstCustomItem(MATERIAL, AddItem.CUSTOM1,COMMON_TYPE,
//            recipe("COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT")));

//    public static  final  SlimefunItem SMG1=new SMGenerator(MATERIAL, AddItem.SMG1,NULL,NULL_RECIPE.clone(),3,114514,0,
//               randItemStackFactory( new PairList<>(){{
//                    put(Material.DIAMOND,1);
//                    put(new ItemStack(Material.BOOK,2),1);
//                    put(CUSTOM1,1);
//                    put("COPPER_DUST",1);
//                    put("EMERALD_ORE",1);
//                    put(
//                            new EqProRandomStack(new PairList<>(){{
//                                put(new ItemStack(Material.LADDER),1);
//                                put(new ItemStack(Material.BEDROCK),1);
//
//                            }}),1
//                     );
//                }}),
//            Material.DIRT
//
//            ).register();
//    public static final   SlimefunItem MMG1=register(new MMGenerator(MATERIAL, AddItem.MMG1,NULL,NULL_RECIPE.clone(),3,114514,0,
//                new PairList<>(){{
//                    put(new Object[]{"DIAMOND_BLOCK"},new Object[]{"114DIAMOND"}  );
//                    put(new Object[]{"BEDROCK"},new Object[]{randItemStackFactory(
//                            new PairList<>(){{
//                                put("2COPPER_DUST",1);
//                                put("4SILVER_DUST",1);
//                            }}
//                    ),"1919COMMAND_BLOCK"});
//                }}
//            ));

//
//    public static final  SlimefunItem TESTER2=register(new SMGenerator(MATERIAL,new SlimefunItemStack("TESTER2",new ItemStack(Material.DIAMOND_ORE),"测试机","测试寄"),
//            RecipeType.NULL,NULL_RECIPE.clone(),12,0,0, (Object)(new ItemStack(Material.DIAMOND,1145))
//           ,null,new ItemStack(Material.DIAMOND_CHESTPLATE)
//            ));
//    public static final  SlimefunItem MACHINE3=register(new EMachine(MATERIAL, AddItem.MACHINE3,NULL,NULL_RECIPE.clone(),
//            Material.BOOK,1919,810,
//            new PairList<>(){{
//                put(new Object[]{new ItemStack(Material.BOOK),new ItemStack(Material.DIAMOND)
//                        ,AddSlimefunItems.CUSTOM1             ,null},3);
//                put(new Object[]{new ItemStack(Material.COMMAND_BLOCK),null
//                        ,AddSlimefunItems.CUSTOM1             ,AddSlimefunItems.MATL114},2);
//                put(new Object[]{new ItemStack(Material.EMERALD),null
//                        ,AddSlimefunItems.MATL114            ,null},1);
//                put(new Object[]{new ItemStack(Material.DIAMOND,64),new ItemStack(Material.DIAMOND,16),
//                        new ItemStack(Material.BEACON,1),null},3);
//                put(new Object[]{new ItemStack(Material.GOLD_INGOT,1),null,
//                        new ItemStack(Material.BEACON,1),null},0);
//                put(new Object[]{AddSlimefunItems.MATL114,null,
//                        AddSlimefunItems.CUSTOM1,null},0);
//            }}));
//    public static final  SlimefunItem MACHINE4=register(new AEMachine(MATERIAL, AddItem.MACHINE4,NULL,NULL_RECIPE.clone(),
//            Material.BOOK,1919,810,
//            new PairList<>(){{
//                put(new Object[]{new ItemStack(Material.BOOK),new ItemStack(Material.DIAMOND)
//                        ,AddSlimefunItems.CUSTOM1             ,null},3);
//                put(new Object[]{new ItemStack(Material.COMMAND_BLOCK),null
//                        ,AddSlimefunItems.CUSTOM1             ,AddSlimefunItems.MATL114},2);
//                put(new Object[]{new ItemStack(Material.EMERALD),null
//                        ,AddSlimefunItems.MATL114            ,null},1);
//                put(new Object[]{new ItemStack(Material.DIAMOND,64),new ItemStack(Material.DIAMOND,16),
//                        new ItemStack(Material.BEACON,1),null},3);
//                put(new Object[]{new ItemStack(Material.GOLD_INGOT,1),null,
//                        new ItemStack(Material.BEACON,1),null},0);
//                put(new Object[]{AddSlimefunItems.MATL114,null,
//                        AddSlimefunItems.CUSTOM1,null},0);
//            }}));
//    public static final  SlimefunItem MANUAL1=register(new ManualCrafter(MATERIAL, AddItem.MANUAL1,NULL,NULL_RECIPE.clone(),
//           1919,810,ENHANCED_CRAFTING_TABLE));
//    public static final  SlimefunItem MANUAL_MULTI=register(new ManualCrafter(MATERIAL, AddItem.MANUAL_MULTI,NULL,NULL_RECIPE.clone(),
//            1919,810,MULTIBLOCK));
//    public static final  SlimefunItem MANUAL_KILL=register(new ManualCrafter(MATERIAL, AddItem.MANUAL_KILL,NULL,NULL_RECIPE.clone(),
//            1919,810,MOB_DROP));
//    public static final  SlimefunItem AUTOSMELTING1=register(new AdvanceCrafter(MATERIAL, AddItem.AUTOSMELTING1,NULL,NULL_RECIPE.clone(),
//            Material.FLINT_AND_STEEL,1919,810,SMELTERY));


//    public static final SlimefunItem INPORT=register(new InputPort(MATERIAL, AddItem.INPORT,NULL,NULL_RECIPE,0,0));
//
//    public static final SlimefunItem OUTPORT=register(new OutputPort(MATERIAL, AddItem.OUTPORT,NULL,NULL_RECIPE,0,0));

    public static final SlimefunItem TESTUNIT1=register(new TestStorageUnit(FUNCTIONAL, AddItem.TESTUNIT1,NULL,NULL_RECIPE,0,0));
//
//    public static final SlimefunItem TESTUNIT2=register(new TestStorageUnit2(MATERIAL, AddItem.TESTUNIT2,NULL,NULL_RECIPE.clone()));
//
//    public static final SlimefunItem TESTUNIT3=register(new me.matl114.logitech.SlimefunItem.TestStorageUnit3(MATERIAL, AddItem.TESTUNIT3,NULL,NULL_RECIPE.clone()));


//
//    public static final SlimefunItem ANTIGRAVITY=register(new AntiGravityBar(MATERIAL, AddItem.ANTIGRAVITY,NULL,NULL_RECIPE.clone()));

//    public static final SlimefunItem WORKBENCH1=register(new TestWorkBench(MATERIAL, AddItem.WORKBENCH1,NULL,NULL_RECIPE.clone(),
//            0,0,
//                new PairList<>(){{
//                    put(mkP(
//                            mkl(null,"2COPPER_DUST",null,AddSlimefunItems.MATL114,"4DIAMOND",AddSlimefunItems.CUSTOM1,null,"3IRON_DUST",null),
//                            mkl("5COMMAND_BLOCK")
//                    ),0);
//                }}
//            ));

//    public static final SlimefunItem TEST_MCORE=new MultiCoreTest(MATERIAL,AddItem.TESTCORE,NULL,NULL_RECIPE.clone(),
//            "test.part", MultiBlockTypes.TEST_TYPE).register();
//    public static final SlimefunItem TEST_SEQ=new SequenceConstructor(MATERIAL,AddItem.TEST_SEQ,NULL,
//            formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),new ItemStack(Material.FIRE_CHARGE),1919,8100,
//            mkMp(
//                    mkP(mkl("128DIAMOND","128GOLD_INGOT","128IRON_INGOT","128COAL"),mkl(AddItem.METAL_CORE)),3
//            ))
//            .register();

}
