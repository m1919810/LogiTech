package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.ItemStack;

/**
 * used to cal num when push item, consume-only, it will not push item when unionSum
 */
public class ItemPusher extends ItemCounter {

    protected int maxStackCnt;
    public ItemPusher(){
        super();
    }
    public ItemPusher(ItemStack item) {
        //can use as storage unit
        super(item);
        this.maxStackCnt = item.getMaxStackSize();

    }
    public ItemPusher(ItemStack item,int maxcnt) {
        //can use as storage unit
        super(item);
        this.maxStackCnt = maxcnt;

    }
    public boolean safeAddAmount(int amount){
        int result=amount+cnt;
        if(result>maxStackCnt){
            return false;
        }else {
            setAmount(result);
            return true;
        }
    }
    /**
     * this arguments has no meaning ,just a formated argument
     * @param menu
     */
    public void updateMenu( BlockMenu menu){
        if(dirty){
            updateItemStack();

        }
    }
    public int getMaxStackCnt() {
        return maxStackCnt;
    }
    public void grab(ItemCounter counter) {
        int left=maxStackCnt-cnt;
        if(left>counter.getAmount()){
            addAmount(counter.getAmount());
            counter.setAmount(0);
        }else{
            setAmount(maxStackCnt);
            counter.addAmount(-left);
        }
    }

    /**
     * should sync before
     * @param source
     */
    public void setFrom(ItemCounter source){
        if(item==null&&(source!=null&&source.getItem()!=null)){
            item=source.getItem();
            maxStackCnt=item.getMaxStackSize();
            cnt=0;
            meta=null;
        }
    }

    /**
     * transport item From target till limit count,return limit left
     * @param tar
     * @param limit
     * @return
     */
    public int transportFrom(ItemCounter counter,int limit){
        int left=Math.min( maxStackCnt-cnt,limit);
        if(left>counter.getAmount()){
            addAmount(counter.getAmount());
            counter.setAmount(0);
        }else{
            setAmount(maxStackCnt);
            counter.addAmount(-left);
        }
        return limit-left;
    }
}
