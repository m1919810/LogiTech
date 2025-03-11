package me.matl114.logitech.core.Blocks.MultiBlockCore;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Setter;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.AbstractMultiBlockType;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public abstract class MultiCore extends MultiPart implements MultiBlockCore {

    public MultiCore(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                     ItemStack[] recipe, String blockId){
        super(itemGroup, item, recipeType, recipe, blockId);

    }
    public abstract MultiBlockService.MultiBlockBuilder getBuilder();
    public abstract AbstractMultiBlockType getMultiBlockType();
    @Setter
    protected boolean autoBuildDefault=true;
    public final void tick(Block b, BlockMenu menu,SlimefunBlockData data, int tickCount){
        //in this case .blockMenu is null?
        Location loc=b.getLocation();
        if(preCondition(b,menu,data)){
            int autoCode=DataCache.getCustomData(data,MultiBlockService.getAutoKey(),autoBuildDefault?1:0);
            if(MultiBlockService.acceptCoreRequest(loc,getBuilder(),getMultiBlockType())){
                runtimeCheck(menu.getLocation(),data,autoCode);
                processCore(b,menu);
            }else{
                if(autoCode>0){
                    autoBuild(loc,data,autoCode);
                }
            }
        }
        process(b,menu,data);
    }
    public boolean preCondition(Block b,BlockMenu inv,SlimefunBlockData data){
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
        this.registerTick(this);
    }
    public boolean isSync(){
        return false;
    }
}
