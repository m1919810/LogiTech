package me.matl114.logitech.utils.UtilClass.ItemClass;

import me.matl114.logitech.utils.MathUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * used to cal num when push item, consume-only, it will not push item when unionSum
 */
public class ItemPusher extends ItemCounter {


    private static ItemPusher INSTANCE=new ItemPusher(new ItemStack(Material.STONE));
    public ItemPusher(){
        super();
    }
    public ItemPusher(ItemStack item) {
        //can use as storage unit
        super(item);

    }
    public static ItemPusher get(ItemStack item) {
        if(item==null) return null;
        ItemPusher itp=INSTANCE.clone();
        itp.init(item);
        return itp;
    }
    protected void init(ItemStack item){
        super.init( item);
    }
    protected void init( ){
        super.init();
    }

    public ItemPusher(ItemStack item,int maxcnt) {
        //can use as storage unit
        super(item);
        this.maxStackCnt = maxcnt;
    }
    public boolean safeAddAmount(int amount){
        int result= MathUtils.safeAdd(amount,cnt) ;
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
    public void updateMenu(@Nonnull BlockMenu menu){
        if(dirty){
            updateItemStack();
        }
    }

    public void grab(ItemCounter counter) {
        long left=((long)maxStackCnt)-cnt;
        int amount = counter.getAmount();
        //bugfix: should not grab <= 0  itemCounter
        if(amount <= 0){
            return;
        }
        if(left > amount){
            addAmount(amount);
            counter.setAmount(0);
        }else {
            setAmount((int)maxStackCnt);
            counter.addAmount(-(int)left);
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
            fromSource(source,true);
        }
    }

    /**
     * transport item From target till limit count,return limit left
     * @param limit
     * @return
     */
    public int transportFrom(ItemCounter counter,int limit){
        //该物品槽能转运的最大数量
        long left=Math.min( ((long) maxStackCnt)-cnt,limit);
        int retLeft ;
        //如果这个数量比提供的少
        if(left>counter.getAmount()){
            //设置真正被传输的数量是... 提供的数量 小于预期left
            int counterAmount=counter.getAmount();
            //counter清空
            counter.setAmount(0);
            //加上
            addAmount(counterAmount);
            retLeft = counterAmount;
        }else{
            //否则这个数量提供的比那个多
            //设置数量+=left
            retLeft = (int)left;
            setAmount(cnt + retLeft);
            //left <= counter.getAmount()
            counter.addAmount(-retLeft);
        }
        return (limit-retLeft);
    }
    protected ItemPusher clone(){
        return (ItemPusher)super.clone();
    }
}
