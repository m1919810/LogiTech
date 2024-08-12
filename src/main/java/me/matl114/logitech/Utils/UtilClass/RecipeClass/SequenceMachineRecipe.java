package me.matl114.logitech.Utils.UtilClass.RecipeClass;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import net.guizhanss.guizhanlib.minecraft.helper.inventory.ItemStackHelper;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SequenceMachineRecipe extends MachineRecipe {
    public String[] displayedNames;
    public SequenceMachineRecipe(int ticks, ItemStack[] inputs, ItemStack[] outputs) {
        super(0, inputs, outputs);
        this.setTicks(ticks);
        int len=inputs.length;
        this.displayedNames = new String[len];
        for(int i=0; i<len; i++) {
            displayedNames[i]="&7⇨ %%s%%-3d&7/%%s%-3d %s".formatted(inputs[i].getAmount(),ItemStackHelper.getDisplayName(inputs[i]));
        }
    }
}
