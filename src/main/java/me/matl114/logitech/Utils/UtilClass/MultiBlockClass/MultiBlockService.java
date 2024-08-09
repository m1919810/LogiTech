package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockPart;
import org.bukkit.Location;


import java.util.*;

public class MultiBlockService {
    //?
    public static final HashMap<String,MultiBlockHandler> MULTIBLOCK_CACHE = new LinkedHashMap<>();

    public static void setMultiBlockStatus(){

    }
    public boolean getOnline(Location loc){
        try{
            String __=StorageCacheUtils.getData(loc,"mb-ol");
            if("1".equals(__)){
                return true;
            }else{
                return false;
            }
        }catch(Throwable e){
            setOnline(loc,false);
            return false;
        }
    }
    public void setOnline(Location loc,boolean toggle){
        if(toggle){
            StorageCacheUtils.setData(loc,"mb-ol","1");
        }else {
            StorageCacheUtils.setData(loc,"mb-ol","0");
        }
    }

    public static boolean createHandler(Location loc, MultiBlockType type){
        //check
        return true;
    }
    public static boolean tickCore(Location loc, UUID uid){
        MultiBlockHandler handler = MULTIBLOCK_CACHE.get(uid);
        if(handler == null){
            //can't find,must be last server's , return false,trigger core's build method
            return false;
        }
        //find current working and start work
        return true;


    }
    public static String getPartId(Location location){
        SlimefunItem blocks= StorageCacheUtils.getSfItem(location);
        if(blocks==null){
            return "";
        }else if(blocks instanceof MultiBlockPart){
            return ((MultiBlockPart)blocks).getPartId();
        }else return "";
    }
}
