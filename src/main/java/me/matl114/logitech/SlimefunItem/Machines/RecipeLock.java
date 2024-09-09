package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import me.matl114.logitech.Utils.DataCache;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Location;

/**
 * this interface provides a restorer for recipe lock machinie ,selected recipe will only change if certain events occurs
 */
public interface RecipeLock  {
    /**
     * representing the temp display item ,if not match to recipe index,change the existing display item
     * @param loc
     */
     default int getNowRecordRecipe(Location loc){
        try{
            String a= StorageCacheUtils.getData(loc,"record");
            return Integer.parseInt(a);

        }   catch (NumberFormatException a){
            setNowRecordRecipe(loc,-1);
            return -1;
        }
    }
    default int getNowRecordRecipe(SlimefunBlockData data){
        try{
            String a= data.getData("record");
            return Integer.parseInt(a);

        }   catch (NumberFormatException a){
            data.setData("record","-1");
            return -1;
        }
    }
    /**
     * representing the temp display item ,if not match to recipe index,change the existing display item
     * @param loc
     * @param val
     */
    default void setNowRecordRecipe(Location loc ,int val){

        StorageCacheUtils.setData(loc, "record", String.valueOf(val));
    }
    default void setNowRecordRecipe(SlimefunBlockData data ,int val){
        data.setData("record",String.valueOf(val));
    }
    MachineRecipe getRecordRecipe(SlimefunBlockData data);

}
