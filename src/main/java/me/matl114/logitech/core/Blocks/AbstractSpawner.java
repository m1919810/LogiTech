package me.matl114.logitech.core.Blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.DistinctiveItem;
import me.matl114.logitech.core.Items.SpecialItems.EntityFeat;
import me.matl114.logitech.utils.CraftUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;

public class AbstractSpawner extends AbstractBlock implements DistinctiveItem {
    private final ItemSetting<Boolean> allowSpawnEggs = new ItemSetting<>(this, "allow-spawn-eggs", true);
    public AbstractSpawner(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemSetting(allowSpawnEggs);
    }
    public void onPlace(BlockPlaceEvent event, Block e) {
        super.onPlace(event, e);
        BlockState data = e.getState(false);
        if(data instanceof CreatureSpawner cs){
            ItemStack it=event.getItemInHand();
            if(it.getItemMeta() instanceof BlockStateMeta bsm){
                if(bsm.getBlockState() instanceof CreatureSpawner cs2){
                    cs.setSpawnedType(cs2.getSpawnedType());
                    cs.setMinSpawnDelay(cs2.getMinSpawnDelay());
                    cs.setMaxSpawnDelay(cs2.getMaxSpawnDelay());
                    cs.setMaxNearbyEntities(cs2.getMaxNearbyEntities());
                    cs.setRequiredPlayerRange(cs2.getRequiredPlayerRange());
                    cs.setSpawnRange(cs2.getSpawnRange());
                    cs.setSpawnCount(cs2.getSpawnCount());
                    cs.update(true,false);
                }
            }
        }
    }
    public void onBreak(BlockBreakEvent event, BlockMenu e) {
        super.onBreak(event, e);
        onSpawnerBreak(event);
    }
    public void onSpawnerBreak(BlockBreakEvent event){
        BlockState data = event.getBlock().getState(false);
        if(data instanceof CreatureSpawner cs){
            event.getBlock().getWorld().dropItemNaturally(
                    event.getBlock().getLocation(),
                    EntityFeat.generateSpawnerFrom(cs.getSpawnedType(),cs.getMinSpawnDelay(),
                            cs.getMaxNearbyEntities(),cs.getRequiredPlayerRange(),cs.getSpawnRange(),cs.getSpawnCount(),true)
            );
            event.getBlock().setType(Material.AIR);
            event.setDropItems(false);
            event.setExpToDrop(0);
        }
    }
    public Collection<ItemStack> getDrops() {
        /**
         * There should be no drops by default since drops are handled by listener
         */
        return new ArrayList<>();
    }
   public void preRegister(){
        super.preRegister();
        this.handleBlock(this);
   }
    public boolean canStack(ItemMeta meta1, ItemMeta meta2){
//        if(!super.canStack(meta1,meta2)){
//            return false;
//        }
        if(meta1 instanceof BlockStateMeta bsm1 &&meta2 instanceof BlockStateMeta bsm2){
            return CraftUtils.matchBlockStateMetaField(bsm1,bsm2);
        }
        return false;
    }
}
