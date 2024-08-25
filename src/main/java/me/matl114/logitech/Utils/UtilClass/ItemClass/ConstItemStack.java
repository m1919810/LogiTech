package me.matl114.logitech.Utils.UtilClass.ItemClass;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConstItemStack extends ItemStack {
    private ItemStack data;
    private ItemMeta meta;
    public ConstItemStack(ItemStack itemStack) {
        super(itemStack);
        this.meta = itemStack.getItemMeta();
        this.data = itemStack;
    }
    public ItemMeta getItemMetaConst() {
        return meta;
    }
    public ItemStack clone(){
        return data.clone();
    }

}
