package me.matl114.logitech.Utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.Utils.UtilClass.ItemClass.*;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.MGeneratorRecipe;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SequenceMachineRecipe;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ShapedMachineRecipe;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.StackMachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class MachineRecipeUtils {
    public static final ItemStack BOTTLE=new ItemStack(Material.GLASS_BOTTLE);
    public static final ItemStack BUCKET=new ItemStack(Material.BUCKET);
    public static final HashMap<Material,ItemStack> NOCONSUME_MAP = new HashMap<>(){{
        put(Material.WATER_BUCKET,BUCKET );
        put(Material.LAVA_BUCKET, BUCKET);
        put(Material.MILK_BUCKET, BUCKET);
        put(Material.HONEY_BOTTLE, BOTTLE);
        put(Material.WATER_CAULDRON, BOTTLE);
        put(Material.POTION, BOTTLE);
        put(Material.SPLASH_POTION, BOTTLE);
        put(Material.LINGERING_POTION, BOTTLE);
        put(Material.DRAGON_EGG, BOTTLE);
    }};
    public interface RecipeConstructor<T>{
        public T construct(int ticks,ItemStack[] input,ItemStack[] output);
    }
    /**
     * only called when init recipe list!
     * @param items
     * @return
     */
    public static ItemStack[] stackIn(ItemStack[] items){
        LinkedHashSet<ItemCounter> stacks = new LinkedHashSet<>();
        boolean __flag;
        for(int i = 0; i < items.length; i++){
            ItemStack a = items[i];
            if(a==null)continue;
            ItemCounter ac=ItemCounter.get(AddUtils.getCopy(a));
            if(ac.getItem() instanceof RandOutItem){
                stacks.add(ac);
                continue;
            }
            __flag=false;
            for(ItemCounter stack : stacks){
                if(CraftUtils.matchItemStack(a,stack,true)){
                    stack.addAmount(a.getAmount());
                    __flag=true;
                    break;
                }
            }
            if(!__flag){
                stacks.add(ac);
            }
        }
        ItemStack[] result = new ItemStack[stacks.size()];
        int cnt=0;
        for(ItemCounter stackCounter : stacks){

            if(stackCounter.getItem() instanceof EqualInItem ei){
                if(stackCounter.getItem() instanceof EquivalItemStack eqi){
                    result[cnt] = AddUtils.equalItemStackFactory(eqi.getItemStacks(),stackCounter.getAmount());
                }
                else{
                    //no other equalInItem till now
                    result[cnt] = stackCounter.getItem();
                    result[cnt].setAmount(stackCounter.getAmount());
                }
            }else if(stackCounter.getItem() instanceof AbstractItemStack){
                result[cnt] = stackCounter.getItem();
            }
            else {
                result[cnt] = stackCounter.getItem();
                result[cnt].setAmount(stackCounter.getAmount());
            }
            ++cnt;
        }
        return result;
    }

    /**
     * only remove null from recipes
     * @param items
     * @return
     */
    public static ItemStack[] in(ItemStack[] items){
        List<ItemStack> result = new ArrayList<>();
        for(int i = 0; i < items.length; i++){
            ItemStack a = items[i];
            if(a!=null){
                result.add(AddUtils.getCopy(a));
            }

        }
        ItemStack[] stacklist= result.toArray(new ItemStack[result.size()]);
        return stacklist;
    }

    /**
     * what can I say ,bro ;for those who need to keep nulls
     * @return
     */
    public static ItemStack[] i(ItemStack[] stacklist){
        ItemStack[] stacks=new ItemStack[stacklist.length];
        int len=stacklist.length;
        for(int i = 0; i<len; i++){
            if(stacklist[i]!=null){
                stacks[i]=AddUtils.getCopy(stacklist[i]);
            }else {
                stacks[i]=null;
            }
        }
        return stacklist;
    }

    /**
     * please make sure your input IS already stacked and each input not similar with each other
     * @param tick tick num, int, not sec num
     * @param input
     * @param output
     */

    /**
     * please make sure your input IS already stacked and each input not similar with each other
     * @param tick tick num, int, not sec num
     * @par
     */
    public static StackMachineRecipe stackFrom(int tick,ItemStack[] ipnut,ItemStack[] output){
        return new StackMachineRecipe(tick,stackIn(ipnut),stackIn(output));
    }
    public static StackMachineRecipe stackFromPair(int tick, Pair<ItemStack[],ItemStack[]> recipe) {
        return new StackMachineRecipe(tick,stackIn(recipe.getFirstValue()),stackIn(recipe.getSecondValue()));
    }
    public static StackMachineRecipe stackFromMachine(MachineRecipe recipe) {
        if(recipe instanceof StackMachineRecipe recipe1){
            return recipe1;
        }
        return new StackMachineRecipe(recipe.getTicks(),stackIn(recipe.getInput()),stackIn(recipe.getOutput()));
    }
    /**
     * from sf recipes shaped,will automatically stackin,time default 0 , can set time
     * will return bottles and buckets
     * @param item sf shaped recipe
     */
    public static StackMachineRecipe stackFromRecipe(SlimefunItem item){
        return stackFromRecipe(item,-1);
    }
    public static <T extends MachineRecipe> T getNoComsumed(T recipe, RecipeConstructor<T> constructor){
        List<ItemStack> outputs=new ArrayList<ItemStack>();
        ItemStack[] output=recipe.getOutput();
        for(int i=0;i<output.length;i++){
            outputs.add(output[i]);
        }
        ItemStack[] input=recipe.getInput();
         boolean hasChanged=false;
         HashMap<ItemStack,Integer> NOCONSUME_RETURN=new HashMap<>();
        for(int i = 0; i < input.length; i++){
            if(input[i]==null)continue;
            if(NOCONSUME_MAP.containsKey(input[i].getType())){
                ItemStack returned=NOCONSUME_MAP.get(input[i].getType());
                if(CraftUtils.matchItemStack(new ItemStack(input[i].getType()),input[i],true)){

                    NOCONSUME_RETURN.put(returned,NOCONSUME_RETURN.getOrDefault(returned,0)+input[i].getAmount());
                    hasChanged=true;
                }
            }
        }
        if(hasChanged){
            for (Map.Entry<ItemStack,Integer> entry : NOCONSUME_RETURN.entrySet()){
                ItemStack returned=entry.getKey().clone();
                returned.setAmount(entry.getValue());
                outputs.add(returned);
            }
            return constructor.construct(recipe.getTicks(),input,outputs.toArray(new ItemStack[outputs.size()]));

        }else {
            return recipe;
        }
    }
    public static StackMachineRecipe stackFromMultiBlock(ItemStack[] input, MultiBlockMachine machine){
        return stackFromMultiBlock(input,machine,-1);
    }
    public static StackMachineRecipe stackFromMultiBlock(ItemStack[] input, MultiBlockMachine machine,int ticks){
        ItemStack recipeOutput= RecipeType.getRecipeOutputList(machine,input);
        SlimefunItem resultSfitem=SlimefunItem.getByItem(recipeOutput);
        if(resultSfitem==null&&ticks==-1){
            ticks=0;
        }
        return getNoComsumed(new StackMachineRecipe(ticks,stackIn(input),new ItemStack[]{recipeOutput}),StackMachineRecipe::new);
    }
    public static StackMachineRecipe stackFromRecipe(SlimefunItem item,int time){
//        ItemStack[] input=item.getRecipe();
//        List<ItemStack> outputs=new ArrayList<ItemStack>(){{
//            add(item.getRecipeOutput());
//        } };
//        for(int i = 0; i < input.length; i++){
//            if(input[i]==null)continue;
//            if(NOCONSUME_MAP.containsKey(input[i].getType())){
//                if(CraftUtils.matchItemStack(new ItemStack(input[i].getType()),input[i],true)){
//                    outputs.add(NOCONSUME_MAP.get(input[i].getType()));
//                }
//            }
//        }
        return getNoComsumed(new StackMachineRecipe(time,stackIn(item.getRecipe()),new ItemStack[]{item.getRecipeOutput()}),StackMachineRecipe::new);
    }
    /**
     * check if any NOT-TO-BE-CONSUMED Item
     */
    public static boolean hasNonConsumed(ItemStack[] input){
        for(int i = 0; i < input.length; i++){
            if(input[i]==null)continue;
            if(NOCONSUME_MAP.containsKey(input[i].getType())){
                return true;
            }
        }
        return false;
    }
    /**
     * build shaped recipe,keep nulls ,don't stack ,make machinerecipe
     * @param
     * @param
     * @return
     */
    public static ShapedMachineRecipe shapeFrom(int ticks,ItemStack[] input,ItemStack[] output){
        return new ShapedMachineRecipe(ticks,i(input),stackIn(output));
    }
    /**
     * build shaped recipe,keep nulls ,don't stack ,add returned bottles,make machinerecipe
     * @param
     * @param
     * @return
     */
    public static ShapedMachineRecipe shapeFromRecipe(SlimefunItem item){
        return shapeFromRecipe(item,-1);
    }
    public static StackMachineRecipe shapeFromMultiBlock(ItemStack[] input, MultiBlockMachine machine){
        return stackFromMultiBlock(input,machine,-1);
    }
    public static StackMachineRecipe shapeFromMultiBlock(ItemStack[] input, MultiBlockMachine machine,int ticks){
        ItemStack recipeOutput= RecipeType.getRecipeOutputList(machine,input);
        SlimefunItem resultSfitem=SlimefunItem.getByItem(recipeOutput);
        if(resultSfitem==null&&ticks==-1){
            ticks=0;
        }
        return getNoComsumed(new StackMachineRecipe(ticks,i(input),new ItemStack[]{recipeOutput}),StackMachineRecipe::new);
    }

    /**
     * build shaped recipe,keep nulls ,don't stack ,add returned bottles,make machinerecipe
     * @param item
     * @param time
     * @return
     */
    public static ShapedMachineRecipe shapeFromRecipe(SlimefunItem item,int time){
        ItemStack[] input=item.getRecipe();
        List<ItemStack> outputs=new ArrayList<ItemStack>(){{
            add(item.getRecipeOutput());
        } };
        for(int i = 0; i < input.length; i++){
            if(input[i]==null)continue;
            if(NOCONSUME_MAP.containsKey(input[i].getType())){
                if(CraftUtils.matchItemStack(new ItemStack(input[i].getType()),input[i],true)){
                    outputs.add(NOCONSUME_MAP.get(input[i].getType()));
                }
            }
        }
        return new ShapedMachineRecipe(time,i(item.getRecipe()),stackIn(outputs.toArray(ItemStack[]::new)));
    }
    /**
     * chear nulls in recipe,add bucket and bottles in output, make machinerecipe
     * @param item
     * @return
     */
    public static MachineRecipe FromRecipe(SlimefunItem item){
        return FromRecipe(item,-1);
    }

    /**
     * chear nulls in recipe,add bucket and bottles in output, make machinerecipe
     * @param item
     * @param time
     * @return
     */
    public static MachineRecipe FromRecipe(SlimefunItem item,int time){
        ItemStack[] input=item.getRecipe();
        List<ItemStack> outputs=new ArrayList<ItemStack>(){{
            add(item.getRecipeOutput());
        } };
        for(int i = 0; i < input.length; i++){
            if(input[i]==null)continue;
            if(NOCONSUME_MAP.containsKey(input[i].getType())){
                if(CraftUtils.matchItemStack(new ItemStack(input[i].getType()),input[i],true)){
                    outputs.add(NOCONSUME_MAP.get(input[i].getType()));
                }
            }
        }
        return new MachineRecipe(time,in(item.getRecipe()),stackIn(outputs.toArray(ItemStack[]::new)));
    }

    /**
     * clear nulls in recipe,why there are nulls
     * @param recipe
     * @return
     */
    public static MachineRecipe FromMachine(MachineRecipe recipe){
        MachineRecipe rep=new MachineRecipe(-1,in(recipe.getInput()),in(recipe.getOutput()));
        rep.setTicks(recipe.getTicks());
        return rep;
    }

    /**
     * clear nulls in input and output, make recipe
     * @param ticks
     * @param input
     * @param output
     * @return
     */
    public static MachineRecipe From(int ticks,ItemStack[] input,ItemStack[] output){
        MachineRecipe rep=new MachineRecipe(0,in(input),in(output));
        rep.setTicks(ticks);
        return rep;
    }


    public static SequenceMachineRecipe sequenceFrom(int ticks,ItemStack[] input,ItemStack[] output){
        List<ItemStack> inputSequence=new ArrayList<ItemStack>();
        for(int i = 0; i < input.length; i++){
            if(input[i]==null)continue;
            int amount=input[i].getAmount();

            if(amount==0)continue;
            //int maxSize=input[i].getMaxStackSize();
//            if(amount>maxSize){
//                while(amount>maxSize){
//                    ItemStack it=input[i].clone();
//                    it.setAmount(maxSize);
//                    inputSequence.add(it);
//                    amount-=maxSize;
//                }
//                ItemStack it=input[i].clone();
//                it.setAmount(amount);
//                inputSequence.add(it);
//            }else {
            inputSequence.add(input[i]);
//            }
        }
        return new SequenceMachineRecipe(ticks,in( inputSequence.toArray(ItemStack[]::new)),in(output));
    }
    public static SequenceMachineRecipe sequenceFromRecipe(SlimefunItem item){
        //not implemented yet
        return null;
    }
    public static SequenceMachineRecipe sequenceFromMachine(MachineRecipe recipe){
        if(recipe instanceof SequenceMachineRecipe recipe1){
            return recipe1;
        }
        return sequenceFrom(recipe.getTicks(),in(recipe.getInput()),in(recipe.getOutput()));
    }
    public static boolean isValidMGInput(ItemStack stack){
        return true;
    }
    public static ItemStack[] makeValidMGInput(ItemStack[] input){
        List<ItemStack> it=new ArrayList<>();
        for(int i = 0; i < input.length; i++){
            if(input[i]==null)continue;
            if(input[i] instanceof DisplayItemStack)continue;
            it.add(input[i]);
        }
        return it.toArray(new ItemStack[it.size()]);
    }
    public static MGeneratorRecipe mgFrom(int ticks,ItemStack[] input,ItemStack[] output){
        input=makeValidMGInput(input);
        return new MGeneratorRecipe(stackFrom(ticks, stackIn(input), stackIn(output)));
    }
    public static MGeneratorRecipe mgFromMachine(MachineRecipe recipe){
        if(recipe instanceof MGeneratorRecipe recipe1){
            return recipe1;
        }else{
            return new MGeneratorRecipe(recipe.getTicks(),stackIn(makeValidMGInput(recipe.getInput())),stackIn(recipe.getOutput()));
        }
    }
    public static MGeneratorRecipe tryGenerateMGFromMachine(MachineRecipe recipe){
        if(recipe instanceof MGeneratorRecipe recipe1){
            return recipe1;
        }
        StackMachineRecipe recipe1;
        if(recipe instanceof StackMachineRecipe recipe2){
            recipe1=recipe2;
        }else {
            recipe1=stackFromMachine(recipe);
        }
        //会删除某些元素为空，所以需要重新stack
        ItemStack[] input=stackIn(makeValidMGInput(recipe1.getInput()));
        ItemStack[] output=recipe1.getOutput();
//        if(input.length==0){
//            return mgFrom(recipe1.getTicks(),input,output);
//        }else
        if(input.length!=0&& output.length!=0){
            if(input.length==1&&output.length==1&&CraftUtils.matchItemStack(output[0],input[0],true)&&input[0].getAmount()>=output[0].getAmount()){
                return mgFrom(recipe1.getTicks(),input,output);
            }
//            if(input[0] instanceof DisplayItemStack){
//                return mgFrom(recipe1.getTicks(),new ItemStack[0],output);
//            }
        }
        return null;
    }

    /**
     * check if valid recipe, don't put sth like item recipe in this
     * @param recipes
     * @return
     */
    public static boolean isMachineRecipe(List<MachineRecipe> recipes){
        int i=recipes.size();
        for(int j=0;j<i;j++){
            if(! (recipes.get(j).getTicks()>=0)){
                return false;
            }
        }
        return true;
    }
    public static boolean isGeneratorRecipe(List<MachineRecipe> recipes){
        int i=recipes.size();
        for(int j=0;j<i;j++){
            if(!((recipes.get(j) instanceof MGeneratorRecipe)&&recipes.get(j).getTicks()>=0)){
                return false;
            }
        }
        return true;
    }

}

