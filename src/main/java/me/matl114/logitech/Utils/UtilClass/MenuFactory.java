package me.matl114.logitech.Utils.UtilClass;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MenuFactory {
    private MenuPreset preset;
    int pages;
    int size;
    int prev;
    int next;
    String title;
    ItemStack[] prefixs;
    ItemStack[] suffixs;
    List<ItemStack> inventory;
    List<ChestMenu.MenuClickHandler> handlers;
    ItemStack[] finalInventory;
    ChestMenu.MenuClickHandler[] finalHandlers;
    boolean isFinal;
    public MenuFactory(MenuPreset preset,String title,int pages) {
        this.isFinal=false;
        this.title=title;
        this.preset = preset;
        this.pages = pages;
        this.size = preset.getSize();
        this.prefixs = new ItemStack[preset.getPrelen()];

        for(int i=0;i<preset.getPrelen();i++) {
            prefixs[i]= ChestMenuUtils.getBackground();
        }
        this.suffixs = new ItemStack[preset.getSuflen()];
        for(int i=0;i<preset.getSuflen();i++) {
            suffixs[i]= ChestMenuUtils.getBackground();
        }
        this.inventory = new ArrayList<>();
        this.handlers=new ArrayList<>();
        int preSize=pages*(this.size-preset.getPrelen()-preset.getSuflen());
        for(int i=0;i<preSize;i++) {
            inventory.add(null);
            handlers.add(null);
        }
        this.prev=-1;
        this.next=-1;
        init();
    }
    public MenuFactory setNext(int slot) {
        if(isFinal)return this;
        next = slot;
        return this;
    }
    public MenuFactory setPrev(int slot) {
        if(isFinal)return this;
        prev = slot;
        return this;
    }
    public abstract void init();
    public MenuFactory setDefaultNPSlots(){
        if(isFinal)return this;
        this.setNext(size-2).setPrev(size-8);
        return this;
    }
    public MenuFactory addInventory(int pos,ItemStack item){
        if(isFinal)return this;
        while(pos>=inventory.size()) {
            inventory.add(null);
            handlers.add(null);
        }
        inventory.set(pos,item);
        return this;
    }
    public MenuFactory addInventory(int pos,ItemStack item,ChestMenu.MenuClickHandler handler){
        if(isFinal)return this;
        while(pos>=handlers.size()){
            handlers.add(null);
            inventory.add(null);
        }
        handlers.set(pos,handler);
        return addInventory(pos,item);
    }
    public MenuFactory addHandler(int pos,ChestMenu.MenuClickHandler handler){
        if(isFinal)return this;
        while(pos>=handlers.size()){
            handlers.add(null);
            inventory.add(null);
        }
        handlers.set(pos,handler);
        return this;
    }
    public ItemStack getInventory(int pos){
        if(isFinal) {
            if(pos>=finalInventory.length) {
                return null;
            }
            else return finalInventory[pos];
        }
        if(pos>=inventory.size()){
            return  null;
        }else return inventory.get(pos);
    }
    public ChestMenu.MenuClickHandler getHandler(int pos){
        if (isFinal){
            if(pos>=finalHandlers.length) {
                return null;
            }else return finalHandlers[pos];
        }
        if(pos>=handlers.size()) {
            return null;
        }else return handlers.get(pos);
    }
    public MenuFactory makeFinal(){
        isFinal=true;
        if(inventory.size()!=handlers.size()){
            throw new RuntimeException("An internal error occurs while creating Menu ... inventory size doesn't match handler size : size "+inventory.size()+" and size "+handlers.size());
        }
        for(int i=0;i<handlers.size();i++) {
            if(handlers.get(i)==null) {
                handlers.set(i,ChestMenuUtils.getEmptyClickHandler());
            }
        }
        finalInventory=inventory.toArray(new ItemStack[inventory.size()]);
        finalHandlers=handlers.toArray(new ChestMenu.MenuClickHandler[handlers.size()]);
        inventory=null;
        handlers=null;
        return  this;
    }
    public CustomMenu build(){
        CustomMenu a;
        if(isFinal){
            a=new CustomMenu(title,size,finalInventory.length,prefixs,suffixs,finalInventory,finalHandlers);
        }else{
            for(int i=0;i<handlers.size();i++) {
                if(handlers.get(i)==null) {
                    handlers.set(i,ChestMenuUtils.getEmptyClickHandler());
                }
            }
            a=new CustomMenu(title,size,inventory.size(),prefixs,suffixs,inventory.toArray(new ItemStack[inventory.size()]),handlers.toArray(new ChestMenu.MenuClickHandler[handlers.size()]));
        }
        if(next>=0&&next<size){
            a.setNextPageButtom(next);
        }
        if(prev>=0&&prev<size){
            a.setPrevPageButtom(prev);
        }
        for(Map.Entry<Integer,ItemStack> par:preset.getPreitems().entrySet()){
            a.overrideItem(par.getKey(),par.getValue());
        }
        for(Map.Entry<Integer,ChestMenu.MenuClickHandler> par:preset.getPrehandlers().entrySet()){
            a.overrideHandler(par.getKey(),par.getValue());
        }
        return a;
    }
}
