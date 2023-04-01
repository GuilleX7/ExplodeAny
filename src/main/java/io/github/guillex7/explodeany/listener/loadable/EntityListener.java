package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.block.BlockStatus;
import io.github.guillex7.explodeany.command.registrable.checktool.ChecktoolManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MessageFormatter;

public final class EntityListener implements LoadableListener {
	@Override
	public String getName() {
		return "Entity";
	}

	@Override
	public boolean shouldBeLoaded() {
		return true;
	}

	@Override
	public boolean isAdvisable() {
		return false;
	}

	private boolean isChecktoolSimilar(ItemStack item) {
		if (item.getType().equals(Material.AIR) && ChecktoolManager.getChecktool().getType().equals(Material.AIR)) {
			return true;
		} else {
			return item.isSimilar(ChecktoolManager.getChecktool());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getHand().equals(EquipmentSlot.HAND)
				&& ChecktoolManager.getPlayersUsingChecktool().contains(event.getPlayer())) {
			ItemStack itemInHand = new ItemStack(event.getPlayer().getInventory().getItemInMainHand());
			if (!isChecktoolSimilar(itemInHand)) {
				return;
			}

			if (!event.getPlayer().hasPermission("explodeany.checktool.use")) {
				event.getPlayer().sendMessage(MessageFormatter
						.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.NOT_ALLOWED)));
				return;
			}

			event.setCancelled(true);
			String formattedMessage;
			Block clickedBlock = event.getClickedBlock();
			if (ConfigurationManager.getInstance().handlesBlock(clickedBlock)) {
				formattedMessage = ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_USE);
				BlockStatus blockStatus = BlockDatabase.getInstance().getBlockStatus(clickedBlock, false);
				formattedMessage = formattedMessage.replaceAll("%DURABILITY%",
						String.format("%.02f", blockStatus.getDurability()));
				formattedMessage = formattedMessage.replaceAll("%MAX_DURABILITY%",
						String.format("%.02f", BlockStatus.getDefaultBlockDurability()));
				formattedMessage = formattedMessage.replaceAll("%B_X%",
						String.format("%d", clickedBlock.getLocation().getBlockX()));
				formattedMessage = formattedMessage.replaceAll("%B_Y%",
						String.format("%d", clickedBlock.getLocation().getBlockY()));
				formattedMessage = formattedMessage.replaceAll("%B_Z%",
						String.format("%d", clickedBlock.getLocation().getBlockZ()));
			} else {
				formattedMessage = ConfigurationManager.getInstance()
						.getLocale(ConfigurationLocale.CHECKTOOL_NOT_HANDLED);
			}

			formattedMessage = formattedMessage.replaceAll("%MATERIAL%", clickedBlock.getType().name());
			event.getPlayer().sendMessage(MessageFormatter.sign(formattedMessage));
		}
	}

	@Override
	public void unload() {
		PlayerInteractEvent.getHandlerList().unregister(this);
	}
}
