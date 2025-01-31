package me.matl114.logitech.core.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.core.Machines.Abstracts.AbstractMachine;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.AddUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class RegisteryLogger extends AbstractMachine {
    protected final int[] BORDER=new int[]{33, 34, 35, 6,  42,  44,   51, 52, 53, 24};
    protected final int[] READING_SLOT=new int[]{0, 1, 2, 3, 4, 5, 9, 10, 11, 12, 13, 14, 18,
            19, 20, 21, 22, 23, 27, 28, 29, 30, 31, 32, 36, 37, 38, 39, 40, 41, 45, 46, 47, 48, 49, 50};
    protected final int LOGGER_SLOT=16;
    protected final int RESULT_SLOT=43;
    protected final int CHOOSE_SLOT=7;
    protected final int TOGGLE_RECIPE=25;
    protected final int ID_SLOT=15;
    protected final ItemStack ID_ITEM=new CustomItemStack(Material.PAPER,"&a点击输入SlimefunID");
    protected final ItemStack USE_RECIPE=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a使用该配方注册");
    protected final ItemStack DEFAULT_RECIPE=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&a使用默认配方注册");
    protected final ItemStack[] CHOOSE_ITEM=new ItemStack[]{
        new CustomItemStack(Material.GRASS_BLOCK,"&a生成: 普通材料"),
        new CustomItemStack(Material.FURNACE,"&a生成: 普通机器模板"),
        new CustomItemStack(Material.DIAMOND_BLOCK,"&a生成: 普通生成器模板"),
        new CustomItemStack(Material.EMERALD,"&a生成: 普通定向模板"),
        new CustomItemStack(Material.CRAFTING_TABLE,"&a生成: 普通快捷"),
        new CustomItemStack(Material.PAPER,"&a生成: 普通物品"),
        new CustomItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,"&a生成: FU单元")
    };

    protected final ItemStack LANGUAGE_ITEM=new CustomItemStack(Material.WRITABLE_BOOK,"&a点击打印本附属语言文件模板","&7开发者专用","&7因为其他人也不用这格式");
    protected final int LANGUAGE_SLOT=8;
    protected final ItemStack ADDITEM_ITEM=new CustomItemStack(Material.BEACON,"&a点击打印本附属物品注册模板","&7开发者专用","&7因为其他人也不用这格式");
    protected final int ADDITEM_SLOT=17;
    protected final ItemStack ADDSFITEM_ITEM=new CustomItemStack(Material.SLIME_BLOCK,"&a点击打印本附属粘液物品注册模板","&7开发者专用","&7因为其他人也不用这格式");
    protected final int ADDSFITEM_SLOT=26;
    protected final ItemStack LOGGER_ITEM=new CustomItemStack(Material.COMMAND_BLOCK,"&a点击打印本附属格式化配方注册字符串","&7开发者专用","&7因为其他人也不用这格式");
    protected final String LANGUAGE_CODE="%s:\n" +
            "      Name: ''\n" +
            "      Lore:\n" +
            "        - ''";
    protected final String DEFAULT_RECIPE_CODE="formatInfoRecipe(AddItem.TMP1,get(\"Tmp.TMP1.Name\"))";
    protected final String[] ADDITEM_CODE=new String[]{
        "public static final SlimefunItemStack %s=themed(\"%s\",Material.%s,Theme.ITEM1,\n" +
                "            get(\"Items.%s.Name\"),getList(\"Items.%s.Lore\"));",
        "public static final SlimefunItemStack %s=themed(\"%s\",Material.%s,Theme.MACHINE1,\n" +
                "            get(\"Machines.%s.Name\"),getList(\"Machines.%s.Lore\"));",
        "public static final SlimefunItemStack %s=themed(\"%s\",Material.%s,Theme.MACHINE2,\n" +
                "            get(\"Generators.%s.Name\"),getList(\"Generators.%s.Lore\"));",
        "public static final SlimefunItemStack %s=themed(\"%s\",Material.%s,Theme.MACHINE2,\n" +
                "            get(\"Generators.%s.Name\"),getList(\"Generators.%s.Lore\"));",
        "public static final SlimefunItemStack %s=themed(\"%s\",Material.%s,Theme.MANUAL1,\n" +
                "            get(\"Manuals.%s.Name\"),getList(\"Manuals.%s.Lore\"));",
        "public static final ItemStack %s=themed(\"%s\",Material.%s, Theme.NONE,\n" +
                "            get(\"Groups.%s.Name\"),getList(\"Groups.%s.Lore\"));",
        "public static final ItemStack %s=themed(\"%s\",Material.%s, Theme.FUNIT,\n" +
                "            get(\"Items.%s.Name\"),getList(\"Items.%s.Lore\"));"
    };
    protected final String[] ADDSFITEM_CODE=new String[]{
        "public static final SlimefunItem %s=new MaterialItem(MATERIAL,AddItem.%s,NULL,\n" +
                "            %s,null)\n" +
                "            .register();",
        "public static final  SlimefunItem %s=new EMachine(BASIC, AddItem.%s,NULL,\n" +
                "            %s, Material.STONE,0,0,null)\n" +
                "            .register();",
        "public static final SlimefunItem %s=new SMGenerator(GENERATORS, AddItem.%s,NULL,\n" +
                "            %s,1,0,0,\n" +
                "            null)\n" +
                "            .register();",
        "public static final SlimefunItem %s = new MMGenerator(GENERATORS, AddItem.%s, NULL,\n" +
                "            %s, 1, 0, 0,null)\n" +
                "            .register();",
        "public static final SlimefunItem %s=new ManualMachine(MANUAL,AddItem.%s,NULL,\n" +
                "            %s,0,0,null)\n" +
                "            .register();",
        "NULL",
        "public static final SlimefunItem %s=new EquipmentFUItem(TOOLS_SUBGROUP_2,AddItem.%s,NULL,\n" +
                "            %s,EFUImplements.DEMO)\n" +
                "            .register();",
    };
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public RegisteryLogger(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe, 0,0);
    }
    public void addInfo(ItemStack item){

    }
    public  void constructMenu(BlockMenuPreset preset){
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(LOGGER_SLOT,LOGGER_ITEM);
        preset.addItem(LANGUAGE_SLOT,LANGUAGE_ITEM);
        preset.addItem(ADDITEM_SLOT,ADDITEM_ITEM);
        preset.addItem(ADDSFITEM_SLOT,ADDSFITEM_ITEM);
        preset.addItem(ID_SLOT,ID_ITEM);
    }

    public void process(Block b, BlockMenu preset, SlimefunBlockData data){

    }
    public void registerTick(SlimefunItem item){
        //no ticker
    }
    public String getRecipeString(BlockMenu inv,int[] slots){
        SlimefunAddon addon= MyAddon.getInstance();
        String result="recipe(";
        String[] idList=new String[slots.length];
        for(int var4 = 0; var4 < slots.length; ++var4){
            ItemStack it=inv.getItemInSlot(slots[var4]);
            if(it!=null){
                SlimefunItem sfit=SlimefunItem.getByItem(it);
                if(sfit!=null){
                    String id=sfit.getId();
                    if(sfit.getAddon()==addon&&id.startsWith(AddUtils.ADDON_ID)){
                        id=id.substring((AddUtils.ADDON_ID+"_").length());
                        if(it.getAmount()==1){
                            idList[var4]="AddItem.%s".formatted(id);
                        }else{
                            idList[var4]="setC(AddItem.%s,%s)".formatted(id,it.getAmount());
                        }
                    }else{
                        if(it.getAmount()==1){
                            idList[var4]="\"%s\"".formatted(id);
                        }else{
                            idList[var4]="\"%s%s\"".formatted(it.getAmount(),id);
                        }
                    }
                }else {
                    if(it.getAmount()==1){
                        idList[var4]="\"%s\"".formatted(it.getType().toString());
                    }else {
                        idList[var4]="\"%s%s\"".formatted(it.getAmount(),it.getType().toString());
                    }

                }
            }else {
                idList[var4]="null";
            }
            if(var4==0)
                result+=idList[var4];
            else if(var4%6==0){
                result+=",\n"+idList[var4];
            }else {
                result+=","+idList[var4];
            }

        }
        result+=")";
        return result;
    }
    public void newMenuInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block){
        if(DataCache.getLastRecipe(blockMenu.getLocation())<0||DataCache.getLastRecipe(blockMenu.getLocation())>CHOOSE_ITEM.length){
            DataCache.setLastRecipe(blockMenu.getLocation(),0);
        }
        blockMenu.addMenuClickHandler(LOGGER_SLOT,((player, i, itemStack, clickAction) -> {
            String recipeString=getRecipeString(blockMenu,READING_SLOT);
            AddUtils.displayCopyString(player,"单击此处拷贝recipe字符串","点击复制到剪贴板",recipeString);
            return false;
        }));
        if(blockMenu.getItemInSlot(CHOOSE_SLOT)==null){
            blockMenu.replaceExistingItem(CHOOSE_SLOT,CHOOSE_ITEM[0]);
        }
        blockMenu.addMenuClickHandler(CHOOSE_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=blockMenu.getLocation();
            int t= DataCache.getLastRecipe(loc);
            if(t>=CHOOSE_ITEM.length-1){
                t=0;
            }else {
                ++t;
            }
            blockMenu.replaceExistingItem(CHOOSE_SLOT,CHOOSE_ITEM[t]);
            DataCache.setLastRecipe(loc,t);
            return false;
        }));
        if(blockMenu.getItemInSlot(TOGGLE_RECIPE)==null){
            blockMenu.replaceExistingItem(TOGGLE_RECIPE,DEFAULT_RECIPE);
        }
        blockMenu.addMenuClickHandler(TOGGLE_RECIPE,((player, i, itemStack, clickAction) -> {

            if (itemStack!=null&&itemStack.getType()==Material.RED_STAINED_GLASS_PANE) {
                blockMenu.replaceExistingItem(TOGGLE_RECIPE,USE_RECIPE);
            }else{
                blockMenu.replaceExistingItem(TOGGLE_RECIPE,DEFAULT_RECIPE);
            }
            return false;
        }));
        blockMenu.addMenuClickHandler(ID_SLOT,((player, i, itemStack, clickAction) -> {
            AddUtils.sendMessage(player,"&e请输入新物品的slimefunID");
            player.closeInventory();
            AddUtils.asyncWaitPlayerInput(player,(msg)->{
                //displayCopyString(player,"点击拷贝语言配置文件框架","点击复制到剪贴板",LANGUAGE_CODE.formatted(msg));
                setRecordId(blockMenu.getLocation(),msg);
                blockMenu.open(player);
            });
            return false;
        }));
        blockMenu.addMenuClickHandler(LANGUAGE_SLOT,((player, i, itemStack, clickAction) -> {
            AddUtils.displayCopyString(player,"点击拷贝语言配置文件框架","点击复制到剪贴板",LANGUAGE_CODE.formatted(getRecordId(blockMenu.getLocation())));
            return false;
        }));
        blockMenu.addMenuClickHandler(ADDITEM_SLOT,((player, i, itemStack, clickAction) -> {
            int index= DataCache.getLastRecipe(blockMenu.getLocation());
            if(index<0||index>=CHOOSE_ITEM.length){
                index=0;
                DataCache.setLastRecipe(blockMenu.getLocation(),index);
            }
            String id=getRecordId(blockMenu.getLocation());
            ItemStack it=blockMenu.getItemInSlot(RESULT_SLOT);
            String material;
            if(it!=null){
                material=it.getType().toString();
            }else{
                material="null";
            }
            AddUtils.displayCopyString(player,"点击拷贝物品注册代码","点击复制到剪贴板",ADDITEM_CODE[index].formatted(id,id,material,id,id));
            return false;
        }));
        blockMenu.addMenuClickHandler(ADDSFITEM_SLOT,((player, i, itemStack, clickAction) -> {
            int index= DataCache.getLastRecipe(blockMenu.getLocation());
            if(index<0||index>=CHOOSE_ITEM.length){
                index=0;
                DataCache.setLastRecipe(blockMenu.getLocation(),index);
            }
            String id=getRecordId(blockMenu.getLocation());
            String recipe;
            if(blockMenu.getItemInSlot(TOGGLE_RECIPE).getType()!=Material.GREEN_STAINED_GLASS_PANE){
                recipe=DEFAULT_RECIPE_CODE;
            }else{
                recipe=getRecipeString(blockMenu,READING_SLOT);
            }
            AddUtils.displayCopyString(player,"点击拷贝物品注册代码","点击复制到剪贴板",ADDSFITEM_CODE[index].formatted(id,id,recipe));
            return false;
        }));
    }
    public String getRecordId(Location loc){
        try{
            return StorageCacheUtils.getData(loc,"id");
        }   catch (Throwable a){
            setRecordId(loc,"null");
            return "null";
        }
    }
    public void setRecordId(Location loc,String id){
        StorageCacheUtils.setData(loc,"id",id);
    }

}
