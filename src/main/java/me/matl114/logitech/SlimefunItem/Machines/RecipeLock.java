package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Location;

/**
 * this interface provides a restorer for recipe lock machinie ,selected recipe will only change if certain events occurs
 */
public interface RecipeLock extends RecipeCache{
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
    /**
     * representing the temp display item ,if not match to recipe index,change the existing display item
     * @param loc
     * @param val
     */
    default void setNowRecordRecipe(Location loc ,int val){

        StorageCacheUtils.setData(loc, "record", String.valueOf(val));
    }

    MachineRecipe getRecordRecipe(Location loc);
}
