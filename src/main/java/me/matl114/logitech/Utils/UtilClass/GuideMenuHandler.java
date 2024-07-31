package me.matl114.logitech.Utils.UtilClass;

import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;

public interface GuideMenuHandler {
    public ChestMenu.MenuClickHandler provide(Player player, PlayerProfile profile, SlimefunGuideMode mode);

}
