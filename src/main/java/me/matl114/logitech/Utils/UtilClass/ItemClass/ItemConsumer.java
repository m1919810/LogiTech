package me.matl114.logitech.Utils.UtilClass.ItemClass;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ItemConsumer extends ItemCounter {
    private List<ItemPusher> targetConsumers;
    private static ItemConsumer INSTANCE=new ItemConsumer(new ItemStack(Material.STONE));
    public ItemConsumer(ItemStack item) {
        super(item);
    }
    public static ItemConsumer get(ItemStack item) {
        ItemConsumer consumer=INSTANCE.clone();
        consumer.init(item);
        return consumer;
    }
    protected void init(ItemStack item) {
        super.init(item);
        this.targetConsumers = null;
    }
    public void updateItemStack(){
        throw new NotImplementedException("this method should not be called");
    }
    private List<ItemPusher> getTargetConsumers(){
        if(targetConsumers==null){
            targetConsumers=new ArrayList<>(6);
        }
        return targetConsumers;
    }
    public void addRelate(ItemPusher target){
        getTargetConsumers().add(target);
        target.dirty=true;
    }
    public void consume(ItemPusher other){
        super.consume(other);
        addRelate(other);
    }

    /**
     * clear all modifi
     */
    public void syncData(){
        clearRelated();
        super.syncData();
    }

    public void clearRelated(){
        if(targetConsumers!=null){
            targetConsumers.clear();
        }
    }
    public void updateItems(BlockMenu inv ,Settings mod){
        if(targetConsumers==null){
            return;
        }
        int len=targetConsumers.size();
        for(int i=0;i<len;i++){
            targetConsumers.get(i).updateMenu(inv);
        }
    }
    protected ItemConsumer clone(){
        return (ItemConsumer)super.clone();
    }

    //used as consume
}
