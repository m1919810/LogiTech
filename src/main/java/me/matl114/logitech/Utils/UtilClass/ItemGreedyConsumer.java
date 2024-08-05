package me.matl114.logitech.Utils.UtilClass;

import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * this consumer have NO cnt limit, when  calling symmetricDiff they will grab allllllll they can get and count num
 */
public class ItemGreedyConsumer extends ItemCounter implements Comparable<ItemGreedyConsumer> {
    private int matchAmount;
    private List<ItemPusher> targetConsumers;
    public ItemGreedyConsumer(ItemStack itemStack) {
        super(itemStack);
        matchAmount = 0;
        targetConsumers = new ArrayList<>(4);
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
        targetConsumers.add(target);
        target.dirty=true;
    }

    public List<ItemPusher> getRelate(){
        return targetConsumers;
    }

    public void consume(ItemPusher other){
        matchAmount += other.getAmount();
        addRelate(other);
    }

    public void clearRelated(){
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
        int s=cnt;
        cnt=matchAmount;
        int len=targetConsumers.size();
        link:
        switch (mod){
            case GRAB :
                for(int i=0;i<len;i++){
                    ItemPusher target = targetConsumers.get(i);
                    target.consume(this);

                    target.updateMenu(inv);

                    if(cnt<=0)break link;
                }
                break link;
            case PUSH:
                for(int i=0;i<len;i++){
                    ItemPusher target = targetConsumers.get(i);

                    target.grab(this);
                    target.updateMenu(inv);

                    if(cnt<=0)break link ;
                }
                break link;

        }
        cnt=s;
    }

}
