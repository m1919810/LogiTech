package me.matl114.logitech.core.Items.SpecialItems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.Items.Abstracts.DataRecordedItem;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Utils;
import org.bukkit.inventory.ItemStack;

public class Singularity extends DataRecordedItem {
    public Singularity(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow(
                        "&f机制 - &c终极合成",
                        "&c特性 ",
                        "&7终极合成是本附属某些终极机器中的机制",
                        "&c具体说明请看\"版本与说明\"分类中的信息",
                        "&7直白了说是让物品存储中的物品直接参与合成/进程",
                        "&7从而打破槽位限制",
                        "&7该物品是终极机器支持的物品之一",
                        "&a建议:不推荐直接将存储奇点放入终极机器,不方便且会有更高卡顿!"),
                null,
                AddUtils.getInfoShow(
                        "&7机制 -&c 物品存储",
                        "&7本物品可以存储某种物品",
                        "&7本物品的最大存储量为2147483647",
                        "&7当该物品被放入终极机器的槽位中时,",
                        "&7可以代理其内部存储的物品&e直接参与合成!",
                        "&7产出的物品也可以&e直接进入内部存储的物品中!",
                        "&e本物品需要奇点交互接口才可以进行数量操作和种类设置"),
                null));
    }
}
