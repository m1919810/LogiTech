package me.matl114.logitech.SlimefunItem.Items;

import com.xzavier0722.mc.plugin.slimefun4.storage.callback.IAsyncReadCallback;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import me.matl114.logitech.SlimefunItem.Cargo.Links.HyperLink;
import me.matl114.logitech.Utils.DataCache;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Optional;

public class HypLink extends CustomSlimefunItem {
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
                    HyperLink.setLink(im,b.get().getLocation());
                    it.setItemMeta(im);
                }
            }
        }else{
            //try load
            ItemStack it= event.getItem();
            ItemMeta im = it.getItemMeta();
            if(HyperLink.isLink(im)){
                Location loc=HyperLink.getLink(im);
                if(loc!=null){
                    SlimefunBlockData data= DataCache.safeLoadBlock(loc);
                    if(data!=null){
                        BlockMenu menu=data.getBlockMenu();
                        if(menu!=null)
                            menu.open(event.getPlayer());
                    }
                }
            }
        }
    }
}
