package me.matl114.logitech.utils.UtilClass.MenuClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import me.matl114.logitech.listeners.Listeners.PlayerQuiteListener;
import me.matl114.logitech.utils.Debug;
import org.bukkit.entity.Player;

public class SimplePlayerHistoryRecord<T extends Object> implements PlayerHistoryRecord<T> {
    final HashMap<UUID, List<T>> records = new HashMap<>();

    {
        PlayerQuiteListener.addHandler((player) -> {
            UUID uid = player.getUniqueId();
            synchronized (records) {
                records.remove(uid);
            }
        });
    }

    public T getRecord(Player player) {
        UUID uuid = player.getUniqueId();
        synchronized (records) {
            List<T> list = records.get(uuid);
            if (list == null) {
                list = new ArrayList<T>();
            }
            if (list.isEmpty()) {
                return null;
            }
            return list.get(list.size() - 1);
        }
    }

    public void addRecord(Player player, T record) {
        UUID uuid = player.getUniqueId();
        synchronized (records) {
            List<T> list = records.computeIfAbsent(uuid, k -> new ArrayList<>());
            list.add(record);
        }
    }

    public T deleteRecord(Player player, T record) {
        UUID uuid = player.getUniqueId();
        synchronized (records) {
            List<T> list = records.get(uuid);
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.remove(list.size() - 1);
        }
    }

    public void deleteAllRecords(Player player) {
        UUID uuid = player.getUniqueId();
        synchronized (records) {
            var list = records.remove(uuid);
            Debug.logger(list);
        }
    }
}
