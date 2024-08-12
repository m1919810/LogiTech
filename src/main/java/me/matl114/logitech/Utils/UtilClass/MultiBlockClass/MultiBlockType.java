package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;


import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class MultiBlockType implements AbstractMultiBlockType {
    private final HashMap<BlockVector,String> STRUCTURE_MAP;
    private final HashMap<BlockVector,String> REQUIREMENT_MAP;
    private BlockVector[] STRUCTURE_LOC;
    private String[] STRUCTURE_IDS;
    private int size;
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
        this.STRUCTURE_LOC=new BlockVector[STRUCTURE_MAP.size()];
        this.STRUCTURE_IDS=new String[STRUCTURE_MAP.size()];
        int i=0;
        for(Map.Entry<BlockVector,String> entry : STRUCTURE_MAP.entrySet()){
            this.STRUCTURE_IDS[i]=entry.getValue();
            this.STRUCTURE_LOC[i]=entry.getKey();
            ++i;
        }
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
    public AbstractMultiBlock genMultiBlockFrom(Location loc, MultiBlockService.Direction dir,boolean hasPrevRecord){
        int len=this.getSchemaSize();
        String id= MultiBlockService.safeGetUUID(loc);
        for(int i=0;i<len;i++){
            Vector delta= dir.rotate(this.getSchemaPart(i));
            Location partloc=loc.clone().add(delta);
            //Debug.logger("check blockid ",i,this.getSchemaPartId(i),"check target id ",MultiBlockService.safeGetPartId(partloc));
            //如果当前匹配 且并未在响应任意其他的多方块才能通过，否则任意一条均为false
            if(!this.getSchemaPartId(i).equals(MultiBlockService.safeGetPartId(partloc))){
                //Debug.logger("wrong at ",delta.toString());
                return null;
            }else{
                //use record but target block uuid not match core uuid
                if(hasPrevRecord&&(!(id.equals( MultiBlockService.safeGetUUID(partloc))))){
                    //Debug.logger("wrong at ",delta.toString());
                    return null;
                    //no record but target block has been occupied by sth
                }else if(!hasPrevRecord&&MultiBlockService.validHandler(MultiBlockService.safeGetUUID(partloc))){//如果是当前已经注册的别的机器的
                    //Debug.logger("wrong at ",delta.toString());
                        return null;
                }
            }
        }
        if(!hasPrevRecord){
            for(Map.Entry<BlockVector,String> entry : REQUIREMENT_MAP.entrySet()){
                Location reqLoc=loc.clone().add(entry.getKey());
                if(!entry.getValue().equals(MultiBlockService.safeGetPartId(reqLoc))){
                    //Debug.logger("wrong at ",entry.getKey().toString());
                    return null;
                }
            }
        }
        //Debug.logger("check finished ,return value ",this.getSchemaSize(),"and req ",REQUIREMENT_MAP.size());
        return new MultiBlock(this,dir);
    }
}
