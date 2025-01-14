package me.matl114.logitech.Listeners.Listeners;

import me.matl114.logitech.core.AddItem;
import me.matl114.logitech.Utils.CraftUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SuperSpongeDryListener implements Listener {
    @EventHandler
    public void onFurnaceDryFinish(FurnaceSmeltEvent event){
        ItemStack stack=event.getSource();
        if(stack.getType()== Material.WET_SPONGE&& CraftUtils.matchItemStack(stack,AddItem.SUPERSPONGE_USED,false)){
            Block block=event.getBlock();
            BlockState blockData=block.getState();
            if(blockData instanceof Container ih){
                Inventory inventory=ih.getInventory();
                if(inventory instanceof FurnaceInventory fi){
                    ItemStack fuelSlot=fi.getFuel();
                    ItemStack outputSlot=fi.getResult();
                    if(outputSlot==null||outputSlot.getType().isAir()){
                        //槽位是空的,直接输入该输出就行
                        event.setResult(AddItem.SUPERSPONGE.clone());
                        if(fuelSlot!=null&&fuelSlot.getType()== Material.BUCKET){
                            //燃料修改
                            fuelSlot.setType(Material.POWDER_SNOW_BUCKET);
                        }
                    }
                    else if( CraftUtils.matchItemStack(outputSlot,AddItem.SUPERSPONGE,false)){
                        //由于bukkit特性,需要我们自己设置数量
                        ItemStack source=fi.getSmelting();
                        if(CraftUtils.matchItemStack(source,AddItem.SUPERSPONGE_USED,false)){
                            outputSlot.setAmount(outputSlot.getAmount()+1);
                            source.setAmount(source.getAmount()-1);
                            if(fuelSlot!=null&&fuelSlot.getType()== Material.BUCKET){
                                //燃料修改
                                fuelSlot.setType(Material.POWDER_SNOW_BUCKET);
                            }
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

}
