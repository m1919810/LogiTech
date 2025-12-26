package me.matl114.logitech.utils.UtilClass.SpecialItemClass;

import me.matl114.logitech.utils.AddUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;

public enum CustomFireworkStar {
    RTP_RUNE(AddUtils.START_CODE);
    ItemStack item;

    CustomFireworkStar(int t) {
        item = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof FireworkEffectMeta fem) {
            fem.setEffect(FireworkEffect.builder()
                    .with(FireworkEffect.Type.BURST)
                    .withColor(Color.fromRGB(t))
                    .build());
        }
        item.setItemMeta(meta);
        AddUtils.hideAllFlags(item);
    }

    public ItemStack getItem() {
        return item;
    }
}
