package me.matl114.logitech.utils.UtilClass.ItemClass;

import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EqProRandomStack extends RandomItemStack  {
    public Random rand=new Random();
    private List<Pair<ItemStack,Integer>> stacks;
    public EqProRandomStack(List<ItemStack> itemSettings,int ignored) {
        this(new ArrayList<>(){{
           itemSettings.forEach(item -> {
               this.add(new Pair<>(item, ignored));
           });
        }});

    }
    public EqProRandomStack copy(){
        return new EqProRandomStack(stacks);
    }

    public EqProRandomStack(List<Pair<ItemStack,Integer>> itemSettings) {
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
