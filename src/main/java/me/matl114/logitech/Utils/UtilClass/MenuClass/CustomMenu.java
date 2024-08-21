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

    /**
     * 将MenuFactory实例化 使用MenuFactory中的数据构造CustomMenu多界面对象(一个包含返回跳转处理函数 和 内部跳转方法的对象)
     * 我们会在界面的时候尝试实例化一个CustomMenu并尝试从MenuFactory载入数据,同时设置跳转
     *
     */
    String title;
    //界面大小
    int size;
    //页数
    int pages;
    //页面中显示大小
    int pageContent;
    //总大小
    int inventorySize;
    MenuFactory factory;
    int nextSlot=-1;
    int prevSlot=-1;
    int backSlot=-1;
    ChestMenu.MenuClickHandler backHandlers=ChestMenuUtils.getEmptyClickHandler();
    public CustomMenu(String title,int size, int menulens,MenuFactory factory) {
        this.factory=factory;
        this.title=title;

        int fixlen=factory.getPrefixSize()+factory.getSuffixSize();
        assert size%9==0&&size<=54;

        this.size=size;
        this.pageContent=size-fixlen;
        this.inventorySize=Math.max(menulens,factory.getInventorySize());
        this.pages =(1+(inventorySize-1)/this.pageContent);
        if(this.pages ==0)this.pages =1;//防止有傻逼不塞东西 写0
    }
    public int getPages(){
        return this.pages;
    }

    /**
     * 设置当前对象跳转槽
     * @param slot
     * @return
     */
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
    public CustomMenu setBackSlot(int slot){
        assert slot<size;
        this.backSlot=slot;
        return this;
    }
    public CustomMenu setBackHandler(ChestMenu.MenuClickHandler handler){
        this.backHandlers=handler==null?ChestMenuUtils.getEmptyClickHandler():handler;
        return this;
    }

    /**
     * 自动生成跳转槽物品
     * @param slot
     * @param page
     * @return
     */
    public ItemStack generatePageSlot( int slot, int page){
        if(slot==prevSlot){
            return MenuUtils.getPreviousButton(page,pages);
        }else if(slot==nextSlot){
            return MenuUtils.getNextButton(page,pages);
        }else if(slot==backSlot){
            return MenuUtils.getBackButton();
        }
        return null;
    }

    /**
     * 自动生成跳转槽处理程序
     * @param slot
     * @param page

     * @return
     */
    public ChestMenu.MenuClickHandler generatePageClick(int slot, int page){
        if(slot==nextSlot){
            return (player1, i1, itemStack, clickAction) -> {
                if(page<pages)
                    openPages(player1,page+1);
                return false;
            };
        }else if(slot==prevSlot){
            return (player1, i1, itemStack, clickAction) -> {
                if(page>1)
                    openPages(player1,page-1);
                return false;
            };
        }else if(slot==backSlot){
            return backHandlers==null?ChestMenuUtils.getEmptyClickHandler():backHandlers;
        }
        else return null;
    }

    /**
     * 载入物品的逻辑
     * @param slot
     * @param page
     * @return
     */
    public ItemStack getItem(int slot,int page){
        if(factory.hasOverridesItem(slot)){
            return factory.getOverrideItem(slot);
        }else if(slot==nextSlot||slot==prevSlot){
            return generatePageSlot(slot,page);
        }
        else {
            int len=factory.getPrefixSize();
            int len2=factory.getSuffixSize();
            if(slot< len){
                return factory.getPrefix(slot);
            }else if (slot>=size-len2){
                return factory.getSuffix(slot);
            }else {
                return factory.getInventory(slot-len+(page-1)*pageContent);
            }
        }
    }

    /**
     * 获得指向该对象的打开槽,公共的
     * @return
     */
    public static CustomMenuHandler getOpenHandler(int page){
        return (cm -> ((player, i, itemStack, clickAction) -> {
            cm.openPages(player,page);
            return false;
        }));
    }
    public CustomMenuHandler getOpenThisHandler(int pages){
        return (cm -> ((player, i, itemStack, clickAction) -> {
            this.openPages(player,pages);
            return false;
        }));
    }

    public void openPages(Player p,int page){
        ChestMenu menu=constructPage(page);
        menu.open(p);
    }
    public void open(Player p){
        openPages(p,1);
    }
    public ChestMenu constructPage(int page){
        assert page<=pages&&page>=1;
        int startIndex=(page-1)*pageContent;
        int endIndex=Math.min(inventorySize,page*pageContent);
        int delta=endIndex-startIndex;
        ChestMenu inv=new ChestMenu(title);
        inv.addItem(size-1,null);
        ItemStack slotItem;
        ChestMenu.MenuClickHandler handler;
        int slot;
        int len=factory.getPrefixSize();
        for(int i=0;i<len;i++){
            slot=i;
            if(factory.hasOverridesItem(slot)){
                slotItem=factory.getOverrideItem(slot);
            }else if(slot==nextSlot||slot==prevSlot){
                slotItem=generatePageSlot(slot,page);
            }
            else {
                slotItem=factory.getPrefix(i);
            }
            if(factory.hasOverrideHandler(slot)){
                handler=factory.getOverrideHandler(slot).getInstance(this);
            }else if(slot==prevSlot||slot==nextSlot){
                handler=generatePageClick(slot,page);
            }else{
                handler=ChestMenuUtils.getEmptyClickHandler();
            }
            inv.addItem(slot,slotItem,handler);
        }
        for(int i=0;i<pageContent;i++){
            slot=i+len;
            if(factory.hasOverridesItem(slot)){
                slotItem=factory.getOverrideItem(slot);
            }else if(slot==nextSlot||slot==prevSlot){
                slotItem=generatePageSlot(slot,page);
            }
            else {
                if(i<delta){
                    slotItem=factory.getInventory(i+startIndex);
                }else{
                    slotItem=null;
                }
            }
            if(factory.hasOverrideHandler(slot)){
                handler=factory.getOverrideHandler(slot).getInstance(this);
            }else if(slot==prevSlot||slot==nextSlot){
                handler=generatePageClick(slot,page);
            }else{
                if(i<delta){
                    handler=factory.getHandler(i+startIndex).getInstance(this);
                    if(handler==null){
                        handler=ChestMenuUtils.getEmptyClickHandler();
                    }
                }else{
                    handler=ChestMenuUtils.getEmptyClickHandler();
                }
            }
            inv.addItem(slot,slotItem,handler);
        }
        int len2=factory.getSuffixSize();
        for(int i=0;i<len2;i++){
            slot=size-len2+i;
            if(factory.hasOverridesItem(slot)){
                slotItem=factory.getOverrideItem(slot);
            }else if(slot==nextSlot||slot==prevSlot){
                slotItem=generatePageSlot(slot,page);
            }
            else {
                slotItem=factory.getSuffix(i);
            }
            if(factory.hasOverrideHandler(slot)){
                handler=factory.getOverrideHandler(slot).getInstance(this);
            }else if(slot==prevSlot||slot==nextSlot){
                handler=generatePageClick(slot,page);
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
