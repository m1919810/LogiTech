package me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiLevelBlock;

import me.matl114.logitech.utils.UtilClass.MultiBlockClass.AbstractMultiBlock;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockService;
import org.bukkit.util.Vector;

import java.util.List;

public class MultiLevelBlock implements AbstractMultiBlock {
    protected int level;
    protected int maxLevel;
    protected AbstractMultiBlock[] SUBPARTS;
    protected int[] INDEXS;
    protected int[] SIZES;
    protected MultiLevelBlockType schema;
    protected MultiBlockService.Direction direction;
    public MultiLevelBlock(MultiLevelBlockType type, int level, MultiBlockService.Direction direction, List< AbstractMultiBlock> multilist){
        this.SUBPARTS=multilist.toArray(new AbstractMultiBlock[level]);
        this.schema=type;
        this.level=level;
        this.maxLevel=type.SUB_NUM;
        this.direction=direction;
        this.INDEXS=new int[level+1];
        this.INDEXS[0]=0;
        this.SIZES=new int[level];
        for (int i=0;i<level;++i){
            this.SIZES[i]=this.SUBPARTS[i].getStructureSize();
            this.INDEXS[i+1]=this.INDEXS[i]+this.SIZES[i];
        }
    }
    public MultiLevelBlockType getType(){
        return this.schema;
    }
    public MultiBlockService.Direction getDirection() {
        return direction;
    }
    public int getLevel(){
        return level;
    }

    /**
     * get structure size
     * @return
     */
    public int getStructureSize(){
        return INDEXS[level];
    }

    /**
     * get structure i th block, should be rotated by DIRECTION
     * @param index
     * @return
     */
   public Vector getStructurePart(int index){
       for(int i=0;i<level;++i){
           if(index<INDEXS[i+1]){
               return SUBPARTS[i].getStructurePart(index-INDEXS[i]);
           }
       }
       return null;
    }

    /**
     * get structure i th id
     * @param index
     * @return
     */
    public String getStructurePartId(int index){
        for(int i=0;i<level;++i){
            if(index<INDEXS[i+1]){
                return SUBPARTS[i].getStructurePartId(index-INDEXS[i]);
            }
        }
        return null;
    }
}
