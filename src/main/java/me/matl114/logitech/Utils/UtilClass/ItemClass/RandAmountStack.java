package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RandAmountStack extends ItemStack implements AbstractItemStack,RandOutItem{
    public Random rand=new Random();
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
        clone.setAmount(min+ rand.nextInt(len));
        return clone;
    }
    public ItemStack getInstance(){
        return this.clone();
    }
}
