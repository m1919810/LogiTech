package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.core.Blocks.MultiBlockCore.MultiBlockCore;
import me.matl114.logitech.Utils.DataCache;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Random;

public class MultiBlockHandler  implements AbstractMultiBlockHandler {

    private final AbstractMultiBlock STRUCTURE_TYPE;
    public final Location CORE;
    private boolean active = false;
    private final int size;
    public final  String uid;
    public MultiBlockService.DeleteCause lastDeleteCause=MultiBlockService.GENERIC;
    private MultiBlockHandler(AbstractMultiBlock block, Location core,String uid) {
        this.STRUCTURE_TYPE =block;
        this.CORE=core;
        this.uid=uid;
        this.size=block.getStructureSize();
    }

    public static AbstractMultiBlockHandler createHandler( Location core,AbstractMultiBlock type,String uid) {
        //check
        MultiBlockHandler multiBlockHandler = new MultiBlockHandler(type, core, uid);
        multiBlockHandler.active=true;
        int len=multiBlockHandler.size;
        for(int i=0;i<len;i++){


            Vector delta= type.getStructurePart(i);
            Location partloc=core.clone().add(delta);
            //设置部件的响应目标
            MultiBlockService.setUUID(partloc,uid);
            //启用部件的响应
            MultiBlockService.setStatus(partloc,1);
            //设置部件的响应码
        }
        //设置部件的响应目标
        MultiBlockService.setUUID(core,uid);
        //启用部件的响应
        MultiBlockService.setStatus(core,1);
        //on enable
        SlimefunItem it=DataCache.getSfItem(core);
        if(it instanceof MultiBlockCore){
            ((MultiBlockCore) it).onMultiBlockEnable(core,multiBlockHandler);
        }
        return multiBlockHandler;
    }
    public AbstractMultiBlock getMultiBlock(){
        return STRUCTURE_TYPE;
    }
    public Location getCore() {
        return CORE.clone();
    }
    public MultiBlockService.Direction getDirection(){
        return this.STRUCTURE_TYPE.getDirection();
    }
    public int getSize(){
        return size;
    }
    public String getUid() {
        return uid;
    }
    public boolean isActive(){
        return active;
    }
    public void toggleOff(MultiBlockService.DeleteCause cause){

        this.lastDeleteCause=cause;
        this.active=false;
    }
    public MultiBlockHandler setDeleteCause(MultiBlockService.DeleteCause deleteCause){
        this.lastDeleteCause=deleteCause;
        return this;
    }
    public MultiBlockService.DeleteCause getLastDeleteCause(){
        return lastDeleteCause;
    }
    /**
     * try match given place as core with multiblock schema
     * used when autoreconnnected after server restart
     * @return
     */
    public int checkIfComplete(){
        int len=this.size;
        for(int i=0;i<len;i++){
            Vector delta= STRUCTURE_TYPE.getStructurePart(i);
            Location partloc=CORE.clone().add(delta);
            //如果当前匹配 且响应当前uid才允许过，否则任意一条均为false
            if((!STRUCTURE_TYPE.getStructurePartId(i).equals(MultiBlockService.safeGetPartId(partloc)))
                    ||(!this.uid.equals(MultiBlockService.safeGetUUID(partloc)))){
                return i;
            }
        }
        return -1;
    }
    protected Random rand=new Random();
    public int checkIfCompleteRandom(){
        int len=this.size;
        int checkChance=10;//随机选取1/10的方块检测
        for(int s=0;s<len;s++){
            int i=rand.nextInt(checkChance);
            if(i==0){
                Vector delta= STRUCTURE_TYPE.getStructurePart(s);
                Location partloc=CORE.clone().add(delta);
                //如果当前匹配 且响应当前uid才允许过，否则任意一条均为false
                if((!STRUCTURE_TYPE.getStructurePartId(s).equals(MultiBlockService.safeGetPartId(partloc)))
                        ||(!this.uid.equals(MultiBlockService.safeGetUUID(partloc)))){
                    return s;
                }
            }
        }
        return -1;
    }
    public void acceptPartRequest(Location loc){
        if(this.active){
//            int index=getPartIndex(loc);
//            if(index>=0){
//
//            }
        }else {
            //multiblock被关闭 被抛出但是有part恰好响应了，则手动关掉part
            MultiBlockService.setStatus(loc,0);
            MultiBlockService.setUUID(loc,"null");
        }
    }

    /**
     * should tell if this is active and complete,then the service will called handler.destroy if not
     * @return
     */
    public boolean acceptCoreRequest(){
        return checkIfOnline();

    }
    public boolean checkIfOnline(){

        return this.active;
    }

    public Location getBlockLoc(int index){
        Vector delta=STRUCTURE_TYPE.getStructurePart(index);
        return this.CORE.clone().add(delta);
    }

    /**
     * this method should called onMultiBlockDisable for CORE and reset blockdata!
     */
    public void destroy(MultiBlockService.DeleteCause cause){
        this.active=false;
        //if(DataCache.hasData(CORE)){
        MultiBlockService.setUUID(CORE,"null");
        MultiBlockService.setStatus(CORE,0);
        SlimefunItem it=DataCache.getSfItem(CORE);
        if(it instanceof MultiBlockCore){
            ((MultiBlockCore) it).onMultiBlockDisable(CORE,this,cause);
        }
        //}
        for(int i=0;i<this.size;i++){
            Location loc=CORE.clone().add(STRUCTURE_TYPE.getStructurePart(i));
            if(DataCache.hasData(loc)){
                MultiBlockService.setUUID(loc,"null");
                MultiBlockService.setStatus(loc,0);
            }
        }
    }

}
