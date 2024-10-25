package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.Schedule.ScheduleSave;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockPart;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.EntityClass.ItemDisplayBuilder;
import me.matl114.logitech.Utils.UtilClass.EntityClass.TransformationBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


import javax.xml.crypto.Data;
import java.util.*;

public class MultiBlockService {
    //?
    public static void setup(){
        ScheduleSave.addFinalTask(()->{
            Set<Location> locs=new HashSet<>(HOLOGRAM_CACHE.keySet());
            for(Location loc:locs){
                removeHologramSync(loc);
            }
        });
    }

    /**
     * returned mbid when no sf block and not multipart
     */
    public static final String MBID_NOSFBLOCK="nu";
    public static final String MBID_COMMONSFBLOCK="sf";
    public static final HashMap<String,AbstractMultiBlockHandler> MULTIBLOCK_CACHE = new LinkedHashMap<>();
    public static final HashMap<Location, DisplayGroup> HOLOGRAM_CACHE=new HashMap<>();
    public static final Random rand=new Random();
    public static final int MAX_MB_NUMBER=1_000_000;
    public static boolean validHandler(String uid){
        AbstractMultiBlockHandler handler=MULTIBLOCK_CACHE.get(uid);
        if(handler==null){
            return false;
        }else {
            String uuid=safeGetUUID(handler.getCore());
            if(uid.equals(uuid)){//uid 核验,说明人没炸 uuid nullable
                return true;
            }
            return false;
        }
    }
    public static class DeleteCause{

        String cause;
        boolean willexplode;
        public DeleteCause(String cause,boolean willExplode){
            this.cause=cause;
            this.willexplode=willExplode;
        }
        public boolean willExplode(){
            return this.willexplode;
        }
        public String getMessage(){
            return this.cause;
        }
    }
    public static class MultiBlockBreakCause extends DeleteCause{
        Block b;
        public static MultiBlockBreakCause get(Block b){
            return new MultiBlockBreakCause(b);
        }
        public MultiBlockBreakCause(Block b){
            super(AddUtils.concat(DataCache.locationToDisplayString(b.getLocation()),"的方块",b.getType().toString(),"被人为破坏"),true);
            this.b=b;
        }
    }
    public static class EnergyOutCause extends DeleteCause{
        int energy;
        int energyCom;
        public static EnergyOutCause get(int energy,int energyCom){
            return new EnergyOutCause(energy,energyCom);
        }
        public EnergyOutCause(int energy,int energyCom){
            super(AddUtils.concat("电力不足! ",String.valueOf( energy),"J/ ",String.valueOf( energyCom),"J"),true);
            this.energy=energy;
            this.energyCom=energyCom;

        }
    }
    public static class StructureBreakCause extends DeleteCause{
        public static StructureBreakCause get(AbstractMultiBlockHandler handler,int index){
           Location loc=  handler.getCore().clone().add(handler.getMultiBlock().getStructurePart(index));
           String getId=safeGetPartId(loc);
           String shouldId=handler.getMultiBlock().getStructurePartId(index);
           return new StructureBreakCause(loc,getId,shouldId);
        }
        public StructureBreakCause(Location loc ,String getId,String shouldId){
            super(AddUtils.concat("多方块结构自检失败,在",
                    DataCache.locationToDisplayString(loc),
                    "处检测到 [",
                    getId,"],实际应为 [",shouldId,"]"),true);
        }
    }
    public static DeleteCause MANUALLY=new DeleteCause("手动关闭",true);
    public static DeleteCause GENERIC=new DeleteCause("服务器重启",false);
    public static void deleteMultiBlock(String uid,DeleteCause cause){
        AbstractMultiBlockHandler handler = MULTIBLOCK_CACHE.remove(uid);
        if(handler != null){
            handler.destroy(cause);
        }
    }
    public static void deleteMultiBlock(Location core,DeleteCause cause){
        Iterator<Map.Entry<String,AbstractMultiBlockHandler>> iterator = MULTIBLOCK_CACHE.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,AbstractMultiBlockHandler> entry = iterator.next();
            if(entry.getValue().getCore().equals(core)){
                entry.getValue().destroy(cause);
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
        public int getRotateX(int x,int z){
            return x*dx+z*dy;
        }
        public int getRotateZ(int x,int z){
            return -x*dy+z*dx;
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
        public static Direction fromInt(int i){
            switch(i){
                case 0:return Direction.NORTH;
                case 1:return Direction.EAST;
                case 2:return Direction.SOUTH;
                case 3:return Direction.WEST;
                default:return Direction.NORTH;
            }
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

    public static String getRandomId(){
        String nextValue;
        do{
            nextValue=String.valueOf(rand.nextInt(MAX_MB_NUMBER));
        }while (MULTIBLOCK_CACHE.containsKey(nextValue));
        return nextValue;
    }
    public static boolean createNewHandler(Location loc,MultiBlockBuilder builder,AbstractMultiBlockType type){
        int statusCode=safeGetStatus(loc);
        if(statusCode==0){
            AbstractMultiBlock block= tryCreateMultiBlock(loc,type);
            if(block!=null){
                Direction.setDirection(loc,block.getDirection());
                String uid=getRandomId();
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
    public static boolean checkIfAbsentRuntime(SlimefunBlockData data){
        String uid=DataCache.getLastUUID(data);
        AbstractMultiBlockHandler handler=MULTIBLOCK_CACHE.get(uid);
        if(handler==null){
            return false;
        }else {
            int index=handler.checkIfCompleteRandom();
            if( index<0){
                return true;
            }else {
                //发出关闭信号,等待下一次coreRequest响应

                handler.toggleOff(StructureBreakCause.get(handler,index));
                return false;
            }
        }
    }
    public static void toggleOff(SlimefunBlockData data,DeleteCause cause){
        if(data==null)return;
        String uid=DataCache.getLastUUID(data);
        AbstractMultiBlockHandler handler=MULTIBLOCK_CACHE.get(uid);
        if(handler!=null){
            handler.toggleOff(cause);
        }
    }
        //called in tickers
    public static Location acceptPartRequest(Location loc){
        int statusCode=getStatus(loc);
        if(statusCode==0){
            return null;
        }
        String uid=DataCache.getLastUUID(loc);
        AbstractMultiBlockHandler handler=MULTIBLOCK_CACHE.get(uid);
        if(statusCode==1){
            if(handler==null){
                setStatus(loc,-3);
                return null;
            }else {
                handler.acceptPartRequest(loc);
                return handler.getCore();
            }
        }else {
            if(handler!=null){
                //reconnect success ,set status to 1
                handler.acceptPartRequest(loc);
                setStatus(loc,1);
                return handler.getCore();
            }//quit
            //no reconnect ,statusCode == -1 means 3tick reconnect time is end,toggleOff
            if(statusCode==-1){
                DataCache.setLastUUID(loc,"null");
            }
            setStatus(loc,statusCode+1);
            return null;
        }
    }

    /**
     * run on loaded ticker
     * @param loc
     * @param reconnect
     * @param type
     * @return
     */
    public static DeleteCause AUTOCONNECT_FAILED=new DeleteCause("初始化多方块结构失败",false);
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
                    String newuid=getRandomId();
                    while(MULTIBLOCK_CACHE.containsKey(newuid)){
                        newuid=getRandomId();
                    }
                    AbstractMultiBlockHandler handler2=reconnect.build(loc,block,newuid);
                    MULTIBLOCK_CACHE.put(newuid,handler2);
                    return true;
                }else{
                    if(statusCode>0){//之前还在运行
                        //为了给loadrequest一个缓冲。
                        //防止直接返回0直接爆
                        statusCode=-20;//自动重连20次
                    }
                    if(statusCode==-18){
                        Debug.debug("LOADING PLAN B !");
                    }
                    setStatus(loc,statusCode+1);
                    if(statusCode==-1){
                        DataCache.setLastUUID(loc,"null");
                        return false;
                    }
                    return true;
                }
            }else {
                if( handler.acceptCoreRequest()){
                    return true;
                }else {
                    //contains auto called delete
                    handler.destroy(handler.getLastDeleteCause());
                    setStatus(loc,0);
                    DataCache.setLastUUID(loc,"null");

                    return false;
                }
            }
        }
    }


    public static void createHologram(Location loc, AbstractMultiBlockType type, Direction direction, HashMap<String, ItemStack> itemmap){
        Location tar=loc.clone().add(0.5,0.5,0.5);
        final DisplayGroup displayGroup = new DisplayGroup(tar,0.1f,0.1f);
        int len=type.getSchemaSize();
        if(type.isSymmetric()){
            direction=Direction.NORTH;
        }
        for(int i=0;i<len;i++){
            displayGroup.addDisplay(
                    "logitech.display.%d".formatted(i),
                    new ItemDisplayBuilder()
                            .setGroupParentOffset(direction.rotate(type.getSchemaPart(i)))
                            .setItemStack(itemmap.get(type.getSchemaPartId(i)))
                            .setTransformation(new TransformationBuilder().scale(0.5f,0.5f,0.5f).build())
                            .setSource(DataCache.locationToString(loc))
                            .build(displayGroup)
            );
        }
        HOLOGRAM_CACHE.put(loc,displayGroup);
    }
    //solar logitech
    public static void removeUnrecordedHolograms(Location loc,int range){
        Collection<Entity> entities=loc.getWorld().getNearbyEntities(loc,range,range,range,(e)->e instanceof Display);
        if(entities==null|| entities.isEmpty()){
            return;
        }
        HashSet<Display> registeredHolograms=new HashSet<>();
        for(DisplayGroup group:HOLOGRAM_CACHE.values()){
            registeredHolograms.addAll(group.getDisplaySet());
        }
        for(Entity e:entities){
            if((e instanceof Display|| e instanceof Interaction)&&!registeredHolograms.contains(e)){
                String source=ItemDisplayBuilder.getSource(e);
                if(source!=null){
                    e.remove();
                }
            }
        }
    }

    public static void removeHologram(Location loc){
        removeHologramSync(loc);
    }
    public static void removeHologramSync(Location loc){
        DisplayGroup displayGroup=HOLOGRAM_CACHE.remove(loc);
        if(displayGroup!=null){
            BukkitUtils.executeSync(()->{
                displayGroup.remove();
            });
        }
        DataCache.setCustomData(loc,"holo",0);
    }

    /**
     * get status code ,0为暂停 1为正常运行 -3到-1为预备暂停
     * @return
     */
    public static int getStatus(SlimefunBlockData data){
        try{
            String __= data.getData("mb-sta");
            if(__!=null){
                return Integer.parseInt(__);
            }
        }catch(Throwable e){
        }
        data.setData("mb-sta","0");
        return 0;
    }
    public static  int getStatus(Location loc){
        return safeGetStatus(loc);
    }

    /**
     * get status safely load ,please make sure loc has sfblock!
     * @param loc
     * @return
     */
    public static  int safeGetStatus(Location loc){
        SlimefunBlockData data=DataCache.safeLoadBlock(loc);
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
        SlimefunBlockData data= DataCache.safeLoadBlock(loc);
        DataCache.setCustomString(data,"mb-sta",String.valueOf(status));
    }
    public static void setStatus(SlimefunBlockData data, int status){
        data.setData("mb-sta",String.valueOf(status));
    }
    public static String safeGetUUID(Location loc){
        String uuid;
        SlimefunBlockData data=DataCache.safeLoadBlock(loc);
        try{
            uuid= data.getData("uuid");
            if(uuid!=null)
                return uuid;
        }catch (Throwable a){

        }
        data.setData("uuid","null");
        return "null";
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
        SlimefunBlockData blockdata= DataCache.safeLoadBlock(location);
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
