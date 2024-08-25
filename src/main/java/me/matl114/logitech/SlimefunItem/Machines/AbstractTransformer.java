package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.MachineRecipeUtils;
import me.matl114.logitech.Utils.UtilClass.ItemClass.DisplayItemStack;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusherProvider;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.List;

public abstract  class AbstractTransformer extends AbstractMachine {
    //我们的目标是 最广的需求 最好的性能 最大的答辩(bushi
    /**
     * public tick stuff
     */

    public final int time;
    private int pubTick;

    protected int PROCESSOR_SLOT=22;
    public final ItemStack INFO_WORKING= new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&a工作中");
    public final ItemStack INFO_NULL= new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&6空闲中");
    public AbstractTransformer(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                             int time,  int energybuffer,int energyConsumption,
                               LinkedHashMap<Object ,Integer> customRecipes){
        super(category,item , recipeType, recipe,energybuffer,energyConsumption);
        Debug.logger("trans constructor called");
        Debug.logger(this.getId());
        this.time = (time<=0)?1:time;
        this.pubTick = 0;

        if(customRecipes!=null){
            this.machineRecipes=new ArrayList<>(customRecipes.size());
            var tmp=AddUtils.buildRecipeMap(customRecipes);
            Debug.debug("build");
            for (Map.Entry<Pair<ItemStack[], ItemStack[]>, Integer> recipePiece : tmp.entrySet()) {
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(MachineRecipeUtils.stackFromMachine(
                        MachineRecipeUtils.mgFrom(recipePiece.getValue(), recipePiece.getKey().getFirstValue(), recipePiece.getKey().getSecondValue())
                ));
            }
        }

        else{
            this.machineRecipes=new ArrayList<>();
        }
        Debug.logger("trans constructor finished");

    }
    public List<ItemStack> _getDisplayRecipes() {
        List<ItemStack> list= super._getDisplayRecipes();
        if(!list.isEmpty()&&list.get(0)==null){
            list.set(0,new DisplayItemStack(
                    new CustomItemStack(
                            Material.KNOWLEDGE_BOOK,
                            "&7速度",
                            "&7每 " + Integer.toString(time) + " 粘液刻生成一次"
                    )));
        }
        return list;
    }
    public List<MachineRecipe> getMachineRecipes() {
        return machineRecipes;
    }

    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        int tick=DataCache.getCustomData(data,"tick",-1);
        //long f=System.nanoTime();
       if(conditionHandle(b,menu)){
           if(tick==-1&&menu.hasViewer()){
               menu.replaceExistingItem(this.PROCESSOR_SLOT,this.INFO_WORKING);
           }
          // long a=System.nanoTime();
           //long s=System.nanoTime();
           progressorCost(b,menu);
           if(tick<=0){
               MachineRecipe nextP = CraftUtils.matchNextRecipe(menu, getInputSlots(),getMachineRecipes(data),
                       true, Settings.SEQUNTIAL,CRAFT_PROVIDER);
               if (nextP != null) {
                   if (tick == 0){
                       int maxMultiple = getCraftLimit(data);
                       if (maxMultiple == 1) {
                           CraftUtils.pushItems(nextP.getOutput(), menu, getOutputSlots(), CRAFT_PROVIDER);
                       } else {

                           CraftUtils.multiPushItems(nextP.getOutput(),menu, getOutputSlots(), maxMultiple, CRAFT_PROVIDER);
                       }
                    }
                   DataCache.setCustomData(data,"tick",nextP.getTicks()-1);
               }

           }else{
               DataCache.setCustomData(data,"tick",tick-1);
           }

        }else if (tick!=-1&&menu.hasViewer()){
           DataCache.setCustomData(data,"tick",-1);
            menu.replaceExistingItem(this.PROCESSOR_SLOT,this.INFO_NULL);
        }
    }
    public int getCraftLimit(Block b, BlockMenu inv){
        return 1;
    }
    public int getCraftLimit(SlimefunBlockData data){
        return 1;
    }
    public final void process(Block block, BlockMenu inv, SlimefunBlockData data){


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
