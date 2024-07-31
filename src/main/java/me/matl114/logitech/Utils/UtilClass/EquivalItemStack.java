package me.matl114.logitech.Utils.UtilClass;

import me.matl114.logitech.Utils.CraftUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EquivalItemStack extends ItemStack implements MultiItemStack{
    public ItemStack[] itemList;
    public Double[] itemWeight;
    public double[] weightSum;
    public int sum;
    public EquivalItemStack(HashMap<ItemStack ,Integer> itemSettings) {
        super(Material.AIR);
        this.sum=itemSettings.keySet().size();
        this.itemList=new ItemStack[sum];
        this.itemWeight=new Double[sum];
        int weight = itemSettings.size();

        int cnt=0;
        weightSum=new double[sum+1];
        weightSum[0]=0;
        for(Map.Entry<ItemStack, Integer> entry : itemSettings.entrySet()) {
            itemList[cnt] = entry.getKey();
            itemWeight[cnt]= 1.0/(double)weight;
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
        return itemList[0].clone();
    }
    public boolean matchItem(ItemStack item,boolean strickCheck){
        for (int i = 0; i < itemList.length; i++) {
            if(CraftUtils.matchItemStack(item,itemList[i],strickCheck)){
                return true;
            }
        }
        return false;

    }
}
