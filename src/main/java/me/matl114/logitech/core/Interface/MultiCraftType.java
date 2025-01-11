package me.matl114.logitech.core.Interface;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import me.matl114.logitech.Utils.DataCache;
import org.bukkit.Location;

public interface MultiCraftType extends RecipeLock {
    static int getRecipeTypeIndex(Location loc){
        try{
            String a= StorageCacheUtils.getData(loc,"craftType");
            return Integer.parseInt(a);

        }   catch (NumberFormatException a){
            setRecipeTypeIndex(loc,-1);
            return -1;
        }
    }
    static int getRecipeTypeIndex(SlimefunBlockData data){
        try{
            String a= data.getData("craftType");
            return Integer.parseInt(a);

        }   catch (NumberFormatException a){
            data.setData("craftType","-1");
            return -1;
        }
    }

    /**
     * will not care about RecipeLock data
     * @param loc
     * @param val
     */
    static void setRecipeTypeIndex(Location loc ,int val){

        StorageCacheUtils.setData(loc, "craftType", String.valueOf(val));
    }
    static void setRecipeTypeIndex(SlimefunBlockData data ,int val){
        data.setData("craftType", String.valueOf(val));
    }

    /**
     * if val not change ,will not clean RecipeLock data
     * @param loc
     * @param val
     */
    static void safeSetRecipeTypeIndex(Location loc, int val){
        int index=getRecipeTypeIndex(loc);
        if(index!=val){
            forceSetRecipeTypeIndex(loc,val );
        }
    }
    static void safeSetRecipeTypeIndex(SlimefunBlockData data, int val){
        int index=getRecipeTypeIndex(data);
        if(index!=val){
            forceSetRecipeTypeIndex(data,val );
        }
    }

    /**
     * force clean RecipeLock data and save val
     * @param loc
     * @param val
     */
    static void forceSetRecipeTypeIndex(Location loc, int val){
        StorageCacheUtils.setData(loc, "craftType", String.valueOf(val));
        DataCache.setLastRecipe(loc,-1);
    }
    static void forceSetRecipeTypeIndex(SlimefunBlockData data, int val){
        data.setData("craftType", String.valueOf(val));
        DataCache.setLastRecipe(data,-1);
    }
}
