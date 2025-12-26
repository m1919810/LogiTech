package me.matl114.logitech.core.Items.SpecialItems;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import java.util.ArrayList;
import java.util.List;
import me.matl114.logitech.core.Blocks.AbstractBlock;
import me.matl114.logitech.manager.PostSetupTasks;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.AddUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.*;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class BNoiseHead extends AbstractBlock {

    public BNoiseHead(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        PostSetupTasks.addPostRegisterTask(() -> Schedules.launchSchedules(
                mnThread, 100, false, Slimefun.getTickerTask().getTickRate()));
    }

    public ItemStack of(Sound sound) {
        ItemStack itemStack = getItem().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            PersistentDataAPI.setString(itemMeta, KEY_BNOUSE, sound.name());
            List<String> lore = itemMeta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }
            lore.add(ChatColor.AQUA + "声音来源: " + sound.name());
            itemMeta.setLore(lore);
            if (itemMeta instanceof SkullMeta meta) {
                meta.setNoteBlockSound(sound.getKey());
            }
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    private static final BukkitRunnable mnThread = new BukkitRunnable() {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack itemStack = player.getInventory().getHelmet();
                if (itemStack != null
                        && itemStack.getType() == Material.PLAYER_HEAD
                        && SlimefunItem.getByItem(itemStack) instanceof BNoiseHead bnoise) {
                    Sound sound = BNoiseHead.getSound(itemStack);
                    if (sound == null) {
                        continue;
                    }
                    player.playSound(player.getLocation(), sound, 1, 1);
                }
            }
        }
    };

    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        if (e.getBlock().getState(false) instanceof Skull skull) {
            NamespacedKey key = skull.getNoteBlockSound();
            if (key != null) {
                for (Sound sound : Sound.values()) {
                    if (key.equals(sound.getKey())) {
                        e.setDropItems(false);
                        e.setExpToDrop(0);
                        Location loc = e.getBlock().getLocation();
                        loc.getWorld().dropItemNaturally(loc.clone().add(0.5, 0.5, 0.5), of(sound));
                        return;
                    }
                }
            }
        }
    }

    private static NamespacedKey KEY_BNOUSE = AddUtils.getNameKey("sound");

    public static Sound getSound(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        String soundName = PersistentDataAPI.getString(itemMeta, KEY_BNOUSE);
        if (soundName == null) {
            return null;
        }
        return Sound.valueOf(soundName);
    }
}
