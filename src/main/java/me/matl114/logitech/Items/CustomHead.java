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
    MOTOR("8cbca012f67e54de9aee72ff424e056c2ae58de5eacc949ab2bcd9683cec"),
    CORE("d78f2b7e5e75639ea7fb796c35d364c4df28b4243e66b76277aadcd6261337"),
    VSPACE("93e2f779147bc025487b27a37b7fc516d7489c589df97016eb8dc0f54d3bf29e"),
    BUSHIGEMEN("8064777a09c00a4a92eee74aba7c672bfea7f0384b256894ea261d5a915af8ee"),
    SUPPORT("6bf5374986e6c8e302ee3658d8553038b6d93abd323f4aae950624ccedaf905"),
    REVERSE("246f958ed3dbbb0cda4739b9b0959be8d03f6121936934c6600d3f74538511da")
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
