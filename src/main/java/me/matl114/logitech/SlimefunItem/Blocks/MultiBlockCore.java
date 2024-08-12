package me.matl114.logitech.SlimefunItem.Blocks;


import me.matl114.logitech.SlimefunItem.Machines.Ticking;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;

public interface MultiBlockCore extends MultiBlockPart , Ticking {
    default  void onMultiBlockDisable(Location loc, AbstractMultiBlockHandler handler){
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
}
