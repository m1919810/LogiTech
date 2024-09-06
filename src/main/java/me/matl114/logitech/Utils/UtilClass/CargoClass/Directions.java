package me.matl114.logitech.Utils.UtilClass.CargoClass;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public enum Directions {
    NORTH(0,0,-1,"N",1),
    WEST(-1,0,0,"W",2),
    SOUTH(0,0,1,"S",3),
    EAST(1,0,0,"E",4),
    UP(0,1,0,"U",5),
    DOWN(0,-1,0,"D",6),
    NONE(0,0,0,"X",0)
    ;
    int seqCode;
    int dx;
    int dy;
    int dz;
    String nickname;
    private Directions(int dx,int dy,int dz,String nickname,int seq){
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.nickname = nickname;
        this.seqCode = seq;
    }
    public Location relate(Location loc){
        return loc.clone().add(dx,dy,dz);
    }
    public Location remote(Location loc,int dis){
        return loc.clone().add(dis*dx,dis*dy,dis*dz);
    }
    public Location remote(Location loc,double dis){
        return loc.clone().add(dis*dx,dis*dy,dis*dz);
    }
    public Location move(Location loc){
        return move(loc,1);
    }
    public Location move(Location loc,int dis){
        return loc.add(dis*dx,dis*dy,dis*dz);
    }
    public Location move(Location loc,double dis){
        return loc.add(dis*dx,dis*dy,dis*dz);
    }
    public String toString(){
        return nickname;
    }
    public int toInt(){
        return seqCode;
    }
    public static Directions fromString(String s){
        switch(s){
            case "N" : return NORTH;
            case "W" : return WEST;
            case "S" : return SOUTH;
            case "E" : return EAST;
            case "U" : return UP;
            case "D" : return DOWN;
            default: return NONE;
        }
    }
    public static Directions fromInt(int i){
        switch(i){
            case 0:return NONE;
            case 1:return NORTH;
            case 2:return WEST;
            case 3:return SOUTH;
            case 4:return EAST;
            case 5:return UP;
            case 6:return DOWN;
            default: return NONE;
        }
    }
    public Directions getNext(){
        switch(this){
            case NORTH: return WEST;
            case WEST: return SOUTH;
            case SOUTH: return EAST;
            case EAST: return UP;
            case UP: return DOWN;
            case DOWN: return NONE;
            case NONE: return NORTH;
            default: return NORTH;
        }
    }
    public BlockFace toBlockFace(){
        switch(this){
            case NORTH: return BlockFace.NORTH;
            case WEST: return BlockFace.WEST;
            case SOUTH: return BlockFace.SOUTH;
            case EAST: return BlockFace.EAST;
            case UP: return BlockFace.UP;
            case DOWN: return BlockFace.DOWN;
            default: return null;
        }
    }
    public static Directions fromBlockFace(BlockFace blockface){
        switch(blockface){
            case NORTH: return Directions.NORTH;
            case WEST: return Directions.WEST;
            case SOUTH: return Directions.SOUTH;
            case EAST: return Directions.EAST;
            case UP: return Directions.UP;
            case DOWN: return Directions.DOWN;
            default: return Directions.NONE;
        }
    }


}
