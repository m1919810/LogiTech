package me.matl114.logitech;


import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.SlimefunItem.AddSlimefunItems;
import me.matl114.logitech.SlimefunItem.Machines.AbstractAdvancedProcessor;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.SlimefunItem.Machines.AbstractProcessor;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

public class Tests {
    public static void log(String message) {
        System.out.println("Test: "+message);
    }


    @Test
    public void test_init(){
        TestStack[] testStacks = new TestStack[]{
                new TestStack(1),
                new TestStack(2),
                new TestStack(3),
        };
        long a =System.nanoTime();
        TestCounter is=null;
        for(int i=0;i<3;++i){
            is=new TestCounter (testStacks[i]);
        }
        long b =System.nanoTime();

        log("time cost "+(b-a)+" ns");
        long c =System.nanoTime();
        TestCounter is2=null;
        for(int i=0;i<3;++i){
            is2=is.clone();
            is2.amount=testStacks[i].getAmount();
            is2.dirty=false;
            is2.it=testStacks[i];
        }
        long d =System.nanoTime();

        log("time cost "+(d-c)+" ns");
        log("check amount "+is2.amount);
        log("check if equal "+((is2==is)?1:0));
        TestConsumer s=new TestConsumer(testStacks[1]);
        TestConsumer s2=s.clone();
        log("check amounnt"+s2.amount);
        s.amount=10;
        log("check if equal "+s2.amount);
        TestSlotPusher itp=new TestSlotPusher(testStacks[1],3);
        TestSlotPusher itp2=itp.clone();
        itp2.init(testStacks[2],5);
        log(itp2.amount+" "+itp2.maxCnt+" "+itp2.slot);
        //ItemStack
    }
}
