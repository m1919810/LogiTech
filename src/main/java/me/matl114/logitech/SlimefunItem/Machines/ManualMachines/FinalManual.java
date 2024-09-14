package me.matl114.logitech.SlimefunItem.Machines.ManualMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.SlimefunItem.Machines.*;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.Utils.UtilClass.MenuClass.MenuFactory;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ImportRecipes;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusherProvider;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FinalManual extends AbstractManual implements MultiCraftType, ImportRecipes {
    protected final int[] INPUT_SLOT = new int[]{3,4,5,6,7,8,12,13,14,15,16,17,21,22,23,24,25,26,30,31,32,33,34,35,39,40,41,42,43,44,48,49,50,51,52,53};
    protected final int[] OUTPUT_SLOT=new int[]{30,31,32,33,34,35,39,40,41,42,43,44,48,49,50,51,52,53,3,4,5,6,7,8,12,13,14,15,16,17,21,22,23,24,25,26};
    protected static final int RECIPE_ITEM_SLOT=0;
    protected static final ItemStack INFO_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6配方匹配机制:"," "
            ,"&b点击左边图标切换合成类型","&b点击右边书本查看当前合成类型信息","&b在输入栏放入物品进行快捷合成");
    protected static final int INFO_SLOT=1;
    protected static final int RECIPEBOOK_SHOW_SLOT=2;
    protected static final ItemStack PREV=new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,"&3搜索上一个配方");
    protected static final int PREV_SLOT=9;
    protected static final int DISPLAYEITEM_SLOT=10;
    protected static final ItemStack NEXT=new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,"&3搜索下一个配方");
    protected static final int NEXT_SLOT=11;
    protected static final ItemStack CRAFT_ONE=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&3单次合成","&6左键&b合成 &d1次 &b当前物品","&6右键&b合成 &d64次 &b当前物品");
    protected static final int ONE_SLOT=18;
    protected static final ItemStack CRAFT_MUL=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&3批量合成","&6左键&b合成 &d3456次 &b当前物品","&6右键&b合成 &d9,999,999次 &b当前物品");
    protected static final int MUL_SLOT=20;
    protected static final ItemStack INFO2_ITEM=new CustomItemStack(Material.BEACON,"&6合成机制: "+ AddUtils.colorful("终极合成"),
            "","&b该快捷机器可直接消耗存储类物品或者链接绑定的实体存储内的物品参与合成",
            "&b产出的物品也可以直接地存入存储类物品或者链接绑定的实体存储",
            "&c配方总输出数量最大会被限制至2147483647",
            "","&b当前支持的物品:",
            "&7逻辑工艺 概念奇点存储(存储类物品)",
            "&7逻辑工艺 量子纠缠奇点(存储链接物品)",
            "&7网络(拓展) 量子存储系列(存储类物品)");
    protected static final int INFO2_SLOT=19;
    protected static final ItemStack DISPLAY_BKGROUND=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE," ");
    protected static final ItemStack DISPLAY_DEFAULT_BKGROUND=new CustomItemStack(Material.RED_STAINED_GLASS_PANE," ");
    protected static final ItemStack RECIPEBOOK_SHOW_ITEM=new CustomItemStack(Material.BOOK,"&6点击查看配方","","&6但是并没有一键放置配方的功能");
    protected static  final int[] RECIPE_DISPLAY={
            27,28,29,36,37,38,45,46,47
    };
    public FinalManual(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         int energybuffer, int energyConsumption) {
        super(category,item,recipeType,recipe,0,0,null);
        this.BW_LIST=new ArrayList<>(){{
            add(RecipeType.ENHANCED_CRAFTING_TABLE);
            add(RecipeType.SMELTERY);
            add(RecipeType.MAGIC_WORKBENCH);
            add(RecipeType.ANCIENT_ALTAR);
            add(BugCrafter.TYPE);
            add(RecipeType.GRIND_STONE);
            add(RecipeType.ORE_CRUSHER);
            add(RecipeType.ORE_WASHER);
            add(RecipeType.GOLD_PAN);
            add(RecipeType.COMPRESSOR);
            add(RecipeType.PRESSURE_CHAMBER);
            add(RecipeType.ARMOR_FORGE);
            add(RecipeType.HEATED_PRESSURE_CHAMBER);
            add(BukkitUtils.VANILLA_CRAFTTABLE);
            add(BukkitUtils.VANILLA_FURNACE);
        }};
        SchedulePostRegister.addPostRegisterTask(
                ()->{
                    registerRecipeList();
                    initMenuFactory();
                }
        );
        //启动奇点合成的支持
        this.CRAFT_PROVIDER=SINGULARITY_PROVIDER;
        setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow(
                                "&f机制 - &c终极合成",
                                "&7机器的输入槽,输出槽均支持终极合成的特性",
                                "&e可以直接消耗存储类物品或者链接绑定的实体存储内的物品参与合成",
                                "&e产出的物品也可以直接地存入存储类物品或者链接绑定的实体存储",
                                "&7当前支持的物品:",
                                "&7逻辑工艺 概念奇点存储(存储类物品)",
                                "&7逻辑工艺 量子纠缠奇点(存储链接物品)",
                                "&7网络(拓展) 量子存储系列(存储类物品)",
                                "&a建议:多使用量子纠缠奇点,可以大幅度减少卡顿!"
                        ),null,
                        AddUtils.getInfoShow(
                                "&f机制 - &c无限级一键合成",
                                "&7本机器最多支持一键合成9,999,999次",
                                "&7\"终极合成\"的特性让它不再是空谈",
                                "&7是时候让存储里的大批物品动起来了"

                        )
                )
        );

    }
    public static final ItemPusherProvider SINGULARITY_PROVIDER=FinalFeature.STORAGE_AND_LOCPROXY_READER;
    //after start,load recipeType from config
    public void registerRecipeList(){
        craftType=BW_LIST.toArray(new RecipeType[BW_LIST.size()]);
    }
    public final List<RecipeType> BW_LIST;
    public RecipeType[] craftType=new RecipeType[]{RecipeType.ENHANCED_CRAFTING_TABLE};
    protected boolean isWhiteList=true;
    public List<MachineRecipe> getMachineRecipes(){
        return machineRecipes;
    }
    public List<MachineRecipe> getMachineRecipes(Block b,BlockMenu inv){
        Location loc=inv.getLocation();
        int index=MultiCraftType.getRecipeTypeIndex(loc);
        if(index>=0&&index<getCraftTypes().length){
            return RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(getCraftTypes()[index]);
        }else if (index>0){
            MultiCraftType.forceSetRecipeTypeIndex(loc,0);
            setNowRecordRecipe(loc,-1);
        }
        return null;
    }
    public RecipeType[] getCraftTypes(){
        return craftType;
    }
    public MachineRecipe getRecordRecipe(SlimefunBlockData data){
        int index=MultiCraftType.getRecipeTypeIndex(data);
        if(index>=0&&index<getCraftTypes().length){
            int index2= getNowRecordRecipe(data);
            if(index2>=0){//I think that's safe
                return RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(getCraftTypes()[index]).get(index2);
            }
        }else {
            MultiCraftType.forceSetRecipeTypeIndex(data,0);
        }return null;
    }
    public void constructMenu(BlockMenuPreset preset) {
        //空白背景 禁止点击
        preset.addItem(DISPLAYEITEM_SLOT, DISPLAY_DEFAULT_BKGROUND, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INFO_SLOT, INFO_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(RECIPE_ITEM_SLOT,DISPLAY_NOMATCH, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INFO2_SLOT,INFO2_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(PREV_SLOT,PREV);
        preset.addItem(NEXT_SLOT,NEXT);
        preset.addItem(ONE_SLOT,CRAFT_ONE);
        preset.addItem(MUL_SLOT,CRAFT_MUL);
        preset.addItem(RECIPEBOOK_SHOW_SLOT,RECIPEBOOK_SHOW_ITEM);
        int len=RECIPE_DISPLAY.length;
        for(int i=0;i<len;i++){
            preset.addItem(RECIPE_DISPLAY[i],DISPLAY_DEFAULT_BKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }
    }
    protected HashMap<RecipeType,MenuFactory>  RECIPEMENU=null;
    protected void initMenuFactory(){
        if(RECIPEMENU==null){
            RECIPEMENU=new HashMap<>();
            int len=craftType.length;
            RecipeType rp;
            for(int i=0;i<len;i++){
                rp=craftType[i];
                RECIPEMENU.put(rp, MenuUtils.createMRecipeListDisplay(RecipeSupporter.RECIPETYPE_ICON.get(rp),RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(rp),
                        null
                ));
            }
        }
    }
    protected MenuFactory getMenuFactory(RecipeType r){
        if(RECIPEMENU==null){
            initMenuFactory();
        }
        return RECIPEMENU.get(r);
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        int ind=MultiCraftType.getRecipeTypeIndex(menu.getLocation());
        //清除非法记录
        if(!(ind>=0&&ind<getCraftTypes().length)){
            MultiCraftType.setRecipeTypeIndex(menu.getLocation(),0);
        }
        menu.addMenuOpeningHandler((player -> {
            FinalManual.this.updateMenu(menu,block,Settings.RUN);
        }));
        menu.addMenuClickHandler(RECIPE_ITEM_SLOT,
                ((player, i, itemStack, clickAction) -> {
                    Location loc=menu.getLocation();
                    int index=MultiCraftType.getRecipeTypeIndex(loc);
                    index+=clickAction.isRightClicked()?-1:1;
                    if(index>=0&&index<=getCraftTypes().length-1){
                        MultiCraftType.forceSetRecipeTypeIndex(loc,index);
                    }else if(index<0){
                        MultiCraftType.forceSetRecipeTypeIndex(loc,getCraftTypes().length-1);
                    }else {
                        MultiCraftType.forceSetRecipeTypeIndex(loc,0);
                    }
                    FinalManual.this.updateMenu(menu,block,Settings.RUN);
                    return false;
                })
                );
        menu.addMenuClickHandler(PREV_SLOT,
                (player, i, itemStack, clickAction)->{
                    if( getNowRecordRecipe(menu.getLocation())!=-1){
                        orderSearchRecipe(menu, Settings.REVERSE);
                        FinalManual.this.updateMenu(menu,block,Settings.RUN);
                    }return false;
                }
        );
        menu.addMenuClickHandler(NEXT_SLOT,

                (player, i, itemStack, clickAction)->{
                    if(getNowRecordRecipe(menu.getLocation())!=-1){
                        orderSearchRecipe(menu,Settings.SEQUNTIAL);
                        FinalManual.this.updateMenu(menu,block,Settings.RUN);

                    }return false;
                }
        );
        menu.addMenuClickHandler(ONE_SLOT,
                (player, i, itemStack, clickAction)->{
                    int limit=1;
                    if(clickAction.isRightClicked()){
                        limit=64;
                    }
                    craft(menu,limit);
                    FinalManual.this.updateMenu(menu,block,Settings.INIT);

                    return false;
                }
        );
        menu.addMenuClickHandler(MUL_SLOT,
                (player, i, itemStack, clickAction)->{
                    int limit=3456;
                    if(clickAction.isRightClicked()){
                        limit=9_999_999;
                    }
                    craft(menu,limit);
                    FinalManual.this.updateMenu(menu,block,Settings.INIT);
                    return false;
                }
        );
        menu.addMenuClickHandler(RECIPEBOOK_SHOW_SLOT,
                ((player, i, itemStack, clickAction) -> {
                    int index=MultiCraftType.getRecipeTypeIndex(menu.getLocation());
                    if(index>=0){
                        RecipeType rp=getCraftTypes()[index];
                        getMenuFactory(rp).build().setBackHandler(((player1, i1, itemStack1, clickAction1) -> {
                            menu.open(player1);
                            return false;
                        })).open(player);
                    }else{
                        player.sendMessage(ChatColors.color("&e配方类型获取错误"));
                        // player.sendMessage();
                    }
                    return false;
                })
        );
        updateMenu(menu,block,Settings.INIT);
    }

    public void updateMenu(BlockMenu inv,Block block,Settings mod){
        if(mod==Settings.INIT){
            orderSearchRecipe(inv,Settings.SEQUNTIAL);
        }
        Location  loc=inv.getLocation();
        int index_=MultiCraftType.getRecipeTypeIndex(loc);
        if(index_>=0){
            RecipeType rp=getCraftTypes()[index_];
            List<MachineRecipe> mRecipe=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(rp);
            inv.replaceExistingItem(RECIPE_ITEM_SLOT,AddUtils.addLore(RecipeSupporter.RECIPETYPE_ICON.get(rp),
                    "&8点击切换配方类型","&8左键顺时针切换","&8右键逆时针切换"));

            int index= DataCache.getLastRecipe(loc);
            int indexRecord=getNowRecordRecipe(loc);
            if(index!=-1){
                MachineRecipe getRecipe=mRecipe.get(index);
                inv.replaceExistingItem(DISPLAYEITEM_SLOT, AddUtils.addLore(getRecipe.getOutput()[0],
                        "&8匹配的产物", "&8若有多输出则显示第一个", "&8配方编号: " + index));
                ItemStack[] opt=getRecipe.getInput();
                int len=opt.length;
                len=Math.min(len,RECIPE_DISPLAY.length);
                for(int i=0;i<len;i++){
                    inv.replaceExistingItem(RECIPE_DISPLAY[i], RecipeDisplay.addRecipeInfo(opt[i],Settings.INPUT,i,1.0,0));
                }
                for(int i=len;i<RECIPE_DISPLAY.length;i++){
                    inv.replaceExistingItem(RECIPE_DISPLAY[i],DISPLAY_BKGROUND);
                }
                if(index!=indexRecord||mod==Settings.INIT) {

                    setNowRecordRecipe(loc,index);
                    return;
                }
                return;
            }else{

                if(indexRecord!=-1||mod==Settings.INIT){
                    inv.replaceExistingItem(DISPLAYEITEM_SLOT,DISPLAY_NOMATCH);
                    setNowRecordRecipe(loc,-1);
                    for(int i=0;i<RECIPE_DISPLAY.length;i++){
                        inv.replaceExistingItem(RECIPE_DISPLAY[i],DISPLAY_DEFAULT_BKGROUND);
                    }
                }
            }
        }else {
            MultiCraftType.forceSetRecipeTypeIndex(loc,0);
        }
    }
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public void orderSearchRecipe(BlockMenu inv, Settings order){
        if(inv!=null){
            int delta;
            switch (order){
                case REVERSE:delta=-1;break;
                case SEQUNTIAL:
                default: delta=1;break;
            }

            Location  loc=inv.getLocation();
            int index= DataCache.getLastRecipe(loc);
            if(index<0){
                return;
            }
            List<MachineRecipe> mRecipe=getMachineRecipes(null,inv);
            if(mRecipe==null)return;
            index=index+delta;
            if(index<0){
                index=mRecipe.size()-1;
            }
            else if(index>=mRecipe.size()){
                index=0;
            }
            DataCache.setLastRecipe(loc,index);
            if(CraftUtils.matchNextRecipe(inv,getInputSlots(),mRecipe,true,order,SINGULARITY_PROVIDER)==null){
                DataCache.setLastRecipe(loc,-1);
            }
        }
    }
//    public void craft(BlockMenu inv,int limit){
//        Location  loc=inv.getLocation();
//        int recordIndex=getNowRecordRecipe(loc);
//        List<MachineRecipe> mRecipe=getMachineRecipes(null,inv);
//        //没有匹配配方会直接返回失败
//        if(recordIndex<0||recordIndex>=mRecipe.size()){
//            return;
//        }
//        MachineRecipe recordRecipe=mRecipe.get(recordIndex);
//        Pair<ItemGreedyConsumer[],ItemGreedyConsumer[]> results=
//                CraftUtils.countMultiRecipe(inv,getInputSlots(),getOutputSlots(),recordRecipe,limit,SINGULARITY_PROVIDER);
//        //输出满了会返回null
//        if(results==null){
//            return;
//        }if(this.energyConsumption>0){
//            int craftTime=CraftUtils.calMaxCraftTime(results.getSecondValue(),limit);
//            this.removeCharge(loc,craftTime*this.energyConsumption);
//        }
//        CraftUtils.multiUpdateInputMenu(results.getFirstValue(),inv);
//        CraftUtils.multiUpdateOutputMenu(results.getSecondValue(),inv);
//    }
    boolean test= MyAddon.testmode();
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        //only works when has viewer.
        if(inv!=null&&(inv.hasViewer())){
            Location  loc=inv.getLocation();
            MachineRecipe getRecipe=CraftUtils.matchNextRecipe(inv,getInputSlots(),getMachineRecipes(b,inv),true,Settings.SEQUNTIAL,SINGULARITY_PROVIDER);
            if(getRecipe==null){
                DataCache.setLastRecipe(loc,-1);
            }
            updateMenu(inv ,b,Settings.RUN);
        }
    }


}
