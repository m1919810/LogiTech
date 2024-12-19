package me.matl114.logitech.Utils;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RandomMobDrop;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.altar.AltarRecipe;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.Composter;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.Crucible;
import io.github.thebusybiscuit.slimefun4.implementation.items.misc.BasicCircuitBoard;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import me.matl114.logitech.ConfigLoader;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.SlimefunItem.AddDepends;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.AddSlimefunItems;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.AEMachine;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.EMachine;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.MTMachine;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ProbItemStack;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ImportRecipes;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.MGeneratorRecipe;
import me.matl114.logitech.SlimefunItem.Machines.AbstractTransformer;
import me.matl114.matlib.Implements.Slimefun.core.CustomRecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.*;

import java.util.*;

public class RecipeSupporter {
    public static void init(){

    }
    public static final SlimefunAddon PLUGIN= MyAddon.getInstance();
//    public static final RecipeType VANILLA_MERCHANT=new RecipeType(AddUtils.getNameKey("vanilla_merchant"),
//            new CustomItemStack(Material.PLAYER_HEAD,"交易内容","","村民交易项目"));
    private static final ArrayList<MachineRecipe> GOLDPAN_RECIPE = new ArrayList<>(){{
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[] {new ItemStack(Material.GRAVEL)},
                new ItemStack[] {
                        AddUtils.randItemStackFactory(
                                new LinkedHashMap<>(){{
                                    put("FLINT",40);
                                    put("CLAY_BALL",20);
                                    put("SIFTED_ORE",35);
                                    put("IRON_NUGGET",5);
                                }}
                        )
                }
        ));
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[] {
                        AddUtils.equalItemStackFactory(new ArrayList<>(){{
                            add("SOUL_SAND");
                            add("SOUL_SOIL");
                        }})
                },
                new ItemStack[] {
                        AddUtils.randItemStackFactory(
                                new LinkedHashMap<>(){{
                                    put("QUARTZ",50);
                                    put("GOLD_NUGGET",25);
                                    put("NETHER_WART",10);
                                    put("BLAZE_POWDER",8);
                                    put("GLOWSTONE_DUST",5);
                                    put("GHAST_TEAR",2);
                                }}
                        )
                }
        ));
    }};
    private static final ArrayList<MachineRecipe> ORE_WASHER_RECIPE=new ArrayList<>(){{
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[]{SlimefunItems.SIFTED_ORE},
                new ItemStack[] {
                        AddUtils.randItemStackFactory(
                                new LinkedHashMap<>(){{
                                    put("IRON_DUST",1);
                                    put("GOLD_DUST",1);
                                    put("COPPER_DUST",1);
                                    put("TIN_DUST",1);
                                    put("ZINC_DUST",1);
                                    put("ALUMINUM_DUST",1);
                                    put("SILVER_DUST",1);
                                    put("MAGNESIUM_DUST",1);
                                    put("LEAD_DUST",1);
                                }}
                        ),
                        SlimefunItems.STONE_CHUNK
                }
        ));
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[]{SlimefunItems.PULVERIZED_ORE},
                new ItemStack[] {SlimefunItems.PURE_ORE_CLUSTER}
        ));
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[]{new ItemStack(Material.SAND,2)},
                new ItemStack[] {SlimefunItems.SALT}
        ));
    }};
    private static final ArrayList<MachineRecipe> TABLE_SAW_RECIPE=new ArrayList<>(){{
        for (Material log : Tag.LOGS.getValues()) {
            Optional<Material> planks = AddUtils.getPlanks(log);

            if (planks.isPresent()) {

                add(MachineRecipeUtils.stackFrom(-1,Utils.array(new ItemStack(log)),Utils.array(new ItemStack(planks.get(),8))));
            }
        }

        for (Material plank : Tag.PLANKS.getValues()) {
            add(MachineRecipeUtils.stackFrom(-1,Utils.array(new ItemStack(plank)),Utils.array(new ItemStack(Material.STICK,4))));
        }
    }};

    /**
     * stored shaped machine recipe pieces
     * be careful ,ticks of these MachineRecipe are -1 ! not able to use directly in machine
     */
    public static final HashMap<RecipeType,List<MachineRecipe>> PROVIDED_SHAPED_RECIPES = new LinkedHashMap<>();
    /**
     * stored Unshaped machine recipe pieces
     * be careful ,ticks of these MachineRecipe are -1 ! not able to use directly in machine
     */
    public static final HashMap<RecipeType, List<MachineRecipe>> PROVIDED_UNSHAPED_RECIPES = new LinkedHashMap<>(){{
    }};
    //总统计表
    public static final HashSet<RecipeType> RECIPE_TYPES = new LinkedHashSet<>();
    //是和多方块绑定的配方
    public static final HashMap<RecipeType,MultiBlockMachine> MULTIBLOCK_RECIPETYPES=new LinkedHashMap<>();
    //检测存在TYPE成员的SlimefunItem，，或者也可以手动添加
    public static final HashMap<SlimefunItem,RecipeType> CUSTOM_RECIPETYPES = new LinkedHashMap<>(){{
    }};
    //废弃的表
    public static final HashSet<RecipeType> SUPPORTED_UNSHAPED_RECIPETYPE =new LinkedHashSet<>()    {{
        add(RecipeType.ENHANCED_CRAFTING_TABLE);
        add(RecipeType.MAGIC_WORKBENCH);
        add(RecipeType.ANCIENT_ALTAR);
        add(RecipeType.ARMOR_FORGE);
        add(RecipeType.COMPRESSOR);
        add(RecipeType.GRIND_STONE);
        add(RecipeType.ORE_CRUSHER);
        add(RecipeType.SMELTERY);
        if(AddDepends.INFINITYWORKBENCH_TYPE!=null){
            add(AddDepends.INFINITYWORKBENCH_TYPE);
        }
        if(AddDepends.MOBDATA_TYPE!=null) {

            add(AddDepends.MOBDATA_TYPE);
        }
        if(AddDepends.NTWQTWORKBENCH_TYPE!=null){
            add(AddDepends.NTWQTWORKBENCH_TYPE);
        }
        if(AddDepends.NTWEP_WORKBENCH_TYPE!=null){
            add(AddDepends.NTWEP_WORKBENCH_TYPE);
        }
    }};
    //废弃的表
    public static final HashSet<RecipeType> SUPPORTED_SHAPED_RECIPETYPE= new HashSet<>(){{
        add(RecipeType.MULTIBLOCK);
    }};
    //被禁用的配方
    public static final HashSet<RecipeType> BLACKLIST_RECIPETYPES = new HashSet<>(){{
        if(AddDepends.VOIDHARVEST!=null){
            add(AddDepends.VOIDHARVEST);
        }

    }};
    //一般来说不能让玩家合成的
    public static final HashSet<RecipeType> ILLEGAL_RECIPETYPES =new HashSet<>(){{
        if(AddDepends.VOIDHARVEST!=null){
            add(AddDepends.VOIDHARVEST);
        }
        add(RecipeType.NULL);
        add(RecipeType.MOB_DROP);
        add(RecipeType.BARTER_DROP);
        add(RecipeType.INTERACT);
        add(RecipeType.GEO_MINER);
    }};

    //被禁用的多方块
    public static final HashSet<MultiBlockMachine> BLACKLIST_MULTIBLOCK = new HashSet<>(){{
    }};
    //检测全部为MultiBlockMachine的机器 并抓取配方
    public static final HashMap<MultiBlockMachine,List<MachineRecipe>> MULTIBLOCK_RECIPES = new LinkedHashMap<>();

    public static final HashMap<EntityType,ItemStack[]> ENTITY_DROPLIST=new LinkedHashMap<>();
    public static final List<MachineRecipe> UNUSTACKABLE_ITEM_RECIPES=new ArrayList<>(){{
        // 桶
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("BUCKET",null,null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.WATER_BUCKET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe(null,"BUCKET",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.LAVA_BUCKET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe(null,null,"BUCKET",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MILK_BUCKET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe(null,null,null,"BUCKET",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.POWDER_SNOW_BUCKET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("BUCKET","BUCKET",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.COD_BUCKET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("BUCKET",null,"BUCKET",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.SALMON_BUCKET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("BUCKET",null,null,"BUCKET",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.TROPICAL_FISH_BUCKET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("BUCKET",null,null,null,"BUCKET",null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.PUFFERFISH_BUCKET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("BUCKET",null,null,null,null,"BUCKET",null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.AXOLOTL_BUCKET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("BUCKET",null,null,null,null,null,"BUCKET",null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.TADPOLE_BUCKET))));

        // 下界合金
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("NETHERITE_INGOT","NETHERITE_UPGRADE_SMITHING_TEMPLATE","DIAMOND_SWORD",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.NETHERITE_SWORD))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("NETHERITE_INGOT","NETHERITE_UPGRADE_SMITHING_TEMPLATE","DIAMOND_SHOVEL",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.NETHERITE_SHOVEL))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("NETHERITE_INGOT","NETHERITE_UPGRADE_SMITHING_TEMPLATE","DIAMOND_PICKAXE",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.NETHERITE_PICKAXE))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("NETHERITE_INGOT","NETHERITE_UPGRADE_SMITHING_TEMPLATE","DIAMOND_AXE",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.NETHERITE_AXE))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("NETHERITE_INGOT","NETHERITE_UPGRADE_SMITHING_TEMPLATE","DIAMOND_HOE",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.NETHERITE_HOE))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("NETHERITE_INGOT","NETHERITE_UPGRADE_SMITHING_TEMPLATE","DIAMOND_HELMET",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.NETHERITE_HELMET))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("NETHERITE_INGOT","NETHERITE_UPGRADE_SMITHING_TEMPLATE","DIAMOND_CHESTPLATE",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.NETHERITE_CHESTPLATE))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("NETHERITE_INGOT","NETHERITE_UPGRADE_SMITHING_TEMPLATE","DIAMOND_LEGGINGS",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.NETHERITE_LEGGINGS))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("NETHERITE_INGOT","NETHERITE_UPGRADE_SMITHING_TEMPLATE","DIAMOND_BOOTS",null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.NETHERITE_BOOTS))));

        // 唱片
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_13",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_13))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_CAT",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_CAT))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_BLOCKS",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_BLOCKS))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_CHIRP",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_CHIRP))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_FAR",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_FAR))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_MALL",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_MALL))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_MELLOHI",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_MELLOHI))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_STAL",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_STAL))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_STRAD",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_STRAD))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_WARD",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_WARD))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_11",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_11))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_WAIT",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_WAIT))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_PIGSTEP",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_PIGSTEP))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_OTHERSIDE",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_OTHERSIDE))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("MUSIC_DISC_RELIC",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MUSIC_DISC_RELIC))));

        // 旗帜图案

        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("GLOBE_BANNER_PATTERN",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.GLOBE_BANNER_PATTERN))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("PIGLIN_BANNER_PATTERN",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.PIGLIN_BANNER_PATTERN))));

        // 潜影盒

        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","WHITE_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.WHITE_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","ORANGE_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.ORANGE_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","MAGENTA_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.MAGENTA_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","LIGHT_BLUE_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.LIGHT_BLUE_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","YELLOW_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.YELLOW_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","LIME_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.LIME_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","PINK_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.PINK_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","GRAY_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.GRAY_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","LIGHT_GRAY_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.LIGHT_GRAY_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","CYAN_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.CYAN_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","PURPLE_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.PURPLE_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","BLUE_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.BLUE_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","BROWN_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.BROWN_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","GREEN_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.GREEN_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","RED_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.RED_SHULKER_BOX))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SHULKER_BOX","BLACK_DYE",null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.BLACK_SHULKER_BOX))));

        // 其它装备
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("TRIDENT",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.TRIDENT))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("TOTEM_OF_UNDYING",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.TOTEM_OF_UNDYING))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("IRON_HORSE_ARMOR",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.IRON_HORSE_ARMOR))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("GOLDEN_HORSE_ARMOR",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.GOLDEN_HORSE_ARMOR))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("DIAMOND_HORSE_ARMOR",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.DIAMOND_HORSE_ARMOR))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("ELYTRA",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.ELYTRA))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SADDLE",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.SADDLE))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("ENCHANTED_BOOK",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.ENCHANTED_BOOK))));

        // 杂类

        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SNOWBALL",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.SNOWBALL))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("EGG",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.EGG))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("ENDER_PEARL",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.ENDER_PEARL))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("WRITTEN_BOOK",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.WRITTEN_BOOK))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("HONEY_BOTTLE",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.HONEY_BOTTLE))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("DEBUG_STICK",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.DEBUG_STICK))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("COMMAND_BLOCK_MINECART",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.COMMAND_BLOCK_MINECART))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("KNOWLEDGE_BOOK",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.KNOWLEDGE_BOOK))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("GOAT_HORN",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.GOAT_HORN))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("POTION",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.POTION))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("SPLASH_POTION",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.SPLASH_POTION))));
        add(MachineRecipeUtils.shapeFrom(-1,Utils.recipe("LINGERING_POTION",null,null,null,null,null,null,null),Utils.recipe(AddSlimefunItems.REPLACE_CARD.getReplaceCard(Material.LINGERING_POTION))));

    }};
    //注册为可识别icon的
    public static final HashMap<RecipeType,ItemStack> RECIPETYPE_ICON=new LinkedHashMap<>();
    //堆叠机器黑名单
    public static final HashSet<SlimefunItem> BLACKLIST_STACKMACHINE=new LinkedHashSet<>();
    //读取机器类黑名单 防止循环读取
    public static final HashSet<Class> BLACKLIST_MACHINECLASS=new HashSet<>(){{
       // add(AbstractMachine.class);
    }};
    //读取的全部机器配方
    public static final HashMap<SlimefunItem ,List<MachineRecipe>> MACHINE_RECIPELIST=new LinkedHashMap<>();
    //记录机器类型和机器耗电
    public static final HashSet<SlimefunItem> MGENERATOR_FORCELIST=new LinkedHashSet<>();
    public static final HashMap<SlimefunItem,Integer> STACKMACHINE_LIST=new LinkedHashMap<>();
    public static final HashMap<SlimefunItem,Integer> STACKMGENERATOR_LIST=new LinkedHashMap<>();
    public static List<MachineRecipe> getStackedRecipes(RecipeType type) {
        if(SUPPORTED_UNSHAPED_RECIPETYPE.contains(type)) {
            if(PROVIDED_UNSHAPED_RECIPES.get(type) == null|| PROVIDED_UNSHAPED_RECIPES.get(type).size() == 0) {
                initUnshapedRecipes(type);
            }
            return PROVIDED_UNSHAPED_RECIPES.get(type);
        }
        return  null;
    }
    public static boolean tryModifyStandardMachineRecipe(SlimefunItem item,List<MachineRecipe> sourceRecipe,boolean append){
        String fieldName=null;
        boolean isMethod=false;
        Object machineRecipes=null;
        if (fieldName == null) {
            machineRecipes = ReflectUtils.invokeGetRecursively(item, Settings.FIELD, "recipes");
            if (machineRecipes != null) {
                fieldName = "recipes";
            }
        }
        if (fieldName == null) {
            machineRecipes = ReflectUtils.invokeGetRecursively(item, Settings.FIELD, "machineRecipes");
            if (machineRecipes != null) {
                fieldName= "machineRecipes";
            }
        }
        if (fieldName == null) {
            machineRecipes = ReflectUtils.invokeGetRecursively(item, Settings.METHOD, "getMachineRecipes");
            if (machineRecipes != null) {
                fieldName = "getMachineRecipes";
                isMethod=true;
            }
        }
        if(machineRecipes==null){
            return false;
        }
        try{
            List<MachineRecipe> result=new ArrayList<>();

            if(append){
                List<MachineRecipe> origin=(List<MachineRecipe>) machineRecipes;
                result.addAll(origin);
            }
            result.addAll(sourceRecipe);
            ReflectUtils.invokeSetRecursively(item,fieldName,result);
            return true;
        }catch (Throwable e){
            try{
                List<MachineRecipe> origin=(List<MachineRecipe>) machineRecipes;
                origin.addAll(sourceRecipe);
                return  true;
            }catch (Throwable e1){
                return false;
            }
        }
    }
    public static void loadMachineModification(Config config,String root,boolean withWarning){
        if(MyAddon.testmode())
            withWarning=true;
        Set<String > keySets=config.getKeys(root);
        for (String machineId:keySets){
            String machinepath=root+"."+machineId;
            SlimefunItem machine;
            try{
                machine= SlimefunItem.getById(machineId);
                machine.getId();
            }catch(Throwable e){
                if(withWarning)
                    Debug.logger("ERROR WHILE LOADING MACHINE CONFIG: not valid item  %s".formatted(machineId));
                continue;
            }
            boolean append=false;
            boolean applyToMachine=false;
            boolean isGenerator=false;
            Set<String> machineRecipe=null;
            String recipeType=null;
            int commonTick=0;
            try{
                String method=config.getString(machinepath+".mode");
                append=method.startsWith("a");
                applyToMachine=method.endsWith("+");
                if(config.contains(machinepath+".recipetype")){
                    recipeType=config.getString(machinepath+".recipetype");
                    commonTick=config.getInt(machinepath+".time");
                }else{
                    machineRecipe=config.getKeys(machinepath+".recipe");
                }
                isGenerator=config.getString(machinepath+".type").startsWith("gen");
            }catch(Throwable e){
                if(withWarning)
                    Debug.logger("ERROR WHILE LOADING MACHINE CONFIG: Error config format in %s ".formatted(machineId));
                Debug.debug(e);
            }
            List<MachineRecipe> recipes=new ArrayList<>(12);
            if(recipeType!=null){
                try{
                    RecipeType type=null;
                    for(RecipeType rp:RECIPE_TYPES){
                        if(recipeType.equalsIgnoreCase(rp.getKey().toString())){
                            type=rp;
                            break;
                        }
                    }
                    if(type==null){
                        if(withWarning)
                            Debug.logger("ERROR WHILE LOADING MACHINE CONFIG: RecipeType %s not found".formatted(recipeType));
                        continue;
                    }
                    try {
                        List<MachineRecipe> rplist = PROVIDED_UNSHAPED_RECIPES.get(type);
                        for(MachineRecipe mr:rplist){
                            recipes.add(MachineRecipeUtils.stackFrom(commonTick,mr.getInput(),mr.getOutput()));
                        }
                    }catch (Throwable e){
                        if(withWarning){

                            Debug.logger("ERROR WHILE LOADING RECIPETYPE MACHINELIST: RecipeType %s".formatted(recipeType));
                        }
                    }
                }catch(Throwable e){}
            }else{
                if(machineRecipe==null){
                    continue;
                }
                try{
                    recipeloop:
                    for(String recipe:machineRecipe){
                        String ippath=AddUtils.concat(machinepath,".recipe.",recipe,".input");
                        String oppath=AddUtils.concat(machinepath,".recipe.",recipe,".output");
                        Set<String> inputKey=config.getKeys(ippath);
                        Set<String> outputKey=config.getKeys(oppath);
    //                    List<String> inputlist=config.getStringList(rcpath+".input");
    //                    List<String> outputlist=config.getStringList(rcpath+".output");
                        int tick=config.getInt(AddUtils.concat(machinepath,".recipe.",recipe,".tick"));
                        ItemStack[] input=new ItemStack[inputKey.size()];
                        ItemStack[] output=new ItemStack[outputKey.size()];
                        int len=0;
                        if(inputKey.size()>0){
                            for(String skey:inputKey){
                                ItemStack it=loadItemStack(config,AddUtils.concat(ippath,".",skey));
                                if(it==null){
                                    if(withWarning)
                                        Debug.logger("ERROR WHILE LOADING MACHINE CONFIG: failed to load recipe output %s".formatted(recipe));
                                    it= AddItem.RESOLVE_FAILED.clone();
                                }
                                input[len]=it;
                                ++len;
                            }
                        }else{
                            Debug.debug("empty input");
                        }
                        int len2=0;
                        if(outputKey.size()>0){
                            for(String skey:outputKey){
                                ItemStack it=loadItemStack(config,AddUtils.concat(oppath,".",skey));
                                if(it==null){
                                    if(withWarning)
                                        Debug.logger("ERROR WHILE LOADING MACHINE CONFIG: failed to load recipe output %s".formatted(recipe));
                                    it= AddItem.RESOLVE_FAILED.clone();
                                }
                                output[len2]=it;
                                ++len2;
                            }
                        }else{
                            Debug.debug("empty output");
                        }
                        if(isGenerator){
                            recipes.add(MachineRecipeUtils.mgFrom(tick,input,output));
                        }else{
                            recipes.add(MachineRecipeUtils.stackFrom(tick,input,output));
                        }
                    }
                }catch(Throwable e){
                    Debug.logger("ERROR WHILE LOADING MACHINE CONFIG: error recipe format in %s".formatted(machineId));
                    Debug.debug(e);
                }
            }

            List<MachineRecipe> recipe=MACHINE_RECIPELIST.get(machine);
            if(recipe==null){
                recipe=new ArrayList<>(12);
                MACHINE_RECIPELIST.put(machine,recipe);
            }
            if(!append){
                recipe.clear();
            }//加入机器列表
            recipe.addAll(recipes);
            if(applyToMachine&&!isGenerator){
                if(!tryModifyStandardMachineRecipe(machine,recipes,append)){
                    Debug.logger("WARNING: %s 不是标准的机器,尝试修改机器配方列表失败,禁用该物品 + 模式".formatted(machineId));

                }
            }
        }
    }
    public static ItemStack loadItemStack(Config config,String fatherPath){

        if( !config.contains(AddUtils.concat(fatherPath,".type"))){
            String it=config.getString(fatherPath);
            return AddUtils.resolveItem(it);
        }

        try {
            Debug.debug("abstract itemstack found");
            String s = config.getString(AddUtils.concat(fatherPath, ".type"));
            String itemPath=AddUtils.concat(fatherPath,".item");
            Set<String> keys=config.getKeys(itemPath);
            List<ItemStack> stack=new ArrayList<>(keys.size());
            for(String key:keys){
                stack.add(loadItemStack(config,AddUtils.concat(itemPath,".",key)));
            }
            if(stack.isEmpty()){
                throw  new IllegalArgumentException("illegal config format %s".formatted(AddUtils.concat(itemPath,".")));
            }
            String weightPath=AddUtils.concat(fatherPath,".weight");
            List<Integer> weights=new ArrayList<>(stack.size());
            keys=config.getKeys(weightPath);
            Debug.debug(weightPath);
            Debug.debug(keys);
            for(String key:keys){
                Debug.debug(key);
                weights.add(config.getInt(AddUtils.concat(weightPath,".",key)));
            }
            if(s.startsWith("eq")){//input eq
                return AddUtils.equalItemStackFactory(stack,weights.isEmpty()?1:weights.get(0));
            }else{

                if(s.startsWith("ra")){
                    return AddUtils.randItemStackFactory(stack,weights);
                }else if(s.startsWith("rp")){
                    double prob=((double)weights.get(0))/100;
                    return new ProbItemStack(stack.get(0),prob);
                }else if(s.startsWith("rc")){
                   // Debug.debug(config.getInt(AddUtils.concat(weightPath,".1")));
                    int min=weights.get(0);
                    int max=weights.get(1);
                    if(min>max){
                        int t=min;
                        min=max;
                        max=t;
                    }else if(min==max){
                        return stack.get(0);
                    }
                    return AddUtils.randAmountItemFactory(stack.get(0),min,max);
                }else{
                    throw new IllegalArgumentException("unsupported item type: "+s);
                }
            }
        }catch(Throwable e1){
            Debug.logger("ERROR WHILE LOADING MACHINE CONFIG: error item format in %s".formatted(fatherPath));
            Debug.debug(e1);
            return AddItem.RESOLVE_FAILED.clone();
        }
    }
    public static void loadStackMachineConfig(Config config,String root,boolean replaceEnergy,boolean forceload){
        Set<String > keySets=config.getKeys(root);
        for (String machineId:keySets){
            String machinepath=root+"."+machineId;
            SlimefunItem machine;
            try{
                machine= SlimefunItem.getById(machineId);
                machine.getId();
            }catch (Throwable e){
                if(forceload)
                    Debug.logger("ERROR WHILE LOADING MACHINE CONFIG: not valid item  %s".formatted(machineId));
                continue;
            }
            String type=null;
            int energy=-1;

            try{
                type=config.getString(machinepath+".type");
                energy=config.getInt(machinepath+".energy");
            }catch(Throwable e){
                if(forceload)
                    Debug.logger("ERROR WHILE LOADING MACHINE CONFIG: error config format in %s".formatted(machineId));
            }
            if(type==null){
                continue;
            }
            if(type.startsWith("gen")){
                Integer en= STACKMACHINE_LIST.remove(machine);
                if(replaceEnergy){
                    STACKMGENERATOR_LIST.put(machine,energy);
                }else {
                    Integer en2=STACKMGENERATOR_LIST.get(machine);
                    en=en2!=null?en2:(en!=null?en:-1);
                    if(en==-1){
                        STACKMGENERATOR_LIST.put(machine,energy);
                    }
                }
            }else if(type.startsWith("mach")){
                Integer en= STACKMGENERATOR_LIST.remove(machine);
                if(replaceEnergy){
                    STACKMACHINE_LIST.put(machine,energy);
                }else {
                    Integer en2=STACKMACHINE_LIST.get(machine);
                    en=en2!=null?en2:(en!=null?en:-1);
                    if(en==-1){
                        STACKMACHINE_LIST.put(machine,energy);
                    }
                }
            }else if(type.startsWith("no")){
                STACKMACHINE_LIST.remove(machine);
                STACKMGENERATOR_LIST.remove(machine);
            }
        }
    }
    private static double getRscChance(Integer chance) {
        if (chance == null) return 0d;
        if (chance >= 100) return 1d;
        if (chance < 1) return 0d;


        return  ((double)chance)/100;
    }
    public static MachineRecipe transferRSCRecipes(MachineRecipe rsc){
        if(!rsc.getClass().getName().endsWith("CustomMachineRecipe")){
            return rsc;
        }
        try{
            boolean isDisplay=(Boolean) ReflectUtils.invokeGetRecursively(rsc,Settings.FIELD,"forDisplay");
            if(isDisplay){
                //是用于展示的配方？
                return null;
            }
            List<Integer> chances=(List<Integer>) ReflectUtils.invokeGetRecursively(rsc,Settings.FIELD,"chances");
            ItemStack[] input=rsc.getInput();
            ItemStack[] output=rsc.getOutput();
            List<ItemStack> outputlist=new ArrayList<>(output.length);
            for(int i=0;i<output.length;i++){
                double chance=getRscChance(chances.get(i));
                if(chance>0.99){
                    outputlist.add(output[i]);
                }else {
                    outputlist.add(new ProbItemStack(output[i],chance));
                }
            }
            if(outputlist.isEmpty())return null;
            MachineRecipe result;
            boolean isRandStack=(Boolean)ReflectUtils.invokeGetRecursively(rsc,Settings.FIELD,"chooseOneIfHas");
            if(isRandStack){
                ItemStack outputRand=AddUtils.eqRandItemStackFactory(outputlist);
                result=MachineRecipeUtils.stackFrom(rsc.getTicks(),input,new ItemStack[]{outputRand});
            }else {
                result=MachineRecipeUtils.stackFrom(rsc.getTicks(),input,outputlist.toArray(new ItemStack[outputlist.size()]));
            }
            return result;
        }catch(Throwable e){
            Debug.debug("generate an exception while transfer rsc recipes %s".formatted(rsc.getClass().getName()));
            if(MyAddon.testmode())
                e.printStackTrace();
            return null;
        }
    }
    public static int tryGetMachineEnergy(SlimefunItem item){
        int energyComsumption=-1;
        if(!(item instanceof EnergyNetComponent)){
            return 0;
        }
        if(item instanceof AContainer container){
            return container.getEnergyConsumption();
        }else{
            Object energy=null;
            String methodName=null;
            try{
                try{
                    if(methodName==null){
                        energy=ReflectUtils.invokeGetRecursively(item,Settings.METHOD,"getEnergyConsumption");
                        if(energy!=null){
                            energyComsumption=(Integer)energy;
                            methodName="getEnergyConsumption() method";
                        }
                    }
                }
                catch(Throwable e){

                }
                try{
                if(methodName==null){

                    energy=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"EnergyConsumption");
                    if(energy!=null){
                        energyComsumption=(Integer)energy;
                        methodName="EnergyConsumption field";
                    }
                }
                }
                catch(Throwable e){

                    }
                try{
                    if(methodName==null){

                        energy=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"energyConsumption");
                        if(energy!=null){
                            energyComsumption=(Integer)energy;
                            methodName="energyConsumption field";
                        }
                    }
                }
                catch(Throwable e){

                }
                try{
                if(methodName==null){
                    energy=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"energyConsumedPerTick");
                    if(energy!=null){
                        energyComsumption=(Integer)energy;
                        methodName="energyConsumedPerTick field";
                    }
                }
                }
                catch(Throwable e){

                }
                try{
                if(methodName==null){
                    energy=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"energyPerTick");
                    if(energy!=null){
                        energyComsumption=(Integer)energy;
                        methodName="energyPerTick field";
                    }
                }
                }
                catch(Throwable e){

                }
                try{
                if(methodName==null){
                    energy=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"per");
                    if(energy!=null){
                        energyComsumption=(Integer)energy;
                        methodName="per";
                    }
                }
                }

                catch(Throwable e){

                }
                if(energyComsumption==-1){
                    Debug.debug("find no energy declared");
                }
            }catch (Throwable exp){
                Debug.debug("exception generate while invoking ",item);
                Debug.debug(exp);
            }
            return energyComsumption;
        }
    }

    /**
     * invoke recursively
     * @param target
     * @param mod
     * @return
     */
    private static boolean hasInit=false;
    public static void initRecipeSupportor(){
        if(hasInit){
            throw new RuntimeException("RecipeSupporter start register task before server start!! register abort");
        }
        hasInit=true;
        long a=System.nanoTime();
        Debug.logger("配方供应器开始进行数据采集,预计耗时2秒");
        Debug.logger("期间tps可能会下降,请不要在供应器工作时进入服务器");
        Debug.logger("期间的报错信息均可忽略");
        Debug.logger("读取全部配方中...");
        RECIPE_TYPES.add(BukkitUtils.VANILLA_CRAFTTABLE);
        PROVIDED_SHAPED_RECIPES.put(BukkitUtils.VANILLA_CRAFTTABLE,new ArrayList<>());
        PROVIDED_UNSHAPED_RECIPES.put(BukkitUtils.VANILLA_CRAFTTABLE,new ArrayList<>());
        RECIPE_TYPES.add(BukkitUtils.VANILLA_FURNACE);
        PROVIDED_UNSHAPED_RECIPES.put(BukkitUtils.VANILLA_FURNACE,new ArrayList<>());
        PROVIDED_SHAPED_RECIPES.put(BukkitUtils.VANILLA_FURNACE,new ArrayList<>());
        //原版配方
        Iterator<Recipe> recipeIterator = PLUGIN.getJavaPlugin().getServer().recipeIterator();
        while (recipeIterator.hasNext()) {
            Recipe next = recipeIterator.next();
            if (next instanceof ShapedRecipe sr) {
                String[] shape=sr.getShape();
                int len1=shape.length;
                int len2=3*len1;
                Map<Character, RecipeChoice> itm=sr.getChoiceMap();
                ItemStack[] inputs=new ItemStack[len2];
                for (int i=0;i<len1;++i){
                    int len3=shape[i].length();
                    for(int j=0;j<len3;++j){
                        RecipeChoice rc=itm.get(shape[i].charAt(j));
                        if(rc==null){
                            inputs[3*i+j]=null;
                        }
                        else if(rc instanceof RecipeChoice.MaterialChoice mc){
                            inputs[3*i+j]=AddUtils.equalItemStackFactory(mc.getChoices());
                        }else if (rc instanceof RecipeChoice.ExactChoice ec){
                            inputs[3*i+j]=AddUtils.equalItemStackFactory(ec.getChoices());
                        }
                        else {
                            inputs[3*i+j]=AddItem.RESOLVE_FAILED;
                            Debug.logger("Unknown RecipeChoice class",rc.getClass().getName());
                        }
                    }
                }

                PROVIDED_SHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE).add(MachineRecipeUtils.shapeFrom(-1,inputs,Utils.array(next.getResult())));
                PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE).add(MachineRecipeUtils.stackFrom(-1,inputs,Utils.array(next.getResult())));

            } else if (next instanceof ShapelessRecipe slr) {
                List< RecipeChoice> choice= slr.getChoiceList();
                int len=choice.size();
                ItemStack[] inputs=new ItemStack[len];
                for(int i=0;i<len;++i){
                    RecipeChoice rc=choice.get(i);
                    if(rc instanceof RecipeChoice.MaterialChoice mc){
                        inputs[i]=AddUtils.equalItemStackFactory(mc.getChoices());
                    }else if (rc instanceof RecipeChoice.ExactChoice ec){
                        inputs[i]=AddUtils.equalItemStackFactory(ec.getChoices());
                    }
                    else{
                        inputs[i]=AddItem.RESOLVE_FAILED;
                        Debug.logger("Unknown RecipeChoice class",rc.getClass().getName());
                    }
                }
               // List<ItemStack> input = ((ShapelessRecipe) next).getIngredientList();
                PROVIDED_SHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE).add(MachineRecipeUtils.shapeFrom(-1,inputs,Utils.array(next.getResult())));
                PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE).add(MachineRecipeUtils.stackFrom(-1,inputs,Utils.array(next.getResult())));
            }else if(next instanceof FurnaceRecipe recipe) {
                RecipeChoice choice =recipe.getInputChoice();

                if (choice instanceof RecipeChoice.MaterialChoice materialChoice) {
                    for (Material input : materialChoice.getChoices()) {
                        PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_FURNACE).add(MachineRecipeUtils.stackFrom(recipe.getCookingTime()/10,new ItemStack[] {new ItemStack(input)},new ItemStack[] {recipe.getResult()}));
                        PROVIDED_SHAPED_RECIPES.get(BukkitUtils.VANILLA_FURNACE).add(MachineRecipeUtils.shapeFrom(recipe.getCookingTime()/10,new ItemStack[] {new ItemStack(input)},new ItemStack[] {recipe.getResult()}));
                    }
                }else if (choice instanceof RecipeChoice.ExactChoice ec){
                    for (ItemStack input:ec.getChoices()) {
                        PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_FURNACE).add(MachineRecipeUtils.stackFrom(recipe.getCookingTime() / 10, new ItemStack[]{input}, new ItemStack[]{recipe.getResult()}));
                        PROVIDED_SHAPED_RECIPES.get(BukkitUtils.VANILLA_FURNACE).add(MachineRecipeUtils.shapeFrom(recipe.getCookingTime() / 10, new ItemStack[]{input}, new ItemStack[]{recipe.getResult()}));
                    }
                }
            }
        }
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            RecipeType recipeType = item.getRecipeType();
            //过会解析
            if(!RECIPE_TYPES.contains(recipeType)){
                RECIPE_TYPES.add(recipeType);
                PROVIDED_SHAPED_RECIPES.put(recipeType,new ArrayList<>());
                PROVIDED_UNSHAPED_RECIPES.put(recipeType,new ArrayList<>());
            }
            if(recipeType==BukkitUtils.VANILLA_CRAFTTABLE){
                BukkitUtils.sendRecipeToVanilla(item);
            }
        }
        RECIPE_TYPES.addAll(CustomRecipeType.getCustomRecipeTypes());
        //先解析多方块机器的配方
        for(RecipeType recipeType :RECIPE_TYPES){
            if(!BLACKLIST_RECIPETYPES.contains(recipeType)) {//not in black list
                SlimefunItem sfitem=recipeType.getMachine();
                if (sfitem instanceof MultiBlockMachine mb) {
                    List<ItemStack[]>  recipeInputs= RecipeType.getRecipeInputList((MultiBlockMachine) sfitem);
                    List<MachineRecipe> recipes=new ArrayList<>();
                    List<MachineRecipe> shapedrecipes=new ArrayList<>();
                    for(ItemStack[] recipeInput: recipeInputs) {
                        recipes.add(MachineRecipeUtils.stackFromMultiBlock(recipeInput,mb,-1 ));
                        shapedrecipes.add(MachineRecipeUtils.shapeFromMultiBlock(recipeInput,mb,-1));
                    }
                    //记录配方类型对应的多方块
                    MULTIBLOCK_RECIPETYPES.put(recipeType,(MultiBlockMachine) sfitem);
                    //将"多方块背后的配方类型"注册为多方块的列表
                    PROVIDED_SHAPED_RECIPES.put(recipeType,shapedrecipes);
                    PROVIDED_UNSHAPED_RECIPES.put(recipeType,recipes);
                    //顺便注册了多方块的配方类型
                    MULTIBLOCK_RECIPES.put((MultiBlockMachine) sfitem,recipes);
                }
            }
        }
        for(RecipeType recipeType :RECIPE_TYPES){
            if(!BLACKLIST_RECIPETYPES.contains(recipeType)) {//not in black list
                if(recipeType instanceof CustomRecipeType crp){
                    List<MachineRecipe> stackRecipes=new ArrayList<>();
                    List<MachineRecipe> shapedRecipes=new ArrayList<>();
                    crp.getRecipes().forEach((in,out)->{
                        stackRecipes.add(MachineRecipeUtils.stackFrom(-1,in,new ItemStack[]{out}));
                        shapedRecipes.add(MachineRecipeUtils.shapeFrom(-1,in,new ItemStack[]{out}));
                    });
                    PROVIDED_SHAPED_RECIPES.computeIfAbsent(recipeType,k->new ArrayList<>()).addAll(shapedRecipes);
                    PROVIDED_UNSHAPED_RECIPES.computeIfAbsent(recipeType,k->new ArrayList<>()).addAll(stackRecipes);
                }
            }
        }
        //非多方块的类型,基本上就是普通物品的配方注册，读取一遍
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()){
            RecipeType recipeType = item.getRecipeType();
            //customRecipeType, 和 Multiblock type都被处理过了
            if((!BLACKLIST_RECIPETYPES.contains(recipeType))&&(!MULTIBLOCK_RECIPETYPES.containsKey(recipeType))&& !(recipeType instanceof CustomRecipeType)){
                PROVIDED_SHAPED_RECIPES.get(recipeType).add(MachineRecipeUtils.shapeFromRecipe(item));
                PROVIDED_UNSHAPED_RECIPES.get(recipeType).add(MachineRecipeUtils.stackFromRecipe(item)) ;
            }
            //解析多方块机器
            if(recipeType==RecipeType.MULTIBLOCK) {
                if(item instanceof MultiBlockMachine ) {
                    if(!BLACKLIST_MULTIBLOCK.contains(item)&&(!MULTIBLOCK_RECIPES.containsKey(item))){
                        List<ItemStack[]>  recipeInputs= RecipeType.getRecipeInputList((MultiBlockMachine) item);
                        List<MachineRecipe> recipes=new ArrayList<>();
                        for(ItemStack[] recipeInput: recipeInputs) {
                            recipes.add(MachineRecipeUtils.stackFrom(0,recipeInput,new ItemStack[] {RecipeType.getRecipeOutputList((MultiBlockMachine) item,recipeInput)}));
                        }
                        MULTIBLOCK_RECIPES.put((MultiBlockMachine) item,recipes);
                    }
                }
            }
            //解析指定类型特殊机器配方类型
            try{
                Object _Type=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"TYPE");
                if(_Type instanceof RecipeType&&!BLACKLIST_RECIPETYPES.contains((RecipeType) _Type)){
                    CUSTOM_RECIPETYPES.put(item,(RecipeType) _Type);
                    //Debug.logger("detect certain field TYPE: "+item);
                }

            }catch (Throwable e){
                //Debug.logger("generate an exception while registering recipes! :"+e.getMessage());
            }
        }
        //强制替换有问题的配方
        PROVIDED_UNSHAPED_RECIPES.put(RecipeType.GOLD_PAN,GOLDPAN_RECIPE);
        PROVIDED_UNSHAPED_RECIPES.put(RecipeType.ORE_WASHER,ORE_WASHER_RECIPE);
        PROVIDED_SHAPED_RECIPES.put(RecipeType.GOLD_PAN,GOLDPAN_RECIPE);
        PROVIDED_SHAPED_RECIPES.put(RecipeType.ORE_WASHER,ORE_WASHER_RECIPE);

        MULTIBLOCK_RECIPES.put((MultiBlockMachine)SlimefunItems.ORE_WASHER.getItem(),ORE_WASHER_RECIPE);
        MULTIBLOCK_RECIPES.put((MultiBlockMachine) SlimefunItems.AUTOMATED_PANNING_MACHINE.getItem(),GOLDPAN_RECIPE);
        MULTIBLOCK_RECIPES.put((MultiBlockMachine) SlimefunItems.TABLE_SAW.getItem(),TABLE_SAW_RECIPE);
        //做生物掉落的配方
        HashMap<EntityType,ItemStack[]> preData=new HashMap<>(){{
        //敌对生物
            put(EntityType.ZOMBIE, Utils.recipe("ROTTEN_FLESH", AddUtils.probItemStackFactory( AddUtils.resolveItem("IRON_INGOT"),2), AddUtils.probItemStackFactory( AddUtils.resolveItem("CARROT"),2), AddUtils.probItemStackFactory( AddUtils.resolveItem("POTATO"),2)));
            put(EntityType.ZOMBIE_VILLAGER, Utils.recipe("ROTTEN_FLESH", AddUtils.probItemStackFactory( AddUtils.resolveItem("IRON_INGOT"),2), AddUtils.probItemStackFactory( AddUtils.resolveItem("CARROT"),2), AddUtils.probItemStackFactory( AddUtils.resolveItem("POTATO"),2)));
            put(EntityType.HUSK, Utils.recipe("ROTTEN_FLESH", AddUtils.probItemStackFactory( AddUtils.resolveItem("IRON_INGOT"),2), AddUtils.probItemStackFactory( AddUtils.resolveItem("CARROT"),2), AddUtils.probItemStackFactory( AddUtils.resolveItem("POTATO"),2)));
            put(EntityType.ZOMBIFIED_PIGLIN, Utils.recipe("ROTTEN_FLESH", "GOLD_NUGGET", AddUtils.probItemStackFactory( AddUtils.resolveItem("GOLD_INGOT"),2), AddUtils.probItemStackFactory( AddUtils.resolveItem("GOLDEN_SWORD"),2)));
            put(EntityType.PIGLIN, Utils.recipe("ARROW", "GOLD_INGOT"));
            put(EntityType.PIGLIN_BRUTE, Utils.recipe(AddUtils.probItemStackFactory( AddUtils.resolveItem("GOLDEN_AXE"),8), "GOLD_INGOT"));
            put(EntityType.DROWNED, Utils.recipe("ROTTEN_FLESH", AddUtils.probItemStackFactory( AddUtils.resolveItem("COPPER_INGOT"),11), AddUtils.probItemStackFactory( AddUtils.resolveItem("NAUTILUS_SHELL"),20), AddUtils.probItemStackFactory( AddUtils.resolveItem("TRIDENT"),2)));
            put(EntityType.ELDER_GUARDIAN, Utils.recipe("PRISMARINE_SHARD", AddUtils.probItemStackFactory( AddUtils.resolveItem("PRISMARINE_CRYSTALS"),33), AddUtils.probItemStackFactory( AddUtils.resolveItem("COD"),50), "WET_SPONGE"));
            put(EntityType.GUARDIAN, Utils.recipe("PRISMARINE_SHARD", AddUtils.probItemStackFactory( AddUtils.resolveItem("PRISMARINE_CRYSTALS"),40), AddUtils.probItemStackFactory( AddUtils.resolveItem("COD"),40)));
            put(EntityType.SKELETON, Utils.recipe("BONE", "ARROW"));
            put(EntityType.STRAY, Utils.recipe("BONE", "ARROW"));
            put(EntityType.WITHER_SKELETON, Utils.recipe("BONE", "COAL", AddUtils.probItemStackFactory( AddUtils.resolveItem("WITHER_SKELETON_SKULL"),2)));
            put(EntityType.CREEPER, Utils.recipe("GUNPOWDER",AddUtils.probItemStackFactory( AddUtils.resolveItem("CREEPER_HEAD"),5)));
            put(EntityType.SPIDER, Utils.recipe("STRING", "SPIDER_EYE"));
            put(EntityType.CAVE_SPIDER, Utils.recipe("STRING", "SPIDER_EYE"));
            put(EntityType.ENDERMAN, Utils.recipe("ENDER_PEARL", AddUtils.probItemStackFactory( AddUtils.resolveItem( "ENDER_EYE"),25)));
            put(EntityType.GHAST, Utils.recipe("GHAST_TEAR", "GUNPOWDER"));
            put(EntityType.EVOKER, Utils.recipe(AddUtils.probItemStackFactory( AddUtils.resolveItem( "TOTEM_OF_UNDYING"),25), "EMERALD"));
            put(EntityType.GIANT, Utils.recipe("ROTTEN_FLESH"));
            put(EntityType.ILLUSIONER, Utils.recipe("BOW", "EMERALD"));
            put(EntityType.PILLAGER, Utils.recipe("CROSSBOW", "EMERALD"));
            put(EntityType.VINDICATOR, Utils.recipe("IRON_AXE", "EMERALD"));
            put(EntityType.RAVAGER, Utils.recipe("SADDLE"));
          //  put(EntityType.VEX, Utils.recipe("IRON_SWORD"));
            put(EntityType.WITCH, Utils.recipe(  AddUtils.eqRandItemStackFactory(Arrays.stream(Utils.recipe("REDSTONE", "GLASS_BOTTLE", "GUNPOWDER", "SPIDER_EYE", "STICK", "SUGAR")).toList())));
            put(EntityType.SLIME, Utils.recipe("SLIME_BALL"));
            put(EntityType.MAGMA_CUBE, Utils.recipe("MAGMA_CREAM"));
            put(EntityType.BLAZE, Utils.recipe("BLAZE_ROD"));
            put(EntityType.PHANTOM, Utils.recipe("PHANTOM_MEMBRANE"));
            put(EntityType.SHULKER, Utils.recipe("SHULKER_SHELL"));
            put(EntityType.HOGLIN, Utils.recipe("PORKCHOP", "LEATHER"));
            put(EntityType.ZOGLIN, Utils.recipe("ROTTEN_FLESH", "LEATHER"));
            //BOSS
            put(EntityType.WITHER, Utils.recipe("NETHER_STAR", "WITHER_SKELETON_SKULL"));
            put(EntityType.ENDER_DRAGON, Utils.recipe( "DRAGON_BREATH",AddUtils.probItemStackFactory(new ItemStack(Material.DRAGON_HEAD),5),new ProbItemStack( new ItemStack(Material.DRAGON_EGG),0.003)));
            put(EntityType.WARDEN, Utils.recipe("SCULK_CATALYST"));
            //实体
            put(EntityType.THROWN_EXP_BOTTLE, Utils.recipe("EXPERIENCE_BOTTLE"));
            put(EntityType.EGG, Utils.recipe("EGG"));
            put(EntityType.ENDER_PEARL, Utils.recipe("ENDER_PEARL"));
            put(EntityType.ENDER_CRYSTAL, Utils.recipe("END_CRYSTAL"));
            put(EntityType.ITEM_FRAME, Utils.recipe("ITEM_FRAME"));
            put(EntityType.GLOW_ITEM_FRAME, Utils.recipe("GLOW_ITEM_FRAME"));
            put(EntityType.PAINTING, Utils.recipe("PAINTING"));
            put(EntityType.BOAT, Utils.recipe("OAK_BOAT"));
            put(EntityType.CHEST_BOAT, Utils.recipe("OAK_CHEST_BOAT"));
            put(EntityType.MINECART, Utils.recipe("MINECART"));
            put(EntityType.MINECART_CHEST, Utils.recipe("CHEST_MINECART"));
            //put(EntityType.MINECART_COMMAND, Utils.recipe("COMMAND_BLOCK_MINECART"));
            put(EntityType.MINECART_FURNACE , Utils.recipe("FURNACE_MINECART"));
            put(EntityType.MINECART_HOPPER , Utils.recipe("HOPPER_MINECART"));
            put(EntityType.MINECART_TNT, Utils.recipe("TNT_MINECART"));
            put(EntityType.MINECART_MOB_SPAWNER, Utils.recipe("MINECART", AddUtils.probItemStackFactory( AddUtils.resolveItem( "SPAWNER"),1)));
            //友好生物
            put(EntityType.VILLAGER, Utils.recipe("PAPER", "BREWING_STAND"));
            put(EntityType.PIG, Utils.recipe("PORKCHOP"));
            put(EntityType.COW, Utils.recipe("BEEF", "LEATHER"));
            put(EntityType.MUSHROOM_COW, Utils.recipe("BEEF", "LEATHER",AddUtils.probItemStackFactory(  AddUtils.resolveItem("RED_MUSHROOM"),25)));
            put(EntityType.CAT, Utils.recipe("STRING"));
            put(EntityType.CHICKEN, Utils.recipe("CHICKEN", "FEATHER"));
            put(EntityType.COD, Utils.recipe("COD", AddUtils.probItemStackFactory( AddUtils.resolveItem( "BONE_MEAL"),5)));
            put(EntityType.DOLPHIN, Utils.recipe("COD"));
            put(EntityType.SHEEP, Utils.recipe("WHITE_WOOL", "MUTTON"));
            put(EntityType.HORSE, Utils.recipe("LEATHER", AddUtils.probItemStackFactory( AddUtils.resolveItem( "SADDLE"),5)));
            put(EntityType.DONKEY, Utils.recipe("LEATHER", AddUtils.probItemStackFactory( AddUtils.resolveItem( "SADDLE"),5)));
            put(EntityType.MULE, Utils.recipe("LEATHER", AddUtils.probItemStackFactory( AddUtils.resolveItem( "SADDLE"),5)));
            put(EntityType.WOLF, Utils.recipe("BONE"));
            put(EntityType.WANDERING_TRADER, Utils.recipe("LEAD"));
            put(EntityType.GLOW_SQUID, Utils.recipe("GLOW_INK_SAC"));
            put(EntityType.GOAT, Utils.recipe("GOAT_HORN"));
            put(EntityType.IRON_GOLEM, Utils.recipe("IRON_INGOT", "POPPY"));
            put(EntityType.LLAMA, Utils.recipe("LEATHER"));
            put(EntityType.TRADER_LLAMA, Utils.recipe("LEATHER"));
            put(EntityType.PANDA, Utils.recipe("BAMBOO", AddUtils.probItemStackFactory( AddUtils.resolveItem( "SLIME_BALL"),1)));
            put(EntityType.PARROT, Utils.recipe("FEATHER"));
            put(EntityType.POLAR_BEAR, Utils.recipe("COD", "SALMON"));
            put(EntityType.PUFFERFISH, Utils.recipe("PUFFERFISH", AddUtils.probItemStackFactory( AddUtils.resolveItem( "BONE_MEAL"),5)));
            put(EntityType.RABBIT, Utils.recipe("RABBIT", "RABBIT_HIDE", AddUtils.probItemStackFactory( AddUtils.resolveItem( "RABBIT_FOOT"),10)));
            put(EntityType.SALMON, Utils.recipe("SALMON", AddUtils.probItemStackFactory( AddUtils.resolveItem( "BONE_MEAL"),5)));
            put(EntityType.SKELETON_HORSE, Utils.recipe("BONE"));
            put(EntityType.SNOWMAN, Utils.recipe("SNOWBALL"));
            put(EntityType.SQUID, Utils.recipe("INK_SAC"));
            put(EntityType.STRIDER, Utils.recipe("STRING"));
            put(EntityType.TROPICAL_FISH, Utils.recipe("TROPICAL_FISH", AddUtils.probItemStackFactory( AddUtils.resolveItem( "BONE_MEAL"),5)));
            put(EntityType.TURTLE, Utils.recipe("SEAGRASS", "SCUTE", AddUtils.probItemStackFactory( AddUtils.resolveItem( "BOWL"),5)));
            put(EntityType.ZOMBIE_HORSE, Utils.recipe("SEAGRASS", "ROTTEN_FLESH"));
        }};
        List<World> worldlist=Bukkit.getWorlds();
        if(!worldlist.isEmpty()){
            World world=worldlist.get(0);
            Location loc=new Location(world,8,888,8);
            //load chunk
            Chunk chunk=loc.getChunk();
            for(EntityType type:EntityType.values()){
                if(!type.isSpawnable()){
                    continue;
                }
                Set<ItemStack> drops=Slimefun.getRegistry().getMobDrops().get(type);
                List<ItemStack> stackList=new ArrayList<>();
                if(drops!=null&&!drops.isEmpty()) {
                    for(ItemStack stack:drops){
                        SlimefunItem item=SlimefunItem.getByItem(stack);
                        if(item instanceof BasicCircuitBoard bcd){
                            stackList.add(stack.clone());
                        }
                        else if(item instanceof RandomMobDrop rmd){
                            stackList.add(AddUtils.probItemStackFactory(stack.clone(),rmd.getMobDropChance()));
                        }
                    }
                }
                ItemStack[] preDatas=preData.get(type);
                if(preDatas!=null){
                    stackList.addAll(Arrays.asList(preDatas));
                }
                ENTITY_DROPLIST.put(type,stackList.toArray(new ItemStack[stackList.size()]));
            }
        }

        Debug.logger("全部配方读取完成");
        Debug.logger("初始化配方类型显示界面...");
        //初始化对应物品
        for (RecipeType type:RECIPE_TYPES){
            try{
                ItemStack it=type.toItem();
                String ns=type.getKey().getNamespace().toUpperCase();
                String id=type.getKey().getKey().toUpperCase();
                if(it!=null){
                    SlimefunItemStack sfit=new SlimefunItemStack(id+"_ICON",AddUtils.addLore(it,"","&7配方类型:"+ns+":"+id));
                    RECIPETYPE_ICON.put(type, sfit  );
                }else{
                    ILLEGAL_RECIPETYPES.add(type);
                }
            }catch (Throwable e){
                ILLEGAL_RECIPETYPES.add(type);
            }
        }
        Debug.logger("初始化机器类型显示界面...");
        Debug.logger("警告: 如果你发现有机器没有检测到,说明他们并不在已知格式中或者并不在配置文件中");

        //解析含有recipe的机器类型
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            try{
                List<MachineRecipe> recipes=new ArrayList<>();
                if (item instanceof AContainer){
                    recipes=((AContainer)item).getMachineRecipes();
                }else if(item instanceof AbstractTransformer atransformer){//自己实现的生成器 但是用的普通机器的格式 需要转掉
                    List<MachineRecipe> recipeslst=atransformer.getMachineRecipes();
                    if(recipeslst!=null){
                        int i=recipeslst.size();
                        recipes=new ArrayList<>();
                        for(int j=0;j<i;j++){
                            recipes.add(MachineRecipeUtils.mgFromMachine(recipeslst.get(j)));
                        }
                    }
                }
                else if (//item instanceof org.lins.mmmjjkx.rykenslimefuncustomizer.objects.customs.machine.CustomMaterialGenerator ||
                       ReflectUtils.isExtendedFrom( item.getClass(),".CustomMaterialGenerator")){
                    //CustomMaterialGenerator
//                    //item instanceof CustomMaterialGenerator){//org.lins.mmmjjkx.rykenslimefuncustomizer.objects.customs.machine.CustomMaterialGenerator){
                    String methodName=null;
                    if(methodName==null){
                        try{
                            Object stack=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"output");
                            Object tick=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"tickRate");
                            List<Integer> chance=null;
                            try{
                                chance=(List<Integer>) ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"chances");
                            }catch (Throwable e){

                            }
                            recipes=new ArrayList<>();
                            ItemStack[] outputList;
                            if(stack instanceof List stacklist){
                                if(chance==null){
                                    if(!stacklist.isEmpty()){
                                        int len=stacklist.size();
                                        ItemStack[] output=new ItemStack[len];
                                        for (int i=0;i<len;++i){
                                            output[i]=(ItemStack) stacklist.get(i);
                                        }
                                        outputList=output;
                                    }else {
                                        outputList=new ItemStack[0];
                                    }
                                }else {
                                    if(!stacklist.isEmpty()){
                                        int len=stacklist.size();
                                        int len2=chance.size();
                                        ItemStack[] output=new ItemStack[len];
                                        for (int i=0;i<len;++i){
                                            output[i]=(ItemStack) stacklist.get(i);
                                            Integer chance_i=(i<len2)?chance.get(i):100;
                                            if(chance_i==null){
                                                chance_i=100;
                                            }
                                            output[i]=AddUtils.probItemStackFactory(output[i],chance_i);
                                        }
                                        outputList=output;
                                    }else {
                                        outputList=new ItemStack[0];
                                    }
                                }
                            }
                            else {
                                outputList=new ItemStack[]{(ItemStack) stack};
                            }
                            boolean chooseOne=Boolean.FALSE;
                            try{
                                chooseOne=(Boolean) ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"chooseOne");
                                if(chooseOne){
                                    outputList=new ItemStack[]{AddUtils.eqRandItemStackFactory(Arrays.stream(outputList).toList())};
                                }
                            }catch (Throwable e){

                            }
                            recipes.add(MachineRecipeUtils.mgFrom((Integer)tick,new ItemStack[0],outputList));
                            methodName="output";
                        }catch (Throwable e){ }
                    }
                    if(methodName==null){
                        try{
                            Object stack=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"generation");
                            Object tick=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"tickRate");
                            recipes=new ArrayList<>();
                            if(stack instanceof List stacklist){
                                if(!stacklist.isEmpty()){
                                    int len=stacklist.size();
                                    ItemStack[] output=new ItemStack[len];
                                    for (int i=0;i<len;++i){
                                        output[i]=(ItemStack) stacklist.get(i);
                                    }
                                    recipes.add(MachineRecipeUtils.mgFrom((Integer)tick,new ItemStack[0],output));
                                }
                            }
                            else {
                                recipes.add(MachineRecipeUtils.mgFrom((Integer) tick,new ItemStack[0],new ItemStack[]{(ItemStack) stack}));
                            }
                            methodName="generation";
                        }catch (Throwable e){ }
                    }
                } else if (resolveSpecialGenerators(item,recipes)) {

                }
                else {
                    boolean blst = false;
                    //if in class blacklist
                    for (Class classt : BLACKLIST_MACHINECLASS) {
                        if (classt.isInstance(item)) {
                            blst = true;
                            break;
                        }
                    }
                    if (!blst) {
                        Class<?> clazz = item.getClass();
                        switch (1) {
                            case 1:
                                Object machineRecipes = null;
                                String methodName = null;
                                String infinityMachineBlockRecipe = ".MachineBlockRecipe";
                                String infinitylib = "infinitylib";
                                try {
                                    if (methodName == null) {
                                        machineRecipes = ReflectUtils.invokeGetRecursively(item, Settings.METHOD, "getMachineRecipes");
                                        if (machineRecipes != null) {
                                            methodName = "getMachineRecipes() method";
                                        }
                                    }
                                    if (methodName == null) {
                                        machineRecipes = ReflectUtils.invokeGetRecursively(item, Settings.FIELD, "recipes");
                                        if (machineRecipes != null) {
                                            methodName = "recipes field";
                                        }
                                    }
                                    if (methodName == null) {
                                        machineRecipes = ReflectUtils.invokeGetRecursively(item, Settings.FIELD, "machineRecipes");
                                        if (machineRecipes != null) {
                                            methodName = "machineRecipes field";
                                        }
                                    }
                                    if (machineRecipes != null) {
                                        if (machineRecipes instanceof List) {
                                            for (Object machineRecipe : (List) machineRecipes) {
                                                //压缩配方,用于提供给机器
                                                if (machineRecipe instanceof MachineRecipe) {
                                                    recipes.add((MachineRecipe) machineRecipe);
                                                } else if (machineRecipe instanceof ItemStack) {//some of sf's shit machine
                                                    if (item instanceof Composter) {
                                                        int len = ((List<?>) machineRecipes).size();
                                                        for (int i = 0; i < len; i += 2) {
                                                            recipes.add(MachineRecipeUtils.stackFrom(4, new ItemStack[]{(ItemStack) ((List<?>) machineRecipes).get(i)}, new ItemStack[]{(ItemStack) ((List<?>) machineRecipes).get(i + 1)}));
                                                        }
                                                        break;
                                                    } else if (item instanceof Crucible) {
                                                        break;
                                                    } else break;
                                                } else if (machineRecipe instanceof AltarRecipe ar) {
                                                    List<ItemStack> inp = (ar).getInput();
                                                    List<ItemStack> inpCopy=new ArrayList<>();
                                                    if(!inp.isEmpty())
                                                        inpCopy.addAll(inp);
                                                    inpCopy.add(4, (ar).getCatalyst());
                                                    MachineRecipe rp = new MachineRecipe(9, inpCopy.toArray(new ItemStack[inpCopy.size()]), new ItemStack[]{((AltarRecipe) machineRecipe).getOutput()});
                                                    recipes.add(rp);
                                                } else if (machineRecipe.getClass().getName().endsWith(infinityMachineBlockRecipe) &&
                                                        machineRecipe.getClass().getName().contains(infinitylib)) {
                                                    MachineRecipe ip = resolveInfinityMachineBlockRecipe(machineRecipe, item);
                                                    if (ip != null) {
                                                        //  Debug.logger("recipe not null");
                                                        recipes.add(ip);
                                                    }
                                                } else {
                                                    throw new ClassCastException("wrong " + methodName + " return type , " +
                                                            "List of " + machineRecipe.getClass().getName());
                                                }
                                            }
                                            break;
                                        }
                                    }
                                } catch (Throwable e) {
                                    e.getMessage();
                                }
                            default:

                        }
                    }

                }
                if(!recipes.isEmpty()){
                    List<MachineRecipe> result=new ArrayList<>();
                    MachineRecipe res=null;
                    for(MachineRecipe machineRecipe : recipes) {
                        //先检测是不是rsc的
                        machineRecipe=transferRSCRecipes(machineRecipe);
                        if(machineRecipe ==null)
                            continue;
                        MGeneratorRecipe validGenerator=MachineRecipeUtils.tryGenerateMGFromMachine(machineRecipe);
                        if(validGenerator!=null){
                            res=validGenerator;
                        }else {
                            res=MachineRecipeUtils.stackFromMachine(machineRecipe);
                        }

                        result.add(res);

                    }
                    MACHINE_RECIPELIST.put(item,result);
                }

            }catch (Throwable e){
                Debug.logger("generate unexpected exception while registering recipes! :"+e.getMessage());
                e.printStackTrace();
            }
        }
        Debug.logger("尝试载入机器配方修改配置文件");
        try{
            List<String> enableAddons=ConfigLoader.INNER_MACHINES.getStringList("enable");
            if(!enableAddons.isEmpty()){
                for(String enableAddon : enableAddons){
                    loadMachineModification(ConfigLoader.INNER_MACHINES,enableAddon,false);
                }
            }
        }catch (Throwable w){

        }
        try{
            loadMachineModification(ConfigLoader.MACHINES,"recipe_modify",true);
        }catch (Throwable e){

        }
        //解析机器用电,并提供给StackMachine和stackGenerator
        //到时候还需要解析生成器配方和用电
        Debug.logger("注册堆叠机器的机器列表");
        //尝试获取
        for(Map.Entry<SlimefunItem,List<MachineRecipe>> e:MACHINE_RECIPELIST.entrySet()){
            SlimefunItem item=e.getKey();
            //黑名单 本附属非processor的machine 和 高级processor
            if(item instanceof ImportRecipes ir &&ir.isConflict()){
                continue;
            }
            int energyComsumption;
            List<MachineRecipe> result=e.getValue();
            if(MachineRecipeUtils.isGeneratorRecipe(result)){
                //剔除掉非AbstractTransformer的本附属机器
                if(!(item instanceof AbstractMachine&&(!(item instanceof AbstractTransformer)))){
                    energyComsumption=tryGetMachineEnergy(item);
                    STACKMGENERATOR_LIST.put(item,energyComsumption);
                }
            }
            else if(MachineRecipeUtils.isMachineRecipe(result)){
                //剔除掉非AbstractProcessor的本附属机器
                if(!(item instanceof AbstractMachine&&(!(item instanceof EMachine)&&!(item instanceof MTMachine)&&!(item instanceof AEMachine)))){
                    energyComsumption=tryGetMachineEnergy(item);
                    STACKMACHINE_LIST.put(item,energyComsumption);
                }
            }
        }
        //加载对堆叠机器列表的修改配置
        try{
            List<String> enableAddons=ConfigLoader.INNER_MACHINES.getStringList("enable");
            if(!enableAddons.isEmpty()){
                for(String enableAddon : enableAddons){
                    loadStackMachineConfig(ConfigLoader.INNER_MACHINES,enableAddon,false,false);
                }
            }
        }catch (Throwable w){

        }
        loadStackMachineConfig(ConfigLoader.MACHINES,"stack_type",true,true);

        Debug.logger("配方供应器工作完成, 耗时 "+(System.nanoTime()-a)+ " 纳秒");
    }
    static{
        Debug.logger("配方供应器开始初始化...");
        initRecipeSupportor();
    }
    public static MachineRecipe resolveInfinityMachineBlockRecipe(Object recipe, SlimefunItem machine){
        int ticks=0;
        try{
            Integer a=(Integer) ReflectUtils.invokeGetRecursively(machine,Settings.FIELD,"ticksPerOutput");
            if(a!=null){
                ticks=a-1;
            }
        }catch (Throwable a){}
        // }
        try{
            String[] item=(String[]) ReflectUtils.invokeGetRecursively(recipe,Settings.FIELD,"strings");
            int[] counts=(int[]) ReflectUtils.invokeGetRecursively(recipe,Settings.FIELD,"amounts");
            ItemStack stack=(ItemStack)ReflectUtils.invokeGetRecursively(recipe,Settings.FIELD,"output");
            ItemStack result;
            if((result=AddUtils.resolveRandomizedItemStack(stack))!=null){
                stack=result;
            }
            int len=item.length;
            ItemStack[] input=new ItemStack[len];
            for (int i=0;i<len;i++){
                input[i]=AddUtils.resolveItem(item[i]);
                if(input[i]==null){
                    return null;
                }
                input[i]= AddUtils.setCount(input[i],counts[i]);
            }
           // Debug.logger("return notnull recipe");
            return MachineRecipeUtils.stackFrom(ticks,input,new ItemStack[]{stack});
        }catch (Throwable e){return null;}
    }
    public static boolean resolveSpecialGenerators(SlimefunItem item,List<MachineRecipe> recipes){

        if(ReflectUtils.isExtendedFrom(item.getClass(),"GrowingMachine") ){
            int ticks=0;
            try{
                Integer a=(Integer) ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"ticksPerOutput");
                if(a!=null){
                    ticks=a-1;
                }
            }catch (Throwable a){
            }
            try{
                EnumMap<Material,ItemStack[]> map=(EnumMap<Material, ItemStack[]>) ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"recipes");
                for(Map.Entry<Material,ItemStack[]> entry:map.entrySet()){
                    recipes.add(MachineRecipeUtils.mgFrom(ticks,Utils.array(new ItemStack(entry.getKey())),entry.getValue()));
                }
            }catch (Throwable e){
                Debug.logger("Error in loading Slimefun Item %s StackMachineRecipe: %s".formatted(item.getId(),e.getMessage()));

                return false;
            }
            return true;
        }else if(ReflectUtils.isExtendedFrom(item.getClass(),".MaterialGenerator")){
            int ticks=1;
            try{
                int amount=(Integer) ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"speed");
                Material material=(Material) ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"material");
                recipes.add(MachineRecipeUtils.mgFrom(ticks,Utils.array(),Utils.array(new ItemStack(material,amount))));
            }catch (Throwable e){
                Debug.logger("Error in loading Slimefun Item %s StackMachineRecipe: %s".formatted(item.getId(),e.getMessage()));
                return false;
            }
            return true;
        }
        else if(ReflectUtils.isExtendedFrom(item.getClass(),".AbstractQuarry")){
            int ticks=0;
            try{
                ticks=(Integer) ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"delaySpeed");
            }catch (Throwable a){
            }
            try{
                Class supremeClass=item.getAddon().getClass();  //Class.forName("com.github.relativobr.supreme.Supreme");
                Object obj=ReflectUtils.invokeGetRecursively(null,supremeClass,Settings.METHOD,"getSupremeOptions");
                obj=ReflectUtils.invokeGetRecursively(obj,Settings.METHOD,"getCustomTickerDelay");
                int a=(Integer)obj;
                ticks*=a;
            }catch (Throwable e){
                Debug.logger("Error in loading Slimefun Item %s StackMachineRecipe: %s".formatted(item.getId(),e.getMessage()));
                return false;
            }
            try{
                Object output=ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"output");
                List outputlist=(List) ReflectUtils.invokeGetRecursively(output,Settings.FIELD,"outputItems");
                List<ItemStack > stacklist=new ArrayList<>();
                List<Integer> chancelist=new ArrayList<>();
                for(Object o:outputlist){
                    ItemStack a=(ItemStack) ReflectUtils.invokeGetRecursively(o,Settings.FIELD,"itemStack");
                    Integer t=(Integer) ReflectUtils.invokeGetRecursively(o,Settings.FIELD,"chance");
                    stacklist.add(a);
                    chancelist.add(t);
                }
                ItemStack outputGroup=AddUtils.randItemStackFactory(stacklist,chancelist);
                recipes.add(MachineRecipeUtils.mgFrom(ticks,new ItemStack[0],new ItemStack[]{outputGroup}));
            }catch (Throwable e){
                Debug.logger("Error in loading Slimefun Item %s StackMachineRecipe: %s".formatted(item.getId(),e.getMessage()));
                return false;
            }
        }
        else if(ReflectUtils.isExtendedFrom(item.getClass(),".Quarry")){
            int ticks=0;
            try {
                ticks=(Integer) ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"INTERVAL");
            }catch (Throwable e){
                Debug.logger("Error in loading Slimefun Item %s StackMachineRecipe: %s".formatted(item.getId(),e.getMessage()));
                return false;
            }
            int amount;
            try{
                amount=(Integer)ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"speed");
            }catch (Throwable e){
                Debug.logger("Error in loading Slimefun Item %s StackMachineRecipe: %s".formatted(item.getId(),e.getMessage()));
                return false;
            }
            Class OscillatorClass=SlimefunItem.getById("QUARRY_OSCILLATOR_DIAMOND").getClass();
            try{
                HashMap OSCILLATORS_MAP=(HashMap) ReflectUtils.invokeGetRecursively(null,OscillatorClass,Settings.FIELD,"OSCILLATORS");
                Material[] outputlist=(Material[]) ReflectUtils.invokeGetRecursively(item,Settings.FIELD,"outputs");
                List<ItemStack> outputItemlist= Arrays.stream(outputlist).map((material -> new ItemStack(material,amount))).toList();
                ItemStack outputRandGroup=AddUtils.eqRandItemStackFactory(outputItemlist);
                recipes.add(MachineRecipeUtils.mgFrom(ticks,new ItemStack[]{new ItemStack(Material.COBBLESTONE)},new ItemStack[]{outputRandGroup}));
                for(Object e:OSCILLATORS_MAP.values()){
                    try {
                        SlimefunItem oscillatorsInstance = (SlimefunItem) e;
                        double chance = (Double) ReflectUtils.invokeGetRecursively(oscillatorsInstance, Settings.FIELD, "chance");
                        ItemStack oscillatorsType=new ItemStack(oscillatorsInstance.getItem().getType(),amount);
                        int chanceToInt=(int)(chance*100);
                        ItemStack randOut=AddUtils.randItemStackFactory(new LinkedHashMap<>(){{
                            put(oscillatorsType,chanceToInt);
                            put(outputRandGroup,100-chanceToInt);
                        }});
                        recipes.add(MachineRecipeUtils.mgFrom(ticks,new ItemStack[]{oscillatorsInstance.getItem()},new ItemStack[]{randOut}));
                    }catch (Throwable E){
                        if(e instanceof SlimefunItem sf){
                            Debug.logger("Error in loading QUARRY_OSCILLATORS Item %s : %s".formatted(sf.getId(),E.getMessage()));
                        }else {
                            Debug.logger("Error in loading QUARRY_OSCILLATORS Item  : %s".formatted(E.getMessage()));
                        }
                    }
                }
                return true;
            }catch (Throwable e){
                Debug.logger("Error in loading Slimefun Item %s StackMachineRecipe: %s".formatted(item.getId(),e.getMessage()));
                return false;
            }

        }
        return false;
    }
    public static MGeneratorRecipe resolveGrowingMachineRecipe(Object recipe, SlimefunItem machine){
        return null;
    }

    private static void initUnshapedRecipes(RecipeType type) {
        List<MachineRecipe> recipes_test = PROVIDED_UNSHAPED_RECIPES.get(type);
        if(recipes_test == null|| recipes_test.size() == 0|| SUPPORTED_UNSHAPED_RECIPETYPE.contains(type)) {
            PROVIDED_UNSHAPED_RECIPES.put(type,new ArrayList<>());
            List<MachineRecipe> recipes = PROVIDED_UNSHAPED_RECIPES.get(type);
            for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
                RecipeType recipeType = item.getRecipeType();
                if (recipeType==type ) {

                    recipes.add(MachineRecipeUtils.stackFromRecipe(item)) ;
                }
            }

        }
    }
    //not finished yet
    public static void initMachinieRecipeWithRecipeType(List<MachineRecipe> machineRecipes,RecipeType... craftType){
        if(machineRecipes.isEmpty()||craftType!=null) {
            if(craftType.length<=0){
                return ;
            }
            else if(craftType.length==1){
                machineRecipes = RecipeSupporter.getStackedRecipes(craftType[0]);

            }else{
                machineRecipes=new ArrayList<>();
                for(RecipeType rt : craftType){
                    if(rt!=null)
                        machineRecipes.addAll(RecipeSupporter.getStackedRecipes(rt));
                }
            }
            if(machineRecipes==null) {
                machineRecipes = new ArrayList<>();
            }
        }

    }
    public static ItemStack getRecipeTypeIcon(RecipeType type){
        ItemStack icon=type.toItem();
        if(icon==null)return new ItemStack(Material.AIR);
        return icon;
    }

    public static void invokeMachineRecipes(){

    }
}
