package me.matl114.logitech.Utils.UtilClass.EquipClass;

import lombok.Getter;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.WorldUtils;
import me.matl114.logitech.core.AddItem;
import me.matl114.matlib.Utils.Inventory.CleanItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class EquipmentFU  {
    @Getter
    private final NamespacedKey key;
    private final EnumSet<EquipmentSlot> slot;
    private Plugin plugin;
    public boolean isSupportSlot(EquipmentSlot slot){
        return this.slot.contains(slot);
    }
    private String prefix=null;
    public EquipmentFU setDisplayPrefix(String pref){
        prefix=new CleanItemStack(Material.STONE,pref).getItemMeta().getDisplayName();
        return this;
    }
    public static ItemStack getInfoFor(String fuName,String... function){
        return AddUtils.themed(Material.KNOWLEDGE_BOOK,AddUtils.resolveColor("&a"+fuName+" : &6机制说明"),function);
    }
    public ItemStack getInfoDisplay(){
        return getInfoFor("Demo 功能单元","这是一个样例功能单元","你需要在这里写点啥来说明他的功能");
    }
    private static final EnumMap<EquipmentSlot,HashMap<NamespacedKey,EquipmentFU>> registry=new EnumMap<>(EquipmentSlot.class);
    public static EquipmentFU getFunctionUnit(EquipmentSlot slot, NamespacedKey key) {
        return registry.computeIfAbsent(slot,(i)->new HashMap<>()).get(key);
    }
    public EquipmentFU( NamespacedKey key,EquipmentSlot... slot) {
        this.key = key;
        this.slot =EnumSet.of(slot[0],slot);
        for (var s:slot){
            registry.computeIfAbsent(s,(i)->new HashMap<>()).put(key,this);
        }
    }


    /**
     * called when player change inventory or player reload
     * these triggerEvent should be readOnly!
     * method will be called NOT prime thread
     * 0->1 onEnable 1->1 onUpdate 1->0 onRemove
     * @param triggerEvent
     * @param player
     */
    public void onUpdate(Event triggerEvent,Player player, int newLevel){

    }
    public void onEnable(Event triggerEvent, Player player,int level){}
    public void onRemove(Event triggerEvent,Player player,int oldLevel){

    }

    /**
     * called as tick in a TickPeriod
     * @param player
     * @param level
     */
    public void onPeriodTick(Player player, int level){

    }
    public int getTickPeriod(){
        return 20;
    }
    public void onTick(Player player, int level,int tickCount) {
        if(tickCount % getTickPeriod() == 0){
            onPeriodTick(player,level);
        }
    }
    /**
     * call when handling Events,these event will be called BEFORE FU updates,so you can cancel these from being update
     */
    public void  onAttack(EntityDamageByEntityEvent event,EventPriority priority,Player damager,int level){

    }
    public void onDamage(EntityDamageEvent event,EventPriority priority, Player adamagee, int level){

    }
    protected void onInteract(PlayerInteractEvent event, Player damager, int level){

    }
    //F key
    protected void onHandSwap(PlayerSwapHandItemsEvent event, Player player, int level){

    }
    //shift key
    protected void onToggleSneak(PlayerToggleSneakEvent event, Player player, int level){

    }
    //...
    protected void onToggleSprint(PlayerToggleSprintEvent event, Player player, int level){

    }
    //else ,when I figure out more needed,

    /**
     * public-to-all method of Event handle
     * @param event
     * @param player
     * @param level
     */
    public void onPlayerEventHandle(PlayerEvent event, Player player, int level){
        //priority will be  HIGHEST
        if(event instanceof PlayerInteractEvent p){
            onInteract(p,player,level);
        }else if(event instanceof PlayerSwapHandItemsEvent p){
            onHandSwap(p,player,level);
        }else if(event instanceof PlayerToggleSneakEvent p){
            onToggleSneak(p,player,level);
        }else if(event instanceof PlayerToggleSprintEvent p){
            onToggleSprint(p,player,level);
        }
    }


    public boolean canEquipedTo(ItemStack itemStack,ItemStack costing){
        return false;
    }
    public Set<Material> getCanEquipedMaterial(){
        //mainly are tools
        return Set.copyOf(WorldUtils.TOOLS_MATERIAL);
    }
    //get Supported cost Material
    public Set<ItemStack> getEquipCostable(){
        //
        return Set.of(AddItem.LSINGULARITY);
    }
    //can override level
    public int getMaxFULevel(ItemStack item) {
        return 10;
    }
    public int getEquipCost(ItemStack alreadyEquiped,ItemStack cost){
        return 1;
    }
    public int getEquipTimeCost(ItemStack alreadyEquiped,ItemStack cost){
        return 10;
    }

    /**
     * change special data into fuDataField and lore or something
     * @param equipment
     * @param fuDataField
     */
    public void onEquipLevelChange(ItemStack equipment, ItemMeta editingMeta, PersistentDataContainer fuDataField, int newLevel){
        //equipment is readOnly!

        //actually ,you should add some lores here
        if(prefix!=null){
            if(newLevel>0){
                List<String> lores=editingMeta.getLore();
                lores=lores==null?new ArrayList<>():lores;
                find_lore:
                {
                    int len=lores.size();
                    for(int i=0;i<len;++i){
                        String lore=lores.get(i);
                        if(lore.startsWith(prefix)){
                            //change level
                            lores.set(i,prefix+newLevel);
                            break find_lore;
                        }
                    }
                    //if not present,add at front
                    lores.add(0,prefix+newLevel);
                }
                editingMeta.setLore(lores);
            }else{
                List<String> lores=editingMeta.getLore();
                if(lores!=null&&!lores.isEmpty()){
                    lores.removeIf(i->i.startsWith(prefix));
                    editingMeta.setLore(lores);
                }
            }
        }

    }

    public void register(){

    }
    public void unregister(){

    }
}
