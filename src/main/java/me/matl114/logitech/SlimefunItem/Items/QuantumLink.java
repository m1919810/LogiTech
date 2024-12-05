package me.matl114.logitech.SlimefunItem.Items;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.Language;
import me.matl114.logitech.SlimefunItem.Cargo.Links.HyperLink;
import me.matl114.logitech.SlimefunItem.Cargo.Links.StorageLink;
import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import me.matl114.logitech.SlimefunItem.DistinctiveCustomItemStack;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.DataCache;
import me.matl114.logitech.Utils.Utils;
import me.matl114.logitech.Utils.WorldUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Optional;

public class QuantumLink extends DistinctiveCustomItemStack {
    public QuantumLink(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe,new ArrayList<>());
        this.setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow("&f机制 - &c终极合成",
                                "&c特性 ",
                                "&7终极合成是本附属某些终极机器中的机制",
                                "&c具体说明请看\"版本与说明\"分类中的信息",
                                "&7直白了说是让物品存储中的物品直接参与合成/进程",
                                "&7从而打破槽位限制",
                                "&7该物品是终极机器支持的物品之一",
                                "&a建议:推荐使用该物品绑定存储性方块放入终极机器,方便且会降低卡顿!"),
                        null,
                        AddUtils.getInfoShow("&7机制 -&c 代理存储",
                                "&7本物品可以类似%s一样绑定某个方块".formatted(Language.get("Items.HYPER_LINK.Name")),
                                "&7若该方块是支持的存储性方块,则该物品会与此存储建立纠缠链接",
                                "&7当该物品被放入终极机器的槽位中时,",
                                "&7可以代理绑定的存储中的物品&e直接参与合成!",
                                "&7产出的物品也可以通过它&e直接进入绑定的存储!",
                                "&7支持的存储性方块:",
                                "&7逻辑工艺 存储接口系列",
                                "&7网络(拓展) 量子存储系列"),null,
                        AddUtils.getInfoShow("&7机制 -&c 纠缠记录",
                                "&7当绑定了存储后","&7该物品的描述上会显示该绑定存储中的物品类型",
                                "&7当你右键该物品时,该显示将被更新为当前的绑定存储的物品类型",
                                "&7除此之外,该物品描述将不会更新,也不会影响其参与合成",
                                "&7参与合成时所代理的存储只与存储位置有关,与描述无关"),null,
                        AddUtils.getInfoShow("&7机制 -&c 报错",
                                "&e如果网络版本不兼容或使用了错误的测试版,会造成报错",
                                "&7如果使用绑定量子存储的该物品进行合成时,出现合成不成功的情况",
                                "&7很可能这是量子存储数据不兼容或者错误版本导致的问题",
                                "&e请立刻将其移出槽位",
                                "&7并判断是存储数据损坏问题还是网络版本问题",
                                "&7若累计报错达到一定数目,将会禁用该兼容"),null
                )
        );
    }
    @Override
    public void preRegister(){
        super.preRegister();
        addItemHandler((ItemUseHandler)this::onBindLocation);
    }
    private void onBindLocation(PlayerRightClickEvent event) {
        if(event.getPlayer().isSneaking()){
            Optional<Block> b = event.getClickedBlock();
            if(b.isPresent()){
                ItemStack it= event.getItem();
                ItemMeta im = it.getItemMeta();
                if(StorageLink.canLink(im)){
                    if(WorldUtils.hasPermission(
                            event.getPlayer(),b.get(), Interaction.INTERACT_BLOCK)){
                        StorageLink.setLink(im,b.get().getLocation());
                        it.setItemMeta(im);
                        AddUtils.sendMessage(event.getPlayer(), "&a成功建立纠缠链接,存储历史记录已刷新!");
                    }else {
                        AddUtils.sendMessage(event.getPlayer(),"&c抱歉,但您似乎并不能在该位置使用此物品.");
                    }
                }
            }
        }else{
            //try load
            ItemStack it= event.getItem();
            ItemMeta im = it.getItemMeta();
            if(StorageLink.isLink(im)){
                Location loc=StorageLink.getLink(im);
                if(loc!=null) {
                    StorageLink.updateLinkLocationDisplay(im,loc);
                    it.setItemMeta(im);
                    AddUtils.sendMessage(event.getPlayer(),"&a纠缠链接的历史记录已经刷新!");
                    if (WorldUtils.hasPermission(
                            event.getPlayer(), loc, Interaction.INTERACT_BLOCK)
                    ) {
                        SlimefunBlockData data = DataCache.safeLoadBlock(loc);
                        if (data != null) {
                            BlockMenu menu = data.getBlockMenu();
                            if (menu != null)
                                menu.open(event.getPlayer());
                        }
                    }else {
                        AddUtils.sendMessage(event.getPlayer(), "&c抱歉,但您似乎并没有访问该位置的权限.");
                    }
                }
            }
        }
    }
}
