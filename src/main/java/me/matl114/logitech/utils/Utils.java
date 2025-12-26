package me.matl114.logitech.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemStack;

public class Utils {
    public static void log(String... message) {
        Debug.logger(String.join(" ", message));
    }

    public static <T extends Object> List<T> list(T... objs) {
        return Arrays.stream(objs).collect(Collectors.toCollection(ArrayList::new));
    }

    public static <T extends Object> T[] array(T... objs) {
        return objs;
    }

    public static ItemStack[] toArray(List<ItemStack> list) {
        return list.toArray(new ItemStack[list.size()]);
    }

    public static ItemStack[] recipe(Object... v) {
        return Arrays.stream(v).map(AddUtils::resolveItem).toArray(ItemStack[]::new);
    }
}
