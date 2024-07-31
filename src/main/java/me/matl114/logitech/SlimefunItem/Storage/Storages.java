package me.matl114.logitech.SlimefunItem.Storage;

import me.matl114.logitech.SlimefunItem.Storage.NetworkAdapts.QuantumStorage;
import me.matl114.logitech.SlimefunItem.Storage.Singularity.SingularityStorage;

public class Storages {
    public static final StorageType SINGULARITY=new SingularityStorage();
    public static final StorageType ADAPT_NTWSTORAGE=new QuantumStorage();
}
