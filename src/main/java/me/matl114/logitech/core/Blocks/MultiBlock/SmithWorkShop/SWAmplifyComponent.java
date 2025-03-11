package me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop;

import com.google.common.base.Preconditions;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import me.matl114.logitech.core.Items.Abstracts.MaterialItem;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SWAmplifyComponent extends MaterialItem {
    static HashMap<Material,SWAmplifyComponent> materialJudgement=new HashMap<>();
    @Getter
    private Material type;
    public SWAmplifyComponent(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        Preconditions.checkArgument(!materialJudgement.containsKey(item.getType()),"Material of these Component must differ from each other for better judgement performance");
        this.type=item.getType();
        materialJudgement.put(item.getType(), this);
        this.setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow("&f机制",
                        "&7插入至锻铸工坊核心",
                        "&7对四周的工坊接口产生增幅"),
                this.getItem().clone()
        ));

    }
    public static SWAmplifyComponent getComponentType(ItemStack item){
        return item==null?null: materialJudgement.get(item.getType());
    }
}
