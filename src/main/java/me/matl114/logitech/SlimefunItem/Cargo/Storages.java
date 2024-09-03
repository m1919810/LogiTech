package me.matl114.logitech.SlimefunItem.Cargo;

import me.matl114.logitech.Depends.NetworksQuantumProxy;
import me.matl114.logitech.SlimefunItem.AddDepends;
import me.matl114.logitech.Depends.NetworksAdaptQuantumStorage;
import me.matl114.logitech.SlimefunItem.Cargo.Singularity.SingularityProxy;
import me.matl114.logitech.SlimefunItem.Cargo.Singularity.SingularityStorage;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.StorageClass.StorageType;

import java.util.function.Supplier;

public class Storages {
    public static void setup(){

    }
    public static final StorageType SINGULARITY=new SingularityStorage();

    public static final StorageType NTWSTORAGE_ADAPTOR =((Supplier<StorageType>)(()->{
        if(AddDepends.hasNetwork){
            try{
                return new NetworksAdaptQuantumStorage();
            }catch (Throwable e){
                Debug.logger("WARNING: AN INTERNAL ERROR OCCUR WHEN ADAPTING NETWORK STORAGE,DISABLING RELAVENT FUNCTION");
            }
        }
        return null;
    })).get();
    public static final StorageType NTWSTORAGE_PROXY =((Supplier<StorageType>)(()->{
        if(AddDepends.hasNetwork){
            try{
                return new NetworksQuantumProxy();
            }catch (Throwable e){
                Debug.logger("WARNING: AN INTERNAL ERROR OCCUR WHEN ADAPTING NETWORK STORAGE,DISABLING RELAVENT FUNCTION");
            }
        }
        return null;
    })).get();
    public static final StorageType SINGULARITY_PROXY=new SingularityProxy();

}
