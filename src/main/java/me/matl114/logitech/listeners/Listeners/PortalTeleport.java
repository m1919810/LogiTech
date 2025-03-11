package me.matl114.logitech.listeners.Listeners;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.core.Blocks.MultiBlock.PortalCore;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalTeleport implements Listener {
    public final int[] dx=new int[]{0,1,0,-1,0,1,1,-1,-1};
    public final int[] dz=new int[]{0,0,1,0,-1,1,-1,-1,1};
    @EventHandler
    //沉浸式体验传送门 abab
    public void onTeleportRedirect(PlayerPortalEvent event) {
        Location loco = event.getFrom();
        int x = loco.getBlockX();
        int y = loco.getBlockY()-1;
        int z = loco.getBlockZ();
        loco=new Location(loco.getWorld(),x,y,z);
        Location loc;
        for(int i=0;i<9;++i) {
            loc=loco.clone().add(dx[i],0,dz[i]);
            if(loc.getBlock().getType()!= Material.CRYING_OBSIDIAN){
                continue;
            }
            SlimefunItem it = DataCache.getSfItem(loc) ;
            if (it instanceof PortalCore) {
                Player player = event.getPlayer();
                player.setPortalCooldown(60);
                event.setCancelled(true);
                //todo left as not done
                Location dst = ((PortalCore) it).checkLink(loc);
                if(dst!=null){
                    SlimefunBlockData destinationData=DataCache.safeGetBlockCacheWithLoad(dst);
                    if(destinationData!=null&&SlimefunItem.getById(destinationData.getSfId()) instanceof PortalCore pc){
                        if(destinationData.isDataLoaded()){
                            if (MultiBlockService.getStatus(destinationData)!=0) {
                                player.teleport(dst.clone().add(0.5, 1, 0.5));
                                AddUtils.sendMessage(player, "&a传送成功!");
                            } else {
                                AddUtils.sendMessage(player, "&c目标传送门未启用!");
                            }
                        }else{
                            AddUtils.sendMessage(player, "&c目标传送门正在随区块加载中!请重试");
                        }
                    }else{
                        AddUtils.sendMessage(player, "&c目标传送门核心已损坏!");
                    }
                }else {
                    AddUtils.sendMessage(player, "&c该传送门中并无有效坐标!");
                }

                return;
            }
        }

    }

    @EventHandler
    public void onTeleportRedirectEntity(EntityPortalEnterEvent event) {
       // Debug.logger("event called ");

        Entity player=event.getEntity();
        if(player==null) return;
        else if (event.getEntity() instanceof Player) {
            return;
        }
        else  if(event.getEntity().getPortalCooldown()>0) {
            return;
        }
        Location loco = event.getLocation();
        Location loc;
        loc=loco.clone().add(0,-1,0);
        if(loc.getBlock().getType()!= Material.CRYING_OBSIDIAN){
            return;
        }
        SlimefunItem it = DataCache.getSfItem(loc);
        if (it instanceof PortalCore) {
            player.setPortalCooldown(180);

            Location dst = ((PortalCore) it).checkLink(loc);
            if(dst!=null){
                SlimefunBlockData destinationData=DataCache.safeGetBlockCacheWithLoad(dst);
                if(destinationData!=null&&SlimefunItem.getById(destinationData.getSfId()) instanceof PortalCore pc){
                    if(destinationData.isDataLoaded()){
                        if (MultiBlockService.getStatus(destinationData)!=0) {
                            for(int i=4;i>=0;--i) {
                                //尽可能丢到门外面 如果没地方就传送到门里面
                                Location loc2=(dst.clone().add(0.5+dx[i], 1, 0.5+dz[i]));
                                if(loc2.getBlock().getType().isAir()||i==0){
                                    //  Debug.logger("check loc ",i);
                                    player.teleport(loc2);
                                    break;
                                }
                            }
                            return;
                        }
                    }
                }
            }


            //  Debug.logger("info");

            if (dst != null&&MultiBlockService.safeGetStatus(dst)!=0) {


            }
        }

    }
}
