package me.matl114.logitech.core.Items.Equipments;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.core.Items.Abstracts.ChargableProps;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.BukkitUtils;
import me.matl114.logitech.Utils.WorldUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TrackingArrowLauncher extends ChargableProps {
    public TrackingArrowLauncher(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(itemGroup, item, recipeType, recipe);
    }
    protected final float MAX_CHARGE=9_000_000.0f;
    protected Random rand = new Random();
    protected final float ARROW_SPEED=1.25F;
    protected final int ARROW_AMOUNT=16;
    protected final int ARROW_EXIST_TIME=8;
    protected final float RAYTRACE_RANGE=60;
    protected final int MAX_TRACETIME=150;
    protected final int PERIOD_TRACETIME=2;
    protected final int BASIC_DAMAGE=40;
    protected final float ENERGYCONSUMPTION=640.0f;
    protected final float DEFLECTION_RATE=0.2f;
    protected final float LOCATION_DELTA=0.4f;
    protected final float SIGHT_DELTA=10f;
    public float getMaxItemCharge(ItemStack var1){
        return MAX_CHARGE;
    }

    @Override
    public void onClickAction(PlayerRightClickEvent event) {
        Player p=event.getPlayer();
        if(p!=null){
            ItemStack item=event.getItem();
            float charge=this.getItemCharge(item);
            if(charge<ENERGYCONSUMPTION){
                AddUtils.sendMessage(p,AddUtils.concat("&c电力不足! ",String.valueOf(charge),"J/",String.valueOf(ENERGYCONSUMPTION),"J"));
            }else {
                if(canUse(p,false)&&WorldUtils.hasPermission(p,p.getEyeLocation(), Interaction.ATTACK_ENTITY)){
                    this.setItemCharge(item,charge-ENERGYCONSUMPTION);
                    onArrowLaunched(p,item);
                }else{
                    AddUtils.sendMessage(p,"&c你没有权限在这里攻击生物!");
                }
            }
        }
        event.cancel();

    }
    public void onArrowLaunched(Player p,ItemStack item){
        ItemMeta meta=item.getItemMeta();
        int powerLevel=meta.getEnchantLevel(Enchantment.ARROW_DAMAGE);
        int sharpnessLevel=meta.getEnchantLevel(Enchantment.DAMAGE_ALL);
        int damage=(int)BASIC_DAMAGE+2*powerLevel+sharpnessLevel;
        //随机生成若干箭矢，需要针对location 朝向 motion做改动
        Location loc = WorldUtils.getHandLocation(p);
        HashSet<Arrow> arrows=new HashSet<>();
        for(int i=0;i<ARROW_AMOUNT;i++){
            arrows.add(spawnArrowRandomly(loc,p,damage));
        }
        Location targetCenter=loc.add(loc.getDirection().normalize().multiply(RAYTRACE_RANGE));
        HashSet<Entity> targetEntities= WorldUtils.getEntityInDistance(targetCenter,1.2*RAYTRACE_RANGE,(e)->{return e!=p
              && WorldUtils.isTargetableLivingEntity(e);});
        //索敌算法
        HashSet<LivingEntity> livingEntities=new HashSet<>();
        for(Entity entity:targetEntities){
            if(entity instanceof LivingEntity li){
                livingEntities.add(li);
            }
        }
        launchAutoTrace(arrows,livingEntities);
    }
    public Arrow spawnArrowRandomly(Location loc,Player p,int damage){
        //0.5的生成位置偏移
        Location loc2=loc.clone().add(rand.nextFloat(-LOCATION_DELTA,LOCATION_DELTA),rand.nextFloat(-LOCATION_DELTA/4,0),rand.nextFloat(-LOCATION_DELTA,LOCATION_DELTA));
        //10度的偏差
        loc2.setPitch(loc2.getPitch()+rand.nextFloat(-SIGHT_DELTA,SIGHT_DELTA));
        loc2.setYaw(loc2.getYaw()+rand.nextFloat(-SIGHT_DELTA,SIGHT_DELTA));
        Arrow a= loc.getWorld().spawnArrow(loc2,loc2.getDirection(),ARROW_SPEED,ARROW_EXIST_TIME);
        a.setDamage(damage);
        a.setCritical(false);
        a.setShooter(p);
        a.setGravity(false);
        a.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

        //这样才有伤害
        return a;
    }
    public void onArrowCancelTrace(Arrow arrow){
        arrow.setGravity(true);
    }
    public void launchAutoTrace(HashSet<Arrow> arrows,HashSet<LivingEntity> targetEntities){
        HashSet<Arrow> runningArrows=new HashSet<>(arrows);
        BukkitRunnable task = new BukkitRunnable() {
            protected int runTime;
            protected boolean isRunning=false;
            public void cancel(){
                super.cancel();
                //runningArrows.forEach((a)->{onArrowCancelTrace(a);});
                BukkitUtils.executeSync(()->{
                    arrows.forEach((a)->{
                        a.remove();
                    });
                });


            }
            public void run(){
                if(isRunning){
                    return;
                }
                isRunning=true;
                if(runTime>=MAX_TRACETIME){
                    this.cancel();
                }else {
                    runTime++;
                    try{
                        if(runTime>5){
                            Iterator<Arrow> iterator=runningArrows.iterator();
                            HashMap<Arrow,LivingEntity> targetMap=new HashMap<>();
                            while(iterator.hasNext()){
                                Arrow arrow=iterator.next();
                                if(arrow.isOnGround()||arrow.isDead()||!arrow.isValid()||arrow.isInBlock()){
                                    onArrowCancelTrace(arrow);
                                    iterator.remove();
                                    arrows.remove(arrow);
                                }
                                else {
                                    Location loc=arrow.getLocation();
                                    float minDistance=RAYTRACE_RANGE;
                                    LivingEntity choosedEntity=null;
                                    Iterator<LivingEntity> iterator2=targetEntities.iterator();
                                    while(iterator2.hasNext()){
                                        LivingEntity targetEntity=iterator2.next();
                                        if(targetEntity.isDead()||!targetEntity.isValid()||loc.getWorld()!=targetEntity.getLocation().getWorld()){
                                            iterator2.remove();
                                        }else {
                                            float distance=(float) loc.distance(targetEntity.getLocation());
                                            if(distance<minDistance){
                                                minDistance=distance;
                                                choosedEntity=targetEntity;
                                            }
                                        }
                                    }
                                    if(choosedEntity!=null){
                                        targetMap.put(arrow,choosedEntity);
                                    }else {
                                        onArrowCancelTrace(arrow);
                                        iterator.remove();
                                    }
                                }
                            }
                            if(arrows.isEmpty()){
                                this.cancel();
                            }
                            BukkitUtils.executeSync(()->{
                                for(Map.Entry<Arrow,LivingEntity> entry:targetMap.entrySet()){
                                    retrackArrow(entry.getKey(),entry.getValue());
                                }
                            });
                        }
                        for(Arrow arrow:arrows){
                            arrow.getWorld().spawnParticle(Particle.CHERRY_LEAVES,arrow.getLocation(),3,0.0,0.0,0.0,1,null,true);
                            if(runTime%2==0&&runTime>5)
                                arrow.getWorld().spawnParticle(Particle.FLAME,arrow.getLocation(),0,0.0,0.0,0.0,1,null,true);

                        }
                    }finally {
                        isRunning=false;
                    }
                }
            }
        };
        Schedules.launchSchedules(task,2*PERIOD_TRACETIME,false,PERIOD_TRACETIME);
    }
    public void retrackArrow(Arrow arrow,LivingEntity target){
        Location loc=arrow.getLocation();
        Location targetLoc=target.getEyeLocation();
        if(loc.getWorld()==targetLoc.getWorld()&&loc.distance(targetLoc)>1.0f) {
            arrow.setVelocity(targetLoc.subtract(loc).toVector().normalize().multiply(ARROW_SPEED * DEFLECTION_RATE).add(arrow.getVelocity().multiply(1.0f - DEFLECTION_RATE)));
        }else {
            arrow.setVelocity(arrow.getVelocity().normalize().multiply(ARROW_SPEED));
        }
    }
}
