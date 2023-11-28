package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.guillex7.explodeany.block.BlockDatabase;

public final class BlockListener implements LoadableListener {
    private BlockDatabase blockDatabase;

    public BlockListener() {
        this.blockDatabase = BlockDatabase.getInstance();
    }

    @Override
    public String getName() {
        return "Block";
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public boolean isAnnounceable() {
        return false;
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