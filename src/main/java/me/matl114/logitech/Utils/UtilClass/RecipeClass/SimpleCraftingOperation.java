package me.matl114.logitech.Utils.UtilClass.RecipeClass;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;

public class SimpleCraftingOperation implements MachineOperation {
    private ItemConsumer[] outputItems;
    private int totalTicks;
    private int currentTicks;
    public SimpleCraftingOperation(ItemConsumer[] outputItems,int time) {
        this.outputItems = outputItems;
        this.totalTicks = time;
        this.currentTicks = 0;
    }
    public void addProgress(int var1){
        this.currentTicks += var1;
    }

    public int getProgress(){
        return this.currentTicks;
    }

    public int getTotalTicks(){
        return this.totalTicks;
    }

    public int getRemainingTicks() {
        return this.totalTicks-this.currentTicks;
    }

    public boolean isFinished() {
        return this.totalTicks<=this.currentTicks;
    }
    public ItemConsumer[] getResults(){
        return this.outputItems;
    }
}