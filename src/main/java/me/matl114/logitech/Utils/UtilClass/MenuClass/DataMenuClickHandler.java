package me.matl114.logitech.Utils.UtilClass.MenuClass;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface DataMenuClickHandler extends ChestMenu.MenuClickHandler {
    default int getInt(int val){
        return 0;
    }
    default void setInt(int val, int val2){

    }
    default String getString(int val){
        return "";
    }
    default void setString(int val, String val2){

    }
    default ItemStack getItemStack(int val){
        return null;
    }
    default void setItemStack(int val, ItemStack val2){

    }
    default Location getLocation(int val){
        return null;
    }
    default void setLocation(int val, Location val2){

    }
    default Object getObject(int val){
        return null;
    }
    default void setObject(int val,Object val2){

    }


}
