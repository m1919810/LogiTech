package me.matl114.logitech.SlimefunItem.Cargo.Config;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.MathUtils;
import me.matl114.logitech.Utils.UtilClass.PdcClass.AbstractAmount;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ChipCardCode {
    /**
     *
     */
    public final static String LORE_DISPLAY_PREFIX = AddUtils.resolveColor("&x&E&B&3&3&E&B芯片内存: &f");
    public final static NamespacedKey KEY_LOC = AddUtils.getNameKey("chip_config");
    public final static String LORE_MATCH="&8⇨ &7Code:&f";
    public final static Pattern LORE_PATTERN=Pattern.compile(AddUtils.resolveColor("^&8⇨ &7Code:&f.*$"));
    public final static Pattern CODE_GROUP_PATTERN=Pattern.compile(AddUtils.resolveColor("^&8⇨ &7Code:&f([0-1]*?)$"));
    public static boolean isConfig(ItemMeta meta) {
        return meta.getPersistentDataContainer().has(KEY_LOC);
    }
    public static boolean canConfig(ItemMeta meta) {
        String it= CraftUtils.parseSfId(meta);
        if(it==null) return false;
        return SlimefunItem.getById(it) instanceof ChipCard;
    }
    public boolean canConfig(SlimefunItem sfitem){
        return sfitem instanceof ChipCard;
    }
    public static int getConfig(ItemMeta meta) {
        Integer code= meta.getPersistentDataContainer().get(KEY_LOC, AbstractAmount.TYPE);
        return code!=null?code:0;
    }
    public static void clearConfig(ItemMeta meta) {
        meta.getPersistentDataContainer().remove(KEY_LOC);
        if(meta.hasLore()){
            List<String> lore = meta.getLore();
            for(int i=0;i<lore.size();){
                if(LORE_PATTERN.matcher(lore.get(i)).matches()){
                    lore.remove(i);
                }else if(lore.get(i).startsWith(LORE_DISPLAY_PREFIX)){
                    lore.remove(i);
                }else{
                    i++;
                }
            }
            meta.setLore(lore);
        }
    }
    public static void setConfig(ItemMeta meta, int code) {
        if(code!=-1) {
            clearConfig(meta);
            meta.getPersistentDataContainer().set(KEY_LOC, AbstractAmount.TYPE,code);
            List<String> lore=meta.hasLore()? meta.getLore():new ArrayList<>();
            lore.add(LORE_DISPLAY_PREFIX);
            lore.add(AddUtils.concat(LORE_MATCH, MathUtils.toBinaryCodeForce(code)));
            meta.setLore(lore);

        }
    }
    public static void setConfigCode(ItemMeta meta, int loc) {
        meta.getPersistentDataContainer().set(KEY_LOC,AbstractAmount.TYPE,loc);
    }
    public static void updateConfigCodeDisplay(ItemMeta meta, int code) {
        List<String> lore =meta.hasLore()? meta.getLore():new ArrayList<>();
        //lore.re
        int size=lore.size();
        int len=Math.min(2,size);
        lore.subList(size-len,size).clear();
        lore.add(LORE_DISPLAY_PREFIX);
        lore.add(AddUtils.concat(LORE_MATCH, MathUtils.toBinaryCodeForce(code)));
        meta.setLore(lore);
    }
}
