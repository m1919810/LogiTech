package me.matl114.logitech.SlimefunItem.Blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import me.matl114.logitech.SlimefunItem.Machines.MenuBlock;
import me.matl114.logitech.SlimefunItem.Machines.Ticking;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockType;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class MultiBlockCore extends MultiBlockPart implements MenuBlock  {

    public MultiBlockCore(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                          ItemStack[] recipe, String blockId){
        super(itemGroup, item, recipeType, recipe, blockId);

    }
    public abstract MultiBlockService.MultiBlockBuilder getBuilder();
    public abstract AbstractMultiBlockType getMultiBlockType();
    public void tick(Block b, BlockMenu menu, int tickCount){
        //in this case .blockMenu is null
        if(MultiBlockService.acceptCoreRequest(b.getLocation(),getBuilder(),getMultiBlockType())){
            processCore(b,menu);
        }
        process(b,menu);

    }
    public final boolean isSync(){
        return true;
    }
    public boolean redirectMenu(){
        return false;
    }
    public void processCore(Block b, BlockMenu menu){
        //doing nothing
    }
    public void preRegister(){
        this.registerBlockMenu(this);
        super.preRegister();
    }
    public  void onMultiBlockDisable(Location loc){

    }
    public void onMultiBlockEnable(Location loc){

    }
}
