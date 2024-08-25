package me.matl114.logitech.Utils.UtilClass.ItemClass;

import org.bukkit.inventory.ItemStack;

public interface RandOutItem {
    /**
     * should not be cloned ,only a preview of ItemStack ,used in ItemConsumer generation
     * read-only
     * @return
     */

   public ItemStack getInstance();
}
