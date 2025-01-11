package me.matl114.logitech.core.Cargo;

import me.matl114.logitech.core.Depends.NetworksQuantumProxyAdapter;
import me.matl114.logitech.core.Registries.AddDepends;
import me.matl114.logitech.core.Depends.NetworksQuantumStorageAdapter;
import me.matl114.logitech.core.AddSlimefunItems;
import me.matl114.logitech.core.Cargo.Singularity.SingularityProxy;
import me.matl114.logitech.core.Cargo.Singularity.SingularityStorage;
import me.matl114.logitech.core.CustomSlimefunItem;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.StorageClass.StorageType;

import java.util.function.Supplier;

public class Storages {
    public static void setup(){

    }
    public static void disableNetworkStorage(){

        AddDepends.NTW_STORAGE_DISPLAY.addDisplayRecipe(AddUtils.getInfoShow("&c警告!",
                "&7由于网络拓展未知错误,该物品已经不兼容",
                "&7请联系服务器管理员检查网络版本",
                "&7如果你认为这是一个偶发性错误或者损坏的物品导致的问题,请重启服务器",
                "&7如果你认为这是网络版本问题,请更换网络版本以重新兼容该物品"));
        AddDepends.NTW_STORAGE_DISPLAY.addDisplayRecipe(null);
    }
    public static  void disableNetworkProxy(){
        try{
            ((CustomSlimefunItem) AddSlimefunItems.QUANTUM_LINK).addDisplayRecipe(AddUtils.getInfoShow("&c警告!",
                    "&7由于网络拓展未知错误,该物品已经不兼容网络存储",
                    "&7请联系服务器管理员检查网络版本",
                    "&7如果你认为这是一个偶发性错误或者损坏的物品导致的问题,请重启服务器",
                    "&7如果你认为这是网络版本问题,请更换网络版本以重新兼容该物品"));
            ((CustomSlimefunItem)AddSlimefunItems.QUANTUM_LINK).addDisplayRecipe(null);
        }catch (Throwable es){
            Debug.logger(es);
        }
    }
    public static final StorageType NTWSTORAGE_PROXY =((Supplier<StorageType>)(()->{
        if(AddDepends.hasNetwork){
            try{
                return new NetworksQuantumProxyAdapter();
            }catch (Throwable e){
                Debug.logger("WARNING: AN INTERNAL ERROR OCCUR WHEN ADAPTING NETWORK STORAGE,DISABLING RELAVENT FUNCTION");
                disableNetworkProxy();
            }
        }
        return null;
    })).get();
    public static final StorageType SINGULARITY_PROXY=new SingularityProxy();
    public static final StorageType NTWSTORAGE_ADAPTOR =((Supplier<StorageType>)(()->{
        if(AddDepends.hasNetwork){
            try{
                return new NetworksQuantumStorageAdapter();
            }catch (Throwable e){
                Debug.logger("WARNING: AN INTERNAL ERROR OCCUR WHEN ADAPTING NETWORK STORAGE,DISABLING RELAVENT FUNCTION");
                disableNetworkStorage();
            }
        }
        return null;
    })).get();
    public static final StorageType SINGULARITY=new SingularityStorage();



}
