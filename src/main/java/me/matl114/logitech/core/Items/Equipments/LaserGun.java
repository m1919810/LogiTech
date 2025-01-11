package me.matl114.logitech.core.Items.Equipments;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.core.Registries.CustomEffects;
import me.matl114.logitech.Utils.UtilClass.EffectClass.PlayerEffects;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.core.Items.Abstracts.ChargableProps;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.WorldUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class LaserGun extends ChargableProps {
    protected final NamespacedKey LEVEL_KEY=AddUtils.getNameKey("laser-lvl");
    protected final String LEVEL_PREFIX= AddUtils.resolveColor("&x&E&B&3&3&E&B发射器功率: &a");
    protected final int getLevel(ItemMeta meta){
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container.has(LEVEL_KEY, PersistentDataType.INTEGER)){
            return container.get(LEVEL_KEY, PersistentDataType.INTEGER);
        }else {
            return 0;
        }
    }
    protected final void setLevel(ItemMeta meta, int level){
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(LEVEL_KEY, PersistentDataType.INTEGER, level);
        List<String> lores=meta.getLore();
        if(lores.size()>1){
            lores.set(lores.size()-3, AddUtils.concat(LEVEL_PREFIX,"第",String.valueOf(level+1),"档/共",String.valueOf(MAX_LEVEL),"档"));
        }
        meta.setLore(lores);
    }
    protected final int MAX_LEVEL=5;
    public LaserGun(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(itemGroup, item, recipeType, recipe);
    }
    public void addInfo(ItemStack stack){
        super.addInfo(stack);
        ItemMeta meta=stack.getItemMeta();
        setLevel(meta,0);
        stack.setItemMeta(meta);
    }
    float MAX_BUFFER=9_999_999.0F;
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
        if(!p.isSneaking()){
            onLaser(p,stack,false,true);
        }else{
            onLaserLevelAdd(p,stack);
        }

    }
    public void onLaserLevelAdd(Player p,ItemStack stack){
        ItemMeta meta=stack.getItemMeta();
        int level=getLevel(meta);
        level=(level+1)%MAX_LEVEL;
        setLevel(meta,level);
        stack.setItemMeta(meta);
        AddUtils.sendMessage(p,"&a激光发射器功率切换成功.当前为 &6第%d档".formatted(level+1));
    }
    public void onLaser(Player p,ItemStack stack,boolean onHead,boolean withWarning){
        if(PLAYERCOOLDOWN.contains(p)){
            if(withWarning){
                AddUtils.sendMessage(p,"&c物品冷却中");
            }
            return;
        }
        float charge=getItemCharge(stack);
        int level =getLevel(stack.getItemMeta());
        float consumption=CONSUMPTION*((float)Math.pow(10,level));
        if(charge>=consumption){
            //setItemCharge(stack,);
            if(canUse(p,withWarning)){
                setItemCharge(stack,charge-consumption);
                launchLaser(p,level,onHead);
                PLAYERCOOLDOWN.add(p);
                Schedules.launchSchedules(()->{
                    PLAYERCOOLDOWN.remove(p);
                },10,false,0);

            }
        }else {
            //if(withWarning){
                AddUtils.sendMessage(p,AddUtils.concat("&c电力不足! ",String.valueOf(charge),"J/",String.valueOf(consumption),"J"));
            //}
        }
    }
    public void launchLaser(Player p,int level,boolean fromEye){
        Location loc;
        if(!fromEye){
            loc=WorldUtils.getHandLocation(p);
        }else {
            loc=p.getEyeLocation();
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
                                    float damage=4*(level+1);
                                    damage=0.5f*damage*damage;
                                    if(WorldUtils.testAttackPermission(p,player,damage)){
                                        PlayerEffects.grantEffect(CustomEffects.LASER,player,4*(level+1),1);
                                    }
                                }
                            }else {
                                if(entity instanceof Damageable le){
                                    hasTarget=true;
                                    if(WorldUtils.testAttackPermission(p,le,0)){
                                        le.damage(100*(level+1),p);
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
