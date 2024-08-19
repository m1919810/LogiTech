package me.matl114.logitech.SlimefunItem.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore;
import me.matl114.logitech.SlimefunItem.Machines.AbstractProcessor;
import me.matl114.logitech.Utils.SecurityUtils;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockType;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;

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

    /**
     * only called when in tickers if status down*****
     * @param loc
     * @param data
     * @param autoCode
     */
    public void autoBuild(Location loc,SlimefunBlockData data,int autoCode){
        if(autoCode==3){//3tick重连一次
            Schedules.launchSchedules(Schedules.getRunnable(()->{
                Location tarloc=loc.clone();
                if(SecurityUtils.lock(SecurityUtils.Lock.MultiBlockBuildLock,tarloc)){
                    try{
                        MultiBlockService.createNewHandler(loc,getBuilder(),getMultiBlockType());
                    }finally{//
                        //secure lockers
                        SecurityUtils.unlock(SecurityUtils.Lock.MultiBlockBuildLock,tarloc);
                    }
                }
            }),0,false,0);
            autoCode=1;
        }else {
            autoCode+=1;
        }
        data.setData("auto",String.valueOf(autoCode));
    }

    /**
     * only called when in tickers to randomly check completeness when status on.if down,send to handler and set Active to false,
     * machine will shut down at next tick
     */
    public void runtimeCheck(Location loc,SlimefunBlockData data,int autoCode){
        int sgn=autoCode>0?1:-1;
        if(autoCode*sgn==3){//3tick检测一次
            Schedules.launchSchedules(Schedules.getRunnable(()->{
                Location tarloc=loc.clone();
                if(SecurityUtils.lock(SecurityUtils.Lock.MultiBlockBuildLock,tarloc)){
                    try{
                        MultiBlockService.checkIfAbsentRuntime(data);
                    }finally{//
                        //secure lockers
                        SecurityUtils.unlock(SecurityUtils.Lock.MultiBlockBuildLock,tarloc);
                    }
                }
            }),0,false,0);
            autoCode=sgn;
        }else {
            autoCode+=sgn;
        }
        data.setData("auto",String.valueOf(autoCode));
    }

    public void preRegister(){
        super.preRegister();
        handleMultiBlockPart(this);
    }

}
