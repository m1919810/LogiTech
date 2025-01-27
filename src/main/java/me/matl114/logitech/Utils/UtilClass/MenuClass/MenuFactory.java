package me.matl114.logitech.Utils.UtilClass.MenuClass;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.Getter;
import me.matl114.logitech.Utils.Debug;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MenuFactory {
    private final MenuPreset preset;
    int pages;
    int size;
    int prev;
    int next;
    int back;
    private CustomMenuHandler finalBackHandler=null;
    @Getter
    String title;
//    private ItemStack[] prefixs;
//    private ItemStack[] suffixs;
    private ItemStack backGround;
    List<ItemStack> inventory;
    List<CustomMenuHandler> handlers;
    private ItemStack[] finalInventory;
    private CustomMenuHandler[] finalHandlers;
    private  HashMap<Integer, ItemStack> overrideItem=new HashMap<>();
    private  HashMap<Integer, CustomMenuHandler> overrideHandler=new HashMap<>();
    boolean isFinal;
    public MenuFactory(MenuPreset preset,String title,int pages) {
        this.isFinal=false;
        this.title=title;
        this.preset = preset;
        this.pages = pages;
        this.size = preset.getSize();
//        this.prefixs = new ItemStack[preset.getPrelen()];
//
//        for(int i=0;i<preset.getPrelen();i++) {
//            prefixs[i]=this.backGround;
//        }
//        this.suffixs = new ItemStack[preset.getSuflen()];
//        for(int i=0;i<preset.getSuflen();i++) {
//            suffixs[i]= this.backGround;
//        }
        this.backGround=ChestMenuUtils.getBackground()  ;
        this.overrideHandler=new HashMap<>();
        this.overrideItem=new HashMap<>();
        HashMap<Integer, ChestMenu.MenuClickHandler> ovhandlers=preset.getPrehandlers();
        for (Map.Entry<Integer, ChestMenu.MenuClickHandler> entry : ovhandlers.entrySet()) {
            this.overrideHandler.put(entry.getKey(),CustomMenuHandler.from(entry.getValue()));
        }
        this.overrideItem.putAll(preset.getPreitems());
        this.inventory = new ArrayList<>();
        this.handlers=new ArrayList<>();
        int preSize=pages*(this.size-preset.getPrelen()-preset.getSuflen());
        for(int i=0;i<preSize;i++) {
            inventory.add(null);
            this.handlers.add(null);
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
    public MenuFactory setBack(int slot) {
        if(isFinal)return this;
        back = slot;
        return this;
    }
    public MenuFactory setBackHandler(CustomMenuHandler handler) {
        this.finalBackHandler=handler;
        return this;
    }
    public abstract void init();
    public MenuFactory setDefaultNPSlots(){
        if(isFinal)return this;
        this.setNext(size-2).setPrev(size-8);
        return this;
    }
    public MenuFactory addOverrides( int slot,ItemStack item) {
        if(isFinal)return this;
        overrideItem.put(slot, item);
        return this;
    }
    public MenuFactory addOverrides(int slot,ChestMenu.MenuClickHandler handler) {
        if(isFinal)return this;
        overrideHandler.put(slot, CustomMenuHandler.from(handler));
        return this;
    }
    public MenuFactory addOverrides(int slot,CustomMenuHandler handler) {
        if(isFinal)return this;
        overrideHandler.put(slot, handler);
        return this;
    }
    public MenuFactory addOverrides(int slot,ItemStack item, ChestMenu.MenuClickHandler handler) {
        if(isFinal)return this;
        overrideHandler.put(slot, CustomMenuHandler.from(handler));
        return addOverrides(slot,item);
    }
    public MenuFactory addOverrides(int slot,ItemStack item,CustomMenuHandler handler) {
        if(isFinal)return this;
        overrideHandler.put(slot,handler);
        return addOverrides(slot,item);
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
        handlers.set(pos,CustomMenuHandler.from(handler));
        return addInventory(pos,item);
    }
    public MenuFactory addInventory(int pos,ItemStack item,CustomMenuHandler handler){
        if(isFinal)return this;
        while(pos>=handlers.size()){
            handlers.add(null);
            inventory.add(null);
        }
        handlers.set(pos,handler);
        return addInventory(pos,item);
    }
    public MenuFactory addHandler(int pos,CustomMenuHandler handler){
        if(isFinal)return this;
        while(pos>=handlers.size()){
            handlers.add(null);
            inventory.add(null);
        }
        handlers.set(pos,handler);
        return this;
    }
    public MenuFactory addHandler(int pos,ChestMenu.MenuClickHandler handler){
        if(isFinal)return this;
        while(pos>=handlers.size()){
            handlers.add(null);
            inventory.add(null);
        }
        handlers.set(pos,CustomMenuHandler.from(handler));
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
    public int getInventorySize(){
        return finalInventory.length;
    }
    public CustomMenuHandler getHandler(int pos){
        if (isFinal){
            if(pos>=finalHandlers.length) {
                return null;
            }else return finalHandlers[pos];
        }
        if(pos>=handlers.size()) {
            return null;
        }else return handlers.get(pos);
    }
    public boolean hasOverridesItem(int loc){
        return overrideItem.containsKey(loc);
    }
    public boolean hasOverrideHandler(int loc){
        return overrideHandler.containsKey(loc);
    }
    public ItemStack getOverrideItem(int loc){
        return overrideItem.get(loc);
    }
    public CustomMenuHandler getOverrideHandler(int loc){
        return overrideHandler.get(loc);
    }
    public MenuFactory setBackGround(ItemStack item) {
        backGround = item;
        return this;
    }
    public int getPrefixSize(){
        return preset.getPrelen();
    }
    public ItemStack getPrefix(int loc){
        return this.backGround;
    }
    public int getSuffixSize(){
        return preset.getSuflen();
    }
    public ItemStack getSuffix(int loc){
        return this.backGround;
    }
    public MenuFactory makeFinal(){
        isFinal=true;
        if(inventory.size()!=handlers.size()){
            throw new RuntimeException("An internal error occurs while creating Menu ... inventory size doesn't match handler size : size "+inventory.size()+" and size "+handlers.size());
        }
        for(int i=0;i<handlers.size();i++) {
            if(handlers.get(i)==null) {
                handlers.set(i,CustomMenuHandler.empty());
            }
        }
        finalInventory=inventory.toArray(new ItemStack[inventory.size()]);
        finalHandlers=handlers.toArray(new CustomMenuHandler[handlers.size()]);
        inventory=null;
        handlers=null;
        return  this;
    }
    public CustomMenu build(){
        if(this.guideBuilder==null){
            return build(null);
        }else {
            return buildGuide(null,this.guideBuilder);
        }
    }
    public CustomMenu build(CustomMenuHandler fatherHandler){
        if(!isFinal)
            makeFinal();
        CustomMenu a=new CustomMenu(title,size,finalInventory.length,this);
        if(next>=0&&next<size){
            a.setNextPageButtom(next);
        }
        if(prev>=0&&prev<size){
            a.setPrevPageButtom(prev);
        }
        if(back>=0&&back<size){
            a.setBackSlot(back);
            if(fatherHandler!=null) {
                a.setBackHandler(fatherHandler.getInstance(a));
            }
            else if(finalBackHandler!=null){
                a.setBackHandler(finalBackHandler.getInstance(a));
            }
        }
        return a;
    }
    public PlayerHistoryRecord<CustomMenu> guideBuilder=null;
    public MenuFactory setGuideModHistory(PlayerHistoryRecord<CustomMenu> guideBuilder){
        if(guideBuilder!=null)
            this.guideBuilder=guideBuilder;
        return this;
    }
    public GuideCustomMenu buildGuide(CustomMenuHandler fatherHandler,PlayerHistoryRecord<CustomMenu> history){
        if(!isFinal)
            makeFinal();
        GuideCustomMenu a=new GuideCustomMenu(title,size,finalInventory.length,this);
        if(next>=0&&next<size){
            a.setNextPageButtom(next);
        }
        if(prev>=0&&prev<size){
            a.setPrevPageButtom(prev);
        }
        if(back>=0&&back<size){
            a.setBackSlot(back);
            if(fatherHandler!=null) {
                a.setBackHandler(fatherHandler.getInstance(a));
            }
            else if(finalBackHandler!=null){
                a.setBackHandler(finalBackHandler.getInstance(a));
            }
        }
        if(guideBuilder==null){
            a.setUseHistory(false);
        }else {
            a.setUseHistory(true);
            a.setHistory(guideBuilder);
        }
        return a;
    }
}
