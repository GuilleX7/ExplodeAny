package io.github.guillex7.explodeany.compat.v1_16_1.listener;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CBlockExplodeListener extends io.github.guillex7.explodeany.compat.v1_8_3.listener.CBlockExplodeListener {
    private static final String RESPAWN_ANCHOR_ENTITY_NAME = "RESPAWN_ANCHOR";

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerInteractEvent(final PlayerInteractEvent event) {
        final Block clickedBlock = event.getClickedBlock();
        final Action action = event.getAction();
        if (!Action.RIGHT_CLICK_BLOCK.equals(action) || clickedBlock == null
                || !Material.RESPAWN_ANCHOR.equals(clickedBlock.getType())
                || Environment.NETHER.equals(clickedBlock.getWorld().getEnvironment())) {
            return;
        }

        final RespawnAnchor respawnAnchor = (RespawnAnchor) clickedBlock.getBlockData();
        final Material itemInHand = event.getMaterial();
        if (respawnAnchor.getCharges() == 0 || (respawnAnchor.getCharges() < 3
                && Material.GLOWSTONE.equals(itemInHand))) {
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
