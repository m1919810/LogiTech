package me.matl114.logitech.core.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.core.Registries.FinalFeature;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Utils;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FinalSequenceConstructor extends SequenceConstructor {
    public FinalSequenceConstructor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                               ItemStack progressItem, int energyConsumption, int energyBuffer,
                               List<Pair<Object, Integer>> customRecipes) {
        super(category,item,recipeType,recipe,progressItem,energyConsumption,energyBuffer,customRecipes);
        this.CRAFT_PROVIDER= FinalFeature.STORAGE_AND_LOCPROXY_READER;
        setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow(
                                "&f机制 - &c终极合成",
                                "&7机器的输入槽,输出槽均支持终极合成的特性",
                                "&e可以直接消耗存储类物品或者链接绑定的实体存储内的物品参与合成",
                                "&e产出的物品也可以直接地存入存储类物品或者链接绑定的实体存储",
                                "&7当前支持的物品:",
                                "&7逻辑工艺 概念奇点存储(存储类物品)",
                                "&7逻辑工艺 量子纠缠奇点(存储链接物品)",
                                "&7网络(拓展) 量子存储系列(存储类物品)",
                                "&a建议:多使用量子纠缠奇点,可以大幅度减少卡顿!"
                        ),null
                )
        );
    }
    public void registerTick(SlimefunItem item){
        this.addItemHandler(FinalFeature.FINAL_SYNC_TICKER);
    }
}
