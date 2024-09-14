package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.google.common.collect.Multimap;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.MenuClass.DataMenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import net.guizhanss.guizhanlib.minecraft.helper.attribute.AttributeHelper;
import net.guizhanss.guizhanlib.minecraft.helper.enchantments.EnchantmentHelper;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;

public class AttributeOperator extends AbstractMachine {
    protected final int[] BORDER=new int[]{0,1,2,3,4,5,6,7,8,11,12,14,15,18,19,25,26,29,30,32,33,36,37,38,39,40,41,42,43,44};
    protected final int[] INPUT_SLOT=new int[]{10,28};
    protected final int[] OUTPUT_SLOT=new int[]{16,34};
    protected final int[] OUTPUT_BORDER=new int[]{17,35};

    protected final int TRANSFER_ALLENCHANT_SLOT=20;
    protected final int TRANSFER_ALLATTRIBUTE_SLOT=24;
    protected final int TRANSFER_ONEENCHANT_SLOT=21;
    protected final int TRANSFER_ONEATTRIBUTE_SLOT=23;
    protected final int CHOOSE_ENCHANT_SLOT=13;
    protected final int CHOOSE_ATTRIBUTE_SLOT=31;
    protected final int FORCE_ENCHANT_SLOT=22;
    protected final int INPUT_ONE_INFO_SLOT=9;
    protected final ItemStack INPUT_ONE_INFO_ITEM=new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE,"&6被转移者输入槽",
            "&7在此放入即将被转移的物品","&7一次最多只能消耗一个");
    protected final int INPUT_TWO_INFO_SLOT=27;
    protected final ItemStack INPUT_TWO_INFO_ITEM=new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE,"&6转移目标输入槽",
            "&7可以放入转移的目标装备,或者附魔书,或者书","&7物品一次最多消耗一个");
    protected final ItemStack TRANSFER_ALLENCHANT_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&6转移所有附魔","&7点击转移一次","&7shift点击切换是否自动运行","&7自动运行: &c否");
    protected final ItemStack TRANSFER_ALLENCHANT_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&6转移所有附魔","&7点击转移一次","&7shift点击切换是否自动运行","&7自动运行: &a是","&e当自动运行时禁止进行其他操作!");
    protected final ItemStack TRANSFER_ALLATTRIBUTE_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&6转移所有属性","&7点击转移一次","&7shift点击切换是否自动运行","&7自动运行: &c否");
    protected final ItemStack TRANSFER_ALLATTRIBUTE_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&6转移所有属性","&7点击转移一次","&7shift点击切换是否自动运行","&7自动运行: &a是","&e当自动运行时禁止进行其他操作!");
    protected final ItemStack TRANSFER_ONEENCHANT_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&6转移单一附魔","&7点击转移一次","&7使用上方的按钮切换选中的附魔");
    protected final ItemStack TRANSFER_ONEATTRIBUTE_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            "&6转移单一属性","&7点击转移一次","&7使用下方的按钮切换选中的属性");
    protected final ItemStack CHOOSE_ENCHANT_ITEM=new CustomItemStack(Material.ENCHANTED_BOOK,
            "&6选择你的附魔","&7点击顺序切换","&7shift点击逆序切换","&8⇨ &c当前暂无附魔");
    protected final ItemStack CHOOSE_ATTRIBUTE_ITEM=new CustomItemStack(Material.DIAMOND_CHESTPLATE,
            "&6选择你的附魔","&7点击顺序切换","&7shift点击逆序切换","&8⇨ &c当前暂无属性");
    protected final ItemStack FORCE_ENCHANT_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6强制附魔","&7当前模式: &c关",
            "&7关闭强制附魔将会在转移附魔时将书本转移为附魔书","&7或在附魔书的已有附魔存储中加入转移的附魔","&e开启则会强制附魔书本和附魔书,并不会加入附魔存储");
    protected final ItemStack FORCE_ENCHANT_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6强制附魔","&7当前模式: &a开",
            "&7关闭强制附魔将读取附魔书中存储的附魔","&7若目标物品为书本,则会自动转为附魔书","&e开启则会强制附魔书本和附魔书,并不会加入附魔存储");
    protected final ItemStack OUTPUT_BORDER_ITEM=new CustomItemStack(Material.PINK_STAINED_GLASS_PANE,"&6输出槽","&7对应输入槽的输出口");
    public AttributeOperator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,int energyBuffer,int energyConsumption) {
        super(category, item, recipeType, recipe, energyBuffer,energyConsumption);
        this.setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow("&f机制",
                                "&7支持在物品之间转移属性增幅符和附魔",
                                "&7分别可以选择转移单项附魔/增幅符或者转移全部附魔/增幅符",
                                "&7单项选择器将显示在菜单中,点击顺序切换,shift点击逆序切换",
                                "&7点击后消耗电力执行转换",
                                "&7该转移不受任何禁止附魔/驱魔的设定影响",
                                "&7该转移不受物品材质的附魔种类限制"
                                ),null,
                        AddUtils.getInfoShow("&f机制",
                                "&7该机器提供\"强制附魔\"机制",
                                "&7当启用时,对于附魔书和书本,将会直接读取其附魔列表",
                                "&7而非其存储的附魔列表",
                                "&e启用该机制通常得不到正常玩家想要的结果",
                                "&7当关闭该机制时,对于附魔书和书本",
                                "&7当附魔书作为被转移物品时,会把书中存储的附魔附加至目标物品",
                                "&7当普通书本作为目标物品时,会自动转为附魔书",
                                "&7当附魔书作为目标物品时,会把被转移物品的附魔列表添加到存储的附魔中"),null
                )
        );
    }
    public void addInfo(ItemStack item){
        if(this.energyConsumption > 0){
            item.setItemMeta(AddUtils.workBenchInfoAdd(item,this.energybuffer,this.energyConsumption).getItemMeta());
        }
    }
    public DataMenuClickHandler createDataHolder(){
        return new DataMenuClickHandler() {
            //0 为 数量 1 为 电力
            int[] intdata=new int[2];
            Object[] object=new Object[2];
            ItemStack itemStacks=null;
            public int getInt(int i){
                return intdata[i];
            }
            public void setInt(int i, int val){
                intdata[i]=val;
            }
            public ItemStack getItemStack(int val){
                return itemStacks;
            }
            public void setItemStack(int val , ItemStack stack){
                itemStacks=stack;
            }
            public Object getObject(int i){
                return object[i];
            }
            public void setObject(int i, Object obj){
                object[i]=obj;
            }
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                return false;
            }
        };
    }
    public final int DATA_SLOT=0;
    public DataMenuClickHandler getDataHolder(Block b, BlockMenu inv){
        ChestMenu.MenuClickHandler handler=inv.getMenuClickHandler(DATA_SLOT);
        if(handler instanceof DataMenuClickHandler dh){return dh;}
        else{
            DataMenuClickHandler dh=createDataHolder();
            inv.addMenuClickHandler(DATA_SLOT,dh);
            return dh;
        }
    }
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<MachineRecipe>();
    }
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public void tick(Block b, BlockMenu inv, SlimefunBlockData data,int tixkwe){
        if(inv.hasViewer()){
            updateMenu(inv,b,Settings.RUN);
        }
        int code=getAutoTransferCode(inv);
        if(code>=0){
                Schedules.launchSchedules(()-> {
                    refreshInputItemData(inv, getDataHolder(b, inv), true);
                    runTransferItemData(inv, code, true, null);
                },0,false,0);
        }
    }
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){

    }
    protected String AUTOKEY_E="ate";
    protected String AUTOKEY_A="ata";
    protected String FORCEKEY="fe";
    public void newMenuInstance(@Nonnull BlockMenu inv, @Nonnull Block block){
        SlimefunBlockData data= DataCache.safeLoadBlock(inv.getLocation());
        DataMenuClickHandler handler=getDataHolder(block,inv);
        inv.addMenuOpeningHandler(player -> updateMenu(inv,block,Settings.RUN));
        inv.addMenuCloseHandler(player -> updateMenu(inv,block,Settings.RUN));
        inv.addItem(TRANSFER_ALLENCHANT_SLOT,TRANSFER_ALLENCHANT_ITEM,((player, i, itemStack, clickAction) -> {
            int code=getAutoTransferCode(inv);
            if(clickAction.isShiftClicked()){
                if(code==0||code==2){
                    DataCache.setCustomString(inv.getLocation(),AUTOKEY_E,"");
                    inv.replaceExistingItem(TRANSFER_ALLENCHANT_SLOT,TRANSFER_ALLENCHANT_ITEM);
                }else {
                    DataCache.setCustomString(inv.getLocation(),AUTOKEY_E,"1");
                    inv.replaceExistingItem(TRANSFER_ALLENCHANT_SLOT,TRANSFER_ALLENCHANT_ITEM_ON    );
                }
                AddUtils.sendMessage(player,"&a成功切换自动运行");
                return false;
            }
            if(code>=0){

                AddUtils.sendMessage(player,"&c当前处于自动模式,禁止进行任何其余操作");
                return false;
            }
            runTransferItemData(inv,0,true,(str)->AddUtils.sendMessage(player,str));

            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        inv.addItem(TRANSFER_ALLATTRIBUTE_SLOT,TRANSFER_ALLATTRIBUTE_ITEM,((player, i, itemStack, clickAction) -> {
            int code=getAutoTransferCode(inv);
            if(clickAction.isShiftClicked()){
                if(code==1||code==2){
                    DataCache.setCustomString(inv.getLocation(),AUTOKEY_A,"");
                    inv.replaceExistingItem(TRANSFER_ALLATTRIBUTE_SLOT,TRANSFER_ALLATTRIBUTE_ITEM);
                }else {
                    DataCache.setCustomString(inv.getLocation(),AUTOKEY_A,"1");
                    inv.replaceExistingItem(TRANSFER_ALLATTRIBUTE_SLOT,TRANSFER_ALLATTRIBUTE_ITEM_ON        );
                }
                AddUtils.sendMessage(player,"&a成功切换自动运行");
                return false;
            }
            if(code>=0){

                AddUtils.sendMessage(player,"&c当前处于自动模式,禁止进行任何其余操作");
                return false;
            }
            runTransferItemData(inv,1,true,(str)->AddUtils.sendMessage(player,str));
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        inv.addItem(TRANSFER_ONEENCHANT_SLOT,TRANSFER_ONEENCHANT_ITEM,((player, i, itemStack, clickAction) -> {
            if(getAutoTransferCode(inv)>=0){
                AddUtils.sendMessage(player,"&c当前处于自动模式,禁止进行任何其余操作");
                return false;
            }
            runTransferItemData(inv,0,false,(str)->AddUtils.sendMessage(player,str));
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        inv.addItem(TRANSFER_ONEATTRIBUTE_SLOT,TRANSFER_ONEATTRIBUTE_ITEM,((player, i, itemStack, clickAction) -> {
            if(getAutoTransferCode(inv)>=0){
                AddUtils.sendMessage(player,"&c当前处于自动模式,禁止进行任何其余操作");
                return false;
            }
            runTransferItemData(inv,1,false,(str)->AddUtils.sendMessage(player,str));
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        inv.addItem(FORCE_ENCHANT_SLOT,FORCE_ENCHANT_ITEM_OFF,((player, i, itemStack, clickAction) -> {
            if(itemStack!=null&& itemStack.getType()==Material.RED_STAINED_GLASS_PANE){
                DataCache.setCustomString(inv.getLocation(),FORCEKEY,"1");
                inv.replaceExistingItem(FORCE_ENCHANT_SLOT,FORCE_ENCHANT_ITEM_ON);
            }else {
                DataCache.setCustomString(inv.getLocation(),FORCEKEY,"");
                inv.replaceExistingItem(FORCE_ENCHANT_SLOT,FORCE_ENCHANT_ITEM_OFF);
            }
            updateMenu(inv,block,Settings.INIT);
            return false;
        }));
        inv.addItem(CHOOSE_ATTRIBUTE_SLOT,CHOOSE_ATTRIBUTE_ITEM,((player, i, itemStack, clickAction) -> {
            if(getAutoTransferCode(inv)>=0){
                AddUtils.sendMessage(player,"&c当前处于自动模式,禁止进行任何其余操作");
                return false;
            }
            DataMenuClickHandler dh=getDataHolder(block,inv);
            dh.setInt(1,dh.getInt(1)+(clickAction.isShiftClicked()?-1:1));
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        inv.addItem(CHOOSE_ENCHANT_SLOT,CHOOSE_ENCHANT_ITEM,((player, i, itemStack, clickAction) -> {
            if(getAutoTransferCode(inv)>=0){
                AddUtils.sendMessage(player,"&c当前处于自动模式,禁止进行任何其余操作");
                return false;
            }
            DataMenuClickHandler dh=getDataHolder(block,inv);
            dh.setInt(0,dh.getInt(0)+(clickAction.isShiftClicked()?-1:1));
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        int autoE=DataCache.getCustomString(data,AUTOKEY_E,"").length();
        int autoA=DataCache.getCustomString(data,AUTOKEY_A,"").length();
        int force=DataCache.getCustomString(data,FORCEKEY,"").length();
        if(autoE==1){
            inv.replaceExistingItem(TRANSFER_ALLENCHANT_SLOT,TRANSFER_ALLENCHANT_ITEM_ON    );
        }
        if(autoA==1){
            inv.replaceExistingItem(TRANSFER_ALLATTRIBUTE_SLOT,TRANSFER_ALLATTRIBUTE_ITEM_ON);
        }
        if(force==1){
            inv.replaceExistingItem(FORCE_ENCHANT_SLOT,FORCE_ENCHANT_ITEM_ON);
        }
        updateMenu(inv,block,Settings.INIT);
    }
    public int getAutoTransferCode(BlockMenu inv){
        ItemStack enchantAutoFlag=inv.getItemInSlot(TRANSFER_ALLENCHANT_SLOT);
        boolean runEnchant=enchantAutoFlag!=null&&enchantAutoFlag.getType()==Material.GREEN_STAINED_GLASS_PANE;
        ItemStack attributeAutoFlag=inv.getItemInSlot(TRANSFER_ALLATTRIBUTE_SLOT);
        boolean runAttribute=attributeAutoFlag!=null&&attributeAutoFlag.getType()==Material.GREEN_STAINED_GLASS_PANE;
        int code=-1+(runEnchant?1:0)+(runAttribute?2:0);
        return code;
    }
    public void updateMenu(BlockMenu inv,Block b,Settings mod){
        DataMenuClickHandler handler=getDataHolder(b,inv);
        refreshInputItemData(inv,handler,mod==Settings.INIT);
        int indexE=handler.getInt(0);
        int indexA=handler.getInt(1);
        Object obj1=handler.getObject(0);
        try{
            if(obj1!=null){
                Map<Enchantment,Integer> emp=(Map<Enchantment,Integer>)obj1;
                if(!emp.isEmpty()){
                    indexE=(indexE+emp.size())%emp.size();
                    handler.setInt(0,indexE);
                }
                getEnchantmentShow(inv,emp,indexE);
            }else {
                inv.replaceExistingItem(CHOOSE_ENCHANT_SLOT,CHOOSE_ENCHANT_ITEM);
            }
        }catch(Throwable ex){
            inv.replaceExistingItem(CHOOSE_ENCHANT_SLOT,CHOOSE_ENCHANT_ITEM);
            Debug.debug(ex);
        }
        Object obj2=handler.getObject(1);
        try{
            if(obj2!=null){
                Multimap<Attribute,AttributeModifier> emp2=(Multimap<Attribute,AttributeModifier>)obj2;
                if(!emp2.isEmpty()){
                    indexA=(indexA+emp2.size())%emp2.size();
                    handler.setInt(1,indexA);
                }
                getAttributeShow(inv,emp2,indexA);
            }else {
                inv.replaceExistingItem(CHOOSE_ATTRIBUTE_SLOT,CHOOSE_ATTRIBUTE_ITEM);
            }
        }catch(Throwable ex){
            inv.replaceExistingItem(CHOOSE_ATTRIBUTE_SLOT,CHOOSE_ATTRIBUTE_ITEM);
            Debug.debug(ex);
        }
    }
    public void refreshInputItemData(BlockMenu inv,DataMenuClickHandler handler,boolean forceReload){
        ItemStack stack1=inv.getItemInSlot(INPUT_SLOT[0]);
        ItemStack stackCache=handler.getItemStack(0);
        if(forceReload||(!(stack1!=null&&CraftUtils.sameCraftItem(stack1,stackCache)))){
            handler.setItemStack(0,stack1);
            handler.setInt(0,0);
            handler.setInt(1,0);
            if(stack1==null||stack1.getType().isAir()){
                handler.setObject(0,null);
                handler.setObject(1,null);
            }else {
                ItemStack forceFlag=inv.getItemInSlot(FORCE_ENCHANT_SLOT);
                boolean forceEnchant=  forceFlag!=null&&forceFlag.getType()==Material.GREEN_STAINED_GLASS_PANE;
                ItemMeta meta=stack1.getItemMeta();
                if(!forceEnchant&&meta instanceof EnchantmentStorageMeta esm){
                    handler.setObject(0,esm.getStoredEnchants());
                }else {
                    handler.setObject(0,meta.getEnchants());
                }
                handler.setObject(1,meta.getAttributeModifiers());
            }
        }
    }
    protected List<String> LORES_ENCHANT=this.CHOOSE_ENCHANT_ITEM.getItemMeta().getLore().subList(0,2);
    protected ItemMeta CHOOSE_ENCHANT_ITEM_META=this.CHOOSE_ENCHANT_ITEM.getItemMeta();
    protected List<String> LORES_ATTRIBUTE=this.CHOOSE_ATTRIBUTE_ITEM.getItemMeta().getLore().subList(0,2);
    protected ItemMeta CHOOSE_ATTRIBUTE_ITEM_META=this.CHOOSE_ATTRIBUTE_ITEM.getItemMeta();
    protected String PREFIX_LORE=AddUtils.resolveColor("&8⇨ &c");
    protected String PREFIX_CHOOSEN=AddUtils.resolveColor("&e>> &x&e&b&3&3&e&b");
    public Enchantment getEnchantmentSelected(Map<Enchantment,Integer> map,int index){
        if(map==null||map.isEmpty()){
            return null;
        }
        int t=0;
        for (Map.Entry<Enchantment,Integer> entry:map.entrySet()) {
            if(index==t){
                return entry.getKey();
            }
            ++t;
        }
        return null;
    }
    public void getEnchantmentShow(BlockMenu inv,Map<Enchantment,Integer> map,int index){
        if(map==null||map.isEmpty()){
            inv.replaceExistingItem(CHOOSE_ENCHANT_SLOT, CHOOSE_ENCHANT_ITEM);
            return;
        }
        List<String> lore=new ArrayList<>(this.LORES_ENCHANT) ;
        ItemMeta meta=CHOOSE_ENCHANT_ITEM_META.clone();
        int t=0;
        for (Map.Entry<Enchantment,Integer> entry:map.entrySet()) {
            lore.add(AddUtils.concat((t==index)?PREFIX_CHOOSEN:PREFIX_LORE,
                    EnchantmentHelper.getEnchantmentName(entry.getKey(),false)," lvl: ",entry.getValue().toString())) ;
            ++t;
        }
        meta.setLore(lore);
        ItemStack stack=inv.getItemInSlot(CHOOSE_ENCHANT_SLOT);
        if(stack==null||stack.getType().isAir()){
            stack=new ItemStack(Material.ENCHANTED_BOOK);
            stack.setItemMeta(meta);
            inv.replaceExistingItem(CHOOSE_ATTRIBUTE_SLOT,stack);
        }else
            stack.setItemMeta(meta);
    }
    public Pair<Attribute,AttributeModifier> getAttributeSelected(Multimap<Attribute,AttributeModifier> att,int index){
        if(att==null||att.isEmpty()){
            return null;
        }
        int t=0;
        for (Map.Entry<Attribute,AttributeModifier> entry:att.entries()){
            if(t==index){
                return new Pair<>(entry.getKey(),entry.getValue());
            }
            ++t;
        }
        return null;
    }
    public void getAttributeShow(BlockMenu inv,Multimap<Attribute,AttributeModifier> att,int index){
        if(att==null||att.isEmpty()){
            inv.replaceExistingItem(CHOOSE_ATTRIBUTE_SLOT, CHOOSE_ATTRIBUTE_ITEM);
            return;
        }
        List<String> lore=new ArrayList<>(this.LORES_ATTRIBUTE) ;
        ItemMeta meta=CHOOSE_ATTRIBUTE_ITEM_META.clone();
        int t=0;
        for (Map.Entry<Attribute,AttributeModifier> entry:att.entries()){
            AttributeModifier mod=entry.getValue();
            String valueString="";
            switch (mod.getOperation()){
                case ADD_NUMBER -> valueString=AddUtils.concat(" +",String.valueOf(mod.getAmount()));
                case ADD_SCALAR -> valueString=AddUtils.concat(" +",String.valueOf(mod.getAmount()*100),"%");
                case MULTIPLY_SCALAR_1 -> valueString=AddUtils.concat(" x",String.valueOf(mod.getAmount()*100),"%");
            }
            lore.add(AddUtils.concat((t==index)?PREFIX_CHOOSEN:PREFIX_LORE, AttributeHelper.getName(entry.getKey()),
                    valueString)) ;
            ++t;
        }
        meta.setLore(lore);
        ItemStack stack=inv.getItemInSlot(CHOOSE_ATTRIBUTE_SLOT);
        if(stack==null||stack.getType().isAir()){
            stack=new ItemStack(Material.DIAMOND_CHESTPLATE);
            stack.setItemMeta(meta);
            inv.replaceExistingItem(CHOOSE_ATTRIBUTE_SLOT,stack);
        }else
            stack.setItemMeta(meta);
    }
    public Pair<ItemStack,ItemStack> transferEnchantment(ItemStack stack1, ItemStack stack2, Enchantment enchantment, boolean forceEnchant){
        if(!forceEnchant&& stack2.getType()==Material.BOOK){
            stack2=new ItemStack(Material.ENCHANTED_BOOK);
        }
        ItemMeta meta1=stack1.getItemMeta();
        ItemMeta meta2=stack2.getItemMeta();
        if(meta1==null||meta2==null){
            return null;
        }
       boolean hasChanged=false;
        if(enchantment==null){
            Map<Enchantment,Integer> ems;
            if(!forceEnchant && meta1 instanceof EnchantmentStorageMeta em){
                ems= em.getStoredEnchants();
                if(ems==null||ems.isEmpty()){
                    return null;
                }
                for(Enchantment e:ems.keySet()){
                    em.removeStoredEnchant(e);
                }
            }else {
                ems=meta1.getEnchants();
                if(ems==null||ems.isEmpty()){
                    return null;
                }
                for(Enchantment e:ems.keySet()){
                    meta1.removeEnchant(e);
                }
            }
            if(!forceEnchant&& meta2 instanceof EnchantmentStorageMeta esm){
                for(Map.Entry<Enchantment,Integer> e:ems.entrySet()){
                    int lvl=e.getValue();
                    if(lvl>esm.getStoredEnchantLevel(e.getKey())){
                        esm.addStoredEnchant(e.getKey(),lvl,true);
                    }
                    hasChanged=true;
                }
            }else {
                for(Map.Entry<Enchantment,Integer> e:ems.entrySet()){
                    int lvl=e.getValue();
                    if(lvl>meta2.getEnchantLevel(e.getKey())){
                        meta2.addEnchant(e.getKey(),lvl,true);
                    }
                    hasChanged=true;
                }
            }

        }else{
            int lvl;
            if(!forceEnchant&& meta1 instanceof EnchantmentStorageMeta em){
                lvl=em.getStoredEnchantLevel(enchantment);
                em.removeStoredEnchant(enchantment);
            }else {
                lvl=meta1.getEnchantLevel(enchantment);
                meta1.removeEnchant(enchantment);
            }
            if(!forceEnchant&&meta2 instanceof EnchantmentStorageMeta esm){
                if(lvl>esm.getStoredEnchantLevel(enchantment)){
                    esm.addStoredEnchant(enchantment,lvl,true);
                }
            }else {
                if(lvl>meta2.getEnchantLevel(enchantment)){
                    meta2.addEnchant(enchantment,lvl,true);
                }
            }
            hasChanged=true;

        }
        if(!hasChanged){
            return null;
        }
        ItemStack res1=new ItemStack(stack1.getType(),1);
        res1.setItemMeta(meta1);
        ItemStack res2=new ItemStack(stack2.getType(),1);
        res2.setItemMeta(meta2);
        return new Pair(res1,res2);
    }
    public void runTransferItemData(BlockMenu inv,int mode,boolean transferAll, Consumer<String> outputStream){
        if(!this.conditionHandle(null,inv)){
            if(outputStream!=null){
                outputStream.accept("&c电力不足!");
            }
            return;
        }
        ItemStack it3=inv.getItemInSlot(OUTPUT_SLOT[0]);
        ItemStack it4=inv.getItemInSlot(OUTPUT_SLOT[1]);
        if(it3!=null||it4!=null){
            if(outputStream!=null){
                outputStream.accept("&c输出空间不足!");
            }
            return;
        }
        ItemStack it1=inv.getItemInSlot(INPUT_SLOT[0]);
        ItemStack it2=inv.getItemInSlot(INPUT_SLOT[1]);
        if (it1 == null||it1.getType().isAir()) {
            if(outputStream!=null){
                outputStream.accept("&c被转换的物品不能为空!");
            }
            return;
        }
        if (it2 == null||it2.getType().isAir()) {
            if(outputStream!=null){
                outputStream.accept("&c转换的目标物品不能为空");
            }
            return;
        }
        Pair<ItemStack,ItemStack> transferResult=null;
        DataMenuClickHandler handler=getDataHolder(null,inv);
        if(mode==0){
            Object e1=handler.getObject(0);
            ItemStack forceFlag=inv.getItemInSlot(FORCE_ENCHANT_SLOT);
            boolean forceEnchant=forceFlag!=null&&forceFlag.getType()==Material.GREEN_STAINED_GLASS_PANE;
            try{
                Map<Enchantment,Integer> map=(Map<Enchantment,Integer>)e1;
                if(map!=null&&!map.isEmpty()){
                    transferResult=transferEnchantment(it1,it2,(transferAll)?null:getEnchantmentSelected(map,handler.getInt(0)),forceEnchant);
                }else {
                    if(outputStream!=null){
                        outputStream.accept("&c被转换的物品不存在附魔");
                    }
                    return;
                }
            }catch (Throwable e){
                Debug.debug(e);
                if(outputStream!=null){
                    outputStream.accept("&c未知错误,请检查MC版本并联系作者!");
                }return;
            }
        }else if(mode==1){
            Object e2=handler.getObject(1);
            try{
                Multimap<Attribute,AttributeModifier> map=(Multimap<Attribute,AttributeModifier>)e2;
                if(map!=null&&!map.isEmpty()){
                    if(transferAll){
                        transferResult= transferAttributes(it1,it2,null,null);
                    }else {
                        Pair<Attribute,AttributeModifier> att=getAttributeSelected(map,handler.getInt(1));
                        transferResult= transferAttributes(it1,it2,att.getFirstValue(),att.getSecondValue());
                    }
                }else {
                    if(outputStream!=null){
                        outputStream.accept("&c被转换的物品不存在属性增幅符");
                    }
                    return;
                }

            }catch (Throwable e){
                Debug.debug(e);
                if(outputStream!=null){
                    outputStream.accept("&c转移失败,该属性增幅符疑似已在该物品上被注册过");
                }return;
            }
        }else {
            Object e1=handler.getObject(0);
            Object e2=handler.getObject(1);
            ItemStack forceFlag=inv.getItemInSlot(FORCE_ENCHANT_SLOT);
            boolean forceEnchant=forceFlag!=null&&forceFlag.getType()==Material.GREEN_STAINED_GLASS_PANE;
            try{
                Map<Enchantment,Integer> map=(Map<Enchantment,Integer>)e1;
                if(map!=null&&!map.isEmpty()){
                    transferResult=transferEnchantment(it1,it2,(transferAll)?null:getEnchantmentSelected(map,handler.getInt(0)),forceEnchant);

                }if(transferResult==null){
                    return;
                }
                Multimap<Attribute,AttributeModifier> map2=(Multimap<Attribute,AttributeModifier>)e2;
                if(map2!=null&&!map2.isEmpty()){
                    if(transferAll){
                        transferResult= transferAttributes(transferResult.getFirstValue(),transferResult.getSecondValue(),null,null);
                    }else {
                        Pair<Attribute,AttributeModifier> att=getAttributeSelected(map2,handler.getInt(1));
                        transferResult= transferAttributes(transferResult.getFirstValue(),transferResult.getSecondValue(),att.getFirstValue(),att.getSecondValue());
                    }
                }if(transferResult==null){
                    return;
                }
            }catch (Throwable e){
                Debug.debug(e);
                return;
            }
        }
        if(transferResult==null){
            if(outputStream!=null){
                outputStream.accept("&c转移失败!目标物品上已有更高级的属性值/附魔");
            }
            return;
        }
        inv.replaceExistingItem(OUTPUT_SLOT[0],transferResult.getFirstValue());
        inv.replaceExistingItem(OUTPUT_SLOT[1],transferResult.getSecondValue());
        it1.setAmount(it1.getAmount()-1);
        it2.setAmount(it2.getAmount()-1);
        if(it1.getAmount()==0)
            refreshInputItemData(inv,handler,true);
        progressorCost(null,inv);
        if(outputStream!=null){
            outputStream.accept("&a转移成功!");
        }
    }
    public boolean equalsIgnoreAmount(AttributeModifier it,AttributeModifier mod) {
        boolean slots = it.getSlot() != null ? it.getSlot() == mod.getSlot() : mod.getSlot() == null;
        return it.getUniqueId().equals(mod.getUniqueId()) && it.getName().equals(mod.getName()) && it.getOperation() == mod.getOperation() && slots;

    }
    public Pair<ItemStack,ItemStack> transferAttributes(ItemStack stack1, ItemStack stack2,Attribute attribute, AttributeModifier modifier){
        ItemMeta meta1=stack1.getItemMeta();
        ItemMeta meta2=stack2.getItemMeta();
        if(meta1==null||meta2==null){
            return null;
        }
        boolean hasChanged=false;
        if(modifier==null){
            for(Attribute a:Attribute.values()){
                Collection<AttributeModifier> mods1=meta1.getAttributeModifiers(a);
                if(mods1==null||mods1.isEmpty()){
                    continue ;
                }
                Collection<AttributeModifier> mods2;
                if(meta2.hasAttributeModifiers())
                    mods2=meta2.getAttributeModifiers(a);
                else
                    mods2=null;
                if(mods2!=null){
                    mods2.addAll(mods1);

                }else {
                    mods1.forEach(mod->{meta2.addAttributeModifier(a,mod);});
                }
                hasChanged=true;
                meta1.removeAttributeModifier(a);
            }
        }else {
            meta1.removeAttributeModifier(attribute,modifier);
            meta2.addAttributeModifier(attribute,modifier);
            hasChanged=true;
        }
        if(!hasChanged){
            return null;
        }
        ItemStack res1=new ItemStack(stack1.getType(),1);
        res1.setItemMeta(meta1);
        ItemStack res2=new ItemStack(stack2.getType(),1);
        res2.setItemMeta(meta2);
        return new Pair(res1,res2);
    }
    public  void constructMenu(BlockMenuPreset preset){
        preset.setSize(45);
        int[] border=BORDER;
        int len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=OUTPUT_BORDER;
        len=border.length;
        for (int i=0;i<len;++i){
            preset.addItem(border[i],OUTPUT_BORDER_ITEM,ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(INPUT_ONE_INFO_SLOT,INPUT_ONE_INFO_ITEM,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INPUT_TWO_INFO_SLOT,INPUT_TWO_INFO_ITEM,ChestMenuUtils.getEmptyClickHandler());
    }
}
