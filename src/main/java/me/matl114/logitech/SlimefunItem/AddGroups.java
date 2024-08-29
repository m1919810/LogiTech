package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.chat.ChatInput;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Items.CustomHead;
import me.matl114.logitech.Schedule.PersistentEffects.AbstractEffect;
import me.matl114.logitech.Schedule.PersistentEffects.CustomEffects;
import me.matl114.logitech.Schedule.PersistentEffects.PlayerEffects;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.SlimefunItem.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.MenuClass.*;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AddGroups {
    public static void registerGroups(SlimefunAddon plugin){
        ROOT.register(plugin);
        INFO.register(plugin);
        MATERIAL.register(plugin);
        BASIC.register(plugin);
        CARGO.register(plugin);
        SINGULARITY.register(plugin);
        ADVANCED.register(plugin);
        BEYOND.register(plugin);
        VANILLA.register(plugin);
        MANUAL.register(plugin);
        SPECIAL.register(plugin);
        SPACE.register(plugin);
        GENERATORS.register(plugin);
        ENERGY.register(plugin);
        FUNCTIONAL.register(plugin);
    }
    // 给你的分类提供一个独一无二的ID
    protected static  final NamespacedKey itemGroupId = AddUtils.getNameKey("addon_category");
    protected static final NamespacedKey bugcrafterId = AddUtils.getNameKey("bugcrafter");

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
    public static ItemGroup UPDATELOG=new CustomItemGroup(AddUtils.getNameKey("log"),AddUtils.colorful("更新日志"),AddItem.UPDATELOG,54,36,new LinkedHashMap<>()) {
        @Override
        protected void init(MenuFactory factory) {
            factory.addInventory(0,AddUtils.getInfoShow("&f更新日志","&7此页为简易的更新日志","&7你或许可以找到你想要的新特性"));
            factory.addInventory(1,AddUtils.getInfoShow("&f2024.8.28",
                    "&7创建日志",
                    "&7创建新的终极机器用于测试",
                    "&7大型配方展示组增加历史记录",
                    "&7修复模拟合成台不显示黑名单的bug",
                    "&7修复原版配方读取,将矿辞读为等价物品组")
            );
            factory.addInventory(2,AddUtils.getInfoShow("&f2024.8.29",
                    "&7修改部分描述"
                    )
            );
        }

        @Override
        protected void addGuideRelated(ChestMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int pages) {

        }
    };
    public static ItemGroup BUGCRAFTER=new CustomItemGroup(bugcrafterId,AddUtils.colorful(AddUtils.ADDON_NAME),AddItem.ALLBIGRECIPES,54,36,new LinkedHashMap<>()) {
        public PlayerHistoryRecord<CustomMenu> historyHandler=new PlayerHistoryRecord<CustomMenu>() {
            HashMap<UUID,List<CustomMenu>> records=new HashMap<>();
            public  CustomMenu getRecord(Player player){
                UUID uuid=player.getUniqueId();
                synchronized(records){
                    List<CustomMenu> list=records.get(uuid);
                    if(list==null){
                        list=new ArrayList<CustomMenu>();
                    }
                    if(list.isEmpty()){
                        return null;
                    }
                    return list.get(list.size()-1);
                }
            }
            public void addRecord(Player player, CustomMenu record){
                UUID uuid=player.getUniqueId();
                synchronized(records){
                    List<CustomMenu> list=records.get(uuid);
                    if(list==null){
                        list=new ArrayList<>();
                        records.put(uuid,list);
                    }
                    list.add(record);

                }
            }
            public CustomMenu deleteRecord(Player player,CustomMenu record){
                UUID uuid=player.getUniqueId();
                synchronized(records){
                    List<CustomMenu> list=records.get(uuid);
                    if(list==null||list.isEmpty()){
                        return null;
                    }
                    return list.remove(list.size()-1);
                }
            }
        };
        MenuFactory subRecipes=null;
        public void initCustomMenus(){
            subRecipes=MenuUtils.createMRecipeListDisplay(AddItem.BUG_CRAFTER,RecipeSupporter.PROVIDED_SHAPED_RECIPES.get(BugCrafter.TYPE),
                    null,historyHandler,MenuUtils::createMRecipeDisplay);
        }
        public MenuFactory getSubRecipes() {
            if(subRecipes==null){
                initCustomMenus();
            }
            return subRecipes;
        }
        protected void init(MenuFactory factory) {
            ItemStack menuBackGround=new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE,"");
            factory.addOverrides(4,AddItem.URL,(Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                final TextComponent link = new TextComponent("单击此处访问Github");
                link.setColor(ChatColor.YELLOW);
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/m1919810/LogiTech"));
                player1.spigot().sendMessage(link);
                return false;
            });
            factory.setBackGround(menuBackGround);
            SchedulePostRegister.addPostRegisterTask(this::initCustomMenus);
        }
        public void openPage(Player var1, PlayerProfile var2, SlimefunGuideMode var3,int page){

            var2.getGuideHistory().add(this,1);
            CustomMenu menu= historyHandler.getRecord(var1);
            if(menu!=null){
                ChestMenu historyMenu=menu.constructPage(-1);
                historyMenu.open(var1);
                return;
            }
            CustomMenu menu2=getSubRecipes().build();
            menu2.setBackSlot(1);
            menu2.setBackHandler(((player, i, itemStack, clickAction) -> {
                var2.getGuideHistory().goBack(Slimefun.getRegistry().getSlimefunGuide(var3));
                return false;
            }));
            menu2.openPages(var1,1);
        }
        @Override
        protected void addGuideRelated(ChestMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int pages) {

        }
    };

    public static final ItemGroup INFO =new CustomItemGroup(AddUtils.getNameKey("info"),null, AddItem.INFO,54,36,new LinkedHashMap<>()) {
        @Override
        protected void init(MenuFactory factory) {
            this.setVisble(false);
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
    public static final ItemGroup ROOT=new CustomItemGroup(itemGroupId,AddUtils.colorful(AddUtils.ADDON_NAME), AddItem.ROOT,54,108,
                new LinkedHashMap<>(){{
                    put(2,MATERIAL);
                    put(0,INFO);
                    put(27,BUGCRAFTER);
                    put(4,BASIC);
                    put(3,MANUAL);
                    put(6,ENERGY);
                    put(5,GENERATORS);
                    put(22, SPACE);
                    put(21,VANILLA);
                    put(8,SPECIAL);
                    put(20,CARGO);
                    put(23,ADVANCED);
                    put(24,SINGULARITY);
                    put(31,BEYOND);
                    put(36,UPDATELOG);
                }}
            ){
        public MenuFactory MACHINEMENU=null;
        public MenuFactory RECIPEMENU=null;
        public void initCustomMenus(){
            MACHINEMENU=MenuUtils.createMachineListDisplay(RecipeSupporter.MACHINE_RECIPELIST.keySet().stream().toList(),null).setBack(1);
            RECIPEMENU=MenuUtils.createRecipeTypeDisplay(RecipeSupporter.RECIPE_TYPES.stream().toList(),null).setBack(1);
//            BUGCRAFTER=MenuUtils.createMRecipeListDisplay(AddItem.BUG_CRAFTER,RecipeSupporter.PROVIDED_SHAPED_RECIPES.get(BugCrafter.TYPE),
//                    null,MenuUtils::createMRecipeDisplay);
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
//        public MenuFactory getBugCrafterMenu(){
//            if(BUGCRAFTER==null){
//                initCustomMenus();
//            }
//            return BUGCRAFTER;
//        }
        //used to set common handlers and common params
        public void init(MenuFactory factory){
            this.setVisble(true);
            //对模板进行最高级别的覆写
            ItemStack menuBackGround=new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE,"");
            factory.addOverrides(4,AddItem.URL,(Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                final TextComponent link = new TextComponent("单击此处访问Github");
                link.setColor(ChatColor.YELLOW);
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/m1919810/LogiTech"));
                player1.spigot().sendMessage(link);
                return false;
            });
            factory.setBackGround(menuBackGround);
            final int[] BORDER=new int[]{
                    1,7,10,11,12,13,14,15,16,19,25,28,34
            };
            int len=BORDER.length;
            for (int i=0;i<len;++i){
                factory.addInventory(BORDER[i],menuBackGround);
            }
            //对模板内的填充物进行修改
            factory.addInventory(9,AddItem.ALLMACHINE);
            factory.addInventory(18,AddItem.ALLRECIPE);
            factory.addInventory(27,AddItem.ALLBIGRECIPES);
            factory.addInventory(35,AddItem.TOBECONTINUE);
            factory.addInventory(72,new ItemStack(Material.COMMAND_BLOCK));
            SchedulePostRegister.addPostRegisterTask(this::initCustomMenus);
            //对自动生成的物品组位置进行修改(如果使用hashmap指定物品组则不用修改
            //彩蛋
            ItemStack info1=new CustomItemStack(Material.CHERRY_HANGING_SIGN,AddUtils.color("请选择你的英雄"),"&8可不要走错方向哦~");
            ItemStack h1=new CustomItemStack(CustomHead.getHead("fd524332cdb381c9e51f77d4cec9bc6d4d1c5bdec1499d206d8383e9176bdfb0"),AddUtils.color("haiman"),"&7点击查看详情");
            ItemStack h2=new CustomItemStack(CustomHead.getHead("a9b046531a6182de634d6fed1f3b4f885ee99bfe2bc0c1684f7b97d396c2059f"),AddUtils.color("mmmjjkx"),"&7点击查看详情");
            ItemStack h3=new CustomItemStack(CustomHead.getHead("7e224b7fb1e9dc78b2abb6a3fbf726b1d7159d08599b2c02b7bf1ad396285da6"),AddUtils.color("tinalness"),"&7点击查看详情");
            ItemStack h4=new CustomItemStack(CustomHead.SUPPORT.getItem(),AddUtils.color("HgTlPbBi"),"&7点击查看详情");
            ItemStack h5=new CustomItemStack(CustomHead.getHead("8e434215b5616bf37dccbacdb240bd16de59507e62a5371ceca80327b398e65"),AddUtils.color("Tmatsuki_rui"),"&7点击查看详情");
            ItemStack h6=new CustomItemStack(AddItem.MATL114,"  ","&8..?");


            factory.addInventory(40,info1);
            factory.addInventory(56, h1,(cm)-> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){has=true;itemStack.setItemMeta(s1);}
                    return false;
                }
            });
            factory.addInventory(57, h2,(cm)-> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){has=true;itemStack.setItemMeta(s2);}
                    return false;
                }
            });
            factory.addInventory(58, h3,(cm)-> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){has=true;itemStack.setItemMeta(s3);}
                    return false;
                }
            });
            factory.addInventory(59, h4,(cm)-> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){has=true;itemStack.setItemMeta(s4);}
                    return false;
                }
            });
            factory.addInventory(60, h5,(cm)-> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){
                        has=true;itemStack.setItemMeta(s5);
                        AddUtils.sendMessage(player,"&eTmatsuki_rui就是纯纯的____");
                        if(AddUtils.standardRandom()<0.3){
                            AddUtils.forceGive(player,head.clone(),AddUtils.random(4));
                        }
                        PlayerEffects.grantEffect(CustomEffects.WRONG_BUTTON,player,1,1);
                    }
                    return false;
                }
            });
            factory.addInventory(63, h6,(cm)-> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){has=true;itemStack.setItemMeta(s6);}
                    return false;
                }
            });
            this.s7metas=new ItemMeta[6];
            String continueStr="&7点击继续";
            ItemStack it=new CustomItemStack(AddItem.MATL114,AddUtils.color("你觉得..."),continueStr);
            factory.addInventory(71,it,(cm)->new ChestMenu.MenuClickHandler() {
                int index=0;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(itemStack!=null)
                        itemStack.setItemMeta(s7metas[index]);
                    index=(index+1)%5;
                    return false;
                }
            });
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("我能在患有痴呆症的情况下完成逻辑工艺的编写吗?"),continueStr);
            s7metas[0]=it.getItemMeta();
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("这能被完成么,这可能完成么?"),continueStr);
            s7metas[1]=it.getItemMeta();
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("我知道这有点难"),continueStr);
            s7metas[2]=it.getItemMeta();
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("所以,我想"),continueStr);
            s7metas[3]=it.getItemMeta();
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("今年我要进行一项从未有人达成过的挑战"),continueStr);
            s7metas[4]=it.getItemMeta();
        }
        final ItemMeta s1=new CustomItemStack(CustomHead.getHead("fd524332cdb381c9e51f77d4cec9bc6d4d1c5bdec1499d206d8383e9176bdfb0"),AddUtils.color("haiman"),"&7haiman科技作者","&7海曼科技会为你提供足够的物质支持").getItemMeta();
        final ItemMeta s2=new CustomItemStack(CustomHead.getHead("a9b046531a6182de634d6fed1f3b4f885ee99bfe2bc0c1684f7b97d396c2059f"),AddUtils.color("mmmjjkx"),"&7rsc开发者","&3纯大蛇\uD83D\uDE0B").getItemMeta();
        final ItemMeta s3=new CustomItemStack(Material.REPEATING_COMMAND_BLOCK,AddUtils.color("tinalness"),"&7大香蕉的本质是命令方块\uD83D\uDE0B(确信","&7但是你或许会需要他的网络拓展").getItemMeta();
        final ItemMeta s4=new CustomItemStack(CustomHead.SUPPORT.getItem(),AddUtils.color("HgTlPbBi"),"&7逻辑工艺的支持者","&7提出了很多有用的点子").getItemMeta();
        final ItemMeta s5=new CustomItemStack(CustomHead.getHead("8e434215b5616bf37dccbacdb240bd16de59507e62a5371ceca80327b398e65"),AddUtils.color("Tmatsuki_rui"),"&7凉城服的祸源","&7纯傻逼,给爷死啊\uD83D\uDE21").getItemMeta();
        final ItemMeta s6=new CustomItemStack(CustomHead.BUSHIGEMEN.getItem(),"  ","&7看得出你的视力很好","&7所以一定要仔细阅读","&7版本说明与机器说明哦").getItemMeta();
        final ItemStack head=new CustomItemStack(Material.PLAYER_HEAD,AddUtils.color("逝者的头颅"));
        ItemMeta[] s7metas;
        //used to set GUIDE based handlers,an interface to adapt CustomMenu menus
        public void addGuideRelated(ChestMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int page){
            //加入实例化之后的handler和item,同打开玩家等数据有关的handler
            if(page==1){
                menu.addMenuClickHandler(27 ,((Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                    getRecipeMenu().build().setBackHandler(((player, i, itemStack, clickAction1) -> {
                        this.openPage(player1,profile,mode,page);
                        return false;
                    })).open(player1);
                    return false;
                }));
                menu.addMenuClickHandler(18,((Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                    getMachineMenu().build().setBackHandler(((player, i, itemStack, clickAction1) -> {
                        this.openPage(player1,profile,mode,page);
                        return false;
                    })).open(player1);
                    return false;
                }));
            }else if(page==2){

            }
        }
    };



}

