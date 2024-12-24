package me.matl114.logitech.SlimefunItem.Cargo.Transportation;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RedstoneAdjacentCargo extends AdjacentCargo {
    public RedstoneAdjacentCargo (ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayList) {
        super(itemGroup, item, recipeType, recipe, displayList);
        setDisplayRecipes(
                Utils.list(AddUtils.getInfoShow("&f机制",
                                "&7这是一个可调控货运机器",
                                "&7可调控货运机器的行为会包括若干对源方块和目标方块",
                                "&7可调控货运机器会进行从源方块到目标方块的物流传输",
                                "&7可调控货运机器会根据某些特定条件决定运输行为",
                                "&7可调节货运机器支持目标方块设置的输入槽限制",
                                "&7但是相应的,其最大传输量会被限制为min(576,配置数)"
                        ),null,
                        AddUtils.getInfoShow("&f机制",
                                "&7本机器可以选择与其相邻的方块参与传输",
                                "&7本机器接受红石信号的调控",
                                "&7当该方块被充能时,进行正常推送",
                                "&7当该方块不被充能时,待机",
                                "&c红石信息的调控将有1sft(0.5s)延迟,当前刻更改的充能状态将在下一刻生效!"),null
                )
        );
    }
    public String POWERED_KEY="po";
    public void cargoTask(Block b, BlockMenu menu, SlimefunBlockData data, int configCode){
        BlockData data1=b.getBlockData();
        if(data1 instanceof Lightable la&&la.isLit()){
            super.cargoTask(b,menu,data,configCode);
           // DataCache.setCustomData(data,POWERED_KEY,la.isLit()?1:0);
        }
    }
    public boolean isSync(){
        //run in sync thread can be faster
        return true;
    }
}
