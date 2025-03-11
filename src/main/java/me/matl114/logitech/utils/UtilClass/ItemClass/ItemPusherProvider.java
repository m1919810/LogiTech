package me.matl114.logitech.utils.UtilClass.ItemClass;

import me.matl114.logitech.utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public interface ItemPusherProvider {
    /**
     * when Settings.INPUT, return ItemPusher or null if item null,
     * when Settings.OUTPUT return ItemPusher or ItemSlotPusher if item null

     * @param slot
     * @return
     */
    static int[] NOSLOT= IntStream.generate(()->-1).limit(60).toArray();
    ItemPusher get(Settings settings, ItemStack item, int slot);
    //need override?
   default ItemPusher getPusher(Settings settings, BlockMenu blockMenu, int slot){
       return get(settings,blockMenu.getItemInSlot(slot),slot);
   }
   default IntFunction<ItemPusher> getMenu(Settings settings, BlockMenu inv, IntFunction<ItemStack> stackFunction,int[] slots){
       return (i -> get(settings,stackFunction.apply(i),slots[i]));
   }




    default IntFunction<ItemPusher> getMenuInstance(Settings settings  ,BlockMenu blockMenu){
        return i->get(settings,blockMenu.getItemInSlot(i),i);
    }
   default IntFunction<ItemPusher> getMenuInstance(Settings settings  ,BlockMenu blockMenu,int[] slots){
       return getMenu(settings,blockMenu,i->blockMenu.getItemInSlot(slots[i]),slots);
   }

   default IntFunction<ItemPusher> getMenuInstance(Settings settings  ,BlockMenu blockMenu,ItemStack[] item){
       return getMenu(settings,blockMenu,i->item[i],NOSLOT);
   }
    default IntFunction<ItemPusher> getMenuInstance(Settings settings  , BlockMenu blockMenu, List<ItemStack> item){
        return getMenu(settings,blockMenu,(i)->  item.get(i),NOSLOT);
    }
    default IntFunction<ItemPusher> getMenuInstance(Settings settings, BlockMenu blockMenu, ItemStack[] item,int[] slots){
       return getMenu(settings,blockMenu,(i)->  item[i],slots);
    }
    default IntFunction<ItemPusher> getMenuInstance(Settings settings, BlockMenu blockMenu,IntFunction<ItemStack> prov,int[] slots){
        return getMenu(settings,blockMenu,i->prov.apply(i),slots);
    }

}
