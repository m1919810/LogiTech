package me.matl114.logitech.core.Items.SpecialItems;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.core.Interface.DirectionalBlock;
import me.matl114.logitech.core.Interface.MenuTogglableBlock;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.utils.DataCache;
import me.matl114.logitech.utils.UtilClass.CargoClass.Directions;
import me.matl114.logitech.utils.UtilClass.PdcClass.AbstractStringList;
import me.matl114.logitech.utils.WorldUtils;
import me.matl114.logitech.core.Items.Abstracts.CustomProps;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConfigCopier extends CustomProps {

    public ConfigCopier(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    private NamespacedKey KEY_DIR= AddUtils.getNameKey("dir-cfg");
    private NamespacedKey KEY_SET= AddUtils.getNameKey("tog-set");
    private NamespacedKey KEY_BWL= AddUtils.getNameKey("bwl-cfg");
    @Override
    public void onClickAction(PlayerRightClickEvent event) {
        Optional<Block> optional=event.getClickedBlock();
        ItemStack configurator=event.getItem();
        if(optional.isPresent()&&configurator!=null){
            Block block=optional.get();
            Player p=event.getPlayer();
            ItemMeta meta=configurator.getItemMeta();
            if(this.getId().equals(CraftUtils.parseSfId(meta))){
                if(WorldUtils.hasPermission(p,block, Interaction.INTERACT_BLOCK)){
                    SlimefunBlockData data= DataCache.safeLoadBlock(block.getLocation());
                    if(data!=null) {
                        SlimefunItem item= SlimefunItem.getById(data.getSfId());
                        PersistentDataContainer container=meta.getPersistentDataContainer();
                        if (p.isSneaking()) {
                            //复制方向配置
                            if (item instanceof DirectionalBlock directional && directional.canModify()) {
                                Directions[] directions = directional.copyDirectionSettings(data);
                                List<String> cfgs= Arrays.stream(directions).map(Directions::toString).toList();
                                container.set(KEY_DIR, AbstractStringList.TYPE,cfgs);
                                AddUtils.sendMessage(p,"&6[方向配置] &a已保存方块的方向配置,共 %s 项".formatted(cfgs.size()));
                            }else {
                                AddUtils.sendMessage(p,"&6[方向配置] &c该方块不含有方向配置");
                            }
                            if(item instanceof MenuTogglableBlock togglableBlock ){
                                boolean[] settings=togglableBlock.getStatus(data.getBlockMenu());
                                byte[] byteArray=new byte[settings.length];
                                for (int i=0;i<settings.length;++i){
                                    byteArray[i]=settings[i]?(byte) 1:(byte) 0;
                                }
                                container.set(KEY_SET, PersistentDataType.BYTE_ARRAY,byteArray);
                                AddUtils.sendMessage(p,"&6[特殊选项] &c已保存方块的特殊选项,共 %s 项".formatted(byteArray.length));
                            }else {
                                AddUtils.sendMessage(p,"&6[特殊选项] &c该方块不含特殊选项");
                            }


                            configurator.setItemMeta(meta);
                        } else {
                            //拷贝方向配置
                            if (item instanceof DirectionalBlock directional && directional.canModify()) {
                                List<String> cfgs= container.get(KEY_DIR, AbstractStringList.TYPE);
                                if(cfgs!=null) {
                                    Directions[] directions=cfgs.stream().map(Directions::fromString).toArray(Directions[]::new);
                                    int result=directional.pasteDirectionSettings(data, directions);
                                    if(result>=0) {
                                        AddUtils.sendMessage(p,"&6[方向配置] &a已应用保存的 %d 个方向配置".formatted(result));
                                    }else {
                                        AddUtils.sendMessage(p,"&6[方向配置] &c该方块的方向配置无法被修改");
                                    }
                                }else {
                                    AddUtils.sendMessage(p,"&6[方向配置] &c配置器没有保存方向配置");
                                }
                            }else{
                                AddUtils.sendMessage(p,"&6[方向配置] &c该方块不含有方向配置");
                            }
                            if(item instanceof MenuTogglableBlock togglableBlock){
                                byte[] byteArray=container.get(KEY_SET, PersistentDataType.BYTE_ARRAY);
                                if(byteArray!=null&&byteArray.length>0) {
                                    boolean[] settings=new boolean[byteArray.length];
                                    for (int i=0;i<settings.length;++i){
                                        settings[i]=byteArray[i]!=0;
                                    }
                                    togglableBlock.toggleStatus(data.getBlockMenu(), settings);
                                    AddUtils.sendMessage(p,"&6[特殊选项] &c已应用保存的特殊选项");
                                }else {
                                    AddUtils.sendMessage(p,"&6[特殊选项] &c配置器没有保存特殊选项");
                                }
                            }else{
                                AddUtils.sendMessage(p,"&6[特殊选项] &c该方块不含特殊选项");
                            }



                        }
                        event.cancel();
                    }
                }else {
                    AddUtils.sendMessage(p,"&c你没有权限在这里使用该物品");
                }
            }else{
                AddUtils.sendMessage(p,"&c无效的配置器");
            }
        }
    }
}
