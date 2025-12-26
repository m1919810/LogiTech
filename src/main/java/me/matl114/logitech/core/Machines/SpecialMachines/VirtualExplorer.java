package me.matl114.logitech.core.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.gadgets.MultiTool;
import io.github.thebusybiscuit.slimefun4.implementation.items.magical.staves.WindStaff;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import java.util.*;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.utils.UtilClass.RecipeClass.CustomMachineOperation;
import me.matl114.logitech.utils.UtilClass.RecipeClass.RandomMachineOperation;
import me.matl114.logitech.utils.UtilClass.RecipeClass.TimeCounterOperation;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTables;

public class VirtualExplorer extends AbstractMachine implements MachineProcessHolder<CustomMachineOperation> {
    protected final int[] INPUT_SLOTS = new int[] {10, 13, 16};
    protected final int[] OUTPUT_SLOTS = IntStream.range(27, 54).toArray();
    protected final int[] BORDER =
            new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 14, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    protected final int FLY_SLOT = 1;
    protected final int ELYTRA_SLOT = 4;
    protected final int FOOD_SLOT = 7;
    protected final int BASE_SPEED_WALK = 2;
    protected final int BASE_SPEED_ELYTRA = 25;
    protected final int BASE_SPEED_FLYMACHINE = 80;
    protected final ItemStack FLY_ITEM = new CustomItemStack(
            Material.ORANGE_STAINED_GLASS_PANE,
            "&6飞行道具槽位",
            "&7可放入鞘翅/无尽飞行器",
            "&7当为空时,基础探索速度=%s".formatted(String.valueOf(BASE_SPEED_WALK)),
            "&7当放入鞘翅时,基础探索速度=%s".formatted(String.valueOf(BASE_SPEED_ELYTRA)),
            "&7当放入鞘翅时,&e需要同时放入飞行驱动道具",
            "&7当放入无尽飞行器时,基础探索速度=%s".formatted(String.valueOf(BASE_SPEED_FLYMACHINE)));
    protected final int FOOD_SPEED = 4;
    protected final int ROCKET_TOTAL_TIME = 12;
    protected final int MUL_ROCKET = 8;
    protected final int MUL_WIND = 12;
    protected final int FOOD_MUL_WIND = 10;
    protected final int MUL_TOOL = 12;

    protected final ItemStack ELYTRA_ITEM = new CustomItemStack(
            Material.ORANGE_STAINED_GLASS_PANE,
            "&6飞行驱动道具",
            "&7可放入元素法杖-风/多功能工具/烟花火箭",
            "&7当放入烟花火箭时,%s刻消耗一个,探索速度x%s".formatted(String.valueOf(ROCKET_TOTAL_TIME), String.valueOf(MUL_ROCKET)),
            "&7当放入元素法杖-风时,食物消耗x%s,探索速度x%s".formatted(String.valueOf(FOOD_MUL_WIND), String.valueOf(MUL_WIND)),
            "&7当放入多功能工具时,多功能工具会消耗自身电力,探索速度x%s".formatted(MUL_TOOL));
    protected final ItemStack FOOD_ITEM = new CustomItemStack(
            Material.ORANGE_STAINED_GLASS_PANE,
            "&6食物槽",
            "&7可放入原版食物",
            "&71刻消耗%s饱食度数值".formatted(String.valueOf(FOOD_SPEED)),
            "&7当饱食度低于0时会消耗该槽位中的食物",
            "&7注:饱食度数值=20*(食物饥饿回复值+饱和度回复值)");
    //    protected final int POP_SLOT=23;
    //    protected final ItemStack POP_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6丢弃模式","&7当前状态:
    // &c关","&7若开启,推送槽位满时,会将未能推送的物品")
    protected final int SEED_SLOT = 21;
    protected final ItemStack SEED_ITEM = new CustomItemStack(
            Material.WHEAT_SEEDS,
            "&6点击输入预测的世界种子(LongType)以更改跑图速度",
            "&7世界种子采用当前世界的种子(/seed)值(LongType)",
            "&7当输入种子>真实种子时,75%概率进度增加,25%概率进度减少",
            "&7当输入种子<真实种子时,75%概率进度减少,25%概率进度增加",
            "&7当输入种子=真实种子时,100%概率进度增加");
    protected final int INFO_SLOT = 22;
    protected final int WORLD_SLOT = 23;
    protected final ItemStack WORLD_WORLD_ITEM = new CustomItemStack(Material.GRASS_BLOCK, "&6当前环境: &a主世界环境", "&7可以跑图");
    protected final ItemStack WORLD_NETHER_ITEM = new CustomItemStack(Material.NETHERRACK, "&6当前环境: &c下界环境", "&7可以跑图");
    protected final ItemStack WORLD_END_ITEM = new CustomItemStack(Material.END_STONE, "&6当前环境: &9末地环境", "&7可以跑图");
    protected final ItemStack WORLD_UNKNOWN_ITEM =
            new CustomItemStack(Material.GRASS_BLOCK, "&6当前环境: &b???", "&7不可以跑图");
    protected final int FLY_PROGRESS_SLOT = 19;
    protected final int FOOD_PROGRESS_SLOT = 25;

    protected ItemStack getInfoItem(
            int progress,
            int totalProgress,
            long predictSeed,
            int speed,
            int foodlevel,
            int totalFoodlevel,
            boolean isFly) {
        if (foodlevel >= 0) {
            if (speed > 0) {
                if (isFly) {
                    return new CustomItemStack(
                            Material.GREEN_STAINED_GLASS_PANE,
                            "&a跑图中",
                            "&7当前饱食进度: %s/%s".formatted(String.valueOf(foodlevel), String.valueOf(totalFoodlevel)),
                            "&7当前跑图速度: %s".formatted(String.valueOf(speed)),
                            "&7当前预测种子: %s".formatted(String.valueOf(predictSeed)),
                            "&7当前跑图进度: %s/%s".formatted(String.valueOf(progress), String.valueOf(totalProgress)));
                } else {
                    return new CustomItemStack(
                            Material.GREEN_STAINED_GLASS_PANE,
                            "&a挖掘中",
                            "&7当前饱食进度: %s/%s".formatted(String.valueOf(foodlevel), String.valueOf(totalFoodlevel)),
                            "&7当前挖掘速度: %s".formatted(String.valueOf(speed)),
                            "&7当前预测种子: %s".formatted(String.valueOf(predictSeed)),
                            "&7当前挖掘进度: %s/%s".formatted(String.valueOf(progress), String.valueOf(totalProgress)));
                }
            } else {
                return new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&c未工作", "&7进度速度=0");
            }
        } else {
            return new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&c未工作", "&7食物不足!");
        }
    }

    protected final Random rand = new Random();

    protected int getRandFlyProgress() {
        return rand.nextInt(1000, 1600);
    }

    protected final int MINE_PROGRESS = 4;
    protected MachineProcessor<CustomMachineOperation> mainProcessor;
    protected MachineProcessor<CustomMachineOperation> foodProcessor;
    protected MachineProcessor<CustomMachineOperation> rockectProcessor;

    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    protected final String KEY_SEED = "sed";

    public VirtualExplorer(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            int energybuffer,
            int energyConsumption) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
        this.mainProcessor = new MachineProcessor<>(this);
        this.mainProcessor.setProgressBar(new ItemStack(Material.FEATHER));
        this.foodProcessor = new MachineProcessor<>(this);
        this.foodProcessor.setProgressBar(new ItemStack(Material.BAKED_POTATO));
        this.rockectProcessor = new MachineProcessor<>(this);
        this.rockectProcessor.setProgressBar(new ItemStack(Material.FIREWORK_ROCKET));
    }

    public void constructMenu(BlockMenuPreset preset) {
        preset.setSize(54);
        for (int i : BORDER) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(FLY_SLOT, FLY_ITEM);
        preset.addItem(ELYTRA_SLOT, ELYTRA_ITEM);
        preset.addItem(FOOD_SLOT, FOOD_ITEM);
        preset.addItem(SEED_SLOT, SEED_ITEM);
    }

    public void newMenuInstance(BlockMenu menu, Block block) {
        menu.addMenuClickHandler(SEED_SLOT, ((player, i, itemStack, clickAction) -> {
            AddUtils.sendMessage(player, "&6[&7自动跑图机&6]&a 请输入种子:");
            player.closeInventory();
            ChatUtils.awaitInput(player, (str) -> {
                try {
                    long seed = Long.parseLong(str);
                    DataCache.setCustomString(menu.getLocation(), KEY_SEED, str);
                    if (menu.getLocation().getWorld().getSeed() == seed) {
                        AddUtils.sendMessage(player, "&6[&7自动跑图机&6]&a 种子正确!");
                    } else {
                        AddUtils.sendMessage(player, "&6[&7自动跑图机&6]&c 种子错误!");
                    }
                } catch (Throwable e) {
                    AddUtils.sendMessage(player, "&6[&7自动跑图机&6]&a 这并不是有效的LongType!");
                }
            });
            return false;
        }));
        ItemStack env =
                switch (menu.getLocation().getWorld().getEnvironment()) {
                    case NETHER -> WORLD_NETHER_ITEM;
                    case CUSTOM -> WORLD_UNKNOWN_ITEM;
                    case NORMAL -> WORLD_WORLD_ITEM;
                    case THE_END -> WORLD_END_ITEM;
                };
        menu.replaceExistingItem(WORLD_SLOT, env);
    }

    public void updateMenu(BlockMenu menu, Block block, Settings mod) {}

    protected int isFood(ItemStack item) {
        if (item == null || SlimefunItem.getByItem(item) != null) {
            return 0;
        } else {
            Material material = item.getType();
            if (WorldUtils.FOOD_SATURATION_MUL_10.containsKey(material)) {
                return WorldUtils.FOOD_SATURATION_MUL_10.get(material);
            }
            return 0;
        }
    }

    protected ItemStack useElytra(ItemStack item) {
        if (item.getType() == Material.ELYTRA) {
            ItemMeta meta = item.getItemMeta();
            if (meta.isUnbreakable()) {
                return item;
            } else {
                int level = meta.getEnchantLevel(Enchantment.DURABILITY);
                if (rand.nextInt(0, level + 1) == 0) {
                    if (meta instanceof Damageable dm) {
                        int damage = dm.getDamage() + 1;
                        if (dm.getDamage() == item.getType().getMaxDurability()) {
                            return null;
                        } else {
                            dm.setDamage(damage);
                        }
                    }
                    item.setItemMeta(meta);
                }
                return item;
            }
        }
        return item;
    }

    protected int consumeFly(BlockMenu inv, CustomMachineOperation foodOperation) {
        ItemStack flyItem = inv.getItemInSlot(INPUT_SLOTS[0]);
        CustomMachineOperation rocketOperation =
                (TimeCounterOperation) this.rockectProcessor.getOperation(inv.getLocation());
        boolean willChangeOffRocket = rocketOperation != null;

        try {
            foodOperation.progress(FOOD_SPEED);
            if (flyItem == null) {
                // walk
                return BASE_SPEED_WALK;
            } else if (flyItem.getType() == Material.ELYTRA) {
                inv.replaceExistingItem(INPUT_SLOTS[0], useElytra(inv.getItemInSlot(INPUT_SLOTS[0])));
                if (rocketOperation != null) {
                    rocketOperation.progress(1);
                    willChangeOffRocket = false;
                    if (rocketOperation.isFinished()) {
                        this.rockectProcessor.endOperation(inv.getLocation());
                    }
                    return BASE_SPEED_ELYTRA * MUL_ROCKET;
                }
                ItemStack flyDrive = inv.getItemInSlot(INPUT_SLOTS[1]);
                if (flyDrive != null) {
                    if (flyDrive.getType() == Material.FIREWORK_ROCKET) {
                        // only null operation will come to here ,so new Operation is needed
                        rocketOperation = new TimeCounterOperation(ROCKET_TOTAL_TIME);
                        flyDrive.setAmount(flyDrive.getAmount() - 1);
                        this.rockectProcessor.startOperation(inv.getLocation(), rocketOperation);

                        return BASE_SPEED_ELYTRA * MUL_ROCKET;
                    } else {
                        SlimefunItem item = SlimefunItem.getByItem(flyDrive);
                        if (item instanceof WindStaff ws) {
                            foodOperation.progress(FOOD_SPEED * (FOOD_MUL_WIND - 1));
                            return BASE_SPEED_ELYTRA * MUL_WIND;
                        } else if (item instanceof MultiTool mt) {
                            ItemStack flyDriveNew = inv.getItemInSlot(INPUT_SLOTS[1]);
                            if (mt.removeItemCharge(flyDriveNew, 0.1F)) {
                                inv.replaceExistingItem(INPUT_SLOTS[1], flyDriveNew);
                                return BASE_SPEED_ELYTRA * MUL_TOOL;
                            }
                        }
                    }
                }
                return BASE_SPEED_ELYTRA;
            } else if ("INFINITY_MATRIX".equals(CraftUtils.parseSfId(flyItem))) {
                return BASE_SPEED_FLYMACHINE;
            } else {
                return BASE_SPEED_WALK;
            }
        } finally {
            if (willChangeOffRocket) {
                this.rockectProcessor.endOperation(inv.getLocation());
            }
        }
    }

    HashMap<Location, OfflinePlayer> placer = new HashMap<>();

    protected void summonRandLoottable(BlockMenu menu) {
        Location loc = menu.getLocation();

        List<LootTables> tables =
                switch (loc.getWorld().getEnvironment()) {
                    case NORMAL -> WorldUtils.OVERWORLD_STRUCTURE_CHESTS;
                    case NETHER -> WorldUtils.NETHER_STRUCTURE_CHESTS;
                    case THE_END -> WorldUtils.END_STRUCTURE_CHESTS;
                    default -> List.of();
                };
        int size = tables.size();
        if (size == 0) return;
        HashSet<ItemConsumer> counters = new HashSet<>();

        int randIndex = rand.nextInt(0, size);
        LootTables table = tables.get(randIndex);
        OfflinePlayer player = placer.computeIfAbsent(loc, (loc2) -> {
            String uid = DataCache.getLastUUID(loc2);
            if ("null".equals(uid)) {
                return null;
            } else {
                try {
                    UUID uuid = UUID.fromString(uid);
                    return Bukkit.getOfflinePlayer(uuid);
                } catch (Throwable t) {
                    return null;
                }
            }
        });
        final HumanEntity entity = player == null ? null : player.getPlayer();
        Schedules.launchSchedules(
                () -> {
                    try {
                        Collection<ItemStack> loot = table.getLootTable()
                                .populateLoot(
                                        rand,
                                        new LootContext.Builder(loc)
                                                .luck(114)
                                                .lootedEntity(entity)
                                                .build());
                        loot.forEach((item) -> {
                            if (item != null && !item.getType().isAir()) {
                                for (ItemCounter counter : counters) {
                                    if (CraftUtils.matchItemStack(item, counter, true)) {
                                        counter.addAmount(item.getAmount());
                                        return;
                                    }
                                }
                                counters.add(CraftUtils.getConsumer(item));
                            }
                        });
                    } catch (Exception e) {
                        if (entity != null) {
                            Debug.logger("An error occurred while generating loot table ", table.name());
                            Debug.logger("player : ", entity);
                        }
                    }
                    CraftUtils.forcePush(counters.toArray(ItemConsumer[]::new), menu, OUTPUT_SLOTS);
                },
                0,
                true,
                0);
    }

    public void process(Block b, BlockMenu menu, SlimefunBlockData data) {
        // check food processor
        Location loc = menu.getLocation();
        if (loc.getWorld().getEnvironment() == World.Environment.CUSTOM) {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(
                        INFO_SLOT, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&c未工作", "不正确的世界环境!"));
            }
            return;
        }
        CustomMachineOperation operation = this.foodProcessor.getOperation(loc);
        if (operation == null || operation.isFinished()) {
            this.foodProcessor.endOperation(loc);
            ItemStack foodStack = menu.getItemInSlot(INPUT_SLOTS[2]);
            int foodlevel = isFood(foodStack);
            if (foodlevel > 0) {
                foodStack.setAmount(foodStack.getAmount() - 1);
                operation = new TimeCounterOperation(foodlevel);
                this.foodProcessor.startOperation(loc, operation);
            } else {
                if (menu.hasViewer()) {
                    menu.replaceExistingItem(FLY_PROGRESS_SLOT, MenuUtils.PROCESSOR_NULL);
                    menu.replaceExistingItem(FOOD_PROGRESS_SLOT, MenuUtils.PROCESSOR_NULL);
                    menu.replaceExistingItem(INFO_SLOT, getInfoItem(0, 0, 0, 0, -1, 0, false));
                }
                return;
            }
        }

        int foodTotal = operation.getTotalTicks();
        int foodlevel = operation.getProgress();
        if (menu.hasViewer()) {
            this.foodProcessor.updateProgressBar(menu, FOOD_PROGRESS_SLOT, operation);
        }

        // check condition
        CustomMachineOperation flyOperation = this.mainProcessor.getOperation(loc);
        if (flyOperation == null) {
            flyOperation = new RandomMachineOperation(
                    getRandFlyProgress(), loc.getWorld().getSeed());
            this.mainProcessor.startOperation(loc, flyOperation);
        }
        int speed = 0;
        boolean isFly = true;
        long seed = 0L;
        try {
            String trySeedStr = data.getData(KEY_SEED);
            if (trySeedStr != null) {
                seed = Long.parseLong(trySeedStr);
            }
        } catch (Throwable e) {
        }
        if (flyOperation instanceof TimeCounterOperation top) {
            isFly = false;
            speed = 1;
            top.progress(1);
            operation.progress(FOOD_SPEED);
            if (top.isFinished()) {
                this.mainProcessor.endOperation(loc);
                summonRandLoottable(menu);
            }
            // mine operator
        } else if (flyOperation instanceof RandomMachineOperation rop) {
            isFly = true;
            // fly operator
            int flySpeed = consumeFly(menu, operation);
            speed = flySpeed;
            if (flySpeed > 0) {

                rop.randProgress(flySpeed, seed);
                if (rop.isFinished()) {
                    this.mainProcessor.endOperation(loc);
                    flyOperation = new TimeCounterOperation(MINE_PROGRESS);
                    this.mainProcessor.startOperation(loc, flyOperation);
                }
            }
        }
        this.progressorCost(b, menu);
        if (menu.hasViewer()) {
            this.mainProcessor.updateProgressBar(menu, FLY_PROGRESS_SLOT, flyOperation);
            menu.replaceExistingItem(
                    INFO_SLOT,
                    getInfoItem(
                            flyOperation.getProgress(),
                            flyOperation.getTotalTicks(),
                            seed,
                            speed,
                            foodlevel,
                            foodTotal,
                            isFly));
        }
    }

    @Nonnull
    @Override
    public MachineProcessor<CustomMachineOperation> getMachineProcessor() {
        return this.mainProcessor;
    }

    @Override
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) return OUTPUT_SLOTS;
        if (item == null || item.getType().isAir()) {
            return INPUT_SLOTS;
        }
        if (WorldUtils.FOOD_SATURATION_MUL_10.containsKey(item.getType())) {
            return new int[] {INPUT_SLOTS[2]};
        } else if (item.getType() == Material.ELYTRA || item.getType() == Material.NETHER_STAR) {
            return new int[] {INPUT_SLOTS[0]};
        } else {
            return new int[] {INPUT_SLOTS[1]};
        }
    }

    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        if (menu != null) {
            this.mainProcessor.endOperation(menu.getLocation());
            this.foodProcessor.endOperation(menu.getLocation());
            this.rockectProcessor.endOperation(menu.getLocation());
            placer.remove(menu.getLocation());
        }
    }

    @Override
    public void onPlace(BlockPlaceEvent e, Block b) {
        super.onPlace(e, b);
        if (e.getPlayer() != null) {
            DataCache.setLastUUID(b.getLocation(), e.getPlayer().getUniqueId().toString());
            placer.put(b.getLocation(), e.getPlayer());
        }
    }
}
