package me.matl114.logitech.core.Interface;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.CargoClass.Directions;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface DirectionalBlock {
    static ItemStack[] DIRECTION_ITEM = new ItemStack[] {
        new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 无"),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向北")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向西")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向南")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向东")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向上")),
        AddUtils.addGlow(new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, "&6点击切换方向", "&3当前方向: 向下"))
    };
    // keep index num
    String[] getSaveKeys();
    // leave -1 in array if skip update
    int[] getDirectionSlots();

    default ChestMenu.MenuClickHandler getDirectionHandler(String saveKey, BlockMenu blockMenu) {
        return ((player, i, itemStack, clickAction) -> {
            SlimefunBlockData data = DataCache.safeLoadBlock(blockMenu.getLocation());
            if (data != null) {
                int direction = DataCache.getCustomData(data, saveKey, 0);
                Directions dir = Directions.fromInt(direction);
                int next = dir.getNext().toInt();
                blockMenu.replaceExistingItem(i, DirectionalBlock.DIRECTION_ITEM[next]);
                DataCache.setCustomData(data, saveKey, next);
            }
            return false;
        });
    }

    default ChestMenu.MenuClickHandler getDirectionHandler(int index, BlockMenu blockMenu) {
        return getDirectionHandler(getSaveKeys()[index], blockMenu);
    }

    /**
     * used in cargoTask
     * @param saveKey
     * @param data
     * @return
     */
    default Directions getDirection(String saveKey, SlimefunBlockData data) {
        int direction = DataCache.getCustomData(data, saveKey, 0);
        return Directions.fromInt(direction);
    }

    default Directions getDirection(int index, SlimefunBlockData data) {
        return getDirection(getSaveKeys()[index], data);
    }

    default void setDirection(String saveKey, SlimefunBlockData data, Directions dir) {
        DataCache.setCustomData(data, saveKey, dir.toInt());
    }

    default void updateDirectionSlots(int index, BlockMenu blockMenu) {
        updateDirectionSlots(getSaveKeys()[index], blockMenu, getDirectionSlots()[index]);
    }

    default void updateDirectionSlots(String saveKey, BlockMenu blockMenu, int slot) {
        SlimefunBlockData data = DataCache.safeLoadBlock(blockMenu.getLocation());
        if (data != null) {
            int direction = DataCache.getCustomData(data, saveKey, 0);
            int dir = Directions.fromInt(direction).toInt();
            blockMenu.replaceExistingItem(slot, DirectionalBlock.DIRECTION_ITEM[dir]);
        }
    }

    default void updateSlot(BlockMenu menu, int iSlot, Directions dir) {
        if (menu != null && iSlot >= 0) {
            menu.replaceExistingItem(iSlot, DirectionalBlock.DIRECTION_ITEM[dir.toInt()]);
        }
    }

    default boolean canModify() {
        return true;
    }
    // 返回拷贝的数量
    default int pasteDirectionSettings(SlimefunBlockData data, Directions[] dir) {
        if (this.canModify()) {
            String[] saveKeys = getSaveKeys();
            int[] slots = getDirectionSlots();
            int len = Math.min(saveKeys.length, dir.length);
            int slotlens = slots.length;
            BlockMenu menu = data.getBlockMenu();
            for (int i = 0; i < len; i++) {
                setDirection(saveKeys[i], data, dir[i]);
                if (i < slotlens) updateSlot(menu, slots[i], dir[i]);
            }
            return len;
        } else return -1;
    }

    default Directions[] copyDirectionSettings(SlimefunBlockData data) {
        if (this.canModify()) {
            String[] saveKeys = getSaveKeys();
            Directions[] dir = new Directions[saveKeys.length];
            for (int i = 0; i < saveKeys.length; i++) {
                dir[i] = getDirection(saveKeys[i], data);
            }
            return dir;
        } else return null;
    }
}
