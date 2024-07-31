package me.matl114.logitech.Utils;

import java.util.logging.Logger;

public class Debug {
    public  static Logger log = Logger.getLogger(AddUtils.ADDON_NAME);
    public  static boolean debug = true;
    public static  void logger(String message) {
        if (debug) {
        log.info(message);
        }
    }
    public static  void logger(int message) {
        logger(Integer.toString(message));
    }
}
