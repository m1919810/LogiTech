package me.matl114.logitech.SlimefunItem.Machines;

import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusherProvider;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemSlotPusher;
import me.matl114.logitech.Utils.UtilClass.StorageClass.ItemStorageCache;
import org.bukkit.inventory.meta.ItemMeta;

public class FinalFeature {
    public static ItemPusherProvider FINAL_READER=((mod, item, slot) -> {
        if(item==null||item.getAmount()!=1){
            return CraftUtils.getpusher.get(mod,item,slot);
        }
        //not null.
        ItemPusher tar=null;
        ItemMeta meta=null;
        if(item.getAmount()==1){
            meta=item.getItemMeta();
            tar= ItemStorageCache.get(item,meta,slot,null);
        }
        //get no yet
        if(tar==null){
            tar= CraftUtils.getpusher.get(mod,item,slot);
            if(meta!=null){//make a convienience
                tar.setMeta(meta);
            }
        }
        return tar;
    });
}
