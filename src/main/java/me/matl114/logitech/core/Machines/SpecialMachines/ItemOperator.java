package me.matl114.logitech.core.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.core.AddItem;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.utils.Settings;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemCounter;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemOperator extends AbstractMachine {
    protected final int[] INPUT_SLOTS=new int[]{
            28
    };
    protected final int[] OUTPUT_SLOTS=new int[]{
            10
    };
    protected final int[] BORDER_OUTPUT=new int[]{
            0,1,2,9,11,18,19,20,27,29,36,37,38
    };
    protected final int[] BORDER_INPUT=new int[]{
            3,4,5,6,7,8,12,17,21,22,23,24,25,26,30,35,39,40,41,42,43,44
    };
    protected final int[] FUNCTION_SLOT=new int[]{
            13,14,15,16,31,32,33,34
    };
    protected final int CONSUMED_DISPLAY_SLOT=37;
    protected final int OPERATED_DISPLAY_SLOT=1;
    protected final ItemStack CONSUMED_DISPLAY_ITEM=new CustomItemStack(Material.FURNACE,"&6消耗物品槽","&7将操作所需求的物品置于该槽位");
    protected final ItemStack OPERATED_DISPLAY_ITEM=new CustomItemStack(Material.ANVIL,"&6操作物品槽","&7将要被操作的物品置于该槽位");
    protected final ItemStack[] FUNCTION_ITEM=new ItemStack[]{
        new CustomItemStack(Material.NAME_TAG,"&6重命名物品","&7点击输入名字以重命名物品","&7需消耗 [命名牌]x1","&a可以使用颜色符号","&e仅可以对粘液物品或者不可堆叠物品操作","&e一次仅可对一个物品操作"),
            new CustomItemStack(Material.LANTERN,"&6显示全部物品属性","&7点击取消所有物品属性隐藏符","&7无需消耗物品"),
            new CustomItemStack(Material.CRYING_OBSIDIAN,"&6赋予无法破坏","&7点击赋予物品无法破坏属性","&7需消耗 [%s] x64".formatted(Language.get("Items.LSINGULARITY.Name")),"&e一次仅可对一个物品操作"),
            new CustomItemStack(Material.GRINDSTONE,"&6重置物品","&7点击重置原版物品","&7无需消耗物品","&7仅限原版物品使用"),
            new CustomItemStack(Material.WRITABLE_BOOK,"&6去除物品名字","&7点击去除物品名字","&7需消耗 [命名牌]x1","&a粘液物品将恢复至原初的名字","&e一次仅可对一个物品操作"),
            new CustomItemStack(Material.SOUL_LANTERN,"&6隐藏全部物品属性","&7点击添加所有物品属性隐藏符","&7无需消耗物品"),
            new CustomItemStack(Material.VERDANT_FROGLIGHT,"&6取消无法破坏","&7点击取消物品无法破坏属性","&7无需消耗物品"),
            new CustomItemStack(Material.LOOM,"&6物品染色","&7点击给可染色物品染色","&7需消耗 [对应染料]x1")
    };
    protected final String RENAME_SF_PREFIX= AddUtils.resolveColor("&7&kS&f&r");
    protected final String RENAME_SF_SUFFIX=AddUtils.resolveColor("&7&kB&f&r");
    protected final String RENAME_VANILLA_PREFIX= AddUtils.resolveColor("&7&kT&f&r");
    protected final String RENAME_VANILLA_SUFFIX=AddUtils.resolveColor("&7&kM&f&r");
    protected final ItemCounter LSIN=CraftUtils.getCounter(AddItem.LSINGULARITY);
    protected final ItemCounter NAMETAG=CraftUtils.getCounter(new ItemStack(Material.NAME_TAG));
    protected final HashSet<String> COLORS=new HashSet<>(){{
        for (Material material : Material.values()) {
            if(material.isItem()&&material.toString().endsWith("_DYE")) {
                add(material.toString().replace("_DYE", ""));
            }
        }
    }};

    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    public ItemOperator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                              int energybuffer, int energyConsumption){
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER_OUTPUT;
        int len=border.length;
        for(int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getOutputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=BORDER_INPUT;
        len=border.length;
        for(int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getInputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(CONSUMED_DISPLAY_SLOT,CONSUMED_DISPLAY_ITEM,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(OPERATED_DISPLAY_SLOT,OPERATED_DISPLAY_ITEM,ChestMenuUtils.getEmptyClickHandler());
        for (int i=0;i<FUNCTION_SLOT.length;i++){
            preset.addItem(FUNCTION_SLOT[i],FUNCTION_ITEM[i],ChestMenuUtils.getEmptyClickHandler());
        }

    }
    private ItemStack getOperated(BlockMenu inv,boolean onlyOne){
        ItemStack stack=inv.getItemInSlot(OUTPUT_SLOTS[0]);
        if(!onlyOne||(stack!=null&&stack.getAmount()==1)){
            return stack;
        }else return null;
    }
    public void newMenuInstance(BlockMenu inv, Block block){
        inv.addMenuClickHandler(FUNCTION_SLOT[0],((player, i, itemStack, clickAction) -> {
            player.closeInventory();
            AddUtils.sendMessage(player,"&e请输入物品名字(支持颜色符号)");
            AddUtils.asyncWaitPlayerInput(player,(string)->{
                inv.open(player);
                ItemStack stack=getOperated(inv,true);
                if(stack==null){
                    AddUtils.sendMessage(player,"&c被操作物品为空或者非单个物品!");
                    return;
                }
                ItemStack consumed=inv.getItemInSlot(INPUT_SLOTS[0]);
                if(consumed!=null&&CraftUtils.matchItemStack(consumed,NAMETAG,true)){
                    ItemMeta meta=stack.getItemMeta();
                    boolean isSlimefunItem = CraftUtils.parseSfItem(meta) != null;
                    if(stack.getMaxStackSize()>1 && !isSlimefunItem){
                        AddUtils.sendMessage(player,"&c仅可对粘液物品或者不可堆叠物品操作!");
                        return;
                    }
                    if( isSlimefunItem ){
                        meta.setDisplayName(AddUtils.resolveColor(AddUtils.concat(RENAME_SF_PREFIX,string,RENAME_SF_SUFFIX)));
                    }else {
                        meta.setDisplayName(AddUtils.resolveColor(AddUtils.concat(RENAME_VANILLA_PREFIX,string,RENAME_VANILLA_SUFFIX)));
                    }
                    stack.setItemMeta(meta);
                    consumed.setAmount(consumed.getAmount()-1);
                    AddUtils.sendMessage(player,"&a操作成功!");
                }else {
                    AddUtils.sendMessage(player,"&c所需物品不足");
                }
            });
            return false;
        }));
        inv.addMenuClickHandler(FUNCTION_SLOT[1],((player, i, itemStack, clickAction) -> {
            ItemStack stack=getOperated(inv,false);
            if(stack==null){
                AddUtils.sendMessage(player,"&c被操作物品为空!");
                return false;
            }
            AddUtils.showAllFlags(stack);
            AddUtils.sendMessage(player,"&a操作成功!");
            return false;
        }));
        inv.addMenuClickHandler(FUNCTION_SLOT[2],((player, i, itemStack, clickAction) -> {
            ItemStack stack=getOperated(inv,true);
            if(stack==null){
                AddUtils.sendMessage(player,"&c被操作物品为空或者非单个物品!");
                return false;
            }
            ItemStack consumed=inv.getItemInSlot(INPUT_SLOTS[0]);
            if(consumed!=null&&consumed.getAmount()>=64&&CraftUtils.matchItemStack(consumed,LSIN,true)){
                ItemMeta meta=stack.getItemMeta();
                meta.setUnbreakable(true);
                stack.setItemMeta(meta);
                consumed.setAmount(consumed.getAmount()-64);
                AddUtils.sendMessage(player,"&a操作成功!");
            }else {
                AddUtils.sendMessage(player,"&c所需物品不足");
            }
            return false;
        }));
        inv.addMenuClickHandler(FUNCTION_SLOT[3],((player, i, itemStack, clickAction) -> {
            ItemStack stack=getOperated(inv,false);
            if(stack==null){
                AddUtils.sendMessage(player,"&c被操作物品为空!");
                return false;
            }
            ItemMeta meta=stack.getItemMeta();
            if(CraftUtils.parseSfItem(meta)==null){
                stack.setItemMeta(Bukkit.getItemFactory().getItemMeta(stack.getType()));
                AddUtils.sendMessage(player,"&a操作成功!");
            }else{
                AddUtils.sendMessage(player,"&c不可对粘液物品操作!");
                return false;
            }
            return false;
        }));
        inv.addMenuClickHandler(FUNCTION_SLOT[4],((player, i, itemStack, clickAction) -> {
            ItemStack stack=getOperated(inv,true);
            if(stack==null){
                AddUtils.sendMessage(player,"&c被操作物品为空或者非单个物品!");
                return false;
            }
            ItemStack consumed=inv.getItemInSlot(INPUT_SLOTS[0]);
            if(consumed!=null&&CraftUtils.matchItemStack(consumed,NAMETAG,true)){
                ItemMeta meta=stack.getItemMeta();
                SlimefunItem item=CraftUtils.parseSfItem(meta);
                if(item==null){
                    meta.setDisplayName(null);
                }else {
                    meta.setDisplayName(item.getItem().getItemMeta().getDisplayName());
                }
                stack.setItemMeta(meta);
                consumed.setAmount(consumed.getAmount()-1);
                AddUtils.sendMessage(player,"&a操作成功!");
            }else {
                AddUtils.sendMessage(player,"&c所需物品不足");
            }
            return false;
        }));
        inv.addMenuClickHandler(FUNCTION_SLOT[5],((player, i, itemStack, clickAction) -> {
            ItemStack stack=getOperated(inv,false);
            if(stack==null){
                AddUtils.sendMessage(player,"&c被操作物品为空!");
                return false;
            }
            AddUtils.hideAllFlags(stack);
            AddUtils.sendMessage(player,"&a操作成功!");
            return false;
        }));
        inv.addMenuClickHandler(FUNCTION_SLOT[6],((player, i, itemStack, clickAction) -> {
            ItemStack stack=getOperated(inv,false);
            if(stack==null){
                AddUtils.sendMessage(player,"&c被操作物品为空!");
                return false;
            }
            ItemMeta meta=stack.getItemMeta();
            meta.setUnbreakable(false);
            stack.setItemMeta(meta);
            AddUtils.sendMessage(player,"&a操作成功!");
            return false;
        }));
        inv.addMenuClickHandler(FUNCTION_SLOT[7],((player, i, itemStack, clickAction) -> {
            ItemStack stack=getOperated(inv,false);
            if(stack==null){
                AddUtils.sendMessage(player,"&c被操作物品为空!");
                return false;
            }
            String dyed=stack.getType().toString();
            boolean isDyable=false;
            String originColor=null;
            for(String color:COLORS){
                if(dyed.startsWith(color)){
                    isDyable=true;
                    originColor=color;
                    break;
                }
            }
            ItemStack consumed=inv.getItemInSlot(INPUT_SLOTS[0]);
            String material=consumed.getType().toString();
            String nowColor=null;
            if(material.endsWith("_DYE")){
                nowColor=material.replace("_DYE","");
            }else {
                AddUtils.sendMessage(player,"&c所需物品槽的物品不是染料");
                return false;
            }
            if(isDyable){

                Material newMaterial=Material.getMaterial(dyed.replace(originColor,nowColor));
                if(newMaterial==null){
                    AddUtils.sendMessage(player,"&c该物品不能被染色");
                    return false;
                }else {
                    stack.setType(newMaterial);
                    consumed.setAmount(consumed.getAmount()-1);
                    AddUtils.sendMessage(player,"&a操作成功!");
                    return false;
                }
            }
            else if(dyed.startsWith("LEATHER_")){
                AddUtils.sendMessage(player,"&c暂时不支持对皮革盔甲的染色");
                return false;
//                ItemMeta meta=stack.getItemMeta();
//                if(meta instanceof LeatherArmorMeta lam){
//                    lam.setColor();
//                }else {
//                    AddUtils.sendMessage(player,"&c该物品不能被染色");
//                    return false;
//                }
            }
            else {
                AddUtils.sendMessage(player,"&c该物品不能被染色");
                return false;
            }
        }));
    }
    public void updateMenu(BlockMenu menu, Block block, Settings mod){}


    @Override
    public void tick(Block b, BlockMenu menu, SlimefunBlockData data, int ticker) {

    }

    public void process(Block b, BlockMenu menu, SlimefunBlockData data){}
}
