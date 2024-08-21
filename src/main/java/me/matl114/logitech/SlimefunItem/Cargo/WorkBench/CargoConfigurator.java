package me.matl114.logitech.SlimefunItem.Cargo.WorkBench;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Blocks.AbstractBlock;
import me.matl114.logitech.SlimefunItem.Cargo.Config.CargoConfigCard;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.UtilClass.CargoClass.CargoConfigs;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;

import java.util.LinkedHashMap;

public class CargoConfigurator extends AbstractBlock {
    protected final int[] BORDER=new int[]{
            0,1,2,3,5,6,7,8,27,28,29,30,32,33,34,35
    };
    protected final int[] INFO_SLOTS=new int[]{
            4,9,10,11,12,13,14,15,16,17
    };
    protected final int[] CONFIG_SLOTS=new int[]{
            18,19,20,21,22,23,24,25,26
    };
    protected final int OUTPUT_SLOT=31;
    protected final ItemStack[] INFO_ITEMS=new ItemStack[]{
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
                    "&6点击构建货运配置卡","&e将任意货运配置卡置于下方槽位","&7并在对应的配置槽位中放入指定物品","&7点击该物品,即可进行配置","&a支持一次配置多个配置卡"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 强对称",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("Items.TRUE_.Name"),Language.get("Items.FALSE_.Name")),"&7配置是否使用强对称传输","&7即是否将物品按槽位进行对应的运输"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 仅空运输",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("Items.TRUE_.Name"),Language.get("Items.FALSE_.Name")),"&7配置是否使用仅空传输","&7即是否只传向空槽位"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 懒惰模式",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("Items.TRUE_.Name"),Language.get("Items.FALSE_.Name")),"&7配置是否使用懒惰模式","&7即是否在传输一次后停止"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 白名单",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("Items.TRUE_.Name"),Language.get("Items.FALSE_.Name")),"&7配置是否使用白名单","&7即是否将物品列表视为白名单,默认黑名单"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 取自输入槽",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("Items.TRUE_.Name"),Language.get("Items.FALSE_.Name")),"&7配置是否取自输入槽","&7即只选择源方块的输入槽进行传输,默认为只选择输出槽"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 传向输出槽",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("Items.TRUE_.Name"),Language.get("Items.FALSE_.Name")),"&7配置是否传向输出槽","&7即只选择目标方块的输出槽进行传输,默认为只选择输入槽"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 逆向传输",
            "&e将[%s]或[%s]置于下方".formatted(Language.get("Items.TRUE_.Name"),Language.get("Items.FALSE_.Name")),"&7配置是否使用逆向传输","&7即是否从目标方块指定槽位向源方块指定槽位传输"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 传输数量限制",
                    "&e将[%s]置于下方".formatted(Language.get("Items.CARGO_PART.Name")),"&7配置传输数量","&a传输数量增加<物品数量>*64"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 传输数量限制",
                    "&e将[%s]置于下方".formatted(Language.get("Items.CARGO_PART.Name")),"&7配置传输数量","&a传输数量增加<物品数量>"),
    };
    protected final ItemStack TIPS_ITEM=AddUtils.randItemStackFactory(
            new LinkedHashMap<>(){{
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7将仅空模式设置为true可以减少机器运行开销"),400);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7强对称模式很强大,或许你会很好的利用它"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7懒惰模式会在首次传输某槽位后终止传输"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7请不要让传输双方的目标槽位重叠,除非你想丢物品!"),800);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7若无特殊需求,尽量不要放置黑白名单,这有助于服务器健康"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7将仅空模式设置为true可以减少机器运行开销"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7货运配置卡可以同时配置一组!"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7已经配置好的配置卡可以放入配置槽重配,这将覆盖之前的设置"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7将仅空模式设置为true可以减少机器运行开销"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7逆向模式看起来似乎没什么用,不过你可以在链式运输中使用它"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7本附属通用货运方法由matl114设计","若有疑问可以寻找作者"),100);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7最有效的配置是除了仅空模式以外全设置false,同时调制合适的传输数量"),100);
                put(AddUtils.addGlow( new CustomItemStack(Material.KNOWLEDGE_BOOK,"&x&E&B&3&3&E&B小彩蛋","&b恭喜您抽中了1/3600概率的彩蛋","&b想来您今天的运势一定很好!")),1);

            }}
    );
    protected final int TIPS_SLOT=35;
    protected ItemCounter MATCHED_CARGO=CraftUtils.getConsumer(AddItem.CARGO_PART);
    public CargoConfigurator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public int[] getConfigureSlots(){
        return CONFIG_SLOTS;
    }
    public int getConfigCardSlot(){
        return OUTPUT_SLOT;
    }

    public void constructMenu(BlockMenuPreset preset){
        int[] slot=BORDER;
        int len=slot.length;
        for (int i=0;i<len;i++){
            preset.addItem(slot[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        slot=INFO_SLOTS;
        len=slot.length;
        for (int i=0;i<len;++i){
            preset.addItem(slot[i],INFO_ITEMS[i],ChestMenuUtils.getEmptyClickHandler());
        }
    }
    public boolean craft(BlockMenu inv){
        ItemStack it=inv.getItemInSlot(getConfigCardSlot());
        if(it==null){
            return false;
        }
        int[] slots=getConfigureSlots();
        int[] configCodes=new int[8];
        ItemStack stack;
        for (int i=0;i<7;++i){
           stack=inv.getItemInSlot(slots[i]);
            if(stack==null){
                return false;
            }
            if(stack.getType()==Material.MUSIC_DISC_5&&stack.hasItemMeta()){
                //有附魔是true 没是false
                configCodes[i]=stack.getEnchantments().isEmpty()?0:1;
            }else return false;
        }
        stack=inv.getItemInSlot(slots[7]);
        if(stack!=null){
            configCodes[7]+=(stack.getAmount())*64;
        }
        stack=inv.getItemInSlot(slots[8]);
        if(stack!=null){
            configCodes[7]+=stack.getAmount();
        }
        ItemMeta meta=it.getItemMeta();
        if(CargoConfigCard.canConfig(meta)){
            int code=0;
            code=CargoConfigs.IS_SYMM.setConfig(code,configCodes[0]);
            code=CargoConfigs.IS_NULL.setConfig(code,configCodes[1]);
            code=CargoConfigs.IS_LAZY.setConfig(code,configCodes[2]);
            code=CargoConfigs.IS_WHITELST.setConfig(code,configCodes[3]);
            code=CargoConfigs.FROM_INPUT.setConfig(code,configCodes[4]);
            code=CargoConfigs.TO_OUTPUT.setConfig(code,configCodes[5]);
            code=CargoConfigs.REVERSE.setConfig(code,configCodes[6]);
            code=CargoConfigs.TRANSLIMIT.setConfig(code,configCodes[7]);
            CargoConfigCard.setConfig(meta,code);
            it.setItemMeta(meta);
            for(int i=0;i<9;++i){
                inv.replaceExistingItem(slots[i],null);
            }
            return true;
        }else return false;
    }
    public void newMenuInstance(BlockMenu inv, Block block){
        inv.addMenuOpeningHandler(player -> {
            inv.replaceExistingItem(TIPS_SLOT,TIPS_ITEM.clone());
        });
        inv.addMenuClickHandler(INFO_SLOTS[0],((player, i, itemStack, clickAction) -> {
            if(craft(inv)){
                AddUtils.sendMessage(player,"&a配置成功!");
            }else{
                AddUtils.sendMessage(player,"&c配置失败,请检查槽位的内容!");
            }
            return false;
        }));
    }
    public void onBreak(BlockBreakEvent event, BlockMenu menu) {
        super.onBreak(event, menu);
        if(menu!=null){
            Location loc =menu.getLocation();
            menu.dropItems(loc, getConfigureSlots());
            menu.dropItems(loc,getConfigCardSlot());
        }
    }
    public void preRegister() {
        super.preRegister();
        this.registerBlockMenu(this);
        this.handleMenu(this);
    }
}
