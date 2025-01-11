package me.matl114.logitech.core.Blocks;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.Registries.CustomEffects;
import me.matl114.logitech.Utils.UtilClass.EffectClass.PlayerEffects;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.core.Blocks.MultiBlock.FinalAltarCore;
import me.matl114.logitech.core.Blocks.MultiBlockCore.MultiBlockPart;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.core.Interface.MenuBlock;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import javax.annotation.Nullable;
import java.util.*;

public class Laser extends AbstractMachine implements MultiBlockPart, FinalAltarCore.FinalAltarChargable, MenuBlock.MenuNotAccessible {
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }

    public final String PARTID;
    public Laser(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                                       int energybuffer, int energyConsumption,String partId) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
        this.PARTID=partId;
        this.setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow("&f机制 - &c激光",
                        "&7该机器满足指定条件会发射激光并消耗电力",
                        "&7可选条件1: 被红石充能",
                        "&7可选条件2: 置于终极祭坛上",
                        "&7发射的激光等级lvl为%d+(祭坛等级)*%d".formatted(DEFAULT_LVL,FORCE_INCREASE),
                        "&7激光会杀死非玩家实体,并对玩家造成0.5*(lvl)^2的真实伤害",
                        "&7激光会沿该方块朝向进行延申,直至碰见首个非空气方块",
                        "&7或者达到最大长度 %d 格".formatted(MAX_SEARCH_LEN)),null,
                AddUtils.getInfoShow("&f机制 - &c充能",
                        "&7当置于终极祭坛上时",
                        "&7该机器会尝试对激光最末端的机器尝试充能",
                        "&7仅有特定机器才能被激光充能",
                        "&7一般用于机器激活的前置条件"),null
        ));
    }
    public String getPartId(){
        return PARTID;
    }
    public String POWERED_KEY="po";
    public String DIRECTION="dir";
    public int MAX_SEARCH_LEN=32;
    public void process(Block b, @Nullable BlockMenu menu, SlimefunBlockData data){

    }
    protected final int DEFAULT_LVL=3;
    protected final int FORCE_INCREASE=6;
    protected static final HashMap<Directions,String> DIR_KEYS=new LinkedHashMap<>(){{
       for(Directions dir:Directions.values()) {
           put(dir, AddUtils.concat("f_",dir.toString()));
       }
    }};
    public interface LaserChargable{
        default int hasCharged(SlimefunBlockData data,Directions dir){
            return DataCache.getCustomData(data,Laser.DIR_KEYS.get(dir),0);
        }
        static void setCharged(SlimefunBlockData data ,Directions dir,int charge){
            DataCache.setCustomData(data,Laser.DIR_KEYS.get(dir),charge);
        }
    }
    //FIXME powered wrongly
    //FIXME charge machine wrongly
    @Override
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker){
        if(conditionHandle(b,menu)){
            final int force=this.mayForced(data);
            Location loc=b.getLocation();
            int charged= DataCache.getCustomData(loc,POWERED_KEY,0);
            if(force>0||charged!=0){
                int direction=DataCache.getCustomData(data,DIRECTION,-1);
                if(direction>=0){
                    progressorCost(b,menu);
                    Directions dir= Directions.fromInt(direction);
                    Schedules.launchSchedules(()->{
                        Location loc2=loc.clone();
                        int i=0;
                        for (;i<MAX_SEARCH_LEN;++i){
                            loc2=dir.move(loc2);
                            if(!WorldUtils.isLightPassableBlock(loc2.getBlock())){
                                break;
                            }
                        }
                        if(i==0)return;
                        Location launcher=loc.clone().add(0.5,0.5,0.5);
                        if(force>0){
                            //给机器充能
                           // Debug.debug(loc2);
                            SlimefunItem it=DataCache.getSfItem(loc2);
                            if(it instanceof LaserChargable lc){
                                SlimefunBlockData data2=DataCache.safeLoadBlock(loc2);
                                LaserChargable.setCharged(data2,dir,2);
                            }
                        }
                        Location end=dir.remote(launcher,i+1);
                        WorldUtils.spawnLineParticle(launcher,end, Particle.END_ROD,5*i);
                        Runnable killingThread=()->{
                            BoundingBox box=new BoundingBox(loc.getX(),loc.getY(),loc.getZ(),end.getX()+0.5,end.getY()+0.5,end.getZ()+0.5);
                            Collection<Entity> entities=loc.getWorld().getNearbyEntities(box);
                            for (Entity e :entities){
                                if(e instanceof Player p){
                                    PlayerEffects.grantEffect(CustomEffects.LASER,p,DEFAULT_LVL+force*FORCE_INCREASE,1);
                                }else if(e instanceof Damageable dm){
                                    dm.damage(114);
                                }else {
                                    e.remove();
                                }
                            }
                        };
                        Schedules.launchSchedules(killingThread,0,true,0);
                        Schedules.launchSchedules(killingThread,6,true,0);
                        //FIXME async thread of searching nonair blocks, and find out the killing bounding box
                    },0,false,0);
                }
            }
            Schedules.launchSchedules(()->{
                //FIXME change this to observer or dropper
                if(force>0){
                    return;
                }
                BlockData data1=b.getBlockData();
                if(data1 instanceof Dispenser dp){
                    DataCache.setCustomData(data,POWERED_KEY,dp.isTriggered()?1:0);
                }

            },0,false,0);
        }
    }
    public void onPlace(BlockPlaceEvent e,Block b){
        super.onPlace(e,b);
        BlockData data1=b.getBlockData();
        if(data1 instanceof Directional dp){
            DataCache.setCustomData(b.getLocation(),DIRECTION,Directions.fromBlockFace(dp.getFacing()).toInt());
        }
    }
    public void onBreak(BlockBreakEvent event,BlockMenu menu ){
        super.onBreak(event,menu);
    }
    public boolean redirectMenu(){
        return false;
    }
    public void constructMenu(BlockMenuPreset preset){

    }
    public void registerBlockMenu(SlimefunItem that){
        //handle blockPlaceEvent
        handleBlock(that);
        handleMultiBlockPart(this);
    }

}
