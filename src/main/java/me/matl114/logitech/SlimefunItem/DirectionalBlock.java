package me.matl114.logitech.SlimefunItem;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface DirectionalBlock {
    static ItemStack[] DIRECTION_ITEM=new ItemStack[]{
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 无"),
            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向北")),
            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向西")),
            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向南")),
            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向东")),
            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向上")),
            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向下"))
    };
    String[] getSaveKeys();
    int[] getDirectionSlots();
    default ChestMenu.MenuClickHandler getDirectionHandler(String saveKey, BlockMenu blockMenu){
        return ((player, i, itemStack, clickAction) -> {
            SlimefunBlockData data= DataCache.safeLoadBlock(blockMenu.getLocation());
            if(data!=null){
                int direction=DataCache.getCustomData(data,saveKey,0);
                Directions dir=Directions.fromInt(direction);
                int next=dir.getNext().toInt();
                blockMenu.replaceExistingItem(i,DirectionalBlock.DIRECTION_ITEM[next]);
                DataCache.setCustomData(data,saveKey,next);
            }
            return false;
        });
    }

    /**
     * used in cargoTask
     * @param saveKey
     * @param data
     * @return
     */
    default Directions getDirection(String saveKey,SlimefunBlockData data){
        int direction=DataCache.getCustomData(data,saveKey,0);
        return Directions.fromInt(direction);
    }
    default void setDirection(String saveKey,SlimefunBlockData data,Directions dir){

    }
    default void updateDirectionSlot(String saveKey, BlockMenu blockMenu,int slot){
        SlimefunBlockData data=DataCache.safeLoadBlock(blockMenu.getLocation());
        if(data!=null){
            int direction=DataCache.getCustomData(data,saveKey,0);
            int dir=Directions.fromInt(direction).toInt();
            blockMenu.replaceExistingItem(slot,DirectionalBlock.DIRECTION_ITEM[dir]);
        }
    }
}
