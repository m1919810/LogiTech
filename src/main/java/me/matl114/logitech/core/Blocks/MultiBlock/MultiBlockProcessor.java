package me.matl114.logitech.core.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.core.Blocks.MultiBlockCore.MultiBlockCore;
import me.matl114.logitech.core.Machines.Abstracts.AbstractProcessor;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.*;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class MultiBlockProcessor extends AbstractProcessor implements MachineProcessHolder<SimpleCraftingOperation>, MultiBlockCore {
    protected final AbstractMultiBlockType MBTYPE;
    protected final String PARTID;
    public abstract int[] getInputSlots();
    public abstract int[] getOutputSlots();
    protected ItemSetting<Boolean> broadCastWhenDump = create("broadcast-dump-loc",true);
    public MultiBlockProcessor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                               ItemStack[] recipe, String blockId, AbstractMultiBlockType type, ItemStack processorItem, int energyConsumption, int energyBuffer,
                               List<Pair<Object, Integer>> customRecipes){
        super(itemGroup, item, recipeType, recipe, processorItem,energyConsumption,energyBuffer,customRecipes);
        this.MBTYPE = type;
        this.PARTID = blockId;

    }

    public MachineProcessor<SimpleCraftingOperation> getMachineProcessor() {
        return this.processor;
    }
    public String getPartId(){
        return this.PARTID;
    }
    public AbstractMultiBlockType getMultiBlockType(){
        return MBTYPE;
    }
    public MultiBlockService.MultiBlockBuilder getBuilder(){
        return MultiBlockHandler::createHandler;
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
