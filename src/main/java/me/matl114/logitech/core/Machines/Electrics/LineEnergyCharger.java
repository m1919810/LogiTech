package me.matl114.logitech.core.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import java.util.Collection;
import java.util.HashSet;
import me.matl114.logitech.core.Interface.DirectionalBlock;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.CargoClass.Directions;
import me.matl114.logitech.utils.WorldUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class LineEnergyCharger extends AbstractEnergyCharger implements DirectionalBlock {
    protected final int MAX_LEN = 64;

    protected final String[] savedKeys = new String[] {"line_dir"};

    public String[] getSaveKeys() {
        return savedKeys;
    }

    protected final int[] DIRECTION_SLOTS = new int[] {10};

    public int[] getDirectionSlots() {
        return DIRECTION_SLOTS;
    }

    public LineEnergyCharger(
            ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energybuffer) {
        super(category, item, recipeType, recipe, energybuffer);
    }

    public Collection<SlimefunBlockData> getChargeRange(BlockMenu inv, Block block, SlimefunBlockData data) {
        Location loc = block.getLocation();
        Directions dir = getDirection(0, data);
        HashSet<SlimefunBlockData> ret = new HashSet<>();
        if (dir == Directions.NONE) {
            return ret;
        }
        Location loc2 = dir.relate(loc).add(0.5, 0.5, 0.5);
        SlimefunBlockData testdata;
        for (int i = 0; i < MAX_LEN; i++) {
            loc = dir.relate(loc);
            if (getChargeableComponent(DataCache.getSfItem(loc)) != null) {
                if ((testdata = DataCache.safeLoadBlock(loc)) != null) ret.add(testdata);
            }
        }
        if (getStatus(inv)[1]) {
            Location loc3 = loc.clone().add(0.5, 0.5, 0.5);
            Schedules.launchSchedules(
                    () -> {
                        WorldUtils.spawnLineParticle(loc2, loc3, Particle.WAX_ON, MAX_LEN);
                    },
                    0,
                    false,
                    0);
        }
        return ret;
    }

    public int getMaxChargeAmount() {
        return MAX_LEN;
    }

    private final int PARTICLE_SLOT = 0;
    private ItemStack PARTICLE_OFF = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&a点击切换粒子效果", "&7当前状态: &c关");
    private ItemStack PARTICLE_ON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&a点击切换粒子效果", "&7当前状态: &a开");

    @Override
    public boolean[] getStatus(BlockMenu inv) {
        ItemStack itemStack = inv.getItemInSlot(PARTICLE_SLOT);
        if (itemStack != null && itemStack.getType() == Material.GREEN_STAINED_GLASS_PANE) {
            return new boolean[] {super.getStatus(inv)[0], true};
        } else {
            return new boolean[] {super.getStatus(inv)[0], false};
        }
    }

    @Override
    public void toggleStatus(BlockMenu inv, boolean... result) {
        super.toggleStatus(inv, result[0]);
        if (result.length == 2) {
            if (result[1]) {
                inv.replaceExistingItem(PARTICLE_SLOT, PARTICLE_ON);
            } else {
                inv.replaceExistingItem(PARTICLE_SLOT, PARTICLE_OFF);
            }
        }
    }

    @Override
    public boolean isBorder(int i) {
        return super.isBorder(i) && i != PARTICLE_SLOT;
    }

    public void newMenuInstance(BlockMenu menu, Block block) {
        super.newMenuInstance(menu, block);
        menu.addMenuClickHandler(DIRECTION_SLOTS[0], getDirectionHandler(0, menu));
        ItemStack icon = menu.getItemInSlot(PARTICLE_SLOT);
        if (icon == null
                || (icon.getType() != Material.RED_STAINED_GLASS_PANE
                        && icon.getType() != Material.GREEN_STAINED_GLASS_PANE)) {
            menu.replaceExistingItem(PARTICLE_SLOT, PARTICLE_OFF);
        }
        menu.addMenuClickHandler(PARTICLE_SLOT, ((player, i, itemStack, clickAction) -> {
            boolean[] t = getStatus(menu);
            toggleStatus(menu, t[0], !t[1]);
            return false;
        }));
        updateDirectionSlots(0, menu);
    }
}
