package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MathUtils;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * this consumer have NO cnt limit, when  calling symmetricDiff they will grab allllllll they can get and count num
 */
public class ItemGreedyConsumer extends ItemCounter implements Comparable<ItemGreedyConsumer> {
    private int matchAmount;
    private int maxStackCnt;
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
//    public ItemMeta getMeta() {
//        if(item.hasItemMeta()){
//            if (meta==null){
//                //check if const item stack
//                if(item instanceof ConstItemStack cis){
//                    meta= cis.getItemMetaConst();
//                }else{
//                    meta=item.getItemMeta();
//                }
//            }
//            return meta;
//        }
//        return null;
//    }
    public void init(ItemStack itemStack) {
        super.init( itemStack);
        this.matchAmount = 0;
    }
    public int maxStackSize(){
        return this.maxStackCnt;
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
        this.matchAmount = MathUtils.safeAdd(this.matchAmount, matchAmount);
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
        return MathUtils.safeDivide(this.matchAmount,this.cnt);
    }

    public void setStackNum(int stackNum){
        //TODO may overflow
        matchAmount = stackNum*cnt  ;
    }
    public void addRelate(ItemPusher target){
        getTargetConsumers().add(target);
        target.dirty=true;
    }

    /**
     * add record of match amount from targte amount ,will not modify target
     * @param other
     */
    public void consume(ItemPusher other){
        this.matchAmount = MathUtils.safeAdd(this.matchAmount, other.getAmount());
        addRelate(other);
    }

    /**
     * operate on the match Amount,push ,sub match A Amount and add to target till maxCnt
     * @param target
     */
    public void push(ItemPusher target){
        int tmp=cnt;
        cnt=matchAmount;
        //safe ,will not overflow
        target.grab(this);
        matchAmount=cnt;
        cnt=tmp;

    }

    /**
     * operate on the match Amount,grab, add match Amount from target
     * @param target
     */
    public void grab(ItemPusher target){
        int tmp=cnt;
        cnt=MathUtils.safeAdd(target.getAmount(),tmp);
        dirty=true;
        target.addAmount(tmp-cnt);
    }

    /**
     */
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

    /**
     * simply update all related in record, be careful to use it
     * @param inv
     * @param mod
     */
    public void updateItems(BlockMenu inv ,Settings mod){
        if(targetConsumers==null){
            return;
        }
        int len=targetConsumers.size();
        for(int i=0;i<len;i++){
            targetConsumers.get(i).updateMenu(inv);
        }
    }
    public void syncItems(){
        if(targetConsumers==null){
            return;
        }
        int len=targetConsumers.size();
        for(int i=0;i<len;i++){
            targetConsumers.get(i).syncData();
        }
    }

    /**
     * calculate again about this if matchAmount changed manually,will use recorded Relateed and try grab/consume
     * make sure your matchAmount is what you want
     * @param inv
     * @param mod
     */
    public void updateItemsPlus(BlockMenu inv , Settings mod){
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
        matchAmount=cnt;
        cnt=s;
    }

    protected ItemGreedyConsumer clone(){
        return (ItemGreedyConsumer) super.clone();
    }

}
