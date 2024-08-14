package io.github.guillex7.explodeany.compat.common.event;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EanyBlockExplodeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Location blockLocation;
    private String blockMaterial;
    private List<Block> blockList;
    private boolean isCancelled = false;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public EanyBlockExplodeEvent(Location blockLocation, String blockMaterial, List<Block> blockList) {
        this.blockLocation = blockLocation;
        this.blockMaterial = blockMaterial;
        this.blockList = blockList;
    }

    public Location getBlockLocation() {
        return blockLocation;
    }

    public String getBlockMaterial() {
        return blockMaterial;
    }

    public List<Block> getBlockList() {
        return blockList;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
