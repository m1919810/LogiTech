package me.matl114.logitech.Listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.matl114.logitech.core.Blocks.MultiBlockCore.MultiBlockPart;
import me.matl114.logitech.core.Interface.MenuBlock;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class BlockOpenListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClick(PlayerRightClickEvent event){
        boolean itemUsed = event.getHand() == EquipmentSlot.OFF_HAND;
        if(event.getPlayer().isSneaking()){
            return ;
        }

        if (!itemUsed && event.useBlock() != Event.Result.DENY && !rightClickBlock(event)) {
            return;
        }
    }
    @ParametersAreNonnullByDefault
    public static boolean rightClickBlock(PlayerRightClickEvent event) {

        Optional<SlimefunItem> optional = event.getSlimefunBlock();

        if (optional.isPresent()) {
            SlimefunItem sfItem = optional.get();

            if (!sfItem.canUse(event.getPlayer(), true)) {
                event.getInteractEvent().setCancelled(true);
                return false;
            }
            if(sfItem instanceof MultiBlockPart){
                MultiBlockPart multiBlockPart = (MultiBlockPart) sfItem;
                if(multiBlockPart.redirectMenu()){
                    //如果是重定向菜单的方块 则阻拦掉event
                    Optional<Block> block=event.getClickedBlock();
                    if(block.isPresent()){
                        Location location=block.get().getLocation();
                        String uid= MultiBlockService.safeGetUUID(location);
                        Location redirect=MultiBlockService.getCore(uid);
                        if(redirect != null){

                            openInventory(event.getPlayer(), redirect.getBlock(), event);
                                return true;
                        }else {
                            AddUtils.sendMessage(event.getPlayer(), "&c该多方块部件并没有组成多方块机器!");
                        }
                    }
                }
            }
            if(sfItem instanceof MenuBlock.MenuNotAccessible){
                event.getInteractEvent().setCancelled(true);
            }

        }
        //of course ,for vanilla blocks ,we need extra listeners to handle multiblockbreak
        else{
            //for vanilla block in multiblock
            Optional<Block> block=event.getClickedBlock();
            if(block.isPresent()){
                Location location=block.get().getLocation();
                if(MultiBlockService.getStatus(location)!=0){
                    String uid= MultiBlockService.safeGetUUID(location);
                    Location redirect=MultiBlockService.getCore(uid);
                    if(redirect != null){
                        openInventory(event.getPlayer(),redirect.getBlock(), event);
                        return true;
                    }
                }
            }
        }

        return true;
    }
    @ParametersAreNonnullByDefault
    private static void openInventory(Player p,  Block clickedBlock, PlayerRightClickEvent event) {
        try {
            if (!p.isSneaking() || event.getItem().getType() .isAir()) {
                event.getInteractEvent().setCancelled(true);

                var blockData = DataCache.safeGetBlockCacheWithLoad(clickedBlock.getLocation());
                if (blockData == null) {
                    return;
                }
                DataCache.runAfterSafeLoad(clickedBlock.getLocation(),(data)->{
                    if (!p.isOnline()) {
                        return;
                    }
                    openMenu(data.getBlockMenu(), clickedBlock, p);
                },true);
            }
        } catch (Exception | LinkageError x) {
            SlimefunItem item=DataCache.getSfItem(clickedBlock.getLocation());
           item.error("An Exception was caught while trying to open the Inventory", x);
        }
    }
    private static void openMenu(BlockMenu menu, Block b, Player p) {
        if (menu != null) {
            if (menu.canOpen(b, p)) {
                menu.open(p);
            } else {
                Slimefun.getLocalization().sendMessage(p, "inventory.no-access", true);
            }
        }
    }
}
