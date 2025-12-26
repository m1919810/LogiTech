package me.matl114.logitech.manager;

import com.google.common.base.Preconditions;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import lombok.Getter;
import me.matl114.logitech.utils.Debug;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Schedules {
    private static BukkitRunnable autoSaveThread;
    private static BukkitRunnable effectThread;

    @Getter
    private static Plugin plugin;

    public static void setupSchedules(Plugin plugin) {
        Schedules.plugin = plugin;
        autoSaveThread = new BukkitRunnable() {
            @Override
            public void run() {
                ScheduleSave.onPeriodicSave();
            }
        };
        int periodAutoSave = 3 * 60 * 20; // 三分钟保存一次
        autoSaveThread.runTaskTimerAsynchronously(plugin, periodAutoSave, periodAutoSave);

        effectThread = new BukkitRunnable() {
            public void run() {
                EffectTickManager.scheduleEffects();
            }
        };
        int periodEffect = 20;
        effectThread.runTaskTimer(plugin, 200, periodEffect);

        BukkitRunnable postRegisterTask = new BukkitRunnable() {
            public void run() {
                PostSetupTasks.schedulePostRegister();
            }
        };
        int delayPostRegister = 1;
        postRegisterTask.runTaskLater(plugin, delayPostRegister);
    }

    public static void onDisableSchedules(Plugin plugin) {
        Debug.logger("保存附属数据中");
        ScheduleSave.onFinalSave();
        Debug.logger("附属数据保存完成");
    }

    public static void execute(Runnable task, boolean isSync) {
        if (Bukkit.isPrimaryThread()) {
            task.run();
        } else {
            Schedules.launchSchedules(task, 0, isSync, 0);
        }
    }

    public static void launchSchedules(BukkitRunnable thread, int delay, boolean isSync) {
        launchSchedules(thread, delay, isSync, -1);
    }

    public static void launchSchedules(BukkitRunnable thread, int delay, boolean isSync, int period) {
        if (period <= 0) {
            if (isSync) {
                if (delay != 0) thread.runTaskLater(plugin, delay);
                else thread.runTask(plugin);
            } else {
                if (delay != 0) thread.runTaskLaterAsynchronously(plugin, delay);
                else thread.runTaskAsynchronously(plugin);
            }
        } else {
            if (isSync) {
                thread.runTaskTimer(plugin, delay, period);
            } else {
                thread.runTaskTimerAsynchronously(plugin, delay, period);
            }
        }
    }

    public static void launchSchedules(Runnable thread, int delay, boolean isSync, int period) {
        launchSchedules(getRunnable(thread), delay, isSync, period);
    }

    public static void launchRepeatingSchedule(
            Consumer<Integer> thread, int delay, boolean isSync, int period, int repeatTime) {
        launchSchedules(
                new BukkitRunnable() {
                    int runTime = 0;

                    @Override
                    public void run() {
                        try {
                            thread.accept(runTime);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        } finally {
                            this.runTime += 1;
                            if (this.runTime >= repeatTime) {
                                this.cancel();
                            }
                        }
                    }
                },
                delay,
                isSync,
                period);
    }
    // this method should be called async
    public static void asyncWaithRepeatingSchedule(
            Consumer<Integer> thread, int delay, boolean isSync, int period, int repeatTime) {
        Preconditions.checkArgument(!Bukkit.isPrimaryThread(), "This method should be called in async thread");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        launchSchedules(
                new BukkitRunnable() {
                    int runTime = 0;

                    @Override
                    public void run() {
                        try {
                            thread.accept(runTime);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        } finally {
                            this.runTime += 1;
                            if (this.runTime >= repeatTime) {
                                countDownLatch.countDown();
                                this.cancel();
                            }
                        }
                    }
                },
                delay,
                isSync,
                period);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static BukkitRunnable getRunnable(Runnable runnable) {
        return new BukkitRunnable() {
            public void run() {
                runnable.run();
            }
        };
    }
}
