package me.matl114.logitech.Listeners.Events;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class AttackPermissionTestEvent extends EntityDamageByEntityEvent {
    //used for electric defense
    @Getter
    private float realDamage;
    public AttackPermissionTestEvent(Entity damager, Entity damagee,float realDamage) {
        super(damager, damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK,0.0);
    }

}
