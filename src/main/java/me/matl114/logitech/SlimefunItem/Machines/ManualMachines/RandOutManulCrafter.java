package me.matl114.logitech.SlimefunItem.Machines.ManualMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import me.matl114.logitech.Utils.AddUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RandOutManulCrafter extends ManualCrafter{
    public RandOutManulCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         int energybuffer, int energyConsumption,
                         RecipeType... craftType) {
        super(category,item,recipeType,recipe,energybuffer,energyConsumption,craftType);
    }
    public boolean preCraft(BlockMenu inv, Player p,boolean sendMessage){
        int[] outputs=getOutputSlots();
        for (int i=0;i<outputs.length;i++){
            if(inv.getItemInSlot(outputs[i])==null){
                return true;
            }
        }
        if(sendMessage){
            AddUtils.sendMessage(p,"&c输出槽已满!无法合成");
        }
        return false;
    }
}
