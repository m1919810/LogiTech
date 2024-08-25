package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.SlimefunItem.Machines.AbstractProcessor;
import me.matl114.logitech.SlimefunItem.Machines.MultiCraftType;
import me.matl114.logitech.SlimefunItem.Machines.RecipeDisplay;
import me.matl114.logitech.SlimefunItem.Machines.RecipeLock;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.DisplayItemStack;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ImportRecipes;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SpecialCrafter extends AbstractProcessor implements RecipeLock, ImportRecipes {
    public List<ItemStack> displayedMemory;
    protected final int publicTime;
    protected final int[] BORDER={
            3,5,12,14
    };
    protected  final int[] RECIPE_DISPLAY={
            30,31,32,39,40,41,48,49,50
    };
    protected final int[] INPUT_SLOT={
            0,1,9,10,18,19,27,28,36,37,45,46
    };
    protected final int[] OUTPUT_SLOT={
            7,8,16,17,25,26,34,35,43,44,52,53
    };
    protected final int[] BORDER_SLOT={
            2,6,11,15,20,24,29,33,38,42,47,51
    };
    protected final int RECIPEITEM_SLOT=13;
    protected final int MACHINEITEM_SLOT=22;
    protected int PARSE_SLOT=4;
    protected RecipeType[] craftType;
    protected final HashSet<RecipeType> BW_LIST;
    protected final ItemStack PARSE_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&e点击解析配方",
            "&b机制:","&6将配方表输出的物品(若有多个则第一个)置于下方第一个槽位","&6将配方表所使用的特殊工作台置于下方第二个槽位","&6右键本槽位,或者开关容器界面,配方将被解析","&6机器将按照解析出的指定配方合成");
    protected final ItemStack DISPLAY_BKGROUND=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE," ");
    protected final ItemStack DISPLAY_DEFAULT_BKGROUND=new CustomItemStack(Material.RED_STAINED_GLASS_PANE," ");
    public SpecialCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          Material progressItem,int ticks, int energyConsumption, int energyBuffer){
        this(category,item,recipeType,recipe,progressItem,ticks,energyConsumption,energyBuffer,new HashSet<>());
    }
    public SpecialCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                             Material progressItem,int ticks, int energyConsumption, int energyBuffer,HashSet<RecipeType> blackList){
        super(category,item,recipeType,recipe,progressItem,energyConsumption,energyBuffer,null);
        this.BW_LIST=blackList;
        this.publicTime=ticks;
        SchedulePostRegister.addPostRegisterTask(()->{
            getCraftTypes();
        });
    }
    public ItemStack getProgressBar(){
        return this.progressbar;
    }
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<>(){{

        }};
    }
    public RecipeType[] getCraftTypes(){
        if(craftType==null){
            HashMap<SlimefunItem,RecipeType> types=RecipeSupporter.CUSTOM_RECIPETYPES;
            if(types==null||types.size()<=0){
                craftType=new RecipeType[0];
                return craftType;
            }
            craftType=new RecipeType[types.size()];
            int cnt=0;
            for(RecipeType e :types.values()){
                if(BW_LIST.contains(e)){//黑名单
                    continue;
                }
                craftType[cnt]=e;
                cnt++;
            }
        }
        return craftType;
    }
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }

    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public void constructMenu(BlockMenuPreset preset) {
        //空白背景 禁止点击

        //输入槽边框
        int[] border = BORDER_SLOT;
        int len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        border = BORDER;
        len =border.length;
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(21,MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(23,MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
        border = RECIPE_DISPLAY;
        len = border.length;
        //emptyhandler
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addMenuClickHandler(border[var4], ChestMenuUtils.getEmptyClickHandler());
        }
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        menu.replaceExistingItem(PARSE_SLOT,PARSE_ITEM);
        menu.addMenuClickHandler(PARSE_SLOT,(player, i, itemStack, clickAction)->{
            parseRecipe(menu);
            updateMenu(menu,block, Settings.RUN);
            return false;
        });
        menu.addMenuOpeningHandler((player -> {
            parseRecipe(menu);
            updateMenu(menu,block,Settings.RUN);
        }));
        menu.addMenuCloseHandler((player -> {
            parseRecipe(menu);
            updateMenu(menu,block,Settings.RUN);
        }));
        updateMenu(menu,block,Settings.INIT);
    }

    public void onBreak(BlockBreakEvent e, BlockMenu menu){
        super.onBreak(e, menu);
        Location loc=menu.getLocation();
        menu.dropItems(loc,RECIPEITEM_SLOT);
        menu.dropItems(loc,MACHINEITEM_SLOT);
    }

    public MachineRecipe getRecordRecipe(Location loc){
        return getRecordRecipe(DataCache.safeLoadBlock(loc));
    }
    public MachineRecipe getRecordRecipe(SlimefunBlockData data){
        int index=MultiCraftType.getRecipeTypeIndex(data);
        if(index>=0){
            RecipeType[] types=getCraftTypes();
            if(index>=types.length){//下标越界检查
                MultiCraftType.setRecipeTypeIndex(data,-1);
                return null;
            }
            List<MachineRecipe> recipes=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(types[index]);
            int index2=getNowRecordRecipe(data);
            if(index2>=0&&recipes.size()>index2){
                return recipes.get(index2);
            }else {
                setNowRecordRecipe(data,-1);
                return null;
            }
        }
        return null;
    }
    public boolean parseRecipe(BlockMenu menu){
        ItemStack machine=menu.getItemInSlot(MACHINEITEM_SLOT);
        SlimefunItem machineType=SlimefunItem.getByItem(machine);
        Location loc=menu.getLocation();
        if(machineType==null||!RecipeSupporter.CUSTOM_RECIPETYPES.containsKey(machineType)){
            setNowRecordRecipe(loc,-1);
            MultiCraftType.setRecipeTypeIndex(loc,-1);
            return false;
        }
        RecipeType type=RecipeSupporter.CUSTOM_RECIPETYPES.get(machineType);
        RecipeType[] ctype=getCraftTypes();
        for (int i=0;i<ctype.length;++i){
            if(type==ctype[i]){
                MultiCraftType.  setRecipeTypeIndex(loc,i);
            }
        }
        ItemStack target=menu.getItemInSlot(RECIPEITEM_SLOT);
        if(target==null||target.getType()==Material.AIR){
            setNowRecordRecipe(menu.getLocation(),-1);
            return false;
        }else{
            List<MachineRecipe> machineRecipes1=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(type);
            for(int i=0;i<machineRecipes1.size();++i){
                MachineRecipe machineRecipe=machineRecipes1.get(i);
                if(CraftUtils.matchItemStack(target,machineRecipe.getOutput()[0], false)){
                    setNowRecordRecipe(menu.getLocation(),i);
                    return true;
                }
            }
            setNowRecordRecipe(menu.getLocation(),-1);
            return false;
        }
    }
    public void updateMenu(BlockMenu menu,Block block,Settings mod){
        MachineRecipe recipe=getRecordRecipe(block.getLocation());
        if(recipe==null){
            for(int var4 = 0; var4 < RECIPE_DISPLAY.length; ++var4) {
                menu.replaceExistingItem(RECIPE_DISPLAY[var4],DISPLAY_DEFAULT_BKGROUND);
            }
        }else{
            ItemStack[] input ;
            input =recipe.getInput();

            int len=Math.min(RECIPE_DISPLAY.length,input.length);
            for(int var4 = 0; var4 < len; ++var4) {
                menu.replaceExistingItem(RECIPE_DISPLAY[var4], RecipeDisplay.addRecipeInfo(input[var4],Settings.INPUT,var4,1.0,0));
            }
            for(int var4 = len; var4 < RECIPE_DISPLAY.length; ++var4) {
                menu.replaceExistingItem(RECIPE_DISPLAY[var4],DISPLAY_BKGROUND);
            } }
    }
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){

        SimpleCraftingOperation currentOperation = this.processor.getOperation(b);
        ItemConsumer[] fastCraft=null;
        if(currentOperation==null){
            ItemStack tmp=inv.getItemInSlot(RECIPEITEM_SLOT);
            ItemStack tmp2=inv.getItemInSlot(MACHINEITEM_SLOT);
            if(tmp==null||tmp.getType()==Material.AIR){
                setNowRecordRecipe(inv.getLocation(),-1);

                updateMenu(inv,b,Settings.RUN);
            }
            else if(tmp2==null||tmp2.getType()==Material.AIR){
                MultiCraftType.  setRecipeTypeIndex(data,-1);
                updateMenu(inv,b,Settings.RUN);
            }
            MachineRecipe next=getRecordRecipe(b.getLocation());
            if(next==null){
                if(inv.hasViewer()){inv.replaceExistingItem(21, MenuUtils.PROCESSOR_NULL);
                    inv.replaceExistingItem(23, MenuUtils.PROCESSOR_NULL);
                }
                return ;
            }

            Pair<ItemConsumer[],ItemConsumer[]> nextP=CraftUtils.countOneRecipe(inv,getInputSlots(),getOutputSlots(),next
            ,CRAFT_PROVIDER);

            if (nextP != null) {

                CraftUtils.updateInputMenu(nextP.getFirstValue(),inv);
                int ticks=this.publicTime;
                if(ticks>0){

                    currentOperation = new SimpleCraftingOperation(nextP.getSecondValue(),ticks );
                    this.processor.startOperation(b, currentOperation);
                }
                else{
                    fastCraft = nextP.getSecondValue();
                }
            }else{//if currentOperation ==null return  , cant find nextRecipe
                if(inv.hasViewer()){inv.replaceExistingItem(21, MenuUtils.PROCESSOR_NULL);
                    inv.replaceExistingItem(23, MenuUtils.PROCESSOR_NULL);
                }
                return ;
            }
        }
        progressorCost(b,inv);
        if (fastCraft!=null) {
            CraftUtils.updateOutputMenu(fastCraft,inv);
        }else if(currentOperation.isFinished()){
            ItemConsumer[] var4=currentOperation.getResults();
            int var5 = var4.length;
            CraftUtils.forcePush(var4,inv,getOutputSlots(),CRAFT_PROVIDER);
            if(inv.hasViewer()){inv.replaceExistingItem(21, MenuUtils.PROCESSOR_NULL);
                inv.replaceExistingItem(23, MenuUtils.PROCESSOR_NULL);
            }
            this.processor.endOperation(b);
        }
        else{
            if(inv.hasViewer()){
                this.processor.updateProgressBar(inv, 21, currentOperation);
                this.processor.updateProgressBar(inv, 23, currentOperation);
            }
            currentOperation.addProgress(1);
        }
    }
    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
        return new int[0];
    }

    @Override
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) return getOutputSlots();
        int [] inputSlots=getInputSlots();
        MachineRecipe now=getRecordRecipe(((BlockMenu)menu).getLocation());
        if(now==null){
            return new int[0];
        }
        int craftlimit=1;
        int amountLimit=0;
        int maxStack=item.getMaxStackSize();
        ItemStack[] recipeInput=now.getInput();
        for (int i=0;i<recipeInput.length;++i){
            if(recipeInput[i].getType()==item.getType()&&recipeInput[i].hasItemMeta()==item.hasItemMeta()){
                amountLimit+=Math.max(recipeInput[i].getAmount()*craftlimit,maxStack);
            }
        }
        // Check the current amount
        int itemAmount = 0;
        for (int slot : getInputSlots()) {
            ItemStack itemInSlot = menu.getItemInSlot(slot);
            if(itemInSlot==null)continue;
            if (itemInSlot.getType()==item.getType()&&itemInSlot.hasItemMeta()==item.hasItemMeta()) {
                itemAmount+=itemInSlot.getAmount();
                // Amount has reached the limited, just return.
                if(itemAmount>=amountLimit){
                    return new int[0];
                }
            }
        }
        return inputSlots;
    }
    public List<ItemStack> _getDisplayRecipes(){
        return new ArrayList<>(){{
            for(SlimefunItem item : RecipeSupporter.CUSTOM_RECIPETYPES.keySet()){
                add(AddUtils.getInfoShow("&f支持的机器","&7将机器插入指定槽位以进行合成"));
                add(new DisplayItemStack(item.getItem()));
            }
        }};
    }


}
