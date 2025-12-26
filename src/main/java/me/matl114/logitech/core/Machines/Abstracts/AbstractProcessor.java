package me.matl114.logitech.core.Machines.Abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import java.util.ArrayList;
import java.util.List;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.utils.UtilClass.RecipeClass.SimpleCraftingOperation;
import me.matl114.matlib.utils.inventory.itemStacks.CleanItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractProcessor extends AbstractMachine
        implements MachineProcessHolder<SimpleCraftingOperation> {
    protected static final int[] BORDER =
            new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    protected static final int[] BORDER_IN = new int[] {9, 10, 11, 12, 18, 21, 27, 28, 29, 30};
    protected static final int[] BORDER_OUT = new int[] {14, 15, 16, 17, 23, 26, 32, 33, 34, 35};

    protected final ItemStack progressbar;
    protected final MachineProcessor<SimpleCraftingOperation> processor;
    protected int PROCESSOR_SLOT = 22;

    public AbstractProcessor(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            ItemStack progressItem,
            int energyConsumption,
            int energyBuffer,
            List<Pair<Object, Integer>> customRecipes) {
        super(category, item, recipeType, recipe, energyBuffer, energyConsumption);

        this.progressbar = new CleanItemStack(progressItem);
        this.processor = new MachineProcessor(this);
        this.processor.setProgressBar(progressbar);
        if (customRecipes != null) {
            this.machineRecipes = new ArrayList<>(customRecipes.size());
            var customRecipes2 = AddUtils.buildRecipeMap(customRecipes);
            for (var recipePiece : customRecipes2) {
                // no need to stack and can not stack(maybe some shitmachine will stack
                // but we stack it in order to format up
                this.machineRecipes.add(MachineRecipeUtils.stackFromMachine(new MachineRecipe(
                        recipePiece.getSecondValue(),
                        recipePiece.getFirstValue().getFirstValue(),
                        recipePiece.getFirstValue().getSecondValue())));
            }
        } else {
            this.machineRecipes = new ArrayList<>();
        }
    }

    public AbstractProcessor(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            Material progressItem,
            int energyConsumption,
            int energyBuffer,
            List<Pair<Object, Integer>> customRecipes) {
        this(
                category,
                item,
                recipeType,
                recipe,
                new ItemStack(progressItem),
                energyConsumption,
                energyBuffer,
                customRecipes);
    }

    /**
     * method from MachineProcessorHolder
     * @return
     */
    public MachineProcessor<SimpleCraftingOperation> getMachineProcessor() {
        return this.processor;
    }

    /**
     * need implement,  method from MachineProcessorHolder
     * @return
     */
    public ItemStack getProgressBar() {
        return this.progressbar;
    }

    /**
     * cancel machineprocessor after break
     * @param e
     * @param menu
     */
    public void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        AbstractProcessor.this.processor.endOperation(menu.getLocation());
    }

    /**
     *
     * @param preset
     */
    public void constructMenu(BlockMenuPreset preset) {
        // 空白背景 禁止点击
        int[] border = AbstractProcessor.BORDER;
        int len = border.length;
        for (int var4 = 0; var4 < len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        // 输入槽边框
        border = AbstractProcessor.BORDER_IN;
        len = border.length;
        for (int var4 = 0; var4 < len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        // 输出槽边框
        border = AbstractProcessor.BORDER_OUT;
        len = border.length;
        for (int var4 = 0; var4 < len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        // 空白边框
        preset.addItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
        border = this.getOutputSlots();
        len = border.length;

        for (int var4 = 0; var4 < len; ++var4) {
            preset.addMenuClickHandler(border[var4], new ChestMenu.AdvancedMenuClickHandler() {
                public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                    return false;
                }

                public boolean onClick(
                        InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                    return cursor == null || cursor.getType() == null || cursor.getType() == Material.AIR;
                }
            });
        }
    }

    public int[] getInputSlots() {
        return new int[] {19, 20};
    }

    public int[] getOutputSlots() {
        return new int[] {24, 25};
    }

    protected boolean USE_HISTORY = true;

    public void process(Block b, BlockMenu inv, SlimefunBlockData data) {
        SimpleCraftingOperation currentOperation = (SimpleCraftingOperation) this.processor.getOperation(b);
        ItemConsumer[] fastCraft = null;
        if (currentOperation == null) {
            Pair<MachineRecipe, ItemConsumer[]> nextP = CraftUtils.findNextRecipe(
                    inv,
                    getInputSlots(),
                    getOutputSlots(),
                    getMachineRecipes(),
                    USE_HISTORY,
                    Settings.SEQUNTIAL,
                    CRAFT_PROVIDER);
            if (nextP != null) {
                MachineRecipe next = nextP.getFirstValue();
                ItemConsumer[] outputInfo = nextP.getSecondValue();
                if (next.getTicks() > 0) {
                    currentOperation = new SimpleCraftingOperation(outputInfo, next.getTicks());
                    this.processor.startOperation(b, currentOperation);
                } else if (next.getTicks() <= 0) {
                    fastCraft = nextP.getSecondValue();
                }
            } else { // if currentOperation ==null return  , cant find nextRecipe
                if (inv.hasViewer()) {
                    inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
                }
                return;
            }
        }

        progressorCost(b, inv);
        if (fastCraft != null) {
            CraftUtils.updateOutputMenu(fastCraft, inv);
            return;
        }
        if (currentOperation.isFinished()) {
            ItemConsumer[] var4 = currentOperation.getResults();
            CraftUtils.forcePush(var4, inv, getOutputSlots(), CRAFT_PROVIDER);
            if (inv.hasViewer()) {

                inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
            }
            this.processor.endOperation(b);
        } else {
            if (inv.hasViewer()) {
                this.processor.updateProgressBar(inv, PROCESSOR_SLOT, currentOperation);
            }
            currentOperation.progress(1);
        }
    }

    public void preRegister() {
        super.preRegister();
    }
}
