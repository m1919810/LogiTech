package me.matl114.logitech.Utils.UtilClass.StorageClass;

import com.google.common.base.Preconditions;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.Manager.ScheduleSave;
import me.matl114.logitech.core.Cargo.StorageMachines.AbstractIOPort;
import me.matl114.logitech.core.Cargo.Storages;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.DataCache;
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
    //由何种存储解析器所解析
    protected StorageType storageType;
    //存储物品的物品源,记录一个CraftItemStack
    protected ItemStack source;
    //存储物品的物品meta 记录着含有存储数据的pdc的meta
    protected ItemMeta sourceMeta;
    //这个成员是记录该cache是否是长期cache
    //长期cache会被记录在Map中,并且代表着一个AbstractIOPort
    //短期cache比如存储解析器产物
    protected boolean persistent=false;
    //这个成员是记录存储数目的
    protected int storageAmount;
    //这个成员是用来记录这个cache是否还可以使用的
    //当玩家尝试从menu里取出存储物品时 该物品将被标记为deprecated 之后的远程访问将不能访问该存储cache
    boolean deprecated = false;
    //
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
        //prevent dupe from last record,
        AbstractIOPort.setStorageAmount(loc,-1,false);
        synchronized(lock){
            return cacheMap.remove(loc);
        }
    }
    static {
        ScheduleSave.addFinalTask(
                ()->{
                    synchronized(lock){
                        for(Map.Entry<Location, ItemStorageCache> a:cacheMap.entrySet()){
                            BlockMenu menu= DataCache.getMenu(a.getKey());
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
        Preconditions.checkArgument( source!=null,"Item Storage cache source should not be null!");
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
    public void setDeprecated(boolean deprecated){
        this.deprecated=deprecated;
        this.dirty=true;
    }
    public boolean getDeprecated(){
        return this.deprecated;
    }


    /**
     * check if cache can continue bind on this item,or just a item change,
     * if continue bind, reset source to this item and return True,else return False
     * check if someOne tend to replace it with a similar one or add its amount or do sth else
     * @param item
     * @return
     */
    public boolean keepRelated(ItemStack item){
        if(item==null)return false;
        if(item.getAmount()!=1)return false;
        else if(CraftUtils.sameCraftItem(item,source)){
            return true;
        }
        //only check pdc, else ignored
        else if (sourceMeta.getPersistentDataContainer().equals(item.getItemMeta().getPersistentDataContainer())){
            source=item;
            this.dirty=true;
            return true;
        }else return false;
    }
    public void updateItemStack(){
        if(dirty) {
            if (wasNull) {
                if (getItem() != null) {
                    item = item.clone();
                    item.setAmount(1);
                    storageAmount = getAmount();
                    storageType.setStorage(sourceMeta, this.getItem());
                    wasNull = false;
                    itemChange();
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
        try{
            AbstractIOPort.setStorageAmount(loc,storageAmount,true);
        }catch (Throwable e){
            Debug.logger("存储cache与粘液机器不对应!位置[%s]疑似出现刷物行为,移除相应存储cache并抛出错误:".formatted(DataCache.locationToDisplayString(loc)));

            ItemStorageCache cache= removeCache(loc);
            cache.updateStorage();
            throw e;
        }
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
        //for saving item data ,not for updating Menu
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
            toNull();
        }
    }
    //修复了setFrom存储时覆写maxSize的问题
    public void setFrom(ItemCounter source){
        if(wasNull||(source!=null&&source.getItem()!=null)){
            fromSource(source,false);
        }
    }
}
