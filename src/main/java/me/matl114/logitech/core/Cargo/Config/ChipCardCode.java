package me.matl114.logitech.core.Cargo.Config;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import me.matl114.logitech.core.AddItem;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.utils.MathUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ChipCardCode {
    /**
     *
     */
    public static final String LORE_DISPLAY_PREFIX = AddUtils.resolveColor("&x&E&B&3&3&E&B芯片内存: &f");

    public static final NamespacedKey KEY_LOC = AddUtils.getNameKey("chip_config");
    public static final String LORE_MATCH = AddUtils.resolveColor("&8⇨ &3Code:&f ");
    // public final static Pattern LORE_PATTERN=Pattern.compile(AddUtils.resolveColor("^&8⇨ &7Code:&f.*$"));
    public static final Pattern CODE_GROUP_PATTERN = Pattern.compile(AddUtils.resolveColor("^&8⇨ &7Code:&f([0-1]*?)$"));
    public static ItemMeta meta = AddItem.CHIP.getItemMeta();
    public static final ItemStack CHIP_0 = ((Supplier<ItemStack>) () -> {
                ItemStack stack = AddItem.CHIP.clone();
                stack.setItemMeta(createCard(0));
                return stack;
            })
            .get();
    public static final ItemMeta chip0meta = CHIP_0.getItemMeta();
    public static final ItemStack CHIP_1 = ((Supplier<ItemStack>) () -> {
                ItemStack stack = AddItem.CHIP.clone();
                stack.setItemMeta(createCard(1));
                return stack;
            })
            .get();
    public static final ItemStack CHIP_FINAL = ((Supplier<ItemStack>) () -> {
                ItemStack stack = AddItem.CHIP.clone();
                stack.setItemMeta(createCard(1431655765));
                return stack;
            })
            .get();

    private static ItemMeta createCard(int code) {
        ItemMeta it = meta.clone();
        setConfig(it, code);
        return it;
    }

    public static ItemMeta getCard(int code) {
        ItemMeta it = chip0meta.clone();
        setConfigCode(it, code);
        updateConfigCodeDisplay(it, code);
        return it;
    }

    public static boolean isConfig(ItemMeta meta) {
        return meta.getPersistentDataContainer().has(KEY_LOC);
    }

    public static boolean canConfig(ItemMeta meta) {
        String it = CraftUtils.parseSfId(meta);
        if (it == null) return false;
        return SlimefunItem.getById(it) instanceof ChipCard;
    }

    public static boolean canConfig(SlimefunItem sfitem) {
        return sfitem instanceof ChipCard;
    }

    public static int getConfig(ItemMeta meta) {
        try {
            Integer code = meta.getPersistentDataContainer().get(KEY_LOC, PersistentDataType.INTEGER);
            return code != null ? code : 0;
        } catch (Throwable ex) {
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
            lore.add(AddUtils.concat(LORE_MATCH, MathUtils.toBinaryCodeForce(code)));
            meta.setLore(lore);
        }
    }

    public static void setConfigCode(ItemMeta meta, int loc) {
        meta.getPersistentDataContainer().set(KEY_LOC, PersistentDataType.INTEGER, loc);
    }

    public static void updateConfigCodeDisplay(ItemMeta meta, int code) {
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        // lore.re
        int size = lore.size();
        int len = Math.min(1, size);
        lore.subList(size - len, size).clear();
        lore.add(AddUtils.concat(LORE_MATCH, MathUtils.toBinaryCodeForce(code)));
        meta.setLore(lore);
    }
}
