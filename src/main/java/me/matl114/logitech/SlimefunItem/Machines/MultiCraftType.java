package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import org.bukkit.Location;

public interface MultiCraftType extends RecipeLock{
    static int getRecipeTypeIndex(Location loc){
        try{
            String a= StorageCacheUtils.getData(loc,"craftType");
            return Integer.parseInt(a);

        }   catch (NumberFormatException a){
            setRecipeTypeIndex(loc,0);
            return 0;
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

    /**
     * force clean RecipeLock data and save val
     * @param loc
     * @param val
     */
    static void forceSetRecipeTypeIndex(Location loc, int val){
        StorageCacheUtils.setData(loc, "craftType", String.valueOf(val));
        RecipeCache.setLastRecipe(loc,-1);
    }
}
