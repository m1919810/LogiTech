package me.matl114.logitech.SlimefunItem.Machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.UtilClass.ItemGreedyConsumer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorkBench extends AbstractMachines {
    public List<MachineRecipe> machineRecipes;
    public AbstractWorkBench(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                            int energybuffer, int energyConsumption, LinkedHashMap<Object,Integer> shapedRecipes){
        super(category,item,recipeType,recipe,energybuffer,energyConsumption);

        if(shapedRecipes != null){
            machineRecipes = new ArrayList<>(shapedRecipes.size());
            var customRecipes2 = AddUtils.buildRecipeMap(shapedRecipes);
            for (Map.Entry<Pair<ItemStack[], ItemStack[]>, Integer> recipePiece : customRecipes2.entrySet()) {
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(new MachineRecipe(recipePiece.getValue(), recipePiece.getKey().getFirstValue(), recipePiece.getKey().getSecondValue()));
            }
        }else{
            machineRecipes = new ArrayList<>();
        }

    }
    public void addInfo(ItemStack item){
        if(this.energyConsumption > 0){
            item.setItemMeta(AddUtils.addLore(item,AddUtils.energyPerCraft(this.energyConsumption)).getItemMeta());
        }
    }

    /**
     * construct your menu here.called in constructor
     * @param preset
     */
    protected abstract void constructMenu(BlockMenuPreset preset);

    /**
     * cargo and IO
     * @return
     */
    public abstract int[] getInputSlots();

    /**
     * cargo and IO
     * @return
     */
    public abstract int[] getOutputSlots();

    public abstract int[] getRecipeSlots();
    public List<MachineRecipe> getMachineRecipes(){
        return machineRecipes;
    }
    public boolean conditionHandle(Block b,BlockMenu menu){
        return true;
    }
    protected void processorCost(Block b,BlockMenu menu) {

    }
    public void craft(BlockMenu inv,int limit){
        Pair<MachineRecipe,ItemGreedyConsumer[]> outputResult=
                CraftUtils.findNextShapedRecipe(inv,getInputSlots(),getOutputSlots(),getMachineRecipes(),limit,true);
        if(outputResult != null){
            CraftUtils.multiUpdateOutputMenu(outputResult.getSecondValue(),inv);
        }
    }
    public void process(Block b, BlockMenu preset){

    }
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow){
        return flow==ItemTransportFlow.WITHDRAW?getOutputSlots():new int[0];
    }
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
        if(item.getMaxStackSize()==1||flow==ItemTransportFlow.WITHDRAW){
            return getSlotsAccessedByItemTransport(flow);
        }else{
            List<Integer> input_slots=new ArrayList<Integer>();
            int[] input=getInputSlots();
            for (int i=0;i<input.length;i++){
                if(menu.getItemInSlot(input[i])!=null){
                    input_slots.add(i);
                }
            }
            int[] array = new int[input_slots.size()];

            for (int i = 0; i < input_slots.size(); i++) {
                array[i] = input_slots.get(i);
            }
            return array;

        }
    }

}
