package me.matl114.logitech.Utils.UtilClass.ItemClass;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EqProRandomStack extends RandomItemStack  {
    public Random rand=new Random();
    private LinkedHashMap<ItemStack,Integer> stacks;
    public EqProRandomStack(LinkedHashSet<ItemStack> itemSettings) {
        this(new LinkedHashMap<>(){{
           itemSettings.forEach(item -> {
               this.put(item, 1);
           });
        }});

    }
    public EqProRandomStack copy(){
        return new EqProRandomStack(stacks);
    }

    public EqProRandomStack(LinkedHashMap<ItemStack,Integer> itemSettings) {
        super(itemSettings);
        stacks=itemSettings;
    }
    public ItemStack clone(){
        return this.itemList[rand.nextInt(this.sum)].clone();
    }
    public ItemStack getInstance(){
        ItemStack it= this.itemList[rand.nextInt(this.sum)];
        return (it instanceof RandOutItem w)?w.getInstance():it;
    }
}
