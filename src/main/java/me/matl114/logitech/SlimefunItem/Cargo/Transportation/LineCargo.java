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
import me.matl114.logitech.SlimefunItem.Interface.DirectionalBlock;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
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
                    "&7在左侧配置链式方向","&7在中间的槽位插入[%s]".formatted(Language.get("Items.CARGO_CONFIG.Name")),"&7在下方放入黑/白名单物品",
                    "&e机器将进行链式传输","&c警告:只有连续方块长度长于1时才会进行传输"),
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&b机制",
                    "&7在右侧配置链式设定","&7在中间的槽位插入[%s]".formatted(Language.get("Items.CARGO_CONFIG.Name")),"&7在下方放入黑/白名单物品",
                    "&e机器将进行链式传输","&c警告:只有连续方块长度长于1时才会进行传输")
    };
    protected final int[] CONFIG_SLOT=new int[]{
        16
    };
    protected final String[] CONFIG_KEYS=new String[]{
        "loop"
    };
    protected final ItemStack[] CONFIG_ITEM=new ItemStack[]{
            new CustomItemStack(Material.BAMBOO_CHEST_RAFT,"&6点击切换是否首尾循环","&3当前设置: false"),
            AddUtils.addGlow(new CustomItemStack(Material.BAMBOO_CHEST_RAFT,"&6点击切换是否首尾循环","&3当前设置: true"))
    };
    protected final int[] CONFIG_VALUE=new int[]{
            0,2
    };
    protected final int DIRECTION_SLOT=10;
    protected final int[] DIRECTION_SLOTS=new int[]{
            DIRECTION_SLOT
    };
    public int[] getBWListSlot(){
        return BWSLOT;
    }
    protected final int MAX_LINE_LEN=64;
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
        setDisplayRecipes(
                Utils.list(AddUtils.getInfoShow("&f机制",
                                "&7这是一个普通货运机器",
                                "&7普通货运机器的行为会包括若干对源方块和目标方块",
                                "&7普通货运机器会进行从源方块到目标方块的物流传输",
                                "&7普通货运机器支持较大规模的传输量",
                                "&7但是相应的,他会忽视机器对物品的输入槽位限制"
                        ),null,
                        AddUtils.getInfoShow("&f机制",
                                "&7本机器可以选择一个方向进行直线搜素",
                                "&&搜索会在方块不存在方块菜单,或至最大搜索距离终止",
                                "&7搜索最大距离为64",
                                "&7在搜索链上的机器会向接下来的机器进行传输",
                                "&7开启首位循环使搜索链上尾部机器向首部机器传输"),null
                )
        );
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
    protected final String[] savedKeys=new String[]{
            "line_dir"
    };
    public String[] getSaveKeys(){
        return savedKeys;
    }
    public int[] getDirectionSlots(){
        return DIRECTION_SLOTS;
    }
    public void newMenuInstance(BlockMenu inv, Block b){
        inv.addMenuOpeningHandler((player -> {
            updateMenu(inv,b,Settings.RUN);
        }));
        inv.addMenuCloseHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });
        inv.addMenuClickHandler(DIRECTION_SLOT,getDirectionHandler(0,inv));
        int len=CONFIG_SLOT.length;
        for (int i=0;i<len;++i){
            inv.addMenuClickHandler(CONFIG_SLOT[i],getConfigHandlers(i,inv));
        }
        updateMenu(inv,b, Settings.INIT);
    }
    public void updateConfigSlots(BlockMenu inv ){
        SlimefunBlockData data= DataCache.safeLoadBlock(inv.getLocation());
        int len=CONFIG_SLOT.length;
        String key;
        int code;
        for (int i=0;i<len;++i){
            key=CONFIG_KEYS[i];
            code=DataCache.getCustomData(data,key,CONFIG_VALUE[i]);
            if(code>=CONFIG_VALUE[i+1]){
                code=CONFIG_VALUE[i+1]-1;
                DataCache.setCustomData(data,key,code);
            }
            inv.replaceExistingItem(CONFIG_SLOT[i],CONFIG_ITEM[code]);
        }
    }
    public ChestMenu.MenuClickHandler getConfigHandlers(int configIdx,BlockMenu inv){
        return ((player, i, itemStack, clickAction) -> {
            SlimefunBlockData data=DataCache.safeLoadBlock(inv.getLocation());
            if(data!=null){
                int index=configIdx;
                int code=DataCache.getCustomData(data,CONFIG_KEYS[index],CONFIG_VALUE[index]);
                code+=1;
                if(code>=CONFIG_VALUE[index+1]){
                    code=CONFIG_VALUE[index];
                }
                inv.replaceExistingItem(CONFIG_SLOT[index],CONFIG_ITEM[code]);
                DataCache.setCustomData(data,CONFIG_KEYS[index],code);
                DataCache.setCustomData(data,CONFIG_KEYS[index],code);

            }
            return false;
        });
    }
    public int getConfigValue(int configIdx,SlimefunBlockData data){
        return DataCache.getCustomData(data,CONFIG_KEYS[configIdx],CONFIG_VALUE[configIdx]);
    }
    public void updateMenu(BlockMenu inv ,Block b,Settings mod){
        loadConfig(inv,b);
        updateDirectionSlots(0,inv);
        updateConfigSlots(inv);
    }
    public void cargoTask(Block b, BlockMenu menu, SlimefunBlockData data, int configCode){
        Directions dir=getDirection(0,data);
        if(dir==Directions.NONE||dir==null)return;
        //非null 非空
        boolean loop =getConfigValue(0,data)==1;
        Location loc=dir.relate(menu.getLocation());
        BlockMenu inv=DataCache.getMenu(loc);
        if(inv!=null){
            HashSet<ItemStack> bwset=new HashSet<>();
            ItemStack it;
            int[] bwslots=getBWListSlot();
            for (int i=0;i<bwslots.length;++i){
                it=menu.getItemInSlot(bwslots[i]);
                if(it!=null){
                    bwset.add(it);
                }
            }
            BlockMenu first=inv;
            BlockMenu next=inv;
            BlockMenu nextTo;
            int limit=MAX_LINE_LEN;
            while(limit>0){
                loc=dir.relate(loc);
                nextTo=DataCache.getMenu(loc);
                if(nextTo==null){
                    break;
                }
                TransportUtils.transportItemGeneral(next,nextTo,configCode,bwset);
                next=nextTo;
                --limit;
            }
            if(loop&&limit!=MAX_LINE_LEN){
                TransportUtils.transportItemGeneral(next,first,configCode,bwset);
            }
        }

    }
}
