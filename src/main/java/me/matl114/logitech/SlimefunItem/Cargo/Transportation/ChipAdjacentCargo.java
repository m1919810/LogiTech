package me.matl114.logitech.SlimefunItem.Cargo.Transportation;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.Cargo.CargoMachine.AbstractSyncTickCargo;
import me.matl114.logitech.SlimefunItem.Cargo.Config.ChipControllable;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.CargoClass.CargoConfigs;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.TickerClass.SyncBlockTick;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

//TODO 待开发
public class ChipAdjacentCargo extends AdjacentCargo implements ChipControllable, SyncBlockTick.SyncTickers {
    public ChipAdjacentCargo(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, List<ItemStack> displayList){
        super(itemGroup, item, recipeType, recipe,displayList);
        setDisplayRecipes(
                Utils.list(AddUtils.getInfoShow("&f机制",
                                "&7这是一个同步货运机器,属于[CHIP_SYNC]组",
                                "&7位于同组的机器将共享同一个ticker计数器",
                                "&7出于对时序安全性的保障,&c每1s该计数器+1并让机器工作一次",
                                "&7机器用该计数器数值顺序读取%s中记录的01码".formatted(Language.get("Items.CHIP.Name")),
                                "&7具体地,机器会读取01码的第<ticker>(mod32)位",
                                "&7机器根据读取结果是0/1会执行相应的行为"
                        ),null,
                        AddUtils.getInfoShow("&f机制",
                                "&7本机器可以选择与其相邻的方块参与传输",
                                "&7本机器接受芯片信息的调控",
                                "&7当读取结果为1时,执行运输",
                                "&7当读取结果为0时,待机",
                                "&c信息将实时生效"),null
                )
        );
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(getInfoSlot(),getInfoOffItem(0), ChestMenuUtils.getEmptyClickHandler());
    }
    public void newMenuInstance(BlockMenu inv, Block b){
        inv.addMenuOpeningHandler((player -> {
            updateMenu(inv,b,Settings.RUN);
        }));
        inv.addMenuCloseHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });
        inv.addMenuClickHandler(DIRECTION_SLOT[0],getDirectionHandler("from_dir",inv));
        inv.addMenuClickHandler(DIRECTION_SLOT[1],getDirectionHandler("to_dir",inv));
        updateMenu(inv,b, Settings.INIT);
    }
    public void updateMenu(BlockMenu inv ,Block b,Settings mod){
        loadConfig(inv,b);
        updateDirectionSlot("from_dir",inv,DIRECTION_SLOT[0]);
        updateDirectionSlot("to_dir",inv,DIRECTION_SLOT[1]);
        loadChipCommand(inv);
    }
    public  int getInfoSlot(){
        return 4;
    }
    public int getConfigSlot(){

        return 3;
    }
    public int getChipSlot(){
        return 5;
    }
    public ItemStack getInfoItem(int code, int tickCount,int bit){
        int ticks=tickCount%32;
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a信息","&7左侧为货运配置卡槽","&7右侧为芯片槽","&7在左侧配置源方方块邻接方向","&7在右侧配置目标方块邻接方向"
                ,AddUtils.concat("&7当前读取编码位数: ",String.valueOf(ticks)),
                AddUtils.concat("&7当前运行状态: ",bit==1?"运行":"待机"));
    }
    public ItemStack getInfoOffItem(int tickCount){
        int ticks=tickCount%32;
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a信息","&7左侧为货运配置卡槽","&7右侧为芯片槽","&7在左侧配置源方方块邻接方向","&7在右侧配置目标方块邻接方向"
                ,AddUtils.concat("&7当前运行状态: ",String.valueOf(ticks)),"&e待机中,芯片不存在或已损坏");
    }
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
        synTickCount=synTickCount%32;
        int bit=  MathUtils.getBit(code,synTickCount);
        if(inv.hasViewer())
            inv.replaceExistingItem(getInfoSlot(),getInfoItem(code,synTickCount,bit));
        if(bit==1){
            super.tick(b,inv,data,synTickCount);
        }
    }

    public void preRegister(){
        super.preRegister();
        //shared ticker
        this.addItemHandler((BlockTicker) AbstractSyncTickCargo. CHIP_SYNC);
    }
    public void onBreak(BlockBreakEvent e, BlockMenu inv){
        super.onBreak(e, inv);
        if(inv!=null){
            inv.dropItems(inv.getLocation(),getChipSlot());
        }
    }
}
