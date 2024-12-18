package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.google.common.base.Preconditions;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.Getter;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Interface.MenuTogglableBlock;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.NMSUtils;
import me.matl114.logitech.Utils.UtilClass.TickerClass.Ticking;
import me.matl114.logitech.Utils.WorldUtils;
import me.matl114.matlib.Utils.Reflect.MethodAccess;
import me.matl114.matlib.Utils.Reflect.ReflectUtils;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Method;
import java.sql.Ref;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TimerBlockEntity extends AbstractTimerRange  {
    public static boolean isEnable(){
        return instance!=null;
    }
    @Getter
    private static TimerBlockEntity instance=null;
    public TimerBlockEntity(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energybuffer, int energyConsumption) {
        super(category, item, recipeType, recipe,7,50, energybuffer, energyConsumption);
        instance=this;
    }

    private static boolean running=false;
    private static int TIMER=50;
    private static Consumer<String> errorOut=(s)->{
        //Debug.logger(s);
    };
    public Runnable getTickTask(Location loc,Location center){
        AtomicReference<Runnable> result=new AtomicReference<>(null);
        Block bb=loc.getBlock();
        NMSUtils. getHandleMethodAccess.invokeCallback((levelAccess)->{
            NMSUtils. getPositionMethodAccess.invokeCallback((blockPos)->{
                NMSUtils. getNMSMethodAccess.invokeCallback((state)->{
                    NMSUtils. getNMSBlockType.invokeCallback((block)->{
                        if(NMSUtils. iTileEntityInterface==null){
                            List<Class> interfaces=ReflectUtils. getAllInterfacesRecursively(block.getClass());
                            var val= interfaces.stream().filter(s->s.getSimpleName().endsWith("TileEntity")).findFirst();
                            if(val.isPresent()){
                                NMSUtils. iTileEntityInterface=val.get();
                            }else {
                                return;
                            }
                        }
                        if(NMSUtils. iTileEntityInterface.isInstance(block)){
                            NMSUtils. getBlockEntityMethodAccess.invokeCallback((blockEntity)->{
                                if(blockEntity!=null){
                                    NMSUtils. getBlockEntityTypeMethodAccess.invokeCallback((type)->{
                                        NMSUtils. getCraftWorldHandleMethodAccess.invokeCallback((world)->{
                                            try{
                                                var ticker= NMSUtils. getTickerMethodAccess.invokeIgnoreFailure(block,world,state,type);
                                                if(ticker!=null){
                                                    if(NMSUtils.blockEntityTickerInterface==null){
                                                        List<Class> interfaces=ReflectUtils. getAllInterfacesRecursively(ticker.getClass());
                                                        NMSUtils. blockEntityTickerInterface= interfaces.stream().filter(s->s.getSimpleName().endsWith("Ticker")).findFirst().get();
                                                    }
                                                    result.set(()->{
                                                        try{
                                                            NMSUtils.tickMethodAccess.invoke(ticker,world,blockPos,state,blockEntity);
                                                        }catch (Throwable e){
                                                        }
                                                    });
                                                }
                                                errorOut.accept("successfully invoke!");
                                            }catch (Throwable e){
                                                errorOut.accept("failed");
                                                e.printStackTrace();
                                            }
                                        },()->{errorOut.accept("failed getCraftWorldHandle");},bb.getWorld());
                                    },()->{errorOut.accept("failed getBlockEntityType");},blockEntity);
                                }else {
                                    errorOut.accept("not a tile Entity");
                                }
                            },()->{errorOut.accept("failed getBlockEntityMethod");},levelAccess,blockPos);
                        }else{
                            removeLocation(center,bb.getLocation());
                            errorOut.accept("not a instance of TileEntity");
                        }
                    },()->{errorOut.accept("failed getNMSBlockType");},state);

                },()->{errorOut.accept("failed getNMS");},bb);
            },()->{errorOut.accept("failed getPositionMethod");},bb);
        },()->{errorOut.accept("failed getHandle");},bb);
        return result.get();
    }
    @Override
    public boolean blockPredicate(Block block) {
        return DataCache.getMenu(block.getLocation())==null&&WorldUtils.isEntityBlock(block.getType());
    }



}
