package me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore;


import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import me.matl114.logitech.SlimefunItem.Interface.MenuBlock;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockType;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.TickerClass.Ticking;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public interface MultiBlockCore extends MultiBlockPart, Ticking , MenuBlock {
    default  void onMultiBlockDisable(Location loc, AbstractMultiBlockHandler handler, MultiBlockService.DeleteCause cause){
        Debug.debug(cause.getMessage());
    }
    default void onMultiBlockEnable(Location loc,AbstractMultiBlockHandler handler){
        MultiBlockService.removeHologram(loc);
    }

    /**
     * should override if Menu if present ,should add MenuBlock.onBreak
     * @param e
     */
    default void onMultiBlockBreak(BlockBreakEvent e) {
        this.onBreak(e,DataCache.getMenu(e.getBlock().getLocation()));
        MultiBlockPart.super.onMultiBlockBreak(e);
        Location loc = e.getBlock().getLocation();
        MultiBlockService.removeHologram(loc);
        locks.remove(loc);
    }
    default void handleBlock(SlimefunItem machine){
        machine.addItemHandler(
            new BlockBreakHandler(false, false) {
                @ParametersAreNonnullByDefault
                public void onPlayerBreak(BlockBreakEvent e, ItemStack itemStack, List<ItemStack> list) {
                    //BlockMenu menu = DataCache.getMenu(e.getBlock().getLocation());// BlockStorage.getInventory(e.getBlock());
                    MultiBlockCore.this.onMultiBlockBreak(e);
                }
            }, new BlockPlaceHandler(false) {
                @ParametersAreNonnullByDefault
                public void onPlayerPlace(BlockPlaceEvent e) {
                    MultiBlockCore.this.onPlace(e, e.getBlockPlaced());
                }
            });
    }
    default boolean redirectMenu(){
        return false;
    }
    default MultiBlockService.MultiBlockBuilder getBuilder(){
        return MultiBlockHandler::createHandler;
    }
    public AbstractMultiBlockType getMultiBlockType();
    /**
     * only called when in tickers if status down*****
     * @param loc
     * @param data
     * @param autoCode
     */
    static ConcurrentHashMap<Location, AtomicBoolean> locks=new ConcurrentHashMap<>();
    static boolean runAsyncOrReturnBlocked(Location loc, Runnable r){
        AtomicBoolean lock=locks.computeIfAbsent(loc,(i)->new AtomicBoolean(false));
        if(lock.compareAndSet(false,true)){
            CompletableFuture.runAsync(()->{
                        try{
                            r.run();
                        }finally{//
                            //secure lockers
                            lock.set(false);
                        }
                    }
            );
            return true;
        }
        return false;
    }
    default void autoBuild(Location loc, SlimefunBlockData data, int autoCode){
        if(autoCode<=0)return;
        if(autoCode==3){//3tick重连一次
            Location tarloc=loc.clone();
            runAsyncOrReturnBlocked(tarloc,()->{
                MultiBlockService.createNewHandler(loc,getBuilder(),getMultiBlockType());
            });
            autoCode=1;
        }else {
            autoCode+=1;
        }
        data.setData(MultiBlockService.getAutoKey(),String.valueOf(autoCode));
    }

    /**
     * only called when in tickers to randomly check completeness when status on.if down,send to handler and set Active to false,
     * machine will shut down at next tick
     */
    default void runtimeCheck(Location loc,SlimefunBlockData data,int autoCode){
        int sgn=autoCode>0?1:-1;
        if(autoCode*sgn==3){//3tick检测一次
            Location tarloc=loc.clone();
            runAsyncOrReturnBlocked(tarloc,()->MultiBlockService.checkIfAbsentRuntime(data));
            autoCode=sgn;
        }else {
            autoCode+=sgn;
        }
        data.setData(MultiBlockService.getAutoKey(),String.valueOf(autoCode));
    }
}
