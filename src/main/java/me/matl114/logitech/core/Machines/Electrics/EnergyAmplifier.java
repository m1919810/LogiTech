package me.matl114.logitech.core.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.core.AddItem;
import me.matl114.logitech.core.Interface.MenuTogglableBlock;
import me.matl114.logitech.core.Machines.Abstracts.AbstractEnergyMachine;
import me.matl114.logitech.core.Machines.Abstracts.AbstractEnergyProvider;
import me.matl114.logitech.core.Registries.FinalFeature;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemPusherProvider;
import me.matl114.logitech.utils.UtilClass.MenuClass.DataMenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class EnergyAmplifier extends AbstractEnergyProvider implements MenuTogglableBlock {
    protected final int[] NULL_SLOT=new int[0];
    public int[] getInputSlots(){
        return NULL_SLOT;
    }
    public int[] getOutputSlots(){
        return NULL_SLOT;
    }
    protected final int[] BORDER=new int[]{
            0,1,2,3,5,6,7,9,10,11,12,14,15,16,17,18,19,20,21,22,23,24,25,26
    };
    protected final int RUNWHENFULL_SLOT=8;
    protected final int STATUS_SLOT=4;
    protected final int MACHINE_SLOT=13;
    protected final ItemStack STATUS_ITEM_OFF=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&c当前状态: 未发电");

    protected ItemPusherProvider MACHINE_PROVIDER= FinalFeature.STORAGE_READER;
    protected double multiply;
    protected final ItemStack RUNWHENFULL_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6过载模式: &c关","&7点击切换过载模式",
            "&7当过载模式启用时候,即使发电机存电量已满仍旧会发电");
    protected final ItemStack RUNWHENFULL_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6过载模式: &a开","&7点击切换过载模式",
            "&7当过载模式启用时候,即使发电机存电量已满仍旧会发电");
    public EnergyAmplifier(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                  int energyBuffer,double multiply) {
        super(category, item, recipeType, recipe,   energyBuffer,1);
        this.multiply=multiply;
        this.setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow("&f机制 - &c终极堆叠发电",
                        "&7该机器可以模拟其上方的发电机发电",
                        "&7在槽位中放入与&e该机器方块上方方块&7的&a发电机&7类型相同的粘液物品",
                        "&a注意:你可以将发电机装入存储类物品放入该槽位",
                        "&a机器将读取存储中的发电机类型和数目",
                        "&7该机器将会发电<槽位中读取的机器数>*%.2f*<上方环境中发电机的发电量>J".formatted(this.multiply),
                        "&a该槽位中支持的存储类物品与&c\"终极合成\"&a特性中的存储类物品相同"),null,
                AddUtils.getInfoShow("&f示例",
                        "&7右侧是一个示意图,讲述了该如何使用该机器"),AddUtils.resolveItem("ENERGY_REGULATOR"), AddItem.OPPO_GEN,
                item,null,AddUtils.getInfoShow("&f说明","&7你将会看见下方的机器发电")
        ));
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(STATUS_SLOT,getStatusItem(0,0,0),ChestMenuUtils.getEmptyClickHandler());
    }
    public ItemStack getStatusItem(int energyProvide,int charge,int stackNum){
        if(stackNum==0){
            return STATUS_ITEM_OFF;
        }else {
            if(charge>this.energybuffer){
                return new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&a当前状态: 发电中",
                        "&7当前单体发电: 电力缓存已满!"
                        ,"&7当前电机数目: %dx".formatted(stackNum),
                        "&7当前工作效率: %.3f".formatted(multiply),
                        "&7当前发电速率: 电力缓存已满!",
                        "&7当前电力存储: %dJ".formatted(charge));
            }else {
                return new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&a当前状态: 发电中",
                        "&7当前单体发电: %dJ/t".formatted(energyProvide)
                        ,"&7当前电机数目: %dx".formatted(stackNum),
                        "&7当前工作效率: %.3f".formatted(multiply),
                        "&7当前发电速率: %dJ/t".formatted((int)(stackNum*energyProvide*multiply)),
                        "&7当前电力存储: %dJ".formatted(charge));
            }
        }
    }
    public void registerTick(SlimefunItem item){
        item.addItemHandler(
            new BlockTicker() {
                public boolean isSynchronized() {
                    return false;
                }

                @ParametersAreNonnullByDefault
                public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
                    BlockMenu menu = data.getBlockMenu();
                    if(menu!=null){
                        if(menu.hasViewer()){
                            updateMenu(menu,b,Settings.RUN);
                        }
                    }
                }
            }
        );
    }
    public  int getGeneratedOutput(@Nonnull Location l, @Nonnull SlimefunBlockData data){
        BlockMenu inv=data.getBlockMenu();
        if(inv==null)return 0;
        DataMenuClickHandler dh=getDataHolder(null,inv);
        Object sf=dh.getObject(0);
        if(sf instanceof EnergyNetProvider ep&&!(sf instanceof EnergyAmplifier)&&!(sf instanceof AbstractEnergyMachine)){
            Location loc2=l.clone().add(0,1,0);
            if(DataCache.getSfItem(loc2) == ep){
                SlimefunBlockData data2=DataCache.safeLoadBlock(loc2);
                if(data2==null){
                    return 0;
                }
                int charge = this.getCharge(l);
                int amplify=dh.getInt(0);
                if(!getStatus(inv)[0]&&charge>=this.energybuffer){
                    if(inv.hasViewer()){
                        inv.replaceExistingItem(STATUS_SLOT,getStatusItem(0,charge,amplify));
                    }
                    return 0;
                }else {
                    int energyOutput=ep.getGeneratedOutput(loc2,data2);
                    if(inv.hasViewer()){
                        inv.replaceExistingItem(STATUS_SLOT,getStatusItem(energyOutput,charge,amplify));
                    }
                    long result=energyOutput;
                    result=(long)(result*amplify*multiply);
                    return MathUtils.fromLong(result);
                }
            }
        }
        if(inv.hasViewer()){
            int charge=this.getCharge(l);
            inv.replaceExistingItem(STATUS_SLOT,getStatusItem(0,charge,0));
        }
        return 0;
    }
    public final int DATA_SLOT=0;
    public DataMenuClickHandler createDataHolder(){
        return new DataMenuClickHandler() {
            //0 为 数量 1 为 电力
            Object it=null;
            int t;
            public void setInt(int val1,int val2){
                t=val2;
            }
            public int getInt(int val1){
                return t;
            }
            public void setObject(int val1,Object val2){
                it=val2;
            }
            public Object getObject(int val2){
                return it;
            }
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                return false;
            }
        };
    }
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
    public void newMenuInstance(BlockMenu inv, Block b){
        inv.addMenuOpeningHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });
        inv.addMenuCloseHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });
        ItemStack stack=inv.getItemInSlot(RUNWHENFULL_SLOT);
        inv.addMenuClickHandler(RUNWHENFULL_SLOT,((player, i, itemStack, clickAction) -> {
            boolean t=getStatus(inv)[0];
            toggleStatus(inv,!t);
            return false;
        }));

        if(stack==null||stack.getType()!=Material.RED_STAINED_GLASS_PANE){
            inv.replaceExistingItem(RUNWHENFULL_SLOT,RUNWHENFULL_ON);
        }else {
            inv.replaceExistingItem(RUNWHENFULL_SLOT,RUNWHENFULL_OFF);
        }
        updateMenu(inv,b,Settings.INIT);
    }
    public void updateMenu(BlockMenu inv, Block b, Settings mod){
        ItemPusher pusher=MACHINE_PROVIDER.getPusher(Settings.INPUT,inv,MACHINE_SLOT);
        DataMenuClickHandler dh=getDataHolder(b,inv);
        if(pusher!=null){
            SlimefunItem sfit=SlimefunItem.getByItem(pusher.getItem());
            if(sfit!=null){
                if(sfit instanceof EnergyNetProvider ep){
                    dh.setInt(0,pusher.getAmount());
                    dh.setObject(0,ep);
                    return;
                }
            }
        }
        dh.setInt(0,0);
        dh.setObject(0,null);
    }
    public void onBreak(BlockBreakEvent e,BlockMenu inv){
        super.onBreak(e,inv);
        if(inv!=null){
            inv.dropItems(inv.getLocation(),MACHINE_SLOT);
        }
    }

    @Override
    public boolean[] getStatus(BlockMenu inv) {
        ItemStack itemStack=inv.getItemInSlot(RUNWHENFULL_SLOT);
        if(itemStack==null||itemStack.getType()!=Material.RED_STAINED_GLASS_PANE){
            return new boolean[]{true};
        }else {
            return new boolean[]{false};
        }
    }
    @Override
    public void toggleStatus(BlockMenu inv, boolean... result) {
        if(result[0]){
            inv.replaceExistingItem(RUNWHENFULL_SLOT,RUNWHENFULL_ON);
        }else {
            inv.replaceExistingItem(RUNWHENFULL_SLOT,RUNWHENFULL_OFF);
        }
    }
}
