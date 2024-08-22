package me.matl114.logitech.Utils.UtilClass.ItemClass;

import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class ProbItemStack extends ItemStack implements MultiItemStack {
    ItemStack stack;
    double prob;
    List<ItemStack> stacklist;
    List<Double> problist;
    public ProbItemStack(ItemStack stack,double prob) {
        super(stack);
        this.stack = stack;
        this.stacklist = new ArrayList<ItemStack>();
        this.problist=new ArrayList<>();
        if(stack instanceof MultiItemStack ms) {
            this.stacklist.addAll(ms.getItemStacks());
            this.problist.addAll(ms.getWeight(prob));
        }else{
            this.stacklist.add(stack);
            this.problist.add(prob);
        }
        this.prob = prob;
    }
    public ItemStack clone(){
        if(AddUtils.standardRandom()<this.prob){
            return stack.clone();
        }
        return new ItemStack(Material.AIR);
    }
    public List<ItemStack> getItemStacks() {
        return stacklist;
    }
    public List<Double> getWeight(Double percent){
        List<Double> doubles=new ArrayList<>();
        int len=this.problist.size();
        for (int i=0;i<len;i++){
            doubles.add(this.problist.get(i)*percent);
        }
        return doubles;
    }
    public int getTypeNum(){
        return 1;
    }
    public boolean matchItem(ItemStack item,boolean strictCheck){
        return false;
    }
}
