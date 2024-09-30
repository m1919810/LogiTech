package me.matl114.logitech.Utils.UtilClass.StorageClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.Schedule.ScheduleSave;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Cargo.StorageMachines.AbstractIOPort;
import me.matl114.logitech.SlimefunItem.Cargo.Storages;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MenuUtils;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemSlotPusher;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * when you load a storage cache from menu slot......
 * automatically deal with menu update ,call updateMenu at persistent=false mod to toggle save
 */
public class ItemStorageCache extends ItemSlotPusher {//extends ItemPusher
    /**
     * should be the reference to the exact slotItem in menu
     */
    protected StorageType storageType;
    protected ItemStack source;
    protected ItemMeta sourceMeta;
    protected boolean persistent=false;
    protected int storageAmount;
    private static byte[] lock=new byte[0];
    public static final HashMap<Location, ItemStorageCache> cacheMap=new HashMap<>();
    public static void setCache(BlockMenu inv,ItemStorageCache cache) {
        synchronized(lock){
            cacheMap.put(inv.getLocation(),cache);
        }
    }
    public static ItemStorageCache getCache(Location loc) {
        synchronized(lock){
            return cacheMap.get(loc);
        }
    }
    public static ItemStorageCache removeCache(Location loc) {
        synchronized(lock){
            return cacheMap.remove(loc);
        }
    }
    static {
        ScheduleSave.addFinalTask(
                ()->{
                    synchronized(lock){
                        for(Map.Entry<Location, ItemStorageCache> a:cacheMap.entrySet()){
                            BlockMenu menu= StorageCacheUtils.getMenu(a.getKey());
                            if(menu!=null){
                                a.getValue().setPersistent(false);
                                a.getValue().updateMenu(menu);
                                a.getValue().setPersistent(true);
                            }
                        }
                    }
                }
        );
        ScheduleSave.addPeriodicTask(()->{
//            synchronized(lock){
//                for(Map.Entry<Location, ItemStorageCache> a:cacheMap.entrySet()){
//                    BlockMenu menu= StorageCacheUtils.getMenu(a.getKey());
//                    if (menu!=null){
//                        a.getValue().setPersistent(false);
//                        a.getValue().updateMenu(menu);
//                        a.getValue().setPersistent(true);
//                    }
//                }
//            }
        });
        Storages.setup();
    }

    /**
     * will try get ,if null, create a possible one or failed return null
     * if type not specific, auto search
     * @param source
     * @param sourceMeta
     * @param saveSlot
     * @return
     */
    //FIXME 尝试把lore修改变成尝试lore修改 并且放入异步
    //首先要保证数据安全!
    public static ItemStorageCache getOrCreate(ItemStack source, ItemMeta sourceMeta, int saveSlot){
        return getOrCreate(source, sourceMeta, saveSlot,i->true);
    }
    public static ItemStorageCache getOrCreate(ItemStack source, ItemMeta sourceMeta, int saveSlot, Predicate<StorageType> filter){
        ItemStorageCache getCache=get(source,sourceMeta,saveSlot,filter);
        if(getCache!=null){
            return getCache;
        }else {
            String id=CraftUtils.parseSfId(sourceMeta);
            SlimefunItem it=id==null?null:SlimefunItem.getById(id);
            StorageType  type=StorageType.getPossibleStorageType(it,filter);
            if(type==null){
                return null;
            }else return new ItemStorageCache(source,saveSlot,type);
        }
    }
    public static ItemStorageCache getOrCreate(ItemStack source, ItemMeta sourceMeta, int saveSlot,StorageType type) {
        ItemStorageCache getCache=get(source,sourceMeta,saveSlot,type);
        if(getCache!=null){
            return getCache;
        }
        else {
            if(!type.canStorage(sourceMeta)){
                return null;
            }
            return new ItemStorageCache(source,saveSlot,type);
        }
    }

    /**
     * will search storage content already have
     * if type not specific, auto search
     * @param source
     * @param sourceMeta
     * @param saveSlot
     * @return
     */
    public static ItemStorageCache get(ItemStack source, ItemMeta sourceMeta, int saveSlot){
        return get(source, sourceMeta, saveSlot,i->true );
    }
    public static ItemStorageCache get(ItemStack source, ItemMeta sourceMeta, int saveSlot, Predicate<StorageType> filter){
        StorageType type=StorageType.getStorageType(sourceMeta,filter);
        if(type==null){return null;}
        return getWithoutCheck(source,sourceMeta,saveSlot,type);
    }
    public static ItemStorageCache get(ItemStack source, ItemMeta sourceMeta, int saveSlot,@Nonnull StorageType type) {
        if(!type.isStorage(sourceMeta)){
            return null;
        }
        return getWithoutCheck(source, sourceMeta, saveSlot, type);
    }
    public static ItemStorageCache getWithoutCheck(ItemStack source, ItemMeta sourceMeta, int saveSlot,@Nonnull StorageType type) {
        if(type instanceof LocationProxy lp){
            Location loc=lp.getLocation(sourceMeta);
            ItemStack stored=lp.getItemStack(loc);
            if(stored==null){
                return null;
            }
            ItemStorageCache cache=new LocationStorageProxy(stored,source,sourceMeta,saveSlot,type,loc);
            cache.setAmount(lp.getAmount(loc));
            cache.storageAmount=cache.getAmount();
            cache.dirty=false;
            return cache;
        }
        ItemStack stored=type.getStorageContent(sourceMeta);
        if(stored==null){
            return null;
        }
        ItemStorageCache tmp= new ItemStorageCache(stored,source,sourceMeta,saveSlot,type);
        tmp.setAmount(type.getStorageAmount(sourceMeta));
        tmp.storageAmount=tmp.getAmount();
        tmp.dirty=false;
        return tmp;
    }
    /**
     * construct common
     * @param item
     * @param source
     * @param sourceMeta
     * @param saveslot
     */
    protected ItemStorageCache(ItemStack item, ItemStack source, ItemMeta sourceMeta, int saveslot,StorageType type) {
        super(item,saveslot);
        assert source!=null;
        this.source = source;
        this.sourceMeta = sourceMeta;
        this.storageType=type;
        this.maxStackCnt=type.getStorageMaxSize(this.sourceMeta);
    }
    protected ItemStorageCache(ItemStack item, ItemStack source, int saveslot,StorageType type) {
        this(item,source,source.getItemMeta(),saveslot,type);
    }
    /**
     * construct when storage = null potential
     * @param source
     * @param slot
     * @param type
     */
    protected ItemStorageCache(ItemStack source, int slot,StorageType type) {
        super(slot);
        this.source = source;
        this.sourceMeta = source.getItemMeta();
        this.storageType=type;
        this.maxStackCnt=type.getStorageMaxSize(sourceMeta);
    }
    protected ItemStorageCache(){
        super(-1);
    }
    public int getStorageAmount(){
        return this.storageAmount;
    }

    /**
     * only const cache can set this true
     * @param persistent
     */
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
        this.dirty=true;
    }

    /**
     * set related slot num
     * @param slot
     */
    public void setSaveSlot(int slot){
        this.slot=slot;
    }


    /**
     * check if cache can continue bind on this item,or just a item change,
     * if continue bind, reset source to this item and return True,else return False
     * @param item
     * @return
     */
    public boolean keepRelated(ItemStack item){
        if(item==null)return false;
        else if(CraftUtils.sameCraftItem(item,source)){
            return true;
        }
        //only check pdc, else ignored
        else if (sourceMeta.getPersistentDataContainer().equals(item.getItemMeta().getPersistentDataContainer())){
            source=item;
            return true;
        }else return false;
    }
    public void updateItemStack(){
        if(dirty) {
            if (wasNull == true) {
                if (getItem() != null) {
                    item = item.clone();
                    item.setAmount(1);
                    storageAmount = getAmount();
                    storageType.setStorage(sourceMeta, this.getItem());
                    wasNull = false;
                }
            }
            storageAmount = getAmount();
        }
    }

    /**
     * save data to sfdata
     * @param loc
     */
    public void syncLocation(Location loc){
        AbstractIOPort.setStorageAmount(loc,storageAmount);
    }
    public void updateStorage(){
        storageType.onStorageAmountWrite(sourceMeta,storageAmount);
        storageType.onStorageDisplayWrite(sourceMeta,storageAmount);
        source.setItemMeta(sourceMeta);
    }
    public void updateMenu(@Nonnull BlockMenu menu){

        if (getItem()!=null&&!getItem().getType().isAir()){
            updateItemStack();
            if(persistent){
                syncLocation(menu.getLocation());
            }
            //make sync to source when needed, do not do when not needed
            if(menu.hasViewer()||!persistent){
                updateStorage();
            }
        //不是persistent 将物品的clone进行替换// 和保存有关
        }
        if((!persistent)&&slot>=0){
            //not work?
            //in case player take it away while working
            if(keepRelated(menu.getItemInSlot(slot)))
                source= MenuUtils.syncSlot(menu,slot,source);
        }
        dirty=false;

    }
    public void syncData(){
        if(!wasNull){
            if(dirty){
                cnt=storageAmount;
                dirty=false;
            }
        }else{
            item=null;
            meta=null;
            cnt=0;
            dirty=false;
        }
    }
    //修复了setFrom存储时覆写maxSize的问题
    public void setFrom(ItemCounter source){
        if(wasNull||(source!=null&&source.getItem()!=null)){
            item=source.getItem();
            cnt=0;
            meta=null;
        }
    }
}
