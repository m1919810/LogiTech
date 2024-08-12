package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import me.matl114.logitech.Utils.DataCache;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public interface AbstractMultiBlockHandler {
    public Location getCore() ;
    public boolean isActive();
    //响应部件信号
    public void acceptPartRequest(Location loc);
    //响应主核信号 返回是否还正常
    public boolean acceptCoreRequest();
    public int getSize();
    public Location getBlockLoc(int index);

    public String getUid();
    public void destroy();

}
