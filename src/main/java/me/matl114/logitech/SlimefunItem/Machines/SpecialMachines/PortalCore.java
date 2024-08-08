package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PortalCore extends AbstractMachine {
    protected final int[] BORDER={1,2,3,5,6,7,8};
    protected final int COORD_SLOT=0;
    protected final int TOGGLE_SLOT=1;
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<MachineRecipe>();
    }
    protected final ItemStack TOGGLE_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &a开启");
    protected final ItemStack TOGGLE_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &c关闭");
    public PortalCore(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                      int energybuffer, int energyConsumption){
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
    }
    public void constructMenu(BlockMenuPreset inv){

    }
    public void newMenuInstance(BlockMenu menu, Block block){

    }
    public void registerTick(SlimefunItem it){
        //no tick
    }
    public void process(Block b, BlockMenu menu){

    }
}
