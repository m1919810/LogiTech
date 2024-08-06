package me.matl114.logitech.SlimefunItem;

import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.sefiraat.networks.slimefun.NetworksSlimefunItemStacks;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.VanillaItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Language;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.SlimefunItem.Items.*;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.*;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.FinalManual;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualCrafter;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualMachine;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.MultiBlockManual;
import me.matl114.logitech.SlimefunItem.Machines.SpecialMachines.*;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.TestWorkBench;
import me.matl114.logitech.SlimefunItem.Storage.StorageMachines.InputPort;
import me.matl114.logitech.SlimefunItem.Storage.StorageMachines.OutputPort;
import me.matl114.logitech.SlimefunItem.Storage.StorageMachines.Singularity;
import me.matl114.logitech.SlimefunItem.Storage.TestStorageUnit;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.BukkitUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.RecipeSupporter;
import me.matl114.logitech.Utils.UtilClass.EqProRandomStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Type;
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
    private static Object mkP(Object v1,Object v2){
        return new Pair(v1,v2);
    }
    private static Object[] mkl(Object ... v){
        return Arrays.stream(v).toArray();
    }
    private static ItemStack[] recipe(Object ... v){
        return Arrays.stream(v).map(AddUtils::resolveItem).toArray(ItemStack[]::new);
    }
    private static <T extends Object> List<T> list(T ... input){
        return Arrays.asList(input);
    }
    private static <T extends Object,Z extends Object> Pair<T,Z> pair(T v1,Z v2){
        return new Pair(v1,v2);
    }
    private static ItemStack setC(ItemStack it,int amount){
        return new CustomItemStack(it,amount);
    }
    private static ItemStack[] nullRecipe(){
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

    public static final SlimefunItem MATL114=new CustomSlimefunItem(AddGroups.MATERIAL, AddItem.MATL114, RecipeType.NULL,
            recipe(AddItem.BUG,null,AddItem.BUG,null,null,null,AddItem.BUG,null,AddItem.BUG)
            ).register();
    public static final SlimefunItem BUG= new MaterialItem(AddGroups.MATERIAL,AddItem.BUG,RecipeType.NULL,
            nullRecipe(),list(AddUtils.getInfoShow("&f获取方式","&7会出现在一些隐蔽地方...","&7又不是什么重要材料","&7还是不要想这玩意了")))
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


    public static final SlimefunItem TMP1=new MaterialItem(AddGroups.MATERIAL,AddItem.TMP1,RecipeType.NULL,
            AddUtils.NULL_RECIPE.clone()).register();
        //generated
    public static final SlimefunItem EXISTE=new MaterialItem(AddGroups.MATERIAL,AddItem.EXISTE,RecipeType.NULL,
                AddUtils.formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem UNIQUE=new MaterialItem(AddGroups.MATERIAL,AddItem.UNIQUE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.LOGIC_REACTOR,Language.get("Machines.LOGIC_REACTOR.Name")),null)
            .register();
    public static final SlimefunItem PARADOX=new MaterialItem(AddGroups.MATERIAL,AddItem.PARADOX,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
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
            .setOutput(setC(AddItem.LFIELD,13))
            .register();



    public static final SlimefunItem LSCHEDULER=new MaterialItem(AddGroups.MATERIAL,AddItem.LSCHEDULER,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem LCRAFT=new MaterialItem(AddGroups.MATERIAL,AddItem.LCRAFT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
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
                    "HEATING_COIL",setC(AddItem.LBOOLIZER,2),"5BATTERY",AddItem.LOGIGATE,setC(AddItem.LBOOLIZER,2),"HEATING_COIL",
                    "HEATING_COIL",setC(AddItem.LBOOLIZER,2),AddItem.LENGINE,"4BATTERY",setC(AddItem.LBOOLIZER,2),"HEATING_COIL",
                    AddItem.LFIELD,"ELECTRIC_MOTOR",setC(AddItem.LBOOLIZER,2),setC(AddItem.LBOOLIZER,2),"ELECTRIC_MOTOR",AddItem.LFIELD,
                    AddItem.NOLOGIC,AddItem.LFIELD,"HEATING_COIL","HEATING_COIL",AddItem.LFIELD,AddItem.NOLOGIC),null)
            .register();
    public static final SlimefunItem LDIGITIZER=new MaterialItem(AddGroups.MATERIAL,AddItem.LDIGITIZER,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem LIOPORT=new MaterialItem(AddGroups.MATERIAL,AddItem.LIOPORT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem PALLADIUM_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.PALLADIUM_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem PLATINUM_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.PLATINUM_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem MOLYBDENUM=new MaterialItem(AddGroups.MATERIAL,AddItem.MOLYBDENUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem CERIUM=new MaterialItem(AddGroups.MATERIAL,AddItem.CERIUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem CADMIUM_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.CADMIUM_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem MENDELEVIUM=new MaterialItem(AddGroups.MATERIAL,AddItem.MENDELEVIUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem DYSPROSIUM=new MaterialItem(AddGroups.MATERIAL,AddItem.DYSPROSIUM,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem BISMUTH_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.BISMUTH_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem ANTIMONY_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.ANTIMONY_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem BORON=new MaterialItem(AddGroups.MATERIAL,AddItem.BORON,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    //alloy
    public static final SlimefunItem CHIP_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.CHIP_INGOT,RecipeType.SMELTERY,
            recipe("12SILVER_INGOT","6REINFORCED_ALLOY_INGOT","16COPPER_INGOT",
                    new CustomItemStack(SlimefunItems.COPPER_INGOT,12),"6SILICON","16ALUMINUM_INGOT"))
            .register();
    public static final SlimefunItem ABSTRACT_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.ABSTRACT_INGOT,RecipeType.SMELTERY,
            recipe(AddItem.TRUE_,AddItem.EXISTE,AddItem.LOGIC,AddItem.NOLOGIC,AddItem.UNIQUE,AddItem.FALSE_))
            .register();
    public static final SlimefunItem PDCECDMD=new MaterialItem(AddGroups.MATERIAL,AddItem.PDCECDMD,RecipeType.SMELTERY,
            recipe("PLUTONIUM",AddItem.CERIUM,AddItem.CADMIUM_INGOT,AddItem.MENDELEVIUM),null)
            .register();
    public static final SlimefunItem REINFORCED_CHIP_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.REINFORCED_CHIP_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem ATOM_INGOT=new MaterialItem(AddGroups.MATERIAL,AddItem.ATOM_INGOT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();

    public static final SlimefunItem DIMENSIONAL_SHARD=new MaterialItem(AddGroups.MATERIAL,AddItem.DIMENSIONAL_SHARD,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem STAR_GOLD=new MaterialItem(AddGroups.MATERIAL,AddItem.STAR_GOLD,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem VIRTUAL_SPACE=new MaterialItem(AddGroups.MATERIAL,AddItem.VIRTUAL_SPACE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem WORLD_FEAT=new MaterialItem(AddGroups.MATERIAL,AddItem.WORLD_FEAT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem NETHER_FEAT=new MaterialItem(AddGroups.MATERIAL,AddItem.NETHER_FEAT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem END_FEAT=new MaterialItem(AddGroups.MATERIAL,AddItem.END_FEAT,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();


    public static final SlimefunItem LPLATE=new MaterialItem(AddGroups.MATERIAL,AddItem.LPLATE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem METAL_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.METAL_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem SMELERY_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.SMELERY_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem MASS_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.MASS_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem TECH_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.TECH_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem SPACE_PLATE=new MaterialItem(AddGroups.MATERIAL,AddItem.SPACE_PLATE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();
    public static final SlimefunItem LOGIC_CORE=new MaterialItem(AddGroups.MATERIAL,AddItem.LOGIC_CORE,RecipeType.NULL,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),null)
            .register();

    //machines
    public static final SlimefunItem BOOL_GENERATOR=new BoolGenerator(AddGroups.BASIC,AddItem.BOOL_GENERATOR,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("OBSERVER","REDSTONE","OBSERVER",
                    "REDSTONE_TORCH","SILICON","SILICON",
                    "STEEL_INGOT","MEDIUM_CAPACITOR","STEEL_INGOT"),Material.RECOVERY_COMPASS,9)
            .register();
    public static final SlimefunItem LOGIC_REACTOR=new LogicReactor(AddGroups.BASIC,AddItem.LOGIC_REACTOR,RecipeType.ANCIENT_ALTAR,
            recipe(AddItem.LOGIGATE,"COMPARATOR",AddItem.LOGIGATE,
                    "HEATING_COIL" , AddItem.TRUE_,"HEATING_COIL",
                    "REINFORCED_PLATE","CARBONADO_EDGED_CAPACITOR","REINFORCED_PLATE"
                    ),Material.COMPARATOR,7)
            .register();
    public static final SlimefunItem BUG_CRAFTER=new BugCrafter(AddGroups.BASIC,AddItem.BUG_CRAFTER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(AddItem.ABSTRACT_INGOT,AddItem.LOGIGATE,AddItem.ABSTRACT_INGOT,
                    AddItem.CHIP_INGOT,AddItem.BUG,AddItem.CHIP_INGOT,
                    AddItem.ABSTRACT_INGOT,"ENERGIZED_CAPACITOR",AddItem.ABSTRACT_INGOT),10_000,1_000,7)
            .register();
    public static final SlimefunItem HEAD_ANALYZER= new HeadAnalyzer(AddGroups.SPECIAL,AddItem.HEAD_ANALYZER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"PLAYER_HEAD",BUG,"PLAYER_HEAD",null,null,null)
            ).register();
    public static final SlimefunItem RECIPE_LOGGER=new RecipeLogger(AddGroups.SPECIAL,AddItem.RECIPE_LOGGER,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"CRAFTING_TABLE",BUG,Material.WRITABLE_BOOK,null,null))
            .register();
    //
    public static final SlimefunItem MANUAL_CORE=new MaterialItem(AddGroups.MANUAL,AddItem.MANUAL_CORE,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("GRANITE",AddItem.TRUE_,"GRANITE",
                    "DIORITE",AddItem.FALSE_,"DIORITE",
                    "ANDESITE",AddItem.TRUE_,"ANDESITE"),null)
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
            recipe(null,"ELECTRIC_ORE_GRINDER","",
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

    //Material Generators
    public static final SlimefunItem MAGIC_STONE=new SMGenerator(AddGroups.GENERATORS, AddItem.MAGIC_STONE,RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe("DIAMOND_PICKAXE","LAVA_BUCKET","DIAMOND_PICKAXE",
                        "PISTON","REDSTONE_TORCH","PISTON",
                        "IRON_PICKAXE","WATER_BUCKET","IRON_PICKAXE"),7,1000,33,
            AddUtils.randItemStackFactory(
                    mkMp("2COBBLESTONE",72,
                            "4COAL",7,
                            "8REDSTONE",6,
                            "3IRON_INGOT",5,
                            "10LAPIS_LAZULI",4,
                            "2GOLD_INGOT",3,
                            "DIAMOND",2,
                            "EMERALD",1
                            )
            ))
            .register();
    public static final SlimefunItem BOOL_MG = new MMGenerator(AddGroups.GENERATORS, AddItem.BOOL_MG, BugCrafter.TYPE,
            recipe(null,AddItem.FALSE_,AddItem.LFIELD,AddItem.LFIELD,AddItem.FALSE_,null,
                    AddItem.TRUE_,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.TRUE_,
                    AddItem.LFIELD,AddItem.ABSTRACT_INGOT,setC(AddItem.LBOOLIZER,47),setC(AddItem.LOGIGATE,31),AddItem.ABSTRACT_INGOT,AddItem.LFIELD,
                    AddItem.LFIELD,AddItem.ABSTRACT_INGOT,setC(AddItem.LOGIGATE,33),setC(AddItem.NOLOGIC,63),AddItem.ABSTRACT_INGOT,AddItem.LFIELD,
                    AddItem.TRUE_,AddItem.LFIELD,AddItem.ABSTRACT_INGOT,AddItem.ABSTRACT_INGOT,AddItem.LFIELD,AddItem.TRUE_,
                    null,AddItem.FALSE_,AddItem.LFIELD,AddItem.LFIELD,AddItem.FALSE_,null), 9, 114514, 999,
            new LinkedHashMap<>(){{
                put(mkl(AddItem.TRUE_),mkl(setC(AddItem.TRUE_,114514)));
                put(mkl(AddItem.FALSE_),mkl(setC(AddItem.FALSE_,1919810)));
                put(mkl(AddItem.LBOOLIZER),mkl(setC(AddItem.LBOOLIZER,1)));
            }})
            .register();
    public static final SlimefunItem OVERWORLD_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.OVERWORLD_MINER,RecipeType.ENHANCED_CRAFTING_TABLE,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),1,0,0,
            null)
            .register();
    public static final SlimefunItem NETHER_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.NETHER_MINER,RecipeType.ENHANCED_CRAFTING_TABLE,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),1,0,0,
            null)
            .register();
    public static final SlimefunItem END_STONE_BRICKS=new SMGenerator(AddGroups.GENERATORS, AddItem.END_STONE_BRICKS,RecipeType.ENHANCED_CRAFTING_TABLE,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),1,0,0,
            null)
            .register();
    public static final SlimefunItem DIMENSION_MINER=new SMGenerator(AddGroups.GENERATORS, AddItem.DIMENSION_MINER,RecipeType.ENHANCED_CRAFTING_TABLE,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),1,0,0,
            null)
            .register();
    public static final SlimefunItem REDSTONE_MG=new SMGenerator(AddGroups.VANILLA, AddItem.REDSTONE_MG,RecipeType.ENHANCED_CRAFTING_TABLE,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),5,10000,1234,
            AddUtils.randItemStackFactory(
                    mkMp("REDSTONE_TORCH",16,"OBSERVER",16,"PISTON",16,"STICKY_PISTON",16,"REPEATER",16,"COMPARATOR",16,"LEVER",16,"NOTE_BLOCK",16,"REDSTONE_LAMP",16,
                            AddUtils.randItemStackFactory(mkMp("SLIME_BALL",26,"HONEY_BLOCK",3,"TNT",2,"REDSTONE",1)),16)
            ))
            .register();
    public static final SlimefunItem DUPE_MG=new MMGenerator(AddGroups.VANILLA, AddItem.DUPE_MG,RecipeType.ENHANCED_CRAFTING_TABLE,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),2,0,0,
            new LinkedHashMap<>(){{
                put(mkl("STRING"),mkl("STRING"));
                put(mkl("RAIL"),mkl("RAIL"));
                put(mkl("POWERED_RAIL"),mkl("POWERED_RAIL"));
                put(mkl("DETECTOR_RAIL"),mkl("DETECTOR_RAIL"));
                put(mkl("ACTIVATOR_RAIL"),mkl("ACTIVATOR_RAIL"));
                put(mkl(Material.BLACK_CARPET),mkl(Material.BLACK_CARPET));
                put(mkl(Material.RED_CARPET),mkl(Material.RED_CARPET));
                put(mkl(Material.ORANGE_CARPET),mkl(Material.ORANGE_CARPET));
                put(mkl(Material.YELLOW_CARPET),mkl(Material.YELLOW_CARPET));
                put(mkl(Material.LIME_CARPET),mkl(Material.LIME_CARPET));
                put(mkl(Material.WHITE_CARPET),mkl(Material.WHITE_CARPET));
                put(mkl(Material.CYAN_CARPET),mkl(Material.CYAN_CARPET));
                put(mkl(Material.BLUE_CARPET),mkl(Material.BLUE_CARPET));
                put(mkl(Material.GRAY_CARPET),mkl(Material.GRAY_CARPET));
                put(mkl(Material.BROWN_CARPET),mkl(Material.BROWN_CARPET));
                put(mkl(Material.GREEN_CARPET),mkl(Material.GREEN_CARPET));
                put(mkl(Material.LIGHT_BLUE_CARPET),mkl(Material.LIGHT_BLUE_CARPET));
                put(mkl(Material.MAGENTA_CARPET),mkl(Material.MAGENTA_CARPET));
                put(mkl(Material.PINK_CARPET),mkl(Material.PINK_CARPET));
                put(mkl(Material.PURPLE_CARPET),mkl(Material.PURPLE_CARPET));
                put(mkl(Material.GRAY_CARPET),mkl(Material.GRAY_CARPET));


            }})
            .register();
    public static final SlimefunItem ENDDUPE_MG=new SMGenerator(AddGroups.VANILLA, AddItem.ENDDUPE_MG,RecipeType.ENHANCED_CRAFTING_TABLE,
            AddUtils.formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.Name")),1,0,0,
            null)
            .register();

    //





    public static final SlimefunItem CUSTOM1=register(new FIrstCustomItem(AddGroups.MATERIAL, AddItem.CUSTOM1,BugCrafter.TYPE,
            recipe("COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT",
                    "COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT","COPPER_INGOT")));
    public static final SlimefunItem MACHINE1=register(new OEMachine(AddGroups.MATERIAL, AddItem.MACHINE1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            Material.BOOK,57,514,
                new LinkedHashMap<>(){{
                    put(new Object[]{new ItemStack(Material.BOOK),new ItemStack(Material.DIAMOND)
                            ,AddSlimefunItems.CUSTOM1             ,null},3);
                    put(new Object[]{new ItemStack(Material.COMMAND_BLOCK),null
                            ,AddSlimefunItems.CUSTOM1             ,AddSlimefunItems.MATL114},2);
                    put(new Object[]{new ItemStack(Material.EMERALD),null
                            ,AddSlimefunItems.MATL114            ,null},1);
                    put(new Object[]{new ItemStack(Material.DIAMOND,64),new ItemStack(Material.DIAMOND,16),
                    new ItemStack(Material.BEACON,1),null},3);
                    put(new Object[]{new ItemStack(Material.GOLD_INGOT,1),null,
                            new ItemStack(Material.BEACON,1),null},0);
                    put(new Object[]{AddSlimefunItems.MATL114,null,
                            AddSlimefunItems.CUSTOM1,null},0);
                }}

            ));
    public static final  SlimefunItem MACHINE2=register(new TEMachine(AddGroups.MATERIAL, AddItem.MACHINE2,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            Material.ACACIA_BOAT,57,514,
                new LinkedHashMap<>(){{
                put(new Object[]{new ItemStack(Material.BOOK),new ItemStack(Material.DIAMOND)
                        ,AddSlimefunItems.CUSTOM1             ,null},3);
                put(new Object[]{new ItemStack(Material.COMMAND_BLOCK),null
                        ,AddSlimefunItems.CUSTOM1             ,AddSlimefunItems.MATL114},2);
                put(new Object[]{new ItemStack(Material.EMERALD),null
                        ,AddSlimefunItems.MATL114            ,null},1);
                put(new Object[]{new ItemStack(Material.DIAMOND,64),new ItemStack(Material.DIAMOND,16),
                        new ItemStack(Material.BEACON,1),null},3);
                put(new Object[]{new ItemStack(Material.GOLD_INGOT,1),null,
                        new ItemStack(Material.BEACON,1),null},0);
                put(new Object[]{AddSlimefunItems.MATL114,null,
                        AddSlimefunItems.CUSTOM1,null},0);
            }}

    ));
    public static  final  SlimefunItem SMG1=new SMGenerator(AddGroups.MATERIAL, AddItem.SMG1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),3,114514,0,
               AddUtils.randItemStackFactory( new LinkedHashMap<>(){{
                    put(Material.DIAMOND,1);
                    put(new ItemStack(Material.BOOK,2),1);
                    put(CUSTOM1,1);
                    put("COPPER_DUST",1);
                    put("EMERALD_ORE",1);
                    put(
                            new EqProRandomStack(new LinkedHashMap<>(){{
                                put(new ItemStack(Material.LADDER),1);
                                put(new ItemStack(Material.BEDROCK),1);

                            }}),1
                     );
                }}),
            Material.DIRT

            ).register();
    public static final   SlimefunItem MMG1=register(new MMGenerator(AddGroups.MATERIAL, AddItem.MMG1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),3,114514,0,
                new LinkedHashMap<>(){{
                    put(new Object[]{"DIAMOND_BLOCK"},new Object[]{"114DIAMOND"}  );
                    put(new Object[]{"BEDROCK"},new Object[]{AddUtils.randItemStackFactory(
                            new LinkedHashMap<>(){{
                                put("2COPPER_DUST",1);
                                put("4SILVER_DUST",1);
                            }}
                    ),"1919COMMAND_BLOCK"});
                }}
            ));

    public static final  SlimefunItem TESTER=register(new TestMachine());
    public static final  SlimefunItem TESTER2=register(new SMGenerator(AddGroups.MATERIAL,new SlimefunItemStack("TESTER2",new ItemStack(Material.DIAMOND_ORE),"测试机","测试寄"),
            RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),12,0,0, (Object)(new ItemStack(Material.DIAMOND,1145))
           ,null,new ItemStack(Material.DIAMOND_CHESTPLATE)
            ));
    public static final  SlimefunItem MACHINE3=register(new EMachine(AddGroups.MATERIAL, AddItem.MACHINE3,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            Material.BOOK,1919,810,
            new LinkedHashMap<>(){{
                put(new Object[]{new ItemStack(Material.BOOK),new ItemStack(Material.DIAMOND)
                        ,AddSlimefunItems.CUSTOM1             ,null},3);
                put(new Object[]{new ItemStack(Material.COMMAND_BLOCK),null
                        ,AddSlimefunItems.CUSTOM1             ,AddSlimefunItems.MATL114},2);
                put(new Object[]{new ItemStack(Material.EMERALD),null
                        ,AddSlimefunItems.MATL114            ,null},1);
                put(new Object[]{new ItemStack(Material.DIAMOND,64),new ItemStack(Material.DIAMOND,16),
                        new ItemStack(Material.BEACON,1),null},3);
                put(new Object[]{new ItemStack(Material.GOLD_INGOT,1),null,
                        new ItemStack(Material.BEACON,1),null},0);
                put(new Object[]{AddSlimefunItems.MATL114,null,
                        AddSlimefunItems.CUSTOM1,null},0);
            }}));
    public static final  SlimefunItem MACHINE4=register(new AEMachine(AddGroups.MATERIAL, AddItem.MACHINE4,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            Material.BOOK,1919,810,
            new LinkedHashMap<>(){{
                put(new Object[]{new ItemStack(Material.BOOK),new ItemStack(Material.DIAMOND)
                        ,AddSlimefunItems.CUSTOM1             ,null},3);
                put(new Object[]{new ItemStack(Material.COMMAND_BLOCK),null
                        ,AddSlimefunItems.CUSTOM1             ,AddSlimefunItems.MATL114},2);
                put(new Object[]{new ItemStack(Material.EMERALD),null
                        ,AddSlimefunItems.MATL114            ,null},1);
                put(new Object[]{new ItemStack(Material.DIAMOND,64),new ItemStack(Material.DIAMOND,16),
                        new ItemStack(Material.BEACON,1),null},3);
                put(new Object[]{new ItemStack(Material.GOLD_INGOT,1),null,
                        new ItemStack(Material.BEACON,1),null},0);
                put(new Object[]{AddSlimefunItems.MATL114,null,
                        AddSlimefunItems.CUSTOM1,null},0);
            }}));
    public static final  SlimefunItem MANUAL1=register(new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
           1919,810,RecipeType.ENHANCED_CRAFTING_TABLE));
    public static final  SlimefunItem MANUAL_MULTI=register(new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_MULTI,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            1919,810,RecipeType.MULTIBLOCK));
    public static final  SlimefunItem MANUAL_KILL=register(new ManualCrafter(AddGroups.MATERIAL, AddItem.MANUAL_KILL,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            1919,810,RecipeType.MOB_DROP));
    public static final  SlimefunItem AUTOSMELTING1=register(new AdvanceCrafter(AddGroups.MATERIAL, AddItem.AUTOSMELTING1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            Material.FLINT_AND_STEEL,1919,810,RecipeType.SMELTERY));
    public static final SlimefunItem SINGULARITY=register(new Singularity(AddGroups.MATERIAL, AddItem.STORAGE_SINGULARITY,RecipeType.NULL,AddUtils.NULL_RECIPE));

    public static final SlimefunItem INPORT=register(new InputPort(AddGroups.MATERIAL, AddItem.INPORT,RecipeType.NULL,AddUtils.NULL_RECIPE,0,0));

    public static final SlimefunItem OUTPORT=register(new OutputPort(AddGroups.MATERIAL, AddItem.OUTPORT,RecipeType.NULL,AddUtils.NULL_RECIPE,0,0));

    public static final SlimefunItem TESTUNIT1=register(new TestStorageUnit(AddGroups.MATERIAL, AddItem.TESTUNIT1,RecipeType.NULL,AddUtils.NULL_RECIPE,0,0));

    public static final SlimefunItem AUTO_SPECIAL= register(new SpecialCrafter(AddGroups.MATERIAL, AddItem.AUTO_SPECIAL, RecipeType.NULL, AddUtils.NULL_RECIPE.clone()
                    , Material.KNOWLEDGE_BOOK,9,810,1919));
    public static final SlimefunItem AUTO_MULTIBLOCK= register(new MultiBlockManual(AddGroups.MATERIAL, AddItem.AUTO_MULTIBLOCK, RecipeType.NULL, AddUtils.NULL_RECIPE.clone()
            ,0,0));
    public static final SlimefunItem ANTIGRAVITY=register(new AntiGravityBar(AddGroups.MATERIAL, AddItem.ANTIGRAVITY,RecipeType.NULL,AddUtils.NULL_RECIPE.clone()));

    public static final SlimefunItem WORKBENCH1=register(new TestWorkBench(AddGroups.MATERIAL, AddItem.WORKBENCH1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            1919,810,
                new LinkedHashMap<>(){{
                    put(mkP(
                            mkl(null,"2COPPER_DUST",null,AddSlimefunItems.MATL114,"4DIAMOND",AddSlimefunItems.CUSTOM1,null,"3IRON_DUST",null),
                            mkl("5COMMAND_BLOCK")
                    ),0);
                }}
            ));
    public static final SlimefunItem FINAL_MANUAL=register(new FinalManual(AddGroups.MATERIAL, AddItem.FINAL_MANUAL,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            1919,810));
}
