package me.matl114.logitech.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityUtils {
    public static ConcurrentHashMap<Lock, HashSet<UUID>> PLAYERACTIONLOCK = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Lock,HashSet<Location>> MACHINETHREADLOCK = new ConcurrentHashMap<>();
    /**
     * set lock on player ,return false if no specific lock or already contains this type of lock
     * @param p
     * @param lock
     * @return
     */
    public static boolean lock(Player p, Lock lock) {
        if (PLAYERACTIONLOCK.containsKey(lock)) {
            HashSet set = PLAYERACTIONLOCK.get(lock);
            UUID uuid = p.getUniqueId();
            if (!set.contains(uuid)) {
                set.add(uuid);
                return true;
            }else {
                return false;
            }
        }return false;
    }
    public static boolean lock( Lock lock,Object obj) {
        ConcurrentHashMap<Lock,HashSet> map=lock.getType().getData();
        if (map.containsKey(lock)) {
            HashSet set=map.get(lock);
            if(!set.contains(obj)) {
                set.add(obj);
                return true;
            }else {
                return false;
            }

        }return false;
    }
    /**
     * remove lock of player, return false if no specific lock or player havn't set lock
     * @param p
     * @param lock
     * @return
     */
    public static boolean unlock(Player p, Lock lock) {
        if (PLAYERACTIONLOCK.containsKey(lock)) {
            HashSet set = PLAYERACTIONLOCK.get(lock);
            UUID uuid = p.getUniqueId();
            return set.remove(uuid);
        }else return false;
    }
    public static boolean unlock(Lock lock,Object obj) {
        ConcurrentHashMap<Lock,HashSet> map=lock.getType().getData();
        if (map.containsKey(lock)) {
            HashSet set=map.get(lock);
            return set.remove(obj);
        }
        return false;
    }
    public enum LockType{
        PLAYERACTION(PLAYERACTIONLOCK),
        MACHINETHREAD(MACHINETHREADLOCK);
        public ConcurrentHashMap<Lock,HashSet> data;
        LockType(ConcurrentHashMap a){
            this.data = a;
        }
        public ConcurrentHashMap<Lock,HashSet> getData(){
            return this.data;
        }
    }
    public enum Lock{
        MenuClickLock(LockType.PLAYERACTION),
        MultiBlockBuildLock(LockType.MACHINETHREAD)
        ;
        LockType type;
        Lock(LockType lockType){
            this.type = lockType;
            lockType.getData().put(this,new HashSet<>());
        }
        public LockType getType(){
            return this.type;
        }
    }
}
