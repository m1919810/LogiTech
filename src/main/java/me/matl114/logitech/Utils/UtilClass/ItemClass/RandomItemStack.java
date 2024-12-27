package me.matl114.logitech.Utils.UtilClass.ItemClass;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
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
    List<Pair<ItemStack ,Integer>> itemMap;
    public RandomItemStack(List<Pair<ItemStack ,Integer>> itemSettings) {
        super(itemSettings.get(0).getFirstValue());
        this.sum=itemSettings.size();
        this.itemList=new ItemStack[sum];
        this.itemWeight=new double[sum];
        int weight = 0;
        for(var entry : itemSettings) {
            weight += entry.getSecondValue();
        }
        int cnt=0;
        weightSum=new int[sum+1];
        weightSum[0]=0;
        for(var entry : itemSettings) {
            itemList[cnt] = entry.getFirstValue();
            double r=entry.getSecondValue();
            itemWeight[cnt]= r/(double)weight;
           weightSum[cnt+1]=weightSum[cnt]+entry.getSecondValue();
            cnt++;
        }
        itemMap=new ArrayList<>(itemSettings);
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
    public ItemStack clone(){
        int i=weightedRandom(this.weightSum);
        return this.itemList[i].clone();
    }
    public RandomItemStack copy(){
        return new RandomItemStack(this.itemMap);
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
