package me.matl114.logitech.Listeners.Listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop.SmithInterfaceProcessor;
import me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop.SmithingInterface;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.CargoClass.ContainerBlockMenuWrapper;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.matl114.logitech.utils.UtilClass.RecipeClass.MultiCraftingOperation;
import me.matl114.matlib.Implements.Slimefun.core.CustomRecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.*;

public class SmithInterfaceListener implements Listener {
    private CustomRecipeType craftTable;
    public SmithInterfaceListener() {
        this.craftTable= SmithInterfaceProcessor.INTERFACED_CRAFTTABLE;

    }
    private final HashMap<Inventory, Location> openingCraftInventory= new HashMap<>();
 //   private final HashMap<AnvilInventory,Location> openingAnvilTable= new HashMap<>();

    private final ItemStack INTERFACED_NO_RECIPE=new CustomItemStack(Material.BARRIER,"&a点击进行合成","&7该工作方块已成功接入锻铸合成端口","&c暂无匹配的配方!");
    private final ItemStack INTERFACED_CRAFTABLE=new CustomItemStack(Material.CRAFTING_TABLE,"&a点击进行合成","&7该工作方块已成功接入锻铸合成端口","&a成功寻找到可行的合成配方","&7点击后将于接口中输出合成结果");
    private final ItemStack INTERFACED_ANVIL=new CustomItemStack(Material.ANVIL,"&a点击进行锻造","&7该工作方块已成功接入锻铸合成端口","&a成功寻找到可行的锻造操作","&7点击后将于接口中输出锻造结果");
    private final ItemStack INTERFACED_SMITH=new CustomItemStack(Material.SMITHING_TABLE,"&a点击进行锻造","&7该工作方块已成功接入锻铸合成端口","&a成功寻找到可行的锻造操作","&7点击后将于接口中输出锻造结果");
    private final ItemStack INTERFACED_GRIND = new CustomItemStack(Material.GRINDSTONE,"&a点击进行卸载","&7该工作方块已成功接入锻铸合成端口","&a成功寻找到可行的卸载方案","&7点击后将于接口中输出锻造结果");
    public boolean isSupportType(Inventory inventory) {
        return inventory instanceof CraftingInventory||inventory instanceof AnvilInventory ||inventory instanceof SmithingInventory ||inventory instanceof GrindstoneInventory; /*can add more types*/
    }
    public void addInventory(Inventory inventory,Location location) {
        openingCraftInventory.put(inventory,location);
        if(inventory instanceof CraftingInventory c) {
            c.setResult(INTERFACED_NO_RECIPE);
        }else if(inventory instanceof AnvilInventory anvil) {
            if(anvil.getSize()>2){
                anvil.setItem(2,INTERFACED_NO_RECIPE);
            }
        }else if(inventory instanceof SmithingInventory smith){
            if(smith.getSize()>3){
                smith.setItem(3,INTERFACED_NO_RECIPE);
            }
        }else if(inventory instanceof GrindstoneInventory grind) {
            if(grind.getSize()>2){
                grind.setItem(2,INTERFACED_NO_RECIPE);
            }
        }
    }
    public void removeInventory(Inventory inventory) {
        openingCraftInventory.remove(inventory);
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void onOpenInventory(InventoryOpenEvent e) {
        //record inventory for quick match
        Inventory inventory = e.getInventory();
        if(isSupportType(inventory) ) {
            Location craftTableLocation = inventory.getLocation();
            if(craftTableLocation!=null&& WorldUtils.isBlockLocation(craftTableLocation)){
                var re=SmithingInterface.getAdjacentInterface(craftTableLocation);
                if(re!=null&&re.getSecondValue() instanceof SmithInterfaceProcessor outport) {
                    addInventory(inventory,re.getFirstValue());
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void onCloseInventory(InventoryCloseEvent e) {
        //record inventory for quick match
        Inventory inventory = e.getInventory();
        if(isSupportType(inventory) && openingCraftInventory.containsKey(inventory)) {
            var re=inventory.getViewers();
            if(re!=null&&(re.size()>=2||re.stream().anyMatch(p->p.getUniqueId().equals(e.getPlayer().getUniqueId())))) {
                //if any other player is viewing this inventory
                return;
            }
            //no one is viewing this, remove this
            removeInventory(inventory);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = false)
    public void onCraftClick(InventoryClickEvent event){
        Inventory inventory = event.getClickedInventory();
        int rawSlot=event.getRawSlot();
        if( openingCraftInventory.containsKey(inventory)){
            if(rawSlot==0&& inventory instanceof CraftingInventory craftingInventory){
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
                onCraftTableCraft(craftingInventory,event.getWhoClicked());
            }else if(event.getRawSlot()==2 &&inventory instanceof AnvilInventory anvilInventory){
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
                onAnvilCraft(anvilInventory,event.getWhoClicked());

            }else if(event.getRawSlot()==3 && inventory instanceof SmithingInventory smithingInventory){
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
                onSmithCraft(smithingInventory,event.getWhoClicked());
            }else if(event.getRawSlot()==2 && inventory instanceof GrindstoneInventory grindstoneInventory){
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
                onGrindCraft(grindstoneInventory,event.getWhoClicked());
            }
        }
        if(!event.isCancelled()){
            Inventory inv=event.getWhoClicked().getOpenInventory().getTopInventory();
            if(openingCraftInventory.containsKey(inv)){
                if(inv instanceof AnvilInventory anvilInventory){//trigger refresh any click
                    onAnvilPrepare(anvilInventory);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = false)
    public void onPrepareCraft(PrepareItemCraftEvent e) {
        if(openingCraftInventory.containsKey(e.getInventory())) {
            onCraftTablePrepare(e.getInventory());
        }
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = false)
    public void onAnvilLogicStop(PrepareAnvilEvent e) {
        //Debug.logger("on Anvil Perpare");
        //boolean failed=e.getResult()==null||e.getResult().getType().isAir();
        if(openingCraftInventory.containsKey(e.getInventory())) {;
            AnvilInventory inv=e.getInventory();
            if(onAnvilPrepare(inv)){
                e.setResult(INTERFACED_ANVIL);
            }else {
                e.setResult(INTERFACED_NO_RECIPE);
            }
            //if(failed){
                //尝试同步inventory
                //当铁砧合成在内部被判定为不能的时候,不会向客户端发送inv变动，需要我们手动发送
            List<HumanEntity> viewers=inv.getViewers();
            Schedules.launchSchedules(()->{
                viewers.forEach(p->{if(p instanceof Player player){ player.updateInventory();}});
            },0,true,0);
            //}
        }
    }
    @EventHandler(priority = EventPriority.NORMAL,ignoreCancelled = false)
    public void onSmithRecipeCommonStop(PrepareSmithingEvent e){
        if(!openingCraftInventory.containsKey(e.getInventory())) {
            if(onSmithCommonNotPrepare(e.getInventory())){
                e.setResult(null);
            }
        }
    }
    @EventHandler(priority = EventPriority.NORMAL,ignoreCancelled = false)
    public void onSmithRecipeComonStop(InventoryClickEvent e){
        if(e.getInventory() instanceof SmithingInventory smithingInventory){
            if(!openingCraftInventory.containsKey(smithingInventory)) {
                if(onSmithCommonNotPrepare(smithingInventory)){
                    if(e.getRawSlot() == 4){
                        e.setResult(Event.Result.DENY);
                        e.setCancelled(true);
                    }
                    smithingInventory.setResult(null);
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = false)
    public void onSmithPrepare(PrepareSmithingEvent e){
        SmithingInventory smithingInventory=e.getInventory();
        if(openingCraftInventory.containsKey(smithingInventory)) {
            if(onSmithPrepare(smithingInventory)){
                e.setResult(INTERFACED_SMITH);
            }else {
                e.setResult(INTERFACED_NO_RECIPE);
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = false)
    public void onGrindPrepare(PrepareGrindstoneEvent e){
        GrindstoneInventory grindstoneInventory=e.getInventory();
        if(openingCraftInventory.containsKey(grindstoneInventory)) {
            if (onGrindPrepare(grindstoneInventory)){
                e.setResult(INTERFACED_GRIND);
            }else {
                e.setResult(INTERFACED_NO_RECIPE);
            }
        }
    }



    public <T extends Inventory,W extends Object> void onPlayerCraftCommon(T inventory, HumanEntity player, Function<T,W> craftProcess, BiFunction<W,Location, MultiCraftingOperation> callBack, Consumer<T> failCallback){
        Location interfaceLocation = openingCraftInventory.get(inventory);
        if(DataCache.getSfItem(interfaceLocation) instanceof SmithInterfaceProcessor outport) {
            if(outport.acceptable(DataCache.getMenu(interfaceLocation))) {
                W result=craftProcess.apply(inventory);
                if(result!=null) {
                    var output=callBack.apply(result, interfaceLocation);
                    if(output!=null) {
                        outport.acceptProgress(output,interfaceLocation);
                        AddUtils.sendMessage(player,"&6[锻铸工坊] &a成功合成!");
                    }else{
                        AddUtils.sendMessage(player,"&6[锻铸工坊] &c该操作已被阻止!");
                    }
                }else {
                    AddUtils.sendMessage(player,"&6[锻铸工坊] &c未知的合成配方或操作方式!");
                    failCallback.accept(inventory);
                }
            }else {
                AddUtils.sendMessage(player,"&6[锻铸工坊] &c该合成端口空间已满或被占用!");
            }
        }
    }
    public Function<CraftingInventory, Pair<MachineRecipe,IntFunction<ItemGreedyConsumer[]>>> CRAFT_PROCESSOR=(inventory1)->{
        BlockMenu inv=ContainerBlockMenuWrapper.getContainerBlockMenu(inventory1,inventory1.getLocation(),10);
        return CraftUtils.matchNextShapedRecipe(inv,new int[]{1,2,3,4,5,6,7,8,9},SmithInterfaceProcessor.getInterfacedCrafttableRecipes(),64,false,Settings.SEQUNTIAL,CraftUtils.getpusher);
    };
    public Function<AnvilInventory,Supplier<MultiCraftingOperation>> ANVIL_PROCESSOR=(inventory1)->{
        inventory1.setMaximumRepairCost(100_000);
        var logics=SmithInterfaceProcessor.getAnvilLogics();
        Supplier<MultiCraftingOperation> matchedLogic=null;
        for(var logic:logics) {
            if((matchedLogic=logic.apply(inventory1))!=null){
                return matchedLogic;
            }
        }
        return null;
    };
    public Function<SmithingInventory,Supplier<MultiCraftingOperation>> SMITH_PROCESSOR=(smithingInventory -> {
        var logics = SmithInterfaceProcessor.getSmithLogics();
        Supplier<MultiCraftingOperation> matchedLogic=null;
        for (var logic:logics.computeIfAbsent(SmithInterfaceProcessor.SmithOperation.BOTH,(b)->new LinkedHashSet<>())){
            if((matchedLogic=logic.apply(smithingInventory))!=null){
                return matchedLogic;
            }
        }
        SmithInterfaceProcessor.SmithOperation operation;
        Recipe recipe = smithingInventory.getRecipe();
        if(recipe instanceof SmithingTransformRecipe smithingRecipe) {
            operation = SmithInterfaceProcessor.SmithOperation.TRANSFORM;
        }else if(recipe instanceof SmithingTrimRecipe smithingRecipe) {
            operation = SmithInterfaceProcessor.SmithOperation.TRIM;
        }else{
            operation = null;
        }
        if(operation!=null) {
            for (var logic:logics.computeIfAbsent(operation,(b)->new LinkedHashSet<>())){
                if((matchedLogic=logic.apply(smithingInventory))!=null){
                    return matchedLogic;
                }
            }
        }
        return null;
    });
    public Function<GrindstoneInventory,Supplier<MultiCraftingOperation>> GRIND_PROCESSOR=(grindInventory->{
        var logics = SmithInterfaceProcessor.getGrindStoneLogics();
        Supplier<MultiCraftingOperation> matchedLogic=null;
        for (var logic :logics){
            if((matchedLogic=logic.apply(grindInventory))!=null){
                return matchedLogic;
            }
        }
        return null;
    });
    public void onCraftTableCraft(CraftingInventory inventory, HumanEntity player){
        onPlayerCraftCommon(inventory,player,CRAFT_PROCESSOR,(result,loc)->{
            ItemStack output=result.getFirstValue().getOutput()[0];
            SlimefunItem item=SlimefunItem.getByItem(output);
            if(item!=null&&item.isDisabled()){
                inventory.setResult(INTERFACED_CRAFTABLE);
                AddUtils.sendMessage(player,"&6[锻铸工坊] &c物品被禁用!");
                return null;
            }else{
                inventory.setResult(INTERFACED_NO_RECIPE);
                return new MultiCraftingOperation(result.getSecondValue().apply(64),result.getFirstValue().getTicks());
            }
        },(inventory1)->{
            inventory1.setResult(INTERFACED_NO_RECIPE);
        });
    }

    public void onAnvilCraft(AnvilInventory inventory,HumanEntity player){
        onPlayerCraftCommon(inventory,player,ANVIL_PROCESSOR,(matchLogic,loc)->{
            if(player instanceof Player p){
                int level=p.getLevel();
                int expNeed=inventory.getRepairCost();
                if(level<expNeed){
                    AddUtils.sendMessage(player,"&6[锻铸工坊] &c所需经验不足!");
                    inventory.setItem(2,INTERFACED_ANVIL);
                    return null;
                }else{
                    p.setLevel(level-expNeed);
                    inventory.setRepairCost(0);
                    MultiCraftingOperation out=matchLogic.get();
                    inventory.setItem(2,INTERFACED_NO_RECIPE);
                    return out;
                }
            }
            inventory.setItem(2,INTERFACED_NO_RECIPE);
            return null;
        },(inventory1)->{
            inventory1.setItem(2,INTERFACED_NO_RECIPE);
        });
    }
    public void onSmithCraft(SmithingInventory inventory,HumanEntity player){
        onPlayerCraftCommon(inventory,player,SMITH_PROCESSOR,(matchLogic,loc)->{
            var output=matchLogic.get();
            inventory.setResult(INTERFACED_NO_RECIPE);
            return output;
        },(smith)->{
            smith.setResult(INTERFACED_NO_RECIPE);
        });
    }
    public void onGrindCraft(GrindstoneInventory inventory,HumanEntity player){
        onPlayerCraftCommon(inventory,player,GRIND_PROCESSOR,(matchLogic,loc)->{
            var output=matchLogic.get();
            inventory.setItem(2,INTERFACED_NO_RECIPE);
            return output;
        },(grind)->{
            grind.setItem(2,INTERFACED_NO_RECIPE);
        });
    }
    public boolean onCraftTablePrepare(CraftingInventory inventory) {
        var result= CRAFT_PROCESSOR.apply(inventory);
        if(result!=null) {
            inventory.setResult(INTERFACED_CRAFTABLE);
            return true;
        }else {
            inventory.setResult(INTERFACED_NO_RECIPE);
            return false;
        }
    }
    public boolean onAnvilPrepare(AnvilInventory inventory) {
        var result= ANVIL_PROCESSOR.apply(inventory);
        if(inventory.getSize()>=3){
            boolean re=result!=null;
            ItemStack set=re?INTERFACED_ANVIL:INTERFACED_NO_RECIPE;
            inventory.setItem(2,set);
            return re;
        }
        return false;
    }
    public boolean onSmithPrepare(SmithingInventory inventory) {
        var result = SMITH_PROCESSOR.apply(inventory);
        if(inventory.getSize()>=4){
            boolean re=result!=null;
            ItemStack set=re?INTERFACED_SMITH:INTERFACED_NO_RECIPE;
            inventory.setItem(3,set);
            return re;
        }
        return false;
    }
    public boolean onSmithCommonNotPrepare(SmithingInventory inventory) {
        if(inventory.getRecipe() instanceof SmithingRecipe recipe){
            if(AddUtils.isNamespace(recipe.getKey())){
                if(SmithInterfaceProcessor.isSmithingInterfaceRecipe(recipe)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean onGrindPrepare(GrindstoneInventory inventory) {
        var result = GRIND_PROCESSOR.apply(inventory);
        if(inventory.getSize()>=3){
            boolean re=result!=null;
            ItemStack set=re?INTERFACED_GRIND:INTERFACED_NO_RECIPE;
            inventory.setItem(2,set);
            return re;
        }
        return false;
    }

}
