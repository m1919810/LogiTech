package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * this consumer have NO cnt limit, when  calling symmetricDiff they will grab allllllll they can get and count num
 */
public class ItemGreedyConsumer extends ItemCounter implements Comparable<ItemGreedyConsumer> {
    private int matchAmount;
    private List<ItemPusher> targetConsumers;
    private static ItemGreedyConsumer INSTANCE=new ItemGreedyConsumer(new ItemStack(Material.STONE));
    public ItemGreedyConsumer(ItemStack itemStack) {
        super(itemStack);
        matchAmount = 0;
    }
    public static ItemGreedyConsumer get(ItemStack itemStack) {
        ItemGreedyConsumer consumer =  INSTANCE.clone();
        consumer.init(itemStack);
        return consumer;
    }
    private List<ItemPusher> getTargetConsumers(){
        if(targetConsumers==null){
            targetConsumers=new ArrayList<>(8);
        }
        return targetConsumers;
    }
    public void init(ItemStack itemStack) {
        super.init( itemStack);
        this.matchAmount = 0;
    }
    /**
     * get total amount of matching items
     * @return
     */
    public int getMatchAmount() {
        return matchAmount;
    }

    /**
     * add total amount of matching items
     * @param matchAmount
     */
    public void addMatchAmount(int matchAmount) {
        this.matchAmount += matchAmount;
        dirty=true;
    }
    public void setMatchAmount(int matchAmount) {
        this.matchAmount = matchAmount;
        dirty=true;
    }

    /**
     * get pieces of stack matched in total amount
     * @return
     */
    public int getStackNum(){
        return (matchAmount/(this.cnt));
    }

    public void setStackNum(int stackNum){
        matchAmount = stackNum*cnt  ;
    }
    public void addRelate(ItemPusher target){
        getTargetConsumers().add(target);
        target.dirty=true;
    }


    public void consume(ItemPusher other){
        matchAmount += other.getAmount();
        addRelate(other);
    }

    public void clearRelated(){
        if(targetConsumers!=null)
            targetConsumers.clear();
    }

    public void syncData(){
        matchAmount = 0;
        super.syncData();
    }

    public  int compareTo(ItemGreedyConsumer o) {
        return this.getStackNum()-o.getStackNum();
    }



    public void updateItems(BlockMenu inv , Settings mod){
        //preserver
        if(targetConsumers==null){
            return;
        }
        int s=cnt;
        cnt=matchAmount;
        int len=targetConsumers.size();
        ItemPusher target;
        link:
        switch (mod){
            case GRAB :
                for(int i=0;i<len;i++){
                    target= targetConsumers.get(i);
                    target.consume(this);
                    target.updateMenu(inv);
                    if(cnt<=0)break link;
                }
                break link;
            case PUSH:
                for(int i=0;i<len;i++){
                    target = targetConsumers.get(i);
                    target.grab(this);
                    target.updateMenu(inv);
                    if(cnt<=0)break link ;
                }
                break link;

        }
        cnt=s;
    }
    protected ItemGreedyConsumer clone(){
        return (ItemGreedyConsumer) super.clone();
    }

}
