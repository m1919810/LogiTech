package me.matl114.logitech.Depends;


import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.google.common.base.Preconditions;
import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedItem;
import io.github.sefiraat.networks.Networks;
import lombok.Getter;
import me.matl114.logitech.Schedule.SchedulePostRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;

public class SupportedPluginManager {

    @Getter
    private static SupportedPluginManager instance;
    @Getter
    private boolean roseStacker;
    @Getter
    private boolean wildStacker;
    @Getter
    private RoseStackerAPI roseStackerAPI;
    @Getter
    private boolean mcMMO;
    @Getter
    private boolean wildChests;

    // endregion

    public SupportedPluginManager() {
        Preconditions.checkArgument(instance == null, "Cannot instantiate class");
        instance = this;

        SchedulePostRegister.addPostRegisterTask(this::firstTickRegistrations);
    }

    public int getStackAmount(Item item) {
        try{
            if (isWildStacker()) {
                return WildStackerAPI.getItemAmount(item);
            } else if (getInstance().isRoseStacker()&&getRoseStackerAPI()!=null) {
                StackedItem stackedItem = getRoseStackerAPI().getStackedItem(item);
                return stackedItem == null ? item.getItemStack().getAmount() : stackedItem.getStackSize();
            } else {
                return item.getItemStack().getAmount();
            }
        }catch (Throwable e){
            return item.getItemStack().getAmount();
        }
    }

    public void setStackAmount(Item item, int amount) {
        try{
            if (isWildStacker()) {
                WildStackerAPI.getStackedItem(item).setStackAmount(amount, true);
            } else if (isRoseStacker()) {
                StackedItem stackedItem =getRoseStackerAPI().getStackedItem(item);
                if (stackedItem != null) {
                    stackedItem.setStackSize(amount);
                }
            } else {
                item.getItemStack().setAmount(amount);
            }
        }catch (Throwable e){
            item.getItemStack().setAmount(amount);
        }
    }

    private void firstTickRegistrations() {
        this.wildChests = Bukkit.getPluginManager().isPluginEnabled("WildChests");
        this.mcMMO = Bukkit.getPluginManager().isPluginEnabled("mcMMO");
        this.roseStacker = Bukkit.getPluginManager().isPluginEnabled("RoseStacker");
        if (roseStacker) {
            this.roseStackerAPI = RoseStackerAPI.getInstance();
        }
        this.wildStacker = Bukkit.getPluginManager().isPluginEnabled("WildStacker");
    }
}
