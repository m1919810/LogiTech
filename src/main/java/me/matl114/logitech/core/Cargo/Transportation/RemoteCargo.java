package me.matl114.logitech.core.Cargo.Transportation;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.core.Cargo.AbstractCargo;
import me.matl114.logitech.core.Cargo.Links.HyperLink;
import me.matl114.logitech.utils.*;
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
    protected final String[] savedKeys=new String[0];
    public String[] getSaveKeys(){
        return savedKeys;
    }
    public int[] getDirectionSlots(){
        return new int[0];
    }
    protected final ItemStack[] INFO_ITEM=new ItemStack[]{
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&b机制",
                    "&7在左侧配置源方方块超链接","&7在中间的槽位插入[%s]".formatted(Language.get("Items.CARGO_CONFIG.Name")),"&7在下方放入黑/白名单物品",
                    "&e机器将从源方方块向目标方块进行传输","&c警告:当你设置两方块相同时,请不要让他们操作同样的槽位,否则后果自负"),
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&b机制",
                    "&7在右侧配置目标方块超链接","&7在中间的槽位插入[%s]".formatted(Language.get("Items.CARGO_CONFIG.Name")),"&7在下方放入黑/白名单物品",
                    "&e机器将从源方方块向目标方块进行传输","&c警告:当你设置两方块相同时,请不要让他们操作同样的槽位,否则后果自负")
    };
    protected final int[] LOCATION_SLOT=new int[]{
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
    public RemoteCargo(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayList) {
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
                                "&7本机器可以选择任意方块参与传输",
                                "将绑定目标方块的%s插入指定槽位以选择参与的方块".formatted(Language.get("Items.HYPER_LINK.Name"))),null
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
    public void newMenuInstance(BlockMenu inv, Block b){
        //update old material to new material

        if(b.getType()!=Material.NOTE_BLOCK){
            b.setType(Material.NOTE_BLOCK);
        }
        inv.addMenuOpeningHandler((player -> {
            updateMenu(inv,b,Settings.RUN);
        }));
        inv.addMenuCloseHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });

        updateMenu(inv,b, Settings.INIT);
    }
    public Location getLocation(String saveKey,SlimefunBlockData data){
        return DataCache.getLocation(saveKey,data);
    }
    public void updateLocationSlot(String saveKey, BlockMenu blockMenu,int slot){
        ItemStack it=blockMenu.getItemInSlot(slot);
        if(it!=null){
            ItemMeta meta=it.getItemMeta();
            if(HyperLink.isLink(meta)){
                Location loc=HyperLink.getLink(meta);
                DataCache.setLocation(saveKey,blockMenu.getLocation(),loc);
                return;
            }
        }
        DataCache.setLocation(saveKey,blockMenu.getLocation(),null);
    }
    public void updateMenu(BlockMenu inv ,Block b,Settings mod){
        loadConfig(inv,b);
        updateLocationSlot("from_dir",inv,LOCATION_SLOT[0]);
        updateLocationSlot("to_dir",inv,LOCATION_SLOT[1]);
    }
    protected boolean transportSmarter=false;
    public void cargoTask(Block b, BlockMenu menu, SlimefunBlockData data, int configCode){
        //Location loc=menu.getLocation();
        Location from_dir=getLocation("from_dir",data);
        if(from_dir==null){
            return;
        }
        Location to_dir=getLocation("to_dir",data);
        if(to_dir==null){
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
        ContainerUtils.transferWithContainer(from_dir,to_dir,configCode,bwset,transportSmarter);
    }
    public void onBreak(BlockBreakEvent e, BlockMenu menu){
        super.onBreak(e, menu);
        if(menu!=null){
            Location loc=menu.getLocation();
            menu.dropItems(loc,LOCATION_SLOT);
        }
    }

}
