package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.UtilClass.MenuClass.DataMenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TntGenerator extends AbstractMachine {
    protected final int[] INPUT_SLOTS=new int[0];
    protected final int[] OUTPUT_SLOTS=new int[0];
    protected final int[] BORDER=new int[]{
            1,2,3,5,6,7
    };
    protected final int START_SLOT=0;
    protected final ItemStack START_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6当前状态","&a已启动","&e点击关闭");
    protected final ItemStack START_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6当前状态","&a已关闭","&e点击启动");
    protected final int ADJUSTMENT_SLOT=4;
    protected final int DELAY_SLOT=8;
    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    protected final String OFFSET_KEY="tntof";
    protected final String FUSE_KEY="tntfu";
    protected final String DELAY_KEY="tntd";
    public TntGenerator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(category, item, recipeType, recipe, 0,0);
        SchedulePostRegister.addPostRegisterTask(
                ()->Schedules.launchSchedules(
                        gtThread,100,false,1
                )
        );

    }
    protected final BukkitRunnable gtThread = new BukkitRunnable() {
        boolean isWorking=false;
        public void run() {
            if(isWorking){
                return;
            }
            try{
                isWorking=true;
                Location location;
                for(Map.Entry<Chunk,ConcurrentHashMap<Location,BlockMenu>> sss:INSTANCES.entrySet()){
                    Chunk chunk=sss.getKey();
                    if (!chunk.isLoaded()){
                        continue;
                    }
                    for (Map.Entry<Location,BlockMenu> e:sss.getValue().entrySet()){
                        location=e.getKey();
                        BlockMenu inv=e.getValue();
                        ItemStack it=inv.getItemInSlot(START_SLOT);
                        if(it==null||it.getType()!=Material.GREEN_STAINED_GLASS_PANE){
                            continue;
                        }
                        DataMenuClickHandler dh=getDataHolder(null,inv);
                        int tick=dh.getInt(3)+1;
                        int delay=dh.getInt(2);
                        if(delay<=tick){
                            dh.setInt(3,0);
                            if(location.getBlock().getBlockData() instanceof NoteBlock nb){
                                if(nb.isPowered()){
                                    Location loc=location.clone().add(0.5,dh.getInt(0)/10.0,0.5);
                                    final int fuse=dh.getInt(1);

                                    Schedules.launchSchedules(()->{
                                       Entity et= loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
                                       if(et instanceof TNTPrimed tnt){
                                           tnt.setFuseTicks(fuse);
                                       }
                                    },0,true,0);
                                }
                            }
                        }else {
                            dh.setInt(3,tick);
                        }
                    }
                }
            }
            catch (Throwable e){
                Debug.logger("ERROR WHILE RUNNING TNTGENERATOR GT THREAD");
                Debug.logger(e);
            }
            finally {
                isWorking=false;
            }
        }
    };
    protected final ConcurrentHashMap<Chunk, ConcurrentHashMap<Location,BlockMenu>> INSTANCES=new ConcurrentHashMap<>();
    public DataMenuClickHandler createDataHolder(){
        return new DataMenuClickHandler() {
            //0 为 数量 1 为 电力
            int[] intdata=new int[4];
            Object runnable=null;
            public int getInt(int i){
                return intdata[i];
            }
            public void setInt(int i, int val){
                intdata[i]=val;
            }
            public Object getObject(int i){
                return runnable;
            }
            public void setObject(int i,Object obj){
                this.runnable=obj;
            }
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                return false;
            }
        };
    }
    public final int DATA_SLOT=1;
    public DataMenuClickHandler getDataHolder(Block b, BlockMenu inv){
        ChestMenu.MenuClickHandler handler=inv.getMenuClickHandler(DATA_SLOT);
        if(handler instanceof DataMenuClickHandler dh){return dh;}
        else{
            DataMenuClickHandler dh=createDataHolder();
            inv.addMenuClickHandler(DATA_SLOT,dh);
            Schedules.launchSchedules(()->{
                updateMenu(  inv,b,Settings.INIT);
            },20,false,0);
            return dh;
        }
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for(int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
    }
    public ItemStack getAdjustmentDisplay(DataMenuClickHandler dh){
        return new CustomItemStack(Material.COMPASS,"&6生成信息",
                "&7上下偏移: %.1f格".formatted(dh.getInt(0)/10.0),
                "&7爆炸延时: %dgt".formatted(dh.getInt(1)),
                "&e左键 增加偏移 0.1",
                "&e右键 减少偏移 0.1",
                "&eshift+左键 增加爆炸延时 1gt",
                "&eshift+右键 减少爆炸延时 1gt");
    }
    public ItemStack getDelayDisplay(DataMenuClickHandler dh){
        return new CustomItemStack(Material.CLOCK,"&6生成频率信息",
                "&7生成间隔: %dgt".formatted(dh.getInt(2)),
                "&e左键 增加延时 1gt",
                "&e右键 减少延时 1gt");
    }
    public void saveData(Location loc,DataMenuClickHandler dh){
        SlimefunBlockData data=DataCache.safeLoadBlock(loc);
        DataCache.setCustomData(data,OFFSET_KEY,dh.getInt(0));
        DataCache.setCustomData(data,FUSE_KEY,dh.getInt(1));
        DataCache.setCustomData(data,DELAY_KEY,dh.getInt(2));
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        SlimefunBlockData data= DataCache.safeLoadBlock(menu.getLocation());
        DataMenuClickHandler dh=getDataHolder(block,menu);
        int offset=DataCache.getCustomData(data,OFFSET_KEY,-10);
        dh.setInt(0,offset);
        int fuse=DataCache.getCustomData(data,FUSE_KEY,80);
        dh.setInt(1,fuse);
        int delay=DataCache.getCustomData(data,DELAY_KEY,50);
        dh.setInt(2,delay);
        dh.setInt(3,0);
        if(menu.getItemInSlot(START_SLOT)==null){
            menu.replaceExistingItem(START_SLOT,START_ITEM_OFF);
        }
        menu.addMenuClickHandler(START_SLOT,((player, i, itemStack, clickAction) -> {
            if(itemStack!=null&&itemStack.getType()!=Material.GREEN_STAINED_GLASS_PANE){
                menu.replaceExistingItem(START_SLOT,START_ITEM_ON);
            }else {
                menu.replaceExistingItem(START_SLOT,START_ITEM_OFF);
            }
            menu.getLocation().getBlock().getState().update(true,false);
            return false;
        }));
        menu.replaceExistingItem(ADJUSTMENT_SLOT,getAdjustmentDisplay(dh));
        menu.addMenuClickHandler(ADJUSTMENT_SLOT,((player, i, itemStack, clickAction) -> {
            int code=0;
            if(clickAction.isShiftClicked()){
                code+=2;
            }if(clickAction.isRightClicked()){
                code+=1;
            }
            DataMenuClickHandler dh2=getDataHolder(block,menu);
            switch(code){
                case 0: dh2.setInt(0,dh2.getInt(0)+1);break;
                case 1: dh2.setInt(0,dh2.getInt(0)-1);break;
                case 2: dh2.setInt(1,dh2.getInt(1)+1);break;
                case 3: dh2.setInt(1,dh2.getInt(1)-1);break;
                default:break;
            }
            if(dh2.getInt(0)<-50){
                dh2.setInt(0,-50);
            }
            if(dh2.getInt(0)>50){
                dh2.setInt(0,50);
            }
            if(dh2.getInt(1)>1600){
                dh2.setInt(1,1600);
            }
            if(dh2.getInt(1)<0){
                dh2.setInt(1,0);
            }
            menu.replaceExistingItem(ADJUSTMENT_SLOT,getAdjustmentDisplay(dh2));
            saveData(menu.getLocation(),dh2);
            return false;
        }));
        menu.replaceExistingItem(DELAY_SLOT,getDelayDisplay(dh));
        menu.addMenuClickHandler(DELAY_SLOT,((player, i, itemStack, clickAction) -> {
            int code=0;
            if(clickAction.isRightClicked()){
                code+=1;
            }
            DataMenuClickHandler dh2=getDataHolder(block,menu);
            switch(code){
                case 0: dh2.setInt(2,dh2.getInt(2)+1);break;
                case 1: dh2.setInt(2,dh2.getInt(2)-1);break;
                default:break;
            }
            if(dh2.getInt(2)>1600){
                dh2.setInt(2,1600);
            }
            if(dh2.getInt(2)<10){
                dh2.setInt(2,10);
            }
            menu.replaceExistingItem(DELAY_SLOT,getDelayDisplay(dh2));
            saveData(menu.getLocation(),dh2);
            return false;
        }));
        Chunk chunk=menu.getLocation().getChunk();
        ConcurrentHashMap<Location ,BlockMenu> map=INSTANCES.getOrDefault(chunk,new ConcurrentHashMap<>());
        map.put(menu.getLocation(),menu);
        INSTANCES.put(chunk,map);

    }
    public void updateMenu(BlockMenu menu, Block block, Settings mod){}
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<MachineRecipe>();
    }
    public void process(Block b, BlockMenu menu, SlimefunBlockData data){}
    public void onBreak(BlockBreakEvent e,BlockMenu inv){
        super.onBreak(e,inv);
        Chunk chunk=e.getBlock().getChunk();
        var map= INSTANCES.getOrDefault(chunk,new ConcurrentHashMap<>());
        map.remove(e.getBlock().getLocation());
    }
}
