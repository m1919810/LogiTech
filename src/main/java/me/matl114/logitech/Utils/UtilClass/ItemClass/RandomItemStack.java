package me.matl114.logitech.Utils.UtilClass.ItemClass;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RandomItemStack extends ItemStack implements MultiItemStack,RandOutItem {
    public Random rand=new Random();
    public ItemStack[] itemList;
    public double[] itemWeight;
    public int sum;
    public int[] weightSum;
    public RandomItemStack(LinkedHashMap<ItemStack ,Integer> itemSettings) {
        super(itemSettings.keySet().iterator().next());
        this.sum=itemSettings.keySet().size();
        this.itemList=new ItemStack[sum];
        this.itemWeight=new double[sum];
        int weight = 0;
        for(Integer entry : itemSettings.values()) {
            weight += entry;
        }
        int cnt=0;
        weightSum=new int[sum+1];
        weightSum[0]=0;
        for(Map.Entry<ItemStack, Integer> entry : itemSettings.entrySet()) {
            itemList[cnt] = entry.getKey();
            double r=entry.getValue();
            itemWeight[cnt]= r/(double)weight;
//           int lastWeight = this.weightMap.isEmpty() ? 0 : this.weightMap.lastKey();//统一转为double
//            this.weightMap.put(entry.getValue() + lastWeight, entry.getKey());
           weightSum[cnt+1]=weightSum[cnt]+entry.getValue();
            cnt++;
        }
        //this.weightSum=this.weightMap.lastKey();

    }
    //递归地解析全体物品列
    public List<ItemStack> getItemStacks() {
        List<ItemStack> items = new ArrayList<>();
        for(int i=0;i<sum;i++) {
            if(itemList[i] instanceof MultiItemStack) {
                items.addAll(((MultiItemStack) itemList[i]).getItemStacks());
            }else{
                items.add(itemList[i].clone());
            }
        }
        return items;
    }
    public List<Double> getWeight(Double percentage) {
        List<Double> weights = new ArrayList<>();
        for(int i=0;i<sum;i++) {
            if(itemList[i] instanceof MultiItemStack) {
                weights.addAll(((MultiItemStack) itemList[i]).getWeight( itemWeight[i]));
            }else{
                weights.add(itemWeight[i]*percentage);
            }
        }
        return weights;
    }
    public int getTypeNum(){
        return this.sum;
    }
//    public ItemStack randIndex(){
//        int randomWeight =   AddUtils.random(weightSum);
//        SortedMap<Integer,ItemStack> tailMap = this.weightMap.tailMap(randomWeight, false);
//        return this.weightMap.get(tailMap.firstKey());
//    }
    public ItemStack clone(){
        int i=weightedRandom(this.weightSum);
        return this.itemList[i].clone();
    }
    public RandomItemStack copy(){
        RandomItemStack stack;
        stack=(RandomItemStack)super.clone();
        stack.itemList=Arrays.copyOf(this.itemList,this.itemList.length);
        stack.sum=this.sum;
        stack.weightSum=Arrays.copyOf(this.weightSum,this.weightSum.length);
        stack.itemWeight=Arrays.copyOf(this.itemWeight,this.itemWeight.length);
        return stack;

    }
    public ItemStack getInstance(){
        long a,s;
        ItemStack it=this.itemList[weightedRandom(this.weightSum)];
        return (it instanceof RandOutItem w)?w.getInstance():it;
    }
    public int weightedRandom(int[] weightSum ){
        int len=weightSum.length;
        int randouble=rand.nextInt(weightSum[len-1]);
        //if(len>114){
        int start=0;
        int end=len-1;
        while(end-start>1){
            int mid=(start+end)/2;
            if(weightSum[mid]<randouble){
                start=mid;
            }else if (weightSum[mid]>randouble) {
                end=mid;
            }else return mid;
        }
        return start;
        // }
//        else{
//
//            for(int i=0;i<len-1;i++){
//                if(randouble<weightSum[i+1]){
//                    return i;
//                }
//            }
//            return 0;
//        }
    }
    public int getMaxStackSize(){
        return 64;
    }
    public void setAmount(int amount){
        throw new NotImplementedException("AbstractItemStack can not set Amount");
    }
    public boolean matchItem(ItemStack item,boolean strictCheck){
        return false;
    }
}
