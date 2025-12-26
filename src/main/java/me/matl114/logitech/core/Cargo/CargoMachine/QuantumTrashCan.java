package me.matl114.logitech.core.Cargo.CargoMachine;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import java.util.stream.IntStream;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.core.Registries.FinalFeature;
import me.matl114.logitech.utils.Settings;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.utils.UtilClass.StorageClass.ItemStorageCache;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class QuantumTrashCan extends AbstractMachine {
    /**
     * constructor of abstractMachines will keep Collections of MachineRecipes,will register energyNetwork params,
     * will set up menu by overriding constructMenu method
     *
     * @param category
     * @param item
     * @param recipeType
     * @param recipe
     * @param energybuffer
     * @param energyConsumption
     */
    protected int[] QUANTUM_SLOT = IntStream.range(0, 9).toArray();

    public QuantumTrashCan(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe, 0, 0);
    }

    @Override
    public void constructMenu(BlockMenuPreset preset) {
        preset.setSize(9);
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    public void process(Block b, BlockMenu preset, SlimefunBlockData data) {
        for (int slot : QUANTUM_SLOT) {
            ItemStack stack = preset.getItemInSlot(slot);
            if (stack == null) continue;
            ItemPusher counter = FinalFeature.STORAGE_AND_LOCPROXY_READER.get(Settings.INPUT, stack, slot);
            if (counter instanceof ItemStorageCache proxy && proxy.getAmount() != 0) {
                proxy.setAmount(0);
                proxy.updateMenu(preset);
            }
        }
    }
}
