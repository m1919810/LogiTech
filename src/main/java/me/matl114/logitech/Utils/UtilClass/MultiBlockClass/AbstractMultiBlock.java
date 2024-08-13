package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import org.bukkit.util.Vector;

public interface AbstractMultiBlock {
    /**
     * get AbstractMultiBlockType
     * @return
     */
    public AbstractMultiBlockType getType();

    /**
     * get direction
     * @return
     */
    default MultiBlockService.Direction getDirection() {
        return MultiBlockService.Direction.NORTH;
    }

    /**
     * get structure size
     * @return
     */
    default int getStructureSize(){
        return getType().getSchemaSize();
    }

    /**
     * get structure i th block, should be rotated by DIRECTION
     * @param index
     * @return
     */
    default Vector getStructurePart(int index){
        return getType().getSchemaPart(index);
    }

    /**
     * get structure i th id
     * @param index
     * @return
     */
    default String getStructurePartId(int index){

        return getType().getSchemaPartId(index);
    }
}
