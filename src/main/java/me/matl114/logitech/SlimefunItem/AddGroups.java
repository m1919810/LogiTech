package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.MenuUtils;
import me.matl114.logitech.Utils.RecipeSupporter;
import me.matl114.logitech.Utils.UtilClass.MenuClass.CustomItemGroup;
import me.matl114.logitech.Utils.UtilClass.MenuClass.CustomMenu;
import me.matl114.logitech.Utils.UtilClass.MenuClass.DummyItemGroup;
import me.matl114.logitech.Utils.UtilClass.MenuClass.MenuFactory;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class AddGroups {
    public static void registerGroups(SlimefunAddon plugin){
        ROOT.register(plugin);
        MATERIAL.register(plugin);
    }
    // 给你的分类提供一个独一无二的ID
    protected static  final NamespacedKey itemGroupId = AddUtils.getNameKey("addon_category");

    public static final ItemGroup MATERIAL =new DummyItemGroup(AddUtils.getNameKey("material" ), AddItem.MATERIAL);

    public static final ItemGroup BASIC =new DummyItemGroup(AddUtils.getNameKey("basic" ), AddItem.BASIC);

    public static final ItemGroup CARGO = new DummyItemGroup(AddUtils.getNameKey("cargo" ), AddItem.CARGO);

    public static final ItemGroup SINGULARITY= new DummyItemGroup(AddUtils.getNameKey("singularity"),AddItem.SINGULARITY);

    public static final ItemGroup ADVANCED= new DummyItemGroup(AddUtils.getNameKey("advanced" ), AddItem.ADVANCED);

    public static final ItemGroup BEYOND= new DummyItemGroup(AddUtils.getNameKey("beyond" ), AddItem.BEYOND);

    public static final ItemGroup VANILLA= new DummyItemGroup(AddUtils.getNameKey("vanilla" ), AddItem.VANILLA);

    public static final ItemGroup MANUAL = new DummyItemGroup(AddUtils.getNameKey("manual" ), AddItem.MANUAL);

    public static final ItemGroup SPECIAL = new DummyItemGroup(AddUtils.getNameKey("special" ), AddItem.SPECIAL);

    public static final ItemGroup SPACE = new DummyItemGroup(AddUtils.getNameKey("space"),AddItem.SPACE);

    public static final ItemGroup GENERATORS = new DummyItemGroup(AddUtils.getNameKey("generators"),AddItem.GENERATORS);

    public static final ItemGroup ENERGY= new DummyItemGroup(AddUtils.getNameKey("energy"),AddItem.ENERGY);

    public static final ItemGroup FUNCTIONAL=new DummyItemGroup(AddUtils.getNameKey("functional"),AddItem.FUNCTIONAL);

    public static final ItemGroup INFO =new CustomItemGroup(AddUtils.getNameKey("info"),null, AddItem.INFO,54,36,new LinkedHashMap<>()) {
        @Override
        protected void init(MenuFactory factory) {
            factory.addInventory(1,AddItem.INFO1);
            factory.addInventory(11,AddItem.INFO2);
            factory.addInventory(21,AddItem.INFO3);
            factory.addInventory(7,AddItem.INFO4);
            factory.addInventory(15,AddItem.INFO5);
            factory.addInventory(23,AddItem.INFO6);
            factory.addInventory(13,AddItem.URL,(Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                final TextComponent link = new TextComponent("单击此处访问Github");
                link.setColor(ChatColor.YELLOW);
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/m1919810/LogiTech"));
                player1.spigot().sendMessage(link);
                return false;
            });
            factory.addInventory(27,AddItem.FEAT1);
            factory.addInventory(28,AddItem.FEAT2);
            factory.addInventory(29,AddItem.FEAT3);
            factory.addInventory(30,AddItem.FEAT4);
            factory.addInventory(31,AddItem.FEAT5);
            factory.addInventory(32,AddItem.FEAT6);
            factory.addInventory(33,AddItem.FEAT7);
            factory.addInventory(34,AddItem.FEAT8);
            factory.addInventory(35,AddItem.FEAT9);
            factory.addOverrides(4,AddItem.MATL114);
        }
        public void addGuideRelated(CustomMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int page){

        }
        @Override
        protected void addGuideRelated(ChestMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int pages) {
            if(AddUtils.standardRandom()<0.666){
                int rand=AddUtils.random(36);
                if(menu.getItemInSlot(rand+9)==null){
                    menu.replaceExistingItem(rand+9,AddItem.BUG);
                    menu.addMenuClickHandler(rand+9,((player, i, itemStack, clickAction) -> {
                        AddUtils.forceGive(player,AddItem.BUG,1);
                        menu.replaceExistingItem(rand+9,null);
                        menu.addMenuClickHandler(rand+9,ChestMenuUtils.getEmptyClickHandler());
                        return false;
                    }));
                }
            }
        }
    };
    //
    public static final ItemGroup ROOT=new CustomItemGroup(itemGroupId,AddUtils.colorful(AddUtils.ADDON_NAME), AddItem.ROOT,54,108,
                new LinkedHashMap<>(){{
                    put(6,MATERIAL);
                    put(0,INFO);
                    //put(8,ALLBIGRECIPES);
                    put(10,BASIC);
                    put(12,MANUAL);
                    put(14,ENERGY);
                    put(16,GENERATORS);
                    put(18, SPACE);
                    put(20,VANILLA);
                    put(22,SPECIAL);
                    put(24,CARGO);
                    put(26,ADVANCED);
                    put(28,SINGULARITY);
                    put(31,BEYOND);
                }}
            ){
        public MenuFactory MACHINEMENU=null;
        public MenuFactory RECIPEMENU=null;
        public MenuFactory BUGCRAFTER=null;
        public void initCustomMenus(){
            MACHINEMENU=MenuUtils.createMachineListDisplay(RecipeSupporter.MACHINE_RECIPELIST.keySet().stream().toList(),null).setBack(1);
            RECIPEMENU=MenuUtils.createRecipeTypeDisplay(RecipeSupporter.RECIPE_TYPES.stream().toList(),null).setBack(1);
            BUGCRAFTER=MenuUtils.createMRecipeListDisplay(AddItem.BUG_CRAFTER,RecipeSupporter.PROVIDED_SHAPED_RECIPES.get(BugCrafter.TYPE),
                    null,MenuUtils::createMRecipeDisplay);
        }
        public MenuFactory getMachineMenu(){
            if(MACHINEMENU==null){
                initCustomMenus();
            }
            return MACHINEMENU;
        }
        public MenuFactory getRecipeMenu(){
            if(RECIPEMENU==null){
                initCustomMenus();
            }
            return RECIPEMENU;
        }
        public MenuFactory getBugCrafterMenu(){
            if(BUGCRAFTER==null){
                initCustomMenus();
            }
            return BUGCRAFTER;
        }
        //used to set common handlers and common params
        public void init(MenuFactory factory){
            //对模板进行最高级别的覆写
            factory.addOverrides(4,AddItem.URL,(Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                final TextComponent link = new TextComponent("单击此处访问Github");
                link.setColor(ChatColor.YELLOW);
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/m1919810/LogiTech"));
                player1.spigot().sendMessage(link);
                return false;
            });
            //对模板内的填充物进行修改
            factory.addInventory(2,AddItem.ALLMACHINE);
            factory.addInventory(4,AddItem.ALLRECIPE);
            factory.addInventory(8,AddItem.ALLBIGRECIPES);
            factory.addInventory(70,new ItemStack(Material.COMMAND_BLOCK));
            factory.addInventory(34,AddItem.TOBECONTINUE);
            SchedulePostRegister.addPostRegisterTask(this::initCustomMenus);
            //对自动生成的物品组位置进行修改(如果使用hashmap指定物品组则不用修改
        }
        //used to set GUIDE based handlers,an interface to adapt CustomMenu menus
        public void addGuideRelated(ChestMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int page){
            //加入实例化之后的handler和item,同打开玩家等数据有关的handler
            if(page==1){
                menu.addMenuClickHandler(13 ,((Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                    getRecipeMenu().build().setBackHandler(((player, i, itemStack, clickAction1) -> {
                        this.openPage(player1,profile,mode,page);
                        return false;
                    })).open(player1);
                    return false;
                }));
                menu.addMenuClickHandler(11,((Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                    getMachineMenu().build().setBackHandler(((player, i, itemStack, clickAction1) -> {
                        this.openPage(player1,profile,mode,page);
                        return false;
                    })).open(player1);
                    return false;
                }));
                menu.addMenuClickHandler(17,(((player1, i1, itemStack1, clickAction) -> {
                    getBugCrafterMenu().build().setBackHandler(((player, i, itemStack, clickAction1) -> {
                        this.openPage(player1,profile,mode,page);
                        return false;
                    })).open(player1);
                    return false;
                })));
            }

        }
    };
}

