package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimulateTestGenerator extends AbstractMachine {
    protected final int MACHINE_SLOT=0;
    protected final int INFO_SLOT=1;
    protected final int[] INPUT_SLOTS=new int[0];
    protected final int[] OUTPUT_SLOTS=new int[]{
            2,3,4,5,6,7,8
    };
    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    public final int ENERGY_ABSMAX;
    public final int OUTPUT_MIN;
    public final int OUTPUT_MAX;
    public final ItemStack OUTPUT= AddItem.PARADOX;
    public final int RANDOM_THREASHOLD;
    public void addInfo(ItemStack stack){
        stack.setItemMeta(AddUtils.addLore(stack,
                new StringBuilder("&8⇨ &e⚡ &7").
                        append(AddUtils.formatDouble(-ENERGY_ABSMAX)).
                        append(" ~ ").
                        append(AddUtils.formatDouble(ENERGY_ABSMAX)).append(" J/t").toString()).getItemMeta());

    }
    public final ItemCounter MACHINE_COUNTER= CraftUtils.getCounter(SlimefunItems.ENERGY_REGULATOR);
    public SimulateTestGenerator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                              int energybuffer, int energyAbsMax,
                                 int outputMin, int outputMax){
        super(category, item, recipeType, recipe, energybuffer, 0);
        this.OUTPUT_MIN=outputMin;
        this.OUTPUT_MAX=outputMax;
        this.ENERGY_ABSMAX=energyAbsMax;
        this.setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow("&f生成机制","&7该机器会模拟虚空量子发电机的行为","&7在左侧机器槽插入能源调节器"
                                ,"&7机器每次运行会模拟%d*log_2(<调节器>)次运行".formatted(MULTIPLE_TIME),"&7当机器中电力位于%dJ~%dJ时".formatted(outputMin,outputMax)
                                ,"&7清空电力并生成"),OUTPUT,this.MACHINE_COUNTER.getItem(),null
                )
        );
        this.RANDOM_THREASHOLD= 2*this.ENERGY_ABSMAX+1;
    }
    public void constructMenu(BlockMenuPreset preset){
        preset.addItem(INFO_SLOT,ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
    }
    public void newMenuInstance(BlockMenu menu, Block block){
    }
    public void updateMenu(BlockMenu menu, Block block, Settings mod){}

    public ItemStack[] OUTPUTS=new ItemStack[]{OUTPUT};
    protected final int MULTIPLE_TIME=4;
    public void tick(Block b, BlockMenu inv, SlimefunBlockData data,int tick){
        ItemStack stack=inv.getItemInSlot(MACHINE_SLOT);
        if(CraftUtils.matchItemStack(stack,MACHINE_COUNTER,false)){
            int amount=stack.getAmount();
            int output=0;
            int charge=this.getCharge(inv.getLocation(),data);
            while(amount>0){
                amount=amount>>1;
                for(int i=0;i<MULTIPLE_TIME;++i){
                    charge+= ThreadLocalRandom.current().nextInt(RANDOM_THREASHOLD)-ENERGY_ABSMAX;
                    if(charge<0){
                        charge=0;
                    }else if(charge>this.energybuffer){
                        charge=this.energybuffer;
                    }else if(charge>=this.OUTPUT_MIN&&charge<=this.OUTPUT_MAX){
                        charge=0;
                        output+=1;
                    }
                }
            }
            if(output!=0){
                CraftUtils.multiPushItems(OUTPUTS,inv,getOutputSlots(),output);
            }
            this.setCharge(inv.getLocation(),charge);
            if(inv.hasViewer()){
                inv.replaceExistingItem(INFO_SLOT,AddUtils.getGeneratorDisplay(true,"虚空量子",charge,this.energybuffer));
            }
        }else {
            if(inv.hasViewer()){
                inv.replaceExistingItem(INFO_SLOT,ChestMenuUtils.getBackground());
            }
        }
    }
    public void process(Block b,BlockMenu inv,SlimefunBlockData data){}
    public void onBreak(BlockBreakEvent event,BlockMenu nb){
        super.onBreak(event,nb);
        if(nb!=null){
            nb.dropItems(nb.getLocation(),MACHINE_SLOT);
        }
    }
}
