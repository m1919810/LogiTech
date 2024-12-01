package me.matl114.logitech.Utils;

import com.bgsoftware.wildstacker.api.loot.LootTable;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.BlockDataController;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.events.AndroidMineEvent;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.androids.AndroidInstance;
import io.github.thebusybiscuit.slimefun4.implementation.tasks.TickerTask;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusher;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTables;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WorldUtils {
    public static SlimefunAddon INSTANCE= MyAddon.getInstance();
    public static final BlockDataController CONTROLLER=Slimefun.getDatabaseManager().getBlockDataController();
    public static void setBlock(Location loc, Material material) {
        loc.getBlock().setType(material);
    }
    public static void setAir(Location loc) {
        loc.getBlock().setType(Material.AIR);
    }
    public static HashMap<Material,Integer> FOOD_LEVELS=new HashMap<>(){{
        put(Material.MUSHROOM_STEW,6);
        put(Material.BEEF,3);
        put(Material.ROTTEN_FLESH,4);
        put(Material.CHORUS_FRUIT,4);
        put(Material.BEETROOT,1);
        put(Material.POTATO,1);
        put(Material.COD,2);
        put(Material.GOLDEN_APPLE,4);
        put(Material.ENCHANTED_GOLDEN_APPLE,4);
        put(Material.SALMON,2);
        put(Material.COOKED_COD,5);
        put(Material.HONEY_BOTTLE,6);
        put(Material.COOKED_PORKCHOP,8);
        put(Material.PUMPKIN_PIE,8);
        put(Material.POISONOUS_POTATO,2);
        put(Material.RABBIT,3);
        put(Material.PORKCHOP,3);
        put(Material.BAKED_POTATO,5);
        put(Material.BEETROOT_SOUP,6);
        put(Material.PUFFERFISH,1);
        put(Material.COOKED_RABBIT,5);
        put(Material.GLOW_BERRIES,2);
        put(Material.SUSPICIOUS_STEW,6);
        put(Material.SWEET_BERRIES,2);
        put(Material.TROPICAL_FISH,1);
        put(Material.COOKED_CHICKEN,6);
        put(Material.BREAD,5);
        put(Material.GOLDEN_CARROT,6);
        put(Material.CHICKEN,2);
        put(Material.MELON_SLICE,2);
        put(Material.RABBIT_STEW,10);
        put(Material.APPLE,4);
        put(Material.CARROT,3);
        put(Material.COOKED_MUTTON,6);
        put(Material.COOKED_SALMON,6);
        put(Material.MUTTON,2);
        put(Material.SPIDER_EYE,2);
        put(Material.COOKIE,2);
        put(Material.DRIED_KELP,1);
        put(Material.COOKED_BEEF,8);
    }};
    public static HashMap<Material,Integer> FOOD_SATURATION_MUL_10=new HashMap<>(){{
        put(Material.MUSHROOM_STEW,192);
        put(Material.BEEF,78);
        put(Material.ROTTEN_FLESH,88);
        put(Material.CHORUS_FRUIT,104);
        put(Material.BEETROOT,32);
        put(Material.POTATO,26);
        put(Material.COD,44);
        put(Material.GOLDEN_APPLE,176);
        put(Material.ENCHANTED_GOLDEN_APPLE,176);
        put(Material.SALMON,44);
        put(Material.COOKED_COD,160);
        put(Material.HONEY_BOTTLE,132);
        put(Material.COOKED_PORKCHOP,288);
        put(Material.PUMPKIN_PIE,208);
        put(Material.POISONOUS_POTATO,52);
        put(Material.RABBIT,78);
        put(Material.PORKCHOP,78);
        put(Material.BAKED_POTATO,160);
        put(Material.BEETROOT_SOUP,192);
        put(Material.PUFFERFISH,22);
        put(Material.COOKED_RABBIT,160);
        put(Material.GLOW_BERRIES,44);
        put(Material.SUSPICIOUS_STEW,192);
        put(Material.SWEET_BERRIES,44);
        put(Material.TROPICAL_FISH,22);
        put(Material.COOKED_CHICKEN,192);
        put(Material.BREAD,160);
        put(Material.GOLDEN_CARROT,264);
        put(Material.CHICKEN,52);
        put(Material.MELON_SLICE,52);
        put(Material.RABBIT_STEW,320);
        put(Material.APPLE,104);
        put(Material.CARROT,96);
        put(Material.COOKED_MUTTON,216);
        put(Material.COOKED_SALMON,216);
        put(Material.MUTTON,52);
        put(Material.SPIDER_EYE,72);
        put(Material.COOKIE,44);
        put(Material.DRIED_KELP,26);
        put(Material.COOKED_BEEF,288);
    }};
    public static List<LootTables> OVERWORLD_STRUCTURE_CHESTS=new ArrayList<>(){{
        add(LootTables.ABANDONED_MINESHAFT);
        add(LootTables.BURIED_TREASURE);
        add(LootTables.DESERT_PYRAMID);
        add(LootTables.IGLOO_CHEST);
        add(LootTables.JUNGLE_TEMPLE);
        add(LootTables.JUNGLE_TEMPLE_DISPENSER);
        add(LootTables.PILLAGER_OUTPOST);
        add(LootTables.ANCIENT_CITY);
        add(LootTables.ANCIENT_CITY_ICE_BOX);
        add(LootTables.RUINED_PORTAL);
        try{
            add(LootTables.TRIAL_CHAMBERS_REWARD);
            add(LootTables.TRIAL_CHAMBERS_SUPPLY);
            add(LootTables.TRIAL_CHAMBERS_CORRIDOR);
            add(LootTables.TRIAL_CHAMBERS_INTERSECTION);
            add(LootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL);
            add(LootTables.TRIAL_CHAMBERS_ENTRANCE);
            add(LootTables.TRIAL_CHAMBERS_CORRIDOR_DISPENSER);
            add(LootTables.TRIAL_CHAMBERS_CHAMBER_DISPENSER);
            add(LootTables.TRIAL_CHAMBERS_WATER_DISPENSER);
            add(LootTables.TRIAL_CHAMBERS_CORRIDOR_POT);
        }catch (Throwable e){
            Debug.logger("1.21 LootTable not supported");
        }
        add(LootTables.SHIPWRECK_MAP);
        add(LootTables.SHIPWRECK_SUPPLY);
        add(LootTables.SHIPWRECK_TREASURE);
        add(LootTables.SIMPLE_DUNGEON);
        add(LootTables.SPAWN_BONUS_CHEST);
        add(LootTables.STRONGHOLD_CORRIDOR);
        add(LootTables.STRONGHOLD_CROSSING);
        add(LootTables.STRONGHOLD_LIBRARY);
        add(LootTables.UNDERWATER_RUIN_BIG);
        add(LootTables.UNDERWATER_RUIN_SMALL);
        add(LootTables.VILLAGE_ARMORER);
        add(LootTables.VILLAGE_BUTCHER);
        add(LootTables.VILLAGE_CARTOGRAPHER);
        add(LootTables.VILLAGE_DESERT_HOUSE);
        add(LootTables.VILLAGE_FISHER);
        add(LootTables.VILLAGE_FLETCHER);
        add(LootTables.VILLAGE_MASON);
        add(LootTables.VILLAGE_PLAINS_HOUSE);
        add(LootTables.VILLAGE_SAVANNA_HOUSE);
        add(LootTables.VILLAGE_SHEPHERD);
        add(LootTables.VILLAGE_SNOWY_HOUSE);
        add(LootTables.VILLAGE_TAIGA_HOUSE);
        add(LootTables.VILLAGE_TANNERY);
        add(LootTables.VILLAGE_TEMPLE);
        add(LootTables.VILLAGE_TOOLSMITH);
        add(LootTables.VILLAGE_WEAPONSMITH);
        add(LootTables.WOODLAND_MANSION);
        //archaeology
        add(LootTables.DESERT_WELL_ARCHAEOLOGY);
        add(LootTables.DESERT_PYRAMID_ARCHAEOLOGY);
        add(LootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON);
        add(LootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE);
        add(LootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY);
        add(LootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY);
        //sniffer
        add(LootTables.SNIFFER_DIGGING);

    }};
    public static List<LootTables> NETHER_STRUCTURE_CHESTS=new ArrayList<>(){{
        add(LootTables.NETHER_BRIDGE);
        add(LootTables.BASTION_TREASURE);
        add(LootTables.BASTION_OTHER);
        add(LootTables.BASTION_BRIDGE);
        add(LootTables.BASTION_HOGLIN_STABLE);
        //PIGLING
        //this need piglin entity
        add(LootTables.PIGLIN_BARTERING);
    }};
    public static List<LootTables> END_STRUCTURE_CHESTS=new ArrayList<>(){{
        add(LootTables.END_CITY_TREASURE);
    }};

    protected static Class CraftBlockStateClass;
    protected static Field IBlockDataField;
    protected static Field BlockPositionField;
    protected static Field WorldField;
    protected static Field WeakWorldField;
    protected static boolean invokeBlockStateSuccess=false;
    static {
        try{
            Debug.debug( Bukkit.getBukkitVersion());
            World sampleWorld= Bukkit.getWorlds().get(0);
            Debug.debug(sampleWorld.getName());
            BlockState blockstate=sampleWorld.getBlockAt(0, 0, 0).getState();
            var result=ReflectUtils.getDeclaredFieldsRecursively(blockstate.getClass(),"data");
            IBlockDataField=result.getFirstValue();
            IBlockDataField.setAccessible(true);
            CraftBlockStateClass=result.getSecondValue();
            BlockPositionField=ReflectUtils.getDeclaredFieldsRecursively(CraftBlockStateClass,"position").getFirstValue();
            BlockPositionField.setAccessible(true);
            WorldField=ReflectUtils.getDeclaredFieldsRecursively(CraftBlockStateClass,"world").getFirstValue();
            WorldField.setAccessible(true);
            WeakWorldField=ReflectUtils.getDeclaredFieldsRecursively(CraftBlockStateClass,"weakWorld").getFirstValue();
            WeakWorldField.setAccessible(true);
            Debug.debug("CraftBlockStateClass: "+CraftBlockStateClass.getName());
            invokeBlockStateSuccess=true;
        }catch (Throwable e){
           Debug.logger(e);
        }
    }
    public static void setup(){

    }
    public static enum DustPreset{
        REDSTONE(Color.fromRGB(255,0,0),1.0f);

        Particle.DustOptions dustOptions;
        public Particle.DustOptions getOptions(){
            return dustOptions;
        }
        DustPreset(Color color,float size){
            dustOptions=new Particle.DustOptions(color,size);
        }
    }
    public static HashSet<Material> WATER_LOGGABLE_TYPES=new HashSet<>(){{
        for (Material m : Material.values()) {
            if(m.isBlock()){
                try{
                    BlockData defaultData= m.createBlockData();
                    if(defaultData instanceof Waterlogged){

                        add(m);
                    }
                }catch (Throwable e){
                    Debug.logger(e);
                }
            }
        }
    }};
    public static void setBlock(Block block, Material material) {

    }
    public static Location getBlockLocation(Location loc){
        return new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY(),loc.getBlockZ());
    }
    /**
     * this method no need to run sync
     * @param loc
     * @param force
     */
    public static void removeSlimefunBlock(Location loc, boolean force){
        removeSlimefunBlock(loc,null,force);
    }
    public static void removeSlimefunBlock(Location loc,Player player, boolean force) {
       if(force){
           CONTROLLER.removeBlock(loc);
           return;
       }else {
           BlockBreakEvent breakEvent =
                   new BlockBreakEvent(loc.getBlock(),player);
           Bukkit.getPluginManager().callEvent(breakEvent);


           if (breakEvent.isCancelled()) {
               return ;
           }
           CONTROLLER.removeBlock(loc);
       }
    }
    public static void removeSlimefunBlockSafe(Location loc){
    }
    public static void moveSlimefunBlock(Location loc, boolean force) {

    }

    /**
     * put arg hasSync to decide whether to create new sunc thread
     * @param loc
     * @param player
     * @param item
     * @param material
     * @param force
     * @param hasSync
     */
    public static ConcurrentHashMap<Location,SlimefunItem> CREATING_QUEUE = new ConcurrentHashMap<>();
    public static boolean createSlimefunBlock(Location loc,Player player,SlimefunItem item,Material material,boolean force){
        if(CREATING_QUEUE.containsKey(loc)){
            SlimefunItem item1 = CREATING_QUEUE.get(loc);
            if(item1==item){
                return false;
            }else {
                force=true;
            }
        }
        final boolean forceVal=force;
        CREATING_QUEUE.put(loc,item);
        BukkitUtils.executeSync(()->{
            try{
                createSlimefunBlockSync(loc,player,item,material,forceVal);
            }finally {
                CREATING_QUEUE.remove(loc);
            }

        });
        return true;
    }
    public static boolean createSlimefunBlockSync(Location loc,Player player,SlimefunItem item,Material material,boolean force){
        Block block = loc.getBlock();
        if(!force&&player!=null){
            if(!hasPermission(player,loc, Interaction.PLACE_BLOCK)){
                return false ;
            }
//            if( DataCache.getSfItem(loc)!=null){
//               Debug.logger("has block ,,, ",DataCache.getSfItem(loc));
//            }
            BlockBreakEvent breakEvent =
                    new BlockBreakEvent(block,player);
            Bukkit.getPluginManager().callEvent(breakEvent);
            if (breakEvent.isCancelled()) {
                return false;
            }
            breakEvent.setDropItems(false);
            if (!item.canUse(player, false)) {
                return false;
            }

        }
        if( DataCache.getSfItem(loc)!=null){
            CONTROLLER.removeBlock(loc);
        }
         BukkitUtils.executeSync(()->    createSlimefunBlockSync(loc, player, item, material));
        return true;
    }
    /**
     * this method must run sync
     * @param loc
     * @param item
     * @param material
     */
    public static void createSlimefunBlockSync(Location loc,Player player,SlimefunItem item,Material material) {
        Block block=loc.getBlock();

        block.setType(material);
        if (Slimefun.getBlockDataService().isTileEntity(block.getType())) {
            Slimefun.getBlockDataService().setBlockData(block, item.getId());
        }
        CONTROLLER.createBlock(loc, item.getId());
//        try{
//            var placeEvent = new SlimefunBlockPlaceEvent(player, item.getItem(), block, item);
//            Bukkit.getPluginManager().callEvent(placeEvent);
//        }catch (Throwable e){
//
//        }
    }
    public static boolean hasPermission( Player player, @Nonnull Block location, @Nonnull Interaction... interactions) {
        if(player==null)return true;
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(player, location, interaction)) {
                return false;
            }
        }
        return true;
    }
    public static boolean hasPermission( Player player, @Nonnull Location location, @Nonnull Interaction... interactions) {
        if(player==null)return true;
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(player, location, interaction)) {
                return false;
            }
        }
        return true;
    }
    public static boolean testAttackPermission(Player player, Damageable entity){
        //entity.damage(0,player);
        try{
            EntityDamageEvent event=new EntityDamageByEntityEvent(player,entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK,0.0);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled()){
                return false;
            }else return true;
        }catch (Throwable e){
            Debug.logger(e);
            return true;
        }

    }
    public static boolean consumeItem(Player player, ItemConsumer... consumers) {
        for (ItemConsumer consumer : consumers) {
            consumer.syncData();
        }
        ItemStack[] contents = player.getInventory().getContents();
        for(ItemStack item : contents){
            if(item==null||item.getType()==Material.AIR){
                continue;
            }else {
                ItemPusher pusher=null;
                for(ItemConsumer consumer : consumers){
                    if(consumer.getAmount()>0){
                        if(item.getType()==consumer.getItem().getType()){
                            if(pusher==null){
                                pusher=CraftUtils.getpusher.get(Settings.INPUT,item,-1);
                            }
                            if(CraftUtils.matchItemCore(pusher,consumer,false)){
                                consumer.consume(pusher);
                                break;
                            }
                        }else {
                            continue;
                        }

                    }
                }
            }
        }
        for(ItemConsumer consumer : consumers){
            if(consumer.getAmount()>0){
                for (ItemConsumer consumer2 : consumers) {
                    consumer2.syncData();
                }
                return false;
            }
        }
        for(ItemConsumer consumer : consumers){
            consumer.updateItems(null,Settings.GRAB);
        }
        return true;

    }
    /**
     * no need to sync
     * @param start
     * @param end

     * @param count
     */
    public static void doLineOperation(Location start,Location end,int count,Consumer<Location> task){
        if(count<=0)return;
        World world= start.getWorld();
        if(end.getWorld()!=world)return;
        if(count==1){
            task.accept(start);
        }else {
            Location walk=start.clone();
            double dx=(end.getX()-start.getX())/(count-1);
            double dy=(end.getY()-start.getY())/(count-1);
            double dz=(end.getZ()-start.getZ())/(count-1);
            for(int i=0;i<count;++i){
                task.accept(walk);
                walk.add(dx,dy,dz);
            }
        }
    }
    public static void spawnLineParticle(Location start, Location end, Particle type, int count){
        doLineOperation(start,end,count,(loc)->{
            loc.getWorld().spawnParticle(type,loc,0,0.0,0.0,0.0,1,null,true);
        });
    }
    private static final ItemStack effectivePickaxe = new ItemStack(Material.DIAMOND_PICKAXE);

//    public ItemStack[] simulateKill(){
//        Player player;
//    }
    //should be without movement
    public static int executeOnSameEntity(Entity entity, Consumer<Entity> execution){
        if(entity==null)return 0;
        return entity.getLocation().getChunk().getWorld().getNearbyEntities(entity.getLocation(),0.5,0.5,0.5,(entity1 -> {
            if(entity1!=null&&entity.getUniqueId().equals( entity1.getUniqueId())){
                execution.accept(entity1);
                return true;
            }
            return false;
        })).size();
    }
//    public static Vector getOrientations(Location loc){
//        loc.getDirection();
//    }
    public static HashSet<Entity> getEntityInDistance(Location location, double distance, Predicate<Entity> predicate){
        return new HashSet<Entity>( location.getWorld().getNearbyEntities(location,2*distance,2*distance,2*distance,(e)->{return e.getLocation().distance(location)<=distance&&predicate.test(e);}));
    }
    public static Pair<Integer,Location> rayTraceLocation(LivingEntity entity, double period,double  maxLimitedDistance, Predicate<Location> execution){
        if(entity==null)return null;
        return rayTraceLocation(entity.getLocation().getDirection(),entity.getEyeLocation(),period,maxLimitedDistance,execution);
    }
    public static Pair<Integer,Location> rayTraceLocation(Vector rayVector,Location startLocation, double period,double maxLimitedDistance, Predicate<Location> execution){
        rayVector= rayVector.normalize().multiply(period);
        Location walkLocation=startLocation.clone();
        int limitTryTime=(int)(maxLimitedDistance/period);
        int i=0;
        for(;i<limitTryTime;++i){
            walkLocation.add(rayVector);
            if(!execution.test(walkLocation)){
                break;
            }
        }
        return new Pair<Integer,Location>(i,walkLocation) ;
    }
    public static boolean isLightPassableBlock(Block block){
        Material material=block.getType();
        if(material==Material.AIR||material.isTransparent()||material==Material.WATER||material==Material.LAVA){
            return true;
        }else return false;
    }
    public static boolean isLiquid(Block block){
        Material material=block.getType();
        if(material==Material.WATER||material==Material.LAVA){
            return true;
        }else return false;
    }
    public static boolean isMaterialWaterLoggable(Material material){
        return WATER_LOGGABLE_TYPES.contains(material);
    }
    public static boolean isWaterLogged(Block block){
        Material type=block.getType();
        if(isMaterialWaterLoggable(type)){
            BlockData blockData=block.getBlockData();
            if(blockData instanceof Waterlogged wl){
                return wl.isWaterlogged();
            }else return false;
        }else{
            return false;
        }

    }
    //IF SF DATA EXISTS,SF BLOCK WILL ALSO BE BREAKED, MAY CAUSE PROBLEMS
    public static boolean breakVanillaBlockByPlayer(Block block,Player player,boolean hasCheckedProtection,boolean withDrop){
        if(hasCheckedProtection||WorldUtils.hasPermission(player,block.getLocation(),Interaction.BREAK_BLOCK)){
            if(block.getType()!=Material.AIR){
                BlockBreakEvent event=new BlockBreakEvent(block,player);
                Bukkit.getPluginManager().callEvent(event);
                if(event.isCancelled()){
                    return false;
                }
                event.setDropItems(withDrop);
                block.setType(Material.AIR);
                return true;
            }else return true;
        }else return false;
    }
    public static boolean moveVanillaBlockByPlayer(Block block1,Block block2,Player player,boolean checkFromPerms,boolean checkToPerms,boolean hasCheckedProtection,boolean applyPhysics){
        if(hasCheckedProtection||((!checkFromPerms||WorldUtils.hasPermission(player,block1,Interaction.BREAK_BLOCK))&&(!checkToPerms||WorldUtils.hasPermission(player,block2,Interaction.PLACE_BLOCK)))){
            if(checkFromPerms){
                BlockBreakEvent event=new BlockBreakEvent(block1,player);
                Bukkit.getPluginManager().callEvent(event);
                if(event.isCancelled()){
                    return false;
                }
                event.setDropItems(false);
            }
            block2.setType(block1.getType());
            block2.setBlockData(block1.getBlockData(),applyPhysics);
            block1.setType(Material.AIR);
            return true;
        }else return false;
    }
    public static boolean testVanillaBlockBreakPermission(Block block,Player player,boolean hasCheckedProtection){
        if(hasCheckedProtection||(WorldUtils.hasPermission(player,block,Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK))){
            BlockBreakEvent event=new BlockBreakEvent(block,player);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled()){
                return false;
            }
            event.setDropItems(false);
            return true;
        }else return false;
    }



    public static void forceLoadChunk(Location loc,int tick){
        int dx=loc.getBlockX()>>4;
        int dz=loc.getBlockZ()>>4;
        World world=loc.getWorld();
        Chunk chunk=world.getChunkAt(dx,dz);
        final boolean isForceload=chunk.isForceLoaded();
        chunk.setForceLoaded(true);
        Schedules.launchSchedules(()->{
            chunk.setForceLoaded(isForceload);
        },tick,true,0);
    }
    public static boolean isLivingEntity(Entity e){
        if(e.isValid()&&!e.isDead()&&e instanceof LivingEntity le&&!le.isInvulnerable()){
            return true;
        }return false;
    }
    public static Location getHandLocation(LivingEntity p){
        Location loc=p.getEyeLocation();
        Location playerLocation=p.getLocation();
        loc.add(playerLocation.subtract(loc).multiply(0.3).toVector());
        return loc;
    }
    public static boolean copyBlockState(BlockState state,Block block2){
        if(invokeBlockStateSuccess){
            BlockState state2=block2.getState();
            if(CraftBlockStateClass.isInstance(state2)&&CraftBlockStateClass.isInstance(state)){
                try{
                    BlockPositionField.set(state,BlockPositionField.get(state2));
                    WorldField.set(state,WorldField.get(state2));
                    WeakWorldField.set(state,WeakWorldField.get(state2));
                    state.update(true,false);
                    return true;
                }catch (Throwable e){
                    return false;
                }
            }else return false;
        }else return false;
    }
}
