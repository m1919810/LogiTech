package me.matl114.logitech.Utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.Getter;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.core.CustomSlimefunItem;
import me.matl114.logitech.Utils.UtilClass.CargoClass.ContainerBlockMenuWrapper;
import me.matl114.matlib.Utils.Algorithm.InitializeSafeProvider;
import me.matl114.matlib.Utils.Reflect.MethodAccess;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class ContainerUtils {
    public static void setup(){
        ContainerBlockMenuWrapper.setup();
    }
    private static int[][] preCalculatedSlots= IntStream.range(0,73).mapToObj(i->{
        if(i<9||i%9==0){
            return IntStream.range(0,i).toArray();
        }else return null;
    }).toArray(int[][]::new);
    private static int[] getSlotAccess(int size){
        if(preCalculatedSlots[size]==null){
            return IntStream.range(0,size).toArray();
        }else return preCalculatedSlots[size];
    }
    @Getter
    protected static BlockMenuPreset containerWrapperMenuPreset =new BlockMenuPreset("LOGITECH_FUNCTIONAL_BLOCKMENU","&c容器") {
        @Override
        public void init() {
            for(int i=0;i<54;++i){
                this.addMenuClickHandler(i, ChestMenuUtils.getEmptyClickHandler());
            }
            this.setSize(54);
        }
        @Override
        public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
            return false;
        }

        @Override
        public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
            return new int[0];
        }
        public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
            if(menu instanceof ContainerBlockMenuWrapper impl){
                return getSlotAccess(impl.getSlotSize());
            }else return getSlotsAccessedByItemTransport(flow);
        };
        public void newInstance(@Nonnull BlockMenu menu, @Nonnull Location l) {
            //do nothing
        }
    };
    /**
     * run callback on MainThread
     * processing Block Inventory in Async thread may trigger sync event which throws IllegalStateException
     * @param callback
     * @param testBlock
     */


    public static void getBlockContainerMenuWrapperWithCallback(Consumer<BlockMenu[]> callback,boolean runningOnMain, Location... testBlock){
        BlockMenu[] results=new BlockMenu[testBlock.length];
        BukkitUtils.executeSync(()->{
            for(int i=0;i<testBlock.length;++i){
                if(testBlock[i]!=null&&  WorldUtils.getBlockStateNoSnapShot(  testBlock[i].getBlock() )instanceof InventoryHolder ivHolder){
                    results[i]= ContainerBlockMenuWrapper.getContainerBlockMenu(ivHolder.getInventory(),testBlock[i]);
                }else{
                    results[i]=null;
                }
            }
            if(runningOnMain){
                //running on mainThread
                callback.accept(results);
            }else {
                //not running on mainThread
                CompletableFuture.supplyAsync(()->{
                    try{
                        callback.accept(results);
                    }catch (Throwable e){
                        Debug.logger(e);
                    }
                    return null;
                });
            }
        });
    }
    //todo add Material check
    //todo add InventoryType slotAccess
    public static void transferWithContainer(Location from, Location to, int configCode, HashSet<ItemStack> bwlist,boolean smart){
        BlockMenu fromInv=DataCache.getMenu(from);

        BlockMenu toInv=DataCache.getMenu(to);
        if(fromInv!=null&&toInv!=null){
            TransportUtils.transportItem(fromInv,toInv,configCode,smart,bwlist,CraftUtils.getpusher);
            return;
        }
        if(!from.getWorld().isChunkLoaded(from.getBlockX()>>4,from.getBlockZ()>>4)||!to.getWorld().isChunkLoaded(to.getBlockX()>>4,to.getBlockZ()>>4)){
            //stop transfering to vanilla container when one chunk is not loaded
            return;
        }
        SlimefunItem fromItem=DataCache.getSfItem(from);
        SlimefunItem toItem =DataCache.getSfItem(to);
        //null, or not my machine
        if((fromInv!=null&&(!(toItem instanceof CustomSlimefunItem)))||(toInv!=null&&(!(fromItem instanceof CustomSlimefunItem)) ) ) {
            Schedules.execute(()->{
                if(fromInv!=null){
                    ContainerUtils.getBlockContainerMenuWrapperWithCallback((blockMenus -> {
                        if(blockMenus[0]!=null){
                            TransportUtils.transportItem(fromInv,blockMenus[0],configCode,smart,bwlist,CraftUtils.getpusher);
                        }

                    }),true, to);
                }else {
                    if(toInv!=null){
                        ContainerUtils.getBlockContainerMenuWrapperWithCallback((blockMenus -> {
                            if(blockMenus[0]!=null)
                                TransportUtils.transportItem(blockMenus[0],toInv,configCode,smart,bwlist,CraftUtils.getpusher);
                        }),true, from);
                    }else {
                            ContainerUtils.getBlockContainerMenuWrapperWithCallback((blockMenus -> {
                                if(blockMenus[0]!=null&&blockMenus[1]!=null){
                                    TransportUtils.transportItem(blockMenus[0],blockMenus[1],configCode,smart,bwlist,CraftUtils.getpusher);
                                }
                            }),true, from,to);
                    }
                }
            },true);
        }
    }
    public static BlockMenu getPlayerBackPackWrapper(Player p){
        return ContainerBlockMenuWrapper.getContainerBlockMenu(p.getInventory(),p.getLocation(),36);
    }
    public static BlockMenu getPlayerInventoryWrapper(Player p){
        return ContainerBlockMenuWrapper.getContainerBlockMenu(p.getInventory(),p.getLocation());
    }
}
