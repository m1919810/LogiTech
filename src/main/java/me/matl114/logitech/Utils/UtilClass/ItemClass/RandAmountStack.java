package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.inventory.ItemStack;

public class RandAmountStack extends ItemStack implements AbstractItemStack{
    private int min;
    private int len;
    public RandAmountStack(ItemStack stack,int min,int max) {
        super(stack);
        this.min = min;
        this.len = max-min+1;

    }
    public int getMin() {
        return min;
    }
    public int getMax(){
        return len+min-1;
    }
    public ItemStack clone(){
        ItemStack clone =super.clone();
        clone.setAmount(min+ AddUtils.random(len));
        return clone;
    }
}
