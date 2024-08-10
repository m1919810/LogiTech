package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import org.bukkit.util.Vector;

public interface AbstractMultiBlock {
    public AbstractMultiBlockType getType();
    default MultiBlockService.Direction getDirection() {
        return MultiBlockService.Direction.NORTH;
    }
    default int getStructureSize(){
        return getType().getSchemaSize();
    }
    default Vector getStructurePart(int index){
        return getType().getSchemaPart(index);
    }
    default String getStructurePartId(int index){

        return getType().getSchemaPartId(index);
    }
}
