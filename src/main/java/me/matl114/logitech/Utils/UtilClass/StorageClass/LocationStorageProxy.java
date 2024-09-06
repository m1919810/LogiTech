package me.matl114.logitech.Utils.UtilClass.StorageClass;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class LocationStorageProxy extends ItemStorageCache{
    Location location;
    boolean lock=false;
    protected LocationStorageProxy(ItemStack item, ItemStack source, ItemMeta sourceMeta, int saveslot,StorageType type,Location location){
        super(item, source, sourceMeta, saveslot, type);
        this.location = location;
    }
    public boolean isDirty(){
        return lock||dirty;
    }
    public void updateStorage(){
        ((LocationProxy)storageType).setAmount(location,getStorageAmount());
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
