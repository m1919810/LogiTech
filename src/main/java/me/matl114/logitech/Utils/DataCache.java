package me.matl114.logitech.Utils;

import com.xzavier0722.mc.plugin.slimefun4.storage.callback.IAsyncReadCallback;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.BlockDataController;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface DataCache {
    public static final BlockDataController CONTROLLER=Slimefun.getDatabaseManager().getBlockDataController();

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
        SlimefunBlockData data=CONTROLLER.getBlockDataFromCache(loc);
        try{
            String a= data.getData("recipe");
            return Integer.parseInt(a);

        }   catch (Throwable a){
            data.setData("recipe", "-1");
            return -1;
        }
    }
    public static int getLastRecipe(SlimefunBlockData data){
        try{
            String a= data.getData("recipe");
            return Integer.parseInt(a);

        }   catch (Throwable a){
            data.setData("recipe", "-1");
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
    public static void setLastRecipe(SlimefunBlockData data ,int val){

        data.setData("recipe", String.valueOf(val));
    }
    static final Pattern LOCATION_DE_PATTERN=Pattern.compile("(.*?),(.*?),(.*?),(.*?)");
    static final String LOCATION_CODE_SPLITER=",";
    static final String LOCATION_CODE_PATTERN="%s,%d,%d,%d";
    public static Location locationFromString(String loc){
        try{
            if("null".equals(loc)){
                return null;
            }
            String[] list=loc.split(LOCATION_CODE_SPLITER);
            if(list.length!=4)return null;
            String world =list[0];
            int x = Integer.parseInt(list[1]);
            int y = Integer.parseInt(list[2]);
            int z = Integer.parseInt(list[3]);
            return new Location(Bukkit.getWorld(world), x, y, z);
        }catch (Throwable e){
        }
        return null;
    }
    public static String locationToString(Location loc){
        if(loc==null){
            return "null";
        }else{
            return new StringBuilder().append(loc.getWorld().getName()).append(',')
                    .append(loc.getBlockX()).append(',').append(loc.getBlockY()).append(',').append(loc.getBlockZ()).toString();
        }
    }
    public static Location getLocation(String key,SlimefunBlockData data){
        String location=data.getData(key);
        if(location!=null){
            Location loc=locationFromString(location);
            return loc;
        }else{
            data.setData(key,"null");
            //important to clone ,dont change origin
            return null;
        }
    }
    public static Location getLastLocation(SlimefunBlockData data){
        return getLocation("location",data);
    }
    public static Location getLastLocation(Location loc){
        SlimefunBlockData data=CONTROLLER.getBlockDataFromCache(loc);
        return getLastLocation(data);
    }
    public static void setLocation(String key, SlimefunBlockData data, Location loc2){
        data.setData(key,locationToString(loc2));
    }
    public static void setLocation(String key, Location loc, Location loc2){
        SlimefunBlockData data=CONTROLLER.getBlockDataFromCache(loc);
        data.setData(key,locationToString(loc2));
    }
    public static void setLastLocation(Location loc ,Location loc2){
        SlimefunBlockData data=CONTROLLER.getBlockDataFromCache(loc);
        setLocation("location",data,loc2);
    }
    public static String getLastUUID(SlimefunBlockData data){

        try{
            String uuid= data.getData("uuid");
            if(uuid!=null)
                return uuid;
        }catch (Throwable a){
        }
        data.setData("uuid","null");
        return "null";
    }
    public static String getLastUUID(Location loc){
        return getLastUUID(CONTROLLER.getBlockDataFromCache(loc));
    }
    public static void setLastUUID(Location loc ,String uuid){
        StorageCacheUtils.setData(loc,"uuid",uuid);
    }
    public static void runAfterSafeLoad(Location loc,Consumer<SlimefunBlockData> consumer,boolean isSync){
        SlimefunBlockData data;
        data= CONTROLLER.getBlockDataFromCache(loc);
        StorageCacheUtils.executeAfterLoad(data,()-> consumer.accept(data),isSync);

    }
    public static HashSet<Location> loadingLocation=new HashSet<>();
    public static byte[] lock=new byte[0];
    public static SlimefunBlockData safeLoadBlock(Location loc){

        SlimefunBlockData data= CONTROLLER.getBlockDataFromCache(loc);
        if(data==null){
            return null;
        }
        if(!data.isDataLoaded()){
            //try call load
            StorageCacheUtils.requestLoad(data);
            return null;
        }
        return data;
    }
    public static int getCustomData(Location loc,String key,int defaultValue){
        SlimefunBlockData data=CONTROLLER.getBlockDataFromCache(loc);
        return getCustomData(data,key,defaultValue);
    }
    public static void setCustomData(Location loc ,String key,int value){
        StorageCacheUtils.setData(loc,key,String.valueOf(value));
    }
    public static int getCustomData(SlimefunBlockData data,String key,int defaultValue){
        try{
            String csd= data.getData(key);
            if(csd!=null)
                return Integer.parseInt(csd);
        }catch (Throwable a){
        }
        data.setData(key,String.valueOf(defaultValue));
        return defaultValue;
    }
    public static void setCustomData(SlimefunBlockData data,String key,int value){
        data.setData(key,String.valueOf(value));
    }
    public static String getCustomString(SlimefunBlockData data,String key,String defaultVal){
        try{
            String csd= data.getData(key);
            if(csd!=null)
                return csd;
        }catch (Throwable a){
        }
        data.setData(key,defaultVal);
        return defaultVal;
    }
    public static void setCustomString(SlimefunBlockData data,String key,String value){
        data.setData(key,value);
    }
}
