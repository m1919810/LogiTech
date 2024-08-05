package me.matl114.logitech.Utils;

import java.util.Arrays;
import java.util.List;

public class Utils {
    public static void log(String... message) {
        Debug.logger(String.join(" ", message));
    }
    public static <T extends Object> List<T> list(T... objs) {
        return Arrays.asList(objs);
    }
    public static <T extends Object> T[] array(T... objs) {
        return objs;
    }
}
