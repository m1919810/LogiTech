package me.matl114.logitech.utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.DistinctiveItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.core.CustomSlimefunItem;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.Algorithms.DynamicArray;

import me.matl114.logitech.utils.UtilClass.ItemClass.*;
import me.matl114.matlib.core.EnvironmentManager;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import net.guizhanss.guizhanlib.minecraft.helper.inventory.ItemStackHelper;
import net.guizhanss.guizhanlib.minecraft.helper.potion.PotionEffectTypeHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffectType;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.IntFunction;

@SuppressWarnings("all")
public class CraftUtils {
    public static final EnumSet<Material> COMPLEX_MATERIALS = EnumSet.noneOf(Material.class);
    static{
        ItemMeta sampleMeta=new ItemStack(Material.STONE).getItemMeta();
        for(Material mat : Material.values()){
            if(mat.isItem()&&!mat.isAir()){
                ItemMeta testMeta=new ItemStack(mat).getItemMeta();
                if(testMeta!=null&& testMeta.getClass()!=sampleMeta.getClass()){
                    COMPLEX_MATERIALS.add(mat);
                }
            }
        }
    }
    private static final HashSet<Material> INDISTINGUISHABLE_MATERIALS = new HashSet<Material>() {{
        //add(Material.SHULKER_BOX);
        add(Material.BUNDLE);
    }};
//    public static final ItemStack DEFAULT_ITEMSTACK=new ItemStack(Material.STONE);
//    public static final ItemMeta NULL_META=(DEFAULT_ITEMSTACK.getItemMeta());
//    public static final Class CRAFTMETAITEMCLASS=NULL_META.getClass();
//    // public static final Class ITEMSTACKCLASS=ItemStack.class;
//    public static Class CRAFTITEMSTACKCLASS;
//    public static ItemStack CRAFTITEMSTACK;
//    public static FieldAccess loreAccess;
//    public static FieldAccess displayNameAccess;
//    public static FieldAccess handledAccess;
//    // public static Field CRAFTHANDLER;
//    public static Class NMSITEMCLASS;
    //public static Field ITEMSTACKMETA;

//    static{
//        Debug.logger("初始化配方合成方法库");
//        try{
//            var CRAFTLORE=CRAFTMETAITEMCLASS.getDeclaredField("lore");
//            CRAFTLORE.setAccessible(true);
//            loreAccess= FieldAccess.of(CRAFTLORE);
//            var CRAFTDISPLAYNAME=CRAFTMETAITEMCLASS.getDeclaredField("displayName");
//            CRAFTDISPLAYNAME.setAccessible(true);
//            displayNameAccess=FieldAccess.of(CRAFTDISPLAYNAME);
//
//        }catch (Throwable e){
//            Debug.logger("Stack reflection failed,please check the error");
//            Debug.logger(e);
//            Debug.logger("disabling the relavent features");
//
//        }
//        try{
//            ChestMenu a=new ChestMenu("byd");
//            a.addItem(0,DEFAULT_ITEMSTACK);
//            CRAFTITEMSTACK=a.getItemInSlot(0);
//            CRAFTITEMSTACKCLASS=CRAFTITEMSTACK.getClass();
//            var CRAFTHANDLER=CRAFTITEMSTACKCLASS.getDeclaredField("handle");
//            CRAFTHANDLER.setAccessible(true);
//            handledAccess=FieldAccess.of(CRAFTHANDLER);
//
//            Object handle=CRAFTHANDLER.get(CRAFTITEMSTACK);
//            //Debug.debug(handle.getClass());
//            NMSITEMCLASS=handle.getClass();
//            //CRAFTMETA=NMSITEMCLASS.getDeclaredField("meta");
////            Field[] fields= NMSITEMCLASS.getDeclaredFields();
////            for(int i=0;i<fields.length;++i){
////            }
//            //ITEMSTACKMETA=DEFAULT_ITEMSTACK.getClass().getDeclaredField("meta");
//            //ITEMSTACKMETA.setAccessible(true);
//            //INVOKE_STACK_SUCCESS=true;
//            // INVOKE_STACK_SUCCESS=false;
//        }catch (Throwable e){
//            Debug.logger("Stack reflection failed,please check the error");
//            Debug.logger(e);
//            Debug.logger("disabling the relavent features");
//
//        }
//    }
    public static void setup(){
        me.matl114.matlib.utils.CraftUtils.NULL_META.getPersistentDataContainer();
    }

    /**
     * if item a and item b both craftItemStack check if handled item match
     * @param a
     * @param b
     * @return
     */
    public static boolean sameCraftItem(ItemStack a, ItemStack b){
        return me.matl114.matlib.utils.CraftUtils.sameCraftItem(a,b);
//        if(CRAFTITEMSTACKCLASS.isInstance(a)&&CRAFTITEMSTACKCLASS.isInstance(b)){
//            try{
//                return  handledAccess.getValue(a)==handledAccess.getValue(b);
//            }catch (Throwable e){
//                return false;
//            }
//        }else return false;
    }
    @Nullable
    public static String parseSfId(ItemStack item) {
        Optional<String> itemID = Slimefun.getItemDataService().getItemData(item);
        return  itemID.orElse(null) ;
    }
    public static String parseSfId(ItemMeta meta){
        Optional<String> itemID = Slimefun.getItemDataService().getItemData(meta);
        return itemID.orElse(null);
    }
    public static SlimefunItem parseSfItem(ItemMeta meta){
        Optional<String> itemID = Slimefun.getItemDataService().getItemData(meta);
        return itemID.map((id)->SlimefunItem.getById(id)).orElse(null);
    }
    public static SlimefunItem parseSfItem(ItemStack item){
        Optional<String> itemID = Slimefun.getItemDataService().getItemData(item);
        return itemID.map((id)->SlimefunItem.getById(id)).orElse(null);
    }
    /**
     * get Consumer for recipe Item
     * @param a
     * @return
     */
    public static ItemConsumer getConsumer(ItemStack a){
        if(a==null)return null;
        if (a instanceof RandOutItem ro) {
            // return new ItemConsumer(a.clone());
            //当物品是随机输出物品时候,取其中的随机实例
            return ItemConsumer.get(ro.getInstance());
        }
        return ItemConsumer.get(a);
    }
    public static ItemCounter getCounter(ItemStack a){
        if(a==null)return null;
        //用于比较和
        return ItemCounter.get(a);
    }

    /**
     * get greedy consumer for recipe Item
     * @param a
     * @return
     */
    public static ItemGreedyConsumer getGreedyConsumer(ItemStack a){
        if(a==null)return null;
        if (a instanceof RandOutItem ro) {
            //当物品是随机输出物品时候,取其中的随机实例
            // return new ItemConsumer(a.clone());
            return ItemGreedyConsumer.get(ro.getInstance());
        }
        return ItemGreedyConsumer.get(a);
    }
    public static ItemGreedyConsumer getGreedyConsumerOnce(ItemStack a){
        return getGreedyConsumerAsAmount(a,1);
    }
    public static ItemGreedyConsumer getGreedyConsumerAsAmount(ItemStack a,int multiply){
        ItemGreedyConsumer greedyConsumer=getGreedyConsumer(a);
        greedyConsumer.setStackNum(multiply);
        return greedyConsumer;
    }

    /**
     * a huge project to adapt sth...
     * use .get(mod,inv,slot) to get ItemPusher
     * mod should be in {Settings.INPUT,Settings.OUTPUT}
     */
    public static final  ItemPusherProvider getpusher=(Settings mod,ItemStack it,int slot)->{
        if(mod==Settings.INPUT||it!=null){
            return ItemPusher.get(it);
        }else{
            return ItemSlotPusher.get(it,slot);
        }
    };
//    public static ItemPusher getSlotPusher(BlockMenu inv,int slot){
//        return getpusher.get(Settings.OUTPUT,inv,slot);
//    }
    public static ItemPusher getPusher(ItemStack it){
        return  ItemPusher.get(it);
    }
//    public static ItemPusher getPusher(BlockMenu inv,int slot){
//        return getpusher.get(Settings.INPUT,inv,slot);
//    }

    public static boolean checkRecipePermission(Player player, MachineRecipe recipe, boolean warn){
        SlimefunItem check;
        for (var itemStack: recipe.getInput()){
            if((check = SlimefunItem.getByItem(itemStack)) != null){
                if(!check.canUse(player, true)){
                    if(warn){
                        AddUtils.sendMessage(player, "&6[&7配方权限&6]&c 这个配方中含有你无法理解的物品,你不能合成它!");
                        Schedules.launchSchedules(player::closeInventory, 1,true,0);
                    }
                    return true;
                }
            }
        }
        for (var itemStack: recipe.getOutput()){
            if((check = SlimefunItem.getByItem(itemStack)) != null){
                if(!check.canUse(player, true)){
                    if(warn){
                        AddUtils.sendMessage(player, "&6[&7配方权限&6]&c 这个配方中含有你无法理解的物品,你不能合成它!");
                        Schedules.launchSchedules(player::closeInventory, 1,true,0);
                    }
                    return true;
                }
            }
        }
        return false;
    }



    public static void clearAmount(BlockMenu inv,ItemPusher ... counters){
        ItemPusher ip;
        for (int i =0;i<counters.length;++i){
            ip=counters[i];
            if (ip!=null){
                ip.setAmount(0);
                ip.updateMenu(inv);
            }
            //this is safe,I said it
        }
    }
    public static void stackUpSlots(BlockMenu inv,int[] slots,boolean useSimilar){
        List<ItemPusher> counters=new ArrayList<>(slots.length);
        loop:
        for (int i:slots){
            ItemStack stack=inv.getItemInSlot(i);
            if(stack==null||stack.getType().isAir()||stack.getAmount()>=stack.getMaxStackSize()){
                continue;
            }else{
                ItemPusher counter=getPusher(stack);
                int len=counters.size();
                for(int j=0;j<len;++j){
                    ItemPusher matcher=counters.get(j);
                    if(matcher!=null&& useSimilar?matcher.getItem().isSimilar(stack):matchItemCore(matcher,counter,true)){
                        matcher.grab(counter);
                        matcher.updateMenu(inv);
                        counter.updateMenu(inv);
                        if(matcher.isFull()){
                            //replace counter counter will be here
                            if(!counter.isEmpty()){
                            counters.set(j,counter);
                            }else {
                                counters.set(j,null);
                            }
                        }
                        continue loop;
                    }
                }
                counters.add(counter);
            }
        }
    }
    /**
     * builtin Method for developments
     */

    public static ItemConsumer[] matchRecipe(List<ItemPusher> slotCounters, MachineRecipe recipe){
        int len2=slotCounters.size();
        ItemStack[] recipeInput = recipe.getInput();
        int cnt = recipeInput.length;
        if(cnt>len2)return null;
        ItemConsumer[] result=new ItemConsumer[cnt];
        ItemConsumer results;
        ItemPusher itemCounter2;
        final boolean[] visited=new boolean[len2];
        for(int i=0;i<cnt;++i) {
            result[i]=getConsumer(recipeInput[i]);
            results=result[i];
            boolean allMatched=false;
            for(int j=0;j<len2;++j) {
                itemCounter2=slotCounters.get(j);
                if(itemCounter2==null)continue;
                if(!visited[j]){
                    itemCounter2.syncData();
                    visited[j]=true;
                }
                else if(itemCounter2.isDirty()){
                    continue;
                }
                if(CraftUtils.matchItemCounter(results,itemCounter2,false)){
                    results.consume(itemCounter2);
                    if(results.getAmount()<=0){
                        allMatched=true;
                        break;
                    }
                }
            }
            if(!allMatched){
                return null;
            }
        }
        return result;

    }

    /**
     * a better version of matchRecipe for
     * @param slotCounters
     * @param recipe
     * @return
     */

    public static ItemGreedyConsumer[] matchMultiRecipe(List<ItemPusher> slotCounters, MachineRecipe recipe,int maxMatchCount){
        int len2=slotCounters.size();
        ItemStack[] recipeInput = recipe.getInput();
        int cnt = recipeInput.length;
        if(cnt>len2)return null;
        ItemGreedyConsumer[] result = new ItemGreedyConsumer[cnt];
        //模拟时间加速 减少~
        maxMatchCount=calMaxCraftAfterAccelerate(maxMatchCount,recipe.getTicks());
        if(maxMatchCount==0){
            return null;
        }
        //int maxAmount;
        final  boolean[] visited =new boolean[len2];
        for(int i=0;i<cnt;++i) {
            ItemGreedyConsumer itemCounter=getGreedyConsumer(recipeInput[i]);
            //in case some idiots! put 0 in recipe
            //maxAmount=Math.min( itemCounter.getAmount()*maxMatchCount,1);
            for(int j=0;j<len2;++j) {
                ItemPusher itemCounter2=slotCounters.get(j);
                if(itemCounter2==null)continue;
                if(!visited[j]){
                    itemCounter2.syncData();
                    visited[j]=true;
                }
                else if(itemCounter2.isDirty()){
                    continue;
                }
                if(CraftUtils.matchItemCounter(itemCounter,itemCounter2,false)){
                    itemCounter.consume(itemCounter2);
                    if(itemCounter.getStackNum()>=maxMatchCount)break;
                }
            }
            //不够一份的量
            if(itemCounter.getStackNum()<1){
                return null;
            }
            result[i]=itemCounter;
        }
        return result;

    }

    /**
     * try input every OutputItem in slotCounters, just a plan ,will return plans
     * @param
     * @param recipe
     * @return
     */

    public static ItemConsumer[] countOneOutput(BlockMenu inv , int[] output, MachineRecipe recipe){
        return countOneOutput(inv,output,recipe,getpusher);
    }
    public static ItemConsumer[] countOneOutput(BlockMenu inv , int[] output, MachineRecipe recipe,ItemPusherProvider pusher){
        int len2=output.length;
        ItemStack[] recipeInput = recipe.getOutput();
        int cnt = recipeInput.length;
        ItemConsumer[] result = new ItemConsumer[cnt];
        DynamicArray<ItemPusher> slotCounters=new DynamicArray<>(ItemPusher[]::new,len2,pusher.getMenuInstance(Settings.OUTPUT,inv,output));
        for(int i=0;i<cnt;++i) {
            result[i]=getConsumer(recipeInput[i]);
            for(int j=0;j<len2;++j) {
                if(result[i].getAmount()<=0)break;
                ItemPusher itemCounter2=slotCounters.get(j);
                if(itemCounter2.getItem()==null){
                    itemCounter2.setFrom(result[i]);
                    itemCounter2.grab(result[i]);
                    result[i].addRelate(itemCounter2);

                }
                else if(itemCounter2.isDirty()){
                   continue;
                }else if(CraftUtils.matchItemCounter(itemCounter2,result[i],false)){
                    itemCounter2.grab(result[i]);
                    result[i].addRelate(itemCounter2);
                }
            }
            if (result[i].getAmount()>0)return null;
        }
        return result;
    }

    /**
     * match if one time of this recipe can be crafted
     * @param inv
     * @param input
     * @param output
     * @param recipe
     * @return
     */

    public static Pair<ItemConsumer[],ItemConsumer[]> countOneRecipe(BlockMenu inv,int[] input,int[] output,MachineRecipe recipe){
        return countOneRecipe(inv,input,output,recipe,getpusher);
    }
    public static Pair<ItemConsumer[],ItemConsumer[]> countOneRecipe(BlockMenu inv,int[] input,int[] output,MachineRecipe recipe,ItemPusherProvider pusher){
        int len=input.length;
        ItemStack[] recipeIn=recipe.getInput();
        int cnt=recipeIn.length;
        DynamicArray<ItemPusher> inputs=new DynamicArray<>(ItemPusher[]::new,len,pusher.getMenuInstance(Settings.INPUT,inv,input));

        ItemConsumer[] inputInfo=matchRecipe(inputs,recipe);
        if(inputInfo!=null){
            ItemConsumer[] outputInfo=countOneOutput(inv,output,recipe,pusher);
            if(outputInfo!=null){
                return new Pair<>(inputInfo,outputInfo);
            }
        }
        return null;
    }
    /**
     * match max craft time of recipe ,return null if cannot craft return the recorded information of inputConsumer and outputConsumner
     * @param inv
     * @param input
     * @param output
     * @param recipe
     * @param limit
     * @return
     */

    public static Pair<ItemGreedyConsumer[],ItemGreedyConsumer[]> countMultiRecipe( BlockMenu inv,int[] input,int[] output, MachineRecipe recipe, int limit) {
        return countMultiRecipe(inv,input,output,recipe,limit,getpusher);
    }
    public static Pair<ItemGreedyConsumer[],ItemGreedyConsumer[]> countMultiRecipe( BlockMenu inv,int[] input,int[] output, MachineRecipe recipe, int limit,ItemPusherProvider pusher){
        int len=input.length;
        ItemStack[] recipeInput = recipe.getInput();
        DynamicArray<ItemPusher> inputCounters=new DynamicArray<>(ItemPusher[]::new,len,pusher.getMenuInstance(Settings.INPUT,inv,input));
        int cnt=recipeInput.length;

        ItemGreedyConsumer[] recipeCounter=new ItemGreedyConsumer[cnt];
        int maxAmount=limit;
        final boolean[] visited=new boolean[len];
        for(int i=0;i<cnt;++i) {
            recipeCounter[i]=getGreedyConsumer(recipeInput[i]);
            for(int j=0;j<len;++j) {
                ItemPusher  itemCounter2=inputCounters.get(j);
                if(itemCounter2==null)continue;
                if(!visited[j]){
                    itemCounter2.syncData();
                    visited[j]=true;
                }
                else if(itemCounter2.isDirty()){
                    //如果该counter已经被人绑定了 就跳过
                    continue;
                }
                if(CraftUtils.matchItemCounter(itemCounter2,recipeCounter[i],false)){
                    //如果匹配 将其加入...list,并算入matchCnt
                    recipeCounter[i].addRelate(itemCounter2);
                    recipeCounter[i].addMatchAmount(itemCounter2.getAmount());
                    if(recipeCounter[i].getStackNum()>=maxAmount)break;
                }
            }
            int stackAmount=recipeCounter[i].getStackNum();
            if(stackAmount>=maxAmount)continue;
            maxAmount=Math.min(maxAmount,stackAmount);
            if(maxAmount<=0)return null;
        }
       // Debug.logger("see match input amount "+maxAmount);
        ItemGreedyConsumer[] recipeCounter2= countMultiOutput(recipeCounter,inv,output,recipe,maxAmount,pusher);

        return recipeCounter2!=null?new Pair<>(recipeCounter,recipeCounter2):null;

    }

    /**
     * return null if no match ,return ItemGreedyConsumer with modification of inputInfo and outputConsumer with written matchAMount
     * @param inputInfo
     * @param inv
     * @param output
     * @param recipe
     * @param limit
     * @return
     */

    public static ItemGreedyConsumer[] countMultiOutput(ItemGreedyConsumer[] inputInfo, BlockMenu inv, int[] output, MachineRecipe recipe, int limit){
        return countMultiOutput(inputInfo,inv,output,recipe,limit,getpusher);
    }

    /**
     * this method has a step reading optimize
     * @param inputInfo
     * @param inv
     * @param output
     * @param recipe
     * @param limit
     * @param pusher
     * @return
     */
    public static  ItemGreedyConsumer[] countMultiOutput(ItemGreedyConsumer[] inputInfo, BlockMenu inv, int[] output, MachineRecipe recipe, int limit,ItemPusherProvider pusher){

        int len2=output.length;
        DynamicArray<ItemPusher> outputCounters=new DynamicArray<>(ItemPusher[]::new,len2,pusher.getMenuInstance(Settings.OUTPUT,inv,output));
        ItemStack[] recipeOutput = recipe.getOutput();
        int cnt2=recipeOutput.length;
        ItemGreedyConsumer[] recipeCounter2=new ItemGreedyConsumer[cnt2];
        int maxAmount2=limit;
        if(cnt2<=0||maxAmount2<=0){
            return null;
        }//优化 当一个输出的时候 直接输出 匹配最大的匹配数
        //99%的情况都是这样的
        //应该不会有很多2b作者给这么高效的机器设置两个输出
        else if (cnt2==1){
            recipeCounter2[0]=getGreedyConsumer(recipeOutput[0]);
            for(int i=0;i<len2;++i) {
                ItemPusher itemCounter=outputCounters.get(i);
                if(itemCounter.getItem()==null){
                    itemCounter.setFrom(recipeCounter2[0]);
                    recipeCounter2[0].addRelate(itemCounter);
                    recipeCounter2[0].addMatchAmount(recipeCounter2[0].getMaxStackCnt());
                }
                else if(itemCounter.getMaxStackCnt()<=itemCounter.getAmount()){
                    continue;
                }
                else if(CraftUtils.matchItemCounter(recipeCounter2[0],itemCounter,false)){
                    recipeCounter2[0].addRelate(itemCounter);
                    recipeCounter2[0].addMatchAmount(itemCounter.getMaxStackCnt()-itemCounter.getAmount());
                }
                if(recipeCounter2[0].getStackNum()>=maxAmount2){
                    break;
                }
            }
            maxAmount2=Math.min(recipeCounter2[0].getStackNum(),maxAmount2);
            if(maxAmount2<=0){return null;}
        }
        //如果真的有,你喜欢就好
        //有可能是桶或者什么
        else{
        //维护一下当前matchAmount最小值
        PriorityQueue<ItemGreedyConsumer> priorityRecipeOutput=new PriorityQueue<>(cnt2+1) ;
        for(int i=0;i<cnt2;++i) {
            recipeCounter2[i]=getGreedyConsumer(recipeOutput[i]);
            priorityRecipeOutput.add(recipeCounter2[i]);
        }
        while(true){
            ItemGreedyConsumer itemCounter2=priorityRecipeOutput.poll();
            if(itemCounter2==null){
                break;
            }
            boolean hasNextPushSlot=false;
            for(int j=0;j<len2;++j) {
                ItemPusher itemCounter=outputCounters.get(j);
                if(itemCounter.getItem()==null){
                    itemCounter.setFrom(itemCounter2);
                    itemCounter2.addRelate(itemCounter);
                    //can output maxCnt amount
                    itemCounter2.addMatchAmount(itemCounter2.getMaxStackCnt());
                    hasNextPushSlot=true;
                    break;
                }
                else if(itemCounter.isDirty()||itemCounter.getMaxStackCnt()<=itemCounter.getAmount()){
                    continue;
                }
                else if(CraftUtils.matchItemCounter(itemCounter2,itemCounter,false)){
                    //what the fuck????
                    //为什么他妈的会覆盖啊 谁写的答辩玩意啊
                    //itemCounter.setFrom(itemCounter2);
                    itemCounter2.addRelate(itemCounter);
                    //must use itemCounter.getMaxStackCnt because ItemStorage
                    itemCounter2.addMatchAmount(itemCounter.getMaxStackCnt()-itemCounter.getAmount());
                    hasNextPushSlot=true;
                    break;
                }
            }
            //?
            //这是什么鬼玩意
            //哦
            //要判断是否有nextSlot
            if(hasNextPushSlot&&itemCounter2.getStackNum()<=maxAmount2){
                priorityRecipeOutput.add(itemCounter2);
            }else{
                //这是不会再增加了 作为一个上线
                maxAmount2=Math.min(maxAmount2,itemCounter2.getStackNum());
                if(maxAmount2<=0)return null;
            }
        }
        }
        for(int i=0;i<cnt2;++i) {
            recipeCounter2[i].setStackNum(maxAmount2);
        }
        int cnt=inputInfo.length;
        for(int i=0;i<cnt;++i){
            inputInfo[i].setStackNum(maxAmount2);
        }
        return recipeCounter2;
    }

    /**
     * this method do not have step reading optimiz,should provide pre-get ItemPushers,used in Places where outputslot are frequently visited
     * we abandon this method because we have better access to dynamicly load ItemPushers
     * @param inputInfo
     * @param output
     * @param recipe
     * @param limit
     * @return
     */
    @Deprecated
    public static ItemGreedyConsumer[] countMultiOutput(ItemGreedyConsumer[] inputInfo, ItemPusher[] output, MachineRecipe recipe, int limit){

        int len2=output.length;
        int outputSlotpointer=0;
        ItemStack[] recipeOutput = recipe.getOutput();
        int cnt2=recipeOutput.length;
        ItemGreedyConsumer[] recipeCounter2=new ItemGreedyConsumer[cnt2];
        int maxAmount2=limit;
        if(cnt2<=0){
            return recipeCounter2;
        }//优化 当一个输出的时候 直接输出 匹配最大的匹配数
        //99%的情况都是这样的
        //应该不会有很多2b作者给这么高效的机器设置两个输出
        else if (cnt2==1){
            recipeCounter2[0]=getGreedyConsumer(recipeOutput[0]);

            for(;outputSlotpointer<len2;) {
                ItemPusher itemCounter=output[outputSlotpointer];
                ++outputSlotpointer;
                if(itemCounter.getItem()==null){
                    itemCounter.setFrom(recipeCounter2[0]);
                    recipeCounter2[0].addRelate(itemCounter);
                    recipeCounter2[0].addMatchAmount(recipeCounter2[0].getMaxStackCnt());
                }
                else if(itemCounter.getMaxStackCnt()<=itemCounter.getAmount()){
                    continue;
                }
                else if(CraftUtils.matchItemCounter(recipeCounter2[0],itemCounter,false)){
                    recipeCounter2[0].addRelate(itemCounter);
                    recipeCounter2[0].addMatchAmount(recipeCounter2[0].getMaxStackCnt()-itemCounter.getAmount());
                }
                if(recipeCounter2[0].getStackNum()>=maxAmount2){
                    break;
                }
            }
            maxAmount2=Math.min(recipeCounter2[0].getStackNum(),maxAmount2);

            if(maxAmount2<=0){return null;}
        }
        //如果真的有,你喜欢就好
        //有可能是桶或者什么
        else{
            //维护一下当前matchAmount最小值
            PriorityQueue<ItemGreedyConsumer> priorityRecipeOutput=new PriorityQueue<>(cnt2+1) ;
            for(int i=0;i<cnt2;++i) {
                recipeCounter2[i]=getGreedyConsumer(recipeOutput[i]);
                priorityRecipeOutput.add(recipeCounter2[i]);
            }
            while(true){
                ItemGreedyConsumer itemCounter2=priorityRecipeOutput.poll();
                if(itemCounter2==null){
                    break;
                }
                boolean hasNextPushSlot=false;
                for(int j=0;j<len2;++j) {
                    ItemPusher itemCounter=output[j];
                    if(itemCounter.isDirty()||itemCounter.getMaxStackCnt()<=itemCounter.getAmount()){
                        continue;
                    }
                    else if(CraftUtils.matchItemCounter(itemCounter2,itemCounter,false)){
                        itemCounter2.addRelate(itemCounter);
                        itemCounter2.addMatchAmount(itemCounter2.getMaxStackCnt()-itemCounter.getAmount());
                        hasNextPushSlot=true;
                        break;
                    }
                }
                if(hasNextPushSlot&&itemCounter2.getStackNum()<=maxAmount2){
                    priorityRecipeOutput.add(itemCounter2);
                }else{
                    //这是不会再增加了 作为一个上线
                    maxAmount2=Math.min(maxAmount2,itemCounter2.getStackNum());
                    if(maxAmount2<=0)return null;
                }
            }
        }
        for(int i=0;i<cnt2;++i) {
            recipeCounter2[i].setStackNum(maxAmount2);
        }
        int cnt=inputInfo.length;
        for(int i=0;i<cnt;++i){
            inputInfo[i].setStackNum(maxAmount2);
        }
        return recipeCounter2;
    }

    /**
     public static Pair<ItemGreedyConsumer[],ItemGreedyConsumer[]> countMultiRecipe(List<ItemPusher> inputCounters, ItemSlotPusher[] outputCounters, MachineRecipe recipe, int limit){
     int len=inputCounters.size();
     ItemStack[] recipeInput = recipe.getInput();
     int cnt = recipeInput.length;
     ItemGreedyConsumer[] recipeCounter=new ItemGreedyConsumer[cnt];
     int maxAmount=limit;
     for(int i=0;i<cnt;++i) {
     recipeCounter[i]=getGreedyConsumer(recipeInput[i]);
     for(int j=0;j<len;++j) {
     ItemPusher  itemCounter2=inputCounters.get(j);
     if(i==0){
     itemCounter2.syncData();
     }
     if(itemCounter2.isDirty()){
     //如果该counter已经被人绑定了 就跳过
     continue;
     }
     if(CraftUtils.matchItemCounter(itemCounter2,recipeCounter[i],false)){
     //如果匹配 将其加入...list,并算入matchCnt
     recipeCounter[i].addRelate(itemCounter2);
     recipeCounter[i].addMatchAmount(itemCounter2.getAmount());

     if(recipeCounter[i].getStackNum()>=limit)break;
     }
     }
     maxAmount=Math.min(maxAmount,recipeCounter[i].getStackNum());
     }
     if (maxAmount==0)return null;
     int len2=outputCounters.length;
     ItemStack[] recipeOutput = recipe.getOutput();
     int cnt2=recipeOutput.length;
     ItemGreedyConsumer[] recipeCounter2= countMultiOutput(recipeCounter,outputCounters,recipe,maxAmount);
     return recipeCounter2!=null?new Pair<>(recipeCounter,recipeCounter2):null;
     }
     **/

    /**
     * force push recipe outputs to outputslot
     * @param
     * @param
     */
    public static boolean forcePush( ItemConsumer[] slotCounters, BlockMenu inv,int[] slots){
        return forcePush(slotCounters,inv,slots,getpusher);
    }
    public static boolean forcePush( ItemConsumer[] slotCounters, BlockMenu inv,int[] slots,ItemPusherProvider pusher){
       // ItemPusher[] slotCounters2=new ItemPusher[slots.length];
        DynamicArray<ItemPusher> slotCounters2=new DynamicArray<>(ItemPusher[]::new,slots.length,pusher.getMenuInstance(Settings.OUTPUT,inv,slots));
        ItemConsumer outputItem;
        ItemPusher itemCounter;
        boolean hasChanged=false;
        int len=slotCounters.length;
        ItemStack previewItem;
        for(int i=0;i<len;++i) {
            outputItem=slotCounters[i];
            //consume mode
            outputItem.syncData();
            int maxAmount=outputItem.getMaxStackCnt();
            for(int j=0;j<slots.length;++j) {
                previewItem=inv.getItemInSlot(slots[j]);
                if(previewItem==null){
                    //是空槽 绝对不可能是存储 直接上 不要创建缓存
                    int amount=Math.min( outputItem.getAmount(),maxAmount);
                    inv.replaceExistingItem(slots[j],outputItem.getItem(),false);
                    previewItem=inv.getItemInSlot(slots[j]);
                    if(previewItem!=null){
                        //防止某些脑瘫push一些null
                        previewItem.setAmount(amount);
                    }
                    outputItem.addAmount(-amount);
                    hasChanged=true;
                    //满槽 绝对不可能是存储，直接跳
                    //至于为什么不用maxAmount 因为outputItem可以是不可堆叠物品，但是存储暂时还没有不可堆叠物品
                    //如果以后有 在此处加上>1判断
                }else if(previewItem.getAmount()>=previewItem.getMaxStackSize()){
                    continue;
                }else{
                    itemCounter=slotCounters2.get(j);
                    if(!itemCounter.isDirty()){
                        //可能是存储里的 或者是被覆写的maxCNT
                        if(itemCounter.getItem()==null){
                            //我们决定将这个分类留在这,为了安全性 毕竟谁也不知到之后会开发啥，不过显然 这玩意大概率是不会被调用的.
                            itemCounter.setFrom(outputItem);
                            itemCounter.grab(outputItem);
                            itemCounter.updateMenu(inv);
                            itemCounter.setDirty(true);
                            hasChanged=true;
                        } else if (itemCounter.getAmount()>=itemCounter.getMaxStackCnt()){
                            continue;
                        }
                        else if(matchItemCounter(outputItem,itemCounter,false)){
                            itemCounter.grab(outputItem);
                            itemCounter.updateMenu(inv);
                            itemCounter.setDirty(true);
                            hasChanged=true;
                        }
                    }
                }
                if(outputItem.getAmount()<=0)break;
            }
        }
        return hasChanged;
    }


    /**
     * remake version of pushItems
     * @return
     */
    public static boolean pushItems(ItemStack[] items,BlockMenu inv,int[] slots){
        return pushItems(items,inv,slots,getpusher);
    }
    public static boolean  pushItems(ItemStack[] items,BlockMenu inv,int[] slots,ItemPusherProvider pusher){
        ItemConsumer[] consumers=new ItemConsumer[items.length];
        for(int i=0;i<items.length;++i) {
            consumers[i]=CraftUtils.getConsumer(items[i]);
        }
        return forcePush(consumers,inv,slots,pusher);
    }
    public static boolean multiPushItems(ItemStack[] items,BlockMenu inv,int[] slots,int multiple){
        return multiPushItems(items,inv,slots,multiple,getpusher);
    }
    public static boolean multiPushItems(ItemStack[] items,BlockMenu inv,int[] slots,int multiple,ItemPusherProvider pusher){
        if(multiple==1){
            return pushItems(items,inv,slots,pusher);
        }
        ItemGreedyConsumer[] slotCounters=new ItemGreedyConsumer[items.length];
        for(int i=0;i<items.length;++i) {
            slotCounters[i]=CraftUtils.getGreedyConsumer(items[i]);
            slotCounters[i].setMatchAmount(slotCounters[i].getAmount()*multiple);
        }
        return multiForcePush(slotCounters,inv,slots,pusher);


    }

    public static boolean multiForcePush(ItemGreedyConsumer[] slotCounters, BlockMenu inv,int[] slots){
        return multiForcePush(slotCounters,inv,slots,getpusher);
    }
    public static boolean multiForcePush(ItemGreedyConsumer[] slotCounters, BlockMenu inv,int[] slots,ItemPusherProvider pusher){
        DynamicArray<ItemPusher> slotCounters2=new DynamicArray<>(ItemPusher[]::new,slots.length,pusher.getMenuInstance(Settings.OUTPUT,inv,slots));
        int len= slotCounters.length;
        ItemPusher itp=null;
        ItemGreedyConsumer outputItem;
        boolean hasChanged=false;
        ItemStack previewItem;
        for(int i=0;i<len;++i) {
            outputItem=slotCounters[i];
            //consume mode
            for(int j=0;j<slots.length;++j) {
                previewItem=inv.getItemInSlot(slots[j]);
                int maxAmount=outputItem.getMaxStackCnt();
                if(previewItem==null){
                    //多倍匹配用的是MatchAmount
                    int amount=Math.min(outputItem.getMatchAmount(),maxAmount);
                    inv.replaceExistingItem(slots[j],outputItem.getItem(),false);
                    previewItem=inv.getItemInSlot(slots[j]);
                    if(previewItem!=null){
                        //防止某些脑瘫push一些null
                        previewItem.setAmount(amount);
                    }//多倍匹配用的是MatchAmount
                    outputItem.addMatchAmount(-amount);
                    hasChanged=true;
                    //同上 不解释
                }else if(previewItem.getAmount()>=previewItem.getMaxStackSize()){
                    continue;
                }else{

                    itp=slotCounters2.get(j);
                    if(!itp.isDirty()){
                        if(itp.getItem()==null){
                            itp.setFrom(outputItem);
                            //needs this push ,because the source of outputItem
                            outputItem.push(itp);
                            itp.updateMenu(inv);
                            itp.setDirty(true);
                            hasChanged=true;

                        }else if (itp.getAmount()>=itp.getMaxStackCnt()){
                            continue;
                        }
                        else if(matchItemCounter(outputItem,itp,false)){
                             outputItem.push(itp);
                             itp.updateMenu(inv);
                             itp.setDirty(true);
                             hasChanged=true;
                        }
                    }
                }
                if(outputItem.getMatchAmount()<=0)break;
            }
        }
        return hasChanged;
    }
    /**
     * simply update consumes
     * @param itemCounters
     */
    public static void updateInputMenu(ItemConsumer[] itemCounters,BlockMenu inv){
        for(int i = 0; i< itemCounters.length; ++i){
            itemCounters[i].updateItems(inv,Settings.GRAB);
        }
    }

    public static void updateOutputMenu(ItemConsumer[] itemCounters,BlockMenu inv){
        for(int i = 0; i< itemCounters.length; ++i){
            itemCounters[i].updateItems(inv,Settings.PUSH);
        }
    }

    public static void multiUpdateInputMenu(ItemGreedyConsumer[] recipeGreedyCounters,BlockMenu inv){
        for(int i = 0; i< recipeGreedyCounters.length; ++i){
            recipeGreedyCounters[i].updateItemsPlus(inv,Settings.GRAB);
        }
    }

    public static void multiUpdateOutputMenu(ItemGreedyConsumer[] recipeGreedyCounters,BlockMenu inv){
        for(int i = 0; i< recipeGreedyCounters.length; ++i){
            recipeGreedyCounters[i].updateItemsPlus(inv,Settings.PUSH);
        }
    }


    /**
     * make pushItem
     * make sure itemCounters.size>=out
     * @param itemCounters
     * @param out
     * @param inv
     */
    public static void updateOutputMenu(ItemPusher[] itemCounters,int[] out,BlockMenu inv){
        int len=out.length;
        for (int i=0;i<len;++i){
            ItemPusher a=itemCounters[i];
            if(a==null){
                continue;
            }
            if(a.isDirty()){
                a.updateMenu(inv);
            }
        }
    }
    /**
     * general findNextRecipe but modified by meeeeeee to adapt ItemCounter
     * @param inv
     * @param slots
     * @param recipes
     * @param useHistory
     * @return
     */
    public static Pair<MachineRecipe,ItemConsumer[] > findNextRecipe(BlockMenu inv, int[] slots,int[] outs, List<MachineRecipe> recipes,boolean useHistory) {
        return findNextRecipe(inv,slots,outs,recipes,useHistory,Settings.SEQUNTIAL);
    }
    public static Pair<MachineRecipe,ItemConsumer[]> findNextRecipe(BlockMenu inv ,int[] slots,int[] outs,List<MachineRecipe> recipes,boolean useHistory,Settings order){
        return findNextRecipe(inv,slots,outs,recipes,useHistory,Settings.SEQUNTIAL,getpusher);
    }
    /**
     * general findNextRecipe but modified by meeeeeee to adapt ItemCounter
     * @param inv
     * @param slots
     * @param outs
     * @param recipes
     * @param useHistory
     * @param order
     * @return
     */
    public static Pair<MachineRecipe,ItemConsumer[]> findNextRecipe(BlockMenu inv ,int[] slots,int[] outs,List<MachineRecipe> recipes,boolean useHistory,Settings order,ItemPusherProvider pusher){
        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len = slots.length;
        final DynamicArray<ItemPusher> slotCounter=new DynamicArray<>(ItemPusher[]::new,len,pusher.getMenuInstance(Settings.INPUT,inv,slots));
        int recipeAmount=recipes.size();
        if(recipeAmount<=0){
            return null;
        }
        int __index=0;
        //if usehistory ,will suggest a better place to start
        if(useHistory) {
            __index= DataCache.getLastRecipe(inv.getLocation());
            __index=(__index<0)?0:__index;
            __index=(__index>=recipeAmount)?(recipeAmount-1):__index;
        }
        int __iter=__index;
        MachineRecipe checkRecipe=recipes.get(__iter);
        ItemConsumer[] inputInfo=matchRecipe(slotCounter,checkRecipe);
        if(inputInfo!=null) {
            if(useHistory) {
                DataCache.setLastRecipe(inv.getLocation(),__iter);
            }
            ItemConsumer[] outputInfo= countOneOutput(inv,outs,checkRecipe,getpusher);
            if(outputInfo!=null){
                updateInputMenu(inputInfo,inv);
                return new Pair<>(checkRecipe,outputInfo);
            }else return null;//for better performance in processors
        }
        __iter+=delta;
        for(;__iter<recipeAmount&&__iter>=0;__iter+=delta){
            checkRecipe=recipes.get(__iter);
            inputInfo=matchRecipe(slotCounter,checkRecipe);
            if(inputInfo!=null) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                ItemConsumer[] outputInfo= countOneOutput(inv,outs,checkRecipe,getpusher);
                if(outputInfo!=null){
                    updateInputMenu(inputInfo,inv);
                    return new Pair<>(checkRecipe,outputInfo);
                }else return null;//for better performance in processors
            }
        }
        if(__iter<0){
            __iter=recipeAmount-1;
        }else{
            __iter=0;
        }
        for(;__iter!=__index;__iter+=delta) {
            checkRecipe=recipes.get(__iter);
            inputInfo=matchRecipe(slotCounter,checkRecipe);
            if(inputInfo!=null) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                ItemConsumer[] outputInfo= countOneOutput(inv,outs,checkRecipe,getpusher);
                if(outputInfo!=null){
                    updateInputMenu(inputInfo,inv);
                    return new Pair<>(checkRecipe,outputInfo);
                }else return null;//for better performance in processors
            }
        }
        return null;
    }

    /**
     * general only find next recipe for input slots ,No check for output slots, no consume for inputslots ,only fetch NEXT matching recipe.
     * used in manuals and MaterialGenerators
     * @param inv
     * @param slots
     * @param recipes
     * @param useHistory
     * @param order
     * @return
     *
     */
    public static MachineRecipe matchNextRecipe(BlockMenu inv ,int[] slots,List<MachineRecipe> recipes,boolean useHistory,Settings order){
        return  matchNextRecipe(inv,slots,recipes,useHistory,order,getpusher);
    }
    public static MachineRecipe matchNextRecipe(BlockMenu inv ,int[] slots,List<MachineRecipe> recipes,boolean useHistory,Settings order,ItemPusherProvider pusher){
        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len = slots.length;
        //final ArrayList<ItemPusher> slotCounter=new ArrayList<>(len);
        final DynamicArray<ItemPusher> slotCounter=new DynamicArray<>(ItemPusher[]::new,len,pusher.getMenuInstance(Settings.INPUT,inv,slots));
        int recipeAmount=recipes.size();
        if(recipeAmount<=0){
            return null;
        }
        int __index=0;
        //if usehistory ,will suggest a better place to start
        if(useHistory) {
            __index= DataCache.getLastRecipe(inv.getLocation());
            __index=(__index<0)?0:__index;
            __index=(__index>=recipeAmount)?(recipeAmount-1):__index;
        }
        int __iter=__index;
        MachineRecipe checkRecipe=recipes.get(__iter);
        if(matchRecipe(slotCounter,checkRecipe)!=null) {
            if(useHistory) {
                DataCache.setLastRecipe(inv.getLocation(),__iter);
            }
            return checkRecipe;

        }
        __iter+=delta;
        for(;__iter<recipeAmount&&__iter>=0;__iter+=delta){
            checkRecipe=recipes.get(__iter);
            if(matchRecipe(slotCounter,checkRecipe)!=null) {

                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return checkRecipe;
            }
        }
        if(__iter<0){
            __iter=recipeAmount-1;
        }else{
            __iter=0;
        }
        for(;__iter!=__index;__iter+=delta) {
            checkRecipe=recipes.get(__iter);
            if(matchRecipe(slotCounter,checkRecipe)!=null) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return checkRecipe;
            }
        }
        return null;
    }
    public static List<Integer> matchAllRecipe(BlockMenu inv ,int[] slots,List<MachineRecipe> recipes,ItemPusherProvider pusher,int matchAmount){
        int delta=1;
        int len = slots.length;
        List<Integer> result=new ArrayList<>(matchAmount+1);
        //final ArrayList<ItemPusher> slotCounter=new ArrayList<>(len);
        final DynamicArray<ItemPusher> slotCounter=new DynamicArray<>(ItemPusher[]::new,len,pusher.getMenuInstance(Settings.INPUT,inv,slots));
        int recipeAmount=recipes.size();
        if(recipeAmount<=0){
            return result;
        }
        //if usehistory ,will suggest a better place to start
        MachineRecipe checkRecipe;
        for(int __iter=0;__iter<recipeAmount;__iter++){
            checkRecipe=recipes.get(__iter);
            if(matchRecipe(slotCounter,checkRecipe)!=null) {
                result.add(__iter);
                if(result.size()>=matchAmount) {
                    return result;
                }
            }
        }
        return result;
    }


//    public static Pair<MachineRecipe,ItemGreedyConsumer[]> matchNextMultiRecipe(BlockMenu inv ,int[] slots,List<MachineRecipe> recipes,boolean useHistory,int limit,Settings order){
//        return matchNextMultiRecipe(inv,slots,recipes,useHistory,limit,order,getpusher);
//    }
    /**
     * a better version of matchNextRecipe for multiCraft,will remember the related slots of inputItems
     * will not calculate maxCraftTime !,you have to calculate by yourself!!!!
     * @param inv
     * @param slots
     * @param recipes
     * @param useHistory
     * @param order
     * @return
     */
    public static Pair<MachineRecipe,ItemGreedyConsumer[]> matchNextMultiRecipe(BlockMenu inv ,int[] slots,List<MachineRecipe> recipes,boolean useHistory,int limit,Settings order,ItemPusherProvider pusher){

        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len = slots.length;
        ArrayList<ItemStack> slotNotNull=new ArrayList<>(len);
        ItemStack it;
        for(int i=0;i<len;++i){
            it=inv.getItemInSlot(slots[i]);
            if(it!=null){
                slotNotNull.add(it);
            }
        }
        DynamicArray<ItemPusher> slotCounter=new DynamicArray<>(ItemPusher[]::new,slotNotNull.size(),pusher.getMenuInstance(Settings.INPUT,inv,slotNotNull));
        int recipeAmount=recipes.size();
        if(recipeAmount<=0){
            return null;
        }
        int __index=0;
        //if usehistory ,will suggest a better place to start
        if(useHistory) {
            __index= DataCache.getLastRecipe(inv.getLocation());
            __index=(__index<0)?0:__index;
            __index=(__index>=recipeAmount)?(recipeAmount-1):__index;
        }
        int __iter=__index;
        MachineRecipe checkRecipe=recipes.get(__iter);
        ItemGreedyConsumer[] result=matchMultiRecipe(slotCounter,checkRecipe,limit);
        if(result!=null) {
            if(useHistory) {
                DataCache.setLastRecipe(inv.getLocation(),__iter);
            }
            return new Pair<>(checkRecipe,result);

        }
        __iter+=delta;
        for(;__iter<recipeAmount&&__iter>=0;__iter+=delta){
            checkRecipe=recipes.get(__iter);
            result=matchMultiRecipe(slotCounter,checkRecipe,limit);
            if(result!=null) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return new Pair<>(checkRecipe,result);

            }
        }
        if(__iter<0){
            __iter=recipeAmount-1;
        }else{
            __iter=0;
        }
        for(;__iter!=__index;__iter+=delta) {
            checkRecipe=recipes.get(__iter);
            result=matchMultiRecipe(slotCounter,checkRecipe,limit);
            if(result!=null) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return new Pair<>(checkRecipe,result);

            }
        }
        return null;
    }
    public static int calMaxCraftTime(ItemGreedyConsumer[] recipes,int limit){
        int len=recipes.length;
        if(len!=0) {
            for(int i=0;i<len;++i){
                limit=Math.min(limit,recipes[i].getStackNum());
            }
            return limit;
        }else return limit;
    }
    public static int calMaxCraftAfterAccelerate(int maxCraftTime,int tick){
        return tick==0?maxCraftTime:((maxCraftTime<=tick)?1:(maxCraftTime/(tick+1)));
    }
    /**
     *it will continue search and at the same time CACULATE the max craftTime for EVERY recipe .
     * cost TOO MUCH,deprecated! use matchNextMultiRecipe+calmaxamount+updateInputMenu instead
     * @param inv
     * @param inputs
     * @param outputs
     * @param recipes
     * @param limit
     * @param useHistory
     * @return
     */
    /**
     public static Pair<MachineRecipe,ItemGreedyConsumer[]> findNextMultiRecipe(BlockMenu inv,int[] inputs,int[] outputs,
     List<MachineRecipe> recipes,int limit,boolean useHistory){
     return findNextMultiRecipe(inv,inputs,outputs,recipes,limit,useHistory,Settings.SEQUNTIAL);
     }
     public static Pair<MachineRecipe,ItemGreedyConsumer[]> findNextMultiRecipe(BlockMenu inv,int[] inputs,int[] outputs,
     List<MachineRecipe> recipes,int limit,boolean useHistory,Settings order){
     return findNextMultiRecipe(inv,inputs,outputs,recipes,limit,useHistory,Settings.SEQUNTIAL,getpusher);
     }
     public static Pair<MachineRecipe,ItemGreedyConsumer[]> findNextMultiRecipe(BlockMenu inv,int[] inputs,int[] outputs,
     List<MachineRecipe> recipes,int limit,boolean useHistory,Settings order,ItemPusherProvider pusher){
     int delta;
     switch(order){
     case REVERSE:delta=-1;break;
     case SEQUNTIAL:
     default: delta=1;break;
     }
     int len = inputs.length;
     final ArrayList<ItemPusher> slotCounter=new ArrayList<>(len);
     for(int i = 0; i < len; ++i) {
     ItemPusher input=pusher.get(Settings.INPUT,inv,inputs[i]);
     if(input!=null){
     slotCounter.add(input);
     }
     }
     //end before anything
     int outlen=outputs.length;
     int recipeAmount=recipes.size();
     int __index=0;
     //if usehistory ,will suggest a better place to start
     if(useHistory) {
     __index= AbstractMachines.getLastRecipe(inv.getLocation());
     __index=(__index<0)?0:__index;
     __index=(__index>=recipeAmount)?(recipeAmount-1):__index;
     }
     int __iter=__index;
     MachineRecipe checkRecipe=recipes.get(__iter);
     final ItemSlotPusher[] outPushers=new ItemSlotPusher[outlen];
     for (int i=0;i<outlen;++i){
     outPushers[i]=ItemSlotPusher.get(inv.getItemInSlot(outputs[i]),outputs[i]);
     }
     Pair<ItemGreedyConsumer[],ItemGreedyConsumer[]> tmp=countMultiRecipe(slotCounter,outPushers,checkRecipe,limit);
     if(tmp!=null) {
     multiUpdateInputMenu(tmp.getFirstValue(),inv);
     if(useHistory) {
     AbstractMachines.setLastRecipe(inv.getLocation(),__iter);
     }
     return new Pair<>(checkRecipe,tmp.getSecondValue());
     }
     __iter+=delta;
     for(;__iter<recipeAmount&&__iter>=0;__iter+=delta){
     checkRecipe=recipes.get(__iter);
     tmp=countMultiRecipe(slotCounter,outPushers,checkRecipe,limit);
     if(tmp!=null) {
     multiUpdateInputMenu(tmp.getFirstValue(),inv);
     if(useHistory) {
     AbstractMachines.setLastRecipe(inv.getLocation(),__iter);
     }
     return new Pair<>(checkRecipe,tmp.getSecondValue());
     }
     }
     if(__iter<0){
     __iter=recipeAmount-1;
     }else{
     __iter=0;
     }
     for(;__iter!=__index;__iter+=delta) {
     checkRecipe=recipes.get(__iter);
     tmp=countMultiRecipe(slotCounter,outPushers,checkRecipe,limit);
     if(tmp!=null) {
     multiUpdateInputMenu(tmp.getFirstValue(),inv);
     if(useHistory) {
     AbstractMachines.setLastRecipe(inv.getLocation(),__iter);
     }
     return new Pair<>(checkRecipe,tmp.getSecondValue());
     }
     }
     return null;
     }
     **/

    public static int matchShapedRecipe(ItemPusher[] input,MachineRecipe recipe,int limit){
        ItemStack[] recipeInput=recipe.getInput();
        int len=input.length;
        int len2=recipeInput.length;
        if(len<len2) return 0;
        int max=limit;
        for(int i=0;i<len2;++i){
            if(input[i]==null){
                if(recipeInput[i]==null){
                    continue;
                }else return 0;
            }
            else if(!matchItemStack(recipeInput[i],input[i],false)) return 0;
            else{
                max=Math.min(max,input[i].getAmount()/recipeInput[i].getAmount());
            }
        }
        return max;
    }
    public static Pair<MachineRecipe, IntFunction<ItemGreedyConsumer[]>> matchNextShapedRecipe(BlockMenu inv, int[] inputs, List<MachineRecipe> recipes, int limit, boolean useHistory, Settings order, ItemPusherProvider pusher){
        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len = inputs.length;
        ItemPusher[] inputItem=new ItemPusher[len];
        IntFunction<ItemPusher> inputSlotInstance=pusher.getMenuInstance(Settings.INPUT,inv,inputs);
        for(int i=0;i<len;++i){
            inputItem[i]=inputSlotInstance.apply(i);
        }
        //end before anything
        if(len==0)return null;
        int recipeAmount=recipes.size();
        int __index=0;
        //if usehistory ,will suggest a better place to start
        if(useHistory) {
            __index= DataCache.getLastRecipe(inv.getLocation());
            __index=(__index<0)?0:__index;
            __index=(__index>=recipeAmount)?(recipeAmount-1):__index;
        }

        int __iter=__index;
        MachineRecipe checkRecipe=recipes.get(__iter);
        int craftAmount=matchShapedRecipe(inputItem,checkRecipe,limit);
        if(craftAmount>0) {
            final int finalAmount=craftAmount;
            final MachineRecipe finalRecipe=checkRecipe;
            final int index=__iter;
            return new Pair<>(checkRecipe,(newLimit)->{
                int amount=Math.min(newLimit,finalAmount);
                ItemStack[] recipeInput=finalRecipe.getInput();
                int len2=recipeInput.length;
                for(int i=0;i<len2;++i){
                    if(inputItem[i]!=null){
                        inputItem[i].setAmount(inputItem[i].getAmount()-amount*recipeInput[i].getAmount());
                        inputItem[i].updateMenu(inv);
                    }
                }
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),index);
                }
                ItemStack[] rpOut=finalRecipe.getOutput();
                ItemGreedyConsumer[] output=new ItemGreedyConsumer[rpOut.length];
                for(int i=0;i<output.length;++i){
                    output[i]=getGreedyConsumer(rpOut[i]);
                    output[i].setStackNum(amount);
                }
                return output;
            });
        }
        __iter+=delta;
        for(;__iter<recipeAmount&&__iter>=0;__iter+=delta){
            checkRecipe=recipes.get(__iter);
            craftAmount=matchShapedRecipe(inputItem,checkRecipe,limit);
            if(craftAmount>0) {
                final int finalAmount=craftAmount;
                final MachineRecipe finalRecipe=checkRecipe;
                final int index=__iter;
                return new Pair<>(checkRecipe,(newLimit)->{
                    int amount=Math.min(newLimit,finalAmount);
                    ItemStack[] recipeInput=finalRecipe.getInput();
                    int len2=recipeInput.length;
                    for(int i=0;i<len2;++i){
                        if(inputItem[i]!=null){
                            inputItem[i].setAmount(inputItem[i].getAmount()-amount*recipeInput[i].getAmount());
                            inputItem[i].updateMenu(inv);
                        }
                    }
                    if(useHistory) {
                        DataCache.setLastRecipe(inv.getLocation(),index);
                    }
                    ItemStack[] rpOut=finalRecipe.getOutput();
                    ItemGreedyConsumer[] output=new ItemGreedyConsumer[rpOut.length];
                    for(int i=0;i<output.length;++i){
                        output[i]=getGreedyConsumer(rpOut[i]);
                        output[i].setStackNum(amount);
                    }
                    return output;
                });
            }
        }
        if(__iter<0){
            __iter=recipeAmount-1;
        }else{
            __iter=0;
        }
        for(;__iter!=__index;__iter+=delta) {
            checkRecipe=recipes.get(__iter);
            craftAmount=matchShapedRecipe(inputItem,checkRecipe,limit);
            if(craftAmount>0) {
                final int finalAmount=craftAmount;
                final MachineRecipe finalRecipe=checkRecipe;
                final int index=__iter;
                return new Pair<>(checkRecipe,(newLimit)->{
                    int amount=Math.min(newLimit,finalAmount);
                    ItemStack[] recipeInput=finalRecipe.getInput();
                    int len2=recipeInput.length;
                    for(int i=0;i<len2;++i){
                        if(inputItem[i]!=null){
                            inputItem[i].setAmount(inputItem[i].getAmount()-amount*recipeInput[i].getAmount());
                            inputItem[i].updateMenu(inv);
                        }
                    }
                    if(useHistory) {
                        DataCache.setLastRecipe(inv.getLocation(),index);
                    }
                    ItemStack[] rpOut=finalRecipe.getOutput();
                    ItemGreedyConsumer[] output=new ItemGreedyConsumer[rpOut.length];
                    for(int i=0;i<output.length;++i){
                        output[i]=getGreedyConsumer(rpOut[i]);
                        output[i].setStackNum(amount);
                    }
                    return output;
                });
            }
        }
        return null;
    }

    public static Pair<MachineRecipe,ItemGreedyConsumer[]> countShapedRecipe(BlockMenu inv, ItemPusher[] inputItem, int[] outputs, MachineRecipe checkRecipe, int craftAmount, ItemPusherProvider pusher){
        int finalAmount=craftAmount;
        ItemGreedyConsumer[] outputCounters=null;
        if(outputs.length!=0){
            outputCounters=countMultiOutput(new ItemGreedyConsumer[]{},inv,outputs,checkRecipe,craftAmount,pusher);
            if(outputCounters!=null)
                finalAmount=outputCounters[0].getStackNum();
            else return null;
        }
        ItemStack[] recipeInput=checkRecipe.getInput();
        int len2=recipeInput.length;
        for(int i=0;i<len2;++i){
            if(inputItem[i]!=null){
                inputItem[i].setAmount(inputItem[i].getAmount()-finalAmount*recipeInput[i].getAmount());
                inputItem[i].updateMenu(inv);
            }
        }
        return new Pair<>(checkRecipe, outputCounters);
    }
//    public static Pair<MachineRecipe,ItemGreedyConsumer[]> findNextShapedRecipe(BlockMenu inv,int[] inputs,int[] outputs,
//                                                                                List<MachineRecipe> recipes,int limit,boolean useHistory){
//        return findNextShapedRecipe(inv,inputs,outputs,recipes,limit,useHistory,Settings.SEQUNTIAL);
//    }

    public static Pair<MachineRecipe,ItemGreedyConsumer[]> matchShapedRecipe(BlockMenu inv,int[] inputs,int[] outputs,
                                                                                List<MachineRecipe> recipes,int limit,boolean useHistory,Settings order,ItemPusherProvider pusher){
        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len = inputs.length;
        ItemPusher[] inputItem=new ItemPusher[len];
        IntFunction<ItemPusher> inputSlotInstance=pusher.getMenuInstance(Settings.INPUT,inv,inputs);
        for(int i=0;i<len;++i){
            inputItem[i]=inputSlotInstance.apply(i);
        }
        //end before anything
        if(len==0)return null;
        int outlen=outputs.length;
        int recipeAmount=recipes.size();
        int __index=0;
        //if usehistory ,will suggest a better place to start
        if(useHistory) {
            __index= DataCache.getLastRecipe(inv.getLocation());
            __index=(__index<0)?0:__index;
            __index=(__index>=recipeAmount)?(recipeAmount-1):__index;
        }

        int __iter=__index;
        MachineRecipe checkRecipe=recipes.get(__iter);
        int craftAmount=matchShapedRecipe(inputItem,checkRecipe,limit);
        if(craftAmount>0) {

            if(useHistory) {
                DataCache.setLastRecipe(inv.getLocation(),__iter);
            }
            return  countShapedRecipe(inv, inputItem, outputs, checkRecipe, craftAmount, pusher);
        }
        __iter+=delta;
        for(;__iter<recipeAmount&&__iter>=0;__iter+=delta){
            checkRecipe=recipes.get(__iter);
            craftAmount=matchShapedRecipe(inputItem,checkRecipe,limit);
            if(craftAmount>0) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return  countShapedRecipe(inv, inputItem, outputs, checkRecipe, craftAmount, pusher);
            }
        }
        if(__iter<0){
            __iter=recipeAmount-1;
        }else{
            __iter=0;
        }
        for(;__iter!=__index;__iter+=delta) {
            checkRecipe=recipes.get(__iter);
            craftAmount=matchShapedRecipe(inputItem,checkRecipe,limit);
            if(craftAmount>0) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return  countShapedRecipe(inv, inputItem, outputs, checkRecipe, craftAmount, pusher);
            }
        }
        return null;

    }

    public static MachineRecipe findNextShapedRecipe(BlockMenu inv,int[] inputs,int[] outputs,
                                                                                List<MachineRecipe> recipes,boolean useHistory,Settings order,ItemPusherProvider pusher){
        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len = inputs.length;
        ItemPusher[] inputItem=new ItemPusher[len];
        IntFunction<ItemPusher> inputSlotInstance=pusher.getMenuInstance(Settings.INPUT,inv,inputs);
        for(int i=0;i<len;++i){
            inputItem[i]=inputSlotInstance.apply(i);
        }
        //end before anything
        if(len==0)return null;
        int outlen=outputs.length;
        int recipeAmount=recipes.size();
        int __index=0;
        //if usehistory ,will suggest a better place to start
        if(useHistory) {
            __index= DataCache.getLastRecipe(inv.getLocation());
            __index=(__index<0)?0:__index;
            __index=(__index>=recipeAmount)?(recipeAmount-1):__index;
        }

        int __iter=__index;
        MachineRecipe checkRecipe=recipes.get(__iter);
        int craftAmount=matchShapedRecipe(inputItem,checkRecipe,1);
        if(craftAmount>0) {
            if(useHistory) {
                DataCache.setLastRecipe(inv.getLocation(),__iter);
            }
            return checkRecipe;
        }
        __iter+=delta;
        for(;__iter<recipeAmount&&__iter>=0;__iter+=delta){
            checkRecipe=recipes.get(__iter);
            craftAmount=matchShapedRecipe(inputItem,checkRecipe,1);
            if(craftAmount>0) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return checkRecipe;
            }
        }
        if(__iter<0){
            __iter=recipeAmount-1;
        }else{
            __iter=0;
        }
        for(;__iter!=__index;__iter+=delta) {
            checkRecipe=recipes.get(__iter);
            craftAmount=matchShapedRecipe(inputItem,checkRecipe,1);
            if(craftAmount>0) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return checkRecipe;
            }
        }
        return null;

    }


    public static boolean matchSequenceRecipeTarget(ItemPusher[] inPushers,ItemConsumer target){
        boolean hasChange=false;
        int len=inPushers.length;
        if(target.getAmount()<=0)return false;
        for(int i=0;i<len;++i){
            if(inPushers[i]==null||inPushers[i].getAmount()==0){
                continue;
            }else if(matchItemCounter(target,inPushers[i],false)){
                hasChange=true;
                target.consume(inPushers[i]);
                if(target.getAmount()<=0){
                    break;
                }
            }
        }
        return hasChange;
    }
    /**
     * sequence CRAFT, match itemstack with the first
     */
    public static Pair<MachineRecipe,ItemConsumer> findNextSequenceRecipe(BlockMenu inv,int[] inputs,List<MachineRecipe> recipes,boolean useHistory,Settings order,ItemPusherProvider pusher,boolean clearInput){
        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len=inputs.length;
        ItemPusher[] inputCounters=new ItemPusher[len];
        IntFunction<ItemPusher> pusherFunc=pusher.getMenuInstance(Settings.INPUT,inv,inputs);
        for(int i=0;i<len;++i){
            inputCounters[i]=pusherFunc.apply(i);
        }
        if(len==0)return null;
        int recipeAmount=recipes.size();
        int __index=0;
        //if usehistory ,will suggest a better place to start
        if(useHistory) {
            __index= DataCache.getLastRecipe(inv.getLocation());
            __index=(__index<0)?0:__index;
            __index=(__index>=recipeAmount)?(recipeAmount-1):__index;
        }
        int __iter=__index;
        MachineRecipe checkRecipe=recipes.get(__iter);
        ItemConsumer result=CraftUtils.getConsumer(checkRecipe.getInput()[0]);
        if(matchSequenceRecipeTarget(inputCounters,result)) {
            if(useHistory) {
                DataCache.setLastRecipe(inv.getLocation(),__iter);
            }
            return new Pair<>(checkRecipe,result);
        }
        __iter+=delta;
        for(;__iter<recipeAmount&&__iter>=0;__iter+=delta){
            checkRecipe=recipes.get(__iter);
            result=CraftUtils.getConsumer(checkRecipe.getInput()[0]);
            if(matchSequenceRecipeTarget(inputCounters,result)) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return new Pair<>(checkRecipe,result);
            }
        }
        if(__iter<0){
            __iter=recipeAmount-1;
        }else{
            __iter=0;
        }
        for(;__iter!=__index;__iter+=delta) {
            checkRecipe=recipes.get(__iter);
            result=CraftUtils.getConsumer(checkRecipe.getInput()[0]);
            if(matchSequenceRecipeTarget(inputCounters,result)) {
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return new Pair<>(checkRecipe,result);
            }
        }
        if(clearInput){
            clearAmount(inv,inputCounters);
        }
        return null;
    }
    /**
     *
     * @param counter1
     * @param counter2
     * @param strictCheck
     * @return
     */
    public static boolean matchItemCounter_test(ItemCounter counter1, ItemCounter counter2, boolean strictCheck){
        return matchItemCounter_test(counter1, counter2, strictCheck);
        }
    public static boolean matchItemCounter(ItemCounter counter1, ItemCounter counter2, boolean strictCheck){
         return matchItemCore(counter1,counter2,strictCheck);
    }
    //
    public static boolean matchItemCore(ItemCounter counter1, ItemCounter counter2, boolean strictCheck) {

        ItemStack stack1=counter1.getItem();
        ItemStack stack2=counter2.getItem();
        if (stack1 == null || stack2 == null) {
            return stack1 == stack2;
        }
        if(stack1 instanceof MultiItemStack) {
            return ((MultiItemStack) stack1).matchItem(stack2,strictCheck);
        }else if (stack2 instanceof MultiItemStack) {
            return ((MultiItemStack) stack2).matchItem(stack1,strictCheck);
        }
        //match material
        Material material=stack1.getType();
        if (material != stack2.getType()) {
            return false;
        }
        ItemMeta meta1=   counter1.getMeta();
        ItemMeta meta2=    counter2.getMeta();
        if(meta1==null||meta2==null ) {
            return meta2==meta1;
        }else if(meta1.getClass()!=meta2.getClass()){
            return false;
        }

        //if indistinguishable meta all return false
        if(INDISTINGUISHABLE_MATERIALS.contains(material)){
            return false;
        }
        //我们使用这个
        if( !me.matl114.matlib.utils.CraftUtils. matchDisplayNameField(meta1,meta2)) {
            return false;
        }
        final boolean hasCustomOne = meta1.hasCustomModelData();
        final boolean hasCustomTwo = meta2.hasCustomModelData();
        if (hasCustomOne) {
            if (!hasCustomTwo || meta1.getCustomModelData() != meta2.getCustomModelData()) {
                return false;
            }
        } else if (hasCustomTwo) {
            return false;
        }
//        //match display name
//        if(!(!meta1.hasDisplayName() || (meta1.getDisplayName().equals(meta2.getDisplayName())))) {
//            return false;
//        }
        //match display name

        //check important metas
        if(canQuickEscapeMetaVariant(meta1,meta2)){
            return false;
        }
        if(COMPLEX_MATERIALS.contains(material)){
            if(EnvironmentManager.getManager().getVersioned().differentSpecialMeta(meta1,meta2)){
                return false;
            }
        }
        //check pdc
        if (!meta1.getPersistentDataContainer().equals(meta2.getPersistentDataContainer())) {
            return false;
        }

        //如果非严格并且是sfid物品比较
        String id = parseSfId(meta1);
        if (id != null) {
            SlimefunItem it=SlimefunItem.getById(id);
            //自动跳过当前附属的物品
            if( it instanceof CustomSlimefunItem ){
                return true;
            }
            //distinctive物品必须判断
            if(it instanceof DistinctiveItem dt){
                return dt.canStack(meta1,meta2);
            }
            if(!strictCheck){
                return true;
            }
        }

        //粘液物品一般不可修改displayName和Lore
        //不然则全非sf物品



        if(!meta1.hasLore()||!meta2.hasLore()){
            return meta1.hasLore()==meta2.hasLore();
        }
        if ( !me.matl114.matlib.utils.CraftUtils. matchLoreField(meta1, meta2)) {
            return false;
            //对于普通物品 检查完lore就结束是正常的
        }else if(!strictCheck){
            return true;
        }
        // Make sure enchantments match
        if (!meta1.getEnchants().equals(meta2.getEnchants())) {
            return false;
        }
        //custommodeldata
        if(!matchAttrbuteField(meta1,meta2)){
            return false;
//        final boolean hasAttributeOne = meta1.hasAttributeModifiers()
        };
//        final boolean hasAttributeTwo = meta2.hasAttributeModifiers();
//        if (hasAttributeOne) {
//            if (!hasAttributeTwo || !Objects.equals(meta1.getAttributeModifiers(),meta2.getAttributeModifiers())) {
//                return false;
//            }
//        } else if (hasAttributeTwo) {
//            return false;
//        }
        return true;
    }
    public static boolean matchAttrbuteField(ItemMeta meta1,ItemMeta meta2){
        final boolean hasAttributeOne = meta1.hasAttributeModifiers();
        final boolean hasAttributeTwo = meta2.hasAttributeModifiers();
        if (hasAttributeOne) {
            if (!hasAttributeTwo || !Objects.equals(meta1.getAttributeModifiers(),meta2.getAttributeModifiers())) {
                return false;
            }
        } else if (hasAttributeTwo) {
            return false;
        }
        return true;
    }

//    public static boolean matchLoreField(ItemMeta meta1, ItemMeta meta2){
//        return loreAccess.compareFieldOrDefault(meta1,meta2,()->matchLore(meta1.getLore(),meta2.getLore(),false));
//    }

//    public static ItemMeta getItemMeta(ItemStack it){
////        if(!INVOKE_STACK_SUCCESS)return it.getItemMeta();
////        if(CRAFTITEMSTACKCLASS.isInstance(it)){
////            return it.getItemMeta();
////        }
//        if(it.getClass()!=CRAFTITEMSTACKCLASS) {
//            try{
//            return (ItemMeta) ITEMSTACKMETA.get(it);
//            }catch (Throwable e){
//
//            }
//        }
//        return it.getItemMeta();
//
//    }
//    public static boolean matchDisplayNameField(ItemMeta meta1, ItemMeta meta2){
//        return displayNameAccess//displayNameAccess.compareFieldOrDefault(meta1,meta2,()->Objects.equals(meta1.getDisplayName(),meta2.getDisplayName()));
//    }
//    private static Class CraftMetaBlockState;

    public static boolean matchBlockStateMetaField(BlockStateMeta meta1, BlockStateMeta meta2){
        return EnvironmentManager.getManager().getVersioned().matchBlockStateMeta(meta1,meta2);
//        return blockEntityTagAccess.ofAccess(meta1).computeIf((b)->{
//            return Objects.equals(b, blockEntityTagAccess.ofAccess(meta2).getRawOrDefault(()->null));
//        },()->meta1.equals(meta2));
    }
    public static boolean amountLargerThan(ItemStack thisItem,ItemStack thatItem){
        if(thisItem==null||thatItem==null){
            return thisItem==thatItem;
        }else{
            return thisItem.getAmount()>=thatItem.getAmount();
        }
    }
    public static void consumeThat(ItemStack thisItem,ItemStack thatItem){
        if(thisItem!=null&&thatItem!=null){
            thatItem.setAmount(thatItem.getAmount()-thisItem.getAmount());
        }
    }
    public static void consumeThat(int amount ,ItemStack thatItem){
        if(thatItem!=null){
            thatItem.setAmount(thatItem.getAmount()-amount);
        }
    }
    public static String getItemName(@Nonnull ItemStack item){
        if(item.hasItemMeta()){
            ItemMeta meta = item.getItemMeta();
            if(meta !=null && meta.hasDisplayName()){
                return meta.getDisplayName();
            }
        }
        try{
            return ItemStackHelper.getName(item);
        }catch (Throwable guizhanLibVersionNotSupport){
            try{
                return item.getI18NDisplayName();
            }catch (Throwable paperVersionNotSupport){
                return item.getType().getKey().getKey();
            }
        }


    }
    public static String getEffectName(PotionEffectType effect){
        try{
            PotionEffectTypeHelper.getName(effect);
        }catch (Throwable e){}
        return effect.getKey().getKey();
    }

    public static boolean matchItemStack(ItemStack stack1, ItemStack stack2,boolean strictCheck){
        if(stack1==null || stack2==null){
            return stack1 == stack2;
        }else {
            return matchItemCore(getCounter(stack1),getCounter(stack2),strictCheck);
        }
    }
    public static boolean matchItemStack(ItemStack counter1,ItemCounter counter2,boolean strictCheck){
        if(counter1==null ){
            return counter2==null|| counter2.getItem()==null;
        }else {
            return matchItemCore(getCounter(counter1),counter2,strictCheck);
        }
    }
    public static boolean matchLore(List<String> lore1,List<String> lore2,boolean strictMod){
        if(strictMod){
            if(lore1==null || lore2==null){
                return lore1 == lore2;
            }
            if(lore1.size()!=lore2.size()){
                return false;
            }
            int len=lore1.size();
            String l1;
            String l2;
            for(int i=0;i<len;++i){
                l1=lore1.get(i);
                l2=lore2.get(i);
                if(l1.length()!=l2.length()){
                    return false;
                }
                if(!l1.equals(l2)){
                    return false;
                }
            }
            return true;
        }else{
            if(lore1==null || lore2==null){
                return lore1 == lore2;
            }
            if(lore1.size()!=lore2.size()){
                return false;
            }
            return lore1.hashCode()==lore2.hashCode();
            /**
             int len=lore1.size();
             String l1;
             String l2;
             for(int i=0;i<len;++i){
             l1=lore1.get(i);
             l2=lore2.get(i);
             if(l1.length()!=l2.length()){
             return false;
             }
             if(l1.hashCode()!=l2.hashCode()){
             return false;
             }
             }
             return true;**/
        }
    }


    /**
     * pieces of shit copied from Network
     * @param metaOne
     * @param metaTwo
     * @return
     */
    public static boolean canQuickEscapeMetaVariant(@Nonnull ItemMeta metaOne, @Nonnull ItemMeta metaTwo) {
        if (metaOne instanceof Damageable instanceOne && metaTwo instanceof Damageable instanceTwo) {
            if (instanceOne.hasDamage() != instanceTwo.hasDamage()) {
                return true;
            }

            if (instanceOne.getDamage() != instanceTwo.getDamage()) {
                return true;
            }
        }
        if (metaOne instanceof Repairable instanceOne && metaTwo instanceof Repairable instanceTwo) {
            if (instanceOne.hasRepairCost() != instanceTwo.hasRepairCost()) {
                return true;
            }

            if (instanceOne.getRepairCost() != instanceTwo.getRepairCost()) {
                return true;
            }
        }
        if (metaOne instanceof BlockDataMeta instanceOne ) {
            if(metaTwo instanceof BlockDataMeta instanceTwo){
                if (instanceOne.hasBlockData() != instanceTwo.hasBlockData()) {
                    return true;
                }
            }else return true;
        }
        return false;
    }



}






























