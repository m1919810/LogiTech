package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.Debug;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCounter implements Cloneable{
    protected int cnt;
    protected boolean dirty;
    protected ItemStack item;
    protected ItemMeta meta=null;
    protected int maxStackCnt;
    //when -1,means item is up to date
    private int cachedItemAmount = -1;
    private static ItemCounter INSTANCE=new ItemCounter(new ItemStack(Material.STONE)) ;
    protected ItemCounter(ItemStack item) {
        dirty=false;
        this.cnt = item.getAmount();
        this.cachedItemAmount = this.cnt;
        this.item=item;
        this.maxStackCnt=item.getMaxStackSize();
        this.maxStackCnt=maxStackCnt<=0?2147483646:maxStackCnt;
    }
    protected void toNull(){
        item=null;
        meta=null;
        cnt=0;
        dirty=false;
        itemChange();
        //
    }
    protected void fromSource(ItemCounter source,boolean overrideMaxSize){
        item=source.getItem();
        if(overrideMaxSize){
            maxStackCnt= item!=null?item.getMaxStackSize():0;
        }
        cnt=0;
        meta=null;
        cachedItemAmount=-1;
        //
    }
    protected void itemChange(){
        cachedItemAmount=item!=null? item.getAmount():0;
    }
    public ItemCounter() {
        dirty=false;
    }
    public static ItemCounter get(ItemStack item) {
        ItemCounter consumer=INSTANCE.clone();
        consumer.init(item);
        return consumer;
    }
    protected void init(ItemStack item) {
        this.dirty=false;
        this.item=item;
        this.cnt=item.getAmount();
        this.maxStackCnt=item.getMaxStackSize();
        this.maxStackCnt=maxStackCnt<=0?2147483646:maxStackCnt;
        this.cachedItemAmount=cnt;
    }
    protected void init() {
        this.dirty=false;
        this.cnt=0;
        this.item=null;
        this.maxStackCnt=0;
        this.cachedItemAmount=0;
    }
    public int getMaxStackCnt() {
        return maxStackCnt;
    }
    public boolean isNull() {
        return item==null;
    }
    public final boolean isFull(){
        return cnt>=this.maxStackCnt;
    }
    public final boolean isEmpty(){
        return cnt<=0;
    }
    /**
     * get meta info ,if havn't get ,getItemMeta() clone one
     * @return
     */
    public ItemMeta getMeta() {
        if(item.hasItemMeta()){
            if (meta==null){
                meta=  item.getItemMeta();
            }
            return meta;
        }
        return null;
    }

    /**
     * make sure you know what you are doing!
     * @param meta
     */
    public void setMeta(ItemMeta meta) {
        this.meta=meta;
    }

    /**
     * get dirty bits
     * @return
     */
    public boolean isDirty(){
        return dirty;
    }
    public void setDirty(boolean t){
        this.dirty=t;
    }
    /**
     * void constructor
     */


    /**
     * get item,should be read-only! and will not represent real amount
     * @return
     */
    public ItemStack getItem() {
        return item;
    }
    /**
     * modify recorded amount
     * @param amount
     */
    public void setAmount(int amount) {
        dirty=dirty||amount!=cnt;
        cnt=amount;
    }
    /**
     * get recorded amount
     */
    public int getAmount() {
        return cnt;
    }

    /**
     * modify recorded amount
     * @param amount
     */
    public void addAmount(int amount) {
        cnt += amount;
        dirty=dirty||(amount!=0);
    }

    /**
     * will sync amount and other data ,override by subclasses
     */
    public void syncData(){
        if(dirty){
            cnt = item.getAmount();
            dirty=false;
        }
    }

    /**
     * will only sync amount,keep the rest of data unchanged
     */
    public void syncAmount(){
        if(dirty){
            cnt=item.getAmount();
            dirty=false;
        }
    }

    /**
     * update amount of real itemstack ,or amount of real storage.etc
     */
    public void updateItemStack(){
        if(dirty){
            //check if cachedItemAmount is not refreshed
            if(cachedItemAmount<0){
                item.setAmount(cnt);
            }else{
                int newCachedItemAmount=item.getAmount();
                cnt+=newCachedItemAmount-cachedItemAmount;
                item.setAmount(cnt);
            }
            cachedItemAmount=cnt;

            dirty=false;
        }
    }

    /**
     * consume other counter ,till one of them got zero
     * @param other
     */
    public void consume(ItemCounter other){
        if(cnt<0){//stop when cnt < 0 (storage) no meaning
            return;
        }
        int diff = (other.getAmount()>cnt)?cnt:other.getAmount();
        cnt-=diff;
        dirty=true;
        other.addAmount(-diff);
    }

    /**
     * grab other counter till maxSize or sth
     * @param other
     */
    public void grab(ItemCounter other){
        cnt+=other.getAmount();
        dirty=true;
        other.setAmount(0);
    }

    /**
     * push to other counter till maxsize or sth
     * @param other
     */
    public void push(ItemCounter other){
        other.grab(this);
    }

    protected ItemCounter clone(){
        ItemCounter clone=null;
        try {
            clone=(ItemCounter) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

}
