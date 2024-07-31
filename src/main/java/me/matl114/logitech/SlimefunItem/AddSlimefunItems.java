package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.SlimefunItem.Items.AntiGravityBar;
import me.matl114.logitech.SlimefunItem.Items.FIrstCustomItem;
import me.matl114.logitech.SlimefunItem.Machines.AutoMachines.*;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.FinalManual;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.ManualCrafter;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.MultiBlockManual;
import me.matl114.logitech.SlimefunItem.Machines.OtherMachines.TestMachine;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.TestWorkBench;
import me.matl114.logitech.SlimefunItem.Storage.StorageMachines.InputPort;
import me.matl114.logitech.SlimefunItem.Storage.StorageMachines.OutputPort;
import me.matl114.logitech.SlimefunItem.Storage.StorageMachines.Singularity;
import me.matl114.logitech.SlimefunItem.Storage.TestStorageUnit;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.EqProRandomStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * register main
 */
public class AddSlimefunItems {
    public static void registerSlimefunItems() {
        Debug.logger("注册附属物品...");
        Debug.logger("注册附属机器...");
    }
    public static final SlimefunAddon instance= MyAddon.getInstance();
    public static SlimefunItem register(SlimefunItem item){
        item.register(instance);
        return item;
    }
    private static Object mkP(Object v1,Object v2){
        return new Pair(v1,v2);
    }
    private static Object[] mkl(Object ... v){
        return Arrays.stream(v).toArray();
    }
    private static LinkedHashMap<Object,Integer> mkMp(Object ... v){
        return null;
    }
    public static final  SlimefunItem MATL114 =register( new SlimefunItem( AddGroups.author,AddSlimefunItemStack.MATL114, RecipeType.NULL,
            new ItemStack[] {
                    null,new ItemStack( Material.DIAMOND), null,
                    new ItemStack( Material.DIAMOND),new ItemStack( Material.COMMAND_BLOCK),new ItemStack( Material.DIAMOND) ,
                    null,new ItemStack( Material.DIAMOND),null
            }
    ));
    public static final SlimefunItem CUSTOM1=register(new FIrstCustomItem(AddGroups.author,AddSlimefunItemStack.CUSTOM1,RecipeType.NULL, AddUtils.NULL_RECIPE.clone()));
    public static final SlimefunItem MACHINE1=register(new OEMachine(AddGroups.author,AddSlimefunItemStack.MACHINE1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
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
    public static final  SlimefunItem MACHINE2=register(new TEMachine(AddGroups.author,AddSlimefunItemStack.MACHINE2,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
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
    public static  final  SlimefunItem SMG1=register(new SMGenerator(AddGroups.author,AddSlimefunItemStack.SMG1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),3,114514,0,
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

            ));
    public static final   SlimefunItem MMG1=register(new MMGenerator(AddGroups.author,AddSlimefunItemStack.MMG1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),3,114514,0,
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
    public static final  SlimefunItem TESTER2=register(new SMGenerator(AddGroups.author,new SlimefunItemStack("TESTER2",new ItemStack(Material.DIAMOND_ORE),"测试机","测试寄"),
            RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),12,0,0, (Object)(new ItemStack(Material.DIAMOND,1145))
           ,null,new ItemStack(Material.DIAMOND_CHESTPLATE)
            ));
    public static final  SlimefunItem MACHINE3=register(new EMachine(AddGroups.author,AddSlimefunItemStack.MACHINE3,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
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
    public static final  SlimefunItem MACHINE4=register(new AEMachine(AddGroups.author,AddSlimefunItemStack.MACHINE4,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
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
    public static final  SlimefunItem MANUAL1=register(new ManualCrafter(AddGroups.author,AddSlimefunItemStack.MANUAL1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
           1919,810,RecipeType.ENHANCED_CRAFTING_TABLE));
    public static final  SlimefunItem MANUAL_MULTI=register(new ManualCrafter(AddGroups.author,AddSlimefunItemStack.MANUAL_MULTI,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            1919,810,RecipeType.MULTIBLOCK));
    public static final  SlimefunItem MANUAL_KILL=register(new ManualCrafter(AddGroups.author,AddSlimefunItemStack.MANUAL_KILL,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            1919,810,RecipeType.MOB_DROP));
    public static final  SlimefunItem AUTOSMELTING1=register(new AdvanceCrafter(AddGroups.author,AddSlimefunItemStack.AUTOSMELTING1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            Material.FLINT_AND_STEEL,1919,810,RecipeType.SMELTERY));
    public static final SlimefunItem SINGULARITY=register(new Singularity(AddGroups.author,AddSlimefunItemStack.STORAGE_SINGULARITY,RecipeType.NULL,AddUtils.NULL_RECIPE));

    public static final SlimefunItem INPORT=register(new InputPort(AddGroups.author,AddSlimefunItemStack.INPORT,RecipeType.NULL,AddUtils.NULL_RECIPE,0,0));

    public static final SlimefunItem OUTPORT=register(new OutputPort(AddGroups.author,AddSlimefunItemStack.OUTPORT,RecipeType.NULL,AddUtils.NULL_RECIPE,0,0));

    public static final SlimefunItem TESTUNIT1=register(new TestStorageUnit(AddGroups.author,AddSlimefunItemStack.TESTUNIT1,RecipeType.NULL,AddUtils.NULL_RECIPE,0,0));

    public static final SlimefunItem AUTO_SPECIAL= register(new SpecialCrafter(AddGroups.author,AddSlimefunItemStack.AUTO_SPECIAL, RecipeType.NULL, AddUtils.NULL_RECIPE.clone()
                    , Material.KNOWLEDGE_BOOK,9,810,1919));
    public static final SlimefunItem AUTO_MULTIBLOCK= register(new MultiBlockManual(AddGroups.author,AddSlimefunItemStack.AUTO_MULTIBLOCK, RecipeType.NULL, AddUtils.NULL_RECIPE.clone()
            ,810,1919));
    public static final SlimefunItem ANTIGRAVITY=register(new AntiGravityBar(AddGroups.author,AddSlimefunItemStack.ANTIGRAVITY,RecipeType.NULL,AddUtils.NULL_RECIPE.clone()));

    public static final SlimefunItem WORKBENCH1=register(new TestWorkBench(AddGroups.author,AddSlimefunItemStack.WORKBENCH1,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            1919,810,
                new LinkedHashMap<>(){{
                    put(mkP(
                            mkl(null,"2COPPER_DUST",null,AddSlimefunItems.MATL114,"4DIAMOND",AddSlimefunItems.CUSTOM1,null,"3IRON_DUST",null),
                            mkl("5COMMAND_BLOCK")
                    ),0);
                }}
            ));
    public static final SlimefunItem FINAL_MANUAL=register(new FinalManual(AddGroups.author,AddSlimefunItemStack.FINAL_MANUAL,RecipeType.NULL,AddUtils.NULL_RECIPE.clone(),
            1919,810));
}
