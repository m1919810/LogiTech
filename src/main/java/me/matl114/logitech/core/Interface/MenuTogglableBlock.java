package me.matl114.logitech.core.Interface;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public interface MenuTogglableBlock {
   // public int getToggleSlot();
    public boolean[] getStatus(BlockMenu inv);
    public void toggleStatus(BlockMenu inv,boolean... result);
}
