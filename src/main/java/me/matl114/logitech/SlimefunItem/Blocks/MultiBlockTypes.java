package me.matl114.logitech.SlimefunItem.Blocks;

import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.AbstractMultiBlockType;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockType;

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
}
