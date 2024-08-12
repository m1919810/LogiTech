package me.matl114.logitech.Utils.UtilClass.CargoClass;

public class CargoConfig {
    public enum Config{
        IS_SYMM,
        IS_NONNULL,
        IS_BLKLST,
        IS_TO,
        ;
        Config(){

        }
    }
    public static boolean getBoolConfig(){
        return true;
    }
}
