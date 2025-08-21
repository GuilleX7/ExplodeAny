package io.github.guillex7.explodeany.compat.v1_8_3.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public class CBlockExplodeListener implements LoadableListener {
    private static final String BED_BLOCK_NAME = "BED";

    protected Map<Location, String> identifiedExplosiveBlocks;

    public CBlockExplodeListener() {
        this.identifiedExplosiveBlocks = new HashMap<>();
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (!event.getBed().getWorld().getEnvironment().equals(Environment.NORMAL)) {
            Location bedLocation = event.getBed().getLocation();
            identifiedExplosiveBlocks.put(bedLocation, CBlockExplodeListener.BED_BLOCK_NAME);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onBlockExplode(BlockExplodeEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        EanyBlockExplodeEvent eanyEvent;

        if (identifiedExplosiveBlocks.containsKey(blockLocation)) {
            String explodingVanillaMaterial = identifiedExplosiveBlocks.get(blockLocation);
            identifiedExplosiveBlocks.remove(blockLocation);

            eanyEvent = new EanyBlockExplodeEvent(blockLocation, explodingVanillaMaterial, event.blockList());
        } else {
            eanyEvent = new EanyBlockExplodeEvent(blockLocation, null, event.blockList());
        }

        Bukkit.getPluginManager().callEvent(eanyEvent);

        event.setCancelled(eanyEvent.isCancelled());
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        // Do nothing
    }

    @Override
    public void unload() {
        BlockExplodeEvent.getHandlerList().unregister(this);
    }
}
