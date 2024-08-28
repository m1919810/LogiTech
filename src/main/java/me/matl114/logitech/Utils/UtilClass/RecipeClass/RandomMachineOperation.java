package me.matl114.logitech.Utils.UtilClass.RecipeClass;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;

import java.util.Random;

public class RandomMachineOperation extends SimpleCraftingOperation {
    public RandomMachineOperation(ItemConsumer[] outputItems,int time) {
        super(outputItems,time);
    }
    static Random rand=new Random();
    public void addProgress(int var1){
        this.currentTicks += rand.nextInt(2*var1+1)-var1;
        this.currentTicks=Math.max(this.currentTicks,0);
    }
    public int getProgress(){
        return
                Math.min(this.currentTicks,this.totalTicks);
    }

    public int getTotalTicks(){
        return this.totalTicks;
    }

    public int getRemainingTicks() {
        return Math.max(0, this.totalTicks-this.currentTicks);
    }


}
