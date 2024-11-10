package me.matl114.logitech.SlimefunItem.Machines.Electrics;


import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.mooy1.infinityexpansion.items.machines.Machines;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class EnergyTrash  extends Capacitor {
    public EnergyTrash(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                       int energybuffer) {
        super(category, item, recipeType, recipe, energybuffer);
    }
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
    }
    public void setCharge(@Nonnull Location l, int charge){

    }
    public void removeCharge(@Nonnull Location l, int charge){
    }
    public void addCharge(@Nonnull Location l, int charge){
    }
    public int getCharge(@Nonnull Location l) {
        return 0;
    }

    /** @deprecated */
    @Deprecated
    public int getCharge(@Nonnull Location l, @Nonnull Config config) {
        return 0;
    }

    public int getCharge(@Nonnull Location l, @Nonnull SlimefunBlockData data) {
        return 0;
    }
}
