package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;


import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.Machines.AbstractTransformer;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.DisplayItemStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SMGenerator extends AbstractTransformer  {

    private final int INFO_SLOT = 0;
    private final int[] OUTPUT_BORDER = {};
    private final int[] INPUT_SLOT    = {};
    private final int[] OUTPUT_SLOTS = {1,2, 3, 4, 5, 6, 7,8};

    public SMGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                       int time, int energybuffer,int energyConsumption,Object... outputs_w) {
        super(itemGroup, item, recipeType, recipe,time,energybuffer,energyConsumption,
                new LinkedHashMap<>(){{
                    this.put(new Pair<>(
                            new ItemStack[]{}, outputs_w
                    ),time) ;
                }}
        );
        this.PROCESSOR_SLOT=0;


    }
    public void addInfo(ItemStack stack){
        stack.setItemMeta( AddUtils.smgInfoAdd(stack,time).getItemMeta() );
        super.addInfo(stack);
    }
    public void constructMenu(BlockMenuPreset preset){
        preset.setSize(9);
        preset.addItem(INFO_SLOT,this.INFO_WORKING.clone(), ChestMenuUtils.getEmptyClickHandler());
    }
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }

    public boolean isSync(){
        return false;
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
                MachineRecipe nextP = getMachineRecipes().get(0);

                if (tick == 0){
                    int maxMultiple = getCraftLimit(data);
                    if (maxMultiple == 1) {
                        CraftUtils.pushItems(nextP.getOutput(), menu, getOutputSlots(), CRAFT_PROVIDER);
                    } else {

                        CraftUtils.multiPushItems(nextP.getOutput(),menu, getOutputSlots(), maxMultiple, CRAFT_PROVIDER);
                    }
                }
                DataCache.setCustomData(data,"tick",nextP.getTicks()-1);

            }else{
                DataCache.setCustomData(data,"tick",tick-1);
            }

        }else if (tick!=-1&&menu.hasViewer()){
            DataCache.setCustomData(data,"tick",-1);
            menu.replaceExistingItem(this.PROCESSOR_SLOT,this.INFO_NULL);
        }
    }

}
