package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Blocks.Laser;
import me.matl114.logitech.SlimefunItem.Blocks.MultiBlock.FinalAltarCore;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.SlimefunItem.Machines.FinalFeature;
import me.matl114.logitech.Utils.*;
import me.matl114.matlib.Utils.Reflect.FieldAccess;
import me.matl114.matlib.core.EnvironmentManager;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
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
    protected final int MAX_UPGRADE_ONE_TIME=3;
    public RandomEditor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          int energybuffer, int energyConsumption){
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
        SchedulePostRegister.addPostRegisterTask(()->{
            ItemStack resultDisplay=new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta meta=resultDisplay.getItemMeta();
            for(Enchantment enchantment:getRegisteredEnchantments()){
                meta.addEnchant(enchantment,255,true);
            }
            for(Attribute attribute:registeredAttributes){
                for (var slot :new EquipmentSlot[]{EquipmentSlot.HAND,EquipmentSlot.OFF_HAND})
                meta.addAttributeModifier(attribute,EnvironmentManager.getManager().getVersioned().createAttributeModifier(UUID.randomUUID(),AddUtils.concat(PREFIX,"_",attribute.getKey().getKey(),"_",slot.name().toLowerCase(Locale.ROOT)),1024, AttributeModifier.Operation.ADD_NUMBER,slot));
            }
            meta.setDisplayName(AddUtils.resolveColor(AddUtils.color("展示物品")));
            resultDisplay.setItemMeta(meta);
            this.setDisplayRecipes(
                    Utils.list(
                            AddUtils.getInfoShow("&f机制 - &c充能",
                                    "&7当置于贰级终极祭坛上时",
                                    "&7且机器被终极祭坛结构中的所有宏激光发射器充能时",
                                    "&7即终极祭坛中四个宏激光发射器分别位于四个壹级以上终极祭坛上时",
                                    "&7机器激活,进行运转"
                            ),null,
                            AddUtils.getInfoShow("&f机制 - &c量化升级",
                                    "&7每次运行机器会对所有槽位的物品进行升级",
                                    "&7每次随机选择一种附魔/属性增幅",
                                    "&7并将该物品的该项数值提示随机1~%d".formatted(MAX_UPGRADE_ONE_TIME),
                                    "&7下面是所有可能的升级展示",
                                    "&7注:附魔<=%d,属性基值<=%.1f".formatted(MAX_ENCHANT,MAX_ATTRIBUTE)),resultDisplay,

                            AddUtils.getInfoShow("&f注意 - &c原版机制",
                                    "&7高版本专属:1.20.5+",
                                    "&7属性有风险,强化需谨慎,",
                                    "&7操作不规范,装备全白搭",
                                    "&c由于高版本属性的等级限制,可能会出现等级过高造成属性重置",
                                    "&c希望大家谨慎使用"
                                    ),null
                    )
            );}
        );

    }
    public void constructMenu(BlockMenuPreset preset){
        preset.setSize(54);
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
            preset.addItem(border[i], INFO_ITEM,ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(STATUS_SLOT,STATUS_OFF,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INFO_SLOT,INFO_ITEM,ChestMenuUtils.getEmptyClickHandler());
    }
    public void tick(Block b, @Nullable BlockMenu menu, SlimefunBlockData data, int tickCount){
        if(menu==null)return;
        process(b,menu,data);
        if(conditionHandle(b,menu)&& FinalFeature.isFinalAltarCharged(this,data)){
            if(menu.hasViewer()){
                menu.replaceExistingItem(STATUS_SLOT,STATUS_ON);
            }

        }else {
            if(menu.hasViewer()){
                menu.replaceExistingItem(STATUS_SLOT,STATUS_OFF );
            }
        }

    }
    private Random rand=new Random();
    protected static Enchantment[] registeredEnchantments;
    protected static Attribute[] registeredAttributes=Attribute.values();
    protected static EquipmentSlot[] equipmentSlots=EquipmentSlot.values();
    protected static int totalAmount;
    protected static String PREFIX="re";
    protected static Field amountField=null;
    protected static boolean getAmountField=false;
    protected final static HashSet<Material> HELMET_MATERIALS=new HashSet<>();
    protected final static HashSet<Material> CHESTPLATE_MATERIALS=new HashSet<>();
    protected final static HashSet<Material> LEGGINGS_MATERIALS=new HashSet<>();
    protected final static HashSet<Material> BOOTS_MATERIALS=new HashSet<>();
    public Enchantment[] getRegisteredEnchantments(){
        if(registeredEnchantments==null||registeredEnchantments.length==0){
            registeredEnchantments=Enchantment.values();
            totalAmount=registeredEnchantments.length+registeredAttributes.length;
        }
        return registeredEnchantments;
    }
    static{
        try{
            amountField= ReflectUtils.getDeclaredFieldsRecursively(AttributeModifier.class,"amount").getFirstValue();
            amountField.setAccessible(true);
            getAmountField=true;
            Debug.debug("INVOKE ATTRIBUTEMODIFIER TRUE");
        }catch (Throwable e){
            Debug.logger(e);
        }
        for(Material material:Material.values()){
            if(material.isItem()){
                if(material.toString().endsWith("HELMET")){
                    Debug.debug("helmet ",material.toString());
                    HELMET_MATERIALS.add(material);
                }
                if(material.toString().endsWith("CHESTPLATE")){
                    Debug.debug("chestplate ",material.toString());
                    CHESTPLATE_MATERIALS.add(material);

                }
                if(material.toString().endsWith("LEGGINGS")){
                    Debug.debug("leggings ",material.toString());
                    LEGGINGS_MATERIALS.add(material);
                }
                if(material.toString().endsWith("BOOTS")){
                    Debug.debug("boots ",material.toString());
                    BOOTS_MATERIALS.add(material);
                }
            }
        }
        HELMET_MATERIALS.add(Material.TURTLE_HELMET);
        CHESTPLATE_MATERIALS.add(Material.ELYTRA);
    }
    public EquipmentSlot getRandAttributeModifierSlots(Material material,int randIndex){
        if(material.getMaxStackSize()!=1){
            return (randIndex%2==0)?EquipmentSlot.HAND:EquipmentSlot.OFF_HAND;
        }else{
            if(HELMET_MATERIALS.contains(material)){
                return EquipmentSlot.HEAD;
            }
            if(CHESTPLATE_MATERIALS.contains(material)){
                return EquipmentSlot.CHEST;
            }
            if(LEGGINGS_MATERIALS.contains(material)){
                return EquipmentSlot.LEGS;
            }
            if(BOOTS_MATERIALS.contains(material)){
                return EquipmentSlot.FEET;
            }
            return (randIndex%2==0)?EquipmentSlot.HAND:EquipmentSlot.OFF_HAND;
        }
    }
    FieldAccess amountFieldA=FieldAccess.ofName(AttributeModifier.class,"amount");
    private final int MAX_ENCHANT=255;
    private final double MAX_ATTRIBUTE=1024;
    public void randomEdit(ItemMeta meta, Material material){
        int index=rand.nextInt(totalAmount);
        int upgrade=rand.nextInt(MAX_UPGRADE_ONE_TIME)+1;
        if(index<registeredEnchantments.length){
            Enchantment e=registeredEnchantments[index];
            int level= meta.getEnchantLevel(e);
            if(level+upgrade<=MAX_ENCHANT){
                meta.addEnchant(e,level+upgrade,true);
            }
        }else {
            index=index-registeredEnchantments.length;
            Attribute att=registeredAttributes[index];
            EquipmentSlot slot=getRandAttributeModifierSlots(material,rand.nextInt(10));
            Collection< AttributeModifier> modifiers=meta.getAttributeModifiers(att);
            boolean hasFind=false;
            if(modifiers!=null){
                for(AttributeModifier mod:modifiers){
                    if(EnvironmentManager.getManager().getVersioned().getAttributeModifierSlot(mod)==slot&&EnvironmentManager.getManager().getVersioned().getAttributeModifierName( mod).startsWith(PREFIX)){
                        hasFind=true;
                        double amount=mod.getAmount();
                        if(amount>=MAX_ATTRIBUTE){
                            break;
                        }
                        if(EnvironmentManager.getManager().getVersioned().setAttributeModifierValue(mod,Math.min(mod.getAmount()+upgrade,MAX_ATTRIBUTE))){
                            break;
                        }
                        //mod.
                        meta.removeAttributeModifier(att,mod);
                        meta.addAttributeModifier(att,EnvironmentManager.getManager().getVersioned().createAttributeModifier(EnvironmentManager.getManager().getVersioned().getAttributeModifierUid(mod),AddUtils.concat(PREFIX,"_",att.getKey().getKey(),"_",slot.name().toLowerCase(Locale.ROOT)),mod.getAmount()+upgrade, AttributeModifier.Operation.ADD_NUMBER,slot));
                        break;
                    }
                }
            }
            if(!hasFind){
                meta.addAttributeModifier(att,EnvironmentManager.getManager().getVersioned ().createAttributeModifier(UUID.randomUUID(),AddUtils.concat(PREFIX,"_",att.getKey().getKey(),"_",slot.name().toLowerCase(Locale.ROOT)),(double) upgrade, AttributeModifier.Operation.ADD_NUMBER,slot));
            }
        }
    }

    @Override
    public void onBreak(BlockBreakEvent e, @Nullable BlockMenu menu) {
        super.onBreak(e, menu);
        if(menu!=null){
            Location l = menu.getLocation();
            menu.dropItems(l, ITEM_SLOT);
        }
    }

    public void process(Block b, BlockMenu inv, SlimefunBlockData data) {
        int len=ITEM_SLOT.length;
        for (int i=0;i<len;++i){
            ItemStack it=inv.getItemInSlot(ITEM_SLOT[i]);
            if(it==null||it.getAmount()!=1){
                continue;
            }else {
                ItemMeta meta=it.getItemMeta();
                randomEdit(meta,it.getType());
                it.setItemMeta(meta);
            }
        }
    }
}
