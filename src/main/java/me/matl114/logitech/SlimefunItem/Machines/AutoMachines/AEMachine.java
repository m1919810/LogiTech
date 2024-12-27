package me.matl114.logitech.SlimefunItem.Machines.AutoMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Machines.AbstractAdvancedProcessor;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.RecipeClass.StackMachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class AEMachine extends AbstractAdvancedProcessor {
    protected final int[] BORDER={
            3,4,5,12,14,21,22,23,30,31,32,39,41,48,49,50
    };
    protected final ItemStack INFO_ITEM=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a并行机器信息",
            "&7在上方槽位插入工件以增加并行数","&7最大并行数: 64");
    public AEMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                     Material processbar, int energyConsumption, int energyBuffer,
                     List<Pair<Object,Integer>> customRecipes) {
        super(category,item,recipeType,recipe,processbar,energyConsumption,energyBuffer,customRecipes);
        this.machineRecipeSupplier=null;
    }
    public AEMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                     Material processbar, int energyConsumption, int energyBuffer,Supplier<List<MachineRecipe>> machineRecipeSupplier){
        super(category,item,recipeType,recipe,processbar,energyConsumption,energyBuffer,null);
        this.machineRecipeSupplier=machineRecipeSupplier;
        SchedulePostRegister.addPostRegisterTask(()->{
            getMachineRecipes();
        });
    }
    public AEMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                    Material processbar, int energyConsumption, int energyBuffer,
                   int time, RecipeType... recipeTypes) {
        this(category,item,recipeType,recipe,processbar,energyConsumption,energyBuffer,()->{
            if(recipeTypes==null||recipeTypes.length==0){
                return new ArrayList<MachineRecipe>();
            }else {
                List<MachineRecipe>   mr=new ArrayList<>();
                for(RecipeType rt : recipeTypes){
                    if(rt!=null){
                        List<MachineRecipe> rep= RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(rt);
                        if(rep==null)rep=new ArrayList<>();
                        int len=rep.size();
                        MachineRecipe o1;
                        for (int i=0;i<len;++i){
                            o1=MachineRecipeUtils.stackFromMachine(rep.get(i));
                            o1.setTicks(time);
                            mr.add(o1);
                        }
                    }
                }
                return mr;
            }
        });
    }
    public String MAXCRAFT_KEY="p";

    public void newMenuInstance(BlockMenu inv,Block b){
        inv.addMenuOpeningHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });
        inv.addMenuCloseHandler(player -> {
            updateMenu(inv,b,Settings.RUN);
        });

    }
    public void updateMenu(BlockMenu menu, Block b , Settings mode){
        ItemStack it=menu.getItemInSlot(CORE_SLOT);
        int num=1;
        if(it!=null&&CraftUtils.matchItemStack(it,CORE_SAMPLE,false)){
            num= it.getAmount();
        }
        DataCache.setCustomData(menu.getLocation(),MAXCRAFT_KEY,num);
        menu.replaceExistingItem(INFO_SLOT,AddUtils.addLore(  this.INFO_ITEM,AddUtils.concat("&7当前并行数: ",String.valueOf(num))));
    }
    public boolean conditionHandle( Block b,BlockMenu menu){
        if(menu.hasViewer()){
            updateMenu(menu,b,Settings.RUN);
        }
        return super.conditionHandle(b,menu);
    }
    public void constructMenu(BlockMenuPreset preset) {
        //空白背景 禁止点击
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        //输入槽边框
        border = BORDER_SLOT;
        len = border.length;
        for(int var4 = 0; var4 <len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addMenuClickHandler(INFO_SLOT,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL, ChestMenuUtils.getEmptyClickHandler());
    }
    protected final ItemCounter CORE_SAMPLE= CraftUtils.getConsumer(AddItem.CHIP_CORE);
    protected final int CORE_SLOT=13;
    public int getCraftLimit(Block b, BlockMenu inv){
        return DataCache.getCustomData(inv.getLocation(),MAXCRAFT_KEY,1);
    }
    public void addInfo(ItemStack stack){
        super.addInfo(stack);
        stack.setItemMeta(AddUtils.addLore(stack,
                "&7插入[%s]增加并行处理数".formatted(Language.get("Items.CHIP_CORE.Name"))).getItemMeta());
    }
    public ItemStack getProgressBar() {
        return progressbar;
    }
    public void onBreak(BlockBreakEvent e,BlockMenu inv){
        super.onBreak(e,inv);
        if(inv!=null){
            inv.dropItems(inv.getLocation(),CORE_SLOT);
        }
    }
}
