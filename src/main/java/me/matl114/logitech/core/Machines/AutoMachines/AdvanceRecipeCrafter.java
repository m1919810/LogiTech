package me.matl114.logitech.core.Machines.AutoMachines;

import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.matl114.logitech.core.Interface.RecipeDisplay;
import me.matl114.logitech.core.Interface.RecipeLock;
import me.matl114.logitech.core.Machines.Abstracts.AbstractAdvancedProcessor;
import me.matl114.logitech.core.Registries.RecipeSupporter;
import me.matl114.logitech.manager.PostSetupTasks;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.matl114.logitech.utils.UtilClass.RecipeClass.ImportRecipes;
import me.matl114.logitech.utils.UtilClass.RecipeClass.MultiCraftingOperation;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class AdvanceRecipeCrafter extends AbstractAdvancedProcessor implements RecipeLock, ImportRecipes {
    protected final int[] BORDER = {3, 5, 12, 14, 21, 22, 23};
    protected final int[] RECIPE_DISPLAY = {30, 31, 32, 39, 40, 41, 48, 49, 50};
    protected final int RECIPEITEM_SLOT = 13;
    protected int PARSE_SLOT = 4;
    protected final ItemStack PARSE_ITEM = new CustomItemStack(
            Material.ORANGE_STAINED_GLASS_PANE,
            "&e点击解析配方",
            "&b机制:",
            "&6将配方表输出的物品(若有多个则第一个)置于下方",
            "&6右键该槽位,或者开关容器界面,配方将被解析",
            "&6机器将按照解析出的指定配方合成");
    protected final ItemStack DISPLAY_BKGROUND = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, " ");
    protected final ItemStack DISPLAY_DEFAULT_BKGROUND = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, " ");
    protected final ItemStack progressItem;
    protected final RecipeType[] craftType;
    protected final int publicTime;
    protected final String KEY_CTIME = "ct";
    protected final int CTIME_SLOT = 5;
    protected final ItemStack CTIME_ITEM = new CustomItemStack(Material.CLOCK, "&6自定义进度时间", "&7点击输入自定义进度时常");

    public AdvanceRecipeCrafter(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            Material processbar,
            int energyConsumption,
            int energyBuffer,
            List<Pair<Object, Integer>> customRecipes) {
        super(category, item, recipeType, recipe, processbar, energyConsumption, energyBuffer, customRecipes);
        this.craftType = null;
        this.publicTime = 0;
        this.progressItem = new ItemStack(processbar);
    }

    public AdvanceRecipeCrafter(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            Material processbar,
            int energyConsumption,
            int energyBuffer,
            int ticks,
            RecipeType... craftType) {
        super(category, item, recipeType, recipe, processbar, energyConsumption, energyBuffer, null);
        this.craftType = craftType;
        this.publicTime = ticks;
        this.progressItem = new ItemStack(processbar);
        PostSetupTasks.addPostRegisterTask(() -> getDisplayRecipes());
    }

    public List<MachineRecipe> getMachineRecipes() {
        if (this.machineRecipes == null || this.machineRecipes.isEmpty()) {
            this.machineRecipes = new ArrayList<>();
            if (this.craftType != null) {
                if (publicTime == 0) {
                    if (this.craftType.length <= 0) {
                        return new ArrayList<>();
                    } else {

                        this.machineRecipes = new ArrayList<>();
                        for (RecipeType rt : this.craftType) {
                            if (rt != null) {
                                List<MachineRecipe> rep = RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(rt);
                                if (rep == null) rep = new ArrayList<>();
                                this.machineRecipes.addAll(rep);
                            }
                        }
                    }
                    // reset ticks to ...
                } else {
                    // @TODO new MachineRecipe to reset ticks
                    for (int i = 0; i < this.craftType.length; i++) {
                        RecipeType rt = this.craftType[i];
                        if (rt != null) {
                            List<MachineRecipe> rep = RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(rt);
                            if (rep == null) rep = new ArrayList<>();
                            this.machineRecipes.addAll(rep);
                        }
                    }
                }
            }
        }
        return this.machineRecipes;
    }

    public ItemStack getProgressBar() {
        return this.progressItem;
    }

    public MachineRecipe getRecordRecipe(SlimefunBlockData data) {
        int index = getNowRecordRecipe(data);
        List<MachineRecipe> recipes = getMachineRecipes();
        if (index >= recipes.size()) { // 越界
            setNowRecordRecipe(data, -1);
            return null;
        }
        if (index < 0) return null;
        else return recipes.get(index);
    }

    public void constructMenu(BlockMenuPreset preset) {
        // 空白背景 禁止点击
        int[] border = BORDER;
        int len = border.length;
        for (int var4 = 0; var4 < len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        // 输入槽边框
        border = BORDER_SLOT;
        len = border.length;
        for (int var4 = 0; var4 < len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(22, MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
        border = RECIPE_DISPLAY;
        len = border.length;
        // emptyhandler
        for (int var4 = 0; var4 < len; ++var4) {
            preset.addMenuClickHandler(border[var4], ChestMenuUtils.getEmptyClickHandler());
        }
    }

    public void newMenuInstance(BlockMenu menu, Block block) {
        if (publicTime > 0) {
            if (menu.getItemInSlot(CTIME_SLOT) == null
                    || menu.getItemInSlot(CTIME_SLOT).getType() != Material.CLOCK) {
                menu.replaceExistingItem(CTIME_SLOT, CTIME_ITEM);
            }
            menu.addMenuClickHandler(CTIME_SLOT, ((player, i, itemStack, clickAction) -> {
                AddUtils.sendMessage(
                        player,
                        "&6[&7自动配方工作台&6]&a 请输入自定义的进度时长(单位:sf tick)&e(必须>=%s)".formatted(String.valueOf(publicTime)));
                player.closeInventory();
                AddUtils.asyncWaitPlayerInput(player, (str) -> {
                    try {
                        int a = Integer.parseInt(str);
                        Preconditions.checkArgument(a >= this.publicTime);
                        DataCache.setCustomData(menu.getLocation(), KEY_CTIME, a);
                        menu.replaceExistingItem(
                                CTIME_SLOT,
                                AddUtils.addLore(CTIME_ITEM, "&c当前的自定义进度时长为: &f%s".formatted(String.valueOf(a))));
                        AddUtils.sendMessage(player, "&6[&7自动配方工作台&6]&a 成功设置!");
                    } catch (Throwable e) {
                        AddUtils.sendMessage(
                                player,
                                "&6[&7自动配方工作台&6]&c 这不是有效的进度时常&e(必须>=%s)".formatted(String.valueOf(this.publicTime)));
                    }
                });
                return false;
            }));
        }
        menu.replaceExistingItem(PARSE_SLOT, PARSE_ITEM);
        menu.addMenuClickHandler(PARSE_SLOT, (player, i, itemStack, clickAction) -> {
            parseRecipe(menu);
            updateMenu(menu, block, Settings.RUN);
            return false;
        });
        menu.addMenuOpeningHandler((player -> {
            parseRecipe(menu);
            updateMenu(menu, block, Settings.RUN);
        }));
        menu.addMenuCloseHandler((player -> {
            parseRecipe(menu);
            updateMenu(menu, block, Settings.RUN);
        }));
        updateMenu(menu, block, Settings.INIT);
    }

    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        Location loc = menu.getLocation();
        menu.dropItems(loc, RECIPEITEM_SLOT);
    }

    public void parseRecipe(BlockMenu menu) {
        ItemStack target = menu.getItemInSlot(RECIPEITEM_SLOT);
        if (target == null || target.getType() == Material.AIR) {
            setNowRecordRecipe(menu.getLocation(), -1);
        } else {
            List<MachineRecipe> machineRecipes1 = getMachineRecipes();
            for (int i = 0; i < machineRecipes1.size(); ++i) {
                MachineRecipe machineRecipe = machineRecipes1.get(i);
                if (CraftUtils.matchItemStack(target, machineRecipe.getOutput()[0], false)) {
                    setNowRecordRecipe(menu.getLocation(), i);
                    return;
                }
            }
            setNowRecordRecipe(menu.getLocation(), -1);
            return;
        }
    }

    public void updateMenu(BlockMenu menu, Block block, Settings mod) {
        SlimefunBlockData data = DataCache.safeLoadBlock(menu.getLocation());
        if (data == null) {
            Schedules.launchSchedules(
                    () -> {
                        updateMenu(menu, block, mod);
                    },
                    20,
                    false,
                    0);
            return;
        }
        MachineRecipe recipe = getRecordRecipe(data);

        if (recipe == null) {
            for (int var4 = 0; var4 < RECIPE_DISPLAY.length; ++var4) {
                menu.replaceExistingItem(RECIPE_DISPLAY[var4], DISPLAY_DEFAULT_BKGROUND);
            }
        } else {
            ItemStack[] input;
            input = recipe.getInput();

            int len = Math.min(RECIPE_DISPLAY.length, input.length);
            for (int var4 = 0; var4 < len; ++var4) {
                menu.replaceExistingItem(
                        RECIPE_DISPLAY[var4], RecipeDisplay.addRecipeInfo(input[var4], Settings.INPUT, var4, 1.0, 0));
            }
            for (int var4 = len; var4 < RECIPE_DISPLAY.length; ++var4) {
                menu.replaceExistingItem(RECIPE_DISPLAY[var4], DISPLAY_BKGROUND);
            }
        }
    }

    public int getCraftLimit(Block b, BlockMenu inv) {
        return 1;
    }

    public void process(Block b, BlockMenu inv, SlimefunBlockData data) {

        MultiCraftingOperation currentOperation = this.processor.getOperation(b);
        ItemGreedyConsumer[] fastCraft = null;
        if (currentOperation == null) {
            MachineRecipe next = getRecordRecipe(data);
            if (next == null) {
                parseRecipe(inv);
                if (inv.hasViewer()) {
                    updateMenu(inv, b, Settings.RUN);
                    inv.replaceExistingItem(22, MenuUtils.PROCESSOR_NULL);
                }
                return;
            }
            Pair<ItemGreedyConsumer[], ItemGreedyConsumer[]> nextP = CraftUtils.countMultiRecipe(
                    inv, getInputSlots(), getOutputSlots(), next, getCraftLimit(b, inv), CRAFT_PROVIDER);
            if (nextP != null) {

                CraftUtils.multiUpdateInputMenu(nextP.getFirstValue(), inv);
                int ticks = next.getTicks();
                if (next.getTicks() < 0) {
                    ticks = DataCache.getCustomData(data, KEY_CTIME, this.publicTime);
                    if (ticks < this.publicTime) {
                        ticks = this.publicTime;
                        DataCache.setCustomData(data, KEY_CTIME, this.publicTime);
                    }
                }
                if (ticks > 0) {

                    currentOperation = new MultiCraftingOperation(nextP.getSecondValue(), ticks);
                    this.processor.startOperation(b, currentOperation);
                } else {
                    fastCraft = nextP.getSecondValue();
                }
            } else { // if currentOperation ==null return  , cant find nextRecipe

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(22, MenuUtils.PROCESSOR_NULL);
                }
                return;
            }
        }
        progressorCost(b, inv);
        if (fastCraft != null) {
            CraftUtils.multiUpdateOutputMenu(fastCraft, inv);
        } else if (currentOperation.isFinished()) {
            ItemGreedyConsumer[] var4 = currentOperation.getResults();
            CraftUtils.multiForcePush(var4, inv, getOutputSlots(), CRAFT_PROVIDER);
            if (inv.hasViewer()) {
                inv.replaceExistingItem(22, MenuUtils.PROCESSOR_NULL);
            }
            this.processor.endOperation(b);
        } else {
            if (inv.hasViewer()) {
                this.processor.updateProgressBar(inv, 22, currentOperation);
            }
            currentOperation.progress(1);
        }
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
        return new int[0];
    }

    @Override
    //    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item)
    // {
    //        if (flow == ItemTransportFlow.WITHDRAW) return getOutputSlots();
    //        int [] inputSlots=getInputSlots();
    //        if(item==null||item.getType().isAir()||(!(menu instanceof  BlockMenu))){
    //            return inputSlots;
    //        }
    //        SlimefunBlockData data=DataCache.safeLoadBlock(((BlockMenu)menu).getLocation());
    //        if(data==null){
    //            return inputSlots;
    //        }
    //        MachineRecipe now=getRecordRecipe(data);
    //        if(now==null){
    //            return new int[0];
    //        }
    //        int craftlimit=getCraftLimit(null,null);
    //        int amountLimit=0;
    //        int maxStack=item.getMaxStackSize();
    //        ItemStack[] recipeInput=now.getInput();
    //        for (int i=0;i<recipeInput.length;++i){
    //            if(recipeInput[i].getType()==item.getType()&&recipeInput[i].hasItemMeta()==item.hasItemMeta()){
    //                amountLimit+=Math.max(recipeInput[i].getAmount()*craftlimit,maxStack);
    //            }
    //        }
    //        // Check the current amount
    //        int itemAmount = 0;
    //        for (int slot : getInputSlots()) {
    //            ItemStack itemInSlot = menu.getItemInSlot(slot);
    //            if(itemInSlot==null)continue;
    //            if (itemInSlot.getType()==item.getType()&&itemInSlot.hasItemMeta()==item.hasItemMeta()) {
    //                itemAmount+=itemInSlot.getAmount();
    //                // Amount has reached the limited, just return.
    //                if(itemAmount>=amountLimit){
    //                    return new int[0];
    //                }
    //            }
    //        }
    //        return inputSlots;
    //    }
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) return getOutputSlots();
        int[] inputSlots = getInputSlots();
        if (item == null || item.getType().isAir() || (!(menu instanceof BlockMenu))) return inputSlots;
        SlimefunBlockData data = DataCache.safeLoadBlock(((BlockMenu) menu).getLocation());
        if (data == null) {
            return inputSlots;
        }
        MachineRecipe now = getRecordRecipe(data);
        if (now == null) {
            return new int[0];
        }
        int craftlimit = getCraftLimit(null, null);
        int amountLimit = 0;
        int maxStack = item.getMaxStackSize();
        ItemStack[] recipeInput = now.getInput();
        ItemCounter pusher = null;
        boolean hasOne = false;
        for (int i = 0; i < recipeInput.length; ++i) {
            if (recipeInput[i].getType() == item.getType() && recipeInput[i].hasItemMeta() == item.hasItemMeta()) {
                if (!hasOne) {
                    amountLimit = Math.max(recipeInput[i].getAmount() * craftlimit, maxStack);
                    hasOne = true;
                } else {
                    if (pusher == null) {
                        // 保证比较之前pusher非null，不用重复计算
                        pusher = CraftUtils.getCounter(item);
                    }
                    // 由于StackMachineRecipe 材料已经被折叠过了 只需要找到一个匹配就行
                    if (CraftUtils.matchItemStack(recipeInput[i], pusher, false)) {
                        amountLimit = Math.max(recipeInput[i].getAmount() * craftlimit, maxStack);
                        break;
                    }
                }
            }
        }
        // Check the current amount
        int itemAmount = 0;
        if (!hasOne) {
            return inputSlots;
        }
        boolean hasItemMeta = item.hasItemMeta();
        for (int slot : inputSlots) {
            ItemStack itemInSlot = menu.getItemInSlot(slot);
            if (itemInSlot == null) continue;
            // 出现类型匹配
            if (itemInSlot.getType() == item.getType() && itemInSlot.hasItemMeta() == hasItemMeta) {
                // 如果pusher!=null,说明上面出现了两个相同type 需要比较
                // 如果比较不匹配 跳过
                if (pusher != null && hasItemMeta && !CraftUtils.matchItemStack(itemInSlot, pusher, false)) {
                    continue;
                }
                itemAmount += itemInSlot.getAmount();
                // Amount has reached the limited, just return.
                if (itemAmount >= amountLimit) {
                    return new int[] {slot};
                }
            }
        }
        return inputSlots;
    }

    protected final IntSet NOT_INTERACTABLE_SLOTS = new IntOpenHashSet(
            Streams.concat(Arrays.stream(BORDER), Arrays.stream(RECIPE_DISPLAY), Arrays.stream(BORDER_SLOT))
                    .iterator());

    @Override
    public boolean useAdvancedMenu() {
        return true;
    }

    @Override
    public void listenDragClick(BlockMenu inv, InventoryDragEvent e) {
        super.listenDragClick(inv, e);
        for (var slot : e.getRawSlots()) {
            if (NOT_INTERACTABLE_SLOTS.contains(slot.intValue())) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @Override
    public void listenOriginClick(BlockMenu inv, InventoryClickEvent e) {
        if (e.getClick() == ClickType.DOUBLE_CLICK) {
            ItemStack stack = e.getCursor();
            if (stack != null && !stack.getType().isAir()) {
                ItemCounter counter = CraftUtils.getCounter(stack);
                for (IntIterator it = NOT_INTERACTABLE_SLOTS.intIterator(); it.hasNext(); ) {
                    int i = it.nextInt();
                    if (CraftUtils.matchItemStack(inv.getItemInSlot(i), counter, true)) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
}
