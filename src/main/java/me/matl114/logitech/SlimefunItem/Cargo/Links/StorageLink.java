package me.matl114.logitech.SlimefunItem.Cargo.Links;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.SlimefunItem.Items.HypLink;
import me.matl114.logitech.SlimefunItem.Items.QuantumLink;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.StorageClass.LocationProxy;
import me.matl114.logitech.Utils.UtilClass.StorageClass.StorageType;
import net.guizhanss.guizhanlib.minecraft.helper.inventory.ItemStackHelper;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class StorageLink {
    public final static String LOC_DISPLAY_PREFIX = AddUtils.resolveColor("&x&E&B&3&3&E&B纠缠链接目标: ");
    public final static String NAME_DISPLAY_PREFIX =AddUtils.resolveColor("&x&E&B&3&3&E&B历史访问记录: ");
    public final  static String BLANK_COLOR=AddUtils.resolveColor("&f");
    public final  static String BLANK_STORAGE=AddUtils.resolveColor("&f空存储位");
    public final static NamespacedKey KEY_LOC = AddUtils.getNameKey("cache_loc");
    public final static String LOC_DISPLAY=AddUtils.resolveColor("&3[%s,x=%d,y=%d,z=%d]");
    public static boolean isLink(ItemMeta meta) {
        return meta.getPersistentDataContainer().has(KEY_LOC);
    }
    public static boolean canLink(ItemMeta meta) {
        String it= CraftUtils.parseSfId(meta);
        if(it==null) return false;
        return SlimefunItem.getById(it) instanceof QuantumLink;
    }
    public boolean canLink(SlimefunItem sfitem){
        return sfitem instanceof QuantumLink;
    }
    public static Location getLink(ItemMeta meta) {
        try{
            return DataCache.locationFromString(meta.getPersistentDataContainer().get(KEY_LOC, PersistentDataType.STRING));
        }catch(Throwable e){
            return null;
        }

    }
    public static void clearLink(ItemMeta meta) {
        meta.getPersistentDataContainer().remove(KEY_LOC);
        if(meta.hasLore()){
            List<String> lore = meta.getLore();
            for(int i=0;i<lore.size();){
                if(lore.get(i).startsWith(LOC_DISPLAY_PREFIX)){
                    lore.remove(i);
                }else if(lore.get(i).startsWith(NAME_DISPLAY_PREFIX)){
                    lore.remove(i);
                }else{
                    i++;
                }
            }
            meta.setLore(lore);
        }
    }
    public static void setLinkLocation(ItemMeta meta, Location loc) {
        if(loc!=null) {
            String  locstr= DataCache.locationToString(loc);
            meta.getPersistentDataContainer().set(KEY_LOC, PersistentDataType.STRING,locstr);
        }
    }
    public static void setLink(ItemMeta meta, Location loc) {
        if(loc!=null) {
            clearLink(meta);
            setLinkLocation(meta, loc);
            List<String> lore=meta.hasLore()? meta.getLore():new ArrayList<>();
            updateLore(lore,loc);
            meta.setLore(lore);
        }
    }
    public static void updateLore(List<String> lore,Location loc){
        lore.add(AddUtils.concat(LOC_DISPLAY_PREFIX,LOC_DISPLAY.formatted(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())));
        Iterator<StorageType> it=StorageType.getStorageTypes();
        String name=null;
        StorageType type;
        while (it.hasNext()){
            type=it.next();
            if(type instanceof LocationProxy lp){
                ItemStack item=lp.getItemStack(loc);
                if(item!=null){
                    name= ItemStackHelper.getDisplayName(item);
                    break;
                }
            }
        }
        name=name==null?BLANK_STORAGE:AddUtils.concat(BLANK_COLOR,name);
        lore.add(AddUtils.concat(NAME_DISPLAY_PREFIX,name));
    }

    public static void updateLinkLocationDisplay(ItemMeta meta, Location loc) {
        List<String> lore =meta.hasLore()? meta.getLore():new ArrayList<>();
        int len=lore.size();
        lore.subList(Math.max(len-2,0),len).clear();
        updateLore(lore,loc);
        meta.setLore(lore);
    }
}
