package me.matl114.logitech.SlimefunItem.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.PersistentEffects.CustomEffects;
import me.matl114.logitech.Schedule.PersistentEffects.PlayerEffects;
import me.matl114.logitech.Schedule.PersistentEffects.RadiationRegion;
import me.matl114.logitech.Schedule.ScheduleSave;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.*;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.CubeMultiBlock.CubeMultiBlock;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.MultiCraftingOperation;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockVector;

import java.util.*;

public class Transmutator extends MultiBlockAdvancedProcessor  {
    protected final int[] BORDER=new int[]{1,2,3,5,6,7,13,40,49};
    protected final int TOGGLE_SLOT=4;
    protected final int HOLOGRAM_SLOT=8;
    protected final ItemStack[] TOGGLE_ITEM=new ItemStack[]{
            new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换嬗变机状态",
                    "&6shift点击切换是否自动构建","&7当前状态: &c关闭","&7自动构建: &c关","&7警告:你最好别在进程中破坏机器")
            ,new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换嬗变机状态",
            "&6shift点击切换是否自动构建","&7当前状态: &c关闭","&7自动构建: &a开","&7警告:你最好别在进程中破坏机器"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换嬗变机状态",
                    "&6shift点击切换是否自动构建","&7当前状态: &a开启","&7自动构建: &c关","&7警告:你最好别在进程中破坏机器")
            ,new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换嬗变机状态",
            "&6shift点击切换是否自动构建","&7当前状态: &a开启","&7自动构建: &a开","&7警告:你最好别在进程中破坏机器")
    };
    /**
     * 保证冷却剂供应即可
     * 不得中途破坏
     */
    protected final ItemStack[] HOLOGRAM_ITEM_ON=new ItemStack[]{
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &a北向"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &a东向"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &a南向"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&7当前状态: &a西向")
    };
    protected final ItemStack HOLOGRAM_ITEM_OFF=new CustomItemStack(SlimefunItems.HOLOGRAM_PROJECTOR,"&6点击切换全息投影","&7当前状态: &c关闭");
    protected final ItemStack COOLER_INFO_ITEM=new CustomItemStack(SlimefunItems.NETHER_ICE_COOLANT_CELL,"&b冷却剂输入槽",
            "&7请保证冷却剂供应","&e上方填入冷却剂","&e下方填入下界冰冷却剂","&7冷却剂槽位会限制货运只传入冷却剂");
    protected final int COOLER_INFO_SLOT=27;
    protected final int HALT_SLOT=0;
    protected final ItemStack HALT_ITEM=new CustomItemStack(Material.BARRIER,"&c点击安全终止进程","&6不会引发熔毁");
    protected final int INFO_SLOT=22;
    protected final ItemStack INFO_ITEM=new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,"&a机器状态信息","");
    protected final int[] INPUT_SLOT=new int[]{20,29,38};
    protected final int[] COOLER_INPUT_SLOT=new int[]{18};
    protected final int[] NETHERICE_INPUT_SLOT=new int[]{36};
    protected final int[] COMMON_INPUT_SLOT=new int[]{20,29,38};
    protected final int[] OUTPUT_SLOT=new int[]{23,24,25,32,33,34,41,42,43};
    protected final int[] ALL_INPUT_SLOT=new int[]{18,36,20,29,38};
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    protected final MachineProcessor<MultiCraftingOperation> coolerProcessor;

    protected final int[] BORDER_IN = new int[]{9, 10, 11, 12, 19, 28, 37,45,46,47,48,21,30,39};
    protected final int[] BORDER_OUT = new int[]{14, 15, 16, 17, 26,35,44,50,51,52,53,23,32,41};
    public Transmutator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                        ItemStack[] recipe, String blockId, AbstractMultiBlockType type, int energyConsumption, int energyBuffer,
                        LinkedHashMap<Object, Integer> customRecipes){
        super(itemGroup, item, recipeType, recipe, blockId,type, AddUtils.addGlow(new ItemStack(Material.FIRE_CHARGE)),
                energyConsumption,energyBuffer,customRecipes);

        this.PROCESSOR_SLOT=31;
        this.coolerProcessor=new MachineProcessor<MultiCraftingOperation>( this);
        this.coolerProcessor.setProgressBar(COOLER_INFO_ITEM);

    }

    public void onMultiBlockDisable(Location loc, AbstractMultiBlockHandler handler){
        MultiCraftingOperation op=Transmutator.this.processor.getOperation(loc);
        if(op!=null&&(!op.isFinished())){//如果还在进行中就暂停
            onDestroyEffect(loc,handler);
        }else{
            removeEffect(loc,handler);
        }
        DataCache.setCustomData(loc,this.HEIGHT_KEY,0);

        super.onMultiBlockDisable(loc,handler);
    }
    public void onMultiBlockEnable(Location loc,AbstractMultiBlockHandler handler){
        super.onMultiBlockEnable(loc,handler);
        createEffect(loc,handler);
    }
    //check if lava
    protected int[] lavadx=new int[]{
            2,2,2,3,3,4,4,4,5,5,6,6,6
    };
    protected int[] lavady=new int[]{
            -2,0,2,-1,1,-2,0,2,-1,1,-2,0,2
    };
    protected int baseHeight=2;
    protected final int RADIATION_PERIOD=1000;
    protected final int RADIATION_RANGE=40;
    protected final int RADIATION_SPEED=80;
    public void createEffect(Location loc, AbstractMultiBlockHandler handler){
        MultiBlockService.Direction dir=handler.getDirection();
        AbstractMultiBlock block=  handler.getMultiBlock();
        if(block instanceof CubeMultiBlock cb){
            List<Location> fillWithLava=new ArrayList<>();
            int height=baseHeight+ cb.getHeight();
            int len=lavadx.length;
            Location locer;
            for(int y=0;y<height;y++){
                for(int j=0;j<len;j++){
                    locer=loc.clone().add(dir.getRotateX(lavadx[j],lavady[j]),y,dir.getRotateZ(lavadx[j],lavady[j]));
                    fillWithLava.add(locer);
                    WorldUtils.removeSlimefunBlock(locer,true);
                }
            }
            Schedules.launchSchedules(()->{
                int size=fillWithLava.size();
                for(int i=0;i<size;i++){
                    fillWithLava.get(i).getBlock().setType(Material.LAVA);
                }
            },0,true,0);
        }
    }
    public void removeEffect(Location loc, AbstractMultiBlockHandler handler){
        MultiBlockService.Direction dir=handler.getDirection();
        AbstractMultiBlock block=  handler.getMultiBlock();
        if(block instanceof CubeMultiBlock cb){
            List<Location> fillWithLava=new ArrayList<>();
            int height=baseHeight+ cb.getHeight();
            int len=lavadx.length;
            Location locer;
            for(int y=0;y<height;y++){
                for(int j=0;j<len;j++){
                    locer=loc.clone().add(dir.getRotateX(lavadx[j],lavady[j]),y,dir.getRotateZ(lavadx[j],lavady[j]));
                    fillWithLava.add(locer);
                    WorldUtils.removeSlimefunBlock(locer,true);
                }
            }
            Schedules.launchSchedules(()->{
                int size=fillWithLava.size();
                for(int i=0;i<size;i++){
                    fillWithLava.get(i).getBlock().setType(Material.AIR);
                }
            },0,true,0);
        }
    }
    public void onDestroyEffect(Location loc,AbstractMultiBlockHandler handler){
        MultiBlockService.Direction dir=handler.getDirection();
        AbstractMultiBlock block=  handler.getMultiBlock();
        if(block instanceof CubeMultiBlock cb){
            int height=baseHeight+ cb.getHeight();
            Location locer;
            for(int y=0;y<height;y++){
                for(int j=2;j<7;j++){
                    for(int k=-2;k<3;k++){
                        locer=loc.clone().add(dir.getRotateX(j,k),y,dir.getRotateZ(j,k));
                        WorldUtils.removeSlimefunBlock(locer,true);
                    }
                }
            }
            List<Location> fillWithLava=new ArrayList<>();
            int len=lavadx.length;
            for(int y=0;y<height;y++){
                for(int j=0;j<len;j++){
                    locer=loc.clone().add(dir.getRotateX(lavadx[j],lavady[j]),y,dir.getRotateZ(lavadx[j],lavady[j]));
                    fillWithLava.add(locer);
                }
            }
            Schedules.launchSchedules(()->{
                int size=fillWithLava.size();
                for(int i=0;i<size;i++){
                    fillWithLava.get(i).getBlock().setType(Material.CRYING_OBSIDIAN);
                }
            },0,true,0);
            Schedules.launchSchedules(()->{
                RadiationRegion.runRadiation(loc,RADIATION_RANGE,2);
            },0,true,0);
            RadiationRegion.addRadiation(loc,RADIATION_RANGE,RADIATION_PERIOD,RADIATION_SPEED,1);
        }
    }
    public HashMap<String,ItemStack> MBID_TO_ITEM=new HashMap<>(){{
        put("nuclear.frame", AddUtils.addGlow(AddItem.TRANSMUTATOR_FRAME.clone()));
        put("nuclear.glass", AddUtils.addGlow(AddItem.TRANSMUTATOR_GLASS.clone()));
        put("nuclear.rod", AddUtils.addGlow(AddItem.TRANSMUTATOR_ROD.clone()));
    }};
    public void constructMenu(BlockMenuPreset preset){
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        //输入槽边框
        border = BORDER_IN;
        len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        //输出槽边框
        border = BORDER_OUT;
        len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        //空白边框
        preset.addItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INFO_SLOT,INFO_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(HALT_SLOT,HALT_ITEM);
        preset.addItem(COOLER_INFO_SLOT,COOLER_INFO_ITEM,ChestMenuUtils.getEmptyClickHandler());
    }
    public void updateMenu(BlockMenu inv, Block block, Settings mod){
        Location loc=block.getLocation();
        int holoStatus=DataCache.getCustomData(loc,"holo",0);
        if(holoStatus==0){
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_OFF);

        }else {
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_ON[holoStatus-1]);
        }
        int status=MultiBlockService.getStatus(loc)==0?0:1;
        //负数为
        int autoRec=DataCache.getCustomData(loc,"auto",0)<=0?0:1;
        inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM[status*2+autoRec]);
        if(status==0){
            inv.replaceExistingItem(INFO_SLOT,INFO_ITEM);
        }

    }
    public void newMenuInstance(BlockMenu inv, Block block){
        inv.addMenuOpeningHandler((player -> {
            updateMenu(inv,block,Settings.RUN);
        }));
        inv.addMenuClickHandler(HALT_SLOT,((player, i, itemStack, clickAction) -> {
            this.processor.endOperation(block);
            inv.replaceExistingItem(PROCESSOR_SLOT,MenuUtils.PROCESSOR_NULL);
            return false;
        }));
        inv.addMenuClickHandler(TOGGLE_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            int statusCode=MultiBlockService.getStatus(loc);
            int autoCode=DataCache.getCustomData(loc,"auto",0)<=0?0:1;
            if(clickAction.isShiftClicked()){
                DataCache.setCustomData(loc,"auto",1-autoCode);
            }
            else if(autoCode==0){
                if(statusCode==0){
                    if(this.getCharge(loc)<energyConsumption){
                        //没电,
                        AddUtils.sendMessage(player,"&c电力不足,无法构建多方块结构!");
                    }
                    else {
                        if(MultiBlockService.createNewHandler(loc,getBuilder(),getMultiBlockType())){
                            AddUtils.sendMessage(player,"&a成功激活多方块结构!");
                        }else {
                            AddUtils.sendMessage(player,"&c多方块结构不全或者结构冲突!");
                        }
                    }
                }else {
                    MultiBlockService.deleteMultiBlock(DataCache.getLastUUID(loc));
                    AddUtils.sendMessage(player,"&a成功关闭多方块结构!");
                }
            }else{
                AddUtils.sendMessage(player,"&c多方块核心处于自动构建模式无法进行手动操作");
            }
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        DataCache.setCustomData(inv.getLocation(),"holo",0);
        inv.addMenuClickHandler(HOLOGRAM_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            int holoStatus=DataCache.getCustomData(inv.getLocation(),"holo",0);
            int statusCode=MultiBlockService.getStatus(loc);
            MultiBlockService.removeHologram(loc);
            if(statusCode==0){
                if(holoStatus!=4){
                    if(holoStatus==0)
                        AddUtils.sendMessage(player,"&a全息投影已开启!");
                    else
                        AddUtils.sendMessage(player,"&a全息投影已切换方向!");
                    MultiBlockService.createHologram(loc,MBTYPE, MultiBlockService.Direction.fromInt(holoStatus), MBID_TO_ITEM);
                    DataCache.setCustomData(loc,"holo",holoStatus+1);
                }
                else {
                    AddUtils.sendMessage(player,"&a全息投影已关闭!");
                }
            }
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));

    }
    protected ItemCounter COOLER=CraftUtils.getConsumer(SlimefunItems.REACTOR_COOLANT_CELL );
    protected ItemCounter NETHERICE=CraftUtils.getConsumer(SlimefunItems.NETHER_ICE_COOLANT_CELL);
    public boolean checkCooler(BlockMenu inv){
        Location loc=inv.getLocation();
        MultiCraftingOperation op=this.coolerProcessor.getOperation(loc);
        if(op==null){
            ItemStack it=inv.getItemInSlot(COOLER_INPUT_SLOT[0]);
            if(it==null){
                return false;
            }
            ItemStack it2=inv.getItemInSlot(NETHERICE_INPUT_SLOT[0]);
            if(it2==null){
                return false;
            }
            if(CraftUtils.matchItemStack(it,COOLER,false)&&CraftUtils.matchItemStack(it2,NETHERICE,false)){
                it.setAmount(it.getAmount()-1);
                it2.setAmount(it2.getAmount()-1);
                op=new MultiCraftingOperation(new ItemGreedyConsumer[0],2);
                coolerProcessor.startOperation(loc,op);
            }else return false;
        }
        else if(op.isFinished()){
            if(inv.hasViewer()){
                inv.replaceExistingItem(COOLER_INFO_SLOT,COOLER_INFO_ITEM);
            }
            this.coolerProcessor.endOperation(loc);
            return true;
        }else{
            op.addProgress(1);

        }
        if(inv.hasViewer())
            this.coolerProcessor.updateProgressBar(inv,COOLER_INFO_SLOT,op);
        return true;
    }

    public void tick(Block b, BlockMenu inv, SlimefunBlockData data, int tickCount){
        //in this case .blockMenu is null
        int statusCode=MultiBlockService.getStatus(data);
        Location loc=b.getLocation();
        int charge=this.getCharge(loc);
        String code=data.getData("auto");
        int autoCode=code==null?0:Integer.parseInt(code);
        if(statusCode==0){
            if(autoCode>0&&charge>2*energyConsumption){//自动构建开启 且有能量
                autoBuild(loc,data,autoCode);
            }
        }else if(MultiBlockService.acceptCoreRequest(b.getLocation(),getBuilder(),getMultiBlockType())){
            //runtime检查是否完整,每3tick检查一次,每次有1/10的概率检测一个方块
            runtimeCheck(loc,data,autoCode);
            //直接开销电量
            super.progressorCost(b,inv);
            if(charge<energyConsumption){
                //避免重连的时候出现问题,重连的时候statusCode为-3到-1,但是如果没有电 直接寄
                MultiBlockService.deleteMultiBlock(DataCache.getLastUUID(loc));
                return;
            }
            if(inv.hasViewer()){
                updateMenu(inv,b,Settings.RUN);
                ItemStack it=inv.getItemInSlot(INFO_SLOT);
                if(it!=null){
                    MultiCraftingOperation currentOperation = (MultiCraftingOperation)this.processor.getOperation(b);
                    AddUtils.setLore(it,"&7当前机器状态: %s".formatted((currentOperation==null)?"&7待机中":"&a进程中"),
                            "&7当前电量: %dJ/%dJ".formatted(charge,energybuffer),"&7当前耗电: %dJ/t".formatted(energyConsumption),
                            "&7当前并行处理数: %-3d".formatted(getCraftLimit(b,inv)));
                }
                else
                    inv.replaceExistingItem(INFO_SLOT,INFO_ITEM);

            }
            super.process(b,inv,data);
        }
    }

    public void progressorCost(Block b, BlockMenu inv){
        //覆盖父类 让process中不扣电
        //转到我的ticker里扣
        if(!checkCooler(inv)){
            MultiBlockService.toggleOff(DataCache.safeLoadBlock(inv.getLocation()));
        }
    }
    public void onBreak(BlockBreakEvent e, BlockMenu menu){
        if(menu!=null){
            Location l = menu.getLocation();
            menu.dropItems(l, this.getInputSlots());
            menu.dropItems(l, this.getOutputSlots());
        }
        //禁止了父类的关闭processor的操作，改为在MultiBlockBreak中关闭
        //合理性:只有多方块完整的时候才能进行processor,在破坏多方块的时候会取消processor
    }
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
        if(flow==ItemTransportFlow.WITHDRAW){
            return getOutputSlots();
        }
        if(item==null||item.getType().isAir()){
            return ALL_INPUT_SLOT;
        }
        if(item.getType()==Material.PLAYER_HEAD){
            if(CraftUtils.matchItemStack(item,COOLER,false)){
                return COOLER_INPUT_SLOT;
            }
            else {
                return NETHERICE_INPUT_SLOT;
            }
        }
        return getInputSlots();
    }
    public void preRegister(){

        super.preRegister();
    }
}
