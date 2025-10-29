package me.matl114.logitech.core.Interface;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.entity.Player;

public interface LogiTechExperimental {
    static boolean checkUsePermission(Player p, boolean sendMessage){
        if( p.hasPermission("logitech.experimental.test")){
            return true;
        }else {
            if (sendMessage) {
                Slimefun.getLocalization().sendMessage(p, "messages.no-permission", true);
            }
            return false;
        }
    }
}
