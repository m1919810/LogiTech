package me.matl114.logitech.utils.UtilClass.ItemClass;

import org.bukkit.inventory.ItemStack;

public interface RandOutItem {
    /**
     * should not be cloned or merged in MachineRecipe,only a preview of ItemStack ,used in ItemConsumer generation
     * read-only
     * @return
     */
    public ItemStack getInstance();
}
