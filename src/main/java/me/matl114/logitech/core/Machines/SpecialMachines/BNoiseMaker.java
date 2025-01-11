package me.matl114.logitech.core.Machines.SpecialMachines;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.core.AddSlimefunItems;
import me.matl114.logitech.core.Items.SpecialItems.BNoiseHead;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Just a kidding lol
 * @author balugaq
 */
public class BNoiseMaker extends AbstractMachine {
    private static final Sound[] SOUNDS = Sound.values();
    private static final int[] BACKGROUND_SLOTS = {
            0,  1,  2,  3,  4,  5,  6,  7,  8,
            9,  10, 11, 12,     14, 15, 16, 17,
            18, 19, 20, 21,     23, 24, 25, 26
    };
    private static final int HEAD_SLOT = 13;
    private static final int MAKE_NOISY_HEAD = 22;

    public BNoiseMaker(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe, 2500, 100);

    }



    @Override
    public void constructMenu(BlockMenuPreset preset) {
        for (int slot : BACKGROUND_SLOTS) {
            preset.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    public void newMenuInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        blockMenu.addItem(MAKE_NOISY_HEAD, new CustomItemStack(Material.CREEPER_HEAD, "&e点击制造B动静头盔"), (p, s, i, t) -> {
            Sound sound = makeNoise(p.getLocation());
            if (sound == null) {
                return false;
            }
            blockMenu.replaceExistingItem(HEAD_SLOT, ((BNoiseHead) AddSlimefunItems.BNOISE_HEAD).of(sound));
            return false;
        });
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
    public void process(@Nonnull Block b, @Nonnull BlockMenu menu, @Nonnull SlimefunBlockData data) {
        makeNoise(b.getLocation());
    }

    @CanIgnoreReturnValue
    public Sound makeNoise(@Nonnull Location location) {
        try {
            // play sound randomly
            int soundIndex = (int) (Math.random() * SOUNDS.length);
            World world = location.getWorld();
            if (world == null) {
                return null;
            }
            Sound sound = SOUNDS[soundIndex];
            Schedules.execute(()->{
                for (Entity entity : world.getNearbyEntities(location, 16, 16, 16)) {
                    if (entity instanceof Player player) {
                        player.playSound(location, sound, 1, 1);
                    }
                }
            },true);

            return sound;
        } catch (Throwable e) {
            return null;
        }
    }
}
