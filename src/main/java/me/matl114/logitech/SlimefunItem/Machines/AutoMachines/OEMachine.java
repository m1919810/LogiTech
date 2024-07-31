package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;


import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;

import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.matl114.logitech.SlimefunItem.Machines.RecipeDisplay;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.MachineRecipeUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class OEMachine extends AContainer implements RecipeDisplay {

    private final String id;
    private final ItemStack progressItem;
    private final int energyConsumption;
    private final int energyBuffer;
    public OEMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
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
        Map<Integer, ItemStack> inventory = new HashMap();
        int[] var3 = this.getInputSlots();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int slot = var3[var5];
            ItemStack item = inv.getItemInSlot(slot);
            if (item != null) {
                inventory.put(slot, ItemStackWrapper.wrap(item));
            }
        }

        Map<Integer, Integer> found = new HashMap();
        Iterator var15 = this.recipes.iterator();

        while(var15.hasNext()) {
            MachineRecipe recipe = (MachineRecipe)var15.next();
            ItemStack[] var17 = recipe.getInput();
            int var19 = var17.length;

            for(int var8 = 0; var8 < var19; ++var8) {
                ItemStack input = var17[var8];
                int[] var10 = this.getInputSlots();
                int var11 = var10.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    int slot = var10[var12];
                    if (SlimefunUtils.isItemSimilar((ItemStack)inventory.get(slot), input, true)) {
                        found.put(slot, input.getAmount());
                        break;
                    }
                }
            }
            if (found.size() == recipe.getInput().length) {
                if (!InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), this.getOutputSlots())) {
                    return null;
                }

                Iterator var18 = found.entrySet().iterator();

                while(var18.hasNext()) {
                    Map.Entry<Integer, Integer> entry = (Map.Entry)var18.next();
                    inv.consumeItem((Integer)entry.getKey(), (Integer)entry.getValue());
                }

                return recipe;
            }
            long c=System.nanoTime();

            found.clear();
        }

        return null;
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
