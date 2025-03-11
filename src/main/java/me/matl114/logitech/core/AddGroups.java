package me.matl114.logitech.core;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.utils.UtilClass.SpecialItemClass.CustomHead;
import me.matl114.logitech.core.Registries.CustomEffects;
import me.matl114.logitech.utils.UtilClass.EffectClass.PlayerEffects;
import me.matl114.logitech.manager.PostSetupTasks;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.core.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.MenuClass.*;
import me.matl114.logitech.core.Registries.RecipeSupporter;
import me.matl114.matlib.Implements.Slimefun.Menu.MenuClickHandler.GuideClickHandler;
import me.matl114.matlib.Implements.Slimefun.Menu.MenuGroup.CustomMenuGroup;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.IntStream;

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
        TOOLS_FUNCTIONAL.register(plugin);
        TOOLS_SUBGROUP_1.register(plugin);
        TOOLS.register(plugin);
        ROOT.setTier(0);
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

    public static final ItemGroup TOOLS_FUNCTIONAL =new DummyItemGroup(AddUtils.getNameKey("tools-functional"),AddItem.TOOLS){
        @Override
        public void add(@Nonnull SlimefunItem item) {
            super.add(item);
        }

        @Override
        public void remove(@Nonnull SlimefunItem item) {
            super.remove(item);
        }
    };
    public static final ItemGroup TOOLS_SUBGROUP_1 = new DummyItemGroup(AddUtils.getNameKey("tools-sub1"),AddItem.TOOLS_SUBGROUP_1);
    public static final ItemGroup TOOLS_SUBGROUP_2 = new DummyItemGroup(AddUtils.getNameKey("tools-sub2"),AddItem.TOOLS_SUBGROUP_2);


    public static final ItemGroup FUNCTIONAL=new DummyItemGroup(AddUtils.getNameKey("functional"),AddItem.FUNCTIONAL);



    public static ItemGroup MORE2=new CustomItemGroup(AddUtils.getNameKey("more2"),AddUtils.color("宇宙安全说明"),AddItem.MORE2,54,36,new LinkedHashMap<>()) {
        @Override
        protected void init(MenuFactory menuFactory) {
            menuFactory.addInventory(0,AddUtils.getInfoShow("&f宇宙安全说明",
                    "&7感谢您能点开到这里,看到这份说明",
                    "&7本附属为我开发的首个附属,无人问津是应该的",
                    "&7您能点进这一分类已经是对我最大的鼓励和支持",
                    "&7本人水平很次,也没有很好的创作能力创作让所有玩家满意的附属",
                    "&7还请包容"));
            menuFactory.addInventory(1,AddUtils.getInfoShow("&f宇宙安全说明",
                    "&7这个附属在某些方面可以说很烂",
                    "&7他拥有冗长的机制说明书和昂贵的合成表",
                    "&7以及作者乱填的,毫无道理的材料消耗",
                    "&7为什么呢",
                    "&7因为作者并不知道怎么编一个让玩家满意的合成表",
                    "&7很可能有些时候,在这方面给玩家带来很大的困扰"));
            menuFactory.addInventory(2,AddUtils.getInfoShow("&f宇宙安全说明",
                    "&7作者也不知道为什么作者要写这一个附属",
                    "&7作者已经投入很多闲暇时间进行附属编写",
                    "&7作者也&a希望这个附属能变得更好&7,而不是更让玩家恶心",
                    "&7作者&a希望附属对玩家有帮助,而不是对玩家造成困扰"
                    ));

            menuFactory.addInventory(3,AddUtils.getInfoShow("&f宇宙安全说明",
                    "&7这个附属还很糟糕,他没有一个所谓的\"主题\"",
                    "&7他既没有乱码科技的制造台,也没有乱序技艺的尘埃反应堆",
                    "&7作者一开始的目标是制作优秀的机器,虽然现在改为了制作高效的机器",
                    "&7但是并没有非常超标的机器存在",
                    "&7还请玩家包容",
                    "&7如果你只是想找op机器",
                    "&7那么本附属可能并不符合你的心意"));
            menuFactory.addInventory(4,AddUtils.getInfoShow("&f宇宙安全说明",
                    "&7作者能拿的出手的机制就这几个",
                    "&7虽说抱歉,但是作者也不会因此而做op机器和刷物机器",
                    "&a但是玩家有什么好的想法或者急切的机器需求",
                    "&a也是可以和作者提的",
                    "",
                    "&7作者真心希望该附属越做越好,而不是越来越烂",
                    "&a所以我在此恳请大家主动发表自己的看法"));
            menuFactory.addInventory(5,AddUtils.getInfoShow("&f宇宙安全说明",
                    "&7所以,作者在此恳请大家",
                    "&a多多包容作者,并把自己的想法跟作者讨论",
                    "&a你可以在版本与说明中找到作者的qq号和作者进行交流",
                    "&a也可以上附属github链接提出issue",
                    "&a不管如何,作者都会听取玩家的意见,争取将附属做得更好"));
            menuFactory.addInventory(6,AddUtils.getInfoShow("&f宇宙安全说明",
                    "&7虽说致歉,但是作者还是会&a坚定的继续开发和维护这个附属",
                    "&7虽然作者也曾想过放弃该附属",
                    "&7但是也只是一时之念,大家不要当真",
                    "&7请大家放心,作者并不会丢下一堆烂摊子跑路"));
            menuFactory.addInventory(7,AddUtils.getInfoShow("&f宇宙安全说明",
                    "&7最后,在此对&e所有觉得这个附属没救的玩家&7致辞","",
                    "&7粘液圈虽然不大,但是比本附属好的附属也是比比皆是",
                    "&7作者的生活中也不只是有粘液,作者也要有自己的生活,并不能帮你救活这个附属",
                    "&7您也不必拘泥于这个附属,我们好聚好散,对大家都好"));
            menuFactory.addInventory(8,AddUtils.getInfoShow("&f宇宙安全说明",
                    "&7最后声明:本说明并不针对某一特定玩家",
                    "&7当您对号入座时,请不要坐岔了,谢谢."
                    ));
        }

        @Override
        protected void addGuideRelated(ChestMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int pages) {

        }
    };
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
            factory.addInventory(15,AddUtils.getInfoShow("&f2024.9.12",
                            "&7增加了内置文件",
                    "&7增加了乱码的机器列表",
                    "&7恕我直言,起名真乱",
                    "&7还有ag科技,一个配方机器都找不到",
                    "&7纯懒狗附属"
                    )
            );
            factory.addInventory(16,AddUtils.getInfoShow("&f2024.9.27",
                    "&7显然 我忘了记录日志",
                    "&7这甜蜜确实挺糟糕的",
                    "&7不如之后我就写更新日志"
            ));
            factory.addInventory(18,AddUtils.getInfoShow("&f2024.9.27更新",
                    "&7修复存储交互接口潜在bug",
                    "&7修复堆叠机器列表导入重复bug",
                    "&7修复反概念物质扩散不会继承玩家权限bug",
                    "&7增加shell玩家权限检测",
                    "&7修复简易堆叠破烂的bug",
                    "&7增加星辰金熔炉的槽位推送限制",
                    "&7修复了未插入货运配置卡时code读取异常bug",
                    "&7还修了啥不太记得了",
                    "&7增加了烘干机到快捷综合机器",
                    "&7增加赤石科技道具",
                    "&7减少超频刷怪笼的合成需求",
                    "&7修复了堆叠机器无法支持非消耗物品的bug",
                    "&7准备增加分拣机",
                    "&7增加堆叠机器配置文件直接导入配方类型的功能",
                    "&7增加tnt复制机",
                    "&7增加破基岩棒",
                    "&7修复逻辑反应堆槽位提供异常",
                    ""));
            factory.addInventory(19,AddUtils.getInfoShow("&f2024.10.12更新",
                    "&7修复部分bug,虽然忘了修了啥了",
                    "&7增加了激光发射器 进阶版",
                    "&7增加桃花飞针,好吃",
                    "&7增加更多的赤石道具",
                    "&7增加了超级海绵",
                    "&7更新啥了忘了",
                    "&7更新了空间塔",
                    "&7发掘了moveSfData的真相",
                    "&7发掘了刷物特性",
                    "&7修复了大量潜在问题,并增加刷物提示",
                    "&7空间塔不再支持移动粘液机器",
                    "&7增加了随机传送符文",
                    "&7增加了物品命名机(物品操作台)",
                    "&7忘了",
                    "&7注册了只使用原版合成台合成的道具",
                    "&7记得找个时间补配方",
                    "&7补啥配方",
                    "&7修复了无尽矿机配方导入异常",
                    "&7修复了物品匹配异常",
                    "&7做好了区块充电器,按理论讲耗时可控,比能源电网要好",
                    "&7这次更新就先做到这里吧 剩下的放到下次更新",
                    "&7更新到了v1.0.3"
            ));
            factory.addInventory(20,AddUtils.getInfoShow("&f2024.?更新",
                    "&7修复部分bug,虽然忘了修了啥了",
                    "&7增加了wiki",
                    "&7好像修了点bug",
                    "&7重构部分代码,优化了多方块结构数据存储",
                    "&7限制了区块充能器",
                    "&7完善了一个较为完整的电力系统",
                    "&7完成了电线",
                    "&7修复了bug",
                    "&7加入了神金的小道具",
                    "&7加入了高级快捷机器,我感觉很好用",
                    "&7加入了货运管道,玩家一定会喜欢的",
                    "&7加入了对原版容器的货运支持",
                    "&7加入了好多其他的东西,忘了",
                    "&7加入了粘液替代卡",
                    "&7加入了配置器",
                    "&7加入了"
            ));
        }

        @Override
        protected void addGuideRelated(ChestMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int pages) {

        }
    };
    public static ItemGroup BUGCRAFTER=new CustomItemGroup(bugcrafterId,AddUtils.colorful(AddUtils.ADDON_NAME),AddItem.ALLBIGRECIPES,54,36,new LinkedHashMap<>()) {
        public PlayerHistoryRecord<CustomMenu> historyHandler=new SimplePlayerHistoryRecord<CustomMenu>();
        MenuFactory subRecipes=null;
        public void initCustomMenus(){
            subRecipes=MenuUtils.createItemListDisplay(BugCrafter.TYPE, RecipeSupporter.RECIPETYPE_TO_ITEMS.get(BugCrafter.TYPE),
                    null,historyHandler,MenuUtils::createMRecipeDisplay,true);
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
            PostSetupTasks.addPostRegisterTask(this::initCustomMenus);
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
                historyHandler.deleteAllRecords(player);
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
            factory.addInventory(13,AddItem.WIKI,(Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                final TextComponent link = new TextComponent("单击此处访问附属wiki");
                link.setColor(ChatColor.YELLOW);
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/m1919810/LogiTech/wiki"));
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
                    menu.replaceExistingItem(rand+9,AddUtils.getWithoutId( AddItem.BUG));
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

    public static final ItemGroup TOOLS=new me.matl114.matlib.Implements.Slimefun.Menu.GuideMenu.CustomItemGroup(AddUtils.getNameKey("tools"),AddItem.TOOLS,true)
            .setLoader(new CustomMenuGroup(AddItem.TOOLS.getItemMeta().getDisplayName(),54,1){{
                            //initialize
                            enablePresets((i)->{
                                ChestMenu menu=this.getDefaultGenerator().apply(i);
                                IntStream.range(0,9).forEach(t->menu.addItem(t,ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler()));
                                IntStream.range(45,54).forEach(t->menu.addItem(t,ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler()));
                                return menu;
                            }).setPageNextSlots(52).setPagePrevSlots(46)
                            .enableContentPlace(IntStream.range(9,45).toArray())
                            .addItem(0,AddItem.TOOLS_RECIPES,(cm)-> (GuideClickHandler)(Player var1, int var2, ItemStack var3, ClickAction var4,  PlayerProfile var5, SlimefunGuideMode var6, FlexItemGroup var7, int var8)->{
                                //left as not completed yet
                                return false;
                            });
                       }
                       },()->{
                        int itemSize=TOOLS_FUNCTIONAL.getItems().size();
                        HashMap<Integer,ItemGroup> items=new HashMap<>();
                        items.put(itemSize+2,TOOLS_SUBGROUP_1);
                        items.put(itemSize+1,TOOLS_SUBGROUP_2);
                        return items;
                    },()->{
                        var re=TOOLS_FUNCTIONAL.getItems();
                        var returned=new HashMap<Integer,SlimefunItem>();
                        for (int i=0;i<re.size();++i){
                            returned.put(i+1,re.get(i));
                        }
                        return returned;
                    }
            ).setBackButton(1)
            .setSearchButton(7);



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
                    put(17,SPECIAL);
                    put(20,CARGO);
                    put(23,ADVANCED);
                    put(24,SINGULARITY);
                    put(31,BEYOND);
                    put(36,UPDATELOG);
                    put(26,MORE2);
                    put(8, TOOLS);
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
                final TextComponent link2 = new TextComponent("单击此处访问附属wiki");
                link2.setColor(ChatColor.YELLOW);
                link2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/m1919810/LogiTech/wiki"));
                player1.spigot().sendMessage(link2);
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
            PostSetupTasks.addPostRegisterTask(this::initCustomMenus);
            //对自动生成的物品组位置进行修改(如果使用hashmap指定物品组则不用修改
            //彩蛋
            ItemStack info1=new CustomItemStack(Material.CHERRY_HANGING_SIGN,AddUtils.color("请选择你的英雄"),"&8可不要走错方向哦~");
            ItemStack h1=new CustomItemStack(CustomHead.getHead("fd524332cdb381c9e51f77d4cec9bc6d4d1c5bdec1499d206d8383e9176bdfb0"),AddUtils.color("haiman"),"&7点击查看详情");
            ItemStack h2=new CustomItemStack(CustomHead.getHead("a9b046531a6182de634d6fed1f3b4f885ee99bfe2bc0c1684f7b97d396c2059f"),AddUtils.color("mmmjjkx"),"&7点击查看详情");
            ItemStack h3=new CustomItemStack(CustomHead.getHead("7e224b7fb1e9dc78b2abb6a3fbf726b1d7159d08599b2c02b7bf1ad396285da6"),AddUtils.color("tinalness"),"&7点击查看详情");
            ItemStack h4=new CustomItemStack(CustomHead.SUPPORTER2.getItem(),AddUtils.color("HgTlPbBi"),"&7点击查看详情");
            ItemStack h5=new CustomItemStack(CustomHead.getHead("8e434215b5616bf37dccbacdb240bd16de59507e62a5371ceca80327b398e65"),AddUtils.color("Tmatsuki_rui"),"&7点击查看详情");
            ItemStack h6=new CustomItemStack(AddItem.MATL114,"  ","&8..?");
            ItemStack h7=new CustomItemStack(CustomHead.TOURUYA.getItem(),AddUtils.color("TouRuya"),"&7点击查看详情");
            ItemStack h8=new CustomItemStack(CustomHead.BIG_SNAKE.getItem(),AddUtils.color("某不知名大蛇"),"&7年轻人,你想刷物么");
            ItemStack h9=new CustomItemStack(CustomHead.MOYU.getItem(),AddUtils.color("xiaoUI1"),"&7点击查看详情");

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
            factory.addInventory(66,h7,(cm)-> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){has=true;
                        itemStack.setItemMeta(s7);
                    }
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
            factory.addInventory(68,h9,(cm -> new ChestMenu.MenuClickHandler() {
                boolean has=false;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(!has&&itemStack!=null){has=true;
                        itemStack.setType(Material.CHORUS_PLANT);
                        itemStack.setItemMeta(s9);
                    }
                    return false;
                }
            }));
            this.smetas =new ItemMeta[6];
            String continueStr="&7点击继续";
            ItemStack it=new CustomItemStack(AddItem.MATL114,AddUtils.color("你觉得..."),continueStr);
            factory.addInventory(71,it,(cm)->new ChestMenu.MenuClickHandler() {
                int index=0;
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if(itemStack!=null)
                        itemStack.setItemMeta(smetas[index]);
                    index=(index+1)%5;
                    return false;
                }
            });
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("我能在患有痴呆症的情况下完成逻辑工艺的编写吗?"),continueStr);
            smetas[0]=it.getItemMeta();
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("这能被完成么,这可能完成么?"),continueStr);
            smetas[1]=it.getItemMeta();
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("我知道这有点难"),continueStr);
            smetas[2]=it.getItemMeta();
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("所以,我想"),continueStr);
            smetas[3]=it.getItemMeta();
            it=new CustomItemStack(AddItem.MATL114,AddUtils.color("今年我要进行一项从未有人达成过的挑战"),continueStr);
            smetas[4]=it.getItemMeta();
            final String PREFIX1=AddUtils.resolveColor("&6今日运势:");
            final String PREFIX2=AddUtils.resolveColor(AddUtils.color("财运: ")+"&7");
            final String PREFIX3=AddUtils.resolveColor(AddUtils.color("桃花运: ")+"&7");
            final String PREFIX4=AddUtils.resolveColor(AddUtils.color("事业运: ")+"&7");
            final ItemStack luckButton=new CustomItemStack(CustomHead.LUCKY_BOX.getItem(),"&6每日运势","&7看看你的运势如何?");
            final int MAX_SIZE=101;
            factory.addInventory(44,luckButton,((player, i, itemStack, clickAction) -> {
                if(itemStack!=null){
                    ItemMeta meta=itemStack.getItemMeta();
                    List<String> lores=meta.getLore();
                    if(lores==null)lores=new ArrayList<>();
                    lores.subList(1,lores.size()).clear();
                    lores.add(PREFIX1);
                    String dateString=AddUtils.getDateString();
                    String name=player.getName();
                    String token=AddUtils.concat(dateString,name,"abcdiaeifg");
                    int code1=Math.abs(MathUtils.sha256(token,4))%MAX_SIZE;
                    int code2=Math.abs(MathUtils.sha512(token,4))%MAX_SIZE;
                    int code3=Math.abs(MathUtils.sha384(token,4))%MAX_SIZE;
                    if("matl114".equals(name)){
                        //byd
                        code1=100;
                        code2=100;
                        code3=100;
                    }
                    lores.add(PREFIX2+code1);
                    lores.add(PREFIX3+code2);
                    lores.add(PREFIX4+code3);
                    String date=LASTLY_CALCUATED_DATE.getOrDefault(player.getUniqueId(),"");
                    if(dateString.equals(date)){
                        AddUtils.sendMessage(player,"&e你今天已经测过一次了,不再进行公告");
                    }else{
                        AddUtils.broadCast("&6玩家 %s 今日的运势为".formatted(player.getName()));
                        AddUtils.broadCast(PREFIX2+code1);
                        AddUtils.broadCast(PREFIX3+code2);
                        AddUtils.broadCast(PREFIX4+code3);
                        LASTLY_CALCUATED_DATE.put(player.getUniqueId(),dateString);
                    }
                    meta.setLore(lores);
                    itemStack.setItemMeta(meta);
                }
                return false    ;
            }));
        }
        final ItemMeta s1=new CustomItemStack(CustomHead.getHead("fd524332cdb381c9e51f77d4cec9bc6d4d1c5bdec1499d206d8383e9176bdfb0"),AddUtils.color("haiman"),"&7haiman科技作者","&7海曼科技会为你提供足够的物质支持").getItemMeta();
        final ItemMeta s2=new CustomItemStack(CustomHead.getHead("a9b046531a6182de634d6fed1f3b4f885ee99bfe2bc0c1684f7b97d396c2059f"),AddUtils.color("mmmjjkx"),"&7rsc开发者","&3纯大蛇\uD83D\uDE0B").getItemMeta();
        final ItemMeta s3=new CustomItemStack(Material.REPEATING_COMMAND_BLOCK,AddUtils.color("tinalness"),"&7大香蕉的本质是命令方块\uD83D\uDE0B(确信","&7但是你或许会需要他的网络拓展").getItemMeta();
        final ItemMeta s4=new CustomItemStack(CustomHead.SUPPORTER2.getItem(),AddUtils.color("HgTlPbBi"),"&7逻辑工艺的支持者","&7提出了很多有用的点子").getItemMeta();
        final ItemMeta s5=new CustomItemStack(CustomHead.getHead("8e434215b5616bf37dccbacdb240bd16de59507e62a5371ceca80327b398e65"),AddUtils.color("Tmatsuki_rui"),"&7凉城服的祸源","&7纯傻逼,给爷死啊\uD83D\uDE21").getItemMeta();
        final ItemMeta s6=new CustomItemStack(CustomHead.BUSHIGEMEN.getItem(),"  ","&7看得出你的视力很好","&7所以一定要仔细阅读","&7版本说明与机器说明哦").getItemMeta();
        final ItemStack head=new CustomItemStack(Material.PLAYER_HEAD,AddUtils.color("逝者的头颅"));
        final ItemMeta s7=new CustomItemStack(CustomHead.TOURUYA.getItem(),AddUtils.color("TouRuya"),"&7人机服腐竹","&7纯人迹","&3\"堆叠开局 怎么输?\uD83D\uDE0B\"").getItemMeta();
        final ItemMeta s8=new CustomItemStack(CustomHead.BIG_SNAKE.getItem(),AddUtils.color("taitaia"),"&7刷物?你在想屁吃\uD83D\uDE0B").getItemMeta();
        final ItemMeta s9 =new CustomItemStack(Material.CHORUS_PLANT,AddUtils.color("xiaoUI1"),"&7知名服务器-&b魔芋&7 的腐竹","&7感谢UI对本项目的支持").getItemMeta();
        ItemMeta[] smetas;
        final HashMap<UUID,String> LASTLY_CALCUATED_DATE=new HashMap<>();
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

