package me.matl114.logitech.core.Items.SpecialItems;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.core.Cargo.Links.HyperLink;
import me.matl114.logitech.core.DistinctiveCustomSlimefunItem;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.WorldUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Optional;

public class HypLink extends DistinctiveCustomSlimefunItem {
    public HypLink(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe,new ArrayList<>());
    }
    @Override
    public void preRegister(){
        super.preRegister();
        addItemHandler((ItemUseHandler)this::onBindLocation);
    }
    private void onBindLocation(PlayerRightClickEvent event) {
        if(event.getPlayer().isSneaking()){
            Optional<Block> b = event.getClickedBlock();
            if(b.isPresent()){
                ItemStack it= event.getItem();
                ItemMeta im = it.getItemMeta();
                if(HyperLink.canLink(im)){
                    if(WorldUtils.hasPermission(
                        event.getPlayer(),b.get(),Interaction.INTERACT_BLOCK)){
                        HyperLink.setLink(im,b.get().getLocation());
                        it.setItemMeta(im);
                    }else {
                        AddUtils.sendMessage(event.getPlayer(),"&c抱歉,但您似乎并不能在该位置使用此物品.");
                    }
                }
            }
        }else{
            //try load
            ItemStack it= event.getItem();
            ItemMeta im = it.getItemMeta();
            if(HyperLink.isLink(im)){
                Location loc=HyperLink.getLink(im);
                if(loc!=null) {
                    if (WorldUtils.hasPermission(event.getPlayer(), loc, Interaction.INTERACT_BLOCK)) {
                        SlimefunBlockData data = DataCache.safeLoadBlock(loc);
                        if (data != null) {
                            BlockMenu menu = data.getBlockMenu();
                            if (menu != null){
                                menu.open(event.getPlayer());
                                return;
                            }
                        }
//                        Block b = loc.getBlock();
//                        PlayerInteractEvent interactTarget=new PlayerInteractEvent(event.getPlayer(), Action.RIGHT_CLICK_BLOCK,null,b, BlockFace.UP);
//                        try{
//                            Bukkit.getPluginManager().callEvent(interactTarget);
//                            if(interactTarget.isCancelled()){
//                                AddUtils.sendMessage(event.getPlayer(), "&c点击该方块的行为被阻止!");
//                                return;
//                            }
//                        }catch (Throwable ignored){
//                        }
//                        BlockState state = b.getState();
//                        if(state instanceof InventoryHolder ivHolder){
//                            //todo do test
//                            event.getPlayer().openInventory(ivHolder.getInventory());
//                        }

                    }else {
                        AddUtils.sendMessage(event.getPlayer(), "&c抱歉,但您似乎并没有访问该位置的权限.");
                    }
                }
            }
        }
    }
}
