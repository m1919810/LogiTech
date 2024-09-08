package me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiLevelBlock;

import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlock;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockType;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockType;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiLevelBlockType implements AbstractMultiBlockType {
    protected final List<AbstractMultiBlockType> SUBMULTIBLOCKS_LIST;
    protected AbstractMultiBlockType[] SUBMULTIBLOCKS;
    public AbstractMultiBlockType getSubParts(int index){
        return SUBMULTIBLOCKS[index];
    }
    protected int[] SIZES;
    protected int[] INDEXS;
    protected int SUB_NUM;
    protected boolean isSymm;
    public abstract void init();
    public MultiLevelBlockType(){
        this.SUBMULTIBLOCKS_LIST=new ArrayList<>();
    }
    public int getSchemaSize(){
        return INDEXS[SUB_NUM];
    }
    public Vector getSchemaPart(int index){
        for(int i=0;i<SUB_NUM;++i){
            if(index<INDEXS[i+1]){
                return SUBMULTIBLOCKS[i].getSchemaPart(index-INDEXS[i]);
            }
        }
        return null;
    }
    public String getSchemaPartId(int index){
        for(int i=0;i<SUB_NUM;++i){
            if(index<INDEXS[i+1]){
                return SUBMULTIBLOCKS[i].getSchemaPartId(index-INDEXS[i]);
            }
        }
        return null;
    }
    public boolean isSymmetric(){
        return  isSymm;
    }
    public MultiLevelBlockType addSubPart(AbstractMultiBlockType type){
        this.SUBMULTIBLOCKS_LIST.add(type);
        return this;
    }
    public MultiLevelBlockType build(){
        init();
        this.SUB_NUM=this.SUBMULTIBLOCKS_LIST.size();
        this.SUBMULTIBLOCKS=this.SUBMULTIBLOCKS_LIST.toArray(new AbstractMultiBlockType[SUB_NUM]);
        this.INDEXS=new int[SUB_NUM+1];
        this.SIZES=new int[SUB_NUM];
        this.INDEXS[0]=0;
        for(int i=0;i<SUB_NUM;++i){
            this.SIZES[i]=this.SUBMULTIBLOCKS[i].getSchemaSize();
            this.INDEXS[i+1]=this.INDEXS[i]+this.SIZES[i];
        }
        return this;
    }
    public AbstractMultiBlock genMultiBlockFrom(Location loc, MultiBlockService.Direction dir,boolean hasPrevRecord){
        int level=0;
        List<AbstractMultiBlock> subparts=new ArrayList<>();
        AbstractMultiBlock part;
        for (;level<SUB_NUM;++level){
            part=SUBMULTIBLOCKS[level].genMultiBlockFrom(loc,dir,hasPrevRecord);
            if(part==null)break;
            subparts.add(part);
        }
        if(level==0)return null;
        return new   MultiLevelBlock(this,level,dir,subparts);
    }

}
