package me.matl114.logitech.core;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Algorithms.PairList;
import me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop.SmithInterfaceProcessor;
import me.matl114.logitech.core.Items.Abstracts.MyVanillaItem;
import me.matl114.logitech.core.Machines.WorkBenchs.BugCrafter;
import me.matl114.matlib.Utils.Algorithm.InitializeProvider;
import me.matl114.matlib.core.EnvironmentManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Blank {
    public static final ItemStack[] LAZY_RECIPE=new ItemStack[]{null,null,null,null,new ItemStack(Material.DIRT),null,null,null,null};
    public static final ItemStack[] LAZY_STACKMACHINE=recipe("ADVANCED_CIRCUIT_BOARD","ENERGIZED_CAPACITOR","ADVANCED_CIRCUIT_BOARD","ELECTRIC_MOTOR","ELECTRIC_FURNACE_3","ELECTRIC_MOTOR",
            "HEATING_COIL","ELECTRIC_MOTOR","HEATING_COIL");
    public static final ItemStack[] LAZY_STACKMGENERATOR=recipe("ADVANCED_CIRCUIT_BOARD","ENERGIZED_CAPACITOR","ADVANCED_CIRCUIT_BOARD","ELECTRIC_MOTOR","ELECTRIC_ORE_GRINDER_3","ELECTRIC_MOTOR",
            "HEATING_COIL","ELECTRIC_MOTOR","HEATING_COIL");
    public static final SlimefunAddon INSTANCE = MyAddon.getInstance();
    public static SlimefunItem register(SlimefunItem item){
        item.register(INSTANCE);
        return item;
    }
    protected static boolean TYPE=false;
    protected static Object mkP(Object v1,Object v2){
        return new Pair(v1,v2);
    }
    protected static Object[] mkl(Object ... v){
        return Arrays.stream(v).toArray();
    }
    protected static int[] CHOOSEN_SLOT=new int[]{

    };
    public static ItemStack[] recipe(Object ... v){
        if(!TYPE||v.length<=9)
            return Arrays.stream(v).map(AddUtils::resolveItem).toArray(ItemStack[]::new);
        else{
            int len=v.length;
            ItemStack[] res = new ItemStack[9];
            int index=0;
            int delta=len/9;
            for(int i=0;i<9;++i){
                if(index>=len)res[i]=null;
                else{
                    res[i]=AddUtils.resolveItem(v[index]);
                }
                index+=delta-1+i%2;
            }
            return res;
        }
    }
    protected static RecipeType COMMON_TYPE=TYPE?RecipeType.ENHANCED_CRAFTING_TABLE: BugCrafter.TYPE;
    protected static <T extends Object> List<T> list(T ... input){
        return Arrays.asList(input);
    }
    protected static <T extends Object,Z extends Object> Pair<T,Z> pair(T v1,Z v2){
        return new Pair(v1,v2);
    }
    public static ItemStack setC(ItemStack it,int amount){
        return new CustomItemStack(it,amount);
    }
    protected static ItemStack[] nullRecipe(){
        return AddUtils.NULL_RECIPE.clone();
    }
    //    private static HashMap<Object,Integer> mrecipe(Object ... v){
//        int len=v.length;
//        return new PairList<>((len+1)/2){{
//            for(int i=0;i<len;i+=2){
//                Object v1=v[i];
//                Integer v2=(Integer)v[i+1];
//                put(v1,v2);
//            }
//        }};
//    }
    private static PairList<Object,Integer> mkMp(Object ... v){
        int len=v.length;
        return new PairList<>(){{
            for(int i=0;i<len;i+=2){
                Object v1=v[i];
                Integer v2=(Integer)v[i+1];
                put(v1,v2);
            }
        }};
    }


}
