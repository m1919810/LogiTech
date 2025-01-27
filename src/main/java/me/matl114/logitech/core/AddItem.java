package me.matl114.logitech.core;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Utils.UtilClass.SpecialItemClass.CustomFireworkStar;
import me.matl114.logitech.Utils.UtilClass.SpecialItemClass.CustomHead;
import me.matl114.logitech.Language;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.matlib.Utils.Algorithm.InitializeProvider;
import me.matl114.matlib.Utils.Algorithm.InitializeSafeProvider;
import me.matl114.matlib.core.EnvironmentManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static me.matl114.logitech.Utils.AddUtils.*;
import static me.matl114.logitech.Language.*;

import java.util.HashSet;
import java.util.List;

public class AddItem {

    public static void registerItemStack(){
        for (ItemStack it :ADDGLOW){
            addGlow(it);
        }
        AddItem.TRACE_ARROW.addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1);
        hideAllFlags(CARGO_CONFIG);
        hideAllFlags(ENTITY_FEAT);
        hideAllFlags(SPACE_CARD);
        hideAllFlags(REPLACE_SF_CARD);
        hideAllFlags(FU_BASE);
        hideAllFlags(DISPLAY_FU_USE_1);
        hideAllFlags(DISPLAY_REMOVE_FU_2);
        setUnbreakable(UNBREAKING_SHIELD,true);
    }

    //Groups
    public static final ItemStack ROOT=new CustomItemStack(Material.BUDDING_AMETHYST,
            get("Groups.ROOT.Name"),getList("Groups.ROOT.Lore"));
    public static final ItemStack INFO=themed(Material.PAPER,Theme.INFO1,
            get("Groups.INFO.Name"), getList("Groups.INFO.Lore"));
    public static final ItemStack MATERIAL=themed(Material.END_CRYSTAL,Theme.CATEGORY2,
            get("Groups.MATERIAL.Name"), getList("Groups.MATERIAL.Lore"));
    public static final ItemStack INFO1=themed(Material.PAPER,Theme.NONE,
            get("Groups.INFO1.Name"), getList("Groups.INFO1.Lore"));
    public static final ItemStack INFO2=themed(Material.PAPER,Theme.NONE,
            get("Groups.INFO2.Name"), getList("Groups.INFO2.Lore"));
    public static final ItemStack INFO3=themed(Material.PAPER,Theme.NONE,
            get("Groups.INFO3.Name"), getList("Groups.INFO3.Lore"));
    public static final ItemStack INFO4=themed(Material.PAPER,Theme.NONE,
            get("Groups.INFO4.Name"), getList("Groups.INFO4.Lore"));
    public static final ItemStack INFO5=themed(Material.PAPER,Theme.NONE,
            get("Groups.INFO5.Name"), getList("Groups.INFO5.Lore"));
    public static final ItemStack INFO6=themed(Material.PAPER,Theme.NONE,
            get("Groups.INFO6.Name"), getList("Groups.INFO6.Lore"));
    public static final ItemStack URL=themed(Material.BOOK,Theme.NONE,
            get("Groups.URL.Name"), getList("Groups.URL.Lore"));
    public static final ItemStack WIKI=themed(Material.KNOWLEDGE_BOOK,Theme.NONE,
            get("Groups.WIKI.Name"), getList("Groups.WIKI.Lore"));
    public static final ItemStack ALLMACHINE=themed(Material.BLAST_FURNACE,Theme.MENU1,
            get("Groups.ALLMACHINE.Name"), getList("Groups.ALLMACHINE.Lore"));
    public static final ItemStack ALLRECIPE=themed(Material.KNOWLEDGE_BOOK,Theme.MENU1,
            get("Groups.ALLRECIPE.Name"), getList("Groups.ALLRECIPE.Lore"));
    public static final ItemStack BASIC=themed(Material.FURNACE,Theme.CATEGORY2,
            get("Groups.BASIC.Name"),getList("Groups.BASIC.Lore")  );
    public static final ItemStack ALLBIGRECIPES =themed(Material.LODESTONE, Theme.CATEGORY2,
            get("Groups.ALLBIGRECIPES.Name"),getList("Groups.ALLBIGRECIPES.Lore"));
    public static final ItemStack CARGO=themed(Material.BAMBOO_CHEST_RAFT,Theme.CATEGORY2,
            get("Groups.CARGO.Name"),getList("Groups.CARGO.Lore"));
    public static final ItemStack SINGULARITY=themed(Material.NETHER_STAR,Theme.CATEGORY2,
            get("Groups.SINGULARITY.Name"),getList("Groups.SINGULARITY.Lore"));
    public static final ItemStack ADVANCED=themed(Material.BEACON,Theme.CATEGORY2,
            get("Groups.ADVANCED.Name"),getList("Groups.ADVANCED.Lore"));
    public static final ItemStack BEYOND=themed(Material.REPEATING_COMMAND_BLOCK,Theme.CATEGORY2,
            get("Groups.BEYOND.Name"),getList("Groups.BEYOND.Lore"));
    public static final ItemStack VANILLA=themed(Material.OBSERVER,Theme.CATEGORY2,
            get("Groups.VANILLA.Name"),getList("Groups.VANILLA.Lore"));
    public static final ItemStack MANUAL=themed(Material.CRAFTING_TABLE,Theme.CATEGORY2,
            get("Groups.MANUAL.Name"),getList("Groups.MANUAL.Lore") );
    public static final ItemStack SPECIAL=themed(Material.SCULK_CATALYST,Theme.CATEGORY2,
            get("Groups.SPECIAL.Name"),getList("Groups.SPECIAL.Lore"));
    public static final ItemStack TOOLS=themed(Material.NETHERITE_AXE, Theme.CATEGORY2,
            get("Groups.TOOLS.Name"),getList("Groups.TOOLS.Lore"));
    public static final ItemStack TOOLS_SUBGROUP_1=themed(Material.MUSIC_DISC_RELIC, Theme.CATEGORY2,
            get("Groups.TOOLS_SUBGROUP_1.Name"),getList("Groups.TOOLS_SUBGROUP_1.Lore"));
    public static final ItemStack TOOLS_SUBGROUP_2=themed(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Theme.CATEGORY2,
            get("Groups.TOOLS_SUBGROUP_2.Name"),getList("Groups.TOOLS_SUBGROUP_2.Lore"));
    public static final ItemStack TOOLS_RECIPES=themed("TOOLS_RECIPES",Material.CRAFTING_TABLE, Theme.CATEGORY2,
            get("Groups.TOOLS_RECIPES.Name"),getList("Groups.TOOLS_RECIPES.Lore"));
    //public static final ItemStack TEMPLATE=themed()
    public static final ItemStack TOBECONTINUE=themed(Material.STRUCTURE_VOID,Theme.CATEGORY2,
            get("Groups.TOBECONTINUE.Name"),getList("Groups.TOBECONTINUE.Lore"));
    public static final ItemStack SPACE =themed(Material.TOTEM_OF_UNDYING, Theme.CATEGORY2,
            get("Groups.SPACE.Name"),getList("Groups.SPACE.Lore"));
    public static final ItemStack GENERATORS=themed(Material.LAVA_BUCKET, Theme.CATEGORY2,
            get("Groups.GENERATORS.Name"),getList("Groups.GENERATORS.Lore"));
    public static final ItemStack ENERGY=themed(Material.LIGHTNING_ROD, Theme.CATEGORY2,
            get("Groups.ENERGY.Name"),getList("Groups.ENERGY.Lore"));
    public static final ItemStack FUNCTIONAL=themed(Material.STRUCTURE_VOID, Theme.CATEGORY2,
            get("Groups.FUNCTIONAL.Name"),getList("Groups.FUNCTIONAL.Lore"));
    public static final ItemStack UPDATELOG=themed(Material.WRITABLE_BOOK,Theme.NONE,
            get("Groups.UPDATELOG.Name"), getList("Groups.UPDATELOG.Lore"));
    public static final ItemStack MORE2=themed("MORE2",Material.WRITABLE_BOOK, Theme.NONE,
            get("Groups.MORE2.Name"),getList("Groups.MORE2.Lore"));
    //feat
    public static final ItemStack FEAT1=themed(Material.BOOK, Theme.NONE,
            get("Groups.FEAT1.Name"),getList("Groups.FEAT1.Lore"));
    public static final ItemStack FEAT2=themed(Material.BOOK, Theme.NONE,
            get("Groups.FEAT2.Name"),getList("Groups.FEAT2.Lore"));
    public static final ItemStack FEAT3=themed(Material.BOOK, Theme.NONE,
            get("Groups.FEAT3.Name"),getList("Groups.FEAT3.Lore"));
    public static final ItemStack FEAT4=themed(Material.BOOK, Theme.NONE,
            get("Groups.FEAT4.Name"),getList("Groups.FEAT4.Lore"));
    public static final ItemStack FEAT5=themed(Material.BOOK, Theme.NONE,
            get("Groups.FEAT5.Name"),getList("Groups.FEAT5.Lore"));
    public static final ItemStack FEAT6=themed(Material.BOOK, Theme.NONE,
            get("Groups.FEAT6.Name"),getList("Groups.FEAT6.Lore"));
    public static final ItemStack FEAT7=themed(Material.BOOK, Theme.NONE,
            get("Groups.FEAT7.Name"),getList("Groups.FEAT7.Lore"));
    public static final ItemStack FEAT8=themed(Material.BOOK, Theme.NONE,
            get("Groups.FEAT8.Name"),getList("Groups.FEAT8.Lore"));
    public static final ItemStack FEAT9=themed(Material.BOOK, Theme.NONE,
            get("Groups.FEAT9.Name"),getList("Groups.FEAT9.Lore"));


    //items
    public static final SlimefunItemStack ENTITY_FEAT=themed("ENTITY_FEAT",Material.SPAWNER,Theme.ITEM1,
            get("Items.ENTITY_FEAT.Name"),getList("Items.ENTITY_FEAT.Lore"));
    public static final SlimefunItemStack BUG= themed("BUG", Material.BONE_MEAL, Theme.ITEM1,
            get("Items.BUG.Name"),getList("Items.BUG.Lore"));
    public static final SlimefunItemStack MATL114 = themed("MATL114", CustomHead.MATL114.getItem(), Theme.ITEM1,
            get("Items.MATL114.Name"),getList("Items.MATL114.Lore"));
    public static final SlimefunItemStack CHIP_INGOT=themed("CHIP_INGOT",Material.BAKED_POTATO,Theme.ITEM1,
            get("Items.CHIP_INGOT.Name"),getList("Items.CHIP_INGOT.Lore"));
    public static final SlimefunItemStack TITANIUM_INGOT=themed("TITANIUM_INGOT",Material.IRON_INGOT,Theme.ITEM1,
            get("Items.TITANIUM_INGOT.Name"),getList("Items.TITANIUM_INGOT.Lore"));
    public static final SlimefunItemStack TUNGSTEN_INGOT=themed("TUNGSTEN_INGOT",Material.NETHERITE_INGOT,Theme.ITEM1,
            get("Items.TUNGSTEN_INGOT.Name"),getList("Items.TUNGSTEN_INGOT.Lore"));
    public static final SlimefunItemStack LOGIC= themed("LOGIC",Material.STRING,Theme.ITEM1,
            get("Items.LOGIC.Name"),getList("Items.LOGIC.Lore"));
    public static final SlimefunItemStack TRUE_ =themed("TRUE_",Material.MUSIC_DISC_5,Theme.ITEM1,
            get("Items.TRUE_.Name"),getList("Items.TRUE_.Lore"));
    public static final SlimefunItemStack FALSE_ =themed("FALSE_",Material.MUSIC_DISC_5,Theme.ITEM1,
            get("Items.FALSE_.Name"),getList("Items.FALSE_.Lore"));
    public static final SlimefunItemStack LOGIGATE=themed("LOGIGATE",Material.COMPARATOR,Theme.ITEM1,
            get("Items.LOGIGATE.Name"),getList("Items.LOGIGATE.Lore"));
        //generated items
    public static final SlimefunItemStack EXISTE=themed("EXISTE",Material.SLIME_BALL,Theme.ITEM1,
            get("Items.EXISTE.Name"),getList("Items.EXISTE.Lore"));
    public static final SlimefunItemStack UNIQUE=themed("UNIQUE",Material.MAGMA_CREAM,Theme.ITEM1,
            get("Items.UNIQUE.Name"),getList("Items.UNIQUE.Lore"));
    public static final SlimefunItemStack PARADOX=themed("PARADOX",Material.NAUTILUS_SHELL,Theme.ITEM1,
            get("Items.PARADOX.Name"),getList("Items.PARADOX.Lore"));
    public static final SlimefunItemStack NOLOGIC=themed("NOLOGIC",Material.STRING,Theme.ITEM1,
            get("Items.NOLOGIC.Name"),getList("Items.NOLOGIC.Lore"));
    public static final SlimefunItemStack LENGINE=themed("LENGINE",Material.MAGENTA_GLAZED_TERRACOTTA,Theme.ITEM1,
            get("Items.LENGINE.Name"),getList("Items.LENGINE.Lore"));
    public static final SlimefunItemStack LFIELD=themed("LFIELD",Material.END_CRYSTAL,Theme.ITEM1,
            get("Items.LFIELD.Name"),getList("Items.LFIELD.Lore"));
    public static final SlimefunItemStack LSCHEDULER=themed("LSCHEDULER",Material.RECOVERY_COMPASS,Theme.ITEM1,
            get("Items.LSCHEDULER.Name"),getList("Items.LSCHEDULER.Lore"));
    public static final SlimefunItemStack LCRAFT=themed("LCRAFT",Material.CONDUIT,Theme.ITEM1,
            get("Items.LCRAFT.Name"),getList("Items.LCRAFT.Lore"));
    public static final SlimefunItemStack LDIGITIZER=themed("LDIGITIZER",Material.TARGET,Theme.ITEM1,
            get("Items.LDIGITIZER.Name"),getList("Items.LDIGITIZER.Lore"));
    public static final SlimefunItemStack LBOOLIZER=themed("LBOOLIZER",Material.LEVER,Theme.ITEM1,
            get("Items.LBOOLIZER.Name"),getList("Items.LBOOLIZER.Lore"));
    public static final SlimefunItemStack LIOPORT=themed("LIOPORT",Material.CALIBRATED_SCULK_SENSOR,Theme.ITEM1,
            get("Items.LIOPORT.Name"),getList("Items.LIOPORT.Lore"));
    public static final SlimefunItemStack PALLADIUM_INGOT=themed("PALLADIUM_INGOT",Material.COPPER_INGOT,Theme.ITEM1,
            get("Items.PALLADIUM_INGOT.Name"),getList("Items.PALLADIUM_INGOT.Lore"));
    public static final SlimefunItemStack PLATINUM_INGOT=themed("PLATINUM_INGOT",Material.GOLD_INGOT,Theme.ITEM1,
            get("Items.PLATINUM_INGOT.Name"),getList("Items.PLATINUM_INGOT.Lore"));
    public static final SlimefunItemStack MOLYBDENUM=themed("MOLYBDENUM",Material.GUNPOWDER,Theme.ITEM1,
            get("Items.MOLYBDENUM.Name"),getList("Items.MOLYBDENUM.Lore"));
    public static final SlimefunItemStack CERIUM=themed("CERIUM",Material.GUNPOWDER,Theme.ITEM1,
            get("Items.CERIUM.Name"),getList("Items.CERIUM.Lore"));
    public static final SlimefunItemStack CADMIUM_INGOT=themed("CADMIUM_INGOT",Material.NETHERITE_INGOT,Theme.ITEM1,
            get("Items.CADMIUM_INGOT.Name"),getList("Items.CADMIUM_INGOT.Lore"));
    public static final SlimefunItemStack MENDELEVIUM=themed("MENDELEVIUM",Material.GLOWSTONE_DUST,Theme.ITEM1,
            get("Items.MENDELEVIUM.Name"),getList("Items.MENDELEVIUM.Lore"));
    public static final SlimefunItemStack DYSPROSIUM=themed("DYSPROSIUM",Material.REDSTONE,Theme.ITEM1,
            get("Items.DYSPROSIUM.Name"),getList("Items.DYSPROSIUM.Lore"));
    public static final SlimefunItemStack BISMUTH_INGOT=themed("BISMUTH_INGOT",Material.IRON_INGOT,Theme.ITEM1,
            get("Items.BISMUTH_INGOT.Name"),getList("Items.BISMUTH_INGOT.Lore"));
    public static final SlimefunItemStack ANTIMONY_INGOT=themed("ANTIMONY_INGOT",Material.IRON_INGOT,Theme.ITEM1,
            get("Items.ANTIMONY_INGOT.Name"),getList("Items.ANTIMONY_INGOT.Lore"));
    public static final SlimefunItemStack BORON=themed("BORON",Material.CLAY_BALL,Theme.ITEM1,
            get("Items.BORON.Name"),getList("Items.BORON.Lore"));
    public static final SlimefunItemStack THALLIUM=themed("THALLIUM",Material.BRICK,Theme.ITEM1,
            get("Items.THALLIUM.Name"),getList("Items.THALLIUM.Lore"));
    public static final SlimefunItemStack HYDRAGYRUM=themed("HYDRAGYRUM",Material.PRISMARINE_CRYSTALS,Theme.ITEM1,
            get("Items.HYDRAGYRUM.Name"),getList("Items.HYDRAGYRUM.Lore"));
    public static final SlimefunItemStack HGTLPBBI=themed("HGTLPBBI",CustomHead.SUPPORTER2.getItem(), Theme.ITEM1,
            get("Items.HGTLPBBI.Name"),getList("Items.HGTLPBBI.Lore"));
    public static final SlimefunItemStack DIMENSIONAL_SHARD=themed("DIMENSIONAL_SHARD",Material.PRISMARINE_SHARD,Theme.ITEM1,
            get("Items.DIMENSIONAL_SHARD.Name"),getList("Items.DIMENSIONAL_SHARD.Lore"));
    public static final SlimefunItemStack STAR_GOLD=themed("STAR_GOLD",Material.NETHER_STAR,Theme.ITEM1,
            get("Items.STAR_GOLD.Name"),getList("Items.STAR_GOLD.Lore"));
    public static final SlimefunItemStack VIRTUAL_SPACE=themed("VIRTUAL_SPACE",CustomHead.VSPACE.getItem(), Theme.ITEM1,
            get("Items.VIRTUAL_SPACE.Name"),getList("Items.VIRTUAL_SPACE.Lore"));
    public static final SlimefunItemStack WORLD_FEAT=themed("WORLD_FEAT",Material.GRASS_BLOCK,Theme.ITEM1,
            get("Items.WORLD_FEAT.Name"),getList("Items.WORLD_FEAT.Lore"));
    public static final SlimefunItemStack NETHER_FEAT=themed("NETHER_FEAT",Material.NETHERITE_SCRAP,Theme.ITEM1,
            get("Items.NETHER_FEAT.Name"),getList("Items.NETHER_FEAT.Lore"));
    public static final SlimefunItemStack END_FEAT=themed("END_FEAT",Material.CHORUS_PLANT,Theme.ITEM1,
            get("Items.END_FEAT.Name"),getList("Items.END_FEAT.Lore"));
    public static final SlimefunItemStack STACKFRAME=themed("STACKFRAME",Material.BEDROCK,Theme.ITEM1,
            get("Items.STACKFRAME.Name"),getList("Items.STACKFRAME.Lore"));

    public static final SlimefunItemStack STAR_GOLD_INGOT=themed("STAR_GOLD_INGOT",Material.GOLD_INGOT,Theme.ITEM1,
            get("Items.STAR_GOLD_INGOT.Name"),getList("Items.STAR_GOLD_INGOT.Lore"));
    public static final SlimefunItemStack ABSTRACT_INGOT=themed("ABSTRACT_INGOT",Material.IRON_INGOT,Theme.ITEM1,
            get("Items.ABSTRACT_INGOT.Name"),getList("Items.ABSTRACT_INGOT.Lore"));
    public static final SlimefunItemStack PDCECDMD=themed("PDCECDMD",CustomHead.BUSHIGEMEN.getItem(), Theme.ITEM1,
            get("Items.PDCECDMD.Name"),getList("Items.PDCECDMD.Lore"));
    public static final SlimefunItemStack REINFORCED_CHIP_INGOT=themed("REINFORCED_CHIP_INGOT",Material.POISONOUS_POTATO,Theme.ITEM1,
            get("Items.REINFORCED_CHIP_INGOT.Name"),getList("Items.REINFORCED_CHIP_INGOT.Lore"));
    public static final SlimefunItemStack ATOM_INGOT=themed("ATOM_INGOT",Material.ECHO_SHARD,Theme.ITEM1,
            get("Items.ATOM_INGOT.Name"),getList("Items.ATOM_INGOT.Lore"));

    public static final SlimefunItemStack LMOTOR=themed("LMOTOR",CustomHead.MOTOR.getItem(),Theme.ITEM1,
            get("Items.LMOTOR.Name"),getList("Items.LMOTOR.Lore"));
    public static final SlimefunItemStack LPLATE=themed("LPLATE",Material.PAPER,Theme.ITEM1,
            get("Items.LPLATE.Name"),getList("Items.LPLATE.Lore"));
    public static final SlimefunItemStack METAL_CORE=themed("METAL_CORE",Material.NETHERITE_BLOCK,Theme.ITEM1,
            get("Items.METAL_CORE.Name"),getList("Items.METAL_CORE.Lore"));
    public static final SlimefunItemStack SMELERY_CORE=themed("SMELERY_CORE",Material.IRON_BLOCK,Theme.ITEM1,
            get("Items.SMELERY_CORE.Name"),getList("Items.SMELERY_CORE.Lore"));
    public static final SlimefunItemStack MASS_CORE=themed("MASS_CORE",Material.COAL_BLOCK,Theme.ITEM1,
            get("Items.MASS_CORE.Name"),getList("Items.MASS_CORE.Lore"));
    public static final SlimefunItemStack TECH_CORE=themed("TECH_CORE",Material.BEACON,Theme.ITEM1,
            get("Items.TECH_CORE.Name"),getList("Items.TECH_CORE.Lore"));
    public static final SlimefunItemStack SPACE_PLATE=themed("SPACE_PLATE",Material.PAPER,Theme.ITEM1,
            get("Items.SPACE_PLATE.Name"),getList("Items.SPACE_PLATE.Lore"));
    public static final SlimefunItemStack LOGIC_CORE=themed("LOGIC_CORE",Material.NETHER_STAR,Theme.ITEM1,
            get("Items.LOGIC_CORE.Name"),getList("Items.LOGIC_CORE.Lore"));
    public static final SlimefunItemStack FINAL_FRAME=themed("FINAL_FRAME",Material.BUDDING_AMETHYST,Theme.MULTIBLOCK1,
            get("Items.FINAL_FRAME.Name"),getList("Items.FINAL_FRAME.Lore"));
    public static final SlimefunItemStack REDSTONE_ENGINE=themed("REDSTONE_ENGINE",Material.SLIME_BLOCK,Theme.ITEM1,
            get("Items.REDSTONE_ENGINE.Name"),getList("Items.REDSTONE_ENGINE.Lore"));
    public static final SlimefunItemStack HYPER_LINK=themed("HYPER_LINK",Material.NETHER_STAR,Theme.ITEM1,
            get("Items.HYPER_LINK.Name"),getList("Items.HYPER_LINK.Lore"));

    public static final SlimefunItemStack SAMPLE_HEAD=themed("SAMPLE_HEAD",Material.PLAYER_HEAD,Theme.ITEM1,
            get("Items.SAMPLE_HEAD.Name"),getList("Items.SAMPLE_HEAD.Lore"));
    public static final SlimefunItemStack CHIP=themed("CHIP",Material.NAME_TAG,Theme.ITEM1,
            get("Items.CHIP.Name"),getList("Items.CHIP.Lore"));
    public static final SlimefunItemStack CHIP_CORE=themed("CHIP_CORE",CustomHead.CORE.getItem(), Theme.ITEM1,
            get("Items.CHIP_CORE.Name"),getList("Items.CHIP_CORE.Lore"));
    public static final SlimefunItemStack LSINGULARITY=themed("LSINGULARITY",Material.FIREWORK_STAR,Theme.ITEM1,
            get("Items.LSINGULARITY.Name"),getList("Items.LSINGULARITY.Lore"));
    public static final SlimefunItemStack RADIATION_CLEAR=themed("RADIATION_CLEAR",Material.GLASS_BOTTLE,Theme.ITEM1,
            get("Items.RADIATION_CLEAR.Name"),getList("Items.RADIATION_CLEAR.Lore"));
    public static final SlimefunItemStack ANTIMASS_CLEAR=themed("ANTIMASS_CLEAR",Material.GLASS_BOTTLE,Theme.ITEM1,
            get("Items.ANTIMASS_CLEAR.Name"),getList("Items.ANTIMASS_CLEAR.Lore"));
    public static final SlimefunItemStack BISILVER=themed("BISILVER",Material.IRON_INGOT,Theme.ITEM1,
            get("Items.BISILVER.Name"),getList("Items.BISILVER.Lore"));
    public static final SlimefunItemStack PAGOLD=themed("PAGOLD",Material.GOLD_INGOT,Theme.ITEM1,
            get("Items.PAGOLD.Name"),getList("Items.PAGOLD.Lore"));
    public static final SlimefunItemStack LASER=themed("LASER",CustomHead.LASER.getItem(), Theme.ITEM1,
            get("Items.LASER.Name"),getList("Items.LASER.Lore"));
    public static final SlimefunItemStack ANTIMASS=themed("ANTIMASS",Material.SCULK,Theme.ITEM1,
            get("Items.ANTIMASS.Name"),getList("Items.ANTIMASS.Lore"));
    public static final SlimefunItemStack VIRTUALWORLD=themed("VIRTUALWORLD",CustomHead.END_BLOCK.getItem(), Theme.ITEM1,
            get("Items.VIRTUALWORLD.Name"),getList("Items.VIRTUALWORLD.Lore"));
    public static final SlimefunItemStack SAMPLE_SPAWNER=themed("SAMPLE_SPAWNER",Material.SPAWNER,Theme.ITEM1,
            get("Items.SAMPLE_SPAWNER.Name"),getList("Items.SAMPLE_SPAWNER.Lore"));
    public static final SlimefunItemStack HOLOGRAM_REMOVER=themed("HOLOGRAM_REMOVER",Material.LIGHT,Theme.ITEM1,
            get("Items.HOLOGRAM_REMOVER.Name"),getList("Items.HOLOGRAM_REMOVER.Lore"));
    public static final SlimefunItemStack WITHERPROOF_REDSTONE=themed("WITHERPROOF_REDSTONE",Material.REDSTONE_BLOCK,Theme.ITEM1,
            get("Items.WITHERPROOF_REDSTONE.Name"),getList("Items.WITHERPROOF_REDSTONE.Lore"));
    public static final SlimefunItemStack WITHERPROOF_REDS=themed("WITHERPROOF_REDS",Material.REDSTONE,Theme.ITEM1,
            get("Items.WITHERPROOF_REDS.Name"),getList("Items.WITHERPROOF_REDS.Lore"));
    public static final SlimefunItemStack BEDROCK_BREAKER=themed("BEDROCK_BREAKER",Material.PISTON,Theme.ITEM1,
            get("Items.BEDROCK_BREAKER.Name"),getList("Items.BEDROCK_BREAKER.Lore"));
    public static final SlimefunItemStack LASER_GUN=themed("LASER_GUN",CustomHead.LASER_GUN.getItem(), Theme.TOOL,
            get("Items.LASER_GUN.Name"),getList("Items.LASER_GUN.Lore"));
    public static final SlimefunItemStack SUPERSPONGE=themed("SUPERSPONGE",Material.SPONGE,Theme.ITEM1,
            get("Items.SUPERSPONGE.Name"),getList("Items.SUPERSPONGE.Lore"));
    public static final SlimefunItemStack SUPERSPONGE_USED=themed("SUPERSPONGE_USED",Material.WET_SPONGE,Theme.ITEM1,
            get("Items.SUPERSPONGE_USED.Name"),getList("Items.SUPERSPONGE_USED.Lore"));
    public static final SlimefunItemStack TRACE_ARROW=themed("TRACE_ARROW",Material.CHERRY_SAPLING,Theme.TOOL,
            get("Items.TRACE_ARROW.Name"),getList("Items.TRACE_ARROW.Lore"));
    public static final SlimefunItemStack DIMENSIONAL_SINGULARITY=themed("DIMENSIONAL_SINGULARITY",Material.AMETHYST_SHARD,Theme.ITEM1,
            get("Items.DIMENSIONAL_SINGULARITY.Name"),getList("Items.DIMENSIONAL_SINGULARITY.Lore"));
    public static final SlimefunItemStack RTP_RUNE=themed("RTP_RUNE", CustomFireworkStar.RTP_RUNE.getItem(),Theme.ITEM1,
            get("Items.RTP_RUNE.Name"),getList("Items.RTP_RUNE.Lore"));
    public static final SlimefunItemStack SPACE_CARD=themed("SPACE_CARD",Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("Items.SPACE_CARD.Name"),getList("Items.SPACE_CARD.Lore"));

    public static final SlimefunItemStack UNBREAKING_SHIELD=themed("UNBREAKING_SHIELD",Material.SHIELD, Theme.TOOL,
            get("Items.UNBREAKING_SHIELD.Name"),getList("Items.UNBREAKING_SHIELD.Lore"));
    public static final ItemStack MACE_ITEM=new InitializeSafeProvider<>(ItemStack.class,()->{
        Material material= EnvironmentManager.getManager().getVersioned().getMaterial("MACE");
        return material==null?null: new ItemStack(material);
    }).v ();
    public static final ItemStack SUPER_COBALT_PICKAXE = new InitializeProvider<>(()->{
        ItemStack item = getCopy( resolveItem("COBALT_PICKAXE") );
        item.setType(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(EnvironmentManager.getManager().getVersioned().getEnchantment("efficiency"),10,true );
        meta.setLore(List.of(DEFAULT_COLOR+"超级钴镐"));
        item.setItemMeta(meta);
        return item;
    }).v();
    public static final SlimefunItemStack FAKE_UI=themed("FAKE_UI",Material.LIGHT_GRAY_STAINED_GLASS_PANE,Theme.ITEM1,
            get("Items.FAKE_UI.Name"),getList("Items.FAKE_UI.Lore"));
    public static final SlimefunItemStack ANTIGRAVITY=themed("ANTIGRAVITY",Material.NETHERITE_INGOT,Theme.ITEM1,
            get("Items.ANTIGRAVITY.Name"),getList("Items.ANTIGRAVITY.Lore"));
    public static final SlimefunItemStack CONFIGURE=themed("CONFIGURE",Material.BLAZE_ROD,Theme.CARGO1,
            get("Items.CONFIGURE.Name"),getList("Items.CONFIGURE.Lore"));
    public static final SlimefunItemStack AMPLIFY_BASE=themed("AMPLIFY_BASE",Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,Theme.ITEM1,
            get("Items.AMPLIFY_BASE.Name"),getList("Items.AMPLIFY_BASE.Lore"));
    public static final SlimefunItemStack SWAMP_SPEED=themed("SWAMP_SPEED",Material.MUSIC_DISC_13,Theme.ITEM1,
            get("Items.SWAMP_SPEED.Name"),getList("Items.SWAMP_SPEED.Lore"));
    public static final SlimefunItemStack SWAMP_RANGE=themed("SWAMP_RANGE",Material.MUSIC_DISC_CHIRP,Theme.ITEM1,
            get("Items.SWAMP_RANGE.Name"),getList("Items.SWAMP_RANGE.Lore"));
    public static final SlimefunItemStack MULTIBLOCKBUILDER=themed("MULTIBLOCKBUILDER",Material.BOOK,Theme.ITEM1,
            get("Items.MULTIBLOCKBUILDER.Name"),getList("Items.MULTIBLOCKBUILDER.Lore"));
    public static final SlimefunItemStack DISPLAY_FU_USE=themed("DISPLAY_FU_USE",Material.SMITHING_TABLE,Theme.TOOL,
            get("Items.DISPLAY_FU_USE.Name"),getList("Items.DISPLAY_FU_USE.Lore"));
    public static final ItemStack DISPLAY_FU_USE_1=themed(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Theme.ITEM1,
            get("Items.DISPLAY_FU_USE_1.Name"),getList("Items.DISPLAY_FU_USE_1.Lore"));
    public static final ItemStack DISPLAY_FU_USE_2=themed(Material.NETHERITE_SWORD,Theme.ITEM1,
            get("Items.DISPLAY_FU_USE_2.Name"),getList("Items.DISPLAY_FU_USE_2.Lore"));
    public static final ItemStack DISPLAY_FU_USE_3=themed(Material.AMETHYST_SHARD,Theme.ITEM1,
            get("Items.DISPLAY_FU_USE_3.Name"),getList("Items.DISPLAY_FU_USE_3.Lore"));
    public static final SlimefunItemStack DISPLAY_REMOVE_FU=themed("DISPLAY_REMOVE_FU",Material.GRINDSTONE,Theme.TOOL,
            get("Items.DISPLAY_REMOVE_FU.Name"),getList("Items.DISPLAY_REMOVE_FU.Lore"));
    public static final ItemStack DISPLAY_REMOVE_FU_1=themed(Material.NETHERITE_SWORD,Theme.ITEM1,
            get("Items.DISPLAY_REMOVE_FU_1.Name"),getList("Items.DISPLAY_REMOVE_FU_1.Lore"));
    public static final ItemStack DISPLAY_REMOVE_FU_2=themed(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,Theme.ITEM1,
            get("Items.DISPLAY_REMOVE_FU_2.Name"),getList("Items.DISPLAY_REMOVE_FU_2.Lore"));
    public static final SlimefunItemStack FU_BASE=themed("FU_BASE",Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("Items.FU_BASE.Name"),getList("Items.FU_BASE.Lore"));
    public static final SlimefunItemStack DEMO_FU=themed("DEMO_FU",Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,Theme.ITEM1,
            get("Items.DEMO_FU.Name"),getList("Items.DEMO_FU.Lore"));
    //nachines
    public static final SlimefunItemStack HEAD_ANALYZER=themed("HEAD_ANALYZER",Material.SOUL_CAMPFIRE, Theme.MACHINE1,
            get("Machines.HEAD_ANALYZER.Name"),getList("Machines.HEAD_ANALYZER.Lore"));
    public static final SlimefunItemStack RECIPE_LOGGER=themed("RECIPE_LOGGER",Material.FLETCHING_TABLE, Theme.MACHINE1,
            get("Machines.RECIPE_LOGGER.Name"),getList("Machines.RECIPE_LOGGER.Lore"));
    public static final SlimefunItemStack BOOL_GENERATOR=themed("BOOL_GENERATOR",Material.REDSTONE_TORCH,Theme.MACHINE1,
            get("Machines.BOOL_GENERATOR.Name"),getList("Machines.BOOL_GENERATOR.Lore"));
    public static final SlimefunItemStack LOGIC_REACTOR=themed("LOGIC_REACTOR",CustomHead.LOGIC_REACTOR.getItem(),Theme.MACHINE1,
            get("Machines.LOGIC_REACTOR.Name"),getList("Machines.LOGIC_REACTOR.Lore"));
    public static final SlimefunItemStack BUG_CRAFTER=themed("BUG_CRAFTER",CustomHead.BUG_CRATFER.getItem(),Theme.MACHINE1,
            get("Machines.BUG_CRAFTER.Name"),getList("Machines.BUG_CRAFTER.Lore"));
    public static final SlimefunItemStack ENDFRAME_MACHINE=themed("ENDFRAME_MACHINE",Material.END_PORTAL_FRAME,Theme.MACHINE1,
            get("Machines.ENDFRAME_MACHINE.Name"),getList("Machines.ENDFRAME_MACHINE.Lore"));
    public static final SlimefunItemStack LVOID_GENERATOR=themed("LVOID_GENERATOR",Material.SOUL_LANTERN,Theme.MACHINE1,
            get("Machines.LVOID_GENERATOR.Name"),getList("Machines.LVOID_GENERATOR.Lore"));
    public static final SlimefunItemStack SPECIAL_CRAFTER=themed("SPECIAL_CRAFTER",Material.LOOM,Theme.MACHINE1,
            get("Machines.SPECIAL_CRAFTER.Name"),getList("Machines.SPECIAL_CRAFTER.Lore"));
    public static final SlimefunItemStack STAR_SMELTERY=themed("STAR_SMELTERY",Material.BLAST_FURNACE,Theme.MACHINE1,
            get("Machines.STAR_SMELTERY.Name"),getList("Machines.STAR_SMELTERY.Lore"));

    public static final SlimefunItemStack INFINITY_AUTOCRAFT=themed("INFINITY_AUTOCRAFT",Material.CRYING_OBSIDIAN,Theme.MACHINE1,
            get("Machines.INFINITY_AUTOCRAFT.Name"),getList("Machines.INFINITY_AUTOCRAFT.Lore"));
    public static final SlimefunItemStack CHIP_MAKER=themed("CHIP_MAKER",Material.CHISELED_BOOKSHELF,Theme.MACHINE1,
            get("Machines.CHIP_MAKER.Name"),getList("Machines.CHIP_MAKER.Lore"));
    public static final SlimefunItemStack CHIP_CONSUMER=themed("CHIP_CONSUMER",Material.TORCH,Theme.MACHINE1,
            get("Machines.CHIP_CONSUMER.Name"),getList("Machines.CHIP_CONSUMER.Lore"));
    public static final SlimefunItemStack CHIP_BICONSUMER=themed("CHIP_BICONSUMER",Material.LANTERN,Theme.MACHINE1,
            get("Machines.CHIP_BICONSUMER.Name"),getList("Machines.CHIP_BICONSUMER.Lore"));
    public static final SlimefunItemStack SEQ_CONSTRUCTOR=themed("SEQ_CONSTRUCTOR",Material.BAMBOO_MOSAIC,Theme.MACHINE1,
            get("Machines.SEQ_CONSTRUCTOR.Name"),getList("Machines.SEQ_CONSTRUCTOR.Lore"));
    public static final SlimefunItemStack STACKMACHINE=themed("STACKMACHINE",Material.FURNACE,Theme.MACHINE1,
            get("Machines.STACKMACHINE.Name"),getList("Machines.STACKMACHINE.Lore"));
    public static final SlimefunItemStack ENERGY_TRASH=themed("ENERGY_TRASH", SlimefunItems.PORTABLE_DUSTBIN.getItem().getItem().clone()
            ,Theme.MACHINE1, get("Machines.ENERGY_TRASH.Name"),getList("Machines.ENERGY_TRASH.Lore"));
    public static final SlimefunItemStack OPPO_GEN=themed("OPPO_GEN",CustomHead.HOT_MACHINE.getItem(), Theme.MACHINE1,
            get("Machines.OPPO_GEN.Name"),getList("Machines.OPPO_GEN.Lore"));
    public static final SlimefunItemStack ARC_REACTOR=themed("ARC_REACTOR",CustomHead.REACTOR.getItem(),Theme.MACHINE1,
            get("Machines.ARC_REACTOR.Name"),getList("Machines.ARC_REACTOR.Lore"));
    public static final SlimefunItemStack ENERGY_AMPLIFIER=themed("ENERGY_AMPLIFIER",Material.NETHERITE_BLOCK,Theme.MACHINE1,
            get("Machines.ENERGY_AMPLIFIER.Name"),getList("Machines.ENERGY_AMPLIFIER.Lore"));
    public static final SlimefunItemStack ADVANCED_CHIP_MAKER=themed("ADVANCED_CHIP_MAKER",Material.CHISELED_BOOKSHELF,Theme.MACHINE1,
            get("Machines.ADVANCED_CHIP_MAKER.Name"),getList("Machines.ADVANCED_CHIP_MAKER.Lore"));
    public static final SlimefunItemStack CHIP_REACTOR=themed("CHIP_REACTOR",Material.JUKEBOX,Theme.MACHINE1,
            get("Machines.CHIP_REACTOR.Name"),getList("Machines.CHIP_REACTOR.Lore"));
    public static final SlimefunItemStack DUST_EXTRACTOR=themed("DUST_EXTRACTOR",Material.CHISELED_STONE_BRICKS,Theme.MACHINE1,
            get("Machines.DUST_EXTRACTOR.Name"),getList("Machines.DUST_EXTRACTOR.Lore"));
    public static final SlimefunItemStack FURNACE_FACTORY=themed("FURNACE_FACTORY",Material.FURNACE,Theme.MACHINE1,
            get("Machines.FURNACE_FACTORY.Name"),getList("Machines.FURNACE_FACTORY.Lore"));
    public static final SlimefunItemStack INGOT_FACTORY=themed("INGOT_FACTORY",Material.RED_GLAZED_TERRACOTTA,Theme.MACHINE1,
            get("Machines.INGOT_FACTORY.Name"),getList("Machines.INGOT_FACTORY.Lore"));
    public static final SlimefunItemStack FINAL_LASER=themed("FINAL_LASER",Material.DROPPER,Theme.MACHINE1,
            get("Machines.FINAL_LASER.Name"),getList("Machines.FINAL_LASER.Lore"));
    public static final SlimefunItemStack FINAL_CONVERTOR=themed("FINAL_CONVERTOR",Material.WARPED_HYPHAE,Theme.MACHINE1,
            get("Machines.FINAL_CONVERTOR.Name"),getList("Machines.FINAL_CONVERTOR.Lore"));
    public static final SlimefunItemStack PRESSOR_FACTORY=themed("PRESSOR_FACTORY",Material.PISTON,Theme.MACHINE1,
            get("Machines.PRESSOR_FACTORY.Name"),getList("Machines.PRESSOR_FACTORY.Lore"));
    public static final SlimefunItemStack CRAFTER=themed("CRAFTER",Material.CRAFTING_TABLE,Theme.MACHINE1,
            get("Machines.CRAFTER.Name"),getList("Machines.CRAFTER.Lore"));
    public static final SlimefunItemStack EASYSTACKMACHINE=themed("EASYSTACKMACHINE",Material.FURNACE,Theme.MACHINE1,
            get("Machines.EASYSTACKMACHINE.Name"),getList("Machines.EASYSTACKMACHINE.Lore"));
    public static final SlimefunItemStack CONVERTOR=themed("CONVERTOR",Material.SEA_LANTERN,Theme.MACHINE1,
            get("Machines.CONVERTOR.Name"),getList("Machines.CONVERTOR.Lore"));
    public static final SlimefunItemStack VIRTUAL_KILLER=themed("VIRTUAL_KILLER",Material.STONECUTTER,Theme.MACHINE1,
            get("Machines.VIRTUAL_KILLER.Name"),getList("Machines.VIRTUAL_KILLER.Lore"));
    public static final SlimefunItemStack INF_MOBSIMULATION=themed("INF_MOBSIMULATION",Material.GILDED_BLACKSTONE,Theme.MACHINE1,
            get("Machines.INF_MOBSIMULATION.Name"),getList("Machines.INF_MOBSIMULATION.Lore"));
    public static final SlimefunItemStack INF_GEOQUARRY=themed("INF_GEOQUARRY",Material.CHISELED_QUARTZ_BLOCK,Theme.MACHINE1,
            get("Machines.INF_GEOQUARRY.Name"),getList("Machines.INF_GEOQUARRY.Lore"));
    public static final SlimefunItemStack RAND_EDITOR=themed("RAND_EDITOR",Material.ENCHANTING_TABLE,Theme.MACHINE1,
            get("Machines.RAND_EDITOR.Name"),getList("Machines.RAND_EDITOR.Lore"));
    public static final SlimefunItemStack ATTR_OP=themed("ATTR_OP",Material.ENCHANTING_TABLE,Theme.MACHINE1,
            get("Machines.ATTR_OP.Name"),getList("Machines.ATTR_OP.Lore"));
    public static final SlimefunItemStack GRIND_FACTORY=themed("GRIND_FACTORY",Material.GRINDSTONE,Theme.MACHINE1,
            get("Machines.GRIND_FACTORY.Name"),getList("Machines.GRIND_FACTORY.Lore"));
    public static final SlimefunItemStack TNT_GEN=themed("TNT_GEN",Material.NOTE_BLOCK,Theme.MACHINE1,
            get("Machines.TNT_GEN.Name"),getList("Machines.TNT_GEN.Lore"));
    public static final SlimefunItemStack ADVANCE_BREWER=themed("ADVANCE_BREWER",Material.SMOKER,Theme.MACHINE1,
            get("Machines.ADVANCE_BREWER.Name"),getList("Machines.ADVANCE_BREWER.Lore"));
    public static final SlimefunItemStack SIMU_LVOID=themed("SIMU_LVOID",Material.SOUL_TORCH,Theme.MACHINE1,
            get("Machines.SIMU_LVOID.Name"),getList("Machines.SIMU_LVOID.Lore"));
    public static final SlimefunItemStack SPACETOWER =themed("SPACETOWER",Material.SHROOMLIGHT,Theme.MACHINE1,
            get("Machines.SPACETOWER.Name"),getList("Machines.SPACETOWER.Lore"));
    public static final SlimefunItemStack SPACETOWER_FRAME=themed("SPACETOWER_FRAME",Material.AMETHYST_BLOCK,Theme.MACHINE1,
            get("Machines.SPACETOWER_FRAME.Name"),getList("Machines.SPACETOWER_FRAME.Lore"));
    public static final SlimefunItemStack ITEM_OP=themed("ITEM_OP",Material.SMITHING_TABLE,Theme.MACHINE1,
            get("Machines.ITEM_OP.Name"),getList("Machines.ITEM_OP.Lore"));
    public static final SlimefunItemStack CHUNK_CHARGER=themed("CHUNK_CHARGER",Material.SCULK_SHRIEKER,Theme.MACHINE1,
            get("Machines.CHUNK_CHARGER.Name"),getList("Machines.CHUNK_CHARGER.Lore"));
    public static final SlimefunItemStack INGOT_CONVERTOR=themed("INGOT_CONVERTOR",Material.PINK_GLAZED_TERRACOTTA,Theme.MACHINE1,
            get("Machines.INGOT_CONVERTOR.Name"),getList("Machines.INGOT_CONVERTOR.Lore"));
    public static final SlimefunItemStack LINE_CHARGER=themed("LINE_CHARGER",Material.DEEPSLATE_TILE_WALL,Theme.MACHINE1,
            get("Machines.LINE_CHARGER.Name"),getList("Machines.LINE_CHARGER.Lore"));
    public static final SlimefunItemStack LINE_CHARGER_PLUS=themed("LINE_CHARGER_PLUS",Material.DEEPSLATE_TILE_WALL,Theme.MACHINE1,
            get("Machines.LINE_CHARGER_PLUS.Name"),getList("Machines.LINE_CHARGER_PLUS.Lore"));
    public static final SlimefunItemStack ADJ_COLLECTOR=themed("ADJ_COLLECTOR",Material.RED_NETHER_BRICKS,Theme.MACHINE1,
            get("Machines.ADJ_COLLECTOR.Name"),getList("Machines.ADJ_COLLECTOR.Lore"));
    public static final SlimefunItemStack ADJ_COLLECTOR_PLUS=themed("ADJ_COLLECTOR_PLUS",Material.RED_NETHER_BRICKS,Theme.MACHINE1,
            get("Machines.ADJ_COLLECTOR_PLUS.Name"),getList("Machines.ADJ_COLLECTOR_PLUS.Lore"));
    public static final SlimefunItemStack LINE_COLLECTOR=themed("LINE_COLLECTOR",Material.RED_NETHER_BRICK_WALL,Theme.MACHINE1,
            get("Machines.LINE_COLLECTOR.Name"),getList("Machines.LINE_COLLECTOR.Lore"));
    public static final SlimefunItemStack LINE_COLLECTOR_PLUS=themed("LINE_COLLECTOR_PLUS",Material.RED_NETHER_BRICK_WALL,Theme.MACHINE1,
            get("Machines.LINE_COLLECTOR_PLUS.Name"),getList("Machines.LINE_COLLECTOR_PLUS.Lore"));
    public static final SlimefunItemStack ENERGY_STORAGE_NONE=themed("ENERGY_STORAGE_NONE",Material.CRACKED_DEEPSLATE_TILES,Theme.MACHINE1,
            get("Machines.ENERGY_STORAGE_NONE.Name"),getList("Machines.ENERGY_STORAGE_NONE.Lore"));
    public static final SlimefunItemStack ENERGY_STORAGE_IN=themed("ENERGY_STORAGE_IN",Material.CHISELED_DEEPSLATE,Theme.MACHINE1,
            get("Machines.ENERGY_STORAGE_IN.Name"),getList("Machines.ENERGY_STORAGE_IN.Lore"));
    public static final SlimefunItemStack ENERGY_STORAGE_IO=themed("ENERGY_STORAGE_IO",Material.CHISELED_POLISHED_BLACKSTONE,Theme.MACHINE1,
            get("Machines.ENERGY_STORAGE_IO.Name"),getList("Machines.ENERGY_STORAGE_IO.Lore"));
    public static final SlimefunItemStack ADJ_CHARGER=themed("ADJ_CHARGER",Material.DEEPSLATE_BRICKS,Theme.MACHINE1,
            get("Machines.ADJ_CHARGER.Name"),getList("Machines.ADJ_CHARGER.Lore"));
    public static final SlimefunItemStack ADJ_CHARGER_PLUS=themed("ADJ_CHARGER_PLUS",Material.DEEPSLATE_BRICKS,Theme.MACHINE1,
            get("Machines.ADJ_CHARGER_PLUS.Name"),getList("Machines.ADJ_CHARGER_PLUS.Lore"));
    public static final SlimefunItemStack ENERGY_PIPE=themed("ENERGY_PIPE",Material.LIGHTNING_ROD,Theme.MACHINE1,
            get("Machines.ENERGY_PIPE.Name"),getList("Machines.ENERGY_PIPE.Lore"));
    public static final SlimefunItemStack ENERGY_PIPE_PLUS=themed("ENERGY_PIPE_PLUS",Material.END_ROD,Theme.MACHINE1,
            get("Machines.ENERGY_PIPE_PLUS.Name"),getList("Machines.ENERGY_PIPE_PLUS.Lore"));
    public static final SlimefunItemStack FINAL_CRAFT=themed("FINAL_CRAFT",Material.BEACON,Theme.MACHINE1,
            get("Machines.FINAL_CRAFT.Name"),getList("Machines.FINAL_CRAFT.Lore"));
    public static final SlimefunItemStack VIRTUAL_EXPLORER=themed("VIRTUAL_EXPLORER",Material.DECORATED_POT,Theme.MACHINE1,
            get("Machines.VIRTUAL_EXPLORER.Name"),getList("Machines.VIRTUAL_EXPLORER.Lore"));
    public static final SlimefunItemStack TIMER_BLOCKENTITY=themed("TIMER_BLOCKENTITY",Material.REDSTONE_TORCH,Theme.MACHINE1,
            get("Machines.TIMER_BLOCKENTITY.Name"),getList("Machines.TIMER_BLOCKENTITY.Lore"));
    public static final SlimefunItemStack TIMER_RD=themed("TIMER_RD",Material.TORCH,Theme.MACHINE1,
            get("Machines.TIMER_RD.Name"),getList("Machines.TIMER_RD.Lore"));
    public static final SlimefunItemStack TIMER_SF=new InitializeSafeProvider<>(SlimefunItemStack.class,()->themed("TIMER_SF_入机",Material.SOUL_TORCH,Theme.MACHINE1,
            get("Machines.TIMER_SF.Name"),getList("Machines.TIMER_SF.Lore"))).v();
//    public static final SlimefunItemStack TIMER_SF_SEQ=themed("TIMER_SF_SEQ",Material.SOUL_TORCH,Theme.MACHINE1,
//            get("Machines.TIMER_SF_SEQ.Name"),getList("Machines.TIMER_SF_SEQ.Lore"));
    //manuals
    public static final SlimefunItemStack MANUAL_CORE=themed("MANUAL_CORE",Material.AMETHYST_SHARD,Theme.ITEM1,
            get("Manuals.MANUAL_CORE.Name"),getList("Manuals.MANUAL_CORE.Lore"));
    public static final SlimefunItemStack CRAFT_MANUAL=themed("CRAFT_MANUAL",Material.CRAFTING_TABLE,Theme.MANUAL1,
            get("Manuals.CRAFT_MANUAL.Name"),getList("Manuals.CRAFT_MANUAL.Lore"));
    public static final SlimefunItemStack FURNACE_MANUAL=themed("FURNACE_MANUAL",Material.FURNACE,Theme.MANUAL1,
            get("Manuals.FURNACE_MANUAL.Name"),getList("Manuals.FURNACE_MANUAL.Lore"));
    public static final SlimefunItemStack ENHANCED_CRAFT_MANUAL=themed("ENHANCED_CRAFT_MANUAL",Material.CRAFTING_TABLE,Theme.MANUAL1,
            get("Manuals.ENHANCED_CRAFT_MANUAL.Name"),getList("Manuals.ENHANCED_CRAFT_MANUAL.Lore"));
    public static final SlimefunItemStack GRIND_MANUAL=themed("GRIND_MANUAL",Material.DISPENSER,Theme.MANUAL1,
            get("Manuals.GRIND_MANUAL.Name"),getList("Manuals.GRIND_MANUAL.Lore"));
    public static final SlimefunItemStack ARMOR_FORGE_MANUAL=themed("ARMOR_FORGE_MANUAL",Material.IRON_BLOCK,Theme.MANUAL1,
            get("Manuals.ARMOR_FORGE_MANUAL.Name"),getList("Manuals.ARMOR_FORGE_MANUAL.Lore"));
    public static final SlimefunItemStack ORE_CRUSHER_MANUAL=themed("ORE_CRUSHER_MANUAL",Material.DROPPER,Theme.MANUAL1,
            get("Manuals.ORE_CRUSHER_MANUAL.Name"),getList("Manuals.ORE_CRUSHER_MANUAL.Lore"));
    public static final SlimefunItemStack COMPRESSOR_MANUAL=themed("COMPRESSOR_MANUAL",Material.PISTON,Theme.MANUAL1,
            get("Manuals.COMPRESSOR_MANUAL.Name"),getList("Manuals.COMPRESSOR_MANUAL.Lore"));
    public static final SlimefunItemStack PRESSURE_MANUAL=themed("PRESSURE_MANUAL",Material.GLASS,Theme.MANUAL1,
            get("Manuals.PRESSURE_MANUAL.Name"),getList("Manuals.PRESSURE_MANUAL.Lore"));
    public static final SlimefunItemStack MAGIC_WORKBENCH_MANUAL=themed("MAGIC_WORKBENCH_MANUAL",Material.BOOKSHELF,Theme.MANUAL1,
            get("Manuals.MAGIC_WORKBENCH_MANUAL.Name"),getList("Manuals.MAGIC_WORKBENCH_MANUAL.Lore"));
    public static final SlimefunItemStack ORE_WASHER_MANUAL=themed("ORE_WASHER_MANUAL",Material.BLUE_STAINED_GLASS,Theme.MANUAL1,
            get("Manuals.ORE_WASHER_MANUAL.Name"),getList("Manuals.ORE_WASHER_MANUAL.Lore"));
    public static final SlimefunItemStack GOLD_PAN_MANUAL=themed("GOLD_PAN_MANUAL",Material.BROWN_TERRACOTTA,Theme.MANUAL1,
            get("Manuals.GOLD_PAN_MANUAL.Name"),getList("Manuals.GOLD_PAN_MANUAL.Lore"));
    public static final SlimefunItemStack ANCIENT_ALTAR_MANUAL=themed("ANCIENT_ALTAR_MANUAL",Material.ENCHANTING_TABLE,Theme.MANUAL1,
            get("Manuals.ANCIENT_ALTAR_MANUAL.Name"),getList("Manuals.ANCIENT_ALTAR_MANUAL.Lore"));
    public static final SlimefunItemStack SMELTERY_MANUAL=themed("SMELTERY_MANUAL",Material.BLAST_FURNACE,Theme.MANUAL1,
            get("Manuals.SMELTERY_MANUAL.Name"),getList("Manuals.SMELTERY_MANUAL.Lore"));
    public static final SlimefunItemStack CRUCIBLE_MANUAL=themed("CRUCIBLE_MANUAL",Material.RED_TERRACOTTA,Theme.MANUAL1,
            get("Manuals.CRUCIBLE_MANUAL.Name"),getList("Manuals.CRUCIBLE_MANUAL.Lore"));
    public static final SlimefunItemStack PULVERIZER_MANUAL=themed("PULVERIZER_MANUAL",Material.GRINDSTONE,Theme.MANUAL1,
            get("Manuals.PULVERIZER_MANUAL.Name"),getList("Manuals.PULVERIZER_MANUAL.Lore"));
    public static final SlimefunItemStack MULTICRAFTTABLE_MANUAL=themed("MULTICRAFTTABLE_MANUAL",Material.CRAFTING_TABLE,Theme.MANUAL1,
            get("Manuals.MULTICRAFTTABLE_MANUAL.Name"),getList("Manuals.MULTICRAFTTABLE_MANUAL.Lore"));
    public static final SlimefunItemStack TABLESAW_MANUAL=themed("TABLESAW_MANUAL",Material.STONECUTTER,Theme.MANUAL1,
            get("Manuals.TABLESAW_MANUAL.Name"),getList("Manuals.TABLESAW_MANUAL.Lore"));
    public static final SlimefunItemStack COMPOSTER=themed("COMPOSTER",Material.CAULDRON,Theme.MANUAL1,
            get("Manuals.COMPOSTER.Name"),getList("Manuals.COMPOSTER.Lore"));
    public static final SlimefunItemStack MULTIMACHINE_MANUAL=themed("MULTIMACHINE_MANUAL",Material.GRAY_STAINED_GLASS,Theme.MANUAL1,
            get("Manuals.MULTIMACHINE_MANUAL.Name"),getList("Manuals.MULTIMACHINE_MANUAL.Lore"));
    public static final SlimefunItemStack MOBDATA_MANUAL=themed("MOBDATA_MANUAL",Material.LODESTONE,Theme.MANUAL1,
            get("Manuals.MOBDATA_MANUAL.Name"),getList("Manuals.MOBDATA_MANUAL.Lore"));
    public static final SlimefunItemStack INFINITY_MANUAL=themed("INFINITY_MANUAL",Material.RESPAWN_ANCHOR,Theme.MANUAL1,
            get("Manuals.INFINITY_MANUAL.Name"),getList("Manuals.INFINITY_MANUAL.Lore"));
    public static final SlimefunItemStack NTWWORKBENCH_MANUAL=themed("NTWWORKBENCH_MANUAL",Material.BAMBOO_BLOCK,Theme.MANUAL1,
            get("Manuals.NTWWORKBENCH_MANUAL.Name"),getList("Manuals.NTWWORKBENCH_MANUAL.Lore"));
    public static final SlimefunItemStack MULTIBLOCK_MANUAL=themed("MULTIBLOCK_MANUAL",Material.BRICKS,Theme.MANUAL1,
            get("Manuals.MULTIBLOCK_MANUAL.Name"),getList("Manuals.MULTIBLOCK_MANUAL.Lore"));
    public static final SlimefunItemStack FINAL_MANUAL=themed("FINAL_MANUAL",Material.REINFORCED_DEEPSLATE,Theme.MANUAL1,
            get("Manuals.FINAL_MANUAL.Name"),getList("Manuals.FINAL_MANUAL.Lore"));
    public static final SlimefunItemStack REPLACE_CARD=themed("REPLACE_CARD",Material.PRIZE_POTTERY_SHERD,Theme.ITEM1,
            get("Items.REPLACE_CARD.Name"),getList("Items.REPLACE_CARD.Lore"));
    public static final SlimefunItemStack REPLACE_SF_CARD=themed("REPLACE_SF_CARD",Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("Items.REPLACE_SF_CARD.Name"),getList("Items.REPLACE_SF_CARD.Lore"));
    public static final SlimefunItemStack CARD_MAKER=themed("CARD_MAKER",Material.FLETCHING_TABLE,Theme.MACHINE1,
            get("Manuals.CARD_MAKER.Name"),getList("Manuals.CARD_MAKER.Lore"));
    public static final SlimefunItemStack ADV_MANUAL=themed("ADV_MANUAL",Material.LECTERN,Theme.MANUAL1,
            get("Manuals.ADV_MANUAL.Name"),getList("Manuals.ADV_MANUAL.Lore"));
    //generators
    public static final SlimefunItemStack MAGIC_STONE=themed("MAGIC_STONE",Material.COBBLESTONE,Theme.MACHINE2,
            get("Generators.MAGIC_STONE.Name"),getList("Generators.MAGIC_STONE.Lore"));
    public static final SlimefunItemStack BOOL_MG=themed("BOOL_MG",Material.REDSTONE_TORCH,Theme.MACHINE2,
            get("Generators.BOOL_MG.Name"),getList("Generators.BOOL_MG.Lore"));
    public static final SlimefunItemStack OVERWORLD_MINER=themed("OVERWORLD_MINER",Material.SMOOTH_STONE,Theme.MACHINE2,
            get("Generators.OVERWORLD_MINER.Name"),getList("Generators.OVERWORLD_MINER.Lore"));
    public static final SlimefunItemStack NETHER_MINER=themed("NETHER_MINER",Material.CRIMSON_NYLIUM,Theme.MACHINE2,
            get("Generators.NETHER_MINER.Name"),getList("Generators.NETHER_MINER.Lore"));
    public static final SlimefunItemStack END_MINER =themed("END_MINER",Material.END_STONE_BRICKS,Theme.MACHINE2,
            get("Generators.END_MINER.Name"),getList("Generators.END_MINER.Lore"));
    public static final SlimefunItemStack DIMENSION_MINER=themed("DIMENSION_MINER",Material.CRYING_OBSIDIAN,Theme.MACHINE2,
            get("Generators.DIMENSION_MINER.Name"),getList("Generators.DIMENSION_MINER.Lore"));
    public static final SlimefunItemStack REDSTONE_MG=themed("REDSTONE_MG",Material.OBSERVER,Theme.MACHINE2,
            get("Generators.REDSTONE_MG.Name"),getList("Generators.REDSTONE_MG.Lore"));
    public static final SlimefunItemStack DUPE_MG=themed("DUPE_MG",Material.STICKY_PISTON,Theme.MACHINE2,
            get("Generators.DUPE_MG.Name"),getList("Generators.DUPE_MG.Lore"));
    public static final SlimefunItemStack ENDDUPE_MG=themed("ENDDUPE_MG",Material.END_PORTAL_FRAME,Theme.MACHINE2,
            get("Generators.ENDDUPE_MG.Name"),getList("Generators.ENDDUPE_MG.Lore"));
    public static final SlimefunItemStack BNOISE_MAKER = themed("BNOISE_MAKER", Material.JUKEBOX, Theme.MACHINE2,
            get("Generators.BNOISE_MAKER.Name"), getList("Generators.BNOISE_MAKER.Lore"));
    public static final SlimefunItemStack BNOISE_HEAD = themed("BNOISE_HEAD", CustomHead.BNOISE_HEAD.getItem(), Theme.ITEM1,
            get("Items.BNOISE_HEAD.Name"), getList("Items.BNOISE_HEAD.Lore"));
    public static final SlimefunItemStack STACKMGENERATOR=themed("STACKMGENERATOR",Material.SMOOTH_STONE,Theme.MACHINE2,
            get("Generators.STACKMGENERATOR.Name"),getList("Generators.STACKMGENERATOR.Lore"));
    public static final SlimefunItemStack REVERSE_GENERATOR=themed("REVERSE_GENERATOR",CustomHead.REVERSE.getItem(), Theme.MACHINE2,
            get("Generators.REVERSE_GENERATOR.Name"),getList("Generators.REVERSE_GENERATOR.Lore"));
    public static final SlimefunItemStack VIRTUAL_MINER=themed("VIRTUAL_MINER",Material.CHERRY_WOOD,Theme.MACHINE2,
            get("Generators.VIRTUAL_MINER.Name"),getList("Generators.VIRTUAL_MINER.Lore"));
    public static final SlimefunItemStack VIRTUAL_PLANT=themed("VIRTUAL_PLANT",Material.STRIPPED_CHERRY_WOOD,Theme.MACHINE2,
            get("Generators.VIRTUAL_PLANT.Name"),getList("Generators.VIRTUAL_PLANT.Lore"));
    public static final SlimefunItemStack MAGIC_PLANT=themed("MAGIC_PLANT",Material.DIRT,Theme.MACHINE2,
            get("Generators.MAGIC_PLANT.Name"),getList("Generators.MAGIC_PLANT.Lore"));
    public static final SlimefunItemStack OVERWORLD_PLANT=themed("OVERWORLD_PLANT",Material.PODZOL,Theme.MACHINE2,
            get("Generators.OVERWORLD_PLANT.Name"),getList("Generators.OVERWORLD_PLANT.Lore"));
    public static final SlimefunItemStack NETHER_PLANT=themed("NETHER_PLANT",Material.WARPED_NYLIUM,Theme.MACHINE2,
            get("Generators.NETHER_PLANT.Name"),getList("Generators.NETHER_PLANT.Lore"));
    public static final SlimefunItemStack END_PLANT=themed("END_PLANT",Material.END_STONE,Theme.MACHINE2,
            get("Generators.END_PLANT.Name"),getList("Generators.END_PLANT.Lore"));
    public static final SlimefunItemStack SMELTRY=themed("SMELTRY",Material.FURNACE,Theme.MACHINE1,
            get("Machines.SMELTRY.Name"),getList("Machines.SMELTRY.Lore"));
    public static final SlimefunItemStack STONE_FACTORY=themed("STONE_FACTORY",Material.STONE_BRICKS,Theme.MACHINE2,
            get("Generators.STONE_FACTORY.Name"),getList("Generators.STONE_FACTORY.Lore"));
    public static final SlimefunItemStack TNT_MG=themed("TNT_MG",Material.ANCIENT_DEBRIS,Theme.MACHINE2,
            get("Generators.TNT_MG.Name"),getList("Generators.TNT_MG.Lore"));
    //cargos
    public static final SlimefunItemStack CARGO_PART=themed("CARGO_PART",Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("Items.CARGO_PART.Name"),getList("Items.CARGO_PART.Lore"));
    public static final SlimefunItemStack CARGO_CONFIG=themed("CARGO_CONFIG",Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("Items.CARGO_CONFIG.Name"),getList("Items.CARGO_CONFIG.Lore"));
    public static final SlimefunItemStack CARGO_CONFIGURATOR=themed("CARGO_CONFIGURATOR",Material.JUKEBOX,Theme.CARGO1,
            get("Items.CARGO_CONFIGURATOR.Name"),getList("Items.CARGO_CONFIGURATOR.Lore"));
    public static final SlimefunItemStack SIMPLE_CARGO=themed("SIMPLE_CARGO",Material.TARGET,Theme.CARGO1,
            get("Cargo.SIMPLE_CARGO.Name"),getList("Cargo.SIMPLE_CARGO.Lore"));
    public static final SlimefunItemStack REMOTE_CARGO=themed("REMOTE_CARGO",Material.CALIBRATED_SCULK_SENSOR,Theme.CARGO1,
            get("Cargo.REMOTE_CARGO.Name"),getList("Cargo.REMOTE_CARGO.Lore"));
    public static final SlimefunItemStack LINE_CARGO=themed("LINE_CARGO",Material.OBSERVER,Theme.CARGO1,
            get("Cargo.LINE_CARGO.Name"),getList("Cargo.LINE_CARGO.Lore"));
    public static final SlimefunItemStack BISORTER=themed("BISORTER",Material.VERDANT_FROGLIGHT,Theme.CARGO1,
            get("Cargo.BISORTER.Name"),getList("Cargo.BISORTER.Lore"));
    public static final SlimefunItemStack QUARSORTER=themed("QUARSORTER",Material.PEARLESCENT_FROGLIGHT,Theme.CARGO1,
            get("Cargo.QUARSORTER.Name"),getList("Cargo.QUARSORTER.Lore"));
    public static final SlimefunItemStack OCTASORTER=themed("OCTASORTER",Material.OCHRE_FROGLIGHT,Theme.CARGO1,
            get("Cargo.OCTASORTER.Name"),getList("Cargo.OCTASORTER.Lore"));
    public static final SlimefunItemStack ADV_TRASH=themed("ADV_TRASH",CustomHead.FIRE_GENERATOR.getItem(), Theme.CARGO1,
            get("Cargo.ADV_TRASH.Name"),getList("Cargo.ADV_TRASH.Lore"));
    public static final SlimefunItemStack STORAGE_OPERATOR=themed("STORAGE_OPERATOR",Material.CARTOGRAPHY_TABLE,Theme.CARGO1,
            get("Cargo.STORAGE_OPERATOR.Name"),getList("Cargo.STORAGE_OPERATOR.Lore"));
    public static final SlimefunItemStack ADV_ADJACENT_CARGO=themed("ADV_ADJACENT_CARGO",Material.TARGET,Theme.CARGO1,
            get("Cargo.ADV_ADJACENT_CARGO.Name"),getList("Cargo.ADV_ADJACENT_CARGO.Lore"));
    public static final SlimefunItemStack ADV_REMOTE_CARGO=themed("ADV_REMOTE_CARGO",Material.CALIBRATED_SCULK_SENSOR,Theme.CARGO1,
            get("Cargo.ADV_REMOTE_CARGO.Name"),getList("Cargo.ADV_REMOTE_CARGO.Lore"));
    public static final SlimefunItemStack ADV_LINE_CARGO=themed("ADV_LINE_CARGO",Material.OBSERVER,Theme.CARGO1,
            get("Cargo.ADV_LINE_CARGO.Name"),getList("Cargo.ADV_LINE_CARGO.Lore"));
    public static final SlimefunItemStack REDSTONE_ADJACENT_CARGO=themed("REDSTONE_ADJACENT_CARGO",Material.REDSTONE_LAMP,Theme.CARGO1,
            get("Cargo.REDSTONE_ADJACENT_CARGO.Name"),getList("Cargo.REDSTONE_ADJACENT_CARGO.Lore"));
    public static final SlimefunItemStack CHIP_ADJ_CARGO=themed("CHIP_ADJ_CARGO",Material.SHROOMLIGHT,Theme.CARGO1,
            get("Cargo.CHIP_ADJ_CARGO.Name"),getList("Cargo.CHIP_ADJ_CARGO.Lore"));
    public static final SlimefunItemStack RESETTER=themed("RESETTER",Material.FLETCHING_TABLE,Theme.CARGO1,
            get("Cargo.RESETTER.Name"),getList("Cargo.RESETTER.Lore"));
    public static final SlimefunItemStack STORAGE_SINGULARITY= themed("STORAGE_SINGULARITY",Material.NETHER_STAR,Theme.ITEM1,
            get("Cargo.STORAGE_SINGULARITY.Name"),getList("Cargo.STORAGE_SINGULARITY.Lore"));
    public static final SlimefunItemStack QUANTUM_LINK=themed("QUANTUM_LINK",Material.NETHER_STAR,Theme.ITEM1,
            get("Cargo.QUANTUM_LINK.Name"),getList("Cargo.QUANTUM_LINK.Lore"));
    public static final SlimefunItemStack INPORT=themed("INPORT",Material.END_STONE,Theme.MACHINE1,
            get("Cargo.INPORT.Name"),getList("Cargo.INPORT.Lore"));
    public static final SlimefunItemStack OUTPORT=themed("OUTPORT",Material.END_STONE,Theme.MACHINE1,
            get("Cargo.OUTPORT.Name"),getList("Cargo.OUTPORT.Lore"));
    public static final SlimefunItemStack IOPORT=themed("IOPORT",Material.PURPUR_PILLAR,Theme.MACHINE1,
            get("Cargo.IOPORT.Name"),getList("Cargo.IOPORT.Lore"));
    public static final SlimefunItemStack STORAGE=themed("STORAGE",Material.LIGHT_GRAY_STAINED_GLASS,Theme.CARGO1,
            get("Cargo.STORAGE.Name"),getList("Cargo.STORAGE.Lore"));
    public static final SlimefunItemStack STORAGE_INPUT=themed("STORAGE_INPUT",Material.BLUE_STAINED_GLASS,Theme.CARGO1,
            get("Cargo.STORAGE_INPUT.Name"),getList("Cargo.STORAGE_INPUT.Lore"));
    public static final SlimefunItemStack STORAGE_OUTPUT=themed("STORAGE_OUTPUT",Material.RED_STAINED_GLASS,Theme.CARGO1,
            get("Cargo.STORAGE_OUTPUT.Name"),getList("Cargo.STORAGE_OUTPUT.Lore"));
    public static final SlimefunItemStack BIFILTER=themed("BIFILTER",Material.PRISMARINE,Theme.CARGO1,
            get("Cargo.BIFILTER.Name"),getList("Cargo.BIFILTER.Lore"));
    public static final SlimefunItemStack QUARFILTER=themed("QUARFILTER",Material.PRISMARINE_BRICKS,Theme.CARGO1,
            get("Cargo.QUARFILTER.Name"),getList("Cargo.QUARFILTER.Lore"));
    public static final SlimefunItemStack OCTAFILTER=themed("OCTAFILTER",Material.DARK_PRISMARINE,Theme.CARGO1,
            get("Cargo.OCTAFILTER.Name"),getList("Cargo.OCTAFILTER.Lore"));
    public static final SlimefunItemStack CARGO_PIP=themed("CARGO_PIP",Material.END_ROD,Theme.CARGO1,
            get("Cargo.CARGO_PIP.Name"),getList("Cargo.CARGO_PIP.Lore"));
    //multiblock
    public static final SlimefunItemStack PORTAL_CORE=themed("PORTAL_CORE",Material.CRYING_OBSIDIAN,Theme.MULTIBLOCK1,
            get("MultiBlock.PORTAL_CORE.Name"),getList("MultiBlock.PORTAL_CORE.Lore"));
    public static final SlimefunItemStack PORTAL_FRAME=themed("PORTAL_FRAME",Material.IRON_BLOCK,Theme.MULTIBLOCK2,
            get("MultiBlock.PORTAL_FRAME.Name"),getList("MultiBlock.PORTAL_FRAME.Lore"));
    public static final SlimefunItemStack SOLAR_REACTOR=themed("SOLAR_REACTOR",Material.LODESTONE,Theme.MULTIBLOCK1,
            get("MultiBlock.SOLAR_REACTOR.Name"),getList("MultiBlock.SOLAR_REACTOR.Lore"));
    public static final SlimefunItemStack SOLAR_REACTOR_FRAME=themed("SOLAR_REACTOR_FRAME",Material.CHISELED_QUARTZ_BLOCK,Theme.MULTIBLOCK2,
            get("MultiBlock.SOLAR_REACTOR_FRAME.Name"),getList("MultiBlock.SOLAR_REACTOR_FRAME.Lore"));
    public static final SlimefunItemStack SOLAR_REACTOR_GLASS=themed("SOLAR_REACTOR_GLASS",Material.TINTED_GLASS,Theme.MULTIBLOCK2,
            get("MultiBlock.SOLAR_REACTOR_GLASS.Name"),getList("MultiBlock.SOLAR_REACTOR_GLASS.Lore"));
    public static final SlimefunItemStack SOLAR_INPUT=themed("SOLAR_INPUT",Material.WAXED_OXIDIZED_COPPER,Theme.MULTIBLOCK2,
            get("MultiBlock.SOLAR_INPUT.Name"),getList("MultiBlock.SOLAR_INPUT.Lore"));
    public static final SlimefunItemStack SOLAR_OUTPUT=themed("SOLAR_OUTPUT",Material.WAXED_COPPER_BLOCK,Theme.MULTIBLOCK2,
            get("MultiBlock.SOLAR_OUTPUT.Name"),getList("MultiBlock.SOLAR_OUTPUT.Lore"));
    public static final SlimefunItemStack TRANSMUTATOR_FRAME=themed("TRANSMUTATOR_FRAME",Material.SMOOTH_STONE, Theme.MULTIBLOCK2,
            get("MultiBlock.TRANSMUTATOR_FRAME.Name"),getList("MultiBlock.TRANSMUTATOR_FRAME.Lore"));
    public static final SlimefunItemStack TRANSMUTATOR_GLASS=themed("TRANSMUTATOR_GLASS",Material.LIGHT_GRAY_STAINED_GLASS,Theme.MULTIBLOCK2,
            get("MultiBlock.TRANSMUTATOR_GLASS.Name"),getList("MultiBlock.TRANSMUTATOR_GLASS.Lore"));
    public static final SlimefunItemStack TRANSMUTATOR_ROD=themed("TRANSMUTATOR_ROD",Material.REINFORCED_DEEPSLATE,Theme.MULTIBLOCK2,
            get("MultiBlock.TRANSMUTATOR_ROD.Name"),getList("MultiBlock.TRANSMUTATOR_ROD.Lore"));
    public static final SlimefunItemStack TRANSMUTATOR=themed("TRANSMUTATOR",Material.FURNACE,Theme.MULTIBLOCK1,
            get("MultiBlock.TRANSMUTATOR.Name"),getList("MultiBlock.TRANSMUTATOR.Lore"));
    public static final SlimefunItemStack FINAL_BASE=themed("FINAL_BASE",Material.POLISHED_DEEPSLATE,Theme.MULTIBLOCK1,
            get("MultiBlock.FINAL_BASE.Name"),getList("MultiBlock.FINAL_BASE.Lore"));
    public static final SlimefunItemStack FINAL_ALTAR=themed("FINAL_ALTAR",Material.CHISELED_DEEPSLATE,Theme.MULTIBLOCK2,
            get("MultiBlock.FINAL_ALTAR.Name"),getList("MultiBlock.FINAL_ALTAR.Lore"));
    public static final SlimefunItemStack SMITH_WORKSHOP=themed("SMITH_WORKSHOP",Material.RESPAWN_ANCHOR,Theme.MACHINE1,
            get("MultiBlock.SMITH_WORKSHOP.Name"),getList("MultiBlock.SMITH_WORKSHOP.Lore"));
    public static final SlimefunItemStack SMITH_INTERFACE_NONE=themed("SMITH_INTERFACE_NONE",Material.SEA_LANTERN,Theme.MACHINE1,
            get("MultiBlock.SMITH_INTERFACE_NONE.Name"),getList("MultiBlock.SMITH_INTERFACE_NONE.Lore"));
    public static final SlimefunItemStack SMITH_INTERFACE_CRAFT=themed("SMITH_INTERFACE_CRAFT",Material.TARGET,Theme.MACHINE1,
            get("MultiBlock.SMITH_INTERFACE_CRAFT.Name"),getList("MultiBlock.SMITH_INTERFACE_CRAFT.Lore"));
    //feat
    public static final SlimefunItemStack CUSTOM1=
            themed("CUSTOM1",new ItemStack(Material.COMMAND_BLOCK),Theme.ITEM1,"测试物件1","只是一个简单的测试");
    public static final SlimefunItemStack MACHINE1=
            themed("MACHINE1",new ItemStack(Material.FURNACE),Theme.MACHINE1,"测试机器1","tnnd对照组");
    public static final SlimefunItemStack MACHINE2=
            themed("MACHINE2",new ItemStack(Material.FURNACE),Theme.MACHINE1,"测试机2","tnnd实验组");
    public static final SlimefunItemStack MACHINE3=
            themed("MACHINE3",new ItemStack(Material.FURNACE),Theme.MACHINE1,"测试机3","tnnd测试组 AbstractProcessor");
    public static final SlimefunItemStack MACHINE4=
            themed("MACHINE4",new ItemStack(Material.FURNACE),Theme.MACHINE1,"测试机4","tnnd测试组 AbstractAdvancedProcessor");
    public static final SlimefunItemStack SMG1=
            themed("SMG1",new ItemStack(Material.DIAMOND_BLOCK),Theme.MACHINE2,"测试生成器1","测测我的");
    public static final SlimefunItemStack MMG1=
            themed("MMG1",new ItemStack(Material.EMERALD_BLOCK),Theme.MACHINE2,"定向生成器1","测测我的");
    public static final SlimefunItemStack MANUAL1=
            themed("MANUAL1",new ItemStack(Material.CRAFTING_TABLE),Theme.MANUAL1,"测试快捷机器","强化工作台");
    public static final SlimefunItemStack MANUAL_MULTI=
            themed("MANUAL_MULTI",new ItemStack(Material.CRAFTING_TABLE),Theme.MANUAL1,"测试快捷机器","多方块机器");
    public static final SlimefunItemStack MANUAL_KILL=
            themed("MANUAL_KILL",new ItemStack(Material.RESPAWN_ANCHOR),Theme.MANUAL1,"测试快捷机器","击杀掉落");
    public static final SlimefunItemStack MANUAL_INF=
            themed("MANUAL_INF",new ItemStack(Material.RESPAWN_ANCHOR),Theme.MANUAL1,"测试快捷机器","无尽工作台");
    public static final SlimefunItemStack MANUAL_MOB=
            themed("MANUAL_MOB",new ItemStack(Material.LODESTONE),Theme.MANUAL1,"测试快捷机器","无尽芯片注入");
    public static final SlimefunItemStack MANUAL_NTWBENCH=
            themed("MANUAL_NTWBENCH",new ItemStack(Material.DRIED_KELP_BLOCK),Theme.MANUAL1,"测试快捷机器","网络工作台");
    public static final SlimefunItemStack AUTOSMELTING1=
            themed("AUTOCRAFT_SMELT",new ItemStack(Material.FURNACE),Theme.MANUAL1,"测试AutoCraft","冶炼炉");
    public static final SlimefunItemStack AUTO_INF=
            themed("AUTOCRAFT_INF",new ItemStack(Material.RESPAWN_ANCHOR),Theme.MANUAL1,"测试定向合成机","无尽工作台");

//    public static final SlimefunItemStack INPORT=
//            themed("INPORT",new ItemStack(Material.END_STONE),Theme.CARGO1,"存入接口","较快的将物品存入奇点...");
//    public static final SlimefunItemStack OUTPORT=
//            themed("OUTPORT",new ItemStack(Material.END_STONE),Theme.CARGO1,"取出接口","较快的将物品取出奇点...");
    public static final SlimefunItemStack TESTUNIT1=
            themed("TESTUNIT1",new ItemStack(Material.GLASS),Theme.CARGO1,"测试存储单元","啥用都没");
    public static final SlimefunItemStack TESTUNIT2=
            themed("TESTUNIT2",new ItemStack(Material.GLASS),Theme.CARGO1,"测试存储单元2","啥用都没");
    public static final SlimefunItemStack TESTUNIT3=
            themed("TESTUNIT3",new ItemStack(Material.GLASS),Theme.CARGO1,"测试存储单元3","啥用都没");
    public static final SlimefunItemStack AUTO_SPECIAL=
            themed("AUTOCRAFT_SPECIAL",new ItemStack(Material.LOOM),Theme.MACHINE2,"测试特殊合成机","测试测试");
    public static final SlimefunItemStack AUTO_MULTIBLOCK=
            themed("AUTOCRAFT_MULTIBLOCK",new ItemStack(Material.BRICKS),Theme.MANUAL1,"测试快捷多方块","测试测试");
    public static final SlimefunItemStack WORKBENCH1=
            themed("WORKBENCH1",new ItemStack(Material.ENCHANTING_TABLE),Theme.BENCH1,"测试工作站","测试测试");
    //final
    public static final SlimefunItemStack FINAL_SEQUENTIAL=themed("FINAL_SEQUENTIAL",Material.STRIPPED_BAMBOO_BLOCK,Theme.MACHINE1,
            get("Machines.FINAL_SEQUENTIAL.Name"),getList("Machines.FINAL_SEQUENTIAL.Lore"));

    public static final SlimefunItemStack FINAL_STACKMACHINE=themed("FINAL_STACKMACHINE",Material.BLAST_FURNACE,Theme.MACHINE1,
            get("Machines.FINAL_STACKMACHINE.Name"),getList("Machines.FINAL_STACKMACHINE.Lore"));
    public static final SlimefunItemStack FINAL_STACKMGENERATOR=themed("FINAL_STACKMGENERATOR",Material.POLISHED_ANDESITE,Theme.MACHINE2,
            get("Generators.FINAL_STACKMGENERATOR.Name"),getList("Generators.FINAL_STACKMGENERATOR.Lore"));
    public static final SlimefunItemStack FINAL_STONE_MG=themed("FINAL_STONE_MG",Material.DEEPSLATE_TILES,Theme.MACHINE2,
            get("Generators.FINAL_STONE_MG.Name"),getList("Generators.FINAL_STONE_MG.Lore"));

    public static final SlimefunItemStack TESTPART=themed("TEST_MPART",Material.OBSIDIAN,Theme.MACHINE1,"测试多方块部件","测试测试");
    public static final SlimefunItemStack TESTCORE=themed("TEST_MCORE",Material.IRON_BLOCK,Theme.MACHINE1,"测试多方块核心","测试测试");
    public static final SlimefunItemStack TEST_SEQ=themed("TEST_SEQ",Material.LOOM,Theme.MACHINE1,
            get("Items.TEST_SEQ.Name"),getList("Items.TEST_SEQ.Lore"));

    //tmp占位符
    public static final SlimefunItemStack TMP1= new SlimefunItemStack("TMP1",Material.STONE,"&b占位符","&7暂未开发");
    public static final SlimefunItemStack RESOLVE_FAILED=themed("RESOLVE_FAILED",Material.STRUCTURE_VOID,Theme.NONE,
            get("Items.RESOLVE_FAILED.Name"),getList("Items.RESOLVE_FAILED.Lore"));
    public static final SlimefunItemStack SHELL=themed("SHELL",Material.BOOK,Theme.ITEM1,
            get("Items.SHELL.Name"),getList("Items.SHELL.Lore"));
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
        add(MULTIBLOCKBUILDER);
    }};
}
