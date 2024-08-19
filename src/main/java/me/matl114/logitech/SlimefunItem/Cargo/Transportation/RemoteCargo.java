package me.matl114.logitech.SlimefunItem.Cargo.Transportation;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.Cargo.AbstractCargo;
import me.matl114.logitech.SlimefunItem.Cargo.Links.HyperLink;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.TransportUtils;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.List;

public class RemoteCargo extends AbstractCargo {
    protected final int[] BORDER=new int[]{
            0,1,2,6,7,8,9,11,15,17,18,19,20,24,25,26
    };
    protected final int[] BWSLOT=new int[]{
            12,13,14,21,22,23
    };
    protected final int[] INFO_SLOT=new int[]{
            3,5
    };
    protected final ItemStack[] INFO_ITEM=new ItemStack[]{
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&b机制",
                    "&7在左侧配置链式方向","&7在中间的槽位插入[%s]".formatted(Language.get("Items.CARGO_CONFIG.Name")),"&7在下方放入黑/白名单物品",
                    "&e机器将进行链式传输","&c警告:只有连续方块长度长于1时才会进行传输"),
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&b机制",
                    "&7在右侧配置链式设定","&7在中间的槽位插入[%s]".formatted(Language.get("Items.CARGO_CONFIG.Name")),"&7在下方放入黑/白名单物品",
                    "&e机器将进行链式传输","&c警告:只有连续方块长度长于1时才会进行传输")
    };
    protected final int[] CONFIG_SLOT=new int[]{

    };
    protected final String[] CONFIG_KEYS=new String[]{

    };
    protected final ItemStack[] CONFIG_ITEM=new ItemStack[]{

    };
    protected final int DIRECTION_SLOT=10;

    public int[] getBWListSlot(){
        return BWSLOT;
    }
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public int getConfigSlot(){
        return 4;
    }
    public RemoteCargo(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayList) {
        super(itemGroup, item, recipeType, recipe, displayList);

    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=INFO_SLOT;
        len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(INFO_SLOT[i],INFO_ITEM[i],ChestMenuUtils.getEmptyClickHandler());
        }
    }
    public void newMenuInstance(BlockMenu inv, Block b){
        inv.addMenuOpeningHandler((player -> {
            updateMenu(inv,b,Settings.RUN);
        }));
        inv.addMenuCloseHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });

        updateMenu(inv,b, Settings.INIT);
    }
    public void updateConfigSlots(){

    }
    public void updateMenu(BlockMenu inv ,Block b,Settings mod){
        loadConfig(inv,b);
        updateDirectionSlot("line_dir",inv,DIRECTION_SLOT);
    }
    public void cargoTask(Block b, BlockMenu menu, SlimefunBlockData data, int configCode){

    }
//

}
