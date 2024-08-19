package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Machines.AbstractEnergyProvider;
import me.matl114.logitech.Utils.*;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TestMachine extends AbstractEnergyProvider {
    public final int ENERGY_ABSMAX;
    public final int OUTPUT_MIN;
    public final int OUTPUT_MAX;
    public final ItemStack OUTPUT= AddItem.PARADOX;
    public final int DISPLAY_SLOT=0;
    public final int[] OUTPUT_SLOT=new int[]{1,2,3,4,5,6,7,8};
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public void addInfo(ItemStack stack){
        stack.setItemMeta(AddUtils.addLore(stack,
                new StringBuilder("&8⇨ &e⚡ &7").
                        append(AddUtils.formatDouble(-ENERGY_ABSMAX)).
                        append(" ~ ").
                        append(AddUtils.formatDouble(ENERGY_ABSMAX)).append(" J/t").toString()).getItemMeta());

    }
    public TestMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,int energyBuffer,int energyAbsMax,
                       int outputMin,int outputMax){
        super(itemGroup, item, recipeType, recipe,energyBuffer,0);
        this.OUTPUT_MIN=outputMin;
        this.OUTPUT_MAX=outputMax;
        this.ENERGY_ABSMAX=energyAbsMax;
        this.machineRecipes=new ArrayList<>();
        this.machineRecipes.add(MachineRecipeUtils.stackFrom(-1,
                Utils.array(AddUtils.getInfoShow("&f生成机制","&7当机器中电力位于1,000J~1,145J时","&7清空电力并生成")),
                Utils.array(OUTPUT)));
    }
    public List<MachineRecipe> getMachineRecipes(){
        return machineRecipes;
    }
    public EnergyNetComponentType getEnergyComponentType(){
            return EnergyNetComponentType.GENERATOR;
    }

    public int getGeneratedOutput(@Nonnull Location l, @Nonnull SlimefunBlockData data){
        BlockMenu inv= StorageCacheUtils.getMenu(l);
        int charge=getCharge(l);

        if(charge>OUTPUT_MIN&&charge<OUTPUT_MAX){
            CraftUtils.pushItems(new ItemStack[]{OUTPUT.clone()},inv,getOutputSlots());
            charge=0;
            setCharge(l,0);
        }
        if(inv.hasViewer()){
            inv.replaceExistingItem(DISPLAY_SLOT,AddUtils.getGeneratorDisplay(true,"虚空量子",charge,this.energybuffer));
        }
        return  AddUtils.random(2*this.ENERGY_ABSMAX+1)-ENERGY_ABSMAX;
    }
    public void constructMenu(BlockMenuPreset inv){
        inv.addItem(DISPLAY_SLOT, ChestMenuUtils.getBackground());
    }
    public void newMenuInstance(BlockMenu inv, Block block){
        inv.addMenuOpeningHandler((player -> {
            inv.replaceExistingItem(DISPLAY_SLOT,ChestMenuUtils.getBackground());
        }));
    }

}
