package me.matl114.logitech.SlimefunItem.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Blocks.MultiCore;
import me.matl114.logitech.SlimefunItem.Cargo.Links.HyperLink;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockType;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PortalCore extends MultiCore {
    protected final int[] BORDER=new int[]{0,1,2,3,5,6,7,9,10,11,12,14,15,16,17};
    protected final int TOGGLE_SLOT=4;
    protected final int LINK_SLOT=13;
    protected final int HOLOGRAM_SLOT=8;
    protected final ItemStack TOGGLE_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &a开启","&7若需要更改目标坐标需要重新构建传送门");
    protected final ItemStack TOGGLE_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &c关闭");
    protected final ItemStack HOLOGRAM_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &a南北向");
    protected final ItemStack HOLOGRAM_ITEM_ON_2=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &a东西向");
    protected final ItemStack HOLOGRAM_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &c关闭");
    protected final MultiBlockType MBTYPE;
    public HashMap<String,ItemStack> MBID_TO_ITEM=new HashMap<>(){{
        put("portal.part", AddItem.PORTAL_FRAME.clone());
    }};
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public PortalCore(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                     ItemStack[] recipe, String blockId, MultiBlockType type){
        super(itemGroup, item, recipeType, recipe, blockId);
        this.MBTYPE = type;
    }
    public MultiBlockType getMultiBlockType(){
        return MBTYPE;
    }
    public MultiBlockService.MultiBlockBuilder getBuilder(){
        return MultiBlockHandler::createHandler;
    }
    public boolean loadLink(BlockMenu inv){
        ItemStack link=inv.getItemInSlot(LINK_SLOT);
        if(link!=null&& HyperLink.isLink(link.getItemMeta())){
            Location loc=HyperLink.getLink(link.getItemMeta());
            if(loc!=null&&validPortalCore(loc)){
                DataCache.setLastLocation(inv.getLocation(),loc);
                return true;
            }
        }
        return false;
    }
    public Location checkLink(Location loc){
        Location loc2=DataCache.getLastLocation(loc);
        if(loc2!=null&&validPortalCore(loc2)&&MultiBlockService.getStatus(loc)!=0){
            return loc2;
        }
        DataCache.setLastLocation(loc,null);
        return null;
    }
    public static boolean validPortalCore(Location loc){
        SlimefunBlockData data= DataCache.safeLoadBlock(loc);
        if(data!=null){
            SlimefunItem it=SlimefunItem.getById(data.getSfId());
            //test if getStatus ok
            if((it instanceof PortalCore)){

                return true;
            }
        }
        return false;
    }

    public void constructMenu(BlockMenuPreset inv){
        int[] border=BORDER;
        int len=border.length;
        for(int i=0;i<len;i++){
            inv.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }

    }

    public void setupPortal(Block block1){
        //block1.setType(Material.NETHER_PORTAL);
        Block block2=block1.getRelative(BlockFace.UP);
        Block block3=block2.getRelative(BlockFace.UP);
        WorldUtils.removeSlimefunBlock(block2.getLocation(),true);
        WorldUtils.removeSlimefunBlock(block3.getLocation(),true);
        Schedules.launchSchedules(()->{
        block3.setType(Material.NETHER_PORTAL);
        block2.setType(Material.NETHER_PORTAL);
        //南北向的
        if(block2.getRelative(BlockFace.EAST).getType().isAir()||block2.getRelative(BlockFace.WEST).getType().isAir()){
            BlockData state2=block2.getBlockData();
            BlockData state3=block3.getBlockData();
            if(state2 instanceof Orientable){
                ((Orientable)state2).setAxis(Axis.Z);

                block2.setBlockData(state2);
            }
            if(state3 instanceof Orientable){
                ((Orientable)state3).setAxis(Axis.Z);
                block3.setBlockData(state3);
            }
        }
        },0,true,0);
    }
    public void deletePortal(Block block1){
        Block block2=block1.getRelative(BlockFace.UP);
        Block block3=block2.getRelative(BlockFace.UP);
        Schedules.launchSchedules(()->{
        if(block2.getType()==Material.NETHER_PORTAL){
            block2.setType(Material.AIR);
        }
        if(block3.getType()==Material.NETHER_PORTAL){
            block3.setType(Material.AIR);
        }
        },0,true,0);
    }
    public void onMultiBlockDisable(Location loc, AbstractMultiBlockHandler handler){
        super.onMultiBlockDisable(loc,handler);
        deletePortal(loc.getBlock());
        BlockMenu inv= StorageCacheUtils.getMenu(loc);
        if(inv!=null){
            inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_OFF);
        }
    }
    public void onMultiBlockEnable(Location loc,AbstractMultiBlockHandler handler){
        super.onMultiBlockEnable(loc,handler);
        setupPortal(loc.getBlock());
    }
    public void updateMenu(BlockMenu inv, Block block, Settings mod){
        int holoStatus=DataCache.getCustomData(inv.getLocation(),"holo",0);
        if(holoStatus==0){
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_OFF);

        }else if(holoStatus==1){
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_ON);

        }else{
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_ON_2);
        }
    }
    public void newMenuInstance(BlockMenu inv, Block block){
        Location loc2=block.getLocation();
        if (MultiBlockService.getStatus(loc2)!=0){
            inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_ON);
        }else {
            inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_OFF);
        }
        inv.addMenuClickHandler(TOGGLE_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            if(MultiBlockService.getStatus(loc)==0){//not working
                if(loadLink(inv)){
                    AddUtils.sendMessage(player,"&a成功检测到目标传送门,成功建立超链接");

                    if(MultiBlockService.createNewHandler(loc,getBuilder(),getMultiBlockType())){
                        inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_ON);
                        AddUtils.sendMessage(player,"&a成功激活传送门!");
                    }else {
                        AddUtils.sendMessage(player,"&c传送门结构不完整或者结构冲突!");
                        inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_OFF);
                    }
                }else {
                    AddUtils.sendMessage(player,"&c目标传送门已损坏,请检查超链接");
                    inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_OFF);
                }
            }else {//working toggle off
                MultiBlockService.deleteMultiBlock(loc);
                AddUtils.sendMessage(player,"&a传送门成功关闭");
            }
            return false;
        }));
        DataCache.setCustomData(inv.getLocation(),"holo",0);
        inv.addMenuClickHandler(HOLOGRAM_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            int holoStatus=DataCache.getCustomData(inv.getLocation(),"holo",0);
            int statusCode=MultiBlockService.getStatus(loc);
            MultiBlockService.removeHologram(loc);
            if(statusCode==0){
                if(holoStatus==0){
                    AddUtils.sendMessage(player,"&a全息投影已切换至南北向!");
                    MultiBlockService.createHologram(loc,MBTYPE, MultiBlockService.Direction.NORTH, MBID_TO_ITEM);
                    DataCache.setCustomData(loc,"holo",1);
                }else if(holoStatus==1){
                    AddUtils.sendMessage(player,"&a全息投影已切换至东西向!");
                    MultiBlockService.createHologram(loc,MBTYPE, MultiBlockService.Direction.WEST, MBID_TO_ITEM);
                    DataCache.setCustomData(loc,"holo",2);
                }
            }
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        updateMenu(inv,block,Settings.RUN);
    }
    public void tick(Block b, BlockMenu menu,SlimefunBlockData data, int tickCount){
        //in this case .blockMenu is null

        if(MultiBlockService.acceptCoreRequest(b.getLocation(),getBuilder(),getMultiBlockType())){
            processCore(b,menu);
        }
        process(b,menu,data);

    }
    public void onBreak(BlockBreakEvent e, BlockMenu inv){
        if(inv!=null){
            Location loc=inv.getLocation();
            inv.dropItems(loc,LINK_SLOT);
        }
        super.onBreak(e,inv);

    }
}
