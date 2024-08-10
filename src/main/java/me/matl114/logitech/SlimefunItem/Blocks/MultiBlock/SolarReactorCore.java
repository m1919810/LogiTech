package me.matl114.logitech.SlimefunItem.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlock.PortalCore;
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
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.inventory.ItemStack;

public class SolarReactorCore extends MultiBlockCore {
    protected final int[] BORDER=new int[]{0,1,2,3,5,6,7,8,9,10,11,12,14,15,16,17};
    protected final int TOGGLE_SLOT=4;

    protected final ItemStack TOGGLE_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &a开启","&7若需要更改目标坐标需要重新构建传送门");
    protected final ItemStack TOGGLE_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &c关闭");
    protected final MultiBlockType MBTYPE;
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public SolarReactorCore(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
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
    public void constructMenu(BlockMenuPreset inv){
        int[] border=BORDER;
        int len=border.length;
        for(int i=0;i<len;i++){
            inv.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }

    }


    public void onMultiBlockDisable(Location loc){

    }
    public void onMultiBlockEnable(Location loc){

    }

    public void newMenuInstance(BlockMenu inv, Block block){
        inv.addMenuClickHandler(TOGGLE_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            int statusCode=MultiBlockService.getStatus(loc);
            if(statusCode==0){
                if(MultiBlockService.createNewHandler(loc,getBuilder(),getMultiBlockType())){
                    AddUtils.sendMessage(player,"&a成功激活多方块结构!");
                }else {
                    AddUtils.sendMessage(player,"&c多方块结构不全或者结构冲突!");
                }
            }else {
                MultiBlockService.deleteMultiBlock(DataCache.getLastUUID(loc));
                AddUtils.sendMessage(player,"&a成功关闭多方块结构!");
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
