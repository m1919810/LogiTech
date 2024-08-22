package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.Debug;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * used to cal num when push item, consume-only, it will not push item when unionSum
 */
public class ItemPusher extends ItemCounter {

    protected int maxStackCnt;
    private static ItemPusher INSTANCE=new ItemPusher(new ItemStack(Material.STONE));
    public ItemPusher(){
        super();
    }
    public ItemPusher(ItemStack item) {
        //can use as storage unit
        super(item);
        this.maxStackCnt = item.getMaxStackSize();

    }
    public static ItemPusher get(ItemStack item) {
        if(item==null) return null;
        ItemPusher itp=INSTANCE.clone();
        itp.init(item);
        return itp;
    }
    protected void init(ItemStack item){
        super.init( item);
        this.maxStackCnt = item.getMaxStackSize();
    }
    protected void init( ){
        super.init();
        this.maxStackCnt=0;
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
    public void push(ItemCounter counter) {
        counter.grab(this);
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
    protected ItemPusher clone(){
        return (ItemPusher)super.clone();
    }
}
