package io.github.guillex7.explodeany.compat.v1_16_1.api;

import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;

public class CBlockExplodeListener extends io.github.guillex7.explodeany.compat.v1_8_3.api.CBlockExplodeListener {
    public CBlockExplodeListener(Consumer<EanyBlockExplodeEvent> eanyBlockExplodeEventConsumer) {
        super(eanyBlockExplodeEventConsumer);
    }

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

        this.identifiedExplosiveBlocks.put(clickedBlock.getLocation(), "RESPAWN_ANCHOR");
    }

    @Override
    public void unload() {
        super.unload();
        PlayerInteractEvent.getHandlerList().unregister(this);
    }
}
