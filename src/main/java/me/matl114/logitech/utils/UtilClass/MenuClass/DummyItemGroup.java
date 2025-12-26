package me.matl114.logitech.utils.UtilClass.MenuClass;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import javax.annotation.ParametersAreNonnullByDefault;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DummyItemGroup extends ItemGroup {

    @ParametersAreNonnullByDefault
    public DummyItemGroup(NamespacedKey key, ItemStack item) {
        super(key, item);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isHidden(Player p) {
        return true;
    }
}
