package me.matl114.logitech.utils.UtilClass.MultiBlockClass;

import org.bukkit.util.Vector;

public class MultiBlock implements AbstractMultiBlock {
    private final AbstractMultiBlockType TYPE;
    private final MultiBlockService.Direction DIRECTION;

    public MultiBlock(AbstractMultiBlockType type, MultiBlockService.Direction direction) {
        this.TYPE = type;
        this.DIRECTION = direction;
    }

    public AbstractMultiBlockType getType() {
        return TYPE;
    }

    public Vector getStructurePart(int index) {
        return DIRECTION.rotate(getType().getSchemaPart(index));
    }

    public MultiBlockService.Direction getDirection() {
        return DIRECTION;
    }
}
