package me.matl114.logitech.SlimefunItem.Items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.Schedule.Schedules;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BNoiseHead extends MaterialItem {
    public static boolean can_access = true;
    public BNoiseHead(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        SchedulePostRegister.addPostRegisterTask(
                ()-> Schedules.launchSchedules(
                        mnThread, 100,false, Slimefun.getTickerTask().getTickRate()
                )
        );
    }

    private static final BukkitRunnable mnThread = new BukkitRunnable() {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack itemStack = player.getInventory().getHelmet();
                if (SlimefunItem.getByItem(itemStack) instanceof BNoiseHead) {
                    Sound sound = BNoiseHead.getSound(itemStack);
                    if (sound == null) {
                        continue;
                    }

                    player.playSound(player.getLocation(), sound, 1, 1);
                }
            }
        }
    };


    public ItemStack of(Sound sound) {
        ItemStack itemStack = getItem().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }

        PersistentDataAPI.setString(itemMeta, new NamespacedKey(MyAddon.getInstance(), "sound"), sound.name());

        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(ChatColor.AQUA + "声音来源: " + sound.name());
        itemMeta.setLore(lore);

        if (can_access) {
            if (itemMeta instanceof SkullMeta skullMeta) {
                try {
                    skullMeta.setNoteBlockSound(sound.getKey());
                } catch (Throwable ignored) {
                    can_access = false;
                }
            }
        }

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
