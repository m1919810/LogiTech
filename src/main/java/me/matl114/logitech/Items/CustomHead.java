package me.matl114.logitech.Items;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.net.URL;

public enum CustomHead {
    MATL114("58d9d8a7927fb859a313fb0068f1d368c9dee7d0adacbbd35ffa4d998ff88deb"),
    LOGIC_REACTOR("87e04b5b252a6f4e6473ea952450a9d8d8db4cd657ffd9b419c25c6c80408f3e"),
    BUG_CRATFER("f2fdb5a1477cb38109030fc9e41691668e4fe05c86aad46c6ad01f4ce4dabd52"),
    MOTOR("8cbca012f67e54de9aee72ff424e056c2ae58de5eacc949ab2bcd9683cec")
    ;
    private ItemStack item;
    public ItemStack getItem() {
        return item;
    }
    CustomHead(String hashcode){
        item= new CustomItemStack(SlimefunUtils.getCustomHead(hashcode));
    }
    public static String getHash(ItemStack item){
        if(item!=null&&(item.getType()== Material.PLAYER_HEAD||item.getType()==Material.PLAYER_WALL_HEAD)){
            ItemMeta meta=item.getItemMeta();
            if(meta instanceof SkullMeta){
                try{
                    URL t=((SkullMeta)meta).getOwnerProfile().getTextures().getSkin();
                    String path=t.getPath();
                    String[] parts=path.split("/");
                    return parts[parts.length-1];
                }catch (Throwable t){
                }
            }
        }
        return null;
    }
    public static ItemStack getHead(String hashcode){
        try{
            return new CustomItemStack(SlimefunUtils.getCustomHead(hashcode));
        }catch (Throwable t){
            return new ItemStack(Material.PLAYER_HEAD);
        }
    }
}
