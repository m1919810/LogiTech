package me.matl114.logitech.utils.UtilClass.ItemClass;

import me.matl114.matlib.utils.inventory.itemStacks.CleanItemStack;
import org.bukkit.inventory.ItemStack;

public class DisplayItemStack extends CleanItemStack implements AbstractItemStack{
    public DisplayItemStack(ItemStack is){
        super(is );
    }
    public DisplayItemStack copy(){
        return new DisplayItemStack(this);
    }


    public ItemStack getInstance() {
        return this;
    }
}
