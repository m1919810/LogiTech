package me.matl114.logitech.Utils;

public class TickCounter {
    public static boolean IS_DIRTY;
    public static int TICK_COUNT=0;
    public static int getTick(){
        if(IS_DIRTY){
            IS_DIRTY=!IS_DIRTY;
            TICK_COUNT++;

        }
        return TICK_COUNT;

    }
}
