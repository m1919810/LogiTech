package me.matl114.logitech.core.Machines.WorkBenchs;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.core.AddItem;
import me.matl114.logitech.core.Machines.Abstracts.AbstractWorkBench;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.ImportRecipes;
import me.matl114.matlib.Implements.Slimefun.core.CustomRecipeType;
import me.matl114.matlib.Utils.Inventory.ItemStacks.CleanItemStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BugCrafter extends AbstractWorkBench implements ImportRecipes {

    public static final CustomRecipeType TYPE=new CustomRecipeType(
            AddUtils.getNameKey("bug_crafter"),
            new CustomItemStack(AddItem.BUG_CRAFTER, AddItem.BUG_CRAFTER.getDisplayName(),
                    "", "&c配方显示不完整，请从%s查看正确的配方!".formatted(Language.get("Machines.BUG_CRAFTER.Name")))
    );
    protected static final int[] INPUT_SLOT=new int[]{0, 1, 2, 3, 4, 5, 9, 10, 11, 12, 13, 14,
            18, 19, 20, 21, 22, 23, 27, 28, 29, 30, 31, 32, 36, 37, 38, 39, 40, 41, 45, 46, 47, 48, 49, 50};
    protected static final  int[] OUTPUT_SLOT=new int[]{ 34,35,43,44,52,53};
    protected static final int[] BORDER_IN=new int[] {6,8,15,17,24,26};
    protected static final int[] BORDER_OUT=new int[] {33,42,51};
    protected static final  int CRAFT_SLOT=16;
    protected static final int LASTRECIPE_SLOT =25;
    protected final ItemStack LASTRECIPE_ITEM = new CleanItemStack(Material.KNOWLEDGE_BOOK,"&6点击放置上一次配方","&7左键放入一份配方","&7右键放入64份配方","&a欢呼吧 懒狗们");
    protected static final int MENU_SLOT=7;
    protected final ItemStack CRAFT_ITEM=new CustomItemStack(Material.COMMAND_BLOCK,"&e点我进行合成","&7一次性最多合成%s个".formatted(CRAFT_LIMIT));
    public BugCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         int energybuffer, int energyConsumption,int limit) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption,limit,null);
        this.machineRecipes=new ArrayList<>();
        TYPE.relatedTo((in,out)->{
            this.machineRecipes.add(MachineRecipeUtils.shapeFrom(-1,in,new ItemStack[]{out}));
        },(in,out)->{
            this.machineRecipes.removeIf(i-> CraftUtils.matchItemStack(i.getOutput()[0],out,true ));
        });
//        this.machineRecipeSupplier=()->{
//            List<MachineRecipe> recipes=new ArrayList<>();
//            recipes.addAll( RecipeSupporter.PROVIDED_SHAPED_RECIPES.get(TYPE));
//            return recipes;
//        };
      //  SchedulePostRegister.addPostRegisterTask(this::getMachineRecipes);
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border = BORDER_IN;
        int len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        //输出槽边框
        border = BORDER_OUT;
        len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(CRAFT_SLOT,CRAFT_ITEM);
        preset.addItem(getRecipeMenuSlot(),AbstractWorkBench.RECIPEBOOK_SHOW_ITEM);
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        menu.addMenuClickHandler(CRAFT_SLOT,
                (player, i, itemStack, clickAction)->{

                    craft(block,menu,player);
                    return false;
                }
        );
        menu.addMenuClickHandler(getRecipeMenuSlot(),((player, i, itemStack, clickAction) -> {
            getRecipeMenu(block,menu).build().setBackHandler(((player1, i1, itemStack1, clickAction1) -> {menu.open(player);return false;})).open(player);
            return false;
        }));
        updateMenu(menu,block, Settings.INIT);
    }

    public void updateMenu(BlockMenu blockMenu, Block block, Settings mod){
        Location loc = block.getLocation();
        int index = DataCache.getLastRecipe(loc);
        List<MachineRecipe> recipeList = getMachineRecipes();
        if(index==-1 || index >= recipeList.size()){
            blockMenu.replaceExistingItem(LASTRECIPE_SLOT,AddUtils.addLore(LASTRECIPE_ITEM,"&7合成历史记录: &c无"));
            blockMenu.addMenuClickHandler(LASTRECIPE_SLOT,ChestMenuUtils.getEmptyClickHandler());
        }else {
            MachineRecipe recipe = recipeList.get(index);
            ItemStack icon = recipe.getOutput()[0];
            blockMenu.replaceExistingItem(LASTRECIPE_SLOT,AddUtils.addLore(LASTRECIPE_ITEM,"&7合成历史记录: &r"+CraftUtils.getItemName(icon)));
            blockMenu.addMenuClickHandler(LASTRECIPE_SLOT,((player, i, itemStack, clickAction) -> {
                moveRecipe(player,blockMenu,recipe,clickAction.isRightClicked());
                return false;
            }));
        }
    }

    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public int[] getRecipeSlots(){
        return INPUT_SLOT;
    }
    public int getRecipeMenuSlot(){
        return MENU_SLOT;
    }
    public boolean isConflict(){
        return false;
    }
}
