package me.matl114.logitech.core.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.Getter;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.Utils.NMSUtils;
import me.matl114.logitech.Utils.WorldUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Animals;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TimerRandomtick extends AbstractTimerRange{
    public static boolean isEnable(){
        return instance!=null;
    }
    @Getter
    private static TimerRandomtick instance=null;
    public TimerRandomtick(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energybuffer, int energyConsumption) {
        super(category, item, recipeType, recipe,10,1, energybuffer, energyConsumption);
        instance=this;
    }
//    Method
//    @Override
    private static Consumer<String> errorOut=(s)->{};
    public Runnable getTickTask(Location location, Location center) {
        Block bb=location.getBlock();
        AtomicReference<Runnable> task=new AtomicReference<>(null);
        Material material=bb.getType();
        Supplier<?> randomTickTask=NMSUtils.doRandomTickAccess.getInvokeTask(bb);
        if(randomTickTask!=null){
            return ()->{
                try{
                    if(SPEED_UP_PLANTS.contains(material)){
                        operateGrowable(bb);
                    }
                    randomTickTask.get();
                }catch (Throwable ignored){}
            };
        }else{
            NMSUtils. getNMSMethodAccess.invokeCallback(state->{
                NMSUtils. getCraftWorldHandleMethodAccess.invokeCallback(world->{
                    NMSUtils. getPositionMethodAccess.invokeCallback(pos->{
                        NMSUtils. randomSourceAccess.ofAccess(world).get((randomSource)->{
                            task.set(()->{
                                try{
                                    if(SPEED_UP_PLANTS.contains(material)){
                                        operateGrowable(bb);
                                    }
                                    NMSUtils. randomTickAccess.invoke(state,world,pos,randomSource);
                                }catch (Throwable ignored){}
                            });
                        }).ifFailed((obj)->{errorOut.accept( "failed getRandomSource");});
                    },()->{errorOut.accept("failed getPosition");},bb);
                },()->{errorOut.accept("failed getWorld");},bb.getWorld());
            },()->{errorOut.accept("failed getNMS");},bb);
        }
        return task.get();

    }
    @Override
    public void process(Block b, BlockMenu preset, SlimefunBlockData data) {
        //do Animal speed up logic
        Location loc=preset.getLocation();
        Schedules.execute(()->{
            loc.getWorld().getNearbyEntities(loc.clone().add(0.5,0.5,0.5),getRange()+0.5f,getRange()+0.5f,getRange()+0.5f,(entity -> {
                if(entity instanceof Animals animals){
                    int age=animals.getAge();
                    if(age<-4){
                        animals.setAge((int)(age*0.25));
                    }
                }
                return false;
            }));
        },true);
    }
    private HashSet<Material> SPEED_UP_PLANTS=new HashSet<>(){{
        add(Material.CARROTS);
        add(Material.NETHER_WART);
        add(Material.SUGAR_CANE);
        add(Material.PUMPKIN_STEM);
        add(Material.COCOA);
        add(Material.MELON_STEM);
        add(Material.BEETROOTS);
        add(Material.POTATOES);
        add(Material.CACTUS);
        add(Material.WHEAT);
        add(Material.SWEET_BERRY_BUSH);
    }};
    public void operateGrowable(Block bb){
        BlockData data=bb.getBlockData();
        if(data instanceof Ageable ageable){
            ageable.setAge(ageable.getMaximumAge());
            bb.setBlockData(ageable);
        }
    }

    @Override
    public boolean blockPredicate(Block block) {
        return typePredicate(block.getType());
    }
    public boolean typePredicate(Material type){
        return WorldUtils.isRandomTickable(type);
    }

    @Override
    public Particle getParticle() {
        return Particle.WAX_ON  ;
    }
}
