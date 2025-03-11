package me.matl114.logitech.utils.UtilClass.FunctionalClass;

import me.matl114.logitech.utils.AddUtils;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

public interface OutputStream {
    public static OutputStream getPlayerOut(Player player) {
        return (OutputStream) (out)->AddUtils.sendMessage(player,out.get());
    }
    public static OutputStream getNullStream(){
        return (out)->{};
    }
    public void out(Supplier<String> out);
}
