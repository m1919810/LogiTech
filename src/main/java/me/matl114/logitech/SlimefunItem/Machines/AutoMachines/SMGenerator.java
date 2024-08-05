package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;


import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.Machines.AbstractTransformer;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.UtilClass.DisplayItemStack;
import me.matl114.logitech.Utils.AddUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SMGenerator extends AbstractTransformer {

    private static final int INFO_SLOT = 0;
    private static final int[] OUTPUT_BORDER = {};
    private static final int[] INPUT_SLOT    = {};
    private static final int[] OUTPUT_SLOTS = {1,2, 3, 4, 5, 6, 7,8};

    public SMGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                       int time, int energybuffer,int energyConsumption,Object... outputs_w) {
        super(itemGroup, item, recipeType, recipe,time,energybuffer,energyConsumption,
                new LinkedHashMap<>(){{
                    this.put(new Pair<>(
                            new ItemStack[]{new DisplayItemStack(
                     new CustomItemStack(
                                Material.KNOWLEDGE_BOOK,
                                "&7速度",
                                "&7每 " + Integer.toString(time) + " 粘液刻生成一次"
                        ))}, Arrays.stream(outputs_w).toArray()
                    ),time) ;
                }}
        );


    }
    public void addInfo(ItemStack stack){
        stack.setItemMeta( AddUtils.smgInfoAdd(stack,time).getItemMeta() );
        super.addInfo(stack);
    }
    public void constructMenu(BlockMenuPreset preset){
        preset.addItem(INFO_SLOT,this.INFO_WORKING.clone(), ChestMenuUtils.getEmptyClickHandler());
    }
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }

    public boolean isSync(){
        return false;
    }
    public void process(Block block, BlockMenu inv){
        MachineRecipe nextP =getMachineRecipes().get(0);
        processorCost(block,inv);
        CraftUtils.pushItems(nextP.getOutput(),inv,getOutputSlots());
    }


}
