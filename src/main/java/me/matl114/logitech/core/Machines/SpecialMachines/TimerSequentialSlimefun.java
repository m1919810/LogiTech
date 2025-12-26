package me.matl114.logitech.core.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.ChunkPosition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import me.matl114.logitech.utils.DataCache;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class TimerSequentialSlimefun extends TimerSlimefun {
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
    public TimerSequentialSlimefun(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            int energybuffer,
            int energyConsumption) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
    }

    public void registerTick(SlimefunItem item) {
        item.addItemHandler(new BlockTicker() {
            public boolean isSynchronized() {
                return false;
            }

            int tickCount = 0;

            @ParametersAreNonnullByDefault
            public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
                Location loc = b.getLocation();
                if (RUNNING_MACHINES.contains(loc)) {
                    return;
                } else {
                    RUNNING_MACHINES.add(loc);
                }
                BlockMenu menu = data.getBlockMenu();
                int timer = 64;
                ChunkPosition position = new ChunkPosition(loc);
                tickingLocationsAccess.get((locations) -> {
                    Set<Location> tickingLocations;
                    synchronized (locations) {
                        tickingLocations = new HashSet<>(locations.get(position));
                    }
                    long startTime = System.nanoTime();
                    int async = tickChunk(tickingLocations, timer);
                    long totalTime = System.nanoTime() - startTime;
                    CompletableFuture.runAsync(() -> {
                        if (menu.hasViewer()) {
                            menu.replaceExistingItem(
                                    REPORT_SLOT, getInfoShow(tickingLocations.size(), async, timer, totalTime));
                        }
                        if (getStatus(menu)[0]) {
                            doParticle(loc);
                        }
                    });
                });
            }

            @Override
            public void uniqueTick() {
                RUNNING_MACHINES.clear();
                tickCount++;
            }
        });
    }

    private int tickChunk(Set<Location> locations, int timer) {
        List<Runnable> tickTasks = new ArrayList<>();
        locations.forEach(l -> {
            SlimefunBlockData blockData = DataCache.safeLoadBlock(l);
            if (blockData != null && blockData.isDataLoaded() && !blockData.isPendingRemove()) {
                SlimefunItem item = SlimefunItem.getById(blockData.getSfId());
                if (item != null && !blacklistedMachines.contains(item.getClass()) && item.getBlockTicker() != null) {
                    if (item.isDisabledIn(l.getWorld())) {
                        return;
                    }
                    try {
                        if (item.getBlockTicker().isSynchronized()) {
                            item.getBlockTicker().update();
                            Slimefun.runSync(() -> {
                                if (!blockData.isPendingRemove()) {
                                    Block b = l.getBlock();
                                    this.tickBlockTimer(l, b, item, blockData, timer);
                                }
                            });
                        } else {
                            item.getBlockTicker().update();
                            Block b = l.getBlock();
                            tickTasks.add(() -> {
                                this.tickBlockTimer(l, b, item, blockData, 1);
                            });
                        }
                    } catch (Exception var8) {
                    }
                }
            }
        });
        List<CompletableFuture> waitThreads = new ArrayList<>();
        for (int i = 0; i < timer; ++i) {
            waitThreads.add(CompletableFuture.runAsync(() -> {
                for (Runnable runnable : tickTasks) {
                    synchronized (runnable) {
                        runnable.run();
                    }
                }
            }));
        }
        CompletableFuture.allOf(waitThreads.toArray(new CompletableFuture[0])).join();
        return tickTasks.size();
    }

    @ParametersAreNonnullByDefault
    private void tickBlockTimer(Location l, Block b, SlimefunItem item, SlimefunBlockData data, int timer) {
        try {
            for (int i = 0; i < timer; ++i) {
                item.getBlockTicker().tick(b, item, data);
            }
        } catch (LinkageError | Exception var11) {
            reportError.invokeCallback((nul) -> {}, () -> {}, Slimefun.getTickerTask(), l, item, var11);
        }
    }
}
