package me.matl114.logitech.core.Items.Equipments;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop.InterfacedSmithTableTrimmer;
import me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop.SmithInterfaceProcessor;
import me.matl114.logitech.core.Items.Abstracts.MaterialItem;
import me.matl114.matlib.algorithms.dataStructures.struct.Triplet;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingInventory;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class DisplayUseTrimmerLogicLate extends MaterialItem implements InterfacedSmithTableTrimmer {
    private final Function<SmithingInventory, Triplet<ItemStack[],Integer,Integer>> trimmerLogic;
    private final Supplier<Set<ItemStack>> trimmerItems;
    private final Supplier<RecipeChoice> trimmerBase;
    private final Supplier<RecipeChoice> trimmerExtra;
    private RecipeChoice trimmerChoice;
    private RecipeChoice trimmerBaseChoice;
    private RecipeChoice trimmerExtraChoice;
    public DisplayUseTrimmerLogicLate(ItemGroup itemGroup, SlimefunItemStack item, ItemStack[] displayUse, Supplier<Set<ItemStack>> trimmerItemLatelyInitFunc,Supplier<RecipeChoice> baseItemLatelyInitFunc,Supplier<RecipeChoice> extraItemLatelyInitFunc , Function<SmithingInventory,Triplet<ItemStack[],Integer,Integer>> trimmerLogic) {
        super(itemGroup, item, SmithInterfaceProcessor.INTERFACED_SMITH_TRIM, displayUse);
        this.trimmerLogic = trimmerLogic;
        this.trimmerItems = trimmerItemLatelyInitFunc;
        this.trimmerBase = baseItemLatelyInitFunc;
        this.trimmerExtra = extraItemLatelyInitFunc;
    }

    @Override
    public Triplet<ItemStack[],Integer,Integer> getTrimmedResult(SmithingInventory inventory) {
        return trimmerLogic.apply(inventory);
    }

    @Override
    public RecipeChoice getTrimmerRecipeChoice() {
        if(trimmerChoice == null) {
            trimmerChoice = new RecipeChoice.ExactChoice(trimmerItems.get().stream().filter(Objects::nonNull).toList());
        }
        return trimmerChoice;
    }

    @Override
    public RecipeChoice getBaseRecipeChoice() {
        if(trimmerBaseChoice == null) {
            trimmerBaseChoice = trimmerBase.get();
        }
        return trimmerBaseChoice;
    }

    @Override
    public RecipeChoice getExtraRecipeChoice() {
        if(trimmerExtraChoice == null) {
            trimmerExtraChoice = trimmerExtra.get();
        }
        return trimmerExtraChoice;
    }

}
