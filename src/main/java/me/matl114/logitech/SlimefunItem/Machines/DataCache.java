package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import org.bukkit.Location;

public interface DataCache {
    /**
     * about recipe history get
     * @param loc
     * @return
     */
    public static int getLastRecipe(Location loc){
        try{
            String a= StorageCacheUtils.getData(loc,"recipe");
            return Integer.parseInt(a);

        }   catch (Throwable a){
            setLastRecipe(loc,-1);
            return -1;
        }
    }

    /**
     * about recipe history set (if you dont call this method ,data will not appear in storage)
     * @param loc
     * @param val
     */
    public static void setLastRecipe(Location loc ,int val){

        StorageCacheUtils.setData(loc, "recipe", String.valueOf(val));
    }
}
