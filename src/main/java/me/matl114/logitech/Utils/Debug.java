package me.matl114.logitech.Utils;

import me.matl114.logitech.MyAddon;

import java.util.logging.Logger;

public class Debug {
    public  static Logger log = Logger.getLogger(AddUtils.ADDON_ID);
    public static Logger testlog=Logger.getLogger("TEST");
    public  static boolean debug = MyAddon.testmode();
    public static boolean pos=false;
    public static  void logger(String message) {
        log.info(message);
    }
    public static void debug(String message) {
        if( MyAddon.testmode()){
            testlog.info(message);
        }
    }
    public static  void logger(int message) {
        logger(Integer.toString(message));
    }
    public static void debug(int message) {
        debug(Integer.toString(message));
    }
    public static void logger(Object ... msgs){
        String msg="";
        for(Object m : msgs){
            msg+=" "+m.toString();
        }
        logger(msg);
    }
    public static void debug(Object ...msgs) {
        String msg="";
        for(Object m : msgs){
            msg+=" "+m.toString();
        }
        debug(msg);
    }
    public static void debug(Throwable t) {
        if( MyAddon.testmode()){
            t.printStackTrace();
        }
    }
}
