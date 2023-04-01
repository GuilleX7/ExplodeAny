package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;

import io.github.guillex7.explodeany.block.BlockDatabase;

public final class BlockListener implements LoadableListener {
	@Override
	public String getName() {
		return "Block";
	}

	@Override
	public boolean shouldBeLoaded() {
		return true;
	}

	@Override
	public boolean isAdvisable() {
		return false;
	}

	@EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		BlockDatabase.getInstance().removeBlockStatus(event.getBlock());
	}

	@Override
	public void unload() {
		BlockExpEvent.getHandlerList().unregister(this);
	}
}