package me.matl114.logitech.SlimefunItem;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.Blocks.AbstractBlock;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.TickerClass.SyncBlockTick;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class TestStorageUnit3 extends AbstractBlock implements SyncBlockTick.SyncTickers {
    public TestStorageUnit3(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }
    public void syncTick(Block b, BlockMenu inv, SlimefunBlockData data, int synTickCount){
        Debug.logger("test 3 tick count ",synTickCount);
    }
    public void preRegister(){
        super.preRegister();
        this.addItemHandler(SyncBlockTick.TESTINSTANCE);
    }
}
