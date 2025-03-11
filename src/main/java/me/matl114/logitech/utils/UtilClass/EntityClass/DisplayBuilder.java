package me.matl114.logitech.utils.UtilClass.EntityClass;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class DisplayBuilder <T extends DisplayBuilder<T> > {
    protected Transformation transformation;
    protected int interpolationDuration;
    protected boolean hasInterpolationDuration;
    protected int interpolationDelay;
    protected boolean hasInterpolationDelay;
    protected float viewRange;
    protected boolean hasViewRange;
    protected float shadowRadius;
    protected boolean hasShadowRadius;
    protected float shadowStrength;
    protected boolean hasShadowStrength;
    protected float displayWidth;
    protected boolean hasDisplayWidth;
    protected float displayHeight;
    protected boolean hasDisplayHeight;
    protected Display.Billboard billboard;
    protected Color glowColorOverride;
    protected Display.Brightness brightness;
    protected Location location;
    protected Vector groupParentOffset;

    DisplayBuilder() {
    }

    public T setTransformation(@Nonnull Transformation transformation) {
        this.transformation = transformation;
        return (T)this;
    }

    public T setInterpolationDuration(int interpolationDuration) {
        this.interpolationDuration = interpolationDuration;
        this.hasInterpolationDuration = true;
        return (T)this;
    }

    public T setInterpolationDelay(int interpolationDelay) {
        this.interpolationDelay = interpolationDelay;
        this.hasInterpolationDelay = true;
        return (T)this;
    }

    public T setViewRange(float viewRange) {
        this.viewRange = viewRange;
        this.hasViewRange = true;
        return (T)this;
    }

    public T setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
        this.hasShadowRadius = true;
        return (T)this;
    }

    public T setShadowStrength(float shadowStrength) {
        this.shadowStrength = shadowStrength;
        this.hasShadowStrength = true;
        return (T)this;
    }

    public T setDisplayWidth(float displayWidth) {
        this.displayWidth = displayWidth;
        this.hasDisplayWidth = true;
        return (T)this;
    }

    public T setDisplayHeight(float displayHeight) {
        this.displayHeight = displayHeight;
        this.hasDisplayHeight = true;
        return (T)this;
    }

    public T setBillboard(@Nonnull Display.Billboard billboard) {
        this.billboard = billboard;
        return (T)this;
    }

    public T setGlowColorOverride(@Nullable Color glowColorOverride) {
        this.glowColorOverride = glowColorOverride;
        return (T)this;
    }

    public T setBrightness(@Nullable Display.Brightness brightness) {
        this.brightness = brightness;
        return (T)this;
    }

    public T setLocation(Location location) {
        this.location = location;
        return (T)this;
    }

    public T setGroupParentOffset(Vector groupParentOffset) {
        this.groupParentOffset = groupParentOffset;
        return (T)this;
    }

    protected void applyDisplay(@Nonnull Display display) {
        if (this.transformation != null) {
            display.setTransformation(this.transformation);
        }

        if (this.hasInterpolationDuration) {
            display.setInterpolationDuration(this.interpolationDuration);
        }

        if (this.hasInterpolationDelay) {
            display.setInterpolationDelay(this.interpolationDelay);
        }

        if (this.hasViewRange) {
            display.setViewRange(this.viewRange);
        }

        if (this.hasShadowRadius) {
            display.setShadowRadius(this.shadowRadius);
        }

        if (this.hasShadowStrength) {
            display.setShadowStrength(this.shadowStrength);
        }

        if (this.hasDisplayWidth) {
            display.setDisplayWidth(this.displayWidth);
        }

        if (this.hasDisplayHeight) {
            display.setDisplayHeight(this.displayHeight);
        }

        if (this.billboard != null) {
            display.setBillboard(this.billboard);
        }

        if (this.glowColorOverride != null) {
            display.setGlowColorOverride(this.glowColorOverride);
        }

        if (this.brightness != null) {
            display.setBrightness(this.brightness);
        }

    }
}
