package me.matl114.logitech.core.Machines.Abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.MachineRecipeUtils;
import me.matl114.logitech.Utils.UtilClass.ItemClass.DisplayItemStack;
import me.matl114.logitech.Utils.UtilClass.MenuClass.DataMenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.List;

public abstract  class AbstractTransformer extends AbstractMachine {
    //我们的目标是 最广的需求 最好的性能 最大的答辩(bushi
    /**
     * public tick stuff
     */

    public final int time;
    private int pubTick;

    protected int PROCESSOR_SLOT=22;
    public final ItemStack INFO_WORKING= new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&a工作中");
    public final ItemStack INFO_NULL= new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&6空闲中");
    public final int[] INPUT=new int[0];
    public ItemStack getWorkingItem(int tickLeft){
        return AddUtils.addLore(INFO_WORKING,AddUtils.concat("&7将在 ",String.valueOf(tickLeft)," tick后产出"));
    }
    public AbstractTransformer(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                             int time,  int energybuffer,int energyConsumption,
                               List<Pair<Object,Integer>> customRecipes){
        super(category,item , recipeType, recipe,energybuffer,energyConsumption);
        this.time = (time<=0)?1:time;
        this.pubTick = 0;

        if(customRecipes!=null){
            this.machineRecipes=new ArrayList<>(customRecipes.size());
            var tmp=AddUtils.buildRecipeMap(customRecipes);
            for(var recipePiece:tmp){
                //no need to stack and can not stack(maybe some shitmachine will stack
                //but we stack it in order to format up
                this.machineRecipes.add(MachineRecipeUtils.stackFromMachine(
                        new MachineRecipe(recipePiece.getSecondValue(),recipePiece.getFirstValue().getFirstValue(), recipePiece.getFirstValue().getSecondValue())
                ));
            }
        }

        else{
            this.machineRecipes=new ArrayList<>();
        }

    }
    public List<ItemStack> _getDisplayRecipes(List<ItemStack> list2) {
        List<ItemStack> list= super._getDisplayRecipes(list2);
        if(!list.isEmpty()&&list.get(0)==null){
            list.set(0,new DisplayItemStack(
                    new CustomItemStack(
                            Material.KNOWLEDGE_BOOK,
                            "&7速度",
                            "&7每 " + Integer.toString(time) + " 粘液刻生成一次"
                    )));
        }
        return list;
    }
    public void newMenuInstance(BlockMenu inv,Block b){
        inv.addMenuOpeningHandler((player -> {
            updateMenu(inv,b,Settings.RUN);
        }));
        inv.addMenuCloseHandler(player -> updateMenu(inv,b,Settings.RUN));
        updateMenu(inv,b,Settings.INIT);
    }
    public void updateMenu(BlockMenu inv,Block b,Settings mod){
        SlimefunBlockData data=DataCache.safeLoadBlock(inv.getLocation());
        if(data==null){
            Schedules.launchSchedules(()->updateMenu(inv,b,mod),20,false,0);
            return;
        }
        MachineRecipe rep=CraftUtils.matchNextRecipe(inv, getInputSlots(),getMachineRecipes(),
                //不许用高级的 你是多有病才能在不消耗的槽里面塞存储
                true, Settings.SEQUNTIAL,CraftUtils.getpusher);
        if(rep ==null){
            DataCache.setLastRecipe(inv.getLocation(),-1);
            return;
        }


    }
    public DataMenuClickHandler createDataHolder(){
        return new DataMenuClickHandler() {
            //0 为 数量 1 为 电力
            int[] tick=new int[2];
            public int getInt(int i){
                return tick[i];
            }
            public void setInt(int i, int val){
                tick[i]=val;
            }
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                return false;
            }
        };
    }
    public final int DATA_SLOT=0;
    public DataMenuClickHandler getDataHolder(Block b, BlockMenu inv){
        ChestMenu.MenuClickHandler handler=inv.getMenuClickHandler(DATA_SLOT);
        if(handler instanceof DataMenuClickHandler dh){return dh;}
        else{
            DataMenuClickHandler dh=createDataHolder();
            inv.addMenuClickHandler(DATA_SLOT,dh);
            updateMenu(inv,b,Settings.RUN);
            return dh;
        }
    }
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {
        DataMenuClickHandler dh=getDataHolder(b,menu);
        int tick=dh.getInt(0);
        boolean hasViewer=menu.hasViewer();
        if(hasViewer){
            updateMenu(menu,b,Settings.RUN);
        }
       if(conditionHandle(b,menu)){
           if(tick>=0&&hasViewer){
               menu.replaceExistingItem(this.PROCESSOR_SLOT,getWorkingItem(tick));
           }
           progressorCost(b,menu);
           if(tick<=0){
               //
               List<MachineRecipe> lst=getMachineRecipes(data);
               int len=lst.size();
               int index=DataCache.getLastRecipe(data);
               if(index>=0&& index<len){
                   MachineRecipe nextP= lst.get(index);

                   if (tick == 0){
                       int maxMultiple =dh.getInt(1);
                       if (maxMultiple == 1) {
                           CraftUtils.pushItems(nextP.getOutput(), menu, getOutputSlots(), CRAFT_PROVIDER);
                       } else {

                           CraftUtils.multiPushItems(nextP.getOutput(),menu, getOutputSlots(), maxMultiple, CRAFT_PROVIDER);
                       }
                    }
                   int maxCraftlimit= getCraftLimit(b,menu);
                   int tickNext=nextP.getTicks();
                   if(tickNext!=0){//超频机制
                       //尝试让time归1
                       //按比例减少maxlimit ,按最小值取craftlimit
                       if(maxCraftlimit<=tickNext){
                           tickNext=( (tickNext+1)/maxCraftlimit)-1;
                           maxCraftlimit=1;
                       }else {
                           maxCraftlimit=(maxCraftlimit/(tickNext));
                           tickNext=0;
                       }
                   }
                   dh.setInt(1,maxCraftlimit);
                   dh.setInt(0,tickNext);
               }else if(index!=-1){
                   updateMenu(menu,b,Settings.RUN);
               }else if(hasViewer) {
                   menu.replaceExistingItem(this.PROCESSOR_SLOT,this.INFO_NULL);
               }
           }else{
               dh.setInt(0,tick-1);
           }

        }else if (tick!=-1){
           dh.setInt(0,-1);
           if(hasViewer)
                menu.replaceExistingItem(this.PROCESSOR_SLOT,this.INFO_NULL);
        }
    }
    public int getCraftLimit(Block b, BlockMenu inv){
        return 1;
    }
    public final void process(Block block, BlockMenu inv, SlimefunBlockData data){


    }
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
        return flow == ItemTransportFlow.INSERT ? this.INPUT : this.getOutputSlots();
    }
    public boolean isSync(){
        return false;
    }

}
