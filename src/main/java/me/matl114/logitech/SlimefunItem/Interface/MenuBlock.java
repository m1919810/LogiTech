package me.matl114.logitech.SlimefunItem.Interface;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.Utils.DataCache;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Consumer;

public interface MenuBlock extends InventoryBlock {
    public interface MenuNotAccessible {

    }
    /**
     * deal with menublock break(after unregister block)
     * @param e
     * @param menu
     */
    default void onBreak(BlockBreakEvent e, BlockMenu menu) {
        if(menu!=null){
            Location l = menu.getLocation();
            menu.dropItems(l, this.getInputSlots());
            menu.dropItems(l, this.getOutputSlots());
        }
    }

    /**
     * deal with menublock place(after register sf block)
     * @param e
     * @param b
     */
    default void onPlace(BlockPlaceEvent e, Block b) {

    }

    /**
     * call this method in constructor
     * @param machine
     */
    default void handleBlock(SlimefunItem machine){
        machine.addItemHandler(
                new BlockBreakHandler(false, false) {
                    @ParametersAreNonnullByDefault
                    public void onPlayerBreak(BlockBreakEvent e, ItemStack itemStack, List<ItemStack> list) {
                        BlockMenu menu = DataCache.getMenu(e.getBlock().getLocation());// BlockStorage.getInventory(e.getBlock());
                        MenuBlock.this.onBreak(e, menu);
                    }
                }, new BlockPlaceHandler(false) {
                    @ParametersAreNonnullByDefault
                    public void onPlayerPlace(BlockPlaceEvent e) {
                        MenuBlock.this.onPlace(e, e.getBlockPlaced());
                    }
                });

    }
    default void registerBlockMenu(SlimefunItem item){
        this.createPreset(item,item.getItemName(),this::constructMenu);
        //handle blockPlaceEvent
        handleBlock(item);
    }
    default boolean listenDoubleClick(){
        return false;
    }
    public static abstract class AdvancedBlockMenuPreset extends BlockMenuPreset{
        //双击 拖拽都无法监听！
        boolean doubleClick;
        public AdvancedBlockMenuPreset(String id,String name,boolean doubleClick){
            super(id,name);
            this.doubleClick=doubleClick;
        }
    }
    default void createPreset(final SlimefunItem item, String title, final Consumer<BlockMenuPreset> setup) {
        if(listenDoubleClick()){
            BlockMenuPreset var10001 = new AdvancedBlockMenuPreset(item.getId(), title,listenDoubleClick())  {
                public void init() {
                    setup.accept(this);
                }

                public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                    return MenuBlock.this.getSlotsAccessedByItemTransport(flow);
                }
                public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
                    return  MenuBlock.this.getSlotsAccessedByItemTransportPlus(menu,flow,item);
                }

                public boolean canOpen(Block b, Player p) {
                    if (p.hasPermission("slimefun.inventory.bypass")) {
                        return true;
                    } else {
                        return item.canUse(p, false) && Slimefun.getProtectionManager().hasPermission(p, b.getLocation(), Interaction.INTERACT_BLOCK);
                    }
                }
                public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block){
                    MenuBlock.this.newMenuInstance(blockMenu, block);
                }
            };
        }else {
            BlockMenuPreset var10001 = new BlockMenuPreset(item.getId(), title)  {
                public void init() {
                    setup.accept(this);
                }

                public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                    return MenuBlock.this.getSlotsAccessedByItemTransport(flow);
                }
                public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
                    return  MenuBlock.this.getSlotsAccessedByItemTransportPlus(menu,flow,item);
                }

                public boolean canOpen(Block b, Player p) {
                    if (p.hasPermission("slimefun.inventory.bypass")) {
                        return true;
                    } else {
                        return item.canUse(p, false) && Slimefun.getProtectionManager().hasPermission(p, b.getLocation(), Interaction.INTERACT_BLOCK);
                    }
                }
                public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block){
                    MenuBlock.this.newMenuInstance(blockMenu, block);
                }
            };
        }
    }

    default int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
        return flow == ItemTransportFlow.INSERT ? this.getInputSlots() : this.getOutputSlots();
    }

    default int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
        return this.getSlotsAccessedByItemTransport(flow);
    }
    /**
     * add different settings varies by location
     * @param blockMenu
     * @param block
     */
    default void newMenuInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block){

    }
    public void constructMenu(BlockMenuPreset preset);
}
