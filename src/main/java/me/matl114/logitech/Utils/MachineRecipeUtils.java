package me.matl114.logitech.Utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.MultiItemStack;
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
     * @param recipe Pair
     */
    public static MachineRecipe stackFrom(int tick,ItemStack[] ipnut,ItemStack[] output){
        return new StackMachineRecipe(tick,stackIn(ipnut),stackIn(output));
    }
    public static MachineRecipe stackFromPair(int tick, Pair<ItemStack[],ItemStack[]> recipe) {
        return new StackMachineRecipe(tick,recipe.getFirstValue(),recipe.getSecondValue());
    }
    public static MachineRecipe stackFromMachine(MachineRecipe recipe) {
        return new StackMachineRecipe(recipe.getTicks(),stackIn(recipe.getInput()),stackIn(recipe.getOutput()));
    }
    /**
     * from sf recipes shaped,will automatically stackin,time default 0 , can set time
     * will return bottles and buckets
     * @param item sf shaped recipe
     */
    public static MachineRecipe stackFromRecipe(SlimefunItem item){
        return stackFromRecipe(item,-1);
    }
    public static MachineRecipe stackFromRecipe(SlimefunItem item,int time){
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
    public static MachineRecipe shapeFrom(int ticks,ItemStack[] input,ItemStack[] output){
        return new ShapedMachineRecipe(ticks,input,output);
    }
    /**
     * build shaped recipe,keep nulls ,don't stack ,add returned bottles,make machinerecipe
     * @param
     * @param
     * @return
     */
    public static MachineRecipe shapeFromRecipe(SlimefunItem item){
        return shapeFromRecipe(item,-1);
    }

    /**
     * build shaped recipe,keep nulls ,don't stack ,add returned bottles,make machinerecipe
     * @param item
     * @param time
     * @return
     */
    public static MachineRecipe shapeFromRecipe(SlimefunItem item,int time){
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
     * @param time
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
}
