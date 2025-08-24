package io.github.guillex7.explodeany.compat.common.event;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EanyBlockExplodeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Location blockLocation;
    private final String blockMaterial;
    private final List<Block> blockList;
    private boolean isCancelled = false;

    public static HandlerList getHandlerList() {
        return EanyBlockExplodeEvent.handlers;
    }

    public EanyBlockExplodeEvent(final Location blockLocation, final String blockMaterial,
            final List<Block> blockList) {
        this.blockLocation = blockLocation;
        this.blockMaterial = blockMaterial;
        this.blockList = blockList;
    }

    public Location getBlockLocation() {
        return this.blockLocation;
    }

    public String getBlockMaterial() {
        return this.blockMaterial;
    }

    public List<Block> getBlockList() {
        return this.blockList;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return EanyBlockExplodeEvent.handlers;
    }
}
