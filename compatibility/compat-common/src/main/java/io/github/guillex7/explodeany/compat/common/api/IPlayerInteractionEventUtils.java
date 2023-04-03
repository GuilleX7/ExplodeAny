package io.github.guillex7.explodeany.compat.common.api;

import org.bukkit.event.player.PlayerInteractEvent;

public interface IPlayerInteractionEventUtils {
    boolean doesInteractionUseMainHand(PlayerInteractEvent event);
}
