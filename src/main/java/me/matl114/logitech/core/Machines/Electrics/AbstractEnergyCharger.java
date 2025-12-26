package me.matl114.logitech.core.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import me.matl114.logitech.core.Interface.MenuTogglableBlock;
import me.matl114.logitech.core.Machines.Abstracts.AbstractEnergyMachine;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Algorithms.AtomicCounter;
import me.matl114.logitech.utils.DataCache;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractEnergyCharger extends AbstractEnergyMachine implements MenuTogglableBlock {
    protected final int[] INPUT_SLOTS = new int[0];
    protected final int[] OUTPUT_SLOTS = new int[0];

    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    protected final int[] BORDER =
            new int[] {0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    protected final int INFO_SLOT = 13;
    protected final int LAZY_SLOT = 8;

    protected int getLazySlot() {
        return LAZY_SLOT;
    }

    protected int getInfoSlot() {
        return INFO_SLOT;
    }

    protected final ItemStack LAZY_ITEM_OFF = new CustomItemStack(
            Material.RED_STAINED_GLASS_PANE,
            "&6点击切换懒惰模式",
            "&7当前状态: &c关闭",
            "&7当启用懒惰模式时,只有电力剩余不足50%的机器会被充能",
            "&7这会大幅度的减少充能耗时");
    protected final ItemStack LAZY_ITEM_ON = new CustomItemStack(
            Material.GREEN_STAINED_GLASS_PANE,
            "&6点击切换懒惰模式",
            "&7当前状态: &a开启",
            "&7当启用懒惰模式时,只有电力剩余不足50%的机器会被充能",
            "&7这会大幅度的减少充能耗时");

    public AbstractEnergyCharger(
            ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energybuffer) {
        super(category, item, recipeType, recipe, energybuffer, 0, EnergyNetComponentType.CONSUMER);
    }

    protected ItemStack getInfoShow(int charge, int machine, int errors) {
        return new CustomItemStack(
                Material.GREEN_STAINED_GLASS_PANE,
                "&7已存储: %sJ/%sJ".formatted(AddUtils.formatDouble(charge), AddUtils.formatDouble(this.energybuffer)),
                "&7范围用电器数目: %d/%d(max)".formatted(machine, getMaxChargeAmount()),
                "&7充电报错数目: %d".formatted(errors));
    }

    public boolean isBorder(int i) {
        return i != getLazySlot();
    }

    public void constructMenu(BlockMenuPreset preset) {
        int[] border = BORDER;
        int len = border.length;
        for (int i = 0; i < len; ++i) {
            if (isBorder(border[i])) {
                preset.addItem(border[i], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }
        }
        preset.addItem(getInfoSlot(), getInfoShow(0, 0, 0), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public boolean[] getStatus(BlockMenu inv) {
        ItemStack itemStack = inv.getItemInSlot(getLazySlot());
        if (itemStack != null && itemStack.getType() == Material.GREEN_STAINED_GLASS_PANE) {
            return new boolean[] {true};
        } else {
            return new boolean[] {false};
        }
    }

    @Override
    public void toggleStatus(BlockMenu inv, boolean... result) {
        if (result[0]) {
            inv.replaceExistingItem(getLazySlot(), LAZY_ITEM_ON);
        } else {
            inv.replaceExistingItem(getLazySlot(), LAZY_ITEM_OFF);
        }
    }

    public void newMenuInstance(BlockMenu menu, Block block) {
        ItemStack icon = menu.getItemInSlot(getLazySlot());
        if (icon == null || icon.getType() != Material.RED_STAINED_GLASS_PANE) {
            menu.replaceExistingItem(getLazySlot(), LAZY_ITEM_ON);
        }
        menu.addMenuClickHandler(getLazySlot(), ((player, i, itemStack, clickAction) -> {
            boolean t = getStatus(menu)[0];
            toggleStatus(menu, !t);
            return false;
        }));
    }

    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
    }

    public abstract Collection<SlimefunBlockData> getChargeRange(BlockMenu menu, Block block, SlimefunBlockData data);

    protected boolean isChargeable(SlimefunItem that) {
        return true;
    }

    protected EnergyNetComponent getChargeableComponent(SlimefunItem item) {
        if (item != null && isChargeable(item) && item instanceof EnergyNetComponent ec) {
            return ec;
        } else return null;
    }

    public abstract int getMaxChargeAmount();

    @Override
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        Location loc = menu.getLocation();
        AtomicCounter charge = new AtomicCounter(this.getCharge(loc, data), this.energybuffer);
        int energyConsumer = 0;
        AtomicInteger errorMachine = new AtomicInteger(0);
        boolean lazymod = getStatus(menu)[0];
        Collection<SlimefunBlockData> allDatas = getChargeRange(
                menu, b, data); // DataCache.getAllSfItemInChunk(loc.getWorld(),loc.getBlockX()>>4,loc.getBlockZ()>>4);
        if (allDatas != null && !allDatas.isEmpty()) {
            for (SlimefunBlockData sf : allDatas) {
                SlimefunItem item = SlimefunItem.getById(sf.getSfId());
                EnergyNetComponent ec = getChargeableComponent(item);
                if (ec != null) {
                    if (ec.getEnergyComponentType() == EnergyNetComponentType.CONSUMER) {
                        if (sf == null || sf.isPendingRemove()) {
                            // ignore pending Remove machine
                            continue;
                        }
                        if (!sf.isDataLoaded()) {
                            DataCache.requestLoad(sf);
                            continue;
                        }
                        Location testLocation = sf.getLocation();
                        if (testLocation == null || loc.equals(testLocation)) {
                            continue;
                        }
                        energyConsumer++;
                        if (!charge.empty()) {
                            try {
                                int testCharge = ec.getCharge(testLocation, sf);
                                int buffer = ec.getCapacity();
                                int left = buffer - testCharge;
                                if (left > 0 && (!lazymod || left >= testCharge)) {
                                    int fetched = charge.required(left);
                                    ec.setCharge(testLocation, fetched + testCharge);
                                }
                            } catch (Throwable e) {
                                errorMachine.incrementAndGet();
                            }
                        }
                        if (energyConsumer >= getMaxChargeAmount()) {
                            break;
                        }
                    }
                }
            }
        }
        this.setCharge(loc, charge.get());
        if (menu.hasViewer()) {
            menu.replaceExistingItem(getInfoSlot(), getInfoShow(charge.get(), energyConsumer, errorMachine.get()));
        }
    }

    public void tickAsync(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        Location loc = menu.getLocation();
        AtomicCounter charge = new AtomicCounter(this.getCharge(loc, data), this.energybuffer);
        int energyConsumer = 0;
        AtomicInteger errorMachine = new AtomicInteger(0);
        boolean lazymod = getStatus(menu)[0];
        Collection<SlimefunBlockData> allDatas = getChargeRange(
                menu, b, data); // DataCache.getAllSfItemInChunk(loc.getWorld(),loc.getBlockX()>>4,loc.getBlockZ()>>4);
        if (allDatas != null && !allDatas.isEmpty()) {
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (SlimefunBlockData sf : allDatas) {
                SlimefunItem item = SlimefunItem.getById(sf.getSfId());
                EnergyNetComponent ec = getChargeableComponent(item);
                if (ec != null) {
                    if (ec.getEnergyComponentType() == EnergyNetComponentType.CONSUMER) {
                        if (!sf.isDataLoaded()) {
                            DataCache.requestLoad(sf);
                            continue;
                        }
                        Location testLocation = sf.getLocation();
                        if (loc.equals(testLocation)) {
                            continue;
                        }
                        energyConsumer++;
                        if (!charge.empty()) {
                            futures.add(CompletableFuture.runAsync(() -> {
                                        int testCharge = ec.getCharge(testLocation, sf);
                                        int buffer = ec.getCapacity();
                                        int left = buffer - testCharge;
                                        if (left > 0 && (!lazymod || left >= testCharge)) {
                                            int fetched = charge.required(left);
                                            ec.setCharge(testLocation, fetched + testCharge);
                                        }
                                    })
                                    .exceptionally(ex -> {
                                        errorMachine.incrementAndGet();
                                        return null;
                                    }));
                        }
                        if (energyConsumer >= getMaxChargeAmount()) {
                            break;
                        }
                    }
                }
            }
            if (!futures.isEmpty()) {
                CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                        .join();
            }
        }
        this.setCharge(loc, charge.get());
        if (menu.hasViewer()) {
            menu.replaceExistingItem(getInfoSlot(), getInfoShow(charge.get(), energyConsumer, errorMachine.get()));
        }
    }
}
