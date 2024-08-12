package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public interface ItemPusherProvider {
    /**
     * when Settings.INPUT, return ItemPusher or null if item null,
     * when Settings.OUTPUT return ItemPusher or ItemSlotPusher if item null
     * @param settings
     * @param blockMenu
     * @param slot
     * @return
     */
    public ItemPusher get(Settings settings, BlockMenu blockMenu, int slot);
}
