package me.matl114.logitech.SlimefunItem.Storage.StorageMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.SlimefunItem.Storage.ItemStorageCache;
import me.matl114.logitech.SlimefunItem.Storage.Storages;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;

import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.ItemPusher;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
    public void process(Block b, BlockMenu menu){
        //先确认存储cache
        long a=System.nanoTime();
        ItemStack stack=menu.getItemInSlot(getStorageSlot());
        Location loc = b.getLocation();
        //如果没有放奇点 则立刻就要清除当前cache
        if(stack == null||stack.getAmount()!=1){
            ItemStorageCache.removeCache(loc);
            if(menu.hasViewer()&&getDisplaySlot()>=0){
                menu.replaceExistingItem(getDisplaySlot(),ITEM_DISPLAY_NULL );
            }
            return;
        }
        //现在是有奇点在的 尝试
        ItemStorageCache cache= ItemStorageCache.getCache(loc);
        //cache不存在或者cache和现在的奇点记录的不同
        if(cache == null||!cache.keepRelated(stack)){
            //Debug.logger("[AbstractIOPort] create new cache");
            SlimefunItem type=SlimefunItem.getByItem(stack);
            cache=ItemStorageCache.getOrCreate(stack,stack.getItemMeta(),getStorageSlot(), Storages.SINGULARITY);
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
        //更新
        if(menu.hasViewer()&&getDisplaySlot()>=0){
            if(cache.getItem()!=null){
            ItemStack tmp=AddUtils.addLore(cache.getItem(),ITEM_DISPLAY_INFO_1);
            menu.replaceExistingItem(getDisplaySlot(),tmp );
            }
        }
        long e=System.nanoTime();
        //Debug.logger("sync success! time cost"+(e-a));
        //现在是100%同步的了,但是可能是null
        int[] slots=getInputSlots();
        int len=slots.length;
        ItemPusher tarCounter;
        ItemStack tar;
        for(int i=0;i<len;i++){
            tar=menu.getItemInSlot(slots[i]);
            //quick pass
            if(tar != null){
                tarCounter=CraftUtils.getPusher(tar);
                if(cache.getItem()==null){
                    cache.grab(tarCounter);
                    //立刻设置种类
                    cache.updateMenu(menu);
                    tarCounter.updateMenu(menu);
                }
                else if(CraftUtils.matchItemCounter(tarCounter,cache,true)){
                    cache.grab(tarCounter);
                    tarCounter.updateMenu(menu);
                }
            }
        }
        slots=getOutputSlots();
        len=slots.length;
        ItemPusher tarCounter2;
        for(int i=0;i<len;i++){
            tarCounter2=CraftUtils.getSlotPusher(menu,slots[i]);
            //quick pass
            if(tarCounter2.getItem()==null){
                tarCounter2.grab(cache);
                tarCounter2.updateMenu(menu);
            }
            else if(tarCounter2.getAmount()==tarCounter2.getMaxStackCnt())continue;
            else if(CraftUtils.matchItemCounter(tarCounter2,cache,true)){
                tarCounter2.grab(cache);
                tarCounter2.updateMenu(menu);
            }
        }
        long r=System.nanoTime();
        //Debug.logger("push and grab success time cost "+(r-e));
        cache.updateMenu(menu);
        long w=System.nanoTime();
        //Debug.logger("update cache success, time cost"+(w-r));

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
