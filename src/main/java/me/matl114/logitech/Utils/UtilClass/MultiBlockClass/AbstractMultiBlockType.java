package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public interface AbstractMultiBlockType {
    /**
     * get schema block list
     * @return
     */
    public BlockVector[] getStructurePos();

    /**
     * get schema size
     * @return
     */
    public int getSchemaSize();

    /**
     * build structure map into arrays
     * @return
     */
    public AbstractMultiBlockType build();

    /**
     * add block, when x=y=z=0,ignored
     * @param x
     * @param y
     * @param z
     * @param id
     * @return
     */
    public MultiBlockType addBlock(int x, int y, int z,String id);

    /**
     * get index th block
     * @param index
     * @return
     */
    public Vector getSchemaPart(int index);

    /**
     * get structure id list
     * @return
     */
    public String[] getStructureIds();

    /**
     * get index th id
     * @param index
     * @return
     */
    public String getSchemaPartId(int index);

    /**
     * if structure is Symmetric ,if true ,only need to check direction NORTH
     * @return
     */
    public boolean isSymmetric();

    /**
     * try generate an AbstractMultiBlock with given direction,null if failed
     * @param loc
     * @param dir
     * @param hasPrevRecord
     * @return
     */
    public AbstractMultiBlock genMultiBlockFrom(Location loc, MultiBlockService.Direction dir,boolean hasPrevRecord);

}
