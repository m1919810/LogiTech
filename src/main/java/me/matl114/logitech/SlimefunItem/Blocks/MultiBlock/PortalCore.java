package me.matl114.logitech.SlimefunItem.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore;
import me.matl114.logitech.SlimefunItem.Storage.Links.HyperLink;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockType;
import me.matl114.logitech.Utils.WorldUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PortalCore extends MultiBlockCore {
    protected final int[] BORDER=new int[]{0,1,2,3,5,6,7,8,9,10,11,12,14,15,16,17};
    protected final int TOGGLE_SLOT=4;
    protected final int LINK_SLOT=13;
    protected final ItemStack TOGGLE_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &a开启","&7若需要更改目标坐标需要重新构建传送门");
    protected final ItemStack TOGGLE_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &c关闭");
    protected final MultiBlockType MBTYPE;
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

    }
    public void deletePortal(Block block1){
        Block block2=block1.getRelative(BlockFace.UP);
        Block block3=block2.getRelative(BlockFace.UP);
        if(block2.getType()==Material.NETHER_PORTAL){
            block2.setType(Material.AIR);
        }
        if(block3.getType()==Material.NETHER_PORTAL){
            block3.setType(Material.AIR);
        }
    }
    public void onMultiBlockDisable(Location loc){
        deletePortal(loc.getBlock());
        BlockMenu inv= StorageCacheUtils.getMenu(loc);
        if(inv!=null){
            inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_OFF);
        }
    }
    public void onMultiBlockEnable(Location loc){
        setupPortal(loc.getBlock());
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
    }
    public void tick(Block b, BlockMenu menu, int tickCount){
        //in this case .blockMenu is null

        if(MultiBlockService.acceptCoreRequest(b.getLocation(),getBuilder(),getMultiBlockType())){
            processCore(b,menu);
        }
        process(b,menu);

    }

}
