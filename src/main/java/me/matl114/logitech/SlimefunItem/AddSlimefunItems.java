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
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * register main
 */
public class AddSlimefunItems {
    public static void registerSlimefunItems() {
        Debug.logger("注册附属物品...");
        Debug.logger("注册附属机器...");
        CRAFTTYPE_MANUAL_RECIPETYPE.put(CRAFT_MANUAL,BukkitUtils.VANILLA_CRAFTTABLE);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(ENHANCED_CRAFT_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(MAGIC_WORKBENCH_MANUAL,RecipeType.MAGIC_WORKBENCH);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(ANCIENT_ALTAR_MANUAL,RecipeType.ANCIENT_ALTAR);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(ARMOR_FORGE_MANUAL,RecipeType.ARMOR_FORGE);
        if(TYPE){
            FINAL_STACKMACHINE.setRecipe(LAZY_STACKMACHINE);
            FINAL_STACKMACHINE.setRecipeType(RecipeType.ENHANCED_CRAFTING_TABLE);
            FINAL_STACKMGENERATOR.setRecipe(LAZY_STACKMGENERATOR);
            FINAL_STACKMGENERATOR.setRecipeType(RecipeType.ENHANCED_CRAFTING_TABLE);
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
    protected static ItemStack[] recipe(Object ... v){
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
                    res[i]=AddUtils.resolveItem(v[index]);
                }
                index+=delta-1+i%2;
            }
            return res;
        }
    }
    protected static RecipeType COMMON_TYPE=TYPE?RecipeType.ENHANCED_CRAFTING_TABLE: BugCrafter.TYPE;
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
    public static HashMap<SlimefunItem,RecipeType> CRAFTTYPE_MANUAL_RECIPETYPE=new HashMap<>();
    public static final SlimefunItem MATL114=new MaterialItem(AddGroups.MATERIAL, AddItem.MATL114,
            AddDepends.INFINITYWORKBENCH_TYPE!=null?AddDepends.INFINITYWORKBENCH_TYPE:COMMON_TYPE,
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

    public static final SlimefunItem LENGINE=new MaterialItem(AddGroups.MATERIAL,AddItem.LENGINE,COMMON_TYPE,
            recipe(AddItem.LOGIC,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LOGIC,
                    AddItem.ABSTRACT_INGOT,"ELECTRIC_MOTOR",AddItem.LOGIC,AddItem.LOGIC,"ELECTRIC_MOTOR",AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,"ELECTRIC_MOTOR",AddItem.LOGIC,AddItem.LOGIC,"ELECTRIC_MOTOR",AddItem.ABSTRACT_INGOT,
                    "REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT"),null)
            .register();


    public static final SlimefunItem LFIELD=new MaterialItem(AddGroups.MATERIAL,AddItem.LFIELD,COMMON_TYPE,
            recipe("SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT",
                    null,AddItem.EXISTE,null,AddItem.EXISTE,null,AddItem.EXISTE,
                    AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,
                    AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,AddItem.UNIQUE,AddItem.EXISTE,
                    null,AddItem.UNIQUE,null,AddItem.UNIQUE,null,AddItem.UNIQUE,
                    "SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT"),null)
            .setOutput(setC(AddItem.LFIELD,31))
            .register();



    public static final SlimefunItem LSCHEDULER=new MaterialItem(AddGroups.MATERIAL,AddItem.LSCHEDULER,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.LFIELD,
                    AddItem.LFIELD,"ANDROID_INTERFACE_ITEMS","ANDROID_MEMORY_CORE","ANDROID_MEMORY_CORE","ANDROID_INTERFACE_ITEMS", AddItem.LFIELD,
                    AddItem.ABSTRACT_INGOT,"ANDROID_MEMORY_CORE","PROGRAMMABLE_ANDROID",setC(AddItem.LENGINE,1),"ANDROID_MEMORY_CORE",AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,"ANDROID_MEMORY_CORE",setC(AddItem.LENGINE,1),"PROGRAMMABLE_ANDROID","ANDROID_MEMORY_CORE",AddItem.ABSTRACT_INGOT,
                    AddItem.LFIELD,"ANDROID_INTERFACE_ITEMS","ANDROID_MEMORY_CORE","ANDROID_MEMORY_CORE","ANDROID_INTERFACE_ITEMS",AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.LFIELD),null)
            .register();
    public static final SlimefunItem LCRAFT=new MaterialItem(AddGroups.MATERIAL,AddItem.LCRAFT,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,"CARGO_MOTOR","CARGO_MOTOR",AddItem.LFIELD, AddItem.LFIELD,
                    AddItem.LFIELD,"CRAFTER_SMART_PORT","CRAFTING_MOTOR","CRAFTING_MOTOR", "CRAFTER_SMART_PORT",AddItem.LFIELD,
                    "CARGO_MOTOR","CRAFTING_MOTOR","VANILLA_AUTO_CRAFTER",setC(AddItem.LENGINE,1),"CRAFTING_MOTOR","CARGO_MOTOR",
                    "CARGO_MOTOR","CRAFTING_MOTOR", setC(AddItem.LENGINE,1),"ENHANCED_AUTO_CRAFTER","CRAFTING_MOTOR","CARGO_MOTOR",
                    AddItem.LFIELD, "CRAFTER_SMART_PORT","CRAFTING_MOTOR","CRAFTING_MOTOR","CRAFTER_SMART_PORT",AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,"CARGO_MOTOR","CARGO_MOTOR",AddItem.LFIELD,AddItem.LFIELD),null)
            .register();
    public static final SlimefunItem LBOOLIZER=new MaterialItem(AddGroups.MATERIAL,AddItem.LBOOLIZER,COMMON_TYPE,
            recipe(AddItem.TRUE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_,
                    AddItem.FALSE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.FALSE_,AddItem.TRUE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.FALSE_,AddItem.TRUE_, AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.FALSE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.TRUE_,AddItem.FALSE_,
                    AddItem.TRUE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.FALSE_,AddItem.TRUE_),null)
            .register();
    public static final SlimefunItem LMOTOR=new MaterialItem(AddGroups.MATERIAL,AddItem.LMOTOR,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,"HEATING_COIL","HEATING_COIL",AddItem.LFIELD,AddItem.LFIELD,
                    AddItem.LFIELD,"ELECTRIC_MOTOR",setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),"ELECTRIC_MOTOR",AddItem.LFIELD,
                    "HEATING_COIL",setC(AddItem.LBOOLIZER,1),"HEATING_COIL",AddItem.LENGINE,setC(AddItem.LBOOLIZER,1),"HEATING_COIL",
                    "HEATING_COIL",setC(AddItem.LBOOLIZER,1),AddItem.LENGINE,"HEATING_COIL",setC(AddItem.LBOOLIZER,1),"HEATING_COIL",
                    AddItem.LFIELD,"ELECTRIC_MOTOR",setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),"ELECTRIC_MOTOR",AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,"HEATING_COIL","HEATING_COIL",AddItem.LFIELD,AddItem.LFIELD),null)
            .register();
    public static final SlimefunItem LDIGITIZER=new MaterialItem(AddGroups.MATERIAL,AddItem.LDIGITIZER,COMMON_TYPE,
            recipe(AddItem.UNIQUE,AddItem.LFIELD,AddItem.LOGIGATE,AddItem.LOGIGATE,AddItem.LFIELD,AddItem.UNIQUE
                    ,AddItem.LFIELD,setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1)
                    ,AddItem.LFIELD,AddItem.LOGIGATE,setC(AddItem.LBOOLIZER,1),AddItem.PARADOX,AddItem.PARADOX
                    ,setC(AddItem.LBOOLIZER,1),AddItem.LOGIGATE,AddItem.LOGIGATE,setC(AddItem.LBOOLIZER,1),AddItem.PARADOX
                    ,AddItem.PARADOX,setC(AddItem.LBOOLIZER,1),AddItem.LOGIGATE,AddItem.LFIELD,setC(AddItem.LBOOLIZER,1)
                    ,setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),setC(AddItem.LBOOLIZER,1),AddItem.LFIELD,AddItem.UNIQUE
                    ,AddItem.LFIELD,AddItem.LOGIGATE,AddItem.LOGIGATE,AddItem.LFIELD,AddItem.UNIQUE),null)
            .register();
    public static final SlimefunItem LIOPORT=new MaterialItem(AddGroups.MATERIAL,AddItem.LIOPORT,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LFIELD,"HOPPER","HOPPER",AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,"GPS_TRANSMITTER","CARGO_NODE","CARGO_NODE",
                    "GPS_TRANSMITTER",AddItem.LFIELD,"HOPPER","CARGO_NODE_INPUT","CARGO_NODE_OUTPUT_ADVANCED",
                    "CARGO_NODE_OUTPUT_ADVANCED","CARGO_NODE_INPUT","HOPPER","HOPPER","CARGO_NODE_INPUT",
                    AddItem.LENGINE,"CARGO_NODE_OUTPUT_ADVANCED","CARGO_NODE_INPUT","HOPPER",AddItem.LFIELD,
                    "GPS_TRANSMITTER","CARGO_NODE","CARGO_NODE","GPS_TRANSMITTER",AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.LFIELD,"HOPPER","HOPPER",AddItem.LFIELD,
                    AddItem.LFIELD),null)
            .register();
    public static final SlimefunItem LPLATE=new MaterialItem(AddGroups.MATERIAL,AddItem.LPLATE,RecipeType.SMELTERY,
            recipe(setC(AddItem.ABSTRACT_INGOT,2),"BATTERY","POTATO",AddItem.CHIP_INGOT),null)
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

    public static final SlimefunItem STAR_GOLD=new MaterialItem(AddGroups.MATERIAL,AddItem.STAR_GOLD,RecipeType.NULL    ,
            recipe(null,AddItem.END_MINER,null,null,AddUtils.getInfoShow("&f获取方式","&7在本附属的矿机中获取")),null)
            .register();
    public static final SlimefunItem STAR_GOLD_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.STAR_GOLD_INGOT,RecipeType.SMELTERY,
            recipe(setC(AddItem.STAR_GOLD,3),setC(AddItem.DIMENSIONAL_SHARD,6)),null)
            .register();

    public static final SlimefunItem WORLD_FEAT=new MaterialItem(AddGroups.MATERIAL,AddItem.WORLD_FEAT,COMMON_TYPE,
            recipe(null,"STONE","PODZOL","PODZOL","GRASS_BLOCK",null,
                    "STONE","PODZOL","STONE","GRASS_BLOCK","PODZOL","GRASS_BLOCK",
                    "PODZOL","STONE","GRASS_BLOCK","STONE","GRASS_BLOCK","PODZOL",
                    "PODZOL","GRASS_BLOCK","STONE","GRASS_BLOCK","STONE","PODZOL",
                    "GRASS_BLOCK","PODZOL","GRASS_BLOCK","STONE","PODZOL","STONE",
                    null,"GRASS_BLOCK","PODZOL","PODZOL","STONE",null),null)
            .register();
    public static final SlimefunItem NETHER_FEAT=new MaterialItem(AddGroups.MATERIAL,AddItem.NETHER_FEAT,COMMON_TYPE,
            recipe(null,"MAGMA_BLOCK","CRIMSON_NYLIUM","CRIMSON_NYLIUM","OBSIDIAN",null,
                    "MAGMA_BLOCK","CRIMSON_NYLIUM","MAGMA_BLOCK","OBSIDIAN","CRIMSON_NYLIUM","OBSIDIAN",
                    "CRIMSON_NYLIUM","MAGMA_BLOCK","OBSIDIAN","MAGMA_BLOCK","OBSIDIAN","CRIMSON_NYLIUM",
                    "CRIMSON_NYLIUM","OBSIDIAN","MAGMA_BLOCK","OBSIDIAN","MAGMA_BLOCK","CRIMSON_NYLIUM",
                    "OBSIDIAN","CRIMSON_NYLIUM","OBSIDIAN","MAGMA_BLOCK","CRIMSON_NYLIUM","MAGMA_BLOCK",
                    null,"OBSIDIAN","CRIMSON_NYLIUM","CRIMSON_NYLIUM","MAGMA_BLOCK",null),null)
            .register();
    public static final SlimefunItem END_FEAT=new MaterialItem(AddGroups.MATERIAL,AddItem.END_FEAT,COMMON_TYPE,
            recipe(null,"CHORUS_FRUIT","CHORUS_FLOWER","CHORUS_FLOWER","END_STONE",null,
                    "CHORUS_FRUIT","CHORUS_FLOWER","CHORUS_FRUIT","END_STONE","CHORUS_FLOWER","END_STONE",
                    "CHORUS_FLOWER","CHORUS_FRUIT","END_STONE","CHORUS_FRUIT","END_STONE","CHORUS_FLOWER",
                    "CHORUS_FLOWER","END_STONE","CHORUS_FRUIT","END_STONE","CHORUS_FRUIT","CHORUS_FLOWER",
                    "END_STONE","CHORUS_FLOWER","END_STONE","CHORUS_FRUIT","CHORUS_FLOWER","CHORUS_FRUIT",
                    null,"END_STONE","CHORUS_FLOWER","CHORUS_FLOWER","CHORUS_FRUIT",null),null)
            .register();
    public static final SlimefunItem ENTITY_FEATURE=new EntityFeat(AddGroups.MATERIAL,AddItem.ENTITY_FEAT,RecipeType.NULL,
            recipe(null,AddUtils.addGlow( new ItemStack(Material.IRON_PICKAXE)),null,null,AddUtils.getInfoShow("&f获取方式","&7当 挖掘任意刷怪笼方块时 ","&750%额外掉落随机种类的生物特征"),null,
                    null,new ItemStack(Material.SPAWNER),null))
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
    public static final SlimefunItem LSINGULARITY=new MaterialItem(AddGroups.MATERIAL,AddItem.LSINGULARITY,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem ATOM_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.ATOM_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SOLAR_REACTOR,Language.get("MultiBlock.SOLAR_REACTOR.Name")),null)
            .register();


    //alloy
    public static final SlimefunItem CHIP_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.CHIP_INGOT,RecipeType.SMELTERY,
            recipe("2SILVER_INGOT","2REINFORCED_ALLOY_INGOT","4IRON_INGOT",
                    setC(SlimefunItems.COPPER_INGOT,4),"2SILICON","4ALUMINUM_INGOT"))
            .register();
    public static final SlimefunItem ABSTRACT_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.ABSTRACT_INGOT,RecipeType.SMELTERY,
            recipe(AddItem.TRUE_,AddItem.EXISTE,AddItem.LOGIC,AddItem.NOLOGIC,AddItem.UNIQUE,AddItem.FALSE_)).setOutput(setC(AddItem.ABSTRACT_INGOT,4))
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
    public static final SlimefunItem BISILVER=new MaterialItem(AddGroups.MATERIAL,AddItem.BISILVER,STARSMELTERY,
            recipe(setC(AddItem.BISMUTH_INGOT,4),setC(AddItem.PARADOX,20),"4SILVER_INGOT",
                    "4BILLON_INGOT"),null)
            .register();
    public static final SlimefunItem PAGOLD=new MaterialItem(AddGroups.MATERIAL,AddItem.PAGOLD,STARSMELTERY,
            recipe(setC(AddItem.PALLADIUM_INGOT,2),setC(AddItem.PARADOX,20),
                    setC(AddItem.PLATINUM_INGOT,2),"7GOLD_22K"
                    ),null)
            .register();

    public static final SlimefunItem PDCECDMD=new MaterialItem(AddGroups.MATERIAL,AddItem.PDCECDMD,AddSlimefunItems.STARSMELTERY,
            recipe("32PLUTONIUM",setC(AddItem.CERIUM,18),setC(AddItem.CADMIUM_INGOT,16),
                    setC(AddItem.MENDELEVIUM,12)
                    ),null)
            .register();
    public static final SlimefunItem HGTLPBBI=new MaterialItem(AddGroups.MATERIAL,AddItem.HGTLPBBI,AddSlimefunItems.STARSMELTERY,
            recipe(setC(AddItem.HYDRAGYRUM,24),setC(AddItem.THALLIUM,16),
                    "16LEAD_INGOT",setC(AddItem.BISILVER,3)),null)
            .register();
    public static final SlimefunItem REINFORCED_CHIP_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.REINFORCED_CHIP_INGOT,AddSlimefunItems.STARSMELTERY,
            recipe(setC(AddItem.STAR_GOLD_INGOT,10),setC(AddItem.CHIP_INGOT,8),
                    setC(AddItem.ATOM_INGOT,16),setC(AddItem.PAGOLD,4),
                    setC(AddItem.CADMIUM_INGOT,4),setC(AddItem.BISILVER,2)
                    ),null)
            .register();



    public static final SlimefunItem SPACE_PLATE=new MaterialItem(AddGroups.MATERIAL,AddItem.SPACE_PLATE,STARSMELTERY,
            recipe(setC(AddItem.STAR_GOLD_INGOT,12),setC(AddItem.PARADOX,24),
                    setC(AddItem.ATOM_INGOT,20),setC(AddItem.LFIELD,12),AddItem.REINFORCED_CHIP_INGOT
                    ),null)
            .register();
    public static final SlimefunItem VIRTUAL_SPACE=new MaterialItem(AddGroups.MATERIAL,AddItem.VIRTUAL_SPACE,COMMON_TYPE,
            recipe(AddItem.SPACE_PLATE,AddItem.BISILVER,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.BISILVER,AddItem.SPACE_PLATE,
                    AddItem.BISILVER,AddItem.LDIGITIZER,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.LDIGITIZER,AddItem.BISILVER,
                    "GPS_TRANSMITTER_4",AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,"GPS_TRANSMITTER_4",
                    AddItem.LIOPORT,AddItem.PAGOLD,AddItem.CHIP_CORE,AddItem.CHIP_CORE,AddItem.PAGOLD,AddItem.LIOPORT,
                    AddItem.BISILVER,AddItem.LDIGITIZER,AddItem.PAGOLD,AddItem.PAGOLD,AddItem.LDIGITIZER,AddItem.BISILVER,
                    AddItem.SPACE_PLATE,AddItem.BISILVER,setC(AddItem.ATOM_INGOT,8),setC(AddItem.ATOM_INGOT,8),AddItem.BISILVER,AddItem.SPACE_PLATE),null)
            .setOutput(setC(AddItem.VIRTUAL_SPACE,8))
            .register();

    public static final SlimefunItem REDSTONE_ENGINE=new MaterialItem(AddGroups.VANILLA,AddItem.REDSTONE_ENGINE,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("TNT","SLIME_BLOCK","ANVIL",
                    "OBSERVER","STICKY_PISTON","STICKY_PISTON",
                    "REDSTONE_TORCH",AddItem.LOGIGATE,"2REPEATER"),null)
            .register();

    public static final SlimefunItem SAMPLE_HEAD=new AbstractBlock(AddGroups.SPECIAL,AddItem.SAMPLE_HEAD,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.HEAD_ANALYZER,Language.get("Machines.HEAD_ANALYZER.Name")))
            .register();
    public static final SlimefunItem SAMPLE_SPAWNER=new AbstractSpawner(AddGroups.SPECIAL,AddItem.SAMPLE_SPAWNER,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENTITY_FEAT,Language.get("Items.ENTITY_FEAT.Name")))
            .register();
    public static final SlimefunItem CHIP=new ChipCard(AddGroups.ADVANCED,AddItem.CHIP,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.CHIP_MAKER,Language.get("Machines.CHIP_MAKER.Name")))
            .register();
    public static final SlimefunItem CHIP_CORE=new MaterialItem(AddGroups.ADVANCED,AddItem.CHIP_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.CHIP_MAKER,Language.get("Machines.CHIP_MAKER.Name")),null)
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

    public static final SlimefunItem STACKFRAME=new MaterialItem(AddGroups.MATERIAL,AddItem.STACKFRAME,COMMON_TYPE,
            recipe(AddItem.LIOPORT,setC(AddItem.ATOM_INGOT,8),"GPS_TRANSMITTER_4","GPS_TRANSMITTER_4",setC(AddItem.ATOM_INGOT,8),AddItem.LMOTOR,
                    setC(AddItem.ATOM_INGOT,8),AddItem.BISILVER,setC(AddItem.STAR_GOLD_INGOT,1),setC(AddItem.STAR_GOLD_INGOT,1),AddItem.BISILVER,setC(AddItem.ATOM_INGOT,8),
                    AddItem.PAGOLD,setC(AddItem.STAR_GOLD_INGOT,1),AddItem.LSINGULARITY,AddItem.SPACE_PLATE,setC(AddItem.STAR_GOLD_INGOT,1),AddItem.PAGOLD,
                    AddItem.PAGOLD,setC(AddItem.STAR_GOLD_INGOT,1),AddItem.LSCHEDULER,AddItem.LSINGULARITY,setC(AddItem.STAR_GOLD_INGOT,1),AddItem.PAGOLD,
                    setC(AddItem.ATOM_INGOT,8),AddItem.BISILVER,setC(AddItem.STAR_GOLD_INGOT,1),setC(AddItem.STAR_GOLD_INGOT,1),AddItem.BISILVER,setC(AddItem.ATOM_INGOT,8),
                    AddItem.LDIGITIZER,setC(AddItem.ATOM_INGOT,8),AddItem.CHIP_CORE,AddItem.CHIP_CORE,setC(AddItem.ATOM_INGOT,8),AddItem.LCRAFT),null)
            .setOutput(setC(AddItem.STACKFRAME,64)).register();
    public static final SlimefunItem LASER=new MaterialItem(AddGroups.ADVANCED,AddItem.LASER,COMMON_TYPE,
            recipe(AddItem.HYDRAGYRUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.HYDRAGYRUM,
                    AddItem.ANTIMONY_INGOT,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.ANTIMONY_INGOT,
                    AddItem.BORON,AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,AddItem.BORON,
                    AddItem.BORON,AddItem.PAGOLD,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.PAGOLD,AddItem.BORON,
                    AddItem.ANTIMONY_INGOT,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.ANTIMONY_INGOT,
                    AddItem.HYDRAGYRUM,AddItem.MOLYBDENUM,AddItem.BORON,AddItem.BORON,AddItem.MOLYBDENUM,AddItem.HYDRAGYRUM),null)
            .register();
    public static final SlimefunItem VIRTUALWORLD=new MaterialItem(AddGroups.MATERIAL,AddItem.VIRTUALWORLD,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.FINAL_CONVERTOR,Language.get("Machines.FINAL_CONVERTOR.Name")),null)
            .register();
    //machines
    public static final SlimefunItem BOOL_GENERATOR=new BoolGenerator(AddGroups.BASIC,AddItem.BOOL_GENERATOR,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("OBSERVER","REDSTONE","OBSERVER",
                    "REDSTONE_TORCH","SILICON","SILICON",
                    "STEEL_INGOT","MEDIUM_CAPACITOR","STEEL_INGOT"),Material.RECOVERY_COMPASS,6)
            .register();
    public static final SlimefunItem LOGIC_REACTOR=new LogicReactor(AddGroups.BASIC,AddItem.LOGIC_REACTOR,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.LOGIGATE,"COMPARATOR",AddItem.LOGIGATE,
                    "HEATING_COIL" , null,"HEATING_COIL",
                    "IRON_INGOT","SMALL_CAPACITOR","IRON_INGOT"
                    ),Material.COMPARATOR,3)
            .register();
    public static final SlimefunItem BUG_CRAFTER=new BugCrafter(AddGroups.BASIC,AddItem.BUG_CRAFTER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.LOGIGATE,AddItem.ABSTRACT_INGOT,
                    AddItem.CHIP_INGOT,AddItem.BUG,AddItem.CHIP_INGOT,
                    AddItem.ABSTRACT_INGOT,"MEDIUM_CAPACITOR",AddItem.ABSTRACT_INGOT),10_000,1_00,7)
            .register();
    public static final  SlimefunItem FURNACE_FACTORY=new MTMachine(AddGroups.BASIC, AddItem.FURNACE_FACTORY,COMMON_TYPE,
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
                    recipelist.add(MachineRecipeUtils.stackFrom(0,rps.getInput(),rps.getOutput()));
                }
                return recipelist;
            })
            .register();
    public static final  SlimefunItem PRESSOR_FACTORY=new MTMachine(AddGroups.BASIC, AddItem.PRESSOR_FACTORY,COMMON_TYPE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_PRESS_2","ELECTRIC_PRESS_2",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_PRESS_2","ELECTRIC_PRESS_2",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_PRESS_2","ELECTRIC_PRESS_2",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT), AddUtils.addGlow( new ItemStack(Material.PISTON))
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
                List<MachineRecipe> compressorRecipes=RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItem.getById("ELECTRIC_PRESS"));
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
    public static final  SlimefunItem SMELTRY=new AEMachine(AddGroups.BASIC, AddItem.SMELTRY,COMMON_TYPE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem. ABSTRACT_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem. ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem. ABSTRACT_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem. ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT),
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
                    mkP(mkl(setC(AddItem.END_FEAT,1),"64END_STONE"),mkl("END_PORTAL_FRAME")),6,
                        mkP(mkl(AddItem.STAR_GOLD,"END_PORTAL_FRAME"),mkl(AddItem.PORTAL_FRAME)),6,
                        mkP(mkl(AddItem.STAR_GOLD_INGOT,"16QUARTZ_BLOCK"),mkl(AddItem.SOLAR_REACTOR_FRAME)),6,
                        mkP(mkl(setC(AddItem.LPLATE,2),"16GLASS"),mkl(AddItem.SOLAR_REACTOR_GLASS)),6,
                        mkP(mkl(AddItem.STACKFRAME,AddItem.TRUE_),mkl(AddItem.STACKMACHINE)),6,
                        mkP(mkl(AddItem.STACKFRAME,AddItem.FALSE_),mkl(AddItem.STACKMGENERATOR)),6,
                        mkP(mkl(AddItem.STACKFRAME,"4ENERGY_REGULATOR"),mkl(AddItem.ENERGY_AMPLIFIER)),6,
                        mkP(mkl(AddItem.SPACE_PLATE,AddItem.MASS_CORE),mkl(setC(AddItem.TRANSMUTATOR_FRAME,9))),6,
                        mkP(mkl(AddItem.SPACE_PLATE,setC(AddItem.LFIELD,24)),mkl(setC(AddItem.TRANSMUTATOR_GLASS,16))),6,
                        mkP(mkl(setC(AddItem.ATOM_INGOT,16),AddItem.PAGOLD),mkl(setC(AddItem.TRANSMUTATOR_ROD,8))),6,
                        mkP(mkl(AddItem.VIRTUALWORLD,AddItem.TRUE_),mkl(setC(AddItem.FINAL_STACKMACHINE,64))),6,
                        mkP(mkl(AddItem.VIRTUALWORLD,AddItem.FALSE_),mkl(setC(AddItem.FINAL_STACKMGENERATOR,64))),6
            ))
            .register();

    public static final  SlimefunItem DUST_EXTRACTOR=new MTMachine(AddGroups.BASIC, AddItem.DUST_EXTRACTOR,COMMON_TYPE,
            recipe(AddItem.LPLATE,setC(AddItem.TECH_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.MASS_CORE,1),AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,4),setC(AddItem.CHIP_CORE,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.ATOM_INGOT,4),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4ELECTRIC_ORE_GRINDER_3","4ELECTRIC_ORE_GRINDER_3",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,"4ELECTRIC_GOLD_PAN_3","4ELECTRIC_GOLD_PAN_3",AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4ELECTRIC_DUST_WASHER_3","4ELECTRIC_DUST_WASHER_3",AddItem.LMOTOR,AddItem.LPLATE,
                    setC(AddItem.PAGOLD,1),setC(AddItem.TECH_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.MASS_CORE,1),setC(AddItem.PAGOLD,1)),
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
    public static final  SlimefunItem INGOT_FACTORY=new MTMachine(AddGroups.BASIC, AddItem.INGOT_FACTORY,COMMON_TYPE,
            recipe(AddItem.LPLATE,setC(AddItem.METAL_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.SMELERY_CORE,1),AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,4),setC(AddItem.CHIP_CORE,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.ATOM_INGOT,4),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",AddItem.LMOTOR,AddItem.LPLATE,
                    setC(AddItem.PAGOLD,1),setC(AddItem.METAL_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.SMELERY_CORE,1),setC(AddItem.PAGOLD,1)),
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
    public static final  SlimefunItem CRAFTER=new SpecialCrafter(AddGroups.BASIC, AddItem.CRAFTER, RecipeType.ENHANCED_CRAFTING_TABLE,
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
    public static final  SlimefunItem CONVERTOR=new EMachine(AddGroups.BASIC, AddItem.CONVERTOR,RecipeType.NULL,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.NOLOGIC,AddItem.NOLOGIC,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT),
            Material.STONE,24_000,500,
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
    public static final  SlimefunItem VIRTUAL_KILLER=new VirtualKiller(AddGroups.BASIC, AddItem.VIRTUAL_KILLER,COMMON_TYPE,
            recipe(AddItem.BISILVER,AddItem.BISILVER,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.BISILVER,AddItem.BISILVER,
                    AddItem.BISILVER,null,"PROGRAMMABLE_ANDROID_3_BUTCHER","PROGRAMMABLE_ANDROID_3_BUTCHER",null,AddItem.BISILVER,
                    AddItem.BISILVER,null,"PROGRAMMABLE_ANDROID_3_FISHERMAN","PROGRAMMABLE_ANDROID_3_FISHERMAN",null,AddItem.BISILVER,
                    AddItem.BISILVER,null,"PROGRAMMABLE_ANDROID_2_BUTCHER","PROGRAMMABLE_ANDROID_2_BUTCHER",null,AddItem.BISILVER,
                    AddItem.BISILVER,null,"PROGRAMMABLE_ANDROID_3_BUTCHER","PROGRAMMABLE_ANDROID_3_BUTCHER",null,AddItem.BISILVER,
                    AddItem.BISILVER,AddItem.BISILVER,AddItem.LENGINE,AddItem.LENGINE,AddItem.BISILVER,AddItem.BISILVER), 3000,300,
            1)
            .register();
    public static final  SlimefunItem LVOID_GENERATOR=new TestGenerator(AddGroups.ENERGY, AddItem.LVOID_GENERATOR,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LFIELD,
                    AddItem.LPLATE,null,null,null,null,AddItem.LPLATE,
                    AddItem.ABSTRACT_INGOT,null,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,null,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,null,AddItem.LENGINE,AddItem.ABSTRACT_INGOT,null,AddItem.ABSTRACT_INGOT,
                    AddItem.LPLATE,null,null,null,null,AddItem.LPLATE,
                    AddItem.LFIELD,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LFIELD),2333,180,
            1000,1145)
            .register();
    public static final  SlimefunItem ENERGY_TRASH=new EnergyTrash(AddGroups.ENERGY, AddItem.ENERGY_TRASH,RecipeType.ANCIENT_ALTAR,
            recipe(AddItem.BUG,"ENERGY_REGULATOR",AddItem.BUG,"ENERGY_CONNECTOR","TRASH_CAN_BLOCK","ENERGY_CONNECTOR",
                    AddItem.BUG,"ENERGIZED_CAPACITOR",AddItem.BUG), 100_000_000)
            .register();
    public static final  SlimefunItem OPPO_GEN=new BiReactor(AddGroups.ENERGY, AddItem.OPPO_GEN,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.FALSE_,AddItem.TRUE_,AddItem.ABSTRACT_INGOT,null,
                    null,AddItem.ABSTRACT_INGOT,AddItem.TRUE_,AddItem.FALSE_,AddItem.ABSTRACT_INGOT,null,
                    AddItem.ABSTRACT_INGOT,AddItem.LBOOLIZER,AddItem.LENGINE,AddItem.LENGINE,AddItem.LBOOLIZER,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.LBOOLIZER,AddItem.LBOOLIZER,AddItem.LBOOLIZER,AddItem.LBOOLIZER,AddItem.ABSTRACT_INGOT,
                    AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT), 50_000,5_000,10_000)
            .register();
    public static final  SlimefunItem ARC_REACTOR=new EGenerator(AddGroups.ENERGY, AddItem.ARC_REACTOR,COMMON_TYPE,
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

    public static final  SlimefunItem CHIP_REACTOR=new ChipReactor(AddGroups.ENERGY, AddItem.CHIP_REACTOR,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.SPACE_PLATE,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.SPACE_PLATE,null,
                    AddItem.SPACE_PLATE,AddItem.PDCECDMD,AddItem.LASER,AddItem.LASER,AddItem.PDCECDMD,AddItem.SPACE_PLATE,
                    AddItem.TECH_CORE,AddItem.CHIP_CORE,AddItem.ADVANCED_CHIP_MAKER,AddItem.ADVANCED_CHIP_MAKER,AddItem.CHIP_CORE,AddItem.TECH_CORE,
                    AddItem.TECH_CORE,AddItem.HGTLPBBI,AddItem.LASER,AddItem.LASER,AddItem.HGTLPBBI,AddItem.TECH_CORE,
                    AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT,AddItem.PLATINUM_INGOT), 200_000_000,0.1,300)
            .register();
    public static final SlimefunItem ENERGY_AMPLIFIER=new EnergyAmplifier(AddGroups.ENERGY,AddItem.ENERGY_AMPLIFIER,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),1_000_000_000)
            .register();
    public static final  SlimefunItem SPECIAL_CRAFTER=new SpecialTypeCrafter(AddGroups.BASIC, AddItem.SPECIAL_CRAFTER,COMMON_TYPE,
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
    public static final  SlimefunItem STAR_SMELTERY=new AEMachine(AddGroups.ADVANCED, AddItem.STAR_SMELTERY,COMMON_TYPE,
            recipe(AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LFIELD,
                    AddItem.STAR_GOLD_INGOT,setC(AddItem.LPLATE,1),"ELECTRIC_INGOT_FACTORY_3","ELECTRIC_INGOT_FACTORY_3",setC(AddItem.LPLATE,1),AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.LMOTOR,"CARBONADO_EDGED_FURNACE","CARBONADO_EDGED_FURNACE",AddItem.LMOTOR,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,AddItem.LMOTOR,"CARBONADO_EDGED_FURNACE","CARBONADO_EDGED_FURNACE",AddItem.LMOTOR,AddItem.STAR_GOLD_INGOT,
                    AddItem.STAR_GOLD_INGOT,setC(AddItem.LPLATE,1),"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",setC(AddItem.LPLATE,1),AddItem.STAR_GOLD_INGOT,
                    AddItem.LFIELD,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.LFIELD), Material.STONE,18_000,180_000,
           120,AddSlimefunItems.STARSMELTERY)
            .register();
    public static final  SlimefunItem SEQ_CONSTRUCTOR=new SequenceConstructor(AddGroups.BASIC, AddItem.SEQ_CONSTRUCTOR,COMMON_TYPE,
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
                    mkP(mkl(AddItem.METAL_CORE,AddItem.SMELERY_CORE,AddItem.TECH_CORE,AddItem.MASS_CORE),mkl(AddItem.EASYSTACKMACHINE)),3
            ))
            .register();




    public static final  SlimefunItem CHIP_MAKER=new EMachine(AddGroups.ADVANCED, AddItem.CHIP_MAKER,COMMON_TYPE,
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

    public static final SlimefunItem CHIP_CONSUMER=new ChipConsumer(AddGroups.ADVANCED,AddItem.CHIP_CONSUMER,COMMON_TYPE,
            recipe(AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,1),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LBOOLIZER,1),AddItem.ABSTRACT_INGOT,
                    AddItem.CHIP_INGOT,AddItem.LFIELD,setC(AddItem.LOGIGATE,1),AddItem.LDIGITIZER,AddItem.LFIELD,AddItem.CHIP_INGOT,
                    AddItem.CHIP_INGOT,AddItem.LFIELD,AddItem.LDIGITIZER,setC(AddItem.LOGIGATE,1),AddItem.LFIELD,AddItem.CHIP_INGOT,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,1),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LBOOLIZER,1),AddItem.ABSTRACT_INGOT,
                    AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.CHIP_INGOT,AddItem.CHIP_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LENGINE),4_000,500)
            .register();

    public static final  SlimefunItem CHIP_BICONSUMER=new ChipBiConsumer(AddGroups.ADVANCED, AddItem.CHIP_BICONSUMER,COMMON_TYPE,
            recipe(AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.LENGINE,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LDIGITIZER,1),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LDIGITIZER,1),AddItem.ABSTRACT_INGOT,
                    AddItem.LPLATE,AddItem.LFIELD,setC(AddItem.LOGIGATE,1),setC(AddItem.UNIQUE,1),AddItem.LFIELD,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LFIELD,setC(AddItem.EXISTE,1),setC(AddItem.LOGIGATE,1),AddItem.LFIELD,AddItem.LPLATE,
                    AddItem.ABSTRACT_INGOT,setC(AddItem.LDIGITIZER,1),AddItem.LFIELD,AddItem.LFIELD,setC(AddItem.LDIGITIZER,1),AddItem.ABSTRACT_INGOT,
                    AddItem.LENGINE,AddItem.ABSTRACT_INGOT,AddItem.LPLATE,AddItem.LPLATE,AddItem.ABSTRACT_INGOT,AddItem.LENGINE),8_000,1_000)
            .register();
    public static final SlimefunItem EASYSTACKMACHINE=new StackMachine(AddGroups.BASIC,AddItem.EASYSTACKMACHINE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.SEQ_CONSTRUCTOR,Language.get("Machines.SEQ_CONSTRUCTOR.Name")),Material.IRON_PICKAXE,
            2_000,20_000,0.5)
            .register();
    public static final SlimefunItem STACKMACHINE=new StackMachine(AddGroups.ADVANCED,AddItem.STACKMACHINE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),Material.IRON_PICKAXE,
            2_000,20_000_000,2.0)
            .register();
    //
    public static final  SlimefunItem ADVANCED_CHIP_MAKER=new ChipCopier(AddGroups.ADVANCED, AddItem.ADVANCED_CHIP_MAKER,COMMON_TYPE,
            recipe(null,AddItem.REINFORCED_CHIP_INGOT,AddItem.CHIP_MAKER,AddItem.CHIP_MAKER,AddItem.REINFORCED_CHIP_INGOT,null,
                    null,AddItem.REINFORCED_CHIP_INGOT,AddItem.CHIP_MAKER,AddItem.CHIP_MAKER,AddItem.REINFORCED_CHIP_INGOT,null,
                    AddItem.HGTLPBBI,AddItem.LPLATE,AddItem.LASER,AddItem.LASER,AddItem.LPLATE,AddItem.HGTLPBBI,
                    AddItem.BISILVER,AddItem.SPACE_PLATE,setC(AddItem.LSINGULARITY,1),setC(AddItem.LSINGULARITY,1),AddItem.SPACE_PLATE,AddItem.BISILVER,
                    AddItem.BISILVER,AddItem.SPACE_PLATE,setC(AddItem.LSINGULARITY,1),setC(AddItem.LSINGULARITY,1),AddItem.SPACE_PLATE,AddItem.BISILVER,
                    AddItem.PDCECDMD,AddItem.LPLATE,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.LPLATE,AddItem.PDCECDMD))
            .register();





    //Material Generators
    public static final SlimefunItem MAGIC_STONE=new SMGenerator(AddGroups.GENERATORS, AddItem.MAGIC_STONE,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("DIAMOND_PICKAXE","LAVA_BUCKET","DIAMOND_PICKAXE",
                    "PISTON",AddItem.LOGIGATE,"PISTON",
                    "COBALT_PICKAXE","WATER_BUCKET","COBALT_PICKAXE"),18,1000,66,
            AddUtils.randItemStackFactory(
                    mkMp("64COBBLESTONE",72,
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
    public static final SlimefunItem BOOL_MG = new MMGenerator(AddGroups.GENERATORS, AddItem.BOOL_MG, COMMON_TYPE,
            recipe(null,AddItem.FALSE_,AddItem.LFIELD,AddItem.LFIELD,AddItem.FALSE_,null,
                    AddItem.TRUE_,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.TRUE_,
                    AddItem.LFIELD,AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,1),setC(AddItem.LOGIGATE,1),AddItem.ABSTRACT_INGOT,AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.ABSTRACT_INGOT,setC(AddItem.LOGIGATE,1),setC(AddItem.LBOOLIZER,1),AddItem.ABSTRACT_INGOT,AddItem.LFIELD,
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
                    mkMp("64COBBLESTONE",40,
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
    public static final SlimefunItem NETHER_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.NETHER_MINER,COMMON_TYPE,
            recipe(null,setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),null,
                    null,AddItem.LPLATE,AddItem.OVERWORLD_MINER,AddItem.OVERWORLD_MINER,AddItem.LPLATE,null,
                    null,AddItem.LPLATE,AddItem.LENGINE,AddItem.LENGINE,AddItem.LPLATE,null,
                    null,AddItem.NETHER_FEAT,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.NETHER_FEAT,null
                  ),10,3_000,600,
            AddUtils.randItemStackFactory(
                    mkMp(
                            "64NETHERRACK",30,
                            "10QUARTZ",40,
                            "16MAGMA_BLOCK",7,
                            "16BLACKSTONE",7,
                            "16BASALT",7,
                            "16NETHER_WART",7,
                            "4ANCIENT_DEBRIS",2
                    )
            ))
            .register();
    public static final SlimefunItem END_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.END_MINER,COMMON_TYPE,
            recipe(null,AddItem.LMOTOR,AddItem.LFIELD,AddItem.LFIELD,AddItem.LMOTOR,null,
                    null,AddItem.LPLATE,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",AddItem.LPLATE,null,
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
                            setC(AddItem.DIMENSIONAL_SHARD,2),30,
                            AddItem.STAR_GOLD,20
                    )
            ))
            .register();
    public static final SlimefunItem DIMENSION_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.DIMENSION_MINER,COMMON_TYPE  ,
            recipe("GPS_TRANSMITTER_4",AddItem.STAR_GOLD,AddItem.LFIELD,AddItem.LFIELD,AddItem.STAR_GOLD,"GPS_TRANSMITTER_4",
                    AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.STAR_GOLD,AddItem.STAR_GOLD_INGOT,
                    AddItem.LFIELD,null,AddItem.NETHER_MINER,AddItem.NETHER_MINER,null,AddItem.LFIELD,
                    AddItem.LFIELD,null,AddItem.END_MINER,AddItem.END_MINER,null,AddItem.LFIELD,
                    AddItem.STAR_GOLD_INGOT,AddItem.LIOPORT,AddItem.LENGINE,AddItem.LENGINE,AddItem.LIOPORT,AddItem.STAR_GOLD_INGOT,
                    "GPS_TRANSMITTER_4",AddItem.STAR_GOLD,AddItem.LFIELD,AddItem.LFIELD,AddItem.STAR_GOLD,"GPS_TRANSMITTER_4"),6,18_000,1_800,
            AddUtils.randItemStackFactory(
                    mkMp(
                            "64COBBLESTONE",1,
                            "64END_STONE",1,
                            "64NETHERRACK",1
                    )
            ),

            AddUtils.randItemStackFactory(
                    mkMp(
                            setC(AddItem.DIMENSIONAL_SHARD,2),2,
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
                    Material.BONE_BLOCK,"PISTON",Material.BONE_BLOCK), 30, 1_000, 33,
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
    public static final SlimefunItem NETHER_PLANT = new MMGenerator(AddGroups.GENERATORS, AddItem.NETHER_PLANT, COMMON_TYPE,
            recipe(null,setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),null,
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
    public static final SlimefunItem END_PLANT = new MMGenerator(AddGroups.GENERATORS, AddItem.END_PLANT, COMMON_TYPE,
            recipe(null,AddItem.LFIELD,setC(AddItem.PARADOX,1),setC(AddItem.PARADOX,1),AddItem.LFIELD,null,
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
    public static final SlimefunItem STONE_FACTORY = new MMGenerator(AddGroups.GENERATORS, AddItem.STONE_FACTORY, COMMON_TYPE,
            recipe(AddItem.LPLATE,setC(AddItem.METAL_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.SMELERY_CORE,1),AddItem.LPLATE,
                    AddItem.LPLATE,setC(AddItem.ATOM_INGOT,4),setC(AddItem.CHIP_CORE,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.ATOM_INGOT,4),AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LENGINE,"4PROGRAMMABLE_ANDROID_3_BUTCHER","4PROGRAMMABLE_ANDROID_3_BUTCHER",AddItem.LENGINE,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LIOPORT,setC(AddItem.OVERWORLD_MINER,4),setC(AddItem.OVERWORLD_MINER,4),AddItem.LIOPORT,AddItem.LPLATE,
                    AddItem.LPLATE,AddItem.LMOTOR,"4PROGRAMMABLE_ANDROID_3_BUTCHER","4PROGRAMMABLE_ANDROID_3_BUTCHER",AddItem.LMOTOR,AddItem.LPLATE,
                    setC(AddItem.PAGOLD,1),setC(AddItem.METAL_CORE,1),setC(AddItem.PAGOLD,1),setC(AddItem.PAGOLD,1),setC(AddItem.SMELERY_CORE,1),setC(AddItem.PAGOLD,1)), 1, 129_600, 2400,
            new LinkedHashMap<>(){{
                put(mkl("COBBLESTONE"),mkl(   AddUtils.randAmountItemFactory(new ItemStack(Material.COBBLESTONE),514,1919)));
                put(mkl("NETHERRACK"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.NETHERRACK),514,1919)));
                put(mkl("END_STONE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.END_STONE),514,1919)));
                put(mkl("GRANITE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.GRANITE),114,514)));
                put(mkl("DIORITE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.DIORITE),114,514)));
                put(mkl("ANDESITE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.ANDESITE),114,514)));
                put(mkl("STONE"),mkl(AddUtils.randAmountItemFactory(new ItemStack(Material.STONE),114,514)));
            }})
            .register();
    public static final SlimefunItem REDSTONE_MG=new SMGenerator(AddGroups.VANILLA, AddItem.REDSTONE_MG,COMMON_TYPE,
            recipe(null,"REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK",null,
                    "REDSTONE_BLOCK",setC(AddItem.NOLOGIC,1),AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,setC(AddItem.NOLOGIC,1),"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",AddItem.ABSTRACT_INGOT,setC(AddItem.REDSTONE_ENGINE,1),setC(AddItem.REDSTONE_ENGINE,1),AddItem.ABSTRACT_INGOT,"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",AddItem.ABSTRACT_INGOT,setC(AddItem.REDSTONE_ENGINE,1),setC(AddItem.REDSTONE_ENGINE,1),AddItem.ABSTRACT_INGOT,"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",setC(AddItem.NOLOGIC,1),AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,setC(AddItem.NOLOGIC,1),"REDSTONE_BLOCK",
                    null,"REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK",null),1,10000,250,
            AddUtils.randItemStackFactory(
                    mkMp("REDSTONE_TORCH",16,"OBSERVER",16,"PISTON",16,"STICKY_PISTON",16,"REPEATER",16,"COMPARATOR",16,"LEVER",16,"NOTE_BLOCK",16,"REDSTONE_LAMP",16,
                            AddUtils.randItemStackFactory(mkMp("SLIME_BALL",26,"HONEY_BLOCK",3,"TNT",2,"REDSTONE",1)),16)
            ))
            .register();
    public static final SlimefunItem DUPE_MG=new MMGenerator(AddGroups.VANILLA, AddItem.DUPE_MG,COMMON_TYPE,
            recipe("TRIPWIRE_HOOK",AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.NOLOGIC,"TRIPWIRE_HOOK",
                    "TRIPWIRE_HOOK",null,null,null,null,"TRIPWIRE_HOOK",
                    "TRIPWIRE_HOOK",AddItem.REDSTONE_ENGINE,null,null,AddItem.REDSTONE_ENGINE,"TRIPWIRE_HOOK",
                    "WHITE_CARPET",AddItem.REDSTONE_ENGINE,null,null,AddItem.REDSTONE_ENGINE,"WHITE_CARPET",
                    "WHITE_CARPET","STICKY_PISTON","OBSERVER","OBSERVER","STICKY_PISTON","WHITE_CARPET",
                    null,"WHITE_CARPET","WHITE_CARPET","WHITE_CARPET","WHITE_CARPET",null),1,1000,116,
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
    public static final SlimefunItem ENDDUPE_MG=new MMGenerator(AddGroups.VANILLA, AddItem.ENDDUPE_MG,COMMON_TYPE,
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
    public static final SlimefunItem REVERSE_GENERATOR = new MMGenerator(AddGroups.GENERATORS, AddItem.REVERSE_GENERATOR, COMMON_TYPE,
            recipe(null,AddItem.SPACE_PLATE,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.SPACE_PLATE,null,
                    null,AddItem.SPACE_PLATE,AddItem.LOGIC_REACTOR,AddItem.LOGIC_REACTOR,AddItem.SPACE_PLATE,null,
                    null,AddItem.PAGOLD,AddItem.NOLOGIC,AddItem.NOLOGIC,AddItem.PAGOLD,null,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    AddItem.PAGOLD,AddItem.SPACE_PLATE,AddItem.LSINGULARITY,AddItem.LSINGULARITY,AddItem.SPACE_PLATE,AddItem.PAGOLD,
                    null,AddItem.PAGOLD,AddItem.LMOTOR,AddItem.LMOTOR,AddItem.PAGOLD,null), 1, 1_000_000, 24_000,
            new LinkedHashMap<>(){{
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
    public static final SlimefunItem VIRTUAL_MINER = new MMGenerator(AddGroups.GENERATORS, AddItem.VIRTUAL_MINER, COMMON_TYPE,
            recipe(AddItem.BISILVER,setC(AddItem.TECH_CORE,1),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.SMELERY_CORE,1),AddItem.BISILVER,
                    AddItem.LDIGITIZER,AddItem.BISILVER,AddItem.NETHER_MINER,AddItem.NETHER_MINER,AddItem.BISILVER,AddItem.LDIGITIZER,
                    AddItem.BISILVER,setC(AddItem.LMOTOR,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.LSINGULARITY,1),setC(AddItem.LMOTOR,1),AddItem.BISILVER,
                    AddItem.BISILVER,setC(AddItem.LENGINE,1),setC(AddItem.VIRTUAL_SPACE,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.LENGINE,1),AddItem.BISILVER,
                    AddItem.LIOPORT,AddItem.BISILVER,AddItem.DIMENSION_MINER,AddItem.DIMENSION_MINER,AddItem.BISILVER,AddItem.LIOPORT,
                    AddItem.BISILVER,setC(AddItem.MASS_CORE,1),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.METAL_CORE,1),AddItem.BISILVER), 1, 129_600, 5400,
            new LinkedHashMap<>(){{
                put(mkl(AddItem.WORLD_FEAT),mkl(AddUtils.randItemStackFactory(
                        mkMp("128COAL",8,
                                "128REDSTONE",8,
                                "128IRON_INGOT",8,
                                "256LAPIS_LAZULI",8,
                                "128DIAMOND",8,
                                "128EMERALD",8)
                )));
                put(mkl(AddItem.NETHER_FEAT),mkl(AddUtils.randItemStackFactory(
                        mkMp("64NETHERITE_INGOT",1,
                                "256QUARTZ",1,
                                "64MAGMA_BLOCK",1,
                                "64OBSIDIAN",1,
                                "64ANCIENT_DEBRIS",1,
                                "4NETHER_ICE",1)
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
    public static final SlimefunItem VIRTUAL_PLANT = new MMGenerator(AddGroups.GENERATORS, AddItem.VIRTUAL_PLANT, COMMON_TYPE,
            recipe(AddItem.BISILVER,setC(AddItem.TECH_CORE,1),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.SMELERY_CORE,1),AddItem.BISILVER,
                    AddItem.LDIGITIZER,AddItem.BISILVER,AddItem.NETHER_PLANT,AddItem.NETHER_PLANT,AddItem.BISILVER,AddItem.LDIGITIZER,
                    AddItem.BISILVER,setC(AddItem.LMOTOR,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.LSINGULARITY,1),setC(AddItem.LMOTOR,1),AddItem.BISILVER,
                    AddItem.BISILVER,setC(AddItem.LENGINE,1),setC(AddItem.VIRTUAL_SPACE,1),setC(AddItem.CHIP_CORE,1),setC(AddItem.LENGINE,1),AddItem.BISILVER,
                    AddItem.LIOPORT,AddItem.BISILVER,AddItem.END_PLANT,AddItem.END_PLANT,AddItem.BISILVER,AddItem.LIOPORT,
                    AddItem.BISILVER,setC(AddItem.MASS_CORE,1),AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,setC(AddItem.METAL_CORE,1),AddItem.BISILVER), 1, 129_600, 5400,
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
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),1,20_000_000,2_000,2)
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
    public static final SlimefunItem PORTAL_CORE=new PortalCore(AddGroups.SPACE,AddItem.PORTAL_CORE,COMMON_TYPE,
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
    public static final SlimefunItem SOLAR_REACTOR=new SolarReactorCore(AddGroups.SPACE,AddItem.SOLAR_REACTOR,COMMON_TYPE,
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
                                    AddUtils.probItemStackFactory( AddItem.LSINGULARITY,73),
                                    AddUtils.randAmountItemFactory(AddItem.STAR_GOLD,39,87),
                                    AddUtils.randAmountItemFactory(AddItem.ATOM_INGOT,63,99),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(setC(AddItem.PALLADIUM_INGOT,3),setC(AddItem.PLATINUM_INGOT,3),setC(AddItem.CADMIUM_INGOT,3),setC(AddItem.BISMUTH_INGOT,3)),
                                            Utils.list(37,29,13,21)
                                    )
                            )
                    ),72,
                    mkP(   mkl(AddItem.SMELERY_CORE)  ,

                            mkl(
                                    setC(AddItem.ABSTRACT_INGOT,64),
                                    AddUtils.probItemStackFactory( AddItem.LSINGULARITY,12),
                                    AddUtils.randAmountItemFactory(AddItem.DIMENSIONAL_SHARD,99,127),
                                    AddUtils.randAmountItemFactory(AddItem.STAR_GOLD,49,83),
                                    AddUtils.randAmountItemFactory(AddItem.ATOM_INGOT,37,51),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(setC(AddItem.PALLADIUM_INGOT,3),setC(AddItem.PLATINUM_INGOT,3),setC(AddItem.CADMIUM_INGOT,3),setC(AddItem.BISMUTH_INGOT,3)),
                                            Utils.list(2,59,13,24)
                                    )
                            )
                    ),72,
                    mkP(   mkl(AddItem.MASS_CORE)  ,

                            mkl(
                                    setC(AddItem.BUG,32),
                                    AddUtils.probItemStackFactory( AddItem.LSINGULARITY,97),
                                    AddUtils.randAmountItemFactory(AddItem.DIMENSIONAL_SHARD,73,127),
                                    AddUtils.randAmountItemFactory(AddItem.STAR_GOLD,39,63),
                                    AddUtils.randAmountItemFactory(AddItem.ATOM_INGOT,12,39),

                                    AddUtils.randItemStackFactory(
                                            Utils.list(setC(AddItem.PALLADIUM_INGOT,3),setC(AddItem.PLATINUM_INGOT,3),setC(AddItem.CADMIUM_INGOT,3),setC(AddItem.BISMUTH_INGOT,3)),
                                            Utils.list(27,22,15,45)
                                    )
                            )

                    ),72,
                    mkP(   mkl(AddItem.TECH_CORE)  ,

                            mkl(
                                    setC(AddItem.LPLATE,64),
                                    setC( AddItem.LSINGULARITY,2),
                                    AddUtils.randAmountItemFactory(AddItem.STAR_GOLD_INGOT,23,35),
                                    AddUtils.randAmountItemFactory(AddItem.ATOM_INGOT,92,127)
                            )

                    ),72
            )).setDisplayRecipes(
                    Utils.list(
                            AddUtils.getInfoShow("&f机制",
                                    "&7该机器需要搭建超新星外壳方可运行",
                                    "&7搭建成功后需要保证外壳内部(除机器顶部)不包含任何非空气方块",
                                    "&7才可以成功启动多方块机器",
                                    "&7在此你可以使用[%s]等远程访问工具打开内部的界面".formatted(Language.get("Items.HYPER_LINK.Name")),
                                    "&7或者开启自动构建模式让机器自行启动"),null,
                            AddUtils.getInfoShow("&f机制",
                                    "&7该机器在构建/待机/运行时候拥有相同的电力消耗量",
                                    "&7当电力不足时会强制关机",
                                    "&7注意:当你使用远程传输时,请确认机器所在区块的加载状态",
                                    "&7并确保能源网络在加载范围内并且能给机器提供足够电力"),null,
                            AddUtils.getInfoShow("&f机制",
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
    public static final  SlimefunItem TRANSMUTATOR=new Transmutator(AddGroups.ADVANCED, AddItem.TRANSMUTATOR,COMMON_TYPE,
            recipe(AddItem.TECH_CORE,AddItem.TRANSMUTATOR_ROD,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.TRANSMUTATOR_ROD,AddItem.MASS_CORE,
                    AddItem.TRANSMUTATOR_ROD,setC(AddItem.CHIP_CORE,1),setC(AddItem.LSINGULARITY,2),setC(AddItem.LSINGULARITY,2),setC(AddItem.CHIP_CORE,1),AddItem.TRANSMUTATOR_ROD,
                    AddItem.SPACE_PLATE,setC(AddItem.PAGOLD,1),"4NUCLEAR_REACTOR","4NUCLEAR_REACTOR",setC(AddItem.PAGOLD,1),AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,setC(AddItem.PAGOLD,1),"4NUCLEAR_REACTOR","4NUCLEAR_REACTOR",setC(AddItem.PAGOLD,1),AddItem.SPACE_PLATE,
                    AddItem.TRANSMUTATOR_ROD,setC(AddItem.CHIP_CORE,1),setC(AddItem.BISILVER,1),setC(AddItem.BISILVER,1),setC(AddItem.CHIP_CORE,1),AddItem.TRANSMUTATOR_ROD,
                    AddItem.METAL_CORE,AddItem.TRANSMUTATOR_ROD,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.TRANSMUTATOR_ROD,AddItem.SMELERY_CORE), "nuclear.core",
            MultiBlockTypes.NUCLEAR_REACTOR,750_000,20_000_000,
            mkMp(mkP(   mkl(setC(AddItem.ATOM_INGOT,64) )  ,
                        mkl(
                                setC(AddItem.ATOM_INGOT,128),
                                AddUtils.randItemStackFactory(
                                        Utils.list(
                                                AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,37,43),
                                                AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,23,40),
                                                AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,6,45),
                                                AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,40,54)
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

                    ),8000,
                    mkP(   mkl(setC(AddItem.BISILVER,3) )  ,

                            mkl(
                                    setC(AddItem.ATOM_INGOT,48),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,15,61),
                                                    AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,13,49),
                                                    AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,23,57),
                                                    AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,12,64)
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

                    ),10000,
                    mkP(   mkl(setC(AddItem.PAGOLD,3) )  ,

                            mkl(
                                    setC(AddItem.ATOM_INGOT,48),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,17,43),
                                                    AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,11,36),
                                                    AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,12,53),
                                                    AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,23,49)
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

                    ),10000,
                    mkP(   mkl(setC(AddItem.PLATINUM_INGOT,24) )  ,

                            mkl(
                                    setC(AddItem.STAR_GOLD_INGOT,128),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,23,53),
                                                    AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,32,40),
                                                    AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,23,35),
                                                    AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,13,58)
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

                    ),10000,
                    mkP(   mkl(setC(AddItem.CADMIUM_INGOT,24) )  ,

                            mkl(
                                    setC(AddItem.STAR_GOLD_INGOT,128),
                                    AddUtils.randItemStackFactory(
                                            Utils.list(
                                                    AddUtils.randAmountItemFactory(AddItem.PALLADIUM_INGOT,17,43),
                                                    AddUtils.randAmountItemFactory(AddItem.PLATINUM_INGOT,13,50),
                                                    AddUtils.randAmountItemFactory(AddItem.CADMIUM_INGOT,6,55),
                                                    AddUtils.randAmountItemFactory(AddItem.BISMUTH_INGOT,20,34)
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

                    ),10000
            )
            ).setDisplayRecipes(
                    Utils.list(
                            AddUtils.getInfoShow("&f机制",
                                    "&7该机器需要搭建超新星外壳方可运行",
                                    "&7该多方块为可变高度多方块机器",
                                    "&7点击开启投影只会显示5蹭机器",
                                    "&7将从下往上数第4层反复搭建即可增高多方块机器",
                                    "&7最多可重复搭建10层",
                                    "&a每多搭建一层该机器并行处理数x2(即机器快一倍)"),null,
                            AddUtils.getInfoShow("&f机制",
                                    "&7该机器在构建/待机/运行时候拥有相同的电力消耗量",
                                    "&7当电力不足时会强制关机",
                                    "&7注意:当你使用远程传输时,请确认机器所在区块的加载状态",
                                    "&7并确保能源网络在加载范围内并且能给机器提供足够电力"),null,
                            AddUtils.getInfoShow("&f机制",
                                    "&7机器自动关闭且&e进程未结束时&c会发生&c熔毁",
                                    "&7熔毁会形成范围40的辐射圈持续约1小时",
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



    //
    //manuals
    public static final SlimefunItem MANUAL_CORE=new MaterialItem(AddGroups.MANUAL,AddItem.MANUAL_CORE,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("SAND","COBBLESTONE","GRAVEL",
                    "SAND","COBBLESTONE","GRAVEL",
                    "SAND","COBBLESTONE","GRAVEL"),null).setOutput(setC(AddItem.MANUAL_CORE,8))
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
            recipe("COBBLESTONE","COBBLESTONE","COBBLESTONE",
                    null,"CRAFTING_TABLE",null,
                    "COBBLESTONE","COBBLESTONE","COBBLESTONE"),0,0,RecipeType.ENHANCED_CRAFTING_TABLE)
            .register();
    public static final SlimefunItem GRIND_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.GRIND_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.BUG,null,
                    null,"OAK_FENCE",null,
                    null,"DISPENSER",null),0,0,RecipeType.GRIND_STONE)
            .register();
    public static final SlimefunItem ARMOR_FORGE_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.ARMOR_FORGE_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.BUG,null,
                    null,"ANVIL",null,
                    null,"DISPENSER",null),0,0,RecipeType.ARMOR_FORGE)
            .register();
    public static final SlimefunItem ORE_CRUSHER_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.ORE_CRUSHER_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,AddItem.BUG,null,
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
            recipe(
                    "COBBLESTONE",null,"COBBLESTONE",
                    "BOOKSHELF","CRAFTING_TABLE","DISPENSER",
                    "COBBLESTONE",null,"COBBLESTONE"),0,0,RecipeType.MAGIC_WORKBENCH)
            .register();
    public static final SlimefunItem ORE_WASHER_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.ORE_WASHER_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,"DISPENSER",AddItem.BUG,
            null,"OAK_FENCE",null,
                    AddItem.BUG,"CAULDRON",AddItem.BUG),0,0,RecipeType.ORE_WASHER)
            .register();
    public static final SlimefunItem GOLD_PAN_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.GOLD_PAN_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,null,AddItem.BUG,
                   null,"OAK_TRAPDOOR",null,
                    AddItem.BUG,"CAULDRON",AddItem.BUG),0,0,RecipeType.GOLD_PAN)
            .register();
    public static final SlimefunItem ANCIENT_ALTAR_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.ANCIENT_ALTAR_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("ANCIENT_PEDESTAL","ANCIENT_PEDESTAL","ANCIENT_PEDESTAL",
                    "ANCIENT_PEDESTAL","ANCIENT_ALTAR","ANCIENT_PEDESTAL",
                    "ANCIENT_PEDESTAL","ANCIENT_PEDESTAL","ANCIENT_PEDESTAL"),0,0,RecipeType.ANCIENT_ALTAR)
            .register();
    public static final SlimefunItem SMELTERY_MANUAL=new ManualCrafter(AddGroups.MANUAL,AddItem.SMELTERY_MANUAL,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,"NETHER_BRICK_FENCE",null,
                    "NETHER_BRICKS","DISPENSER","NETHER_BRICKS",
                    AddItem.BUG,AddItem.BUG,AddItem.BUG),0,0,RecipeType.SMELTERY)
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
                    "OAK_SLAB",AddItem.BUG,"OAK_SLAB",
                    "OAK_SLAB","CAULDRON","OAK_SLAB"),0,0,()->{
                return RecipeSupporter.MACHINE_RECIPELIST.get(SlimefunItems.COMPOSTER.getItem());
            })
            .register();
    public static final SlimefunItem MULTIMACHINE_MANUAL=new ManualMachine(AddGroups.MANUAL,AddItem.MULTIMACHINE_MANUAL,COMMON_TYPE,
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
    public static final SlimefunItem MULTIBLOCK_MANUAL=new MultiBlockManual(AddGroups.MANUAL,AddItem.MULTIBLOCK_MANUAL,COMMON_TYPE,
            recipe(AddItem.STAR_GOLD_INGOT,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.STAR_GOLD_INGOT,
                    AddItem.BUG,AddItem.LFIELD,"OAK_FENCE","OAK_FENCE",AddItem.LFIELD,AddItem.BUG,
                    AddItem.BUG,AddItem.LCRAFT,AddItem.LSINGULARITY,AddItem.CHIP_CORE,AddItem.LCRAFT,AddItem.BUG,
                    AddItem.BUG,AddItem.WORLD_FEAT,AddItem.VIRTUAL_SPACE,AddItem.MANUAL_CORE,AddItem.WORLD_FEAT,AddItem.BUG,
                    AddItem.BUG,AddItem.LFIELD,"DISPENSER","DISPENSER",AddItem.LFIELD,AddItem.BUG,
                    AddItem.STAR_GOLD_INGOT,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.BUG,AddItem.STAR_GOLD_INGOT),0,0,null)
            .register();




    //cargo items
    public static final SlimefunItem CARGO_PART=new MaterialItem(AddGroups.CARGO,AddItem.CARGO_PART,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("CARGO_NODE","CARGO_NODE_OUTPUT","CARGO_NODE"
                    ,AddItem.FALSE_,null,AddItem.TRUE_,
                    "CARGO_NODE","CARGO_NODE_INPUT","CARGO_NODE"),null)
            .setOutput(setC(AddItem.CARGO_PART,64)).register();
    public static final SlimefunItem CARGO_CONFIG=new ConfigCard(AddGroups.CARGO,AddItem.CARGO_CONFIG,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.PARADOX,AddItem.CARGO_PART,AddItem.PARADOX,
                    AddItem.NOLOGIC,AddItem.BUG,AddItem.LOGIC,
                    AddItem.PARADOX,AddItem.CARGO_PART,AddItem.PARADOX))
            .register();
    public static final SlimefunItem CARGO_CONFIGURATOR=new CargoConfigurator(AddGroups.CARGO,AddItem.CARGO_CONFIGURATOR,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.BUG,AddItem.ABSTRACT_INGOT,AddItem.BUG,AddItem.ABSTRACT_INGOT,AddItem.BUG,AddItem.ABSTRACT_INGOT,
                    "OAK_PLANKS","CARTOGRAPHY_TABLE","OAK_PLANKS"))
            .register();
    public static final SlimefunItem ADV_TRASH=new TrashCan(AddGroups.CARGO,AddItem.ADV_TRASH,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("TRASH_CAN_BLOCK",AddItem.LOGIC,"TRASH_CAN_BLOCK",AddItem.LOGIC,"COAL_GENERATOR",AddItem.LOGIC,
                    "TRASH_CAN_BLOCK",AddItem.LOGIC,"TRASH_CAN_BLOCK"))
            .register();
    public static final SlimefunItem SIMPLE_CARGO=new AdjacentCargo(AddGroups.CARGO,AddItem.SIMPLE_CARGO,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,"HOPPER",AddItem.ABSTRACT_INGOT,
                    null,AddItem.CARGO_PART ,null,
                    AddItem.ABSTRACT_INGOT,"IRON_INGOT",AddItem.ABSTRACT_INGOT),
            list(AddUtils.getInfoShow("&f机制","")))
            .register();
    public static final SlimefunItem REMOTE_CARGO=new RemoteCargo(AddGroups.CARGO,AddItem.REMOTE_CARGO,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.HYPER_LINK,AddItem.ABSTRACT_INGOT
                    ,"CARGO_NODE",AddItem.CARGO_PART,"CARGO_NODE",
                    AddItem.ABSTRACT_INGOT,AddItem.PARADOX,AddItem.ABSTRACT_INGOT),
            null)
            .register();
    public static final SlimefunItem LINE_CARGO=new LineCargo(AddGroups.CARGO,AddItem.LINE_CARGO,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,"HOPPER",AddItem.ABSTRACT_INGOT,AddItem.NOLOGIC,AddItem.CARGO_PART,AddItem.NOLOGIC,
                    AddItem.ABSTRACT_INGOT,"HOPPER",AddItem.ABSTRACT_INGOT),null)
            .register();
    public static final  SlimefunItem BISORTER=new BiSorter(AddGroups.CARGO, AddItem.BISORTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.CARGO_PART,AddItem.CARGO_PART,AddItem.PARADOX,null,
                    setC(AddItem.PARADOX,1),AddItem.LBOOLIZER,"HOPPER","HOPPER",AddItem.LBOOLIZER,setC(AddItem.PARADOX,1),
                    setC(AddItem.PARADOX,1),AddItem.LBOOLIZER,AddItem.LSCHEDULER,"HOPPER",AddItem.LBOOLIZER,setC(AddItem.PARADOX,1),
                    null,AddItem.PARADOX,AddItem.CARGO_PART,AddItem.CARGO_PART,AddItem.PARADOX,null))
            .register();
    public static final SlimefunItem QUARSORTER=new QuarSorter(AddGroups.CARGO,AddItem.QUARSORTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.LFIELD,AddItem.LFIELD,AddItem.PARADOX,null,
                    setC(AddItem.PARADOX,1),AddItem.CARGO_PART,"2HOPPER","HOPPER",AddItem.CARGO_PART,setC(AddItem.PARADOX,1),
                    setC(AddItem.PARADOX,1),AddItem.CARGO_PART,AddItem.BISORTER,"2HOPPER",AddItem.CARGO_PART,setC(AddItem.PARADOX,1),
                    null,AddItem.PARADOX,AddItem.LFIELD,AddItem.LFIELD,AddItem.PARADOX,null
                    ))
            .register();
    public static final SlimefunItem OCTASORTER=new OctaSorter(AddGroups.CARGO,AddItem.OCTASORTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,AddItem.STAR_GOLD_INGOT,AddItem.STAR_GOLD_INGOT,AddItem.PARADOX,null,
                    setC(AddItem.PARADOX,1),"HOPPER",setC(AddItem.LBOOLIZER,1),AddItem.LSCHEDULER,"HOPPER",setC(AddItem.PARADOX,1),
                    setC(AddItem.PARADOX,1),"HOPPER",AddItem.QUARSORTER,setC(AddItem.LBOOLIZER,1),"HOPPER",setC(AddItem.PARADOX,1),
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
    public static final  SlimefunItem INPORT=new InputPort(AddGroups.SINGULARITY, AddItem.INPORT,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.PARADOX,"CHEST","CHEST",AddItem.PARADOX,null,
                    null,"CHEST",AddItem.UNIQUE,AddItem.EXISTE,"CHEST",null,
                    null,"CHEST",AddItem.LOGIC,AddItem.NOLOGIC,"CHEST",null,
                    null,AddItem.PARADOX,"CHEST","CHEST",AddItem.PARADOX,null
                    ))
            .register();
    public static final  SlimefunItem OUTPORT=new OutputPort(AddGroups.SINGULARITY, AddItem.OUTPORT,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null,
                    null,"CHEST",AddItem.EXISTE,AddItem.UNIQUE,"CHEST",null,
                    null,"CHEST",AddItem.NOLOGIC,AddItem.LOGIC,"CHEST",null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null
                    ))
            .register();
    public static final SlimefunItem IOPORT=new IOPort(AddGroups.SINGULARITY,AddItem.IOPORT,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null,
                    null,"CHEST",AddItem.PARADOX,AddItem.PARADOX,"CHEST",null,
                    null,"CHEST",AddItem.PARADOX,AddItem.PARADOX,"CHEST",null,
                    null,AddItem.ABSTRACT_INGOT,"CHEST","CHEST",AddItem.ABSTRACT_INGOT,null
            ))
            .register();




//TODO add descriptions to these shits


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
    public static final SlimefunItem ANTIMASS=new SpreadBlock(AddGroups.BEYOND,AddItem.ANTIMASS,STARSMELTERY,
            recipe(setC(AddItem.LOGIC_CORE,9),setC(AddItem.VIRTUAL_SPACE,64),"64ENERGIZED_CAPACITOR",setC(AddItem.PARADOX,64),setC(AddItem.FINAL_FRAME,3)),LOGIC_CORE,Material.COMMAND_BLOCK,Material.SCULK)
            .register();
    public static final  SlimefunItem FINAL_LASER=new Laser(AddGroups.BEYOND, AddItem.FINAL_LASER,COMMON_TYPE,
            recipe(setC(AddItem.SPACE_PLATE,1),setC(AddItem.HGTLPBBI,4),setC(AddItem.TECH_CORE,2),setC(AddItem.TECH_CORE,2),setC(AddItem.HGTLPBBI,4),setC(AddItem.SPACE_PLATE,1),
                    setC(AddItem.SPACE_PLATE,1),setC(AddItem.HGTLPBBI,4),setC(AddItem.TECH_CORE,2),setC(AddItem.TECH_CORE,2),setC(AddItem.HGTLPBBI,4),setC(AddItem.SPACE_PLATE,1),
                    setC(AddItem.LSINGULARITY,4),setC(AddItem.LASER,2),setC(AddItem.VIRTUAL_SPACE,2),setC(AddItem.VIRTUAL_SPACE,2),setC(AddItem.LASER,2),setC(AddItem.LSINGULARITY,4),
                    setC(AddItem.LSINGULARITY,4),setC(AddItem.LASER,2),setC(AddItem.VIRTUAL_SPACE,2),setC(AddItem.VIRTUAL_SPACE,2),setC(AddItem.LASER,2),setC(AddItem.LSINGULARITY,4),
                    setC(AddItem.PAGOLD,3),setC(AddItem.BISILVER,2),setC(AddItem.LASER,2),setC(AddItem.LASER,2),setC(AddItem.BISILVER,2),setC(AddItem.PAGOLD,3),
                    null,setC(AddItem.PAGOLD,3),setC(AddItem.LSINGULARITY,4),setC(AddItem.LSINGULARITY,4),setC(AddItem.PAGOLD,3),null), 8_000_000,1_200_000,"final.sub")
            .register();
    public static final SlimefunItem FINAL_BASE=new MultiPart(AddGroups.BEYOND,AddItem.FINAL_BASE,STARSMELTERY,
            recipe(setC(AddItem.LOGIC_CORE,16)),"final.base")
            .register();
    public static final SlimefunItem FINAL_ALTAR=new FinalAltarCore(AddGroups.BEYOND,AddItem.FINAL_ALTAR,STARSMELTERY,
            recipe(setC(AddItem.HGTLPBBI,16),AddItem.FINAL_FRAME),"final.core")
            .register();
    public static final  SlimefunItem FINAL_SEQUENTIAL=new FinalSequenceConstructor(AddGroups.BEYOND, AddItem.FINAL_SEQUENTIAL,COMMON_TYPE,
            recipe(AddItem.STACKFRAME,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.PDCECDMD,AddItem.STACKFRAME,
                    AddItem.PDCECDMD,setC(AddItem.SPACE_PLATE,2),AddItem.STORAGE_SINGULARITY,AddItem.STORAGE_SINGULARITY,setC(AddItem.SPACE_PLATE,2),AddItem.PDCECDMD,
                    setC(AddItem.SPACE_PLATE,2),AddItem.LIOPORT,AddItem.SEQ_CONSTRUCTOR,AddItem.SEQ_CONSTRUCTOR,AddItem.LIOPORT,setC(AddItem.SPACE_PLATE,2),
                    setC(AddItem.SPACE_PLATE,2),setC(AddItem.VIRTUAL_SPACE,32),setC(AddItem.LSINGULARITY,64),setC(AddItem.LSINGULARITY,64),setC(AddItem.VIRTUAL_SPACE,32),setC(AddItem.SPACE_PLATE,2),
                    AddItem.HGTLPBBI,setC(AddItem.SPACE_PLATE,2),setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.SPACE_PLATE,2)
                    ,AddItem.HGTLPBBI, AddItem.STACKFRAME,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.HGTLPBBI,AddItem.STACKFRAME), AddUtils.addGlow( new ItemStack(Material.FIRE_CHARGE)),            10240,114514,
            mkMp(
                    mkP(mkl(setC(AddItem.MASS_CORE,128),setC(AddItem.SMELERY_CORE,128),setC(AddItem.HGTLPBBI,128),
                            setC(AddItem.SINGULARITY,64),setC(AddItem.VIRTUAL_SPACE,64),setC(AddItem.PDCECDMD,128),
                            setC(AddItem.TECH_CORE,128),setC(AddItem.METAL_CORE,128)),mkl(setC(AddItem.FINAL_FRAME,9))),2,
                    mkP(mkl("1919810IRON_DUST","1919810GOLD_DUST","1919810COPPER_DUST",
                            "1919810TIN_DUST","1919810SILVER_DUST","1919810LEAD_DUST",
                            "1919810ALUMINUM_DUST","1919810ZINC_DUST","1919810MAGNESIUM_DUST"),mkl(AddItem.LOGIC_CORE)),2
            ))
            .register();
    public static final SlimefunItem FINAL_STONE_MG=new FinalMGenerator(AddGroups.BEYOND, AddItem.FINAL_STONE_MG,COMMON_TYPE,
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

    public static final SlimefunItem FINAL_MANUAL=new FinalManual(AddGroups.BEYOND,AddItem.FINAL_MANUAL,COMMON_TYPE,
            recipe(setC(AddItem.STACKFRAME,2),AddItem.CRAFT_MANUAL,AddItem.FURNACE_MANUAL,AddItem.ENHANCED_CRAFT_MANUAL,AddItem.GRIND_MANUAL,setC(AddItem.STACKFRAME,2),
                    AddItem.ORE_CRUSHER_MANUAL,null,setC(AddItem.LASER,2),setC(AddItem.LASER,2),null,AddItem.TABLESAW_MANUAL,
                    AddItem.PRESSURE_MANUAL,setC(AddItem.VIRTUAL_SPACE,16),setC(AddItem.MANUAL_CORE,64),setC(AddItem.LSINGULARITY,64),setC(AddItem.VIRTUAL_SPACE,16),AddItem.COMPRESSOR_MANUAL,
                    AddItem.CRUCIBLE_MANUAL,setC(AddItem.VIRTUAL_SPACE,16),AddItem.FINAL_FRAME,AddItem.FINAL_FRAME,setC(AddItem.VIRTUAL_SPACE,16),AddItem.SMELTERY_MANUAL,
                    AddItem.GOLD_PAN_MANUAL,null,setC(AddItem.LASER,2),setC(AddItem.LASER,2),null,AddItem.ANCIENT_ALTAR_MANUAL,
                    setC(AddItem.STACKFRAME,2),AddItem.MAGIC_WORKBENCH_MANUAL,AddItem.COMPOSTER,AddItem.ARMOR_FORGE_MANUAL,AddItem.ORE_WASHER_MANUAL,setC(AddItem.STACKFRAME,2)),0,0)
            .register();
    //TODO 完成反概念物质的扩散 变化和掉落

    public static final  SlimefunItem FINAL_CONVERTOR=new FinalConvertor(AddGroups.BEYOND, AddItem.FINAL_CONVERTOR,COMMON_TYPE,
            recipe(AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.FINAL_FRAME,AddItem.FINAL_FRAME,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,AddItem.PDCECDMD,AddItem.END_FEAT,AddItem.END_FEAT,AddItem.PDCECDMD,AddItem.SPACE_PLATE,
                    AddItem.LOGIC_CORE,AddItem.PDCECDMD,AddItem.NETHER_FEAT,AddItem.NETHER_FEAT,AddItem.PDCECDMD,AddItem.LOGIC_CORE,
                    AddItem.LOGIC_CORE,AddItem.PDCECDMD,AddItem.WORLD_FEAT,AddItem.WORLD_FEAT,AddItem.PDCECDMD,AddItem.LOGIC_CORE,
                    AddItem.SPACE_PLATE,AddItem.PDCECDMD,AddItem.FINAL_FRAME,AddItem.FINAL_FRAME,AddItem.PDCECDMD,AddItem.SPACE_PLATE,
                    AddItem.SPACE_PLATE,AddItem.SPACE_PLATE,AddItem.LOGIC_CORE,AddItem.LOGIC_CORE,AddItem.SPACE_PLATE,AddItem.SPACE_PLATE), 840_000_000,100_000_000,
            AddUtils.randItemStackFactory(
                    Utils.list(
                            AddUtils.probItemStackFactory(setC(AddItem.VIRTUALWORLD,1),100),
                            AddUtils.probItemStackFactory( setC(  AddItem.STAR_GOLD_INGOT,64),20),
                            AddUtils.probItemStackFactory(  setC(AddItem.ATOM_INGOT,64),10),
                            AddUtils.probItemStackFactory(setC(AddItem.VIRTUALWORLD,1),20),
                            AddItem.ANTIMASS
                    ),
                    Utils.list(
                            1,1,1,1,1
                    )
            ))
            .register();

    public static final  SlimefunItem FINAL_STACKMACHINE=new FinalStackMachine(AddGroups.BEYOND, AddItem.FINAL_STACKMACHINE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")), Material.STONE,100,200_000_000,64)
            .register();
    public static final SlimefunItem FINAL_STACKMGENERATOR=new FinalStackMGenerator(AddGroups.BEYOND, AddItem.FINAL_STACKMGENERATOR,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.ENDFRAME_MACHINE,Language.get("Machines.ENDFRAME_MACHINE.Name")),1,200_000_000,100,
            32)
            .register();
    public static final  SlimefunItem RAND_EDITOR=new RandomEditor(AddGroups.BEYOND, AddItem.RAND_EDITOR,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")), 200_000_000,25_000_000)
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
            CommandShell.setup(event.getPlayer(),"0");
        }
    }
            .register();



//    public static final SlimefunItem CUSTOM1=register(new FIrstCustomItem(AddGroups.MATERIAL, AddItem.CUSTOM1,COMMON_TYPE,
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
