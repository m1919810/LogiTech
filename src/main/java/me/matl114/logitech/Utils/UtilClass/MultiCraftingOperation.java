package me.matl114.logitech.Utils.UtilClass;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;

public class MultiCraftingOperation implements MachineOperation {
    private ItemGreedyConsumer[] outputItems;
    private int totalTicks;
    private int currentTicks;
    public MultiCraftingOperation(ItemGreedyConsumer[] outputItems,int time) {
        this.outputItems = outputItems;
        this.totalTicks = time;
        this.currentTicks = 0;
        for (int i = 0; i < outputItems.length; i++) {
            outputItems[i].clearRelated();
        }
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
    public ItemGreedyConsumer[] getResults(){
        return this.outputItems;
    }
}
