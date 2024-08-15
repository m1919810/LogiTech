package me.matl114.logitech.Utils.Algorithms;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

import java.util.AbstractList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class DynamicArray<T> extends AbstractList<T> implements List<T> {
    private T[] array;
    private int size;
    private IntFunction<T> func;
    public DynamicArray(IntFunction<T[]> generator , int size,IntFunction<T> indexer) {
        this.array=generator.apply(size);
        this.size=size;
        this.func=indexer;
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public T get(int index){
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException();
        }
        T a=array[index];
        if(a!=null){
            return a;
        }
        else {
            array[index]=func.apply(index);
            return array[index];
        }
    }
    public T[] getResult(){
        return array;
    }
}
