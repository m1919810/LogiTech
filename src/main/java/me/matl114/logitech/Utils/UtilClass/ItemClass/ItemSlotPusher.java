package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MenuUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * ItemPusher for menu slot
 * automatically replaceExistingItem when item=null at beginning
 */
public class ItemSlotPusher extends ItemPusher {
    //mark if it is null before last sync
    protected boolean wasNull;
    protected int slot;
    private static ItemSlotPusher INSTANCE=new ItemSlotPusher(new ItemStack(Material.STONE),-1);
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
    public static ItemSlotPusher get(ItemStack item,int slot){
//        if(item==null){
//            return new ItemSlotPusher(slot);
//        }else {
//            return new ItemSlotPusher(item,slot);
//        }
        ItemSlotPusher itp=INSTANCE.clone();
        itp.init(item,slot);
        return itp;
    }
    protected void init(ItemStack item,int slot){
        if(item!=null){
            super.init(item);
            this.wasNull=false;
        }else {
            super.init();
            this.wasNull=true;
        }
        this.slot=slot;
    }
    public boolean isNull(){
        return wasNull;
    }
    public int getMaxStackCnt() {
        return maxStackCnt;
    }

    //sync data need blockmenu
    public void updateMenu(@Nonnull BlockMenu menu){
        if(dirty&&getItem()!=null&&!getItem().getType().isAir()){
            if(wasNull){//空
                //从数据源clone一个 正式转变为有实体的ItemStack 因为consumer那边可能是sfItem MultiItem
                if(getAmount()>0){//非0
                    item= MenuUtils.syncSlot(menu,slot,item);
                    wasNull=false;
                    super.updateMenu(menu);
                }//若空且是0，寄.直接退出
            }
            else{//不为空，同正常一样
                //已经是之前有了的 可以直接修改
                super.updateMenu(menu);
            }

        }
    }
    public void updateItemStack(){
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
            if(source!=null&&source.getItem()!=null&&source.getAmount()>0)
                setFrom(source);
            else return;
        }
        super.grab(source);
    }
    public int transportFrom(ItemCounter source,int limit){
        if(this.item==null){
            if(source!=null&&source.getItem()!=null)
            setFrom(source);
            else return limit;
        }
        return super.transportFrom(source,limit);
    }
    public void setFrom(ItemCounter source){
        if(wasNull||(source!=null&&source.getItem()!=null)){
            item=source.getItem();
            maxStackCnt=item!=null?item.getMaxStackSize():0;
            cnt=0;
            meta=null;
        }
    }
    protected ItemSlotPusher clone(){
        return (ItemSlotPusher) super.clone();
    }

}
