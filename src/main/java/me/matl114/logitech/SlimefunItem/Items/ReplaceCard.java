package me.matl114.logitech.SlimefunItem.Items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.AddHandlers;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.Utils;
import net.guizhanss.guizhanlib.minecraft.helper.inventory.ItemStackHelper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ReplaceCard extends CustomSlimefunItem {
    public final static String LOC_DISPLAY_PREFIX = AddUtils.resolveColor("&x&E&B&3&3&E&B替代的物品: &f");
    public final static NamespacedKey KEY_LOC = AddUtils.getNameKey("rep_mat_id");
    public ReplaceCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow("&f机制",
                                "&7可以在快捷机器中替代其中记录的不可堆叠物品",
                                "&7桶类物品会在合成之后返还铁通")
                )
        );
    }

    public  ItemStack getReplaceCard(Material item) {
        String id=item.toString();
        String loreDisplay=AddUtils.concat(LOC_DISPLAY_PREFIX, ItemStackHelper.getDisplayName(new ItemStack(item)));
        ItemStack stack=getItem().clone();
        ItemMeta meta= stack.getItemMeta();
        PersistentDataContainer container=meta.getPersistentDataContainer();
        container.set(KEY_LOC, PersistentDataType.STRING,id);
        List<String> lore=meta.getLore();
        lore.add(loreDisplay);
        meta.setLore(lore);
        String name=meta.getDisplayName();
        name=AddUtils.concat(name," - ",ItemStackHelper.getDisplayName(new ItemStack(item)));
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack getCardReplacement(ItemStack item) {
        if(item!=null&&item.getType()==this.getItem().getType()&&item.hasItemMeta()){
            ItemMeta meta=item.getItemMeta();
            PersistentDataContainer container=meta.getPersistentDataContainer();
            if(container.has(KEY_LOC)){
                try {
                    String id = container.get(KEY_LOC, PersistentDataType.STRING);
                    Material mat = Material.getMaterial(id);
                    return new ItemStack(mat,item.getAmount());
                }catch (Throwable e){
                    Debug.debug(e);
                }
            }
        }
        return item;
    }
    @Override
    public  void preRegister(){
        super.preRegister();
        //addItemHandler(AddHandlers.stopAttackHandler);
        if(this.getItem().getType().isBlock()){
            addItemHandler(AddHandlers.stopPlacementHandler);
            addItemHandler(AddHandlers.stopPlaceerHandler);
        }
    }
}
