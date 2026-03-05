package io.github.guillex7.explodeany.compat.bukkit.v1_8.api;

import org.bukkit.event.player.PlayerInteractEvent;

import io.github.guillex7.explodeany.compat.common.bukkit.api.IPlayerInteractionEventUtils;

public class CPlayerInteractionEventUtils implements IPlayerInteractionEventUtils {
    @Override
    public boolean doesInteractionUseMainHand(final PlayerInteractEvent event) {
        return true;
    }
}
