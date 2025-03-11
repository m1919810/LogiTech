package me.matl114.logitech.utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.Algorithms.PairList;
import me.matl114.logitech.core.AddSlimefunItems;
import me.matl114.logitech.utils.UtilClass.RecipeClass.ShapedMachineRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.Locale;
import java.util.Optional;

public class BukkitUtils {
    public static final RecipeType VANILLA_CRAFTTABLE =new RecipeType(AddUtils.getNameKey("vanilla_crafttable"),
            new CustomItemStack(Material.CRAFTING_TABLE,null,"","&6万物起源 工作台"));
    public static final RecipeType VANILLA_FURNACE=new RecipeType(AddUtils.getNameKey("vanilla_furnace"),
            new CustomItemStack(Material.FURNACE,null,"","&6原版熔炉"));

    public static void sendRecipeToVanilla(NamespacedKey key,ShapedMachineRecipe recipe){
        sendShapedRecipeToVanilla(key,recipe.getInput(),recipe.getOutput()[0]);
    }
    public static Optional<String> getOptionalVanillaMyPluginRecipe(NamespacedKey key){
        try{
            if(AddUtils.isNamespace(key)){
                String valueId = key.getKey();
                valueId = valueId.substring(0,valueId.length()-3).toUpperCase(Locale.ROOT);
                return Optional.of(valueId);
            }
            return Optional.empty();
        }catch (Throwable e){
            return Optional.empty();
        }
    }
    public static Optional<SlimefunItem> getOptionalVanillaSlimefunRecipe(NamespacedKey key){
        try{
            if(AddUtils.isNamespace(key)){
                String valueId = key.getKey();
                valueId = valueId.substring(0,valueId.length()-3).toUpperCase(Locale.ROOT);
                return Optional.ofNullable(SlimefunItem.getById(valueId));
            }
            return Optional.empty();
        }catch (Throwable e){
            return Optional.empty();
        }
    }
    public static void sendRecipeToVanilla(SlimefunItem sfitem,Class<?> craftingType){
        sendRecipeToVanilla(sfitem.getId(),sfitem.getRecipe(),sfitem.getRecipeOutput(),craftingType);

    }
    public static NamespacedKey getIdKey(String uniqueId){
        return AddUtils.getNameKey(uniqueId+"_vl");
    }
    public static void sendRecipeToVanilla(String uniqueId,ItemStack[] in,ItemStack out,Class<?> craftingType){
        if (craftingType==ShapedRecipe.class) {
            sendShapedRecipeToVanilla(AddUtils.getNameKey(uniqueId+"_vl"),in,out);
        }else if(craftingType== SmithingTrimRecipe.class){
            sendSmithRecipeToVanilla(AddUtils.getNameKey(uniqueId+"_vl"),in,out,true);
        }else if(craftingType==SmithingTransformRecipe.class){
            sendSmithRecipeToVanilla(AddUtils.getNameKey(uniqueId+"_vl"),in,out,false);
        }
//        else{
//            throw new IllegalArgumentException("Invalid recipe class: "+craftingType);
//        }

    }
    private static final PairList<SlimefunItem,Class<?>> moreVanillaRecipes =  new PairList<>();
//    public static void registerMoreVanillaRecipeTypes(SlimefunItem recipeItem,Class<?> clazz) {
//        moreVanillaRecipes.put(recipeItem,clazz);
//    }
    public static void setup(){
        addMoreRecipes();
    }
    public static void addMoreRecipes(){
        sendRecipeToVanilla(AddSlimefunItems.CRAFT_MANUAL,ShapedRecipe.class);
        sendRecipeToVanilla(AddSlimefunItems.ENHANCED_CRAFT_MANUAL,ShapedRecipe.class);
        sendRecipeToVanilla(AddSlimefunItems.MAGIC_WORKBENCH_MANUAL,ShapedRecipe.class);
        moreVanillaRecipes.forEach(pair->sendRecipeToVanilla(pair.getFirstValue(),pair.getSecondValue()));
    }
    public static void sendShapedRecipeToVanilla(NamespacedKey key, ItemStack[] input, ItemStack output){
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
    public static void sendSmithRecipeToVanilla(NamespacedKey key,ItemStack[] input,ItemStack output,boolean isTrimOrTransform){
        SmithingRecipe recipe;
        RecipeChoice input1 = new RecipeChoice.ExactChoice(input[0]);
        RecipeChoice input2 = new RecipeChoice.ExactChoice(input[1]);
        RecipeChoice input3 = new RecipeChoice.ExactChoice(input[2]);
        if(isTrimOrTransform){
            recipe = new SmithingTrimRecipe(key,input1,input2,input3);
        }else{
            recipe = new SmithingTransformRecipe(key,output,input1,input2,input3);
        }
        Bukkit.addRecipe(recipe);
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
