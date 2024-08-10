package me.matl114.logitech.Listeners.Listeners;

import com.xzavier0722.mc.plugin.slimefun4.storage.callback.IAsyncReadCallback;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.matl114.logitech.Listeners.CustomListener;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockPart;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class BlockMenuRedirect extends CustomListener {
    @EventHandler
    public void onRightClick(PlayerRightClickEvent event){
        boolean itemUsed = event.getHand() == EquipmentSlot.OFF_HAND;


        if (!itemUsed && event.useBlock() != Event.Result.DENY && !rightClickBlock(event)) {
            return;
        }
    }
    @ParametersAreNonnullByDefault
    private boolean rightClickBlock(PlayerRightClickEvent event) {
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
                    Optional<Block> block=event.getClickedBlock();
                    if(block.isPresent()){
                        Location location=event.getClickedBlock().get().getLocation();
                        String uid= DataCache.getLastUUID(location);
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
        }

        return true;
    }
    @ParametersAreNonnullByDefault
    private void openInventory(Player p, SlimefunItem item, Block clickedBlock, PlayerRightClickEvent event) {
        try {
            if (!p.isSneaking() || event.getItem().getType() == Material.AIR) {
                event.getInteractEvent().setCancelled(true);

                var blockData = StorageCacheUtils.getBlock(clickedBlock.getLocation());
                if (blockData == null) {
                    return;
                }

                if (blockData.isDataLoaded()) {
                    openMenu(blockData.getBlockMenu(), clickedBlock, p);
                } else {
                    Slimefun.getDatabaseManager()
                            .getBlockDataController()
                            .loadBlockDataAsync(blockData, new IAsyncReadCallback<>() {
                                @Override
                                public boolean runOnMainThread() {
                                    return true;
                                }

                                @Override
                                public void onResult(SlimefunBlockData result) {
                                    if (!p.isOnline()) {
                                        return;
                                    }

                                    openMenu(result.getBlockMenu(), clickedBlock, p);
                                }
                            });
                }
            }
        } catch (Exception | LinkageError x) {
            item.error("An Exception was caught while trying to open the Inventory", x);
        }
    }
    private void openMenu(BlockMenu menu, Block b, Player p) {
        if (menu != null) {
            if (menu.canOpen(b, p)) {
                menu.open(p);
            } else {
                Slimefun.getLocalization().sendMessage(p, "inventory.no-access", true);
            }
        }
    }
}
