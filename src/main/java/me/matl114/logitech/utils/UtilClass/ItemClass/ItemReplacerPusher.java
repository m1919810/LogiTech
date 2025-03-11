package me.matl114.logitech.utils.UtilClass.ItemClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
        super.updateItemStack();
        source.setAmount(cnt);
    }
    //修复了setFrom存储时覆写maxSize的问题
    public void setFrom(ItemCounter source){
        super.setFrom(source);
    }
}
