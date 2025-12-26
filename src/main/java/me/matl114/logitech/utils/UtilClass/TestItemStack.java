package me.matl114.logitech.utils.UtilClass;

import me.matl114.logitech.utils.Debug;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TestItemStack extends ItemStack {
    int a = 0;

    public TestItemStack(Material type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
    }

    public TestItemStack(ItemStack item) {
        super(item);
    }

    public Material getType() {
        Debug.logger("getType called");
        return super.getType();
    }

    public ItemMeta getItemMeta() {
        Debug.logger("getItemMeta called");
        return super.getItemMeta();
    }

    public boolean equals(Object a) {
        Debug.logger("equal called");
        Debug.logger("beyond");
        return super.equals(a);
    }

    public int hashCode() {
        Debug.logger("hash called");
        Debug.logger("beyond");
        return super.hashCode();
    }
    //    public TestItemStack clone(){
    //        Debug.logger("clone called");
    //        return new TestItemStack(this);
    //    }
}
