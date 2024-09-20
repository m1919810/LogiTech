package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.SlimefunItem.Cargo.StorageMachines.AbstractIOPort;
import me.matl114.logitech.Utils.MenuUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class ItemReplacerPusher extends ItemPusher{
    ItemStack source;
    private static ItemReplacerPusher INSTANCE=new ItemReplacerPusher(new ItemStack(Material.STONE),new ItemStack(Material.STONE));
    public static ItemPusher get(ItemStack source,ItemStack data){
        if(source!=null && data!=null){
            return new ItemReplacerPusher(source,data);
        }else {
            return ItemPusher.get(source);
        }
    }
    protected ItemReplacerPusher(ItemStack replacer,ItemStack replacement){
        super(replacement);
        this.source =replacer;

    }
    public void updateItemStack(){
        if(isDirty()){
            source.setAmount(cnt);
        }
        super.updateItemStack();
    }
    //修复了setFrom存储时覆写maxSize的问题
    public void setFrom(ItemCounter source){
        super.setFrom(source);
    }
}
