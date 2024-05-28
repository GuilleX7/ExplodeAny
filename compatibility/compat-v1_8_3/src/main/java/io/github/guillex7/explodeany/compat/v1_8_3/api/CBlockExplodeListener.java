package io.github.guillex7.explodeany.compat.v1_8_3.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import io.github.guillex7.explodeany.compat.common.data.ExplodingVanillaMaterial;
import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.compat.common.event.IBukkitListener;

public class CBlockExplodeListener implements IBukkitListener {
    protected final Consumer<EanyBlockExplodeEvent> eanyBlockExplodeEventConsumer;
    protected Map<Location, ExplodingVanillaMaterial> identifiedExplosiveBlocks;

    public CBlockExplodeListener(Consumer<EanyBlockExplodeEvent> eanyBlockExplodeEventConsumer) {
        this.eanyBlockExplodeEventConsumer = eanyBlockExplodeEventConsumer;
        this.identifiedExplosiveBlocks = new HashMap<>();
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (!event.getBed().getWorld().getEnvironment().equals(Environment.NORMAL)) {
            Location bedLocation = event.getBed().getLocation();
            identifiedExplosiveBlocks.put(bedLocation, ExplodingVanillaMaterial.BED);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onBlockExplode(BlockExplodeEvent event) {
        Location blockLocation = event.getBlock().getLocation();

        if (identifiedExplosiveBlocks.containsKey(blockLocation)) {
            ExplodingVanillaMaterial explodingVanillaMaterial = identifiedExplosiveBlocks.get(blockLocation);
            identifiedExplosiveBlocks.remove(blockLocation);

            eanyBlockExplodeEventConsumer.accept(new EanyBlockExplodeEvent() {
                @Override
                public Location getBlockLocation() {
                    return blockLocation;
                }

                @Override
                public ExplodingVanillaMaterial getBlockMaterial() {
                    return explodingVanillaMaterial;
                }

                @Override
                public List<Block> getBlockList() {
                    return event.blockList();
                }

                @Override
                public boolean isCancelled() {
                    return event.isCancelled();
                }

                @Override
                public void setCancelled(boolean cancelled) {
                    event.setCancelled(cancelled);
                }
            });
        }
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void unload() {
        BlockExplodeEvent.getHandlerList().unregister(this);
    }
}
