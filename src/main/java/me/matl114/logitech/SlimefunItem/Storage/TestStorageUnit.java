package me.matl114.logitech.SlimefunItem.Storage;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MenuUtils;
import me.matl114.logitech.Utils.UtilClass.MenuFactory;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class TestStorageUnit extends AbstractMachine {
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
    private static final int[] OUTPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};

    public TestStorageUnit(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                            int energybuffer, int energyConsumption){
        super(category,item , recipeType, recipe,energybuffer,energyConsumption);



    }
    public void addInfo(ItemStack item){

    }
    public void constructMenu(BlockMenuPreset preset){
        preset.setSize(54);
        preset.addMenuClickHandler(18,(player, i, itemStack, clickAction)->{

            new MenuFactory(MenuUtils.SIMPLE_MENU,"测试他妈的",4){
                public void init(){
                    setDefaultNPSlots();
                    addInventory(0,new ItemStack(Material.COMMAND_BLOCK));
                }
            }.build().openPages(player,1);
            return false;


        });
        preset.addMenuClickHandler(19,(player,i,itemstack,clickAction)->{
            Debug.logger("override failed");
            return false;
        });
        preset.addMenuClickHandler(19,(player,i,itemstack,clickAction)->{
            Debug.logger("override success");
            return false;
        });
    }
    public  int[] getInputSlots(){
        return INPUT_SLOT;
    }

    /**
     * cargo and IO
     * @return
     */
    public static ItemStack rand= AddUtils.randItemStackFactory(
            new LinkedHashMap<>(){{
                put("COBBLESTONE",1);
                put("DIAMOND",1);
            }}
    );
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<MachineRecipe>();
    }
    public void process(Block b, BlockMenu preset){
        ItemStack it=preset.getItemInSlot(0);
        if(it!=null){
            it.setAmount(114514);
        }
        ItemStack stack=new ItemStack(Material.COMMAND_BLOCK);
        stack.setAmount(-128);
        preset.replaceExistingItem(2,stack);
        preset.replaceExistingItem(3,rand);
        ItemStack a=preset.getItemInSlot(4);
        if(a!=null){
            //Debug.logger("a="+a.getItemMeta().toString());
            ItemMeta as=a.getItemMeta();
            //Debug.logger("stack="+as.getClass());
            Class clazz=as.getClass();
            try{
                Field getlore=clazz.getDeclaredField("lore");
                getlore.setAccessible(true);
                Object o=getlore.get(as);
                Debug.logger("o="+o.toString());
            }
            catch(Throwable e){
                Debug.logger("invoke failed ");
                e.printStackTrace();

            }

        }
        Location loc=preset.getLocation();

        new BukkitRunnable(){
            public void run(){
                preset.dropItems(loc,IntStream.range(27,54).toArray());
            }
        }.runTask(MyAddon.getInstance());



    }
}
