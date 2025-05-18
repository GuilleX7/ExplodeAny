package io.github.guillex7.explodeany.compat.v1_16_1.api;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CBlockExplodeListener extends io.github.guillex7.explodeany.compat.v1_8_3.api.CBlockExplodeListener {
    private static final String RESPAWN_ANCHOR_ENTITY_NAME = "RESPAWN_ANCHOR";

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        Action action = event.getAction();
        if (!action.equals(Action.RIGHT_CLICK_BLOCK) || clickedBlock == null
                || !clickedBlock.getType().equals(Material.RESPAWN_ANCHOR)
                || clickedBlock.getWorld().getEnvironment().equals(Environment.NETHER)) {
            return;
        }

        RespawnAnchor respawnAnchor = (RespawnAnchor) clickedBlock.getBlockData();
        Material itemInHand = event.getMaterial();
        if (respawnAnchor.getCharges() == 0 || (respawnAnchor.getCharges() < 3
                && itemInHand.equals(Material.GLOWSTONE))) {
            return;
        }

        this.identifiedExplosiveBlocks.put(clickedBlock.getLocation(),
                CBlockExplodeListener.RESPAWN_ANCHOR_ENTITY_NAME);
    }

    @Override
    public void unload() {
        super.unload();
        PlayerInteractEvent.getHandlerList().unregister(this);
    }
}
