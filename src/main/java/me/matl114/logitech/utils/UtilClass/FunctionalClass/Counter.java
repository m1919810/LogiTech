package me.matl114.logitech.utils.UtilClass.FunctionalClass;

public class  Counter<T extends Object> {
    int a;
    T value;
    public Counter() {
        a = 0;
        value = null;
    }
    public Counter(int a) {
        this.a = a;
        value = null;
    }
    public Counter(int a,T value) {
        this.a = a;
        this.value = value;
    }
//    public void increment() {
//        a+=1;
//    }
//    public void decrement() {
//        a-=1;
//    }
//    public int getCounter() {
//        return a;
//    }
    public void setCounter(int value) {
        a=value;
    }
    public T getValue() {
        synchronized (this){
            return value;
        }
    }
    public void setValue(T value) {
        synchronized (this){
            this.value = value;
        }
    }
    public synchronized  void updateValue(T value,int counterStamp) {
        synchronized (this){
            this.value=value;
            this.a=counterStamp;
        }
    }
//    public T read(int counterStamp){
//        return read(counterStamp,1);
//    }
    public T read(int counterStamp,int delayValue){
        synchronized (this){
            if(this.a>=counterStamp-delayValue){
                return this.value;
            }else {
                this.value=null;
                return null;
            }
        }
    }

}
