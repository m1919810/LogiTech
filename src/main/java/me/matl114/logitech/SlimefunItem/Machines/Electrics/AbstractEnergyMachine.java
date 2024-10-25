package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemState;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotHopperable;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import me.matl114.logitech.SlimefunItem.Interface.MenuBlock;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.UtilClass.TickerClass.Ticking;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class AbstractEnergyMachine extends CustomSlimefunItem implements Ticking, MenuBlock, EnergyNetComponent, NotHopperable {
    public final  int energybuffer;
    public final int energyConsumption;
    public final EnergyNetComponentType energyNetComponent;
    public AbstractEnergyMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                 int energybuffer, int energyConsumption, EnergyNetComponentType energyNetComponent){
        super(category, item, recipeType, recipe);
        this.energybuffer = energybuffer;
        this.energyConsumption = energyConsumption;
        this.energyNetComponent = energyNetComponent;

    }
    public void addInfo(ItemStack stack){
        stack.setItemMeta(AddUtils.capacitorInfoAdd(stack,this.energybuffer).getItemMeta());
    }
    /**
     * construct your menu here.called in constructor
     * @param preset
     */
    public abstract void constructMenu(BlockMenuPreset preset);
    /**
     * cargo and IO
     * @return
     */
    public abstract int[] getInputSlots();

    /**
     * cargo and IO
     * @return
     */
    public abstract int[] getOutputSlots();

    public void removeCharge(@Nonnull Location l, int charge){
        if(charge>0){
            EnergyNetComponent.super.removeCharge(l,charge);
        }
    }
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return this.energyNetComponent;
    }

    /**
     * get capacity
     * @return
     */
    public final int getCapacity(){
        return this.energybuffer;
    }

    public abstract void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) ;
    public final void tick(Block b, BlockMenu menu, int ticker) {
    }
    public void enable() {
        super.enable();
        this.registerDefaultRecipes();
    }

    public void disable() {
        super.disable();
        //this.getMachineRecipes().clear();
    }
    public void postRegister() {
        super.postRegister();
        if (this.getState() == ItemState.ENABLED) {
            this.registerDefaultRecipes();
        }

    }
    public void preRegister(){
        super.preRegister();
        //
        registerTick(this);
        //为menublock提供 需要
        registerBlockMenu(this);
    }
    protected void registerDefaultRecipes() {
    }
}
