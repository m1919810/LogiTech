package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Interface.DirectionalBlock;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.matl114.logitech.Utils.WorldUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashSet;

public class LineEnergyCollector extends AbstractEnergyCollector implements DirectionalBlock {
    protected final int MAX_LEN=64;

    protected final String[] savedKeys = new String[]{
            "line_dir"
    };
    public  String[] getSaveKeys(){
        return savedKeys;
    }
    protected final int[] DIRECTION_SLOTS = new int[]{
            10
    };
    public int[] getDirectionSlots(){
        return DIRECTION_SLOTS;
    }
    public LineEnergyCollector(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                             int energybuffer){
        super(category, item, recipeType, recipe, energybuffer);
    }
    public Collection<SlimefunBlockData> getCollectRange(BlockMenu inv, Block block, SlimefunBlockData data){
        Location loc=block.getLocation();
        Directions dir=getDirection(0,data);
        HashSet<SlimefunBlockData> ret=new HashSet<>();
        if(dir==Directions.NONE){
            return ret;
        }
        Location loc2=dir.relate(loc).add(0.5,0.5,0.5);
        int i;
        SlimefunBlockData testData;
        for (i=0;i<MAX_LEN;i++){
            loc=dir.relate(loc);
            SlimefunItem item=DataCache.getSfItem(loc);

            if(getEnergyProvider(item)!=null){
                if((testData=DataCache.safeLoadBlock(loc))!=null)
                    ret.add(testData);
            }
            if(item instanceof LineEnergyCollector){
                break;
            }
        }
        Location loc3=loc.clone().add(0.5,0.5,0.5);
        final int num=i;
        Schedules.launchSchedules(()->{

            WorldUtils.spawnLineParticle(loc2,loc3, Particle.WAX_OFF,num);
        },0,false,0);
        return ret;
    }
    public int getMaxCollectAmount(){
        return MAX_LEN;
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        super.newMenuInstance(menu, block);
        menu.addMenuClickHandler(DIRECTION_SLOTS[0],getDirectionHandler(0,menu));
        updateDirectionSlots(0,menu);
    }

}
