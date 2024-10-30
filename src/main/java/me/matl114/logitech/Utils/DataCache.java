package me.matl114.logitech.Utils;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.BlockDataController;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunChunkData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.LocationUtils;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.*;
import org.bukkit.block.Block;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class DataCache {
    public static final BlockDataController CONTROLLER=Slimefun.getDatabaseManager().getBlockDataController();
    public static Method GETBLOCKDATACACHE;
    public static boolean INVOKE_GETBLOCKDATACACHE=false;
    public static Map<String, SlimefunChunkData> loadedChunkData;
    public static boolean INVOKE_LOADEDCHUNK=false;
    static{
        try{
            GETBLOCKDATACACHE=CONTROLLER.getClass().getDeclaredMethod("getBlockDataFromCache",String.class,String.class);
            GETBLOCKDATACACHE.setAccessible(true);
            INVOKE_GETBLOCKDATACACHE=true;

        }catch (Throwable e){
            Debug.logger("INVOKE DATA CONTROLLER GETBLOCKDATAFROMCACHE FAILED! EXCEPTION");
            Debug.logger(e);
        }
        try{
            Field loadedChunkDataField=CONTROLLER.getClass().getDeclaredField("loadedChunk");
            loadedChunkDataField.setAccessible(true);
            loadedChunkData=(Map<String, SlimefunChunkData>)loadedChunkDataField.get(CONTROLLER);
            INVOKE_LOADEDCHUNK=true;
        }catch (Throwable e){
            Debug.logger("INVOKE DATA CONTROLLER loadedChunk FAILED! EXCEPTION");
            Debug.logger(e);
        }
    }
    public static void setup(){

    }
    public static boolean hasData(Location loc){
        return safeGetBlockDataFromCache(loc)!=null;
    }
    public static SlimefunItem getSfItem(Location loc){
        SlimefunBlockData data= safeGetBlockDataFromCache(loc);
        return data==null?null:SlimefunItem.getById(data.getSfId());
    }

    /**
     * about recipe history get
     * @param loc
     * @return
     */
    public static int getLastRecipe(Location loc){
        SlimefunBlockData data=safeGetBlockDataFromCache(loc);
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
     * must run in ticks or after data load!
     * @param loc
     * @param val
     */
    public static void setLastRecipe(Location loc ,int val){
        SlimefunBlockData data=safeGetBlockDataFromCache(loc);
        data.setData("recipe", String.valueOf(val));
    }
    /**
     * must run in ticks or after data load!
     */
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
    public static final String DISPLAY_PATTERN="[%s,%.0f,%.0f,%.0f]";
    public static String locationToDisplayString(Location loc){
        return loc!=null? DISPLAY_PATTERN.formatted(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ()):"null";
    }
    public static Location getLocation(String key,SlimefunBlockData data){
        if(data==null)return null;
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
        SlimefunBlockData data= safeGetBlockDataFromCache(loc);
        return getLastLocation(data);
    }
    public static void setLocation(String key, SlimefunBlockData data, Location loc2){
        safeSetData(data,key,locationToString(loc2));
    }
    public static void setLocation(String key, Location loc, Location loc2){
        safeSetData(loc,key,locationToString(loc2));
    }
    public static void setLastLocation(Location loc ,Location loc2){

        setLocation("location",loc,loc2);
    }
    public static String getLastUUID(SlimefunBlockData data){

        try{
            String uuid= data.getData("uuid");
            if(uuid!=null)
                return uuid;
        }catch (Throwable a){
        }
        safeSetData(data,"uuid","null");
        return "null";
    }
    public static String getLastUUID(Location loc){
        return getLastUUID(safeLoadBlock(loc));
    }
    public static void setLastUUID(Location loc ,String uuid){
        safeSetData(loc,"uuid",uuid);
    }
    public static void runAfterSafeLoad(Location loc,Consumer<SlimefunBlockData> consumer,boolean isSync){
        SlimefunBlockData data;
        data= safeGetBlockDataFromCache(loc);
        if(data!=null)
            StorageCacheUtils.executeAfterLoad(data,()-> consumer.accept(data),isSync);
    }
    private static String getChunkKey(Location loc) {
        String var10000 = loc.getWorld().getName();
        return var10000 + ";" + (loc.getBlockX()>>4) + ":" + (loc.getBlockZ()>>4);
    }
    private static void safeSetData(SlimefunBlockData data,String key,String value){
        if(data==null)return;
        StorageCacheUtils.executeAfterLoad(data,()-> data.setData(key,value),false);
    }
    private static void safeSetData(Location loc,String key,String value){
        runAfterSafeLoad(loc,(data)-> data.setData(key,value),false);
    }
    public static HashSet<SlimefunBlockData> getAllSfItemInChunk(World world,int x,int z){
        if(INVOKE_LOADEDCHUNK){
            String chunkkey= world.getName() + ";" + x + ":" +z;
            try{
                SlimefunChunkData data=loadedChunkData.get(chunkkey);
                if(data!=null){
                    return new HashSet<>(data.getAllBlockData());
                }
            }catch (Throwable e){
                Debug.debug("getAllSfItemInChunk INVOKE FAILED! DISABLING BETTER GETBLOCKFROMCACHE");
                Debug.debug(e);
                INVOKE_LOADEDCHUNK=false;
            }
        }
        return new HashSet<>( CONTROLLER.getChunkData(world.getChunkAt(x,z)).getAllBlockData());
    }
    public static void removeBlockData(Location loc){
        SlimefunItem item=getSfItem(loc);
        CONTROLLER.removeBlock(loc);
        if(item instanceof MachineProcessHolder<?> mmp){
            mmp.getMachineProcessor().endOperation(loc);
        }
    }
    private static SlimefunBlockData safeGetBlockDataFromCache(Location loc){
        if(INVOKE_LOADEDCHUNK){
            String chunkKey=getChunkKey(loc);
            try{
                SlimefunChunkData data=loadedChunkData.get(chunkKey);
                if(data!=null){
                    return data.getBlockData(loc);
                }
            }catch (Throwable e){
                Debug.debug("getBlockDataFromCache INVOKE FAILED! DISABLING BETTER GETBLOCKFROMCACHE");
                Debug.debug(e);
                INVOKE_LOADEDCHUNK=false;
            }
        }
        return CONTROLLER.getBlockDataFromCache(loc);
    }
    public static SlimefunBlockData safeGetBlockCacheWithLoad(Location loc){
        SlimefunBlockData data=safeGetBlockDataFromCache(loc);
        if (data==null){
            return null;
        }
        if(!data.isDataLoaded()){
            StorageCacheUtils.requestLoad(data);
            return data;
        }
        return data;
    }
    public static SlimefunBlockData safeLoadBlock(Location loc){
        SlimefunBlockData data= safeGetBlockDataFromCache(loc);
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
    public static void requestLoad(SlimefunBlockData data){
        StorageCacheUtils.requestLoad(data);
    }
    public static boolean moveSlimefunBlockData(Location loc1,Location loc2){
        SlimefunBlockData data=safeGetBlockCacheWithLoad(loc1);
        SlimefunBlockData data2=safeGetBlockCacheWithLoad(loc2);
        if(data!=null){
            if(data2!=null){
                Debug.debug("remove called");
                CONTROLLER.removeBlock(loc2);
            }
            CONTROLLER.setBlockDataLocation(data,loc2);
            return true;
        }
        else {return false;
        }
        //这里是否要发出SlimefunBlockPlaceEvent?
    }


    public static BlockMenu getMenu(Location loc) {
        SlimefunBlockData blockData = safeLoadBlock(loc);
        if (blockData == null) {
            return null;
        } else {
            return blockData.getBlockMenu();
        }
    }
    public static int getCustomData(Location loc,String key,int defaultValue){
        SlimefunBlockData data= safeLoadBlock(loc);
        return getCustomData(data,key,defaultValue);
    }
    public static void setCustomData(Location loc ,String key,int value){
        safeSetData(loc,key,String.valueOf(value));
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
        safeSetData(data,key,String.valueOf(value));
    }
    public static String getCustomString(Location loc,String key,String defaultValue){
        SlimefunBlockData data= safeLoadBlock(loc);
        return getCustomString(data,key,defaultValue);
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
    public static void setCustomString(Location loc,String key,String value){
        safeSetData(loc,key,value);
    }
    public static void setCustomString(SlimefunBlockData data,String key,String value){
        safeSetData(data,key,value);
    }
}
