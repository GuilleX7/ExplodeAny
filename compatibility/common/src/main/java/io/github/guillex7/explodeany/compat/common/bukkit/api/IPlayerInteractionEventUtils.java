package io.github.guillex7.explodeany.compat.common.bukkit.api;

import org.bukkit.event.player.PlayerInteractEvent;

public interface IPlayerInteractionEventUtils {
    boolean doesInteractionUseMainHand(PlayerInteractEvent event);
}
