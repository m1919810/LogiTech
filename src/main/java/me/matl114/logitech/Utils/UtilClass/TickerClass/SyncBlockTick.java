package me.matl114.logitech.Utils.UtilClass.TickerClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;

public class SyncBlockTick extends BlockTicker {
    public static SyncBlockTick TESTINSTANCE = new SyncBlockTick();

    public interface SyncTickers{
        public void syncTick(Block b, BlockMenu inv,SlimefunBlockData data,int synTickCount);
    }
    int tickCount=0;
    public SyncBlockTick(){

    }
    public boolean isSynchronized() {
        return false;
    }
    public void uniqueTick() {
        tickCount++;
    }
    public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
       if(item instanceof SyncTickers){
           ((SyncTickers)item).syncTick(b,data.getBlockMenu(),data,tickCount);
       }
    }
}
