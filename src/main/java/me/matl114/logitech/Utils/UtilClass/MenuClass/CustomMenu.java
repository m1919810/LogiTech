package me.matl114.logitech.Utils.UtilClass.MenuClass;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MenuUtils;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CustomMenu {
    private static ItemStack[] constructItemList(HashMap<Integer,ItemStack> content) {
        if(content==null) {
            return new ItemStack[0];
        }
        int maxIndex= Collections.max(content.keySet());
        ItemStack[] items = new ItemStack[maxIndex];
        for(int i=0; i<maxIndex; i++) {
            items[i] = content.get(i);
        }
        return items;
    }
    private static ChestMenu.MenuClickHandler[] constructHandler(HashMap<Integer, ChestMenu.MenuClickHandler> itemHandlers) {
        if(itemHandlers==null) {
            return new ChestMenu.MenuClickHandler[0];
        }
        int maxIndex= Collections.max(itemHandlers.keySet());
        ChestMenu.MenuClickHandler[] handler = new ChestMenu.MenuClickHandler[maxIndex];
        for(int i=0; i<maxIndex; i++) {
            if(itemHandlers.containsKey(i)) {
                handler[i] = itemHandlers.get(i);
            }else {
                handler[i] = ChestMenuUtils.getEmptyClickHandler();
            }
        }
        return handler;
    }
    public CustomMenu(String title,int size, int menulens, HashMap<Integer, ItemStack> prefix,HashMap<Integer, ItemStack> suffix,
                      HashMap<Integer, ItemStack> itemDisplays,HashMap<Integer, ChestMenu.MenuClickHandler> itemHandlers) {
        this(title,size,menulens,prefix,suffix,constructItemList(itemDisplays),constructHandler(itemHandlers));
    }
    private static ItemStack[] constructBackGround(HashMap<Integer,ItemStack> prefix, Settings mod){
        if(prefix==null) {
            prefix=new HashMap<>();
        }
        switch (mod){
            case PREFIX :int prefixlen=0;

                int prefixCnt=prefix.size();
                List<ItemStack> items1=new ArrayList<>();
                while(prefixCnt>0){
                    ItemStack item=prefix.get(prefixlen );
                    if(item!=null){
                        items1.add(item);
                    }else{
                        items1.add(ChestMenuUtils.getBackground());
                    }
                    prefixCnt--;
                    prefixlen++;
                }
                int actuallyPrefixlen=(1+((prefixlen-1)/9))*9;
                for(int i=prefixlen;i<actuallyPrefixlen;i++){
                    items1.add(ChestMenuUtils.getBackground());
                }
                return items1.toArray(new ItemStack[items1.size()]);
            case SUFFIX :
            default:
                int suffixlen=0;
                int suffixCnt=prefix.size();
                List<ItemStack> items2=new ArrayList<>();
                while(suffixCnt>0){
                    ItemStack item=prefix.get(53-suffixlen);
                    if(item!=null){
                        items2.add(item);
                    }else {
                        items2.add(ChestMenuUtils.getBackground());
                    }
                    suffixCnt--;
                    suffixlen++;
                }
                int actuallySuffixlen=(1+(suffixlen-1)/9)*9;
                for(int i=suffixlen;i<actuallySuffixlen;i++){
                    items2.add(ChestMenuUtils.getBackground());
                }
                return items2.toArray(new ItemStack[items2.size()]);
        }
    }
    public CustomMenu(String title, int size,int menulens, HashMap<Integer, ItemStack> prefix,HashMap<Integer, ItemStack> suffix,
                      ItemStack[] itemDisplays,ChestMenu.MenuClickHandler[] itemHandlers) {
        this(title,size,menulens,constructBackGround(prefix,Settings.PREFIX),constructBackGround(suffix,Settings.SUFFIX),itemDisplays,itemHandlers);
    }
    public CustomMenu(String title ,int size,int menulens,ItemStack[] prefix,ItemStack[] suffix,
                      HashMap<Integer, ItemStack> itemDisplays,HashMap<Integer, ChestMenu.MenuClickHandler> itemHandlers){
        this(title,size,menulens,prefix,suffix,
                constructItemList(itemDisplays),constructHandler(itemHandlers));
    }
    String title;
    //界面大小
    int size;
    //页数
    int pages;
    //页面中显示大小
    int pageContent;
    //总大小
    int inventorySize;
    ItemStack[] inventory;
    ItemStack[] prefixs;
    ItemStack[] suffixs;
    int nextSlot=-1;
    int prevSlot=-1;
    int backSlot=-1;
    ChestMenu.MenuClickHandler backHandlers=ChestMenuUtils.getEmptyClickHandler();
    HashMap<Integer,ItemStack> overrideItem=new HashMap<>();
    HashMap<Integer,ChestMenu.MenuClickHandler> overrideHandler=new HashMap<>();
    ChestMenu.MenuClickHandler[] menuHandlers;
    public CustomMenu(String title,int size, int menulens, ItemStack[] prefix,ItemStack[] suffix,ItemStack[] itemDisplays,ChestMenu.MenuClickHandler[] itemHandlers) {
        this.title=title;
        this.prefixs=prefix;
        this.suffixs=suffix;
        int fixlen=prefix.length+suffix.length;
        assert size%9==0&&size<=54;

        this.size=size;
        this.pageContent=size-fixlen;
        this.inventorySize=Math.max(menulens,itemDisplays.length);
        this.pages =(1+(inventorySize-1)/this.pageContent);
        if(this.pages ==0)this.pages =1;//防止有傻逼不塞东西 写0
        this.inventory=new ItemStack[inventorySize];
        System.arraycopy(itemDisplays,0,this.inventory,0,itemDisplays.length);
        this.menuHandlers=new ChestMenu.MenuClickHandler[inventorySize];
        System.arraycopy(itemHandlers,0,this.menuHandlers,0,itemHandlers.length);
        for(int i=itemHandlers.length;i<inventorySize;i++){
            menuHandlers[i]=ChestMenuUtils.getEmptyClickHandler();
        }
    }
    public int getPages(){
        return this.pages;
    }
    public CustomMenu setBackSlot(int slot){
        this.backSlot=slot;
        return this;
    }
    public int getBackSlot(){
        return this.backSlot;
    }
    public CustomMenu setNextPageButtom(int slot){
        assert slot<size;
        this.nextSlot=slot;
        return this;
    }
    public CustomMenu setPrevPageButtom(int slot){
        assert slot<size;
        this.prevSlot=slot;
        return this;
    }

    public CustomMenu setInventory(int index,ItemStack item){
        inventory[index]=item;
        return this;
    }
    public CustomMenu setHandler(int index,ChestMenu.MenuClickHandler handler){
        menuHandlers[index]=handler;
        return this;
    }
    public CustomMenu overrideHandler(int slot,ChestMenu.MenuClickHandler handler){
        overrideHandler.put(slot,handler);
        return this;
    }
    public CustomMenu overrideItem(int slot,ItemStack item){
        overrideItem.put(slot,item);
        return this;
    }
    public ItemStack generatePageSlot( int slot, int page){
        if(slot==prevSlot){
            return MenuUtils.getPreviousButton(page,pages);
        }else if(slot==nextSlot){
            return MenuUtils.getNextButton(page,pages);
        }
        return null;
    }
    public ItemStack getItem(int slot,int page){
        if(overrideItem.containsKey(slot)){
            return overrideItem.get(slot);
        }else if(slot==nextSlot||slot==prevSlot){
            return generatePageSlot(slot,page);
        }
        else {
            if(slot<prefixs.length){
                return prefixs[slot];
            }else if (slot>=size-suffixs.length){
                return suffixs[slot];
            }else {
                return inventory[slot-prefixs.length+(page-1)*pageContent];
            }
        }
    }
    public ItemStack getInventory(int index){
        if(index<inventory.length)
            return inventory[index];
        else
            return null;
    }

    /**
     * use for backSlot
     * @return
     */
//    public ChestMenu.MenuClickHandler getOpenHandler(){
//        return getOpenHandler(1);
//    }
    public ChestMenu.MenuClickHandler getOpenHandler(int page, ChestMenu.MenuClickHandler backHandlers){
        return (player1, i1, itemStack, clickAction) ->{
            this.openPages(player1,page,backHandlers);
            return false;
        };
    }
    public ChestMenu.MenuClickHandler generatePageClick(int slot, int page,ChestMenu.MenuClickHandler backHandlers){
        if(slot==nextSlot){
            return (player1, i1, itemStack, clickAction) -> {
                if(page<pages)
                    openPages(player1,page+1,backHandlers);
                return false;
            };
        }else if(slot==prevSlot){
            return (player1, i1, itemStack, clickAction) -> {
                if(page>1)
                    openPages(player1,page-1,backHandlers);
                return false;
            };
        }
        else return null;
    }
//    public void openPages(Player player,int page){
//        openPages(player,page,null);
//    }
    private static class CustomPage{
        CustomMenu menu;
        int page;
        ChestMenu.MenuClickHandler backhandler;
        public CustomPage(CustomMenu menu,int page,ChestMenu.MenuClickHandler backhandler){
            this.menu=menu;
            this.page=page;
            this.backhandler=backhandler;
        }
//        public ChestMenu constructPage(){
//
//        }
    }
    public void openPages(Player p,int page,ChestMenu.MenuClickHandler backHandlers){
        ChestMenu menu=constructPage(page,backHandlers);

        menu.open(p);
    }
//    public void open(Player p){
//        open(p,null);
//    }
    public void open(Player p,ChestMenu.MenuClickHandler backHandlers){
        openPages(p,1,backHandlers);
    }
    public ChestMenu constructPage(int page,ChestMenu.MenuClickHandler backHandlers){
        assert page<=pages&&page>=1;
        int startIndex=(page-1)*pageContent;
        int endIndex=Math.min(inventorySize,page*pageContent);
        int delta=endIndex-startIndex;
        ChestMenu inv=new ChestMenu(title);
        inv.addItem(size-1,null);
        ItemStack slotItem;
        ChestMenu.MenuClickHandler handler;
        int slot;
        for(int i=0;i<prefixs.length;i++){
            slot=i;
            if(overrideItem.containsKey(slot)){
                slotItem=overrideItem.get(slot);
            }else if(slot==nextSlot||slot==prevSlot){
                slotItem=generatePageSlot(slot,page);
            }
            else {
                slotItem=prefixs[i];
            }
            if(overrideHandler.containsKey(slot)){
                handler=overrideHandler.get(slot);
            }else if(slot==prevSlot||slot==nextSlot){
                handler=generatePageClick(slot,page,backHandlers);
            }else{
                handler=ChestMenuUtils.getEmptyClickHandler();
            }
            inv.addItem(slot,slotItem,handler);
        }
        for(int i=0;i<pageContent;i++){
            slot=i+prefixs.length;
            if(overrideItem.containsKey(slot)){
                slotItem=overrideItem.get(slot);
            }else if(slot==nextSlot||slot==prevSlot){
                slotItem=generatePageSlot(slot,page);
            }
            else {
                if(i<delta){
                    slotItem=inventory[i+startIndex];
                }else{
                    slotItem=null;
                }
            }
            if(overrideHandler.containsKey(slot)){
                handler=overrideHandler.get(slot);
            }else if(slot==prevSlot||slot==nextSlot){
                handler=generatePageClick(slot,page,backHandlers);
            }else{
                if(i<delta){
                    handler=menuHandlers[i+startIndex];
                }else{
                    handler=ChestMenuUtils.getEmptyClickHandler();
                }
            }
            inv.addItem(slot,slotItem,handler);
        }
        for(int i=0;i<suffixs.length;i++){
            slot=size-suffixs.length+i;
            if(overrideItem.containsKey(slot)){
                slotItem=overrideItem.get(slot);
            }else if(slot==nextSlot||slot==prevSlot){
                slotItem=generatePageSlot(slot,page);
            }
            else {
                slotItem=suffixs[i];
            }
            if(overrideHandler.containsKey(slot)){
                handler=overrideHandler.get(slot);
            }else if(slot==prevSlot||slot==nextSlot){
                handler=generatePageClick(slot,page,backHandlers);
            }else{
                handler=ChestMenuUtils.getEmptyClickHandler();
            }
            inv.addItem(slot,slotItem,handler);
        }
        if(this.backSlot>=0&&backHandlers!=null){
            inv.addMenuClickHandler(this.backSlot,backHandlers);
        }
        return inv;
    }
}
