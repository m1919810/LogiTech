package me.matl114.logitech.SlimefunItem.Items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemDropHandler;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.matl114.logitech.Depends.SupportedPluginManager;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EntityFeat extends ItemWithHandler<ItemDropHandler> {
    protected final  int MIDDLE_MERGE=3;
    protected final  int SUPER_MERGE=9;
    public  EntityFeat(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(itemGroup, item, recipeType, recipe);
        ItemStack resultItemStack=generateSpawnerFrom(EntityType.ZOMBIE,8,60,64,6,10,true);
        this.setDisplayRecipes(
                Utils.list(
                        AddUtils.getInfoShow("&f机制",
                                "&7将该物品丢出以合成普通的刷怪笼",
                                "&7当一次性丢出数量不足一组时,将会按%d:1合成普通刷怪笼".formatted(MIDDLE_MERGE),
                                "&7当一次性丢出数量达到%d时,将会按%d:1合成&e超频刷怪笼&7,&a其生成速率等数据将被大幅提高".formatted(SUPER_MERGE,SUPER_MERGE)),
                        resultItemStack
                )
        );
    }
    public static ItemStack getItemFromEntityType(EntityType entityType) {
        ItemStack item = AddItem.ENTITY_FEAT.clone();
        ItemMeta meta = item.getItemMeta();

        // Fixes #2583 - Proper NBT handling of Spawners
        if(entityType!=null&&entityType.isSpawnable())
            if (meta instanceof BlockStateMeta stateMeta) {
                BlockState state = stateMeta.getBlockState();

                if (state instanceof CreatureSpawner spawner) {
                    spawner.setSpawnedType(entityType);
                }

                stateMeta.setBlockState(state);
            }

        // Setting the lore to indicate the Type visually
        List<String> lore = meta.getLore();
        boolean hasTypeLore=false;
        for (int i = 0; i < lore.size(); i++) {
            String currentLine = lore.get(i);
            if (currentLine.contains("<Type>") || currentLine.contains("<类型>")) {
                String typeName = ChatUtils.humanize(entityType.name());
                lore.set(i, currentLine.replace("<Type>", typeName).replace("<类型>", typeName));
                hasTypeLore=true;
                break;
            }
        }
        if(!hasTypeLore){
            lore.add("");
            lore.add("&7类型: "+ AddUtils.color((entityType==null||entityType==EntityType.UNKNOWN||!entityType.isSpawnable())?"类型":ChatUtils.humanize(entityType.name())));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    /**
     * delay=200 maxNearby=6 requirePlayerRange =16 spawnRange=4
     * @param type
     * @param delay
     * @return
     */
    public final static String SPAWNER_ATTRIBUTE=AddUtils.resolveColor(  AddUtils.color("刷怪笼属性:"));
    public static ItemStack generateSpawnerFrom(EntityType type,boolean displayAttribute){
        return generateSpawnerFrom(type,200,6,16,4,4,displayAttribute);
    }
    public static ItemStack generateSpawnerFrom(EntityType type,int delay,int maxNearbyEntities,int requirePlayerRange,int spawnRange,int spawnCount,boolean displayAttribute){

        ItemStack item = AddItem.SAMPLE_SPAWNER.clone();
        if(type==null||!type.isSpawnable()){
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        if(meta instanceof BlockStateMeta bm){
            BlockState state = bm.getBlockState();
            if(state instanceof CreatureSpawner spawner){
                spawner.setSpawnedType(type);
                spawner.setMinSpawnDelay(delay);
                spawner.setMaxSpawnDelay(4*delay);
                spawner.setMaxNearbyEntities(maxNearbyEntities);
                spawner.setRequiredPlayerRange(requirePlayerRange);
                spawner.setSpawnRange(spawnRange);
                spawner.setSpawnCount(spawnCount);
            }
            bm.setBlockState(state);
        }
        if(displayAttribute){
            List<String> lore =new ArrayList<>();
            lore.add(SPAWNER_ATTRIBUTE);
            lore.add(AddUtils.resolveColor( AddUtils.concat("&7类型: &b",ChatUtils.humanize(type.name()))));
            lore.add(AddUtils.resolveColor( AddUtils.concat("&7最小生成延迟: &b",String.valueOf(delay))));
            lore.add(AddUtils.resolveColor( AddUtils.concat("&7最大邻近实体: &b",String.valueOf(maxNearbyEntities))));
            lore.add(AddUtils.resolveColor( AddUtils.concat("&7玩家激活范围: &b",String.valueOf(requirePlayerRange))));
            lore.add(AddUtils.resolveColor( AddUtils.concat("&7最大生成范围: &b",String.valueOf(spawnRange))));
            lore.add(AddUtils.resolveColor( AddUtils.concat("&7单次生成数量: &b",String.valueOf(spawnCount))));
            meta.setLore(lore);
        }
        if(delay<20){
            meta.setDisplayName(AddUtils.resolveColor( AddUtils.color("超频刷怪笼")));
        }
        item.setItemMeta(meta);
        return item;
    }
    public static EntityType getEntityTypeFromItem(ItemStack stack) {
        if(stack!=null&&stack.getType()== Material.SPAWNER){
            ItemMeta meta=stack.getItemMeta();
            if(meta instanceof BlockStateMeta bsm){
                BlockState state=bsm.getBlockState();
                if(state instanceof CreatureSpawner cs){
                    return cs.getSpawnedType();
                }
            }
        }
        return null;
    }
    public boolean onItemDrop(PlayerDropItemEvent var1, Player var2, Item var3){
        if(isItem(var3.getItemStack())){
            if(canUse(var2,true)){
                Schedules.launchSchedules(
                        ()->craftToSpawmers(var2, var3)
               ,30,true,0 );
            }
            return true;
        }
        return false;
    }
    public void craftToSpawmers(Player var2, Item var3){
        if(!var3.isValid()){
            return;
        }
        ItemStack targetItemStack=var3.getItemStack();



        int amount= SupportedPluginManager.getInstance().getStackAmount(var3);
        if(amount<3){
            AddUtils.sendMessage(var2,"&c数量不足!");
            return;
        }
        else{
            EntityType type=getEntityTypeFromItem(targetItemStack);
            if(type==null||!type.isSpawnable()){
                AddUtils.sendMessage(var2,"&c该种类的刷怪笼并不支持获取!");
                return;
            }
            var3.remove();
            ItemStack extraStack=targetItemStack.clone();
            ItemStack resultItemStack;
            if(amount<9){
                resultItemStack=generateSpawnerFrom(type,true);
                resultItemStack.setAmount(amount/MIDDLE_MERGE);
                extraStack.setAmount(amount%MIDDLE_MERGE);
                AddUtils.sendMessage(var2,"&a已合成%d个普通刷怪笼".formatted(amount/9));
            }else {
                resultItemStack=generateSpawnerFrom(type,2,60,64,6,10,true);
                resultItemStack.setAmount(amount/SUPER_MERGE);
                extraStack.setAmount(amount/SUPER_MERGE);
                AddUtils.sendMessage(var2,"&a已合成1个超频刷怪笼");
            }
            var3.getWorld().dropItemNaturally(var3.getLocation(),extraStack);
            var3.getWorld().dropItemNaturally(var3.getLocation(), resultItemStack);
        }
    }
    @Override
    public ItemDropHandler[] getItemHandler() {
        return new ItemDropHandler[]{
                (ItemDropHandler) this::onItemDrop
        };
    }
    public boolean canStack(ItemMeta meta1,ItemMeta meta2){
        if(meta1 instanceof BlockStateMeta bsm1 &&meta2 instanceof BlockStateMeta bsm2){
            return CraftUtils.matchBlockStateMetaOnInvoke(bsm1,bsm2);
        }
        return false;
    }
}
