package me.matl114.logitech.Utils.UtilClass.ItemClass;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import me.matl114.logitech.Utils.ReflectUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;

public class ConstSlimefunItemStack extends SlimefunItemStack {
    public SlimefunItemStack data;
    public ItemMeta thismeta;
    Field lockedField;
    public ConstSlimefunItemStack(SlimefunItemStack stack){
        super(stack.getItemId(),stack);
        this.data =stack ;
        this.thismeta = stack.getItemMeta();
        this.lockedField=ReflectUtils.getDeclaredFieldsRecursively(this.getClass(),"locked").getFirstValue();
        try{
            Object locked=this.lockedField.get(this);
            this.lockedField.set(this, Boolean.FALSE);
            this.setItemMeta(data.getItemMeta());
            this.lockedField.set(this, locked);
        }catch (Throwable e){

        }
    }
    public ItemStack clone() {
        ItemStack stack= super.clone();
        if(stack instanceof SlimefunItemStack){
            try{
                Object locked=this.lockedField.get(stack);
                this.lockedField.set(stack, Boolean.FALSE);
                stack.setItemMeta(thismeta);
                this.lockedField.set(stack, locked);
            }catch (Throwable ignored){

            }
        }

        return stack;
    }


}
