package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;

import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;

public class MultiBlockHandler implements Keyed {
    public final MultiBlockType STRUCTURE_TYPE;
    public final Location CORE;
    public boolean active = false;
    public final int[] RESPOND;
    public MultiBlockHandler(MultiBlockType type, Location core) {
        this.STRUCTURE_TYPE = type;
        this.CORE=core;
        this.RESPOND=new int[type.getStructureSize()];
        for (int i = 0; i < type.getStructureSize(); i++) {
            this.RESPOND[i]=3;
        }
    }
    public boolean coreRequest(){
        if(checkIfOnline()){//need continue
            return true;
        }return false;
    }
    public boolean checkIfOnline(){
        for (int i=0;i<this.RESPOND.length;i++){
            if (this.RESPOND[i]>=4){
                //keep
            }else if(this.RESPOND[i]>0){
                //common tick
                --this.RESPOND[i];
            }
            else{
                //check sf id if match ,set to 20,else return false
                if(this.STRUCTURE_TYPE.getStructureIds()[i].equals(MultiBlockService.getPartId(getBlockLoc(i)))){
                    //not respond by sf tick,because has sfid but no tick respond.
                    this.RESPOND[i]=20;
                }else {
                    //has tick,no respond,no match sfid means it has been destroyed
                    return false;
                }
            }
        }
        return true;
    }

    public Location getBlockLoc(int index){
        return this.CORE.clone().add(STRUCTURE_TYPE.getStructurePos()[index]);
    }

    public NamespacedKey getKey(){
        return STRUCTURE_TYPE.getKey();
    }
}
