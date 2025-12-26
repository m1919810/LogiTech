package me.matl114.logitech.core.Interface;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import me.matl114.logitech.utils.DataCache;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Location;

/**
 * this interface provides a restorer for recipe lock machinie ,selected recipe will only change if certain events occurs
 */
public interface RecipeLock {
    /**
     * representing the temp display item ,if not match to recipe index,change the existing display item
     * @param loc
     */
    default int getNowRecordRecipe(Location loc) {
        // try{
        return DataCache.getCustomData(loc, "record", -1);
        // return Integer.parseInt(a);

        //        }   catch (NumberFormatException a){
        //            setNowRecordRecipe(loc,-1);
        //            return -1;
        //        }
    }

    default int getNowRecordRecipe(SlimefunBlockData data) {
        return DataCache.getCustomData(data, "record", -1);
        //        try{
        //            String a= data.getData("record");
        //            return Integer.parseInt(a);
        //
        //        }   catch (NumberFormatException a){
        //            data.setData("record","-1");
        //            return -1;
        //        }
    }
    /**
     * representing the temp display item ,if not match to recipe index,change the existing display item
     * @param loc
     * @param val
     */
    default void setNowRecordRecipe(Location loc, int val) {

        DataCache.setCustomData(loc, "record", val);
    }

    default void setNowRecordRecipe(SlimefunBlockData data, int val) {
        data.setData("record", String.valueOf(val));
    }

    MachineRecipe getRecordRecipe(SlimefunBlockData data);
}
