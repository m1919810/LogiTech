package me.matl114.logitech.core.Cargo.Config;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import me.matl114.logitech.utils.DataCache;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface ChipControllable {
    public int getChipSlot();

    static final String CCODEKEY = "ccmd";

    default String getKey() {
        return CCODEKEY;
    }

    default void loadChipCommand(BlockMenu inv) {
        SlimefunBlockData data = DataCache.safeLoadBlock(inv.getLocation());
        ItemStack it = inv.getItemInSlot(getChipSlot());
        if (it != null) {
            ItemMeta im = it.getItemMeta();
            if (ChipCardCode.isConfig(im)) {
                int code = ChipCardCode.getConfig(im);
                data.setData(CCODEKEY, String.valueOf(code));
                return;
            }
        }
        data.setData(CCODEKEY, "n");
        return;
    }
}
