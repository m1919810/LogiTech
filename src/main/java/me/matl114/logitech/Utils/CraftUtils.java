package me.matl114.logitech.Utils;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.Utils.Algorithms.DynamicArray;

import me.matl114.logitech.Utils.UtilClass.ItemClass.*;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;

public class CraftUtils {
    private static final HashSet<Material> COMPLEX_MATERIALS = new HashSet<>(){{
        add(Material.AXOLOTL_BUCKET);
        add(Material.WRITABLE_BOOK);
        add(Material.WRITTEN_BOOK);
        add(Material.ENCHANTED_BOOK);
        add(Material.BUNDLE);
        add(Material.FIREWORK_STAR);
        add(Material.FIREWORK_ROCKET);
        add(Material.COMPASS);
        add(Material.LEATHER_BOOTS);
        add(Material.LEATHER_HELMET);
        add(Material.LEATHER_LEGGINGS);
        add(Material.LEATHER_CHESTPLATE);
        add(Material.MAP);
        add(Material.POTION);
        add(Material.SPLASH_POTION);
        add(Material.LINGERING_POTION);
        add(Material.SUSPICIOUS_STEW);
        add(Material.COD_BUCKET);
        add(Material.SALMON_BUCKET);
        add(Material.TADPOLE_BUCKET);
        add(Material.PUFFERFISH_BUCKET);
        add(Material.TROPICAL_FISH_BUCKET);
        add(Material.PLAYER_HEAD);
        add(Material.PLAYER_WALL_HEAD);
    }};
    private static final HashSet<Material> INDISTINGUISHABLE_MATERIALS = new HashSet<Material>() {{
        add(Material.SHULKER_BOX);
        add(Material.BUNDLE);
    }};
    public static final ItemMeta NULL_META=(new ItemStack(Material.STONE).getItemMeta());
    public static final Class CRAFTMETAITEMCLASS=NULL_META.getClass();
    public static Field CRAFTLORE;
    public static Field CRAFTDISPLAYNAME;
    public static boolean INVOKE_SUCCESS;
    static{
        try{
            CRAFTLORE=CRAFTMETAITEMCLASS.getDeclaredField("lore");
            CRAFTDISPLAYNAME=CRAFTMETAITEMCLASS.getDeclaredField("displayName");
            CRAFTLORE.setAccessible(true);
            CRAFTDISPLAYNAME.setAccessible(true);
            INVOKE_SUCCESS=true;
            //Debug.logger("INVOKE META SUCCESS");
        }catch (Throwable e){
            Debug.logger("INVOKE META FAILED,PLEASE CHECK LOGGER!!!!!!");
            INVOKE_SUCCESS=false;
            e.printStackTrace();
            Debug.logger("DISABLING RELEVENT FEATURE");

        }
    }
    public static void setup(){

    }
    @Nullable
    public static String parseSfId(ItemStack item) {
        Optional<String> itemID = Slimefun.getItemDataService().getItemData(item);
        return itemID.isPresent() ? (String)itemID.get() : null;
    }
    public static String parseSfId(ItemMeta meta){
        Optional<String> itemID = Slimefun.getItemDataService().getItemData(meta);
        return itemID.isPresent() ? (String)itemID.get() : null;
    }
    /**
     * get Consumer for recipe Item
     * @param a
     * @return
     */
    public static ItemConsumer getConsumer(ItemStack a){
        if(a==null)return null;
        if (a instanceof RandomItemStack){
            // return new ItemConsumer(a.clone());
            return ItemConsumer.get(a.clone());
        }
        return ItemConsumer.get(a);
    }

    /**
     * get greedy consumer for recipe Item
     * @param a
     * @return
     */
    public static ItemGreedyConsumer getGreedyConsumer(ItemStack a){
        if(a==null)return null;
        if (a instanceof RandomItemStack){
            return  ItemGreedyConsumer.get(a.clone());
        }
        return ItemGreedyConsumer.get(a);
    }

    /**
     * a huge project to adapt sth...
     * use .get(mod,inv,slot) to get ItemPusher
     * mod should be in {Settings.INPUT,Settings.OUTPUT}
     */
    public static final  ItemPusherProvider getpusher=(Settings mod,BlockMenu inv,int slot)->{
        ItemStack it=inv.getItemInSlot(slot);
        if(mod==Settings.INPUT||it!=null){
            return ItemPusher.get(it);
        }else{
            return ItemSlotPusher.get(it,slot);
        }
    };
    public static ItemPusher getSlotPusher(BlockMenu inv,int slot){
        return getpusher.get(Settings.OUTPUT,inv,slot);
    }
    public static ItemPusher getPusher(ItemStack it){
        return  ItemPusher.get(it);
    }
    public static ItemPusher getPusher(BlockMenu inv,int slot){
        return getpusher.get(Settings.INPUT,inv,slot);
    }
    public static void clearAmount(ItemPusher ... counters){
        for (int i =0;i<counters.length;++i){
            counters[i].setAmount(0);
            //this is safe,I said it
            counters[i].updateMenu(null);
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
        for(int i=0;i<cnt;++i) {
            result[i]=getConsumer(recipeInput[i]);
            results=result[i];
            for(int j=0;j<len2;++j) {
                itemCounter2=slotCounters.get(j);
                if(itemCounter2==null)continue;
                if(i==0){
                    itemCounter2.syncData();
                }
                if(CraftUtils.matchItemCounter(results,itemCounter2,false)){
                    results.consume(itemCounter2);
                    if(results.getAmount()<=0)break;
                }
            }
            if(results.getAmount()>0){
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
        for(int i=0;i<cnt;++i) {
            ItemGreedyConsumer itemCounter=getGreedyConsumer(recipeInput[i]);
            for(int j=0;j<len2;++j) {
                ItemPusher itemCounter2=slotCounters.get(j);
                if(itemCounter2==null)continue;
                if(i==0){
                    itemCounter2.syncData();
                }
                if(CraftUtils.matchItemCounter(itemCounter,itemCounter2,false)){
                    itemCounter.consume(itemCounter2);
                    if(itemCounter.getStackNum()>=maxMatchCount)break;
                }
            }
            //不够一份的量
            if(itemCounter.getAmount()>itemCounter.getMatchAmount()){
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
        DynamicArray<ItemPusher> slotCounters=new DynamicArray<>(ItemPusher[]::new,len2,(i)->(pusher.get(Settings.OUTPUT,inv,output[i])));
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
        DynamicArray<ItemPusher> inputs=new DynamicArray<>(ItemPusher[]::new,len,(i)->pusher.get(Settings.INPUT,inv,input[i]));

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
        DynamicArray<ItemPusher> inputCounters=new DynamicArray<>(ItemPusher[]::new,len,(i)->pusher.get(Settings.INPUT,inv,input[i]));
        int cnt=recipeInput.length;

        ItemGreedyConsumer[] recipeCounter=new ItemGreedyConsumer[cnt];
        int maxAmount=limit;
        for(int i=0;i<cnt;++i) {
            recipeCounter[i]=getGreedyConsumer(recipeInput[i]);
            for(int j=0;j<len;++j) {
                ItemPusher  itemCounter2=inputCounters.get(j);
                if(itemCounter2==null)continue;
                if(i==0){
                    itemCounter2.syncData();
                }
                if(itemCounter2.isDirty()){
                    //如果该counter已经被人绑定了 就跳过
                    continue;
                }
                else if(CraftUtils.matchItemCounter(itemCounter2,recipeCounter[i],false)){
                    //如果匹配 将其加入...list,并算入matchCnt
                    recipeCounter[i].addRelate(itemCounter2);
                    recipeCounter[i].addMatchAmount(itemCounter2.getAmount());
                }
                if(recipeCounter[i].getStackNum()>=maxAmount)break;
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
    public static ItemGreedyConsumer[] countMultiOutput(ItemGreedyConsumer[] inputInfo, BlockMenu inv, int[] output, MachineRecipe recipe, int limit,ItemPusherProvider pusher){

        int len2=output.length;
        DynamicArray<ItemPusher> outputCounters=new DynamicArray<>(ItemPusher[]::new,len2,(i)->(pusher.get(Settings.OUTPUT,inv,output[i])));
        int outputSlotpointer=0;
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
                    ItemPusher itemCounter=pusher.get(Settings.OUTPUT,inv,output[i]);
                    if(itemCounter.getItem()==null){
                        recipeCounter2[0].addRelate(itemCounter);
                        recipeCounter2[0].addMatchAmount(recipeCounter2[0].getItem().getMaxStackSize());
                    }
                    else if(itemCounter.getMaxStackCnt()==itemCounter.getAmount()){
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
                    itemCounter2.addRelate(itemCounter);
                    itemCounter2.addMatchAmount(itemCounter2.getItem().getMaxStackSize());
                    hasNextPushSlot=true;
                    break;
                }
                else if(itemCounter.isDirty()||itemCounter.getMaxStackCnt()==itemCounter.getAmount()){
                    continue;
                }
                else if(CraftUtils.matchItemCounter(itemCounter2,itemCounter,false)){
                    itemCounter2.addRelate(itemCounter);
                    itemCounter2.addMatchAmount(itemCounter2.getItem().getMaxStackSize()-itemCounter.getAmount());
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
                    recipeCounter2[0].addRelate(itemCounter);
                    recipeCounter2[0].addMatchAmount(recipeCounter2[0].getItem().getMaxStackSize());
                }
                else if(itemCounter.getMaxStackCnt()==itemCounter.getAmount()){
                    continue;
                }
                else if(CraftUtils.matchItemCounter(recipeCounter2[0],itemCounter,false)){
                    recipeCounter2[0].addRelate(itemCounter);
                    recipeCounter2[0].addMatchAmount(recipeCounter2[0].getItem().getMaxStackSize()-itemCounter.getAmount());
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
                    if(itemCounter.isDirty()||itemCounter.getMaxStackCnt()==itemCounter.getAmount()){
                        continue;
                    }
                    else if(CraftUtils.matchItemCounter(itemCounter2,itemCounter,false)){
                        itemCounter2.addRelate(itemCounter);
                        itemCounter2.addMatchAmount(itemCounter2.getItem().getMaxStackSize()-itemCounter.getAmount());
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
        DynamicArray<ItemPusher> slotCounters2=new DynamicArray<>(ItemPusher[]::new,slots.length,(i)->(pusher.get(Settings.OUTPUT,inv,slots[i])));
        ItemConsumer outputItem;
        ItemPusher itemCounter;
        int len=slotCounters.length;
        for(int i=0;i<len;++i) {
            outputItem=slotCounters[i];
            //consume mode
            outputItem.syncData();
            for(int j=0;j<slots.length;++j) {
                itemCounter=slotCounters2.get(j);
                if(!itemCounter.isDirty()){
                    if(itemCounter.getItem()==null){
                        outputItem.push(itemCounter);
//                        itemCounter.grab(outputItem);
//                        outputItem.addRelate(itemCounter);
                    }else if (itemCounter.getAmount()==itemCounter.getMaxStackCnt()){
                        continue;
                    }
                    else if(matchItemCounter(outputItem,itemCounter,false)){
                        outputItem.push(itemCounter);
//                        itemCounter.grab(outputItem);
//                        outputItem.addRelate(itemCounter);
                    }
                    if(outputItem.getAmount()<=0)break;
                }
            }
        }
        boolean hasChanged=false;
        for(int i=0;i< len;++i) {
            outputItem=slotCounters[i];
            outputItem.updateItems(inv,Settings.PUSH);
            if(outputItem.isDirty()){
                hasChanged=true;
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
        DynamicArray<ItemPusher> slotCounters2=new DynamicArray<>(ItemPusher[]::new,slots.length,(i)->(pusher.get(Settings.OUTPUT,inv,slots[i])));
        int len= slotCounters.length;
        ItemPusher itp=null;
        ItemGreedyConsumer outputItem;
        for(int i=0;i<len;++i) {
            outputItem=slotCounters[i];
            //consume mode
            for(int j=0;j<slots.length;++j) {
                itp=slotCounters2.get(j);
                if(!itp.isDirty()){
                    if(itp.getItem()==null){
                        outputItem.push(itp);
//                        itp.grab(outputItem);
//                        outputItem.addRelate(itp);
                    }else if (itp.getAmount()==itp.getMaxStackCnt()){
                        continue;
                    }
                    else if(matchItemCounter(outputItem,itp,false)){
                        outputItem.push(itp);
//                        itp.grab(outputItem);
//                        outputItem.addRelate(itp);
                    }
                    if(outputItem.getMatchAmount()<=0)break;
                }
            }
        }
        boolean hasChanged=false;
        for(int i=0;i<len;++i) {
//            itp=slotCounters2.get(i);
//            if(itp.isDirty()){
//                hasChanged=true;
//                itp.updateMenu(inv);
//            }
            outputItem=slotCounters[i];
            outputItem.updateItems(inv,Settings.PUSH);
            if(outputItem.isDirty()){
                hasChanged=true;
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
        final DynamicArray<ItemPusher> slotCounter=new DynamicArray<>(ItemPusher[]::new,len,(i)->(pusher.get(Settings.INPUT,inv,slots[i])));
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
        final DynamicArray<ItemPusher> slotCounter=new DynamicArray<>(ItemPusher[]::new,len,(i)->(pusher.get(Settings.INPUT,inv,slots[i])));
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

    /**
     * a better version of matchNextRecipe for multiCraft,will remember the related slots of inputItems
     * @param inv
     * @param slots
     * @param recipes
     * @param useHistory
     * @param order
     * @return
     */
    public static Pair<MachineRecipe,ItemGreedyConsumer[]> matchNextMultiRecipe(BlockMenu inv ,int[] slots,List<MachineRecipe> recipes,boolean useHistory,int limit,Settings order){
        return matchNextMultiRecipe(inv,slots,recipes,useHistory,limit,order,getpusher);
    }
    public static Pair<MachineRecipe,ItemGreedyConsumer[]> matchNextMultiRecipe(BlockMenu inv ,int[] slots,List<MachineRecipe> recipes,boolean useHistory,int limit,Settings order,ItemPusherProvider pusher){
        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len = slots.length;
        final DynamicArray<ItemPusher> slotCounter=new DynamicArray<>(ItemPusher[]::new,len,(i -> pusher.get(Settings.INPUT,inv,slots[i])));
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
            return Math.min(limit,recipes[0].getStackNum());
        }else return 0;
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

    public static int matchShapedRecipe(ItemStack[] input,MachineRecipe recipe,int limit){
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
            else if(!matchItemStack(input[i],recipeInput[i],false)) return 0;
            else{
                max=Math.min(max,input[i].getAmount()/recipeInput[i].getAmount());
            }
        }
        return max;
    }
    public static Pair<MachineRecipe,ItemGreedyConsumer[]> findNextShapedRecipe(BlockMenu inv,int[] inputs,int[] outputs,
                                                                                List<MachineRecipe> recipes,int limit,boolean useHistory){
        return findNextShapedRecipe(inv,inputs,outputs,recipes,limit,useHistory,Settings.SEQUNTIAL);
    }

    public static Pair<MachineRecipe,ItemGreedyConsumer[]> findNextShapedRecipe(BlockMenu inv,int[] inputs,int[] outputs,
                                                                                List<MachineRecipe> recipes,int limit,boolean useHistory,Settings order){
        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len = inputs.length;
        ItemStack[] inputItem=new ItemStack[len];
        for(int i=0;i<len;++i){
            inputItem[i]=inv.getItemInSlot(inputs[i]);
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
            int finalAmount=craftAmount;
            ItemGreedyConsumer[] outputCounters=null;
            if(outputs.length!=0){
                outputCounters=countMultiOutput(new ItemGreedyConsumer[]{},inv,outputs,checkRecipe,craftAmount);
                if(outputCounters!=null)
                    finalAmount=outputCounters[0].getStackNum();
                else return null;
            }
            ItemStack[] recipeInput=checkRecipe.getInput();
            int len2=recipeInput.length;
            for(int i=0;i<len2;++i){
                if(inputItem[i]!=null)
                    inputItem[i].setAmount(inputItem[i].getAmount()-finalAmount*recipeInput[i].getAmount());
            }
            if(useHistory) {
                DataCache.setLastRecipe(inv.getLocation(),__iter);
            }
            return new Pair<>(checkRecipe,outputCounters);
        }
        __iter+=delta;
        for(;__iter<recipeAmount&&__iter>=0;__iter+=delta){
            checkRecipe=recipes.get(__iter);
            craftAmount=matchShapedRecipe(inputItem,checkRecipe,limit);
            if(craftAmount>0) {
                int finalAmount=craftAmount;
                ItemGreedyConsumer[] outputCounters=null;
                if(outputs.length!=0){
                    outputCounters=countMultiOutput(new ItemGreedyConsumer[]{},inv,outputs,checkRecipe,craftAmount);
                    if(outputCounters!=null)
                        finalAmount=outputCounters[0].getStackNum();
                    else return null;
                }
                ItemStack[] recipeInput=checkRecipe.getInput();
                int len2=recipeInput.length;
                for(int i=0;i<len2;++i){
                    if(inputItem[i]!=null)
                        inputItem[i].setAmount(inputItem[i].getAmount()-finalAmount*recipeInput[i].getAmount());
                }
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return new Pair<>(checkRecipe,outputCounters);
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
                int finalAmount=craftAmount;
                ItemGreedyConsumer[] outputCounters=null;
                if(outputs.length!=0){
                    outputCounters=countMultiOutput(new ItemGreedyConsumer[]{},inv,outputs,checkRecipe,craftAmount);
                    if(outputCounters!=null)
                        finalAmount=outputCounters[0].getStackNum();
                    else return null;
                }
                ItemStack[] recipeInput=checkRecipe.getInput();
                int len2=recipeInput.length;
                for(int i=0;i<len2;++i){
                    if(inputItem[i]!=null)
                        inputItem[i].setAmount(inputItem[i].getAmount()-finalAmount*recipeInput[i].getAmount());
                }
                if(useHistory) {
                    DataCache.setLastRecipe(inv.getLocation(),__iter);
                }
                return new Pair<>(checkRecipe,outputCounters);
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
    public static Pair<MachineRecipe,ItemConsumer> findNextSequenceRecipe(BlockMenu inv,int[] inputs,List<MachineRecipe> recipes,boolean useHistory,ItemPusherProvider pusher,Settings order){
        int delta;
        switch(order){
            case REVERSE:delta=-1;break;
            case SEQUNTIAL:
            default: delta=1;break;
        }
        int len=inputs.length;
        ItemPusher[] inputCounters=new ItemPusher[len];
        for(int i=0;i<len;++i){
            inputCounters[i]=pusher.get(Settings.INPUT,inv,inputs[i]);
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
        return null;
    }
    /**
     *
     * @param counter1
     * @param counter2
     * @param strictCheck
     * @return
     */
    public static boolean matchItemCounter(ItemCounter counter1, ItemCounter counter2, boolean strictCheck) {
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
        if (stack1.getType() != stack2.getType()) {
            return false;
        }
        ItemMeta meta1=counter1.getMeta();
        ItemMeta meta2=counter2.getMeta();
        if(meta1==null||meta2==null ) {
            return meta2==meta1;
        }
        //if indistinguishable meta all return false
        if(INDISTINGUISHABLE_MATERIALS.contains(stack1.getType())){
            return false;
        }
//        //match display name
//        if(!(!meta1.hasDisplayName() || (meta1.getDisplayName().equals(meta2.getDisplayName())))) {
//            return false;
//        }
        //match display name
        if(!(!meta1.hasDisplayName() || matchDisplayNameOnInvoke(meta1,meta2))) {
            return false;
        }
        //check important metas
        if(canQuickEscapeMetaVariant(meta1,meta2)){
            return false;
        }
        //check pdc
        if (!meta1.getPersistentDataContainer().equals(meta2.getPersistentDataContainer())) {
            return false;
        }
        //stop check if not strict like itemmatch in recipeFinder and consumer
        if(!strictCheck)return true;
        //check special meta
//        if(INVOKE_SUCCESS)
//        return meta1.equals(meta2);
        if(!meta1.hasLore()||!meta2.hasLore()){
            return meta1.hasLore()==meta2.hasLore();
        }
        if ( !matchLoreOnInvoke(meta1, meta2)) {
            return false;
        }

        if(COMPLEX_MATERIALS.contains(stack1.getType())){
            if(canQuickEscapeMaterialVariant(meta1,meta2)){
                return false;
            }
        }
        // Make sure enchantments match
        if (!meta1.getEnchants().equals(meta2.getEnchants())) {
            return false;
        }
        //custommodeldata
        final boolean hasCustomOne = meta1.hasCustomModelData();
        final boolean hasCustomTwo = meta2.hasCustomModelData();
        if (hasCustomOne) {
            if (!hasCustomTwo || meta1.getCustomModelData() != meta2.getCustomModelData()) {
                return false;
            }
        } else if (hasCustomTwo) {
            return false;
        }

        return true;

    }
    public static boolean matchLoreOnInvoke(ItemMeta meta1,ItemMeta meta2){
        try{
            Object lore1= (CRAFTLORE.get(meta1));
            Object  lore2= (CRAFTLORE.get(meta2));
            //Debug.logger("invoke time cost "+(System.nanoTime()-a));

            return  Objects.equals(lore1,lore2);
            //Debug.logger("compare time cost "+(c-b));
        }catch (Throwable e){
            //Debug.logger("FAILED TO INVOKE ITEMMETA");r
            return matchLore(meta1.getLore(),meta2.getLore(),false);
        }
    }
    public static boolean matchDisplayNameOnInvoke(ItemMeta meta1,ItemMeta meta2){
        try{
            Object name1=(CRAFTDISPLAYNAME.get(meta1));
            Object name2=(CRAFTDISPLAYNAME.get(meta2));
            return name1.equals(name2);
        }catch (Throwable e){
            return meta1.getDisplayName().equals(meta2.getDisplayName());
        }
    }
    public static boolean matchItemStack(ItemStack stack1, ItemStack stack2,boolean strictCheck){
        if(stack1==null || stack2==null){
            return stack1 == stack2;
        }else {
            return matchItemCounter(getConsumer(stack1),getConsumer(stack2),strictCheck);
        }
    }
    public static boolean matchItemStack(ItemStack counter1,ItemCounter counter2,boolean strictCheck){
        if(counter1==null ){
            return counter2.getItem()==null;
        }else {
            return matchItemCounter(getConsumer(counter1),counter2,strictCheck);
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
     * can be later modified to card-implements (water card or storage card(great idea wtf))
     * @param ?
     * @return
     */
    public static boolean canQuickEscapeMaterialVariant(@Nonnull ItemMeta metaOne, @Nonnull ItemMeta metaTwo) {
        // Banner

        // Axolotl
        if (metaOne instanceof AxolotlBucketMeta instanceOne && metaTwo instanceof AxolotlBucketMeta instanceTwo) {
            if (instanceOne.hasVariant() != instanceTwo.hasVariant()) {
                return true;
            }

            if(!instanceOne.hasVariant() || !instanceTwo.hasVariant())
                return true;

            if (instanceOne.getVariant() != instanceTwo.getVariant()) {
                return true;
            }
        }

        // Books
        if (metaOne instanceof BookMeta instanceOne && metaTwo instanceof BookMeta instanceTwo) {
            if (instanceOne.getPageCount() != instanceTwo.getPageCount()) {
                return true;
            }
            if (!Objects.equals(instanceOne.getAuthor(), instanceTwo.getAuthor())) {
                return true;
            }
            if (!Objects.equals(instanceOne.getTitle(), instanceTwo.getTitle())) {
                return true;
            }
            if (!Objects.equals(instanceOne.getGeneration(), instanceTwo.getGeneration())) {
                return true;
            }
        }

        // Bundle
        if (metaOne instanceof BundleMeta instanceOne && metaTwo instanceof BundleMeta instanceTwo) {
            if (instanceOne.hasItems() != instanceTwo.hasItems()) {
                return true;
            }
            if (!instanceOne.getItems().equals(instanceTwo.getItems())) {
                return true;
            }
        }

        // Compass
        if (metaOne instanceof CompassMeta instanceOne && metaTwo instanceof CompassMeta instanceTwo) {
            if (instanceOne.isLodestoneTracked() != instanceTwo.isLodestoneTracked()) {
                return true;
            }
            if (!Objects.equals(instanceOne.getLodestone(), instanceTwo.getLodestone())) {
                return true;
            }
        }


        // Firework Star
        if (metaOne instanceof FireworkEffectMeta instanceOne && metaTwo instanceof FireworkEffectMeta instanceTwo) {
            if (!Objects.equals(instanceOne.getEffect(), instanceTwo.getEffect())) {
                return true;
            }
        }

        // Firework
        if (metaOne instanceof FireworkMeta instanceOne && metaTwo instanceof FireworkMeta instanceTwo) {
            if (instanceOne.getPower() != instanceTwo.getPower()) {
                return true;
            }
            if (!instanceOne.getEffects().equals(instanceTwo.getEffects())) {
                return true;
            }
        }

        // Leather Armor
        if (metaOne instanceof LeatherArmorMeta instanceOne && metaTwo instanceof LeatherArmorMeta instanceTwo) {
            if (!instanceOne.getColor().equals(instanceTwo.getColor())) {
                return true;
            }
        }

        // Maps
        if (metaOne instanceof MapMeta instanceOne && metaTwo instanceof MapMeta instanceTwo) {
            if (instanceOne.hasMapView() != instanceTwo.hasMapView()) {
                return true;
            }
            if (instanceOne.hasLocationName() != instanceTwo.hasLocationName()) {
                return true;
            }
            if (instanceOne.hasColor() != instanceTwo.hasColor()) {
                return true;
            }
            if (!Objects.equals(instanceOne.getMapView(), instanceTwo.getMapView())) {
                return true;
            }
            if (!Objects.equals(instanceOne.getLocationName(), instanceTwo.getLocationName())) {
                return true;
            }
            if (!Objects.equals(instanceOne.getColor(), instanceTwo.getColor())) {
                return true;
            }
        }

        // Potion
        if (metaOne instanceof PotionMeta instanceOne && metaTwo instanceof PotionMeta instanceTwo) {
            if (!instanceOne.getBasePotionData().equals(instanceTwo.getBasePotionData())) {
                return true;
            }
            if (instanceOne.hasCustomEffects() != instanceTwo.hasCustomEffects()) {
                return true;
            }
            if (instanceOne.hasColor() != instanceTwo.hasColor()) {
                return true;
            }
            if (!Objects.equals(instanceOne.getColor(), instanceTwo.getColor())) {
                return true;
            }
            if (!instanceOne.getCustomEffects().equals(instanceTwo.getCustomEffects())) {
                return true;
            }
        }

        // Skull
        if (metaOne instanceof SkullMeta instanceOne && metaTwo instanceof SkullMeta instanceTwo) {
            if (instanceOne.hasOwner() != instanceTwo.hasOwner()) {
                return true;
            }
            if (!Objects.equals(instanceOne.getOwningPlayer(), instanceTwo.getOwningPlayer())) {
                return true;
            }
        }

        // Stew
        if (metaOne instanceof SuspiciousStewMeta instanceOne && metaTwo instanceof SuspiciousStewMeta instanceTwo) {
            if (!Objects.equals(instanceOne.getCustomEffects(), instanceTwo.getCustomEffects())) {
                return true;
            }
        }

        // Fish Bucket
        if (metaOne instanceof TropicalFishBucketMeta instanceOne && metaTwo instanceof TropicalFishBucketMeta instanceTwo) {
            if (instanceOne.hasVariant() != instanceTwo.hasVariant()) {
                return true;
            }
            if (!instanceOne.getPattern().equals(instanceTwo.getPattern())) {
                return true;
            }
            if (!instanceOne.getBodyColor().equals(instanceTwo.getBodyColor())) {
                return true;
            }
            return !instanceOne.getPatternColor().equals(instanceTwo.getPatternColor());
        }
        return false;
    }
    /**
     * pieces of shit copied from Network
     * @param metaOne
     * @param metaTwo
     * @return
     */
    public static boolean canQuickEscapeMetaVariant(@Nonnull ItemMeta metaOne, @Nonnull ItemMeta metaTwo) {

        // Damageable (first as everything can be damageable apparently)
        if (metaOne instanceof Damageable instanceOne && metaTwo instanceof Damageable instanceTwo) {
            if (instanceOne.getDamage() != instanceTwo.getDamage()) {
                return true;
            }
        }
        //banner
        if (metaOne instanceof BannerMeta instanceOne && metaTwo instanceof BannerMeta instanceTwo) {
            if (!instanceOne.getPatterns().equals(instanceTwo.getPatterns())) {
                return true;
            }
        }
        // Enchantment Storage
        if (metaOne instanceof EnchantmentStorageMeta instanceOne && metaTwo instanceof EnchantmentStorageMeta instanceTwo) {
            if (instanceOne.hasStoredEnchants() != instanceTwo.hasStoredEnchants()) {
                return true;
            }
            if (!instanceOne.getStoredEnchants().equals(instanceTwo.getStoredEnchants())) {
                return true;
            }
        }
        // Crossbow
        if (metaOne instanceof CrossbowMeta instanceOne && metaTwo instanceof CrossbowMeta instanceTwo) {
            if (instanceOne.hasChargedProjectiles() != instanceTwo.hasChargedProjectiles()) {
                return true;
            }
            if (!instanceOne.getChargedProjectiles().equals(instanceTwo.getChargedProjectiles())) {
                return true;
            }
        }
        // Cannot escape via any meta extension check
        return false;
    }

}






























