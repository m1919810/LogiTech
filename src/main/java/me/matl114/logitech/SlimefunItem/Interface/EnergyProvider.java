package me.matl114.logitech.SlimefunItem.Interface;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.BlockDataConfigWrapper;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.Location;

import javax.annotation.Nonnull;

public interface EnergyProvider extends EnergyNetProvider {
    default boolean willExplode(@Nonnull Location l, @Nonnull SlimefunBlockData data) {
        return false;
    }
}
