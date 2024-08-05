package me.matl114.logitech.SlimefunItem.Storage;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.Schedule.ScheduleSave;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MenuUtils;
import me.matl114.logitech.Utils.UtilClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.ItemSlotPusher;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

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
    public static final HashMap<Location, ItemStorageCache> cacheMap=new HashMap<>();
    public static void setCache(Location loc, ItemStorageCache cache) {
        cacheMap.put(loc,cache);
    }
    public static ItemStorageCache getCache(Location loc) {
        return cacheMap.get(loc);
    }
    public static ItemStorageCache removeCache(Location loc) {
        return cacheMap.remove(loc);
    }
    static {
        ScheduleSave.addFinalTask(
                ()->{
                    for(Map.Entry<Location, ItemStorageCache> a:cacheMap.entrySet()){
                        BlockMenu menu= StorageCacheUtils.getMenu(a.getKey());
                        if (!menu.hasViewer()){
                            a.getValue().setPersistent(false);
                            a.getValue().updateMenu(menu);
                            a.getValue().setPersistent(true);
                        }
                    }
                }
        );
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
    public static ItemStorageCache getOrCreate(ItemStack source, ItemMeta sourceMeta, int saveSlot){
        return getOrCreate(source, sourceMeta, saveSlot,null);
    }
    public static ItemStorageCache getOrCreate(ItemStack source, ItemMeta sourceMeta, int saveSlot,StorageType type) {
        ItemStorageCache getCache=get(source,sourceMeta,saveSlot,type);
        if(getCache!=null){
            return getCache;
        }
        else {
            SlimefunItem it=SlimefunItem.getByItem(source);
            if(type==null){
                type=StorageType.getPossibleStorageType(it);
            }
            else if(!type.canStorage(sourceMeta)){
                return null;
            }
            if(type!=null) {
                ItemStorageCache tmp=new ItemStorageCache(source,saveSlot,type);
                return tmp;
            }else return null;
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
        return get(source, sourceMeta, saveSlot,null);
    }
    public static ItemStorageCache get(ItemStack source, ItemMeta sourceMeta, int saveSlot,StorageType type) {
        if(type==null){
            type=StorageType.getStorageType(sourceMeta);
            //Debug.logger("check type "+type);
        }
        else if(!type.isStorage(sourceMeta)){
            return null;
        }
        if(type!=null) {
            //has storage ,load storage
            ItemStack stored=type.getStorageContent(sourceMeta);
            ItemStorageCache tmp= new ItemStorageCache(stored,source,sourceMeta,saveSlot,type);
            tmp.setAmount(type.getStorageAmount(sourceMeta));
            tmp.storageAmount=tmp.getAmount();
            return tmp;
        }
        else return null;
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
        //only check pdc, else ignored
        else if (sourceMeta.getPersistentDataContainer().equals(item.getItemMeta().getPersistentDataContainer())){
            source=item;
            return true;
        }else return false;
    }

    protected void updateItemStack(){
        if(wasNull==true) {
            Debug.logger("was null");
            if(getItem()!=null){
                item=item.clone();
                //样板保证是一个，用storageAmount mook掉真实itemAmount
                item.setAmount(1);
                storageAmount=getAmount();
                storageType.setStorage(sourceMeta,this.getItem());

                wasNull=false;
            }
        }
        storageAmount=getAmount();

    }

    public void updateMenu(@Nonnull BlockMenu menu){
        long a=System.nanoTime();
        if (dirty&&getItem()!=null&&!getItem().getType().isAir()){

            updateItemStack();
            //make sync to source when needed, do not do when not needed
            if(menu.hasViewer()||!persistent){
                storageType.setStorageAmount(sourceMeta,storageAmount);
                storageType.updateStorageAmountDisplay(sourceMeta,this.getAmount());
                source.setItemMeta(sourceMeta);
            }

        //不是persistent 将物品的clone进行替换// 和保存有关
        }
        long b=System.nanoTime();
        if(!persistent&&slot>=0){
            //not work?
            source= MenuUtils.syncSlot(menu,slot,source);
        }
        long e=System.nanoTime();
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
