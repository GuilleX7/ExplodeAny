package io.github.guillex7.explodeany.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.guillex7.explodeany.block.BlockDatabase;

public class BlockListener implements Listener {
	@EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		BlockDatabase.getInstance().removeBlockStatus(event.getBlock());
	}
}