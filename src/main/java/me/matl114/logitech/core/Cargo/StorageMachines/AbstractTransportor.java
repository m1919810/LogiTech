package me.matl114.logitech.core.Cargo.StorageMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotHopperable;
import me.matl114.logitech.core.DistinctiveCustomSlimefunItem;
import me.matl114.logitech.core.Interface.MenuBlock;
import me.matl114.logitech.Utils.UtilClass.TickerClass.Ticking;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class AbstractTransportor extends DistinctiveCustomSlimefunItem implements Ticking, MenuBlock, NotHopperable{
    public AbstractTransportor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(category,item , recipeType, recipe);
    }
    public void addInfo(ItemStack stack){
    }
    public abstract void constructMenu(BlockMenuPreset preset);
    public abstract int[] getInputSlots();
    public abstract int[] getOutputSlots();
    public abstract void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker);
    public void updateMenu(BlockMenu blockMenu, Block block, Settings mod){
    }
    public void newMenuInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block){
        updateMenu(blockMenu,block,Settings.INIT);
    }
    public void preRegister(){
        super.preRegister();
        //
        registerTick(this);
        //为menublock提供 需要
        registerBlockMenu(this);
    }
}
