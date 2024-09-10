package me.matl114.logitech.SlimefunItem.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlockTypes;
import me.matl114.logitech.SlimefunItem.Blocks.MultiCore;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.*;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiLevelBlock.MultiLevelBlock;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiLevelBlock.MultiLevelBlockType;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class FinalAltarCore  extends MultiCore {
    protected static final String LVL_KEY="lv";
    public interface FinalAltarChargable{
        public static String FORCE_STATE="fi";
        default int getForced(SlimefunBlockData data){
            return  DataCache.getCustomData(data,FORCE_STATE,0);
        }
        static void setForced(SlimefunBlockData data,int lvl){
            DataCache.setCustomString(data,FORCE_STATE,String.valueOf(lvl));
        }
        default int mayForced(SlimefunBlockData data){
            //TODO 完成多方块结构 完成多级激活模式
            //TODO 暂时不知道咋做，先写着
            int forcelvl=DataCache.getCustomData(data,FORCE_STATE,0);
            if(forcelvl>0){
                return forcelvl;
            }
            Location loc=data.getLocation().clone().add(0,-1,0);
            if(DataCache.getSfItem(loc) instanceof FinalAltarCore){
                int lv=DataCache.getCustomData(loc,FinalAltarCore.LVL_KEY,0);
                if(lv>0){
                    DataCache.setCustomData(data,FORCE_STATE,lv);
                    return lv;
                }
            }
            return 0;
        }
        static void clearForced(Location loc){
            SlimefunItem it=DataCache.getSfItem(loc);
            if(it instanceof FinalAltarChargable fac){
                DataCache.setCustomData(loc,FORCE_STATE,0);
            }
        }
    }
    //TODO set force level when mulitblock broke down

    protected final int[] BORDER=new int[]{0,1,2,3,5,6,7};
    protected final int HOLOGRAM_SLOT=8;
    protected final int STATUS_SLOT=4;
    protected final ItemStack STATUS_ITEM_ON_1=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&6点击切换祭坛状态","&6shift点击尝试刷新祭坛","&7祭坛状态: &a开启","&7等级: &6壹","&7关闭祭坛不会破坏祭坛本体,只会取消上方机器的激活状态");
    protected final ItemStack STATUS_ITEM_ON_2=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&6点击切换祭坛状态","&6shift点击尝试刷新祭坛","&7祭坛状态: &a开启","&7等级: &6贰","&7关闭祭坛不会破坏祭坛本体,只会取消上方机器的激活状态");
    protected final ItemStack STATUS_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,
            "&6点击切换祭坛状态","&6shift点击尝试刷新祭坛","&7祭坛状态: &c关闭","&7关闭祭坛不会破坏祭坛本体,只会取消上方机器的激活状态");
    protected final ItemStack HOLOGRAM_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &a一级祭坛");
    protected final ItemStack HOLOGRAM_ITEM_ON_2=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &a二级祭坛");
    protected final ItemStack HOLOGRAM_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &c关闭");
    protected final MultiLevelBlockType MBTYPE;
    public HashMap<String,ItemStack> MBID_TO_ITEM=new HashMap<>(){{
        put("final.sub", AddItem.FINAL_LASER.clone());
        put("final.frame", AddItem.FINAL_FRAME.clone());
        put("final.base", AddItem.FINAL_BASE.clone());
    }};
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public FinalAltarCore(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                      ItemStack[] recipe, String blockId){
        super(itemGroup, item, recipeType, recipe, blockId);
        this.MBTYPE = MultiBlockTypes.FINAL_ALTAR;
    }
    public MultiLevelBlockType getMultiBlockType(){
        return MBTYPE;
    }
    public MultiBlockService.MultiBlockBuilder BUILDER=( (core, type, uid) -> {
        AbstractMultiBlockHandler blockHandler=MultiBlockHandler.createHandler(core,type,uid);
        AbstractMultiBlock block=blockHandler.getMultiBlock();
        int level=0;
        if(block instanceof MultiLevelBlock lb){
            level=lb.getLevel();
        }
        DataCache.setCustomData(core,LVL_KEY,level);
        return blockHandler;
    });
    public MultiBlockService.MultiBlockBuilder getBuilder(){
        return BUILDER;
    }

    public void constructMenu(BlockMenuPreset inv) {
        int[] border = BORDER;
        int len = border.length;
        for (int i = 0; i < len; i++) {
            inv.addItem(border[i], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        inv.addItem(HOLOGRAM_SLOT, HOLOGRAM_ITEM_OFF);
    }
    public void onMultiBlockDisable(Location loc, AbstractMultiBlockHandler handler, MultiBlockService.DeleteCause cause){
        super.onMultiBlockDisable(loc,handler,cause);
        //要设置上面的机器,清除里面的force level数据
        Location poweredLoc =loc.clone().add(0,1,0);
        FinalAltarChargable.clearForced(poweredLoc);
        BlockMenu inv= StorageCacheUtils.getMenu(loc);
        if(inv!=null){
            inv.replaceExistingItem(STATUS_SLOT,STATUS_ITEM_OFF);
        }
    }
    public void onMultiBlockEnable(Location loc,AbstractMultiBlockHandler handler){
        super.onMultiBlockEnable(loc,handler);
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
            int lvl=DataCache.getCustomData(inv.getLocation(),LVL_KEY,0);
            inv.replaceExistingItem(STATUS_SLOT,lvl==1? STATUS_ITEM_ON_1:STATUS_ITEM_ON_2);
        }else {
            inv.replaceExistingItem(STATUS_SLOT,STATUS_ITEM_OFF);
        }
        inv.addMenuClickHandler(STATUS_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            if(MultiBlockService.getStatus(loc)==0){//not working
                if(MultiBlockService.createNewHandler(loc,getBuilder(),getMultiBlockType())){
                    int code=DataCache.getCustomData(loc,LVL_KEY,0);
                    if(code>0){
                        inv.replaceExistingItem(STATUS_SLOT,code==1? STATUS_ITEM_ON_1:STATUS_ITEM_ON_2);
                        AddUtils.sendMessage(player,"&a成功激活终极祭坛!");
                    }else {
                        MultiBlockService.deleteMultiBlock(loc,MultiBlockService.GENERIC);
                        AddUtils.sendMessage(player,"&c构建祭坛时出现未知方块数据错误!请联系管理员");
                    }
                }else {
                    AddUtils.sendMessage(player,"&c终极祭坛结构不完整或者结构冲突!");
                    inv.replaceExistingItem(STATUS_SLOT,STATUS_ITEM_OFF);
                }

            }else {//working toggle off
                MultiBlockService.deleteMultiBlock(loc,MultiBlockService.MANUALLY);
                AddUtils.sendMessage(player,"&a终极祭坛成功关闭");
                inv.replaceExistingItem(STATUS_SLOT,STATUS_ITEM_OFF);
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
                    AddUtils.sendMessage(player,"&a全息投影已切换至一级祭坛!");
                    MultiBlockService.createHologram(loc,MBTYPE.getSubParts(0), MultiBlockService.Direction.NORTH, MBID_TO_ITEM);
                    DataCache.setCustomData(loc,"holo",1);
                }else if(holoStatus==1){
                    AddUtils.sendMessage(player,"&a全息投影已切换至二级祭坛!");
                    MultiBlockService.createHologram(loc,MBTYPE, MultiBlockService.Direction.WEST, MBID_TO_ITEM);
                    DataCache.setCustomData(loc,"holo",2);
                }else {
                    AddUtils.sendMessage(player,"&a全息投影已关闭!");
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
            int autoCode=DataCache.getCustomData(data,"auto",0);
            runtimeCheck(menu.getLocation(),data,autoCode);
            processCore(b,menu);
        }
        process(b,menu,data);
    }
    public void processCore(Block b, BlockMenu menu){
        if(menu.hasViewer()){
            updateMenu(menu,b,Settings.RUN);
        }
    }

    public void onBreak(BlockBreakEvent e, BlockMenu inv){
        super.onBreak(e,inv);
    }



}
