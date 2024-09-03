package me.matl114.logitech.SlimefunItem.Cargo.StorageMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.TransportUtils;
import me.matl114.logitech.Utils.UtilClass.StorageClass.ItemStorageCache;
import me.matl114.logitech.SlimefunItem.Cargo.Storages;
import me.matl114.logitech.Utils.AddUtils;

import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusher;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public abstract class AbstractIOPort extends AbstractMachine {
    public AbstractIOPort(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                            int energybuffer, int energyConsumption){
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
    }
    public static final String ITEM_DISPLAY_INFO_1="&8奇点中存储的物品样式";
    public static final ItemStack ITEM_DISPLAY_NULL=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c未检测到物品存储!");
    protected static final ItemStack INFO_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6将奇点置于下方","&a机器将自动检测奇点内容并运行");
    public abstract void addInfo(ItemStack item);
    public abstract void constructMenu(BlockMenuPreset preset);
    public abstract int[] getInputSlots();
    public abstract int[] getOutputSlots();
    public List<MachineRecipe> getMachineRecipes(){
        return null;
    }
    public abstract int getStorageSlot();
    public abstract int getDisplaySlot();
    public void updateMenu(BlockMenu inv, Block b, Settings mod){
        Location loc = inv.getLocation();
        ItemStorageCache cache= ItemStorageCache.getCache(loc);
        if(cache!=null){
            //更新lore不需要setPersistent 因为hasViewer就可以
            cache.updateMenu(inv);
            if(cache.getItem()!=null&&getDisplaySlot()>=0){
                ItemStack tmp=AddUtils.addLore(cache.getItem(),ITEM_DISPLAY_INFO_1);
                inv.replaceExistingItem(getDisplaySlot(),tmp );
            }
        }

    }
    public void newMenuInstance(BlockMenu inv,Block b){
        inv.addMenuOpeningHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });
        inv.addMenuCloseHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });

    }
    //TODO 修理更新不及时bug,增加兼容 增加存储兼容
    public void process(Block b, BlockMenu menu, SlimefunBlockData data){
        //先确认存储cache
        ItemStack stack=menu.getItemInSlot(getStorageSlot());
        Location loc = b.getLocation();
        //如果没有放存储 则立刻就要清除当前cache
        if(stack == null||stack.getAmount()!=1){
            ItemStorageCache cache= ItemStorageCache.removeCache(loc);
            if(cache!=null){
                //同步 如果他真的被拿走了 但是一般来说这是不可能的事情
                cache.updateItemStack();
                cache.updateStorage();
            }
            if(menu.hasViewer()&&getDisplaySlot()>=0){
                menu.replaceExistingItem(getDisplaySlot(),ITEM_DISPLAY_NULL );
            }
            return;
        }
        //现在 有存储在的 尝试找到cache
        ItemStorageCache cache= ItemStorageCache.getCache(loc);
        //cache不存在或者    cache和现在的奇点记录的不同 记录不同只有可能是玩家替换,这时候是因为
        if(cache == null||(menu.hasViewer()&&!cache.keepRelated(stack))){
            //对不上了自然要丢掉废弃的
            //FIXME ban remote bind,that's not persistent* yee we need a persistent() method

            ItemStorageCache.removeCache(loc);
            //TODO not support proxy yet
            cache=ItemStorageCache.getOrCreate(stack,stack.getItemMeta(),getStorageSlot(),i->!i.isStorageProxy());
            //cache=ItemStorageCache.getOrCreate(stack,stack.getItemMeta(),getStorageSlot(), Storages.SINGULARITY);
            if(cache==null){
                return;
            }
            else {
                cache.setSaveSlot(getStorageSlot());
                //设置为长期存在的 不会实时clone保存
                cache.updateMenu(menu);
                cache.setPersistent(true);
                ItemStorageCache.setCache(loc, cache);

            }
        }
        ItemPusher[] cachelst=new ItemPusher[]{cache};
        TransportUtils.cacheTransportation(menu,cachelst,menu,getInputSlots(), Settings.INPUT);
        TransportUtils.cacheTransportation(menu,cachelst,menu,getOutputSlots(), Settings.OUTPUT);
        cache.updateMenu(menu);
        if(menu.hasViewer()){
            updateMenu(menu,b,Settings.RUN);
        }
    }

    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        Location loc = menu.getLocation();
        ItemStorageCache cache= ItemStorageCache.removeCache(loc);
        if(cache!=null){
            cache.setPersistent(false);
            cache.updateMenu(menu);
        }
        menu.dropItems(loc,getStorageSlot());
        //
        super.onBreak(e, menu);
    }
}
