package me.matl114.logitech.SlimefunItem.Cargo.Config;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.SlimefunItem.Cargo.StorageMachines.Singularity;
import me.matl114.logitech.SlimefunItem.Items.ConfigCard;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.UtilClass.CargoClass.CargoConfigs;
import me.matl114.logitech.Utils.UtilClass.PdcClass.AbstractAmount;
import me.matl114.logitech.Utils.UtilClass.PdcClass.AbstractLocation;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CargoConfigCard {
    public final static String LORE_DISPLAY_PREFIX = AddUtils.resolveColor("&x&E&B&3&3&E&B通用货运配置卡: &f");
    public final static NamespacedKey KEY_LOC = AddUtils.getNameKey("cargo_config");
    public final static String LORE_MATCH="&8⇨ &7";
    public final static String[] LORE_PREFIX=new String[]{
            AddUtils.resolveColor ("&8⇨ &7强对称: &3%s"),
            AddUtils.resolveColor ("&8⇨ &7仅空传输: &3%s"),
            AddUtils.resolveColor ("&8⇨ &7懒惰模式: &3%s"),
            AddUtils.resolveColor ("&8⇨ &7白名单: &3%s"),
            AddUtils.resolveColor ("&8⇨ &7取自输入槽: &3%s"),
            AddUtils.resolveColor ("&8⇨ &7传向输出槽: &3%s"),
            AddUtils.resolveColor ("&8⇨ &7逆向传输: &3%s"),

            AddUtils.resolveColor ("&8⇨ &7传输数量: &3%d")
    };
    public static boolean isConfig(ItemMeta meta) {
        return meta.getPersistentDataContainer().has(KEY_LOC);
    }
    public static boolean canConfig(ItemMeta meta) {
        String it=CraftUtils.parseSfId(meta);
        if(it==null) return false;
        return SlimefunItem.getById(it) instanceof ConfigCard;
    }
    public boolean canConfig(SlimefunItem sfitem){
        return sfitem instanceof ConfigCard;
    }
    public static int getConfig(ItemMeta meta) {
        Integer code= meta.getPersistentDataContainer().get(KEY_LOC, AbstractAmount.TYPE);
        return code!=null?code:-1;
    }
    public static void clearConfig(ItemMeta meta) {
        meta.getPersistentDataContainer().remove(KEY_LOC);
        if(meta.hasLore()){
            List<String> lore = meta.getLore();
            for(int i=0;i<lore.size();){
                if(lore.get(i).startsWith(LORE_MATCH)){
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
            lore.add(LORE_PREFIX[0].formatted(CargoConfigs.IS_SYMM.getConfig(code)?"true":"false"));
            lore.add(LORE_PREFIX[1].formatted(CargoConfigs.IS_NULL.getConfig(code)?"true":"false"));
            lore.add(LORE_PREFIX[2].formatted(CargoConfigs.IS_LAZY.getConfig(code)?"true":"false"));
            lore.add(LORE_PREFIX[3].formatted(CargoConfigs.IS_WHITELST.getConfig(code)?"true":"false"));
            lore.add(LORE_PREFIX[4].formatted(CargoConfigs.FROM_INPUT.getConfig(code)?"true":"false"));
            lore.add(LORE_PREFIX[5].formatted(CargoConfigs.TO_OUTPUT.getConfig(code)?"true":"false"));
            lore.add(LORE_PREFIX[6].formatted(CargoConfigs.REVERSE.getConfig(code)?"true":"false"));
            lore.add(LORE_PREFIX[7].formatted(CargoConfigs.TRANSLIMIT.getConfigInt(code)));

            meta.setLore(lore);

        }
    }
    public static void setConfigCode(ItemMeta meta, int loc) {
        if(loc!=-1) {
            meta.getPersistentDataContainer().set(KEY_LOC,AbstractAmount.TYPE,loc);
        }else {
            clearConfig(meta);
        }
    }
    public static void updateConfigCodeDisplay(ItemMeta meta, int code) {
        List<String> lore =meta.hasLore()? meta.getLore():new ArrayList<>();
        //lore.re
        int size=lore.size();
        int len=Math.min(9,size);
        lore.subList(size-len,size).clear();
        lore.add(LORE_PREFIX[0].formatted(CargoConfigs.IS_SYMM.getConfig(code)?"true":"false"));
        lore.add(LORE_PREFIX[1].formatted(CargoConfigs.IS_NULL.getConfig(code)?"true":"false"));
        lore.add(LORE_PREFIX[2].formatted(CargoConfigs.IS_LAZY.getConfig(code)?"true":"false"));
        lore.add(LORE_PREFIX[3].formatted(CargoConfigs.IS_WHITELST.getConfig(code)?"true":"false"));
        lore.add(LORE_PREFIX[4].formatted(CargoConfigs.FROM_INPUT.getConfig(code)?"true":"false"));
        lore.add(LORE_PREFIX[5].formatted(CargoConfigs.TO_OUTPUT.getConfig(code)?"true":"false"));
        lore.add(LORE_PREFIX[6].formatted(CargoConfigs.REVERSE.getConfig(code)?"true":"false"));
        lore.add(LORE_PREFIX[7].formatted(CargoConfigs.TRANSLIMIT.getConfigInt(code)));
        meta.setLore(lore);
    }
}
