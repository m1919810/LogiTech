package me.matl114.logitech.Utils.UtilClass.RecipeClass;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;

public interface CustomMachineOperation extends MachineOperation {
    public abstract void progress(int i);
    @Override
    default void addProgress(int i) {

    }
}
