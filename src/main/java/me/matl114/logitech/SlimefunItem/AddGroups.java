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
import me.matl114.logitech.Listeners.Listeners.PlayerQuiteListener;
import me.matl114.logitech.Schedule.PersistentEffects.AbstractEffect;
import me.matl114.logitech.Schedule.PersistentEffects.CustomEffects;
import me.matl114.logitech.Schedule.PersistentEffects.PlayerEffects;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.Schedule.Schedules;
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
                    "&7修改部分描述",
                    "&7修复了自定义菜单历史记录bug",
                    "&7平衡性调整",
                    "&7修改了等价物品组配置的读取方式",
                    "&7修改了语言文件读取方式,并研发了自动提取字符串脚本",
                    "&7研发出乱码科技的英文版\"乱翻科技\"",
                    "&7我没有痴呆症"
                    )
            );
            factory.addInventory(3,AddUtils.getInfoShow("&f2024.8.30",
                            "&7进行平衡性调整",
                    "&7修复分拣机与糟糕sft导致的冲突",
                    "&7新增若干货运机器与其配方",
                    "&7遇见了芯片最优解",
                    "&7与玩家交流,获得反馈",
                    "&7出现了手残破坏多方块结构的玩家",
                    "&7大家一定要小心谨慎,少得痴呆症"
                    )
            );
            factory.addInventory(4,AddUtils.getInfoShow("&f&m2024.8.30 &r&f2024.8.31",
                            "&7增加了可调节的货运机器",
                            "&7开始研发逻辑奇点部分的机器",
                            "&7发现远古测试机器的bug",
                            "&7中午出门把身份证丢了,晦气",
                            "&7下午治疗身份证去了,没更新痴呆症",
                            "&7大家一定要小心谨慎,少得身份症",
                             "&7晚上和haiman整了SlimefunEssentials",
                            "&7JEI十分的强劲,我觉得我都不用写搜索了"
                    )
            );
            factory.addInventory(5,AddUtils.getInfoShow("&f2024.9.1",
                            "&7修复薯片核心配方无法原版自动化",
                    "&7到处转转,学会怎么上传头颅",
                    "&7完成终极机制之远程接入存储,但是出现了小的bug",
                    "&7修一个小bug 花费了我好久。实际上这是我算法架构的问题",
                    "&7但是我还是尝试了若干方案之后,最终决定这么搞,希望不会炸",
                    "&7经过了亿点重构,准备开始测试了,希望人没事"
                    )
            );
            factory.addInventory(6,AddUtils.getInfoShow("&f2024.9.1",
                            "&7今天是几号来着",
                    "&7方案全部无效",
                    "&7新特性不能就这么寄了",
                    "&7躺下睡觉吧还是"
                    )
            );
            factory.addInventory(7,AddUtils.getInfoShow("&f2024.9.3",
                            "&7哈哈哈哈哈哈哈哈",
                    "&7终于想到一个方法了",
                    "&7既然他提供超过两个引用会寄,那我就要根据menu和slots为单位控制产生的引用数",
                    "&7不要再对引用位置做无所谓的控制,这没有必要",
                    "&7只要将cache生成器包装入一个方法 然后在方法里狠狠注入检测代码就可以了!",
                    "&7我他娘的还真是小机灵鬼",
                    "&7开干",
                    "&7终于,,,修好了!"
                    )
            );
            factory.addInventory(8,AddUtils.getInfoShow("&f2024.9.3",
                            "&7我一定是记错日期了,不然为什么一觉醒来日期没变呢",
                    "&7继续完善存储链接体系",
                    "&7应广大玩家要求,修改逻辑反应堆为运行中不销耗布尔组件,只在搜索阶段消耗",
                    "&7发现配方搜素算法一个cache未及时同步的bug",
                    "&7好在在新测试员的帮助下很快解决.",
                    "&7比Tmatsuki_rui能干多了."
                    )
            );
            factory.addInventory(9,AddUtils.getInfoShow("&f2024.9.4",
                            "&7一不小心忘了写了",
                    "&7还能写啥呢",
                    "&7修bug,完善终极科技线",
                    "&7修复超新星不同步bug",
                    "&7修复了存储缓存生成后dirty位错误的bug"
                    )
            );
            factory.addInventory(10,AddUtils.getInfoShow("&f2024.9.5",
                            "&7是时候给附属加一点强力的生产机器了",
                    "&7不然都没人玩,成本太高了",
                    "&7开发了多线程机器,虽然就是复制粘贴",
                    "&7研究一下同化方块机制",
                    "&7尝试优化push方法"
                    )
            );
            factory.addInventory(11,AddUtils.getInfoShow("&f2024.9.6",
                            "&7整了一些机器 想必玩家们会喜欢",
                    "&7开发了一些数值比较好看的机器",
                    "&7希望是这样的",
                    "&7"
                    )
            );
            factory.addInventory(12,AddUtils.getInfoShow("&f2024.9.7",
                            "&7整了一些机器 想必玩家们会喜欢",
                            "&7整了一款全新的高端多方块",
                            "&7包括两个等级和一些激发机制",
                            "&7我觉得那真是太酷啦"
                    )
            );
            factory.addInventory(13,AddUtils.getInfoShow("&f2024.9.7",
                            "&7开发了LogiTech shell",
                            "&7一款基于原神(bushi)的shell命令行工具",
                            "&7可以查询和修改类/对象的成员等等,支持设置变量",
                            "&7紧急修复了因dataload导致的卡服",
                            "&7这就是依托"
                    )
            );
            factory.addInventory(14,AddUtils.getInfoShow("&f2024.9.9",
                            "&7开学了",
                    "&7开学第一天没课",
                    "&7兼容构思rsc生成器随机输出",
                    "&7我希望开启一个计划,让大家一起完善machine堆叠列表"
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
            {
                PlayerQuiteListener.addHandler((playerQuiteListener)->{
                    UUID uid=playerQuiteListener.getPlayer().getUniqueId();
                    synchronized(records) {
                        records.remove(uid);
                    }
                });
            }
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
            for(int s=0;s<2;++s){
                int rand=AddUtils.random(36);
                if(menu.getItemInSlot(rand+9)==null){
                    menu.replaceExistingItem(rand+9,AddItem.BUG);
                    menu.addMenuClickHandler(rand+9,((player, i, itemStack, clickAction) -> {
                        AddUtils.forceGive(player,AddItem.BUG,1);
                        menu.replaceExistingItem(rand+9,null);
                        menu.addMenuClickHandler(rand+9,ChestMenuUtils.getEmptyClickHandler());
                        return false;
                    }));
                    break;
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
            ItemStack h4=new CustomItemStack(CustomHead.SUPPORTER2.getItem(),AddUtils.color("HgTlPbBi"),"&7点击查看详情");
            ItemStack h5=new CustomItemStack(CustomHead.getHead("8e434215b5616bf37dccbacdb240bd16de59507e62a5371ceca80327b398e65"),AddUtils.color("Tmatsuki_rui"),"&7点击查看详情");
            ItemStack h6=new CustomItemStack(AddItem.MATL114,"  ","&8..?");
            ItemStack h8=new CustomItemStack(CustomHead.BIG_SNAKE.getItem(),AddUtils.color("某不知名大蛇"),"&7年轻人,你想刷物么");

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
                    if(!has&&itemStack!=null){has=true;
                        itemStack.setType(Material.REPEATING_COMMAND_BLOCK);
                        itemStack.setItemMeta(s3);}
                    return false;
                }
            });
            factory.addInventory(59, h4,(cm)-> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){
                        has=true;
                        itemStack.setItemMeta(s4);}
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
            factory.addInventory(67, h8,(cm)-> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){has=true;
                        itemStack.setItemMeta(s8);
                        Schedules.launchSchedules(()->{
                            PlayerEffects.grantEffect(CustomEffects.WRONG_BUTTON,player,2,1);
                        },100,true,0);
                    }
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
        final ItemMeta s4=new CustomItemStack(CustomHead.SUPPORTER2.getItem(),AddUtils.color("HgTlPbBi"),"&7逻辑工艺的支持者","&7提出了很多有用的点子").getItemMeta();
        final ItemMeta s5=new CustomItemStack(CustomHead.getHead("8e434215b5616bf37dccbacdb240bd16de59507e62a5371ceca80327b398e65"),AddUtils.color("Tmatsuki_rui"),"&7凉城服的祸源","&7纯傻逼,给爷死啊\uD83D\uDE21").getItemMeta();
        final ItemMeta s6=new CustomItemStack(CustomHead.BUSHIGEMEN.getItem(),"  ","&7看得出你的视力很好","&7所以一定要仔细阅读","&7版本说明与机器说明哦").getItemMeta();
        final ItemStack head=new CustomItemStack(Material.PLAYER_HEAD,AddUtils.color("逝者的头颅"));
        final ItemMeta s8=new CustomItemStack(CustomHead.BIG_SNAKE.getItem(),AddUtils.color("taitaia"),"&7刷物?你在想屁吃\uD83D\uDE0B").getItemMeta();
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

