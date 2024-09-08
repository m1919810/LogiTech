package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Language;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Schedule.PersistentEffects.RadiationRegion;
import me.matl114.logitech.SlimefunItem.Blocks.*;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlock.*;
import me.matl114.logitech.SlimefunItem.Cargo.CargoMachine.*;
import me.matl114.logitech.SlimefunItem.Cargo.StorageMachines.IOPort;
import me.matl114.logitech.SlimefunItem.Cargo.Transportation.*;
import me.matl114.logitech.SlimefunItem.Cargo.WorkBench.ChipCopier;
import me.matl114.logitech.SlimefunItem.Cargo.Config.ChipCard;
import me.matl114.logitech.SlimefunItem.Cargo.Config.ChipCardCode;
import me.matl114.logitech.SlimefunItem.Cargo.Config.ConfigCard;
import me.matl114.logitech.SlimefunItem.Cargo.WorkBench.CargoConfigurator;
import me.matl114.logitech.SlimefunItem.Cargo.WorkBench.ChipBiConsumer;
import me.matl114.logitech.SlimefunItem.Cargo.WorkBench.ChipConsumer;
import me.matl114.logitech.SlimefunItem.Items.*;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.*;
import me.matl114.logitech.SlimefunItem.Machines.Electrics.*;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.FinalManual;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualCrafter;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualMachine;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.MultiBlockManual;
import me.matl114.logitech.SlimefunItem.Machines.SpecialMachines.*;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.SlimefunItem.Cargo.StorageMachines.InputPort;
import me.matl114.logitech.SlimefunItem.Cargo.StorageMachines.OutputPort;
import me.matl114.logitech.SlimefunItem.Items.Singularity;
import me.matl114.logitech.SlimefunItem.Cargo.TestStorageUnit;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.CommandClass.CommandShell;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * register main
 */
public class AddSlimefunItems {
    public static void registerSlimefunItems() {
        Debug.logger("注册附属物品...");
        Debug.logger("注册附属机器...");
    }
    public static final SlimefunAddon INSTANCE = MyAddon.getInstance();
    public static SlimefunItem register(SlimefunItem item){
        item.register(INSTANCE);
        return item;
    }
    protected static Object mkP(Object v1,Object v2){
        return new Pair(v1,v2);
    }
    protected static Object[] mkl(Object ... v){
        return Arrays.stream(v).toArray();
    }
    protected static ItemStack[] recipe(Object ... v){
        return Arrays.stream(v).map(AddUtils::resolveItem).toArray(ItemStack[]::new);
    }
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
        return AddUtils.NULL_RECIPE.clone();
    }
//    private static HashMap<Object,Integer> mrecipe(Object ... v){
//        int len=v.length;
//        return new LinkedHashMap<>((len+1)/2){{
//            for(int i=0;i<len;i+=2){
//                Object v1=v[i];
//                Integer v2=(Integer)v[i+1];
//                put(v1,v2);
//            }
//        }};
//    }
    private static LinkedHashMap<Object,Integer> mkMp(Object ... v){
        int len=v.length;
        return new LinkedHashMap<>((len+1)/2){{
            for(int i=0;i<len;i+=2){
                Object v1=v[i];
                Integer v2=(Integer)v[i+1];
                put(v1,v2);
            }
        }};
    }
    //items
    public static final RecipeType STARSMELTERY=new RecipeType(
            AddUtils.getNameKey("star_smeltery"),
            new CustomItemStack(AddItem.STAR_SMELTERY, AddItem.STAR_SMELTERY.getDisplayName(),
                    "", "&c在%s中锻造!".formatted(Language.get("Machines.STAR_SMELTERY.Name")))
    );

    public static final SlimefunItem MATL114=new MaterialItem(AddGroups.MATERIAL, AddItem.MATL114,
            AddDepends.INFINITYWORKBENCH_TYPE!=null?AddDepends.INFINITYWORKBENCH_TYPE:BugCrafter.TYPE,
            recipe(AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,
                    AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,
                    AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,
                    AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG)
            ).register();
    public static final SlimefunItem BUG= new MaterialItem(AddGroups.MATERIAL,AddItem.BUG,RecipeType.NULL,
            nullRecipe(),list(AddUtils.getInfoShow("&f获取方式","&7会出现在一些隐蔽地方...",
            "&7当你出现疑问,为什么这个物品找不到时",
            "你可能需要多看看\"的版本与说明\"分类(物理意义)")))
            .register();

    public static final SlimefunItem TRUE=new MaterialItem(AddGroups.MATERIAL,AddItem.TRUE_,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.BOOL_GENERATOR,Language.get("Machines.BOOL_GENERATOR.Name")))
            .register();
    public static final SlimefunItem FALSE=new MaterialItem(AddGroups.MATERIAL,AddItem.FALSE_,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.BOOL_GENERATOR,Language.get("Machines.BOOL_GENERATOR.Name")))
            .register();
    public static final SlimefunItem LOGIGATE=new MaterialItem(AddGroups.MATERIAL,AddItem.LOGIGATE,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.TRUE_,"REDSTONE_TORCH","SILVER_INGOT",
                        "COMPARATOR","OBSERVER","REDSTONE_LAMP",
                    AddItem.FALSE_,"REDSTONE_TORCH","SILVER_INGOT"))
            .register();
    public static final SlimefunItem LOGIC=new MaterialItem(AddGroups.MATERIAL,AddItem.LOGIC,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")))
            .register();
    public static final SlimefunItem NOLOGIC=new MaterialItem(AddGroups.MATERIAL,AddItem.NOLOGIC,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")))
            .register();



        //generated
    public static final SlimefunItem EXISTE=new MaterialItem(AddGroups.MATERIAL,AddItem.EXISTE,RecipeType.NULL,
                AddUtils.formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem UNIQUE=new MaterialItem(AddGroups.MATERIAL,AddItem.UNIQUE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem PARADOX=new MaterialItem(AddGroups.MATERIAL,AddItem.PARADOX,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.LVOID_GENERATOR,Language.get("Machines.LVOID_GENERATOR.Name")),null)
            .register();

    public static final SlimefunItem LENGINE=new MaterialItem(AddGroups.MATERIAL,AddItem.LENGINE,BugCrafter.TYPE,
            recipe(AddItem.LOGIC,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LOGIC,
                    AddItem.ABSTRACT_INGOT,"ENERGY_CONNECTOR",AddItem.LOGIGATE,AddItem.LOGIGATE,"ENERGY_CONNECTOR",AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,"ELECTRIC_MOTOR",AddItem.LOGIC,AddItem.LOGIC,"ELECTRIC_MOTOR",AddItem.ABSTRACT_INGOT,
                    "REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT"),null)
            .register();


    public static final SlimefunItem LFIELD=new MaterialItem(AddGroups.MATERIAL,AddItem.LFIELD,BugCrafter.TYPE,
            recipe("SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT",
                    null,AddItem.EXISTE,null,AddItem.EXISTE,null,AddItem.EXISTE,
                    AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,
                    AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,
                    null,AddItem.UNIQUE,null,AddItem.UNIQUE,null,AddItem.UNIQUE,
                    "SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT"),null)
            .setOutput(setC(AddItem.LFIELD,17))
            .register();



    public static final SlimefunItem LSCHEDULER=new MaterialItem(AddGroups.MATERIAL,AddItem.LSCHEDULER,BugCrafter.TYPE,
            recipe(AddItem.NOLOGIC,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.NOLOGIC,
                    AddItem.LFIELD,"ANDROID_INTERFACE_ITEMS","2ANDROID_MEMORY_CORE","2ANDROID_MEMORY_CORE","ANDROID_INTERFACE_ITEMS", AddItem.LFIELD,
                    AddItem.ABSTRACT_INGOT,"2ANDROID_MEMORY_CORE","3PROGRAMMABLE_ANDROID_2",setC(AddItem.LOGIGATE,2),"2ANDROID_MEMORY_CORE",AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,"2ANDROID_MEMORY_CORE",setC(AddItem.LENGINE,2),"4PROGRAMMABLE_ANDROID","2ANDROID_MEMORY_CORE",AddItem.ABSTRACT_INGOT,
                    AddItem.LFIELD,"ANDROID_INTERFACE_ITEMS","2ANDROID_MEMORY_CORE","2ANDROID_MEMORY_CORE","ANDROID_INTERFACE_ITEMS",AddItem.LFIELD,
                    AddItem.NOLOGIC,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.NOLOGIC),null)
            .register();
    public static final SlimefunItem LCRAFT=new MaterialItem(AddGroups.MATERIAL,AddItem.LCRAFT,BugCrafter.TYPE,
            recipe(AddItem.EXISTE,AddItem.LFIELD,"CARGO_MOTOR","CARGO_MOTOR",AddItem.LFIELD, AddItem.EXISTE,
                    AddItem.LFIELD,"CRAFTER_SMART_PORT","2CRAFTING_MOTOR","2CRAFTING_MOTOR", "CRAFTER_SMART_PORT",AddItem.LFIELD,
                    "CARGO_MOTOR","CRAFTING_MOTOR","6VANILLA_AUTO_CRAFTER", AddItem.LOGIGATE,"CRAFTING_MOTOR","CARGO_MOTOR",
                    "CARGO_MOTOR","CRAFTING_MOTOR", setC(AddItem.LENGINE,1),"5ENHANCED_AUTO_CRAFTER","CRAFTING_MOTOR","CARGO_MOTOR",
                    AddItem.LFIELD, "CRAFTER_SMART_PORT","2CRAFTING_MOTOR","2CRAFTING_MOTOR","CRAFTER_SMART_PORT",AddItem.LFIELD,
                    AddItem.EXISTE,AddItem.LFIELD,"CARGO_MOTOR","CARGO_MOTOR",AddItem.LFIELD, AddItem.EXISTE),null)
            .register();
    public static final SlimefunItem LBOOLIZER=new MaterialItem(AddGroups.MATERIAL,AddItem.LBOOLIZER,BugCrafter.TYPE,
            recipe(AddItem.TRUE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_,
                    AddItem.FALSE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.FALSE_,AddItem.TRUE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.FALSE_,AddItem.TRUE_, AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.FALSE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.TRUE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_),null)
            .register();
    public static final SlimefunItem LMOTOR=new MaterialItem(AddGroups.MATERIAL,AddItem.LMOTOR,BugCrafter.TYPE,
            recipe(AddItem.NOLOGIC,AddItem.LFIELD,"HEATING_COIL","HEATING_COIL",AddItem.LFIELD,AddItem.NOLOGIC,
                    AddItem.LFIELD,"ELECTRIC_MOTOR",setC(AddItem.LBOOLIZER,2),setC(AddItem.LBOOLIZER,2),"ELECTRIC_MOTOR",AddItem.LFIELD,
                    "HEATING_COIL",setC(AddItem.LBOOLIZER,1),"3BATTERY",AddItem.LOGIGATE,setC(AddItem.LBOOLIZER,1),"HEATING_COIL",
                    "HEATING_COIL",setC(AddItem.LBOOLIZER,1),AddItem.LENGINE,"4BATTERY",setC(AddItem.LBOOLIZER,1),"HEATING_COIL",
                    AddItem.LFIELD,"ELECTRIC_MOTOR",setC(AddItem.LBOOLIZER,2),setC(AddItem.LBOOLIZER,2),"ELECTRIC_MOTOR",AddItem.LFIELD,
                    AddItem.NOLOGIC,AddItem.LFIELD,"HEATING_COIL","HEATING_COIL",AddItem.LFIELD,AddItem.NOLOGIC),null)
            .register();
    public static final SlimefunItem LDIGITIZER=new MaterialItem(AddGroups.MATERIAL,AddItem.LDIGITIZER,BugCrafter.TYPE,
            recipe(AddItem.UNIQUE,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.UNIQUE
                    ,AddItem.LFIELD,setC(AddItem.LBOOLIZER,2),setC(AddItem.LBOOLIZER,2),setC(AddItem.LBOOLIZER,2),setC(AddItem.LBOOLIZER,2)
                    ,AddItem.LFIELD,AddItem.LOGIGATE,setC(AddItem.LBOOLIZER,2),"9REPEATER",AddItem.PARADOX
                    ,setC(AddItem.LBOOLIZER,2),AddItem.LOGIGATE,AddItem.LOGIGATE,setC(AddItem.LBOOLIZER,2),AddItem.LENGINE
                    ,"7COMPARATOR",setC(AddItem.LBOOLIZER,2),AddItem.LOGIGATE,AddItem.LFIELD,setC(AddItem.LBOOLIZER,2)
                    ,setC(AddItem.LBOOLIZER,2),setC(AddItem.LBOOLIZER,2),setC(AddItem.LBOOLIZER,2),AddItem.LFIELD,AddItem.UNIQUE
                    ,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.UNIQUE),null)
            .register();
    public static final SlimefunItem LIOPORT=new MaterialItem(AddGroups.MATERIAL,AddItem.LIOPORT,BugCrafter.TYPE,
            recipe("CHEST",AddItem.LFIELD,"HOPPER","HOPPER",AddItem.LFIELD,
                    "CHEST",AddItem.LFIELD,"GPS_TRANSMITTER_2","2CARGO_NODE","2CARGO_NODE",
                    "GPS_TRANSMITTER_2",AddItem.LFIELD,"HOPPER","2CARGO_NODE_INPUT","3CARGO_NODE_OUTPUT_ADVANCED",
                    setC(AddItem.LOGIGATE,3),"2CARGO_NODE_INPUT","HOPPER","HOPPER","2CARGO_NODE_INPUT",
                    AddItem.LENGINE,"CARGO_MANAGER","2CARGO_NODE_INPUT","HOPPER",AddItem.LFIELD,
                    "GPS_TRANSMITTER_2","2CARGO_NODE","2CARGO_NODE","GPS_TRANSMITTER_2",AddItem.LFIELD,
                    "CHEST",AddItem.LFIELD,"HOPPER","HOPPER",AddItem.LFIELD,
                    "CHEST"),null)
            .register();
    public static final SlimefunItem LPLATE=new MaterialItem(AddGroups.MATERIAL,AddItem.LPLATE,RecipeType.SMELTERY,
            recipe(setC(AddItem.ABSTRACT_INGOT,2),"BATTERY","POTATO",AddItem.CHIP_INGOT),null)
            .register();
    public static final SlimefunItem PALLADIUM_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.PALLADIUM_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem PLATINUM_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.PLATINUM_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem MOLYBDENUM=new MaterialItem(AddGroups.MATERIAL,AddItem.MOLYBDENUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem CERIUM=new MaterialItem(AddGroups.MATERIAL,AddItem.CERIUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem CADMIUM_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.CADMIUM_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem MENDELEVIUM=new MaterialItem(AddGroups.MATERIAL,AddItem.MENDELEVIUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem DYSPROSIUM=new MaterialItem(AddGroups.MATERIAL,AddItem.DYSPROSIUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem BISMUTH_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.BISMUTH_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem ANTIMONY_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.ANTIMONY_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem THALLIUM=new MaterialItem(AddGroups.MATERIAL,AddItem.THALLIUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem HYDRAGYRUM=new MaterialItem(AddGroups.MATERIAL,AddItem.HYDRAGYRUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();
    public static final SlimefunItem BORON=new MaterialItem(AddGroups.MATERIAL,AddItem.BORON,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TRANSMUTATOR,Language.get("MultiBlock.TRANSMUTATOR.Name")),null)
            .register();


    //alloy
    public static final SlimefunItem CHIP_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.CHIP_INGOT,RecipeType.SMELTERY,
            recipe("8SILVER_INGOT","6REINFORCED_ALLOY_INGOT","10IRON_INGOT",
                    new CustomItemStack(SlimefunItems.COPPER_INGOT,12),"6SILICON","16ALUMINUM_INGOT"))
            .register();
    public static final SlimefunItem ABSTRACT_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.ABSTRACT_INGOT,RecipeType.SMELTERY,
            recipe(AddItem.TRUE_,AddItem.EXISTE,AddItem.LOGIC,AddItem.NOLOGIC,AddItem.UNIQUE,AddItem.FALSE_))
            .register();
    public static final SlimefunItem BISILVER=new MaterialItem(AddGroups.MATERIAL,AddItem.BISILVER,STARSMELTERY,
            recipe(setC(AddItem.BISMUTH_INGOT,12),setC(AddItem.PARADOX,24),"4SILVER_INGOT",
                    "4BILLON_INGOT"),null)
            .register();
    public static final SlimefunItem PAGOLD=new MaterialItem(AddGroups.MATERIAL,AddItem.PAGOLD,STARSMELTERY,
            recipe(setC(AddItem.PALLADIUM_INGOT,8),setC(AddItem.PARADOX,24),
                    setC(AddItem.PLATINUM_INGOT,4),"7GOLD_22K"
                    ),null)
            .register();

    public static final SlimefunItem PDCECDMD=new MaterialItem(AddGroups.MATERIAL,AddItem.PDCECDMD,AddSlimefunItems.STARSMELTERY,
            recipe("64PLUTONIUM",setC(AddItem.CERIUM,18),setC(AddItem.CADMIUM_INGOT,16),
                    setC(AddItem.MENDELEVIUM,12)
                    ),null)
            .register();
    public static final SlimefunItem HGTLPBBI=new MaterialItem(AddGroups.MATERIAL,AddItem.HGTLPBBI,AddSlimefunItems.STARSMELTERY,
            recipe(setC(AddItem.HYDRAGYRUM,24),setC(AddItem.THALLIUM,32),
                    "54LEAD_INGOT",setC(AddItem.BISILVER,8)),null)
            .register();
    public static final SlimefunItem REINFORCED_CHIP_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.REINFORCED_CHIP_INGOT,AddSlimefunItems.STARSMELTERY,
            recipe(setC(AddItem.STAR_GOLD_INGOT,10),setC(AddItem.CHIP_INGOT,16),
                    setC(AddItem.ATOM_INGOT,31),setC(AddItem.PAGOLD,4),
                    setC(AddItem.CADMIUM_INGOT,4),setC(AddItem.BISILVER,3)
                    ),null)
            .register();
    public static final SlimefunItem ATOM_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.ATOM_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();

    public static final SlimefunItem DIMENSIONAL_SHARD=new AbstractGeoResource(AddGroups.MATERIAL, AddItem.DIMENSIONAL_SHARD,
            recipe(null,AddItem.END_MINER,null,null,AddUtils.getInfoShow("&f获取方式","&7在末地大部分群系","或风袭沙丘或蘑菇岛开采","&7或者在本附属的矿机中获取")),
            1, new HashMap<>(){{
                put(Biome.END_BARRENS,1);
                put(Biome.END_HIGHLANDS,1);
                put(Biome.THE_END,1);
                put(Biome.SMALL_END_ISLANDS,1);
                put(Biome.WINDSWEPT_GRAVELLY_HILLS,1);
                put(Biome.MUSHROOM_FIELDS,1);
            }}) .registerGeo();

    public static final SlimefunItem STAR_GOLD=new AbstractGeoResource(AddGroups.MATERIAL,AddItem.STAR_GOLD,
            recipe(null,AddItem.END_MINER,null,null,AddUtils.getInfoShow("&f获取方式","&7在末地部分群系开采","&7或者在本附属的矿机中获取")),0,
            new HashMap<>(){{
                put(Biome.END_BARRENS,1);
            }}).registerGeo();
    public static final SlimefunItem STAR_GOLD_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.STAR_GOLD_INGOT,RecipeType.SMELTERY,
            recipe(setC(AddItem.STAR_GOLD,7),setC(AddItem.DIMENSIONAL_SHARD,9)),null)
            .register();

    public static final SlimefunItem WORLD_FEAT=new MaterialItem(AddGroups.MATERIAL,AddItem.WORLD_FEAT,BugCrafter.TYPE,
            recipe(null,"GRASS_BLOCK","CLAY","SAND","STONE",null,
                    "GRASS_BLOCK","PODZOL","GRASS_BLOCK","STONE","PODZOL","STONE",
                    "OAK_LOG","GRASS_BLOCK","STONE","GRASS_BLOCK","STONE","ICE",
                    "OAK_LEAVES","STONE","GRASS_BLOCK","STONE","GRASS_BLOCK","ANDESITE",
                    "STONE","PODZOL","STONE","GRASS_BLOCK","PODZOL","GRASS_BLOCK",
                    null,"STONE","GRANITE","DIORITE","GRASS_BLOCK",null),null)
            .register();
    public static final SlimefunItem NETHER_FEAT=new MaterialItem(AddGroups.MATERIAL,AddItem.NETHER_FEAT,BugCrafter.TYPE,
            recipe(null,"NETHERITE_INGOT","NETHER_WART","BASALT","OBSIDIAN",null,
                    "NETHERITE_INGOT","MAGMA_BLOCK","NETHERITE_INGOT","OBSIDIAN","MAGMA_BLOCK","OBSIDIAN",
                    "SHROOMLIGHT","NETHERITE_INGOT","OBSIDIAN","NETHERITE_INGOT","OBSIDIAN","NETHERRACK",
                    "STRANGE_NETHER_GOO","OBSIDIAN","NETHERITE_INGOT","OBSIDIAN","NETHERITE_INGOT","CRIMSON_NYLIUM",
                    "OBSIDIAN","MAGMA_BLOCK","OBSIDIAN","NETHERITE_INGOT","MAGMA_BLOCK","NETHERITE_INGOT",
                    null,"OBSIDIAN","BLACKSTONE","WARPED_NYLIUM","NETHERITE_INGOT",null),null)
            .register();
    public static final SlimefunItem END_FEAT=new MaterialItem(AddGroups.MATERIAL,AddItem.END_FEAT,BugCrafter.TYPE,
            recipe(null,"CHORUS_FLOWER","4CHORUS_FRUIT","4CHORUS_FRUIT","END_CRYSTAL",null,
                    "CHORUS_FLOWER","ENDER_EYE","CHORUS_FLOWER","END_CRYSTAL","ENDER_EYE","END_CRYSTAL",
                    "4END_ROD","CHORUS_FLOWER","END_CRYSTAL","CHORUS_FLOWER","END_CRYSTAL","4END_ROD",
                    "4END_ROD","END_CRYSTAL","CHORUS_FLOWER","END_CRYSTAL","CHORUS_FLOWER","4END_ROD",
                    "END_CRYSTAL","ENDER_EYE","END_CRYSTAL","CHORUS_FLOWER","ENDER_EYE","CHORUS_FLOWER",
                    null,"END_CRYSTAL","4CHORUS_FRUIT","4CHORUS_FRUIT","CHORUS_FLOWER",null),null)
            .register();
    public static final SlimefunItem HYPER_LINK=new HypLink(AddGroups.SPACE,AddItem.HYPER_LINK,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT,
                    AddItem.PARADOX,AddItem.DIMENSIONAL_SHARD,AddItem.PARADOX,
                    AddItem.ABSTRACT_INGOT,AddItem.BUG,AddItem.ABSTRACT_INGOT))
            .register();


    public static final SlimefunItem METAL_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.METAL_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),null)
            .register();
    public static final SlimefunItem SMELERY_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.SMELERY_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),null)
            .register();
    public static final SlimefunItem MASS_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.MASS_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),null)
            .register();
    public static final SlimefunItem TECH_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.TECH_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),null)
            .register();
    public static final SlimefunItem SPACE_PLATE=new MaterialItem(AddGroups.MATERIAL,AddItem.SPACE_PLATE,STARSMELTERY,
            recipe(setC(AddItem.STAR_GOLD_INGOT,24),setC(AddItem.PARADOX,64),
                    setC(AddItem.ATOM_INGOT,64),setC(AddItem.LFIELD,36),setC(AddItem.REINFORCED_CHIP_INGOT,3)
                    ),null)
            .register();
    public static final SlimefunItem VIRTUAL_SPACE=new MaterialItem(AddGroups.MATERIAL,AddItem.VIRTUAL_SPACE,BugCrafter.TYPE,
            recipe(AddItem.SPACE_PLATE,AddItem.BISILVER,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.BISILVER,AddItem.SPACE_PLATE,
                    AddItem.BISILVER,AddItem.LDIGITIZER,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.LDIGITIZER,AddItem.BISILVER,
                    "GPS_TRANSMITTER_4",AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,"GPS_TRANSMITTER_4",
                    AddItem.LIOPORT,AddItem.PAGOLD,AddItem.CHIP_CORE,AddItem.CHIP_CORE,AddItem.PAGOLD,AddItem.LIOPORT,
                    AddItem.BISILVER,AddItem.LDIGITIZER,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.LDIGITIZER,AddItem.BISILVER,
                    AddItem.SPACE_PLATE,AddItem.BISILVER,setC(AddItem.ATOM_INGOT,8),setC(AddItem.ATOM_INGOT,8),AddItem.BISILVER,AddItem.SPACE_PLATE),null)
            .setOutput(setC(AddItem.VIRTUAL_SPACE,8))
            .register();

    public static final SlimefunItem REDSTONE_ENGINE=new MaterialItem(AddGroups.VANILLA,AddItem.REDSTONE_ENGINE,BugCrafter.TYPE,
            recipe("TNT","SLIME_BLOCK","ANVIL","ANVIL","SLIME_BLOCK","TNT",
                    "OBSERVER","STICKY_PISTON","STICKY_PISTON","STICKY_PISTON","STICKY_PISTON","OBSERVER",
                    "REDSTONE_TORCH",AddItem.LOGIGATE,"2REPEATER","2REPEATER",AddItem.LOGIGATE,"REDSTONE_TORCH",
                    "REDSTONE_TORCH",AddItem.LOGIGATE,"2REPEATER","2REPEATER",AddItem.LOGIGATE,"REDSTONE_TORCH",
                    "OBSERVER","STICKY_PISTON","STICKY_PISTON","STICKY_PISTON","STICKY_PISTON","OBSERVER",
                    "TNT","SLIME_BLOCK","ANVIL","ANVIL","SLIME_BLOCK","TNT"),null)
            .register();

    public static final SlimefunItem SAMPLE_HEAD=new AbstractBlock(AddGroups.SPECIAL,AddItem.SAMPLE_HEAD,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.HEAD_ANALYZER,Language.get("Machines.HEAD_ANALYZER.Name")))
            .register();
    public static final SlimefunItem CHIP=new ChipCard(AddGroups.ADVANCED,AddItem.CHIP,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.CHIP_MAKER,Language.get("Machines.CHIP_MAKER.Name")))
            .register();
    public static final SlimefunItem CHIP_CORE=new MaterialItem(AddGroups.ADVANCED,AddItem.CHIP_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.CHIP_MAKER,Language.get("Machines.CHIP_MAKER.Name")),null)
            .register();
    public static final SlimefunItem LSINGULARITY=new MaterialItem(AddGroups.MATERIAL,AddItem.LSINGULARITY,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem LOGIC_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.LOGIC_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.FINAL_SEQUENTIAL,Language.get("Machines.FINAL_SEQUENTIAL.Name")),null)
            .register();
    public static final SlimefunItem FINAL_FRAME=new MultiPart(AddGroups.MATERIAL,AddItem.FINAL_FRAME,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.FINAL_SEQUENTIAL,Language.get("Machines.FINAL_SEQUENTIAL.Name")),"final.frame"){
            public boolean redirectMenu(){
                return false;
            }
    }
            .register();

    public static final SlimefunItem STACKFRAME=new MaterialItem(AddGroups.MATERIAL,AddItem.STACKFRAME,BugCrafter.TYPE,
            recipe(AddItem.SPACE_PLATE,AddItem.BISILVER,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.BISILVER,AddItem.SPACE_PLATE,
                    AddItem.BISILVER,AddItem.LDIGITIZER,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.LDIGITIZER,AddItem.BISILVER,
                    "GPS_TRANSMITTER_4",AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,"GPS_TRANSMITTER_4",
                    AddItem.LIOPORT,AddItem.PAGOLD,AddItem.CHIP_CORE,AddItem.CHIP_CORE,AddItem.PAGOLD,AddItem.LIOPORT,
                    AddItem.BISILVER,AddItem.LDIGITIZER,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.LDIGITIZER,AddItem.BISILVER,
                    AddItem.SPACE_PLATE,AddItem.BISILVER,setC(AddItem.ATOM_INGOT,8),setC(AddItem.ATOM_INGOT,8),AddItem.BISILVER,AddItem.SPACE_PLATE),null)
            .setOutput(setC(AddItem.STACKFRAME,39)).register();
    public static final SlimefunItem LASER=new MaterialItem(AddGroups.ADVANCED,AddItem.LASER,BugCrafter.TYPE,
            recipe(AddItem.HYDRAGYRUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.HYDRAGYRUM,
                    AddItem.ANTIMONY_INGOT,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.ANTIMONY_INGOT,
                    AddItem.BORON,AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,AddItem.BORON,
                    AddItem.BORON,AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,AddItem.BORON,
                    AddItem.ANTIMONY_INGOT,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.ANTIMONY_INGOT,
                    AddItem.HYDRAGYRUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.HYDRAGYRUM),null)
            .register();
    public static final SlimefunItem VIRTUALWORLD=new MaterialItem(AddGroups.MATERIAL,AddItem.VIRTUALWORLD,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    //machines
    public static final SlimefunItem BOOL_GENERATOR=new BoolGenerator(AddGroups.BASIC,AddItem.BOOL_GENERATOR,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("OBSERVER","REDSTONE","OBSERVER",
                    "REDSTONE_TORCH","SILICON","SILICON",
                    "STEEL_INGOT","MEDIUM_CAPACITOR","STEEL_INGOT"),Material.RECOVERY_COMPASS,6)
            .register();
    public static final SlimefunItem LOGIC_REACTOR=new LogicReactor(AddGroups.BASIC,AddItem.LOGIC_REACTOR,RecipeType.ANCIENT_ALTAR,
            recipe(AddItem.LOGIGATE,"COMPARATOR",AddItem.LOGIGATE,
                    "HEATING_COIL" , AddItem.TRUE_,"HEATING_COIL",
                    "REINFORCED_PLATE","CARBONADO_EDGED_CAPACITOR","REINFORCED_PLATE"
                    ),Material.COMPARATOR,3)
            .register();
    public static final SlimefunItem BUG_CRAFTER=new BugCrafter(AddGroups.BASIC,AddItem.BUG_CRAFTER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.LOGIGATE,AddItem.ABSTRACT_INGOT,
                    AddItem.CHIP_INGOT,AddItem.BUG,AddItem.CHIP_INGOT,
                    AddItem.ABSTRACT_INGOT,"ENERGIZED_CAPACITOR",AddItem.ABSTRACT_INGOT),10_000,1_000,7)
            .register();
    public static final  SlimefunItem FURNACE_FACTORY=new MTMachine(AddGroups.BASIC, AddItem.FURNACE_FACTORY,BugCrafter.TYPE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_FURNACE","ELECTRIC_FURNACE",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_FURNACE","ELECTRIC_FURNACE",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT),
            AddUtils.addGlow( new ItemStack(Material.FLINT_AND_STEEL)),60,6_400,
            ()->{
                List<MachineRecipe> recipelist=new ArrayList<>();
                List<MachineRecipe> rp=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_FURNACE);
                for (MachineRecipe rps:rp){
                    recipelist.add(MachineRecipeUtils.stackFrom(1,rps.getInput(),rps.getOutput()));
                }
                return recipelist;
            })
            .register();
    public static final  SlimefunItem SMELTRY=new AEMachine(AddGroups.BASIC, AddItem.SMELTRY,BugCrafter.TYPE,
            recipe(AddItem.ABSTRACT_INGOT,setC(AddItem.STAR_GOLD_INGOT,2),AddItem.LMOTOR,AddItem.LMOTOR,setC(AddItem.STAR_GOLD_INGOT,2),AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.STAR_GOLD_INGOT,2),AddItem.LENGINE,AddItem.LENGINE,setC(AddItem.STAR_GOLD_INGOT,2),AddItem.ABSTRACT_INGOT),
            Material.STONE,100,12_800,
            ()->{
                List<MachineRecipe> recipelist=new ArrayList<>();
                List<MachineRecipe> rp=RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItem.getById("ELECTRIC_SMELTERY"));
                for (MachineRecipe rps:rp){
                    recipelist.add(MachineRecipeUtils.stackFrom(0,rps.getInput(),rps.getOutput()));
                }
                return recipelist;
            })
            .register();
    public static final  SlimefunItem ENDFRAME_MACHINE=new EMachine(AddGroups.VANILLA, AddItem.ENDFRAME_MACHINE,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.PARADOX,AddItem.LENGINE,AddItem.PARADOX,
                    AddItem.END_FEAT,AddItem.DIMENSIONAL_SHARD,AddItem.END_FEAT,
                    AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.ABSTRACT_INGOT), Material.STONE,324,2_000,
            mkMp(
                    mkP(mkl(setC(AddItem.END_FEAT,4),"64END_STONE"),mkl("END_PORTAL_FRAME")),25,
                        mkP(mkl(AddItem.STAR_GOLD,"3END_PORTAL_FRAME"),mkl(AddItem.PORTAL_FRAME)),6,
                        mkP(mkl(AddItem.STAR_GOLD_INGOT,"16QUARTZ_BLOCK"),mkl(AddItem.SOLAR_REACTOR_FRAME)),6,
                        mkP(mkl(setC(AddItem.LPLATE,2),"16GLASS"),mkl(AddItem.SOLAR_REACTOR_GLASS)),6,
                        mkP(mkl(AddItem.STACKFRAME,AddItem.TRUE_),mkl(AddItem.STACKMACHINE)),6,
                        mkP(mkl(AddItem.STACKFRAME,AddItem.FALSE_),mkl(AddItem.STACKMGENERATOR)),6,
                        mkP(mkl(AddItem.STACKFRAME,"4ENERGY_REGULATOR"),mkl(AddItem.ENERGY_AMPLIFIER)),6,
                        mkP(mkl(setC(AddItem.SPACE_PLATE,2),AddItem.MASS_CORE),mkl(setC(AddItem.TRANSMUTATOR_FRAME,9))),6,
                        mkP(mkl(AddItem.SPACE_PLATE,setC(AddItem.LFIELD,24)),mkl(setC(AddItem.TRANSMUTATOR_GLASS,7))),6,
                        mkP(mkl(setC(AddItem.ATOM_INGOT,32),AddItem.PAGOLD),mkl(setC(AddItem.TRANSMUTATOR_ROD,8))),6
            ))
            .register();

    public static final  SlimefunItem DUST_EXTRACTOR=new MTMachine(AddGroups.BASIC, AddItem.DUST_EXTRACTOR,BugCrafter.TYPE,
            recipe(AddItem.LPLATE,setC(AddItem.TECH_CORE,4),setC(AddItem.PAGOLD,2),setC(AddItem.PAGOLD,2),setC(AddItem.MASS_CORE,4),AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,8),setC(AddItem.CHIP_CORE,2),setC(AddItem.CHIP_CORE,2),setC(AddItem.ATOM_INGOT,8),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4ELECTRIC_ORE_GRINDER_3","4ELECTRIC_ORE_GRINDER_3",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,"4ELECTRIC_GOLD_PAN_3","4ELECTRIC_GOLD_PAN_3",AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4ELECTRIC_DUST_WASHER_3","4ELECTRIC_DUST_WASHER_3",AddItem.LMOTOR,AddItem.LPLATE,
                    setC(AddItem.PAGOLD,2),setC(AddItem.TECH_CORE,4),setC(AddItem.PAGOLD,2),setC(AddItem.PAGOLD,2),setC(AddItem.MASS_CORE,4),setC(AddItem.PAGOLD,2)),
            new ItemStack(Material.LANTERN),1800,86400,
                mkMp(
                        mkP(mkl("64COBBLESTONE"),mkl(AddUtils.randItemStackFactory(
                                new LinkedHashMap<>(){{
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
    public static final  SlimefunItem INGOT_FACTORY=new MTMachine(AddGroups.BASIC, AddItem.INGOT_FACTORY,BugCrafter.TYPE,
            recipe(AddItem.LPLATE,setC(AddItem.METAL_CORE,4),setC(AddItem.PAGOLD,2),setC(AddItem.PAGOLD,2),setC(AddItem.SMELERY_CORE,4),AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,8),setC(AddItem.CHIP_CORE,2),setC(AddItem.CHIP_CORE,2),setC(AddItem.ATOM_INGOT,8),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",AddItem.LMOTOR,AddItem.LPLATE,
                    setC(AddItem.PAGOLD,2),setC(AddItem.METAL_CORE,4),setC(AddItem.PAGOLD,2),setC(AddItem.PAGOLD,2),setC(AddItem.SMELERY_CORE,4),setC(AddItem.PAGOLD,2)),
            new ItemStack(Material.LANTERN),2400,129600,
            mkMp(
                    mkP(mkl("64IRON_DUST"),mkl("64IRON_INGOT")),0,
                    mkP(mkl("64GOLD_DUST"),mkl("64GOLD_INGOT")),0,
                    mkP(mkl("64COPPER_DUST"),mkl("64COPPER_INGOT")),0,
                    mkP(mkl("64TIN_DUST"),mkl("64TIN_INGOT")),0,
                    mkP(mkl("64ZINC_DUST"),mkl("64ZINC_INGOT")),0,
                    mkP(mkl("64ALUMINUM_DUST"),mkl("64ALUMINUM_INGOT")),0,
                    mkP(mkl("64SILVER_DUST"),mkl("64SILVER_INGOT")),0,
                    mkP(mkl("64MAGNESIUM_DUST"),mkl("64MAGNESIUM_INGOT")),0,
                    mkP(mkl("64LEAD_DUST"),mkl("64LEAD_INGOT")),0


            )
    )
            .register();
    public static final  SlimefunItem LVOID_GENERATOR=new TestGenerator(AddGroups.ENERGY, AddItem.LVOID_GENERATOR,BugCrafter.TYPE,
            recipe(AddItem.LFIELD,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LFIELD,
                    AddItem.LPLATE,null,null,null,null,AddItem.LPLATE,
                    AddItem.ABSTRACT_INGOT,null,AddItem.LENGINE,AddItem.LENGINE,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.LENGINE,AddItem.LENGINE,null,AddItem.ABSTRACT_INGOT,
                    AddItem.LPLATE,null,null,null,null,AddItem.LPLATE,
                    AddItem.LFIELD,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LFIELD),2333,180,
            1000,1145)
            .register();
    public static final  SlimefunItem ENERGY_TRASH=new EnergyTrash(AddGroups.ENERGY, AddItem.ENERGY_TRASH,RecipeType.ANCIENT_ALTAR,
            recipe(AddItem.BUG,"ENERGY_REGULATOR",AddItem.BUG,"ENERGY_CONNECTOR","TRASH_CAN_BLOCK","ENERGY_CONNECTOR",
                    AddItem.BUG,"ENERGIZED_CAPACITOR",AddItem.BUG), 100_000_000)
            .register();
    public static final  SlimefunItem OPPO_GEN=new BiReactor(AddGroups.ENERGY, AddItem.OPPO_GEN,BugCrafter.TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.FALSE_,AddItem.TRUE_,AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.TRUE_,AddItem.FALSE_,AddItem.ABSTRACT_INGOT,null,
                    AddItem.ABSTRACT_INGOT,AddItem.LBOOLIZER,AddItem.LENGINE,AddItem.LENGINE,AddItem.LBOOLIZER,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.LBOOLIZER,AddItem.LBOOLIZER,AddItem.LBOOLIZER,AddItem.LBOOLIZER,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT), 15_000,1_250,10_000)
            .register();
    public static final  SlimefunItem ARC_REACTOR=new EGenerator(AddGroups.ENERGY, AddItem.ARC_REACTOR,BugCrafter.TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.PAGOLD,null,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.CHIP_CORE,AddItem.CHIP_CORE,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.SMELERY_CORE,AddItem.TECH_CORE,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.MASS_CORE,AddItem.METAL_CORE,AddItem.LSINGULARITY,AddItem.PAGOLD,
                    AddItem.BISILVER,AddItem.BISILVER,AddItem.BISILVER,AddItem.BISILVER,AddItem.BISILVER,AddItem.BISILVER), Material.BEACON,2_500_000,116_665,
            mkMp(
                mkP(    mkl(AddItem.PAGOLD),mkl("GOLD_INGOT")) ,600,
                    mkP(    mkl(AddItem.BISILVER),mkl(AddItem.ABSTRACT_INGOT)) ,480
            ))
            .register();

    public static final  SlimefunItem CHIP_REACTOR=new ChipReactor(AddGroups.ENERGY, AddItem.CHIP_REACTOR,BugCrafter.TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.SPACE_PLATE,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.SPACE_PLATE,null,
                    AddItem.SPACE_PLATE,AddItem.PDCECDMD,AddItem.LASER,AddItem.LASER,AddItem.PDCECDMD,AddItem.SPACE_PLATE,
                    AddItem.TECH_CORE,AddItem.CHIP_CORE,AddItem.ADVANCED_CHIP_MAKER,AddItem.ADVANCED_CHIP_MAKER,AddItem.CHIP_CORE,AddItem.TECH_CORE,
                    AddItem.TECH_CORE,AddItem.HGTLPBBI,AddItem.LASER,AddItem.LASER,AddItem.HGTLPBBI,AddItem.TECH_CORE,
                    AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT), 200_000_000,0.1,30)
            .register();
    public static final SlimefunItem ENERGY_AMPLIFIER=new EnergyAmplifier(AddGroups.ENERGY,AddItem.ENERGY_AMPLIFIER,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),1_000_000_000)
            .register();
    public static final  SlimefunItem SPECIAL_CRAFTER=new SpecialCrafter(AddGroups.BASIC, AddItem.SPECIAL_CRAFTER,BugCrafter.TYPE,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.LFIELD,AddItem.LOGIC,AddItem.LOGIC,AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,
                    AddItem.LFIELD,AddItem.LDIGITIZER,setC(AddItem.LPLATE,2),setC(AddItem.LPLATE,2),AddItem.LMOTOR,AddItem.LFIELD,
                    AddItem.LOGIC,setC(AddItem.LPLATE,2),setC(AddItem.LCRAFT,2),AddItem.LIOPORT,setC(AddItem.LPLATE,2),AddItem.LOGIC,
                    AddItem.LOGIC,setC(AddItem.LPLATE,2),AddItem.LSCHEDULER,setC(AddItem.LCRAFT,2),setC(AddItem.LPLATE,2),AddItem.LOGIC,
                    AddItem.LFIELD,AddItem.LMOTOR,setC(AddItem.LPLATE,2),setC(AddItem.LPLATE,2),AddItem.LDIGITIZER,AddItem.LFIELD,
                    AddItem.STAR_GOLD_INGOT,AddItem.LFIELD,AddItem.LOGIC,AddItem.LOGIC,AddItem.LFIELD,AddItem.STAR_GOLD_INGOT)
            , Material.NETHER_STAR,10,800,20_000,new HashSet<>(){{
                if(AddDepends.INFINITYWORKBENCH_TYPE!=null)
                    add(AddDepends.INFINITYWORKBENCH_TYPE);
                if(AddDepends.VOIDHARVEST!=null)
                    add(AddDepends.VOIDHARVEST);
            }})
            .register();

    public static final  SlimefunItem STAR_SMELTERY=new AEMachine(AddGroups.ADVANCED, AddItem.STAR_SMELTERY,BugCrafter.TYPE,
            recipe(AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LFIELD,
                    AddItem.STAR_GOLD_INGOT,setC(AddItem.LPLATE,2),"ELECTRIC_INGOT_FACTORY_3","ELECTRIC_INGOT_FACTORY_3",setC(AddItem.LPLATE,2),AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.LMOTOR,"CARBONADO_EDGED_FURNACE","CARBONADO_EDGED_FURNACE",AddItem.LMOTOR,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.LMOTOR,"CARBONADO_EDGED_FURNACE","CARBONADO_EDGED_FURNACE",AddItem.LMOTOR,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,setC(AddItem.LPLATE,2),"2ELECTRIC_SMELTERY_2","2ELECTRIC_SMELTERY_2",setC(AddItem.LPLATE,2),AddItem.STAR_GOLD_INGOT,
                    AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LFIELD), Material.STONE,18_000,180_000,
           120,AddSlimefunItems.STARSMELTERY)
            .register();
    public static final  SlimefunItem SEQ_CONSTRUCTOR=new SequenceConstructor(AddGroups.BASIC, AddItem.SEQ_CONSTRUCTOR,BugCrafter.TYPE,
            recipe(setC(AddItem.STAR_GOLD,2),AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,setC(AddItem.STAR_GOLD,2),
                    AddItem.ABSTRACT_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.STAR_GOLD_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.LBOOLIZER,null,AddItem.LENGINE,AddItem.LENGINE,null,AddItem.LBOOLIZER,
                    AddItem.LBOOLIZER,AddItem.LIOPORT,AddItem.LSCHEDULER,AddItem.LDIGITIZER,AddItem.LIOPORT,AddItem.LBOOLIZER,
                    AddItem.ABSTRACT_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LCRAFT,AddItem.LCRAFT,AddItem.STAR_GOLD_INGOT,AddItem.ABSTRACT_INGOT,
                    setC(AddItem.STAR_GOLD,2),AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,setC(AddItem.STAR_GOLD,2)),new ItemStack(Material.NETHERITE_BLOCK) ,1280,8848,
            mkMp(
                    //重金核心铜铁金 下界  重金核心-------
                    //镁铅锡锌 冶炼核心-------
                    //银铝 红石 钻石 科技核心-------
                    //煤炭 青金石 石英 绿宝石 物质核心-------
                    mkP(mkl("512COPPER_INGOT","512IRON_INGOT","512GOLD_INGOT","512NETHERITE_INGOT"),mkl(AddItem.METAL_CORE)),3,
                    mkP(mkl("512MAGNESIUM_INGOT","512LEAD_INGOT","512TIN_INGOT","512ZINC_INGOT"),mkl(AddItem.SMELERY_CORE)),3,
                    mkP(mkl("512SILVER_INGOT","512ALUMINUM_INGOT","512DIAMOND","512REDSTONE"),mkl(AddItem.TECH_CORE)),3,
                    mkP(mkl("512COAL","512LAPIS_LAZULI","512QUARTZ","512EMERALD"),mkl(AddItem.MASS_CORE)),3
            ))
            .register();




    public static final  SlimefunItem CHIP_MAKER=new EMachine(AddGroups.ADVANCED, AddItem.CHIP_MAKER,BugCrafter.TYPE,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.LENGINE,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.LENGINE,AddItem.STAR_GOLD_INGOT,
                    null,AddItem.STAR_GOLD_INGOT,AddItem.LOGIC,AddItem.NOLOGIC,AddItem.STAR_GOLD_INGOT,null,
                    AddItem.LPLATE,null,AddItem.LOGIC,AddItem.NOLOGIC,null,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LBOOLIZER,AddItem.LDIGITIZER,AddItem.LDIGITIZER,AddItem.LBOOLIZER,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LBOOLIZER,AddItem.LSCHEDULER,AddItem.LSCHEDULER,AddItem.LBOOLIZER,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE,AddItem.LPLATE), Material.POTATO,0,0,
            mkMp(
                mkP(mkl(setC(AddItem.CHIP_INGOT,6),AddItem.EXISTE),mkl( ChipCardCode.CHIP_0)),4,
                    mkP(mkl(setC(AddItem.CHIP_INGOT,6),AddItem.UNIQUE),mkl( ChipCardCode.CHIP_1)),4,
                    mkP(mkl(AddItem.LSCHEDULER,ChipCardCode.CHIP_FINAL),mkl(CHIP_CORE)),4
            )).register();

    public static final SlimefunItem CHIP_CONSUMER=new ChipConsumer(AddGroups.ADVANCED,AddItem.CHIP_CONSUMER,BugCrafter.TYPE,
            recipe(AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,64),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LBOOLIZER,64),AddItem.ABSTRACT_INGOT,
                    AddItem.CHIP_INGOT,AddItem.LFIELD,setC(AddItem.LOGIGATE,16),AddItem.LDIGITIZER,AddItem.LFIELD,AddItem.CHIP_INGOT,
                    AddItem.CHIP_INGOT,AddItem.LFIELD,AddItem.LDIGITIZER,setC(AddItem.LOGIGATE,16),AddItem.LFIELD,AddItem.CHIP_INGOT,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,64),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LBOOLIZER,64),AddItem.ABSTRACT_INGOT,
                    AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE),4_000,500)
            .register();

    public static final  SlimefunItem CHIP_BICONSUMER=new ChipBiConsumer(AddGroups.ADVANCED, AddItem.CHIP_BICONSUMER,BugCrafter.TYPE,
            recipe(AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LDIGITIZER,2),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LDIGITIZER,2),AddItem.ABSTRACT_INGOT,
                    AddItem.LPLATE,AddItem.LFIELD,setC(AddItem.LOGIGATE,16),setC(AddItem.UNIQUE,2),AddItem.LFIELD,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LFIELD,setC(AddItem.EXISTE,2),setC(AddItem.LOGIGATE,16),AddItem.LFIELD,AddItem.LPLATE,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LDIGITIZER,2),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LDIGITIZER,2),AddItem.ABSTRACT_INGOT,
                    AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.LENGINE),8_000,1_000)
            .register();
    public static final SlimefunItem STACKMACHINE=new StackMachine(AddGroups.ADVANCED,AddItem.STACKMACHINE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),Material.IRON_PICKAXE,
            2_000,2_000_000,1.0)
            .register();
    //
    public static final  SlimefunItem ADVANCED_CHIP_MAKER=new ChipCopier(AddGroups.ADVANCED, AddItem.ADVANCED_CHIP_MAKER,BugCrafter.TYPE,
            recipe(null,AddItem.REINFORCED_CHIP_INGOT,AddItem.CHIP_MAKER,AddItem.CHIP_MAKER,AddItem.REINFORCED_CHIP_INGOT,null,
                    null,AddItem.REINFORCED_CHIP_INGOT,AddItem.CHIP_MAKER,AddItem.CHIP_MAKER,AddItem.REINFORCED_CHIP_INGOT,null,
                    AddItem.HGTLPBBI,AddItem.SPACE_PLATE,AddItem.LASER,AddItem.LASER,AddItem.SPACE_PLATE,AddItem.HGTLPBBI,
                    AddItem.BISILVER,AddItem.SPACE_PLATE,setC(AddItem.LSINGULARITY,2),setC(AddItem.LSINGULARITY,2),AddItem.SPACE_PLATE,AddItem.BISILVER,
                    AddItem.BISILVER,AddItem.SPACE_PLATE,setC(AddItem.LSINGULARITY,2),setC(AddItem.LSINGULARITY,2),AddItem.SPACE_PLATE,AddItem.BISILVER,
                    AddItem.PDCECDMD,AddItem.SPACE_PLATE,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.SPACE_PLATE,AddItem.PDCECDMD))
            .register();





    //Material Generators
    public static final SlimefunItem MAGIC_STONE=new SMGenerator(AddGroups.GENERATORS, AddItem.MAGIC_STONE,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("DIAMOND_PICKAXE","LAVA_BUCKET","DIAMOND_PICKAXE",
                    "PISTON",AddItem.LOGIGATE,"PISTON",
                    "COBALT_PICKAXE","WATER_BUCKET","COBALT_PICKAXE"),18,1000,66,
            AddUtils.randItemStackFactory(
                    mkMp("2COBBLESTONE",72,
                            "2COAL",7,
                            "4REDSTONE",6,
                            "2IRON_INGOT",5,
                            "8LAPIS_LAZULI",4,
                            "2GOLD_INGOT",3,
                            "DIAMOND",2,
                            "EMERALD",1
                    )
            ))
            .register();
    public static final SlimefunItem BOOL_MG = new MMGenerator(AddGroups.GENERATORS, AddItem.BOOL_MG, BugCrafter.TYPE,
            recipe(null,AddItem.FALSE_,AddItem.LFIELD,AddItem.LFIELD,AddItem.FALSE_,null,
                    AddItem.TRUE_,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.TRUE_,
                    AddItem.LFIELD,AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,47),setC(AddItem.LOGIGATE,17),AddItem.ABSTRACT_INGOT,AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.ABSTRACT_INGOT,setC(AddItem.LOGIGATE,15),setC(AddItem.NOLOGIC,21),AddItem.ABSTRACT_INGOT,AddItem.LFIELD,
                    AddItem.TRUE_,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.TRUE_,
                    null,AddItem.FALSE_,AddItem.LFIELD,AddItem.LFIELD,AddItem.FALSE_,null), 6, 114514, 256,
            new LinkedHashMap<>(){{
                put(mkl(AddItem.TRUE_),mkl(setC(AddItem.TRUE_,114514)));
                put(mkl(AddItem.FALSE_),mkl(setC(AddItem.FALSE_,1919810)));
                put(mkl(AddItem.LBOOLIZER),mkl(setC(AddItem.LBOOLIZER,1)));
            }})
            .register();
    public static final SlimefunItem OVERWORLD_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.OVERWORLD_MINER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.MAGIC_STONE,AddItem.ABSTRACT_INGOT,
                    AddItem.UNIQUE,AddItem.LENGINE,AddItem.EXISTE
                    ,AddItem.WORLD_FEAT,AddItem.MAGIC_STONE,AddItem.WORLD_FEAT
            ),14,2_500,400,
            AddUtils.randItemStackFactory(
                    mkMp("8COBBLESTONE",40,
                            "3COAL",9,
                            "3REDSTONE",9,
                            "3IRON_INGOT",9,
                            "8LAPIS_LAZULI",9,
                            "3GOLD_INGOT",9,
                            "3DIAMOND",8,
                            "3EMERALD",7
                    )
            ))
            .register();
    public static final SlimefunItem NETHER_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.NETHER_MINER,BugCrafter.TYPE,
            recipe(null,setC(AddItem.PARADOX,3),setC(AddItem.PARADOX,3),setC(AddItem.PARADOX,3),setC(AddItem.PARADOX,3),null,
                    null,AddItem.LPLATE,AddItem.OVERWORLD_MINER,AddItem.OVERWORLD_MINER,AddItem.LPLATE,null,
                    null,AddItem.LPLATE,AddItem.LENGINE,AddItem.LENGINE,AddItem.LPLATE,null,
                    null,AddItem.NETHER_FEAT,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.NETHER_FEAT,null
                  ),10,3_000,600,
            AddUtils.randItemStackFactory(
                    mkMp(
                            "32NETHERRACK",30,
                            "10QUARTZ",40,
                            "16MAGMA_BLOCK",7,
                            "16BLACKSTONE",7,
                            "16BASALT",7,
                            "16NETHER_WART",7,
                            "4ANCIENT_DEBRIS",2
                    )
            ))
            .register();
    public static final SlimefunItem END_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.END_MINER,BugCrafter.TYPE,
            recipe(null,AddItem.LMOTOR,AddItem.LFIELD,AddItem.LFIELD,AddItem.LMOTOR,null,
                    null,AddItem.LPLATE,"END_PORTAL_FRAME","END_PORTAL_FRAME",AddItem.LPLATE,null,
                    null,AddItem.LPLATE,AddItem.NETHER_MINER,AddItem.NETHER_MINER,AddItem.LPLATE,null,
                    null,AddItem.LPLATE,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.LPLATE,null,
                    AddItem.END_FEAT,AddItem.LENGINE,AddItem.END_FEAT,AddItem.END_FEAT,AddItem.LENGINE,AddItem.END_FEAT
            ),8,6_000,1000,
            AddUtils.randItemStackFactory(
                    mkMp(
                            "64END_STONE",150,
                            "10OBSIDIAN",100,
                            "4CHORUS_FLOWER",30,
                            "2SHULKER_SHELL",35,
                            "DRAGON_BREATH",35,
                            AddItem.DIMENSIONAL_SHARD,40,
                            AddItem.STAR_GOLD,10
                    )
            ))
            .register();
    public static final SlimefunItem DIMENSION_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.DIMENSION_MINER,BugCrafter.TYPE  ,
            recipe("GPS_TRANSMITTER_4",AddItem.STAR_GOLD,AddItem.LFIELD,AddItem.LFIELD,AddItem.STAR_GOLD,"GPS_TRANSMITTER_4",
                    AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.STAR_GOLD,AddItem.STAR_GOLD_INGOT,
                    AddItem.LFIELD,null,AddItem.NETHER_MINER,AddItem.NETHER_MINER,null,AddItem.LFIELD,
                    AddItem.LFIELD,null,AddItem.END_MINER,AddItem.END_MINER,null,AddItem.LFIELD,
                    AddItem.STAR_GOLD_INGOT,AddItem.LIOPORT,AddItem.LENGINE,AddItem.LENGINE,AddItem.LIOPORT,AddItem.STAR_GOLD_INGOT,
                    "GPS_TRANSMITTER_4",AddItem.STAR_GOLD,AddItem.LFIELD,AddItem.LFIELD,AddItem.STAR_GOLD,"GPS_TRANSMITTER_4"),5,18_000,1_800,
            AddUtils.randItemStackFactory(
                    mkMp(
                            "64COBBLESTONE",1,
                            "64END_STONE",1,
                            "64NETHERRACK",1
                    )
            ),

            AddUtils.randItemStackFactory(
                    mkMp(
                            AddItem.DIMENSIONAL_SHARD,3,
                            AddItem.STAR_GOLD,1
                    )
            ),
            AddUtils.randItemStackFactory(
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

    public static final SlimefunItem MAGIC_PLANT = new MMGenerator(AddGroups.GENERATORS, AddItem.MAGIC_PLANT, RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(Material.DIAMOND_HOE,"WATER_BUCKET",Material.DIAMOND_HOE,
                    "OBSERVER",AddItem.LOGIGATE,"OBSERVER",
                    Material.BONE_BLOCK,"PISTON",Material.BONE_BLOCK), 54, 1_000, 33,
            new LinkedHashMap<>(){{
                put(mkl("WHEAT_SEEDS"),mkl("WHEAT","2WHEAT_SEEDS"));
                put(mkl("CARROT"),mkl("2CARROT"));
                put(mkl("POTATO"),mkl("2POTATO"));
                put(mkl("PUMPKIN_SEEDS"),mkl("PUMPKIN","2PUMPKIN_SEEDS"));
                put(mkl("SUGAR_CANE"),mkl("3SUGAR_CANE"));
                put(mkl("OAK_SAPLING"),mkl("OAK_SAPLING","2OAK_LOG","2OAK_LEAVES"));
            }})
            .register();
    public static final SlimefunItem OVERWORLD_PLANT = new MMGenerator(AddGroups.GENERATORS, AddItem.OVERWORLD_PLANT, RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("CROP_GROWTH_ACCELERATOR_2",AddItem.MAGIC_PLANT,"CROP_GROWTH_ACCELERATOR_2",
                    "TREE_GROWTH_ACCELERATOR",AddItem.LENGINE,"TREE_GROWTH_ACCELERATOR"
                    ,AddItem.WORLD_FEAT,AddItem.MAGIC_PLANT,AddItem.WORLD_FEAT
            ), 14, 2_500,400,
            new LinkedHashMap<>(){{
                put(mkl("COCOA_BEANS"),mkl("9COCOA_BEANS"));
                put(mkl("MELON_SEEDS"),mkl("3MELON","3MELON_SEEDS"));
                put(mkl("PUMPKIN_SEEDS"),mkl("3PUMPKIN","3PUMPKIN_SEEDS"));
                put(mkl("BEETROOT_SEEDS"),mkl("9BEETROOT","6BEETROOT_SEEDS"));
                put(mkl("WHEAT_SEEDS"),mkl("9WHEAT","6WHEAT_SEEDS"));
                put(mkl("APPLE"),mkl("9APPLE"));
                put(mkl("BROWN_MUSHROOM"),mkl("9BROWN_MUSHROOM"));
                put(mkl("RED_MUSHROOM"),mkl("9RED_MUSHROOM"));
                put(mkl("DEAD_BUSH"),mkl("9DEAD_BUSH","6STICK"));
                put(mkl("CARROT"),mkl("9CARROT"));
                put(mkl("POTATO"),mkl("9POTATO"));
                put(mkl("SWEET_BERRIES"),mkl("9SWEET_BERRIES"));
                put(mkl("SUGAR_CANE"),mkl("9SUGAR_CANE"));
                put(mkl("BAMBOO"),mkl("9BAMBOO"));
                put(mkl("CACTUS"),mkl("9CACTUS"));
                put(mkl("OAK_SAPLING"),mkl("9OAK_SAPLING","18OAK_LOG","6APPLE","9OAK_LEAVES","6STICK"));
                put(mkl("BIRCH_SAPLING"),mkl("9BIRCH_SAPLING","18BIRCH_LOG","6APPLE","9BIRCH_LEAVES","6STICK"));
                put(mkl("SPRUCE_SAPLING"),mkl("9SPRUCE_SAPLING","18SPRUCE_LOG","6APPLE","9SPRUCE_LEAVES","6STICK"));
                put(mkl("DARK_OAK_SAPLING"),mkl("9DARK_OAK_SAPLING","18DARK_OAK_LOG","6APPLE","9DARK_OAK_LEAVES","6STICK"));
                put(mkl("JUNGLE_SAPLING"),mkl("9JUNGLE_SAPLING","18JUNGLE_LOG","6APPLE","9JUNGLE_LEAVES","6STICK"));
                put(mkl("ACACIA_SAPLING"),mkl("9ACACIA_SAPLING","18ACACIA_LOG","6APPLE","9ACACIA_LEAVES","6STICK"));
                put(mkl("MANGROVE_PROPAGULE"),mkl("9MANGROVE_PROPAGULE","18MANGROVE_LOG","9MANGROVE_LEAVES"));
                put(mkl("CHERRY_SAPLING"),mkl("9CHERRY_SAPLING","18CHERRY_LOG","9CHERRY_LEAVES"));
            }})
            .register();
    public static final SlimefunItem NETHER_PLANT = new MMGenerator(AddGroups.GENERATORS, AddItem.NETHER_PLANT, BugCrafter.TYPE,
            recipe(null,setC(AddItem.PARADOX,3),setC(AddItem.PARADOX,3),setC(AddItem.PARADOX,3),setC(AddItem.PARADOX,3),null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.OVERWORLD_PLANT,AddItem.LOGIGATE,AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.LOGIGATE,"TREE_GROWTH_ACCELERATOR",AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.NETHER_FEAT,"MEDIUM_CAPACITOR","MEDIUM_CAPACITOR",AddItem.NETHER_FEAT,null
            ), 12,3_000,600,
            new LinkedHashMap<>(){{
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
    public static final SlimefunItem END_PLANT = new MMGenerator(AddGroups.GENERATORS, AddItem.END_PLANT, BugCrafter.TYPE,
            recipe(null,AddItem.LFIELD,setC(AddItem.PARADOX,3),setC(AddItem.PARADOX,3),AddItem.LFIELD,null,
                    null,AddItem.ABSTRACT_INGOT,"CROP_GROWTH_ACCELERATOR_2","TREE_GROWTH_ACCELERATOR",AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.OVERWORLD_PLANT,AddItem.NETHER_PLANT,AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.ABSTRACT_INGOT,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.ABSTRACT_INGOT,null,
                    AddItem.END_FEAT,AddItem.LENGINE,AddItem.END_FEAT,AddItem.END_FEAT,AddItem.LENGINE,AddItem.END_FEAT
            ), 10,6_000,1000,
            new LinkedHashMap<>(){{
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
    public static final SlimefunItem STONE_FACTORY = new MMGenerator(AddGroups.GENERATORS, AddItem.STONE_FACTORY, BugCrafter.TYPE,
            recipe(AddItem.LPLATE,setC(AddItem.METAL_CORE,4),setC(AddItem.PAGOLD,2),setC(AddItem.PAGOLD,2),setC(AddItem.SMELERY_CORE,4),AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,8),setC(AddItem.CHIP_CORE,2),setC(AddItem.CHIP_CORE,2),setC(AddItem.ATOM_INGOT,8),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4PROGRAMMABLE_ANDROID_3_BUTCHER","4PROGRAMMABLE_ANDROID_3_BUTCHER",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,setC(AddItem.OVERWORLD_MINER,4),setC(AddItem.OVERWORLD_MINER,4),AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4PROGRAMMABLE_ANDROID_3_BUTCHER","4PROGRAMMABLE_ANDROID_3_BUTCHER",AddItem.LMOTOR,AddItem.LPLATE,
                    setC(AddItem.PAGOLD,2),setC(AddItem.METAL_CORE,4),setC(AddItem.PAGOLD,2),setC(AddItem.PAGOLD,2),setC(AddItem.SMELERY_CORE,4),setC(AddItem.PAGOLD,2)), 1, 129_600, 2400,
            new LinkedHashMap<>(){{
                put(mkl("COBBLESTONE"),mkl(   AddUtils.randAmountItemFactory(new ItemStack(Material.COBBLESTONE),114,514)));
                put(mkl("NETHERRACK"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.NETHERRACK),114,514)));
                put(mkl("END_STONE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.END_STONE),114,514)));
                put(mkl("GRANITE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.GRANITE),114,514)));
                put(mkl("DIORITE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.DIORITE),114,514)));
                put(mkl("ANDESITE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.ANDESITE),114,514)));
                put(mkl("STONE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.STONE),114,514)));
            }})
            .register();
    public static final SlimefunItem REDSTONE_MG=new SMGenerator(AddGroups.VANILLA, AddItem.REDSTONE_MG,BugCrafter.TYPE,
            recipe(null,"REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK",null,
                    "REDSTONE_BLOCK",setC(AddItem.NOLOGIC,4),AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,setC(AddItem.NOLOGIC,4),"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",AddItem.ABSTRACT_INGOT,setC(AddItem.REDSTONE_ENGINE,2),setC(AddItem.REDSTONE_ENGINE,2),AddItem.ABSTRACT_INGOT,"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",AddItem.ABSTRACT_INGOT,setC(AddItem.REDSTONE_ENGINE,2),setC(AddItem.REDSTONE_ENGINE,2),AddItem.ABSTRACT_INGOT,"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",setC(AddItem.NOLOGIC,4),AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,setC(AddItem.NOLOGIC,4),"REDSTONE_BLOCK",
                    null,"REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK",null),3,10000,250,
            AddUtils.randItemStackFactory(
                    mkMp("REDSTONE_TORCH",16,"OBSERVER",16,"PISTON",16,"STICKY_PISTON",16,"REPEATER",16,"COMPARATOR",16,"LEVER",16,"NOTE_BLOCK",16,"REDSTONE_LAMP",16,
                            AddUtils.randItemStackFactory(mkMp("SLIME_BALL",26,"HONEY_BLOCK",3,"TNT",2,"REDSTONE",1)),16)
            ))
            .register();
    public static final SlimefunItem DUPE_MG=new MMGenerator(AddGroups.VANILLA, AddItem.DUPE_MG,BugCrafter.TYPE,
            recipe("TRIPWIRE_HOOK",AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.NOLOGIC,"TRIPWIRE_HOOK",
                    "TRIPWIRE_HOOK",null,null,null,null,"TRIPWIRE_HOOK",
                    "TRIPWIRE_HOOK",AddItem.REDSTONE_ENGINE,"RAIL","ACTIVATOR_RAIL",AddItem.REDSTONE_ENGINE,"TRIPWIRE_HOOK",
                    "WHITE_CARPET",AddItem.REDSTONE_ENGINE,"POWERED_RAIL","DETECTOR_RAIL",AddItem.REDSTONE_ENGINE,"WHITE_CARPET",
                    "WHITE_CARPET","STICKY_PISTON","OBSERVER","OBSERVER","STICKY_PISTON","WHITE_CARPET",
                    null,"WHITE_CARPET","WHITE_CARPET","WHITE_CARPET","WHITE_CARPET",null),2,1000,116,
            new LinkedHashMap<>(){{
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

            }})
            .register();
    public static final SlimefunItem ENDDUPE_MG=new MMGenerator(AddGroups.VANILLA, AddItem.ENDDUPE_MG,BugCrafter.TYPE,
            recipe(null,"SAND","ANVIL","ANVIL","SAND",null,
                    null,AddItem.REDSTONE_ENGINE,"END_PORTAL_FRAME","END_PORTAL_FRAME",AddItem.REDSTONE_ENGINE,null,
                    "OBSERVER",AddItem.REDSTONE_ENGINE,"END_PORTAL_FRAME","END_PORTAL_FRAME",AddItem.REDSTONE_ENGINE,"OBSERVER",
                    "OBSERVER",AddItem.DUPE_MG,"END_PORTAL_FRAME","END_PORTAL_FRAME",AddItem.DUPE_MG,"OBSERVER",
                    "END_STONE",AddItem.LFIELD,setC(AddItem.DIMENSIONAL_SHARD,2),setC(AddItem.DIMENSIONAL_SHARD,2),AddItem.LFIELD,"END_STONE",
                    null,"END_STONE",AddItem.LFIELD,AddItem.LFIELD,"END_STONE",null),3,1000,116,
            new LinkedHashMap<>(){{
                put(mkl("SAND"),mkl("16SAND"));
                put(mkl("ANVIL"),mkl("3ANVIL"));
                put(mkl("CHIPPED_ANVIL"),mkl("3CHIPPED_ANVIL"));
                put(mkl("DAMAGED_ANVIL"),mkl("3DAMAGED_ANVIL"));
                put(mkl("GRAVEL"),mkl("16GRAVEL"));
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
    public static final SlimefunItem REVERSE_GENERATOR = new MMGenerator(AddGroups.GENERATORS, AddItem.REVERSE_GENERATOR, BugCrafter.TYPE,
            recipe(null,AddItem.SPACE_PLATE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.SPACE_PLATE,null,
                    null,AddItem.SPACE_PLATE,AddItem.LOGIC_REACTOR,AddItem.LOGIC_REACTOR,AddItem.SPACE_PLATE,null,
                    null,AddItem.PAGOLD,AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.PAGOLD,null,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    null,AddItem.PAGOLD,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.PAGOLD,null), 6, 1_000_000, 24_000,
            new LinkedHashMap<>(){{
                put(mkl(AddItem.LOGIGATE),mkl(setC(AddItem.LOGIGATE,64)));
                put(mkl(AddItem.LOGIC),mkl(setC(AddItem.NOLOGIC,64)));
                put(mkl(AddItem.NOLOGIC),mkl(setC(AddItem.LOGIC,64)));
                put(mkl(AddItem.EXISTE),mkl(setC(AddItem.UNIQUE,64)));
                put(mkl(AddItem.UNIQUE),mkl(setC(AddItem.EXISTE,64)));
                put(mkl(AddItem.BUG),mkl(setC(AddItem.BUG,3)));
                put(mkl("IRON_INGOT"),mkl(setC(AddItem.ABSTRACT_INGOT,16)));
                put(mkl(AddItem.ABSTRACT_INGOT),mkl("64IRON_INGOT"));
            }})
            .register();
    public static final SlimefunItem VIRTUAL_MINER = new MMGenerator(AddGroups.GENERATORS, AddItem.VIRTUAL_MINER, BugCrafter.TYPE,
            recipe(AddItem.BISILVER,setC(AddItem.TECH_CORE,8),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.SMELERY_CORE,8),AddItem.BISILVER,
                    AddItem.LDIGITIZER,AddItem.BISILVER,AddItem.NETHER_MINER,AddItem.NETHER_MINER,AddItem.BISILVER,AddItem.LDIGITIZER,
                    AddItem.BISILVER,setC(AddItem.LMOTOR,2),setC(AddItem.CHIP_CORE,2),setC(AddItem.LSINGULARITY,3),setC(AddItem.LMOTOR,2),AddItem.BISILVER,
                    AddItem.BISILVER,setC(AddItem.LENGINE,2),setC(AddItem.VIRTUAL_SPACE,4),setC(AddItem.CHIP_CORE,2),setC(AddItem.LENGINE,2),AddItem.BISILVER,
                    AddItem.LIOPORT,AddItem.BISILVER,AddItem.DIMENSION_MINER,AddItem.DIMENSION_MINER,AddItem.BISILVER,AddItem.LIOPORT,
                    AddItem.BISILVER,setC(AddItem.MASS_CORE,8),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.METAL_CORE,8),AddItem.BISILVER), 1, 129_600, 5400,
            new LinkedHashMap<>(){{
                put(mkl(AddItem.WORLD_FEAT),mkl(AddUtils.randItemStackFactory(
                        mkMp("64COAL",8,
                                "64REDSTONE",8,
                                "64IRON_INGOT",8,
                                "128LAPIS_LAZULI",8,
                                "64DIAMOND",8,
                                "64EMERALD",8)
                )));
                put(mkl(AddItem.NETHER_FEAT),mkl(AddUtils.randItemStackFactory(
                        mkMp("32NETHERITE_INGOT",1,
                                "128QUARTZ",1,
                                "64MAGMA_BLOCK",1,
                                "64OBSIDIAN",1,
                                "32ANCIENT_DEBRIS",1,
                                "2NETHER_ICE",1)
                )));
                put(mkl(AddItem.END_FEAT),mkl(AddUtils.randItemStackFactory(
                        mkMp("4DRAGON_BREATH",2,
                        "4CHORUS_FLOWER",2,
                                "16ENDER_EYE",2,
                                "16ENDER_PEARL",2,
                                "4BUCKET_OF_OIL",2,
                                AddItem.STAR_GOLD,2
                        )
                )));
            }})
            .register();
    public static final SlimefunItem VIRTUAL_PLANT = new MMGenerator(AddGroups.GENERATORS, AddItem.VIRTUAL_PLANT, BugCrafter.TYPE,
            recipe(AddItem.BISILVER,setC(AddItem.TECH_CORE,8),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.SMELERY_CORE,8),AddItem.BISILVER,
                    AddItem.LDIGITIZER,AddItem.BISILVER,AddItem.NETHER_PLANT,AddItem.NETHER_PLANT,AddItem.BISILVER,AddItem.LDIGITIZER,
                    AddItem.BISILVER,setC(AddItem.LMOTOR,2),setC(AddItem.CHIP_CORE,2),setC(AddItem.LSINGULARITY,3),setC(AddItem.LMOTOR,2),AddItem.BISILVER,
                    AddItem.BISILVER,setC(AddItem.LENGINE,2),setC(AddItem.VIRTUAL_SPACE,4),setC(AddItem.CHIP_CORE,2),setC(AddItem.LENGINE,2),AddItem.BISILVER,
                    AddItem.LIOPORT,AddItem.BISILVER,AddItem.END_PLANT,AddItem.END_PLANT,AddItem.BISILVER,AddItem.LIOPORT,
                    AddItem.BISILVER,setC(AddItem.MASS_CORE,8),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.METAL_CORE,8),AddItem.BISILVER), 1, 129_600, 5400,
            new LinkedHashMap<>(){{
                put(mkl("MELON_SEEDS"),mkl("3MELON","3MELON_SEEDS"));
                put(mkl("PUMPKIN_SEEDS"),mkl("3PUMPKIN","3PUMPKIN_SEEDS"));
                put(mkl("BEETROOT_SEEDS"),mkl("9BEETROOT","6BEETROOT_SEEDS"));
                put(mkl("WHEAT_SEEDS"),mkl("9WHEAT","6WHEAT_SEEDS"));
                put(mkl("CARROT"),mkl("9CARROT"));
                put(mkl("POTATO"),mkl("9POTATO"));
                put(mkl("SWEET_BERRIES"),mkl("9SWEET_BERRIES"));
                put(mkl("SUGAR_CANE"),mkl("9SUGAR_CANE"));
                put(mkl("OAK_SAPLING"),mkl("9OAK_SAPLING","18OAK_LOG","6APPLE","9OAK_LEAVES","6STICK"));
                put(mkl("BIRCH_SAPLING"),mkl("9BIRCH_SAPLING","18BIRCH_LOG","6APPLE","9BIRCH_LEAVES","6STICK"));
                put(mkl("SPRUCE_SAPLING"),mkl("9SPRUCE_SAPLING","18SPRUCE_LOG","6APPLE","9SPRUCE_LEAVES","6STICK"));
                put(mkl("DARK_OAK_SAPLING"),mkl("9DARK_OAK_SAPLING","18DARK_OAK_LOG","6APPLE","9DARK_OAK_LEAVES","6STICK"));
                put(mkl("JUNGLE_SAPLING"),mkl("9JUNGLE_SAPLING","18JUNGLE_LOG","6APPLE","9JUNGLE_LEAVES","6STICK"));
                put(mkl("ACACIA_SAPLING"),mkl("9ACACIA_SAPLING","18ACACIA_LOG","6APPLE","9ACACIA_LEAVES","6STICK"));
                put(mkl("MANGROVE_PROPAGULE"),mkl("9MANGROVE_PROPAGULE","18MANGROVE_LOG","9MANGROVE_LEAVES"));
                put(mkl("CHERRY_SAPLING"),mkl("9CHERRY_SAPLING","18CHERRY_LOG","9CHERRY_LEAVES"));
                put(mkl("NETHER_WART"),mkl("12NETHER_WART"));
                put(mkl("NETHER_SPROUTS"),mkl("12NETHER_SPROUTS"));
                put(mkl("CRIMSON_FUNGUS"),mkl("6CRIMSON_FUNGUS","18CRIMSON_STEM","6SHROOMLIGHT","12NETHER_WART_BLOCK"));
                put(mkl("WARPED_FUNGUS"),mkl("6WARPED_FUNGUS","18WARPED_STEM","6SHROOMLIGHT","12WARPED_WART_BLOCK"));
                put(mkl("CHORUS_FLOWER"),mkl("6CHORUS_FLOWER","24CHORUS_FRUIT"));
                put(mkl("LILY_PAD"),mkl("9LILY_PAD"));
            }})
            .register();
    public static final SlimefunItem STACKMGENERATOR=new StackMGenerator(AddGroups.GENERATORS, AddItem.STACKMGENERATOR,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),1,20_000_000,2_000,1)
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
    public static final SlimefunItem PORTAL_CORE=new PortalCore(AddGroups.SPACE,AddItem.PORTAL_CORE,BugCrafter.TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,AddItem.DIMENSIONAL_SHARD,AddItem.DIMENSIONAL_SHARD,AddItem.LFIELD,AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,"GPS_TRANSMITTER_2","GPS_TRANSMITTER_2",AddItem.STAR_GOLD_INGOT,AddItem.LFIELD,
                    AddItem.DIMENSIONAL_SHARD,"GPS_TRANSMITTER_2",AddItem.NOLOGIC,AddItem.HYPER_LINK,"GPS_TRANSMITTER_2",AddItem.DIMENSIONAL_SHARD,
                    AddItem.DIMENSIONAL_SHARD,"GPS_TRANSMITTER_2",AddItem.LENGINE,AddItem.LDIGITIZER,"GPS_TRANSMITTER_2",AddItem.DIMENSIONAL_SHARD,
                    AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,"GPS_TRANSMITTER_2","GPS_TRANSMITTER_2",AddItem.STAR_GOLD_INGOT,AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,AddItem.DIMENSIONAL_SHARD,AddItem.DIMENSIONAL_SHARD,AddItem.LFIELD,AddItem.LFIELD),"portal.core",
            MultiBlockTypes.PORTAL_TYPE)
            .register();
    public static final SlimefunItem PORTAL_FRAME=new MultiPart(AddGroups.SPACE,AddItem.PORTAL_FRAME,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"portal.part")
            .register();
    public static final SlimefunItem SOLAR_REACTOR=new SolarReactorCore(AddGroups.SPACE,AddItem.SOLAR_REACTOR,BugCrafter.TYPE,
            recipe(AddItem.LPLATE,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.LPLATE,
                    AddItem.STAR_GOLD_INGOT,AddItem.TECH_CORE,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.MASS_CORE,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,"2GPS_TRANSMITTER_4",AddItem.SMELERY_CORE,AddItem.METAL_CORE,"2GPS_TRANSMITTER_4",AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,"2GPS_TRANSMITTER_4",AddItem.MASS_CORE,AddItem.TECH_CORE,"2GPS_TRANSMITTER_4",AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.METAL_CORE,AddItem.CHIP_CORE,AddItem.CHIP_CORE,AddItem.SMELERY_CORE,AddItem.STAR_GOLD_INGOT,
                    AddItem.LPLATE,AddItem.STAR_GOLD_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem.STAR_GOLD_INGOT,AddItem.LPLATE)
            ,"solar.core",MultiBlockTypes.SOLAR_TYPE,80_000,2_000_000,
            mkMp(mkP(   mkl(AddItem.METAL_CORE)  ,
                            mkl(
                                    setC(AddItem.STAR_GOLD_INGOT,12),
                                    AddUtils.probItemStackFactory( AddItem.LSINGULARITY,73),
                                    AddUtils.randAmountItemFactory(AddItem.STAR_GOLD,39,127),
                                    AddUtils.randAmountItemFactory(AddItem.ATOM_INGOT,63,99),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(setC(AddItem.PALLADIUM_INGOT,3),setC(AddItem.PLATINUM_INGOT,3),setC(AddItem.CADMIUM_INGOT,3),setC(AddItem.BISMUTH_INGOT,3)),
                                            Utils.list(37,9,3,1)
                                    )
                            )
                    ),72,
                    mkP(   mkl(AddItem.SMELERY_CORE)  ,

                            mkl(
                                    setC(AddItem.ABSTRACT_INGOT,64),
                                    AddUtils.probItemStackFactory( AddItem.LSINGULARITY,12),
                                    AddUtils.randAmountItemFactory(AddItem.DIMENSIONAL_SHARD,99,127),
                                    AddUtils.randAmountItemFactory(AddItem.STAR_GOLD,83,127),
                                    AddUtils.randAmountItemFactory(AddItem.ATOM_INGOT,37,51),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(setC(AddItem.PALLADIUM_INGOT,3),setC(AddItem.PLATINUM_INGOT,3),setC(AddItem.CADMIUM_INGOT,3),setC(AddItem.BISMUTH_INGOT,3)),
                                            Utils.list(2,59,13,24)
                                    )
                            )

                    ),72,
                    mkP(   mkl(AddItem.MASS_CORE)  ,

                            mkl(
                                    setC(AddItem.BUG,64),
                                    AddUtils.probItemStackFactory( AddItem.LSINGULARITY,97),
                                    AddUtils.randAmountItemFactory(AddItem.DIMENSIONAL_SHARD,73,127),
                                    AddUtils.randAmountItemFactory(AddItem.STAR_GOLD,39,63),
                                    AddUtils.randAmountItemFactory(AddItem.ATOM_INGOT,12,39),

                                    AddUtils.randItemStackFactory(
                                            Utils.list(setC(AddItem.PALLADIUM_INGOT,3),setC(AddItem.PLATINUM_INGOT,3),setC(AddItem.CADMIUM_INGOT,3),setC(AddItem.BISMUTH_INGOT,3)),
                                            Utils.list(40,33,22,29)
                                    )
                            )

                    ),72,
                    mkP(   mkl(AddItem.TECH_CORE)  ,

                            mkl(
                                    setC(AddItem.LPLATE,64),
                                    setC( AddItem.LSINGULARITY,2),
                                    AddUtils.randAmountItemFactory(AddItem.STAR_GOLD_INGOT,3,12),
                                    AddUtils.randAmountItemFactory(AddItem.ATOM_INGOT,92,127)
                            )

                    ),72
            ))
            .register();
    public static final SlimefunItem SOLAR_REACTOR_FRAME=new MultiPart(AddGroups.SPACE,AddItem.SOLAR_REACTOR_FRAME,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"solar.frame")
            .register();
    public static final SlimefunItem SOLAR_REACTOR_GLASS=new MultiPart(AddGroups.SPACE,AddItem.SOLAR_REACTOR_GLASS,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"solar.glass")
            .register();
    public static final SlimefunItem SOLAR_INPUT=new MultiIOPort(AddGroups.SPACE,AddItem.SOLAR_INPUT,RecipeType.ANCIENT_ALTAR,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.HYPER_LINK,AddItem.PARADOX,
                    AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT),"solar.frame",true,true)
            .register();
    public static final SlimefunItem SOLAR_OUTPUT=new MultiIOPort(AddGroups.SPACE,AddItem.SOLAR_OUTPUT,RecipeType.ANCIENT_ALTAR,
            recipe(AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.HYPER_LINK,AddItem.STAR_GOLD_INGOT,
                    AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX),"solar.frame",false,false)
            .register() ;
    public static final SlimefunItem TRANSMUTATOR_FRAME=new MultiPart(AddGroups.ADVANCED,AddItem.TRANSMUTATOR_FRAME,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"nuclear.frame")
            .register();
    public static final SlimefunItem TRANSMUTATOR_GLASS=new MultiPart(AddGroups.ADVANCED,AddItem.TRANSMUTATOR_GLASS,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"nuclear.glass")
            .register();
    public static final SlimefunItem TRANSMUTATOR_ROD=new MultiPart(AddGroups.ADVANCED,AddItem.TRANSMUTATOR_ROD,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),"nuclear.rod")
            .register();
    public static final  SlimefunItem TRANSMUTATOR=new Transmutator(AddGroups.ADVANCED, AddItem.TRANSMUTATOR,BugCrafter.TYPE,
            recipe(AddItem.TECH_CORE,AddItem.TRANSMUTATOR_ROD,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.TRANSMUTATOR_ROD,AddItem.MASS_CORE,
                    AddItem.TRANSMUTATOR_ROD,setC(AddItem.CHIP_CORE,2),setC(AddItem.LSINGULARITY,2),setC(AddItem.LSINGULARITY,2),setC(AddItem.CHIP_CORE,2),AddItem.TRANSMUTATOR_ROD,
                    AddItem.SPACE_PLATE,setC(AddItem.PAGOLD,4),"16NUCLEAR_REACTOR","16NUCLEAR_REACTOR",setC(AddItem.PAGOLD,4),AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,setC(AddItem.PAGOLD,4),"16NUCLEAR_REACTOR","16NUCLEAR_REACTOR",setC(AddItem.PAGOLD,4),AddItem.SPACE_PLATE,
                    AddItem.TRANSMUTATOR_ROD,setC(AddItem.CHIP_CORE,2),setC(AddItem.BISILVER,3),setC(AddItem.BISILVER,3),setC(AddItem.CHIP_CORE,2),AddItem.TRANSMUTATOR_ROD,
                    AddItem.METAL_CORE,AddItem.TRANSMUTATOR_ROD,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.TRANSMUTATOR_ROD,AddItem.SMELERY_CORE), "nuclear.core",
            MultiBlockTypes.NUCLEAR_REACTOR,750_000,20_000_000,
            mkMp(mkP(   mkl(setC(AddItem.ATOM_INGOT,64) )  ,
                        mkl(
                                setC(AddItem.ATOM_INGOT,128),
                                AddUtils.randItemStackFactory(
                                        Utils.list(
                                                AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,17,33),
                                                AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,13,40),
                                                AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,6,25),
                                                AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,20,24)
                                        ),
                                        Utils.list(1,1,1,1)
                                ),
                                AddUtils.randItemStackFactory(
                                        Utils.list(
                                                AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,1,4),
                                                AddUtils.randAmountItemFactory(AddItem.CERIUM,1,4),
                                                AddUtils.randAmountItemFactory(AddItem.MENDELEVIUM,1,4),
                                                AddUtils.randAmountItemFactory(AddItem.DYSPROSIUM,1,4),
                                                AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,1,4),
                                                AddUtils.randAmountItemFactory(AddItem.ANTIMONY_INGOT,1,4),
                                                AddUtils.randAmountItemFactory(AddItem.THALLIUM,1,4),
                                                AddUtils.randAmountItemFactory(AddItem.HYDRAGYRUM,1,4),
                                                AddUtils.randAmountItemFactory(AddItem.BORON,1,4)
                                        ),
                                        Utils.list(1,1,1,1,1,1,1,1,1)
                                )
                        )

                    ),12000,
                    mkP(   mkl(setC(AddItem.BISILVER,3) )  ,

                            mkl(
                                    setC(AddItem.ATOM_INGOT,48),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,15,31),
                                                    AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,13,29),
                                                    AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,23,37),
                                                    AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,12,24)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,5,23),
                                                    AddUtils.randAmountItemFactory(AddItem.CERIUM,11,21),
                                                    AddUtils.randAmountItemFactory(AddItem.MENDELEVIUM,7,17),
                                                    AddUtils.randAmountItemFactory(AddItem.DYSPROSIUM,3,24),
                                                    AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,10,14),
                                                    AddUtils.randAmountItemFactory(AddItem.ANTIMONY_INGOT,6,11),
                                                    AddUtils.randAmountItemFactory(AddItem.THALLIUM,9,10),
                                                    AddUtils.randAmountItemFactory(AddItem.HYDRAGYRUM,2,8),
                                                    AddUtils.randAmountItemFactory(AddItem.BORON,1,9)
                                            ),
                                            Utils.list(15,14,13,12,11,10,9,8,7)
                                    )
                            )

                    ),12500,
                    mkP(   mkl(setC(AddItem.PAGOLD,3) )  ,

                            mkl(
                                    setC(AddItem.ATOM_INGOT,48),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,17,23),
                                                    AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,11,16),
                                                    AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,12,23),
                                                    AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,23,29)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,1,9),
                                                    AddUtils.randAmountItemFactory(AddItem.CERIUM,3,24),
                                                    AddUtils.randAmountItemFactory(AddItem.MENDELEVIUM,7,12),
                                                    AddUtils.randAmountItemFactory(AddItem.DYSPROSIUM,12,15),
                                                    AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,23,26),
                                                    AddUtils.randAmountItemFactory(AddItem.ANTIMONY_INGOT,1,3),
                                                    AddUtils.randAmountItemFactory(AddItem.THALLIUM,13,19),
                                                    AddUtils.randAmountItemFactory(AddItem.HYDRAGYRUM,11,23),
                                                    AddUtils.randAmountItemFactory(AddItem.BORON,6,9)
                                            ),
                                            Utils.list(12,11,10,9,8,7,15,14,13)
                                    )
                            )

                    ),12500,
                    mkP(   mkl(setC(AddItem.PLATINUM_INGOT,24) )  ,

                            mkl(
                                    setC(AddItem.STAR_GOLD_INGOT,128),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,23,33),
                                                    AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,32,40),
                                                    AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,23,25),
                                                    AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,20,28)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,4,22),
                                                    AddUtils.randAmountItemFactory(AddItem.CERIUM,6,9),
                                                    AddUtils.randAmountItemFactory(AddItem.MENDELEVIUM,8,11),
                                                    AddUtils.randAmountItemFactory(AddItem.DYSPROSIUM,1,20),
                                                    AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,2,17),
                                                    AddUtils.randAmountItemFactory(AddItem.ANTIMONY_INGOT,10,14),
                                                    AddUtils.randAmountItemFactory(AddItem.THALLIUM,11,24),
                                                    AddUtils.randAmountItemFactory(AddItem.HYDRAGYRUM,13,19),
                                                    AddUtils.randAmountItemFactory(AddItem.BORON,7,17)
                                            ),
                                            Utils.list(9,8,7,15,14,13,12,11,10)
                                    )
                            )

                    ),12500,
                    mkP(   mkl(setC(AddItem.CADMIUM_INGOT,24) )  ,

                            mkl(
                                    setC(AddItem.STAR_GOLD_INGOT,128),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,17,33),
                                                    AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,13,40),
                                                    AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,6,25),
                                                    AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,20,24)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,3,21),
                                                    AddUtils.randAmountItemFactory(AddItem.CERIUM,6,17),
                                                    AddUtils.randAmountItemFactory(AddItem.MENDELEVIUM,7,13),
                                                    AddUtils.randAmountItemFactory(AddItem.DYSPROSIUM,2,31),
                                                    AddUtils.randAmountItemFactory(AddItem.MOLYBDENUM,12,14),
                                                    AddUtils.randAmountItemFactory(AddItem.ANTIMONY_INGOT,10,24),
                                                    AddUtils.randAmountItemFactory(AddItem.THALLIUM,7,22),
                                                    AddUtils.randAmountItemFactory(AddItem.HYDRAGYRUM,14,29),
                                                    AddUtils.randAmountItemFactory(AddItem.BORON,11,23)
                                            ),
                                            Utils.list(1,1,1,1,1,1,1,1,1)
                                    )
                            )

                    ),12500
            )
            )
            .register();



    //
    //manuals
    public static final SlimefunItem MANUAL_CORE=new MaterialItem(AddGroups.MANUAL,AddItem.MANUAL_CORE,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("GRANITE","GRANITE","GRANITE",
                    "DIORITE",AddItem.BUG,"DIORITE",
                    "ANDESITE","ANDESITE","ANDESITE"),null).setOutput(setC(AddItem.MANUAL_CORE,8))
            .register();
    public static final SlimefunItem CRAFT_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.CRAFT_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,"CRAFTING_TABLE",AddItem.BUG,
                    null,"CRAFTING_TABLE",null,
                    null,null,null),0,0, BukkitUtils.VANILLA_CRAFTTABLE)
            .register();
    public static final SlimefunItem FURNACE_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.FURNACE_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.MANUAL_CORE,"HEATING_COIL",AddItem.MANUAL_CORE,
                    "HEATING_COIL","FURNACE","HEATING_COIL",
                    AddItem.BUG,AddItem.BUG,AddItem.BUG),0,0,BukkitUtils.VANILLA_FURNACE)
            .register();
    public static final SlimefunItem ENHANCED_CRAFT_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.ENHANCED_CRAFT_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,"CRAFTING_TABLE",null,
                    null,AddItem.BUG,null,
                    null,AddItem.BUG,null),0,0,RecipeType.ENHANCED_CRAFTING_TABLE)
            .register();
    public static final SlimefunItem GRIND_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.GRIND_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.MANUAL_CORE,null,
                    null,"OAK_FENCE",null,
                    null,"DISPENSER",null),0,0,RecipeType.GRIND_STONE)
            .register();
    public static final SlimefunItem ARMOR_FORGE_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.ARMOR_FORGE_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.MANUAL_CORE,null,
                    null,"ANVIL",null,
                    null,"DISPENSER",null),0,0,RecipeType.ARMOR_FORGE)
            .register();
    public static final SlimefunItem ORE_CRUSHER_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.ORE_CRUSHER_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.MANUAL_CORE,null,
                    null,"OAK_FENCE",null,
                    "IRON_INGOT","DISPENSER","IRON_INGOT"),0,0,RecipeType.ORE_CRUSHER)
            .register();
    public static final SlimefunItem COMPRESSOR_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.COMPRESSOR_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.MANUAL_CORE,null,
                    null,"OAK_FENCE",null,
                    "PISTON","DISPENSER","PISTON"),0,0,RecipeType.COMPRESSOR)
            .register();
    public static final SlimefunItem PRESSURE_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.PRESSURE_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("GLASS","CAULDRON","GLASS",
                    "PISTON",AddItem.MANUAL_CORE,"PISTON",
                    "PISTON","DISPENSER","PISTON"),0,0,RecipeType.PRESSURE_CHAMBER)
            .register();
    public static final SlimefunItem MAGIC_WORKBENCH_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.MAGIC_WORKBENCH_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,
                    null,AddItem.BUG,null,
                    "BOOKSHELF","CRAFTING_TABLE","DISPENSER"),0,0,RecipeType.MAGIC_WORKBENCH)
            .register();
    public static final SlimefunItem ORE_WASHER_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.ORE_WASHER_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.MANUAL_CORE,"DISPENSER",AddItem.MANUAL_CORE,
            AddItem.BUG,"OAK_FENCE",AddItem.BUG,
            AddItem.MANUAL_CORE,"CAULDRON",AddItem.MANUAL_CORE),0,0,RecipeType.ORE_WASHER)
            .register();
    public static final SlimefunItem GOLD_PAN_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.GOLD_PAN_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.MANUAL_CORE,null,AddItem.MANUAL_CORE,
                    AddItem.BUG,"OAK_TRAPDOOR",AddItem.BUG,
                    AddItem.MANUAL_CORE,"CAULDRON",AddItem.MANUAL_CORE),0,0,RecipeType.GOLD_PAN)
            .register();
    public static final SlimefunItem ANCIENT_ALTAR_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.ANCIENT_ALTAR_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("ANCIENT_PEDESTAL","ANCIENT_PEDESTAL","ANCIENT_PEDESTAL",
                    "ANCIENT_PEDESTAL","ANCIENT_ALTAR","ANCIENT_PEDESTAL",
                    "ANCIENT_PEDESTAL","ANCIENT_PEDESTAL","ANCIENT_PEDESTAL"),0,0,RecipeType.ANCIENT_ALTAR)
            .register();
    public static final SlimefunItem SMELTERY_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.SMELTERY_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.MANUAL_CORE,"NETHER_BRICK_FENCE",AddItem.MANUAL_CORE,
                    "NETHER_BRICKS","DISPENSER","NETHER_BRICKS",
                     AddItem.MANUAL_CORE,"IGNITION_CHAMBER",AddItem.MANUAL_CORE),0,0,RecipeType.SMELTERY)
            .register();
    public static final SlimefunItem CRUCIBLE_MANUAL=new ManualMachine(AddGroups.MANUAL,AddItem.CRUCIBLE_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("TERRACOTTA",AddItem.MANUAL_CORE,"TERRACOTTA",
                    "TERRACOTTA",AddItem.BUG,"TERRACOTTA",
                    "TERRACOTTA","FLINT_AND_STEEL","TERRACOTTA"),0,0,()->{
                        return RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItems.ELECTRIFIED_CRUCIBLE.getItem());
                })
            .register();
    public static final SlimefunItem PULVERIZER_MANUAL=new ManualMachine(AddGroups.MANUAL,AddItem.PULVERIZER_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,"ELECTRIC_ORE_GRINDER",null,
                    "LEAD_INGOT",AddItem.BUG,"LEAD_INGOT",
                    "MEDIUM_CAPACITOR","HEATING_COIL","MEDIUM_CAPACITOR"),0,0,()->{
        //keep a question,if we get 铸锭机 recipe.
                        return RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItems.ELECTRIC_INGOT_PULVERIZER.getItem());
            })
            .register();
    public static final SlimefunItem MULTICRAFTTABLE_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.MULTICRAFTTABLE_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,AddItem.MANUAL_CORE,"OUTPUT_CHEST",
                    AddItem.MANUAL_CORE,AddItem.ANCIENT_ALTAR_MANUAL,"CRAFTING_TABLE",
                    "BOOKSHELF","CRAFTING_TABLE","DISPENSER"),0,0,RecipeType.ENHANCED_CRAFTING_TABLE,RecipeType.MAGIC_WORKBENCH,RecipeType.ANCIENT_ALTAR)
            .register();
    public static final SlimefunItem TABLESAW_MANUAL=new ManualMachine(AddGroups.MANUAL,AddItem.TABLESAW_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,
                    "SMOOTH_STONE_SLAB","STONECUTTER","SMOOTH_STONE_SLAB",
                    AddItem.MANUAL_CORE,"IRON_BLOCK",AddItem.MANUAL_CORE),0,0,()->{
                        return RecipeSupporter.MULTIBLOCK_RECIPES.get(SlimefunItems.TABLE_SAW.getItem());
                 })
            .register();
    public static final SlimefunItem COMPOSTER=new ManualMachine(AddGroups.MANUAL,AddItem.COMPOSTER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("OAK_SLAB",AddItem.MANUAL_CORE,"OAK_SLAB",
                    "OAK_SLAB",AddItem.MANUAL_CORE,"OAK_SLAB",
                    "OAK_SLAB","CAULDRON","OAK_SLAB"),0,0,()->{
                return RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItems.COMPOSTER.getItem());
            })
            .register();
    public static final SlimefunItem MULTIMACHINE_MANUAL=new ManualMachine(AddGroups.MANUAL,AddItem.MULTIMACHINE_MANUAL,BugCrafter.TYPE,
            recipe(null,null,AddItem.LFIELD,AddItem.LFIELD,null,null,
                    null,AddItem.LFIELD,"HEATED_PRESSURE_CHAMBER_2","HEATED_PRESSURE_CHAMBER_2",AddItem.LFIELD,null,
                    AddItem.LFIELD,"FOOD_COMPOSTER_2",AddItem.LENGINE,AddItem.LENGINE,"FOOD_COMPOSTER_2",AddItem.LFIELD,
                    AddItem.LFIELD,"FOOD_FABRICATOR_2",AddItem.LENGINE,AddItem.LENGINE,"FOOD_FABRICATOR_2",AddItem.LFIELD,
                    null,AddItem.LFIELD,"HEATED_PRESSURE_CHAMBER_2","HEATED_PRESSURE_CHAMBER_2",AddItem.LFIELD,null,
                    null,null,AddItem.LFIELD,AddItem.LFIELD,null,null),0,0,()->{
                List<MachineRecipe> recipelist=new ArrayList<>();
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItems.HEATED_PRESSURE_CHAMBER.getItem()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItems.REFINERY.getItem()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItems.FOOD_COMPOSTER.getItem()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItems.FOOD_FABRICATOR.getItem()));
                return recipelist;
            })
            .register();
    public static final SlimefunItem MULTIBLOCK_MANUAL=new MultiBlockManual(AddGroups.MANUAL,AddItem.MULTIBLOCK_MANUAL,BugCrafter.TYPE,
            recipe(AddItem.SPACE_PLATE,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.SPACE_PLATE,
                    AddItem.BUG,AddItem.LFIELD,"OAK_FENCE","OAK_FENCE",AddItem.LFIELD,AddItem.BUG,
                    AddItem.BUG,AddItem.LCRAFT,AddItem.LSINGULARITY,AddItem.CHIP_CORE,AddItem.LCRAFT,AddItem.BUG,
                    AddItem.BUG,AddItem.WORLD_FEAT,AddItem.VIRTUAL_SPACE,AddItem.MANUAL_CORE,AddItem.WORLD_FEAT,AddItem.BUG,
                    AddItem.BUG,AddItem.LFIELD,"DISPENSER","DISPENSER",AddItem.LFIELD,AddItem.BUG,
                    AddItem.SPACE_PLATE,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.SPACE_PLATE),0,0,null)
            .register();




    //cargo items
    public static final SlimefunItem CARGO_PART=new MaterialItem(AddGroups.CARGO,AddItem.CARGO_PART,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("CARGO_NODE","CARGO_NODE_OUTPUT","CARGO_NODE",AddItem.FALSE_,AddItem.LOGIGATE,AddItem.TRUE_,
                    "CARGO_NODE","CARGO_NODE_INPUT","CARGO_NODE"),null)
            .setOutput(setC(AddItem.CARGO_PART,13)).register();
    public static final SlimefunItem CARGO_CONFIG=new ConfigCard(AddGroups.CARGO,AddItem.CARGO_CONFIG,RecipeType.ANCIENT_ALTAR,
            recipe(AddItem.PARADOX,AddItem.CARGO_PART,AddItem.PARADOX,AddItem.NOLOGIC,AddItem.BUG,AddItem.LOGIC,
                    AddItem.PARADOX,AddItem.CARGO_PART,AddItem.PARADOX))
            .register();
    public static final SlimefunItem CARGO_CONFIGURATOR=new CargoConfigurator(AddGroups.CARGO,AddItem.CARGO_CONFIGURATOR,BugCrafter.TYPE,
            recipe(setC(AddItem.BUG,3),AddItem.PARADOX,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.PARADOX,setC(AddItem.BUG,3),
                    AddItem.PARADOX,"HOPPER",setC(AddItem.ABSTRACT_INGOT,2),setC(AddItem.ABSTRACT_INGOT,2),"HOPPER",AddItem.PARADOX,
                    AddItem.CHIP_INGOT,setC(AddItem.ABSTRACT_INGOT,2),"FLETCHING_TABLE","LOOM",setC(AddItem.ABSTRACT_INGOT,2),AddItem.CHIP_INGOT,
                    AddItem.CHIP_INGOT,setC(AddItem.ABSTRACT_INGOT,2),"CARTOGRAPHY_TABLE","SMITHING_TABLE",setC(AddItem.ABSTRACT_INGOT,2),AddItem.CHIP_INGOT,
                    AddItem.PARADOX,"HOPPER",setC(AddItem.ABSTRACT_INGOT,2),setC(AddItem.ABSTRACT_INGOT,2),"HOPPER",AddItem.PARADOX,
                    setC(AddItem.BUG,3),AddItem.PARADOX,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.PARADOX,setC(AddItem.BUG,3)))
            .register();
    public static final SlimefunItem ADV_TRASH=new TrashCan(AddGroups.CARGO,AddItem.ADV_TRASH,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("TRASH_CAN_BLOCK",AddItem.LOGIC,"TRASH_CAN_BLOCK",AddItem.LOGIC,"COAL_GENERATOR",AddItem.LOGIC,
                    "TRASH_CAN_BLOCK",AddItem.LOGIC,"TRASH_CAN_BLOCK"))
            .register();
    public static final SlimefunItem SIMPLE_CARGO=new AdjacentCargo(AddGroups.CARGO,AddItem.SIMPLE_CARGO,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.PARADOX,"HOPPER",AddItem.PARADOX,
                    AddItem.PARADOX,AddItem.CARGO_PART ,AddItem.PARADOX,
                    AddItem.PARADOX,AddItem.PARADOX,AddItem.PARADOX),
            list(AddUtils.getInfoShow("&f机制","")))
            .register();
    public static final SlimefunItem REMOTE_CARGO=new RemoteCargo(AddGroups.CARGO,AddItem.REMOTE_CARGO,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.PARADOX,AddItem.HYPER_LINK,AddItem.PARADOX
                    ,"CARGO_NODE",AddItem.CARGO_PART,"CARGO_NODE",
                    AddItem.PARADOX,AddItem.PARADOX,AddItem.PARADOX),
            null)
            .register();
    public static final SlimefunItem LINE_CARGO=new LineCargo(AddGroups.CARGO,AddItem.LINE_CARGO,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.PARADOX,AddItem.LBOOLIZER,AddItem.PARADOX,
                    "GPS_TRANSMITTER_2",AddItem.CARGO_PART,"GPS_TRANSMITTER_2",
                    AddItem.PARADOX,AddItem.LDIGITIZER,AddItem.PARADOX),null)
            .register();
    public static final  SlimefunItem BISORTER=new BiSorter(AddGroups.CARGO, AddItem.BISORTER,BugCrafter.TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.CARGO_PART,AddItem.CARGO_PART,AddItem.PARADOX,null,
                    setC(AddItem.PARADOX,2),AddItem.LBOOLIZER,AddItem.LOGIGATE,"HOPPER",AddItem.LBOOLIZER,setC(AddItem.PARADOX,2),
                    setC(AddItem.PARADOX,2),AddItem.LBOOLIZER,AddItem.LSCHEDULER,AddItem.LOGIGATE,AddItem.LBOOLIZER,setC(AddItem.PARADOX,2),
                    null,AddItem.PARADOX,AddItem.CARGO_PART,AddItem.CARGO_PART,AddItem.PARADOX,null))
            .register();
    public static final SlimefunItem QUARSORTER=new QuarSorter(AddGroups.CARGO,AddItem.QUARSORTER,BugCrafter.TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.LFIELD,AddItem.LFIELD,AddItem.PARADOX,null,
                    setC(AddItem.PARADOX,2),AddItem.CARGO_PART,"2HOPPER",setC(AddItem.LOGIGATE,2),AddItem.CARGO_PART,setC(AddItem.PARADOX,2),
                    setC(AddItem.PARADOX,2),AddItem.CARGO_PART,AddItem.BISORTER,"2HOPPER",AddItem.CARGO_PART,setC(AddItem.PARADOX,2),
                    null,AddItem.PARADOX,AddItem.LFIELD,AddItem.LFIELD,AddItem.PARADOX,null
                    ))
            .register();
    public static final SlimefunItem OCTASORTER=new OctaSorter(AddGroups.CARGO,AddItem.OCTASORTER,BugCrafter.TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,null,
                    setC(AddItem.PARADOX,2),"HOPPER",setC(AddItem.LBOOLIZER,4),AddItem.LSCHEDULER,"HOPPER",setC(AddItem.PARADOX,2),
                    setC(AddItem.PARADOX,2),"HOPPER",AddItem.QUARSORTER,setC(AddItem.LBOOLIZER,4),"HOPPER",setC(AddItem.PARADOX,2),
                    null,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,null))
            .register();
    public static final SlimefunItem STORAGE_OPERATOR=new StorageOperator(AddGroups.SINGULARITY,AddItem.STORAGE_OPERATOR,RecipeType.MAGIC_WORKBENCH,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.LIOPORT,AddItem.PARADOX,
                    AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT))
            .register();
    public static final SlimefunItem ADV_ADJACENT_CARGO=new AdjacentCargoPlus(AddGroups.CARGO,AddItem.ADV_ADJACENT_CARGO,RecipeType.MAGIC_WORKBENCH,
            recipe(AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX,AddItem.ABSTRACT_INGOT,AddItem.SIMPLE_CARGO,AddItem.ABSTRACT_INGOT,
                    AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX),null)
            .register();
    public static final SlimefunItem ADV_REMOTE_CARGO=new RemoteCargoPlus(AddGroups.CARGO,AddItem.ADV_REMOTE_CARGO,RecipeType.MAGIC_WORKBENCH,
            recipe(AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX,AddItem.ABSTRACT_INGOT,AddItem.REMOTE_CARGO,AddItem.ABSTRACT_INGOT,
                    AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX),null)
            .register();
    public static final SlimefunItem ADV_LINE_CARGO=new LineCargoPlus(AddGroups.CARGO,AddItem.ADV_LINE_CARGO,RecipeType.MAGIC_WORKBENCH,
            recipe(AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX,AddItem.ABSTRACT_INGOT,AddItem.LINE_CARGO,AddItem.ABSTRACT_INGOT,
                    AddItem.PARADOX,"CARGO_MOTOR",AddItem.PARADOX),null)
            .register();
    public static final  SlimefunItem REDSTONE_ADJACENT_CARGO=new RedstoneAdjacentCargo(AddGroups.CARGO, AddItem.REDSTONE_ADJACENT_CARGO,RecipeType.MAGIC_WORKBENCH,
            recipe(AddItem.ADV_ADJACENT_CARGO,AddItem.PARADOX,AddItem.ADV_ADJACENT_CARGO,AddItem.LOGIGATE,AddItem.REDSTONE_ENGINE,AddItem.LOGIGATE,
                    AddItem.ADV_ADJACENT_CARGO,AddItem.LOGIGATE,AddItem.ADV_ADJACENT_CARGO), null).setOutput(setC(AddItem.REDSTONE_ADJACENT_CARGO,4))
            .register();
    public static final SlimefunItem CHIP_ADJ_CARGO=new ChipAdjacentCargo(AddGroups.CARGO,AddItem.CHIP_ADJ_CARGO,RecipeType.MAGIC_WORKBENCH,
            recipe(AddItem.ADV_ADJACENT_CARGO,AddItem.PARADOX,AddItem.ADV_ADJACENT_CARGO,AddItem.LOGIGATE,AddItem.CHIP_CORE,AddItem.LOGIGATE,
                    AddItem.ADV_ADJACENT_CARGO,AddItem.LOGIGATE,AddItem.ADV_ADJACENT_CARGO),null).setOutput(setC(AddItem.CHIP_ADJ_CARGO,4))
            .register();
    public static final SlimefunItem RESETTER=new StorageCleaner(AddGroups.SINGULARITY,AddItem.RESETTER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("CRAFTING_TABLE",AddItem.ABSTRACT_INGOT,"CRAFTING_TABLE",AddItem.ABSTRACT_INGOT,"TRASH_CAN_BLOCK",AddItem.ABSTRACT_INGOT,
                    "CRAFTING_TABLE",AddItem.ABSTRACT_INGOT,"CRAFTING_TABLE"))
            .register();
    public static final SlimefunItem STORAGE_SINGULARITY=new Singularity(AddGroups.SINGULARITY, AddItem.STORAGE_SINGULARITY, RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT,
            AddItem.PARADOX,"CHEST",AddItem.PARADOX,
            AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT))
            .register();
    public static final SlimefunItem QUANTUM_LINK=new QuantumLink(AddGroups.SINGULARITY,AddItem.QUANTUM_LINK,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.PARADOX,AddItem.DIMENSIONAL_SHARD,AddItem.PARADOX,AddItem.DIMENSIONAL_SHARD,AddItem.LSINGULARITY,AddItem.DIMENSIONAL_SHARD,
                    AddItem.ABSTRACT_INGOT,AddItem.BUG,AddItem.ABSTRACT_INGOT))
            .register();
    public static final  SlimefunItem INPORT=new InputPort(AddGroups.SINGULARITY, AddItem.INPORT,BugCrafter.TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,"CHEST","CHEST",AddItem.PARADOX,null,
                    null,"CHEST",AddItem.UNIQUE,AddItem.EXISTE,"CHEST",null,
                    null,"CHEST",AddItem.LOGIC,AddItem.NOLOGIC,"CHEST",null,
                    null,AddItem.PARADOX,"CHEST","CHEST",AddItem.PARADOX,null
                    ))
            .register();
    public static final  SlimefunItem OUTPORT=new OutputPort(AddGroups.SINGULARITY, AddItem.OUTPORT,BugCrafter.TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null,
                    null,"CHEST",AddItem.EXISTE,AddItem.UNIQUE,"CHEST",null,
                    null,"CHEST",AddItem.NOLOGIC,AddItem.LOGIC,"CHEST",null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null
                    ))
            .register();
    public static final SlimefunItem IOPORT=new IOPort(AddGroups.SINGULARITY,AddItem.IOPORT,BugCrafter.TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null,
                    null,"CHEST",AddItem.PARADOX,AddItem.PARADOX,"CHEST",null,
                    null,"CHEST",AddItem.PARADOX,AddItem.PARADOX,"CHEST",null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null
            ))
            .register();







    //special
    public static final SlimefunItem HEAD_ANALYZER= new HeadAnalyzer(AddGroups.SPECIAL,AddItem.HEAD_ANALYZER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"PLAYER_HEAD",BUG,"PLAYER_HEAD",null,null,null)
    ).register();
    public static final SlimefunItem RECIPE_LOGGER=new RegisteryLogger(AddGroups.SPECIAL,AddItem.RECIPE_LOGGER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"CRAFTING_TABLE",BUG,Material.WRITABLE_BOOK,null,null))
            .register();
    public static final SlimefunItem RADIATION_CLEAR=new CustomProps(AddGroups.SPECIAL, AddItem.RADIATION_CLEAR, RecipeType.NULL,
            AddUtils.NULL_RECIPE.clone(), null) {
                public void onClickAction(PlayerRightClickEvent event) {
                    Optional<Block> b= event.getClickedBlock();
                    if(b.isPresent()){
                        RadiationRegion.removeRadiation(b.get().getLocation());
                    }
                }
            }
            .register();
    public static final SlimefunItem ANTIMASS_CLEAR=new CustomProps(AddGroups.SPECIAL, AddItem.ANTIMASS_CLEAR, RecipeType.NULL,
            AddUtils.NULL_RECIPE.clone(), null) {
        public void onClickAction(PlayerRightClickEvent event) {
            if(ANTIMASS instanceof SpreadBlock sb){
                sb.getSpreadOwner().clear();
                sb.getSpreadTicker().clear();
            }
            AddUtils.sendMessage(event.getPlayer(),"&a反概念物质已成功清除");
        }
    }
            .register();

    //final
    public static final  SlimefunItem FINAL_LASER=new Laser(AddGroups.BEYOND, AddItem.FINAL_LASER,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")), 8_000_000,1_200_000,"final.sub")
            .register();
    public static final SlimefunItem FINAL_BASE=new MultiPart(AddGroups.BEYOND,AddItem.FINAL_BASE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),"final.base")
            .register();
    public static final SlimefunItem FINAL_ALTAR=new FinalAltarCore(AddGroups.BEYOND,AddItem.FINAL_ALTAR,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),"final.core")
            .register();
    public static final  SlimefunItem FINAL_SEQUENTIAL=new FinalSequenceConstructor(AddGroups.BEYOND, AddItem.FINAL_SEQUENTIAL,BugCrafter.TYPE,
            recipe(AddItem.STACKFRAME,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.STACKFRAME,
                    AddItem.PDCECDMD,setC(AddItem.SPACE_PLATE,2),AddItem.STORAGE_SINGULARITY,AddItem.STORAGE_SINGULARITY,setC(AddItem.SPACE_PLATE,2),AddItem.PDCECDMD,
                    setC(AddItem.SPACE_PLATE,2),AddItem.LIOPORT,AddItem.SEQ_CONSTRUCTOR,AddItem.SEQ_CONSTRUCTOR,AddItem.LIOPORT,setC(AddItem.SPACE_PLATE,2),
                    setC(AddItem.SPACE_PLATE,2),setC(AddItem.VIRTUAL_SPACE,32),setC(AddItem.LSINGULARITY,64),setC(AddItem.LSINGULARITY,64),setC(AddItem.VIRTUAL_SPACE,32),
                    AddItem.HGTLPBBI,setC(AddItem.SPACE_PLATE,2),setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.SPACE_PLATE,2),AddItem.HGTLPBBI,
                    AddItem.STACKFRAME,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.STACKFRAME), AddUtils.addGlow( new ItemStack(Material.FIRE_CHARGE)),            10240,114514,
            mkMp(
                    mkP(mkl(setC(AddItem.MASS_CORE,192),setC(AddItem.SMELERY_CORE,192),setC(AddItem.HGTLPBBI,128),
                            setC(AddItem.SINGULARITY,128),setC(AddItem.VIRTUAL_SPACE,128),setC(AddItem.PDCECDMD,128),
                            setC(AddItem.TECH_CORE,192),setC(AddItem.METAL_CORE,192)),mkl(setC(AddItem.FINAL_FRAME,7))),2,
                    mkP(mkl("1919810IRON_DUST","1919810GOLD_DUST","1919810COPPER_DUST",
                            "1919810TIN_DUST","1919810SILVER_DUST","1919810LEAD_DUST",
                            "1919810ALUMINUM_DUST","1919810ZINC_DUST","1919810MAGNESIUM_DUST"),mkl(AddItem.LOGIC_CORE)),2
            ))
            .register();
    public static final SlimefunItem FINAL_STONE_MG=new FinalMGenerator(AddGroups.BEYOND, AddItem.FINAL_STONE_MG,BugCrafter.TYPE,
            recipe(AddItem.STACKFRAME,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.STACKFRAME,
                    AddItem.PDCECDMD,setC(AddItem.MASS_CORE,16),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.MASS_CORE,16),AddItem.HGTLPBBI,
                    AddItem.PDCECDMD,AddItem.SPACE_PLATE,AddItem.NETHER_FEAT,AddItem.VIRTUAL_SPACE,AddItem.SPACE_PLATE,AddItem.HGTLPBBI,
                    AddItem.HGTLPBBI,AddItem.SPACE_PLATE,AddItem.FINAL_FRAME,AddItem.WORLD_FEAT,AddItem.SPACE_PLATE,AddItem.PDCECDMD,
                    AddItem.HGTLPBBI,setC(AddItem.MASS_CORE,16),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.MASS_CORE,16),AddItem.PDCECDMD,
                    AddItem.STACKFRAME,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.STACKFRAME),1,1_440_000,57_600,
            new LinkedHashMap<>(){{
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

    public static final SlimefunItem FINAL_MANUAL=new FinalManual(AddGroups.BEYOND,AddItem.FINAL_MANUAL,BugCrafter.TYPE,
            recipe(setC(AddItem.STACKFRAME,2),AddItem.CRAFT_MANUAL,AddItem.FURNACE_MANUAL,AddItem.ENHANCED_CRAFT_MANUAL,AddItem.GRIND_MANUAL,setC(AddItem.STACKFRAME,2),
                    AddItem.ORE_CRUSHER_MANUAL,null,setC(AddItem.LASER,2),setC(AddItem.LASER,2),null,AddItem.TABLESAW_MANUAL,
                    AddItem.PRESSURE_MANUAL,setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.MANUAL_CORE,64),setC(AddItem.LSINGULARITY,64),setC(AddItem.VIRTUAL_SPACE,16),AddItem.COMPRESSOR_MANUAL,
                    AddItem.CRUCIBLE_MANUAL,setC(AddItem.VIRTUAL_SPACE,16),AddItem.FINAL_FRAME,AddItem.FINAL_FRAME,setC(AddItem.VIRTUAL_SPACE,16),AddItem.SMELTERY_MANUAL,
                    AddItem.GOLD_PAN_MANUAL,null,setC(AddItem.LASER,2),setC(AddItem.LASER,2),null,AddItem.ANCIENT_ALTAR_MANUAL,
                    setC(AddItem.STACKFRAME,2),AddItem.MAGIC_WORKBENCH_MANUAL,AddItem.COMPOSTER,AddItem.ARMOR_FORGE_MANUAL,AddItem.ORE_WASHER_MANUAL,setC(AddItem.STACKFRAME,2)),0,0)
            .register();
    //TODO 完成反概念物质的扩散 变化和掉落
    public static final SlimefunItem ANTIMASS=new SpreadBlock(AddGroups.BEYOND,AddItem.ANTIMASS,STARSMELTERY,
            recipe(setC(AddItem.LOGIC_CORE,9),setC(AddItem.VIRTUAL_SPACE,64),"64ENERGIZED_CAPACITOR",setC(AddItem.PARADOX,64),setC(AddItem.FINAL_FRAME,3)),LOGIC_CORE,Material.COMMAND_BLOCK,Material.SCULK)
            .register();
    public static final  SlimefunItem FINAL_CONVERTOR=new FinalConvertor(AddGroups.BEYOND, AddItem.FINAL_CONVERTOR,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")), 240_000_000,100_000_000,
            AddUtils.randItemStackFactory(
                    Utils.list(
                            AddUtils.probItemStackFactory( setC(  AddItem.STAR_GOLD_INGOT,4),20),
                            AddUtils.probItemStackFactory(  setC(AddItem.ATOM_INGOT,2),10),
                            AddItem.ANTIMASS
                    ),
                    Utils.list(
                            4,3,1
                    )
            ))
            .register();
    public static final  SlimefunItem FINAL_STACKMACHINE=new FinalStackMachine(AddGroups.BEYOND, AddItem.FINAL_STACKMACHINE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")), Material.STONE,100,20_000_000,16)
            .register();
    public static final SlimefunItem FINAL_STACKMGENERATOR=new FinalStackMGenerator(AddGroups.BEYOND, AddItem.FINAL_STACKMGENERATOR,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),1,20_000_000,100,
            16)
            .register();


    public static final SlimefunItem TMP1=new MaterialItem(AddGroups.FUNCTIONAL,AddItem.TMP1,RecipeType.NULL,
            AddUtils.NULL_RECIPE.clone())
            .register();
    public static final SlimefunItem RESOLVE_FAILED=new MaterialItem(AddGroups.FUNCTIONAL, AddItem.RESOLVE_FAILED,RecipeType.NULL,
            AddUtils.NULL_RECIPE.clone(),null)
            .register();
    public static final SlimefunItem SHELL=new CustomProps(AddGroups.FUNCTIONAL, AddItem.SHELL, RecipeType.NULL,
            AddUtils.NULL_RECIPE.clone(), null) {
        @Override
        public void onClickAction(PlayerRightClickEvent event) {
            CommandShell.setup(event.getPlayer());
        }
    }
            .register();



//    public static final SlimefunItem CUSTOM1=register(new FIrstCustomItem(AddGroups.MATERIAL, AddItem.CUSTOM1,BugCrafter.TYPE,
//            recipe("COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
//                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT")));

//    public static  final  SlimefunItem SMG1=new SMGenerator(AddGroups.MATERIAL, AddItem.SMG1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),3,114514,0,
//               AddUtils.randItemStackFactory( new LinkedHashMap<>(){{
//                    put(Material.DIAMOND,1);
//                    put(new ItemStack(Material.BOOK,2),1);
//                    put(CUSTOM1,1);
//                    put("COPPER_DUST",1);
//                    put("EMERALD_ORE",1);
//                    put(
//                            new EqProRandomStack(new LinkedHashMap<>(){{
//                                put(new ItemStack(Material.LADDER),1);
//                                put(new ItemStack(Material.BEDROCK),1);
//
//                            }}),1
//                     );
//                }}),
//            Material.DIRT
//
//            ).register();
//    public static final   SlimefunItem MMG1=register(new MMGenerator(AddGroups.MATERIAL, AddItem.MMG1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),3,114514,0,
//                new LinkedHashMap<>(){{
//                    put(new Object[]{"DIAMOND_BLOCK"},new Object[]{"114DIAMOND"}  );
//                    put(new Object[]{"BEDROCK"},new Object[]{AddUtils.randItemStackFactory(
//                            new LinkedHashMap<>(){{
//                                put("2COPPER_DUST",1);
//                                put("4SILVER_DUST",1);
//                            }}
//                    ),"1919COMMAND_BLOCK"});
//                }}
//            ));

//
//    public static final  SlimefunItem TESTER2=register(new SMGenerator(AddGroups.MATERIAL,new SlimefunItemStack("TESTER2",new ItemStack(Material.DIAMOND_ORE),"测试机","测试寄"),
//            RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),12,0,0, (Object)(new ItemStack(Material.DIAMOND,1145))
//           ,null,new ItemStack(Material.DIAMOND_CHESTPLATE)
//            ));
//    public static final  SlimefunItem MACHINE3=register(new EMachine(AddGroups.MATERIAL, AddItem.MACHINE3,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
//            Material.BOOK,1919,810,
//            new LinkedHashMap<>(){{
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
//    public static final  SlimefunItem MACHINE4=register(new AEMachine(AddGroups.MATERIAL, AddItem.MACHINE4,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
//            Material.BOOK,1919,810,
//            new LinkedHashMap<>(){{
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
//    public static final  SlimefunItem MANUAL1=register(new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
//           1919,810,RecipeType.ENHANCED_CRAFTING_TABLE));
//    public static final  SlimefunItem MANUAL_MULTI=register(new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_MULTI,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
//            1919,810,RecipeType.MULTIBLOCK));
//    public static final  SlimefunItem MANUAL_KILL=register(new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_KILL,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
//            1919,810,RecipeType.MOB_DROP));
//    public static final  SlimefunItem AUTOSMELTING1=register(new AdvanceCrafter(AddGroups.MATERIAL, AddItem.AUTOSMELTING1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
//            Material.FLINT_AND_STEEL,1919,810,RecipeType.SMELTERY));


//    public static final SlimefunItem INPORT=register(new InputPort(AddGroups.MATERIAL, AddItem.INPORT,RecipeType.NULL,AddUtils.NULL_RECIPE,0,0));
//
//    public static final SlimefunItem OUTPORT=register(new OutputPort(AddGroups.MATERIAL, AddItem.OUTPORT,RecipeType.NULL,AddUtils.NULL_RECIPE,0,0));

    public static final SlimefunItem TESTUNIT1=register(new TestStorageUnit(AddGroups.FUNCTIONAL, AddItem.TESTUNIT1,RecipeType.NULL,AddUtils.NULL_RECIPE,0,0));
//
//    public static final SlimefunItem TESTUNIT2=register(new TestStorageUnit2(AddGroups.MATERIAL, AddItem.TESTUNIT2,RecipeType.NULL,AddUtils.NULL_RECIPE.clone()));
//
//    public static final SlimefunItem TESTUNIT3=register(new me.matl114.logitech.SlimefunItem.TestStorageUnit3(AddGroups.MATERIAL, AddItem.TESTUNIT3,RecipeType.NULL,AddUtils.NULL_RECIPE.clone()));



    public static final SlimefunItem ANTIGRAVITY=register(new AntiGravityBar(AddGroups.MATERIAL, AddItem.ANTIGRAVITY,RecipeType.NULL,AddUtils.NULL_RECIPE.clone()));

//    public static final SlimefunItem WORKBENCH1=register(new TestWorkBench(AddGroups.MATERIAL, AddItem.WORKBENCH1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
//            0,0,
//                new LinkedHashMap<>(){{
//                    put(mkP(
//                            mkl(null,"2COPPER_DUST",null,AddSlimefunItems.MATL114,"4DIAMOND",AddSlimefunItems.CUSTOM1,null,"3IRON_DUST",null),
//                            mkl("5COMMAND_BLOCK")
//                    ),0);
//                }}
//            ));

    public static final SlimefunItem TEST_MPART=new MultiPart(AddGroups.MATERIAL,AddItem.TESTPART,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            "test.part").register();
    public static final SlimefunItem TEST_MCORE=new MultiCoreTest(AddGroups.MATERIAL,AddItem.TESTCORE,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            "test.part", MultiBlockTypes.TEST_TYPE).register();
    public static final SlimefunItem TEST_SEQ=new SequenceConstructor(AddGroups.MATERIAL,AddItem.TEST_SEQ,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),new ItemStack(Material.FIRE_CHARGE),1919,8100,
            mkMp(
                    mkP(mkl("128DIAMOND","128GOLD_INGOT","128IRON_INGOT","128COAL"),mkl(AddItem.METAL_CORE)),3
            ))
            .register();

}
