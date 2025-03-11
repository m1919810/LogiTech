package me.matl114.logitech.core.Cargo.Transportation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Language;
import me.matl114.logitech.utils.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RemoteCargoPlus extends RemoteCargo {
    public RemoteCargoPlus(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayList) {
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
                                "&7本机器可以选择任意方块参与传输",
                                "将绑定目标方块的%s插入指定槽位以选择参与的方块".formatted(Language.get("Items.HYPER_LINK.Name"))),null
                )
        );
        this.transportSmarter=true;
    }
}
