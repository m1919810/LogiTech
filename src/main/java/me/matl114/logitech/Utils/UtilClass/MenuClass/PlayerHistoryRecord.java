package me.matl114.logitech.Utils.UtilClass.MenuClass;

import org.bukkit.entity.Player;

public interface PlayerHistoryRecord<T extends Object> {
    T getRecord(Player player);
    void addRecord(Player player, T record);
    T deleteRecord(Player player,T record);
}
