package me.matl114.logitech.core.Interface;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import io.github.thebusybiscuit.slimefun4.utils.ChargeUtils;
import me.matl114.matlib.algorithms.algorithm.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public interface LogiTechChargable extends Rechargeable {
    static void setCharge(ItemStack item, float charge, float maximum) {
        if (item != null && item.getType() != Material.AIR) {

            if (!(charge < 0.0F) && !(charge > maximum)) {
                ItemMeta meta = item.getItemMeta();
                ChargeUtils.setCharge(meta, charge, maximum);
                item.setItemMeta(meta);
            } else {
                throw new IllegalArgumentException("Charge must be between zero and " + maximum + ".");
            }
        } else {
            throw new IllegalArgumentException("Cannot set Item charge for null or AIR");
        }
    }

    static float getCharge(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            return ChargeUtils.getCharge(item.getItemMeta());
        } else {
            throw new IllegalArgumentException("Cannot get Item charge for null or AIR");
        }
    }

    static float addCharge(ItemStack item, float charge, float maximum) {
        Validate.isTrue(charge > 0.0F, "Charge must be above zero!");
        if (item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.getItemMeta();
            float currentCharge = ChargeUtils.getCharge(meta);
            if (currentCharge >= maximum) {
                return charge;
            } else {
                float chargeUnlimited = currentCharge + charge;
                float newCharge = Math.min(chargeUnlimited, maximum);
                ChargeUtils.setCharge(meta, newCharge, maximum);
                item.setItemMeta(meta);
                return chargeUnlimited - newCharge;
            }
        } else {
            throw new IllegalArgumentException("Cannot add Item charge for null or AIR");
        }
    }

    static boolean removeCharge(ItemStack item, float charge, float maximum) {
        Validate.isTrue(charge > 0.0F, "Charge must be above zero!");
        if (item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.getItemMeta();
            float currentCharge = ChargeUtils.getCharge(meta);
            if (currentCharge < charge) {
                return false;
            } else {
                float newCharge = Math.max(currentCharge - charge, 0.0F);
                ChargeUtils.setCharge(meta, newCharge, maximum);
                item.setItemMeta(meta);
                return true;
            }
        } else {
            throw new IllegalArgumentException("Cannot remove Item charge for null or AIR");
        }
    }
    static List<Function<ItemMeta,Float>> moreCustomCharges = new ArrayList<>();
    static AtomicReference<Function<ItemMeta,Float>[]> predicates = new AtomicReference<>(new Function[0]) ;
    static void registerCustomChargables(Function<ItemMeta,Float> howToGetMaxCharge){
        moreCustomCharges.add(howToGetMaxCharge);
        predicates.set(moreCustomCharges.toArray(Function[]::new));
    }
    //返回溢出量,设置电力为max(charge,maxCharge),返回多余的
    //不会考虑数量
    static float setChargeSafe(ItemStack item, float charge) {
        ItemMeta meta = item.getItemMeta();
        if(Slimefun.getItemDataService().getItemData(meta).map(SlimefunItem::getById).orElse(null) instanceof Rechargeable rechargeable ){
            float outFlow ;
            float newCharge ;
            float maxCharge = rechargeable.getMaxItemCharge(item);
            if( charge > maxCharge){
                outFlow = charge - maxCharge;
                newCharge = maxCharge;
            }else{
                outFlow = 0;
                newCharge = charge;
            }
            rechargeable.setItemCharge(item, newCharge);
            return outFlow;
        }else {
            Float maxCharge = Utils.computeTilPresent(meta,predicates.get());
            if(maxCharge!=null){
                float outFlow;
                float newCharge ;
                if(charge > maxCharge){
                    outFlow = charge -maxCharge;
                    newCharge = maxCharge;
                }else {
                    outFlow = 0;
                    newCharge = charge;
                }
                setCharge(item,newCharge,maxCharge);
                return outFlow;
            }else{
                return -1;
            }
        }
    }
    @Nullable
    static Float getMaxItemChargeOrNull(@Nonnull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta!=null){
            if(Slimefun.getItemDataService().getItemData(meta).map(SlimefunItem::getById).orElse(null) instanceof Rechargeable rechargeable ){
                return rechargeable.getMaxItemCharge(item);
            }else {
                return Utils.computeTilPresent(meta,predicates.get());
            }
        }
        return null;
    }
    //尝试改变charge
    //返回无法改变的量 例如change +3 实际+2 返回+1
    //当无法充电时返回charge
    //会考虑数量
    static float changeChargeSafe(ItemStack item, float charge){
        return changeChargeSafe(item, charge, null);
    }

    static float changeChargeSafe(ItemStack item, float charge, ChargePredicate predicate){
        ItemMeta meta = item.getItemMeta();
        Float maxCharge = getMaxItemChargeOrNull(item);
        if(maxCharge == null){
            return charge;
        }
        int amount = item.getAmount();
        float singleCharge = charge/amount;
        float nowCharge = ChargeUtils.getCharge(meta);
        if(predicate != null && predicate.shouldCharge(meta, maxCharge, nowCharge, singleCharge)){
            return charge;
        }
        float newCharge = singleCharge + nowCharge;
        float left = 0;
        if( newCharge>maxCharge){
            left = (newCharge - maxCharge )* amount;
            newCharge = maxCharge;
        }else if(newCharge <0){
            left = newCharge * amount;
            newCharge = 0;
        }
        ChargeUtils.setCharge(meta, newCharge, maxCharge);
        item.setItemMeta(meta);
        return left;
    }

    public static interface ChargePredicate{
        public boolean shouldCharge(ItemMeta meta, float maxCharg, float charge, float deltaCharge);
    }
}
