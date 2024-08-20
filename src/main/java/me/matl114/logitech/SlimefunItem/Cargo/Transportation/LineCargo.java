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
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.TransportUtils;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
//#TODO 继续写lineCargo并对RemoteCargo做性能测试
public class LineCargo extends AbstractCargo {
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
                    "&7在左侧配置源方方块邻接方向","&7在中间的槽位插入[%s]".formatted(Language.get("Items.CARGO_CONFIG.Name")),"&7在下方放入黑/白名单物品",
                    "&e机器将从源方方块向目标方块进行传输","&c警告:当你设置两方块相同时,请不要让他们操作同样的槽位,否则后果自负"),
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&b机制",
                    "&7在右侧配置目标方块邻接方向","&7在中间的槽位插入[%s]".formatted(Language.get("Items.CARGO_CONFIG.Name")),"&7在下方放入黑/白名单物品",
                    "&e机器将从源方方块向目标方块进行传输","&c警告:当你设置两方块相同时,请不要让他们操作同样的槽位,否则后果自负")
    };
    protected final int[] DIRECTION_SLOT=new int[]{
            10,16
    };
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
    public LineCargo(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayList) {
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
            updateMenu(inv,b, Settings.RUN);
        }));
        inv.addMenuCloseHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });
//        inv.addMenuClickHandler(DIRECTION_SLOT[0],getDirectionHandler("from_dir",inv));
//        inv.addMenuClickHandler(DIRECTION_SLOT[1],getDirectionHandler("to_dir",inv));
        updateMenu(inv,b, Settings.INIT);
    }
    public void updateMenu(BlockMenu inv ,Block b,Settings mod){
        loadConfig(inv,b);
        updateDirectionSlot("from_dir",inv,DIRECTION_SLOT[0]);
        updateDirectionSlot("to_dir",inv,DIRECTION_SLOT[1]);
    }
    public void cargoTask(Block b, BlockMenu menu, SlimefunBlockData data, int configCode){
        Location loc=menu.getLocation();
        Directions from_dir=getDirection("from_dir",data);
        BlockMenu from= StorageCacheUtils.getMenu(from_dir.relate(loc));
        if(from==null){
            return;
        }
        Directions to_dir=getDirection("to_dir",data);
        BlockMenu to= StorageCacheUtils.getMenu(to_dir.relate( loc));
        if(to==null){
            return;
        }
        int[] bwslots=getBWListSlot();
        HashSet<ItemStack> bwset=new HashSet<>();
        ItemStack it;
        for (int i=0;i<bwslots.length;++i){
            it=menu.getItemInSlot(bwslots[i]);
            if(it!=null){
                bwset.add(it);
            }
        }
        TransportUtils.transportItemGeneral(from,to,configCode,bwset);
    }
}
