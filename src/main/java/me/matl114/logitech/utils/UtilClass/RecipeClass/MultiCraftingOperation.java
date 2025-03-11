package me.matl114.logitech.utils.UtilClass.RecipeClass;

import me.matl114.logitech.utils.UtilClass.ItemClass.ItemGreedyConsumer;

public class MultiCraftingOperation implements CustomMachineOperation {
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


    public void progress(int var1){
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
