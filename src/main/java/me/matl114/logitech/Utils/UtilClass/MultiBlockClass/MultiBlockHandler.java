package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore;
import me.matl114.logitech.SlimefunItem.Blocks.MultiCore;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MultiBlockHandler  implements AbstractMultiBlockHandler {

    private final AbstractMultiBlock STRUCTURE_TYPE;
    public final Location CORE;
    private boolean active = false;
    private final int[] RESPOND;
    public final  String uid;
    private MultiBlockHandler(AbstractMultiBlock type, Location core,String uid) {
        this.STRUCTURE_TYPE = type;
        this.CORE=core;
        this.uid=uid;
        this.RESPOND=new int[type.getStructureSize()];
        for (int i = 0; i < type.getStructureSize(); i++) {
            this.RESPOND[i]=3;
        }
    }
    public static AbstractMultiBlockHandler createHandler( Location core,AbstractMultiBlock type,String uid) {
        //check
        MultiBlockHandler multiBlockHandler = new MultiBlockHandler(type, core, uid);
        multiBlockHandler.active=true;
        int len=multiBlockHandler.RESPOND.length;
        for(int i=0;i<len;i++){


            Vector delta= type.getStructurePart(i);
            Location partloc=core.clone().add(delta);

            //设置部件的响应目标
            DataCache.setLastUUID(partloc,uid);
            //启用部件的响应
            MultiBlockService.setStatus(partloc,1);
            //设置部件的响应码
            setPartIndex(partloc,i);
        }
        //设置部件的响应目标
        DataCache.setLastUUID(core,uid);
        //启用部件的响应
        MultiBlockService.setStatus(core,1);
        //on enable
        SlimefunItem it=StorageCacheUtils.getSfItem(core);
        if(it instanceof MultiBlockCore){
            ((MultiBlockCore) it).onMultiBlockEnable(core,multiBlockHandler);
        }
        return multiBlockHandler;
    }
    public Location getCore() {
        return CORE.clone();
    }
    public int getSize(){
        return RESPOND.length;
    }
    public String getUid() {
        return uid;
    }
    public boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active=active;
    }
    /**
     * try match given place as core with multiblock schema
     * used when autoreconnnected after server restart
     * @return
     */
    public boolean checkIfComplete(){
        int len=this.RESPOND.length;
        boolean complete=true;
        for(int i=0;i<len;i++){
            Vector delta= STRUCTURE_TYPE.getStructurePart(i);
            Location partloc=CORE.clone().add(delta);
            //如果当前匹配 且响应当前uid才允许过，否则任意一条均为false
            if((!STRUCTURE_TYPE.getStructurePartId(i).equals(MultiBlockService.safeGetPartId(partloc)))
                    ||(!this.uid.equals(MultiBlockService.safeGetUUID(partloc)))){
                complete=false;
                break;
            }
        }
        return complete;
    }
    public boolean checkIfCompleteRandom(){
        int len=this.RESPOND.length;
        boolean complete=true;
        int checkChance=10;//随机选取1/10的方块检测
        for(int s=0;s<len;s++){
            int i= AddUtils.random(checkChance);
            if(i==0){
                Vector delta= STRUCTURE_TYPE.getStructurePart(s);
                Location partloc=CORE.clone().add(delta);
                //如果当前匹配 且响应当前uid才允许过，否则任意一条均为false
                if((!STRUCTURE_TYPE.getStructurePartId(s).equals(MultiBlockService.safeGetPartId(partloc)))
                        ||(!this.uid.equals(MultiBlockService.safeGetUUID(partloc)))){
                    complete=false;
                    break;
                }
            }
        }
        return complete;
    }
    public void acceptPartRequest(Location loc){
        if(this.active){
            int index=getPartIndex(loc);
            if(index>=0){
                RESPOND[index]=3;

            }
        }else {
            //multiblock被关闭 被抛出但是有part恰好响应了，则手动关掉part
            MultiBlockService.setStatus(loc,0);
            DataCache.setLastUUID(loc,"null");
        }
    }
    public boolean acceptCoreRequest(){
        if(checkIfOnline()){//need continue
            //I don't know what to said
            return true;
        }else{
            //销毁multiblock的全部内容
            destroy();
            return false;
        }
    }
    public static int getPartIndex(Location loc){
        try{
            String __= StorageCacheUtils.getData(loc,"mb-idx");
            if(__!=null){
                return Integer.parseInt(__);
            }
        }catch(Throwable e){
        }
        setPartIndex(loc,-1);
        return -1;
    }
    public static void setPartIndex(Location loc,int index){
        StorageCacheUtils.setData(loc,"mb-idx",String.valueOf(index));
    }

    public boolean checkIfOnline(){
        //there is no need to check!
        //if there's any shit who dies, they send deleteMultiBlock or they are not dying
//        for (int i=0;i<this.RESPOND.length;i++){
//            if (this.RESPOND[i]>=4){
//                //keep
//            }else if(this.RESPOND[i]>0){
//                //common tick
//                --this.RESPOND[i];
//            }
//            else{
//                //check sf id if match ,set to 20,else return false
//                Location blockloc=getBlockLoc(i);
//                if(this.STRUCTURE_TYPE.getStructurePartId(i).equals(MultiBlockService.getPartId(blockloc))
//                        &&this.uid.equals(DataCache.getLastUUID(blockloc))){
//                    //not respond by sf tick,because has sfid but no tick respond.
//                    this.RESPOND[i]=20;
//                }else {
//                    //has tick,no respond,no match sfid means it has been destroyed
//                    return false;
//                }
//            }
//        }
        return this.active;
    }

    public Location getBlockLoc(int index){
        Vector delta=STRUCTURE_TYPE.getStructurePart(index);
        return this.CORE.clone().add(delta);
    }
    public void destroy(){
        this.active=false;
        if(DataCache.hasData(CORE)){
            DataCache.setLastUUID(CORE,"null");
            MultiBlockService.setStatus(CORE,0);
            SlimefunItem it=DataCache.getData(CORE);
            if(it instanceof MultiBlockCore){
                ((MultiBlockCore) it).onMultiBlockDisable(CORE,this);
            }
        }
        for(int i=0;i<this.RESPOND.length;i++){
            Location loc=CORE.clone().add(STRUCTURE_TYPE.getStructurePart(i));
            if(DataCache.hasData(loc)){
                DataCache.setLastUUID(loc,"null");
                MultiBlockService.setStatus(loc,0);
            }
        }
    }

}
