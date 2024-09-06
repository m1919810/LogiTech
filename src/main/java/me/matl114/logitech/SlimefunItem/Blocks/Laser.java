package me.matl114.logitech.SlimefunItem.Blocks;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.matl114.logitech.Utils.UtilClass.TickerClass.Ticking;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Laser extends AbstractMachine {
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<>();
    }
    public Laser(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                       int energybuffer, int energyConsumption) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
    }
    public String POWERED_KEY="po";
    public String DIRECTION="dir";
    public String FORCE_STATE="fo";
    public int MAX_SEARCH_LEN=32;
    public void process(Block b, @Nullable BlockMenu menu, SlimefunBlockData data){

    }
    @Override
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker){
        final int force=DataCache.getCustomData(data,FORCE_STATE,0);
        if(force==1||conditionHandle(b,menu)){
            Location loc=b.getLocation();
            int charged= DataCache.getCustomData(loc,POWERED_KEY,0);
            if(charged!=0){
                int direction=DataCache.getCustomData(data,DIRECTION,-1);
                if(direction>=0){
                    progressorCost(b,menu);
                    Directions dir= Directions.fromInt(direction);
                    Schedules.launchSchedules(()->{
                        Location loc2=loc.clone();
                        int i=0;
                        for (;i<MAX_SEARCH_LEN;++i){
                            loc2=dir.move(loc2);
                            if(!loc2.getBlock().getType().isAir()){
                                break;
                            }
                        }
                        if(i==0)return;
                        Location start=dir.move( loc.clone().add(0.5,0.5,0.5));
                        Location center=dir.remote(start,((double)(i-1))/2 );
                        Location end=dir.remote(start,i-1);
                        //FIXME async thread of searching nonair blocks, and find out the killing bounding box
                    },0,false,0);
                }
            }
            Schedules.launchSchedules(()->{
                //FIXME change this to observer or dropper
                if(force==1){
                    return;
                }
                BlockData data1=b.getBlockData();
                if(data1 instanceof Lightable la){
                    DataCache.setCustomData(data,POWERED_KEY,la.isLit()?1:0);
                }
            },0,false,0);
        }
    }
    public void constructMenu(BlockMenuPreset preset){

    }
    public void registerBlockMenu(SlimefunItem that){

    }
}
