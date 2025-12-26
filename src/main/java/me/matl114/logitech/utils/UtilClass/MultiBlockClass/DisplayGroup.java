package me.matl114.logitech.utils.UtilClass.MultiBlockClass;

import java.util.*;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.PdcClass.AbstractStringList;
import me.matl114.logitech.utils.WorldUtils;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.persistence.PersistentDataType;

public class DisplayGroup {
    private static final NamespacedKey KEY_LIST = AddUtils.getNameKey("child_display_list");
    private static final NamespacedKey KEY_NAMES = AddUtils.getNameKey("child_display_names");
    protected static NamespacedKey KEY_SOURCE = AddUtils.getNameKey("display-source");

    @Nonnull
    private final Interaction parentDisplay;

    private final Map<String, Display> displays;

    public DisplayGroup(@Nonnull Location location) {
        this(location, 1.0F, 1.0F);
    }

    public DisplayGroup(@Nonnull Location location, float height, float width) {
        this.displays = new HashMap();
        this.parentDisplay = (Interaction) location.getWorld().spawnEntity(location, EntityType.INTERACTION);
        this.parentDisplay.setInteractionHeight(height);
        this.parentDisplay.setInteractionWidth(width);
        this.parentDisplay
                .getPersistentDataContainer()
                .set(
                        KEY_SOURCE,
                        PersistentDataType.STRING,
                        DataCache.locationToString(new Location(
                                location.getWorld(),
                                location.getBlockX(),
                                location.getBlockY(),
                                location.getBlockZ())));
        this.applyLists(new ArrayList(), new ArrayList());
    }

    //    public DisplayGroup(@Nonnull Interaction textDisplay) {
    //        this.displays = new HashMap();
    //        this.parentDisplay = textDisplay;
    //        List<String> childList = this.getChildList();
    //        List<String> childNames = this.getChildNames();
    //        if (childList != null && childNames != null) {
    //            if (childList.size() != childNames.size()) {
    //                throw new IllegalStateException("This display's memory has been borked");
    //            } else {
    //                for(int i = 0; i < childList.size(); ++i) {
    //                    String s = (String)childList.get(i);
    //                    UUID uuid = UUID.fromString(s);
    //                    Entity entity = Bukkit.getEntity(uuid);
    //                    if (entity != null && !entity.isDead() && entity instanceof Display) {
    //                        Display display = (Display)entity;
    //                        this.displays.put((String)childNames.get(i), display);
    //                    }
    //                }
    //
    //            }
    //        } else {
    //            throw new IllegalArgumentException("This display was never part of a group");
    //        }
    //    }

    @Nonnull
    public Interaction getParentDisplay() {
        return this.parentDisplay;
    }

    public UUID getParentUUID() {
        return this.parentDisplay.getUniqueId();
    }

    public Location getLocation() {
        return this.parentDisplay.getLocation();
    }

    public Map<String, Display> getDisplays() {
        return Collections.unmodifiableMap(this.displays);
    }

    public void addDisplay(@Nonnull String name, @Nonnull Display display) {
        List<String> childList = this.getChildList();
        List<String> childNames = this.getChildNames();
        if (childList != null && childNames != null) {
            childList.add(display.getUniqueId().toString());
            childNames.add(name);
            this.applyLists(childList, childNames);
            this.displays.put(name, display);
        } else {
            throw new IllegalArgumentException("This display doesn't appear to have a group");
        }
    }

    @Nullable public Display removeDisplay(@Nonnull String name) {
        Display display = (Display) this.displays.remove(name);
        if (display == null) {
            return display;
        } else {
            List<String> childList = this.getChildList();
            List<String> childNames = this.getChildNames();
            if (childList != null && childNames != null) {
                childList.add(display.getUniqueId().toString());
                childNames.add(name);
                this.applyLists(childList, childNames);
                return display;
            } else {
                throw new IllegalArgumentException("This display doesn't appear to have a group");
            }
        }
    }

    public void killDisplay(@Nonnull String name) {
        Display display = this.removeDisplay(name);
        if (display != null) {
            display.remove();
        }
    }

    public void remove() {
        this.displays.forEach((s, display) -> {
            WorldUtils.executeOnSameEntity(display, (entity -> entity.remove()));
            display.remove();
        });
        WorldUtils.executeOnSameEntity(this.parentDisplay, (entity -> entity.remove()));
        this.parentDisplay.remove();
    }

    public void teleport(@Nonnull Location location) {
        this.displays.forEach((s, display) -> {
            Location offset = this.getParentDisplay().getLocation().subtract(display.getLocation());
            display.teleport(location.clone().add(offset));
        });
        this.getParentDisplay().teleport(location);
    }

    @Nullable private List<String> getChildList() {
        return (List<String>) this.parentDisplay.getPersistentDataContainer().get(KEY_LIST, AbstractStringList.TYPE);
        // return (List) PersistentDataAPI.get(this.parentDisplay, KEY_LIST, AbstractStringList.TYPE);
    }

    @Nullable private List<String> getChildNames() {
        return (List<String>) this.parentDisplay.getPersistentDataContainer().get(KEY_NAMES, AbstractStringList.TYPE);
        // return (List)PersistentDataAPI.get(this.parentDisplay, KEY_NAMES, AbstractStringList.TYPE);
    }

    private void applyLists(@Nonnull List<String> childList, @Nonnull List<String> childNames) {
        this.parentDisplay.getPersistentDataContainer().set(KEY_LIST, AbstractStringList.TYPE, childList);
        this.parentDisplay.getPersistentDataContainer().set(KEY_NAMES, AbstractStringList.TYPE, childNames);
    }

    public Collection<Display> getDisplaySet() {
        if (this.displays == null || this.displays.isEmpty()) {
            return new HashSet<>();
        } else {
            return this.displays.values();
        }
    }
    //    @Nullable
    //    public static dev.sefiraat.sefilib.entity.display.DisplayGroup fromUUID(@Nonnull UUID uuid) {
    //        Entity entity = Bukkit.getEntity(uuid);
    //        if (entity != null && !entity.isDead() && entity instanceof Interaction display) {
    //            return fromInteraction(display);
    //        } else {
    //            return null;
    //        }
    //    }
    //
    //    @Nullable
    //    public static dev.sefiraat.sefilib.entity.display.DisplayGroup fromInteraction(@Nonnull Interaction
    // interaction) {
    //        return PersistentDataAPI.has(interaction, KEY_LIST, AbstractStringList.TYPE) ? new
    // dev.sefiraat.sefilib.entity.display.DisplayGroup(interaction) : null;
    //    }
}
