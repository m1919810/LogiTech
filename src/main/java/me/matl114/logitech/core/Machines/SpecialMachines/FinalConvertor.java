package me.matl114.logitech.core.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import java.lang.reflect.Field;
import java.util.*;
import javax.annotation.Nullable;
import me.matl114.logitech.core.Blocks.Laser;
import me.matl114.logitech.core.Blocks.MultiBlock.FinalAltarCore;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.core.Registries.FinalFeature;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Debug;
import me.matl114.logitech.utils.MachineRecipeUtils;
import me.matl114.logitech.utils.UtilClass.ItemClass.RandOutItem;
import me.matl114.logitech.utils.UtilClass.ItemClass.RandomItemStack;
import me.matl114.logitech.utils.Utils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class FinalConvertor extends AbstractMachine
        implements FinalAltarCore.FinalAltarChargable, Laser.LaserChargable {
    static final Int2ReferenceOpenHashMap<Material> ID_TO_MATERIAL;
    static int MAX_RANDOM_BUFFER;
    static int MAX_RANDOM_BUFFER_SQARE;
    static int MAX_TRY_COUNT;
    static final Random rand = new Random();
    static Field ID_FIELD;
    static final HashMap<Material, Integer> MATERIAL_TO_ID;
    static final int MAX;
    static final int MIN;

    static {
        HashMap<Integer, Material> map;
        MATERIAL_TO_ID = new HashMap<>();
        try {
            map = new HashMap<>();
            Debug.debug("TRY PLAN A");
            for (Material material : Material.values()) {
                map.put(material.getId(), material);
            }
            MAX_RANDOM_BUFFER = 64;
            MAX_TRY_COUNT = 144;
        } catch (Throwable e) {
            Debug.debug("PLAN A FAILED");
            Debug.debug(e);
            try {
                Debug.debug("TRY PLAN B");
                map = new HashMap<>();
                ID_FIELD = Material.class.getDeclaredField("id");
                ID_FIELD.setAccessible(true);
                for (Material material : Material.values()) {
                    map.put((Integer) ID_FIELD.get(material), material);
                }
                MAX_RANDOM_BUFFER = 81;
                MAX_TRY_COUNT = 576;
            } catch (Throwable e2) {
                Debug.debug("PLAN B FAILED");
                Debug.debug(e2);
                Debug.debug("USE PLAN C");
                map = new HashMap<>();
                int t = rand.nextInt(3);
                for (Material material : Material.values()) {
                    t += 1 + rand.nextInt(3);
                    map.put(t, material);
                }
                MAX_RANDOM_BUFFER = 10;
                MAX_TRY_COUNT = 36;
            }
        }
        MAX_RANDOM_BUFFER_SQARE = MAX_RANDOM_BUFFER * MAX_RANDOM_BUFFER;

        int maxValue = Collections.max(map.keySet()) + MAX_RANDOM_BUFFER_SQARE;
        Debug.debug("GET MAX AVAILABLE MATERIAL VALUE ", maxValue);
        Debug.debug("GET COUNT OF MATERIAL ", map.size());
        ID_TO_MATERIAL = new Int2ReferenceOpenHashMap<>();
        int max = 0;
        int min = 0;
        for (Map.Entry<Integer, Material> e : map.entrySet()) {
            ID_TO_MATERIAL.put(e.getKey().intValue(), e.getValue());
            MATERIAL_TO_ID.put(e.getValue(), e.getKey());
            max = Math.max(max, e.getKey());
            min = Math.min(min, e.getKey());
        }
        MAX = max;
        MIN = min;

        Debug.debug("MATERIAL MAPPING DONE");
    }

    public static Material getRandomMaterial(Material material) {
        Integer start_nullable = MATERIAL_TO_ID.get(material);
        int start;
        if (start_nullable == null) {
            start = rand.nextInt(MAX - MIN + 1) + MIN;
            Debug.debug("ERROR! MATERIAL NOT IN MAPPER ", material.toString());
        } else {
            start = start_nullable;
        }
        Material mat;
        for (int i = 0; i < MAX_TRY_COUNT; ++i) {

            double x = rand.nextInt(MAX_RANDOM_BUFFER_SQARE);
            int sgn = rand.nextInt(2) == 0 ? -1 : 1;
            int delta = start + (int) (sgn * x);

            mat = ID_TO_MATERIAL.get(delta);
            if (mat != null) return mat;
        }
        return null;
    }

    protected final int[] BORDER = new int[] {13, 22, 31, 40, 49};
    protected final int[] INPUT_BORDER = new int[] {0, 1, 2, 3};
    protected final int[] OUTPUT_BORDER = new int[] {5, 6, 7, 8};
    protected final int[] INPUT_SLOT = new int[] {
        9, 10, 11, 12,
        18, 19, 20, 21,
        27, 28, 29, 30,
        36, 37, 38, 39,
        45, 46, 47, 48
    };
    protected final int[] OUTPUT_SLOT = new int[] {
        14, 15, 16, 17,
        23, 24, 25, 26,
        32, 33, 34, 35,
        41, 42, 43, 44,
        50, 51, 52, 53
    };

    public int[] getInputSlots() {
        return INPUT_SLOT;
    }

    public int[] getOutputSlots() {
        return OUTPUT_SLOT;
    }

    protected final int STATUS_SLOT = 4;
    protected final ItemStack STATUS_ON =
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6机器信息", "&7状态: &a已激活");
    protected final ItemStack STATUS_OFF =
            new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&6机器信息", "&7状态: &c未激活");
    protected final ItemStack NULL_OUTPUT;
    protected final RandOutItem NULL_OUT;
    protected final MachineRecipe RECIPE_FOR_DISPLAY;

    public FinalConvertor(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            int energybuffer,
            int energyConsumption,
            RandomItemStack nullOutput) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
        this.NULL_OUTPUT = nullOutput;
        this.NULL_OUT = nullOutput;
        this.setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow(
                        "&f机制 - &c充能",
                        "&7当置于贰级终极祭坛上时",
                        "&7且机器被终极祭坛结构中的所有宏激光发射器充能时",
                        "&7即终极祭坛中四个宏激光发射器分别位于四个壹级以上终极祭坛上时",
                        "&7机器激活,进行运转"),
                null,
                AddUtils.getInfoShow(
                        "&f机制 - &c随机波动",
                        "&7当机器运转时,",
                        "&7机器会随机波动若干次输入测的物品材质id",
                        "&7并尝试将其转为其他材质的原版物品",
                        "&7同时赋予其随机的数量",
                        "&7当转换失败时,机器会随机从下方可能的输出中选择一项",
                        "&7进行输出")));
        this.RECIPE_FOR_DISPLAY = MachineRecipeUtils.stackFrom(
                -1, new ItemStack[] {AddUtils.getInfoShow("&f可能的输出", "&7如下所示")}, new ItemStack[] {NULL_OUTPUT});
    }

    public void constructMenu(BlockMenuPreset preset) {
        int[] border = BORDER;
        int len = border.length;
        for (int i = 0; i < len; ++i) {
            preset.addItem(border[i], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        border = INPUT_BORDER;
        len = border.length;
        for (int i = 0; i < len; ++i) {
            preset.addItem(border[i], ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        border = OUTPUT_BORDER;
        len = border.length;
        for (int i = 0; i < len; ++i) {
            preset.addItem(border[i], ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(STATUS_SLOT, STATUS_OFF, ChestMenuUtils.getEmptyClickHandler());
    }

    public List<MachineRecipe> getMachineRecipes() {
        List<MachineRecipe> recipes = new ArrayList<>();
        recipes.add(RECIPE_FOR_DISPLAY);
        return recipes;
    }

    public void tick(Block b, @Nullable BlockMenu menu, SlimefunBlockData data, int tickCount) {
        if (menu == null) return;
        if (conditionHandle(b, menu) && FinalFeature.isFinalAltarCharged(this, data)) {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(STATUS_SLOT, STATUS_ON);
            }
            process(b, menu, data);
        } else {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(STATUS_SLOT, STATUS_OFF);
            }
        }
    }

    public void process(Block b, BlockMenu inv, SlimefunBlockData data) {
        int hasPutOutput = 0;
        int hasPutInput = 0;
        ItemStack input;
        ItemStack output;
        loop:
        while (true) {
            do {
                if (hasPutInput >= INPUT_SLOT.length) {
                    break loop;
                }
                input = inv.getItemInSlot(INPUT_SLOT[hasPutInput]);
                ++hasPutInput;
            } while (input == null);
            do {
                if (hasPutOutput >= OUTPUT_SLOT.length) {
                    break loop;
                }
                output = inv.getItemInSlot(OUTPUT_SLOT[hasPutOutput]);
                ++hasPutOutput;

            } while (output != null);
            Material randMaterial = getRandomMaterial(input.getType());

            ItemStack result = (randMaterial != null && randMaterial.isItem() && !randMaterial.isAir())
                    ? new ItemStack(randMaterial, rand.nextInt(Math.max(1, randMaterial.getMaxStackSize())) + 1)
                    : NULL_OUT.getInstance();

            inv.replaceExistingItem(OUTPUT_SLOT[hasPutOutput - 1], result);
            inv.replaceExistingItem(INPUT_SLOT[hasPutInput - 1], null);
            progressorCost(b, inv);
            break;
        }
    }
}
