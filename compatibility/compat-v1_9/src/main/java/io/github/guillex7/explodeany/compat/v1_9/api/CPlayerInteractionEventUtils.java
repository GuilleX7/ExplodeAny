package io.github.guillex7.explodeany.compat.v1_9.api;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;

public class CPlayerInteractionEventUtils implements IPlayerInteractionEventUtils {
    @Override
    public boolean doesInteractionUseMainHand(PlayerInteractEvent event) {
        return event.getHand().equals(EquipmentSlot.HAND);
    }
}
