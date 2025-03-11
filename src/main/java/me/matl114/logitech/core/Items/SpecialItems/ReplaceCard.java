package me.matl114.logitech.core.Items.SpecialItems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.core.Items.Abstracts.DataRecordedItem;
import me.matl114.logitech.core.Registries.AddHandlers;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Utils;
import me.matl114.logitech.utils.WorldUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public class ReplaceCard extends DataRecordedItem {
    public final static String LOC_DISPLAY_PREFIX = AddUtils.resolveColor("&x&E&B&3&3&E&B替代的物品: &f");
    public final static NamespacedKey KEY_LOC = AddUtils.getNameKey("rep_mat_id");
    public enum ReplaceType{
        MATERIAL(i->new ItemStack(Material.getMaterial(i)),i->i.getType().toString()),
        SLIMEFUN(i-> {
            var sf=SlimefunItem.getById(i);
            return sf==null?null:AddUtils.getCleaned( sf.getRecipeOutput() );
        }, i-> Slimefun.getItemDataService().getItemData(i).orElse(null)),
        UNKNOWN((i)->null,(i)->null);
        ReplaceType(Function<String,ItemStack> deserializeFunc,Function<ItemStack,String> serializeFunc) {
            this.deserializeFunc = deserializeFunc;
            this.serializeFunc = serializeFunc;
        }
        public Function<String,ItemStack> deserializeFunc;
        public Function<ItemStack,String> serializeFunc;
    }
    ReplaceType type=ReplaceType.UNKNOWN;
    static HashSet<ReplaceCard> instances=new HashSet<>();
    public ReplaceCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,ReplaceType type) {
        super(itemGroup, item, recipeType, recipe);
        this.setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow("&f机制",
                                "&7可以在快捷机器中替代其中记录的不可堆叠物品",
                                "&7桶类物品会在合成之后返还铁桶")
                )
        );
        this.type=type;
        instances.add(this);
    }

    public  ItemStack getReplaceCard(Material item) {
        String id=item.toString();
        return getReplaceCard(id, CraftUtils.getItemName(new ItemStack(item)));
    }
    public ItemStack getReplaceCard(SlimefunItem item){
        String id=item.getId();
        return getReplaceCard(id,CraftUtils.getItemName(item.getItem()));
    }
    public ItemStack getReplaceCard(String id,String displayName){
        String loreDisplay=AddUtils.concat(LOC_DISPLAY_PREFIX, displayName);
        ItemStack stack=getItem().clone();
        ItemMeta meta= stack.getItemMeta();
        PersistentDataContainer container=meta.getPersistentDataContainer();
        container.set(KEY_LOC, PersistentDataType.STRING,id);
        List<String> lore=meta.getLore();
        lore.add(loreDisplay);
        meta.setLore(lore);
        String name=meta.getDisplayName();
        name=AddUtils.concat(name," - ",displayName);
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack getAllCardReplacement(ItemStack item,ItemMeta meta){
        ItemStack result;
        for(ReplaceCard replaceCard:instances){
            result=replaceCard.getCardReplacement(item,meta);
            if(result!=item){
                return result;
            }
        }
        return item;
    }
    public ItemStack getCardReplacement(ItemStack item,ItemMeta meta) {
        if(item!=null&&item.getType()==this.getItem().getType()){
            PersistentDataContainer container=meta.getPersistentDataContainer();
            if(container.has(KEY_LOC)){
                try {
                    String id = container.get(KEY_LOC, PersistentDataType.STRING);
                    ItemStack newStack=type.deserializeFunc.apply(id);
                    if(newStack!=null){
                        newStack.setAmount(item.getAmount());
                        return newStack;
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }
        return item;
    }
    @Override
    public  void preRegister(){
        super.preRegister();
        //addItemHandler(AddHandlers.stopAttackHandler);
        if(WorldUtils.isBlock(getItem().getType())){
            addItemHandler(AddHandlers.stopPlacementHandler);
            addItemHandler(AddHandlers.stopPlaceerHandler);
        }
    }
}
