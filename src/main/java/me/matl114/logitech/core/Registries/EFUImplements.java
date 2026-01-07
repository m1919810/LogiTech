package me.matl114.logitech.core.Registries;

import static me.matl114.logitech.core.AddItem.LSINGULARITY;
import static me.matl114.logitech.utils.AddUtils.getNameKey;
import static org.bukkit.inventory.EquipmentSlot.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.utils.UtilClass.EquipClass.EquipmentFU;
import me.matl114.matlib.algorithms.dataStructures.struct.Triplet;
import me.matl114.matlib.utils.version.VersionedRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EFUImplements {
    public static void init() {}

    public static final EquipmentSlot[] ARMOR = {HEAD, CHEST, LEGS, FEET};
    public static final EquipmentSlot[] ALLSLOT = {HEAD, CHEST, LEGS, FEET, HAND, OFF_HAND};
    public static final EquipmentSlot[] ANYHAND = {HAND, OFF_HAND};
    public static final EquipmentFU DEMO =
            new SimpleFU(getNameKey("demo_fu"), EquipmentFU.FURarity.COMMON, EquipmentSlot.HAND) {};

    public static final EquipmentFU MINER = new SimplePotionEffectEquipmentFU(
            "攻速强化",
            getNameKey("att-speed-fu"),
            EquipmentFU.FURarity.COMMON,
            10,
            16,
            List.of(Triplet.of("haste", 1, 0.5f)),
            ANYHAND);
    //    public static final EquipmentFU BIGSNAKE_KILLER =new
    // EquipmentFU(getNameKey("bigsnake-killer"),EquipmentFU.FURarity.RARE, EquipmentSlot.HAND);{
    //        {
    //
    //        }
    //    };

    // A default sample of what you can do
    public static class SimpleFU extends EquipmentFU {
        public SimpleFU(NamespacedKey key, FURarity rarity, EquipmentSlot... slot) {
            super(key, rarity, slot);
        }
        // used for RecipeChoice,get Supported cost Material
        public Set<ItemStack> getEquipCostable() {
            return Set.of(LSINGULARITY);
        }
        // set max level here,limit setting to
        public int getMaxFULevel(@Nullable ItemStack item) {
            return 10;
        }
        // get equiping cost
        public int getEquipCost(ItemStack alreadyEquiped, ItemStack cost) {
            return getRarity().getLevel();
        }
        // get equiping progress time cost
        public int getEquipTimeCost(ItemStack alreadyEquiped, ItemStack cost) {
            return 12;
        }
    }

    public static class SimplePotionEffectEquipmentFU extends SimpleFU {
        List<LevelPotionEffectProvider> effects;
        int maxLevel;
        int maxTotalLevel;
        // A*X+B 级别
        public SimplePotionEffectEquipmentFU(
                String name,
                NamespacedKey key,
                FURarity rarity,
                int maxLevel,
                int maxTotalLevel,
                List<Triplet<String, Integer, Float>> potionEffectList,
                EquipmentSlot... slot) {
            super(key, rarity, slot);
            effects =
                    potionEffectList.stream().map(LevelPotionEffectProvider::of).toList();
            setDisplayName(name);
            this.maxLevel = maxLevel;
            this.maxTotalLevel = maxTotalLevel;
        }

        public ItemStack getInfoDisplay() {
            List<String> infos = new ArrayList<>();
            infos.add("提供指定的药水效果增益");
            infos.add("不同槽位的单元等级可叠加,最大%d级".formatted(maxTotalLevel));
            infos.add("药水效果每5秒刷新一次");
            infos.add("该单元提供的药水效果等级如下:");
            effects.forEach(effectProvider -> {
                infos.add("⇨ §b%s : §7".formatted(CraftUtils.getEffectName(effectProvider.getEffect()))
                        + (effectProvider.getBaseLevel() == 0 ? "" : "%d+".formatted(effectProvider.getBaseLevel()))
                        + ".1f*<单元等级>".formatted(effectProvider.getAmplifier()));
            });
            return getInfoFor(getFullName(), infos.toArray(String[]::new));
        }
        // set max level here,limit setting to
        public int getMaxFULevel(ItemStack item) {
            return maxLevel;
        }

        @Override
        public int getSFTickPeriod() {
            return 10;
        }

        @Override
        public void onSFTick(Player player, int level, int tickCount) {
            effects.forEach(effectProvider -> effectProvider.applyEffect(player, Math.min(level, maxTotalLevel)));
        }

        @Getter
        @AllArgsConstructor
        public static class LevelPotionEffectProvider {
            private PotionEffectType effect;
            private int baseLevel;
            private float amplifier;

            public static LevelPotionEffectProvider of(Triplet<String, Integer, Float> t) {
                PotionEffectType type = VersionedRegistry.getInstance().getPotionEffectType(t.getA());
                return new LevelPotionEffectProvider(type, t.getB(), t.getC());
            }

            public static LevelPotionEffectProvider of(PotionEffectType effect, int baseLevel, float amplifier) {
                return new LevelPotionEffectProvider(effect, baseLevel, amplifier);
            }

            public void applyEffect(Player player, int level) {
                // 太高还是算了
                int potionLevel = Math.min(baseLevel + (int) (amplifier * level), 250);
                player.addPotionEffect(new PotionEffect(effect, 200, potionLevel), true);
            }
        }
    }
}
