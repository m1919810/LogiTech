package me.matl114.logitech.SlimefunItem.Cargo.Transportation;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.Cargo.AbstractCargo;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.TransportUtils;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.matl114.logitech.Utils.Utils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;

public class AdjacentCargoPlus extends AdjacentCargo {
    public AdjacentCargoPlus(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayList) {
        super(itemGroup, item, recipeType, recipe, displayList);
        setDisplayRecipes(
                Utils.list(AddUtils.getInfoShow("&f机制",
                                "&7这是一个智能货运机器",
                                "&7智能货运机器的行为会包括若干对源方块和目标方块",
                                "&7智能货运机器会进行从源方块到目标方块的物流传输",
                                "&7智能货运机器支持目标方块设置的输入槽限制",
                                "&7但是相应的,其最大传输量会被限制为min(576,配置数)"
                        ),null,
                        AddUtils.getInfoShow("&f机制",
                                "&7本机器可以选择与其相邻的方块参与传输"),null
                )
        );
    }
    public void cargoTask(Block b, BlockMenu menu, SlimefunBlockData data, int configCode){
        Location loc=menu.getLocation();
        Directions from_dir=getDirection("from_dir",data);
        BlockMenu from= StorageCacheUtils.getMenu(from_dir.relate(loc));
        if(from==null){
            return;
        }
        Directions to_dir=getDirection("to_dir",data);
        BlockMenu to= StorageCacheUtils.getMenu(to_dir.relate( loc));
        if(to==null){
            return;
        }
        int[] bwslots=getBWListSlot();
        HashSet<ItemStack> bwset=new HashSet<>();
        ItemStack it;
        for (int i=0;i<bwslots.length;++i){
            it=menu.getItemInSlot(bwslots[i]);
            if(it!=null){
                bwset.add(it);
            }
        }
        TransportUtils.transportItemSmarter(from,to,configCode,bwset);
    }
}
