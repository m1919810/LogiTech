package me.matl114.logitech.Utils;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface DataCache {
    public static boolean hasData(Location loc){
        return StorageCacheUtils.hasBlock(loc);
    }
    public static SlimefunItem getData(Location loc){
        return StorageCacheUtils.getSfItem(loc);
    }
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
    static final Pattern LOCATION_DE_PATTERN=Pattern.compile("(.*?),(.*?),(.*?),(.*?)");
    static final String LOCATION_CODE_PATTERN="%s,%.1f,%.1f,%.1f";
    public static Location getLastLocation(Location loc){
        try{
            String location= StorageCacheUtils.getData(loc,"location");
            if("null".equals(location)){
                return null;
            }
            Matcher matcher = LOCATION_DE_PATTERN.matcher(location);
            if(matcher.matches()){
                String world = matcher.group(1);
                float x = Float.parseFloat(matcher.group(2));
                float y = Float.parseFloat(matcher.group(3));
                float z = Float.parseFloat(matcher.group(4));

                return new Location(Bukkit.getWorld(world), x, y, z);
            }
        }catch (Throwable a){

        }
        setLastLocation(loc,null);
        //important to clone ,dont change origin
        return null;
    }
    public static void setLastLocation(Location loc ,Location loc2){
        if(loc2==null){
            StorageCacheUtils.setData(loc,"location","null");
        }else{
            StorageCacheUtils.setData(loc,"location",LOCATION_CODE_PATTERN.formatted(loc2.getWorld().getName(), loc2.getX(), loc2.getY(), loc2.getZ()));
        }
    }
    public static String getLastUUID(Location loc){
        String uuid;
        SlimefunBlockData data=Slimefun.getDatabaseManager().getBlockDataController().getBlockDataFromCache(loc);
        try{
            uuid= data.getData("uuid");
            if(uuid!=null)
                return uuid;
        }catch (Throwable a){
        }
        data.setData("uuid","null");
        return "null";
    }
    public static void setLastUUID(Location loc ,String uuid){
        StorageCacheUtils.setData(loc,"uuid",uuid);
    }
    public static void safeGetLastUUID(Location loc){

    }
    public static SlimefunBlockData safeLoadBlock(Location loc){
        return Slimefun.getDatabaseManager().getBlockDataController().getBlockData(loc);
    }


}
