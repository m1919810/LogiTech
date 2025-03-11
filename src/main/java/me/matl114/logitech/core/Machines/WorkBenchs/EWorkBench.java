package me.matl114.logitech.core.Machines.WorkBenchs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.manager.PostSetupTasks;
import me.matl114.logitech.utils.Settings;
import me.matl114.logitech.core.Machines.Abstracts.AbstractWorkBench;
import me.matl114.logitech.utils.Algorithms.PairList;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class EWorkBench extends AbstractWorkBench {
    protected static final int[] INPUT_SLOT=new int[]{11,12,13,20,21,22,29,30,31};
    protected static final  int[] OUTPUT_SLOT=new int[]{ 15,16,24,25,33,34};
    protected static final  int CRAFT_SLOT=23;
    protected static final ItemStack CRAFT_ITEM=new CustomItemStack(Material.PAPER,"&e点我进行合成");
    protected static final int MENU_SLOT=14;
    protected static final int[] BORDER=new int[]{
            0,1,2,3,4,5,6,7,8,9,10,17,18,19,26,27,28,32,35,36,37,38,39,40,41,42,43,44
    };
    public EWorkBench(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         int energybuffer, int energyConsumption,int craftlimit, List<Pair<Object,Integer>> shapedRecipes) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption,craftlimit, shapedRecipes);
    }
    public EWorkBench(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                      int energybuffer, int energyConsumption,int craftlimit, Supplier<List<MachineRecipe>> machineRecipeSupplier) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption,craftlimit,new PairList<>());
        this.machineRecipeSupplier=machineRecipeSupplier;
        PostSetupTasks.addPostRegisterTask(()->{
            getMachineRecipes();
        });
    }
    public void constructMenu(BlockMenuPreset preset){
        //preset.setSize(45);
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(CRAFT_SLOT,CRAFT_ITEM);
        preset.addItem(getRecipeMenuSlot(),AbstractWorkBench.RECIPEBOOK_SHOW_ITEM);
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        menu.addMenuClickHandler(CRAFT_SLOT,
                (player, i, itemStack, clickAction)->{
                    craft(block,menu,player);
                    return false;
                }
        );
        menu.addMenuClickHandler(getRecipeMenuSlot(),((player, i, itemStack, clickAction) -> {
            getRecipeMenu(block,menu).build().setBackHandler(((player1, i1, itemStack1, clickAction1) -> {menu.open(player);return false;})).open(player);
            return false;
        }));
//        menu.addMenuClickHandler(getRecipeMenuSlot(),((player, i, itemStack, clickAction) -> {
//            getRecipeMenu(block,menu).build().open(player);
//            return false;
//        }));
    }
    public void updateMenu(BlockMenu blockMenu, Block block, Settings mod){

    }
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public int[] getRecipeSlots(){
        return INPUT_SLOT;
    }
    public int getRecipeMenuSlot(){return MENU_SLOT; }
}
