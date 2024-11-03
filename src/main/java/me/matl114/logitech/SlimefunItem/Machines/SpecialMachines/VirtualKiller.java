package me.matl114.logitech.SlimefunItem.Machines.SpecialMachines;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.RepairedSpawner;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.Items.EntityFeat;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.Utils.UtilClass.MenuClass.DataMenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VirtualKiller extends AbstractMachine {
    protected final int[] INPUT_SLOTS=new int[0];
    protected final int[] OUTPUT_SLOTS=new int[]{
            18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53
    };
    protected final int[] BORDER=new int[]{
            0,1,2,3,5,6,7,8
    };
    protected final int[] OUTPUT_BORDER=new int[]{9,10,11,12,14,15,16,17};
    protected final int INFO_SLOT=13;
    protected final int MULTIPLY;
    protected final int SPAWNER_SLOT=4;

    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }
    public ItemStack getInfoItem(EntityType type,int amount) {
        return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
                "&6机制",
                "&7上方放入带生物种类的任意笼子",
                "&7下方输出该生物可能的特殊掉落物",
                "&7输出倍率=%d".formatted(MULTIPLY),
               AddUtils.concat( "&7当前生物: &a",type==null?"&c无生物": ChatUtils.humanize(type.name())),
                AddUtils.concat("&7当前数量: &a",String.valueOf(amount)));
    }


    public VirtualKiller(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                           int energybuffer, int energyConsumption,int multiply){
        super(category, item, recipeType, recipe, energybuffer, energyConsumption);
        this.MULTIPLY=multiply;
        this.setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow("&f机制",
                        "&7在机器输入槽内插入某种生物的任意类型刷怪笼",
                        "&7包括破损的刷怪笼,已修复的刷怪笼,带nbt的刷怪笼掉落物,等等",
                        "&7机器即可执行产出,会产出普通掉落物和所有附属加载的粘液特殊掉落物",
                        "&a注明:普通掉落物部分由ChatGPT生成,并由@haiman补充完整,若有欠缺欢迎补充"),null
        ));
    }
    public List<MachineRecipe> provideDisplayRecipe(){
        HashMap<EntityType,ItemStack[]> map=getEntityMap();
        List<MachineRecipe> providedRecipes=new ArrayList<>();
        for(Map.Entry<EntityType,ItemStack[]> entry:map.entrySet()){
            if(entry.getValue().length!=0)
                providedRecipes.add(MachineRecipeUtils.stackFrom(0,new ItemStack[]{ EntityFeat.getItemFromEntityType(entry.getKey())},entry.getValue()));
        }
        return providedRecipes;
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len=border.length;
        for(int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=OUTPUT_BORDER;
        len=border.length;
        for(int i=0;i<len;i++){
            preset.addItem(border[i],ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(INFO_SLOT,getInfoItem(null,0),ChestMenuUtils.getEmptyClickHandler());
        preset.setSize(54);
    }
    public DataMenuClickHandler createDataHolder(){
        return new DataMenuClickHandler() {
            //0 为 数量 1 为 电力
            int amount;
            Object spawnerType;
            public int getInt(int i){
                return amount;
            }
            public void setInt(int i, int val){
                amount=val;
            }
            public Object getObject(int i){
                return spawnerType;
            }
            public void setObject(int i, Object val){
                spawnerType=val;
            }
            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                return false;
            }
        };
    }
    public final int DATA_SLOT=0;
    public DataMenuClickHandler getDataHolder(Block b, BlockMenu inv){
        ChestMenu.MenuClickHandler handler=inv.getMenuClickHandler(DATA_SLOT);
        if(handler instanceof DataMenuClickHandler dh){return dh;}
        else{
            DataMenuClickHandler dh=createDataHolder();
            inv.addMenuClickHandler(DATA_SLOT,dh);
            return dh;
        }
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        menu.addMenuOpeningHandler(player -> updateMenu(menu,block,Settings.RUN));
        menu.addMenuCloseHandler(player -> updateMenu(menu,block,Settings.RUN));
        updateMenu(menu,block,Settings.INIT);
    }
    public void updateMenu(BlockMenu menu,Block block,Settings mod){
        parseItem(menu,block);
    }
    public List<MachineRecipe> getMachineRecipes(){
        return new ArrayList<MachineRecipe>();
    }

    public void parseItem(BlockMenu menu, Block block){
        DataMenuClickHandler handler= getDataHolder(block,menu);
        ItemStack stack=menu.getItemInSlot(SPAWNER_SLOT);
        EntityType e=EntityFeat.getEntityTypeFromItem(stack);
        if(e!=null){
            int amount=stack.getAmount();
            handler.setInt(0,amount);
            handler.setObject(0,e);
            menu.replaceExistingItem(INFO_SLOT,getInfoItem(e,amount));
            return;
        }
        handler.setInt(0,0);
        handler.setObject(0,null);
        menu.replaceExistingItem(INFO_SLOT,getInfoItem(null,0));
    }
    public HashMap<EntityType,ItemStack[]> getEntityMap(){
        return RecipeSupporter.ENTITY_DROPLIST;
    }
    public void process(Block b, BlockMenu menu, SlimefunBlockData data){
        if(menu.hasViewer()){
            updateMenu(menu,b,Settings.RUN);
        }
        DataMenuClickHandler handler= getDataHolder(b,menu);
        Object entity=handler.getObject(0);
        int amount=handler.getInt(0);
        if(entity!=null&&amount>0){
            if(entity instanceof EntityType et){
                progressorCost(b,menu);
                CraftUtils.multiPushItems(getEntityMap().get(et),menu,getOutputSlots(),this.MULTIPLY,this.CRAFT_PROVIDER);
            }
        }
    }
    public void onBreak(BlockBreakEvent event,BlockMenu inv){
        super.onBreak(event,inv);
        if(inv!=null){
            inv.dropItems(inv.getLocation(),SPAWNER_SLOT);
        }
    }
}
