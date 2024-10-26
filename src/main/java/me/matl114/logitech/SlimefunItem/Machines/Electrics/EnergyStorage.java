package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class EnergyStorage extends AbstractEnergyMachine{
    protected final int[] INPUT_SLOTS=new int[0];
    protected final int[] OUTPUT_SLOTS=new int[0];
    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    protected final int[] BORDER=new int[]{
            0,1,2,3,4,5,6,7,8,9,10,11,12,14,15,16,17,18,19,20,21,22,23,24,25,26
    };
    protected final int INFO_SLOT=13;

    protected int getInfoSlot(){
        return INFO_SLOT;
    }

    public EnergyStorage(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                 int energybuffer,EnergyNetComponentType energyNetComponent){
        super(category, item, recipeType, recipe, energybuffer, 0, energyNetComponent);
    }

    protected ItemStack getInfoShow(int charge){
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6信息","&7已存储: %sJ/%sJ".formatted(AddUtils.formatDouble(charge),AddUtils.formatDouble(this.energybuffer)));
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(getInfoSlot(),getInfoShow(0),ChestMenuUtils.getEmptyClickHandler());
    }
    public void newMenuInstance(BlockMenu menu, Block block){
    }

    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
    }

    @Override
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        if(menu.hasViewer()){
            menu.replaceExistingItem(INFO_SLOT,getInfoShow(this.getCharge(menu.getLocation(),data)));
        }
    }
}
