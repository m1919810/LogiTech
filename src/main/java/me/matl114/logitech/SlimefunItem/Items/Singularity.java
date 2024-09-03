package me.matl114.logitech.SlimefunItem.Items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Language;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Utils;
import org.bukkit.inventory.ItemStack;

public class Singularity extends ItemNotPlaceable {
    public Singularity(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.setDisplayRecipes(
                Utils.list(
                        //TODO 写完描述
                        AddUtils.getInfoShow("&f机制 - &c终极合成",
                                "&c特性 ",
                                "&7终极合成是本附属某些终极机器中的机制",
                                "&c具体说明请看\"版本与说明\"分类中的信息",
                                "&7直白了说是让物品存储中的物品直接参与合成/进程",
                                "&7从而打破槽位限制",
                                "&7该物品是终极机器支持的物品之一"),
                        null,
                        AddUtils.getInfoShow("&7机制 -&c 物品存储",
                                "&7本物品可以类似%s一样绑定某个方块".formatted(Language.get("Items.HYPER_LINK.Name")),
                                "&7若该方块是支持的存储性方块,则该物品会与此存储建立纠缠链接",
                                "&7当该物品被放入终极机器的槽位中时,",
                                "&7可以代理绑定的存储中的物品&e直接参与合成!",
                                "&7产出的物品也可以通过它&e直接进入绑定的存储!",
                                "&7支持的存储性方块:",
                                "&7逻辑工艺 存储接口系列",
                                "&7网络(拓展) 量子存储系列"),null,
                        AddUtils.getInfoShow("&7机制 -&c 物品记录",
                                "&7当绑定了存储后","&7该物品的描述上会显示该绑定存储中的物品类型",
                                "&7当你右键该物品时,该显示将被更新为当前的绑定存储的物品类型",
                                "&7除此之外,该物品描述将不会更新,也不会影响其参与合成",
                                "&7参与合成时所代理的存储只与存储位置有关,与描述无关"),null
                )
        );
    }
}
