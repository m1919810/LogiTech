package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.SlimefunItem.Machines.ChunkLimit;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;

public class LineEnergyCharger extends AbstractEnergyCharger  {
    protected final int MAX_LEN=64;
//    protected final ItemStack[] DIRECTION_ITEM=new ItemStack[]{
//            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 无"),
//            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向北")),
//            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向西")),
//            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向南")),
//            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向东")),
//            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向上")),
//            AddUtils.addGlow( new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6点击切换方向","&3当前方向: 向下"))
//    };
    public LineEnergyCharger(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                              int energybuffer){
        super(category, item, recipeType, recipe, energybuffer);
    }
    public Collection<SlimefunBlockData> getChargeRange(BlockMenu inv, Block block, SlimefunBlockData data){
        Location loc=block.getLocation();
        return DataCache.getAllSfItemInChunk(loc.getWorld(),loc.getBlockX()>>4,loc.getBlockZ()>>4);
    }


}