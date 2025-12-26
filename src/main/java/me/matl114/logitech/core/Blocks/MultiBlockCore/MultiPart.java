package me.matl114.logitech.core.Blocks.MultiBlockCore;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.Blocks.AbstractBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class MultiPart extends AbstractBlock implements MultiBlockPart {
    public final String BLOCKID;

    public MultiPart(
            ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, String blockId) {
        super(itemGroup, item, recipeType, recipe);
        this.BLOCKID = blockId;
    }

    public String getPartId() {
        return BLOCKID;
    }

    public boolean redirectMenu() {
        return true;
    }

    public void tick(Block b, BlockMenu menu, int tickCount) {
        // donig nothing
    }

    public void processPart(Block b, BlockMenu menu, Location core) {
        // doing nothing
    }

    public void process(Block b, BlockMenu menu, SlimefunBlockData data) {
        // doing nothing
    }

    public void preRegister() {
        //        if(redirectMenu()){
        //            this.addHandler((BlockUseHandler)this::onMenuRedirect);
        //        }
        super.preRegister();
        handleMultiBlockPart(this);
        // registerTick(this);
    }
}
