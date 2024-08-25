package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.SlimefunItem.Machines.*;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ImportRecipes;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.MultiCraftingOperation;
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
import java.util.LinkedHashMap;
import java.util.List;

public class AdvanceRecipeCrafter extends AbstractAdvancedProcessor implements RecipeLock, ImportRecipes {
    protected final int[] BORDER={
            3,5,12,14,21,22,23
    };
    protected   final int[] RECIPE_DISPLAY={
            30,31,32,39,40,41,48,49,50
    };
    protected  final int RECIPEITEM_SLOT=13;
    protected int PARSE_SLOT=4;
    protected  final ItemStack PARSE_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&e点击解析配方",
            "&b机制:","&6将配方表输出的物品(若有多个则第一个)置于下方","&6右键该槽位,或者开关容器界面,配方将被解析","&6机器将按照解析出的指定配方合成");
    protected  final ItemStack DISPLAY_BKGROUND=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE," ");
    protected  final ItemStack DISPLAY_DEFAULT_BKGROUND=new CustomItemStack(Material.RED_STAINED_GLASS_PANE," ");
    protected final ItemStack progressItem;
    protected final RecipeType[] craftType;
    protected final int publicTime;
    public AdvanceRecipeCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                Material processbar, int energyConsumption, int energyBuffer,
                                LinkedHashMap<Object, Integer> customRecipes)  {
        super(category,item,recipeType,recipe,processbar,energyConsumption,energyBuffer,customRecipes);
        this.craftType=null;
        this.publicTime=0;
        this.progressItem = new ItemStack(processbar);
    }

    public AdvanceRecipeCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                Material processbar, int energyConsumption, int energyBuffer,int ticks,
                                RecipeType... craftType)  {
        super(category,item,recipeType,recipe,processbar,energyConsumption,energyBuffer,null);
        this.craftType=craftType;
        this.publicTime=ticks;
        this.progressItem = new ItemStack(processbar);
        SchedulePostRegister.addPostRegisterTask(()->
            getDisplayRecipes()
        );
    }
    public List<MachineRecipe> getMachineRecipes() {
        if(this.machineRecipes == null||this.craftType!=null) {
            if(this.machineRecipes==null) {
                this.machineRecipes = new ArrayList<>();
            }
            if(publicTime==0){
                if(this.craftType.length<=0){
                    return new ArrayList<>();
                }
                else {

                    this.machineRecipes=new ArrayList<>();
                    for(RecipeType rt : this.craftType){
                        if(rt!=null){
                            List<MachineRecipe> rep=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(rt);
                            if(rep==null)rep=new ArrayList<>();
                            this.machineRecipes.addAll(rep);
                        }
                    }
                }
                //reset ticks to ...
            }else{
                //@TODO new MachineRecipe to reset ticks
                this.machineRecipes=new ArrayList<>();
                for(int i=0;i<this.craftType.length;i++){
                    RecipeType rt = this.craftType[i];
                    if(rt!=null){
                        List<MachineRecipe> rep=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(rt);
                        if(rep==null)rep=new ArrayList<>();
                        this.machineRecipes.addAll(rep);
                    }
                }
            }
        }
        return this.machineRecipes;
    }
    public ItemStack getProgressBar(){
        return this.progressItem;
    }
    public MachineRecipe getRecordRecipe(SlimefunBlockData data){
        int index=getNowRecordRecipe(data);
        List<MachineRecipe> recipes=getMachineRecipes();
        if(index>=recipes.size()){//越界
            setNowRecordRecipe(data,-1);
            return null;
        }
        if(index<0)return null;
       else return recipes.get(index);

    }
   public void constructMenu(BlockMenuPreset preset) {
        //空白背景 禁止点击
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        //输入槽边框
        border = BORDER_SLOT;
        len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(22,MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
        border = RECIPE_DISPLAY;
        len = border.length;
        //emptyhandler
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addMenuClickHandler(border[var4], ChestMenuUtils.getEmptyClickHandler());
        }
    }
    public void newMenuInstance(BlockMenu menu,Block block){
        menu.replaceExistingItem(PARSE_SLOT,PARSE_ITEM);
        menu.addMenuClickHandler(PARSE_SLOT,(player, i, itemStack, clickAction)->{
            parseRecipe(menu);
            updateMenu(menu,block,Settings.RUN);
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
    }
    public void parseRecipe(BlockMenu menu){
        ItemStack target=menu.getItemInSlot(RECIPEITEM_SLOT);
        if(target==null||target.getType()==Material.AIR){
            setNowRecordRecipe(menu.getLocation(),-1);
        }else{
            List<MachineRecipe> machineRecipes1=getMachineRecipes();
            for(int i=0;i<machineRecipes1.size();++i){
                MachineRecipe machineRecipe=machineRecipes1.get(i);
                if(CraftUtils.matchItemStack(target,machineRecipe.getOutput()[0], false)){
                    setNowRecordRecipe(menu.getLocation(),i);
                    return ;
                }
            }
            setNowRecordRecipe(menu.getLocation(),-1);
            return;
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
    public int getCraftLimit(Block b,BlockMenu inv){
        return 1;
    }
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){

        MultiCraftingOperation currentOperation = this.processor.getOperation(b);
        ItemGreedyConsumer[] fastCraft=null;
        if(currentOperation==null){
            ItemStack tmp=inv.getItemInSlot(RECIPEITEM_SLOT);
            if(tmp==null||tmp.getType()==Material.AIR){
                setNowRecordRecipe(inv.getLocation(),-1);
                updateMenu(inv,b,Settings.RUN);
            }
            MachineRecipe next=getRecordRecipe(data);
            if(next==null){

                if(inv.hasViewer()){inv.replaceExistingItem(22, MenuUtils.PROCESSOR_NULL);
                }
                return ;
            }

            Pair<ItemGreedyConsumer[],ItemGreedyConsumer[]> nextP=CraftUtils.countMultiRecipe(inv,getInputSlots(),
                    getOutputSlots(),next,getCraftLimit(b,inv),CRAFT_PROVIDER);

            if (nextP != null) {

                    CraftUtils.multiUpdateInputMenu(nextP.getFirstValue(),inv);
                    int ticks=this.publicTime;
                if(ticks>0){

                    currentOperation = new MultiCraftingOperation(nextP.getSecondValue(),ticks);
                    this.processor.startOperation(b, currentOperation);
                }
                else{
                    fastCraft = nextP.getSecondValue();
                }
            }else{//if currentOperation ==null return  , cant find nextRecipe

                    if(inv.hasViewer()){inv.replaceExistingItem(22, MenuUtils.PROCESSOR_NULL);
                    }
                return ;
            }
        }
        progressorCost(b,inv);
        if (fastCraft!=null) {
            CraftUtils.multiUpdateOutputMenu(fastCraft,inv);
        }else if(currentOperation.isFinished()){
            ItemGreedyConsumer[] var4=currentOperation.getResults();
            int var5 = var4.length;
            CraftUtils.multiForcePush(var4,inv,getOutputSlots(),CRAFT_PROVIDER);
            if(inv.hasViewer()){inv.replaceExistingItem(22, MenuUtils.PROCESSOR_NULL);
            }
            this.processor.endOperation(b);
        }
        else{
            if(inv.hasViewer()){
                this.processor.updateProgressBar(inv, 22, currentOperation);

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
        int craftlimit=getCraftLimit(null,null);
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
}
