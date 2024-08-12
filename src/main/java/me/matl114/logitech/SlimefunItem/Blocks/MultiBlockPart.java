package me.matl114.logitech.SlimefunItem.Blocks;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import me.matl114.logitech.Listeners.Listeners.BlockMenuRedirect;
import me.matl114.logitech.SlimefunItem.Machines.MenuBlock;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public interface MultiBlockPart {
    public String getPartId();
    default boolean redirectMenu(){
        return true;
    }
    //you must add handle menu to preRegister in order to handle MultiBlockPart
    default void onMultiBlockBreak(BlockBreakEvent e) {
        String uid= MultiBlockService.safeGetUUID(e.getBlock().getLocation());
        MultiBlockService.deleteMultiBlock(uid);
    }
    default void handleMultiBlockPart(SlimefunItem it){
        it.addItemHandler(new BlockBreakHandler(false, false) {
            @ParametersAreNonnullByDefault
            public void onPlayerBreak(BlockBreakEvent e, ItemStack itemStack, List<ItemStack> list) {
                MultiBlockPart.this.onMultiBlockBreak(e);
            }
        });
    }
    default void onMenuRedirect(PlayerRightClickEvent event) {
        boolean itemUsed = event.getHand() == EquipmentSlot.OFF_HAND;


        if (!itemUsed && event.useBlock() != Event.Result.DENY && !BlockMenuRedirect.rightClickBlock(event)) {
            return;
        }
    }
}
