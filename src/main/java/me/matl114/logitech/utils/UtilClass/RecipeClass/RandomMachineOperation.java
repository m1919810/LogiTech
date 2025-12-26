package me.matl114.logitech.utils.UtilClass.RecipeClass;

import java.util.Random;

public class RandomMachineOperation implements CustomMachineOperation {
    int totalTicks;
    long key;
    int currentTick;

    public RandomMachineOperation(int time, long key) {
        this.totalTicks = time;
        this.key = key;
        this.currentTick = 0;
    }

    static Random rand = new Random();

    public void progress(int var1) {}

    public void randProgress(int amount, long value) {
        if (key == value) {
            currentTick += amount;
        } else {
            int offset = ((rand.nextInt(0, 4) == 0) ^ (key < value)) ? 1 : -1;
            currentTick += amount * offset;
        }
        currentTick = Math.max(currentTick, -1);
    }

    @Override
    public int getProgress() {
        return this.currentTick;
    }

    public int getTotalTicks() {
        return this.totalTicks;
    }

    public int getRemainingTicks() {
        return Math.max(0, this.totalTicks - this.currentTick);
    }
}
