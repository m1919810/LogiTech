package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.Utils.UtilClass.MenuClass.CustomMenu;
import me.matl114.logitech.Utils.UtilClass.MenuClass.MenuFactory;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorkBench extends AbstractMachine {
    public List<MachineRecipe> machineRecipes;
    protected final int CRAFT_LIMIT;

    protected int getRecipeMenuSlot(){
        return -1;
    }
    protected static final ItemStack LAZY_ONECLICK=new CustomItemStack(Material.KNOWLEDGE_BOOK,"&b使用物品栏中的物品摆放配方","&7左键放入一份配方","&7右键放入64份配方","&a欢呼吧 懒狗们");
    protected static final ItemStack RECIPEBOOK_SHOW_ITEM=new CustomItemStack(Material.BOOK,"&6点击查看配方","","&a而且有一键放置配方的功能","&b欢呼吧 懒狗们");

    public AbstractWorkBench(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                            int energybuffer, int energyConsumption,int limit, LinkedHashMap<Object,Integer> shapedRecipes){
        super(category,item,recipeType,recipe,energybuffer,energyConsumption);
        this.CRAFT_LIMIT=limit;
        if(shapedRecipes != null){
            machineRecipes = new ArrayList<>(shapedRecipes.size());
            var customRecipes2 = AddUtils.buildRecipeMap(shapedRecipes);
            for (Map.Entry<Pair<ItemStack[], ItemStack[]>, Integer> recipePiece : customRecipes2.entrySet()) {
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(new MachineRecipe(recipePiece.getValue(), recipePiece.getKey().getFirstValue(), recipePiece.getKey().getSecondValue()));
            }
        }else{
            machineRecipes = new ArrayList<>();
        }

    }
    public void addInfo(ItemStack item){
        item.setItemMeta(AddUtils.advancedMachineShow(item,CRAFT_LIMIT).getItemMeta());
        if(this.energyConsumption > 0){
            item.setItemMeta(AddUtils.workBenchInfoAdd(item,this.energybuffer,this.energyConsumption).getItemMeta());
        }
    }
    public List<MachineRecipe> provideDisplayRecipe(){
        List<MachineRecipe> machineRecipes =  getMachineRecipes();
        List<MachineRecipe> targetRecipe = new ArrayList<>();
        int size=machineRecipes.size();
        for (int i=0;i<size;++i) {
            targetRecipe.add(MachineRecipeUtils.stackFrom(machineRecipes.get(i).getTicks(),
                    Utils.array(AddUtils.getInfoShow("&f有序配方合成","&7请在配方显示界面或者机器界面查看")),machineRecipes.get(i).getOutput()));
        }
        return targetRecipe;
    }
    /**
     * construct your menu here.called in constructor
     * @param preset
     */
    public abstract void constructMenu(BlockMenuPreset preset);
    public MenuFactory getRecipeMenu(Block b,BlockMenu inv){

         return MenuUtils.createMRecipeListDisplay(getItem(),getMachineRecipes(),null,(MenuUtils.RecipeMenuConstructor)(itemstack, recipes, backhandler,history)->{
            return MenuUtils.createMRecipeDisplay(itemstack,recipes,backhandler).addOverrides(8,LAZY_ONECLICK).addHandler(8,((player, i, itemStack, clickAction) -> {
                moveRecipe(player,inv,recipes, clickAction.isRightClicked());
                return false;
            }));
        });

//        return MenuUtils.createMRecipeListDisplay(getItem(),getMachineRecipes(),((player, i, itemStack, clickAction) -> {
//            inv.open(player);
//            return false;
//        }),(itemstack,recipes,backhandler)->{
//            return MenuUtils.createMRecipeDisplay(itemstack,recipes,backhandler).overrideItem(8,LAZY_ONECLICK).overrideHandler(8,((player, i, itemStack, clickAction) -> {
//                moveRecipe(player,inv,recipes, clickAction.isRightClicked());
//                return false;
//            }));
//        });
    }
    public int getCraftLimit(Block b,BlockMenu inv){
        return CRAFT_LIMIT;
    }
    /**
     * cargo and IO
     * @return
     */
    public abstract int[] getInputSlots();

    /**
     * cargo and IO
     * @return
     */
    public abstract int[] getOutputSlots();

    public abstract int[] getRecipeSlots();
    public List<MachineRecipe> getMachineRecipes(){
        return machineRecipes;
    }
    public void registerTick(SlimefunItem item){
        //no ticker
    }
    public boolean conditionHandle(Block b,BlockMenu menu){
        //do nothing
        return true;
    }
    protected void progressorCost(Block b, BlockMenu menu) {
        //do nothing
    }
    public void craft(Block b,BlockMenu inv,Player player){
        Location loc=inv.getLocation();
        int charge=getCharge(inv.getLocation());
        int limit;
        if(this.energyConsumption > 0){
            int chargelimit=(charge/this.energyConsumption);
            if(chargelimit==0){
                AddUtils.sendMessage(player,AddUtils.concat("&c电力不足或条件不足! ",String.valueOf(charge),"J/ ",String.valueOf( this.energyConsumption),"J"));
                return;
            }
            limit=Math.min(chargelimit,getCraftLimit(b,inv));
        }else {
            limit=getCraftLimit(b,inv);
        }
        if(limit == 0){
            AddUtils.sendMessage(player,"&c合成失败,请检查合成条件是否均具备");
            return;
        }
        Pair<MachineRecipe, ItemGreedyConsumer[]> outputResult=
                CraftUtils.findNextShapedRecipe(inv,getInputSlots(),getOutputSlots(),getMachineRecipes(),
                        limit,true,Settings.SEQUNTIAL);
        if(outputResult != null){
            if(this.energyConsumption > 0){
                int craftTime=outputResult.getSecondValue()[0].getStackNum();
                removeCharge(loc,craftTime*this.energyConsumption);
            }
            CraftUtils.multiUpdateOutputMenu(outputResult.getSecondValue(),inv);
        }
    }

    public void process(Block b, BlockMenu preset, SlimefunBlockData data){

    }
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow){
        return flow==ItemTransportFlow.WITHDRAW?getOutputSlots():getInputSlots();
    }
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
        if(flow==ItemTransportFlow.WITHDRAW){
            return getOutputSlots();
        }else if(item==null||item.getMaxStackSize()<=1){
            return getInputSlots();
        }
        else{
            List<Integer> input_slots=new ArrayList<Integer>();
            int[] input=getInputSlots();
            for (int i=0;i<input.length;i++){
                if(menu.getItemInSlot(input[i])!=null){
                    input_slots.add(input[i]);
                }
            }
            int[] array = new int[input_slots.size()];

            for (int i = 0; i < input_slots.size(); i++) {
                array[i] = input_slots.get(i);
            }
            return array;

        }
    }
    //modified from INFINITY EXPANSION
    private void moveRecipe(@Nonnull Player player, @Nonnull BlockMenu menu, MachineRecipe machinerecipe, boolean max) {
        ItemStack[] recipe =machinerecipe.getInput();
        PlayerInventory inv = player.getInventory();
        int[] inputs=getRecipeSlots();
        int maxCnt=max?64:1;
        if(recipe.length>inputs.length){
            AddUtils.sendMessage(player,"&c配方大小不匹配,不能在该工作台进行");
            player.closeInventory();
            return;
        }
        ItemConsumer[] itcs=new ItemConsumer[recipe.length];
        ItemStack[] playerInv =inv.getContents();
        ItemPusher[] itps=new ItemPusher[recipe.length];
        ItemPusher[] ips=new ItemPusher[playerInv.length];
        for (int s=0;s<playerInv.length;++s) { //each slot in their inv\
            ips[s]=CraftUtils.getPusher(playerInv[s]);
        }
        boolean[] hasInit=new boolean[recipe.length];
        boolean[] match=new boolean[recipe.length];
        for (int i = 0 ; i < maxCnt; i++) {
            for (int slot = 0 ; slot < recipe.length ; slot++) { //each item in recipe
                boolean firstTime=false;
                if(!hasInit[slot]){
                    itcs[slot]=CraftUtils.getConsumer(recipe[slot]);
                    hasInit[slot]=true;
                    firstTime=true;
                }
                if (itcs[slot] == null) {
                    continue;
                }
                // 重置consumer计数器
                itcs[slot].syncAmount();
                int recipeCount=itcs[slot].getAmount();
                    //获取当前槽位的东西
                if(firstTime){
                    itps[slot]=CraftUtils.getpusher.getPusher(Settings.OUTPUT,menu,inputs[slot]);
                    //不匹配，即非空且不match，就没有下次了
                    if(!itps[slot].isNull()&&!CraftUtils.matchItemCounter(itps[slot],itcs[slot],false)) {
                        continue;
                    }else{//匹配，设置为consumer的
                        match[slot]=true;
                        itps[slot].setFrom(itcs[slot]);
                    }
                }else if(!match[slot]){
                    continue;
                }
                for (int s=0;s<playerInv.length;++s) { //each slot in their inv\
                    if(ips[s]==null||ips[s].getAmount()==0){
                        continue;
                    }//空和为0 直接早退
                    else if ( CraftUtils.matchItemCounter(ips[s],itcs[slot],false)) { //matches recipe
                        //consume,
                        itcs[slot].consume(ips[s]);
                        if(itcs[slot].getAmount()<=0){
                            break;
                        }
                    }
                }
                //try safe push
                //能全放下
                if(itcs[slot].getAmount()<=0&&itps[slot].safeAddAmount(recipeCount)){//push的数量
                    //真的有地方,同步背包里的相关counter
                    itcs[slot].updateItems(menu,Settings.PUSH);
                }else{
                    //放不进去,返回z物品
                    itcs[slot].syncItems();
                }
            }//提前中断,意味着没有后面的了
        }
        for(int i=0;i<recipe.length;++i){
            if(itps[i]!=null){
                itps[i].updateMenu(menu);
            }
        }
        menu.open(player);
    }

}
