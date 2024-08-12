package me.matl114.logitech.SlimefunItem.Blocks;


import me.matl114.logitech.SlimefunItem.Machines.Ticking;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import org.bukkit.Location;

public interface MultiBlockCore extends MultiBlockPart , Ticking {
    default  void onMultiBlockDisable(Location loc){
    }
    default void onMultiBlockEnable(Location loc){
        MultiBlockService.removeHologram(loc);
    }
    default boolean redirectMenu(){
        return false;
    }
}
