package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.Machines.ManualMachines.FinalManual;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusherProvider;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class AbstractManual extends AbstractMachine implements  RecipeLock {

    protected static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53};
    protected static final int[] OUTPUT_SLOT = new int[] {27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    protected static final ItemStack BORDER=new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE," ");
    protected static final ItemStack PREV=new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,"&3搜索上一个配方");
    protected static final ItemStack NEXT=new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,"&3搜索下一个配方");
    protected static final ItemStack CRAFT_ONE=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&3单次合成","&6左键&b合成 &d1次 &b当前物品","&6右键&b合成 &d16次 &b当前物品");
    protected static final ItemStack CRAFT_MUL=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&3批量合成","&6左键&b合成 &d64次 &b当前物品","&6右键&b合成 &d3456次 &b当前物品");
    protected static final ItemStack DISPLAY_NOMATCH=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c没有匹配的配方");
    protected static final int CRAFT_ONE_SLOT=40;
    protected static final int CRAFT_MUL_SLOT=49;
    protected static final int NEXT_SLOT=32;
    protected static final int PREV_SLOT=30;
    private final String id;
    public final  int energybuffer;
    public final int energyConsumption;
    public final EnergyNetComponentType energyNetComponent;
    public AbstractManual(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                int energybuffer, int energyConsumption,
                          LinkedHashMap<Object, Integer> customRecipes){
        super(category,item , recipeType, recipe,energybuffer,energyConsumption);
        this.energybuffer = energybuffer;
        this.energyConsumption = energyConsumption;
        this.energyNetComponent=(energybuffer==0)?(EnergyNetComponentType.NONE) :
                ((energyConsumption>0)?(EnergyNetComponentType.CONNECTOR):(EnergyNetComponentType.GENERATOR));
        this.id = item.getItemId();

        if(customRecipes!=null) {
            this.machineRecipes = new ArrayList<>(customRecipes.size());
            var customRecipes2 = AddUtils.buildRecipeMap(customRecipes);
            for (Map.Entry<Pair<ItemStack[], ItemStack[]>, Integer> recipePiece : customRecipes2.entrySet()) {
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(MachineRecipeUtils.stackFromMachine(
                        new MachineRecipe(recipePiece.getValue(), recipePiece.getKey().getFirstValue(), recipePiece.getKey().getSecondValue())
                ));
            }
        }else
        {
            this.machineRecipes=new ArrayList<>();

        }

    }
    public void addInfo(ItemStack item){
        if(this.energyConsumption > 0){
            item.setItemMeta(AddUtils.workBenchInfoAdd(item,this.energybuffer,this.energyConsumption).getItemMeta());
        }
    }
    public List<MachineRecipe> getMachineRecipes(){
        return machineRecipes;
    }

    public MachineRecipe getRecordRecipe(Location loc){
        return getMachineRecipes().get(getNowRecordRecipe(loc));
    }
    public MachineRecipe getRecordRecipe(SlimefunBlockData data){return getMachineRecipes().get(getNowRecordRecipe(data));}
    public void constructMenu(BlockMenuPreset preset){
        preset.addItem(30,PREV);
        preset.addItem(32,NEXT);
        preset.addMenuClickHandler(31,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(39, BORDER, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(40,CRAFT_ONE);
        preset.addItem(41, BORDER, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(48, BORDER, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(49, CRAFT_MUL);
        preset.addItem(50, BORDER, ChestMenuUtils.getEmptyClickHandler());
    }
    public void updateMenu(BlockMenu inv,Block block,Settings mod){
        if(mod==Settings.INIT){
            orderSearchRecipe(inv,Settings.SEQUNTIAL);
        }
        Location  loc=inv.getLocation();
        int index= DataCache.getLastRecipe(loc);
        int indexRecord=getNowRecordRecipe(loc);
        if(index!=-1){
            MachineRecipe getRecipe=getMachineRecipes(block,inv).get(index);
            inv.replaceExistingItem(31, AddUtils.addLore(getRecipe.getOutput()[0],
                    "&8匹配的产物", "&8若有多输出则显示第一个", "&8配方编号: " + index));
            if(index!=indexRecord||mod==Settings.INIT) {


               setNowRecordRecipe(loc,index);
            }
        }
        else{
            if(indexRecord!=-1||mod==Settings.INIT){
                inv.replaceExistingItem(31,DISPLAY_NOMATCH);
               setNowRecordRecipe(loc,-1);
            }
        }
    }
    public void newMenuInstance(BlockMenu menu,Block block){
        menu.addMenuOpeningHandler((player -> {
            AbstractManual.this.updateMenu(menu,block,Settings.RUN);
        }));
        menu.addMenuClickHandler(PREV_SLOT,
                (player, i, itemStack, clickAction)->{
                    if(getNowRecordRecipe(menu.getLocation())!=-1){
                    orderSearchRecipe(menu,Settings.REVERSE);
                    AbstractManual.this.updateMenu(menu,block,Settings.RUN);
                    }return false;
                }
                );
        menu.addMenuClickHandler(NEXT_SLOT,

                (player, i, itemStack, clickAction)->{
                    if(getNowRecordRecipe(menu.getLocation())!=-1){
                    orderSearchRecipe(menu,Settings.SEQUNTIAL);
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
                    craft(menu,limit);
                    AbstractManual.this.tick(block,menu,1);
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
                    craft(menu,limit);
                    AbstractManual.this.tick(block,menu,1);
                    return false;
                }
        );
        //make sure your Recipe index renew in updateMenu
        updateMenu(menu,block,Settings.INIT);
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
            if(CraftUtils.matchNextRecipe(inv,getInputSlots(),mRecipe,true,order)==null){
                DataCache.setLastRecipe(loc,-1);
            }
        }
    }


    //FIXME 面对不同物品属于同一物品组 搜索成功但合成失败
    public void craft(BlockMenu inv,int limit){
        Location  loc=inv.getLocation();

        int recordIndex=getNowRecordRecipe(loc);
        List<MachineRecipe> mRecipe=getMachineRecipes(null,inv);
        //没有匹配配方会直接返回失败
        if(recordIndex<0||recordIndex>=mRecipe.size()){
            return;
        }
        MachineRecipe recordRecipe=mRecipe.get(recordIndex);
        //计算电力

        Pair<ItemGreedyConsumer[],ItemGreedyConsumer[]> results=
                CraftUtils.countMultiRecipe(inv,getInputSlots(),getOutputSlots(),recordRecipe,limit,CRAFT_PROVIDER);
        //输出满了会返回null
        if(results==null){
            return;
        }
        if(this.energyConsumption>0){
            int craftTime=CraftUtils.calMaxCraftTime(results.getSecondValue(),limit);
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
            Location  loc=inv.getLocation();
            MachineRecipe getRecipe=CraftUtils.matchNextRecipe(inv,getInputSlots(),getMachineRecipes(b,inv),true,Settings.SEQUNTIAL);
            if(getRecipe==null){
                DataCache.setLastRecipe(loc,-1);
            }
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
