package me.matl114.logitech.core.Registries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.matl114.logitech.Utils.UtilClass.EquipClass.EquipmentFU;
import me.matl114.matlib.Utils.Algorithm.Triplet;
import me.matl114.matlib.core.EnvironmentManager;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static me.matl114.logitech.Utils.AddUtils.*;

public class EFUImplements {
    public static void init(){

    }
    public static final EquipmentFU DEMO = new EquipmentFU(getNameKey("demo_fu"),EquipmentFU.FURarity.COMMON, EquipmentSlot.HAND);
    public static final EquipmentFU BIGSNAKE_KILLER =new EquipmentFU(getNameKey("bigsnake-killer"),EquipmentFU.FURarity.RARE, EquipmentSlot.HAND);{

        {

        }
    };


    public static class SimplePotionEffectEquipmentFU extends EquipmentFU{
        List<PotionEffect> effects;
        //A*X+B 级别
        public SimplePotionEffectEquipmentFU(NamespacedKey key, FURarity rarity,int maxLevel ,List<Triplet<String,Integer,Float>> potionEffectList, EquipmentSlot... slot) {
            super(key, rarity, slot);
        }

    }
    @Getter
    @AllArgsConstructor
    public static class LevelPotionEffectProvider{
        private PotionEffectType effect;
        private int baseLevel;
        private float amplifier;
        public static LevelPotionEffectProvider of(Triplet<String,Integer,Float> t){
            PotionEffectType type = EnvironmentManager.getManager().getVersioned().getPotionEffectType(t.getA());
            return new LevelPotionEffectProvider(type,t.getB(),t.getC());
        }
        public static LevelPotionEffectProvider of(PotionEffectType effect, int baseLevel, float amplifier){
            return new LevelPotionEffectProvider(effect, baseLevel, amplifier);
        }

    }
}
