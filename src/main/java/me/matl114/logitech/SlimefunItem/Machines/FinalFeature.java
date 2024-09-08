package me.matl114.logitech.SlimefunItem.Machines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.SlimefunItem.Blocks.Laser;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlock.FinalAltarCore;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.Settings;
import me.matl114.logitech.Utils.UtilClass.CargoClass.Directions;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemPusherProvider;
import me.matl114.logitech.Utils.UtilClass.StorageClass.ItemStorageCache;
import me.matl114.logitech.Utils.UtilClass.StorageClass.LocationStorageProxy;
import me.matl114.logitech.Utils.UtilClass.StorageClass.StorageType;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.function.IntFunction;

public class FinalFeature {
    public static ItemPusherProvider STORAGE_READER =new ItemPusherProvider() {
        @Override
        public ItemPusher get(Settings mod, ItemStack item, int slot) {
            if(item==null||item.getAmount()!=1){
                return CraftUtils.getpusher.get(mod,item,slot);
            }
            //not null.
            ItemPusher tar=null;
            ItemMeta meta=null;
            if(item.getAmount()==1){
                meta=item.getItemMeta();
                //只生成不是代理的缓存
                tar= ItemStorageCache.get(item,meta,slot,(i->!i.isStorageProxy()));
            }
            //get no yet
            if(tar==null){
                tar= CraftUtils.getpusher.get(mod,item,slot);
                if(meta!=null){//make a convienience
                    tar.setMeta(meta);
                }
            }
            return tar;
        }
    };



    public static ItemPusherProvider STORAGE_AND_LOCPROXY_READER=new ItemPusherProvider() {
        @Override
        public ItemPusher get(Settings mod, ItemStack item, int slot) {
            if(item==null||item.getAmount()!=1){
                return CraftUtils.getpusher.get(mod,item,slot);
            }
            //not null.
            ItemPusher tar=null;
            ItemMeta meta=null;
            if(item.getAmount()==1){
                meta=item.getItemMeta();
                //全部生成 全部启动
                tar= ItemStorageCache.get(item,meta,slot,i->true);
            }
            //get no yet
            if(tar==null){
                tar= CraftUtils.getpusher.get(mod,item,slot);
                if(meta!=null){//make a convienience
                    tar.setMeta(meta);
                }
            }
            return tar;
        }
        @Override
        public IntFunction<ItemPusher> getMenu(Settings settings, BlockMenu inv, IntFunction<ItemStack> stackFunction, int[] slots){
            return new IntFunction<ItemPusher>() {
                HashSet<Location> proxyLocation;
                public ItemPusher apply2(int slot){
                    long a,b;
                    a=System.nanoTime();
                    ItemPusher tar=apply(slot);
                    b=System.nanoTime();
                    Debug.debug("apply menu time ",b-a," on slot ",slot,", amount ",tar.getAmount());
                    return tar;

                }

                public ItemPusher apply(int slot) {

                    ItemPusher tar= get(settings,stackFunction.apply(slot),slots[slot]);
                    if(tar instanceof LocationStorageProxy lsp){
                        if(proxyLocation==null){
                            proxyLocation=new HashSet<>();
                        }
                        //禁止在一次模拟中制造超过两个连接
                        Location loc=lsp.getProxyLocation();
                        if(proxyLocation.contains(loc)){
                            //视为正常物品 不参与存储代理

                            return CraftUtils.getpusher.get(settings,stackFunction.apply(slot),slots[slot]);
                        }else {
                            proxyLocation.add(loc);
                        }
                    }
                    return tar;
                }
            };
        }

    };
    public static boolean isFinalAltarCharged(SlimefunItem that, SlimefunBlockData data){
        if(that instanceof FinalAltarCore.FinalAltarChargable fac&&that instanceof Laser.LaserChargable lc){
            if(fac.mayForced(data)>=2){
                int t;
                if((t=lc.hasCharged(data,Directions.EAST))>0){
                    Laser.LaserChargable.setCharged(data,Directions.EAST,t-1);
                }else {
                    return false;
                }
                if((t=lc.hasCharged(data,Directions.WEST))>0){
                    Laser.LaserChargable.setCharged(data,Directions.WEST,t-1);
                }else {
                    return false;
                }
                if((t=lc.hasCharged(data,Directions.NORTH))>0){
                    Laser.LaserChargable.setCharged(data,Directions.NORTH,t-1);
                }else {
                    return false;
                }
                if((t=lc.hasCharged(data,Directions.SOUTH))>0){
                    Laser.LaserChargable.setCharged(data,Directions.SOUTH,t-1);
                }else {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
