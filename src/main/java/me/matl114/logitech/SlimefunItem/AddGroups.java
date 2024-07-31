package me.matl114.logitech.SlimefunItem;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.MenuUtils;
import me.matl114.logitech.Utils.RecipeSupporter;
import me.matl114.logitech.Utils.UtilClass.CustomItemGroup;
import me.matl114.logitech.Utils.UtilClass.CustomMenu;
import me.matl114.logitech.Utils.UtilClass.DummyItemGroup;
import me.matl114.logitech.Utils.UtilClass.MenuFactory;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AddGroups {
    public static void registerGroups(SlimefunAddon plugin){
        ROOT.register(plugin);
        author.register(plugin);
    }
    // 给你的分类提供一个独一无二的ID
    protected static  final NamespacedKey itemGroupId = AddUtils.getNameKey("addon_category");

    protected static  final NamespacedKey itemGroupId2 = AddUtils.getNameKey("addon_category2");

    public static final ItemGroup author=new DummyItemGroup(AddUtils.getNameKey("author" ), AddUtils.themed(Material.BOOK,AddUtils.Theme.CATEGORY,"版本与说明"));
    public static final ItemGroup ROOT=new CustomItemGroup(itemGroupId,AddUtils.colorful(AddUtils.ADDON_NAME),new CustomItemStack(Material.COMMAND_BLOCK,"&4MATL114的附属测试模板"),54,108,
                new ArrayList<>(){{
                    add(author);
                    add(author);
                    add(author);
                }}
            ){
        //used to set common handlers and common params
        public void init(MenuFactory factory){
            factory.addInventory(0,new CustomItemStack(Material.BOOK,AddUtils.colorful("点击获取github链接")),(Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                final TextComponent link = new TextComponent("单击此处访问Github");
                link.setColor(ChatColor.YELLOW);
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/m1919810/SlimefunAddonTemplate"));
                player1.spigot().sendMessage(link);
                return false;
            });
            factory.addInventory(1,AddUtils.themed(Material.BOOK,AddUtils.Theme.CATEGORY,"配方类型大赏"));
            factory.addInventory(2,AddUtils.themed(Material.BOOK,AddUtils.Theme.CATEGORY,"机器类型大赏"));
            factory.addInventory(70,new ItemStack(Material.COMMAND_BLOCK));
            //this is dangerous,not suggested
            int [] slots=previewGroupSlot();
            slots[1]=77;

        }
        //used to set GUIDE based handlers,an interface to adapt CustomMenu menus
        public void addGuideHandler(CustomMenu menu, Player p, PlayerProfile profile, SlimefunGuideMode mode, int page){
            profile.getGuideHistory().add(this, page);
            menu.setHandler(1 ,((Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                MenuUtils.createRecipeTypeDisplay(RecipeSupporter.RECIPE_TYPES.stream().toList(),((player, i, itemStack, clickAction1) -> {
                    profile.getGuideHistory().openLastEntry(Slimefun.getRegistry().getSlimefunGuide(mode));
                    return false;
                })).open(player1);
                return false;
            }));
            menu.setHandler(2,((Player player1, int i1, ItemStack itemstack1, ClickAction clickAction) -> {
                profile.getGuideHistory().add(this, page);
                MenuUtils.createMachineListDisplay(RecipeSupporter.MACHINE_RECIPELIST.keySet().stream().toList(),((player, i, itemStack, clickAction1) -> {
                    profile.getGuideHistory().openLastEntry(Slimefun.getRegistry().getSlimefunGuide(mode));
                    return false;
                })).open(player1);
                return false;
            }));
        }
    };
}

