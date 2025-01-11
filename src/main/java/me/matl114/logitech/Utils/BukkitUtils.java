package me.matl114.logitech.Utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Manager.Schedules;
import me.matl114.logitech.core.AddSlimefunItems;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ShapedMachineRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class BukkitUtils {
    public static final RecipeType VANILLA_CRAFTTABLE =new RecipeType(AddUtils.getNameKey("vanilla_crafttable"),
            new CustomItemStack(Material.CRAFTING_TABLE,null,"","&6万物起源 工作台"));
    public static final RecipeType VANILLA_FURNACE=new RecipeType(AddUtils.getNameKey("vanilla_furnace"),
            new CustomItemStack(Material.FURNACE,null,"","&6原版熔炉"));

    public static void sendRecipeToVanilla(NamespacedKey key,ShapedMachineRecipe recipe){
        sendRecipeToVanilla(key,recipe.getInput(),recipe.getOutput()[0]);
    }
    public static void sendRecipeToVanilla(SlimefunItem sfitem){
        sendRecipeToVanilla(AddUtils.getNameKey(sfitem.getId()+"_vl"),sfitem.getRecipe(),sfitem.getRecipeOutput());
    }
    public static void setup(){
        addMoreRecipes();
    }
    public static void addMoreRecipes(){
        sendRecipeToVanilla(AddSlimefunItems.CRAFT_MANUAL);
        sendRecipeToVanilla(AddSlimefunItems.ENHANCED_CRAFT_MANUAL);
        sendRecipeToVanilla(AddSlimefunItems.MAGIC_WORKBENCH_MANUAL);
    }
    public static void sendRecipeToVanilla(NamespacedKey key, ItemStack[] input, ItemStack output){
        if(input.length>9){
            Debug.logger("this recipe can not be sent to Crafting Table: ",key.toString());
            return;
        }
        RecipeChoice[] inputChoice=new RecipeChoice[9];
        for(int i=0;i<input.length;i++){
            inputChoice[i]=input[i]==null?null: new RecipeChoice.ExactChoice(input[i]);
        }
        ShapedRecipe vanillaRecipe=new ShapedRecipe(key,output);
        String[] pattern=new String[3];
        for(int i=0;i<3;i++){
            StringBuilder builder=new StringBuilder();
            for(int j=0;j<3;j++){
                builder.append(inputChoice[3*i+j]==null?" ":String.valueOf(3*i+j).charAt(0));
            }
            pattern[i]=builder.toString();
        }
        vanillaRecipe.shape(pattern);
        for(int i=0;i<9;i++){
            if(inputChoice[i]!=null){
                vanillaRecipe.setIngredient(String.valueOf(i).charAt(0),inputChoice[i]);
            }
        }

        Bukkit.addRecipe(vanillaRecipe);
    }
    public static void executeSync(Runnable runnable){
        if(Bukkit.isPrimaryThread()){
            runnable.run();
        }else {
            Schedules.launchSchedules(
                    runnable,0,true,0
            );
        }
    }
    public static void executeAsync(Runnable runnable){
        if(Bukkit.isPrimaryThread()){
            Schedules.launchSchedules(
                    runnable,0,false,0
            );
        }else {
            runnable.run();
        }
    }
}
