package me.matl114.logitech.Utils.UtilClass.ItemClass;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RandomItemStack extends ItemStack implements MultiItemStack {
    public ItemStack[] itemList;
    public Double[] itemWeight;
    public double[] weightSum;
    public int sum;
    public RandomItemStack(LinkedHashMap<ItemStack ,Integer> itemSettings) {
        super(itemSettings.keySet().iterator().next());
        this.sum=itemSettings.keySet().size();
        this.itemList=new ItemStack[sum];
        this.itemWeight=new Double[sum];
        int weight = 0;
        for(Integer entry : itemSettings.values()) {
            weight += entry;
        }
        int cnt=0;
        weightSum=new double[sum+1];
        weightSum[0]=0;
        for(Map.Entry<ItemStack, Integer> entry : itemSettings.entrySet()) {
            itemList[cnt] = entry.getKey();
            double r=entry.getValue();
            itemWeight[cnt]= r/(double)weight;
            weightSum[cnt+1]=weightSum[cnt]+itemWeight[cnt];
            cnt++;
        }


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
        return itemList[AddUtils.weightedRandom(this.weightSum())].clone();
    }
    public int getMaxStackSize(){
        return 64;
    }
    public double[] weightSum(){
        return this.weightSum;
    }
    public void setAmount(int amount){
        throw new NotImplementedException("RandomItemStack is mutable and can not set Amount");
    }
    public boolean matchItem(ItemStack item,boolean strictCheck){
        return false;
    }
}
