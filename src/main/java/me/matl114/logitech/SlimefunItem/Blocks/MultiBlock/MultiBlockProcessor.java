package me.matl114.logitech.SlimefunItem.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore;
import me.matl114.logitech.SlimefunItem.Blocks.MultiCore;
import me.matl114.logitech.SlimefunItem.Machines.AbstractProcessor;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.MachineRecipeUtils;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockType;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class MultiBlockProcessor extends AbstractProcessor implements MachineProcessHolder<SimpleCraftingOperation>, MultiBlockCore {
    protected final MultiBlockType MBTYPE;
    protected final String PARTID;
    public abstract int[] getInputSlots();
    public abstract int[] getOutputSlots();
    public MultiBlockProcessor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                            ItemStack[] recipe, String blockId, MultiBlockType type, ItemStack processorItem, int energyConsumption, int energyBuffer,
                            LinkedHashMap<Object, Integer> customRecipes){
        super(itemGroup, item, recipeType, recipe, processorItem,energyConsumption,energyBuffer,customRecipes);
        this.MBTYPE = type;
        this.PARTID = blockId;

    }
    public List<MachineRecipe> getMachineRecipes(){
        return this.machineRecipes;
    }
    public MachineProcessor<SimpleCraftingOperation> getMachineProcessor() {
        return this.processor;
    }
    public String getPartId(){
        return this.PARTID;
    }
    public MultiBlockService.MultiBlockBuilder getBuilder(){
        return MultiBlockHandler::createHandler;
    }
    public MultiBlockType getMultiBlockType(){
        return MBTYPE;
    }
    public void onMultiBlockDisable(Location loc, AbstractMultiBlockHandler handler){
        processor.endOperation(loc);
        MultiBlockCore.super.onMultiBlockDisable(loc,handler);
    }
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int tickCount){
        //in this case .blockMenu is null
        if(MultiBlockService.acceptCoreRequest(b.getLocation(),getBuilder(),getMultiBlockType())){
            process(b,menu,data);
        }
    }
    public boolean isSync(){
        return true;
    }

    public void preRegister(){
        super.preRegister();
        handleMultiBlockPart(this);
    }

}
