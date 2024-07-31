package me.matl114.logitech.Utils.UtilClass;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ItemCounter {
    protected int cnt;
    protected boolean dirty;
    protected ItemStack item;
    protected ItemMeta meta=null;
    public ItemCounter(ItemStack item) {
        dirty=false;
        this.cnt = item.getAmount();
        this.item=item;

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
    public boolean isDirty(){
        return dirty;
    }
    public ItemCounter() {
        dirty=false;
    }
    public ItemStack getItem() {
        return item;
    }
    public void setAmount(int amount) {
        cnt=amount;
        dirty=true;
    }
    public int getAmount() {
        return cnt;
    }
    public void addAmount(int amount) {
        cnt += amount;
        dirty=dirty||(amount!=0);
    }
    public void syncData(){
        if(dirty){
        cnt=item.getAmount();
        dirty=false;
        }
    }
    protected void updateItemStack(){
        if(dirty){

            item.setAmount(cnt);
            dirty=false;
        }
    }
    public void consume(ItemCounter other){
        int diff = (other.getAmount()>cnt)?cnt:other.getAmount();
        cnt-=diff;
        dirty=true;
        other.addAmount(-diff);
    }
    public void grab(ItemCounter other){
        cnt+=other.getAmount();
        dirty=true;
        other.setAmount(0);
    }
    public void push(ItemCounter other){
        other.grab(this);
    }

}
