package me.matl114.logitech.SlimefunItem.Blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import me.matl114.logitech.SlimefunItem.Machines.Ticking;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MultiBlockPart extends AbstractBlock implements  Ticking{
    public final String BLOCKID;
    public MultiBlockPart(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,String blockId) {
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
    public void process(Block b, BlockMenu menu){
        //doing nothing
    }
    public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops){
        Location loc=e.getBlock().getLocation();
        String uid= DataCache.getLastUUID(loc);
        MultiBlockService.deleteMultiBlock(uid);
    }
    public void preRegister(){
        this.registerTick(this);

        this.addHandler(new BlockBreakHandler(false,false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                String uid=DataCache.getLastUUID(blockBreakEvent.getBlock().getLocation());
                MultiBlockService.deleteMultiBlock(uid);
            }
        });
        super.preRegister();
    }
}
