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
import me.matl114.logitech.SlimefunItem.Interface.RecipeDisplay;
import me.matl114.logitech.SlimefunItem.Machines.*;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.*;
import me.matl114.logitech.Utils.UtilClass.MenuClass.DataMenuClickHandler;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ImportRecipes;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.MultiCraftingOperation;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class SpecialCrafter extends AbstractAdvancedProcessor implements  ImportRecipes {
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
            "&b机制:","&6将配方表输出的物品(若有多个则第一个)，","&e置于下方第一个槽位",
            "&6将合成该物品所使用的特殊机器(物品)","&e置于下方第二个槽位",
            "&6机器将会尝试解析该物品的配方","&6机器将按照解析出的指定配方合成");
    protected final ItemStack DISPLAY_BKGROUND=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE," ");
    protected final ItemStack DISPLAY_DEFAULT_BKGROUND=new CustomItemStack(Material.RED_STAINED_GLASS_PANE," ");
    protected ItemPusherProvider MACHINE_PROVIDER=CraftUtils.getpusher;
    public SpecialCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          Material progressItem,int ticks, int energyConsumption, int energyBuffer){
        this(category,item,recipeType,recipe,progressItem,ticks,energyConsumption,energyBuffer,new HashSet<>());
    }
    public SpecialCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                             Material progressItem,int ticks, int energyConsumption, int energyBuffer,HashSet<RecipeType> blackList){
        super(category,item,recipeType,recipe,progressItem,energyConsumption,energyBuffer,null);
        this.BW_LIST=blackList;
        Debug.debug(this.BW_LIST);
        this.publicTime=ticks;
        SchedulePostRegister.addPostRegisterTask(()->{
            getCraftTypes();
        });
    }
    public ItemStack getProgressBar(){
        return this.progressbar;
    }
    public abstract HashMap<SlimefunItem,RecipeType> getRecipeTypeMap();
    public abstract boolean advanced();
    public int getCraftLimit(Block b,BlockMenu menu){
        if(advanced()){
            DataMenuClickHandler handler=getDataHolder(b,menu);
            int t= handler.getInt(2);
            return t<=0?1:t;
        }else return 1;
    }
    public RecipeType[] getCraftTypes(){
        if(craftType==null){
            HashMap<SlimefunItem,RecipeType> types=getRecipeTypeMap();
            if(types==null||types.size()<=0){
                craftType=new RecipeType[0];
                return craftType;
            }
            craftType=new RecipeType[types.size()];
            int cnt=0;
            Debug.debug(BW_LIST);
            for(RecipeType e :types.values()){
                Debug.debug(e.getKey());
                if(BW_LIST.contains(e)){//黑名单
                    Debug.debug(e.getKey());
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
            updateMenu(menu,block, Settings.RUN);
            return false;
        });
        menu.addMenuOpeningHandler((player -> {
            updateMenu(menu,block,Settings.RUN);
        }));
        menu.addMenuCloseHandler((player -> {
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
    public boolean parseRecipe(BlockMenu menu){
        ItemPusher machine=MACHINE_PROVIDER.getPusher(Settings.OUTPUT,menu,MACHINEITEM_SLOT);
        SlimefunItem machineType=SlimefunItem.getByItem(machine.getItem());
        HashMap<SlimefunItem,RecipeType> providedRecipesMap=getRecipeTypeMap();
        DataMenuClickHandler handler=getDataHolder(null,menu);
        if(providedRecipesMap==null) {
            handler.setInt(0,-1);
            handler.setInt(1,-1);
            handler.setInt(2,0);
            return false;
        }
        if(machineType==null||!providedRecipesMap.containsKey(machineType)){
            handler.setInt(0,-1);
            handler.setInt(1,-1);
            handler.setInt(2,0);
            //MultiCraftType.setRecipeTypeIndex(loc,-1);
            return false;
        }
        //TODO 继续进行这块的优化检查，虽然我也不知道优化啥
        RecipeType type=providedRecipesMap.get(machineType);
        RecipeType[] ctype=getCraftTypes();
        for (int i=0;i<ctype.length;++i){
            if(type==ctype[i]){
                //0位设置type
                handler.setInt(0,i);
                handler.setInt(2,machine.getAmount());
                break;
            }
        }
        ItemStack target=menu.getItemInSlot(RECIPEITEM_SLOT);
        if(target==null||target.getType()==Material.AIR){
            //record 1 配方位记录-1
            handler.setInt(1,-1);
            return false;
        }else{
            List<MachineRecipe> machineRecipes1=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(type);
            for(int i=0;i<machineRecipes1.size();++i){
                MachineRecipe machineRecipe=machineRecipes1.get(i);
                if(CraftUtils.matchItemStack(target,machineRecipe.getOutput()[0], false)){
                    handler.setInt(1,i);
                    return true;
                }
            }
            handler.setInt(1,-1);
            return false;
        }
    }
    public DataMenuClickHandler createDataHolder(){
        return new DataMenuClickHandler() {
            //0 为 数量 1 为 电力
            int[] intdata=new int[3];
            public int getInt(int i){
                return intdata[i];
            }
            public void setInt(int i, int val){
                intdata[i]=val;
            }
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                return false;
            }
        };
    }
    public final int DATA_SLOT=3;
    public DataMenuClickHandler getDataHolder(Block b, BlockMenu inv){
        ChestMenu.MenuClickHandler handler=inv.getMenuClickHandler(DATA_SLOT);
        if(handler instanceof DataMenuClickHandler dh){return dh;}
        else{
            DataMenuClickHandler dh=createDataHolder();
            inv.addMenuClickHandler(DATA_SLOT,dh);
            return dh;
        }
    }
    public void updateMenu(BlockMenu menu,Block block,Settings mod){
        DataMenuClickHandler hd=getDataHolder(block,menu);
        parseRecipe(menu);
        MachineRecipe recipe=getRecipe(menu);
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
            }
        }
    }

    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        if(inv.hasViewer()){
            updateMenu(inv,b,Settings.RUN);
        }
        MultiCraftingOperation currentOperation = this.processor.getOperation(b);
        ItemGreedyConsumer[] fastCraft=null;
        if(currentOperation==null){
            MachineRecipe next=getRecipe(inv);
            if(next==null){
                if(inv.hasViewer()){inv.replaceExistingItem(21, MenuUtils.PROCESSOR_NULL);
                    inv.replaceExistingItem(23, MenuUtils.PROCESSOR_NULL);
                }
                return ;
            }
            int maxCraftlimit=getCraftLimit(b,inv);
            int time= next.getTicks();
            time=time<0?this.publicTime:time;
            if(time!=0){//超频机制
                //尝试让time归1
                //按比例减少maxlimit ,按最小值取craftlimit
                if(maxCraftlimit<=time){
                    time=( (time+1)/maxCraftlimit)-1;
                    maxCraftlimit=1;
                }else {
                    maxCraftlimit=(maxCraftlimit/(time+1));
                    time=0;
                }
            }
            Pair<ItemGreedyConsumer[],ItemGreedyConsumer[]> nextP=CraftUtils.countMultiRecipe(inv,getInputSlots(),getOutputSlots(),next
            ,maxCraftlimit,CRAFT_PROVIDER);
            if (nextP != null) {

                CraftUtils.multiUpdateInputMenu(nextP.getFirstValue(),inv);
                if(time>0){
                    currentOperation = new MultiCraftingOperation(nextP.getSecondValue(),time);
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
            CraftUtils.multiUpdateOutputMenu(fastCraft,inv);
        }else if(currentOperation.isFinished()){
            ItemGreedyConsumer[] var4=currentOperation.getResults();
            CraftUtils.multiForcePush(var4,inv,getOutputSlots(),CRAFT_PROVIDER);
            if(inv.hasViewer())
            {
                inv.replaceExistingItem(21, MenuUtils.PROCESSOR_NULL);
                inv.replaceExistingItem(23, MenuUtils.PROCESSOR_NULL);
            }
            this.processor.endOperation(b);
        }
        else{
            if(inv.hasViewer()){
                this.processor.updateProgressBar(inv, 21, currentOperation);
                this.processor.updateProgressBar(inv, 23, currentOperation);
            }
            currentOperation.progress(1);
        }
    }
    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
        return new int[0];
    }
    public MachineRecipe getRecipe(BlockMenu inv){
        DataMenuClickHandler handler=getDataHolder(null,inv);
        int craftTypeIndex=handler.getInt(0);
        if(craftTypeIndex>=0){
            RecipeType type=getCraftTypes()[craftTypeIndex];
            List<MachineRecipe> recipes=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(type);
            int recipeIndex=handler.getInt(1);
            if(recipeIndex>=0&&recipeIndex<recipes.size()){
                return recipes.get(recipeIndex);
            }
        }
        return  null;
    }
    @Override
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) return getOutputSlots();
        int [] inputSlots=getInputSlots();
        if(item==null||item.getType().isAir()||(!(menu instanceof BlockMenu)))return inputSlots;
        MachineRecipe now=getRecipe((BlockMenu)menu);
        if(now==null){
            return new int[0];
        }
        int craftlimit=getCraftLimit(null,(BlockMenu)menu);
        int amountLimit=0;
        int maxStack=item.getMaxStackSize();
        ItemStack[] recipeInput=now.getInput();
        ItemCounter pusher=null;
        boolean hasOne=false;
        for (int i=0;i<recipeInput.length;++i){
            if(recipeInput[i].getType()==item.getType()&&recipeInput[i].hasItemMeta()==item.hasItemMeta()){
                if(!hasOne){
                    amountLimit=Math.max(recipeInput[i].getAmount()*craftlimit,maxStack);
                    hasOne=true;
                }else{
                    if(pusher==null){
                        //保证比较之前pusher非null，不用重复计算
                        pusher=CraftUtils.getCounter(item);
                    }

                    //由于StackMachineRecipe 材料已经被折叠过了 只需要找到一个匹配就行
                    if(CraftUtils.matchItemStack(recipeInput[i],pusher,false)){
                        amountLimit=Math.max(recipeInput[i].getAmount()*craftlimit,maxStack);
                        break;
                    }
                }
            }
        }
        // Check the current amount
        int itemAmount = 0;
        if(!hasOne){
            return inputSlots;
        }
        boolean hasItemMeta=item.hasItemMeta();
        for (int slot : inputSlots) {
            ItemStack itemInSlot = menu.getItemInSlot(slot);
            if(itemInSlot==null)continue;
            //出现类型匹配
            if (itemInSlot.getType()==item.getType()&&itemInSlot.hasItemMeta()==hasItemMeta) {
                //如果pusher!=null,说明上面出现了两个相同type 需要比较
                //如果比较不匹配 跳过
                if(pusher!=null&&hasItemMeta&&!CraftUtils.matchItemStack(itemInSlot ,pusher,false)){
                    continue;
                }
                itemAmount+=itemInSlot.getAmount();
                // Amount has reached the limited, just return.
                if(itemAmount>=amountLimit){
                    return new int[]{slot};
                }
            }
        }
        return inputSlots;
    }
    public List<ItemStack> _getDisplayRecipes(List<ItemStack> displayRecipe2){
        List<ItemStack> displayRecipe=new ArrayList<>(displayRecipe2);
        HashMap<SlimefunItem,RecipeType> providedRecipeTypeMap=getRecipeTypeMap();
        if(providedRecipeTypeMap!=null&&!providedRecipeTypeMap.isEmpty()){
            for(Map.Entry<SlimefunItem,RecipeType> e: providedRecipeTypeMap.entrySet()){
                if(BW_LIST.contains(e.getValue())){
                    continue;
                }
                displayRecipe.add(AddUtils.getInfoShow("&f支持的机器","&7将机器插入指定槽位以进行合成"));
                displayRecipe.add(new DisplayItemStack(e.getKey().getItem()));
            }
        }
        return displayRecipe;
    }


}
