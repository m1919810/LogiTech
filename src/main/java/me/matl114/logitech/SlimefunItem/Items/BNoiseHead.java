package me.matl114.logitech.SlimefunItem.Items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Utils.UtilClass.CommandClass.LogitechMain;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BNoiseHead extends MaterialItem {

    public BNoiseHead(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public ItemStack of(Sound sound) {
        ItemStack itemStack = getItem().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataAPI.setString(itemMeta, new NamespacedKey(MyAddon.getInstance(), "sound"), sound.name());
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(ChatColor.AQUA + "声音来源: " + sound.name());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static Sound getSound(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        String soundName = PersistentDataAPI.getString(itemMeta, new NamespacedKey(MyAddon.getInstance(), "sound"));
        if (soundName == null) {
            return null;
        }
        return Sound.valueOf(soundName);
    }
}
