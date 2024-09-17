package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import jdk.jshell.execution.Util;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Machines.AbstractProcessor;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogicReactor extends AbstractProcessor {
    protected final int[] BORDER=new int[]{0, 2, 4, 6,  8, 9, 10, 11, 12,  14, 15, 16, 17, 18,
            19,  21,  23, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,  38, 40, 42,  44};

    protected final int[] INPUT_SLOT=new int[]{1,3,5,7,37,39,41,43,20};
    protected final int[] OUTPUT_SLOT=new int[]{24};
    protected final int[] PROCESSOR_SLOT=new int[]{22};
    protected final int[] BORDER_IN=new int[] {19,12,30,14,32,25};
    protected final int[] BORDER_OUT=new int[] {10,28,21,23,16,34};
    protected final int[] BOOL_SLOT= Arrays.copyOfRange(getInputSlots(),0,8);
    protected final int[] COMMON_SLOT= Arrays.copyOfRange(getInputSlots(),8,getInputSlots().length);
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    protected final int processorTick;
    protected final int SWITCH_SLOT=13;
    protected final ItemStack SWITCH_OFF=new CustomItemStack(Material.SOUL_TORCH,"&6反应堆运行状态: &c关闭","&7点击开启反应堆");
    protected final ItemStack SWITCH_ON=new CustomItemStack(Material.REDSTONE_TORCH,"&6反应堆运行状态: &a开启","&7点击关闭反应堆","&c注意:关闭反应堆将丢失当前机器进度");
    public LogicReactor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material progressItem, int tick) {
        super(category,item,recipeType,recipe,progressItem,400,4000,null);
        this.processorTick = tick;

        this.setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow("&f机制",
                                "&7该机器拥有四对布尔输入槽,分别在&c同一列&7由彩色的玻璃板标出",
                                "&7这些槽位用于输入布尔组件,即 %s 和 %s".formatted(Language.get("Items.TRUE_.Name"),Language.get("Items.FALSE_.Name")),
                                "&7只有这些槽位的物品满足一定机制时,合成才会尝试进行"),null,
                        AddUtils.getInfoShow("&f机制",
                                "&7机器还有一个&c正常输入槽&7和&e正常输出槽&7,分别位于第三排的&e左侧&7和&e右侧空格,分别用于输入 [%s]和输出产物 ".formatted(Language.get("Items.LOGIGATE.Name")),
                                "&7当机器为空闲,四对布尔输入槽&c非空&7且满足右侧某个输入条件时",
                                "&7才会尝试&e消耗&7正常输入槽内的 [%s] 进行合成".formatted(Language.get("Items.LOGIGATE.Name")),
                                "&7警告:当机器尝试匹配输入条件进行合成时,四对布尔输入槽内的物品会被&c清空&7!",
                                "&7指示:当机器在合成进程中运行时,四对布尔输入槽内的物品不会被&c清空!",
                                "&7指示:不过玩家使用普通货运输入时,布尔槽位只会被输入布尔组件",
                                "&7指示:你可以关闭机器来停止布尔组件的消耗"
                        ),null

                )
        );
        this.machineRecipes=new ArrayList<>(){{
            add(MachineRecipeUtils.FromMachine(AddUtils.formatInfoMachineRecipe(Utils.array(AddItem.LOGIC),tick,
                    "&7当四对输入槽物品均为同样的布尔组件时","&7将会尝试生成 %s".formatted(Language.get("Items.LOGIC.Name")))));
            add(MachineRecipeUtils.FromMachine(AddUtils.formatInfoMachineRecipe(Utils.array(AddItem.NOLOGIC),tick,
                    "&7当四对输入槽物品均为相反的布尔组件时","&7将会尝试生成 %s".formatted(Language.get("Items.NOLOGIC.Name")))));
            add(MachineRecipeUtils.FromMachine(AddUtils.formatInfoMachineRecipe(Utils.array(AddItem.UNIQUE),tick,
                    "&7当四对输入槽物品中只有一对布尔组件相异","&7将会尝试生成 %s".formatted(Language.get("Items.UNIQUE.Name")))));
            add(MachineRecipeUtils.FromMachine(AddUtils.formatInfoMachineRecipe(Utils.array(AddItem.EXISTE),tick,
                    "&7当前三个材料的生成条件均不满足","&7将会尝试生成 %s".formatted(Language.get("Items.EXISTE.Name")))));
        }};
    }
    public void constructMenu(BlockMenuPreset preset) {
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
        border = PROCESSOR_SLOT;
        len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {
            preset.addItem(border[var4], MenuUtils.PROCESSOR_NULL , ChestMenuUtils.getEmptyClickHandler());
        }
    }
    public void newMenuInstance(@Nonnull BlockMenu inv, @Nonnull Block block){
        if(inv.getItemInSlot(SWITCH_SLOT)==null){
            inv.replaceExistingItem(SWITCH_SLOT,SWITCH_OFF);
        }
        inv.addMenuClickHandler(SWITCH_SLOT,((player, i, itemStack, clickAction) -> {
            if(itemStack.getType()==Material.SOUL_TORCH){
                inv.replaceExistingItem(SWITCH_SLOT,SWITCH_ON);
            }else{
                inv.replaceExistingItem(SWITCH_SLOT,SWITCH_OFF);
                this.processor.endOperation(block);
            }
            return false;
        }));
    }
    public int checkLogic(BlockMenu inv,int[] inputs){
        int code1=0;
        int code2=0;
        int cnt=0;
        ItemStack it;
        for (int i=0;i<4;++i){
            it=inv.getItemInSlot(inputs[i]);
            if (it!=null){

                if(CraftUtils.matchItemStack(it, AddItem.TRUE_,false)){
                    code1=2*code1+1;
                    ++cnt;
                }else if (CraftUtils.matchItemStack(it, AddItem.FALSE_,false)){
                    code1=2*code1;
                }else {
                    cnt=-64;
                }
                it.setAmount(0);
            }else  cnt=-64;
        }
        for (int i=4;i<8;++i){
            it=inv.getItemInSlot(inputs[i]);
            if (it!=null){
                if(CraftUtils.matchItemStack(it, AddItem.TRUE_,false)){
                    code2=2*code2+1;
                    ++cnt;
                }else if (CraftUtils.matchItemStack(it, AddItem.FALSE_,false)){
                    code2=2*code2;

                }else {

                    cnt=-64;
                }
                it.setAmount(0);
            }else cnt=-64;
        }
        if(cnt<0){
            return -1;
        }
        //机制判断
        if(code1==code2){
            return 0;
        }else{
           int code=code1^code2;
           if(code==15){//均不同
               return 1;
           }else if (code==1||code==2||code==4||code==8){//仅一位
               return 2;
           }else return 3;//else
        }
    }
    public boolean conditionHandle(Block b,BlockMenu menu){
        if(menu.getItemInSlot(SWITCH_SLOT).getType()==Material.SOUL_TORCH){
            return false;
        }else return super.conditionHandle(b,menu);
    }
    protected Pair<MachineRecipe, ItemConsumer[]> findNextRecipe(BlockMenu inv,int[] inputs,int[] outputs){
        ItemStack iv=inv.getItemInSlot(inputs[0]);
        if(iv!=null&&CraftUtils.matchItemStack(iv, AddItem.LOGIGATE,false)){
            int code=checkLogic(inv,BOOL_SLOT);
            if(code==-1){
                return null;
            }else{
                MachineRecipe result=getMachineRecipes().get(code);
                ItemConsumer[] outCon=CraftUtils.countOneOutput(inv,outputs,result);
                if(outCon==null){
                    return null;
                }
                iv.setAmount(iv.getAmount()-1);
                return new Pair<>(result,outCon);
            }
        }
        return null;

    }
    @Override
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        SimpleCraftingOperation currentOperation = (SimpleCraftingOperation)this.processor.getOperation(b);
        ItemConsumer[] fastCraft=null;
        if(currentOperation==null){

            Pair<MachineRecipe, ItemConsumer[]> nextP = findNextRecipe(inv,COMMON_SLOT,getOutputSlots());
            if (nextP != null) {

                MachineRecipe next =nextP.getFirstValue();
                ItemConsumer[] outputInfo=nextP.getSecondValue();
                if(next.getTicks()>0){
                    currentOperation = new SimpleCraftingOperation(outputInfo,next.getTicks());
                    this.processor.startOperation(b, currentOperation);
                }
                else {
                    fastCraft = nextP.getSecondValue();
                }
            }else{//if currentOperation ==null return  , cant find nextRecipe
                if(inv.hasViewer()){
                    for(int var4 = 0; var4 <PROCESSOR_SLOT.length; ++var4) {
                        inv.replaceExistingItem(PROCESSOR_SLOT[var4],MenuUtils.PROCESSOR_NULL );
                    }
                }
                return ;
            }
        }

        progressorCost(b,inv);
        if (fastCraft!=null) {
            CraftUtils.updateOutputMenu(fastCraft,inv);
        }else if(currentOperation.isFinished()){
            ItemConsumer[] var4=currentOperation.getResults();
            CraftUtils.forcePush(var4,inv,getOutputSlots());
            if(inv.hasViewer()){
                for(int var5 = 0; var5 <PROCESSOR_SLOT.length; ++var5) {
                    inv.replaceExistingItem(PROCESSOR_SLOT[var5],MenuUtils.PROCESSOR_NULL );
                }
            }
            this.processor.endOperation(b);
        }
        else{
            if(inv.hasViewer()){
                for(int var5 = 0; var5 <PROCESSOR_SLOT.length; ++var5) {
                    this.processor.updateProgressBar(inv, PROCESSOR_SLOT[var5], currentOperation);
                }
            }
            currentOperation.addProgress(1);
        }
    }
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
        if(flow==ItemTransportFlow.WITHDRAW)
            return getOutputSlots();
        if(item!=null&&item.getType()==Material.MUSIC_DISC_5){
            return BOOL_SLOT;
        }else {
            return COMMON_SLOT;
        }
    }
}
