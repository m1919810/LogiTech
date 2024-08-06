package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.chat.ChatInput;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Items.CustomHead;
import me.matl114.logitech.MyAddon;
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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RecipeLogger extends AbstractMachine {
    protected final int[] BORDER=new int[]{33, 34, 35, 6, 7, 8, 42, 43, 44, 15,  17, 51, 52, 53, 24, 25, 26};
    protected final int[] READING_SLOT=new int[]{0, 1, 2, 3, 4, 5, 9, 10, 11, 12, 13, 14, 18,
            19, 20, 21, 22, 23, 27, 28, 29, 30, 31, 32, 36, 37, 38, 39, 40, 41, 45, 46, 47, 48, 49, 50};
    protected final int LOGGER_SLOT=16;
    protected final ItemStack LOGGER_ITEM=new CustomItemStack(Material.COMMAND_BLOCK,"&a点击打印本附属格式化配方注册字符串","&7开发者专用","&7因为其他人也不用这格式");
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public RecipeLogger(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe, 0,0);
    }
    public void addInfo(ItemStack item){

    }
    protected  void constructMenu(BlockMenuPreset preset){
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {

            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(LOGGER_SLOT,LOGGER_ITEM);
    }
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<MachineRecipe>();
    }
    public void process(Block b, BlockMenu preset){

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
        }
        result+=String.join(",", idList)+")";
        return result;
    }
    public void newMenuInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block){
        blockMenu.addMenuClickHandler(LOGGER_SLOT,((player, i, itemStack, clickAction) -> {
            String recipeString=getRecipeString(blockMenu,READING_SLOT);
            final TextComponent link = new TextComponent("单击此处拷贝recipe字符串");
            link.setColor(ChatColor.YELLOW);
            link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("点击复制到剪贴板")));
            link.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,recipeString));
            player.spigot().sendMessage(link);
            return false;
        }));
    }

}
