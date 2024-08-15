package me.matl114.logitech;

import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

public class TestCounter  implements Cloneable {
    TestStack it;
    int amount;
    boolean dirty;

    public TestCounter(TestStack it) {
        this.it = it;
        this.amount = it.getAmount();
        this.dirty=false;
    }
    public TestCounter clone() {
        TestCounter it=null;
        try{
            it=(TestCounter)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return it;
    }
    public boolean equals(Object o){
        return false;
    }
    public void init(TestStack it){
        Tests.log("TestCounter init");
        this.it=it;
        this.amount=it.getAmount();
        this.dirty=false;
    }
}