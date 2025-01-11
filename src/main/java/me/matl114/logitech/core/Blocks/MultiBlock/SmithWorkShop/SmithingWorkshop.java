package me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.core.Registries.MultiBlockTypes;
import me.matl114.logitech.core.Blocks.MultiBlockCore.MultiCore;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.UtilClass.FunctionalClass.OutputStream;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.*;
import me.matl114.logitech.Utils.Utils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

public class SmithingWorkshop extends MultiCore {
    protected final int[] BORDER=new int[]{0,1,2,3,5,6,7};
    protected final int HOLOGRAM_SLOT=8;
    protected final int STATUS_SLOT=4;
    protected final ItemStack STATUS_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&6锻造核心信息","&7工坊状态: &a开启","&7自动构建:强制开启");
    protected final ItemStack STATUS_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            "&6锻造核心信息","&c点击查看结构错误","&7工坊状态: &c关闭","&7自动构建:强制开启");
    protected final ItemStack HOLOGRAM_ITEM_OFF=new CustomItemStack(SlimefunItems.HOLOGRAM_PROJECTOR,"&6点击切换全息投影","&e或使用/logitech multiblock 查看搭建教程","&7当前状态: &c关闭");
    protected final ItemStack[] HOLOGRAM_ITEM_ON=new ItemStack[]{
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&e或使用/logitech multiblock 查看搭建教程","&7当前状态: &a北向"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&e或使用/logitech multiblock 查看搭建教程","&7当前状态: &a东向"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&e或使用/logitech multiblock 查看搭建教程","&7当前状态: &a南向"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&e或使用/logitech multiblock 查看搭建教程","&7当前状态: &a西向")
    };
    protected final ItemStack PLUGIN_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6工坊增幅组件槽","&7于下方插入工坊增幅组件");
    protected final int PLUGIN_SLOT=13;
    protected final int[] PLUG_SLOT=IntStream.range(18,27).toArray();
    protected final MultiBlockType MBTYPE;
    public HashMap<String,ItemStack> MBID_TO_ITEM=new HashMap<>(){{
        MultiBlockTypes.SMITHING_VANILLA_PART.forEach((m)->{
            put(m.toString(),new ItemStack(m));
        });
        put(Material.LAVA.toString(),new ItemStack(Material.LAVA_BUCKET));
        put("smith.interface",AddUtils.addGlow(new ItemStack(Material.SEA_LANTERN)));

        //????
    }};
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public SmithingWorkshop(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                          ItemStack[] recipe, String blockId){
        super(itemGroup, item, recipeType, recipe, blockId);
        this.MBTYPE = MultiBlockTypes.SMITHING_WORKSHOP;
        this.setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow("&f机制 - &c多方块机器",
                        "&7")
        ));
    }
    public MultiBlockType getMultiBlockType(){
        return MBTYPE;
    }
    public MultiBlockService.MultiBlockBuilder BUILDER=( (core, type, uid) -> {
        AbstractMultiBlockHandler blockHandler= MultiBlockHandler.createHandler(core,type,uid);
       // AbstractMultiBlock block=blockHandler.getMultiBlock();
        return blockHandler;
    });
    public MultiBlockService.MultiBlockBuilder getBuilder(){
        return BUILDER;
    }
    public boolean useAdvancedMenu(){
        return true;
    }

    @Override
    public void listenDragClick(BlockMenu inv, InventoryDragEvent e) {
        super.listenDragClick(inv,e);
        var rawSlots=e.getRawSlots();
        for (int i:PLUG_SLOT){
            if(rawSlots.contains(i)){
                e.setCancelled(true);return;
            }
        }
    }
    @Override
    public void listenOriginClick(BlockMenu inv, InventoryClickEvent e) {
        super.listenOriginClick(inv, e);
        if(e.isCancelled()){return;}
        if(e.getClick()== ClickType.SHIFT_LEFT||e.getClick()== ClickType.SHIFT_RIGHT){
            if(e.getRawSlot()>=inv.getInventory().getSize()){
                e.setCancelled(true);
                ItemStack stack=e.getCurrentItem();
                if(SWAmplifyComponent.getComponentType(stack)!=null&&SlimefunItem.getByItem(stack)==SWAmplifyComponent.getComponentType(stack)){
                    for (int i:PLUG_SLOT){
                        if(inv.getItemInSlot(i)==null){
                            ItemStack cloned=stack.clone();
                            cloned.setAmount(1);
                            stack.setAmount(stack.getAmount()-1);
                            inv.replaceExistingItem(i,cloned);
                            AddUtils.sendMessage(e.getWhoClicked(),"&6[锻铸工坊] &a成功设置增幅组件");
                            return;
                        }
                    }
                    AddUtils.sendMessage(e.getWhoClicked(),"&6[锻铸工坊] &c增幅槽位已满");
                    return;
                }else {
                    AddUtils.sendMessage(e.getWhoClicked(),"&6[锻铸工坊] &c并不是有效的增幅组件");
                }
            }else if(e.getRawSlot()>=0){
                for (int i:PLUG_SLOT){
                    if(i==e.getRawSlot()){

                        return;
                    }
                }
            }
        }
    }



    public void constructMenu(BlockMenuPreset inv) {
        int[] border = BORDER;
        int len = border.length;
        for (int i = 0; i < len; i++) {
            inv.addItem(border[i], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        inv.addItem(HOLOGRAM_SLOT, HOLOGRAM_ITEM_OFF);
        IntStream.range(9,18).forEach(i->{
            inv.addItem(i,ChestMenuUtils.getInputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        });
        IntStream.range(27,36).forEach(i->{
            inv.addItem(i,ChestMenuUtils.getInputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        });
    }
    public void onMultiBlockDisable(Location loc, AbstractMultiBlockHandler handler, MultiBlockService.DeleteCause cause){
        super.onMultiBlockDisable(loc,handler,cause);
        //这里也要清除,lvl数据 防止上面重新读取回来
        //要设置上面的机器,清除里面的force level数据
        BlockMenu inv= DataCache.getMenu(loc);
        if(inv!=null){
            inv.replaceExistingItem(STATUS_SLOT,STATUS_ITEM_OFF);
        }
    }
    public void onMultiBlockEnable(Location loc,AbstractMultiBlockHandler handler){
        super.onMultiBlockEnable(loc,handler);
        BlockMenu inv= DataCache.getMenu(loc);
        if(inv!=null){
            updateMenu(inv,loc.getBlock(),Settings.RUN);
        }
    }
    public void updateMenu(BlockMenu inv, Block block, Settings mod){
        int holoStatus=DataCache.getCustomData(inv.getLocation(),MultiBlockService.getHologramKey(),0);
        if(holoStatus==0){
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_OFF);

        }else if(holoStatus<=4&&holoStatus>0){
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_ON[holoStatus-1]);

        }else{
            DataCache.getCustomData(inv.getLocation(),MultiBlockService.getHologramKey(),0);
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_OFF);
        }
        int status=MultiBlockService.getStatus(inv.getLocation());
        inv.replaceExistingItem(STATUS_SLOT,status==0?STATUS_ITEM_OFF:STATUS_ITEM_ON);
    }
    public void newMenuInstance(BlockMenu inv, Block block){
        Location loc2=block.getLocation();
        if (MultiBlockService.getStatus(loc2)!=0){
            inv.replaceExistingItem(STATUS_SLOT,STATUS_ITEM_ON);
        }else {
            inv.replaceExistingItem(STATUS_SLOT,STATUS_ITEM_OFF);
        }
        DataCache.setCustomData(inv.getLocation(),MultiBlockService.getAutoKey(),1);
        inv.addMenuClickHandler(STATUS_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            int statusCode=MultiBlockService.getStatus(loc);
            if(clickAction.isShiftClicked()){
                AddUtils.sendMessage(player,"&c无法切换自动构建!");
            }
            else {
                if(statusCode==0){
                    if(MultiBlockService.createNewHandler(loc,getBuilder(),getMultiBlockType(), OutputStream.getPlayerOut(player))){
                        AddUtils.sendMessage(player,"&a成功激活多方块结构!");
                    }else {
                        AddUtils.sendMessage(player,"&c多方块结构不全或者结构冲突!");
                    }
                }else {
                    AddUtils.sendMessage(player,"&c多方块核心处于自动构建模式无法进行手动操作");
                }
            }
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        DataCache.setCustomData(inv.getLocation(),MultiBlockService.getHologramKey(),0);
        inv.addMenuClickHandler(HOLOGRAM_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            int holoStatus=DataCache.getCustomData(inv.getLocation(),MultiBlockService.getHologramKey(),0);
            int statusCode=MultiBlockService.getStatus(loc);
            MultiBlockService.removeHologramSync(loc);
            if(statusCode==0){
                if(holoStatus!=4){
                    if(holoStatus==0)
                        AddUtils.sendMessage(player,"&a全息投影已开启!");
                    else
                        AddUtils.sendMessage(player,"&a全息投影已切换方向!");
                    MultiBlockService.createHologram(loc,MBTYPE, MultiBlockService.Direction.fromInt(holoStatus), MBID_TO_ITEM);
                    DataCache.setCustomData(loc,MultiBlockService.getHologramKey(),holoStatus+1);
                }
                else {
                    AddUtils.sendMessage(player,"&a全息投影已关闭!");
                }
            }
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        Arrays.stream(PLUG_SLOT).forEach(i->{
            inv.addMenuClickHandler(i,((player, i1, itemStack, clickAction) -> {
                if(itemStack!=null&&clickAction.isShiftClicked()){
                    ItemStack cloned=itemStack.clone();
                    int amount=cloned.getAmount();
                    var leftOver= player.getInventory().addItem(cloned);
                    if(leftOver.get(0)!=null&&leftOver.get(0).getAmount()==amount){
                        AddUtils.sendMessage(player,"&6[锻铸工坊] &c玩家背包已满!");
                    }else{
                        int realAmount=leftOver.get(0)==null?0: leftOver.get(0).getAmount();
                        itemStack.setAmount(realAmount);
                        AddUtils.sendMessage(player,"&6[锻铸工坊] &a成功取出增幅组件");
                    }
                }
                else if(itemStack==null^player.getItemOnCursor().getType().isAir()){
                    if(itemStack==null){
                        ItemStack stack=player.getItemOnCursor();
                        if(SWAmplifyComponent.getComponentType(stack)!=null&&SlimefunItem.getByItem(stack)==SWAmplifyComponent.getComponentType(stack)){
                            ItemStack put=stack.clone();
                            put.setAmount(1);
                            stack.setAmount(stack.getAmount()-1);
                            inv.replaceExistingItem(i,put);
                            AddUtils.sendMessage(player,"&6[锻铸工坊] &a成功设置增幅组件");
                        }else {
                            AddUtils.sendMessage(player,"&6[锻铸工坊] &c并不是有效的增幅组件");
                        }
                    }else{
                        player.setItemOnCursor(itemStack);
                        inv.replaceExistingItem(i,null);
                        AddUtils.sendMessage(player,"&6[锻铸工坊] &a成功取出增幅组件");
                    }
                }
                return false;
            }));
        });
        updateMenu(inv,block,Settings.RUN);
    }

    public int getAmplifyCompentLevel(Location loc,SWAmplifyComponent component){
        BlockMenu inv=DataCache.getMenu(loc);
        Material type=component.getType();
        return (int)Arrays.stream(PLUG_SLOT).filter(i->{
            var a=inv.getItemInSlot(i);
            return a!=null&&a.getType()==type;
        }).count();
    }
    public boolean hasAmplifyCompentLevel(Location loc,SWAmplifyComponent component){
        BlockMenu inv=DataCache.getMenu(loc);
        Material type=component.getType();
        for (int i:PLUG_SLOT) {
            var a=inv.getItemInSlot(i);
            if(a!=null&&a.getType()==type){
                return true;
            }
        }
        return false;
    }

    public void processCore(Block b, BlockMenu menu){
        if(menu.hasViewer()){
            updateMenu(menu,b,Settings.RUN);
        }
    }

    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        if (menu!=null){
            menu.dropItems(menu.getLocation(),PLUG_SLOT);
        }
    }
}
