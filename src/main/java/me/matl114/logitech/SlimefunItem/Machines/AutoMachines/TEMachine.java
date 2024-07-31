package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;

import me.matl114.logitech.SlimefunItem.Machines.RecipeDisplay;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.MachineRecipeUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class TEMachine extends AContainer implements RecipeDisplay {

    private final String id;
    private final ItemStack progressItem;
    private final int energyConsumption;
    private final int energyBuffer;




    public TEMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                    Material progressItem, int energyConsumption, int energyBuffer,
                    LinkedHashMap<Object[], Integer> customRecipes) {

        super(category,item , recipeType, recipe);
        ItemStack item2=AddUtils.machineInfoAdd(item,energyBuffer,energyConsumption);
        item.setItemMeta(item2.getItemMeta());


        this.id = item.getItemId();
        this.progressItem = new ItemStack(progressItem);
        this.energyConsumption = energyConsumption;
        this.energyBuffer = energyBuffer;

        //registerDefaultRecipes
        customRecipes.forEach(
                (objects, time) ->
                {
                    Pair<ItemStack[],ItemStack[]> a=AddUtils.buildRecipes(new Pair<>(
                            Arrays.copyOfRange(objects,0,2),Arrays.copyOfRange(objects,2,4)));
                    registerRecipe(time, MachineRecipeUtils.in(a.getFirstValue()),MachineRecipeUtils.in(a.getSecondValue()));
                }
        );
        getMachineProcessor().setProgressBar(getProgressBar());


        // Gets called in AContainer, but customRecipes is null at that time.
        // registerDefaultRecipes();
    }
    public List<MachineRecipe> provideDisplayRecipe(){
        return recipes;
    }
    @Override
    public ItemStack getProgressBar() {
        return progressItem;
    }

    @Override
    public int getCapacity() {
        return energyBuffer;
    }

    @Override
    public int getEnergyConsumption() {
        return energyConsumption;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    protected void registerDefaultRecipes() {


    }
    protected MachineRecipe findNextRecipe(BlockMenu inv) {

        var a= CraftUtils.findNextRecipe(inv,getInputSlots(),getOutputSlots(),this.recipes,true);
        if(a==null)return null;
        else return a.getFirstValue();

    }

    //force use method from implements. override method in AContainer
    /*
    public List<ItemStack> getDisplayRecipes(){
        return ((RecipeDisplay)this).getDisplayRecipes();
    }//terrible error! cause self-call
    /* //we have RecipeDisplay implements
    @Override
    public List<ItemStack> getDisplayRecipes() {

        ArrayList<ItemStack> displayRecipes = new ArrayList<>(){};

        for (int i = 0; i < recipes.size(); i++) {

            MachineRecipe recipe = recipes.get(i);
            int len1=recipe.getInput().length;
            int len2=recipe.getOutput().length;
            int len=Math.max(len1,len2);
            int endp1=len1;
            int endp2=len-len2;
            int t=0;
            int s=0;
            for(int j=0;j<len;++j){

                if(j<endp1){
                    displayRecipes.add(recipe.getInput()[t]);++t;

                }

                else displayRecipes.add(null);

                if(j>=endp2){
                    displayRecipes.add(recipe.getOutput()[s]);++s;
                }else displayRecipes.add(null);
            }
        }

        return displayRecipes;
    }
    */
    @Override
    public String getMachineIdentifier() {
        return id;
    }
    public  List<ItemStack> getDisplayRecipes() {

        return _getDisplayRecipes();
    }
}
