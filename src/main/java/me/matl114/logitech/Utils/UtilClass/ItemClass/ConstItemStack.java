package me.matl114.logitech.Utils.UtilClass.ItemClass;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConstItemStack extends ItemStack {
    private ItemStack data;
    public ConstItemStack(ItemStack itemStack) {
        super(itemStack);
        this.data = itemStack;
    }
    public boolean setItemMeta(ItemMeta meta){
        return true;
    }
    public ItemStack clone(){
        return data.clone();
    }

}
