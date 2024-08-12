package me.matl114.logitech.SlimefunItem.Blocks;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import me.matl114.logitech.Listeners.Listeners.BlockMenuRedirect;
import me.matl114.logitech.SlimefunItem.Machines.MenuBlock;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

public interface MultiBlockPart extends MenuBlock {
    public String getPartId();
    default boolean redirectMenu(){
        return true;
    }
    //you must add handle menu to preRegister in order to handle MultiBlockPart
    default void onBreak(BlockBreakEvent e, BlockMenu menu) {
        String uid= MultiBlockService.safeGetUUID(e.getBlock().getLocation());
        MultiBlockService.deleteMultiBlock(uid);
        MenuBlock.super.onBreak(e,menu);
    }
    default void onMenuRedirect(PlayerRightClickEvent event) {
        boolean itemUsed = event.getHand() == EquipmentSlot.OFF_HAND;


        if (!itemUsed && event.useBlock() != Event.Result.DENY && !BlockMenuRedirect.rightClickBlock(event)) {
            return;
        }
    }
}
