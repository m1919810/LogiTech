package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.AddSlimefunItems;
import me.matl114.logitech.SlimefunItem.Machines.AbstractProcessor;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BoolGenerator extends AbstractProcessor {
    protected final int[] BORDER=new int[]{0,  2, 3, 5, 6,  8,9, 17, 18, 20, 21,  23, 24,26};
    protected final int[] INPUT_SLOT=new int[]{1,19,7,25};
    protected final int[] OUTPUT_SLOT=new int[]{4,13,22};
    protected final int[] PROCESSOR_SLOT=new int[]{12,14};
    protected final int[] BORDER_IN=new int[] {10,11};
    protected final int[] BORDER_OUT=new int[] {15,16};
    protected final int processtick;
    protected final int inputCost=53;
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public BoolGenerator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Material progressItem,int tick) {
        super(category,item,recipeType,recipe,progressItem,250,2000,null);
        this.processtick=tick;
        setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow("&f生成机制","&7当所有输入槽内物品全部相同时候,生成%s".formatted(Language.get("Items.TRUE_.Name"))),AddItem.TRUE_,
                AddUtils.getInfoShow("&f生成机制","&7当任两输入槽内物品全部不同时候,生成%s".formatted(Language.get("Items.FALSE_.Name"))),AddItem.FALSE_,
                AddUtils.getInfoShow("&f生成机制","&7当配方匹配时,槽位依次消耗min(槽位物品数,%s)个物品".formatted(inputCost) ),null));
        this.machineRecipes=new ArrayList<>();
        this.machineRecipes.add(MachineRecipeUtils.stackFrom(tick,new ItemStack[0],new ItemStack[]{AddSlimefunItems.setC( AddItem.TRUE_,3)}));
        this.machineRecipes.add(MachineRecipeUtils.stackFrom(tick,new ItemStack[0],new ItemStack[]{AddSlimefunItems.setC( AddItem.FALSE_,3)}));
        AddUtils.addGlow(this.getProgressBar());
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
            preset.addItem(border[var4],MenuUtils.PROCESSOR_NULL , ChestMenuUtils.getEmptyClickHandler());
        }


    }
    protected Pair<MachineRecipe, ItemConsumer[]> findNextRecipe(BlockMenu inv, int[] input, int[] output){
        ItemPusher[] inputs=new ItemPusher[4];
        for(int var2 = 0; var2 < 4; ++var2) {
            inputs[var2]=CraftUtils.getpusher.get(Settings.INPUT,inv,input[var2]);

            if(inputs[var2]==null){
                return null;
            }
        }
        //四个非null
        boolean lastResult=CraftUtils.matchItemCounter(inputs[0],inputs[1],false);
        for(int var2 = 2; var2 < 4; ++var2) {
            if(lastResult!=CraftUtils.matchItemCounter(inputs[0],inputs[var2],false)){
                CraftUtils.clearAmount(inputs);
                return null;
            }
        }
        MachineRecipe result;
        if(lastResult){
            //全相等的,输出
            result= getMachineRecipes().get(0);
        }else{
            //现在it1与it2 it3 it4均不同，比较it2，it3，it4
            for(int var2 = 1; var2 < 3; ++var2) {
                for(int var3 = var2+1; var3 < 4; ++var3) {
                    if(CraftUtils.matchItemCounter(inputs[var2],inputs[var3],false)){
                        CraftUtils.clearAmount(inputs);
                        return null;
                    }
                }
            }
            result= getMachineRecipes().get(1);
            //通过了全部不同的检验
        }
        ItemConsumer[] outCon=CraftUtils.countOneOutput(inv,output,result);
        if(outCon!=null){

            for(int var2 = 0; var2 < 4; ++var2) {
                inputs[var2].addAmount(-inputCost);
                inputs[var2].updateMenu(inv);
            }
            return new Pair<>(result,outCon);
        }else{
            return null;
        }
    }

    @Override
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        SimpleCraftingOperation currentOperation = (SimpleCraftingOperation)this.processor.getOperation(b);
        ItemConsumer[] fastCraft=null;
        if(currentOperation==null){
            Pair<MachineRecipe, ItemConsumer[]> nextP = findNextRecipe(inv,getInputSlots(),getOutputSlots());
            if (nextP != null) {

                MachineRecipe next =nextP.getFirstValue();
                ItemConsumer[] outputInfo=nextP.getSecondValue();
                if(next.getTicks()>0){
                    currentOperation = new SimpleCraftingOperation(outputInfo,next.getTicks());
                    this.processor.startOperation(b, currentOperation);
                }
                else if(next.getTicks()<=0){
                    fastCraft = nextP.getSecondValue();
                }
            }else{//if currentOperation ==null return  , cant find nextRecipe
                if(inv.hasViewer()){
                    for(int var4 = 0; var4 <PROCESSOR_SLOT.length; ++var4) {
                        inv.addItem(PROCESSOR_SLOT[var4],MenuUtils.PROCESSOR_NULL );
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
                    inv.addItem(PROCESSOR_SLOT[var5],MenuUtils.PROCESSOR_NULL );
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

}
