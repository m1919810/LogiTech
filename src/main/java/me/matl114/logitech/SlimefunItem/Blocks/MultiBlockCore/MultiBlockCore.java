package me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore;


import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.SecurityUtils;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockType;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.TickerClass.Ticking;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;

public interface MultiBlockCore extends MultiBlockPart, Ticking {
    default  void onMultiBlockDisable(Location loc, AbstractMultiBlockHandler handler, MultiBlockService.DeleteCause cause){
        Debug.debug(cause.getMessage());
    }
    default void onMultiBlockEnable(Location loc,AbstractMultiBlockHandler handler){
        MultiBlockService.removeHologram(loc);
    }
    default void onMultiBlockBreak(BlockBreakEvent e) {
        MultiBlockPart.super.onMultiBlockBreak(e);
        MultiBlockService.removeHologram(e.getBlock().getLocation());

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

    default void autoBuild(Location loc, SlimefunBlockData data, int autoCode){
        if(autoCode==3){//3tick重连一次
            Schedules.launchSchedules(Schedules.getRunnable(()->{
                Location tarloc=loc.clone();
                if(SecurityUtils.lock(SecurityUtils.Lock.MultiBlockBuildLock,tarloc)){
                    try{
                        MultiBlockService.createNewHandler(loc,getBuilder(),getMultiBlockType());
                    }finally{//
                        //secure lockers
                        SecurityUtils.unlock(SecurityUtils.Lock.MultiBlockBuildLock,tarloc);
                    }
                }
            }),0,false,0);
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
            Schedules.launchSchedules(Schedules.getRunnable(()->{
                Location tarloc=loc.clone();
                if(SecurityUtils.lock(SecurityUtils.Lock.MultiBlockBuildLock,tarloc)){
                    try{
                        MultiBlockService.checkIfAbsentRuntime(data);
                    }finally{//
                        //secure lockers
                        SecurityUtils.unlock(SecurityUtils.Lock.MultiBlockBuildLock,tarloc);
                    }
                }
            }),0,false,0);
            autoCode=sgn;
        }else {
            autoCode+=sgn;
        }
        data.setData(MultiBlockService.getAutoKey(),String.valueOf(autoCode));
    }
}
