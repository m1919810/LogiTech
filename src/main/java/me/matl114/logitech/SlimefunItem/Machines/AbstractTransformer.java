package me.matl114.logitech.SlimefunItem.Machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.MachineRecipeUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.List;

public abstract  class AbstractTransformer extends AbstractMachine implements PublicTicking {
    //我们的目标是 最广的需求 最好的性能 最大的答辩(bushi
    /**
     * public tick stuff
     */

    public final int time;
    private int pubTick;
    private int diffTick;

    protected int PROCESSOR_SLOT=22;
    public static final ItemStack INFO_WORKING= new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&a工作中");
    public static final ItemStack INFO_NULL= new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&6空闲中");
    public AbstractTransformer(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                             int time,  int energybuffer,int energyConsumption,
                               LinkedHashMap<Object ,Integer> customRecipes){
        super(category,item , recipeType, recipe,energybuffer,energyConsumption);
        this.time = (time<=0)?1:time;
        this.pubTick = 0;
        this.diffTick =(3+ AddUtils.random(1145))%this.time;

        if(customRecipes!=null){
            this.machineRecipes=new ArrayList<>(customRecipes.size());
            var tmp=AddUtils.buildRecipeMap(customRecipes);
            for (Map.Entry<Pair<ItemStack[], ItemStack[]>, Integer> recipePiece : tmp.entrySet()) {
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(MachineRecipeUtils.stackFromMachine(
                        new MachineRecipe(recipePiece.getValue(), recipePiece.getKey().getFirstValue(), recipePiece.getKey().getSecondValue())
                ));
            }
        }
        else{
            this.machineRecipes=new ArrayList<>();
        }

    }
    public List<MachineRecipe> getMachineRecipes() {
        return machineRecipes;
    }






    public void tick(Block b, BlockMenu menu,int ticker) {
       if(conditionHandle(b,menu)){
           processorCost(b,menu);
           if(ticker%this.time==this.diffTick){
            process(b,menu);
         }
    }}
    public void process(Block block, BlockMenu inv){
        MachineRecipe nextP = CraftUtils.matchNextRecipe(inv, getInputSlots(),getMachineRecipes(),true, Settings.SEQUNTIAL);
        if (nextP != null) {
            if(inv.hasViewer()){
                inv.replaceExistingItem(this.PROCESSOR_SLOT,this.INFO_WORKING);
            }
            processorCost(block,inv);
            CraftUtils.pushItems(nextP.getOutput(),inv,getOutputSlots());
        }else if (inv.hasViewer()){
            inv.replaceExistingItem(this.PROCESSOR_SLOT,this.INFO_NULL);
        }
    }
    public int getPublicTick(){
        return this.pubTick;
    }
    public void updatePublicTick(){
        this.pubTick++;
    }
    public boolean isSync(){
        return false;
    }

}
