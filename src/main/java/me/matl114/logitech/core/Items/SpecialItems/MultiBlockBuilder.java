package me.matl114.logitech.core.Items.SpecialItems;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import java.util.*;
import me.matl114.logitech.core.Blocks.MultiBlockCore.MultiBlockCore;
import me.matl114.logitech.core.Items.Abstracts.CustomProps;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.AbstractMultiBlockType;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.utils.WorldUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MultiBlockBuilder extends CustomProps {
    public MultiBlockBuilder(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void onClickAction(PlayerRightClickEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() || player.getGameMode() == GameMode.CREATIVE) {
            Optional<Block> block = event.getClickedBlock();
            if (block.isPresent()) {
                Location location = block.get().getLocation();
                if (DataCache.getSfItem(location) instanceof MultiBlockCore mcore) {
                    event.cancel();
                    multiBlockForceBuild(location, mcore.getMultiBlockType(), mcore, player);
                } else {
                    AddUtils.sendMessage(player, "&c并不是一个多方块机器核心");
                }
            }
        } else {
            AddUtils.sendMessage(player, "&c你没有使用这个物品的权限");
        }
    }

    public void multiBlockForceBuild(
            Location coreLocation, AbstractMultiBlockType type, MultiBlockCore core, Player player) {
        AddUtils.sendMessage(player, "&a请输入生成的方向 (NORTH,WEST,SOUTH,EAST)");
        AddUtils.asyncWaitPlayerInput(player, (dir) -> {
            MultiBlockService.Direction direction;
            try {
                direction = MultiBlockService.Direction.valueOf(dir);
            } catch (Throwable e) {
                AddUtils.sendMessage(player, "&c无效的方向! 搭建已取消");
                return;
            }
            List<String> argList = type.getRequiredArguments();
            final Map<String, String> result = new HashMap<>();
            asyncGetArguments(player, argList, result, () -> {
                Map<Vector, String> schema = type.getMultiBlockSchemaFromArguments(direction, result);
                Map<String, ItemStack> idMapper = core.getIdMappingDisplayUse();
                Iterator<Map.Entry<Vector, String>> iterator = schema.entrySet().iterator();
                Schedules.launchSchedules(
                        new BukkitRunnable() {
                            public void run() {
                                if (iterator.hasNext()) {
                                    var entry = iterator.next();
                                    ItemStack stack = idMapper.getOrDefault(entry.getValue(), null);
                                    Location loc = coreLocation.clone().add(entry.getKey());
                                    if (stack != null) {
                                        Block block = loc.getBlock();
                                        // align to int
                                        loc = block.getLocation();
                                        SlimefunItem item = SlimefunItem.getByItem(stack);
                                        if (item != null) {
                                            if (DataCache.hasData(loc)) {
                                                DataCache.removeBlockData(loc);
                                            }

                                            Slimefun.getDatabaseManager()
                                                    .getBlockDataController()
                                                    .createBlock(loc, item.getId());
                                        }
                                        block.setType(WorldUtils.getBlockOrAir(stack.getType()));
                                    } else {
                                        loc.getBlock().setType(Material.AIR);
                                    }
                                } else {
                                    this.cancel();
                                    AddUtils.sendMessage(player, "&a搭建完毕!");
                                }
                            }
                        },
                        4,
                        true,
                        1);
            });
        });
    }

    private void asyncGetArguments(
            Player player, List<String> arguments, Map<String, String> results, Runnable callback) {
        if (arguments.isEmpty()) {
            callback.run();
        } else {
            AddUtils.sendMessage(player, "&a请输入下面参数: " + arguments.get(0) + " (输入\"取消\"以取消)");
            AddUtils.asyncWaitPlayerInput(player, (dir) -> {
                if (Objects.equals("取消", dir)) {
                    return;
                }
                results.put(arguments.get(0), dir);
                asyncGetArguments(player, arguments.subList(1, arguments.size()), results, callback);
            });
        }
    }
}
