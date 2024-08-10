package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public interface AbstractMultiBlockType {
    public BlockVector[] getStructurePos();
    public int getSchemaSize();
    public AbstractMultiBlockType build();
    public MultiBlockType addBlock(int x, int y, int z,String id);
    public Vector getSchemaPart(int index);
    public String[] getStructureIds();
    public String getSchemaPartId(int index);
    public boolean isSymmetric();
    public AbstractMultiBlock genMultiBlockFrom(Location loc, MultiBlockService.Direction dir,boolean hasPrevRecord);
}
