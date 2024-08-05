package me.matl114.logitech.Utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class SecurityUtils {
    public static HashMap<Lock, HashSet<UUID>> PLAYERACTIONLOCK = new HashMap<>();

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
    public enum LockType{
        PLAYERACTION(PLAYERACTIONLOCK);
        public HashMap data;
        LockType(HashMap a){
            this.data = a;
        }
        public HashMap getData(){
            return this.data;
        }
    }
    public enum Lock{
        MenuClickLock(LockType.PLAYERACTION)
        ;
        Lock(LockType lockType){
            lockType.getData().put(this,new HashSet<>());
        }
    }
}
