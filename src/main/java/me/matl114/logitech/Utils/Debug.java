package me.matl114.logitech.Utils;

import java.util.logging.Logger;

public class Debug {
    public  static Logger log = Logger.getLogger(AddUtils.ADDON_ID);
    public  static boolean debug = true;
    public static  void logger(String message) {
        if (debug) {
        log.info(message);
        }
    }
    public static  void logger(int message) {
        logger(Integer.toString(message));
    }
    public static void logger(Object ... msgs){
        String msg="";
        for(Object m : msgs){
            msg+=" "+m.toString();
        }
        logger(msg);
    }
}
