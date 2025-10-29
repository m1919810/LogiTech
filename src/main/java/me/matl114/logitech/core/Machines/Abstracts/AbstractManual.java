package me.matl114.logitech.core.Machines.Abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.core.Interface.RecipeLock;
import me.matl114.logitech.core.Registries.FinalFeature;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.matl114.matlib.utils.inventory.itemStacks.CleanItemStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractManual extends AbstractMachine implements RecipeLock {

    protected static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53};
    protected static final int[] OUTPUT_SLOT = new int[] {27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    protected static final ItemStack BORDER=new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE," ");
//    protected static final ItemStack PREV=new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,"&3搜索上一个配方");
//    protected static final ItemStack NEXT=new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,"&3搜索下一个配方");
    protected static final ItemStack CRAFT_ONE=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&3单次合成","&6左键&b合成 &d1次 &b当前物品","&6右键&b合成 &d16次 &b当前物品");
    protected static final ItemStack CRAFT_MUL=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&3批量合成","&6左键&b合成 &d64次 &b当前物品","&6右键&b合成 &d3456次 &b当前物品");
    protected static ItemStack getMiddleItem(ItemStack stack,int index){
        if(stack==null)return DISPLAY_NOMATCH_MID;
        //trigger all Randomized Clone
        stack = stack.clone();
        var meta=stack.getItemMeta();
        List<String> lore=meta.getLore();
        lore=lore==null?new ArrayList<>():lore;
        if(index>=0){
            lore.add( "§8匹配的产物");
            lore.add("§8配方编号: " + index);
        }
        lore.add("");
        lore.add("§3shift点击一键收拾机器内物品");
        lore.add("§7将未堆满一组的相同物品合并");
        lore.add("§6shift+左键 §b按输入槽顺序收拾");
        lore.add("§6shift+右键 §b按输出槽顺序收拾");
        meta.setLore(lore);
        return new CleanItemStack(stack.getType(),stack.getAmount(),()->meta);
    }
    protected static ItemStack getPrevItem(ItemStack stack){
        if(stack==null)return DISPLAY_NOMATCH_PREV;
        stack = stack.clone();
        var meta=stack.getItemMeta();
        List<String> lore=meta.getLore();
        lore=lore==null?new ArrayList<>():lore;
        lore.add("§3点击搜索上一个配方");
        meta.setLore(lore);
        return new CleanItemStack(stack.getType(),stack.getAmount(),()->meta);
    }
    protected static ItemStack getNextItem(ItemStack stack){
        if(stack==null)return DISPLAY_NOMATCH_NEXT;
        stack = stack.clone();
        var meta=stack.getItemMeta();
        List<String> lore=meta.getLore();
        lore=lore==null?new ArrayList<>():lore;
        lore.add("§3点击搜索下一个配方");
        meta.setLore(lore);
        return new CleanItemStack(stack.getType(),stack.getAmount(),()->meta);
    }
    protected static final ItemStack DISPLAY_NOMATCH=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c没有匹配的配方");
    protected static final ItemStack DISPLAY_NOMATCH_MID=getMiddleItem(DISPLAY_NOMATCH,-1);
    protected static final ItemStack DISPLAY_NOMATCH_PREV=getPrevItem(DISPLAY_NOMATCH);
    protected static final ItemStack DISPLAY_NOMATCH_NEXT=getNextItem(DISPLAY_NOMATCH);
    protected static final int CRAFT_ONE_SLOT=40;
    protected static final int CRAFT_MUL_SLOT=49;
    protected static final int NEXT_SLOT=32;
    protected static final int MID_SLOT=31;
    protected static final int PREV_SLOT=30;
    private final String id;
    public final  int energybuffer;
    public final int energyConsumption;
    public final EnergyNetComponentType energyNetComponent;
    protected final ItemSetting<Boolean> considerPermission;
    public AbstractManual(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                int energybuffer, int energyConsumption,
                          List<Pair<Object,Integer>> customRecipes){
        super(category,item , recipeType, recipe,energybuffer,energyConsumption);
        this.energybuffer = energybuffer;
        this.energyConsumption = energyConsumption;
        this.energyNetComponent=(energybuffer==0)?(EnergyNetComponentType.NONE) :
                ((energyConsumption>0)?(EnergyNetComponentType.CONNECTOR):(EnergyNetComponentType.GENERATOR));
        this.id = item.getItemId();

        if(customRecipes!=null) {
            this.machineRecipes = new ArrayList<>(customRecipes.size());
            var customRecipes2 = AddUtils.buildRecipeMap(customRecipes);
            for(var recipePiece:customRecipes2){
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(MachineRecipeUtils.stackFromMachine(
                        new MachineRecipe(recipePiece.getSecondValue(),recipePiece.getFirstValue().getFirstValue(), recipePiece.getFirstValue().getSecondValue())
                ));
            }
        }else
        {
            this.machineRecipes=new ArrayList<>();

        }
        this.CRAFT_PROVIDER= FinalFeature.MANUAL_CARD_READER;
        this.setDisplayRecipes(Utils.list(
                FinalFeature.MANUAL_CARD_INFO,
                null
        ));
        this.considerPermission = create("check-recipe-permission", false);

    }
    public void addInfo(ItemStack item){
        if(this.energyConsumption > 0){
            item.setItemMeta(AddUtils.workBenchInfoAdd(item,this.energybuffer,this.energyConsumption).getItemMeta());
        }
    }

    public MachineRecipe getRecordRecipe(Location loc){
        return getMachineRecipes().get(getNowRecordRecipe(loc));
    }
    public MachineRecipe getRecordRecipe(SlimefunBlockData data){return getMachineRecipes().get(getNowRecordRecipe(data));}



    public void constructMenu(BlockMenuPreset preset){
//        preset.addItem(30,PREV);
//        preset.addItem(32,NEXT);
        //preset.addMenuClickHandler(31,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(39, BORDER, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(40,CRAFT_ONE);
        preset.addItem(41, BORDER, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(48, BORDER, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(49, CRAFT_MUL);
        preset.addItem(50, BORDER, ChestMenuUtils.getEmptyClickHandler());
    }
    public void updateMenu(BlockMenu inv,Block block,Settings mod){
        if(mod==Settings.INIT){
            orderSearchRecipe(inv,Settings.SEQUNTIAL, true);
        }else{
            Location  loc=inv.getLocation();
            MachineRecipe getRecipe=CraftUtils.matchNextRecipe(inv,getInputSlots(),getMachineRecipes(block,inv),true,Settings.SEQUNTIAL,CRAFT_PROVIDER);
            if(getRecipe==null){
                DataCache.setLastRecipe(loc,-1);
            }
        }
        Location  loc=inv.getLocation();
        int index= DataCache.getLastRecipe(loc);
        int indexRecord=getNowRecordRecipe(loc);
        if(index!=-1){
            if(index!=indexRecord || mod==Settings.INIT) {
                setNowRecordRecipe(loc,index);
            }

            int nextIndex = orderSearchRecipeFrom(inv,Settings.SEQUNTIAL, index, false);
            int prevIndex = orderSearchRecipeFrom(inv,Settings.REVERSE, index , false);
//            DataCache.setLastRecipe(loc,index);
            var rps=getMachineRecipes(block,inv);
            MachineRecipe getRecipe=rps.get(index);
            inv.replaceExistingItem(MID_SLOT, getMiddleItem(getRecipe.getOutput()[0],index));
            if(prevIndex<0){//valid index
                prevIndex = index;
            }
            getRecipe = rps.get(prevIndex);
            inv.replaceExistingItem(PREV_SLOT, getPrevItem(getRecipe.getOutput()[0]));
            if(nextIndex<0){//valid index
                nextIndex = prevIndex;
            }
            getRecipe = rps.get(nextIndex);
            inv.replaceExistingItem(NEXT_SLOT, getNextItem(getRecipe.getOutput()[0]));
        }
        else{
            if(indexRecord!=-1||mod==Settings.INIT){
               setNowRecordRecipe(loc,-1);
            }
            inv.replaceExistingItem(MID_SLOT,DISPLAY_NOMATCH_MID);
            inv.replaceExistingItem(PREV_SLOT,DISPLAY_NOMATCH_PREV);
            inv.replaceExistingItem(NEXT_SLOT,DISPLAY_NOMATCH_NEXT);
        }
    }
    public void newMenuInstance(BlockMenu menu,Block block){
        menu.addMenuOpeningHandler((player -> {
            AbstractManual.this.updateMenu(menu,block,Settings.RUN);
        }));
        menu.addMenuClickHandler(PREV_SLOT,
                (player, i, itemStack, clickAction)->{
                    if(getNowRecordRecipe(menu.getLocation())!=-1){
                    orderSearchRecipe(menu,Settings.REVERSE, true);
                    AbstractManual.this.updateMenu(menu,block,Settings.RUN);
                    }return false;
                }
                );
        menu.addMenuClickHandler(NEXT_SLOT,

                (player, i, itemStack, clickAction)->{
                    if(getNowRecordRecipe(menu.getLocation())!=-1){

                    orderSearchRecipe(menu,Settings.SEQUNTIAL, true);
                    AbstractManual.this.updateMenu(menu,block,Settings.RUN);

                    }return false;
                }
        );
        menu.addMenuClickHandler(CRAFT_ONE_SLOT,
                (player, i, itemStack, clickAction)->{
                    int limit=1;
                    if(clickAction.isRightClicked()){
                        limit=16;
                    }
                    if(this.energyConsumption>0){
                        int charge=this.getCharge(menu.getLocation())/this.energyConsumption;
                        limit=Math.min(limit,charge);
                        if(limit==0){
                            AddUtils.sendMessage(player,"&c电力不足!无法进行合成");
                        }
                    }
                    AbstractManual.this.updateMenu(menu,block,Settings.RUN);
                    craft(player,menu,limit);
                    AbstractManual.this.updateMenu(menu,block,Settings.RUN);
                    return false;
                }
        );
        menu.addMenuClickHandler(CRAFT_MUL_SLOT,
                (player, i, itemStack, clickAction)->{
                    int limit=64;
                    if(clickAction.isRightClicked()){
                        limit=3456;
                    }
                    if(this.energyConsumption>0){
                        int charge=this.getCharge(menu.getLocation())/this.energyConsumption;
                        limit=Math.min(limit,charge);
                        if(limit==0){
                            AddUtils.sendMessage(player,"&c电力不足!无法进行合成");
                        }
                    }
                    AbstractManual.this.updateMenu(menu,block,Settings.RUN);
                    craft(player,menu,limit);
                    AbstractManual.this.updateMenu(menu,block,Settings.RUN);
                    return false;
                }
        );
        menu.addMenuClickHandler(MID_SLOT,((player, i, itemStack, clickAction) -> {
            if(clickAction.isShiftClicked()){
                CraftUtils.stackUpSlots(menu,clickAction.isRightClicked()?getOutputSlots():getInputSlots(),true);
            }
            return false;
        }));
        //make sure your Recipe index renew in updateMenu
        updateMenu(menu,block,Settings.INIT);
    }

    public int orderSearchRecipe(BlockMenu inv, Settings order, boolean setHistory){
        if(inv == null)return-1;
        return orderSearchRecipeFrom(inv, order, DataCache.getLastRecipe(inv.getLocation()), setHistory);
    }
    public int orderSearchRecipeFrom(BlockMenu inv, Settings order, int index, boolean setHistory){
        if(inv!=null){
            if(index < 0){
                return -1;
            }
            int delta = switch (order) {
                case REVERSE -> -1;
                default -> 1;
            };
            List<MachineRecipe> mRecipe=getMachineRecipes(null,inv);
            if(mRecipe==null)return -1;
            index=index+delta;
            if(index < 0){
                index = mRecipe.size()-1;
            }
            else if(index >= mRecipe.size()){
                index = 0;
            }
            int selected = CraftUtils.matchNextRecipe(inv,getInputSlots(),mRecipe,index,order,CRAFT_PROVIDER);
            if(setHistory){
                Location  loc=inv.getLocation();
                DataCache.setLastRecipe(loc,selected);
            }
            return Math.min(selected, mRecipe.size() -1);
        }
        return -1;
    }
    public boolean preCraft(BlockMenu inv, Player player, boolean sendMessage){
        return true;
    }


    public void craft(Player player,BlockMenu inv,int limit){
        if(preCraft(inv,player,true)){
            Location  loc=inv.getLocation();
            int recordIndex=getNowRecordRecipe(loc);
            List<MachineRecipe> mRecipe=getMachineRecipes(null,inv);
            //没有匹配配方会直接返回失败
            if(recordIndex<0||recordIndex>=mRecipe.size()){
                return;
            }
            MachineRecipe recordRecipe=mRecipe.get(recordIndex);

            if(this.considerPermission.getValue() && CraftUtils.checkRecipePermission(player, recordRecipe, true)){
                return;
            }
            doCraft(inv, limit, recordRecipe);
        }
    }

    public void doCraft(BlockMenu inv, int limit, MachineRecipe recordRecipe){
        //计算电力
        Pair<ItemGreedyConsumer[],ItemGreedyConsumer[]> results=
            CraftUtils.countMultiRecipe(inv,getInputSlots(),getOutputSlots(),recordRecipe,limit,CRAFT_PROVIDER);
        //输出满了会返回null
        if(results==null){
            return;
        }
        if(this.energyConsumption>0){
            int craftTime=CraftUtils.calMaxCraftTime(results.getSecondValue(),limit);
            Location  loc=inv.getLocation();
            this.removeCharge(loc,craftTime*this.energyConsumption);
        }
        CraftUtils.multiUpdateInputMenu(results.getFirstValue(),inv);
        CraftUtils.multiUpdateOutputMenu(results.getSecondValue(),inv);
    }
    /**
     * manual machine main tick,check recipe and update data,
     * @param b
     * @param inv
     */
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        //only works when has viewer.
        if(inv!=null&&inv.hasViewer()){
            updateMenu(inv ,b,Settings.RUN);
        }
    }

    public void preRegister(){
        super.preRegister();
    }
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }

}
