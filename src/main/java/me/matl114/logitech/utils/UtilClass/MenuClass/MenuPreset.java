package me.matl114.logitech.utils.UtilClass.MenuClass;

import java.util.HashMap;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.inventory.ItemStack;

public class MenuPreset {

    int size;
    int prelen;
    int suflen;

    public MenuPreset(int size) {
        this(size, 0, 0);
    }

    public MenuPreset(int size, int prelen, int suflen) {

        this.size = size;
        this.prelen = prelen;
        this.suflen = suflen;
    }

    HashMap<Integer, ItemStack> preitems = new HashMap<>();
    HashMap<Integer, ChestMenu.MenuClickHandler> prehandlers = new HashMap<>();

    public MenuPreset addItem(ItemStack item, int... slot) {
        for (int slot_i : slot) preitems.put(slot_i, item);
        return this;
    }

    public MenuPreset addItem(ChestMenu.MenuClickHandler handler, int... slot) {
        for (int slot_i : slot) prehandlers.put(slot_i, handler);
        return this;
    }

    public MenuPreset addItem(ItemStack item, ChestMenu.MenuClickHandler handler, int... slot) {
        for (int slot_i : slot) prehandlers.put(slot_i, handler);
        return addItem(item, slot);
    }

    public HashMap<Integer, ItemStack> getPreitems() {
        return preitems;
    }

    public HashMap<Integer, ChestMenu.MenuClickHandler> getPrehandlers() {
        return prehandlers;
    }

    public int getSize() {
        return size;
    }

    public MenuPreset setSize(int size) {
        this.size = size;
        return this;
    }

    public int getPrelen() {
        return prelen;
    }

    public int getSuflen() {
        return suflen;
    }
}
