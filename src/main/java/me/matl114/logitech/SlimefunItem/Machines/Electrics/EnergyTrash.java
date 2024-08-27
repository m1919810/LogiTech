package me.matl114.logitech.SlimefunItem.Machines.Electrics;


import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.mooy1.infinityexpansion.items.machines.Machines;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class EnergyTrash  extends Capacitor {
    public EnergyTrash(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                       int energybuffer) {
        super(category, item, recipeType, recipe, energybuffer);
    }
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        this.setCharge(b.getLocation(),0);
    }
}
