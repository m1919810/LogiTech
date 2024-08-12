package me.matl114.logitech.SlimefunItem.Blocks;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class MultiPart extends AbstractBlock implements MultiBlockPart{
    public final String BLOCKID;
    public MultiPart(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, String blockId) {
        super(itemGroup, item, recipeType, recipe);
        this.BLOCKID = blockId;
    }
    public String getPartId(){
        return BLOCKID;
    }
    public boolean redirectMenu(){
        return true;
    }
//    public void tick(Block b, BlockMenu menu, int tickCount){
//        //in this case .blockMenu is null
//        Location core=MultiBlockService.acceptPartRequest(b.getLocation());
//        if(core!=null){
//            processPart(b,menu,core);
//        }
//        process(b,menu);
//    }
    public void tick(Block b,BlockMenu menu,int tickCount) {
        //donig nothing
    }
    public void processPart(Block b, BlockMenu menu,Location core){
        //doing nothing
    }
    public void process(Block b, BlockMenu menu, SlimefunBlockData data){
        //doing nothing
    }
    public void preRegister(){
//        if(redirectMenu()){
//            this.addHandler((BlockUseHandler)this::onMenuRedirect);
//        }
        handleMenu(this);
        super.preRegister();
    }
}
