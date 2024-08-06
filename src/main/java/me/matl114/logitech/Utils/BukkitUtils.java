package me.matl114.logitech.Utils;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;

public class BukkitUtils {
    public static final RecipeType VANILLA_CRAFTTABLE =new RecipeType(AddUtils.getNameKey("vanilla_crafttable"),
            new CustomItemStack(Material.CRAFTING_TABLE,null,"","&6万物起源 工作台"));
    public static final RecipeType VANILLA_FURNACE=new RecipeType(AddUtils.getNameKey("vanilla_furnace"),
            new CustomItemStack(Material.FURNACE,null,"","&6原版熔炉"));
}
