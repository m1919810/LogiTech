package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.chat.ChatInput;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.matl114.logitech.Items.CustomHead;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.event.ClickEvent;
//import net.kyori.adventure.text.format.NamedTextColor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class HeadAnalyzer extends AbstractMachine{
    protected final int[] BORDER=new int[]{
            0,2,3,5,6,8,9,11,12,14,15,17,18,19,20,21,22,23,24,25,26
    };
    protected final int ENCODE_SLOT=7;
    protected final int CHANGE_SLOT=4;
    protected final int CHANGE_INPUT=13;
    protected final int DECODE_SLOT=1;
    protected final int ENCODE_DISPLAY=16;
    protected final int DECODE_INPUTSLOT=10;
    protected final ItemStack CHANGE_ITEM=new CustomItemStack(Material.CRAFTING_TABLE,"&e点击转换头颅","&7当下方为默认头颅时,点击输入hash,可以更改头颅样式","&7当下方为展示示例头颅或非粘液物品时,将头颅转为默认头颅");
    protected final ItemStack ENCODE_ITEM=new CustomItemStack(Material.NAME_TAG,"&e点击输入hash","&7将在下方显示头颅样式");
    protected final ItemStack ENCODE_DISPLAY_NULL=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c空","&c暂未输入hash或者不是有效的hash");
    protected final ItemStack DECODE_ITEM=new CustomItemStack(Material.KNOWLEDGE_BOOK,"&e点击解析下方头颅","&7若下方为有效物品,则显示hash","&3hash: &f空");
    protected final ItemMeta BLANK_SKULL=new ItemStack(Material.PLAYER_HEAD).getItemMeta();
    private String getHash(ItemStack item){
        String st= CustomHead.getHash(item);
        if(st!=null)
            return "&3hash: &f"+st;
        else
            return "&3hash: &f空";
    }
    private ItemStack getSkull(String string){
        try{
            return new CustomItemStack(SlimefunUtils.getCustomHead(string),"&a头颅样式展示品","&7这是一个展示头颅","&3hash: &f"+string);
        }catch (Throwable t){
            return ENCODE_DISPLAY_NULL.clone();
        }
    }
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public HeadAnalyzer(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
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
        preset.addItem(ENCODE_DISPLAY,ENCODE_DISPLAY_NULL, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(DECODE_SLOT,DECODE_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(ENCODE_SLOT,ENCODE_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(CHANGE_SLOT,CHANGE_ITEM, ChestMenuUtils.getEmptyClickHandler());
    }
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<MachineRecipe>();
    }
    public void process(Block b, BlockMenu preset, SlimefunBlockData data){

    }
    public void registerTick(SlimefunItem item){
        //no ticker
    }
    public void newMenuInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block){
        blockMenu.addMenuClickHandler(ENCODE_SLOT,((player, i, itemStack, clickAction) -> {
            player.closeInventory();

            player.sendMessage(ChatColors.color("&e请输入头颅的hash码"));
            ChatInput.waitForPlayer(
                    Slimefun.instance(),
                    player,
                    msg ->{
                        blockMenu.replaceExistingItem(ENCODE_DISPLAY,getSkull(msg));
                        blockMenu.open(player);
                    } );

            return false;
        }));
        blockMenu.addMenuClickHandler(DECODE_SLOT,((player, i, itemStack, clickAction) -> {
            String st= CustomHead.getHash(blockMenu.getItemInSlot(DECODE_INPUTSLOT));
            if(st!=null){
                String res="&3hash: &f"+st;
                ItemStack it=blockMenu.getItemInSlot(DECODE_SLOT);
                AddUtils.setLore(it,1,res);
                AddUtils.displayCopyString(player,"单击此处拷贝hash码","点击复制到剪贴板",st);

            }else{
                blockMenu.replaceExistingItem(DECODE_SLOT,DECODE_ITEM);
            }
            return false;
        }));
        blockMenu.addMenuClickHandler(CHANGE_SLOT,((player, i, itemStack, clickAction) -> {
            ItemStack it=blockMenu.getItemInSlot(CHANGE_INPUT);
            if(it!=null&&it.getType()==Material.PLAYER_HEAD){

                if(!it.hasItemMeta()){
                    blockMenu.replaceExistingItem(CHANGE_INPUT,null);
                    AddUtils.sendMessage(player,"&e请输入头颅的hash码");
                    player.closeInventory();
                    ChatInput.waitForPlayer(
                            Slimefun.instance(),
                            player,
                            msg ->{
                                ItemStack it2=AddItem.SAMPLE_HEAD.clone();
                                ItemMeta meta2=it2.getItemMeta();
                                ItemMeta target_meta=CustomHead.getHead(msg).getItemMeta();

                                if(meta2 instanceof SkullMeta skmeta && target_meta instanceof SkullMeta skmeta2){
                                    skmeta.setOwnerProfile(skmeta2.getOwnerProfile());
                                }
                                it2.setItemMeta(meta2);
                                blockMenu.replaceExistingItem(CHANGE_INPUT,it2);
                                blockMenu.open(player);
                                return;
                            } );
                }else{
                    ItemMeta meta=it.getItemMeta();
                    String id= CraftUtils.parseSfId(meta);
                    if(id==null||id.equals( AddItem.SAMPLE_HEAD.getItemId())){
                        it.setItemMeta(null);
                    }else{
                        AddUtils.sendMessage(player,"&c不是合法物品,无法清除头颅数据!");
                    }
                }
            }
            return false;
        }));
    }

}
