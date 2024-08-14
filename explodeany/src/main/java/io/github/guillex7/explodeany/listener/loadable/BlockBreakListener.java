package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public final class BlockBreakListener implements LoadableListener {
    private final BlockDatabase blockDatabase;

    public BlockBreakListener() {
        this.blockDatabase = BlockDatabase.getInstance();
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        /* Nothing to do */
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        this.blockDatabase.removeBlockStatus(event.getBlock());
    }

    @Override
    public void unload() {
        BlockBreakEvent.getHandlerList().unregister(this);
    }
}
