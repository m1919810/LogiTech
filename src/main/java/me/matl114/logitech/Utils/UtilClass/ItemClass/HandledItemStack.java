package me.matl114.logitech.Utils.UtilClass.ItemClass;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class HandledItemStack extends ItemStack {
    public ItemStack handle;
    public ItemMeta handleMeta;
    private ItemMeta createItemMeta(){
        handleMeta = handle.getItemMeta();
        return handleMeta;
    }
    public HandledItemStack(ItemStack itemStack) {
        super();
        this.handle = itemStack;
    }
    @Nullable
    public ItemMeta getItemMeta() {
        return this.handleMeta==null?createItemMeta():this.handleMeta;
    }

    public boolean hasItemMeta() {
        return handle.hasItemMeta();
    }

    public boolean setItemMeta(@Nullable ItemMeta itemMeta) {

        this.handleMeta = itemMeta;
        return true;
    }



    public Material getType() {
        return this.handle.getType();
    }

    public void setType( Material type) {
        this.handle.setType(type);
    }

    public int getAmount() {
        return this.handle.getAmount();
    }

    public void setAmount(int amount) {
        this.handle.setAmount(amount);
    }

    @Nullable
    public MaterialData getData() {
        return this.handle.getData();
    }

    public void setData(@Nullable MaterialData data) {
        this.handle.setData(data);
    }

    /** @deprecated */
    @Deprecated
    public void setDurability(short durability) {
       this.handle.setDurability(durability);

    }

    /** @deprecated */
    @Deprecated
    public short getDurability() {
        return   this.handle.getDurability();
    }

    public int getMaxStackSize() {
        return this.handle.getMaxStackSize();
    }



    public String toString() {
        return this.handle.toString();
    }

    public boolean equals(Object obj) {
        return this.handle.equals(obj);
    }

    public boolean isSimilar(@Nullable ItemStack stack) {
        return this.handle.isSimilar(stack);
    }

    public ItemStack clone() {
        return this.handle.clone();
    }

    public int hashCode() {
        return this.handle.hashCode();
    }

    public boolean containsEnchantment( Enchantment ench) {
        return this.handle.containsEnchantment(ench);
    }

    public int getEnchantmentLevel( Enchantment ench) {
        return this.handle.getEnchantmentLevel(ench);
    }


    public Map<Enchantment, Integer> getEnchantments() {
        return this.handle.getEnchantments();
    }

    public void addEnchantments( Map<Enchantment, Integer> enchantments) {
        this.handle.addEnchantments(enchantments);

    }

    public void addEnchantment(Enchantment ench, int level) {
        this.handle.addEnchantment(ench, level);
    }

    public void addUnsafeEnchantments( Map<Enchantment, Integer> enchantments) {
        this.handle.addUnsafeEnchantments(enchantments);

    }

    public void addUnsafeEnchantment( Enchantment ench, int level) {
        this.handle.addUnsafeEnchantment(ench, level);
    }

    public int removeEnchantment( Enchantment ench) {
        return this.handle.removeEnchantment(ench);
    }

    public void removeEnchantments() {
        this.handle.removeEnchantments();
    }

    public Map<String, Object> serialize() {
        return this.handle.serialize();
    }





    public String getTranslationKey() {
        return Bukkit.getUnsafe().getTranslationKey(this);
    }
}
