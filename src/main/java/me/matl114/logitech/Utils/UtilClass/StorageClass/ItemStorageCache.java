package me.matl114.logitech.Utils.UtilClass.StorageClass;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.Schedule.ScheduleSave;
import me.matl114.logitech.Schedule.Schedules;
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
    protected BlockMenu blockMenu;
    protected boolean save = false;
    private static byte[] lock=new byte[0];
    public static final HashMap<Location, ItemStorageCache> cacheMap=new HashMap<>();
    public static void setCache(BlockMenu inv,ItemStorageCache cache) {
        synchronized(lock){
            cacheMap.put(inv.getLocation(),cache);
        }
        cache.blockMenu=inv;
    }
    //TODO 潜在的并发风险：？ 分别get出去操作
    //TODO 更多潜在风险 并行的修改
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
                            BlockMenu menu= a.getValue().getBlockMenu();
                            if(menu!=null){
                                a.getValue().setSave(true);
                                a.getValue().updateMenu(menu);
                            }
                        }
                    }
                }
        );
        ScheduleSave.addPeriodicTask(()->{
            synchronized(lock){
                for(Map.Entry<Location, ItemStorageCache> a:cacheMap.entrySet()){
                    BlockMenu menu= StorageCacheUtils.getMenu(a.getKey());
                    if (menu!=null){
                        a.getValue().setSave(true);
                        a.getValue().updateMenu(menu);
                    }
                }
            }
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
        ItemStack stored=type.getStorageContent(sourceMeta);
        if(stored==null){
            return null;
        }
        return createFromType(source,stored,sourceMeta,saveSlot,type);
    }
    private static ItemStorageCache createFromType(ItemStack source,ItemStack stored,ItemMeta sourceMeta,int saveSlot,StorageType type){
//        if(type instanceof LocationProxy lcp){
//            LocationStorageProxy proxy=LocationStorageProxy.get(lcp.getLocation(sourceMeta),lcp);
//            if(proxy!=null){
//                Debug.debug("not null proxy");
//                return proxy;
//            }
//        }
        ItemStorageCache tmp= new ItemStorageCache(stored,source,sourceMeta,saveSlot,type);

        tmp.setAmount(type.getStorageAmount(sourceMeta));
        tmp.storageAmount=tmp.getAmount();
        if(type instanceof LocationProxy lcp){
            tmp=LocationStorageProxy.createProxy(lcp.getLocation(sourceMeta),tmp,lcp);
        }
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
        assert source!=null;
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
    public BlockMenu getBlockMenu(){
        return this.blockMenu;
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
    public void setSave(boolean save){
        this.save=save;
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
                    //样板保证是一个，用storageAmount mook掉真实itemAmount
                    item.setAmount(1);
                    storageAmount = getAmount();
                    storageType.setStorage(sourceMeta, this.getItem());
                    wasNull = false;
                }
            }
            storageAmount = getAmount();
        }
    }
    public void updateStorage(){
        storageType.onStorageAmountWrite(sourceMeta,storageAmount);
        storageType.onStorageDisplayWrite(sourceMeta,storageAmount);
        source.setItemMeta(sourceMeta);
    }
    //TODO 是否可以设置异步设置lore？
    public void updateMenu(@Nonnull BlockMenu menu){
        synchronized (this){//防止保存线程和不同的代理带来并发错误
            if (getItem()!=null&&!getItem().getType().isAir()){
                updateItemStack();
                //make sync to source when needed, do not do when not needed
                if(menu.hasViewer()||!persistent||save){
                    updateStorage();
                }
            //不是persistent 将物品的clone进行替换// 和保存有关
            }
            if((menu.hasViewer()||!persistent||save)&&slot>=0){
                //not work?
                source= MenuUtils.syncSlot(menu,slot,source);
            }
            save=false;
            dirty=false;
        }
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
    public void setFrom(ItemCounter sourcet){
        super.setFrom(sourcet);
    }
}
