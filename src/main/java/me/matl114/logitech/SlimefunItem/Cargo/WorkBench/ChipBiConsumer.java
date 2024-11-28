package me.matl114.logitech.SlimefunItem.Cargo.WorkBench;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Cargo.Config.ChipCardCode;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.Utils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChipBiConsumer extends AbstractMachine {
    protected int[] BORDER=new int[]{
            0,2,3,5,6,8,9,11,12,14,15,17,18,19,20,21,23,24,25,26,
            27,28,29,30,32,33,34,35,36,37,38,39,40,41,42,43,44
    };
    protected int[] INPUT_SLOT=new int[]{
            10,13,16
    };
    protected int[] OUTPUT_SLOT=new int[]{
            31
    };
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    protected int[] INFO_SLOT=new int[]{
            1,4,7,22
    };
    protected ItemStack[] INFO_ITEM=new ItemStack[]{
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6芯片输入槽","&7将待操作的第一位芯片放入该槽"),
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6逻辑物质输入槽","&7将位运算对应的物品放入该槽"),
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6芯片输入槽","&7将待操作的第二位芯片放入该槽"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a输出槽","&7只有输入槽为空时才会输出")
    };
    public ChipBiConsumer(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                        int energybuffer, int energyConsumption) {
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
        setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow("&f机制",
                        "&7机器可以进行 且 或 异或 三种位运算",
                        "&7将所需物品插入指定槽位即可消耗电力运算"),null,
                AddUtils.getInfoShow("&f且","&7插入%s进行且操作".formatted(Language.get("Items.TRUE_.Name")),
                        "&7运算规则:","&7依次对两个芯片的对应01位进行'&'运算","&8⇨ &70&&71=1&&70=0&&70=0","&8⇨ &71&&71=1"),AddItem.TRUE_,
                AddUtils.getInfoShow("&f或","&7插入%s进行或操作".formatted(Language.get("Items.FALSE_.Name")),
                        "&7运算规则:","&7依次对两个芯片的对应01位进行'|'运算","&8⇨ &70|1=1|0=1|1=1","&8⇨ &70|0=0"),AddItem.FALSE_,
                AddUtils.getInfoShow("&f异或","&7插入%s进行异或操作".formatted(Language.get("Items.LOGIC.Name")),
                        "&7运算规则:","&7依次对两个芯片的对应01位进行'&'运算","&8⇨ &70^1=1^0=1","&8⇨ &70^0=1^1=0"),AddItem.LOGIC
        ));
    }
    protected Material ChipMaterial=AddItem.CHIP.getType();

    protected final ItemCounter[] MATCH_ITEM=new ItemCounter[]{
            CraftUtils.getConsumer( AddItem.TRUE_),
            CraftUtils.getConsumer(  AddItem.FALSE_),
            CraftUtils.getConsumer(  AddItem.LOGIC),
    };
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for(int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=INFO_SLOT;
        len=border.length;
        for(int i=0;i<len;i++){
            preset.addItem(INFO_SLOT[i],INFO_ITEM[i],ChestMenuUtils.getEmptyClickHandler());
        }
    }
    public void process(Block b, BlockMenu inv, SlimefunBlockData data){
        ItemStack it=inv.getItemInSlot(INPUT_SLOT[0]);
        ItemStack it2=inv.getItemInSlot(INPUT_SLOT[1]);
        ItemStack it3=inv.getItemInSlot(INPUT_SLOT[2]);
        ItemStack it4=inv.getItemInSlot(OUTPUT_SLOT[0]);
        if(it2==null||it==null||it3==null||it4!=null){
            return;
        }
        int index=-1;
        for(index=0;index<3;index++){
            if(CraftUtils.matchItemStack(it2,MATCH_ITEM[index],false)){
                break;
            }
        }
        if(index==-1){return;}
        ItemMeta meta=it.getItemMeta();
        ItemMeta meta3=it3.getItemMeta();
        if(ChipCardCode.isConfig(meta)&&ChipCardCode.isConfig(meta3)){
            it.setAmount(it.getAmount()-1);
            it2.setAmount(it2.getAmount()-1);
            it3.setAmount(it3.getAmount()-1);
            final int indexer=index;
            progressorCost(b,inv);
            Schedules.launchSchedules(()->{
            ItemStack itout=AddItem.CHIP.clone();
            int code=ChipCardCode.getConfig(meta);
            int code2=ChipCardCode.getConfig(meta3);
            switch(indexer){
                case 0:code=code&code2;break;
                case 1:code=code|code2;break;
                case 2:code=code^code2;break;
            }
            itout.setItemMeta(ChipCardCode.getCard(code));
            if(inv.getItemInSlot(OUTPUT_SLOT[0])==null){
                inv.replaceExistingItem(OUTPUT_SLOT[0],itout,false);
            }
            },0,false,0);
        }
    }
    int[] CHIP_SLOT=new int[]{
            INPUT_SLOT[0],INPUT_SLOT[2],
    };
    int[] NOCHIP_SLOT=new int[]{
            INPUT_SLOT[1],
    };
    @Override
    public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if(flow==ItemTransportFlow.WITHDRAW)return getOutputSlots();
        if(item==null||item.getType().isAir()){
            return getInputSlots();
        }
        if(item.getType()==ChipMaterial){
            return CHIP_SLOT;
        }
        return NOCHIP_SLOT;
    }
}
