package me.matl114.logitech.SlimefunItem.Machines.ManualMachines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.SlimefunItem.Machines.*;
import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.UtilClass.DisplayItemStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiBlockManual extends AbstractManual implements MultiCraftType {
    public List<ItemStack> displayedMemory;
    protected final int[] INPUT_SLOT=new int[]{4,5,6,7,8,13,14,15,16,17,22,23,24,25,26,31,32,33,34,35,40,41,42,43,44,49,50,51,52,53};
    protected final int[] OUTPUT_SLOT=new int[]{31,32,33,34,35,40,41,42,43,44,49,50,51,52,53,4,5,6,7,8,13,14,15,16,17,22,23,24,25,26};
    protected final int[] BORDER=new int[]{3,12,21,30,39,48,46};
    protected static final int[] MACHINEITEM_SLOT= new int[ ]{0,1,2,9,10,11,18,19,20};
    public MultiBlockMachine[] craftType ;
    protected static final ItemStack DISPLAY_DEFAULT_BKGROUND=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c没有匹配的多方块机器");
    protected static final int DISPLAYEITEM_SLOT=27;
    protected static final ItemStack INFO_ITEM=new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,"&6机制:"," ","&6在上方的3x3区域摆入多方块机器构造","&6即可加载配方","&6在右边放入物品进行快捷合成");
    protected static final int INFO_SLOT=28;
    protected static final ItemStack PREV=new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,"&3搜索上一个配方");
    protected static final int PREV_SLOT=36;
    protected static final ItemStack NEXT=new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE,"&3搜索下一个配方");
    protected static final int NEXT_SLOT=38;
    protected static final ItemStack CRAFT_ONE=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&3单次合成","&6左键&b合成 &d1次 &b当前物品","&6右键&b合成 &d16次 &b当前物品");
    protected static final int ONE_SLOT=45;
    protected static final ItemStack CRAFT_MUL=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,
            "&3批量合成","&6左键&b合成 &d64次 &b当前物品","&6右键&b合成 &d3456次 &b当前物品");
    protected static final int MUL_SLOT=47;
    protected static final ItemStack DISPLAY_NOMATCH=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&c没有匹配的配方");
    protected static final int RECIPE_ITEM_SLOT=37;
    protected static final int RECIPEBOOK_SHOW_SLOT=29;
    protected static final ItemStack RECIPEBOOK_SHOW_ITEM=new CustomItemStack(Material.BOOK,"&6点击查看配方","","&6但是并没有一键放置配方的功能");
    public MultiBlockManual(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         int energybuffer, int energyConsumption,
                         RecipeType... craftType){
        super(category,item,recipeType,recipe,energybuffer,energyConsumption,null);
    }
    public MultiBlockMachine[] getCraftTypes(){
        if(craftType==null||craftType.length<=0){
            HashMap<MultiBlockMachine,List<MachineRecipe>> types= RecipeSupporter.MULTIBLOCK_RECIPES;
            if(types==null||types.size()<=0){
                return craftType;
            }
            craftType=new MultiBlockMachine[types.size()];
            int cnt=0;
            for(MultiBlockMachine e :types.keySet()){
                craftType[cnt]=e;
                cnt++;
            }
        }return craftType;
    }
    public MachineRecipe getRecordRecipe(Location loc){
        int index=MultiCraftType.getRecipeTypeIndex(loc);

        if(index>=0&&index<getCraftTypes().length){
            int index2= getNowRecordRecipe(loc);
            if(index2>=0){
                return RecipeSupporter.MULTIBLOCK_RECIPES.get(getCraftTypes()[index]).get(index2);
            }
        }else if (index>0){
            MultiCraftType.setRecipeTypeIndex(loc,-1);
            setNowRecordRecipe(loc,-1);
        }
        return null;
    }
    public List<MachineRecipe> getMachineRecipes(Block b,BlockMenu inv){
        Location loc=inv.getLocation();
        int index=MultiCraftType.getRecipeTypeIndex(loc);
        if(index>=0&&index<getCraftTypes().length){
            return RecipeSupporter.MULTIBLOCK_RECIPES.get(getCraftTypes()[index]);
        }else if (index>0){
            MultiCraftType.setRecipeTypeIndex(loc,-1);
            setNowRecordRecipe(loc,-1);
        }
        return null;
    }
    public void constructMenu(BlockMenuPreset preset) {
        //空白背景 禁止点击
        int[] border = BORDER;
        int len=border.length;
        for(int var4 = 0; var4 < len; ++var4) {
            preset.addItem(border[var4], ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(DISPLAYEITEM_SLOT, DISPLAY_DEFAULT_BKGROUND, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INFO_SLOT, INFO_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(RECIPE_ITEM_SLOT,DISPLAY_NOMATCH, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(PREV_SLOT,PREV);
        preset.addItem(NEXT_SLOT,NEXT);
        preset.addItem(ONE_SLOT,CRAFT_ONE);
        preset.addItem(MUL_SLOT,CRAFT_MUL);
        preset.addItem(RECIPEBOOK_SHOW_SLOT,RECIPEBOOK_SHOW_ITEM);
    }
    public void newMenuInstance(BlockMenu menu, Block block){
        menu.addMenuOpeningHandler((player -> {
            MultiBlockManual.this.process(block,menu);
        }));
        menu.addMenuClickHandler(PREV_SLOT,
                (player, i, itemStack, clickAction)->{
                    if( getNowRecordRecipe(menu.getLocation())!=-1){
                        orderSearchRecipe(menu, Settings.REVERSE);
                        MultiBlockManual.this.updateMenu(menu,block,Settings.RUN);
                    }return false;
                }
        );
        menu.addMenuClickHandler(NEXT_SLOT,

                (player, i, itemStack, clickAction)->{
                    if(getNowRecordRecipe(menu.getLocation())!=-1){
                        orderSearchRecipe(menu,Settings.SEQUNTIAL);
                        MultiBlockManual.this.updateMenu(menu,block,Settings.RUN);

                    }return false;
                }
        );
        menu.addMenuClickHandler(ONE_SLOT,
                (player, i, itemStack, clickAction)->{
                    int limit=1;
                    if(clickAction.isRightClicked()){
                        limit=16;
                    }
                    craft(menu,limit);
                    MultiBlockManual.this.tick(block,menu,1);
                    return false;
                }
        );
        menu.addMenuClickHandler(MUL_SLOT,
                (player, i, itemStack, clickAction)->{
                    int limit=64;
                    if(clickAction.isRightClicked()){
                        limit=3456;
                    }

                    craft(menu,limit);
                    MultiBlockManual.this.tick(block,menu,1);
                    return false;
                }
        );
        menu.addMenuClickHandler(RECIPEBOOK_SHOW_SLOT,
                ((player, i, itemStack, clickAction) -> {
                    int index=MultiCraftType.getRecipeTypeIndex(menu.getLocation());
                    if(index>=0){
                        MultiBlockMachine multiblock=getCraftTypes()[index];
                        MenuUtils.createMRecipeListDisplay(multiblock.getItem(),RecipeSupporter.MULTIBLOCK_RECIPES.get(multiblock),
                                ((player1, i1, itemStack1, clickAction1) -> {
                                    menu.open(player1);
                                    return false;
                                })
                                ).open(player);
                    }else{

                        player.sendMessage(ChatColors.color("&e您所摆放的方式并不构成多方块机器"));

                       // player.sendMessage();
                    }
                    return false;
                })
                );
        updateMenu(menu,block,Settings.INIT);
    }
    public void updateMenu(BlockMenu inv,Block block,Settings mod){
        if(mod==Settings.INIT){
            parseRecipe(inv);
            orderSearchRecipe(inv,Settings.SEQUNTIAL);
        }
        Location  loc=inv.getLocation();
        int index_=MultiCraftType.getRecipeTypeIndex(loc);
        if(index_>=0){
            MultiBlockMachine nowMachine=getCraftTypes()[index_];
            List<MachineRecipe> mRecipe=RecipeSupporter.MULTIBLOCK_RECIPES.get(nowMachine);
            inv.replaceExistingItem(DISPLAYEITEM_SLOT,AddUtils.addLore(nowMachine.getItem(),"&8检测出的多方块机器"));

        int index= RecipeCache.getLastRecipe(loc);
        int indexRecord=getNowRecordRecipe(loc);
        if(index!=-1){
            MachineRecipe getRecipe=mRecipe.get(index);
            inv.replaceExistingItem(RECIPE_ITEM_SLOT, AddUtils.addLore(getRecipe.getOutput()[0],
                    "&8匹配的产物", "&8若有多输出则显示第一个", "&8配方编号: " + index));
            if(index!=indexRecord||mod==Settings.INIT) {

                setNowRecordRecipe(loc,index);
                return;
            }
        }else{

            if(indexRecord!=-1||mod==Settings.INIT){
                inv.replaceExistingItem(RECIPE_ITEM_SLOT,DISPLAY_NOMATCH);
                setNowRecordRecipe(loc,-1);
            }
        }
        }else{
            inv.replaceExistingItem(DISPLAYEITEM_SLOT,DISPLAY_DEFAULT_BKGROUND);
            inv.replaceExistingItem(RECIPE_ITEM_SLOT,DISPLAY_NOMATCH);
            setNowRecordRecipe(loc,-1);
        }
    }
    @Override
    public void onBreak(BlockBreakEvent e, BlockMenu inv){
        super.onBreak(e,inv);
        Location l=inv.getLocation();
        inv.dropItems(l,MACHINEITEM_SLOT);
    }
    public boolean parseRecipe(BlockMenu menu){
        Location loc=menu.getLocation();
        ItemStack[] multimachineRecipe=new ItemStack[9];
        for(int i=0;i<9;i++){
            multimachineRecipe[i]=menu.getItemInSlot(MACHINEITEM_SLOT[i]);
        }
        MultiBlockMachine[] mulM=getCraftTypes();
        int offset=MultiCraftType.getRecipeTypeIndex(loc);
        if(offset<=0){offset=0;}
        int index;
        for(int i=0;i<mulM.length;i++){
            index=(i+offset)%mulM.length;
            ItemStack[] recipe=mulM[index].getRecipe();
            boolean match=true;
            if(recipe!=null&&recipe.length>=9){
                for(int j=0;j<9;j++){
                   if(recipe[j]==null||multimachineRecipe[j]==null){
                       if(recipe[j]!=multimachineRecipe[j]){

                          match=false;
                          break;
                       }
                   }else{
                       if(recipe[j].getType()!=multimachineRecipe[j].getType()){
                           match=false;
                           break;
                       }
                   }
                }
            }else {
                match=false;
                break;
            }
            if(match==true){
                MultiCraftType.setRecipeTypeIndex(loc,index);
                return true;
            }
        }
        MultiCraftType.setRecipeTypeIndex(loc,-1);
        //清空当前搜索缓存
        return false;
    }
    public void process(Block b, BlockMenu inv){
        //only works when has viewer.
        if(inv!=null&&inv.hasViewer()){
            if(parseRecipe(inv)){
            Location  loc=inv.getLocation();
            MachineRecipe getRecipe=CraftUtils.matchNextRecipe(inv,getInputSlots(),getMachineRecipes(b,inv),true,Settings.SEQUNTIAL);

            if(getRecipe==null){
                RecipeCache.setLastRecipe(loc,-1);
            }
            }
            updateMenu(inv ,b,Settings.RUN);
        }
    }
    public int[] getInputSlots(){
        return INPUT_SLOT;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOT;
    }
    public List<ItemStack> getDisplayRecipes(){
        if(displayedMemory==null||displayedMemory.isEmpty()) {
            displayedMemory=new ArrayList<>(){{
                for(SlimefunItem item : RecipeSupporter.MULTIBLOCK_RECIPES.keySet()){
                    add(new DisplayItemStack(new CustomItemStack(Material.BOOK,"&f支持的多方块机器","&8将机器配方置于指定槽位以进行合成")));
                    add(new DisplayItemStack(item.getItem()));
                }
            }};
        }
        return displayedMemory;
    }
}
