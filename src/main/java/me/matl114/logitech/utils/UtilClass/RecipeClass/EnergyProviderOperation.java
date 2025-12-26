package me.matl114.logitech.utils.UtilClass.RecipeClass;

import me.matl114.logitech.utils.UtilClass.ItemClass.ItemConsumer;

public class EnergyProviderOperation implements CustomMachineOperation {
    private ItemConsumer[] outputItems;
    private int energyPertick;
    private int totalTicks;
    private int currentTicks;

    public EnergyProviderOperation(ItemConsumer[] outputItems, int time, int energyPerTick) {
        this.outputItems = outputItems;
        this.totalTicks = time;
        this.currentTicks = 0;
        this.energyPertick = energyPerTick;
    }

    public void progress(int var1) {
        this.currentTicks += var1;
    }

    public int getProgress() {
        return this.currentTicks;
    }

    public int getTotalTicks() {
        return this.totalTicks;
    }

    public int getRemainingTicks() {
        return this.totalTicks - this.currentTicks;
    }

    public boolean isFinished() {
        return this.totalTicks <= this.currentTicks;
    }

    public ItemConsumer[] getResults() {
        return this.outputItems;
    }

    public int getEnergy() {
        return this.energyPertick;
    }
}
