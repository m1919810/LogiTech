package me.matl114.logitech.utils.UtilClass.EntityClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.DisplayGroup;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemDisplayBuilder extends DisplayBuilder<ItemDisplayBuilder> {
    protected ItemStack itemStack;
    protected ItemDisplay.ItemDisplayTransform itemDisplayTransform;
    protected String source;

    public ItemDisplayBuilder() {}

    public ItemDisplayBuilder setItemStack(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemDisplayBuilder setItemDisplayTransform(@Nonnull ItemDisplay.ItemDisplayTransform itemDisplayTransform) {
        this.itemDisplayTransform = itemDisplayTransform;
        return this;
    }

    public ItemDisplayBuilder setSource(@Nonnull String source) {
        this.source = source;
        return this;
    }

    public ItemDisplay build() {
        if (this.location == null) {
            throw new IllegalStateException("You must provide a location for the Display Entity");
        } else {
            return this.generateDisplay(this.location);
        }
    }

    public ItemDisplay build(@Nonnull DisplayGroup group) {
        if (this.groupParentOffset == null) {
            throw new IllegalStateException("You must provide a Group Parent Offset vector");
        } else {
            return this.generateDisplay(group.getLocation().clone().add(this.groupParentOffset));
        }
    }

    protected static NamespacedKey KEY_SOURCE = AddUtils.getNameKey("display-source");

    private ItemDisplay markSource(ItemDisplay itemDisplay) {
        String sour = source == null ? "default" : source;
        itemDisplay.getPersistentDataContainer().set(KEY_SOURCE, PersistentDataType.STRING, sour);
        return itemDisplay;
    }

    public static String getSource(Entity e) {
        PersistentDataContainer container = e.getPersistentDataContainer();
        if (container.has(KEY_SOURCE, PersistentDataType.STRING)) {
            return container.get(KEY_SOURCE, PersistentDataType.STRING);
        } else {
            return null;
        }
    }

    private ItemDisplay generateDisplay(@Nonnull Location location) {
        ItemDisplay display = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
        display.setItemStack(this.itemStack);
        if (this.itemDisplayTransform != null) {
            display.setItemDisplayTransform(this.itemDisplayTransform);
        }
        this.applyDisplay(display);
        this.markSource(display);
        return display;
    }
}
