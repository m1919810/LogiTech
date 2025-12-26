package me.matl114.logitech.core.Cargo.Config;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import java.util.ArrayList;
import java.util.List;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.utils.UtilClass.CargoClass.CargoConfigs;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CargoConfigCard {
    /**
     *
     */
    public static final String LORE_DISPLAY_PREFIX = AddUtils.resolveColor("&x&E&B&3&3&E&B通用货运配置卡: &f");

    public static final NamespacedKey KEY_LOC = AddUtils.getNameKey("cargo_config");
    public static final String LORE_MATCH = AddUtils.resolveColor("&8⇨ &7");
    public static final String[] LORE_PREFIX = new String[] {
        AddUtils.resolveColor("&8⇨ &7强对称: &3"),
        AddUtils.resolveColor("&8⇨ &7仅空传输: &3"),
        AddUtils.resolveColor("&8⇨ &7懒惰模式: &3"),
        AddUtils.resolveColor("&8⇨ &7白名单: &3"),
        AddUtils.resolveColor("&8⇨ &7逆向传输: &3"),
        AddUtils.resolveColor("&8⇨ &7源方块模式: &3"),
        AddUtils.resolveColor("&8⇨ &7目标方模式: &3"),
        AddUtils.resolveColor("&8⇨ &7传输数量: &3")
    };

    public static void appendConfigLore(List<String> lore, int code) {
        lore.add(AddUtils.concat(LORE_PREFIX[0], CargoConfigs.IS_SYMM.getConfig(code) ? "true" : "false"));
        lore.add(AddUtils.concat(LORE_PREFIX[1], CargoConfigs.IS_NULL.getConfig(code) ? "true" : "false"));
        lore.add(AddUtils.concat(LORE_PREFIX[2], CargoConfigs.IS_LAZY.getConfig(code) ? "true" : "false"));
        lore.add(AddUtils.concat(LORE_PREFIX[3], CargoConfigs.IS_WHITELST.getConfig(code) ? "true" : "false"));
        lore.add(AddUtils.concat(LORE_PREFIX[4], CargoConfigs.REVERSE.getConfig(code) ? "true" : "false"));

        lore.add(AddUtils.concat(
                LORE_PREFIX[5],
                (CargoConfigs.FROM_INPUT_FIRST.getConfig(code)
                        ? ("输入槽" + (CargoConfigs.FROM_REVERSED.getConfig(code) ? "+输出槽" : ""))
                        : ("输出槽" + (CargoConfigs.FROM_REVERSED.getConfig(code) ? "+输入槽" : "")))));
        lore.add(AddUtils.concat(
                LORE_PREFIX[6],
                (CargoConfigs.TO_OUTPUT_FIRST.getConfig(code)
                        ? ("输出槽" + (CargoConfigs.TO_REVERSED.getConfig(code) ? "+输入槽" : ""))
                        : ("输入槽" + (CargoConfigs.TO_REVERSED.getConfig(code) ? "+输出槽" : "")))));
        lore.add(AddUtils.concat(LORE_PREFIX[7], String.valueOf(CargoConfigs.TRANSLIMIT.getConfigInt(code))));
    }

    public static boolean isConfig(ItemMeta meta) {
        return meta.getPersistentDataContainer().has(KEY_LOC);
    }

    public static boolean canConfig(ItemMeta meta) {
        String it = CraftUtils.parseSfId(meta);
        if (it == null) return false;
        return SlimefunItem.getById(it) instanceof ConfigCard;
    }

    public boolean canConfig(SlimefunItem sfitem) {
        return sfitem instanceof ConfigCard;
    }

    public static int getConfig(ItemMeta meta) {
        try {
            Integer code = meta.getPersistentDataContainer().get(KEY_LOC, PersistentDataType.INTEGER);
            return code != null ? code : -1;
        } catch (Throwable e) {
            return 0;
        }
    }

    public static void clearConfig(ItemMeta meta) {
        meta.getPersistentDataContainer().remove(KEY_LOC);
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (int i = 0; i < lore.size(); ) {
                if (lore.get(i).startsWith(LORE_MATCH)) {
                    lore.remove(i);
                } else if (lore.get(i).startsWith(LORE_DISPLAY_PREFIX)) {
                    lore.remove(i);
                } else {
                    i++;
                }
            }
            meta.setLore(lore);
        }
    }

    public static void setConfig(ItemMeta meta, int code) {
        if (code != -1) {
            clearConfig(meta);
            meta.getPersistentDataContainer().set(KEY_LOC, PersistentDataType.INTEGER, code);
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            lore.add(LORE_DISPLAY_PREFIX);
            appendConfigLore(lore, code);

            meta.setLore(lore);
        }
    }

    public static void setConfigCode(ItemMeta meta, int loc) {
        if (loc != -1) {
            meta.getPersistentDataContainer().set(KEY_LOC, PersistentDataType.INTEGER, loc);
        } else {
            clearConfig(meta);
        }
    }

    public static void updateConfigCodeDisplay(ItemMeta meta, int code) {
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        // lore.re
        int size = lore.size();
        int len = Math.min(9, size);
        lore.subList(size - len, size).clear();
        appendConfigLore(lore, code);
        meta.setLore(lore);
    }
}
