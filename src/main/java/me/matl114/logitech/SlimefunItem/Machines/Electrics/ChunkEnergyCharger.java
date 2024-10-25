package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.SlimefunItem.Interface.ChunkLimit;
import me.matl114.logitech.Utils.DataCache;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChunkEnergyCharger extends AbstractEnergyCharger implements ChunkLimit {

    HashMap<Chunk, Location> MACHINE_POSITION=new HashMap<>();
    public HashMap<Chunk,Location> getRecords(){
        return MACHINE_POSITION;
    }

    public ChunkEnergyCharger(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                              int energybuffer){
        super(category, item, recipeType, recipe, energybuffer);
    }
    public Collection<SlimefunBlockData> getChargeRange(BlockMenu inv,Block block,SlimefunBlockData data){
        Location loc=block.getLocation();
        return DataCache.getAllSfItemInChunk(loc.getWorld(),loc.getBlockX()>>4,loc.getBlockZ()>>4);
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        if(!onChunkPlace(menu.getLocation(),this)){
            onChunkReachLimit(menu.getLocation(),this,(str)->{menu.getLocation().getWorld().getNearbyEntities(menu.getLocation(),10,10,10,(e)->{
                if(e instanceof Player player){
                    player.sendMessage(str);
                }
                return false;
            });});
            return;
        }
        super.newMenuInstance(menu, block);
    }
    protected boolean isChargeable(SlimefunItem that){
        return !(that instanceof AbstractEnergyMachine);
    }
    public int getMaxChargeAmount(){
        return 64;
    }
    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        onChunkBreak(menu.getLocation(),this);
    }






}
