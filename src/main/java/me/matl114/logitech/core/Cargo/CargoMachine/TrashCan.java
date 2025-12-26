package me.matl114.logitech.core.Cargo.CargoMachine;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import java.util.stream.IntStream;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class TrashCan extends AbstractMachine {
    protected final int[] INPUT_SLOTS = IntStream.rangeClosed(0, 53).toArray();
    protected final int[] OUTPUT_SLOTS = new int[0];

    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    public TrashCan(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe, 0, 0);
    }

    public void constructMenu(BlockMenuPreset preset) {
        preset.setSize(54);
    }

    public void process(Block b, BlockMenu preset, SlimefunBlockData data) {}

    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        // Schedules.launchSchedules(()->{
        int[] slots = getInputSlots();
        ItemStack it;
        for (int i = 0; i < slots.length; i++) {
            if (menu.getItemInSlot(slots[i]) == null) break;
            menu.replaceExistingItem(slots[i], null, false);
        }
        // },0,false,0);
    }
}
