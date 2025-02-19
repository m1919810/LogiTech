package me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.UtilClass.MenuClass.DataMenuClickHandler;
import me.matl114.logitech.core.Interface.LogiTechChargable;
import me.matl114.logitech.core.Interface.MenuTogglableBlock;
import me.matl114.logitech.core.Items.SpecialItems.PlayerIdCard;
import me.matl114.matlib.Algorithms.DataStructures.Struct.Pair;
import me.matl114.matlib.Utils.Inventory.ItemStacks.CleanItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SmithInterfaceCharger extends SmithingInterface implements MenuTogglableBlock {
    protected final int[] INPUT_SLOT = {19,20,21,22,23,28,29,30,31,32,37,38,39,40,41,46,47,48,49,50};
    protected final int[] IDCARD_SLOT = {26,35,44,53};
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getExtraDropSlot(){
        return IDCARD_SLOT;
    }
    protected final int[] CHARGER_BORDER = {9,10,11,12,13,14,15,18,24,27,33,36,42,45,51,6};
    protected final int[] REMOTE_BORDER = {16,25,34,43,52,7};
    protected final int DISPLAY_SLOT = 0;
    protected final int[] DISPLAY_CHARGE_SLOT = {1,2,3,4,5};
    protected final int TOGGLE_WHITELIST_SLOT = 8;
    protected final ItemStack TOGGLE_WHITELIST_OFF = new CleanItemStack(Material.RED_STAINED_GLASS_PANE,"&6白名单: &c关","&7当白名单关闭时,","&7只会匹配与下方ID卡不匹配的玩家","进行远程充电");
    protected final ItemStack TOGGLE_WHITELIST_ON = new CleanItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6白名单: &a开","&7当白名单开启时,","&7只会匹配与下方ID卡匹配的玩家","进行远程充电");
    protected final ItemStack CHARGER_DISPLAY = new CleanItemStack(ChestMenuUtils.getOutputSlotTexture(),"&6充电物品输入槽","&7放入可充电物品以充电","&7可以向本附属自定义的充电物品充电","&a充电速率 = <基础充电速率>*<速度增幅组件数量>");
    protected final ItemStack REMOTE_DISPLAY = new CleanItemStack(ChestMenuUtils.getInputSlotTexture(),"&6玩家ID卡插入槽","&7放入绑定玩家的玩家ID卡","&7可以对该玩家进行远程充电","&7充电范围包括玩家的盔甲槽和主手副手","&7可以向本附属自定义的充电物品充电","&a充电速率 = <基础充电速率>*<速度增幅组件数量>","&a充电范围受<范围增幅组件>数量影响","&7分别为16格(0个),1600格(1个),同一世界(2个),任意位置(>2个)");
    protected final int TOGGLE_LAZYMOD = 17;
    protected final ItemStack TOGGLE_LAZYMOD_OFF = new CleanItemStack(Material.RED_STAINED_GLASS_PANE,"&6懒惰模式: &c关","&7懒惰模式开启时","&7仅装备电量过半才会进行远程充电");
    protected final ItemStack TOGGLE_LAZYMOD_ON = new CleanItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6懒惰模式: &a开","&7懒惰模式开启时","&7仅装备电量过半才会进行远程充电");
    protected final ItemStack FUNCTION_DISPLAY = new CleanItemStack(SlimefunItems.BATTERY,"&6充电台功能概述",
            "&7充电台基础充电速率: %dJ".formatted(energyConsumption),
            "&7本充电台拥有三种充电方式,他们同时进行",
            "&71.将可充电物品放入下方槽位,对首个可充电物品进行充电",
            "&72.玩家打开界面时,将对玩家的盔甲槽和主副手进行充电",
            "&73.将绑定了玩家的玩家ID卡插入右侧槽位,会对玩家进行远程充电",
            "&7机器会尽可能尝试保证自身加载",
            "&a向锻铸工坊插入对应增幅组件可以提升充电台的相应功能"
    );
    protected final ItemStack[] DISPLAY_CHARGE_LEVEL = {
            new CleanItemStack(Material.RED_STAINED_GLASS_PANE,"&6电力显示: &c不足20%"),
            new CleanItemStack(Material.YELLOW_STAINED_GLASS_PANE,"&6电力显示: &e20%~40%"),
            new CleanItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6电力显示: &e40%~60%"),
            new CleanItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6电力显示: &b60%~80%"),
            new CleanItemStack(Material.LIME_STAINED_GLASS_PANE,"&6电力显示: &a80%~100%")
    };
    public Pair<ItemStack,Integer> getChargeDisplay(int charge) {
        float per =( ((float)charge)/((float) this.energybuffer));
        int level = Math.min(Math.max((int)(5.0f*per),0),4) ;
        ItemStack template = DISPLAY_CHARGE_LEVEL[level];
        return Pair.of( AddUtils.addLore(template,"","⇨ %dJ/%dJ".formatted(charge,this.energybuffer)),level);
    }
    public SmithInterfaceCharger(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energybuffer, int energyConsumption) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption, false);
    }
    public void processInterface(Block b, BlockMenu inv, SlimefunBlockData data, Location coreLocation, int speed) {
        //implement logic here
        int charge = getCharge(inv.getLocation(),data);
        for (int slot:INPUT_SLOT){

        }
        if(inv.hasViewer()){
            updateMenu(inv,b,Settings.RUN);

        }
    }
    public int processCharge(Player pl,int energyCharge){
        return energyCharge;
    }
    public int processCharge(ItemStack item,int energyCharge,int speed){
        int tobeCharge = Math.min( speed*energyConsumption,energyCharge);

        float left = LogiTechChargable.changeChargeSafe(item,energyCharge);
        return Math.max(0,energyCharge-tobeCharge+ (int)left );
    }
    public void addInfo(ItemStack stack){
        stack.setItemMeta(AddUtils.addLore(stack, LoreBuilder.powerBuffer(energybuffer), "&8⇨ &e⚡ &7" + energyConsumption + " J/次 基础充电速率").getItemMeta());
    }

    @Override
    public void constructMenu(BlockMenuPreset preset) {
        for (int i :CHARGER_BORDER) {
            preset.addItem(i,CHARGER_DISPLAY,ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i: REMOTE_BORDER) {
            preset.addItem(i,REMOTE_DISPLAY,ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(DISPLAY_SLOT,FUNCTION_DISPLAY,ChestMenuUtils.getEmptyClickHandler());
        for (int i : DISPLAY_CHARGE_SLOT){
            preset.addMenuClickHandler(i,ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    public void newMenuInstance(@NotNull BlockMenu blockMenu, @NotNull Block block) {
        toggleStatus(blockMenu,getStatus(blockMenu));
        blockMenu.addMenuClickHandler(TOGGLE_WHITELIST_SLOT,((player, i, itemStack, clickAction) -> {
            var re = getStatus(blockMenu);
            toggleStatus(blockMenu,new boolean[]{!re[0],re[1]});
            return false;
        }));
        blockMenu.addMenuClickHandler(TOGGLE_LAZYMOD,((player, i, itemStack, clickAction) -> {
            var re = getStatus(blockMenu);
            toggleStatus(blockMenu,new boolean[]{re[0],!re[1]});
            return false;
        }));
        updateMenu(blockMenu,block,Settings.INIT);
    }

    @Override
    public void updateMenu(BlockMenu blockMenu, Block block, Settings mod) {
        int charge = getCharge(blockMenu.getLocation());
        var result =getChargeDisplay(charge);
        var item = result.getA();
        //be in 0~4
        var val = result.getB();
        for (int i=0 ; i<= val ;++i){
            blockMenu.replaceExistingItem(DISPLAY_CHARGE_SLOT[i],item);
        }
        for (int i = val+1 ;i<DISPLAY_CHARGE_SLOT.length;++i){
            blockMenu.replaceExistingItem(i,ChestMenuUtils.getBackground());
        }
        var dh = getDataHolder(blockMenu);
        Set<UUID> Uids= Arrays.stream(IDCARD_SLOT).mapToObj(blockMenu::getItemInSlot).filter(Objects::nonNull).map(itemThis->{
            if(SlimefunItem.getByItem(itemThis) instanceof PlayerIdCard card){
                return card.getUid(itemThis);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toSet());
        dh.setObject(0,Uids);
    }
    public DataMenuClickHandler getDataHolder(BlockMenu inv){
        if(inv.getMenuClickHandler(0) instanceof DataMenuClickHandler dh){
            return dh;
        }else {
            var result = new DataMenuClickHandler(){
                Object uuidList;
                @Override
                public void setObject(int val, Object val2) {
                   uuidList = val;
                }
                @Override
                public Object getObject(int val) {
                    return uuidList;
                }
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    return false;
                }
            };
            inv.addMenuClickHandler(0,result);
            return result;
        }
    }

    @Override
    public boolean[] getStatus(BlockMenu inv) {
        ItemStack item = inv.getItemInSlot(TOGGLE_WHITELIST_SLOT);
        boolean status1 = item!=null&&item.getType()==Material.GREEN_STAINED_GLASS_PANE;
        item = inv.getItemInSlot(TOGGLE_LAZYMOD);
        boolean status2 = item!=null&&item.getType()==Material.GREEN_STAINED_GLASS_PANE;
        return new boolean[]{status1,status2};
    }

    @Override
    public void toggleStatus(BlockMenu inv, boolean... result) {
        if(result.length>0)
            inv.replaceExistingItem(TOGGLE_WHITELIST_SLOT,result[0]?TOGGLE_WHITELIST_ON:TOGGLE_WHITELIST_OFF);
        if(result.length>1)
            inv.replaceExistingItem(TOGGLE_LAZYMOD,result[1]?TOGGLE_LAZYMOD_ON:TOGGLE_LAZYMOD_OFF);
    }
}
