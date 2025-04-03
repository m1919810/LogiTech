package me.matl114.logitech.utils;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import lombok.Getter;
import me.matl114.logitech.utils.Algorithms.DynamicArray;
import me.matl114.logitech.utils.UtilClass.CargoClass.CargoConfigs;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemPusherProvider;
import me.matl114.matlib.algorithms.dataStructures.complex.lockFactory.DefaultLockFactory;
import me.matl114.matlib.utils.reflect.MethodAccess;
import me.matl114.matlib.utils.reflect.ProxyUtils;
import me.matl114.matlibAdaptor.algorithms.dataStructures.LockFactory;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.IntFunction;

public class TransportUtils {
    public static final ItemStack AIR = new ItemStack(Material.AIR);
    public static ItemPusher[] cacheTransportation(BlockMenu cacheInv,ItemPusher[] cachelst,BlockMenu menu,int[] slots,Settings mod){
        return cacheTransportation(cacheInv,cachelst,menu,slots,mod,CraftUtils.getpusher);
    }
    public static ItemPusher[] cacheTransportation(BlockMenu cacheInv,ItemPusher[] cachelst, BlockMenu menu, int[] slots,Settings mod,ItemPusherProvider provider){
        switch (mod){
            case INPUT -> {
                return transportItemToCache(cacheInv, cachelst, menu, slots, provider);
            }
            case OUTPUT -> {
                return transportItemFromCache(cacheInv, cachelst, menu, slots, provider);
            }default -> {
                return null;
            }
        }
    }
    /**
     * transportation from cache to slots ,will not update cache Amount to menu,will update slots to menu
     * @param menu
     * @param slots
     * @param provider
     * @return
     */
    public static ItemPusher[] transportItemFromCache(BlockMenu cacheInv,ItemPusher[] cachelst, BlockMenu menu, int[] slots, ItemPusherProvider provider){
        int len=slots.length;
        ItemPusher tarCounter2;
        ItemPusher[] tarCounter=new ItemPusher[len];
        int len2=cachelst.length;
        IntFunction<ItemPusher> fromPusherFunc=provider.getMenuInstance(Settings.OUTPUT,menu,slots);
        for(int j=0;j<len2;++j){
            ItemPusher cache=cachelst[j];
            //bugfix: pushing <=0 items means no pushing
            if(cache==null || cache.getAmount()<=0){
                continue;
            }
            for(int i=0;i<len;i++){
                if(tarCounter[i]==null){
                    tarCounter[i]=fromPusherFunc.apply(i);  //provider.get(Settings.OUTPUT,menu,slots[i]);
                }
                tarCounter2=tarCounter[i];
                //quick pass
                if(tarCounter2.getItem()==null){
                    tarCounter2.setFrom(cache);
                    tarCounter2.grab(cache);
                    tarCounter2.updateMenu(menu);
                }
                else if(tarCounter2.getAmount()>=tarCounter2.getMaxStackCnt())continue;
                else if(CraftUtils.matchItemCounter(tarCounter2,cache,true)){
                    tarCounter2.grab(cache);
                    tarCounter2.updateMenu(menu);
                }
                if(cache.getAmount()==0){
                    break;
                }
            }
        }
        //move update in loop
       // updatePushers(tarCounter,menu);
        return tarCounter;
        //代表是否全部推送完毕
    }

    /**
     * transportation from slots to cache ,will not update cache Amount to menu,will update slots to menu
     * @param menu
     * @param slots
     * @param provider
     * @return
     */
    public static ItemPusher[] transportItemToCache(BlockMenu cacheInv,ItemPusher[] cachelst, BlockMenu menu, int[] slots, ItemPusherProvider provider){
        int len=slots.length;
        ItemPusher tarCounter2;
        ItemPusher[] tarCounter=new ItemPusher[len];
        int len2=cachelst.length;
        IntFunction<ItemPusher> fromPusherFunc=provider.getMenuInstance(Settings.INPUT,menu,slots);
        for(int j=0;j<len;++j){
            tarCounter[j]=fromPusherFunc.apply(j);  //provider.get(Settings.INPUT,menu,slots[j]);
        }
        for(int j=0;j<len2;++j){
            ItemPusher cache=cachelst[j];
            if(cache==null){
                continue;
            }
            for(int i=0;i<len;i++){

                //quick pass
                if(tarCounter[i] != null){
                    tarCounter2=tarCounter[i];
                    if(cache.getItem()==null){
                        cache.setFrom(tarCounter2);
                        cache.grab(tarCounter2);
                        //立刻设置种类,防止设置源被同步
                        cache.updateMenu(cacheInv);
                        //设置完后再更新源
                        tarCounter2.updateMenu(menu);
                    }
                    else if(CraftUtils.matchItemCounter(tarCounter2,cache,true)){
                        cache.grab(tarCounter2);
                        tarCounter2.updateMenu(menu);
                    }
                }
            }
        }
        //move update in loop
        //updatePushers(tarCounter,menu);
        return tarCounter;
    }
    public static void updatePushers(ItemPusher[] pusher,BlockMenu menu){
        int len=pusher.length;
        ItemPusher cache;
        for(int i=0;i<len;++i){
            cache=pusher[i];
            if(cache!=null){
                cache.updateMenu(menu);
            }
        }
    }
    public static void dynamicUpdatePushers(DynamicArray<ItemPusher> pushers,BlockMenu menu){
        int len=pushers.getMaxVisitedIndex();//is index ,not len
        ItemPusher cache;
        for(int i=0;i<=len;++i){
            cache=pushers.get(i);
            if(cache!=null){
                cache.updateMenu(menu);
            }
        }
    }
    public static void transportItemGeneral(BlockMenu from, BlockMenu to, int ConfigCode, HashSet<ItemStack> bwlist){
        transportItem(from,to,ConfigCode,false,bwlist,CraftUtils.getpusher);
    }
    public static void transportItemSmarter(BlockMenu from, BlockMenu to, int ConfigCode, HashSet<ItemStack> bwlist){
       transportItem(from,to,ConfigCode,true,bwlist,CraftUtils.getpusher);
    }
    private static int[] getSlotAccess(BlockMenu from,int configCode,boolean isFrom,boolean notDefaultPriority,ItemStack item){
        if(isFrom){
            int[] fromSlot=notDefaultPriority?
                    from.getPreset().getSlotsAccessedByItemTransport(from,ItemTransportFlow.INSERT,item)
                    :from.getPreset().getSlotsAccessedByItemTransport(from,ItemTransportFlow.WITHDRAW,item);
            if (CargoConfigs.FROM_REVERSED.getConfig(configCode)) {
                int[] fromSlot2=notDefaultPriority?from.getPreset().getSlotsAccessedByItemTransport(from,ItemTransportFlow.WITHDRAW,item):from.getPreset().getSlotsAccessedByItemTransport(from,ItemTransportFlow.INSERT,item);
                int fromSlotlen=fromSlot.length;
                fromSlot = Arrays.copyOf(fromSlot, fromSlotlen+ fromSlot2.length);
                System.arraycopy(fromSlot2, 0, fromSlot, fromSlotlen,fromSlot2.length);
            }
            return fromSlot;
        }else {
            int[] fromSlot=notDefaultPriority?
                    from.getPreset().getSlotsAccessedByItemTransport(from,ItemTransportFlow.WITHDRAW,AIR)
                    :from.getPreset().getSlotsAccessedByItemTransport(from,ItemTransportFlow.INSERT,item);
            if (CargoConfigs.TO_REVERSED.getConfig(configCode)) {
                int[] fromSlot2=notDefaultPriority?from.getPreset().getSlotsAccessedByItemTransport(from,ItemTransportFlow.INSERT,item):from.getPreset().getSlotsAccessedByItemTransport(from,ItemTransportFlow.WITHDRAW,item);
                int fromSlotlen=fromSlot.length;
                fromSlot = Arrays.copyOf(fromSlot, fromSlotlen+ fromSlot2.length);
                System.arraycopy(fromSlot2, 0, fromSlot, fromSlotlen,fromSlot2.length);

            }
            return fromSlot;
        }
    }
    public static void transportItem(BlockMenu from,BlockMenu to ,int configCode,boolean smart,HashSet<ItemStack> bwlist,ItemPusherProvider provider){
        //NOOOOO self-transportation here!
        if(from==to || Objects.equals(from.getLocation(),to.getLocation())){
            return;
        }
        boolean from_input= CargoConfigs.FROM_INPUT_FIRST.getConfig(configCode);
        boolean to_output= CargoConfigs.TO_OUTPUT_FIRST.getConfig(configCode);

        boolean reverse=CargoConfigs.REVERSE.getConfig(configCode);
        if(!reverse){

            int[] fromSlot=getSlotAccess(from,configCode,true,from_input,AIR);
            int[] toSlot=getSlotAccess(to,configCode,false,to_output,AIR);
            //只有目标INSERT才需要
            ItemTransportFlow flow=to_output?ItemTransportFlow.WITHDRAW:ItemTransportFlow.INSERT;
            smart=smart&&(CargoConfigs.TO_REVERSED.getConfig(configCode)|| flow==ItemTransportFlow.INSERT);
            transportItemEnsureLock(from,fromSlot,to,toSlot,configCode,smart,bwlist,flow,provider);
            //if (CargoConfigs.F)
        }else {
            int[] toSlot=to_output?
                    to.getPreset().getSlotsAccessedByItemTransport(to,ItemTransportFlow.WITHDRAW,AIR)
                    :to.getPreset().getSlotsAccessedByItemTransport(to,ItemTransportFlow.INSERT,AIR);
            ItemTransportFlow flow=from_input?ItemTransportFlow.INSERT:ItemTransportFlow.WITHDRAW;
            int[] fromSlot= from.getPreset().getSlotsAccessedByItemTransport(from,flow,AIR);
            //只有目标INSERT才需要
            smart= smart&&(CargoConfigs.FROM_REVERSED.getConfig(configCode)|| flow==ItemTransportFlow.INSERT);
            transportItemEnsureLock(to,toSlot,from,fromSlot,configCode,smart,bwlist,flow,provider);

        }
    }
    public static void transportItem(BlockMenu from,int[] fromSlot,BlockMenu to,int[] toSlot,int configCode,
                                     boolean smart, HashSet<ItemStack> bwList,ItemPusherProvider provider){
        transportItemEnsureLock(from,fromSlot,to,toSlot,configCode,smart,bwList,ItemTransportFlow.INSERT,provider);
    }
    @Getter
    private static boolean asyncMode=false;
    @Getter
    private static LockFactory<Location> transportationLockFactory;
//            = new ObjectLockFactory<Location>(Location.class,Location::clone) .init(MyAddon.getInstance()).setupRefreshTask(10*20*60);
    public static void setup(){
        try{
            Object lockFactory = MethodAccess.ofName(Slimefun.class,"getCargoLockFactory")
                    .noSnapShot()
                    .initWithNull()
                    .invoke(null);
            transportationLockFactory = ProxyUtils.buildAdaptorOf(LockFactory.class, lockFactory);
            asyncMode=true;
            Debug.logger("Slimefun Async Cargo Factory Adaptor created successfully");
            Debug.logger("Starting Transportation task async Mode");
        }catch (Throwable anyError){
            Debug.severe("Slimefun Async Cargo Factory not found!");
            Debug.severe("Stopping Transportation task async Mode");
            transportationLockFactory=new DefaultLockFactory<>();
            asyncMode=false;
        }
    }

    private static void transportItemEnsureLock(BlockMenu from, int[] fromSlot, BlockMenu to, int[] toSlot, int configCode,
                                               boolean smart, HashSet<ItemStack> bwList, ItemTransportFlow mainDestinationFlow, ItemPusherProvider provider){
        boolean issymm=CargoConfigs.IS_SYMM.getConfig(configCode);
        boolean isnull=CargoConfigs.IS_NULL.getConfig(configCode);
        boolean islazy=CargoConfigs.IS_LAZY.getConfig(configCode);
        boolean iswtlist=CargoConfigs.IS_WHITELST.getConfig(configCode);
        transportationLockFactory.ensureLock(()->{
            int limit=CargoConfigs.TRANSLIMIT.getConfigInt(configCode);
            if(issymm){
                transportItemSymm(from,fromSlot,to,toSlot,isnull,islazy,iswtlist,bwList,limit,provider);
            }else{
                if(!smart){
                    transportItemGreedy_2(from,fromSlot,to,toSlot,isnull,islazy,iswtlist,bwList,limit,provider);
                }else {
                    limit=Math.min(limit,576);
                    boolean multiDestinationSlot=CargoConfigs.TO_REVERSED.getConfig(configCode);
                    if(multiDestinationSlot){
                        transportItemSmart_2(from,fromSlot,to,toSlot,isnull,islazy,iswtlist,bwList,limit,provider,mainDestinationFlow,mainDestinationFlow==ItemTransportFlow.INSERT?ItemTransportFlow.WITHDRAW:ItemTransportFlow.INSERT);
                    }else {
                        transportItemSmart_2(from,fromSlot,to,toSlot,isnull,islazy,iswtlist,bwList,limit,provider,mainDestinationFlow);
                    }
                }
            }
        },from.getLocation(),to.getLocation());

    }

    /**
     * standard method
     * @param from
     * @param fromSlot
     * @param to
     * @param toSlot
     * @param isSymm
     * @param blacklist
     * @param translimit
     */
    public static void transportItem(BlockMenu from, int[] fromSlot, BlockMenu to, int[] toSlot,boolean isSymm,
                                     HashSet<ItemStack> blacklist, int translimit){
        if(isSymm){
            transportItemSymm(from,fromSlot,to,toSlot,false,false,false,blacklist,translimit,CraftUtils.getpusher);
        }else {
            transportItemGreedy_2(from,fromSlot,to,toSlot,false,false,false,blacklist,translimit,CraftUtils.getpusher);
        }
    }
    public static boolean inbwlist(HashSet<ItemStack> bwset,ItemPusher pusher){
        if(bwset==null||bwset.isEmpty()){return false;}
        for(ItemStack item:bwset){

            if(CraftUtils.matchItemStack(item,pusher,false)){

                return true;
            }
        }return false;
    }



    /**
     * second version of transportGreedy, greate improvement of fullsize destination transportation
     * @param from
     * @param fromSlot
     * @param to
     * @param toSlot
     * @param isnull
     * @param lazy
     * @param whitlist
     * @param blacklist
     * @param translimit
     * @param provider
     */
    //transport illegalStack failed
    //should transport illegalStack from inputs to null slot

    private static void transportItemGreedy_2(BlockMenu from, int[] fromSlot, BlockMenu to, int[] toSlot,
                                             boolean isnull,boolean lazy,boolean whitlist, HashSet<ItemStack> blacklist, int translimit,
                                             ItemPusherProvider provider){
        int len=fromSlot.length;
        int len2=toSlot.length;
//        int[] toCacheIndex=new int[len2];
//        int indexLen=0;
//        ItemStack stack;
//        for(int i=0;i<len2;++i){
//            stack=to.getItemInSlot(toSlot[i]);
//            //如果是空 则大概率要考虑这玩意
//            if(stack==null){
//                toCacheIndex[indexLen]=toSlot[i];
//                ++indexLen;
//            }
//            //如果不是非空模式 且非满 才要考虑这玩意
//            else if(!isnull&&stack.getAmount()<stack.getMaxStackSize()){
//                toCacheIndex[indexLen]=toSlot[i];
//                ++indexLen;
//            }
//        }
//        //我那么大一个输出槽呢？
//        if(indexLen<=0)return;
        DynamicArray<ItemPusher> toCache=new DynamicArray<>(ItemPusher[]::new,54,provider.getMenuInstance(Settings.OUTPUT,to));
        //use global record instead of index record
        boolean[] toRecord=new boolean[54];
        ItemPusher fromPusher ;
        ItemPusher toPusher;
        int fromAmount;
        //this is indexes
        int maxSizeSlotTill=0;
        IntFunction<ItemPusher> fromPusherFunc=provider.getMenuInstance(Settings.INPUT,from,fromSlot);
        loop:
        for(int i=0;i<len;++i){
            fromPusher=fromPusherFunc.apply(i);
            if(fromPusher==null||(inbwlist(blacklist,fromPusher)^whitlist)){
                continue;
            }
            for(int index=maxSizeSlotTill;index<len2;++index){
               int slot=toSlot[index];
                if(!toRecord[slot]){
                    toPusher=toCache.get(slot);
                    if(toPusher.getItem()==null){
                        toPusher.setFrom(fromPusher);
                        //转运方法,
                        fromAmount=Math.min(translimit, fromPusher.getAmount());
                        toPusher.setAmount(fromAmount);
                        translimit-=fromAmount;
                        //拆开写转运
                        //
                        if( fromAmount>=fromPusher.getMaxStackCnt()){
                            //满了 或者非空了 ?,全转,下次别来了
                            fromPusher.setAmount(0);
                            //标记为true说明已经转完了
                            toRecord[slot]=true;
                            maxSizeSlotTill+=(index==maxSizeSlotTill)?1:0;
                        }else {
                            fromPusher.addAmount(-fromAmount);
                            //设置历史,如果为空按理说应该没有先前的设置过
                        }
                        toPusher.updateMenu(to);
                        fromPusher.updateMenu(from);
                        //FME 非空逻辑有问题 需要在获取槽位的时候剔除掉
                    }else if(isnull||toPusher.isFull()){
                        //这个槽已经被处理过了，不可能继续塞物品
                        toRecord[slot] = true;
                    } else if(CraftUtils.matchItemCounter(fromPusher,toPusher,true)){
                        translimit=toPusher.transportFrom(fromPusher,translimit);
                        //DO 将update改到不同的地方 不要做存储
                        toPusher.updateMenu(to);
                        fromPusher.updateMenu(from);
                    }
                    //结束了,,
                    //没有转运份额了,终结
                    if(translimit<=0){
                        break loop;
                    }
                    if(fromPusher.getAmount()<=0){
                        break;
                    }
                }
            }
            if(lazy&&fromPusher.isDirty()){
                //当前非空 且被运输了 并且是首位阻断模式 直接退出
                break ;
            }
            //不一样则新增record,下次用
        }
    }

    /**
     * use slot limit of getSlotAccess
     * @param from
     * @param fromSlot
     * @param to
     * @param toSlot
     * @param isnull
     * @param lazy
     * @param whitlist
     * @param blacklist
     * @param translimit
     * @param provider
     */
    private static void transportItemSmart_2(BlockMenu from, int[] fromSlot, BlockMenu to, int[] toSlot,
                                             boolean isnull,boolean lazy,boolean whitlist, HashSet<ItemStack> blacklist, int translimit,
                                             ItemPusherProvider provider,ItemTransportFlow... flows){
        int len=fromSlot.length;
        //int len2=toSlot.length;
        //int[] toCacheIndex=new int[len2];
       // int indexLen=0;
       // ItemStack stack;
//        int[] slotMappers=new int[54];//we assert that slot is under 54
//        for(int i=0;i<len2;++i){
//            stack=to.getItemInSlot(toSlot[i]);
//            //如果是空 则大概率要考虑这玩意
//            if(stack==null){
//                toCacheIndex[indexLen]=toSlot[i];
//                ++indexLen;
//                slotMappers[toSlot[i]]=indexLen;//以区别0
//            }
//            //如果不是非空模式 且非满 才要考虑这玩意
//            else if(!isnull&&stack.getAmount()<stack.getMaxStackSize()){
//                toCacheIndex[indexLen]=toSlot[i];
//                ++indexLen;
//                slotMappers[toSlot[i]]=indexLen;//+1.，以区别0
//                //这里会重复
//            }
//        }
        //我那么大一个输出槽呢？
        //todo use directly mapped index cache to avoid same slot in slots!
        DynamicArray<ItemPusher> toCache=new DynamicArray<>(ItemPusher[]::new,54,provider.getMenuInstance(Settings.OUTPUT,to));
        boolean[] toRecord=new boolean[54];
        ItemPusher fromPusher ;
        ItemPusher toPusher;
        int fromAmount;
        int[] restrictedInsertSlot;
        IntFunction<ItemPusher> fromPusherFunc=provider.getMenuInstance(Settings.INPUT,from,fromSlot);
        loop:
        for(int i=0;i<len;++i){
            //caches will update when read
            fromPusher=fromPusherFunc.apply(i);       //  provider.get(Settings.INPUT,from,fromSlot[i]);
            if(fromPusher==null||(inbwlist(blacklist,fromPusher)^whitlist)){
                continue;
            }
            loop2:
            for (ItemTransportFlow flow:flows){
                restrictedInsertSlot=to.getPreset().getSlotsAccessedByItemTransport(to,flow,fromPusher.getItem());
                int restrictedLen=restrictedInsertSlot.length;
                for(int j=0;j<restrictedLen;++j){
                    //index=slotMappers[restrictedInsertSlot[j]]-1;
                    //not in available slots
                    //if(index<0)continue ;
                    //bugfix: should use indexJ instead of j
                    int indexJ = restrictedInsertSlot[j];

                    if(!toRecord[indexJ]){
                        toPusher=toCache.get(indexJ);
                        if(toPusher.getItem()==null){
                            toPusher.setFrom(fromPusher);
                            //转运方法,
                            fromAmount=Math.min(translimit, fromPusher.getAmount());
                            toPusher.setAmount(fromAmount);
                            translimit-=fromAmount;
                            //拆开写转运
                            //
                            if( fromAmount>=fromPusher.getMaxStackCnt()){
                                //满了 或者非空了 ?,全转,下次别来了
                                fromPusher.setAmount(0);
                                //标记为true说明已经转完了
                                toRecord[indexJ]=true;
                            }else {
                                fromPusher.addAmount(-fromAmount);
                                //设置历史,如果为空按理说应该没有先前的设置过
                            }
                            toPusher.updateMenu(to);
                            fromPusher.updateMenu(from);
                            //E 非空逻辑有问题 需要在获取槽位的时候剔除掉
                        }else if(isnull||toPusher.isFull()){
                            toRecord[indexJ] = true;
                        }
                        else if( CraftUtils.matchItemCounter(fromPusher,toPusher,true)){//如果匹配
                                translimit=toPusher.transportFrom(fromPusher,translimit);
                                //TO 将update改到不同的地方 不要做存储
                                toPusher.updateMenu(to);
                                fromPusher.updateMenu(from);
                            }

                        //结束了,,
                        //没有转运份额了,终结
                        if(translimit==0){
                            break loop;
                        }
                        if(fromPusher.getAmount()<=0){
                            break loop2;
                        }
                    }
                }
            }
            if(lazy&&fromPusher.isDirty()){
                //当前非空 且被运输了 并且是首位阻断模式 直接退出
                break ;
            }
            //不一样则新增record,下次用
        }
    }


    //No more improve plan yet
    @Deprecated
    private static void transportItemGreedy_3_workspace(BlockMenu from, int[] fromSlot, BlockMenu to, int[] toSlot,
                                             boolean isnull,boolean lazy,boolean whitlist, HashSet<ItemStack> blacklist, int translimit,
                                             ItemPusherProvider provider){
        int len=fromSlot.length;
        int len2=toSlot.length;
        int[] toCacheIndex=new int[len2];
        int indexLen=0;
        ItemStack stack;
        //TOO what if we do loop on outputslot first
        //TDO 调整for顺序 将输出作为主循环 输入作为
        //TDO 这样output槽可以不生成cache？ 其实我觉得生成的也没啥
        //TDO 增加一个动态迭代器 或者动态获取的什么玩意
        //TDO 还是先整个最简单的
        for(int i=0;i<len2;++i){
            stack=to.getItemInSlot(toSlot[i]);
            //如果是空 则大概率要考虑这玩意
            if(stack==null){
                toCacheIndex[indexLen]=toSlot[i];
                ++indexLen;
            }
            //如果不是非空模式 且非满 才要考虑这玩意
            else if(!isnull&&stack.getAmount()<stack.getMaxStackSize()){
                toCacheIndex[indexLen]=toSlot[i];
                ++indexLen;
            }
        }
        //我那么大一个输出槽呢？
        if(indexLen<=0)return;
        DynamicArray<ItemPusher> toCache=new DynamicArray<>(ItemPusher[]::new,indexLen,provider.getMenuInstance(Settings.OUTPUT,to,toCacheIndex));
        boolean[] toRecord=new boolean[indexLen];
        ItemPusher fromPusher ;
        ItemPusher toPusher;
        int fromAmount;
        IntFunction<ItemPusher> fromPusherFunc=provider.getMenuInstance(Settings.INPUT,from,fromSlot);
        loop:
        for(int i=0;i<len;++i){
            fromPusher=fromPusherFunc.apply(i);
            if(fromPusher==null||(inbwlist(blacklist,fromPusher)^whitlist)){
                continue;
            }
            for(int index=0;index<indexLen;++index){
                if(!toRecord[index]){
                    toPusher=toCache.get(index);
                    if(toPusher.getItem()==null){
                        toPusher.setFrom(fromPusher);
                        //转运方法,
                        fromAmount=Math.min(translimit, fromPusher.getAmount());
                        toPusher.setAmount(fromAmount);
                        translimit-=fromAmount;
                        //拆开写转运
                        //
                        if( fromAmount>=fromPusher.getMaxStackCnt()){
                            //满了 或者非空了 ?,全转,下次别来了
                            fromPusher.setAmount(0);
                            //标记为true说明已经转完了
                            toRecord[index]=true;
                        }else {
                            fromPusher.addAmount(-fromAmount);
                            //设置历史,如果为空按理说应该没有先前的设置过
                        }
                        toPusher.updateMenu(to);
                        fromPusher.updateMenu(from);
                        // 非空逻辑有问题 需要在获取槽位的时候剔除掉
                    }else {
                        if(CraftUtils.matchItemCounter(fromPusher,toPusher,true)){//如果匹配
                            translimit=toPusher.transportFrom(fromPusher,translimit);
                            //T 将update改到不同的地方 不要做存储
                            toPusher.updateMenu(to);
                            fromPusher.updateMenu(from);
                        }
                    }
                    //结束了,,
                    //没有转运份额了,终结
                    if(translimit==0){
                        break loop;
                    }
                    if(fromPusher.getAmount()<=0){
                        break;
                    }
                }
            }
            if(lazy&&fromPusher.isDirty()){
                //当前非空 且被运输了 并且是首位阻断模式 直接退出
                break ;
            }
            //不一样则新增record,下次用
        }
    }





    //translimit cal wrong
    private static void transportItemSymm(BlockMenu from, int[] fromSlot, BlockMenu to, int[] toSlot,
                                         boolean isnull,boolean lazy,boolean whitlist, HashSet<ItemStack> blacklist, int translimit,
                                         ItemPusherProvider provider){
        int len=fromSlot.length;
        int len2=toSlot.length;
        int transSlot=Math.min(len2,len);
        IntFunction<ItemPusher> fromPusherFunc=provider.getMenuInstance(Settings.INPUT,from,fromSlot);
        IntFunction<ItemPusher> toPusherFunc=provider.getMenuInstance(Settings.OUTPUT,to,toSlot);
        for(int i=0;i<transSlot;++i){
            ItemPusher fromCache=fromPusherFunc.apply(i);//provider.get(Settings.INPUT,from,fromSlot[i]);
            ItemPusher toCache=toPusherFunc.apply(i);   //provider.get(Settings.OUTPUT,to,toSlot[i]);
            if(fromCache==null||(inbwlist(blacklist,fromCache)^whitlist)){
                continue;
            }
            if(toCache.getItem()==null){
                toCache.setFrom(fromCache);
                translimit=toCache.transportFrom(fromCache,translimit);
            }
            else if(isnull||toCache.getAmount()>=toCache.getMaxStackCnt()){
            }
            else if(CraftUtils.matchItemCounter(fromCache,toCache,true)){
                translimit=toCache.transportFrom(fromCache,translimit);
            }
            boolean hasUpdate=toCache.isDirty();
            if(hasUpdate){
                toCache.updateMenu(to);
                fromCache.updateMenu(from);
                if(lazy){
                    break;
                }
            }
            if(translimit<=0){
                break ;
            }
        }

    }
    public static int[] getInvInputSlot(BlockMenu inv){
        return inv.getPreset().getSlotsAccessedByItemTransport(inv,ItemTransportFlow.INSERT,AIR);
    }
    public static int[] getInvOutputSlot(BlockMenu inv){
        return inv.getPreset().getSlotsAccessedByItemTransport(inv,ItemTransportFlow.WITHDRAW,AIR);
    }
}
