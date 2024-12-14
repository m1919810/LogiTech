package me.matl114.logitech.SlimefunItem.Items;


import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.AddHandlers;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MaterialItem extends CustomSlimefunItem {
    public MaterialItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.checkCanStack=false;
    }
    public MaterialItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> recipeDisplay) {
        super(itemGroup, item, recipeType, recipe,recipeDisplay);
        this.checkCanStack=false;
    }
    public void addInfo(ItemStack stack){

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
