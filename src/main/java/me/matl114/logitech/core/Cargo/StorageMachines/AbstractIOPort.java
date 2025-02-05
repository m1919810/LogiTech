package me.matl114.logitech.core.Cargo.StorageMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Language;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.StorageClass.ItemStorageCache;

import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.core.Registries.FinalFeature;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

//TODO 实现 代理+货运
//TODO 检查均衡性是否出问题
public abstract class AbstractIOPort extends AbstractMachine {
    public AbstractIOPort(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(category, item, recipeType, recipe,0,0);
        this.setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow(
                                "&7机制 - &e存储性方块",
                                "&7该方块为存储性方块",
                                "提供一个特殊的存储槽,用于承载和交互存储类物品",
                                "&7本交互接口支持的存储类物品:",
                                "&7- 逻辑工艺 概念奇点",
                                "&7- 网络(拓展) 量子存储系列",
                                "&7作为存储性方块,该方块可以被[%s]绑定".formatted(Language.get("Cargo.QUANTUM_LINK.Name"))

                        )   ,null,
                        AddUtils.getInfoShow(
                                "&7机制 - &e交互存储",
                                "&7该方块可以用于设置存储类物品的种类",
                                "&7将空的&c存储类物品&7置于其中指定存储槽中",
                                "&7在输入槽内放入物品即可设置存储种类",
                                "&7当存储槽中的存储物品已设置物品种类,",
                                "&7该机器会尝试将输入槽中的物品输入存储,",
                                "&7该机器会尝试将物品从存储输出至输出槽",
                                "&7注明: &e网络量子系列不支持空存储设置存储种类"
                        )
             )
        );
    }
    public static final String ITEM_DISPLAY_INFO_1="&8奇点中存储的物品样式";
    public static final ItemStack ITEM_DISPLAY_NULL=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c未检测到物品存储!");
    protected static final ItemStack INFO_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6将奇点置于下方","&a机器将自动检测奇点内容并运行");
    public abstract void addInfo(ItemStack item);
    public abstract void constructMenu(BlockMenuPreset preset);
    public abstract int[] getInputSlots();
    public abstract int[] getOutputSlots();
    public abstract int getStorageSlot();
    public abstract int getDisplaySlot();
    public void updateMenu(BlockMenu inv, Block b, Settings mod){
        Location loc = inv.getLocation();
        ItemStorageCache cache= ItemStorageCache.getCache(loc);
        if(cache!=null){
            //保证newInstance的时候也会call到该方法
                cache.setPersistent(false);
                cache.updateMenu(inv);
                cache.setPersistent(true);
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
        inv.addMenuClickHandler(getStorageSlot(),((player, i, itemStack, clickAction) -> {
            ItemStorageCache cache= ItemStorageCache.getCache(inv.getLocation());
            if(itemStack==null||itemStack.getAmount()!=1){
                if(cache!=null){
                    AddUtils.sendMessage(player,"&c存储物品与缓存并不同步,请稍后再试");
                    return false;
                }else return true;
            }else{
                //当有人企图从中间取出物品时
                if(cache!=null){
                    //FIXME check thoroughly
                    if(cache.keepRelated(itemStack)){
                        //ItemStorageCache.removeCache(inv.getLocation());
                        cache.setPersistent(false);
                        cache.updateMenu(inv);
                        cache.setPersistent(true);
                        cache.setDeprecated(true);
                        inv.replaceExistingItem(getDisplaySlot(),ITEM_DISPLAY_NULL );
                        return true;
                    }else {
                        AddUtils.sendMessage(player,"&c存储物品与缓存并不同步,请稍后再试");
                        return false;
                    }
                }else{
                    return true;
                }
            }
        }));
        //清除可能的残留
        ItemStack stack=inv.getItemInSlot(getStorageSlot());
        if(stack==null||stack.getAmount()!=1){
            ItemStorageCache.removeCache(inv.getLocation());
        }else{
            int history=getStorageAmount(inv.getLocation());
            if(history>=0){
                ItemStorageCache cache=ItemStorageCache.getOrCreate(stack,stack.getItemMeta(),getStorageSlot(),i->!i.isStorageProxy());
                if(cache==null){
                    if(getDisplaySlot()>=0){
                        inv.replaceExistingItem(getDisplaySlot(),ITEM_DISPLAY_NULL );
                    }
                    ItemStorageCache.removeCache(inv.getLocation());
                }
                else {//重启之时
                    cache.setSaveSlot(getStorageSlot());
                    //载入history
                    cache.setAmount(history);
                    //设置为长期存在的 不会实时clone保存
                    cache.updateMenu(inv);
                    cache.setPersistent(true);
                    ItemStorageCache.setCache(inv, cache);
                }
            }else{
                ItemStorageCache.removeCache(inv.getLocation());
            }
        }

    }
    public boolean useAdvancedMenu(){
        return true;
    }

    @Override
    public void listenDragClick(BlockMenu inv,InventoryDragEvent e) {
        if(e.getRawSlots().contains(getStorageSlot())){
            e.setCancelled(true);
        }
    }
    @Override
    public void listenOriginClick(BlockMenu inv, InventoryClickEvent e) {
        super.listenOriginClick(inv, e);
        if(e.isCancelled()){return;}
        if(e.getClick()== ClickType.SHIFT_LEFT||e.getClick()== ClickType.SHIFT_RIGHT){
            if(e.getRawSlot()>=inv.getInventory().getSize()){
                ItemStack stack=e.getCurrentItem();
                ItemStack storage=inv.getItemInSlot(getStorageSlot());
                if(storage!=null&&stack!=null&&CraftUtils.matchItemStack(stack,storage,false)){
                    e.setCancelled(true);
                }
            }
        }
    }

    public static int getStorageAmount(Location loc){
        return DataCache.getCustomData(loc,"amt",-1);
    }
    public static void setStorageAmount(Location loc,int t,boolean withException){
        SlimefunBlockData data=DataCache.safeGetBlockCacheWithLoad(loc);
        if(data!=null&&SlimefunItem.getById(data.getSfId()) instanceof AbstractIOPort){
            setAmount(data,t);

        }else {
            if(withException)
                throw new IllegalArgumentException("该位置的粘液数据不是AbstractIOPort!");
        }
    }
    protected static void setAmount(SlimefunBlockData data,int t){
        DataCache.setCustomData(data,"amt",t);
    }

    public void process(Block b, BlockMenu menu, SlimefunBlockData data){
        //先确认存储cache
        ItemStack stack=menu.getItemInSlot(getStorageSlot());
        Location loc = menu.getLocation();
        //如果没有放存储 则立刻就要清除当前cache
        if(stack == null||stack.getAmount()!=1){
            //FIXME 问题 很大
            ItemStorageCache cache= ItemStorageCache.removeCache(loc);
            if(cache!=null&&!cache.getDeprecated()){
                //同步 如果他真的被拿走了 但是一般来说这是不可能的事情
                cache.setPersistent(false);
                cache.updateMenu(menu);

                cache.setDeprecated(true);

            }
            //删除cache后 物品record变为-1个
            //在remove中已经有操作了，删除
            //setAmount(data,-1);

            if(menu.hasViewer()&&getDisplaySlot()>=0){
                menu.replaceExistingItem(getDisplaySlot(),ITEM_DISPLAY_NULL );
            }
            return;
        }
        //现在 有存储在的 尝试找到cache
        ItemStorageCache cache= ItemStorageCache.getCache(loc);
        //cache不存在或者    cache和现在的奇点记录的不同 记录不同只有可能是玩家替换,这时候是因为
        if(cache == null||cache.getDeprecated()||(!cache.keepRelated(stack))){
            //对不上了自然要丢掉废弃的
            if(cache!=null){
                //这里是否要刷新？ 应该不用
                //这里的情况是 存在一个cache 在某两个tick之间 玩家切换了存储为另一个物品，导致cache被标记为deprecated 或者是产生了奇怪的updateMenu使得cache与stack不同步了
                //不管怎么样 在链接断开的时候 已经进行了同步了
                //这里我们只需要执行移除就行
                ItemStorageCache.removeCache(loc);
            }
            //尝试载入新的cache 可能是空的
            //don't support proxy ,that's a nightmare
            cache=ItemStorageCache.getOrCreate(stack,stack.getItemMeta(),getStorageSlot(),i->!i.isStorageProxy());
            if(cache==null){
                if(menu.hasViewer()&&getDisplaySlot()>=0){
                    menu.replaceExistingItem(getDisplaySlot(),ITEM_DISPLAY_NULL );
                }
                return;
            }
            else {
                cache.setSaveSlot(getStorageSlot());
                //只有重启的时候需要使用history 其他时间物品都是同步的
                //设置为长期存在的 不会实时clone保存
                cache.updateMenu(menu);
                cache.setPersistent(true);
                ItemStorageCache.setCache(menu, cache);
            }
        }
        //执行货运
        ItemPusher[] cachelst=new ItemPusher[]{cache};
        //FIXME 考虑是否将这个改为locationProxy? 顺便兼容更多的机器
        TransportUtils.cacheTransportation(menu,cachelst,menu,getInputSlots(), Settings.INPUT);
        TransportUtils.cacheTransportation(menu,cachelst,menu,getOutputSlots(), Settings.OUTPUT);
        cache.updateMenu(menu);
        //例行刷新
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
    public void registerTick(SlimefunItem that){
        //make IOPort operation also sync
        that.addItemHandler(FinalFeature.FINAL_SYNC_TICKER);
    }
}
