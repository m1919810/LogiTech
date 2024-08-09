package me.matl114.logitech.Utils.UtilClass.ItemClass;

import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.concurrent.ThreadLocalRandom;

public class EqProRandomStack extends RandomItemStack {
    public EqProRandomStack(LinkedHashSet<ItemStack> itemSettings) {
        super(new LinkedHashMap<>(){{
           itemSettings.forEach(item -> {
               this.put(item, 1);
           });
        }});
        this.weightSum=null;
    }
    public EqProRandomStack(LinkedHashMap<ItemStack,Integer> itemSettings) {
        super(itemSettings);
        this.weightSum=null;
    }
    public ItemStack clone(){
        return this.itemList[ThreadLocalRandom.current().nextInt(this.sum)].clone();
    }
}
