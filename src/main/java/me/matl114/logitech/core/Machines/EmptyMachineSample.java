package me.matl114.logitech.core.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.utils.Settings;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public  class EmptyMachineSample extends AbstractMachine {
    protected final int[] INPUT_SLOTS=new int[0];
    protected final int[] OUTPUT_SLOTS=new int[0];
    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }

    public EmptyMachineSample(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                           int energybuffer, int energyConsumption){
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
    }
    public void constructMenu(BlockMenuPreset preset){

    }
    public void newMenuInstance(BlockMenu menu, Block block){
    }
    public void updateMenu(BlockMenu menu, Block block, Settings mod){}
    public void process(Block b, BlockMenu menu, SlimefunBlockData data){}
}
