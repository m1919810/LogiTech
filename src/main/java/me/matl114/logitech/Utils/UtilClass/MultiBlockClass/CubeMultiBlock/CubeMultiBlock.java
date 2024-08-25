package me.matl114.logitech.Utils.UtilClass.MultiBlockClass.CubeMultiBlock;

import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlock;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockType;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import org.bukkit.util.Vector;

import java.util.EnumMap;

public class CubeMultiBlock implements AbstractMultiBlock {
    private final CubeMultiBlockType TYPE;
    private final MultiBlockService.Direction DIRECTION;
    private final int height;
    private final int size1;
    private final int size2;
    private final int size3;
    public CubeMultiBlock(CubeMultiBlockType type, MultiBlockService.Direction direction,int height) {
        this.TYPE = type;
        this.DIRECTION = direction;
        this.height = height;
        this.size1=TYPE.sizeB;
        this.size2=size1+ TYPE.sizeP*height;
        this.size3=size2+TYPE.sizeT;
    }
    public AbstractMultiBlockType getType() {
        return TYPE;
    }
    public int getHeight() {
        return height;
    }
    public Vector getStructurePart(int index){
        if(index<size1){
            return DIRECTION.rotate(TYPE.BOTTOM_LOC[index].clone());
        }else if(index<size2){
            int t=index-size1;
            Vector s= DIRECTION.rotate(TYPE.PLATE_LOC[t%TYPE.sizeP].clone());
            s.setY(s.getBlockY()+(int)(t/TYPE.sizeP));
            return s;
        }else{
            Vector s= DIRECTION.rotate(TYPE.TOP_LOC[index-size2].clone());
            s.setY(s.getBlockY()+height-1);
            return s;
        }
    }
    public String getStructurePartId(int index){
        return (index<size1)?TYPE.BOTTOM_IDS[index]:((index<size2)?TYPE.PLATE_IDS[(index-size1)%TYPE.sizeP]:TYPE.TOP_IDS[index-size2]);
    }
    public MultiBlockService.Direction getDirection() {
        return DIRECTION;
    }
    public int getStructureSize(){
        return size3;
    }
}
