package me.matl114.logitech.Utils.UtilClass.RecipeClass;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SequenceCraftingOperation implements MachineOperation {
    private ItemConsumer[] inputItems;
    private MachineRecipe recipe;
    private int tickPerSeq;
    private int totalTicks;
    private int currentTicks;
    private int currentSeqTicks;
    public SequenceCraftingOperation(MachineRecipe recipe) {
        this.recipe = recipe;
        ItemStack[] stack=recipe.getInput();
        this.inputItems=new ItemConsumer[stack.length];
        for(int i=0;i<stack.length;i++){
            this.inputItems[i]= CraftUtils.getConsumer(stack[i]);
        }
        this.totalTicks=this.inputItems.length;
        this.currentTicks = 0;
        this.tickPerSeq=recipe.getTicks();
        this.currentSeqTicks=0;

    }
    public void addProgress(int var1){
        if(this.currentSeqTicks<this.tickPerSeq){
            this.currentSeqTicks+=var1;
        }
        if(this.currentSeqTicks>=this.tickPerSeq&&this.inputItems[this.currentTicks].getAmount()<=0){
            this.currentTicks += 1;
            this.currentSeqTicks=0;
        }
    }

    public int getProgress(){
        return this.currentSeqTicks;
    }
    public int getTotalTicks(){
        return this.tickPerSeq;
    }

    public int getRemainingTicks() {
        return this.tickPerSeq-this.currentSeqTicks;
    }

    public boolean isFinished() {
        return this.totalTicks<=this.currentTicks;
    }
    public ItemStack[] getResults(){
        return this.recipe.getOutput();
    }
    public ItemConsumer getRequiredItem(){
        return this.inputItems[this.currentTicks];
    }
    public int getSeqCount(){
        return this.totalTicks;
    }
    public int getNowSeq(){
        return this.currentTicks;
    }
    public ItemStack getDisplays(){
        List<String > lines = new ArrayList<>();
        lines.add("&7当前进程信息:");
        if(recipe instanceof SequenceMachineRecipe seqRecipe){
            int total=getSeqCount();
            int now=getNowSeq();
            for(int i=0;i<now;++i){
                lines.add(seqRecipe.displayedNames[i].formatted("&a",inputItems[i].getItem().getAmount(),"&a"));
            }
            if(now!=total)
                lines.add(seqRecipe.displayedNames[now].formatted("&e",inputItems[now].getItem().getAmount()-inputItems[now].getAmount(),"&e"));
            for(int i=now+1;i<total;++i){
                lines.add(seqRecipe.displayedNames[i].formatted("&c",0,"&c"));
            }
            lines.add(AddUtils.concat("&7当前物品处理时间: ",String.valueOf(this.currentSeqTicks),"t/",String.valueOf(this.tickPerSeq),"t"));
            ItemStack it=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a阶段输入进程",lines);
            return it;
        }else
            return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a阶段输入进程","&7当前进程信息","&c错误!使用的不是有序输入配方");
    }
}
