package me.matl114.logitech.utils.UtilClass.MultiBlockClass;


import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.FunctionalClass.OutputStream;
import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class MultiBlockType implements AbstractMultiBlockType {
    private HashMap<BlockVector,String> STRUCTURE_MAP;
    private HashMap<BlockVector,String> REQUIREMENT_MAP;
    private BlockVector[] STRUCTURE_LOC;
    private String[] STRUCTURE_IDS;
    private BlockVector[] REQUIREMENT_LOC;
    private String[] REQUIREMENT_IDS;
    private int size;
    private int sizeR;
    boolean isFinal=false;
    protected boolean isSymmetric=false;


    public abstract void init();
    public MultiBlockType() {
        this.STRUCTURE_MAP = new LinkedHashMap<BlockVector,String>();
        this.REQUIREMENT_MAP=new LinkedHashMap<>();
    }
    public BlockVector[] getStructurePos() {
        return STRUCTURE_LOC;
    }
    public BlockVector getSchemaPart(int index){
        return STRUCTURE_LOC[index].clone();
    }
    public int getSchemaSize() {
        return size;
    }
    public int getRequirementSize() {return sizeR;}
    public String getRequirementPartId(int index) {return REQUIREMENT_IDS[index];}
    public BlockVector getRequirementPart(int index) {return REQUIREMENT_LOC[index].clone();}

    public String[] getStructureIds() {
        return STRUCTURE_IDS;
    }
    public String getSchemaPartId(int index) {
        return STRUCTURE_IDS[index];
    }
    public boolean isSymmetric(){
        return isSymmetric;
    }
    public MultiBlockType build(){
        init();
        isFinal=true;
        this.size=STRUCTURE_MAP.size();
        this.sizeR=REQUIREMENT_MAP.size();
        this.STRUCTURE_LOC=new BlockVector[STRUCTURE_MAP.size()];
        this.STRUCTURE_IDS=new String[STRUCTURE_MAP.size()];
        int i=0;
        for(Map.Entry<BlockVector,String> entry : STRUCTURE_MAP.entrySet()){
            this.STRUCTURE_IDS[i]=entry.getValue();
            this.STRUCTURE_LOC[i]=entry.getKey();
            ++i;
        }
        this.REQUIREMENT_LOC=new BlockVector[REQUIREMENT_MAP.size()];
        this.REQUIREMENT_IDS=new String[REQUIREMENT_MAP.size()];
        int i1=0;
        for(Map.Entry<BlockVector,String> entry : REQUIREMENT_MAP.entrySet()){
            this.REQUIREMENT_IDS[i1]=entry.getValue();
            this.REQUIREMENT_LOC[i1]=entry.getKey();
            ++i1;
        }
        this.REQUIREMENT_MAP.clear();
        this.STRUCTURE_MAP.clear();
        this.REQUIREMENT_MAP=null;
        this.STRUCTURE_MAP=null;
        return this;
    }
    public MultiBlockType addBlock(int x, int y, int z,String id) {
        if(x==0 && y==0 && z==0){
            return this;
        }
        STRUCTURE_MAP.put(new BlockVector(x,y,z),id);
        return this;
    }

    /**
     * used in check multiblock from ,only when first setup(hasPrevRecord=false)
     * keep some place with air, so that some block can be generated
     * @param x
     * @param y
     * @param z
     * @param id
     * @return
     */
    public MultiBlockType addRequirement(int x, int y, int z, String id) {
        if(x==0 && y==0 && z==0){
            return this;
        }
        REQUIREMENT_MAP.put(new BlockVector(x,y,z),id);
        return this;
    }
    public AbstractMultiBlock genMultiBlockFrom(Location loc, MultiBlockService.Direction dir, boolean hasPrevRecord, OutputStream errorOut){
        int len=this.getSchemaSize();
        for(int i=0;i<len;i++){
            Vector delta= dir.rotate(this.getSchemaPart(i));
            Location partloc=loc.clone().add(delta);
            //Debug.logger("check blockid ",i,this.getSchemaPartId(i),"check target id ",MultiBlockService.safeGetPartId(partloc));
            //如果当前匹配 且并未在响应任意其他的多方块才能通过，否则任意一条均为false
            if(!this.getSchemaPartId(i).equals(MultiBlockService.safeGetPartId(partloc))){
              //  Debug.logger("wrong at ",delta.toString());
                final int a=i;
                errorOut.out(()->{
                    return "&c位于%s的多方块部件并不是有效的部件,需要%s".formatted(DataCache.locationToDisplayString(partloc),this.getSchemaPartId(a));
                });
                return null;
            }else{
                //use record but target block uuid not match core uuid
                //used to  reload multiblock Structure after server restart
//                if(hasPrevRecord&&false){
//                //    Debug.logger("wrong at ",delta.toString());
//                    errorOut.out(()->"&c错误!你不该看见该消息,请联系作者");
//                    return null;
//                    //no record but target block has been occupied by sth
//                }else
                if(!hasPrevRecord&&MultiBlockService.validHandler(MultiBlockService.safeGetUUID(partloc))){//如果是当前已经注册的别的机器的
                   // Debug.logger("wrong at ",delta.toString());
                    errorOut.out(()->{return "&c结构冲突!位于%s的部件属于另个机器! id-%s,位于%s".formatted(DataCache.locationToDisplayString(partloc),MultiBlockService.safeGetUUID(partloc),DataCache.locationToDisplayString( MultiBlockService.getCore(MultiBlockService.safeGetUUID(partloc))));});
                        return null;
                }
            }
        }
        if(!hasPrevRecord){
            int len2=REQUIREMENT_LOC.length;
            for(int i=0;i<len2;i++){
                Location reqLoc=loc.clone().add(REQUIREMENT_LOC[i]);
                if(!REQUIREMENT_IDS[i].equals(MultiBlockService.safeGetPartId(reqLoc))){
                    //Debug.logger("wrong at ",entry.getKey().toString());
                    int a=i;
                    errorOut.out(()-> {return "&c位于%s的方块并不满足构建所需要的额外条件".formatted(DataCache.locationToDisplayString(reqLoc));});
                    return null;
                }
            }
        }
      //  Debug.logger("check finished ,return value ",this.getSchemaSize(),"and req ",REQUIREMENT_MAP.size());
        return new MultiBlock(this,dir);
    }
    public List<String> getRequiredArguments(){
        return List.of();
    }

    public Map<Vector,String> getMultiBlockSchemaFromArguments(MultiBlockService.Direction dir,Map<String,String> arguments){
        if(isSymmetric()){
            dir= MultiBlockService.Direction.NORTH;
        }
        Map<Vector,String> result=new HashMap<>();
        int size = getSchemaSize();
        for(int i=0; i<size; i++){
            result.put(dir.rotate(this.getSchemaPart(i)),getSchemaPartId(i));
        }
        int requirement = getRequirementSize();
        for(int i=0; i<requirement; i++){
            result.put(dir.rotate(this.getRequirementPart(i)),getRequirementPartId(i));
        }
        return result;
    }
}
