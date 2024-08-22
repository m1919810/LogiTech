package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.ItemStack;

public interface ItemPusherProvider {
    /**
     * when Settings.INPUT, return ItemPusher or null if item null,
     * when Settings.OUTPUT return ItemPusher or ItemSlotPusher if item null

     * @param slot
     * @return
     */
    public ItemPusher get(Settings settings, ItemStack item, int slot);
   default ItemPusher get(Settings settings, BlockMenu blockMenu, int slot){
       return get(settings,blockMenu.getItemInSlot(slot),slot);
   }
}
