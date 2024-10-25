package me.matl114.logitech.SlimefunItem.Blocks.MultiBlockCore;

import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.CubeMultiBlock.CubeMultiBlockType;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockType;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiLevelBlock.MultiLevelBlockType;

public class MultiBlockTypes {
    public static void setup(){

    }
    public static final MultiBlockType TEST_TYPE=new MultiBlockType() {
        @Override
        public void init() {
            addBlock(0,0,0,"test.part");
            addBlock(0,1,0,"test.part");
            addBlock(0,2,0,"test.part");
            addBlock(0,3,0,"test.part");
            addBlock(1,3,0,"test.part");
            addBlock(0,3,1,"test.part");
            addBlock(-1,3,0,"test.part");
            addBlock(0,3,-1,"test.part");
            this.isSymmetric=true;
        }
    }.build();
    public static final MultiBlockType PORTAL_TYPE=new MultiBlockType() {
        public void  init(){
            addBlock(0,0,0,"portal.core");
            addBlock(1,0,0,"portal.part");
            addBlock(1,1,0,"portal.part");
            addBlock(1,2,0,"portal.part");
            addBlock(1,3,0,"portal.part");
            addBlock(0,3,0,"portal.part");
            addBlock(-1,0,0,"portal.part");
            addBlock(-1,1,0,"portal.part");
            addBlock(-1,2,0,"portal.part");
            addBlock(-1,3,0,"portal.part");
            addRequirement(0,1,0, MultiBlockService.MBID_NOSFBLOCK);
            addRequirement(0,2,0, MultiBlockService.MBID_NOSFBLOCK);
            this.isSymmetric=false;
        }
    }.build();
    public static final MultiBlockType SOLAR_TYPE=new MultiBlockType() {
        @Override
        public void init() {

            String id_frame="solar.frame";
            String id_glass="solar.glass";
            String id=id_frame;
            for (int i=-2;i<=2;i++){
                for (int j=-2;j<=2;j++){
                    if(i*j!=4&&(i*j!=-4)){
                        //y=-3 and y=5
                        addBlock(i,-3,j,id);
                        addBlock(i,5,j,id);
                        //nulls in -2 and 4
                        addRequirement(i,-2,j, MultiBlockService.MBID_NOSFBLOCK);
                        addRequirement(i,4,j, MultiBlockService.MBID_NOSFBLOCK);
                    }
                }
            }
            for (int i=-2;i<=2;i++){
                for (int j=-2;j<=2;j++){
                    addRequirement(i,-1,j, MultiBlockService.MBID_NOSFBLOCK);
                    addRequirement(i,0,j, MultiBlockService.MBID_NOSFBLOCK);
                    addRequirement(i,1,j, MultiBlockService.MBID_NOSFBLOCK);
                    addRequirement(i,2,j, MultiBlockService.MBID_NOSFBLOCK);
                    addRequirement(i,3,j, MultiBlockService.MBID_NOSFBLOCK);
                }
            }
            //y=-2
            int y=-2;
            for(int i=-1;i<=1;++i){
                addBlock(i,y,-3,id);
                addBlock(i,y,3,id);
                addBlock(-3,y,i,id);
                addBlock(3,y,i,id);
            }
            addBlock(-2,y,-2,id);
            addBlock(2,y,-2,id);
            addBlock(2,y,2,id);
            addBlock(-2,y,2,id);
             y=4;
            for(int i=-1;i<=1;++i){
                addBlock(i,y,-3,id);
                addBlock(i,y,3,id);
                addBlock(-3,y,i,id);
                addBlock(3,y,i,id);
            }
            addBlock(-2,y,-2,id);
            addBlock(2,y,-2,id);
            addBlock(2,y,2,id);
            addBlock(-2,y,2,id);
            //y=3
            //y=-1
            id=id_glass;
            y=-1;
            for(int i=-1;i<=1;++i){
                addBlock(i,y,-3,id);
                addBlock(i,y,3,id);
                addBlock(-3,y,i,id);
                addBlock(3,y,i,id);
            }
            y=3;
            for(int i=-1;i<=1;++i){
                addBlock(i,y,-3,id);
                addBlock(i,y,3,id);
                addBlock(-3,y,i,id);
                addBlock(3,y,i,id);
            }
            id=id_frame;
            y=-1;
            addBlock(-3,y,-2,id);
            addBlock(3,y,-2,id);
            addBlock(3,y,2,id);
            addBlock(-3,y,2,id);
            addBlock(-2,y,-3,id);
            addBlock(2,y,-3,id);
            addBlock(2,y,3,id);
            addBlock(-2,y,3,id);
            y=3;
            addBlock(-3,y,-2,id);
            addBlock(3,y,-2,id);
            addBlock(3,y,2,id);
            addBlock(-3,y,2,id);
            addBlock(-2,y,-3,id);
            addBlock(2,y,-3,id);
            addBlock(2,y,3,id);
            addBlock(-2,y,3,id);
            //y=0,1,2
            for(y=0;y<3;++y){
                addBlock(-3,y,-3,id);
                addBlock(3,y,-3,id);
                addBlock(3,y,3,id);
                addBlock(-3,y,3,id);

            }
            id=id_glass;
            for(y=0;y<3;++y){
                for(int i=-2;i<=2;++i){
                    addBlock(i,y,-3,id);
                    addBlock(i,y,3,id);
                    addBlock(-3,y,i,id);
                    addBlock(3,y,i,id);
                }
            }
            this.isSymmetric=true;

        }
    }.build();
    public static final CubeMultiBlockType NUCLEAR_REACTOR=new CubeMultiBlockType(){
        protected int[] dx=new int[]{
            2,2,3,3,3,4,4,5,5,5,6,6
        };
        protected int[] dy=new int[]{
                -1,1,-2,0,2,-1,1,-2,0,2,-1,1
        };
        public void init(){
            this.maxHeight=10;
            String frameId="nuclear.frame";
            String glassId="nuclear.glass";
            String metalId="nuclear.rod";
            addBottom(0,-1,0,frameId);
            addBottom(0,1,0,frameId);
            for(int i=1;i<=7;++i){
                for(int j=-3;j<=3;++j){
                    addBottom(i,-1,j,frameId);
                }
            }
            for(int k=0;k<2;++k){
                addBottom(1,k,-3,frameId);
                addBottom(1,k,3,frameId);
                addBottom(7,k,-3,frameId);
                addBottom(7,k,3,frameId);
                addBottom(1,k,0,frameId);
                for(int j=-2;j<=2;++j){
                    addBottom(7,k,j,glassId);
                    addBottom(4+j,k,-3,glassId);
                    addBottom(4+j,k,3,glassId);
                    if(j!=0){
                        addBottom(1,k,j,glassId);
                    }
                }
                int len=dx.length;
                for(int i=0;i<len;++i){
                    addBottom(dx[i],k,dy[i],metalId);
                }
            }
            int k=2;
            addPlate(1,k,-3,frameId);
            addPlate(1,k,3,frameId);
            addPlate(7,k,-3,frameId);
            addPlate(7,k,3,frameId);
            addPlate(1,k,0,frameId);
            for(int j=-2;j<=2;++j){
                addPlate(7,k,j,glassId);
                addPlate(4+j,k,-3,glassId);
                addPlate(4+j,k,3,glassId);
                addPlate(1,k,j,glassId);
            }
            int len=dx.length;
            for(int i=0;i<len;++i){
                addPlate(dx[i],k,dy[i],metalId);
            }
            k=3;
            addTop(1,k,-3,frameId);
            addTop(1,k,3,frameId);
            addTop(7,k,-3,frameId);
            addTop(7,k,3,frameId);
            addTop(1,k,0,frameId);
            for(int j=-2;j<=2;++j){
                addTop(7,k,j,frameId);
                addTop(4+j,k,-3,frameId);
                addTop(4+j,k,3,frameId);
                addTop(1,k,j,frameId);
            }
        }
    }.build();
    public static final MultiBlockType FINAL_BASE=new MultiBlockType() {
        @Override
        public void init() {
            String baseId="final.base";
            String frameId="final.frame";
            addBlock(0,-1,0,frameId);
            addBlock(0,-2,0,frameId);
            addBlock(0,-3,0,frameId);
            for(int y=1;y<4;++y){
                for(int i=-y;i<=y;++i){
                    for (int j=-y;j<=y;++j){
                        if(i+j<=y&&i+j>=-y&&i-j<=y&&i-j>=-y&&!(i==0&&j==0)){
                            addBlock(i,-y,j,baseId);
                        }
                    }
                }
            }
            addBlock(-2,-3,-2,baseId);
            addBlock(2,-3,-2,baseId);
            addBlock(-2,-3,2,baseId);
            addBlock(2,-3,2,baseId);
            this.isSymmetric=true;
        }
    }.build();
    public static final MultiBlockType FINAL_RING=new MultiBlockType() {
        @Override
        public void init() {
            String baseId="final.base";
            String subId="final.sub";
            int[] dx=new int[]{
                    2,5,7,8
            };
            for(int y=0;y<3;++y){
                for(int i=0;i<4;++i){
                    addBlock(dx[i],y,dx[3-i],baseId);
                    addBlock(-dx[i],y,dx[3-i],baseId);
                    addBlock(dx[i],y,-dx[3-i],baseId);
                    addBlock(-dx[i],y,-dx[3-i],baseId);
                }
            }
            addBlock(0,1,8,subId);
            addBlock(0,1,-8,subId);
            addBlock(8,1,0,subId);
            addBlock(-8,1,0,subId);
            this.isSymmetric=true;
        }
    }.build();
    public static final MultiLevelBlockType FINAL_ALTAR=new MultiLevelBlockType() {
        @Override
        public void init() {
            this.isSymm=true;
            addSubPart(FINAL_BASE);
            addSubPart(FINAL_RING);
        }
    }.build();
}
