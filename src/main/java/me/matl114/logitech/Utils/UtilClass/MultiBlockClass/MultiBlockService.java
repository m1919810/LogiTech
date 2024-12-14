package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.Schedule.ScheduleSave;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore.MultiBlockCore;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore.MultiBlockPart;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.EntityClass.ItemDisplayBuilder;
import me.matl114.logitech.Utils.UtilClass.EntityClass.TransformationBuilder;
import me.matl114.logitech.Utils.UtilClass.FunctionalClass.OutputStream;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
    public static final String MBID_AIR ="nu";
   // protected static final String MBID_COMMONSFBLOCK="sf";
    public static String getAutoKey(){
        return MB_AUTO_KEY;
    }
    protected static final String MB_AUTO_KEY="auto";
    public static String getHologramKey(){
        return MB_HOLOGRAM_KEY;
    }
    protected static final String MB_HOLOGRAM_KEY="holo";
    //will be deprecated soon
    //
    @Deprecated
    private static final String MB_STATUS_KEY="mb-sta";
    @Deprecated
    private static final String MB_UUID_KEY="uuid";
    //move data key-value to cached status map
    private static final ConcurrentHashMap<Location, AtomicInteger> MB_STATUS_MAP=new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Location, AtomicReference<String>> MB_UUID_MAP=new ConcurrentHashMap<>();
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
    public static void handleVanillaBlockBreak(Location loc){
        if(getStatus(loc)!=0){
            MultiBlockService.deleteMultiBlock(safeGetUUID(loc), MultiBlockService.MultiBlockBreakCause.get(loc.getBlock()));
        }
    }

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
            String __= DataCache.getCustomString(loc,"mb-dir","0");
            switch(__){
                case "0":return Direction.NORTH;
                case "1":return Direction.EAST;
                case "2":return Direction.SOUTH;
                case "3":return Direction.WEST;
                default: return Direction.NORTH;
            }

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
            DataCache.setCustomString(loc,"mb-dir",str1);
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
        @Nullable
        AbstractMultiBlockHandler build(Location core,AbstractMultiBlock type,String uid );
    }
    /**
     * try match given place as core with multiblock schema
     * when init
     * @param loc
     * @param type
     * @return
     */
    public static AbstractMultiBlock tryCreateMultiBlock(Location loc, AbstractMultiBlockType type,OutputStream errorOut){
        //may not be loaded ,use safe read
        AbstractMultiBlock block=null;
        for (MultiBlockService.Direction direction:MultiBlockService.Direction.values()){
            //test this direction
            errorOut.out(()->"&a尝试以朝向 &e%s&a 构建多方块".formatted(direction.name()));
            block=type.genMultiBlockFrom(loc,direction,false,errorOut);

            if(block!=null){
                return block;
            }

            if(type.isSymmetric()){
                errorOut.out(()->"&c本多方块结构为中心对称多方块结构,构建失败");
                break;//only test one direction is Symmetric
            }
        }
        return null;
    }

    public static String getRandomId(){
        String nextValue;
        do{
            nextValue=String.valueOf(rand.nextInt(MAX_MB_NUMBER));
        }while (MULTIBLOCK_CACHE.containsKey(nextValue));
        return nextValue;
    }
    public static boolean createNewHandler(Location loc, MultiBlockBuilder builder, AbstractMultiBlockType type){
        return createNewHandler(loc,builder,type,OutputStream.getNullStream());
    }
    public static boolean createNewHandler(Location loc, MultiBlockBuilder builder, AbstractMultiBlockType type, OutputStream errorOut){
        int statusCode=safeGetStatus(loc);
        if(statusCode==0){
            AbstractMultiBlock block= tryCreateMultiBlock(loc,type,errorOut);
            if(block!=null){
                Direction.setDirection(loc,block.getDirection());
                String uid=getRandomId();
                AbstractMultiBlockHandler handler=builder.build(loc,block,uid);
                if(handler!=null){
                    MULTIBLOCK_CACHE.put(uid,handler);
                    return true;
                }else{
                    //builder refused
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return true;
        }
    }
    public static boolean checkIfAbsentRuntime(SlimefunBlockData data){
        String uid=safeGetUUID(data.getLocation());
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
        String uid=safeGetUUID(data.getLocation());
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
        String uid=safeGetUUID(loc);
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
                setUUID(loc,"null");
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
            //core are pure sf block
            String uid=safeGetUUID(loc);
            AbstractMultiBlockHandler handler=MULTIBLOCK_CACHE.get(uid);
            if(handler==null){
                //找不到handler 但是code非0 说明是意外中断，尝试重建handler
                AbstractMultiBlock block=type.genMultiBlockFrom(loc,Direction.getDirection(loc),true,OutputStream.getNullStream());
                if(block!=null){
                    String newuid=getRandomId();
                    while(MULTIBLOCK_CACHE.containsKey(newuid)){
                        newuid=getRandomId();
                    }
                    AbstractMultiBlockHandler handler2=reconnect.build(loc,block,newuid);
                    MULTIBLOCK_CACHE.put(newuid,handler2);
                    return true;
                }else{
                    //不进行倒计时,
                    setStatus(loc,-20);
                    return false;
                }
            }else {
                if( handler.acceptCoreRequest()){
                    return true;
                }else {
                    //contains auto called delete
                    handler.destroy(handler.getLastDeleteCause());
                    setStatus(loc,0);
                    MultiBlockService.setUUID(loc,"null");
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
        DataCache.setCustomData(loc,MB_HOLOGRAM_KEY,0);
    }

    @Deprecated
    public static  int getStatus(Location loc){
        return safeGetStatus(loc);
    }

    /**
     * get status safely load ,please make sure loc has sfblock!
     * @param loc
     * @return
     */
    /**
     * get status code ,0为暂停 1为正常运行 -3到-1为预备暂停
     * @return
     */
    public static int getStatus(SlimefunBlockData data){
        SlimefunItem item=SlimefunItem.getById(data.getSfId());
//        if(item==null){
        //this case shouldn't be present
//            return  MB_STATUS_MAP.getOrDefault(data.getLocation(),DEFAULT_STATUS).get();;
//        }
        if(item instanceof MultiBlockCore){
            try{
                String __=  data.getData(MB_STATUS_KEY);
                if(__!=null){
                    return Integer.parseInt(__);
                }
            }catch(Throwable e){
            }
            data.setData(MB_STATUS_KEY,"0");
            return 0;
        }else return MB_STATUS_MAP.computeIfAbsent(data.getLocation(),(l)->new AtomicInteger(0)).get();
    }
    public static  int safeGetStatus(Location loc){
        SlimefunItem item=DataCache.getSfItem(loc);
        if(item==null){
            //此处之后可以增加原版方块作为多方块部件?
            //MB_STATUS_MAP.remove(loc);

            return  MB_STATUS_MAP.getOrDefault(loc,DEFAULT_STATUS).get();
        }
        if(item instanceof MultiBlockCore){
            //with ticker ,should load automatically
            SlimefunBlockData data=DataCache.safeLoadBlock(loc);
            if(data!=null){
                try{
                    String __=  data.getData(MB_STATUS_KEY);
                    if(__!=null){
                        return Integer.parseInt(__);
                    }
                }catch(Throwable e){
                }
                data.setData(MB_STATUS_KEY,"0");
                return 0;
            }else {
                return -1;
            }
        }else return MB_STATUS_MAP.computeIfAbsent(loc,(l)->new AtomicInteger(0)).get();
    }
    public static void setStatus(Location loc, int status){
//        SlimefunBlockData data= DataCache.safeLoadBlock(loc);
//        DataCache.setCustomString(data,MB_STATUS_KEY,String.valueOf(status));
        SlimefunItem item=DataCache.getSfItem(loc);
        if(item==null){
            //此处之后可以增加原版方块作为多方块部件?
            //MB_STATUS_MAP.remove(loc);
            if(status==0){
                MB_STATUS_MAP.remove(loc);
            }else {
                MB_STATUS_MAP.computeIfAbsent(loc,(l)->new AtomicInteger(0)).set(status);
            }
            return;
        }
        if(item instanceof MultiBlockCore){
            DataCache.setCustomString(loc,MB_STATUS_KEY,String.valueOf(status));
        }else{
            MB_STATUS_MAP.computeIfAbsent(loc,(l)->new AtomicInteger(0)).set(status);
        }
    }
    private static AtomicInteger DEFAULT_STATUS=new AtomicInteger(0);
    private static AtomicReference<String> DEFAULT_UUID=new AtomicReference<>("null");
    public static String safeGetUUID(Location loc){
        SlimefunItem item=DataCache.getSfItem(loc);
        if(item==null){
            //此处之后可以增加原版方块作为多方块部件?
            //MB_UUID_MAP.remove(loc);
            return MB_UUID_MAP.getOrDefault(loc,DEFAULT_UUID).get();
        }
        if(item instanceof MultiBlockCore){
            String uuid;
            SlimefunBlockData data=DataCache.safeLoadBlock(loc);
            if(data!=null){
                try{
                    uuid= data.getData(MB_UUID_KEY);
                    if(uuid!=null)
                        return uuid;
                }catch (Throwable a){

                }
                data.setData(MB_UUID_KEY,"null");
            }
            return "null";
        }else {
            return MB_UUID_MAP.computeIfAbsent(loc,(l)->new AtomicReference<>("null")).get();
        }
    }
    public static void setUUID(Location loc, String uuid){
        SlimefunItem item=DataCache.getSfItem(loc);
        if(item==null){
            //此处之后可以增加原版方块作为多方块部件?
            //MB_UUID_MAP.remove(loc);
            if(uuid==null||"null".equals(uuid)){
                MB_UUID_MAP.remove(loc);
            }else {
                MB_UUID_MAP.computeIfAbsent(loc,(l)->new AtomicReference<>(null)).set(uuid);
            }
            return ;
        }
        if(item instanceof MultiBlockCore){
            DataCache.setCustomString(loc,MB_UUID_KEY,uuid);
        }else {
            MB_UUID_MAP.computeIfAbsent(loc,(l)->new AtomicReference<>(null)).set(uuid);
        }
    }
    /**
     *
     * @param location
     * @return
     */
    private static HashMap<Material,String> TAG_GROUP=new HashMap<>();
    public static void registerMaterialTag(Material material,String alias){
        TAG_GROUP.put(material,alias);
    }
    static {
        TAG_GROUP.put(Material.AIR,MBID_AIR);
    }
    public static String getPartId(Location location){
        SlimefunItem blocks= DataCache.getSfItem(location);
        if(blocks==null){
            Material type=location.getBlock().getType();
            String alias=TAG_GROUP.get(type);
            if(alias==null){
                return type.toString();
            }else {
                return alias;
            }
        }else if(blocks instanceof MultiBlockPart){
            return ((MultiBlockPart)blocks).getPartId();
        }else return MBID_AIR;
    }
    @Deprecated
    public static String safeGetPartId(Location location){
        return getPartId(location);
//        SlimefunBlockData blockdata= DataCache.safeLoadBlock(location);
//        if(blockdata!=null){
//            String blocks=blockdata.getSfId();
//            SlimefunItem block=blocks == null ? null : SlimefunItem.getById(blocks);
//            if(block==null){
//                return MBID_AIR;
//            }else if(block instanceof MultiBlockPart){
//                return ((MultiBlockPart)block).getPartId();
//            }else return MBID_COMMONSFBLOCK;
//        }else {
//            return MBID_AIR;
//        }
    }
}
