package me.matl114.logitech.SlimefunItem.Items;

import com.xzavier0722.mc.plugin.slimefun4.storage.callback.IAsyncReadCallback;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import me.matl114.logitech.SlimefunItem.Cargo.Links.HyperLink;
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
                    var controller = Slimefun.getDatabaseManager().getBlockDataController();
                    controller.getBlockDataAsync(
                        loc,
                        new IAsyncReadCallback<SlimefunBlockData>() {
                            @Override
                            public boolean runOnMainThread() {
                                // 如果return true，回调将会在主线程执行。如不重写该方法默认为false。
                                return true;
                            }
                            @Override
                            public void onResult(SlimefunBlockData result){
                                BlockMenu menu=result.getBlockMenu();
                                if(menu!=null){
                                    menu.open(event.getPlayer());
                                }
                            }
                        }
                    );
                }
            }
        }
    }
}
