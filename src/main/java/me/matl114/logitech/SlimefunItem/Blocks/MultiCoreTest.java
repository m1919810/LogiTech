package me.matl114.logitech.SlimefunItem.Blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockHandler;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.Utils.UtilClass.MultiBlockClass.MultiBlockType;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class MultiCoreTest extends MultiCore {
    protected final int[] BORDER=new int[]{0,1,2,3,5,6,7,8};
    protected final int TOGGLE_SLOT=4;
    protected final ItemStack TOGGLE_ITEM=new CustomItemStack(Material.BEDROCK,"&6点击进行多方块机器构建测试");
    protected final MultiBlockType MBTYPE;
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public MultiCoreTest(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                         ItemStack[] recipe, String blockId, MultiBlockType type){
        super(itemGroup, item, recipeType, recipe, blockId);
        this.MBTYPE = type;
    }
    public MultiBlockType getMultiBlockType(){
        return MBTYPE;
    }
    public MultiBlockService.MultiBlockBuilder getBuilder(){
        return MultiBlockHandler::createHandler;
    }
    public void constructMenu(BlockMenuPreset inv){
        inv.addItem(TOGGLE_SLOT,TOGGLE_ITEM);
    }
    public void newMenuInstance(BlockMenu inv,  Block block){
        inv.addMenuClickHandler(TOGGLE_SLOT,((player, i, itemStack, clickAction) -> {
            if(MultiBlockService.createNewHandler(inv.getLocation(),getBuilder(),getMultiBlockType())){
                Debug.logger("successfully create a new multiblock");
                AddUtils.sendMessage(player,"&a成功激活多方块结构!");
            }else {
                Debug.logger("failed to create a new multiblock");
                AddUtils.sendMessage(player,"&c多方块结构不全或者结构冲突!");
            }
            return false;
        }));
    }

}
