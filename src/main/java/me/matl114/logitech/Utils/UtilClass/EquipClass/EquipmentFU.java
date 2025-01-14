package me.matl114.logitech.Utils.UtilClass.EquipClass;

import lombok.Getter;
import me.matl114.matlib.Utils.Inventory.CleanItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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


    public boolean canEquipedTo(ItemStack itemStack, Player player){
        return false;
    }

    /**
     * write special data into fuDataField
     * @param equipment
     * @param fuDataField
     */
    public void onEquip(ItemStack equipment, ItemMeta editingMeta, PersistentDataContainer fuDataField, int newLevel){
        //equipment is readOnly!

        //actually ,you should add some lores here
        if(prefix!=null){
            List<String> lores=editingMeta.getLore();
            lores=lores==null?new ArrayList<>():lores;
            find_lore:
            {
                int len=lores.size();
                for(int i=0;i<len;++i){
                    String lore=lores.get(i);
                    if(lore.startsWith(prefix)){
                        lores.set(i,prefix+newLevel);
                        break find_lore;
                    }
                }
                lores.add(prefix+newLevel);
            }
        }
    }

    public void register(){

    }
    public void unregister(){}
}
