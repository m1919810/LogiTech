package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.SlimefunItem.Machines.AbstractTransformer;
import me.matl114.logitech.SlimefunItem.Machines.MultiCraftType;
import me.matl114.logitech.Utils.*;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import net.guizhanss.guizhanlib.minecraft.helper.inventory.ItemStackHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StackMGenerator extends MMGenerator implements MultiCraftType {
    protected final int[] BORDER=new int[]{
            6,7,8,12,14,15,17,21,23,24,25,26
    };
    protected final int [] INPUT_SLOT = new int[]{
            0,1,2,9,10,11,18,19,20
    };
    protected final int [] OUTPUT_SLOTS=new int[]{
            27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53
    };
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    protected static List<SlimefunItem> BW_LIST;
    protected static int[] BW_LIST_ENERGYCOMSUME;
    protected static int BWSIZE;
    protected final int MACHINE_SLOT=13;
    protected final int MINFO_SLOT=16;
    protected final int INFO_SLOT=4;
    protected final ItemStack INFO_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&b机制",
            "&6将要模拟的机器放在下方槽位","&6机器会进行模拟,其中","&7<并行处理数>=<机器数*工作效率>","&7<耗电数>=<机器数量*单个机器耗电/工作效率>","&6有关高级机器和并行处理数的信息,请见粘液书<版本与说明>","&6支持的机器可以在粘液书<通用机器类型大全>"
            ,"&6或者左边按钮查看","&6机器支持的配方可以点击右侧按钮查看");
    protected final ItemStack MINFO_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c机器信息","&7待机中");
    protected final int MACHINEMENU_SLOT=3;
    protected final int RECIPEMENU_SLOT=5;
    protected final ItemStack MACHINEMENU_ICON=new CustomItemStack(Material.BLAST_FURNACE,"&b该机器支持的机器列表","&6点击打开菜单");
    protected final ItemStack RECIPEMENU_ICON=new CustomItemStack(Material.KNOWLEDGE_BOOK,"&b当前模拟的机器配方列表","&6点击打开菜单");
    protected ItemStack getInfoItem(int craftlimit,int energyCost,int charge,double efficiency,String name){
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a机器信息","&7当前模拟的机器名称: %s".formatted(name),
                "&7当前并行处理数: %-3d".formatted(craftlimit),"&7当前每刻耗电量: %sJ/t".formatted(AddUtils.formatDouble(energyCost)),"&7当前电量: %sJ".formatted(AddUtils.formatDouble(charge)),
                "&7当前工作效率: %s".formatted(AddUtils.getPercentFormat(efficiency)));
    }
    protected ItemStack getInfoOffItem(int craftlimit ,int energyCost,int charge,String name){
        return new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c机器信息","&c缺少电力!","&7当前模拟的机器名称: %s".formatted(name),
                "&7当前并行处理数: %-3d".formatted(craftlimit),"&7当前每刻耗电量: %sJ/t".formatted(AddUtils.formatDouble(energyCost)),"&7当前电量: %sJ".formatted(AddUtils.formatDouble(charge)));
    }

    protected double efficiency=1.0;
    static{
        SchedulePostRegister.addPostRegisterTask(()->{
            getMachineList();
        });
    }
    public StackMGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                           int time, int energybuffer, int energyConsumption) {
        super(itemGroup, item, recipeType, recipe, time, energybuffer, energyConsumption, new LinkedHashMap<>());
        this.PROCESSOR_SLOT=22;
        String[] lores=new String[]{"&7下侧为输出槽,左侧为输入检测槽","&a输入检测槽中的物品不会被消耗!","&e生产数量只与堆叠机器的数目有关,与输入检测数目无关","&e生产时间为原配方生产时间"};
        AddUtils.setLore(this.INFO_WORKING,lores);
        AddUtils.setLore(this.INFO_NULL,lores);
    }
    public static List<SlimefunItem> getMachineList(){
        if(BW_LIST==null){
            RecipeSupporter.init();
            BWSIZE=RecipeSupporter.STACKMGENERATOR_LIST.size();
            BW_LIST=new ArrayList<>(BWSIZE+1);
            BW_LIST_ENERGYCOMSUME=new int[BWSIZE];
            int i=0;
            for(Map.Entry<SlimefunItem,Integer> e:RecipeSupporter.STACKMGENERATOR_LIST.entrySet()){
                BW_LIST.add(e.getKey());
                BW_LIST_ENERGYCOMSUME[i]=e.getValue();
                ++i;
            }
        }
        return BW_LIST;
    }
    public static final int getListSize(){
        if(BW_LIST==null||BWSIZE==0){
            BWSIZE=getMachineList().size();
        }
        return BWSIZE;
    }
    public static int getEnergy(int index){
        if(BW_LIST_ENERGYCOMSUME==null||BW_LIST_ENERGYCOMSUME.length==0){
            getMachineList();
        }
        return BW_LIST_ENERGYCOMSUME[index];
    }
    public void addInfo(ItemStack stack){

    }
    public MachineRecipe getRecordRecipe(Location loc){
        return getRecordRecipe(DataCache.safeLoadBlock(loc));
    }
    public MachineRecipe getRecordRecipe(SlimefunBlockData data){
        List<MachineRecipe> lst=getMachineRecipes(data);
        int size=lst.size();
        if(size>0){
            int index=DataCache.getLastRecipe(data);
            if(index>=0&&index<size){
                return lst.get(index);
            }
        }
        return null;
    }
    public void constructMenu(BlockMenuPreset preset) {
        //空白背景 禁止点击
        preset.setSize(54);
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(MACHINEMENU_SLOT,MACHINEMENU_ICON);
        preset.addItem(INFO_SLOT,INFO_ITEM,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(RECIPEMENU_SLOT,RECIPEMENU_ICON);
        preset.addItem(MINFO_SLOT,MINFO_ITEM_OFF,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(PROCESSOR_SLOT, this.INFO_NULL, ChestMenuUtils.getEmptyClickHandler());
    }
    public void newMenuInstance(BlockMenu inv, Block block){
        inv.addMenuOpeningHandler((player -> {
            updateMenu(inv,block, Settings.RUN);
        }));
        inv.addMenuCloseHandler((player -> {
            updateMenu(inv,block,Settings.RUN);
        }));
        inv.addMenuClickHandler(RECIPEMENU_SLOT,((player, i, itemStack, clickAction) -> {
            int index=MultiCraftType.getRecipeTypeIndex(inv.getLocation());
            if(index>=0&&index<getListSize()){
                SlimefunItem item=getMachineList().get(index);

                MenuUtils.createMRecipeListDisplay(item.getItem(),RecipeSupporter.MACHINE_RECIPELIST.get(item),
                        ((player1, i1, itemStack1, clickAction1) -> {
                            inv.open(player1);
                            return false;
                        })
                ).open(player);
            }else{
                player.sendMessage(ChatColors.color("&e您所放置的机器为空或者为不支持的机器"));
                // player.sendMessage();
            }
            return false;
        }));
        inv.addMenuClickHandler(MACHINEMENU_SLOT,((player, i, itemStack, clickAction) -> {
            MenuUtils.createMachineListDisplay(getMachineList(),((player1, i1, itemStack1, clickAction1) -> {
                inv.open(player1);
                return false;
            })).open(player);
            return false;
        }));
        updateMenu(inv,block,Settings.INIT);
    }
    public void updateMenu(BlockMenu inv, Block block, Settings mod){
        SlimefunBlockData data= DataCache.safeLoadBlock(inv.getLocation());
        ItemStack it=inv.getItemInSlot(MACHINE_SLOT);
        SlimefunItem sfitem=SlimefunItem.getByItem(it);
        int index=MultiCraftType.getRecipeTypeIndex(data);
        if(sfitem!=null){
            SlimefunItem historyMachine;
            if(index>=0&&index<getListSize()){
                historyMachine= getMachineList().get(index);
            }else {
                historyMachine=null;
            }
            if(sfitem==historyMachine){
                //和历史机器一样
                return;
            }else {
                int size=getListSize();
                List<SlimefunItem> lst=getMachineList();
                for(int i=0;i<size;++i){
                    if(sfitem==lst.get(i)){
                        //是该机器,设置下标，都不用查了 肯定不一样
                        MultiCraftType.forceSetRecipeTypeIndex(data,i);
                        int charge=getEnergy(i);
                        DataCache.setCustomData(data,"mae",charge==0?energyConsumption:charge);
                        DataCache.setCustomData(data,"tick",-1);
                        return;
                    }
                }
            }//找不到,给你机会你不重用啊
        }
        if(index!=-1)//即将变成-1,不一样才变,不用重复查询
            MultiCraftType.forceSetRecipeTypeIndex(data,-1);
        DataCache.setCustomData(data,"mae",0);
    }
    public void processorCost(Block b,BlockMenu inv){
        Location loc=inv.getLocation();
        int charge=DataCache.getCustomData(loc,"mae",energyConsumption);
        int craftLimit=getCraftLimit(b,inv);
        int consumption=(int)(Math.min((craftLimit*charge)/efficiency,this.energybuffer));
        this.removeCharge(loc,consumption);
    }
    public int getCraftLimit(Block b,BlockMenu inv){
        return inv.getItemInSlot(MACHINE_SLOT).getAmount();
    }
    public int getCraftLimit(SlimefunBlockData data){ return data.getBlockMenu().getItemInSlot(MACHINE_SLOT).getAmount(); }
    public List<MachineRecipe> getMachineRecipes() {
        return new ArrayList<>();
    }
    public List<MachineRecipe> getMachineRecipes(Block b, BlockMenu inv){
        return getMachineRecipes(DataCache.safeLoadBlock(inv.getLocation()));
    }
    public List<MachineRecipe> getMachineRecipes(SlimefunBlockData data){
        int index= MultiCraftType.getRecipeTypeIndex(data);
        if(index>=0&&index<getListSize()){
            List<MachineRecipe> lst= RecipeSupporter.MACHINE_RECIPELIST.get(getMachineList().get(index ));
            return lst!=null?lst:new ArrayList<>();
        }
        return new ArrayList<>();
    }
    public void tick(Block b, @Nullable BlockMenu inv, SlimefunBlockData data, int tickCount){
        //首先 加载
        if(inv.hasViewer()){
            updateMenu(inv,b,Settings.RUN);
        }
        int index=MultiCraftType.getRecipeTypeIndex(data);
        if(index>=0&&index<getListSize()){//有效机器
            int charge=DataCache.getCustomData(data,"mae",energyConsumption);
            int craftLimit=getCraftLimit(b,inv);
            int consumption=(int)(Math.min((craftLimit*charge)/efficiency,this.energybuffer));
            int energy=this.getCharge(inv.getLocation(),data);
            if(energy>consumption){
                if(inv.hasViewer()){
                    inv.replaceExistingItem(MINFO_SLOT,getInfoItem(craftLimit,consumption,energy,this.efficiency, ItemStackHelper.getDisplayName(inv.getItemInSlot(MACHINE_SLOT))));

                }
                int tick=DataCache.getCustomData(data,"tick",-1);
                if(tick<=0){
                    process(b,inv,data);
                    if (inv.hasViewer()){
                        inv.replaceExistingItem(this.PROCESSOR_SLOT,this.INFO_NULL);
                    }
                }else{
                    DataCache.setCustomData(data,"tick",tick-1);
                    if(inv.hasViewer()){
                        inv.replaceExistingItem(this.PROCESSOR_SLOT,this.INFO_WORKING);
                    }
                }
            }else {
                //没电
                if(inv.hasViewer()){
                    inv.replaceExistingItem(MINFO_SLOT,getInfoOffItem(craftLimit,consumption,energy,ItemStackHelper.getDisplayName(inv.getItemInSlot(MACHINE_SLOT))));
                }
            }

        }else {
            DataCache.setCustomData(data,"tick",-1);
            if(inv.hasViewer()){
                inv.replaceExistingItem(MINFO_SLOT,MINFO_ITEM_OFF);
            }
        }
    }
    public void onBreak(BlockBreakEvent e, BlockMenu inv){
        if(inv!=null){
            Location loc=inv.getLocation();
            inv.dropItems(loc,MACHINE_SLOT);
        }
        super.onBreak(e,inv);

    }

}
