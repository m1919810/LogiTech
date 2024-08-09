package me.matl114.logitech.Utils;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    static final String LOCATION_DE_PATTERN="(.*?),(.*?),(.*?),(.*?)";
    static final String LOCATION_CODE_PATTERN="%s@,%.2f,%.2f,%.2f";
    public static Location getLastLocation(Location loc){
        try{
            String location= StorageCacheUtils.getData(loc,"location");
            Pattern locationPattern = Pattern.compile(LOCATION_DE_PATTERN);
            Matcher matcher = locationPattern.matcher(location);
            if(matcher.matches()){
                String world = matcher.group(1);
                float x = Float.parseFloat(matcher.group(2));
                float y = Float.parseFloat(matcher.group(3));
                float z = Float.parseFloat(matcher.group(4));

                return new Location(Bukkit.getWorld(world), x, y, z);
            }
        }catch (Throwable a){
            setLastLocation(loc,loc);
        }
        //important to clone ,dont change origin
        return loc.clone();
    }
    public static void setLastLocation(Location loc ,Location loc2){
        Debug.logger(loc.toString());
        Debug.logger(loc2.toString());
        Debug.logger(LOCATION_CODE_PATTERN.formatted(loc2.getWorld().getName(), loc2.getX(), loc2.getY(), loc2.getZ()));
        StorageCacheUtils.setData(loc,"location",LOCATION_CODE_PATTERN.formatted(loc2.getWorld().getName(), loc2.getX(), loc2.getY(), loc2.getZ()));
    }
    public static String getLastUUID(Location loc){
        String uuid;
        try{
            uuid= StorageCacheUtils.getData(loc,"uuid");
            if(uuid!=null)
                return uuid;
        }catch (Throwable a){
        }
        uuid=AddUtils.getUUID();
        setLastUUID(loc,uuid)
        return uuid;
    }
    public static void setLastUUID(Location loc ,String uuid){
        StorageCacheUtils.setData(loc,"uuid",uuid);
    }


}
