package me.matl114.logitech.Utils.UtilClass.MultiBlockClass;


import me.matl114.logitech.Utils.AddUtils;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MultiBlockType implements Keyed {
    private final HashMap<Vector,String> STRUCTURE_MAP;
    private Vector[] STRUCTURE_LOC;
    private String[] STRUCTURE_IDS;
    private int size;
    public final NamespacedKey namespaceKey;
    boolean isFinal=false;
    public MultiBlockType(String key) {
        this.namespaceKey = AddUtils.getNameKey(key);
        this.STRUCTURE_MAP = new LinkedHashMap<Vector,String>();
    }
    public Vector[] getStructurePos() {
        return STRUCTURE_LOC;
    }
    public int getStructureSize() {
        return size;
    }
    public String[] getStructureIds() {
        return STRUCTURE_IDS;
    }
    public MultiBlockType build(){
        isFinal=true;
        this.size=STRUCTURE_MAP.size();
        this.STRUCTURE_LOC=new Vector[STRUCTURE_MAP.size()];
        this.STRUCTURE_IDS=new String[STRUCTURE_MAP.size()];
        int i=0;
        for(Map.Entry<Vector,String> entry : STRUCTURE_MAP.entrySet()){
            this.STRUCTURE_IDS[i]=entry.getValue();
            this.STRUCTURE_LOC[i]=entry.getKey();
            ++i;
        }
        return this;
    }
    public MultiBlockType addBlock(int x, int y, int z,String id) {
        STRUCTURE_MAP.put(new Vector(x,y,z),id);
        return this;
    }
    public NamespacedKey getKey(){
        return namespaceKey;
    }
}
