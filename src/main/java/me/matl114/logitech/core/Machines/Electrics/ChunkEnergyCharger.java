package me.matl114.logitech.core.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Listeners.Listeners.SlimefunBlockPlaceLimitListener;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.core.Interface.ChunkLimit;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.WorldUtils;
import me.matl114.logitech.core.Machines.Abstracts.AbstractEnergyMachine;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChunkEnergyCharger extends AbstractEnergyCharger implements ChunkLimit {

    HashMap<Chunk, Location> MACHINE_POSITION=new HashMap<>();
    public HashMap<Chunk,Location> getRecords(){
        return MACHINE_POSITION;
    }

    public ChunkEnergyCharger(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                              int energybuffer){
        super(category, item, recipeType, recipe, energybuffer);
    }
    protected final String PARTICLE_TICK="pt";
    protected final int PERIOD=8;
    public Collection<SlimefunBlockData> getChargeRange(BlockMenu inv,Block block,SlimefunBlockData data){
        Location loc=block.getLocation();
        Schedules.launchSchedules(()->{
            int t;
            if((t=DataCache.getCustomData(data,PARTICLE_TICK,0))<PERIOD){
                DataCache.setCustomData(data,PARTICLE_TICK,t+1);
            }else {
                DataCache.setCustomData(data,PARTICLE_TICK,0);
                if(getStatus(inv)[1]){
                    WorldUtils.spawnLineParticle(loc.clone().add(0.5,0.5,0.5),loc.clone().add(0.5,128,0.5),Particle.SONIC_BOOM,80);
                }

            }
        },0,false,0);
        return DataCache.getAllSfItemInChunk(loc.getWorld(),loc.getBlockX()>>4,loc.getBlockZ()>>4);
    }
    private final int PARTICLE_SLOT=0;
    private ItemStack PARTICLE_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&a点击切换粒子效果","&7当前状态: &c关");
    private ItemStack PARTICLE_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a点击切换粒子效果","&7当前状态: &a开");
    @Override
    public boolean[] getStatus(BlockMenu inv) {
        ItemStack itemStack=inv.getItemInSlot(PARTICLE_SLOT);
        if(itemStack!=null&&itemStack.getType()== Material.GREEN_STAINED_GLASS_PANE){
            return new boolean[]{super.getStatus(inv)[0],true};
        }else {
            return new boolean[]{super.getStatus(inv)[0],false};
        }
    }

    @Override
    public void toggleStatus(BlockMenu inv, boolean... result) {
        super.toggleStatus(inv, result[0]);
        if(result.length==2){
            if(result[1]){
                inv.replaceExistingItem(PARTICLE_SLOT,PARTICLE_ON);
            }else {
                inv.replaceExistingItem(PARTICLE_SLOT,PARTICLE_OFF);
            }
        }
    }
    @Override
    public boolean isBorder(int i) {
        return super.isBorder(i)&&i!=PARTICLE_SLOT;
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        if(!onChunkPlace(menu.getLocation(),ChunkEnergyCharger.class)){
            onChunkReachLimit(menu.getLocation(),this,(str)->{menu.getLocation().getWorld().getNearbyEntities(menu.getLocation(),10,10,10,(e)->{
                if(e instanceof Player player){
                    player.sendMessage(str);
                }
                return false;
            });});
            return;
        }
        super.newMenuInstance(menu, block);
        ItemStack icon=menu.getItemInSlot(PARTICLE_SLOT);
        if(icon==null||(icon.getType()!=Material.RED_STAINED_GLASS_PANE&&icon.getType()!=Material.GREEN_STAINED_GLASS_PANE)){
            menu.replaceExistingItem(PARTICLE_SLOT,PARTICLE_OFF);
        }
        menu.addMenuClickHandler(PARTICLE_SLOT,((player, i, itemStack, clickAction) -> {
            boolean[] t=getStatus(menu);
            toggleStatus(menu,t[0],!t[1]);
            return false;
        }));
    }
    protected boolean isChargeable(SlimefunItem that){
        return !(that instanceof AbstractEnergyMachine);
    }
    public int getMaxChargeAmount(){
        return 114514;
    }
    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        onChunkBreak(menu.getLocation());
    }
    public void preRegister(){
        super.preRegister();
        SlimefunBlockPlaceLimitListener.registerBlockLimit(this,(event)->{
            Location loc=event.getBlockPlaced().getLocation();
            if(!onChunkPlace(loc,ChunkEnergyCharger.class)){
                onChunkReachLimit(loc,this,(str)-> {
                    if (event.getPlayer() != null) {
                        event.getPlayer().sendMessage(str);
                    }
                });
                event.setCancelled(true);
            }
        });
    }





}
