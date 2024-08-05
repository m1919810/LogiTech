package me.matl114.logitech.Utils.UtilClass;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemConsumer extends ItemCounter {
    private List<ItemPusher> targetConsumers;
    public ItemConsumer(ItemStack item) {
        super(item);
        targetConsumers = new ArrayList<>(2);
    }
    public void updateItemStack(){
        throw new NotImplementedException("this method should not be called");
    }
    public void addRelate(ItemPusher target){
        targetConsumers.add(target);
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
        targetConsumers.clear();
        super.syncData();
    }
    public List<ItemPusher> getRelate(){
        return targetConsumers;
    }

    public void clearRelated(){
        targetConsumers.clear();
    }
    public void updateItems(BlockMenu inv ,Settings mod){
        int len=targetConsumers.size();

                for(int i=0;i<len;i++){
                    ItemPusher target = targetConsumers.get(i);
                    target.updateMenu(inv);
                }

        }


    //used as consume
}
