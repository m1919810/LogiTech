package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import me.matl114.logitech.Items.AddItems;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AddSlimefunItemStack {
    public static void registerItemStack(){

    }
    public static final SlimefunItemStack MATL114 =
            AddUtils.themed("MATL114", AddItems.MATL114, AddUtils.Theme.ITEM1,"MATL114", "the author");
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
}
