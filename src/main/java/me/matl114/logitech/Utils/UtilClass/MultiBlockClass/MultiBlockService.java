package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockPart;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import org.bukkit.Location;
import org.bukkit.util.Vector;


import java.util.*;

public class MultiBlockService {
    //?
    /**
     * returned mbid when no sf block and not multipart
     */
    public static final String MBID_NOSFBLOCK="nu";
    public static final String MBID_COMMONSFBLOCK="sf";
    public static final HashMap<String,AbstractMultiBlockHandler> MULTIBLOCK_CACHE = new LinkedHashMap<>();
    public static void deleteMultiBlock(String uid){
        AbstractMultiBlockHandler handler = MULTIBLOCK_CACHE.remove(uid);
        if(handler != null){
            handler.destroy();
        }
    }
    public static void deleteMultiBlock(Location core){
        Iterator<Map.Entry<String,AbstractMultiBlockHandler>> iterator = MULTIBLOCK_CACHE.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,AbstractMultiBlockHandler> entry = iterator.next();
            if(entry.getValue().getCore().equals(core)){
                entry.getValue().destroy();
                iterator.remove();
            }
        }
    }
    public static Location getCore(String uid){
        AbstractMultiBlockHandler handler = MULTIBLOCK_CACHE.get(uid);
        if(handler != null&&handler.isActive()){
            return handler.getCore();
        }return null;
    }
    public enum Direction{
        NORTH(1,0),
        WEST(0,1),
        SOUTH(-1,0),
        EAST(0,-1)
        ;
        int dx;
        int dy;
        private Direction(int dx,int dy){
            this.dx = dx;
            this.dy = dy;
        }
        //will return input one
        public Vector rotate(Vector v){
            double x=v.getBlockX()*dx+v.getBlockZ()*dy;
            double y=-v.getBlockX()*dy+v.getBlockZ()*dx;
            v.setX(x);
            v.setZ(y);
            return v;
        }
        public static Direction getDirection(Location loc){
            try{
                String __= StorageCacheUtils.getData(loc,"mb-dir");
                switch(__){
                    case "0":return Direction.NORTH;
                    case "1":return Direction.EAST;
                    case "2":return Direction.SOUTH;
                    case "3":return Direction.WEST;
                    default:
                }
            }catch(Throwable e){
            }
            setDirection(loc,Direction.NORTH);
            return Direction.NORTH;

        }
        public static void setDirection(Location loc,Direction dir){
            String str1;
            switch(dir){
                case EAST:str1="1";break;
                case SOUTH:str1="2";break;
                case WEST:str1="3";break;
                case NORTH:
                default:str1="0";
            }
            StorageCacheUtils.setData(loc,"mb-dir",str1);
        }
    }
    public interface MultiBlockBuilder{
        AbstractMultiBlockHandler build(Location core,AbstractMultiBlock type,String uid );
    }
    /**
     * try match given place as core with multiblock schema
     * when init
     * @param loc
     * @param type
     * @return
     */
    public static AbstractMultiBlock tryCreateMultiBlock(Location loc, AbstractMultiBlockType type){
        //may not be loaded ,use safe read
        AbstractMultiBlock block=null;
        for (MultiBlockService.Direction direction:MultiBlockService.Direction.values()){
            //test this direction
            block=type.genMultiBlockFrom(loc,direction,false);
            if(block!=null){
                return block;
            }

            if(type.isSymmetric()){
                break;//only test one direction is Symmetric
            }
        }
        return block;
    }
    public static boolean createNewHandler(Location loc,MultiBlockBuilder builder,AbstractMultiBlockType type){
        int statusCode=safeGetStatus(loc);
        if(statusCode==0){
            AbstractMultiBlock block= tryCreateMultiBlock(loc,type);
            if(block!=null){
                Direction.setDirection(loc,block.getDirection());
                String uid=AddUtils.getUUID();
                Debug.logger("check build uid ",uid);
                Debug.logger("start build, direction",block.getDirection());

                AbstractMultiBlockHandler handler=builder.build(loc,block,uid);
                MULTIBLOCK_CACHE.put(uid,handler);
                return true;
            }else {
                return false;
            }
        }else {
            return true;
        }
    }


//    //called in tickers
//    public static Location acceptPartRequest(Location loc){
//        int statusCode=getStatus(loc);
//        if(statusCode==0){
//            return null;
//        }
//        String uid=DataCache.getLastUUID(loc);
//        AbstractMultiBlockHandler handler=MULTIBLOCK_CACHE.get(uid);
//        if(statusCode==1){
//            if(handler==null){
//                setStatus(loc,-3);
//                return null;
//            }else {
//                handler.acceptPartRequest(loc);
//                return handler.getCore();
//            }
//        }else {
//            if(handler!=null){
//                //reconnect success ,set status to 1
//                handler.acceptPartRequest(loc);
//                setStatus(loc,1);
//                return handler.getCore();
//            }//quit
//            //no reconnect ,statusCode == -1 means 3tick reconnect time is end,toggleOff
//            if(statusCode==-1){
//                DataCache.setLastUUID(loc,"null");
//            }
//            setStatus(loc,statusCode+1);
//            return null;
//        }
//
//    }
    public static boolean acceptCoreRequest(Location loc,MultiBlockBuilder reconnect,AbstractMultiBlockType type){
        int statusCode=getStatus(loc);
        if(statusCode==0){
            return false;
        }else {
            String uid=DataCache.getLastUUID(loc);
            AbstractMultiBlockHandler handler=MULTIBLOCK_CACHE.get(uid);
            if(handler==null){
                //找不到handler 但是code非0 说明是意外中断，尝试重建handler
                AbstractMultiBlock block=type.genMultiBlockFrom(loc,Direction.getDirection(loc),true);
                if(block!=null){
                    String newuid=AddUtils.getUUID();
                    while(MULTIBLOCK_CACHE.containsKey(newuid)){
                        newuid=AddUtils.getUUID();
                    }
                    AbstractMultiBlockHandler handler2=reconnect.build(loc,block,newuid);
                    MULTIBLOCK_CACHE.put(newuid,handler2);
                    return true;
                }else{
                    if(statusCode>0){//之前还在运行
                        statusCode=-3;//自动重连三次
                    }
                    setStatus(loc,statusCode+1);
                    if(statusCode==-1){
                        DataCache.setLastUUID(loc,"null");
                    }
                    return false;
                }
            }else {
                if( handler.acceptCoreRequest()){
                    return true;
                }else {
                    deleteMultiBlock(handler.getUid());
                    setStatus(loc,0);
                    DataCache.setLastUUID(loc,"null");

                    return false;
                }
            }
        }
    }

    /**
     * get status code ,0为暂停 1为正常运行 -3到-1为预备暂停
     * @param loc
     * @return
     */
    public static  int getStatus(Location loc){
        try{
            String __= StorageCacheUtils.getData(loc,"mb-sta");
            if(__!=null){
                return Integer.parseInt(__);
            }
        }catch(Throwable e){
        }
        setStatus(loc,0);
        return 0;
    }

    /**
     * get status safely load ,please make sure loc has sfblock!
     * @param loc
     * @return
     */
    public static  int safeGetStatus(Location loc){
        SlimefunBlockData data=Slimefun.getDatabaseManager().getBlockDataController().getBlockData(loc);
        if(data!=null){
            try{
                String __=  data.getData("mb-sta");
                if(__!=null){
                    return Integer.parseInt(__);
                }
            }catch(Throwable e){
            }
            data.setData("mb-sta","0");
            return 0;
        }else {
            return -1;
        }
    }
    public static void setStatus(Location loc, int status){
        StorageCacheUtils.setData(loc,"mb-sta",String.valueOf(status));
    }

    /**
     *
     * @param location
     * @return
     */
    public static String getPartId(Location location){
        SlimefunItem blocks= StorageCacheUtils.getSfItem(location);
        if(blocks==null){
            return MBID_NOSFBLOCK;
        }else if(blocks instanceof MultiBlockPart){
            return ((MultiBlockPart)blocks).getPartId();
        }else return MBID_NOSFBLOCK;
    }
    public static String safeGetPartId(Location location){
        SlimefunBlockData blockdata= Slimefun.getDatabaseManager().getBlockDataController().getBlockData(location);
        if(blockdata!=null){
            String blocks=blockdata.getSfId();
            SlimefunItem block=blocks == null ? null : SlimefunItem.getById(blocks);
            if(block==null){
                return MBID_NOSFBLOCK;
            }else if(block instanceof MultiBlockPart){
                return ((MultiBlockPart)block).getPartId();
            }else return MBID_COMMONSFBLOCK;
        }else {
            return MBID_NOSFBLOCK;
        }
    }
}
