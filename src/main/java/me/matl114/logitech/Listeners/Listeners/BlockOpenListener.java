package me.matl114.logitech.Listeners.Listeners;

import com.xzavier0722.mc.plugin.slimefun4.storage.callback.IAsyncReadCallback;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore.MultiBlockPart;
import me.matl114.logitech.SlimefunItem.Interface.MenuBlock;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class BlockOpenListener implements Listener {
    @EventHandler

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
                    if(!MyAddon.testmode())
                        event.getInteractEvent().setCancelled(true);
                    Optional<Block> block=event.getClickedBlock();
                    if(block.isPresent()){
                        Location location=event.getClickedBlock().get().getLocation();
                        String uid= MultiBlockService.safeGetUUID(location);
                        Location redirect=MultiBlockService.getCore(uid);
                        if(redirect != null){

                            openInventory(event.getPlayer(), sfItem,redirect.getBlock(), event);
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

        return true;
    }
    @ParametersAreNonnullByDefault
    private static void openInventory(Player p, SlimefunItem item, Block clickedBlock, PlayerRightClickEvent event) {
        try {
            if (!p.isSneaking() || event.getItem().getType() == Material.AIR) {
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
