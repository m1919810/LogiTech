package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public interface ItemPusherProvider {
    public ItemPusher get(Settings settings, BlockMenu blockMenu, int slot);
}
