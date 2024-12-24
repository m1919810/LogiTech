package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.matl114.logitech.Utils.DataCache;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.annotation.ParametersAreNonnullByDefault;

public final class AddHandlers {
    public static void stopUseClick(PlayerRightClickEvent event) {
        event.cancel();
    }
    public static void stopAttackClick(PlayerRightClickEvent e) {
        e.cancel();
    }
    public static final ItemUseHandler stopPlacementHandler =AddHandlers::stopUseClick;
    public static final BlockUseHandler stopItemUseHandler =AddHandlers::stopUseClick;
    public static final ItemUseHandler stopAttackHandler =AddHandlers::stopAttackClick;
    public static final BlockPlaceHandler stopPlaceerHandler =new BlockPlaceHandler(false) {
        @ParametersAreNonnullByDefault
        public void onPlayerPlace(BlockPlaceEvent e) {
            e.setCancelled(true);
            DataCache.removeBlockData(e.getBlock().getLocation());
        }
    };
}
