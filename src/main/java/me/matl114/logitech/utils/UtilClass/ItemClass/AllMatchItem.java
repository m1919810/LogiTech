package me.matl114.logitech.utils.UtilClass.ItemClass;

import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.matlib.Utils.Inventory.ItemStacks.CleanItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AllMatchItem extends EquivalItemStack{
    public static AllMatchItem ofAmount(int amount){
        var re = new AllMatchItem();
        re.setEqualAmount(amount);
        return re;
    }
    private static List<Pair<ItemStack,Integer>> ALL_MATCH_ITEMS = List.of(new Pair<>(new CleanItemStack(Material.STONE,"&a匹配任意物品"),1));
    public AllMatchItem() {
        super(ALL_MATCH_ITEMS);
    }
    public AllMatchItem copy(){

        return ofAmount(this.getAmount());
    }
    public boolean matchItem(ItemStack item,boolean strickCheck){
        return true;
    }
}
