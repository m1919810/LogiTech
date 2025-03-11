package me.matl114.logitech.utils.Algorithms;

import java.util.AbstractList;
import java.util.List;

public class  SimpleLinkList<T> extends AbstractList<T> implements List<T> {
    List<T> list;
    int len;
    List<T> data;
    int[] next;
    public SimpleLinkList(List<T> list) {
        this.len =list.size() ;
        this.data = list;
        this.next = new int[len+1];
        this.next[len]=-1;
    }
    public T get(int i){
        return data.get(i);
    }
    public int size(){
        return len;
    }
    public boolean isEmpty(){
        return len == 0;
    }
    public void deleteNext(int index){
        int tar=getNext(getNext(index)) ;
        next[index+1]=tar;
    }
    public int getNext(int index){
        if(next[index+1]==0){
            next[index+1]=index+1;
        }return next[index+1];
    }
    public boolean hasNext(int index){
        return getNext(index)>=0;
    }
}
