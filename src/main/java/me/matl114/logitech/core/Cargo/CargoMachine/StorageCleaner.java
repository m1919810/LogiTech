package me.matl114.logitech.core.Cargo.CargoMachine;

import com.google.common.collect.ImmutableMultimap;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import java.util.Map;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Utils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class StorageCleaner extends AbstractMachine {
    protected final int[] BORDER =
            new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 18, 19, 20, 21, 23, 24, 25, 26, 36, 37, 38, 39, 40, 41, 42, 43, 44};

    protected final int[] INPUT_SLOT = new int[] {9, 10, 11, 12, 13, 14, 15, 16, 17};
    protected final int[] OUTPUT_SLOT = new int[] {27, 28, 29, 30, 31, 32, 33, 34, 35};

    public int[] getInputSlots() {
        return INPUT_SLOT;
    }

    public int[] getOutputSlots() {
        return OUTPUT_SLOT;
    }

    protected final int BOTTON_SLOT = 22;
    protected final ItemStack BOTTON_ITEM = new CustomItemStack(
            Material.GREEN_STAINED_GLASS_PANE, "&a信息", "&7上方输入槽,下方输出槽", "&7当对应输出槽为空时,将会执行重置操作", "&7除去存储外,其余物品也可以被重置");

    public StorageCleaner(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe, 0, 0);
        setDisplayRecipes(
                Utils.list(AddUtils.getInfoShow("&f机制", "&7重置存储内的数据", "&7将会试图清除输入槽内的物品数据", "&7并转移至对应的输出槽位中")));
    }

    public void constructMenu(BlockMenuPreset preset) {
        int[] slot = BORDER;
        int len = slot.length;
        for (int i = 0; i < len; i++) {
            preset.addItem(slot[i], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(BOTTON_SLOT, BOTTON_ITEM, ChestMenuUtils.getEmptyClickHandler());
    }

    public void newMenuInstance(BlockMenu inv, Block block) {}

    public void process(Block b, BlockMenu inv, SlimefunBlockData data) {
        // doing nothings
        int[] input = getInputSlots();
        int[] output = getOutputSlots();
        int len = input.length;
        ItemStack it1, it2;
        for (int i = 0; i < len; i++) {
            it1 = inv.getItemInSlot(input[i]);
            it2 = inv.getItemInSlot(output[i]);
            if (it1 == null || it2 != null) {
                continue;
            }
            SlimefunItem it = SlimefunItem.getByItem(it1);
            //            ItemStack out =;
            ////            if (it!=null){
            ////                out=it.getRecipeOutput();
            ////                out.setAmount(it1.getAmount());
            ////            }else{
            ////                out=it1;
            ////            }
            inv.replaceExistingItem(input[i], null);
            inv.replaceExistingItem(output[i], getResetItem(it1, it));
        }
    }

    public ItemStack getResetItem(ItemStack origin, SlimefunItem sfitem) {
        if (sfitem == null) {
            return origin;
        }
        ItemStack stack = AddUtils.getCopy(sfitem.getItem());
        stack.setAmount(origin.getAmount());
        ItemMeta meta = stack.getItemMeta();
        ItemMeta originMeta = origin.getItemMeta();
        if (meta != null && originMeta != null) {
            // restore ench
            if (meta.hasEnchants()) {
                for (Enchantment enchantment : meta.getEnchants().keySet()) {
                    meta.removeEnchant(enchantment);
                }
            }
            for (Map.Entry<Enchantment, Integer> entry :
                    originMeta.getEnchants().entrySet()) {
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
            // restore attr
            if (originMeta.hasAttributeModifiers()) {
                meta.setAttributeModifiers(originMeta.getAttributeModifiers());
            } else {
                meta.setAttributeModifiers(ImmutableMultimap.of());
            }
            if (meta instanceof Damageable dm1 && originMeta instanceof Damageable dm2) {
                dm1.setDamage(dm2.getDamage());
            }
        }
        stack.setItemMeta(meta);
        return stack;
    }
}
