package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SequenceCraftingOperation;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSequenceProcessor extends AbstractMachine implements MachineProcessHolder<SequenceCraftingOperation> {
    protected final int[] BORDER=new int[]{
            0,1,2,3,4,5,6,7,8,
            12,21,30,14,23,32,31,
            36,37,38,39,40,41,42,43,44
    };
    protected final int[] BORDER_IN=new int[]{
            9,10,11,18,20,27,28,29
    };
    protected final int[] BORDER_OUT=new int[]{
            15,16,17,24,26,33,34,35
    };
    protected final int[] INPUT_SLOT=new int[]{19};
    protected final int[] OUTPUT_SLOT=new int[]{25};
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public  int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }

    protected final ItemStack progressbar;
    protected final MachineProcessor<SequenceCraftingOperation> processor;
    protected int PROCESSOR_SLOT=22;
    protected int INFO_SLOT=13;
    protected int CLEAN_SLOT=31;
    protected ItemStack[] CLEAN_ITEM=new CustomItemStack[]{
            new CustomItemStack(SlimefunItems.TRASH_CAN,"&3开启输入槽清空模式","&7当输入槽物品无法匹配有序输入配方时,会清空输入槽","&7当前状态: &c关闭"),
            new CustomItemStack(SlimefunItems.TRASH_CAN,"&3关闭输入槽清空模式","&7当输入槽物品无法匹配有序输入配方时,会清空输入槽","&7当前状态: &a开启")
    };
    protected ItemStack INFO_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6机制","&b该机器类型用于合成阶段输入配方","&b放入配方序列的第一个物品开启配方","&b按顺序满足配方每阶段输入需求才会进入下一阶段","&b完成全部阶段机器会尝试输出结果");
    public AbstractSequenceProcessor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                     ItemStack progressItem, int energyConsumption, int energyBuffer,
                                     LinkedHashMap<Object, Integer> customRecipes) {
        super(category,item,recipeType,recipe,energyBuffer,energyConsumption);
        this.progressbar=new ItemStack(progressItem);
        this.processor = new MachineProcessor(this);
        this.processor.setProgressBar(progressbar);
        if(customRecipes!=null) {
            this.machineRecipes = new ArrayList<>(customRecipes.size());
            var customRecipes2 = AddUtils.buildRecipeMap(customRecipes);
            for (Map.Entry<Pair<ItemStack[], ItemStack[]>, Integer> recipePiece : customRecipes2.entrySet()) {
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(MachineRecipeUtils.sequenceFromMachine(
                        new MachineRecipe(recipePiece.getValue(), recipePiece.getKey().getFirstValue(), recipePiece.getKey().getSecondValue())
                ));
            }
        }else
        {
            this.machineRecipes=new ArrayList<>();
        }
    }
    public List<MachineRecipe> getMachineRecipes(){
        return this.machineRecipes;
    }
    public MachineProcessor<SequenceCraftingOperation> getMachineProcessor() {
        return this.processor;
    }
    public ItemStack getProgressBar(){
        return this.progressbar;
    }
    public void constructMenu(BlockMenuPreset preset){
        //空白背景 禁止点击
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        //输入槽边框
        border = BORDER_IN;
        len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        //输出槽边框
        border = BORDER_OUT;
        len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        //空白边框
        preset.addItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INFO_SLOT,INFO_ITEM,ChestMenuUtils.getEmptyClickHandler());
    }
    public void newMenuInstance(BlockMenu inv,Block b){
        int clean=DataCache.getCustomData(inv.getLocation(),"clean",0);
        inv.replaceExistingItem(CLEAN_SLOT,CLEAN_ITEM[clean]);
        inv.addMenuClickHandler(CLEAN_SLOT,((player, i, itemStack, clickAction) -> {
            int cleanCode=1-DataCache.getCustomData(inv.getLocation(),"clean",0);
            inv.replaceExistingItem(CLEAN_SLOT,CLEAN_ITEM[cleanCode]);
            DataCache.setCustomData(inv.getLocation(),"clean",cleanCode);
            return false;
        }));
    }
    public void cleanInputs(BlockMenu inv){
        int[] slots=getInputSlots();
        for (int i=0;i<slots.length;++i){
            inv.replaceExistingItem(slots[i],null);
        }
    }
    //TODO 增加pusher成员 使用成员进行process
    //TODO 以便子类修改和调控
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        SequenceCraftingOperation currentOperation = (SequenceCraftingOperation)this.processor.getOperation(b);

        if(currentOperation==null){
            Pair<MachineRecipe,ItemConsumer> nextP=   CraftUtils.findNextSequenceRecipe(inv,getInputSlots(),getMachineRecipes(),true,CraftUtils.getpusher, Settings.SEQUNTIAL);
            if(nextP==null){
                if(inv.hasViewer()){
                    inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
                }//满载清空
                int clear=DataCache.getCustomData(data,"clean",0);
                if(clear!=0){
                    cleanInputs( inv);
                }
                return ;
            }else {
                MachineRecipe next =nextP.getFirstValue();
                currentOperation = new SequenceCraftingOperation(next);
                this.processor.startOperation(b, currentOperation);
            }
        }
        progressorCost(b,inv);
        if(currentOperation.isFinished()){
            if(CraftUtils.pushItems(currentOperation.getResults(),inv,getOutputSlots())){
                if(inv.hasViewer()){
                    inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
                }
                this.processor.endOperation(b);
            }else {
                if(inv.hasViewer()){
                    inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_SPACE);
                }
            }
        }else {
            ItemConsumer fastNext=currentOperation.getRequiredItem();
            if(fastNext.getAmount()>0){
                int[] slots=getInputSlots();
                ItemPusher[] inputers=new ItemPusher[slots.length];
                for(int i=0;i<slots.length;i++){
                    inputers[i]=CraftUtils.getpusher.get(Settings.INPUT,inv,slots[i]);
                }
                if(!CraftUtils.matchSequenceRecipeTarget(inputers,fastNext)){
                    //没有更改 触发清除，清除输入槽内全部物品
                    int clear=DataCache.getCustomData(data,"clean",0);
                    if(clear!=0){
                        cleanInputs(inv);
                    }
                    //显然没动 也不需要刷新
                }else {
                    //同步关联的物品，即消耗
                    fastNext.updateItems(inv,Settings.GRAB);
                    //只清除记录，不同步数据
                    fastNext.clearRelated();
                    //更新界面再+1


                }
                //进度加一
            }
            if(fastNext.isDirty())
                currentOperation.addProgress(1);
                if(inv.hasViewer()){
                    inv.replaceExistingItem(PROCESSOR_SLOT,currentOperation.getDisplays());
                }//记得保证这玩意先于getDisplay 否则会数组越界

        }
    }
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e,menu);
        AbstractSequenceProcessor.this.processor.endOperation(menu.getLocation());
    }


}
