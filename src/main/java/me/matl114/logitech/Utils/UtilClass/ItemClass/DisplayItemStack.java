package me.matl114.logitech.Utils.UtilClass.ItemClass;

import org.bukkit.inventory.ItemStack;

public class DisplayItemStack extends ItemStack implements AbstractItemStack{
    public DisplayItemStack(ItemStack is){
        super(is );
    }
    public DisplayItemStack copy(){
        return new DisplayItemStack(this);
    }
}
