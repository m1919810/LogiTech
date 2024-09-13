package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Blocks.Laser;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlock.FinalAltarCore;
import me.matl114.logitech.SlimefunItem.Items.EntityFeat;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.SlimefunItem.Machines.FinalFeature;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MachineRecipeUtils;
import me.matl114.logitech.Utils.ReflectUtils;
import me.matl114.logitech.Utils.UtilClass.ItemClass.RandOutItem;
import me.matl114.logitech.Utils.UtilClass.ItemClass.RandomItemStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;

public class RandomEditor extends AbstractMachine implements FinalAltarCore.FinalAltarChargable , Laser.LaserChargable {
    protected final int[] BORDER=new int[0];
    protected final int[] INPUT_BORDER=new int[]{
            0,1,2,3,5,6,7,8
    };
    protected final int[] OUTPUT_BORDER=new int[]{
            9,10,11,12,14,15,16,17
    };
    protected final int INFO_SLOT=13;
    protected final ItemStack INFO_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6机制说明","&7将任意物品放入下方槽位",
            "&7机器将修改物品的随机一项附魔,属性","&7等物品信息的数值,并将其+1","&c每个格子只能放入一个物品!");
    protected final int[] ITEM_SLOT=new int[]{

            18,19,20,21,22,23,24,25,26,
            27,28,29,30,31,32,33,34,35,
            36,37,38,39,40,41,42,43,44,
            45,46,47,48,49,50,51,52,53
    };
    protected final int[] OUTPUT_SLOT=new int[0];
    public int[] getInputSlots(){
        return OUTPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    protected final int STATUS_SLOT=4;
    protected final ItemStack STATUS_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6机器信息","&7状态: &a已激活");
    protected final ItemStack STATUS_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6机器信息","&7状态: &c未激活");

    public RandomEditor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          int energybuffer, int energyConsumption){
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=INPUT_BORDER;
        len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i], ChestMenuUtils.getInputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=OUTPUT_BORDER;
        len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i], ChestMenuUtils.getOutputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(STATUS_SLOT,STATUS_OFF,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INFO_SLOT,INFO_ITEM,ChestMenuUtils.getEmptyClickHandler());
    }
    public List<MachineRecipe> getMachineRecipes(){
        List<MachineRecipe> recipes=new ArrayList<>();
        return recipes;
    }
    public void tick(Block b, @Nullable BlockMenu menu, SlimefunBlockData data, int tickCount){
        if(menu==null)return;
        if(conditionHandle(b,menu)&& FinalFeature.isFinalAltarCharged(this,data)){
            if(menu.hasViewer()){
                menu.replaceExistingItem(STATUS_SLOT,STATUS_ON);
            }
            process(b,menu,data);
        }else {
            if(menu.hasViewer()){
                menu.replaceExistingItem(STATUS_SLOT,STATUS_OFF );
            }
        }

    }
    private Random rand=new Random();
    protected static Enchantment[] registeredEnchantments=Enchantment.values();
    protected static Attribute[] registeredAttributes=Attribute.values();
    protected static EquipmentSlot[] equipmentSlots=EquipmentSlot.values();
    protected static int totalAmount=registeredEnchantments.length+equipmentSlots.length*registeredAttributes.length;
    protected static String PREFIX="re";
    protected static Field amountField=null;
    protected static boolean getAmountField=false;
    static{
        try{
            amountField= ReflectUtils.getDeclaredFieldsRecursively(AttributeModifier.class,"amount").getFirstValue();
            amountField.setAccessible(true);
            getAmountField=true;
            Debug.debug("INVOKE ATTRIBUTEMODIFIER TRUE");
        }catch (Throwable e){
            Debug.logger(e);
        }
    }
    public void randomEditor(ItemMeta meta,Material material){
        int index=rand.nextInt(totalAmount);
        if(index<registeredEnchantments.length){
            Enchantment e=registeredEnchantments[index];
            int level= meta.getEnchantLevel(e);
            meta.addEnchant(e,level+1,true);
        }else {
            index=index-registeredEnchantments.length;
            Attribute att=registeredAttributes[index/equipmentSlots.length];
            EquipmentSlot slot=equipmentSlots [index%equipmentSlots.length];
            Collection< AttributeModifier> modifiers=meta.getAttributeModifiers(att);
            boolean hasFind=false;
            if(modifiers!=null){
                for(AttributeModifier mod:modifiers){
                    if(mod.getSlot()==slot&&mod.getName().startsWith(PREFIX)){
                        hasFind=true;
                        if(getAmountField){
                            try{
                                amountField.set(mod,(double)(mod.getAmount()+1));
                                break;
                            }catch (Throwable e){
                                getAmountField=false;
                            }
                        }
                        meta.removeAttributeModifier(att,mod);
                        meta.addAttributeModifier(att,new AttributeModifier(UUID.randomUUID(),AddUtils.concat(PREFIX,att.getKey().getKey()),mod.getAmount()+1, AttributeModifier.Operation.ADD_NUMBER,slot));
                        break;
                    }
                }
            }
            if(!hasFind){
                meta.addAttributeModifier(att,new AttributeModifier(UUID.randomUUID(),AddUtils.concat(PREFIX,att.getKey().getKey()),1.0d, AttributeModifier.Operation.ADD_NUMBER,slot));
            }
        }
    }
    public void process(Block b, BlockMenu inv, SlimefunBlockData data) {
        int len=ITEM_SLOT.length;
        Schedules.launchSchedules(()->{
            for (int i=0;i<len;++i){
                ItemStack it=inv.getItemInSlot(ITEM_SLOT[i]);
                if(it==null||it.getAmount()!=1){
                    continue;
                }else {
                    ItemMeta meta=it.getItemMeta();
                    randomEditor(meta,it.getType());
                    it.setItemMeta(meta);
                }
            }
        },0,false,0);
    }
}
