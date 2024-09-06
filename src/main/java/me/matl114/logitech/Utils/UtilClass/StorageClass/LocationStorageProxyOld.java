package me.matl114.logitech.Utils.UtilClass.StorageClass;

import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class LocationStorageProxyOld extends ItemStorageCache {

//    ItemStorageCache handle;
//    Location source;
//    LocationProxy type;
//    //if data locked ,if locked,no modification here
//    //does it work?
//    //错误的 这个方法并不可行
//    //主要原因是我进行代理的时候会创建两个以上的对象
//    //他们并不互通，也不知道对方什么时候结束代理
//    //所以
//    //不如就创建一个对象 其他的只会获得其引用！
//    boolean lock=false;
//
////    public static LocationStorageProxy get(Location loc,LocationProxy type) {
////        LocationStorageProxy proxy = getProxy(loc);
////        //不存在历史记录
////        if(proxy==null)return null;
////        //check if absent
////        if(proxy.type==type&&proxy.handle.getStorageAmount()==type.getAmount(loc)&&proxy.handle.getMaxStackCnt()==type.getMaxAmount(loc)&&
////                CraftUtils.matchItemStack(type.getItemStack(loc),proxy.handle,true)){
////            Debug.debug("get a history");
////            Debug.debug("loc="+proxy.source);
////            Debug.debug("type="+proxy.handle.getItem());
////            Debug.debug("storage="+proxy.handle.getStorageAmount());
////            Debug.debug("simulation="+proxy.handle.getAmount());
////
////
////            //如果**存储**的数量和类型都与proxy记录相符 则使用历史对象
////            //因为按理说这玩意修改存储数量的时候会一并修改掉对应proxy的对象
////            return proxy;
////        }else{
////            Debug.debug(proxy.handle.getStorageAmount(),type.getAmount(loc),proxy.handle.getMaxStackCnt(),type.getMaxAmount(loc),CraftUtils.matchItemStack(type.getItemStack(loc),proxy.handle,true)?1:0);
////            //如果已经不对了 那就gg 禁止proxy的一切操作 并删除记录
////            removeProxy(loc);
////            Debug.debug("locked");
////            proxy.lock=true;
////            return null;
////        }
////    }
//    public static LocationStorageProxyOld createProxy(Location loc,ItemStorageCache cache,LocationProxy type) {
//          return new LocationStorageProxyOld(loc,cache,type);
//    }
//
//    private LocationStorageProxyOld(Location source, ItemStorageCache cache,LocationProxy type){
//        super();
//       this. handle = cache;
//       this. source = source;
//       this.type = type;
//    }
//    public boolean isDirty(){
//        return lock||dirty;
//    }
//    public Location geProxytLocation(){
//        return source;
//    }
//    public boolean proxyAccessible(){
////        if(lock)return false;
////        //查询当前位置是否有人访问
////        LocationStorageProxy proxy=getProxy(source);
////        //如果历史访问记录是自己或者是null则成功，并且加入自己
////        if(proxy==this)return true;
////        else if(proxy==null){
////            setProxy(source,this);
////            return true;
////        }
////        //直接查看dirty值 因为我们要覆盖dirty
////        //
////        else if(proxy.isDirty()){
////            lock=true;
////            return false;
////        }else{
////            //当前还没进行访问申请，被刷新下来
////            setProxy(source,this);
////            return true;
////        }
//        return !lock;
//    }
//    public void updateStorage(){
//        type.setAmount(source,handle.getStorageAmount());
//        type.updateLocation(source);
//    }
//    public void updateMenu(@Nonnull BlockMenu menu){
//        if (getItem()!=null&&!getItem().getType().isAir()){
//            updateItemStack();
//            updateStorage();
//        }
//        dirty=false;
//    }
//    @Override
//    public void updateItemStack() {
//        if(proxyAccessible()){
//            handle.updateItemStack();
//        }
//    }
//    public void syncData(){
//        if(proxyAccessible()){
//            handle.syncData();
//        }
//    }
//    public void syncAmount(){
//        if(proxyAccessible()){
//            handle.syncAmount();
//        }
//    }
//    public int getStorageAmount(){
//       if(proxyAccessible()){
//           return handle.getStorageAmount();
//       }else return 0;
//    }
//    /**
//     * only const cache can set this true
//     * @param persistent
//     */
//    public void setPersistent(boolean persistent) {
//        if(proxyAccessible()){
//            handle.setPersistent(persistent);
//        }
//    }
//
//    /**
//     * set related slot num
//     * @param slot
//     */
//    public void setSaveSlot(int slot){
//
//        if(proxyAccessible()){
//            handle.setSaveSlot(slot);
//        }
//    }
//
//    /**
//     * check if cache can continue bind on this item,or just a item change,
//     * if continue bind, reset source to this item and return True,else return False
//     * @param item
//     * @return
//     */
//    public boolean keepRelated(ItemStack item){
//        if(proxyAccessible()){
//            return handle.keepRelated(item);
//        }else return false;
//    }
////    public void updateItemStack(){
////        if(dirty) {
////            if (wasNull == true) {
////                if (getItem() != null) {
////                    item = item.clone();
////                    //样板保证是一个，用storageAmount mook掉真实itemAmount
////                    item.setAmount(1);
////                    storageAmount = getAmount();
////                    storageType.setStorage(sourceMeta, this.getItem());
////
////                    wasNull = false;
////                }
////            }
////            storageAmount = getAmount();
////        }
////    }
//
//
//    public int getMaxStackCnt() {
//        if(proxyAccessible()){
//            return handle.getMaxStackCnt();
//        }else return 0;
//    }
//    public boolean isNull() {
//        if(proxyAccessible()){
//            return handle.isNull();
//        }else return true;
//    }
//    public ItemMeta getMeta() {
//        if(proxyAccessible()){
//            return handle.getMeta();
//        }else return null;
//    }
//    public void setMeta(ItemMeta meta) {
//        if(proxyAccessible()){
//            handle.setMeta(meta);
//        }
//    }
//    public void setDirty(boolean t){
//        if(proxyAccessible()){
//            handle.setDirty(t);
//        }
//    }
//    public ItemStack getItem() {
//        if(proxyAccessible()){
//            return handle.getItem();
//        }else return null;
//    }
//    public void setAmount(int amount) {
//        if(proxyAccessible()){
//            handle.setAmount(amount);
//        }
//    }
//    public int getAmount() {
//        if(proxyAccessible()){
//            return handle.getAmount();
//        }return 0;
//    }
//    public void addAmount(int amount) {
//        if(proxyAccessible()){
//            handle.addAmount(amount);
//        }
//    }
//
//    public void consume(ItemCounter other){
//        if(proxyAccessible()){
//            handle.consume(other);
//        }
//    }
//    public void setFrom(ItemCounter source){
//        if(proxyAccessible()){
//            handle.setFrom(source);
//        }
//    }
//    public void grab(ItemCounter other){
//        if(proxyAccessible()){
//            handle.grab(other);
//        }
//    }
//    public void push(ItemCounter other){
//        if(proxyAccessible()){
//            handle.push(other);
//        }
//    }
//    public boolean safeAddAmount(int amount){
//       if(proxyAccessible()){
//           return handle.safeAddAmount(amount);
//       }else return false;
//    }
//    public int transportFrom(ItemCounter source,int limit){
//        if(proxyAccessible()){
//            return handle.transportFrom(source,limit);
//        }return limit;
//    }

}
