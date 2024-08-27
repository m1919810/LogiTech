package me.matl114.logitech.SlimefunItem.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Machines.AbstractEnergyProvider;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.ItemClass.DisplayItemStack;
import me.matl114.logitech.Utils.Utils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BiReactor extends AbstractEnergyProvider {
    protected final int[] BORDER=new int[]{
            3,4,5,12,14,21,22,23
    };
    protected final int[] INPUT_BORDER=new int[]{
            0,2,9,11,18,19,20
    };
    protected final int[] OUTPUT_BORDER=new int[]{
            6,8,15,17,24,25,26
    };
    protected final int[] INPUT_SLOTS=new int[]{
            10,16
    };
    protected final int[] TRUE_SLOTS=new int[]{
            16
    };
    protected final int[] FALSE_SLOTS=new int[]{
            10
    };
    protected final int[] INFO_SLOT=new int[]{
            1,7
    };
    protected final int STATUS_SLOT=13;
    protected final ItemStack[] STATUS_ITEM=new ItemStack[2];


    protected final ItemStack[] INFO_ITEMS=new ItemStack[]{
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6[%s]输入槽".formatted(Language.get("Items.FALSE_.Name"))),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6[%s]输入槽".formatted(Language.get("Items.TRUE_.Name")))
    };
    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
        if(flow==ItemTransportFlow.WITHDRAW)return getOutputSlots();
        if(item!=null&& item.getType()==Material.MUSIC_DISC_5){
            return item.getEnchantments().isEmpty()?FALSE_SLOTS:TRUE_SLOTS;
        }
        return getInputSlots();
    }


    protected final int[] OUTPUT_SLOTS=new int[0];
    protected int energyConsumptionFalse;
    public BiReactor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                     int energyBuffer, int energyProvider,int falseEnergy) {
        super(category, item, recipeType, recipe,   energyBuffer,energyProvider);
        this.energyConsumptionFalse=falseEnergy;
        STATUS_ITEM[0]=AddUtils.addGlow( new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e状态: &a正","&7发电量: %dJ/t".formatted(this.energyConsumption)));
        STATUS_ITEM[1]=AddUtils.addGlow( new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&e状态: &c负","&7发电量: %dJ/t".formatted(-this.energyConsumptionFalse)));
        this.setDisplayRecipes(Utils.list(AddUtils.getInfoShow("&f机制 &a正","&7当同时输入%s和%s时,发电 %dJ".
                formatted(Language.get("Items.FALSE_.Name"),Language.get("Items.TRUE_.Name"),this.energyConsumption)),
                new DisplayItemStack(AddItem.TRUE_),
                AddUtils.getInfoShow("&f机制 &c负","&7当不满足输入条件时候,发电 -%dJ".
                        formatted(this.energyConsumptionFalse)),
                new DisplayItemStack(AddItem.FALSE_)));
    }
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<>();
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=INPUT_BORDER;
        len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getInputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=OUTPUT_BORDER;
        len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getOutputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=INFO_SLOT;
        len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i],INFO_ITEMS[i],ChestMenuUtils.getEmptyClickHandler());
        }
       preset.addItem(STATUS_SLOT,STATUS_ITEM[0],ChestMenuUtils.getEmptyClickHandler());


    }
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull SlimefunBlockData data){
        BlockMenu inv=data.getBlockMenu();
        if(inv!=null){

            ItemStack it= inv.getItemInSlot(TRUE_SLOTS[0]);
            if(it!=null&&it.getType()==Material.MUSIC_DISC_5&&(!it.getEnchantments().isEmpty())){
                ItemStack it2;
                it2=inv.getItemInSlot(FALSE_SLOTS[0]);
                if(it2!=null&&it2.getType()==Material.MUSIC_DISC_5&&(it2.getEnchantments().isEmpty())){
                    it.setAmount(0);
                    it2.setAmount(0);
                    if(inv.hasViewer()){
                        inv.replaceExistingItem(STATUS_SLOT,STATUS_ITEM[0]);
                    }
                    return this.energyConsumption;
                }
            }
            if(inv.hasViewer()){
                inv.replaceExistingItem(STATUS_SLOT,STATUS_ITEM[1]);
            }
            return -this.energyConsumptionFalse;
        }else return 0;
    }
}
