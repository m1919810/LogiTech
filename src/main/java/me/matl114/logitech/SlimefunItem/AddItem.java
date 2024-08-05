package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Items.CustomHead;
import me.matl114.logitech.Language;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class AddItem {

    public static void registerItemStack(){
        for (ItemStack it :ADDGLOW){
            AddUtils.addGlow(it);
        }
    }

    //Groups
    public static final ItemStack ROOT=new CustomItemStack(Material.BUDDING_AMETHYST,
            Language.get("Groups.ROOT.Name"),Language.getList("Groups.ROOT.Lore"));
    public static final ItemStack INFO=AddUtils.themed(Material.PAPER,AddUtils.Theme.INFO1,
            Language.get("Groups.INFO.Name"), Language.getList("Groups.INFO.Lore"));
    public static final ItemStack MATERIAL=AddUtils.themed(Material.END_CRYSTAL,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.MATERIAL.Name"), Language.getList("Groups.MATERIAL.Lore"));
    public static final ItemStack INFO1=AddUtils.themed(Material.PAPER,AddUtils.Theme.NONE,
            Language.get("Groups.INFO1.Name"), Language.getList("Groups.INFO1.Lore"));
    public static final ItemStack INFO2=AddUtils.themed(Material.PAPER,AddUtils.Theme.NONE,
            Language.get("Groups.INFO2.Name"), Language.getList("Groups.INFO2.Lore"));
    public static final ItemStack INFO3=AddUtils.themed(Material.PAPER,AddUtils.Theme.NONE,
            Language.get("Groups.INFO3.Name"), Language.getList("Groups.INFO3.Lore"));
    public static final ItemStack INFO4=AddUtils.themed(Material.PAPER,AddUtils.Theme.NONE,
            Language.get("Groups.INFO4.Name"), Language.getList("Groups.INFO4.Lore"));
    public static final ItemStack INFO5=AddUtils.themed(Material.PAPER,AddUtils.Theme.NONE,
            Language.get("Groups.INFO5.Name"), Language.getList("Groups.INFO5.Lore"));
    public static final ItemStack INFO6=AddUtils.themed(Material.PAPER,AddUtils.Theme.NONE,
            Language.get("Groups.INFO6.Name"), Language.getList("Groups.INFO6.Lore"));
    public static final ItemStack URL=AddUtils.themed(Material.BOOK,AddUtils.Theme.NONE,
            Language.get("Groups.URL.Name"), Language.getList("Groups.URL.Lore"));
    public static final ItemStack ALLMACHINE=AddUtils.themed(Material.BLAST_FURNACE,AddUtils.Theme.MENU1,
            Language.get("Groups.ALLMACHINE.Name"), Language.getList("Groups.ALLMACHINE.Lore"));
    public static final ItemStack ALLRECIPE=AddUtils.themed(Material.KNOWLEDGE_BOOK,AddUtils.Theme.MENU1,
            Language.get("Groups.ALLRECIPE.Name"), Language.getList("Groups.ALLRECIPE.Lore"));
    public static final ItemStack BASIC=AddUtils.themed(Material.FURNACE,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.BASIC.Name"),Language.getList("Groups.BASIC.Lore")  );
    public static final ItemStack DEPENDS=AddUtils.themed(Material.LODESTONE, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.DEPENDS.Name"),Language.getList("Groups.DEPENDS.Lore"));
    public static final ItemStack CARGO=AddUtils.themed(Material.BAMBOO_CHEST_RAFT,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.CARGO.Name"),Language.getList("Groups.CARGO.Lore"));
    public static final ItemStack SINGULARITY=AddUtils.themed(Material.NETHER_STAR,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.SINGULARITY.Name"),Language.getList("Groups.SINGULARITY.Lore"));
    public static final ItemStack ADVANCED=AddUtils.themed(Material.BEACON,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.ADVANCED.Name"),Language.getList("Groups.ADVANCED.Lore"));
    public static final ItemStack BEYOND=AddUtils.themed(Material.REPEATING_COMMAND_BLOCK,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.BEYOND.Name"),Language.getList("Groups.BEYOND.Lore"));
    public static final ItemStack VANILLA=AddUtils.themed(Material.OBSERVER,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.VANILLA.Name"),Language.getList("Groups.VANILLA.Lore"));
    public static final ItemStack MANUAL=AddUtils.themed(Material.CRAFTING_TABLE,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.MANUAL.Name"),Language.getList("Groups.MANUAL.Lore") );
    public static final ItemStack SPECIAL=AddUtils.themed(Material.SCULK_CATALYST,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.SPECIAL.Name"),Language.getList("Groups.SPECIAL.Lore"));
    //public static final ItemStack TEMPLATE=AddUtils.themed()
    public static final ItemStack TOBECONTINUE=AddUtils.themed(Material.STRUCTURE_VOID,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.TOBECONTINUE.Name"),Language.getList("Groups.TOBECONTINUE.Lore"));
    public static final ItemStack WEAPON=AddUtils.themed(Material.TOTEM_OF_UNDYING, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.WEAPON.Name"),Language.getList("Groups.WEAPON.Lore"));
    public static final ItemStack GENERATORS=AddUtils.themed(Material.LAVA_BUCKET, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.GENERATORS.Name"),Language.getList("Groups.GENERATORS.Lore"));
    public static final ItemStack ENERGY=AddUtils.themed(Material.LIGHTNING_ROD, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.ENERGY.Name"),Language.getList("Groups.ENERGY.Lore"));
    //items
    public static final SlimefunItemStack BUG= AddUtils.themed("BUG", Material.BONE_MEAL, AddUtils.Theme.ITEM1,
            Language.get("Items.BUG.Name"),Language.getList("Items.BUG.Lore"));
    public static final SlimefunItemStack MATL114 = AddUtils.themed("MATL114", CustomHead.MATL114.getItem(), AddUtils.Theme.ITEM1,
            Language.get("Items.MATL114.Name"),Language.getList("Items.MATL114.Lore"));
    public static final SlimefunItemStack CHIP_INGOT=AddUtils.themed("CHIP_INGOT",Material.BAKED_POTATO,AddUtils.Theme.ITEM1,
            Language.get("Items.CHIP_INGOT.Name"),Language.getList("Items.CHIP_INGOT.Lore"));
    public static final SlimefunItemStack TITANIUM_INGOT=AddUtils.themed("TITANIUM_INGOT",Material.IRON_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.TITANIUM_INGOT.Name"),Language.getList("Items.TITANIUM_INGOT.Lore"));
    public static final SlimefunItemStack TUNGSTEN_INGOT=AddUtils.themed("TUNGSTEN_INGOT",Material.NETHERITE_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.TUNGSTEN_INGOT.Name"),Language.getList("Items.TUNGSTEN_INGOT.Lore"));
    public static final SlimefunItemStack LOGIC= AddUtils.themed("LOGIC",Material.STRING,AddUtils.Theme.ITEM1,
            Language.get("Items.LOGIC.Name"),Language.getList("Items.LOGIC.Lore"));
    public static final SlimefunItemStack TRUE_ =AddUtils.themed("TRUE_",Material.MUSIC_DISC_5,AddUtils.Theme.ITEM1,
            Language.get("Items.TRUE_.Name"),Language.getList("Items.TRUE_.Lore"));
    public static final SlimefunItemStack FALSE_ =AddUtils.themed("FALSE_",Material.MUSIC_DISC_5,AddUtils.Theme.ITEM1,
            Language.get("Items.FALSE_.Name"),Language.getList("Items.FALSE_.Lore"));
    public static final SlimefunItemStack LOGIGATE=AddUtils.themed("LOGIGATE",Material.COMPARATOR,AddUtils.Theme.ITEM1,
            Language.get("Items.LOGIGATE.Name"),Language.getList("Items.LOGIGATE.Lore"));
        //generated items
    public static final SlimefunItemStack EXISTE=AddUtils.themed("EXISTE",Material.SLIME_BALL,AddUtils.Theme.ITEM1,
            Language.get("Items.EXISTE.Name"),Language.getList("Items.EXISTE.Lore"));
    public static final SlimefunItemStack UNIQUE=AddUtils.themed("UNIQUE",Material.MAGMA_CREAM,AddUtils.Theme.ITEM1,
            Language.get("Items.UNIQUE.Name"),Language.getList("Items.UNIQUE.Lore"));
    public static final SlimefunItemStack PARADOX=AddUtils.themed("PARADOX",Material.NAUTILUS_SHELL,AddUtils.Theme.ITEM1,
            Language.get("Items.PARADOX.Name"),Language.getList("Items.PARADOX.Lore"));
    public static final SlimefunItemStack NOLOGIC=AddUtils.themed("NOLOGIC",Material.STRING,AddUtils.Theme.ITEM1,
            Language.get("Items.NOLOGIC.Name"),Language.getList("Items.NOLOGIC.Lore"));
    public static final SlimefunItemStack LENGINE=AddUtils.themed("LENGINE",Material.MAGENTA_GLAZED_TERRACOTTA,AddUtils.Theme.ITEM1,
            Language.get("Items.LENGINE.Name"),Language.getList("Items.LENGINE.Lore"));
    public static final SlimefunItemStack LFIELD=AddUtils.themed("LFIELD",Material.END_CRYSTAL,AddUtils.Theme.ITEM1,
            Language.get("Items.LFIELD.Name"),Language.getList("Items.LFIELD.Lore"));
    public static final SlimefunItemStack LSCHEDULER=AddUtils.themed("LSCHEDULER",Material.RECOVERY_COMPASS,AddUtils.Theme.ITEM1,
            Language.get("Items.LSCHEDULER.Name"),Language.getList("Items.LSCHEDULER.Lore"));
    public static final SlimefunItemStack LCRAFT=AddUtils.themed("LCRAFT",Material.CONDUIT,AddUtils.Theme.ITEM1,
            Language.get("Items.LCRAFT.Name"),Language.getList("Items.LCRAFT.Lore"));
    public static final SlimefunItemStack LDIGITIZER=AddUtils.themed("LDIGITIZER",Material.TARGET,AddUtils.Theme.ITEM1,
            Language.get("Items.LDIGITIZER.Name"),Language.getList("Items.LDIGITIZER.Lore"));
    public static final SlimefunItemStack LBOOLIZER=AddUtils.themed("LBOOLIZER",Material.LEVER,AddUtils.Theme.ITEM1,
            Language.get("Items.LBOOLIZER.Name"),Language.getList("Items.LBOOLIZER.Lore"));
    public static final SlimefunItemStack LIOPORT=AddUtils.themed("LIOPORT",Material.CALIBRATED_SCULK_SENSOR,AddUtils.Theme.ITEM1,
            Language.get("Items.LIOPORT.Name"),Language.getList("Items.LIOPORT.Lore"));
    public static final SlimefunItemStack PALLADIUM_INGOT=AddUtils.themed("PALLADIUM_INGOT",Material.COPPER_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.PALLADIUM_INGOT.Name"),Language.getList("Items.PALLADIUM_INGOT.Lore"));
    public static final SlimefunItemStack PLATINUM_INGOT=AddUtils.themed("PLATINUM_INGOT",Material.GOLD_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.PLATINUM_INGOT.Name"),Language.getList("Items.PLATINUM_INGOT.Lore"));
    public static final SlimefunItemStack MOLYBDENUM=AddUtils.themed("MOLYBDENUM",Material.GUNPOWDER,AddUtils.Theme.ITEM1,
            Language.get("Items.MOLYBDENUM.Name"),Language.getList("Items.MOLYBDENUM.Lore"));
    public static final SlimefunItemStack CERIUM=AddUtils.themed("CERIUM",Material.GUNPOWDER,AddUtils.Theme.ITEM1,
            Language.get("Items.CERIUM.Name"),Language.getList("Items.CERIUM.Lore"));
    public static final SlimefunItemStack CADMIUM_INGOT=AddUtils.themed("CADMIUM_INGOT",Material.NETHERITE_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.CADMIUM_INGOT.Name"),Language.getList("Items.CADMIUM_INGOT.Lore"));
    public static final SlimefunItemStack MENDELEVIUM=AddUtils.themed("MENDELEVIUM",Material.GLOWSTONE_DUST,AddUtils.Theme.ITEM1,
            Language.get("Items.MENDELEVIUM.Name"),Language.getList("Items.MENDELEVIUM.Lore"));
    public static final SlimefunItemStack DYSPROSIUM=AddUtils.themed("DYSPROSIUM",Material.REDSTONE,AddUtils.Theme.ITEM1,
            Language.get("Items.DYSPROSIUM.Name"),Language.getList("Items.DYSPROSIUM.Lore"));
    public static final SlimefunItemStack BISMUTH_INGOT=AddUtils.themed("BISMUTH_INGOT",Material.IRON_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.BISMUTH_INGOT.Name"),Language.getList("Items.BISMUTH_INGOT.Lore"));
    public static final SlimefunItemStack ANTIMONY_INGOT=AddUtils.themed("ANTIMONY_INGOT",Material.IRON_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.ANTIMONY_INGOT.Name"),Language.getList("Items.ANTIMONY_INGOT.Lore"));
    public static final SlimefunItemStack BORON=AddUtils.themed("BORON",Material.CLAY_BALL,AddUtils.Theme.ITEM1,
            Language.get("Items.BORON.Name"),Language.getList("Items.BORON.Lore"));
    public static final SlimefunItemStack DIMENSIONAL_SHARD=AddUtils.themed("DIMENSIONAL_SHARD",Material.PRISMARINE_SHARD,AddUtils.Theme.ITEM1,
            Language.get("Items.DIMENSIONAL_SHARD.Name"),Language.getList("Items.DIMENSIONAL_SHARD.Lore"));
    public static final SlimefunItemStack STAR_GOLD=AddUtils.themed("STAR_GOLD",Material.NETHER_STAR,AddUtils.Theme.ITEM1,
            Language.get("Items.STAR_GOLD.Name"),Language.getList("Items.STAR_GOLD.Lore"));
    public static final SlimefunItemStack VIRTUAL_SPACE=AddUtils.themed("VIRTUAL_SPACE",Material.PLAYER_HEAD,AddUtils.Theme.ITEM1,
            Language.get("Items.VIRTUAL_SPACE.Name"),Language.getList("Items.VIRTUAL_SPACE.Lore"));
    public static final SlimefunItemStack WORLD_FEAT=AddUtils.themed("WORLD_FEAT",Material.GRASS_BLOCK,AddUtils.Theme.ITEM1,
            Language.get("Items.WORLD_FEAT.Name"),Language.getList("Items.WORLD_FEAT.Lore"));
    public static final SlimefunItemStack NETHER_FEAT=AddUtils.themed("NETHER_FEAT",Material.NETHERITE_SCRAP,AddUtils.Theme.ITEM1,
            Language.get("Items.NETHER_FEAT.Name"),Language.getList("Items.NETHER_FEAT.Lore"));
    public static final SlimefunItemStack END_FEAT=AddUtils.themed("END_FEAT",Material.CHORUS_PLANT,AddUtils.Theme.ITEM1,
            Language.get("Items.END_FEAT.Name"),Language.getList("Items.END_FEAT.Lore"));


    public static final SlimefunItemStack ABSTRACT_INGOT=AddUtils.themed("ABSTRACT_INGOT",Material.IRON_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.ABSTRACT_INGOT.Name"),Language.getList("Items.ABSTRACT_INGOT.Lore"));
    public static final SlimefunItemStack PDCECDMD=AddUtils.themed("PDCECDMD",Material.PLAYER_HEAD,AddUtils.Theme.ITEM1,
            Language.get("Items.PDCECDMD.Name"),Language.getList("Items.PDCECDMD.Lore"));
    public static final SlimefunItemStack REINFORCED_CHIP_INGOT=AddUtils.themed("REINFORCED_CHIP_INGOT",Material.POISONOUS_POTATO,AddUtils.Theme.ITEM1,
            Language.get("Items.REINFORCED_CHIP_INGOT.Name"),Language.getList("Items.REINFORCED_CHIP_INGOT.Lore"));
    public static final SlimefunItemStack ATOM_INGOT=AddUtils.themed("ATOM_INGOT",Material.ECHO_SHARD,AddUtils.Theme.ITEM1,
            Language.get("Items.ATOM_INGOT.Name"),Language.getList("Items.ATOM_INGOT.Lore"));

    //nachines
    public static final SlimefunItemStack HEAD_ANALYZER=AddUtils.themed("HEAD_ANALYZER",Material.SOUL_CAMPFIRE, AddUtils.Theme.MACHINE1,
            Language.get("Machines.HEAD_ANALYZER.Name"),Language.getList("Machines.HEAD_ANALYZER.Lore"));
    public static final SlimefunItemStack BOOL_GENERATOR=AddUtils.themed("BOOL_GENERATOR",Material.REDSTONE_TORCH,AddUtils.Theme.MACHINE1,
            Language.get("Machines.BOOL_GENERATOR.Name"),Language.getList("Machines.BOOL_GENERATOR.Lore"));
    public static final SlimefunItemStack LOGIC_REACTOR=AddUtils.themed("LOGIC_REACTOR",CustomHead.LOGIC_REACTOR.getItem(),AddUtils.Theme.MACHINE1,
            Language.get("Machines.LOGIC_REACTOR.Name"),Language.getList("Machines.LOGIC_REACTOR.Lore"));
    public static final SlimefunItemStack BUG_CRAFTER=AddUtils.themed("BUG_CRAFTER",CustomHead.BUG_CRATFER.getItem(),AddUtils.Theme.MACHINE1,
            Language.get("Machines.BUG_CRAFTER.Name"),Language.getList("Machines.BUG_CRAFTER.Lore"));

    //manuals
    public static final SlimefunItemStack MANUAL_CORE=AddUtils.themed("MANUAL_CORE",Material.AMETHYST_SHARD,AddUtils.Theme.ITEM1,
            Language.get("Manuals.MANUAL_CORE.Name"),Language.getList("Manuals.MANUAL_CORE.Lore"));
    public static final SlimefunItemStack ENHANCED_CRAFT_MANUAL=AddUtils.themed("ENHANCED_CRAFT_MANUAL",Material.CRAFTING_TABLE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.ENHANCED_CRAFT_MANUAL.Name"),Language.getList("Manuals.ENHANCED_CRAFT_MANUAL.Lore"));
    public static final SlimefunItemStack GRIND_MANUAL=AddUtils.themed("GRIND_MANUAL",Material.DISPENSER,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.GRIND_MANUAL.Name"),Language.getList("Manuals.GRIND_MANUAL.Lore"));
    public static final SlimefunItemStack ARMOR_FORGE_MANUAL=AddUtils.themed("ARMOR_FORGE_MANUAL",Material.ANVIL,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.ARMOR_FORGE_MANUAL.Name"),Language.getList("Manuals.ARMOR_FORGE_MANUAL.Lore"));
    public static final SlimefunItemStack ORE_CRUSHER_MANUAL=AddUtils.themed("ORE_CRUSHER_MANUAL",Material.DROPPER,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.ORE_CRUSHER_MANUAL.Name"),Language.getList("Manuals.ORE_CRUSHER_MANUAL.Lore"));
    public static final SlimefunItemStack COMPRESSOR_MANUAL=AddUtils.themed("COMPRESSOR_MANUAL",Material.PISTON,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.COMPRESSOR_MANUAL.Name"),Language.getList("Manuals.COMPRESSOR_MANUAL.Lore"));
    public static final SlimefunItemStack PRESSURE_MANUAL=AddUtils.themed("PRESSURE_MANUAL",Material.GLASS,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.PRESSURE_MANUAL.Name"),Language.getList("Manuals.PRESSURE_MANUAL.Lore"));
    public static final SlimefunItemStack MAGIC_WORKBENCH_MANUAL=AddUtils.themed("MAGIC_WORKBENCH_MANUAL",Material.BOOKSHELF,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.MAGIC_WORKBENCH_MANUAL.Name"),Language.getList("Manuals.MAGIC_WORKBENCH_MANUAL.Lore"));
    public static final SlimefunItemStack ORE_WASHER_MANUAL=AddUtils.themed("ORE_WASHER_MANUAL",Material.BLUE_STAINED_GLASS,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.ORE_WASHER_MANUAL.Name"),Language.getList("Manuals.ORE_WASHER_MANUAL.Lore"));
    public static final SlimefunItemStack GOLD_PAN_MANUAL=AddUtils.themed("GOLD_PAN_MANUAL",Material.BROWN_TERRACOTTA,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.GOLD_PAN_MANUAL.Name"),Language.getList("Manuals.GOLD_PAN_MANUAL.Lore"));
    public static final SlimefunItemStack ANCIENT_ALTAR_MANUAL=AddUtils.themed("ANCIENT_ALTAR_MANUAL",Material.ENCHANTING_TABLE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.ANCIENT_ALTAR_MANUAL.Name"),Language.getList("Manuals.ANCIENT_ALTAR_MANUAL.Lore"));
    public static final SlimefunItemStack SMELTERY_MANUAL=AddUtils.themed("SMELTERY_MANUAL",Material.BLAST_FURNACE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.SMELTERY_MANUAL.Name"),Language.getList("Manuals.SMELTERY_MANUAL.Lore"));
    public static final SlimefunItemStack CRUCIBLE_MANUAL=AddUtils.themed("CRUCIBLE_MANUAL",Material.RED_TERRACOTTA,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.CRUCIBLE_MANUAL.Name"),Language.getList("Manuals.CRUCIBLE_MANUAL.Lore"));
    public static final SlimefunItemStack PULVERIZER_MANUAL=AddUtils.themed("PULVERIZER_MANUAL",Material.GRINDSTONE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.PULVERIZER_MANUAL.Name"),Language.getList("Manuals.PULVERIZER_MANUAL.Lore"));
    public static final SlimefunItemStack MULTICRAFTTABLE_MANUAL=AddUtils.themed("MULTICRAFTTABLE_MANUAL",Material.CRAFTING_TABLE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.MULTICRAFTTABLE_MANUAL.Name"),Language.getList("Manuals.MULTICRAFTTABLE_MANUAL.Lore"));
    public static final SlimefunItemStack TABLESAW_MANUAL=AddUtils.themed("TABLESAW_MANUAL",Material.STONECUTTER,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.TABLESAW_MANUAL.Name"),Language.getList("Manuals.TABLESAW_MANUAL.Lore"));
    public static final SlimefunItemStack COMPOSTER=AddUtils.themed("COMPOSTER",Material.CAULDRON,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.COMPOSTER.Name"),Language.getList("Manuals.COMPOSTER.Lore"));
    public static final SlimefunItemStack MULTIMACHINE_MANUAL=AddUtils.themed("MULTIMACHINE_MANUAL",Material.GRAY_STAINED_GLASS,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.MULTIMACHINE_MANUAL.Name"),Language.getList("Manuals.MULTIMACHINE_MANUAL.Lore"));

    public static final SlimefunItemStack CUSTOM1=
            AddUtils.themed("CUSTOM1",new ItemStack(Material.COMMAND_BLOCK),AddUtils.Theme.ITEM1,"测试物件1","只是一个简单的测试");
    public static final SlimefunItemStack MACHINE1=
            AddUtils.themed("MACHINE1",new ItemStack(Material.FURNACE),AddUtils.Theme.MACHINE1,"测试机器1","tnnd对照组");
    public static final SlimefunItemStack MACHINE2=
            AddUtils.themed("MACHINE2",new ItemStack(Material.FURNACE),AddUtils.Theme.MACHINE1,"测试机2","tnnd实验组");
    public static final SlimefunItemStack MACHINE3=
            AddUtils.themed("MACHINE3",new ItemStack(Material.FURNACE),AddUtils.Theme.MACHINE1,"测试机3","tnnd测试组 AbstractProcessor");
    public static final SlimefunItemStack MACHINE4=
            AddUtils.themed("MACHINE4",new ItemStack(Material.FURNACE),AddUtils.Theme.MACHINE1,"测试机4","tnnd测试组 AbstractAdvancedProcessor");
    public static final SlimefunItemStack SMG1=
            AddUtils.themed("SMG1",new ItemStack(Material.DIAMOND_BLOCK),AddUtils.Theme.MACHINE2,"测试生成器1","测测我的");
    public static final SlimefunItemStack MMG1=
            AddUtils.themed("MMG1",new ItemStack(Material.EMERALD_BLOCK),AddUtils.Theme.MACHINE2,"定向生成器1","测测我的");
    public static final SlimefunItemStack MANUAL1=
            AddUtils.themed("MANUAL1",new ItemStack(Material.CRAFTING_TABLE),AddUtils.Theme.MANUAL1,"测试快捷机器","强化工作台");
    public static final SlimefunItemStack MANUAL_MULTI=
            AddUtils.themed("MANUAL_MULTI",new ItemStack(Material.CRAFTING_TABLE),AddUtils.Theme.MANUAL1,"测试快捷机器","多方块机器");
    public static final SlimefunItemStack MANUAL_KILL=
            AddUtils.themed("MANUAL_KILL",new ItemStack(Material.RESPAWN_ANCHOR),AddUtils.Theme.MANUAL1,"测试快捷机器","击杀掉落");
    public static final SlimefunItemStack MANUAL_INF=
            AddUtils.themed("MANUAL_INF",new ItemStack(Material.RESPAWN_ANCHOR),AddUtils.Theme.MANUAL1,"测试快捷机器","无尽工作台");
    public static final SlimefunItemStack MANUAL_MOB=
            AddUtils.themed("MANUAL_MOB",new ItemStack(Material.LODESTONE),AddUtils.Theme.MANUAL1,"测试快捷机器","无尽芯片注入");
    public static final SlimefunItemStack MANUAL_NTWBENCH=
            AddUtils.themed("MANUAL_NTWBENCH",new ItemStack(Material.DRIED_KELP_BLOCK),AddUtils.Theme.MANUAL1,"测试快捷机器","网络工作台");
    public static final SlimefunItemStack AUTOSMELTING1=
            AddUtils.themed("AUTOCRAFT_SMELT",new ItemStack(Material.FURNACE),AddUtils.Theme.MANUAL1,"测试AutoCraft","冶炼炉");
    public static final SlimefunItemStack AUTO_INF=
            AddUtils.themed("AUTOCRAFT_INF",new ItemStack(Material.RESPAWN_ANCHOR),AddUtils.Theme.MANUAL1,"测试定向合成机","无尽工作台");
    public static final SlimefunItemStack STORAGE_SINGULARITY=
            AddUtils.themed("SINGULARITY",new ItemStack(Material.NETHER_STAR),AddUtils.Theme.CARGO1,"存储奇点","将物品压缩成奇点...");
    public static final SlimefunItemStack INPORT=
            AddUtils.themed("INPORT",new ItemStack(Material.END_STONE),AddUtils.Theme.CARGO1,"存入接口","较快的将物品存入奇点...");
    public static final SlimefunItemStack OUTPORT=
            AddUtils.themed("OUTPORT",new ItemStack(Material.END_STONE),AddUtils.Theme.CARGO1,"取出接口","较快的将物品取出奇点...");
    public static final SlimefunItemStack TESTUNIT1=
            AddUtils.themed("TESTUNIT1",new ItemStack(Material.GLASS),AddUtils.Theme.CARGO1,"测试存储单元","啥用都没");
    public static final SlimefunItemStack AUTO_SPECIAL=
            AddUtils.themed("AUTOCRAFT_SPECIAL",new ItemStack(Material.LOOM),AddUtils.Theme.MACHINE2,"测试特殊合成机","测试测试");
    public static final SlimefunItemStack AUTO_MULTIBLOCK=
            AddUtils.themed("AUTOCRAFT_MULTIBLOCK",new ItemStack(Material.BRICKS),AddUtils.Theme.MANUAL1,"测试快捷多方块","测试测试");
    public static final SlimefunItemStack ANTIGRAVITY=
            AddUtils.themed("ANTI_GRAVITY_ITEM",new ItemStack(Material.NETHERITE_INGOT),AddUtils.Theme.ITEM1,"反重力装置","测试测试");
    public static final SlimefunItemStack WORKBENCH1=
            AddUtils.themed("WORKBENCH1",new ItemStack(Material.ENCHANTING_TABLE),AddUtils.Theme.BENCH1,"测试工作站","测试测试");
    public static final SlimefunItemStack FINAL_MANUAL=
            AddUtils.themed("FINAL_MANUAL",new ItemStack(Material.REINFORCED_DEEPSLATE),AddUtils.Theme.MANUAL1,"测试终极快捷","测试测试");


    public static final SlimefunItemStack MAGIC_STONE=AddUtils.themed("MAGIC_STONE",Material.COBBLESTONE,AddUtils.Theme.MACHINE2,
            Language.get("Generators.MAGIC_STONE.Name"),Language.getList("Generators.MAGIC_STONE.Lore"));
    public static final SlimefunItemStack BOOL_MG=AddUtils.themed("BOOL_MG",Material.REDSTONE_TORCH,AddUtils.Theme.MACHINE2,
            Language.get("Generators.BOOL_MG.Name"),Language.getList("Generators.BOOL_MG.Lore"));
    //tmp占位符
    public static final SlimefunItemStack TMP1= new SlimefunItemStack("TMP1",Material.STONE,"TMP1","暂未开发");
    public static final HashSet<ItemStack> ADDGLOW=new HashSet<>(){{
        add(BUG);
        add(INFO);
        add(BEYOND);
        add(TRUE_);
        add(CHIP_INGOT);
        add(PARADOX);
        add(NOLOGIC);
        add(DIMENSIONAL_SHARD);
        add(WORLD_FEAT);
        add(NETHER_FEAT);
        add(END_FEAT);
        add(REINFORCED_CHIP_INGOT);
        add(ABSTRACT_INGOT);
    }};
}
