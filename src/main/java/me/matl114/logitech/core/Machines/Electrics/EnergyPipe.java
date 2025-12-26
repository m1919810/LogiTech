package me.matl114.logitech.core.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.DataCache;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class EnergyPipe extends AbstractPipe {
    protected final int MAX_TRANSFER;
    protected final double TRANSFER_COST;

    public EnergyPipe(
            ItemGroup category,
            SlimefunItemStack item,
            RecipeType recipeType,
            ItemStack[] recipe,
            int maxTransfer,
            double transferCost) {
        super(category, item, recipeType, recipe);
        this.MAX_TRANSFER = maxTransfer;
        this.TRANSFER_COST = transferCost;
    }

    public void addInfo(ItemStack stack) {
        stack.setItemMeta(AddUtils.addLore(
                        stack,
                        "&7最大电力传输速率: %sJ/t".formatted(AddUtils.formatDouble(MAX_TRANSFER)),
                        "&7能量传输损耗率: %s%%".formatted(AddUtils.formatDouble(100 * this.TRANSFER_COST)))
                .getItemMeta());
    }

    @Override
    public boolean avalibleDestination(Location toLocation) {
        if (DataCache.getSfItem(toLocation) instanceof EnergyNetComponent ec && ec.isChargeable()) {
            return true;
        }
        return false;
    }

    @Override
    public void transfer(Location fromLocation, Location toLocation) {
        SlimefunBlockData toData = DataCache.safeLoadBlock(toLocation);
        SlimefunBlockData fromData = DataCache.safeLoadBlock(fromLocation);
        if (toData == null || fromData == null) {
            return;
        }
        if (SlimefunItem.getById(toData.getSfId()) instanceof EnergyNetComponent ec1
                && SlimefunItem.getById(fromData.getSfId()) instanceof EnergyNetComponent ec2) {
            if (ec1.isChargeable() && ec2.isChargeable()) {

                int fromCharge = ec2.getCharge(fromLocation);
                int toCharge = ec1.getCharge(toLocation);
                int transfer = Math.min(Math.min(fromCharge, MAX_TRANSFER), ((int)
                        (((double) (ec1.getCapacity() - toCharge)) / (1 - TRANSFER_COST))));

                ec2.setCharge(fromLocation, fromCharge - transfer);
                ec1.setCharge(toLocation, toCharge + (int) (((double) transfer) * (1 - TRANSFER_COST)));
            }
        }
    }
}
