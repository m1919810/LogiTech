package me.matl114.logitech.Utils.UtilClass.MenuClass;

import city.norain.slimefun4.VaultIntegration;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.chat.ChatInput;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomItemGroup extends FlexItemGroup {
    protected static final ItemStack INVOKE_ERROR=new CustomItemStack(Material.BARRIER,"&c","","&c获取物品组物品展示失败");
    protected MenuFactory menuFactory;
    protected int pages;
    protected int size;
    protected int prelen=9;
    protected int suflen=9;
    protected int contentPerPage;
    protected List<ItemGroup> subGroups;
    protected int[] subGroupIndex;
    protected boolean isVisible;
    @Override
    public ItemStack getItem(Player p){
        return this.item;
    }

    /**
     * construct normal ItemGroup
     * @param key
     * @param title
     * @param item
     * @param size
     * @param inventorylen
     * @param subGroup
     */
//    public CustomItemGroup(NamespacedKey key, String title, ItemStack item, int size,int inventorylen, List<ItemGroup> subGroup){
//        super(key,item);
//
//        assert size>=27&&size%9==0;
//        this.subGroups=subGroup;
//        this.contentPerPage=size-18;
//        this.pages=1+(inventorylen-1)/contentPerPage;
//        this.isVisible=true;
//        if(title==null){
//            title=item.getItemMeta().getDisplayName();
//        }
//        this.menuFactory=new MenuFactory(MenuUtils.SIMPLE_MENU.setSize(size),title,this.pages) {
//            @Override
//            public void init() {
//                setDefaultNPSlots();
//            }
//        };
//        init(menuFactory);
//        addItemGroups();
//        this.size=size;
//
//    }

    /**
     * this type of ItemGroup has custom ItemGroup Position
     * @param key
     * @param title
     * @param item
     * @param size
     * @param inventorylen
     * @param subGroup
     */
    public CustomItemGroup(NamespacedKey key, String title, ItemStack item, int size,int inventorylen, HashMap<Integer,ItemGroup> subGroup){
        super(key,item);

        assert size>=27&&size%9==0;
        this.subGroups=subGroup.values().stream().toList();
        this.contentPerPage=size-18;
        this.pages=1+(inventorylen-1)/this.contentPerPage;
        this.isVisible=false;
        if(title==null){
            title=item.getItemMeta().getDisplayName();
        }
        this.menuFactory=new MenuFactory(MenuUtils.SIMPLE_MENU.setSize(size),title,this.pages) {
            @Override
            public void init() {
                setDefaultNPSlots();
            }
        };
        init(menuFactory);
        this.subGroupIndex=new int[subGroup.size()];
        int cnt=0;
        for (Map.Entry<Integer,ItemGroup> entry : subGroup.entrySet()){
            int i=entry.getKey();
            subGroupIndex[cnt]=i;
            ++cnt;
            ItemGroup itg=entry.getValue();
            try{
                Class clazz= Class.forName("io.github.thebusybiscuit.slimefun4.api.items.ItemGroup");
                Field _hasType=clazz.getDeclaredField("item");
                _hasType.setAccessible(true);
                ItemStack it=(ItemStack)_hasType.get((ItemGroup)itg);
                menuFactory.addInventory(i,it);
            }catch (Throwable e){
                menuFactory.addInventory(i,AddUtils.renameItem(INVOKE_ERROR,itg.getUnlocalizedName()));
            }
        }
        menuFactory.makeFinal();
        this.size=size;
    }
    /**
     * used to init menuFactory ,set common Inventory and handlers ,used to set common params
     * @param menuFactory
     */
    protected abstract void init(MenuFactory menuFactory);

    /**
     * used to set menuFactory parts related to SFguide,set sf-guide based handlers like menu-back-redirect-to-guidePage handlers,
     * called before menu construct
     * @param menu
     * @param p
     * @param profile
     * @param mode
     * @param pages
     */
    protected abstract void addGuideRelated(ChestMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int pages);
    protected int[] getSubGroupIndex(){
        return subGroupIndex;
    }
    protected List<ItemGroup> getItemGroup(){
        return this.subGroups;
    }
    protected void setVisble(boolean visble){
        this.isVisible=visble;
    }

    /**
     * used to set menu parts related to menu,
     * called after menu construct
     * default doing nothing,
     * @param menu
     * @param p
     * @param profile
     * @param mode
     * @param pages
     */

    /**
     * get a preview slotPlan of ItemGroups, will init subGroupIndex, can modify slot to custom subGroup position
     * @return
     */
    protected int[] previewGroupSlot(){
        List<ItemGroup> subGroups=this.subGroups;
        int i=0;
        this.subGroupIndex=new int[subGroups.size()];
        int groupSize=subGroups.size();
        for(int j=0;j<groupSize;j++){
            //非空,会一直寻找直到找到一个空的位置
            while(!(menuFactory.getInventory(i)==null&&(menuFactory.getHandler(i)==null||menuFactory.getHandler(i)== ChestMenuUtils.getEmptyClickHandler()))){
                ++i;
            }
            this.subGroupIndex[j]=i;
            ++i;
        }
        return this.subGroupIndex;
    }
    private void addItemGroups(){
        int i=0;
        if(this.subGroupIndex==null||this.subGroupIndex.length!=subGroups.size()){
            previewGroupSlot();
        }
        int groupSize=subGroups.size();
        for(int j=0;j<groupSize;j++){
            i=subGroupIndex[j];
            ItemGroup itg=subGroups.get(j);
            try{
                Class clazz= Class.forName("io.github.thebusybiscuit.slimefun4.api.items.ItemGroup");
                Field _hasType=clazz.getDeclaredField("item");
                _hasType.setAccessible(true);
                ItemStack it=(ItemStack)_hasType.get((ItemGroup)itg);
                menuFactory.addInventory(i,it);
            }catch (Throwable e){
                menuFactory.addInventory(i,AddUtils.renameItem(INVOKE_ERROR,itg.getUnlocalizedName()));
            }

        }
    }
    public boolean isVisible(Player var1, PlayerProfile var2, SlimefunGuideMode var3){
        return isVisible;
    }
    public boolean isHidden(Player p) {
        return !isVisible;
    }
    public void open(Player var1, PlayerProfile var2, SlimefunGuideMode var3){
        int page=getLastPage(var1,var2,var3);
        if(page<=0||page>this.pages){
            page=1;
        }

        openPage(var1,var2,var3,page);
    }

    public void openPage(Player var1, PlayerProfile var2, SlimefunGuideMode var3,int page){
        assert page>=1&&page<=pages;
        var2.getGuideHistory().add(this,page);
        ChestMenu menu=menuFactory.build().constructPage(page);
        //prev键
        menu.addMenuClickHandler(this.size-8,((player, i, itemStack, clickAction) -> {
            if(page>1){
                this.openPage(var1,var2,var3,page-1);
            }return false;
        }));
        //next键
        menu.addMenuClickHandler(this.size-2,((player, i, itemStack, clickAction) -> {

            if(page<this.pages){
                this.openPage(var1,var2,var3,page+1);
            }return false;
        }));
        //搜索键
        menu.replaceExistingItem(7,ChestMenuUtils.getSearchButton(var1));
        menu.addMenuClickHandler(7, (pl, slot, item, action) -> {
            pl.closeInventory();
            Slimefun.getLocalization().sendMessage(pl, "guide.search.message");
            ChatInput.waitForPlayer(
                    Slimefun.instance(),
                    pl,
                    msg -> SlimefunGuide.openSearch(var2, msg, SlimefunGuideMode.SURVIVAL_MODE, true));
            return false;
        });
        //返回键
        menu.addMenuClickHandler(1,((player, i, itemStack, clickAction) -> {
            var2.getGuideHistory().goBack(Slimefun.getRegistry().getSlimefunGuide(var3));
            return false;
        }));
        //依次按照计算好的index放入物品组，也可以自定义index放入
        int groupSize=subGroups.size();
        int startIndex=(page-1)*contentPerPage;
        int endIndex=page*contentPerPage;
        for(int j=0;j<groupSize;j++){
            ItemGroup itg=subGroups.get(j);
            int index=subGroupIndex[j];
            if(index>=startIndex&&index<endIndex){
                menu.addMenuClickHandler(index-startIndex+prelen,((player, i, itemStack, clickAction) -> {
                    SlimefunGuide.openItemGroup(var2, itg, var3, 1);
                    return false;
                }));
            }
        }
        addGuideRelated(menu,var1,var2,var3,page);

        menu.open(var1);
    }
    private void displaySlimefunItem(ChestMenu menu, ItemGroup itemGroup, Player p, PlayerProfile profile, SlimefunItem sfitem, SlimefunGuideMode mode, int page, int index) {
        Research research = sfitem.getResearch();
        if (SlimefunGuideMode.CHEAT_MODE!=mode && !Slimefun.getPermissionsService().hasPermission(p, sfitem)) {
            List<String> message = Slimefun.getPermissionsService().getLore(sfitem);
            menu.addItem(index, new CustomItemStack(ChestMenuUtils.getNoPermissionItem(), sfitem.getItemName(), (String[])message.toArray(new String[0])));
            menu.addMenuClickHandler(index, ChestMenuUtils.getEmptyClickHandler());
        } else if (SlimefunGuideMode.CHEAT_MODE!=mode&& research != null && !profile.hasUnlocked(research)) {
            String lore;
            if (VaultIntegration.isEnabled()) {
                Object[] var10001 = new Object[]{research.getCurrencyCost()};
                lore = String.format("%.2f", var10001) + " 游戏币";
            } else {
                lore = research.getLevelCost() + " 级经验";
            }

            menu.addItem(index, new CustomItemStack(new CustomItemStack(ChestMenuUtils.getNoPermissionItem(), "&f" + ItemUtils.getItemName(sfitem.getItem()), new String[]{"&7" + sfitem.getId(), "&4&l" + Slimefun.getLocalization().getMessage(p, "guide.locked"), "", "&a> 单击解锁", "", "&7需要 &b", lore})));
            menu.addMenuClickHandler(index, (pl, slot, item, action) -> {
                research.unlockFromGuide(Slimefun.getRegistry().getSlimefunGuide(mode), p, profile, sfitem, itemGroup, page);
                return false;
            });
        } else {
            menu.addItem(index, sfitem.getItem());
            menu.addMenuClickHandler(index, (pl, slot, item, action) -> {
                try {
                    if (SlimefunGuideMode.CHEAT_MODE!=mode) {
                        Slimefun.getRegistry().getSlimefunGuide(mode).displayItem(profile, sfitem, true);
                    } else if (pl.hasPermission("slimefun.cheat.items")) {
                        if (sfitem instanceof MultiBlockMachine) {
                            Slimefun.getLocalization().sendMessage(pl, "guide.cheat.no-multiblocks");
                        } else {
                            ItemStack clonedItem = sfitem.getItem().clone();
                            if (action.isShiftClicked()) {
                                clonedItem.setAmount(clonedItem.getMaxStackSize());
                            }

                            pl.getInventory().addItem(new ItemStack[]{clonedItem});
                        }
                    } else {
                        Slimefun.getLocalization().sendMessage(pl, "messages.no-permission", true);
                    }
                } catch (LinkageError | Exception var8) {
                    Throwable x = var8;
                    p.sendMessage(ChatColor.DARK_RED + "An internal server error has occurred. Please inform an admin, check the console for further info.");
                    sfitem.error("This item has caused an error message to be thrown while viewing it in the Slimefun guide.", x);
                }

                return false;
            });
        }

    }
    //modified from guizhan Infinity Expansion 2
    private int getLastPage(Player var1, PlayerProfile var2, SlimefunGuideMode var3){
        try{
            Class clazz= GuideHistory.class;
            Method getEntryMethod=clazz.getDeclaredMethod("getLastEntry",boolean.class);
            getEntryMethod.setAccessible(true);
            Object entry=getEntryMethod.invoke(var2.getGuideHistory(),false);
            Class entryClass= Class.forName("io.github.thebusybiscuit.slimefun4.core.guide.GuideEntry");
            Method entryGetObjMethod=entryClass.getDeclaredMethod("getIndexedObject");
            entryGetObjMethod.setAccessible(true);
            Object obj=entryGetObjMethod.invoke(entry);
            if(obj instanceof CustomItemGroup){
                Method entryGetPageMethod=entryClass.getDeclaredMethod("getPage");
                entryGetPageMethod.setAccessible(true);
                return (Integer)entryGetPageMethod.invoke(entry);
            }else{
                return 1;
            }

        }catch (Throwable e){
            return 1;
        }

    }
}
