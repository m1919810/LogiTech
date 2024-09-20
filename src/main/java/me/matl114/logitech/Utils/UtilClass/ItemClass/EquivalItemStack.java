package me.matl114.logitech.Utils.UtilClass.ItemClass;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.Utils.CraftUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EquivalItemStack extends ItemStack implements MultiItemStack ,EqualInItem{
    public ItemStack[] itemList;
    public ItemCounter[] counterList;
    public Double[] itemWeight;
    public double[] weightSum;
    public int sum;
    private static Material getFirstMaterial(HashMap<ItemStack,Integer> map) {
        if(map.isEmpty()) {
            return Material.STONE;
        }else {
            Iterator<Map.Entry<ItemStack,Integer>> iterator = map.entrySet().iterator();
            if(iterator.hasNext()) {
                Map.Entry<ItemStack,Integer> entry = iterator.next();
                return entry.getKey().getType();
            }else {
                return Material.STONE;
            }
        }
    }
    protected int eamount=1;
    public EquivalItemStack(HashMap<ItemStack ,Integer> itemSettings) {
        super(getFirstMaterial(itemSettings));
        this.sum=itemSettings.keySet().size();
        this.itemList=new ItemStack[sum];
        this.counterList=new ItemCounter[sum];
        this.itemWeight=new Double[sum];
        int weight = itemSettings.size();

        int cnt=0;
        weightSum=new double[sum+1];
        weightSum[0]=0;
        for(Map.Entry<ItemStack, Integer> entry : itemSettings.entrySet()) {
            itemList[cnt] = entry.getKey();
            counterList[cnt]=ItemCounter.get(itemList[cnt]);
            itemWeight[cnt]= 1.0/(double)weight;
            weightSum[cnt+1]=weightSum[cnt]+itemWeight[cnt];
            ++cnt;
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
        for(int i=0;i<items.size();i++) {
            items.get(i).setAmount(this.eamount);
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
        for (int i = 0; i < counterList.length; i++) {
            if(CraftUtils.matchItemStack(item,counterList[i],strickCheck)){
                return true;
            }
        }
        return false;
    }
    public void setEqualAmount(int t){
        this.eamount=t;
    }
    public int getAmount(){
        return this.eamount;
    }
    public void setAmount(int t){
        throw new NotImplementedException("this method shoudln't be called");
    }
}
