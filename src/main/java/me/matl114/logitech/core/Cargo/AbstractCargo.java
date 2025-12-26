package me.matl114.logitech.core.Cargo;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import me.matl114.logitech.core.Cargo.Config.CargoConfigCard;
import me.matl114.logitech.core.CustomSlimefunItem;
import me.matl114.logitech.core.Interface.DirectionalBlock;
import me.matl114.logitech.core.Interface.MenuBlock;
import me.matl114.logitech.core.Interface.RecipeDisplay;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.Settings;
import me.matl114.logitech.utils.UtilClass.CargoClass.CargoConfigs;
import me.matl114.logitech.utils.UtilClass.TickerClass.Ticking;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * abstracts of Inventory transportation machines
 */
public abstract class AbstractCargo extends CustomSlimefunItem
        implements RecipeDisplay, MenuBlock, Ticking, DirectionalBlock {
    public abstract int[] getInputSlots();

    public abstract int[] getOutputSlots();

    public abstract int getConfigSlot();

    public abstract int[] getBWListSlot();

    public int defaultConfigCode = CargoConfigs.getDefaultConfig();

    public int getDefaultConfig() {
        return defaultConfigCode;
    }

    protected final ItemStack[] DIRECTION_ITEM = new ItemStack[] {
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 无"),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向北")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向西")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向南")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向东")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向上")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向下"))
    };

    public AbstractCargo(
            ItemGroup itemGroup,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            List<ItemStack> displayList) {
        super(itemGroup, item, recipeType, recipe, null);
        this.displayedMemory = new ArrayList<>();
        if (displayList == null) {
            displayList = new ArrayList<>();
        }
        int len = displayList.size();
        for (int i = 0; i < len; i++) {
            displayedMemory.add(displayList.get(i));
            displayedMemory.add(null);
        }
    }

    public abstract void constructMenu(BlockMenuPreset preset);

    public int getConfigCode(SlimefunBlockData data) {
        return DataCache.getCustomData(data, "config", -1);
    }

    public void loadConfig(BlockMenu inv, Block b) {
        loadConfig(inv, b, DataCache.safeLoadBlock(inv.getLocation()));
    }

    public void loadConfig(BlockMenu inv, Block b, SlimefunBlockData data) {
        int code = -1;
        ItemStack item = inv.getItemInSlot(getConfigSlot());
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && CargoConfigCard.isConfig(meta)) {
                code = CargoConfigCard.getConfig(meta);
            }
        }
        DataCache.setCustomData(data, "config", code);
    }

    public void newMenuInstance(BlockMenu menu, Block b) {
        menu.addMenuOpeningHandler(player -> {
            updateMenu(menu, b, Settings.RUN);
        });
        menu.addMenuCloseHandler(player -> {
            updateMenu(menu, b, Settings.RUN);
        });
        updateMenu(menu, b, Settings.INIT);
    }

    public void preRegister() {
        super.preRegister();
        registerTick(this);
        registerBlockMenu(this);
        handleBlock(this);
    }

    public void updateMenu(BlockMenu blockMenu, Block block, Settings mod) {
        loadConfig(blockMenu, block);
    }

    public void tick(Block b, @Nullable BlockMenu menu, SlimefunBlockData data, int tickCount) {
        if (menu.hasViewer()) {
            updateMenu(menu, b, Settings.RUN);
        }
        int configCode = getConfigCode(data);
        if (conditionHandle(b, menu) && configCode >= 0) {
            cargoTask(b, menu, data, configCode);
        }
    }

    public boolean conditionHandle(Block b, BlockMenu menu) {
        return true;
    }

    public void cargoTask(Block b, BlockMenu menu, SlimefunBlockData data, int configCode) {
        // doing nothing
    }

    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        MenuBlock.super.onBreak(e, menu);
        if (menu != null) {
            Location loc = menu.getLocation();
            menu.dropItems(loc, getConfigSlot());
            menu.dropItems(loc, getBWListSlot());
        }
    }
}
