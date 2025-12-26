package me.matl114.logitech.utils.UtilClass.FunctionalClass;

import java.util.concurrent.CountDownLatch;
import me.matl114.logitech.manager.Schedules;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AsyncResultRunnable<T extends Object> extends BukkitRunnable implements Runnable {
    public T result;

    public abstract T result();

    public void run() {
        result = result();
    }

    public T getResult() {
        return result;
    }

    public T waitThreadDone(boolean isSync) {
        if (Bukkit.isPrimaryThread() && isSync) {
            return result();
        } else {
            CountDownLatch latch = new CountDownLatch(1);
            Schedules.launchSchedules(
                    () -> {
                        this.run();
                        latch.countDown();
                    },
                    0,
                    isSync,
                    0);
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public CountDownLatch runThreadBackground() {
        CountDownLatch latch = new CountDownLatch(1);
        Schedules.launchSchedules(
                () -> {
                    this.run();
                    latch.countDown();
                },
                0,
                false,
                0);
        return latch;
    }
}
