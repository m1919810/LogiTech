package me.matl114.logitech.core.Cargo.SpaceStorage;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.ConfigLoader;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.Utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.util.*;

public class StorageSpace {
    public static String WORLD_NAME = "logispace";
    public static World STORAGE_WORLD;
    public static int WORLD_MIN_Y =0;
    public static int WORLD_MAX_Y =128;
    public static int STATUS_2_MIN_Y=65;
    public static int STATUS_1_MIN_Y=1;
    public static int STORAGE_DISTANCE=100;
    public static int STORAGE_MAXSIZE=63;
    protected static String FORMAT_LOC="{%d},{%d},{%d},{%d},{%d}";
    public static boolean ENABLED = true;

    static {
        if(!ConfigLoader.CONFIG.contains("options.disable-spacestorage")){
            ConfigLoader.CONFIG.setValue("options.disable-spacestorage",false);
            ConfigLoader.CONFIG.save();
        }
        else if(ConfigLoader.CONFIG.getBoolean("options.disable-spacestorage")){
            ENABLED = false;
        }


    }
    public static void setup(){
        if(ENABLED){
            try{
                WorldCreator worldCreator = new WorldCreator(WORLD_NAME);
                worldCreator.generator(new StorageWorldGen());
                worldCreator.generateStructures(false);
                STORAGE_WORLD = worldCreator.createWorld();
                STORAGE_WORLD.setGameRule(GameRule.DO_MOB_SPAWNING,false);
                STORAGE_WORLD.setGameRule(GameRule.MOB_GRIEFING,false);
                STORAGE_WORLD.setGameRule(GameRule.KEEP_INVENTORY,true);
            }catch(Throwable e){
                e.printStackTrace();
                ENABLED=false;
            }
        }
    }
    public static class StorageWorldGen extends ChunkGenerator {
        public ChunkData generateChunkData(World world, Random random, int chunkx, int chunkz, BiomeGrid biomeGrid){
            ChunkData chunkData=createChunkData(world);
            chunkData.setRegion(0, WORLD_MIN_Y,0,16, WORLD_MAX_Y,16, Material.BARRIER);
            return chunkData;
        }
    }
    //about Configs and data storage
    public static Pair<Integer, Location> generateNewLocation(int sizeX, int sizeY, int sizeZ){
        int num=ConfigLoader.SPACE_STORAGE.getOrSetDefault("index",0)+1;
        ConfigLoader.SPACE_STORAGE.setValue("index", num);
        ConfigLoader.SPACE_STORAGE.save();
        int k=(((int)(Math.sqrt(num)-1))/2)+1;
        int l=num-(2*k-1)*(2*k-1);
        //4k^2-4k+1 -> 4k^2+4k+1
        //8k+8
        int period=l/k;
        int step=l%k;
        int dx;
        int dz;
        switch (period){
            case 0: dx=k; dz=step;break ;
            case 1: dx=(k-step);dz=k;break;
            case 2: dx=-step;dz=k;break;
            case 3: dx=-k;dz=(k-step);break;
            case 4: dx=-k;dz=-step;break;
            case 5: dx=-k+step;dz=-k;break ;
            case 6: dx=step;dz=-k;break;
            default:
                dx=k;dz=-k+step;break;

        }
        dx*=STORAGE_DISTANCE;
        dz*=STORAGE_DISTANCE;
        ConfigLoader.SPACE_STORAGE.setValue("storages."+num+".loc",String.join(",",
                String.valueOf(dx),String.valueOf(dz),String.valueOf(sizeX),String.valueOf(sizeY),String.valueOf(sizeZ)) );
        ConfigLoader.SPACE_STORAGE.setValue("storages."+num+".status",0);
        ConfigLoader.SPACE_STORAGE.save();
        return new Pair<>(num, new Location(STORAGE_WORLD,dx,0,dz));
    }

    //about execution
    //should execute Async
    public static boolean threadRunning(){
        synchronized (COUNTER_LOCK){
            return SYNC_THREAD_COUNTER>0;
        }
    }
    public static void onSpaceExchange(Player player, Location startLocation,Location endLocation,Location targetLocation,int status,boolean checkPerms){
        BukkitUtils.executeAsync(()->onSpaceExchangeAsync(player,startLocation,endLocation,targetLocation,status,checkPerms));
    }

    public static byte[] COUNTER_LOCK=new byte[0];
    public static int SYNC_THREAD_COUNTER=0;
    //make sure startlocation<endlocation
    //actually we find that
    //事实上想要挪动粘液方块而不造成刷物是不可能的
    //因为挪动的时候不能调用onBlockBreak
    //但是很多操作都在onBlockBreak
    //menu内含物掉落和这些操作在一起
    private static void onSpaceExchangeAsync(Player player, Location startLocation,Location endLocation,Location storeStartLocation,int status,boolean checkPerms){
        //存入的位置 status=1->上面 status变成2  status=2->下面 status变成1
        synchronized (COUNTER_LOCK){
            SYNC_THREAD_COUNTER++;
        }
        Location depositLocation = storeStartLocation.clone();
        depositLocation.setY(status==1?STATUS_2_MIN_Y:STATUS_1_MIN_Y);
        Location withDrawLocation = storeStartLocation.clone();
        withDrawLocation.setY(status==1?STATUS_1_MIN_Y:STATUS_2_MIN_Y);
//        Debug.logger(depositLocation);
//        Debug.logger(withDrawLocation);
        int d1=storeStartLocation.getBlockX();
        int h=depositLocation.getBlockY();
        int h2=withDrawLocation.getBlockY();
        int d2=storeStartLocation.getBlockZ();
        int x = endLocation.getBlockX()- startLocation.getBlockX();
        int y = endLocation.getBlockY()- startLocation.getBlockY();
        int z = endLocation.getBlockZ()- startLocation.getBlockZ();
        int chunkx=startLocation.getBlockX()>>4;
        int chunkz=startLocation.getBlockZ()>>4;
        int chunkx_=endLocation.getBlockX()>>4;
        int chunkz_=endLocation.getBlockZ()>>4;
        //提前载入sfBlocks获取可能得位置，防止反复访问造成的开销
        HashSet<Location> sfBlocklocations=new HashSet<>();
        for(int i=chunkx;i<=chunkx_;++i){
            for(int j=chunkz;j<=chunkz_;++j){
                HashSet<SlimefunBlockData> data=DataCache.getAllSfItemInChunk(startLocation.getWorld(),i,j);
                sfBlocklocations.addAll(data.stream().map((datas)->datas.getLocation()).toList());
            }
        }
        Debug.debug("sf block collected");
        //8x8x8为一个单位发起替换线程
        for (int dx=0;dx<x;dx+=8){
            for (int dy=0;dy<y;dy+=8){
                for (int dz=0;dz<z;dz+=8){
                    final int dx_=dx;
                    final int dy_=dy;
                    final int dz_=dz;
                    synchronized (COUNTER_LOCK){
                        SYNC_THREAD_COUNTER++;
                    }
                    Schedules.launchSchedules(()->{
                        HashMap<Block,Block> depositePairs=new HashMap<>();
                        HashMap<Block,Block> withdrawPairs=new HashMap<>();
                        HashSet<Block> changedBlocks=new HashSet<>();

                        for(int i=0;dx_+i<x && i<8;++i){
                            for(int j=0;dy_+j<y && j<8;++j){
                                for(int k=0;dz_+k<z && k<8;++k){
                                    Location originLocation=startLocation.clone().add(dx_+i,dy_+j,dz_+k);
                                    //先进行简单权限检测 剔除保护module里保护的内容
                                    //这里保证100%没有粘液方块
                                    if(!sfBlocklocations.contains(originLocation)&&(!checkPerms||WorldUtils.hasPermission(player,originLocation, Interaction.PLACE_BLOCK,Interaction.BREAK_BLOCK))){
                                        Block block1=originLocation.getBlock();
                                        Block block2=STORAGE_WORLD.getBlockAt(d1+dx_+i,h+dy_+j,d2+dz_+k);
                                        Material material=block1.getType();
                                        Material material2=block2.getType();
                                        boolean haveToChange=false;
                                        //将非空方块转移 同时删除存储空间中已有的方块
                                        if((material!=Material.AIR||(material2!=Material.AIR&&material2!=Material.BARRIER))){
                                            depositePairs.put(block1, block2);
                                            haveToChange=true;
                                        }
                                        Block block3=STORAGE_WORLD.getBlockAt(d1+dx_+i,h2+dy_+j,d2+dz_+k);
                                        Material material3=block3.getType();
                                        if(material3!=Material.AIR&&material3!=Material.BARRIER){
                                            withdrawPairs.put(block1, block3);
                                            haveToChange=true;
                                        }
                                        if(haveToChange){
                                            changedBlocks.add(block1);
                                        }
                                    }

                                }
                            }
                        }
                        //当有任务的时候 发起方块交换任务
                        if(!depositePairs.isEmpty()||!withdrawPairs.isEmpty()){

                            Schedules.launchSchedules(()->{
                                try{
                                    for(Block b1:changedBlocks){
                                        Block b2=depositePairs.get(b1);
                                        Block b3=withdrawPairs.get(b1);
                                        if(!checkPerms||WorldUtils.testVanillaBlockBreakPermission(b1,player,true)){
                                            //FIXME blockEntity not moved
                                            //FIXME how?
                                            if (b2 == null) {
                                                if(!WorldUtils.copyBlockState(b3.getState(),b1)){
                                                    AddUtils.sendMessage(player,"&e转移方块数据时出现未知错误,请联系管理员查询日志历史");
                                                    continue;
                                                }
                                                b3.setType(Material.AIR,false);
                                            }else if(b3==null){
                                                if(!WorldUtils.copyBlockState(b1.getState(),b2)){
                                                    AddUtils.sendMessage(player,"&e转移方块数据时出现未知错误,请联系管理员查询日志历史");
                                                    continue;
                                                }
                                                b1.setType(Material.AIR,false);
                                            }else {
                                                if(!WorldUtils.copyBlockState(b1.getState(),b2)){
                                                    AddUtils.sendMessage(player,"&e转移方块数据时出现未知错误,请联系管理员查询日志历史");
                                                    continue;
                                                }
                                                if(!WorldUtils.copyBlockState(b3.getState(),b1)){
                                                    AddUtils.sendMessage(player,"&e转移方块数据时出现未知错误,请联系管理员查询日志历史");
                                                    continue;
                                                }
                                                b3.setType(Material.AIR,false);
                                            }
                                        }
                                    }
                                }finally {
                                    synchronized (COUNTER_LOCK){
                                        SYNC_THREAD_COUNTER-=1;
                                    }
                                }
                            },0,true,0);
                        }else{
                            synchronized (COUNTER_LOCK){
                                SYNC_THREAD_COUNTER-=1;
                            }
                        }
                    },0,false,0);
                }
            }
        }
        boolean hasFinished=false;
        for(int i=0;i<300;++i){
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            synchronized (COUNTER_LOCK){
                if(SYNC_THREAD_COUNTER<=1){
                    hasFinished=true;

                }
            }
            if(hasFinished){
                AddUtils.sendMessage(player,"&a空间IO操作执行完毕!");
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (COUNTER_LOCK){
                    SYNC_THREAD_COUNTER=0;
                }
                return;
            }
        }
        if(!hasFinished){
            Debug.logger("StorageSpace的线程计数器出现异常状态!疑似出现线程死循环,请联系作者");
        }
        SYNC_THREAD_COUNTER=0;
    }
}
