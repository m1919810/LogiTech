package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.Debug;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ItemCounter implements Cloneable{
    protected int cnt;
    protected boolean dirty;
    protected ItemStack item;
    protected ItemMeta meta=null;
    protected ItemCounter(ItemStack item) {
        dirty=false;
        this.cnt = item.getAmount();
        this.item=item;
    }
    public ItemCounter() {
        dirty=false;
    }
    protected void init(ItemStack item) {
        this.dirty=false;
        this.item=item;
        this.cnt=item.getAmount();
    }
    protected void init() {
        this.dirty=false;
        this.cnt=0;
        this.item=null;

    }
    public boolean isNull() {
        return item==null;
    }
    /**
     * get meta info ,if havn't get ,getItemMeta() clone one
     * @return
     */
    public ItemMeta getMeta() {
        if(item.hasItemMeta()){
            if (meta==null){
                meta=item.getItemMeta();
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
        cnt=item.getAmount();
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
    protected void updateItemStack(){
        if(dirty){

            item.setAmount(cnt);
            dirty=false;
        }
    }

    /**
     * consume other counter ,till one of them got zero
     * @param other
     */
    public void consume(ItemCounter other){
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
