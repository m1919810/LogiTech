package me.matl114.logitech.core.Machines.Abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.matl114.matlib.Utils.Inventory.ItemStacks.CleanItemStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AbstractMultiThreadProcessor extends AbstractMachine implements MachineProcessHolder<SimpleCraftingOperation> {
    protected final int[] BORDER_IN=new int[]{
            0,1,2,3,4,5,6,7,8
    };
    protected final int[] BORDER_OUT=new int[]{
            36,37,38,39,40,41,42,43,44
    };
    protected final int[][] THREAD_INPUT=new int[][]{
            new int[]{9},
            new int[]{10},
            new int[]{11},
            new int[]{12},
            new int[]{13},
            new int[]{14},
            new int[]{15},
            new int[]{16},
            new int[]{17},
    };
    protected final int[][] THREAD_OUTPUT=new int[][]{
           new int[]{27},
            new int[]{28},
            new int[]{29},
            new int[]{30},
            new int[]{31},
            new int[]{32},
            new int[]{33},
            new int[]{34},
            new int[]{35},
    };
    protected final int[] INPUT=new int[]{
            9,10,11,12,13,14,15,16,17,
    };
    protected final int[] OUTPUT=new int[]{
            27,28,29,30,31,32,33,34,35,
    };
    protected final int[] PROCESSOR_SLOT=new int[]{
            18,19,20,21,22,23,24,25,26
    };
    public int[] getInputSlots(){
        return INPUT;
    }
    public int[] getOutputSlots(){
        return OUTPUT;
    }
    public int[] getProcessorSlots(){
        return PROCESSOR_SLOT;
    }
    protected final ItemStack progressbar;
    protected final MachineProcessor<SimpleCraftingOperation>[] processor;
    protected int THREAD_NUM=9;
    public AbstractMultiThreadProcessor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                             ItemStack progressItem, int energyConsumption, int energyBuffer,
                                        List<Pair<Object,Integer>> customRecipes){
        super(category,item , recipeType, recipe,energyBuffer,energyConsumption);

        this.progressbar=new CleanItemStack(progressItem);
        this.processor=new MachineProcessor[THREAD_NUM];
        for(int i=0;i<THREAD_NUM;i++){
            this.processor[i]=new MachineProcessor(this);
            this.processor[i].setProgressBar(progressbar);
        }
        if(customRecipes!=null) {
            this.machineRecipes = new ArrayList<>(customRecipes.size());
            var customRecipes2 = AddUtils.buildRecipeMap(customRecipes);
            for(var recipePiece:customRecipes2){
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(MachineRecipeUtils.stackFromMachine(
                        new MachineRecipe(recipePiece.getSecondValue(),recipePiece.getFirstValue().getFirstValue(), recipePiece.getFirstValue().getSecondValue())
                ));
            }
        }else
        {
            this.machineRecipes=new ArrayList<>();
        }

    }
    public AbstractMultiThreadProcessor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                             Material progressItem, int energyConsumption, int energyBuffer,
                                        List<Pair<Object,Integer>> customRecipes){
        this(category,item,recipeType,recipe,new ItemStack(progressItem),energyConsumption,energyBuffer,customRecipes);
    }

    /**
     * method from MachineProcessorHolder
     * @return
     */
    public MachineProcessor<SimpleCraftingOperation> getMachineProcessor() {
        return this.processor[0];
    }


    /**
     * need implement,  method from MachineProcessorHolder
     * @return
     */
    public ItemStack getProgressBar(){
        return this.progressbar;
    }


    /**
     * cancel machineprocessor after break
     * @param e
     * @param menu
     */
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e,menu);
        for (int i=0;i<THREAD_NUM;i++){
            processor[i].endOperation(menu.getLocation());
        }
    }

    /**
     *
     * @param preset
     */
    public void constructMenu(BlockMenuPreset preset) {
        //空白背景 禁止点击
        //输入槽边框
        int[] border = BORDER_IN;
        int len = border.length;
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
        border=PROCESSOR_SLOT;
        len = border.length;
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addItem(PROCESSOR_SLOT[var4], MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        int run=0;
        boolean hasViewer=inv.hasViewer();
        for (int i=0;i<THREAD_NUM;i++){
            SimpleCraftingOperation currentOperation = (SimpleCraftingOperation)this.processor[i].getOperation(b);
            ItemConsumer[] fastCraft=null;
            if(currentOperation==null){
                Pair<MachineRecipe, ItemConsumer[]> nextP = CraftUtils.findNextRecipe(inv,
                        THREAD_INPUT[i],THREAD_OUTPUT[i],getMachineRecipes(),false
                        , Settings.SEQUNTIAL,CRAFT_PROVIDER);
                if (nextP != null) {
                    MachineRecipe next =nextP.getFirstValue();
                    ItemConsumer[] outputInfo=nextP.getSecondValue();
                    if(next.getTicks()>0){
                        currentOperation = new SimpleCraftingOperation(outputInfo,next.getTicks());
                        this.processor[i].startOperation(b, currentOperation);
                    }
                    else if(next.getTicks()<=0){
                        fastCraft = nextP.getSecondValue();
                    }
                }else{//if currentOperation ==null return  , cant find nextRecipe
                    if(hasViewer){
                        inv.replaceExistingItem(PROCESSOR_SLOT[i], MenuUtils.PROCESSOR_NULL);
                    }
                    continue;
                }
            }
            run+=1;
            if (fastCraft!=null) {
                CraftUtils.updateOutputMenu(fastCraft,inv);
                continue;
            }
            if(currentOperation.isFinished()){
                ItemConsumer[] var4=currentOperation.getResults();
                CraftUtils.forcePush(var4,inv,THREAD_OUTPUT[i],CRAFT_PROVIDER);
                if(hasViewer){

                    inv.replaceExistingItem(PROCESSOR_SLOT[i], MenuUtils.PROCESSOR_NULL);
                }
                this.processor[i].endOperation(b);
            }
            else{
                if(hasViewer){
                    this.processor[i].updateProgressBar(inv, PROCESSOR_SLOT[i], currentOperation);
                }
                currentOperation.progress(1);
            }
        }
        this.removeCharge(inv.getLocation(),this.energyConsumption*run);
    }
    public void preRegister(){
        super.preRegister();
    }
}
