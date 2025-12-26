package me.matl114.logitech.core.Items.Equipments;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import me.matl114.logitech.core.AddItem;
import me.matl114.logitech.core.Items.Abstracts.MaterialItem;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.MenuUtils;
import me.matl114.logitech.utils.UtilClass.EquipClass.EquipmentFU;
import me.matl114.matlib.utils.inventory.itemStacks.CleanItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@Getter
public class EquipmentFUItem extends MaterialItem {
    private final EquipmentFU fUnit;

    @Getter
    private static final Set<EquipmentFUItem> entries = new LinkedHashSet<EquipmentFUItem>();

    public EquipmentFUItem(
            ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, EquipmentFU fUnit) {
        super(itemGroup, item, recipeType, recipe);
        this.fUnit = fUnit;
        entries.add(this);
    }

    private ItemStack maxLevel = null;

    public EquipmentFUItem maxLevel(String... description) {
        maxLevel = new CleanItemStack(Material.BOOK, "&a单装备等级限制", description);
        return this;
    }

    private ItemStack availableItem = null;

    public EquipmentFUItem availableItem(String... description) {
        availableItem = new CleanItemStack(Material.BOOK, "&a可装配的装备范围", description);
        return this;
    }

    private ItemStack cost = null;
    private ItemStack costSample = AddItem.ABSTRACT_INGOT;

    public EquipmentFUItem cost(ItemStack costSample, String... description) {
        cost = new CleanItemStack(Material.BOOK, "&a所需锻造材料", description);
        this.costSample = costSample;
        return this;
    }

    public EquipmentFUItem complete() {
        List<ItemStack> items = new ArrayList<>();
        items.add(fUnit.getInfoDisplay());
        items.add(null);
        List<String> slots = new ArrayList<>();
        slots.add("&7可生效的槽位:");
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (fUnit.isSupportSlot(slot)) {
                slots.add("&8⇨ &b" + slot.name());
            }
        }
        items.add(new CleanItemStack(Material.BOOK, "&a功能单元槽位限制", slots));
        items.add(null);
        items.add(
                maxLevel == null
                        ? new CleanItemStack(
                                Material.BOOK, "&a单装备等级限制", "&7最大等级: %s".formatted(fUnit.getMaxFULevel(null)))
                        : maxLevel);
        items.add(null);
        items.add(
                availableItem == null
                        ? new CleanItemStack(Material.BOOK, "&a可装配的装备范围", "&7支持装配至任意工具类物品")
                        : availableItem);
        items.add(null);
        items.add(
                cost == null
                        ? new CleanItemStack(
                                Material.BOOK,
                                "&a所需锻造材料",
                                "&7抽象锭 x%d".formatted(fUnit.getRarity().getLevel()))
                        : cost);
        items.add(MenuUtils.getDisplayItem(costSample));
        this.setDisplayRecipes(items);
        return this;
    }

    public EquipmentFUItem register() {
        complete();
        super.register();
        return this;
    }

    public void addInfo(ItemStack stack) {
        AddUtils.addGlow(stack);
        AddUtils.hideAllFlags(stack);
        var meta = stack.getItemMeta();
        meta.setDisplayName(fUnit.getRarity().getStyle() + meta.getDisplayName());
        stack.setItemMeta(meta);
    }
}
