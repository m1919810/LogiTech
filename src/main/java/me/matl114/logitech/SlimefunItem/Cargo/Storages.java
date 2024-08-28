package me.matl114.logitech.SlimefunItem.Cargo;

import me.matl114.logitech.SlimefunItem.AddDepends;
import me.matl114.logitech.Depends.NetworksAdaptQuantumStorage;
import me.matl114.logitech.SlimefunItem.Cargo.Singularity.SingularityStorage;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.StorageClass.StorageType;

import java.util.function.Supplier;

public class Storages {
    public static void setup(){

    }
    public static final StorageType SINGULARITY=new SingularityStorage();

    public static final StorageType ADAPT_NTWSTORAGE=((Supplier<StorageType>)(()->{
        if(AddDepends.hasNetwork){
            try{
                return new NetworksAdaptQuantumStorage();
            }catch (Throwable e){
                Debug.logger("WARNING: AN INTERNAL ERROR OCCUR WHEN ADAPTING NETWORK STORAGE,DISABLING RELAVENT FUNCTION");
            }
        }
        return null;
    })).get();
    //TODO 支持绑定实体存储
    //TODO IO接口加一个均衡的 均算作实体存储

}
