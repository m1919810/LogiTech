package me.matl114.logitech.SlimefunItem.Items;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.Schedule.PersistentEffects.CustomEffects;
import me.matl114.logitech.Schedule.PersistentEffects.PlayerEffects;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.WorldUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class LaserGun extends ChargableProps{
    public LaserGun(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(itemGroup, item, recipeType, recipe);
    }
    float MAX_BUFFER=1296000.0F;
    float CONSUMPTION=640.0F;
    @Override
    public float getMaxItemCharge(ItemStack var1) {
        return MAX_BUFFER;
    }
    public Set<Player> PLAYERCOOLDOWN=ConcurrentHashMap.newKeySet();
    @Override
    public void onClickAction(PlayerRightClickEvent event) {
        event.cancel();
        Player p=event.getPlayer();
        ItemStack stack=event.getItem();
        if(p==null||stack==null){
            return;
        }
        onLaser(p,stack,false,true);

    }
    public void onLaser(Player p,ItemStack stack,boolean onHead,boolean withWarning){
        if(PLAYERCOOLDOWN.contains(p)){
            if(withWarning){
                AddUtils.sendMessage(p,"&c物品冷却中");
            }
            return;
        }
        float charge=getItemCharge(stack);
        if(charge>=CONSUMPTION){
            //setItemCharge(stack,);
            if(canUse(p,withWarning)){
                setItemCharge(stack,charge-CONSUMPTION);
                launchLaser(p,onHead);
                PLAYERCOOLDOWN.add(p);
                Schedules.launchSchedules(()->{
                    PLAYERCOOLDOWN.remove(p);
                },10,false,0);

            }
        }else {
            if(withWarning){
                AddUtils.sendMessage(p,AddUtils.concat("&c电力不足! ",String.valueOf(charge),"J/",String.valueOf(CONSUMPTION),"J"));
            }
        }
    }
    public void launchLaser(Player p,boolean fromEye){
        Location loc=p.getEyeLocation().clone();
        if(!fromEye){
            Location playerLocation=p.getLocation();
            loc.add(playerLocation.subtract(loc).multiply(0.25).toVector());
        }
        final Pair<Integer,Location> endLocaion=WorldUtils.rayTraceLocation(p.getLocation().getDirection(), loc, 0.25, 100, new Predicate<Location>() {
            HashSet<Location> testHistory=new HashSet<>();
            public boolean testIsAir(Location loc){
                Location blockLocation=WorldUtils.getBlockLocation(loc);
                if(testHistory.contains(loc)){
                    return true;
                }else if(WorldUtils.isLightPassableBlock(blockLocation.getBlock())) {

                        testHistory.add(loc);
                        return true;

                }else return false;
            }
            @Override
            public boolean test(Location location) {
                if(testIsAir(location)){

                    Collection<Entity> entities=location.getWorld().getNearbyEntities(location,0.4,0.4,0.4);
                    if(!entities.isEmpty()){
                        boolean hasTarget=false;
                        for(Entity entity:entities){

                            if(entity instanceof Player player){
                                if(p!=player){
                                    hasTarget=true;

                                    if(WorldUtils.testAttackPermission(p,player)){
                                        PlayerEffects.grantEffect(CustomEffects.LASER,player,4,1);
                                    }
                                }
                            }else {
                                if(entity instanceof Damageable le){
                                    hasTarget=true;
                                    if(WorldUtils.testAttackPermission(p,le)){
                                        le.damage(100,p);
                                    }
                                }
                            }
                        }
                        return !hasTarget;

                    }else {
                        return true;
                    }
                }else {
                    return false;
                }
            }
        });
        WorldUtils.spawnLineParticle(loc,endLocaion.getSecondValue(), Particle.END_ROD,endLocaion.getFirstValue());

    }
}
