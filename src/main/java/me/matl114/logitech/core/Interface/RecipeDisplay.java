package me.matl114.logitech.core.Interface;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import java.util.ArrayList;
import java.util.List;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Settings;
import me.matl114.logitech.utils.UtilClass.ItemClass.AbstractItemStack;
import me.matl114.logitech.utils.UtilClass.ItemClass.DisplayItemStack;
import me.matl114.logitech.utils.UtilClass.ItemClass.MultiItemStack;
import me.matl114.logitech.utils.UtilClass.ItemClass.RandAmountStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.ItemStack;

public interface RecipeDisplay extends RecipeDisplayItem {
    List<MachineRecipe> provideDisplayRecipe();

    default List<ItemStack> getDisplayRecipes() {
        return this._getDisplayRecipes(new ArrayList<>());
    }

    static ItemStack addRecipeInfo(ItemStack stack, Settings settings, int index, Double pro, int time) {
        if (stack == null
                || (!(stack instanceof AbstractItemStack) && stack.getType().isAir())) {
            // Debug.logger("Catch unexpected null item:",stack,"at id");
            return null;
        }
        List<String> lore = new ArrayList<>();
        switch (settings) {
            case INPUT:
                if (pro >= 0.0) {
                    lore.add("");
                    lore.add("&a材料 " + (index + 1));

                    if (pro < 1.0) {
                        lore.add("&e等价物品输入");
                        // lore.add("&e权重: " + AddUtils.getPercentFormat(pro));
                    }
                }
                if (stack.getAmount() > 64 || stack.getAmount() <= 0) {
                    lore.add("&c输入数量: " + stack.getAmount());
                }
                stack = AddUtils.addLore(stack, lore.toArray(String[]::new));
                break;
            case OUTPUT:
            default:
                if (pro >= 0.0) {
                    lore.add("");
                    lore.add("&a产物 " + (index + 1));

                    if (pro < 1.0) {
                        lore.add("&e随机物品输出");
                        lore.add("&e概率: " + AddUtils.getPercentFormat(pro));
                    }
                }
                if (stack instanceof RandAmountStack rand) {
                    lore.add("&c随机输出数量: %d~%d".formatted(rand.getMin(), rand.getMax()));
                } else if (stack.getAmount() > 64 || stack.getAmount() <= 0) {
                    lore.add("&c输出数量: " + stack.getAmount());
                }
                if (time >= 0) lore.add("&e进程耗时: " + (int) (time / 2) + "s (" + time + "tick)");
                else lore.add("&e直接合成~");
                stack = AddUtils.addLore(stack, lore.toArray(String[]::new));

                break;
        }
        if (stack.getAmount() <= 0) {
            stack.setAmount(1);
        }
        return stack;
    }

    default List<ItemStack> _getDisplayRecipes(List<ItemStack> displayRecipe) {

        if (displayRecipe == null || displayRecipe.isEmpty()) {
            displayRecipe = new ArrayList<>();
        }
        ArrayList<ItemStack> displayRecipes = new ArrayList<>(displayRecipe);
        List<MachineRecipe> recipes = provideDisplayRecipe();
        if (recipes == null || recipes.isEmpty()) {
            return displayRecipes;
        }
        if (displayRecipes.size() % 2 == 1) displayRecipes.add(null);

        for (int i = 0; i < recipes.size(); i++) {

            MachineRecipe recipe = recipes.get(i);
            ItemStack[] input = recipe.getInput();
            ItemStack[] output = recipe.getOutput();
            List<ItemStack> getInputList = new ArrayList<>();
            List<ItemStack> getOutputList = new ArrayList<>();
            //            try{
            for (int j = 0; j < input.length; j++) {
                if (input[j] == null) {
                    break;
                } else if (input[j] instanceof DisplayItemStack) {
                    getInputList.add(addRecipeInfo(input[j], Settings.INPUT, j, -1.0, recipe.getTicks()));
                } else if (input[j] instanceof MultiItemStack) {
                    List<ItemStack> tmp = ((MultiItemStack) input[j]).getItemStacks();
                    List<Double> a = ((MultiItemStack) input[j]).getWeight(1.0);
                    for (int k = 0; k < tmp.size(); k++) {
                        getInputList.add(addRecipeInfo(tmp.get(k), Settings.INPUT, j, a.get(k), recipe.getTicks()));
                    }
                } else {
                    getInputList.add(addRecipeInfo(input[j], Settings.INPUT, j, 1.0, recipe.getTicks()));
                }
            }
            for (int j = 0; j < output.length; j++) {
                if (output[j] == null) {
                    break;
                } else if (output[j] instanceof DisplayItemStack) {
                    getOutputList.add(addRecipeInfo(output[j], Settings.OUTPUT, j, -1.0, recipe.getTicks()));
                } else if (output[j] instanceof MultiItemStack) {
                    List<ItemStack> tmp = ((MultiItemStack) output[j]).getItemStacks();
                    List<Double> a = ((MultiItemStack) output[j]).getWeight(1.0);
                    for (int k = 0; k < tmp.size(); k++) {
                        getOutputList.add(addRecipeInfo(tmp.get(k), Settings.OUTPUT, j, a.get(k), recipe.getTicks()));
                    }
                } else {
                    getOutputList.add(addRecipeInfo(output[j], Settings.OUTPUT, j, 1.0, recipe.getTicks()));
                }
            }
            int len1 = getInputList.size();
            int len2 = getOutputList.size();
            int len = Math.max(len1, len2);
            int endp1 = len1;
            int endp2 = len - len2;
            int t = 0;
            int s = 0;
            for (int j = 0; j < len; ++j) {
                ItemStack tmp;
                if (j < endp1) {
                    displayRecipes.add(getInputList.get(t));
                    ++t;
                } else displayRecipes.add(null);

                if (j >= endp2) {
                    displayRecipes.add(getOutputList.get(s));
                    ++s;
                } else displayRecipes.add(null);
            }
            //            }catch (Throwable e){
            //                Debug.logger("catch unexpected exception:",e,"at recipe:",recipe.getOutput()[0],"at
            // sfitem",((SlimefunItem)this).getId());
            //            }
        }

        return displayRecipes;
    }
}
