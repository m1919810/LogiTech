package me.matl114.logitech.core.Blocks;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Listeners.Listeners.SlimefunBlockPlaceLimitListener;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.TickerClass.Ticking;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SpreadBlock extends AbstractBlock implements Ticking {
    protected final  SlimefunItem RESULT;
    protected final  Material SPREAD_MATERIAL;
    protected final  Material RESULT_MATERIAL;
    public SpreadBlock(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,SlimefunItem result,
                       Material spreadMaterial,Material finalMaterial) {
        super(itemGroup, item, recipeType, recipe);
        this.RESULT = result;
        this.SPREAD_MATERIAL = spreadMaterial;
        this.RESULT_MATERIAL = finalMaterial;
        this.setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow("&f机制 - &c同化",
                                "&7该物品拥有着将邻接的方块同化为自身的能力",
                                "&7方块尝试同化邻接方块的行为,我们称之为一次同化",
                                "&7被玩家放下或者被同化出来的方块会进行一次同化",
                                "&a进行同化行为后该位置的方块将将不会继续同化,也不会被同化",
                                "&7在随机的时长后被该位置的方块将会转变为目标物品",
                                "&e当转变为目标物品时,将有%d%%的概率不改变方块材质".formatted(CHANCE_KEEP_MATERIAL)),AddUtils.addLore( result.getItem(),"&a同化最终的结果"),
                        AddUtils.getInfoShow("&f机制 - &c反能量",
                                "&7该物品同化的能量来源是所谓的\"反能量值\"",
                                "&7当玩家放下时候,方块反能量值为%d".formatted(LIFE_DEFAULT),
                                "&7被同化出的方块将会继承同化者的反能量值,并有60%的概率衰减1",
                                "&7同化进行后的方块每sft将有60%的概率将自身反能量值衰减1",
                                "&7当反能量值为0的时候,方块将转变为目标物品"),null,
                        AddUtils.getInfoShow("&f机制 - &c数量阈值",
                                "&7整个服务器中最多有%d个方块同时进行同化行为".formatted(ONE_TICK_SPREAD_MAXCNT),
                                "&7当超过该阈值后余下的方块将&c停止运行",
                                "&a因此该物品并不会对服务器tps造成影响")
                )
        );
    }
    protected Random rand = new Random();
    protected final int LIFE_DEFAULT=13;
    protected final int ONE_TICK_SPREAD_MAXCNT=1600;
    protected final int CHANCE_KEEP_MATERIAL=2;
    protected final ConcurrentHashMap<Location, Player> SPREAD_PLAYER=new ConcurrentHashMap<>();
    public Map<Location,Player> getSpreadOwner(){
        return SPREAD_PLAYER;
    }
    protected final ConcurrentHashMap<Location,Integer> SPREAD_TICKER=new ConcurrentHashMap<>();
    protected final Set<Location> PENDING_CREATE = ConcurrentHashMap.newKeySet();
    public Map<Location,Integer> getSpreadTicker(){
        return SPREAD_TICKER;
    }
    protected final Vector[] SPREAD_DELTA=new Vector[]{
            new Vector(1,0,0),
            new Vector(0,0,1),
            new Vector(-1,0,0),
            new Vector(0,0,-1),
            new Vector(0,1,0),
            new Vector(0,-1,0),
    };
    public void createResultAt(Location loc){
        Player player=SPREAD_PLAYER.remove(loc);
        SPREAD_TICKER.remove(loc);
        WorldUtils.createSlimefunBlock(loc,player,RESULT,(rand.nextInt(100)<(100-CHANCE_KEEP_MATERIAL))? RESULT_MATERIAL:SPREAD_MATERIAL,true,()->{});
    }
    public void registerTick(SlimefunItem item){
        item.addItemHandler(
                new BlockTicker() {
                    int runPerTick=0;
                    public boolean isSynchronized() {
                        return isSync();
                    }

                    @ParametersAreNonnullByDefault
                    public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
                        if(runPerTick>ONE_TICK_SPREAD_MAXCNT)return;
                        Location loc = data.getLocation();
                        if(PENDING_CREATE.contains(loc)){
                            return;
                        }
                        Integer life=SPREAD_TICKER.get(loc);
                        if(life==null||life==0){
                            createResultAt(loc);
                        }
                        else if(life<0){
                            SPREAD_TICKER.put(loc, life+rand.nextInt(100)<60?1:0);
                        }
                        else {
                            int lifeToSet=life-(rand.nextInt(100)<60?1:0);
                            Player player=SPREAD_PLAYER.get(loc);
                            for (int i=0;i<SPREAD_DELTA.length;i++){
                                Location loc2=loc.clone().add(SPREAD_DELTA[i]);
                                if(SPREAD_TICKER.containsKey(loc2)){
                                    continue;
                                }
                                SlimefunItem it=DataCache.getSfItem(loc2);
                                if(it!=SpreadBlock.this&&it!=RESULT&&!loc2.getBlock().getType().isAir()){
                                    runPerTick++;
                                    PENDING_CREATE.add(loc2);
                                    WorldUtils.createSlimefunBlock(loc2,player,SpreadBlock.this, SPREAD_MATERIAL,false,()->{
                                        //create after permission check ,if fail, no create
                                        SPREAD_TICKER.put(loc2,lifeToSet);
                                        SPREAD_PLAYER.put(loc2,player);
                                        PENDING_CREATE.remove(loc2);
                                    });
                                }
                            }
                            SPREAD_TICKER.put(loc, -life+1);
                        }
                    }

                    @Override
                    public void uniqueTick() {
                        this.runPerTick=0;
                    }
                }


        );
    }
    public void tick(Block b, @Nullable BlockMenu menu, SlimefunBlockData data, int tickCount){

    }
    public void onPlace(BlockPlaceEvent e, Block b) {
        SPREAD_PLAYER.put(b.getLocation(), e.getPlayer());
        SPREAD_TICKER.put(b.getLocation(),LIFE_DEFAULT);
        b.setType(SPREAD_MATERIAL);
    }

    public boolean isSync(){
        return false;
    }
    public void preRegister(){
        super.preRegister();
        registerTick(this);
        handleBlock(this);
        SlimefunBlockPlaceLimitListener.registerBlockLimit(this,(event)->{
            if(!this.SPREAD_TICKER.isEmpty()){
                reCheckSpreadTicker();
                if(!this.SPREAD_TICKER.isEmpty()){
                    event.setCancelled(true);
                    if(event.getPlayer()!=null){
                        AddUtils.sendMessage(event.getPlayer(),"&c不能同时存在超过1个[%s]&c的扩散进程".formatted(this.getItem().getItemMeta().getDisplayName()));
                    }
                }
            }
        });
    }
    public void reCheckSpreadTicker(){
        var iter = SPREAD_TICKER.entrySet().iterator();
        while(iter.hasNext()){
            var entry = iter.next().getKey();
            if(DataCache.getSfItem(entry)!=this){
                iter.remove();
            }
        }
    }

}
