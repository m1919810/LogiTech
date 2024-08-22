package me.matl114.logitech.SlimefunItem.Cargo.CargoMachine;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.Cargo.Config.CargoConfigCard;
import me.matl114.logitech.SlimefunItem.Cargo.Config.ChipControllable;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.CargoClass.CargoConfigs;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.TickerClass.SyncBlockTick;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public abstract class AbstractSorter extends AbstractSyncTickCargo implements  ChipControllable {
    public static SyncBlockTick SORTER_SYNC =new SyncBlockTick();

    public AbstractSorter(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,int divideAmount){
        super(itemGroup, item, recipeType, recipe);
        setDisplayRecipes(
                Utils.list(AddUtils.getInfoShow("&f机制",
                                "&7这是一个同步货运机器,属于[SORTER_SYNC]组",
                                "&7位于同组的机器共享同一个ticker计数器",
                                "&7机器用该计数器顺序读取%s中记录的01码".formatted(Language.get("Items.CHIP.Name")),
                                "&7机器会读取01码的第<ticker>%32位",
                                "&7机器根据读取结果的不同会执行不同的行为"
                        ),null,
                        AddUtils.getInfoShow("&f机制",
                                "&7机器拥有%d个输入槽位以及%d个输入白名单槽".formatted(divideAmount,divideAmount),
                                "&7白名单槽会对智能货运机器或者其他附属货运进行限制",
                                "&7其中,白名单槽不放物品代表对应槽位匹配任意输入",
                                "&7其中,白名单槽放物品代表对应槽位匹配该物品的输入",
                                "&ePS:网络附属和原版货运会接受该限制"),null,
                        AddUtils.getInfoShow("&f机制",
                                "&7机器会选中一个输入槽",
                                "&7每个tick机器将该槽位物品转移至输出槽",
                                "&7玩家可以使用%s控制机器选择输入槽".formatted(Language.get("Items.CHIP.Name")),
                                "&e当机器读取到01码中的1时会顺时针选择至下一个输入槽位",
                                "&e当机器读取到01码中的0时会保持选择当前槽位不动",
                                "&c当机器读取至01码最后一位后会重置选择的槽位至第一个输入槽,并重头读取"
                        ),null
                )
        );
    }
    public void newMenuInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block){
        blockMenu.addMenuOpeningHandler(player -> {
            updateMenu(blockMenu,block,Settings.INIT);
        });
        blockMenu.addMenuCloseHandler(player -> {
            updateMenu(blockMenu,block,Settings.INIT);
        });
        updateMenu(blockMenu,block,Settings.INIT);
        blockMenu.replaceExistingItem(getInfoSlot(),getInfoOffItem(0));
    }
    public void updateMenu(BlockMenu blockMenu, Block block, Settings mod){
        loadChipCommand(blockMenu);
    }
    public abstract int getInfoSlot();
    public ItemStack getInfoItem(int code, int tickCount){
        int ticks=tickCount%32;
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a信息","&7下方为货运输出槽","&7上方为芯片槽"
        ,AddUtils.concat("&7当前读取编码位数: ",String.valueOf(ticks)),
        AddUtils.concat("&7当前选择的槽位数: ",String.valueOf(MathUtils.bitCount(code,ticks+1)%getInputSlots().length)));
    }
    public ItemStack getInfoOffItem(int tickCount){
        int ticks=tickCount%32;
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a信息","&7下方为货运输出槽","&7上方为芯片槽"
                ,AddUtils.concat("&7当前读取编码位数: ",String.valueOf(ticks)),"&e待机中,芯片不存在或已损坏");
    }
    public abstract int[] getInputWLSlot();
    protected int[] blankSlot=new int[0];
    public void syncTick(Block b, BlockMenu inv, SlimefunBlockData data, int synTickCount){
        if(inv.hasViewer())
            updateMenu(inv,b,Settings.RUN);
        String cmd=data.getData(CCODEKEY);
        int code;
        if(cmd==null||cmd.startsWith("nu")){
            if(cmd==null)
                data.setData(CCODEKEY,"nu");
            if(inv.hasViewer())
                inv.replaceExistingItem(getInfoSlot(),getInfoOffItem(synTickCount));
            return;
        }
        try{
            code=Integer.parseInt(cmd);
        }catch (Throwable e){
            data.setData(CCODEKEY,"nu");
            if(inv.hasViewer())
                inv.replaceExistingItem(getInfoSlot(),getInfoOffItem(synTickCount));
            return;
        }
        if(inv.hasViewer())
            inv.replaceExistingItem(getInfoSlot(),getInfoItem(code,synTickCount));
        int[] inputs=getInputSlots();
        int slot=inputs[ MathUtils.bitCount(code,(synTickCount%32)+1)%inputs.length];
        TransportUtils.transportItem(inv,new int[]{slot},inv,getOutputSlots(), CargoConfigs.getDefaultConfig(),null,CraftUtils.getpusher);
    }
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
        if(flow==ItemTransportFlow.WITHDRAW){
            return getOutputSlots();
        }
        else {
            if(item==null||item.getType().isAir()){
                return getInputSlots();
            }
            int[] slots=getInputSlots();
            int[] wlslots=getInputWLSlot();
            int len=slots.length;
            ItemStack st;
            ItemCounter ct=CraftUtils.getConsumer(item);
            for (int i=0;i<len;i++){
                st=menu.getItemInSlot(wlslots[i]);
                if(st==null||(st.getAmount()!=st.getMaxStackSize() && CraftUtils.matchItemStack(st,ct,false))){
                    return new int[] {slots[i]};
                }
            }
            return blankSlot;
        }
    }
    public void preRegister(){
        super.preRegister();
        //shared ticker
        this.addItemHandler((BlockTicker) SORTER_SYNC);
    }
    public void onBreak(BlockBreakEvent e, BlockMenu inv){
        super.onBreak(e, inv);
        if(inv!=null){
            inv.dropItems(inv.getLocation(),getInputWLSlot());
            inv.dropItems(inv.getLocation(),getChipSlot());
        }
    }
}
