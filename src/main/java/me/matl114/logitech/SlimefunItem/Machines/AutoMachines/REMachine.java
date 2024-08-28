package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.MenuUtils;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.RandomMachineOperation;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class REMachine extends EMachine{
    public REMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                     Material processbar, int energyConsumption, int energyBuffer,
                     LinkedHashMap<Object, Integer> customRecipes) {
        super(category, item, recipeType, recipe, processbar, energyConsumption, energyBuffer, customRecipes);
    }
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        SimpleCraftingOperation currentOperation = (SimpleCraftingOperation)this.processor.getOperation(b);
        ItemConsumer[] fastCraft=null;
        if(currentOperation==null){
            Pair<MachineRecipe, ItemConsumer[]> nextP = CraftUtils.findNextRecipe(inv,getInputSlots(),getOutputSlots(),getMachineRecipes(),true
                    , Settings.SEQUNTIAL,CRAFT_PROVIDER);
            if (nextP != null) {
                MachineRecipe next =nextP.getFirstValue();
                ItemConsumer[] outputInfo=nextP.getSecondValue();
                if(next.getTicks()>0){
                    currentOperation = new RandomMachineOperation(outputInfo,next.getTicks());
                    this.processor.startOperation(b, currentOperation);
                }
                else if(next.getTicks()<=0){
                    fastCraft = nextP.getSecondValue();
                }
            }else{//if currentOperation ==null return  , cant find nextRecipe
                if(inv.hasViewer()){
                    inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
                }
                return ;
            }
        }

        progressorCost(b,inv);
        if (fastCraft!=null) {
            CraftUtils.updateOutputMenu(fastCraft,inv);
            return;
        }
        if(currentOperation.isFinished()){
            ItemConsumer[] var4=currentOperation.getResults();
            CraftUtils.forcePush(var4,inv,getOutputSlots(),CRAFT_PROVIDER);
            if(inv.hasViewer()){

                inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
            }
            this.processor.endOperation(b);
        }
        else{
            if(inv.hasViewer()){
                this.processor.updateProgressBar(inv, PROCESSOR_SLOT, currentOperation);

            }
            currentOperation.addProgress(3);

        }


    }
}
