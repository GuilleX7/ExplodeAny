package io.github.guillex7.explodeany.compat.v1_8.api;

import org.bukkit.event.player.PlayerInteractEvent;

import io.github.guillex7.explodeany.compat.common.api.IPlayerInteractionEventUtils;

public class CPlayerInteractionEventUtils implements IPlayerInteractionEventUtils {
    @Override
    public boolean doesInteractionUseMainHand(PlayerInteractEvent event) {
        return true;
    }
}
