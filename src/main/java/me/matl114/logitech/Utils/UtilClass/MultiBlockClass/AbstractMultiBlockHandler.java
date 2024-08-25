package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import me.matl114.logitech.Utils.DataCache;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public interface AbstractMultiBlockHandler {
    /**
     * location of core
     * @return
     */
    public Location getCore() ;
    public MultiBlockService.Direction getDirection();
    public AbstractMultiBlock getMultiBlock();
    /**
     * is multiblock be build
     * @return
     */
    public boolean isActive();
    //响应部件信号
    public void setActive(boolean active);
    /**
     * accept in part ticks
     * @param loc
     */
    public void acceptPartRequest(Location loc);
    //响应主核信号 返回是否还正常

    /**
     * accept in core ticks,check if active and return boolean value,when active is down, return false,
     * the service method will call the  destroy method
     *  @return
     */
    public boolean acceptCoreRequest();

    /**
     * return the size of structure
     * @return
     */
    public int getSize();

    /**
     * return the index th block in structure
     * @param index
     * @return
     */
    public Location getBlockLoc(int index);

    /**
     * strict check if the multiblock matches,not suggested in tickers
     * @return
     */
    public boolean checkIfComplete();

    /**
     * random check if the multiblock matches,suggested in tickers
     * @return
     */
    default boolean checkIfCompleteRandom(){
        return checkIfComplete();
    }

    /**
     * get handler uid
     * @return
     */
    public String getUid();

    /**
     * this method should called onMultiBlockDisable for CORE and reset blockdata!
     */
    public void destroy();
}
