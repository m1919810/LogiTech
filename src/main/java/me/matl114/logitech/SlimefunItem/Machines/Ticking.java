package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;

public interface Ticking {
    /**
     * call this method in preRegister
     * @param item
     */
    default void registerTick(SlimefunItem item){
        item.addItemHandler(
                new BlockTicker() {
                    public boolean isSynchronized() {
                        return Ticking.this.isSync();
                    }

                    @ParametersAreNonnullByDefault
                    public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
                        BlockMenu menu = StorageCacheUtils.getMenu(b.getLocation());
                        //BlockMenu menu = BlockStorage.getInventory(b);
                        if (menu != null) {
                            Ticking.this.tick(b, menu,0);
                        }

                    }
                }


        );
    }
    default boolean isSync(){
        return  false;
    }
    /**
     * imple blockTicking in this method
     * @param b
     * @param menu
     * @param tickCount
     */
    void tick(Block b, BlockMenu menu,int tickCount);
}
