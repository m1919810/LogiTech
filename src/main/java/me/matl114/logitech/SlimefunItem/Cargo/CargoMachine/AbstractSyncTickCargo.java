package me.matl114.logitech.SlimefunItem.Cargo.CargoMachine;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.Blocks.AbstractBlock;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.UtilClass.TickerClass.SyncBlockTick;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * controlled by sync tick and chip code to perform certain sequence task
 */
public abstract class AbstractSyncTickCargo extends AbstractBlock implements SyncBlockTick.SyncTickers {
    public static SyncBlockTick CARGO_SYNC_INSTANCE =new SyncBlockTick();
    public static SyncBlockTick CHIP_SYNC =new SyncBlockTick(){
        public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
            if(this.tickCount%2==0)return;
            if(item instanceof SyncTickers){
                ((SyncTickers)item).syncTick(b,data.getBlockMenu(),data,this.tickCount/2);
            }
        }
    };

    public AbstractSyncTickCargo(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public abstract int[] getInputSlots();
    public abstract int[] getOutputSlots();
    public abstract void constructMenu(BlockMenuPreset preset);
    public abstract void newMenuInstance(BlockMenu menu,Block b);
    public abstract void updateMenu(BlockMenu blockMenu, Block block, Settings mod);
    public abstract void syncTick(Block b, BlockMenu inv, SlimefunBlockData data, int synTickCount);
    public void preRegister(){
        super.preRegister();
        this.registerBlockMenu(this);
        this.handleBlock(this);
    }
}
