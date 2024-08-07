package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.MachineRecipeUtils;
import me.matl114.logitech.Utils.MenuUtils;
import me.matl114.logitech.Utils.UtilClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.SimpleCraftingOperation;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractEnergyProcessor extends AbstractEnergyProvider implements  MachineProcessHolder<SimpleCraftingOperation> {
    protected final int[] BORDER = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    protected final int[] BORDER_IN = new int[]{9, 10, 11, 12, 18, 21, 27, 28, 29, 30};
    protected final int[] BORDER_OUT = new int[]{14, 15, 16, 17, 23, 26, 32, 33, 34, 35};

    protected final ItemStack progressbar;
    protected final MachineProcessor<SimpleCraftingOperation> processor;
    protected int PROCESSOR_SLOT=22;
    public AbstractEnergyProcessor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                   Material progressItem, int energyProvider, int energyBuffer,
                                   LinkedHashMap<Object, Integer> customRecipes){
        super(category, item, recipeType, recipe,  energyProvider, energyBuffer);
        this.progressbar=new ItemStack(progressItem);
        this.processor = new MachineProcessor(this);
        this.processor.setProgressBar(progressbar);
        if(customRecipes!=null) {
            this.machineRecipes = new ArrayList<>(customRecipes.size());
            var customRecipes2 = AddUtils.buildRecipeMap(customRecipes);
            for (Map.Entry<Pair<ItemStack[], ItemStack[]>, Integer> recipePiece : customRecipes2.entrySet()) {
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(MachineRecipeUtils.stackFromMachine(
                        new MachineRecipe(recipePiece.getValue(), recipePiece.getKey().getFirstValue(), recipePiece.getKey().getSecondValue())
                ));
            }
        }else
        {
            this.machineRecipes=new ArrayList<>();
        }

    }
    /**
     * method from MachineProcessorHolder
     * @return
     */
    public MachineProcessor<SimpleCraftingOperation> getMachineProcessor() {
        return this.processor;
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
        AbstractEnergyProcessor.this.processor.endOperation(menu.getLocation());
    }

    /**
     *
     * @param preset
     */
    protected void constructMenu(BlockMenuPreset preset) {
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
        border =BORDER_OUT;
        len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        //空白边框
        preset.addItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
        border = this.getOutputSlots();
        len = border.length;
    }
    public int[] getInputSlots(){
        return new int[]{19,20};
    }

    public int[] getOutputSlots(){
        return new int[]{24,25};
    }
    public List<MachineRecipe> getMachineRecipes(){
        return this.machineRecipes;
    }
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull SlimefunBlockData data){

        BlockMenu inv= StorageCacheUtils.getMenu(l);
        if(conditionHandle(null,inv)){
            SimpleCraftingOperation currentOperation = (SimpleCraftingOperation)this.processor.getOperation(l);
            ItemConsumer[] fastCraft=null;
            if(currentOperation==null){
                Pair<MachineRecipe, ItemConsumer[]> nextP = CraftUtils.findNextRecipe(inv,getInputSlots(),getOutputSlots(),getMachineRecipes(),true);
                if (nextP != null) {

                    MachineRecipe next =nextP.getFirstValue();
                    ItemConsumer[] outputInfo=nextP.getSecondValue();
                    if(next.getTicks()>0){
                        currentOperation = new SimpleCraftingOperation(outputInfo,next.getTicks());
                        this.processor.startOperation(l, currentOperation);
                    }
                    else if(next.getTicks()<=0){
                        fastCraft = nextP.getSecondValue();
                    }
                }else{//if currentOperation ==null return  , cant find nextRecipe
                    if(inv.hasViewer()){inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
                    }
                    return 0;
                }
            }

            //processorCost(b,inv);
            if (fastCraft!=null) {
                CraftUtils.updateOutputMenu(fastCraft,inv);
            }else if(currentOperation.isFinished()){
                ItemConsumer[] var4=currentOperation.getResults();
                CraftUtils.forcePush(var4,inv,getOutputSlots());
                if(inv.hasViewer()){

                    inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
                }
                this.processor.endOperation(l);
            }
            else{
                if(inv.hasViewer()){
                    this.processor.updateProgressBar(inv, PROCESSOR_SLOT, currentOperation);

                }
                currentOperation.addProgress(1);

            }
            return -this.energyConsumption;
        }else{
            return 0;
        }


    }
    public void preRegister(){
        super.preRegister();
    }


}
