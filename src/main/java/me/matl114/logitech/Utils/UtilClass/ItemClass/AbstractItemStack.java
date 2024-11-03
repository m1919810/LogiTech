package me.matl114.logitech.Utils.UtilClass.ItemClass;

import org.bukkit.inventory.ItemStack;

public interface AbstractItemStack {
    //under this interface should itemstack clone to get an instance before they do sth
    public <T extends ItemStack> T copy();
}
