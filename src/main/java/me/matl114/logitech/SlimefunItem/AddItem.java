package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Items.CustomFireworkStar;
import me.matl114.logitech.Items.CustomHead;
import me.matl114.logitech.Language;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class AddItem {

    public static void registerItemStack(){
        for (ItemStack it :ADDGLOW){
            AddUtils.addGlow(it);
        }
        AddItem.TRACE_ARROW.addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1);
        AddUtils.hideAllFlags(CARGO_CONFIG);
        AddUtils.hideAllFlags(ENTITY_FEAT);
        AddUtils.hideAllFlags(SPACE_CARD);
        AddUtils.hideAllFlags(REPLACE_SF_CARD);
        AddUtils.setUnbreakable(UNBREAKING_SHIELD,true);
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
    public static final ItemStack WIKI=AddUtils.themed(Material.KNOWLEDGE_BOOK,AddUtils.Theme.NONE,
            Language.get("Groups.WIKI.Name"), Language.getList("Groups.WIKI.Lore"));
    public static final ItemStack ALLMACHINE=AddUtils.themed(Material.BLAST_FURNACE,AddUtils.Theme.MENU1,
            Language.get("Groups.ALLMACHINE.Name"), Language.getList("Groups.ALLMACHINE.Lore"));
    public static final ItemStack ALLRECIPE=AddUtils.themed(Material.KNOWLEDGE_BOOK,AddUtils.Theme.MENU1,
            Language.get("Groups.ALLRECIPE.Name"), Language.getList("Groups.ALLRECIPE.Lore"));
    public static final ItemStack BASIC=AddUtils.themed(Material.FURNACE,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.BASIC.Name"),Language.getList("Groups.BASIC.Lore")  );
    public static final ItemStack ALLBIGRECIPES =AddUtils.themed(Material.LODESTONE, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.ALLBIGRECIPES.Name"),Language.getList("Groups.ALLBIGRECIPES.Lore"));
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
    public static final ItemStack TOOLS=AddUtils.themed(Material.NETHERITE_AXE, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.TOOLS.Name"),Language.getList("Groups.TOOLS.Lore"));
    //public static final ItemStack TEMPLATE=AddUtils.themed()
    public static final ItemStack TOBECONTINUE=AddUtils.themed(Material.STRUCTURE_VOID,AddUtils.Theme.CATEGORY2,
            Language.get("Groups.TOBECONTINUE.Name"),Language.getList("Groups.TOBECONTINUE.Lore"));
    public static final ItemStack SPACE =AddUtils.themed(Material.TOTEM_OF_UNDYING, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.SPACE.Name"),Language.getList("Groups.SPACE.Lore"));
    public static final ItemStack GENERATORS=AddUtils.themed(Material.LAVA_BUCKET, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.GENERATORS.Name"),Language.getList("Groups.GENERATORS.Lore"));
    public static final ItemStack ENERGY=AddUtils.themed(Material.LIGHTNING_ROD, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.ENERGY.Name"),Language.getList("Groups.ENERGY.Lore"));
    public static final ItemStack FUNCTIONAL=AddUtils.themed(Material.STRUCTURE_VOID, AddUtils.Theme.CATEGORY2,
            Language.get("Groups.FUNCTIONAL.Name"),Language.getList("Groups.FUNCTIONAL.Lore"));
    public static final ItemStack UPDATELOG=AddUtils.themed(Material.WRITABLE_BOOK,AddUtils.Theme.NONE,
            Language.get("Groups.UPDATELOG.Name"), Language.getList("Groups.UPDATELOG.Lore"));
    public static final ItemStack MORE2=AddUtils.themed("MORE2",Material.WRITABLE_BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.MORE2.Name"),Language.getList("Groups.MORE2.Lore"));
    //feat
    public static final ItemStack FEAT1=AddUtils.themed(Material.BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.FEAT1.Name"),Language.getList("Groups.FEAT1.Lore"));
    public static final ItemStack FEAT2=AddUtils.themed(Material.BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.FEAT2.Name"),Language.getList("Groups.FEAT2.Lore"));
    public static final ItemStack FEAT3=AddUtils.themed(Material.BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.FEAT3.Name"),Language.getList("Groups.FEAT3.Lore"));
    public static final ItemStack FEAT4=AddUtils.themed(Material.BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.FEAT4.Name"),Language.getList("Groups.FEAT4.Lore"));
    public static final ItemStack FEAT5=AddUtils.themed(Material.BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.FEAT5.Name"),Language.getList("Groups.FEAT5.Lore"));
    public static final ItemStack FEAT6=AddUtils.themed(Material.BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.FEAT6.Name"),Language.getList("Groups.FEAT6.Lore"));
    public static final ItemStack FEAT7=AddUtils.themed(Material.BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.FEAT7.Name"),Language.getList("Groups.FEAT7.Lore"));
    public static final ItemStack FEAT8=AddUtils.themed(Material.BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.FEAT8.Name"),Language.getList("Groups.FEAT8.Lore"));
    public static final ItemStack FEAT9=AddUtils.themed(Material.BOOK, AddUtils.Theme.NONE,
            Language.get("Groups.FEAT9.Name"),Language.getList("Groups.FEAT9.Lore"));


    //items
    public static final SlimefunItemStack ENTITY_FEAT=AddUtils.themed("ENTITY_FEAT",Material.SPAWNER,AddUtils.Theme.ITEM1,
            Language.get("Items.ENTITY_FEAT.Name"),Language.getList("Items.ENTITY_FEAT.Lore"));
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
    public static final SlimefunItemStack THALLIUM=AddUtils.themed("THALLIUM",Material.BRICK,AddUtils.Theme.ITEM1,
            Language.get("Items.THALLIUM.Name"),Language.getList("Items.THALLIUM.Lore"));
    public static final SlimefunItemStack HYDRAGYRUM=AddUtils.themed("HYDRAGYRUM",Material.PRISMARINE_CRYSTALS,AddUtils.Theme.ITEM1,
            Language.get("Items.HYDRAGYRUM.Name"),Language.getList("Items.HYDRAGYRUM.Lore"));
    public static final SlimefunItemStack HGTLPBBI=AddUtils.themed("HGTLPBBI",CustomHead.SUPPORTER2.getItem(), AddUtils.Theme.ITEM1,
            Language.get("Items.HGTLPBBI.Name"),Language.getList("Items.HGTLPBBI.Lore"));
    public static final SlimefunItemStack DIMENSIONAL_SHARD=AddUtils.themed("DIMENSIONAL_SHARD",Material.PRISMARINE_SHARD,AddUtils.Theme.ITEM1,
            Language.get("Items.DIMENSIONAL_SHARD.Name"),Language.getList("Items.DIMENSIONAL_SHARD.Lore"));
    public static final SlimefunItemStack STAR_GOLD=AddUtils.themed("STAR_GOLD",Material.NETHER_STAR,AddUtils.Theme.ITEM1,
            Language.get("Items.STAR_GOLD.Name"),Language.getList("Items.STAR_GOLD.Lore"));
    public static final SlimefunItemStack VIRTUAL_SPACE=AddUtils.themed("VIRTUAL_SPACE",CustomHead.VSPACE.getItem(), AddUtils.Theme.ITEM1,
            Language.get("Items.VIRTUAL_SPACE.Name"),Language.getList("Items.VIRTUAL_SPACE.Lore"));
    public static final SlimefunItemStack WORLD_FEAT=AddUtils.themed("WORLD_FEAT",Material.GRASS_BLOCK,AddUtils.Theme.ITEM1,
            Language.get("Items.WORLD_FEAT.Name"),Language.getList("Items.WORLD_FEAT.Lore"));
    public static final SlimefunItemStack NETHER_FEAT=AddUtils.themed("NETHER_FEAT",Material.NETHERITE_SCRAP,AddUtils.Theme.ITEM1,
            Language.get("Items.NETHER_FEAT.Name"),Language.getList("Items.NETHER_FEAT.Lore"));
    public static final SlimefunItemStack END_FEAT=AddUtils.themed("END_FEAT",Material.CHORUS_PLANT,AddUtils.Theme.ITEM1,
            Language.get("Items.END_FEAT.Name"),Language.getList("Items.END_FEAT.Lore"));
    public static final SlimefunItemStack STACKFRAME=AddUtils.themed("STACKFRAME",Material.BEDROCK,AddUtils.Theme.ITEM1,
            Language.get("Items.STACKFRAME.Name"),Language.getList("Items.STACKFRAME.Lore"));

    public static final SlimefunItemStack STAR_GOLD_INGOT=AddUtils.themed("STAR_GOLD_INGOT",Material.GOLD_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.STAR_GOLD_INGOT.Name"),Language.getList("Items.STAR_GOLD_INGOT.Lore"));
    public static final SlimefunItemStack ABSTRACT_INGOT=AddUtils.themed("ABSTRACT_INGOT",Material.IRON_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.ABSTRACT_INGOT.Name"),Language.getList("Items.ABSTRACT_INGOT.Lore"));
    public static final SlimefunItemStack PDCECDMD=AddUtils.themed("PDCECDMD",CustomHead.BUSHIGEMEN.getItem(), AddUtils.Theme.ITEM1,
            Language.get("Items.PDCECDMD.Name"),Language.getList("Items.PDCECDMD.Lore"));
    public static final SlimefunItemStack REINFORCED_CHIP_INGOT=AddUtils.themed("REINFORCED_CHIP_INGOT",Material.POISONOUS_POTATO,AddUtils.Theme.ITEM1,
            Language.get("Items.REINFORCED_CHIP_INGOT.Name"),Language.getList("Items.REINFORCED_CHIP_INGOT.Lore"));
    public static final SlimefunItemStack ATOM_INGOT=AddUtils.themed("ATOM_INGOT",Material.ECHO_SHARD,AddUtils.Theme.ITEM1,
            Language.get("Items.ATOM_INGOT.Name"),Language.getList("Items.ATOM_INGOT.Lore"));

    public static final SlimefunItemStack LMOTOR=AddUtils.themed("LMOTOR",CustomHead.MOTOR.getItem(),AddUtils.Theme.ITEM1,
            Language.get("Items.LMOTOR.Name"),Language.getList("Items.LMOTOR.Lore"));
    public static final SlimefunItemStack LPLATE=AddUtils.themed("LPLATE",Material.PAPER,AddUtils.Theme.ITEM1,
            Language.get("Items.LPLATE.Name"),Language.getList("Items.LPLATE.Lore"));
    public static final SlimefunItemStack METAL_CORE=AddUtils.themed("METAL_CORE",Material.NETHERITE_BLOCK,AddUtils.Theme.ITEM1,
            Language.get("Items.METAL_CORE.Name"),Language.getList("Items.METAL_CORE.Lore"));
    public static final SlimefunItemStack SMELERY_CORE=AddUtils.themed("SMELERY_CORE",Material.IRON_BLOCK,AddUtils.Theme.ITEM1,
            Language.get("Items.SMELERY_CORE.Name"),Language.getList("Items.SMELERY_CORE.Lore"));
    public static final SlimefunItemStack MASS_CORE=AddUtils.themed("MASS_CORE",Material.COAL_BLOCK,AddUtils.Theme.ITEM1,
            Language.get("Items.MASS_CORE.Name"),Language.getList("Items.MASS_CORE.Lore"));
    public static final SlimefunItemStack TECH_CORE=AddUtils.themed("TECH_CORE",Material.BEACON,AddUtils.Theme.ITEM1,
            Language.get("Items.TECH_CORE.Name"),Language.getList("Items.TECH_CORE.Lore"));
    public static final SlimefunItemStack SPACE_PLATE=AddUtils.themed("SPACE_PLATE",Material.PAPER,AddUtils.Theme.ITEM1,
            Language.get("Items.SPACE_PLATE.Name"),Language.getList("Items.SPACE_PLATE.Lore"));
    public static final SlimefunItemStack LOGIC_CORE=AddUtils.themed("LOGIC_CORE",Material.NETHER_STAR,AddUtils.Theme.ITEM1,
            Language.get("Items.LOGIC_CORE.Name"),Language.getList("Items.LOGIC_CORE.Lore"));
    public static final SlimefunItemStack FINAL_FRAME=AddUtils.themed("FINAL_FRAME",Material.BUDDING_AMETHYST,AddUtils.Theme.MULTIBLOCK1,
            Language.get("Items.FINAL_FRAME.Name"),Language.getList("Items.FINAL_FRAME.Lore"));
    public static final SlimefunItemStack REDSTONE_ENGINE=AddUtils.themed("REDSTONE_ENGINE",Material.SLIME_BLOCK,AddUtils.Theme.ITEM1,
            Language.get("Items.REDSTONE_ENGINE.Name"),Language.getList("Items.REDSTONE_ENGINE.Lore"));
    public static final SlimefunItemStack HYPER_LINK=AddUtils.themed("HYPER_LINK",Material.NETHER_STAR,AddUtils.Theme.ITEM1,
            Language.get("Items.HYPER_LINK.Name"),Language.getList("Items.HYPER_LINK.Lore"));

    public static final SlimefunItemStack SAMPLE_HEAD=AddUtils.themed("SAMPLE_HEAD",Material.PLAYER_HEAD,AddUtils.Theme.ITEM1,
            Language.get("Items.SAMPLE_HEAD.Name"),Language.getList("Items.SAMPLE_HEAD.Lore"));
    public static final SlimefunItemStack CHIP=AddUtils.themed("CHIP",Material.NAME_TAG,AddUtils.Theme.ITEM1,
            Language.get("Items.CHIP.Name"),Language.getList("Items.CHIP.Lore"));
    public static final SlimefunItemStack CHIP_CORE=AddUtils.themed("CHIP_CORE",CustomHead.CORE.getItem(), AddUtils.Theme.ITEM1,
            Language.get("Items.CHIP_CORE.Name"),Language.getList("Items.CHIP_CORE.Lore"));
    public static final SlimefunItemStack LSINGULARITY=AddUtils.themed("LSINGULARITY",Material.FIREWORK_STAR,AddUtils.Theme.ITEM1,
            Language.get("Items.LSINGULARITY.Name"),Language.getList("Items.LSINGULARITY.Lore"));
    public static final SlimefunItemStack RADIATION_CLEAR=AddUtils.themed("RADIATION_CLEAR",Material.GLASS_BOTTLE,AddUtils.Theme.ITEM1,
            Language.get("Items.RADIATION_CLEAR.Name"),Language.getList("Items.RADIATION_CLEAR.Lore"));
    public static final SlimefunItemStack ANTIMASS_CLEAR=AddUtils.themed("ANTIMASS_CLEAR",Material.GLASS_BOTTLE,AddUtils.Theme.ITEM1,
            Language.get("Items.ANTIMASS_CLEAR.Name"),Language.getList("Items.ANTIMASS_CLEAR.Lore"));
    public static final SlimefunItemStack BISILVER=AddUtils.themed("BISILVER",Material.IRON_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.BISILVER.Name"),Language.getList("Items.BISILVER.Lore"));
    public static final SlimefunItemStack PAGOLD=AddUtils.themed("PAGOLD",Material.GOLD_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.PAGOLD.Name"),Language.getList("Items.PAGOLD.Lore"));
    public static final SlimefunItemStack LASER=AddUtils.themed("LASER",CustomHead.LASER.getItem(), AddUtils.Theme.ITEM1,
            Language.get("Items.LASER.Name"),Language.getList("Items.LASER.Lore"));
    public static final SlimefunItemStack ANTIMASS=AddUtils.themed("ANTIMASS",Material.SCULK,AddUtils.Theme.ITEM1,
            Language.get("Items.ANTIMASS.Name"),Language.getList("Items.ANTIMASS.Lore"));
    public static final SlimefunItemStack VIRTUALWORLD=AddUtils.themed("VIRTUALWORLD",CustomHead.END_BLOCK.getItem(), AddUtils.Theme.ITEM1,
            Language.get("Items.VIRTUALWORLD.Name"),Language.getList("Items.VIRTUALWORLD.Lore"));
    public static final SlimefunItemStack SAMPLE_SPAWNER=AddUtils.themed("SAMPLE_SPAWNER",Material.SPAWNER,AddUtils.Theme.ITEM1,
            Language.get("Items.SAMPLE_SPAWNER.Name"),Language.getList("Items.SAMPLE_SPAWNER.Lore"));
    public static final SlimefunItemStack HOLOGRAM_REMOVER=AddUtils.themed("HOLOGRAM_REMOVER",Material.LIGHT,AddUtils.Theme.ITEM1,
            Language.get("Items.HOLOGRAM_REMOVER.Name"),Language.getList("Items.HOLOGRAM_REMOVER.Lore"));
    public static final SlimefunItemStack WITHERPROOF_REDSTONE=AddUtils.themed("WITHERPROOF_REDSTONE",Material.REDSTONE_BLOCK,AddUtils.Theme.ITEM1,
            Language.get("Items.WITHERPROOF_REDSTONE.Name"),Language.getList("Items.WITHERPROOF_REDSTONE.Lore"));
    public static final SlimefunItemStack WITHERPROOF_REDS=AddUtils.themed("WITHERPROOF_REDS",Material.REDSTONE,AddUtils.Theme.ITEM1,
            Language.get("Items.WITHERPROOF_REDS.Name"),Language.getList("Items.WITHERPROOF_REDS.Lore"));
    public static final SlimefunItemStack BEDROCK_BREAKER=AddUtils.themed("BEDROCK_BREAKER",Material.PISTON,AddUtils.Theme.ITEM1,
            Language.get("Items.BEDROCK_BREAKER.Name"),Language.getList("Items.BEDROCK_BREAKER.Lore"));
    public static final SlimefunItemStack LASER_GUN=AddUtils.themed("LASER_GUN",CustomHead.LASER_GUN.getItem(), AddUtils.Theme.ITEM1,
            Language.get("Items.LASER_GUN.Name"),Language.getList("Items.LASER_GUN.Lore"));
    public static final SlimefunItemStack SUPERSPONGE=AddUtils.themed("SUPERSPONGE",Material.SPONGE,AddUtils.Theme.ITEM1,
            Language.get("Items.SUPERSPONGE.Name"),Language.getList("Items.SUPERSPONGE.Lore"));
    public static final SlimefunItemStack SUPERSPONGE_USED=AddUtils.themed("SUPERSPONGE_USED",Material.WET_SPONGE,AddUtils.Theme.ITEM1,
            Language.get("Items.SUPERSPONGE_USED.Name"),Language.getList("Items.SUPERSPONGE_USED.Lore"));
    public static final SlimefunItemStack TRACE_ARROW=AddUtils.themed("TRACE_ARROW",Material.CHERRY_SAPLING,AddUtils.Theme.ITEM1,
            Language.get("Items.TRACE_ARROW.Name"),Language.getList("Items.TRACE_ARROW.Lore"));
    public static final SlimefunItemStack DIMENSIONAL_SINGULARITY=AddUtils.themed("DIMENSIONAL_SINGULARITY",Material.AMETHYST_SHARD,AddUtils.Theme.ITEM1,
            Language.get("Items.DIMENSIONAL_SINGULARITY.Name"),Language.getList("Items.DIMENSIONAL_SINGULARITY.Lore"));
    public static final SlimefunItemStack RTP_RUNE=AddUtils.themed("RTP_RUNE", CustomFireworkStar.RTP_RUNE.getItem(),AddUtils.Theme.ITEM1,
            Language.get("Items.RTP_RUNE.Name"),Language.getList("Items.RTP_RUNE.Lore"));
    public static final SlimefunItemStack SPACE_CARD=AddUtils.themed("SPACE_CARD",Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,AddUtils.Theme.ITEM1,
            Language.get("Items.SPACE_CARD.Name"),Language.getList("Items.SPACE_CARD.Lore"));
    public static final ItemStack UNBREAKING_SHIELD=AddUtils.themed(Material.SHIELD, AddUtils.Theme.ITEM1,
            Language.get("Items.UNBREAKING_SHIELD.Name"),Language.getList("Items.UNBREAKING_SHIELD.Lore"));
    public static final SlimefunItemStack FAKE_UI=AddUtils.themed("FAKE_UI",Material.LIGHT_GRAY_STAINED_GLASS_PANE,AddUtils.Theme.ITEM1,
            Language.get("Items.FAKE_UI.Name"),Language.getList("Items.FAKE_UI.Lore"));
    public static final SlimefunItemStack ANTIGRAVITY=AddUtils.themed("ANTIGRAVITY",Material.NETHERITE_INGOT,AddUtils.Theme.ITEM1,
            Language.get("Items.ANTIGRAVITY.Name"),Language.getList("Items.ANTIGRAVITY.Lore"));
    public static final SlimefunItemStack CONFIGURE=AddUtils.themed("CONFIGURE",Material.BLAZE_ROD,AddUtils.Theme.CARGO1,
            Language.get("Items.CONFIGURE.Name"),Language.getList("Items.CONFIGURE.Lore"));
    public static final SlimefunItemStack AMPLIFY_BASE=AddUtils.themed("AMPLIFY_BASE",Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,AddUtils.Theme.ITEM1,
            Language.get("Items.AMPLIFY_BASE.Name"),Language.getList("Items.AMPLIFY_BASE.Lore"));
    public static final SlimefunItemStack SWAMP_SPEED=AddUtils.themed("SWAMP_SPEED",Material.MUSIC_DISC_13,AddUtils.Theme.ITEM1,
            Language.get("Items.SWAMP_SPEED.Name"),Language.getList("Items.SWAMP_SPEED.Lore"));
    public static final SlimefunItemStack SWAMP_RANGE=AddUtils.themed("SWAMP_RANGE",Material.MUSIC_DISC_CHIRP,AddUtils.Theme.ITEM1,
            Language.get("Items.SWAMP_RANGE.Name"),Language.getList("Items.SWAMP_RANGE.Lore"));
    //nachines
    public static final SlimefunItemStack HEAD_ANALYZER=AddUtils.themed("HEAD_ANALYZER",Material.SOUL_CAMPFIRE, AddUtils.Theme.MACHINE1,
            Language.get("Machines.HEAD_ANALYZER.Name"),Language.getList("Machines.HEAD_ANALYZER.Lore"));
    public static final SlimefunItemStack RECIPE_LOGGER=AddUtils.themed("RECIPE_LOGGER",Material.FLETCHING_TABLE, AddUtils.Theme.MACHINE1,
            Language.get("Machines.RECIPE_LOGGER.Name"),Language.getList("Machines.RECIPE_LOGGER.Lore"));
    public static final SlimefunItemStack BOOL_GENERATOR=AddUtils.themed("BOOL_GENERATOR",Material.REDSTONE_TORCH,AddUtils.Theme.MACHINE1,
            Language.get("Machines.BOOL_GENERATOR.Name"),Language.getList("Machines.BOOL_GENERATOR.Lore"));
    public static final SlimefunItemStack LOGIC_REACTOR=AddUtils.themed("LOGIC_REACTOR",CustomHead.LOGIC_REACTOR.getItem(),AddUtils.Theme.MACHINE1,
            Language.get("Machines.LOGIC_REACTOR.Name"),Language.getList("Machines.LOGIC_REACTOR.Lore"));
    public static final SlimefunItemStack BUG_CRAFTER=AddUtils.themed("BUG_CRAFTER",CustomHead.BUG_CRATFER.getItem(),AddUtils.Theme.MACHINE1,
            Language.get("Machines.BUG_CRAFTER.Name"),Language.getList("Machines.BUG_CRAFTER.Lore"));
    public static final SlimefunItemStack ENDFRAME_MACHINE=AddUtils.themed("ENDFRAME_MACHINE",Material.END_PORTAL_FRAME,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ENDFRAME_MACHINE.Name"),Language.getList("Machines.ENDFRAME_MACHINE.Lore"));
    public static final SlimefunItemStack LVOID_GENERATOR=AddUtils.themed("LVOID_GENERATOR",Material.SOUL_LANTERN,AddUtils.Theme.MACHINE1,
            Language.get("Machines.LVOID_GENERATOR.Name"),Language.getList("Machines.LVOID_GENERATOR.Lore"));
    public static final SlimefunItemStack SPECIAL_CRAFTER=AddUtils.themed("SPECIAL_CRAFTER",Material.LOOM,AddUtils.Theme.MACHINE1,
            Language.get("Machines.SPECIAL_CRAFTER.Name"),Language.getList("Machines.SPECIAL_CRAFTER.Lore"));
    public static final SlimefunItemStack STAR_SMELTERY=AddUtils.themed("STAR_SMELTERY",Material.BLAST_FURNACE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.STAR_SMELTERY.Name"),Language.getList("Machines.STAR_SMELTERY.Lore"));

    public static final SlimefunItemStack INFINITY_AUTOCRAFT=AddUtils.themed("INFINITY_AUTOCRAFT",Material.CRYING_OBSIDIAN,AddUtils.Theme.MACHINE1,
            Language.get("Machines.INFINITY_AUTOCRAFT.Name"),Language.getList("Machines.INFINITY_AUTOCRAFT.Lore"));
    public static final SlimefunItemStack CHIP_MAKER=AddUtils.themed("CHIP_MAKER",Material.CHISELED_BOOKSHELF,AddUtils.Theme.MACHINE1,
            Language.get("Machines.CHIP_MAKER.Name"),Language.getList("Machines.CHIP_MAKER.Lore"));
    public static final SlimefunItemStack CHIP_CONSUMER=AddUtils.themed("CHIP_CONSUMER",Material.TORCH,AddUtils.Theme.MACHINE1,
            Language.get("Machines.CHIP_CONSUMER.Name"),Language.getList("Machines.CHIP_CONSUMER.Lore"));
    public static final SlimefunItemStack CHIP_BICONSUMER=AddUtils.themed("CHIP_BICONSUMER",Material.LANTERN,AddUtils.Theme.MACHINE1,
            Language.get("Machines.CHIP_BICONSUMER.Name"),Language.getList("Machines.CHIP_BICONSUMER.Lore"));
    public static final SlimefunItemStack SEQ_CONSTRUCTOR=AddUtils.themed("SEQ_CONSTRUCTOR",Material.BAMBOO_MOSAIC,AddUtils.Theme.MACHINE1,
            Language.get("Machines.SEQ_CONSTRUCTOR.Name"),Language.getList("Machines.SEQ_CONSTRUCTOR.Lore"));
    public static final SlimefunItemStack STACKMACHINE=AddUtils.themed("STACKMACHINE",Material.FURNACE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.STACKMACHINE.Name"),Language.getList("Machines.STACKMACHINE.Lore"));
    public static final SlimefunItemStack ENERGY_TRASH=AddUtils.themed("ENERGY_TRASH", SlimefunItems.PORTABLE_DUSTBIN.getItem().getItem().clone()
            ,AddUtils.Theme.MACHINE1, Language.get("Machines.ENERGY_TRASH.Name"),Language.getList("Machines.ENERGY_TRASH.Lore"));
    public static final SlimefunItemStack OPPO_GEN=AddUtils.themed("OPPO_GEN",CustomHead.HOT_MACHINE.getItem(), AddUtils.Theme.MACHINE1,
            Language.get("Machines.OPPO_GEN.Name"),Language.getList("Machines.OPPO_GEN.Lore"));
    public static final SlimefunItemStack ARC_REACTOR=AddUtils.themed("ARC_REACTOR",CustomHead.REACTOR.getItem(),AddUtils.Theme.MACHINE1,
            Language.get("Machines.ARC_REACTOR.Name"),Language.getList("Machines.ARC_REACTOR.Lore"));
    public static final SlimefunItemStack ENERGY_AMPLIFIER=AddUtils.themed("ENERGY_AMPLIFIER",Material.NETHERITE_BLOCK,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ENERGY_AMPLIFIER.Name"),Language.getList("Machines.ENERGY_AMPLIFIER.Lore"));
    public static final SlimefunItemStack ADVANCED_CHIP_MAKER=AddUtils.themed("ADVANCED_CHIP_MAKER",Material.CHISELED_BOOKSHELF,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ADVANCED_CHIP_MAKER.Name"),Language.getList("Machines.ADVANCED_CHIP_MAKER.Lore"));
    public static final SlimefunItemStack CHIP_REACTOR=AddUtils.themed("CHIP_REACTOR",Material.JUKEBOX,AddUtils.Theme.MACHINE1,
            Language.get("Machines.CHIP_REACTOR.Name"),Language.getList("Machines.CHIP_REACTOR.Lore"));
    public static final SlimefunItemStack DUST_EXTRACTOR=AddUtils.themed("DUST_EXTRACTOR",Material.CHISELED_STONE_BRICKS,AddUtils.Theme.MACHINE1,
            Language.get("Machines.DUST_EXTRACTOR.Name"),Language.getList("Machines.DUST_EXTRACTOR.Lore"));
    public static final SlimefunItemStack FURNACE_FACTORY=AddUtils.themed("FURNACE_FACTORY",Material.FURNACE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.FURNACE_FACTORY.Name"),Language.getList("Machines.FURNACE_FACTORY.Lore"));
    public static final SlimefunItemStack INGOT_FACTORY=AddUtils.themed("INGOT_FACTORY",Material.RED_GLAZED_TERRACOTTA,AddUtils.Theme.MACHINE1,
            Language.get("Machines.INGOT_FACTORY.Name"),Language.getList("Machines.INGOT_FACTORY.Lore"));
    public static final SlimefunItemStack FINAL_LASER=AddUtils.themed("FINAL_LASER",Material.DROPPER,AddUtils.Theme.MACHINE1,
            Language.get("Machines.FINAL_LASER.Name"),Language.getList("Machines.FINAL_LASER.Lore"));
    public static final SlimefunItemStack FINAL_CONVERTOR=AddUtils.themed("FINAL_CONVERTOR",Material.WARPED_HYPHAE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.FINAL_CONVERTOR.Name"),Language.getList("Machines.FINAL_CONVERTOR.Lore"));
    public static final SlimefunItemStack PRESSOR_FACTORY=AddUtils.themed("PRESSOR_FACTORY",Material.PISTON,AddUtils.Theme.MACHINE1,
            Language.get("Machines.PRESSOR_FACTORY.Name"),Language.getList("Machines.PRESSOR_FACTORY.Lore"));
    public static final SlimefunItemStack CRAFTER=AddUtils.themed("CRAFTER",Material.CRAFTING_TABLE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.CRAFTER.Name"),Language.getList("Machines.CRAFTER.Lore"));
    public static final SlimefunItemStack EASYSTACKMACHINE=AddUtils.themed("EASYSTACKMACHINE",Material.FURNACE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.EASYSTACKMACHINE.Name"),Language.getList("Machines.EASYSTACKMACHINE.Lore"));
    public static final SlimefunItemStack CONVERTOR=AddUtils.themed("CONVERTOR",Material.SEA_LANTERN,AddUtils.Theme.MACHINE1,
            Language.get("Machines.CONVERTOR.Name"),Language.getList("Machines.CONVERTOR.Lore"));
    public static final SlimefunItemStack VIRTUAL_KILLER=AddUtils.themed("VIRTUAL_KILLER",Material.STONECUTTER,AddUtils.Theme.MACHINE1,
            Language.get("Machines.VIRTUAL_KILLER.Name"),Language.getList("Machines.VIRTUAL_KILLER.Lore"));
    public static final SlimefunItemStack INF_MOBSIMULATION=AddUtils.themed("INF_MOBSIMULATION",Material.GILDED_BLACKSTONE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.INF_MOBSIMULATION.Name"),Language.getList("Machines.INF_MOBSIMULATION.Lore"));
    public static final SlimefunItemStack INF_GEOQUARRY=AddUtils.themed("INF_GEOQUARRY",Material.CHISELED_QUARTZ_BLOCK,AddUtils.Theme.MACHINE1,
            Language.get("Machines.INF_GEOQUARRY.Name"),Language.getList("Machines.INF_GEOQUARRY.Lore"));
    public static final SlimefunItemStack RAND_EDITOR=AddUtils.themed("RAND_EDITOR",Material.ENCHANTING_TABLE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.RAND_EDITOR.Name"),Language.getList("Machines.RAND_EDITOR.Lore"));
    public static final SlimefunItemStack ATTR_OP=AddUtils.themed("ATTR_OP",Material.ENCHANTING_TABLE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ATTR_OP.Name"),Language.getList("Machines.ATTR_OP.Lore"));
    public static final SlimefunItemStack GRIND_FACTORY=AddUtils.themed("GRIND_FACTORY",Material.GRINDSTONE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.GRIND_FACTORY.Name"),Language.getList("Machines.GRIND_FACTORY.Lore"));
    public static final SlimefunItemStack TNT_GEN=AddUtils.themed("TNT_GEN",Material.NOTE_BLOCK,AddUtils.Theme.MACHINE1,
            Language.get("Machines.TNT_GEN.Name"),Language.getList("Machines.TNT_GEN.Lore"));
    public static final SlimefunItemStack ADVANCE_BREWER=AddUtils.themed("ADVANCE_BREWER",Material.SMOKER,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ADVANCE_BREWER.Name"),Language.getList("Machines.ADVANCE_BREWER.Lore"));
    public static final SlimefunItemStack SIMU_LVOID=AddUtils.themed("SIMU_LVOID",Material.SOUL_TORCH,AddUtils.Theme.MACHINE1,
            Language.get("Machines.SIMU_LVOID.Name"),Language.getList("Machines.SIMU_LVOID.Lore"));
    public static final SlimefunItemStack SPACETOWER =AddUtils.themed("SPACETOWER",Material.SHROOMLIGHT,AddUtils.Theme.MACHINE1,
            Language.get("Machines.SPACETOWER.Name"),Language.getList("Machines.SPACETOWER.Lore"));
    public static final SlimefunItemStack SPACETOWER_FRAME=AddUtils.themed("SPACETOWER_FRAME",Material.AMETHYST_BLOCK,AddUtils.Theme.MACHINE1,
            Language.get("Machines.SPACETOWER_FRAME.Name"),Language.getList("Machines.SPACETOWER_FRAME.Lore"));
    public static final SlimefunItemStack ITEM_OP=AddUtils.themed("ITEM_OP",Material.SMITHING_TABLE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ITEM_OP.Name"),Language.getList("Machines.ITEM_OP.Lore"));
    public static final SlimefunItemStack CHUNK_CHARGER=AddUtils.themed("CHUNK_CHARGER",Material.SCULK_SHRIEKER,AddUtils.Theme.MACHINE1,
            Language.get("Machines.CHUNK_CHARGER.Name"),Language.getList("Machines.CHUNK_CHARGER.Lore"));
    public static final SlimefunItemStack INGOT_CONVERTOR=AddUtils.themed("INGOT_CONVERTOR",Material.PINK_GLAZED_TERRACOTTA,AddUtils.Theme.MACHINE1,
            Language.get("Machines.INGOT_CONVERTOR.Name"),Language.getList("Machines.INGOT_CONVERTOR.Lore"));
    public static final SlimefunItemStack LINE_CHARGER=AddUtils.themed("LINE_CHARGER",Material.DEEPSLATE_TILE_WALL,AddUtils.Theme.MACHINE1,
            Language.get("Machines.LINE_CHARGER.Name"),Language.getList("Machines.LINE_CHARGER.Lore"));
    public static final SlimefunItemStack LINE_CHARGER_PLUS=AddUtils.themed("LINE_CHARGER_PLUS",Material.DEEPSLATE_TILE_WALL,AddUtils.Theme.MACHINE1,
            Language.get("Machines.LINE_CHARGER_PLUS.Name"),Language.getList("Machines.LINE_CHARGER_PLUS.Lore"));
    public static final SlimefunItemStack ADJ_COLLECTOR=AddUtils.themed("ADJ_COLLECTOR",Material.RED_NETHER_BRICKS,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ADJ_COLLECTOR.Name"),Language.getList("Machines.ADJ_COLLECTOR.Lore"));
    public static final SlimefunItemStack ADJ_COLLECTOR_PLUS=AddUtils.themed("ADJ_COLLECTOR_PLUS",Material.RED_NETHER_BRICKS,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ADJ_COLLECTOR_PLUS.Name"),Language.getList("Machines.ADJ_COLLECTOR_PLUS.Lore"));
    public static final SlimefunItemStack LINE_COLLECTOR=AddUtils.themed("LINE_COLLECTOR",Material.RED_NETHER_BRICK_WALL,AddUtils.Theme.MACHINE1,
            Language.get("Machines.LINE_COLLECTOR.Name"),Language.getList("Machines.LINE_COLLECTOR.Lore"));
    public static final SlimefunItemStack LINE_COLLECTOR_PLUS=AddUtils.themed("LINE_COLLECTOR_PLUS",Material.RED_NETHER_BRICK_WALL,AddUtils.Theme.MACHINE1,
            Language.get("Machines.LINE_COLLECTOR_PLUS.Name"),Language.getList("Machines.LINE_COLLECTOR_PLUS.Lore"));
    public static final SlimefunItemStack ENERGY_STORAGE_NONE=AddUtils.themed("ENERGY_STORAGE_NONE",Material.CRACKED_DEEPSLATE_TILES,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ENERGY_STORAGE_NONE.Name"),Language.getList("Machines.ENERGY_STORAGE_NONE.Lore"));
    public static final SlimefunItemStack ENERGY_STORAGE_IN=AddUtils.themed("ENERGY_STORAGE_IN",Material.CHISELED_DEEPSLATE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ENERGY_STORAGE_IN.Name"),Language.getList("Machines.ENERGY_STORAGE_IN.Lore"));
    public static final SlimefunItemStack ENERGY_STORAGE_IO=AddUtils.themed("ENERGY_STORAGE_IO",Material.CHISELED_POLISHED_BLACKSTONE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ENERGY_STORAGE_IO.Name"),Language.getList("Machines.ENERGY_STORAGE_IO.Lore"));
    public static final SlimefunItemStack ADJ_CHARGER=AddUtils.themed("ADJ_CHARGER",Material.DEEPSLATE_BRICKS,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ADJ_CHARGER.Name"),Language.getList("Machines.ADJ_CHARGER.Lore"));
    public static final SlimefunItemStack ADJ_CHARGER_PLUS=AddUtils.themed("ADJ_CHARGER_PLUS",Material.DEEPSLATE_BRICKS,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ADJ_CHARGER_PLUS.Name"),Language.getList("Machines.ADJ_CHARGER_PLUS.Lore"));
    public static final SlimefunItemStack ENERGY_PIPE=AddUtils.themed("ENERGY_PIPE",Material.LIGHTNING_ROD,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ENERGY_PIPE.Name"),Language.getList("Machines.ENERGY_PIPE.Lore"));
    public static final SlimefunItemStack ENERGY_PIPE_PLUS=AddUtils.themed("ENERGY_PIPE_PLUS",Material.END_ROD,AddUtils.Theme.MACHINE1,
            Language.get("Machines.ENERGY_PIPE_PLUS.Name"),Language.getList("Machines.ENERGY_PIPE_PLUS.Lore"));
    public static final SlimefunItemStack FINAL_CRAFT=AddUtils.themed("FINAL_CRAFT",Material.BEACON,AddUtils.Theme.MACHINE1,
            Language.get("Machines.FINAL_CRAFT.Name"),Language.getList("Machines.FINAL_CRAFT.Lore"));
    public static final SlimefunItemStack VIRTUAL_EXPLORER=AddUtils.themed("VIRTUAL_EXPLORER",Material.DECORATED_POT,AddUtils.Theme.MACHINE1,
            Language.get("Machines.VIRTUAL_EXPLORER.Name"),Language.getList("Machines.VIRTUAL_EXPLORER.Lore"));
    public static final SlimefunItemStack TIMER_BLOCKENTITY=AddUtils.themed("TIMER_BLOCKENTITY",Material.REDSTONE_TORCH,AddUtils.Theme.MACHINE1,
            Language.get("Machines.TIMER_BLOCKENTITY.Name"),Language.getList("Machines.TIMER_BLOCKENTITY.Lore"));
    public static final SlimefunItemStack TIMER_SF=AddUtils.themed("TIMER_SF",Material.SOUL_TORCH,AddUtils.Theme.MACHINE1,
            Language.get("Machines.TIMER_SF.Name"),Language.getList("Machines.TIMER_SF.Lore"));
    public static final SlimefunItemStack TIMER_SF_SEQ=AddUtils.themed("TIMER_SF_SEQ",Material.SOUL_TORCH,AddUtils.Theme.MACHINE1,
            Language.get("Machines.TIMER_SF_SEQ.Name"),Language.getList("Machines.TIMER_SF_SEQ.Lore"));
    //manuals
    public static final SlimefunItemStack MANUAL_CORE=AddUtils.themed("MANUAL_CORE",Material.AMETHYST_SHARD,AddUtils.Theme.ITEM1,
            Language.get("Manuals.MANUAL_CORE.Name"),Language.getList("Manuals.MANUAL_CORE.Lore"));
    public static final SlimefunItemStack CRAFT_MANUAL=AddUtils.themed("CRAFT_MANUAL",Material.CRAFTING_TABLE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.CRAFT_MANUAL.Name"),Language.getList("Manuals.CRAFT_MANUAL.Lore"));
    public static final SlimefunItemStack FURNACE_MANUAL=AddUtils.themed("FURNACE_MANUAL",Material.FURNACE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.FURNACE_MANUAL.Name"),Language.getList("Manuals.FURNACE_MANUAL.Lore"));
    public static final SlimefunItemStack ENHANCED_CRAFT_MANUAL=AddUtils.themed("ENHANCED_CRAFT_MANUAL",Material.CRAFTING_TABLE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.ENHANCED_CRAFT_MANUAL.Name"),Language.getList("Manuals.ENHANCED_CRAFT_MANUAL.Lore"));
    public static final SlimefunItemStack GRIND_MANUAL=AddUtils.themed("GRIND_MANUAL",Material.DISPENSER,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.GRIND_MANUAL.Name"),Language.getList("Manuals.GRIND_MANUAL.Lore"));
    public static final SlimefunItemStack ARMOR_FORGE_MANUAL=AddUtils.themed("ARMOR_FORGE_MANUAL",Material.IRON_BLOCK,AddUtils.Theme.MANUAL1,
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
    public static final SlimefunItemStack MOBDATA_MANUAL=AddUtils.themed("MOBDATA_MANUAL",Material.LODESTONE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.MOBDATA_MANUAL.Name"),Language.getList("Manuals.MOBDATA_MANUAL.Lore"));
    public static final SlimefunItemStack INFINITY_MANUAL=AddUtils.themed("INFINITY_MANUAL",Material.RESPAWN_ANCHOR,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.INFINITY_MANUAL.Name"),Language.getList("Manuals.INFINITY_MANUAL.Lore"));
    public static final SlimefunItemStack NTWWORKBENCH_MANUAL=AddUtils.themed("NTWWORKBENCH_MANUAL",Material.BAMBOO_BLOCK,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.NTWWORKBENCH_MANUAL.Name"),Language.getList("Manuals.NTWWORKBENCH_MANUAL.Lore"));
    public static final SlimefunItemStack MULTIBLOCK_MANUAL=AddUtils.themed("MULTIBLOCK_MANUAL",Material.BRICKS,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.MULTIBLOCK_MANUAL.Name"),Language.getList("Manuals.MULTIBLOCK_MANUAL.Lore"));
    public static final SlimefunItemStack FINAL_MANUAL=AddUtils.themed("FINAL_MANUAL",Material.REINFORCED_DEEPSLATE,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.FINAL_MANUAL.Name"),Language.getList("Manuals.FINAL_MANUAL.Lore"));
    public static final SlimefunItemStack REPLACE_CARD=AddUtils.themed("REPLACE_CARD",Material.PRIZE_POTTERY_SHERD,AddUtils.Theme.ITEM1,
            Language.get("Items.REPLACE_CARD.Name"),Language.getList("Items.REPLACE_CARD.Lore"));
    public static final SlimefunItemStack REPLACE_SF_CARD=AddUtils.themed("REPLACE_SF_CARD",Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE,AddUtils.Theme.ITEM1,
            Language.get("Items.REPLACE_SF_CARD.Name"),Language.getList("Items.REPLACE_SF_CARD.Lore"));
    public static final SlimefunItemStack CARD_MAKER=AddUtils.themed("CARD_MAKER",Material.FLETCHING_TABLE,AddUtils.Theme.MACHINE1,
            Language.get("Manuals.CARD_MAKER.Name"),Language.getList("Manuals.CARD_MAKER.Lore"));
    public static final SlimefunItemStack ADV_MANUAL=AddUtils.themed("ADV_MANUAL",Material.LECTERN,AddUtils.Theme.MANUAL1,
            Language.get("Manuals.ADV_MANUAL.Name"),Language.getList("Manuals.ADV_MANUAL.Lore"));
    //generators
    public static final SlimefunItemStack MAGIC_STONE=AddUtils.themed("MAGIC_STONE",Material.COBBLESTONE,AddUtils.Theme.MACHINE2,
            Language.get("Generators.MAGIC_STONE.Name"),Language.getList("Generators.MAGIC_STONE.Lore"));
    public static final SlimefunItemStack BOOL_MG=AddUtils.themed("BOOL_MG",Material.REDSTONE_TORCH,AddUtils.Theme.MACHINE2,
            Language.get("Generators.BOOL_MG.Name"),Language.getList("Generators.BOOL_MG.Lore"));
    public static final SlimefunItemStack OVERWORLD_MINER=AddUtils.themed("OVERWORLD_MINER",Material.SMOOTH_STONE,AddUtils.Theme.MACHINE2,
            Language.get("Generators.OVERWORLD_MINER.Name"),Language.getList("Generators.OVERWORLD_MINER.Lore"));
    public static final SlimefunItemStack NETHER_MINER=AddUtils.themed("NETHER_MINER",Material.CRIMSON_NYLIUM,AddUtils.Theme.MACHINE2,
            Language.get("Generators.NETHER_MINER.Name"),Language.getList("Generators.NETHER_MINER.Lore"));
    public static final SlimefunItemStack END_MINER =AddUtils.themed("END_MINER",Material.END_STONE_BRICKS,AddUtils.Theme.MACHINE2,
            Language.get("Generators.END_MINER.Name"),Language.getList("Generators.END_MINER.Lore"));
    public static final SlimefunItemStack DIMENSION_MINER=AddUtils.themed("DIMENSION_MINER",Material.CRYING_OBSIDIAN,AddUtils.Theme.MACHINE2,
            Language.get("Generators.DIMENSION_MINER.Name"),Language.getList("Generators.DIMENSION_MINER.Lore"));
    public static final SlimefunItemStack REDSTONE_MG=AddUtils.themed("REDSTONE_MG",Material.OBSERVER,AddUtils.Theme.MACHINE2,
            Language.get("Generators.REDSTONE_MG.Name"),Language.getList("Generators.REDSTONE_MG.Lore"));
    public static final SlimefunItemStack DUPE_MG=AddUtils.themed("DUPE_MG",Material.STICKY_PISTON,AddUtils.Theme.MACHINE2,
            Language.get("Generators.DUPE_MG.Name"),Language.getList("Generators.DUPE_MG.Lore"));
    public static final SlimefunItemStack ENDDUPE_MG=AddUtils.themed("ENDDUPE_MG",Material.END_PORTAL_FRAME,AddUtils.Theme.MACHINE2,
            Language.get("Generators.ENDDUPE_MG.Name"),Language.getList("Generators.ENDDUPE_MG.Lore"));
    public static final SlimefunItemStack STACKMGENERATOR=AddUtils.themed("STACKMGENERATOR",Material.SMOOTH_STONE,AddUtils.Theme.MACHINE2,
            Language.get("Generators.STACKMGENERATOR.Name"),Language.getList("Generators.STACKMGENERATOR.Lore"));
    public static final SlimefunItemStack REVERSE_GENERATOR=AddUtils.themed("REVERSE_GENERATOR",CustomHead.REVERSE.getItem(), AddUtils.Theme.MACHINE2,
            Language.get("Generators.REVERSE_GENERATOR.Name"),Language.getList("Generators.REVERSE_GENERATOR.Lore"));
    public static final SlimefunItemStack VIRTUAL_MINER=AddUtils.themed("VIRTUAL_MINER",Material.CHERRY_WOOD,AddUtils.Theme.MACHINE2,
            Language.get("Generators.VIRTUAL_MINER.Name"),Language.getList("Generators.VIRTUAL_MINER.Lore"));
    public static final SlimefunItemStack VIRTUAL_PLANT=AddUtils.themed("VIRTUAL_PLANT",Material.STRIPPED_CHERRY_WOOD,AddUtils.Theme.MACHINE2,
            Language.get("Generators.VIRTUAL_PLANT.Name"),Language.getList("Generators.VIRTUAL_PLANT.Lore"));
    public static final SlimefunItemStack MAGIC_PLANT=AddUtils.themed("MAGIC_PLANT",Material.DIRT,AddUtils.Theme.MACHINE2,
            Language.get("Generators.MAGIC_PLANT.Name"),Language.getList("Generators.MAGIC_PLANT.Lore"));
    public static final SlimefunItemStack OVERWORLD_PLANT=AddUtils.themed("OVERWORLD_PLANT",Material.PODZOL,AddUtils.Theme.MACHINE2,
            Language.get("Generators.OVERWORLD_PLANT.Name"),Language.getList("Generators.OVERWORLD_PLANT.Lore"));
    public static final SlimefunItemStack NETHER_PLANT=AddUtils.themed("NETHER_PLANT",Material.WARPED_NYLIUM,AddUtils.Theme.MACHINE2,
            Language.get("Generators.NETHER_PLANT.Name"),Language.getList("Generators.NETHER_PLANT.Lore"));
    public static final SlimefunItemStack END_PLANT=AddUtils.themed("END_PLANT",Material.END_STONE,AddUtils.Theme.MACHINE2,
            Language.get("Generators.END_PLANT.Name"),Language.getList("Generators.END_PLANT.Lore"));
    public static final SlimefunItemStack SMELTRY=AddUtils.themed("SMELTRY",Material.FURNACE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.SMELTRY.Name"),Language.getList("Machines.SMELTRY.Lore"));
    public static final SlimefunItemStack STONE_FACTORY=AddUtils.themed("STONE_FACTORY",Material.STONE_BRICKS,AddUtils.Theme.MACHINE2,
            Language.get("Generators.STONE_FACTORY.Name"),Language.getList("Generators.STONE_FACTORY.Lore"));
    public static final SlimefunItemStack TNT_MG=AddUtils.themed("TNT_MG",Material.ANCIENT_DEBRIS,AddUtils.Theme.MACHINE2,
            Language.get("Generators.TNT_MG.Name"),Language.getList("Generators.TNT_MG.Lore"));
    //cargos
    public static final SlimefunItemStack CARGO_PART=AddUtils.themed("CARGO_PART",Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,AddUtils.Theme.ITEM1,
            Language.get("Items.CARGO_PART.Name"),Language.getList("Items.CARGO_PART.Lore"));
    public static final SlimefunItemStack CARGO_CONFIG=AddUtils.themed("CARGO_CONFIG",Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,AddUtils.Theme.ITEM1,
            Language.get("Items.CARGO_CONFIG.Name"),Language.getList("Items.CARGO_CONFIG.Lore"));
    public static final SlimefunItemStack CARGO_CONFIGURATOR=AddUtils.themed("CARGO_CONFIGURATOR",Material.JUKEBOX,AddUtils.Theme.CARGO1,
            Language.get("Items.CARGO_CONFIGURATOR.Name"),Language.getList("Items.CARGO_CONFIGURATOR.Lore"));
    public static final SlimefunItemStack SIMPLE_CARGO=AddUtils.themed("SIMPLE_CARGO",Material.TARGET,AddUtils.Theme.CARGO1,
            Language.get("Cargo.SIMPLE_CARGO.Name"),Language.getList("Cargo.SIMPLE_CARGO.Lore"));
    public static final SlimefunItemStack REMOTE_CARGO=AddUtils.themed("REMOTE_CARGO",Material.CALIBRATED_SCULK_SENSOR,AddUtils.Theme.CARGO1,
            Language.get("Cargo.REMOTE_CARGO.Name"),Language.getList("Cargo.REMOTE_CARGO.Lore"));
    public static final SlimefunItemStack LINE_CARGO=AddUtils.themed("LINE_CARGO",Material.OBSERVER,AddUtils.Theme.CARGO1,
            Language.get("Cargo.LINE_CARGO.Name"),Language.getList("Cargo.LINE_CARGO.Lore"));
    public static final SlimefunItemStack BISORTER=AddUtils.themed("BISORTER",Material.VERDANT_FROGLIGHT,AddUtils.Theme.CARGO1,
            Language.get("Cargo.BISORTER.Name"),Language.getList("Cargo.BISORTER.Lore"));
    public static final SlimefunItemStack QUARSORTER=AddUtils.themed("QUARSORTER",Material.PEARLESCENT_FROGLIGHT,AddUtils.Theme.CARGO1,
            Language.get("Cargo.QUARSORTER.Name"),Language.getList("Cargo.QUARSORTER.Lore"));
    public static final SlimefunItemStack OCTASORTER=AddUtils.themed("OCTASORTER",Material.OCHRE_FROGLIGHT,AddUtils.Theme.CARGO1,
            Language.get("Cargo.OCTASORTER.Name"),Language.getList("Cargo.OCTASORTER.Lore"));
    public static final SlimefunItemStack ADV_TRASH=AddUtils.themed("ADV_TRASH",CustomHead.FIRE_GENERATOR.getItem(), AddUtils.Theme.CARGO1,
            Language.get("Cargo.ADV_TRASH.Name"),Language.getList("Cargo.ADV_TRASH.Lore"));
    public static final SlimefunItemStack STORAGE_OPERATOR=AddUtils.themed("STORAGE_OPERATOR",Material.CARTOGRAPHY_TABLE,AddUtils.Theme.CARGO1,
            Language.get("Cargo.STORAGE_OPERATOR.Name"),Language.getList("Cargo.STORAGE_OPERATOR.Lore"));
    public static final SlimefunItemStack ADV_ADJACENT_CARGO=AddUtils.themed("ADV_ADJACENT_CARGO",Material.TARGET,AddUtils.Theme.CARGO1,
            Language.get("Cargo.ADV_ADJACENT_CARGO.Name"),Language.getList("Cargo.ADV_ADJACENT_CARGO.Lore"));
    public static final SlimefunItemStack ADV_REMOTE_CARGO=AddUtils.themed("ADV_REMOTE_CARGO",Material.CALIBRATED_SCULK_SENSOR,AddUtils.Theme.CARGO1,
            Language.get("Cargo.ADV_REMOTE_CARGO.Name"),Language.getList("Cargo.ADV_REMOTE_CARGO.Lore"));
    public static final SlimefunItemStack ADV_LINE_CARGO=AddUtils.themed("ADV_LINE_CARGO",Material.OBSERVER,AddUtils.Theme.CARGO1,
            Language.get("Cargo.ADV_LINE_CARGO.Name"),Language.getList("Cargo.ADV_LINE_CARGO.Lore"));
    public static final SlimefunItemStack REDSTONE_ADJACENT_CARGO=AddUtils.themed("REDSTONE_ADJACENT_CARGO",Material.REDSTONE_LAMP,AddUtils.Theme.CARGO1,
            Language.get("Cargo.REDSTONE_ADJACENT_CARGO.Name"),Language.getList("Cargo.REDSTONE_ADJACENT_CARGO.Lore"));
    public static final SlimefunItemStack CHIP_ADJ_CARGO=AddUtils.themed("CHIP_ADJ_CARGO",Material.SHROOMLIGHT,AddUtils.Theme.CARGO1,
            Language.get("Cargo.CHIP_ADJ_CARGO.Name"),Language.getList("Cargo.CHIP_ADJ_CARGO.Lore"));
    public static final SlimefunItemStack RESETTER=AddUtils.themed("RESETTER",Material.FLETCHING_TABLE,AddUtils.Theme.CARGO1,
            Language.get("Cargo.RESETTER.Name"),Language.getList("Cargo.RESETTER.Lore"));
    public static final SlimefunItemStack STORAGE_SINGULARITY= AddUtils.themed("STORAGE_SINGULARITY",Material.NETHER_STAR,AddUtils.Theme.ITEM1,
            Language.get("Cargo.STORAGE_SINGULARITY.Name"),Language.getList("Cargo.STORAGE_SINGULARITY.Lore"));
    public static final SlimefunItemStack QUANTUM_LINK=AddUtils.themed("QUANTUM_LINK",Material.NETHER_STAR,AddUtils.Theme.ITEM1,
            Language.get("Cargo.QUANTUM_LINK.Name"),Language.getList("Cargo.QUANTUM_LINK.Lore"));
    public static final SlimefunItemStack INPORT=AddUtils.themed("INPORT",Material.END_STONE,AddUtils.Theme.MACHINE1,
            Language.get("Cargo.INPORT.Name"),Language.getList("Cargo.INPORT.Lore"));
    public static final SlimefunItemStack OUTPORT=AddUtils.themed("OUTPORT",Material.END_STONE,AddUtils.Theme.MACHINE1,
            Language.get("Cargo.OUTPORT.Name"),Language.getList("Cargo.OUTPORT.Lore"));
    public static final SlimefunItemStack IOPORT=AddUtils.themed("IOPORT",Material.PURPUR_PILLAR,AddUtils.Theme.MACHINE1,
            Language.get("Cargo.IOPORT.Name"),Language.getList("Cargo.IOPORT.Lore"));
    public static final SlimefunItemStack STORAGE=AddUtils.themed("STORAGE",Material.LIGHT_GRAY_STAINED_GLASS,AddUtils.Theme.CARGO1,
            Language.get("Cargo.STORAGE.Name"),Language.getList("Cargo.STORAGE.Lore"));
    public static final SlimefunItemStack STORAGE_INPUT=AddUtils.themed("STORAGE_INPUT",Material.BLUE_STAINED_GLASS,AddUtils.Theme.CARGO1,
            Language.get("Cargo.STORAGE_INPUT.Name"),Language.getList("Cargo.STORAGE_INPUT.Lore"));
    public static final SlimefunItemStack STORAGE_OUTPUT=AddUtils.themed("STORAGE_OUTPUT",Material.RED_STAINED_GLASS,AddUtils.Theme.CARGO1,
            Language.get("Cargo.STORAGE_OUTPUT.Name"),Language.getList("Cargo.STORAGE_OUTPUT.Lore"));
    public static final SlimefunItemStack BIFILTER=AddUtils.themed("BIFILTER",Material.PRISMARINE,AddUtils.Theme.CARGO1,
            Language.get("Cargo.BIFILTER.Name"),Language.getList("Cargo.BIFILTER.Lore"));
    public static final SlimefunItemStack QUARFILTER=AddUtils.themed("QUARFILTER",Material.PRISMARINE_BRICKS,AddUtils.Theme.CARGO1,
            Language.get("Cargo.QUARFILTER.Name"),Language.getList("Cargo.QUARFILTER.Lore"));
    public static final SlimefunItemStack OCTAFILTER=AddUtils.themed("OCTAFILTER",Material.DARK_PRISMARINE,AddUtils.Theme.CARGO1,
            Language.get("Cargo.OCTAFILTER.Name"),Language.getList("Cargo.OCTAFILTER.Lore"));
    public static final SlimefunItemStack CARGO_PIP=AddUtils.themed("CARGO_PIP",Material.END_ROD,AddUtils.Theme.CARGO1,
            Language.get("Cargo.CARGO_PIP.Name"),Language.getList("Cargo.CARGO_PIP.Lore"));
    //multiblock
    public static final SlimefunItemStack PORTAL_CORE=AddUtils.themed("PORTAL_CORE",Material.CRYING_OBSIDIAN,AddUtils.Theme.MULTIBLOCK1,
            Language.get("MultiBlock.PORTAL_CORE.Name"),Language.getList("MultiBlock.PORTAL_CORE.Lore"));
    public static final SlimefunItemStack PORTAL_FRAME=AddUtils.themed("PORTAL_FRAME",Material.IRON_BLOCK,AddUtils.Theme.MULTIBLOCK2,
            Language.get("MultiBlock.PORTAL_FRAME.Name"),Language.getList("MultiBlock.PORTAL_FRAME.Lore"));
    public static final SlimefunItemStack SOLAR_REACTOR=AddUtils.themed("SOLAR_REACTOR",Material.LODESTONE,AddUtils.Theme.MULTIBLOCK1,
            Language.get("MultiBlock.SOLAR_REACTOR.Name"),Language.getList("MultiBlock.SOLAR_REACTOR.Lore"));
    public static final SlimefunItemStack SOLAR_REACTOR_FRAME=AddUtils.themed("SOLAR_REACTOR_FRAME",Material.CHISELED_QUARTZ_BLOCK,AddUtils.Theme.MULTIBLOCK2,
            Language.get("MultiBlock.SOLAR_REACTOR_FRAME.Name"),Language.getList("MultiBlock.SOLAR_REACTOR_FRAME.Lore"));
    public static final SlimefunItemStack SOLAR_REACTOR_GLASS=AddUtils.themed("SOLAR_REACTOR_GLASS",Material.TINTED_GLASS,AddUtils.Theme.MULTIBLOCK2,
            Language.get("MultiBlock.SOLAR_REACTOR_GLASS.Name"),Language.getList("MultiBlock.SOLAR_REACTOR_GLASS.Lore"));
    public static final SlimefunItemStack SOLAR_INPUT=AddUtils.themed("SOLAR_INPUT",Material.WAXED_OXIDIZED_COPPER,AddUtils.Theme.MULTIBLOCK2,
            Language.get("MultiBlock.SOLAR_INPUT.Name"),Language.getList("MultiBlock.SOLAR_INPUT.Lore"));
    public static final SlimefunItemStack SOLAR_OUTPUT=AddUtils.themed("SOLAR_OUTPUT",Material.WAXED_COPPER_BLOCK,AddUtils.Theme.MULTIBLOCK2,
            Language.get("MultiBlock.SOLAR_OUTPUT.Name"),Language.getList("MultiBlock.SOLAR_OUTPUT.Lore"));
    public static final SlimefunItemStack TRANSMUTATOR_FRAME=AddUtils.themed("TRANSMUTATOR_FRAME",Material.SMOOTH_STONE, AddUtils.Theme.MULTIBLOCK2,
            Language.get("MultiBlock.TRANSMUTATOR_FRAME.Name"),Language.getList("MultiBlock.TRANSMUTATOR_FRAME.Lore"));
    public static final SlimefunItemStack TRANSMUTATOR_GLASS=AddUtils.themed("TRANSMUTATOR_GLASS",Material.LIGHT_GRAY_STAINED_GLASS,AddUtils.Theme.MULTIBLOCK2,
            Language.get("MultiBlock.TRANSMUTATOR_GLASS.Name"),Language.getList("MultiBlock.TRANSMUTATOR_GLASS.Lore"));
    public static final SlimefunItemStack TRANSMUTATOR_ROD=AddUtils.themed("TRANSMUTATOR_ROD",Material.REINFORCED_DEEPSLATE,AddUtils.Theme.MULTIBLOCK2,
            Language.get("MultiBlock.TRANSMUTATOR_ROD.Name"),Language.getList("MultiBlock.TRANSMUTATOR_ROD.Lore"));
    public static final SlimefunItemStack TRANSMUTATOR=AddUtils.themed("TRANSMUTATOR",Material.FURNACE,AddUtils.Theme.MULTIBLOCK1,
            Language.get("MultiBlock.TRANSMUTATOR.Name"),Language.getList("MultiBlock.TRANSMUTATOR.Lore"));
    public static final SlimefunItemStack FINAL_BASE=AddUtils.themed("FINAL_BASE",Material.POLISHED_DEEPSLATE,AddUtils.Theme.MULTIBLOCK1,
            Language.get("MultiBlock.FINAL_BASE.Name"),Language.getList("MultiBlock.FINAL_BASE.Lore"));
    public static final SlimefunItemStack FINAL_ALTAR=AddUtils.themed("FINAL_ALTAR",Material.CHISELED_DEEPSLATE,AddUtils.Theme.MULTIBLOCK2,
            Language.get("MultiBlock.FINAL_ALTAR.Name"),Language.getList("MultiBlock.FINAL_ALTAR.Lore"));
    public static final SlimefunItemStack SMITH_WORKSHOP=AddUtils.themed("SMITH_WORKSHOP",Material.RESPAWN_ANCHOR,AddUtils.Theme.MACHINE1,
            Language.get("MultiBlock.SMITH_WORKSHOP.Name"),Language.getList("MultiBlock.SMITH_WORKSHOP.Lore"));
    public static final SlimefunItemStack SMITH_INTERFACE_NONE=AddUtils.themed("SMITH_INTERFACE_NONE",Material.SEA_LANTERN,AddUtils.Theme.MACHINE1,
            Language.get("MultiBlock.SMITH_INTERFACE_NONE.Name"),Language.getList("MultiBlock.SMITH_INTERFACE_NONE.Lore"));
    public static final SlimefunItemStack SMITH_INTERFACE_CRAFT=AddUtils.themed("SMITH_INTERFACE_CRAFT",Material.TARGET,AddUtils.Theme.MACHINE1,
            Language.get("MultiBlock.SMITH_INTERFACE_CRAFT.Name"),Language.getList("MultiBlock.SMITH_INTERFACE_CRAFT.Lore"));
    //feat
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

//    public static final SlimefunItemStack INPORT=
//            AddUtils.themed("INPORT",new ItemStack(Material.END_STONE),AddUtils.Theme.CARGO1,"存入接口","较快的将物品存入奇点...");
//    public static final SlimefunItemStack OUTPORT=
//            AddUtils.themed("OUTPORT",new ItemStack(Material.END_STONE),AddUtils.Theme.CARGO1,"取出接口","较快的将物品取出奇点...");
    public static final SlimefunItemStack TESTUNIT1=
            AddUtils.themed("TESTUNIT1",new ItemStack(Material.GLASS),AddUtils.Theme.CARGO1,"测试存储单元","啥用都没");
    public static final SlimefunItemStack TESTUNIT2=
            AddUtils.themed("TESTUNIT2",new ItemStack(Material.GLASS),AddUtils.Theme.CARGO1,"测试存储单元2","啥用都没");
    public static final SlimefunItemStack TESTUNIT3=
            AddUtils.themed("TESTUNIT3",new ItemStack(Material.GLASS),AddUtils.Theme.CARGO1,"测试存储单元3","啥用都没");
    public static final SlimefunItemStack AUTO_SPECIAL=
            AddUtils.themed("AUTOCRAFT_SPECIAL",new ItemStack(Material.LOOM),AddUtils.Theme.MACHINE2,"测试特殊合成机","测试测试");
    public static final SlimefunItemStack AUTO_MULTIBLOCK=
            AddUtils.themed("AUTOCRAFT_MULTIBLOCK",new ItemStack(Material.BRICKS),AddUtils.Theme.MANUAL1,"测试快捷多方块","测试测试");
    public static final SlimefunItemStack WORKBENCH1=
            AddUtils.themed("WORKBENCH1",new ItemStack(Material.ENCHANTING_TABLE),AddUtils.Theme.BENCH1,"测试工作站","测试测试");
    //final
    public static final SlimefunItemStack FINAL_SEQUENTIAL=AddUtils.themed("FINAL_SEQUENTIAL",Material.STRIPPED_BAMBOO_BLOCK,AddUtils.Theme.MACHINE1,
            Language.get("Machines.FINAL_SEQUENTIAL.Name"),Language.getList("Machines.FINAL_SEQUENTIAL.Lore"));

    public static final SlimefunItemStack FINAL_STACKMACHINE=AddUtils.themed("FINAL_STACKMACHINE",Material.BLAST_FURNACE,AddUtils.Theme.MACHINE1,
            Language.get("Machines.FINAL_STACKMACHINE.Name"),Language.getList("Machines.FINAL_STACKMACHINE.Lore"));
    public static final SlimefunItemStack FINAL_STACKMGENERATOR=AddUtils.themed("FINAL_STACKMGENERATOR",Material.POLISHED_ANDESITE,AddUtils.Theme.MACHINE2,
            Language.get("Generators.FINAL_STACKMGENERATOR.Name"),Language.getList("Generators.FINAL_STACKMGENERATOR.Lore"));
    public static final SlimefunItemStack FINAL_STONE_MG=AddUtils.themed("FINAL_STONE_MG",Material.DEEPSLATE_TILES,AddUtils.Theme.MACHINE2,
            Language.get("Generators.FINAL_STONE_MG.Name"),Language.getList("Generators.FINAL_STONE_MG.Lore"));

    public static final SlimefunItemStack TESTPART=AddUtils.themed("TEST_MPART",Material.OBSIDIAN,AddUtils.Theme.MACHINE1,"测试多方块部件","测试测试");
    public static final SlimefunItemStack TESTCORE=AddUtils.themed("TEST_MCORE",Material.IRON_BLOCK,AddUtils.Theme.MACHINE1,"测试多方块核心","测试测试");
    public static final SlimefunItemStack TEST_SEQ=AddUtils.themed("TEST_SEQ",Material.LOOM,AddUtils.Theme.MACHINE1,
            Language.get("Items.TEST_SEQ.Name"),Language.getList("Items.TEST_SEQ.Lore"));

    //tmp占位符
    public static final SlimefunItemStack TMP1= new SlimefunItemStack("TMP1",Material.STONE,"&b占位符","&7暂未开发");
    public static final SlimefunItemStack RESOLVE_FAILED=AddUtils.themed("RESOLVE_FAILED",Material.STRUCTURE_VOID,AddUtils.Theme.NONE,
            Language.get("Items.RESOLVE_FAILED.Name"),Language.getList("Items.RESOLVE_FAILED.Lore"));
    public static final SlimefunItemStack SHELL=AddUtils.themed("SHELL",Material.BOOK,AddUtils.Theme.ITEM1,
            Language.get("Items.SHELL.Name"),Language.getList("Items.SHELL.Lore"));
    public static final HashSet<ItemStack> ADDGLOW=new HashSet<>(){{
        add(RESOLVE_FAILED);
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
        add(STAR_GOLD_INGOT);
        add(METAL_CORE);
        add(TECH_CORE);
        add(SMELERY_CORE);
        add(MASS_CORE);
        add(PORTAL_FRAME);
        add(LSINGULARITY);
        add(ATOM_INGOT);
        add(PAGOLD);
        add(BISILVER);
        add(STACKFRAME);
        add(MULTIBLOCK_MANUAL);
        add(ADVANCED_CHIP_MAKER);
        add(ADV_ADJACENT_CARGO);
        add(ADV_REMOTE_CARGO);
        add(ADV_LINE_CARGO);
        add(ENERGY_AMPLIFIER);
        add(MORE2);
        add(INF_MOBSIMULATION);
        add(ENTITY_FEAT);
        add(WITHERPROOF_REDSTONE);
        add(WITHERPROOF_REDS);
        add(BEDROCK_BREAKER);
        add(DIMENSIONAL_SINGULARITY);
        add(SPACETOWER_FRAME);
        add(ADJ_COLLECTOR_PLUS);
        add(ADJ_CHARGER_PLUS);
        add(LINE_CHARGER_PLUS);
        add(LINE_COLLECTOR_PLUS);
        add(ENERGY_PIPE_PLUS);
        add(CONFIGURE);
        add(TOOLS);
    }};
}
