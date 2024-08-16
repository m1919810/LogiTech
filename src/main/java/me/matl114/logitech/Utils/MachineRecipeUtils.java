package me.matl114.logitech.Utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.Utils.UtilClass.ItemClass.DisplayItemStack;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.MultiItemStack;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.MGeneratorRecipe;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SequenceMachineRecipe;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ShapedMachineRecipe;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.StackMachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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
    /**
     * only called when init recipe list!
     * @param items
     * @return
     */
    public static ItemStack[] stackIn(ItemStack[] items){
        LinkedHashSet<ItemConsumer> stacks = new LinkedHashSet<>();
        boolean __flag=false;
        for(int i = 0; i < items.length; i++){
            ItemStack a = items[i];
            if(a==null)continue;
            __flag=false;
            for(ItemConsumer stack : stacks){
                if(CraftUtils.matchItemStack(a,stack.getItem(),true)){
                    stack.addAmount(a.getAmount());
                    __flag=true;
                    break;
                }
            }
            if(__flag==false){
                stacks.add(new ItemConsumer(a));
            }
        }
        ItemStack[] result = new ItemStack[stacks.size()];
        int cnt=0;
        for(ItemConsumer stack : stacks){
            if(stack.getItem() instanceof MultiItemStack){
                result[cnt] = stack.getItem();
            }
            else {
                result[cnt] = stack.getItem().clone();
                result[cnt].setAmount(stack.getAmount());
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
                result.add(a);
            }

        }
        return result.toArray(new ItemStack[result.size()]);
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
        return new StackMachineRecipe(tick,recipe.getFirstValue(),recipe.getSecondValue());
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
    public static StackMachineRecipe stackFromRecipe(SlimefunItem item,int time){
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
        return new StackMachineRecipe(time,stackIn(item.getRecipe()),stackIn(outputs.toArray(ItemStack[]::new)));
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
        return new ShapedMachineRecipe(ticks,input,output);
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
        return new ShapedMachineRecipe(time,item.getRecipe(),stackIn(outputs.toArray(ItemStack[]::new)));
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
        return new SequenceMachineRecipe(ticks,inputSequence.toArray(ItemStack[]::new),output);
    }
    public static SequenceMachineRecipe sequenceFromRecipe(SlimefunItem item){
        //not implemented yet
        return null;
    }
    public static SequenceMachineRecipe sequenceFromMachine(MachineRecipe recipe){
        if(recipe instanceof SequenceMachineRecipe recipe1){
            return recipe1;
        }
        return sequenceFrom(recipe.getTicks(),recipe.getInput(),recipe.getOutput());
    }
    public static boolean isValidMGInput(ItemStack stack){
        return true;
    }
    public static ItemStack[] makeValidMGInput(ItemStack[] input){
        return input;
    }
    public static MGeneratorRecipe mgFrom(int ticks,ItemStack[] input,ItemStack[] output){
        input=makeValidMGInput(input);
        return new MGeneratorRecipe(stackFrom(ticks, input, output));
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
        if(input.length==0){
            return mgFrom(recipe1.getTicks(),input,output);
        }else if(output.length!=0){
            if(CraftUtils.matchItemStack(output[0],input[0],false)){
                return mgFrom(recipe1.getTicks(),input,output);
            }
        }
        return null;
    }

}

