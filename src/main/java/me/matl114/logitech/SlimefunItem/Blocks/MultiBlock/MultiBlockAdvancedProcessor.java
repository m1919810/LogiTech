package me.matl114.logitech.SlimefunItem.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore.MultiBlockCore;
import me.matl114.logitech.SlimefunItem.Machines.AbstractAdvancedProcessor;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.*;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.CubeMultiBlock.CubeMultiBlock;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.MultiCraftingOperation;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class MultiBlockAdvancedProcessor extends AbstractAdvancedProcessor implements MachineProcessHolder<MultiCraftingOperation>, MultiBlockCore{
    protected final AbstractMultiBlockType MBTYPE;
    protected final String PARTID;
    public abstract int[] getInputSlots();
    public abstract int[] getOutputSlots();
    public MultiBlockAdvancedProcessor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                               ItemStack[] recipe, String blockId, AbstractMultiBlockType type, ItemStack processorItem, int energyConsumption, int energyBuffer,
                               LinkedHashMap<Object, Integer> customRecipes){
        super(itemGroup, item, recipeType, recipe, Material.STONE,energyConsumption,energyBuffer,customRecipes);
        this.processor.setProgressBar(processorItem);
        this.MBTYPE = type;
        this.PARTID = blockId;

    }
    protected final String HEIGHT_KEY="h";
    public MachineProcessor<MultiCraftingOperation> getMachineProcessor() {
        return this.processor;
    }
    public String getPartId(){
        return this.PARTID;
    }
    public AbstractMultiBlockType getMultiBlockType(){
        return MBTYPE;
    }
    public AbstractMultiBlockHandler createAdvanceProcessor (Location core, AbstractMultiBlock type, String uid){
        AbstractMultiBlockHandler handler=MultiBlockHandler.createHandler(core, type, uid);
        if(handler.getMultiBlock() instanceof CubeMultiBlock cb){
            DataCache.setCustomData(core,HEIGHT_KEY,cb.getHeight());
        }
        return handler;
    }
    public MultiBlockService.MultiBlockBuilder getBuilder(){
        return (MultiBlockService.MultiBlockBuilder) this::createAdvanceProcessor;
    }
    public int getCraftLimit(Block b,BlockMenu inv){
        return 1<<DataCache.getCustomData(inv.getLocation(),HEIGHT_KEY,0);
    }
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int tickCount){
        //in this case .blockMenu is null
        if(MultiBlockService.acceptCoreRequest(b.getLocation(),getBuilder(),getMultiBlockType())){
            process(b,menu,data);
        }
    }
    public boolean isSync(){
        return false;
    }
    protected boolean endOperationWhenBreak=false;
    public void onBreak(BlockBreakEvent e, BlockMenu menu){
        super.onBreak(e,menu);
        if(endOperationWhenBreak&& menu!=null){
            this.processor.endOperation(menu.getLocation());
        }
        //合理性:processor可能需要在MultiBlockBreak中处理processor
    }

}
