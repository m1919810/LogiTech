package me.matl114.logitech.Utils.UtilClass.StorageClass;

import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MathUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class LocationStorageProxy extends ItemStorageCache{
    Location location;
    boolean lock=false;
    int lastStorageAmount;
    protected LocationStorageProxy(ItemStack item, ItemStack source, ItemMeta sourceMeta, int saveslot,StorageType type,Location location){
        super(item, source, sourceMeta, saveslot, type);
        this.location = location;
        this.lastStorageAmount=((LocationProxy)storageType).getAmount(location);
    }
    public boolean isDirty(){
        return lock||dirty;
    }
    public void updateStorage(){
        //这里是代理存储 并不是唯一修改源
        //需要刷新一下并加上用当前记录-历史记录 的东西
        int delta=((LocationProxy)storageType).getAmount(location)-this.lastStorageAmount;
        ((LocationProxy)storageType).setAmount(location, Math.min(getMaxStackCnt(), MathUtils.safeAdd(getStorageAmount(),delta)));
        ((LocationProxy)storageType).updateLocation(location);
    }
    public void updateMenu(@Nonnull BlockMenu menu){
        if (getItem()!=null&&!getItem().getType().isAir()){
            updateItemStack();
            updateStorage();
        }
        dirty=false;
    }
    public Location getProxyLocation(){
        return location;
    }
}
