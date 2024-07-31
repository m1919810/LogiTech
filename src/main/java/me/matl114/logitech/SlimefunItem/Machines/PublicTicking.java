package me.matl114.logitech.SlimefunItem.Machines;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;

public interface PublicTicking extends Ticking{
    //one type one ticker
    //all machines under one object use same ticks

    int getPublicTick();
    void updatePublicTick();
    /**
     * call this method in preRegister
     * @param item
     */
    default void registerTick(SlimefunItem item){
        item.addItemHandler(
                new BlockTicker() {
                    public boolean isSynchronized() {
                        return PublicTicking.this.isSync();
                    }

                    @ParametersAreNonnullByDefault
                    public void tick(Block b, SlimefunItem item, Config data) {
                        BlockMenu menu = BlockStorage.getInventory(b);
                        if (menu != null) {
                            PublicTicking.this.tick(b, menu,getPublicTick());
                        }

                    }
                    public void uniqueTick() {
                        PublicTicking.this.updatePublicTick();
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
