package me.matl114.logitech.Utils.UtilClass;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * ItemPusher for menu slot
 * automatically replaceExistingItem when item=null at beginning
 */
public class ItemSlotPusher extends ItemPusher{
    //mark if it is null before last sync
    protected boolean wasNull;
    protected int slot;

    protected ItemSlotPusher(int slot){
        super();
        this.cnt=0;
        this.wasNull=true;
        this.item=null;
        this.slot=slot;
    }
    protected ItemSlotPusher(ItemStack item,int slot){
        super(item,item.getMaxStackSize());
        this.wasNull=false;
        this.slot=slot;
    }
    public int getMaxStackCnt() {
        return maxStackCnt;
    }
    public static ItemSlotPusher get(ItemStack item,int slot){
        if(item==null){
            return new ItemSlotPusher(slot);
        }else {
            return new ItemSlotPusher(item,slot);
        }
    }
    public ItemMeta getItemMeta(){
        if(item==null){
            return null;
        }
        return super.getMeta();
    }
    //sync data need blockmenu
    public void updateMenu(@Nonnull BlockMenu menu){
        if(dirty&&getItem()!=null&&!getItem().getType().isAir()){
            if(wasNull){
                //从数据源clone一个 正式转变为有实体的ItemStack 因为consumer那边可能是sfItem MultiItem
                item=item.clone();
                super.updateMenu(menu);
                menu.replaceExistingItem(slot,getItem());
                wasNull=false;
            }
            else{
                //已经是之前有了的 可以直接修改
                super.updateMenu(menu);
            }

        }
    }
    protected void updateItemStack(){
        if(getItem()!=null){
            super.updateItemStack();
        }
    }
    public void syncData(){
        if(!wasNull){
            super.syncData();
        }else{
            item=null;
            meta=null;
            cnt=0;
            dirty=false;
        }
    }

    public void grab(ItemCounter source){
        if(this.item==null){
            if(source!=null)
            setFrom(source);
            else return;
        }
        super.grab(source);
    }
    public void setFrom(ItemCounter source){
        item=source.getItem();
        maxStackCnt=item!=null?item.getMaxStackSize():0;
        cnt=0;
        meta=null;
    }

}
