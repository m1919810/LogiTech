package me.matl114.logitech.Utils.UtilClass.MultiBlockClass.CubeMultiBlock;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.NotImplementedException;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.FunctionalClass.OutputStream;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlock;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockType;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlock;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 该方块适用与由一个"底部"
 * 若干层 "盘" 和一个"顶部" 构成的机器
 * 它应当生成对应的多方块实例
 * 我们希望它可以兼容到之前的handler里
 */
public abstract class CubeMultiBlockType implements AbstractMultiBlockType {
    private HashMap<BlockVector,String> BOTTOM_MAP;
    private HashMap<BlockVector,String> PLATE_MAP;
    private HashMap<BlockVector,String> TOP_MAP;
    protected BlockVector[] BOTTOM_LOC;
    protected String[] BOTTOM_IDS;
    protected BlockVector[] PLATE_LOC;
    protected String[] PLATE_IDS;
    protected BlockVector[] TOP_LOC;
    protected String[] TOP_IDS;
    protected boolean isFinal=false;
    protected int sizeB;
    protected int sizeP;
    protected int sizeT;
    protected int defaultSize;
    protected int maxHeight=8;
    protected boolean isSymmetric=false;


    public abstract void init();
    public CubeMultiBlockType() {
        this.BOTTOM_MAP = new LinkedHashMap<BlockVector,String>();
        this.PLATE_MAP=new LinkedHashMap<>();
        this.TOP_MAP=new LinkedHashMap<>();
    }

    public BlockVector getSchemaPart(int index){
        return index<this.sizeB?BOTTOM_LOC[index].clone():((index<this.sizeB+this.sizeP)?PLATE_LOC[index-this.sizeB].clone():TOP_LOC[index-this.sizeP-this.sizeB].clone());
    }


    public String getSchemaPartId(int index) {
        return index<this.sizeB?BOTTOM_IDS[index]:((index<this.sizeB+this.sizeP)?PLATE_IDS[index-this.sizeB]:TOP_IDS[index-this.sizeP-this.sizeB]);
    }
    public int getSchemaSize(){
        return this.defaultSize;
    }
    public boolean isSymmetric(){
        return isSymmetric;
    }
    public CubeMultiBlockType build(){
        init();
        isFinal=true;
        this.sizeB=BOTTOM_MAP.size();
        this.sizeT=TOP_MAP.size();
        this.sizeP=PLATE_MAP.size();
        this.BOTTOM_LOC=new BlockVector[this.sizeB];
        this.BOTTOM_IDS=new String[this.sizeB];
        int i=0;
        for(Map.Entry<BlockVector,String> entry : BOTTOM_MAP.entrySet()){
            this.BOTTOM_IDS[i]=entry.getValue();
            this.BOTTOM_LOC[i]=entry.getKey();
            ++i;
        }
        this.PLATE_LOC=new BlockVector[this.sizeP];
        this.PLATE_IDS=new String[this.sizeP];
        int i1=0;
        for(Map.Entry<BlockVector,String> entry : PLATE_MAP.entrySet()){
            this.PLATE_IDS[i1]=entry.getValue();
            this.PLATE_LOC[i1]=entry.getKey();
            ++i1;
        }
        this.TOP_LOC=new BlockVector[this.sizeT];
        this.TOP_IDS=new String[this.sizeT];
        int i2=0;
        for(Map.Entry<BlockVector,String> entry :TOP_MAP.entrySet()){
            this.TOP_IDS[i2]=entry.getValue();
            this.TOP_LOC[i2]=entry.getKey();
            ++i2;
        }
        this.BOTTOM_MAP.clear();
        this.PLATE_MAP.clear();
        this.TOP_MAP.clear();
        this.defaultSize=this.sizeB+this.sizeP+this.sizeT;
        return this;
    }
    public CubeMultiBlockType addBottom(int x, int y, int z,String id) {
        if(x==0 && y==0 && z==0){
            return this;
        }
        BOTTOM_MAP.put(new BlockVector(x,y,z),id);
        return this;
    }
    public CubeMultiBlockType addPlate(int x, int y, int z,String id) {
        if(x==0 && y==0 && z==0){
            return this;
        }
        PLATE_MAP.put(new BlockVector(x,y,z),id);
        return this;
    }
    public CubeMultiBlockType addTop(int x, int y, int z,String id) {
        if(x==0 && y==0 && z==0){
            return this;
        }
        TOP_MAP.put(new BlockVector(x,y,z),id);
        return this;
    }



    public AbstractMultiBlock genMultiBlockFrom(Location loc, MultiBlockService.Direction dir, boolean hasPrevRecord, OutputStream errorOut){
        int len=this.sizeB;
        //底部匹配
        for(int i=0;i<len;i++){
            Vector delta= dir.rotate(BOTTOM_LOC[i].clone());
            Location partloc=loc.clone().add(delta);
            //Debug.logger("check blockid ",i,this.getSchemaPartId(i),"check target id ",MultiBlockService.safeGetPartId(partloc));
            //如果当前匹配 且并未在响应任意其他的多方块才能通过，否则任意一条均为false
            if(!this.BOTTOM_IDS[i].equals(MultiBlockService.safeGetPartId(partloc))){
                //  Debug.logger("wrong at ",delta.toString());
                int a=i;
                errorOut.out(()->{
                    return "&c位于%s的多方块部件并不是有效的底部部件,需要%s".formatted(DataCache.locationToDisplayString(partloc),this.BOTTOM_IDS[a]);
                });
                return null;
            }else{
                //use record but target block uuid not match core uuid
//                if(hasPrevRecord&&(!(id.equals( MultiBlockService.safeGetUUID(partloc))))){
//                    //    Debug.logger("wrong at ",delta.toString());
//                    errorOut.out(()->"&c错误!你不该看见该消息,请联系作者");
//                    return null;
//                    //no record but target block has been occupied by sth
//                }else
                if(!hasPrevRecord&&MultiBlockService.validHandler(MultiBlockService.safeGetUUID(partloc))){//如果是当前已经注册的别的机器的
                    // Debug.logger("wrong at ",delta.toString());
                    errorOut.out(()->{return "&c结构冲突!位于%s的部件属于另个机器! id-%s,位于%s".formatted(DataCache.locationToDisplayString(partloc),MultiBlockService.safeGetUUID(partloc),DataCache.locationToDisplayString( MultiBlockService.getCore(MultiBlockService.safeGetUUID(partloc))));});
                    return null;
                }
            }
        }
        boolean hasStop=false;
        int height=0;
        int len2=this.sizeP;
        Vector[] rotatedVector=new Vector[len2];
        search:
        do{
            Vector delta;
            for(int i=0;i<len2;i++){
                if(height==0){
                    rotatedVector[i]= dir.rotate(PLATE_LOC[i].clone());
                }
                delta=rotatedVector[i];
                Location partloc=loc.clone().add(delta).add(0,height,0);
                //Debug.logger("check blockid ",i,this.getSchemaPartId(i),"check target id ",MultiBlockService.safeGetPartId(partloc));
                //如果当前匹配 且并未在响应任意其他的多方块才能通过，否则任意一条均为false
                if(!this.PLATE_IDS[i].equals(MultiBlockService.safeGetPartId(partloc))){
                    //  Debug.logger("wrong at ",delta.toString());
                    final int endHeight=height+1;
                    final int endI=i;
                    errorOut.out(()->{
                        return "&c位于%s的多方块部件并不是有效的顶层部件,当前搜索中的中间层高度:%s,需要%s;终止向上延申".formatted(DataCache.locationToDisplayString(partloc),String.valueOf(endHeight),this.PLATE_IDS[endI]);
                    });
                    break search;
                }else{
                    //use record but target block uuid not match core uuid
//                    if(hasPrevRecord&&(!(id.equals( MultiBlockService.safeGetUUID(partloc))))){
//                        //    Debug.logger("wrong at ",delta.toString());
//                        break search;
//                        //no record but target block has been occupied by sth
//                    }else
                    if(!hasPrevRecord&&MultiBlockService.validHandler(MultiBlockService.safeGetUUID(partloc))){//如果是当前已经注册的别的机器的
                        // Debug.logger("wrong at ",delta.toString());
                        final int endHeight=height+1;
                        errorOut.out(()->{return "&c结构冲突!位于%s的部件属于另个机器! id-%s,位于%s,终止向上延申,当前高度:%h".formatted(DataCache.locationToDisplayString(partloc),MultiBlockService.safeGetUUID(partloc),DataCache.locationToDisplayString( MultiBlockService.getCore(MultiBlockService.safeGetUUID(partloc))),String.valueOf(endHeight));});
                        break search;
                    }
                }
            }
            ++height;

        }while(height<maxHeight);
        int len3=this.sizeT;
        //底部匹配
        for(int i=0;i<len3;i++){
            Vector delta= dir.rotate(TOP_LOC[i].clone());
            Location partloc=loc.clone().add(delta).add(0,height-1,0);
            //Debug.logger("check blockid ",i,this.getSchemaPartId(i),"check target id ",MultiBlockService.safeGetPartId(partloc));
            //如果当前匹配 且并未在响应任意其他的多方块才能通过，否则任意一条均为false
            if(!this.TOP_IDS[i].equals(MultiBlockService.safeGetPartId(partloc))){
                //  Debug.logger("wrong at ",delta.toString());
                int a=i;
                int h=height;
                errorOut.out(()->{
                    return "&c位于%s的多方块部件并不是有效的顶层部件,当前中间层高度:%s,需要%s".formatted(DataCache.locationToDisplayString(partloc),String.valueOf(h),this.TOP_IDS[a]);
                });
                return null;
            }else{
                //use record but target block uuid not match core uuid
//                if(hasPrevRecord&&(!(id.equals( MultiBlockService.safeGetUUID(partloc))))){
//                    //    Debug.logger("wrong at ",delta.toString());
//                    errorOut.out(()->"&c错误!你不该看见该消息,请联系作者");
//                    return null;
//                    //no record but target block has been occupied by sth
//                }else
                if(!hasPrevRecord&&MultiBlockService.validHandler(MultiBlockService.safeGetUUID(partloc))){//如果是当前已经注册的别的机器的
                    // Debug.logger("wrong at ",delta.toString());
                    errorOut.out(()->{return "&c结构冲突!位于%s的部件属于另个机器! id-%s,位于%s".formatted(DataCache.locationToDisplayString(partloc),MultiBlockService.safeGetUUID(partloc),DataCache.locationToDisplayString( MultiBlockService.getCore(MultiBlockService.safeGetUUID(partloc))));});
                    return null;
                }
            }
        }
        //  Debug.logger("check finished ,return value ",this.getSchemaSize(),"and req ",REQUIREMENT_MAP.size());
        return new CubeMultiBlock(this,dir,height);
    }
    public List<String> getRequiredArguments(){
        return List.of("height");
    }

    public Map<Vector,String> getMultiBlockSchemaFromArguments(MultiBlockService.Direction dir,Map<String,String> arguments){
        if(isSymmetric()){
            dir= MultiBlockService.Direction.NORTH;
        }
        Map<Vector,String> result=new HashMap<>();
        for(int i=0;i<this.sizeB;i++){
            result.put(dir.rotate(BOTTOM_LOC[i].clone()),BOTTOM_IDS[i]);
        }
        int height = 1;
        try{
            height=Integer.parseInt(arguments.get("height"));
        }catch (Throwable e){}
        height = Math.min(height,maxHeight);
        for(int i=0;i<this.sizeP;i++){
            Vector plateVector =   dir.rotate(PLATE_LOC[i].clone());
            for(int j=0;j<height;j++){
                result.put(plateVector.clone().add(new Vector(0,j,0)),PLATE_IDS[i]);
            }
        }
        for(int i=0;i<this.sizeT;i++){
            result.put(dir.rotate(TOP_LOC[i].clone()).add(new Vector(0,height-1,0)),TOP_IDS[i]);
        }
        return result;
    }
}
