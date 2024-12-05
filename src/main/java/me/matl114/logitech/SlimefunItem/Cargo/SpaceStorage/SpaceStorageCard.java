package me.matl114.logitech.SlimefunItem.Cargo.SpaceStorage;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import me.matl114.logitech.ConfigLoader;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import me.matl114.logitech.SlimefunItem.DistinctiveCustomItemStack;
import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceStorageCard extends DistinctiveCustomItemStack {
    protected final NamespacedKey KEY_UID= AddUtils.getNameKey("space-st-uid");
    protected final NamespacedKey KEY_STATUS= AddUtils.getNameKey("space-st-status");
    protected final NamespacedKey KEY_INDEX=AddUtils.getNameKey("space-st-index");
    protected final String LOCSIZE_PREFIX=AddUtils.resolveColor("&x&E&B&3&3&E&B存储的空间信息: ");
    protected final String INDEX_PREFIX=AddUtils.resolveColor("&x&E&B&3&3&E&B空间磁盘编号: ");
    public String getUid(ItemMeta meta){
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container.has(KEY_UID)){
            return container.get(KEY_UID, PersistentDataType.STRING);
        }
        return null;
    }
    public void setUid(ItemMeta meta, String uid){
        meta.getPersistentDataContainer().set(KEY_UID, PersistentDataType.STRING, uid);
    }
    public int getIndex(ItemMeta meta){
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container.has(KEY_INDEX)){
            try{
            return container.get(KEY_INDEX, PersistentDataType.INTEGER);
            }catch(Throwable e){
                return -1;
            }
        }
        return -1;
    }
    public void setIndex(ItemMeta meta, int index){
        meta.getPersistentDataContainer().set(KEY_INDEX, PersistentDataType.INTEGER, index);
    }
    public int getStatus(ItemMeta meta){
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container.has(KEY_STATUS)){
            try{
                return container.get(KEY_STATUS, PersistentDataType.INTEGER);
            }catch(Throwable e){
                return -1;
            }
        }
        return -1;
    }
    public void setStatus(ItemMeta meta, int status){
        meta.getPersistentDataContainer().set(KEY_STATUS, PersistentDataType.INTEGER, status);
    }
    public void addStorageInfo(ItemMeta meta,int index,int sizeX,int sizeY,int sizeZ){
        List<String> lores=meta.getLore();
        lores=new ArrayList<>( lores.subList(0,Math.max(lores.size()-2,0)));
        lores.add(INDEX_PREFIX+index);
        lores.add(INDEX_PREFIX+String.join("x",String.valueOf(sizeX),String.valueOf(sizeY),String.valueOf(sizeZ)));
        meta.setLore(lores);
    }
    public SpaceStorageCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public void addInfo(ItemStack stack){
        super.addInfo(stack);
        ItemMeta meta=stack.getItemMeta();
        List<String> lore=meta.getLore();
        lore.add(INDEX_PREFIX);
        lore.add(LOCSIZE_PREFIX);
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }

}
