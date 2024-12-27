package me.matl114.logitech.Utils.Algorithms;

import me.matl114.logitech.Utils.MathUtils;

public class AtomicCounter {
    private volatile int value;
    private final int maxinum;
    private byte[] lock=new byte[0];
    public AtomicCounter(int value,int max) {
        this.value = value;
        this.maxinum = max;
    }
    public boolean enough(){
        return this.value >= maxinum;
    }
    public boolean empty(){
        return this.value <= 0;
    }

    /**
     * return part that can't add into value
     * @param value
     * @return
     */
    public int increment(int value){
        int more=0;
        synchronized (lock) {
            this.value+=value;
            more=this.value-maxinum;
            if(more>=0){
                this.value=maxinum;
            }
        }
        return Math.max(more, 0);
    }
    /**
     * return part that can't add into value
     * @param value
     * @return
     */
    public int safeIncrement(int value){
        int expected=this.maxinum-value;
        synchronized (lock) {
            if(this.value<=expected){
                this.value+=value;
                return 0;
            }else{
                expected-=this.value;
                this.value=maxinum;
            }
        }
        return -expected;
    }

    /**
     * return the required value,if not enough return as mush as it can
     * @param value
     * @return
     */
    public int required(int value){
        synchronized (lock) {
            if(this.value>=value){
                this.value-=value;
                return value;
            }else {
                value=this.value;
                this.value=0;
            }
        }
        return value;
    }

    public int get(){
        return value;
    }
}
