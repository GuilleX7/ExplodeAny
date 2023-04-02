package io.github.guillex7.explodeany.listener.loadable;

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
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.ItemStackUtils;
import io.github.guillex7.explodeany.util.StringUtils;

public final class EntityListener implements LoadableListener {
	private ChecktoolManager checktoolManager;
	private ConfigurationManager configurationManager;
	private BlockDatabase blockDatabase;

	public EntityListener() {
		super();
		this.checktoolManager = ChecktoolManager.getInstance();
		this.configurationManager = ConfigurationManager.getInstance();
		this.blockDatabase = BlockDatabase.getInstance();
	}

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

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getHand().equals(EquipmentSlot.HAND)
				&& this.checktoolManager.getPlayersUsingChecktool().contains(event.getPlayer())) {
			ItemStack itemInHand = new ItemStack(event.getPlayer().getInventory().getItemInMainHand());
			ItemStack checktool = this.checktoolManager.getChecktool();

			if (!ItemStackUtils.areItemStacksSimilar(itemInHand, checktool)) {
				return;
			}

			if (!event.getPlayer().hasPermission(PermissionNode.CHECKTOOL_USE.getKey())) {
				event.getPlayer().sendMessage(this.configurationManager.getLocale(ConfigurationLocale.NOT_ALLOWED));
				return;
			}

			event.setCancelled(true);
			String formattedMessage;
			Block clickedBlock = event.getClickedBlock();
			if (this.configurationManager.handlesBlock(clickedBlock)) {
				formattedMessage = this.configurationManager.getLocale(ConfigurationLocale.CHECKTOOL_USE);

				BlockStatus blockStatus = this.blockDatabase.getBlockStatus(clickedBlock, false);
				double durabilityPercentage = blockStatus.getDurability() / BlockStatus.getDefaultBlockDurability()
						* 100;

				formattedMessage = formattedMessage
						.replaceAll("%DURABILITY_PERCENTAGE%",
								String.format("%.02f", durabilityPercentage))
						.replaceAll("%DURABILITY%",
								String.format("%.02f", blockStatus.getDurability()))
						.replaceAll("%MAX_DURABILITY%",
								String.format("%.02f", BlockStatus.getDefaultBlockDurability()))
						.replaceAll("%B_X%",
								String.format("%d", clickedBlock.getLocation().getBlockX()))
						.replaceAll("%B_Y%",
								String.format("%d", clickedBlock.getLocation().getBlockY()))
						.replaceAll("%B_Z%",
								String.format("%d", clickedBlock.getLocation().getBlockZ()));
			} else {
				formattedMessage = this.configurationManager
						.getLocale(ConfigurationLocale.CHECKTOOL_NOT_HANDLED);
			}

			String materialName = clickedBlock.getType().name();
			String prettyMaterialName = StringUtils.beautifyName(materialName);
			
			formattedMessage = formattedMessage
					.replaceAll("%MATERIAL%", materialName)
					.replaceAll("%PRETTY_MATERIAL%", prettyMaterialName);

			event.getPlayer().sendMessage(formattedMessage);
		}
	}

	@Override
	public void unload() {
		PlayerInteractEvent.getHandlerList().unregister(this);
	}
}
