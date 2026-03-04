package io.github.guillex7.explodeany.compat.v1_9.api;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CPlayerInteractionEventUtils
        extends io.github.guillex7.explodeany.compat.v1_8_3.api.CPlayerInteractionEventUtils {
    @Override
    public boolean doesInteractionUseMainHand(final PlayerInteractEvent event) {
        return EquipmentSlot.HAND.equals(event.getHand());
    }
}
