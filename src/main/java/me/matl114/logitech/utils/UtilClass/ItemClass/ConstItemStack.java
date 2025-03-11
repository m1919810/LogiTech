package me.matl114.logitech.utils.UtilClass.ItemClass;

import me.matl114.matlib.Utils.Inventory.ItemStacks.CleanItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConstItemStack extends CleanItemStack {
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
