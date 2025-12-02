package me.matl114.logitech.utils;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.BlockDataController;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.listeners.Events.AttackPermissionTestEvent;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.matlib.core.EnvironmentManager;
import me.matl114.matlib.utils.reflect.wrapper.FieldAccess;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTables;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WorldUtils {
    public static SlimefunAddon INSTANCE= MyAddon.getInstance();
    public static final BlockDataController CONTROLLER=Slimefun.getDatabaseManager().getBlockDataController();
    {
    }
    public static void setBlock(Location loc, Material material) {
        loc.getBlock().setType(material);
    }
    public static void setAir(Location loc) {
        loc.getBlock().setType(Material.AIR);
    }
    public static EnumMap<Material,Integer> FOOD_LEVELS=new EnumMap<>(Material.class){{
        put(Material.MUSHROOM_STEW,6);
        put(Material.BEEF,3);
        put(Material.ROTTEN_FLESH,4);
        put(Material.CHORUS_FRUIT,4);
        put(Material.BEETROOT,1);
        put(Material.POTATO,1);
        put(Material.COD,2);
        put(Material.GOLDEN_APPLE,4);
        put(Material.ENCHANTED_GOLDEN_APPLE,4);
        put(Material.SALMON,2);
        put(Material.COOKED_COD,5);
        put(Material.HONEY_BOTTLE,6);
        put(Material.COOKED_PORKCHOP,8);
        put(Material.PUMPKIN_PIE,8);
        put(Material.POISONOUS_POTATO,2);
        put(Material.RABBIT,3);
        put(Material.PORKCHOP,3);
        put(Material.BAKED_POTATO,5);
        put(Material.BEETROOT_SOUP,6);
        put(Material.PUFFERFISH,1);
        put(Material.COOKED_RABBIT,5);
        put(Material.GLOW_BERRIES,2);
        put(Material.SUSPICIOUS_STEW,6);
        put(Material.SWEET_BERRIES,2);
        put(Material.TROPICAL_FISH,1);
        put(Material.COOKED_CHICKEN,6);
        put(Material.BREAD,5);
        put(Material.GOLDEN_CARROT,6);
        put(Material.CHICKEN,2);
        put(Material.MELON_SLICE,2);
        put(Material.RABBIT_STEW,10);
        put(Material.APPLE,4);
        put(Material.CARROT,3);
        put(Material.COOKED_MUTTON,6);
        put(Material.COOKED_SALMON,6);
        put(Material.MUTTON,2);
        put(Material.SPIDER_EYE,2);
        put(Material.COOKIE,2);
        put(Material.DRIED_KELP,1);
        put(Material.COOKED_BEEF,8);
        //till 1.20.4
    }};
    public static EnumMap<Material,Integer> FOOD_SATURATION_MUL_10=new EnumMap<>(Material.class){{
        put(Material.MUSHROOM_STEW,192);
        put(Material.BEEF,78);
        put(Material.ROTTEN_FLESH,88);
        put(Material.CHORUS_FRUIT,104);
        put(Material.BEETROOT,32);
        put(Material.POTATO,26);
        put(Material.COD,44);
        put(Material.GOLDEN_APPLE,176);
        put(Material.ENCHANTED_GOLDEN_APPLE,176);
        put(Material.SALMON,44);
        put(Material.COOKED_COD,160);
        put(Material.HONEY_BOTTLE,132);
        put(Material.COOKED_PORKCHOP,288);
        put(Material.PUMPKIN_PIE,208);
        put(Material.POISONOUS_POTATO,52);
        put(Material.RABBIT,78);
        put(Material.PORKCHOP,78);
        put(Material.BAKED_POTATO,160);
        put(Material.BEETROOT_SOUP,192);
        put(Material.PUFFERFISH,22);
        put(Material.COOKED_RABBIT,160);
        put(Material.GLOW_BERRIES,44);
        put(Material.SUSPICIOUS_STEW,192);
        put(Material.SWEET_BERRIES,44);
        put(Material.TROPICAL_FISH,22);
        put(Material.COOKED_CHICKEN,192);
        put(Material.BREAD,160);
        put(Material.GOLDEN_CARROT,264);
        put(Material.CHICKEN,52);
        put(Material.MELON_SLICE,52);
        put(Material.RABBIT_STEW,320);
        put(Material.APPLE,104);
        put(Material.CARROT,96);
        put(Material.COOKED_MUTTON,216);
        put(Material.COOKED_SALMON,216);
        put(Material.MUTTON,52);
        put(Material.SPIDER_EYE,72);
        put(Material.COOKIE,44);
        put(Material.DRIED_KELP,26);
        put(Material.COOKED_BEEF,288);
        //till 1.20.4
    }};
    public static HashMap<String,HashSet<LootTables>> LOOTTABLES_TYPES =new HashMap<>(){{
        for (LootTables loots:LootTables.values()){
            String[] splitedPath=loots.getKey().getKey().split("/");
            if(splitedPath.length!=0){
                String type=splitedPath[0];
                if(type!=null){
                    computeIfAbsent(type,t->new HashSet<>()).add(loots);
                }
            }
        }
    }};

    public static List<LootTables> NETHER_STRUCTURE_CHESTS=new ArrayList<>(){{
        add(LootTables.NETHER_BRIDGE);
        add(LootTables.BASTION_TREASURE);
        add(LootTables.BASTION_OTHER);
        add(LootTables.BASTION_BRIDGE);
        add(LootTables.BASTION_HOGLIN_STABLE);
        //PIGLING
        add(LootTables.PIGLIN_BARTERING);

    }};
    public static List<LootTables> END_STRUCTURE_CHESTS=new ArrayList<>(){{
        add(LootTables.END_CITY_TREASURE);
        //till 1.20.4
    }};
    public static List<LootTables> OVERWORLD_STRUCTURE_CHESTS=new ArrayList<>(){{
        Set<LootTables> set=new HashSet<>();
        set.addAll(LOOTTABLES_TYPES.getOrDefault("chests",new HashSet<>()));
        set.addAll(LOOTTABLES_TYPES.getOrDefault("archaeology",new HashSet<>()));
        set.addAll(LOOTTABLES_TYPES.getOrDefault("spawners",new HashSet<>()));
        set.removeAll(NETHER_STRUCTURE_CHESTS);
        set.removeAll(END_STRUCTURE_CHESTS);
        addAll(set);
    }};
    public static EnumSet<Material> BLOCKTYPE_WITH_ENTITY=EnumSet.copyOf( new HashSet<Material>(){{
        for (Material material:Material.values()){
            if(material.isBlock()){
                try{
                    if(material.createBlockData().createBlockState() instanceof TileState){
                        add(material);
                    }
                }catch (Throwable ignored){

                }
            }
        }
        //till 1.20.4
    }}
    );
    public static EnumSet<Material> BLOCKTYPE_WITH_NONNULL_TICKER=EnumSet.copyOf(
            new HashSet<Material>(){{
        add(Material.DROPPER);
        add(Material.SCULK_CATALYST);
        add(Material.DISPENSER);
        add(Material.SOUL_CAMPFIRE);
        add(Material.FURNACE);
        add(Material.BEACON);
        add(Material.BLAST_FURNACE);
        add(Material.MOVING_PISTON);
        add(Material.DAYLIGHT_DETECTOR);
        add(Material.DECORATED_POT);
        add(Material.SPAWNER);
        add(Material.BREWING_STAND);
        add(Material.ENCHANTING_TABLE);
        add(Material.LECTERN);
        add(Material.CALIBRATED_SCULK_SENSOR);
        add(Material.HOPPER);
        add(Material.END_PORTAL);
        add(Material.CONDUIT);
        add(Material.BELL);
        add(Material.END_GATEWAY);
        add(Material.SMOKER);
        add(Material.CHISELED_BOOKSHELF);
        add(Material.BEE_NEST);
        add(Material.SCULK_SENSOR);
        add(Material.JUKEBOX);
        add(Material.COMMAND_BLOCK);
        add(Material.REPEATING_COMMAND_BLOCK);
        add(Material.BEEHIVE);
        add(Material.CHAIN_COMMAND_BLOCK);
        add(Material.CAMPFIRE);
        add(Material.SCULK_SHRIEKER);
        //till 1.20.4
    }}
    );
    public static EnumSet<Material> WATER_VARIENT=EnumSet.of(Material.WATER,Material.BUBBLE_COLUMN);
     //till 1.20.4
    public static EnumSet<Material> BLOCK_MUST_WATERLOGGED=EnumSet.of(Material.SEAGRASS,Material.TALL_SEAGRASS,Material.KELP_PLANT,Material.KELP);
    public static EnumSet<Material> BLOCK_WITH_RANDOMTICK=EnumSet.copyOf(

      new HashSet<Material>(){{
        add(Material.GRASS_BLOCK);
        add(Material.CARROTS);
        add(Material.CUT_COPPER);
        add(Material.POINTED_DRIPSTONE);
        add(Material.KELP);
        add(Material.DEEPSLATE_REDSTONE_ORE);
        add(Material.NETHER_WART);
        add(Material.OAK_SAPLING);
        add(Material.COPPER_BLOCK);
        add(Material.CUT_COPPER_STAIRS);
        add(Material.FARMLAND);
        add(Material.EXPOSED_CUT_COPPER_SLAB);
        add(Material.CHERRY_SAPLING);
        add(Material.SUGAR_CANE);
        add(Material.PUMPKIN_STEM);
        add(Material.LAVA);
        add(Material.BAMBOO);
        add(Material.EXPOSED_CUT_COPPER);
        add(Material.ICE);
        add(Material.TORCHFLOWER_CROP);
        add(Material.WEATHERED_CUT_COPPER_SLAB);
        add(Material.FLOWERING_AZALEA_LEAVES);
        add(Material.VINE);
        add(Material.WEEPING_VINES);
        add(Material.DARK_OAK_LEAVES);
        add(Material.COCOA);
        add(Material.CHORUS_FLOWER);
        add(Material.SPRUCE_LEAVES);
        add(Material.MANGROVE_LEAVES);
        add(Material.CRIMSON_NYLIUM);
        add(Material.ACACIA_LEAVES);
        add(Material.ACACIA_SAPLING);
        add(Material.SNOW);
        add(Material.LARGE_AMETHYST_BUD);
        add(Material.SPRUCE_SAPLING);
        add(Material.MYCELIUM);
        add(Material.TURTLE_EGG);
        add(Material.MELON_STEM);
        add(Material.EXPOSED_COPPER);
        add(Material.CAVE_VINES);
        add(Material.BIRCH_LEAVES);
        add(Material.SMALL_AMETHYST_BUD);
        add(Material.WEATHERED_CUT_COPPER);
        add(Material.OAK_LEAVES);
        add(Material.AMETHYST_CLUSTER);
        add(Material.WEATHERED_COPPER);
        add(Material.EXPOSED_CUT_COPPER_STAIRS);
        add(Material.DARK_OAK_SAPLING);
        add(Material.RED_MUSHROOM);
        add(Material.REDSTONE_ORE);
        add(Material.MANGROVE_PROPAGULE);
        add(Material.BEETROOTS);
        add(Material.POTATOES);
        add(Material.MANGROVE_ROOTS);
        add(Material.WEATHERED_CUT_COPPER_STAIRS);
        add(Material.CACTUS);
        add(Material.BROWN_MUSHROOM);
        add(Material.NETHER_PORTAL);
        add(Material.CHERRY_LEAVES);
        add(Material.PITCHER_CROP);
        add(Material.BIRCH_SAPLING);
        add(Material.JUNGLE_LEAVES);
        add(Material.MEDIUM_AMETHYST_BUD);
        add(Material.CUT_COPPER_SLAB);
        add(Material.FROSTED_ICE);
        add(Material.AZALEA_LEAVES);
        add(Material.TWISTING_VINES);
        add(Material.WHEAT);
        add(Material.SWEET_BERRY_BUSH);
        add(Material.WARPED_NYLIUM);
        add(Material.BUDDING_AMETHYST);
        add(Material.JUNGLE_SAPLING);
        add(Material.BAMBOO_SAPLING);
        //till 1.20.4

    }}
    );
    public static EnumSet<Material> BLOCK_WITH_MULTI_BLOCKSTATE=EnumSet.copyOf(
     new HashSet<Material>(){{
        add(Material.TRIPWIRE_HOOK);
        add(Material.REPEATING_COMMAND_BLOCK);
        add(Material.COMPOSTER);
        add(Material.PEARLESCENT_FROGLIGHT);
        add(Material.STRIPPED_ACACIA_LOG);
        add(Material.PURPUR_SLAB);
        add(Material.PURPLE_BED);
        add(Material.DARK_OAK_SIGN);
        add(Material.MOSSY_STONE_BRICK_WALL);
        add(Material.HORN_CORAL);
        add(Material.BAMBOO_PRESSURE_PLATE);
        add(Material.EXPOSED_CUT_COPPER_SLAB);
        add(Material.CHERRY_SAPLING);
        add(Material.SOUL_LANTERN);
        add(Material.OAK_TRAPDOOR);
        add(Material.TUBE_CORAL_WALL_FAN);
        add(Material.BAMBOO);
        add(Material.BIRCH_WOOD);
        add(Material.DEAD_BRAIN_CORAL_FAN);
        add(Material.TUBE_CORAL_FAN);
        add(Material.DEEPSLATE_TILE_SLAB);
        add(Material.MANGROVE_LOG);
        add(Material.WATER_CAULDRON);
        add(Material.BONE_BLOCK);
        add(Material.POLISHED_DEEPSLATE_WALL);
        add(Material.COCOA);
        add(Material.ANVIL);
        add(Material.MAGENTA_CANDLE_CAKE);
        add(Material.BEE_NEST);
        add(Material.SCULK_SHRIEKER);
        add(Material.DRAGON_HEAD);
        add(Material.BRAIN_CORAL_FAN);
        add(Material.BROWN_CANDLE);
        add(Material.CHERRY_DOOR);
        add(Material.LIGHT_BLUE_CANDLE_CAKE);
        add(Material.SHULKER_BOX);
        add(Material.YELLOW_SHULKER_BOX);
        add(Material.RAIL);
        add(Material.NETHER_BRICK_WALL);
        add(Material.CAVE_VINES_PLANT);
        add(Material.WHITE_SHULKER_BOX);
        add(Material.ACACIA_FENCE);
        add(Material.GRANITE_WALL);
        add(Material.PURPUR_PILLAR);
        add(Material.POLISHED_DIORITE_SLAB);
        add(Material.OAK_LEAVES);
        add(Material.DEAD_BRAIN_CORAL);
        add(Material.BLACK_STAINED_GLASS_PANE);
        add(Material.LIGHT_GRAY_CANDLE_CAKE);
        add(Material.MANGROVE_WALL_SIGN);
        add(Material.SMOKER);
        add(Material.OXIDIZED_CUT_COPPER_STAIRS);
        add(Material.NOTE_BLOCK);
        add(Material.LECTERN);
        add(Material.CACTUS);
        add(Material.MUD_BRICK_STAIRS);
        add(Material.STRIPPED_DARK_OAK_WOOD);
        add(Material.CHAIN_COMMAND_BLOCK);
        add(Material.JIGSAW);
        add(Material.FIRE);
        add(Material.WARPED_HANGING_SIGN);
        add(Material.PLAYER_WALL_HEAD);
        add(Material.STRIPPED_WARPED_STEM);
        add(Material.WARPED_BUTTON);
        add(Material.STRUCTURE_BLOCK);
        add(Material.DIORITE_SLAB);
        add(Material.DARK_OAK_WALL_SIGN);
        add(Material.POLISHED_BLACKSTONE_BRICK_STAIRS);
        add(Material.LIGHT_BLUE_BANNER);
        add(Material.FROSTED_ICE);
        add(Material.TNT);
        add(Material.RED_SHULKER_BOX);
        add(Material.BIRCH_HANGING_SIGN);
        add(Material.BUBBLE_COLUMN);
        add(Material.MAGENTA_BED);
        add(Material.WHEAT);
        add(Material.ACACIA_LOG);
        add(Material.SWEET_BERRY_BUSH);
        add(Material.JUNGLE_SAPLING);
        add(Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
        add(Material.CHERRY_BUTTON);
        add(Material.COMMAND_BLOCK);
        add(Material.BIRCH_FENCE_GATE);
        add(Material.MAGENTA_WALL_BANNER);
        add(Material.STRIPPED_WARPED_HYPHAE);
        add(Material.ORANGE_GLAZED_TERRACOTTA);
        add(Material.BLACK_CANDLE_CAKE);
        add(Material.GREEN_GLAZED_TERRACOTTA);
        add(Material.LIGHTNING_ROD);
        add(Material.TUBE_CORAL);
        add(Material.PITCHER_PLANT);
        add(Material.DEEPSLATE_REDSTONE_ORE);
        add(Material.JUNGLE_FENCE_GATE);
        add(Material.WARPED_STEM);
        add(Material.BRAIN_CORAL);
        add(Material.GREEN_BANNER);
        add(Material.PEONY);
        add(Material.DEEPSLATE_BRICK_SLAB);
        add(Material.POWDER_SNOW_CAULDRON);
        add(Material.RESPAWN_ANCHOR);
        add(Material.CHERRY_WOOD);
        add(Material.SKELETON_SKULL);
        add(Material.OXIDIZED_CUT_COPPER_SLAB);
        add(Material.MANGROVE_WALL_HANGING_SIGN);
        add(Material.CUT_SANDSTONE_SLAB);
        add(Material.CYAN_GLAZED_TERRACOTTA);
        add(Material.HANGING_ROOTS);
        add(Material.CHEST);
        add(Material.SNOW);
        add(Material.RED_SANDSTONE_STAIRS);
        add(Material.PURPLE_BANNER);
        add(Material.DEEPSLATE_TILE_STAIRS);
        add(Material.WITHER_SKELETON_SKULL);
        add(Material.LIGHT_GRAY_WALL_BANNER);
        add(Material.FURNACE);
        add(Material.MELON_STEM);
        add(Material.POLISHED_DEEPSLATE_STAIRS);
        add(Material.CRIMSON_DOOR);
        add(Material.STONE_BRICK_WALL);
        add(Material.IRON_DOOR);
        add(Material.STONE_BRICK_STAIRS);
        add(Material.GLOW_LICHEN);
        add(Material.BLACK_WALL_BANNER);
        add(Material.QUARTZ_SLAB);
        add(Material.COBBLED_DEEPSLATE_STAIRS);
        add(Material.BLACKSTONE_SLAB);
        add(Material.BLUE_CANDLE);
        add(Material.MAGENTA_GLAZED_TERRACOTTA);
        add(Material.BAMBOO_TRAPDOOR);
        add(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
        add(Material.DARK_PRISMARINE_SLAB);
        add(Material.OAK_BUTTON);
        add(Material.WHITE_BANNER);
        add(Material.SUNFLOWER);
        add(Material.SMOOTH_QUARTZ_SLAB);
        add(Material.BASALT);
        add(Material.BUBBLE_CORAL_FAN);
        add(Material.SMOOTH_SANDSTONE_STAIRS);
        add(Material.CHERRY_SIGN);
        add(Material.WAXED_OXIDIZED_CUT_COPPER_STAIRS);
        add(Material.WAXED_EXPOSED_CUT_COPPER_SLAB);
        add(Material.STRIPPED_DARK_OAK_LOG);
        add(Material.NETHER_BRICK_STAIRS);
        add(Material.RED_BED);
        add(Material.GRAY_BANNER);
        add(Material.WHITE_BED);
        add(Material.MOSSY_STONE_BRICK_STAIRS);
        add(Material.RED_NETHER_BRICK_WALL);
        add(Material.FIRE_CORAL);
        add(Material.LIGHT_BLUE_BED);
        add(Material.IRON_TRAPDOOR);
        add(Material.DEAD_BUBBLE_CORAL_WALL_FAN);
        add(Material.WAXED_WEATHERED_CUT_COPPER_STAIRS);
        add(Material.SPRUCE_FENCE);
        add(Material.POWERED_RAIL);
        add(Material.END_ROD);
        add(Material.AZALEA_LEAVES);
        add(Material.ACACIA_WALL_HANGING_SIGN);
        add(Material.MANGROVE_SLAB);
        add(Material.PISTON);
        add(Material.SPRUCE_WALL_SIGN);
        add(Material.SANDSTONE_STAIRS);
        add(Material.BAMBOO_WALL_HANGING_SIGN);
        add(Material.PRISMARINE_WALL);
        add(Material.HORN_CORAL_FAN);
        add(Material.GREEN_SHULKER_BOX);
        add(Material.STONE_STAIRS);
        add(Material.KELP);
        add(Material.ACACIA_BUTTON);
        add(Material.LIGHT);
        add(Material.LIME_CANDLE);
        add(Material.BIRCH_TRAPDOOR);
        add(Material.CHERRY_FENCE);
        add(Material.FARMLAND);
        add(Material.MANGROVE_HANGING_SIGN);
        add(Material.OAK_FENCE);
        add(Material.PUMPKIN_STEM);
        add(Material.LAVA);
        add(Material.OAK_SLAB);
        add(Material.CUT_RED_SANDSTONE_SLAB);
        add(Material.ACACIA_WALL_SIGN);
        add(Material.DARK_OAK_LEAVES);
        add(Material.CRIMSON_BUTTON);
        add(Material.MANGROVE_LEAVES);
        add(Material.ORANGE_WALL_BANNER);
        add(Material.ORANGE_STAINED_GLASS_PANE);
        add(Material.BIRCH_DOOR);
        add(Material.BIRCH_WALL_SIGN);
        add(Material.TRAPPED_CHEST);
        add(Material.JUNGLE_FENCE);
        add(Material.OAK_DOOR);
        add(Material.DEEPSLATE_TILE_WALL);
        add(Material.SPRUCE_PRESSURE_PLATE);
        add(Material.LIME_BED);
        add(Material.LIME_CANDLE_CAKE);
        add(Material.BROWN_BED);
        add(Material.SMOOTH_RED_SANDSTONE_STAIRS);
        add(Material.POLISHED_BLACKSTONE_WALL);
        add(Material.COMPARATOR);
        add(Material.PURPLE_STAINED_GLASS_PANE);
        add(Material.FIRE_CORAL_FAN);
        add(Material.SPRUCE_WOOD);
        add(Material.MAGENTA_BANNER);
        add(Material.BLUE_BANNER);
        add(Material.WARPED_FENCE_GATE);
        add(Material.BEEHIVE);
        add(Material.SANDSTONE_SLAB);
        add(Material.STRIPPED_OAK_LOG);
        add(Material.PURPLE_WALL_BANNER);
        add(Material.LADDER);
        add(Material.REDSTONE_ORE);
        add(Material.CRIMSON_FENCE_GATE);
        add(Material.BIRCH_SIGN);
        add(Material.RED_SANDSTONE_SLAB);
        add(Material.HAY_BLOCK);
        add(Material.BROWN_CANDLE_CAKE);
        add(Material.SUSPICIOUS_SAND);
        add(Material.BLACK_BANNER);
        add(Material.WITHER_SKELETON_WALL_SKULL);
        add(Material.STRIPPED_JUNGLE_WOOD);
        add(Material.CRIMSON_STEM);
        add(Material.BIRCH_SAPLING);
        add(Material.MANGROVE_BUTTON);
        add(Material.RED_NETHER_BRICK_STAIRS);
        add(Material.QUARTZ_PILLAR);
        add(Material.OAK_WALL_HANGING_SIGN);
        add(Material.WATER);
        add(Material.GRAY_BED);
        add(Material.STRIPPED_CRIMSON_STEM);
        add(Material.STRIPPED_CHERRY_WOOD);
        add(Material.BAMBOO_BUTTON);
        add(Material.ZOMBIE_HEAD);
        add(Material.SMOOTH_STONE_SLAB);
        add(Material.DEAD_TUBE_CORAL_WALL_FAN);
        add(Material.PINK_BANNER);
        add(Material.TALL_SEAGRASS);
        add(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        add(Material.JUKEBOX);
        add(Material.POLISHED_GRANITE_SLAB);
        add(Material.CAMPFIRE);
        add(Material.STRIPPED_BIRCH_WOOD);
        add(Material.TRIPWIRE);
        add(Material.ZOMBIE_WALL_HEAD);
        add(Material.PIGLIN_WALL_HEAD);
        add(Material.RED_CANDLE);
        add(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE);
        add(Material.BAMBOO_FENCE_GATE);
        add(Material.SPRUCE_WALL_HANGING_SIGN);
        add(Material.OAK_PRESSURE_PLATE);
        add(Material.LARGE_FERN);
        add(Material.MANGROVE_FENCE_GATE);
        add(Material.ANDESITE_STAIRS);
        add(Material.SPRUCE_STAIRS);
        add(Material.POINTED_DRIPSTONE);
        add(Material.MANGROVE_FENCE);
        add(Material.MUSHROOM_STEM);
        add(Material.BRICK_WALL);
        add(Material.BAMBOO_DOOR);
        add(Material.OAK_SAPLING);
        add(Material.BIRCH_STAIRS);
        add(Material.COBBLED_DEEPSLATE_SLAB);
        add(Material.DARK_OAK_HANGING_SIGN);
        add(Material.BAMBOO_MOSAIC_SLAB);
        add(Material.OAK_HANGING_SIGN);
        add(Material.ROSE_BUSH);
        add(Material.CHERRY_LOG);
        add(Material.ACTIVATOR_RAIL);
        add(Material.VINE);
        add(Material.ENDER_CHEST);
        add(Material.DEAD_TUBE_CORAL);
        add(Material.YELLOW_BANNER);
        add(Material.CYAN_BED);
        add(Material.REDSTONE_LAMP);
        add(Material.MANGROVE_DOOR);
        add(Material.LIME_GLAZED_TERRACOTTA);
        add(Material.BROWN_SHULKER_BOX);
        add(Material.POLISHED_BLACKSTONE_SLAB);
        add(Material.JUNGLE_WALL_HANGING_SIGN);
        add(Material.LARGE_AMETHYST_BUD);
        add(Material.COBBLED_DEEPSLATE_WALL);
        add(Material.DARK_OAK_STAIRS);
        add(Material.ACACIA_PRESSURE_PLATE);
        add(Material.MANGROVE_SIGN);
        add(Material.CARVED_PUMPKIN);
        add(Material.BIG_DRIPLEAF_STEM);
        add(Material.ATTACHED_PUMPKIN_STEM);
        add(Material.ANDESITE_WALL);
        add(Material.MANGROVE_WOOD);
        add(Material.ACACIA_FENCE_GATE);
        add(Material.LIME_STAINED_GLASS_PANE);
        add(Material.DARK_OAK_DOOR);
        add(Material.MOSSY_COBBLESTONE_STAIRS);
        add(Material.ATTACHED_MELON_STEM);
        add(Material.MANGROVE_PRESSURE_PLATE);
        add(Material.YELLOW_WALL_BANNER);
        add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
        add(Material.POTATOES);
        add(Material.POLISHED_BLACKSTONE_STAIRS);
        add(Material.MANGROVE_ROOTS);
        add(Material.POLISHED_BLACKSTONE_BRICK_SLAB);
        add(Material.MAGENTA_CANDLE);
        add(Material.JUNGLE_DOOR);
        add(Material.WHITE_CANDLE_CAKE);
        add(Material.GRAY_CANDLE_CAKE);
        add(Material.JUNGLE_PRESSURE_PLATE);
        add(Material.BLUE_CANDLE_CAKE);
        add(Material.JUNGLE_SLAB);
        add(Material.LILAC);
        add(Material.PRISMARINE_BRICK_SLAB);
        add(Material.LANTERN);
        add(Material.GRAY_STAINED_GLASS_PANE);
        add(Material.MEDIUM_AMETHYST_BUD);
        add(Material.BIRCH_WALL_HANGING_SIGN);
        add(Material.GREEN_CANDLE);
        add(Material.DEAD_HORN_CORAL);
        add(Material.SCULK_VEIN);
        add(Material.DEEPSLATE);
        add(Material.ACACIA_HANGING_SIGN);
        add(Material.DAYLIGHT_DETECTOR);
        add(Material.BIRCH_SLAB);
        add(Material.SMOOTH_SANDSTONE_SLAB);
        add(Material.SCULK_SENSOR);
        add(Material.BROWN_WALL_BANNER);
        add(Material.RED_BANNER);
        add(Material.JUNGLE_LOG);
        add(Material.GREEN_BED);
        add(Material.ACACIA_SIGN);
        add(Material.BAMBOO_SLAB);
        add(Material.PURPUR_STAIRS);
        add(Material.CHAIN);
        add(Material.POLISHED_DIORITE_STAIRS);
        add(Material.CONDUIT);
        add(Material.LIGHT_GRAY_CANDLE);
        add(Material.SCAFFOLDING);
        add(Material.OAK_FENCE_GATE);
        add(Material.POLISHED_BASALT);
        add(Material.GRAY_GLAZED_TERRACOTTA);
        add(Material.OAK_LOG);
        add(Material.WARPED_WALL_HANGING_SIGN);
        add(Material.SUGAR_CANE);
        add(Material.CRIMSON_HYPHAE);
        add(Material.RED_GLAZED_TERRACOTTA);
        add(Material.TORCHFLOWER_CROP);
        add(Material.WARPED_DOOR);
        add(Material.WAXED_EXPOSED_CUT_COPPER_STAIRS);
        add(Material.STRIPPED_SPRUCE_LOG);
        add(Material.JUNGLE_WALL_SIGN);
        add(Material.WEEPING_VINES);
        add(Material.FIRE_CORAL_WALL_FAN);
        add(Material.LIGHT_BLUE_WALL_BANNER);
        add(Material.CRIMSON_WALL_HANGING_SIGN);
        add(Material.BLACK_SHULKER_BOX);
        add(Material.ACACIA_LEAVES);
        add(Material.DARK_OAK_FENCE_GATE);
        add(Material.RED_CANDLE_CAKE);
        add(Material.BRICK_SLAB);
        add(Material.ACACIA_DOOR);
        add(Material.END_PORTAL_FRAME);
        add(Material.CHERRY_SLAB);
        add(Material.WARPED_HYPHAE);
        add(Material.VERDANT_FROGLIGHT);
        add(Material.ACACIA_STAIRS);
        add(Material.SKELETON_WALL_SKULL);
        add(Material.BROWN_MUSHROOM_BLOCK);
        add(Material.POLISHED_ANDESITE_STAIRS);
        add(Material.CAVE_VINES);
        add(Material.WHITE_CANDLE);
        add(Material.PIGLIN_HEAD);
        add(Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
        add(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        add(Material.CHERRY_STAIRS);
        add(Material.CRIMSON_SIGN);
        add(Material.SMALL_AMETHYST_BUD);
        add(Material.WARPED_SIGN);
        add(Material.BLAST_FURNACE);
        add(Material.SPRUCE_HANGING_SIGN);
        add(Material.MUD_BRICK_SLAB);
        add(Material.SNIFFER_EGG);
        add(Material.BLUE_GLAZED_TERRACOTTA);
        add(Material.WAXED_CUT_COPPER_SLAB);
        add(Material.BEETROOTS);
        add(Material.BLACKSTONE_STAIRS);
        add(Material.PINK_BED);
        add(Material.NETHER_PORTAL);
        add(Material.MUD_BRICK_WALL);
        add(Material.CREEPER_WALL_HEAD);
        add(Material.DARK_OAK_BUTTON);
        add(Material.BIRCH_PRESSURE_PLATE);
        add(Material.YELLOW_CANDLE);
        add(Material.PITCHER_CROP);
        add(Material.BROWN_GLAZED_TERRACOTTA);
        add(Material.JUNGLE_SIGN);
        add(Material.SPRUCE_BUTTON);
        add(Material.STRIPPED_SPRUCE_WOOD);
        add(Material.JUNGLE_LEAVES);
        add(Material.STRIPPED_CRIMSON_HYPHAE);
        add(Material.BAMBOO_FENCE);
        add(Material.PRISMARINE_SLAB);
        add(Material.CHORUS_PLANT);
        add(Material.ACACIA_TRAPDOOR);
        add(Material.DECORATED_POT);
        add(Material.GREEN_WALL_BANNER);
        add(Material.DEAD_HORN_CORAL_WALL_FAN);
        add(Material.SCULK_CATALYST);
        add(Material.BAMBOO_WALL_SIGN);
        add(Material.RED_STAINED_GLASS_PANE);
        add(Material.BLACK_GLAZED_TERRACOTTA);
        add(Material.DEEPSLATE_BRICK_WALL);
        add(Material.INFESTED_DEEPSLATE);
        add(Material.BAMBOO_BLOCK);
        add(Material.CRIMSON_HANGING_SIGN);
        add(Material.RED_MUSHROOM_BLOCK);
        add(Material.PLAYER_HEAD);
        add(Material.HOPPER);
        add(Material.SPRUCE_LOG);
        add(Material.YELLOW_BED);
        add(Material.STRIPPED_CHERRY_LOG);
        add(Material.SPRUCE_FENCE_GATE);
        add(Material.GRASS_BLOCK);
        add(Material.DEAD_HORN_CORAL_FAN);
        add(Material.BIG_DRIPLEAF);
        add(Material.POLISHED_GRANITE_STAIRS);
        add(Material.RED_NETHER_BRICK_SLAB);
        add(Material.YELLOW_GLAZED_TERRACOTTA);
        add(Material.WARPED_SLAB);
        add(Material.IRON_BARS);
        add(Material.BIRCH_FENCE);
        add(Material.JUNGLE_HANGING_SIGN);
        add(Material.ACACIA_SLAB);
        add(Material.OAK_SIGN);
        add(Material.YELLOW_CANDLE_CAKE);
        add(Material.ACACIA_WOOD);
        add(Material.SEA_PICKLE);
        add(Material.BLACK_BED);
        add(Material.DARK_OAK_PRESSURE_PLATE);
        add(Material.WARPED_WALL_SIGN);
        add(Material.COBBLESTONE_STAIRS);
        add(Material.PINK_CANDLE_CAKE);
        add(Material.SUSPICIOUS_GRAVEL);
        add(Material.REDSTONE_WIRE);
        add(Material.CHIPPED_ANVIL);
        add(Material.TALL_GRASS);
        add(Material.CHORUS_FLOWER);
        add(Material.DEAD_BRAIN_CORAL_WALL_FAN);
        add(Material.CHERRY_FENCE_GATE);
        add(Material.SPRUCE_DOOR);
        add(Material.POLISHED_DEEPSLATE_SLAB);
        add(Material.RED_WALL_BANNER);
        add(Material.DEAD_BUBBLE_CORAL_FAN);
        add(Material.DEAD_FIRE_CORAL);
        add(Material.ACACIA_SAPLING);
        add(Material.NETHER_BRICK_FENCE);
        add(Material.SOUL_CAMPFIRE);
        add(Material.JUNGLE_BUTTON);
        add(Material.CHERRY_TRAPDOOR);
        add(Material.SPRUCE_SAPLING);
        add(Material.COBBLESTONE_WALL);
        add(Material.MAGENTA_SHULKER_BOX);
        add(Material.DARK_OAK_WALL_HANGING_SIGN);
        add(Material.DEEPSLATE_BRICK_STAIRS);
        add(Material.BLUE_BED);
        add(Material.POLISHED_BLACKSTONE_BUTTON);
        add(Material.STONECUTTER);
        add(Material.JACK_O_LANTERN);
        add(Material.STRIPPED_OAK_WOOD);
        add(Material.MANGROVE_TRAPDOOR);
        add(Material.POLISHED_BLACKSTONE_BRICK_WALL);
        add(Material.PETRIFIED_OAK_SLAB);
        add(Material.DARK_OAK_SAPLING);
        add(Material.CYAN_WALL_BANNER);
        add(Material.HORN_CORAL_WALL_FAN);
        add(Material.DEAD_BUBBLE_CORAL);
        add(Material.LIGHT_BLUE_CANDLE);
        add(Material.STRIPPED_MANGROVE_LOG);
        add(Material.SOUL_WALL_TORCH);
        add(Material.ORANGE_CANDLE_CAKE);
        add(Material.STRIPPED_JUNGLE_LOG);
        add(Material.RED_SANDSTONE_WALL);
        add(Material.LIGHT_GRAY_BED);
        add(Material.PURPLE_CANDLE_CAKE);
        add(Material.CHERRY_WALL_HANGING_SIGN);
        add(Material.CUT_COPPER_SLAB);
        add(Material.BIRCH_BUTTON);
        add(Material.SANDSTONE_WALL);
        add(Material.DARK_OAK_SLAB);
        add(Material.SPRUCE_SLAB);
        add(Material.DARK_OAK_WOOD);
        add(Material.ORANGE_SHULKER_BOX);
        add(Material.BAMBOO_HANGING_SIGN);
        add(Material.BRAIN_CORAL_WALL_FAN);
        add(Material.SPRUCE_SIGN);
        add(Material.CHERRY_HANGING_SIGN);
        add(Material.WAXED_OXIDIZED_CUT_COPPER_SLAB);
        add(Material.LIGHT_BLUE_SHULKER_BOX);
        add(Material.CAKE);
        add(Material.DARK_OAK_FENCE);
        add(Material.CARROTS);
        add(Material.CANDLE_CAKE);
        add(Material.DARK_OAK_LOG);
        add(Material.STRIPPED_BIRCH_LOG);
        add(Material.DEAD_FIRE_CORAL_FAN);
        add(Material.LIME_SHULKER_BOX);
        add(Material.WHITE_WALL_BANNER);
        add(Material.GRAY_CANDLE);
        add(Material.WARPED_PRESSURE_PLATE);
        add(Material.CRIMSON_STAIRS);
        add(Material.WALL_TORCH);
        add(Material.CHERRY_WALL_SIGN);
        add(Material.PRISMARINE_STAIRS);
        add(Material.CUT_COPPER_STAIRS);
        add(Material.DAMAGED_ANVIL);
        add(Material.MOSSY_STONE_BRICK_SLAB);
        add(Material.WEATHERED_CUT_COPPER_SLAB);
        add(Material.FLOWERING_AZALEA_LEAVES);
        add(Material.PURPLE_GLAZED_TERRACOTTA);
        add(Material.ORANGE_BANNER);
        add(Material.SPRUCE_LEAVES);
        add(Material.MOVING_PISTON);
        add(Material.BLUE_STAINED_GLASS_PANE);
        add(Material.BAMBOO_SIGN);
        add(Material.STONE_BUTTON);
        add(Material.BLACKSTONE_WALL);
        add(Material.CRIMSON_PRESSURE_PLATE);
        add(Material.REDSTONE_TORCH);
        add(Material.DARK_PRISMARINE_STAIRS);
        add(Material.ORANGE_CANDLE);
        add(Material.BREWING_STAND);
        add(Material.BIRCH_LEAVES);
        add(Material.CYAN_SHULKER_BOX);
        add(Material.OAK_WOOD);
        add(Material.STONE_BRICK_SLAB);
        add(Material.WAXED_WEATHERED_CUT_COPPER_SLAB);
        add(Material.SMALL_DRIPLEAF);
        add(Material.BIRCH_LOG);
        add(Material.SMOOTH_QUARTZ_STAIRS);
        add(Material.AMETHYST_CLUSTER);
        add(Material.EXPOSED_CUT_COPPER_STAIRS);
        add(Material.DETECTOR_RAIL);
        add(Material.ANDESITE_SLAB);
        add(Material.OAK_WALL_SIGN);
        add(Material.BAMBOO_MOSAIC_STAIRS);
        add(Material.CRIMSON_WALL_SIGN);
        add(Material.BAMBOO_STAIRS);
        add(Material.MUDDY_MANGROVE_ROOTS);
        add(Material.CYAN_BANNER);
        add(Material.WHITE_GLAZED_TERRACOTTA);
        add(Material.STICKY_PISTON);
        add(Material.DARK_OAK_TRAPDOOR);
        add(Material.PURPLE_SHULKER_BOX);
        add(Material.BARREL);
        add(Material.GRAY_WALL_BANNER);
        add(Material.CHISELED_BOOKSHELF);
        add(Material.MOSSY_COBBLESTONE_WALL);
        add(Material.STRIPPED_ACACIA_WOOD);
        add(Material.JUNGLE_STAIRS);
        add(Material.DEAD_FIRE_CORAL_WALL_FAN);
        add(Material.DRAGON_WALL_HEAD);
        add(Material.END_STONE_BRICK_SLAB);
        add(Material.SPRUCE_TRAPDOOR);
        add(Material.GRINDSTONE);
        add(Material.TWISTING_VINES);
        add(Material.GREEN_CANDLE_CAKE);
        add(Material.OCHRE_FROGLIGHT);
        add(Material.PINK_WALL_BANNER);
        add(Material.CREEPER_HEAD);
        add(Material.TARGET);
        add(Material.CANDLE);
        add(Material.PINK_CANDLE);
        add(Material.POLISHED_ANDESITE_SLAB);
        add(Material.CYAN_STAINED_GLASS_PANE);
        add(Material.MANGROVE_STAIRS);
        add(Material.WARPED_TRAPDOOR);
        add(Material.END_STONE_BRICK_WALL);
        add(Material.CYAN_CANDLE_CAKE);
        add(Material.LIME_BANNER);
        add(Material.END_STONE_BRICK_STAIRS);
        add(Material.CRIMSON_TRAPDOOR);
        add(Material.PINK_GLAZED_TERRACOTTA);
        add(Material.DEAD_TUBE_CORAL_FAN);
        add(Material.STONE_SLAB);
        add(Material.COBBLESTONE_SLAB);
        add(Material.BLUE_SHULKER_BOX);
        add(Material.CALIBRATED_SCULK_SENSOR);
        add(Material.LIME_WALL_BANNER);
        add(Material.NETHER_WART);
        add(Material.REDSTONE_WALL_TORCH);
        add(Material.OBSERVER);
        add(Material.PRISMARINE_BRICK_STAIRS);
        add(Material.JUNGLE_TRAPDOOR);
        add(Material.STRIPPED_BAMBOO_BLOCK);
        add(Material.STONE_PRESSURE_PLATE);
        add(Material.SMOOTH_RED_SANDSTONE_SLAB);
        add(Material.STRIPPED_MANGROVE_WOOD);
        add(Material.BROWN_STAINED_GLASS_PANE);
        add(Material.LIGHT_GRAY_SHULKER_BOX);
        add(Material.GRANITE_SLAB);
        add(Material.MYCELIUM);
        add(Material.TURTLE_EGG);
        add(Material.PINK_PETALS);
        add(Material.WARPED_FENCE);
        add(Material.JUNGLE_WOOD);
        add(Material.DISPENSER);
        add(Material.PURPLE_CANDLE);
        add(Material.BLACK_CANDLE);
        add(Material.GRANITE_STAIRS);
        add(Material.OAK_STAIRS);
        add(Material.PINK_SHULKER_BOX);
        add(Material.MOSSY_COBBLESTONE_SLAB);
        add(Material.PINK_STAINED_GLASS_PANE);
        add(Material.GRAY_SHULKER_BOX);
        add(Material.DIORITE_WALL);
        add(Material.PODZOL);
        add(Material.QUARTZ_STAIRS);
        add(Material.BROWN_BANNER);
        add(Material.CYAN_CANDLE);
        add(Material.YELLOW_STAINED_GLASS_PANE);
        add(Material.MAGENTA_STAINED_GLASS_PANE);
        add(Material.LOOM);
        add(Material.DROPPER);
        add(Material.CRIMSON_SLAB);
        add(Material.MANGROVE_PROPAGULE);
        add(Material.WEATHERED_CUT_COPPER_STAIRS);
        add(Material.WHITE_STAINED_GLASS_PANE);
        add(Material.LIGHT_GRAY_BANNER);
        add(Material.CRIMSON_FENCE);
        add(Material.BELL);
        add(Material.CHERRY_LEAVES);
        add(Material.PISTON_HEAD);
        add(Material.REPEATER);
        add(Material.GREEN_STAINED_GLASS_PANE);
        add(Material.BLUE_WALL_BANNER);
        add(Material.NETHER_BRICK_SLAB);
        add(Material.BRICK_STAIRS);
        add(Material.BUBBLE_CORAL);
        add(Material.ORANGE_BED);
        add(Material.WAXED_CUT_COPPER_STAIRS);
        add(Material.DIORITE_STAIRS);
        add(Material.WARPED_STAIRS);
        add(Material.GLASS_PANE);
        add(Material.LEVER);
        add(Material.BUBBLE_CORAL_WALL_FAN);
        add(Material.CHERRY_PRESSURE_PLATE);

        //till 1.20.4
    }}
    );

    public static EnumMap<Material,Material> ITEM_HAS_DIFFERENT_ID_BLOCK=new EnumMap<>(Material.class){{
        put(Material.STRING,Material.TRIPWIRE);
        put(Material.MELON_SEEDS,Material.MELON_STEM);
        put(Material.SWEET_BERRIES,Material.SWEET_BERRY_BUSH);
        put(Material.TORCHFLOWER_SEEDS,Material.TORCHFLOWER_CROP);
        put(Material.CARROT,Material.CARROTS);
        put(Material.PUMPKIN_SEEDS,Material.PUMPKIN_STEM);
        put(Material.BEETROOT_SEEDS,Material.BEETROOTS);
        put(Material.POTATO,Material.POTATOES);
        put(Material.PITCHER_POD,Material.PITCHER_CROP);
        put(Material.COCOA_BEANS,Material.COCOA);
        put(Material.REDSTONE,Material.REDSTONE_WIRE);
        put(Material.GLOW_BERRIES,Material.CAVE_VINES);
        put(Material.POWDER_SNOW_BUCKET,Material.POWDER_SNOW);
        put(Material.WHEAT_SEEDS,Material.WHEAT);
    }};
    public static EnumSet<Material> TOOLS_MATERIAL =EnumSet.copyOf(new HashSet<Material>(){{
        for (Material material:Material.values()){
            //tools
            if(material.isItem()&&material.getMaxDurability()>0){
                add(material);
            }
        }
        try{
            addAll(Tag.ITEMS_TOOLS.getValues());
        }catch (Throwable versionNoTag){        }
    }});
    public final static EnumSet<Material> HELMET_MATERIALS=EnumSet.noneOf(Material.class);
    public final static EnumSet<Material> CHESTPLATE_MATERIALS=EnumSet.noneOf(Material.class);
    public final static EnumSet<Material> LEGGINGS_MATERIALS=EnumSet.noneOf(Material.class);
    public final static EnumSet<Material> BOOTS_MATERIALS=EnumSet.noneOf(Material.class);
    static{
        for(Material material:Material.values()){
            if(material.isItem()){
                if(material.toString().endsWith("HELMET")){
                    Debug.debug("helmet ",material.toString());
                    HELMET_MATERIALS.add(material);
                }
                if(material.toString().endsWith("CHESTPLATE")){
                    Debug.debug("chestplate ",material.toString());
                    CHESTPLATE_MATERIALS.add(material);

                }
                if(material.toString().endsWith("LEGGINGS")){
                    Debug.debug("leggings ",material.toString());
                    LEGGINGS_MATERIALS.add(material);
                }
                if(material.toString().endsWith("BOOTS")){
                    Debug.debug("boots ",material.toString());
                    BOOTS_MATERIALS.add(material);
                }
            }
        }
        HELMET_MATERIALS.add(Material.TURTLE_HELMET);
        CHESTPLATE_MATERIALS.add(Material.ELYTRA);
    }
    @Nullable
    public static Material getBlock(Material material){
        return ITEM_HAS_DIFFERENT_ID_BLOCK.getOrDefault(material,(material.isBlock()?material:null));
    }
    public static Material getBlockOrAir(Material material){
        return ITEM_HAS_DIFFERENT_ID_BLOCK.getOrDefault(material,(material.isBlock()?material: Material.AIR));
    }
    public static boolean isBlock(Material material){
        return material.isBlock()||ITEM_HAS_DIFFERENT_ID_BLOCK.containsKey(material);
    }

    protected static Class CraftBlockStateClass;
//    protected static Field IBlockDataField;
//    protected static Field BlockPositionField;
//    protected static Field WorldField;
//    protected static Field WeakWorldField;
    protected static boolean invokeBlockStateSuccess=false;
    protected static FieldAccess iBlockDataFieldAccess;
    protected static FieldAccess blockPositionFieldAccess;
    protected static FieldAccess worldFieldAccess;
    protected static FieldAccess weakWorldFieldAccess;
    static {
        try{
            BlockState blockstate;
            try{
                blockstate=Material.STONE.createBlockData().createBlockState();
            }catch (Throwable versionTooEarlyError){
                World sampleWorld= Bukkit.getWorlds().get(0);
                Debug.debug(sampleWorld.getName());
                blockstate= sampleWorld.getBlockAt(0, 0, 0).getState();
            }
            var result=ReflectUtils.getDeclaredFieldsRecursively(blockstate.getClass(),"data");
            var IBlockDataField=result.getFirstValue();
           // Debug.logger(result.getSecondValue());
            IBlockDataField.setAccessible(true);
            iBlockDataFieldAccess= FieldAccess.of(IBlockDataField);
            CraftBlockStateClass=result.getSecondValue();
            var BlockPositionField=ReflectUtils.getDeclaredFieldsRecursively(CraftBlockStateClass,"position").getFirstValue();
            BlockPositionField.setAccessible(true);
            blockPositionFieldAccess=FieldAccess.of(BlockPositionField);
            var WorldField=ReflectUtils.getDeclaredFieldsRecursively(CraftBlockStateClass,"world").getFirstValue();
            WorldField.setAccessible(true);
            worldFieldAccess=FieldAccess.of(WorldField);
            var WeakWorldField=ReflectUtils.getDeclaredFieldsRecursively(CraftBlockStateClass,"weakWorld").getFirstValue();
            WeakWorldField.setAccessible(true);
            weakWorldFieldAccess=FieldAccess.of(WeakWorldField);
            Debug.debug("CraftBlockStateClass: "+CraftBlockStateClass.getName());
            invokeBlockStateSuccess=true;
        }catch (Throwable e){
           Debug.logger(e);
        }
    }
    public static void setup(){

    }
    public static enum DustPreset{
        REDSTONE(Color.fromRGB(255,0,0),1.0f);

        Particle.DustOptions dustOptions;
        public Particle.DustOptions getOptions(){
            return dustOptions;
        }
        DustPreset(Color color,float size){
            dustOptions=new Particle.DustOptions(color,size);
        }
    }
    public static HashSet<Material> WATER_LOGGABLE_TYPES=new HashSet<>(){{
        for (Material m : Material.values()) {
            if(isBlock(m)){
                try{

                    BlockData defaultData= getBlock(m).createBlockData();
                    if(defaultData instanceof Waterlogged){

                        add(m);
                    }
                }catch (Throwable e){
                    Debug.logger(e);
                }
            }
        }
    }};
    static{

    }

    public static BlockState getBlockStateNoSnapShot(Block block){
//        if(getStateHandle!=null){
//            try{
//                return (BlockState)getStateHandle.invokeExact(block,false);
//            }catch (Throwable ignored){}
//        }
        return block.getState(false);
    }








    public static boolean isEntityBlock(Material type){
        return BLOCKTYPE_WITH_ENTITY.contains(type);
    }
    public static boolean isRandomTickable(Material type){
        return BLOCK_WITH_RANDOMTICK.contains(type);
    }
    public static boolean isBlockLocation(Location location){
        if(location==null){
            return false;
        }
        return location.getX()==location.getBlockX() && location.getY()==location.getBlockY() && location.getZ()==location.getBlockZ();
    }


    public static Location getBlockLocation(Location loc){
        return new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY(),loc.getBlockZ());
    }
    /**
     * this method no need to run sync
     * @param loc
     * @param force
     */
    public static void removeSlimefunBlock(Location loc, boolean force){
        removeSlimefunBlock(loc,null,force);
    }
    public static void removeSlimefunBlock(Location loc,Player player, boolean force) {
       if(force){
           CONTROLLER.removeBlock(loc);
           return;
       }else {
           BlockBreakEvent breakEvent =
                   new BlockBreakEvent(loc.getBlock(),player);
           //set drop to false
           breakEvent.setDropItems(false);
           breakEvent.setExpToDrop(0);
           Bukkit.getPluginManager().callEvent(breakEvent);


           if (breakEvent.isCancelled()) {
               return ;
           }
           CONTROLLER.removeBlock(loc);
       }
    }
    public static void removeSlimefunBlockSafe(Location loc){
    }
    public static void moveSlimefunBlock(Location loc, boolean force) {

    }

    /**
     * put arg hasSync to decide whether to create new sunc thread
     * @param loc
     * @param player
     * @param item
     * @param material
     * @param force
     * @param hasSync
     */
    private static final ConcurrentHashMap<Location,SlimefunItem> CREATING_QUEUE = new ConcurrentHashMap<>();
    private static boolean abort =false;
    public static void abortCreatingQueue(){
        CREATING_QUEUE.clear();;
        abort  =true;
        Schedules.launchSchedules(()->abort = false,10,true,0);
    }
    public static boolean createSlimefunBlock(Location loc,Player player,SlimefunItem item,Material material,boolean force,Runnable callback){
        if(CREATING_QUEUE.containsKey(loc)){
            SlimefunItem item1 = CREATING_QUEUE.get(loc);
            if(item1==item){
                return false;
            }else {
                force=true;
            }
        }
        final boolean forceVal=force;
        CREATING_QUEUE.put(loc,item);
        BukkitUtils.executeSync(()->{
            try{
                createSlimefunBlockSync(loc,player,item,material,forceVal,callback);
            }finally {
                CREATING_QUEUE.remove(loc);
            }

        });
        return true;
    }
    public static boolean createSlimefunBlockSync(Location loc,Player player,SlimefunItem item,Material material,boolean force,Runnable callback){
        if(abort){
            return false;
        }
        Block block = loc.getBlock();
        if(!force&&player!=null){
            if(!hasPermission(player,loc, Interaction.PLACE_BLOCK)){
                return false ;
            }
//            if( DataCache.getSfItem(loc)!=null){
//               Debug.logger("has block ,,, ",DataCache.getSfItem(loc));
//            }
            BlockBreakEvent breakEvent =
                    new BlockBreakEvent(block,player);
            breakEvent.setDropItems(false);
            breakEvent.setExpToDrop(0);
            Bukkit.getPluginManager().callEvent(breakEvent);
            if (breakEvent.isCancelled()) {
                return false;
            }
            breakEvent.setDropItems(false);
            if (!item.canUse(player, false)) {
                return false;
            }

        }
        if( DataCache.getSfItem(loc)!=null){
            CONTROLLER.removeBlock(loc);
        }
         BukkitUtils.executeSync(()->    {
                     createSlimefunBlockSync(loc, player, item, material);
                     callback.run();
             }
         );
        return true;
    }
    /**
     * this method must run sync
     * @param loc
     * @param item
     * @param material
     */
    public static void createSlimefunBlockSync(Location loc,Player player,SlimefunItem item,Material material) {
        Block block=loc.getBlock();

        block.setType(material);
        if (Slimefun.getBlockDataService().isTileEntity(block.getType())) {
            Slimefun.getBlockDataService().setBlockData(block, item.getId());
        }
        CONTROLLER.createBlock(loc, item.getId());
//        try{
//            var placeEvent = new SlimefunBlockPlaceEvent(player, item.getItem(), block, item);
//            Bukkit.getPluginManager().callEvent(placeEvent);
//        }catch (Throwable e){
//
//        }
    }
    public static boolean hasPermission( Player player, @Nonnull Block location, @Nonnull Interaction... interactions) {
        if(player==null)return true;
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(player, location, interaction)) {
                return false;
            }
        }
        return true;
    }
    public static boolean hasPermission( Player player, @Nonnull Location location, @Nonnull Interaction... interactions) {
        if(player==null)return true;
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(player, location, interaction)) {
                return false;
            }
        }
        return true;
    }
    public static boolean testAttackPermission(Player player, Damageable entity,float expectedDamage){
        //entity.damage(0,player);
        try{
            EntityDamageEvent event=new AttackPermissionTestEvent(player,entity,expectedDamage);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled()){
                return false;
            }else return true;
        }catch (Throwable e){
            Debug.logger(e);
            return true;
        }

    }
    public static boolean consumeItem(Player player, ItemConsumer... consumers) {
        for (ItemConsumer consumer : consumers) {
            consumer.syncData();
        }
        ItemStack[] contents = player.getInventory().getContents();
        for(ItemStack item : contents){
            if(item==null||item.getType()==Material.AIR){
                continue;
            }else {
                ItemPusher pusher=null;
                for(ItemConsumer consumer : consumers){
                    if(consumer.getAmount()>0){
                        if(item.getType()==consumer.getItem().getType()){
                            if(pusher==null){
                                pusher=CraftUtils.getpusher.get(Settings.INPUT,item,-1);
                            }
                            if(CraftUtils.matchItemCore(pusher,consumer,false)){
                                consumer.consume(pusher);
                                break;
                            }
                        }else {
                            continue;
                        }

                    }
                }
            }
        }
        for(ItemConsumer consumer : consumers){
            if(consumer.getAmount()>0){
                for (ItemConsumer consumer2 : consumers) {
                    consumer2.syncData();
                }
                return false;
            }
        }
        for(ItemConsumer consumer : consumers){
            consumer.updateItems(null,Settings.GRAB);
        }
        return true;

    }
    /**
     * no need to sync
     * @param start
     * @param end

     * @param count
     */
    public static void doLineOperation(Location start,Location end,int count,Consumer<Location> task){
        if(count<=0)return;
        World world= start.getWorld();
        if(end.getWorld()!=world)return;
        if(count==1){
            task.accept(start);
        }else {
            Location walk=start.clone();
            double dx=(end.getX()-start.getX())/(count-1);
            double dy=(end.getY()-start.getY())/(count-1);
            double dz=(end.getZ()-start.getZ())/(count-1);
            for(int i=0;i<count;++i){
                task.accept(walk);
                walk.add(dx,dy,dz);
            }
        }
    }
    public static void spawnLineParticle(Location start, Location end, Particle type, int count){
        doLineOperation(start,end,count,(loc)->{
            loc.getWorld().spawnParticle(type,loc,0,0.0,0.0,0.0,1,null,false);
        });
    }
    private static final ItemStack effectivePickaxe = new ItemStack(Material.DIAMOND_PICKAXE);

//    public ItemStack[] simulateKill(){
//        Player player;
//    }
    //should be without movement
    public static int executeOnSameEntity(Entity entity, Consumer<Entity> execution){
        if(entity==null)return 0;
        return entity.getLocation().getChunk().getWorld().getNearbyEntities(entity.getLocation(),0.5,0.5,0.5,(entity1 -> {
            if(entity1!=null&&entity.getUniqueId().equals( entity1.getUniqueId())){
                execution.accept(entity1);
                return true;
            }
            return false;
        })).size();
    }
//    public static Vector getOrientations(Location loc){
//        loc.getDirection();
//    }
    public static HashSet<Entity> getEntityInDistance(Location location, double distance, Predicate<Entity> predicate){
        return new HashSet<Entity>( location.getWorld().getNearbyEntities(location,2*distance,2*distance,2*distance,(e)->{return e.getLocation().distance(location)<=distance&&predicate.test(e);}));
    }
    public static Pair<Integer,Location> rayTraceLocation(LivingEntity entity, double period,double  maxLimitedDistance, Predicate<Location> execution){
        if(entity==null)return null;
        return rayTraceLocation(entity.getLocation().getDirection(),entity.getEyeLocation(),period,maxLimitedDistance,execution);
    }
    public static Pair<Integer,Location> rayTraceLocation(Vector rayVector,Location startLocation, double period,double maxLimitedDistance, Predicate<Location> execution){
        rayVector= rayVector.normalize().multiply(period);
        Location walkLocation=startLocation.clone();
        int limitTryTime=(int)(maxLimitedDistance/period);
        int i=0;
        for(;i<limitTryTime;++i){
            walkLocation.add(rayVector);
            if(!execution.test(walkLocation)){
                break;
            }
        }
        return new Pair<Integer,Location>(i,walkLocation) ;
    }
    public static boolean isLightPassableBlock(Block block){
        Material material=block.getType();
        if(material.isAir()||material.isTransparent()||WATER_VARIENT.contains(material)||material==Material.LAVA){
            return true;
        }else return false;
    }
    public static boolean isLiquid(Block block){
        Material material=block.getType();
        if(WATER_VARIENT.contains(material)||material==Material.LAVA){
            return true;
        }else return false;
    }
    public static boolean isMaterialWaterLoggable(Material material){
        return WATER_LOGGABLE_TYPES.contains(material);
    }
    public static boolean isWaterLogged(Block block){
        Material type=block.getType();
        if(isMaterialWaterLoggable(type)){
            BlockData blockData=block.getBlockData();
            if(blockData instanceof Waterlogged wl){
                return wl.isWaterlogged();
            }else return false;
        }else{
            return false;
        }

    }
    //IF SF DATA EXISTS,SF BLOCK WILL ALSO BE BREAKED, MAY CAUSE PROBLEMS
    public static boolean breakVanillaBlockByPlayer(Block block,Player player,boolean hasCheckedProtection,boolean withDrop){
        if(hasCheckedProtection||WorldUtils.hasPermission(player,block.getLocation(),Interaction.BREAK_BLOCK)){
            if(!block.getType().isAir()){
                BlockBreakEvent event=new BlockBreakEvent(block,player);
                event.setDropItems(false);
                event.setExpToDrop(0);
                Bukkit.getPluginManager().callEvent(event);
                if(event.isCancelled()){
                    return false;
                }
                event.setDropItems(withDrop);
                block.setType(Material.AIR);
                return true;
            }else return true;
        }else return false;
    }
    public static boolean moveVanillaBlockByPlayer(Block block1,Block block2,Player player,boolean checkFromPerms,boolean checkToPerms,boolean hasCheckedProtection,boolean applyPhysics){
        if(hasCheckedProtection||((!checkFromPerms||WorldUtils.hasPermission(player,block1,Interaction.BREAK_BLOCK))&&(!checkToPerms||WorldUtils.hasPermission(player,block2,Interaction.PLACE_BLOCK)))){
            if(checkFromPerms){
                BlockBreakEvent event=new BlockBreakEvent(block1,player);
                event.setDropItems(false);
                event.setExpToDrop(0);
                Bukkit.getPluginManager().callEvent(event);
                if(event.isCancelled()){
                    return false;
                }
                event.setDropItems(false);
            }
            block2.setType(block1.getType());
            block2.setBlockData(block1.getBlockData(),applyPhysics);
            block1.setType(Material.AIR);
            return true;
        }else return false;
    }
    public static boolean testVanillaBlockBreakPermission(Block block,Player player,boolean hasCheckedProtection){
        if(hasCheckedProtection||(WorldUtils.hasPermission(player,block,Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK))){
            BlockBreakEvent event=new BlockBreakEvent(block,player);
            event.setDropItems(false);
            event.setExpToDrop(0);
            Bukkit.getPluginManager().callEvent(event);

            if(event.isCancelled()){
                return false;
            }
            event.setDropItems(false);
            return true;
        }else return false;
    }



    public static void forceLoadChunk(Location loc,int tick){
        int dx=loc.getBlockX()>>4;
        int dz=loc.getBlockZ()>>4;
        World world=loc.getWorld();
        Chunk chunk=world.getChunkAt(dx,dz);
        final boolean isForceload=chunk.isForceLoaded();
        chunk.setForceLoaded(true);
        Schedules.launchSchedules(()->{
            chunk.setForceLoaded(isForceload);
        },tick,true,0);
    }
    public static boolean isTargetableLivingEntity(Entity e){
        if(e.isValid()&&!e.isDead()&&e instanceof LivingEntity le&&!le.isInvulnerable()){
            if(e instanceof ArmorStand stand && (stand.isMarker()||stand.isSmall())){
                return false;
            }
            return true;
        }return false;
    }
    public static Location getHandLocation(LivingEntity p){
        Location loc=p.getEyeLocation();
        Location playerLocation=p.getLocation();
        loc.add(playerLocation.subtract(loc).multiply(0.3).toVector());
        return loc;
    }
    public static boolean copyBlockState(BlockState state,Block block2){
        return EnvironmentManager.getManager().getVersioned().copyBlockStateTo(state,block2)!=null;
    }
}
