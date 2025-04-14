package me.matl114.logitech.utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.core.Registries.RecipeSupporter;
import me.matl114.logitech.utils.UtilClass.CargoClass.ContainerBlockMenuWrapper;
import me.matl114.logitech.utils.UtilClass.ItemClass.*;
import me.matl114.logitech.utils.UtilClass.MenuClass.*;
import me.matl114.matlib.common.lang.annotations.Note;
import me.matl114.matlib.utils.NMSInventoryUtils;
import me.matl114.matlib.utils.inventory.itemStacks.CleanItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MenuUtils {
    public static final ItemStack PROCESSOR_NULL= new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE, " ");
    public static final ItemStack PROCESSOR_SPACE=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6进程完成","&c空间不足");
    public static final ItemStack PROCESSOR_ENERGY=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c电力不足","");
    public static final ItemStack PREV_BUTTON_ACTIVE = new SlimefunItemStack("_UI_PREVIOUS_ACTIVE", Material.LIME_STAINED_GLASS_PANE, "&r⇦ Previous Page", new String[0]);
    public static final ItemStack NEXT_BUTTON_ACTIVE = new SlimefunItemStack("_UI_NEXT_ACTIVE", Material.LIME_STAINED_GLASS_PANE, "&rNext Page ⇨", new String[0]);
    public static final ItemStack PREV_BUTTON_INACTIVE = new SlimefunItemStack("_UI_PREVIOUS_INACTIVE", Material.BLACK_STAINED_GLASS_PANE, "&8⇦ Previous Page", new String[0]);
    public static final ItemStack NEXT_BUTTON_INACTIVE = new SlimefunItemStack("_UI_NEXT_INACTIVE", Material.BLACK_STAINED_GLASS_PANE, "&8Next Page ⇨", new String[0]);
    public static final ItemStack BACK_BUTTON = new SlimefunItemStack("_UI_BACK", Material.ENCHANTED_BOOK, "&7⇦ 返回", (meta) -> {
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
    });
    public static final ItemStack SEARCH_BUTTON = new SlimefunItemStack("_UI_SEARCH", Material.NAME_TAG, "&b搜索...", "",ChatColor.GRAY + "⇨ " +"&b单击搜索物品");
    public static final ItemStack NO_ITEM = new SlimefunItemStack("_UI_NO_ITEM",Material.BARRIER,"&8 ");
    public static final ItemStack PRESET_INFO=new CustomItemStack(Material.CYAN_STAINED_GLASS_PANE,"&3配方类型信息");
    public static final ItemStack PRESET_MORE=new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,"&a更多物品(已省略)");

    public static final ChestMenu.MenuClickHandler CLOSE_HANDLER=((player, i, itemStack, clickAction) -> {
        player.closeInventory();
        return false;

    });
    /**
     * change the object in the slot to a different object ,in order to trigger save at this slot when server down ,
     * will return the ref to the current object in the slot
     * @return
     */
    public static ItemStack syncSlot(BlockMenu inv ,int slot){
        ItemStack item = inv.getItemInSlot(slot);
        return syncSlot(inv,slot,item);
    }
    /**
     * change the object in the slot to a different object ,in order to trigger save at this slot when server down ,
     * will return the ref to the current object in the slot
     * @return
     */
    public static ItemStack syncSlot(BlockMenu inv ,int slot, ItemStack item){
//        ItemStack craftCopy = me.matl114.matlib.Utils.CraftUtils.getCraftCopy(item);
//        NMSInventoryUtils.setTileInvItemNoUpdate(inv.getInventory(), );
        ItemStack craftCopy = getCraftCopyOf(item);
        NMSInventoryUtils.setInvItemNoCopy(inv.getInventory(), slot, craftCopy);
        //inv.replaceExistingItem(slot, item,false);
        return craftCopy;
    }
    //todo use inventoryHelper here
    @Note("may not be really NO-COPY")
    public static ItemStack syncSlotNoCopy(BlockMenu inv, int slot,@Nonnull ItemStack item){
        //remain
        if(inv instanceof ContainerBlockMenuWrapper wrapper){
            //avoid our implementation and shit goes in reflection
            wrapper.replaceExistingItem(slot, item);
            return wrapper.getItemInSlot(slot);
        }else {
            NMSInventoryUtils.setInvItemNoCopy(inv.getInventory(), slot, item);
            return item;
        }
    }
    public static ItemStack getCraftCopyOf(ItemStack item){
        if(item instanceof RandOutItem rand){
            item=rand.getInstance();
        }
        return me.matl114.matlib.utils.CraftUtils.getCraftCopy(item);
    }



    public static ItemStack getPreviousButton(int page, int pages) {
        return pages != 1 && page != 1 ? new CustomItemStack(PREV_BUTTON_ACTIVE, (meta) -> {
            ChatColor var10001 = ChatColor.WHITE;
            meta.setDisplayName("" + var10001 + "⇦ " + "上一页");
            meta.setLore(Arrays.asList("", ChatColor.GRAY + "(" + page + " / " + pages + ")"));
        }) : new CustomItemStack(PREV_BUTTON_INACTIVE, (meta) -> {
            ChatColor var10001 = ChatColor.DARK_GRAY;
            meta.setDisplayName("" + var10001 + "⇦ " +"上一页");
            meta.setLore(Arrays.asList("", ChatColor.GRAY + "(" + page + " / " + pages + ")"));
        });
    }
    public static ItemStack getNextButton( int page, int pages) {
        return pages != 1 && page != pages ? new CustomItemStack(NEXT_BUTTON_ACTIVE, (meta) -> {
            ChatColor var10001 = ChatColor.WHITE;
            meta.setDisplayName("" + var10001 + "下一页" + " ⇨");
            meta.setLore(Arrays.asList("", ChatColor.GRAY + "(" + page + " / " + pages + ")"));
        }) : new CustomItemStack(NEXT_BUTTON_INACTIVE, (meta) -> {
            ChatColor var10001 = ChatColor.DARK_GRAY;
            meta.setDisplayName("" + var10001 + "下一页" + " ⇨");
            meta.setLore(Arrays.asList("", ChatColor.GRAY + "(" + page + " / " + pages + ")"));
        });
    }
    public static ItemStack getBackButton( String... lore) {
        return new CustomItemStack(BACK_BUTTON, "&7⇦ " +"返回", lore);
    }
//clickType of InventoryClickEvnet
// 左键
//    LEFT,
    //shift左键
//    SHIFT_LEFT,
    //右键

//    RIGHT,
    //shift右键
//    SHIFT_RIGHT,
    //左键点空白
//    WINDOW_BORDER_LEFT,
    //右键点空白
//    WINDOW_BORDER_RIGHT,
    //中键物品
//    MIDDLE,
    //用数字切换
//    NUMBER_KEY,
    //双击
//    DOUBLE_CLICK,
    //q
//    DROP,
    //ctrl+q
//    CONTROL_DROP,
    //创造
//    CREATIVE,
    //F切副手
//    SWAP_OFFHAND,
    //不知道
//    UNKNOWN;

    //sf不能监听的对槽位的修改
    //SHIFT_..
    //DOUBLE_CLICK
    //NUMBER有一些没有监听
    //
//    public static void strictHandleInventoryClick(InventoryClickEvent e){
//
//    }




    private static final int[] RECIPE_DISPLAY_INDEX2SLOT = new int[]{
            36,45,37,46,38,47,39,48,40,49,41,50,42,51,43,52,44,53
    };
    //3x3配方表的显示
    public static final MenuPreset SIMPLE_MENU=new MenuPreset(54,9,9)
            .addItem(BACK_BUTTON,1)
            .addItem(new CustomItemStack(Material.BOOK,"&aINFO_TEST"),7);
    public static final MenuPreset RECIPE_MENU_3X3=new MenuPreset(27,0,0)
            .addItem(BACK_BUTTON,1);
    public static final MenuPreset RECIPE_MENU_3X3_WITH_DISPLAY=new MenuPreset(54,36,0)
            .addItem(BACK_BUTTON,1);
    //not used yet need
    public static final MenuPreset RECIPE_MENU_3x3_PLUS=new MenuPreset(54,0,0)
            .addItem(ChestMenuUtils.getBackground(),27,29,30,31,32,33,35);
    //6x6大配方的显示
    public static final MenuPreset RECIPE_MENU_6X6= new MenuPreset(54,0,0)
            .addItem(BACK_BUTTON,0)
            .addItem(PRESET_INFO,7,16,17)
            .addItem(ChestMenuUtils.getBackground(),9,18,27,36,45)
            .addItem(ChestMenuUtils.getOutputSlotTexture(),25,26,34,43,52,53);
    public static final MenuPreset RECIPE_DISPLAY_SINGLE = new MenuPreset(36,18,0)
            .addItem(BACK_BUTTON,1);
    public static final  int RECIPEBACKSLOT_3X3=1;
    public static final int RECIPETYPESLOT_3X3=10;
    public static final int RECIPEBACKSLOT_6X6=0;
    public static final int RECIPETYPESLOT_6X6=8;
    public static final int[] RECIPESLOT_3X3=new int[]{3,4,5,12,13,14,21,22,23};
    public static final int[] RECIPEOUT_3X3=new int[]{7,16,25};
    public static final int[] RECIPESLOT_6x6=new int[]{1,2,3,4,5,6,10,11,12,13,14,15,19,20,21,22,23,24,28,29,30,31,32,33,37,38,39,40,41,42,46,47,48,49,50,51};
    public static final int[] RECIPEOUTPUT_6X6=new int[]{35,44};
    public interface RecipeMenuConstructor{
        public MenuFactory construct(ItemStack icon, MachineRecipe recipe , CustomMenuHandler backhandler,SlimefunItem optionalItem, PlayerHistoryRecord<CustomMenu> history);
        default MenuFactory construct(ItemStack icon, MachineRecipe recipe , CustomMenuHandler backhandler, PlayerHistoryRecord<CustomMenu> history){
            return construct(icon,recipe,backhandler,null,history);
        }
    }
    public static MenuFactory createMRecipeListDisplay(ItemStack machine, List<MachineRecipe> machineRecipes, @Nullable CustomMenuHandler backHandler){
        return createMRecipeListDisplay(machine,machineRecipes,backHandler,null,MenuUtils::createMRecipeDisplay);
    }
    public static MenuFactory createMRecipeListDisplay(ItemStack machine, List<MachineRecipe> machineRecipes, @Nullable CustomMenuHandler backHandler,PlayerHistoryRecord<CustomMenu> history){
        return createMRecipeListDisplay(machine,machineRecipes,backHandler,history,MenuUtils::createMRecipeDisplay);
    }
    public static MenuFactory createMRecipeListDisplay(ItemStack machine, List<MachineRecipe> machineRecipes, @Nullable CustomMenuHandler backHandler,RecipeMenuConstructor constructor){
        return createMRecipeListDisplay(machine,machineRecipes,backHandler,null,constructor);
    }
    public static MenuFactory createMRecipeListDisplay(ItemStack machine, List<MachineRecipe> machineRecipes, @Nullable CustomMenuHandler backHandler,PlayerHistoryRecord<CustomMenu> history,RecipeMenuConstructor constructor){
        return  createMRecipeListDisplay(machine,machineRecipes,backHandler,history,constructor,false);
    }
    public static MenuFactory createMRecipeListDisplay(ItemStack machine, List<MachineRecipe> machineRecipes, @Nullable CustomMenuHandler backHandler,PlayerHistoryRecord<CustomMenu> history,RecipeMenuConstructor constructor,boolean withoutId){

        int RecipeSize = machineRecipes.size();
        int pageContent=36;
        int pageNum=(1+(RecipeSize-1)/pageContent);
        ItemStack displayMachine;
        if(machine!=null){
            displayMachine=AddUtils.addLore(machine,"&8机器配方显示");
        }else{
            displayMachine=NO_ITEM;
        }
        MenuFactory a=new MenuFactory(SIMPLE_MENU,CraftUtils.getItemName(machine),pageNum){
            public void init(){
                setDefaultNPSlots();
            }
        }.addOverrides(7,displayMachine);
        a.setGuideModHistory(history);
        a.setBack(1);
        if(backHandler!=null){
            a.setBackHandler(backHandler);
        }else{
            a.setBackHandler(CustomMenuHandler.from(CLOSE_HANDLER));
        }
        for(int i=0;i<RecipeSize;i++){
            MachineRecipe recipe=machineRecipes.get(i);
            int pageNow=(1+(i)/pageContent);
            if(recipe.getOutput().length>0){
                a.addInventory(i,withoutId? AddUtils.getWithoutId(recipe.getOutput()[0]):recipe.getOutput()[0]);
            }
            else{
                a.addInventory(i,withoutId? AddUtils.getWithoutId( NO_ITEM):NO_ITEM);
            }
            a.addHandler(i,(cm)-> (player, i1, itemStack, clickAction) -> {
                constructor.construct(machine,recipe,cm.getBackToThisHandler(pageNow),history).build().open(player);
                return false;
            });
        }
        return a;
    }
    public static MenuFactory createItemListDisplay(RecipeType type, List<SlimefunItem> items, @Nullable CustomMenuHandler backHandler){
        return createItemListDisplay(type,items,backHandler,null,MenuUtils::createMRecipeDisplay);
    }

    public static MenuFactory createItemListDisplay(RecipeType type, List<SlimefunItem> items, @Nullable CustomMenuHandler backHandler,PlayerHistoryRecord<CustomMenu> history,RecipeMenuConstructor constructor){
        return createItemListDisplay(type,items,backHandler,null,constructor,false);
    }
    public static MenuFactory createItemListDisplay(RecipeType type, List<SlimefunItem> items, @Nullable CustomMenuHandler backHandler,PlayerHistoryRecord<CustomMenu> history,RecipeMenuConstructor constructor,boolean withoutId){

        int RecipeSize = items.size();
        int pageContent=36;
        int pageNum=(1+(RecipeSize-1)/pageContent);
        ItemStack displayMachine;
        ItemStack typeIcon = type.toItem();
        if(typeIcon !=null){

            displayMachine=AddUtils.addLore(typeIcon,"&8配方类型显示");
        }else{
            displayMachine=NO_ITEM;
        }
        MenuFactory a=new MenuFactory(SIMPLE_MENU,AddUtils.color("类型:"+type.getKey().toString()),pageNum){
            public void init(){
                setDefaultNPSlots();
            }
        }.addOverrides(7,displayMachine);
        a.setGuideModHistory(history);
        a.setBack(1);
        if(backHandler!=null){
            a.setBackHandler(backHandler);
        }else{
            a.setBackHandler(CustomMenuHandler.from(CLOSE_HANDLER));
        }
        for(int i=0;i<RecipeSize;i++){
            SlimefunItem item = items.get(i);
            int pageNow=(1+(i)/pageContent);

            a.addInventory(i,withoutId? AddUtils.getWithoutId(item.getRecipeOutput()):item.getRecipeOutput());

            a.addHandler(i,(cm)-> (player, i1, itemStack, clickAction) -> {
                constructor.construct(displayMachine,new MachineRecipe(0,item.getRecipe(),new ItemStack[]{item.getRecipeOutput()}),cm.getBackToThisHandler(pageNow),item,history).build().open(player);
                return false;
            });
        }
        return a;
    }
    private static String getAmountDisplay(ItemStack stack){
        if(stack instanceof RandAmountStack rand){
            return "%d~%d".formatted(rand.getMin(),rand.getMax());
        }
        else{
            return "x"+stack.getAmount();
        }

    }

    public static ItemStack getDisplayItem(ItemStack it){
        if(it==null||it.getType().isAir())return null;
        ItemStack finalStack;
        if(it instanceof RandomItemStack||it instanceof ProbItemStack){
            List<ItemStack> list=((MultiItemStack)it).getItemStacks();
            List<String> namelist=new ArrayList<>(){{
                add("");
                add("&3随机物品输出~");

                List<Double> wlist=((MultiItemStack)it).getWeight(1.0);
                int len=list.size();
                for(int i=0;i<len;i++){
                    add(AddUtils.concat(  "&f",
                            CraftUtils.getItemName(list.get(i)),
                            " &e",getAmountDisplay(list.get(i)),
                            " 概率: ",AddUtils.getPercentFormat(wlist.get(i))) );
                }
            }};
            //如果是proItemStack就获得其实例 因为不能整一个air出来
            ItemStack sample=list.get(0);
            if(sample.getType().isAir())return null;
            finalStack= AddUtils.addLore(sample,namelist.toArray(new String[namelist.size()]));
        }else if(it instanceof EquivalItemStack){
            List<ItemStack> list=((EquivalItemStack)it).getItemStacks();
            List<String> namelist=new ArrayList<>(){{
                add("");
                add("&3等价物品输入~");
                int len=list.size();
                for(int i=0;i<len;i++){
                    add(AddUtils.concat(  "&f",
                            CraftUtils.getItemName(list.get(i)),
                            " ",getAmountDisplay(list.get(i))) );
                }
            }};
            ItemStack sample=list.get(0);
            if(sample.getType().isAir())return null;
            finalStack= AddUtils.addLore(sample,namelist.toArray(new String[namelist.size()]));
        }
        else if(it instanceof RandAmountStack){
            finalStack=AddUtils.addLore(AddUtils.getCleaned(it),AddUtils.concat("&e随机数量: ",getAmountDisplay(it)));
        }else{
            finalStack=it;
        }
        //防止因为意外出现的air 指
        if(finalStack.getType().isAir()){
            ItemMeta meta=finalStack.getItemMeta();
            finalStack=NO_ITEM.clone();
            finalStack.setItemMeta(meta);
        }
        if(finalStack.getAmount()>64){
            finalStack=AddUtils.addLore(finalStack, "&c数量: "+finalStack.getAmount());
        }else if(finalStack.getAmount()<=0){
            finalStack=AddUtils.addLore(finalStack, "&c数量: "+finalStack.getAmount());
            finalStack.setAmount(1);
        }
        return finalStack;
    }
    public static MenuFactory createMRecipeDisplay(ItemStack machine,MachineRecipe recipe,@Nullable CustomMenuHandler backHandler){
        return createMRecipeDisplay(machine,recipe,backHandler,null,null);
    }
    public static MenuFactory createMRecipeDisplay(ItemStack machine,MachineRecipe recipe,@Nullable CustomMenuHandler backHandler, SlimefunItem optional){
        return createMRecipeDisplay(machine,recipe,backHandler,optional,null);
    }
    public static MenuFactory createMRecipeDisplay(ItemStack machine,MachineRecipe recipe,@Nullable CustomMenuHandler backHandler, PlayerHistoryRecord<CustomMenu> history){
        return createMRecipeDisplay(machine,recipe,backHandler,null,history);
    }
    public static MenuFactory createMRecipeDisplay(ItemStack machine,MachineRecipe recipe,@Nullable CustomMenuHandler backHandler,@Nullable SlimefunItem optionalItem,@Nullable PlayerHistoryRecord<CustomMenu> history){
        ItemStack[] input=recipe.getInput();
        ItemStack[] output=recipe.getOutput();
        int inputlen=input.length;
        if(inputlen<=9){
            MenuFactory a;
            List<ItemStack> displayedDown;
            getInfo:{
                if(optionalItem instanceof RecipeDisplayItem recipeDisplay){
                    displayedDown = recipeDisplay.getDisplayRecipes();
                    if(!displayedDown.isEmpty()){
                        a=new MenuFactory(RECIPE_MENU_3X3_WITH_DISPLAY,AddUtils.colorful("配方展示"),((displayedDown.size()+17)/18)) {
                            @Override
                            public void init() {

                            }
                        };
                        break getInfo;
                    }
                }
                a=new MenuFactory(RECIPE_MENU_3X3,AddUtils.colorful("配方展示"),1){
                    public void init(){

                    }
                };
                displayedDown = List.of();
            }
            a.setGuideModHistory(history);
            a.setBack(RECIPEBACKSLOT_3X3);
            a.setBackGround(null);
            if(backHandler!=null){
                a.setBackHandler(backHandler);
            }else{
                a.setBackHandler(CustomMenuHandler.from(CLOSE_HANDLER));
            }
            if(machine!=null&&!machine.getType().isAir()){
                if(recipe.getTicks()>=0)
                    a.addOverrides(RECIPETYPESLOT_3X3,AddUtils.addLore(machine,"","&a在此处耗时"+recipe.getTicks()+"&at制作"));
                else
                    a.addOverrides(RECIPETYPESLOT_3X3,AddUtils.addLore(machine,"","&a在此处制作"));
            }
            for(int i=0;i<input.length;++i){
                a.addOverrides(RECIPESLOT_3X3[i],getDisplayItem(input[i]) );
                SlimefunItem sfitem=SlimefunItem.getByItem(input[i]);
                if(sfitem!=null){
                    a.addOverrides(RECIPESLOT_3X3[i],(cm)->((player, i1, itemStack, clickAction) -> {
                        createItemRecipeDisplay(sfitem,cm.getBackToThisHandler(1),history).build().open(player);
                        return false;
                    }));
                }
            }
            if(output==null){
                output=new ItemStack[0];
            }
            if(output.length==1){
                a.addOverrides(RECIPEOUT_3X3[(RECIPEOUT_3X3.length-1)/2], getDisplayItem(output[0]));
            }else if(output.length>1&& output.length<=RECIPEOUT_3X3.length){
                for(int s=0;s<output.length;++s){
                    a.addOverrides(RECIPEOUT_3X3[s],getDisplayItem(output[s]) );
                }
            }else if(output.length>RECIPEOUT_3X3.length){
                for(int s=0;s<(RECIPEOUT_3X3.length-1);++s){
                    a.addOverrides(RECIPEOUT_3X3[s],getDisplayItem(output[s]) );
                }
                a.addOverrides(RECIPEOUT_3X3[RECIPEOUT_3X3.length-1],PRESET_MORE );
            }
            if(!displayedDown.isEmpty()){
                a.setPrev(28);
                a.setNext(34);
                //绘制栏目
                for(int i=27;i<36;++i){
                    if(i==28||i==34)continue;
                    a.addOverrides(i,ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
                }
                int size = displayedDown.size();
                //绘制dsplayed
                for(int i=0;i<size;++i){
                    int pageI = i/18;
                    int indexI = i%18;
                    a.addInventory(18*pageI+(RECIPE_DISPLAY_INDEX2SLOT[indexI]-36),displayedDown.get(i),(cm1)->((player1, i1, itemStack1, clickAction1) -> {
                        SlimefunItem item = SlimefunItem.getByItem(itemStack1);
                        if(item!=null){
                            createItemRecipeDisplay(item,cm1.getBackToThisHandler(1),history).build().open(player1);
                        }
                        return false;
                    }));
                }
            }
            return a;
        }
        else{
            MenuFactory a=new MenuFactory(RECIPE_MENU_6X6,AddUtils.colorful("配方展示"),1){
                public void init(){
                }
            };
            a.setGuideModHistory(history);
            a.setBack(RECIPEBACKSLOT_6X6);
            if(backHandler!=null){
                a.setBackHandler (backHandler);
            }else{
                a.setBackHandler(CustomMenuHandler.from(CLOSE_HANDLER));
            }
            if(recipe.getTicks()>=0)
                a.addInventory(RECIPETYPESLOT_6X6,AddUtils.addLore(machine,"","&a在该机器中耗时"+recipe.getTicks()+"&at制作"));
            else
                a.addInventory(RECIPETYPESLOT_6X6,AddUtils.addLore(machine,"","&a在该机器中制作"));
            int len=Math.min(inputlen,RECIPESLOT_6x6.length);

            for(int i=0;i<len;++i){
                if(i==len-1&&len<inputlen){
                    a.addInventory(RECIPESLOT_6x6[i],PRESET_MORE);
                    continue;
                }
                a.addInventory(RECIPESLOT_6x6[i],getDisplayItem(input[i]) );
                SlimefunItem sfitem=SlimefunItem.getByItem(input[i]);
                if(sfitem!=null){
                    a.addHandler(RECIPESLOT_6x6[i],(cm)->((player, i1, itemStack, clickAction) -> {
                        createItemRecipeDisplay(sfitem,cm.getBackToThisHandler(1),history).build().open(player);
                        return false;
                    }) );
                }
            }
            if(output==null){
                output=new ItemStack[0];
            }
            int len2=Math.min(output.length,RECIPEOUTPUT_6X6.length);
            for(int i=0;i<len2;++i){
                if(i==len2-1&&len2<output.length){
                    a.addInventory(RECIPEOUTPUT_6X6[i],PRESET_MORE );
                    continue;
                }
                a.addInventory(RECIPEOUTPUT_6X6[i],output[i] );
            }
            if(optionalItem instanceof RecipeDisplayItem recipeDisplay){
                a.addOverrides(53,AddUtils.addGlow( new CleanItemStack(Material.KNOWLEDGE_BOOK,"&a点击查看物品展示的配方","&7以及物品额外说明")),(cm)->((player, i, itemStack, clickAction) -> {
                    List<ItemStack> displayedDown = recipeDisplay.getDisplayRecipes();
                    if(displayedDown.isEmpty()){
                        AddUtils.sendMessage(player,"&c该物品并无展示的配方");
                        return false;
                    }
                    var recipeMenu = new MenuFactory(RECIPE_DISPLAY_SINGLE,CraftUtils.getItemName( optionalItem.getItem()),(displayedDown.size()+17)/18) {
                        @Override
                        public void init() {
                        }
                    };
                    recipeMenu.setPrev(10);
                    recipeMenu.setNext(16);
                    recipeMenu.setBack(1);
                    recipeMenu.addOverrides(4,optionalItem.getItem());
                    int size = displayedDown.size();
                    for (int index = 0;index < size;++index){
                        int pageI = index /18;
                        int indexI = index %18;
                        recipeMenu.addInventory(18*pageI+(RECIPE_DISPLAY_INDEX2SLOT[indexI]-36),displayedDown.get(index),(cm1)->((player1, i1, itemStack1, clickAction1) -> {
                            SlimefunItem item = SlimefunItem.getByItem(itemStack1);
                            if(item!=null){
                                createItemRecipeDisplay(item,cm1.getBackToThisHandler(1),history).build().open(player1);
                            }
                            return false;
                        }));
                    }
                    var customMenu = recipeMenu.build(cm.getBackToThisHandler(1));
                    customMenu.open(player);
                    return false;
                }));
            }
            return a;
        }

    }
    public static MenuFactory createItemRecipeDisplay(SlimefunItem item,@Nullable CustomMenuHandler backHandler,PlayerHistoryRecord<CustomMenu> history){
        return createMRecipeDisplay(item.getRecipeType().toItem(),new MachineRecipe(-1,item.getRecipe(),new ItemStack[]{item.getRecipeOutput()}),backHandler,item,history);
    }
    public static MenuFactory createItemRecipeDisplay(SlimefunItem item,@Nullable CustomMenuHandler backHandler){
        return createMRecipeDisplay(item.getRecipeType().toItem(),new MachineRecipe(-1,item.getRecipe(),new ItemStack[]{item.getRecipeOutput()}),backHandler,item,null);
    }

    /**
     * 制造 “制造配方类型展示界面的MenuFactory” 并且将其返回CMHandler设置为backHandler
     * @param recipeTypes
     * @param backHandler
     * @return
     */
    public static MenuFactory createRecipeTypeDisplay(List<RecipeType> recipeTypes, CustomMenuHandler backHandler){
        return createRecipeTypeDisplay(recipeTypes,backHandler,null);
    }
    public static MenuFactory createRecipeTypeDisplay(List<RecipeType> recipeTypes, CustomMenuHandler backHandler,PlayerHistoryRecord<CustomMenu> history){
        HashMap<RecipeType,ItemStack> map= RecipeSupporter.RECIPETYPE_ICON;
        int RecipeSize =0;
        for(RecipeType entry:recipeTypes){
            if(map.containsKey(entry)){
                RecipeSize++;
            }
        }
        int pageContent=36;
        int pageNum=(1+(RecipeSize-1)/pageContent);

        MenuFactory a=new MenuFactory(SIMPLE_MENU,AddUtils.colorful("合法配方类型大赏"),pageNum){
            public void init(){
                setDefaultNPSlots();
            }
        }.addOverrides(7, SlimefunGuide.getItem(SlimefunGuide.getDefaultMode()));
        a.setGuideModHistory(history);
        if(backHandler!=null){
            a.setBack(1);
            a.setBackHandler(backHandler);
        }else{
            a.setBack(1);
            a.setBackHandler(CustomMenuHandler.from(CLOSE_HANDLER));
        }
        int i=0;
        for(RecipeType entry:recipeTypes){
            if(!map.containsKey(entry)){
                continue;
            }
            int currentPage=(1+(i)/pageContent);
            a.addInventory(i,map.get(entry));
            List<SlimefunItem> recipe=RecipeSupporter.RECIPETYPE_TO_ITEMS.get(entry);
            if(recipe==null||recipe.isEmpty()){
                a.addHandler(1,((player, i1, itemStack, clickAction) -> {
                    player.sendMessage(ChatColors.color("&e该配方类型在本附属的配方类型黑名单中,暂不支持查看"));
                    return false;
                }));
            }else{
                a.addHandler(i,(customMenuHandler) ->((player, i1, itemStack, clickAction) -> {
                    createItemListDisplay(entry,recipe,customMenuHandler.getBackToThisHandler(currentPage)).build().open(player);
                    return false;
                }));
            }
            ++i;
        }
        return a;
    }
    public static MenuFactory createMachineListDisplay(List<SlimefunItem> machineTypes,CustomMenuHandler backHandler){
        return createMachineListDisplay(machineTypes,backHandler,null);
    }
    public static MenuFactory createMachineListDisplay(List<SlimefunItem> machineTypes,CustomMenuHandler backHandler,PlayerHistoryRecord<CustomMenu> history){
        HashMap<SlimefunItem,List<MachineRecipe>> map=RecipeSupporter.MACHINE_RECIPELIST;
        int RecipeSize =0;
        for(SlimefunItem entry:machineTypes){
            if(map.containsKey(entry)){
                RecipeSize++;
            }
        }
        int pageContent=36;
        int pageNum=(1+(RecipeSize-1)/pageContent);

        MenuFactory a=new MenuFactory(SIMPLE_MENU,AddUtils.colorful("识别机器类型大赏"),pageNum){
            public void init(){
                setDefaultNPSlots();
            }
        }.addOverrides(7, SlimefunGuide.getItem(SlimefunGuide.getDefaultMode())).setGuideModHistory(history);

        a.setBack(1);
        if(backHandler!=null){
            a.setBackHandler(backHandler);
        }else{
            a.setBackHandler(CustomMenuHandler.from( CLOSE_HANDLER));
        }
        int i=0;
        for(SlimefunItem entry:machineTypes){
            if(!map.containsKey(entry)){
                continue;
            }
            int currentPage=(1+(i)/pageContent);
            a.addInventory(i,entry.getItem());
            List<MachineRecipe> recipe=map.get(entry);
            if(recipe==null){
                a.addHandler(i,((player, i1, itemStack, clickAction) -> {
                    player.sendMessage(ChatColors.color("&e该机器类型无法识别,暂不支持查看"));
                    return false;
                }));
            }else{
                final List<MachineRecipe> machineRecipes=recipe;
                a.addHandler(i,(cm)-> (player, i1, itemStack, clickAction) -> {
                    createMRecipeListDisplay(entry.getItem(),machineRecipes,cm.getBackToThisHandler(currentPage)).build().open(player);
                    return false;
                });
            }
            ++i;
        }
        return a;
    }
    public BlockMenu createBlockMenuFromChest(Block b){
        //new BlockMenu()
        return null;
    }



}
