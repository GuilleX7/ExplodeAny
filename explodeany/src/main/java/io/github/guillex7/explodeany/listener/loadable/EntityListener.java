package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.block.BlockStatus;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.services.ChecktoolManager;
import io.github.guillex7.explodeany.util.ItemStackUtils;
import io.github.guillex7.explodeany.util.StringUtils;

public final class EntityListener extends LoadableListener {
    private final ChecktoolManager checktoolManager;
    private final ConfigurationManager configurationManager;
    private final BlockDatabase blockDatabase;
    private final CompatibilityManager compatibilityManager;

    public EntityListener() {
        this.checktoolManager = ChecktoolManager.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.blockDatabase = BlockDatabase.getInstance();
        this.compatibilityManager = CompatibilityManager.getInstance();
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
    public boolean isAnnounceable() {
        return false;
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && this.compatibilityManager.getApi().getPlayerInteractionEventUtils()
                        .doesInteractionUseMainHand(event)
                && this.checktoolManager.isPlayerUsingChecktool(player)) {
            ItemStack itemInHand = new ItemStack(this.compatibilityManager.getApi().getPlayerInventoryUtils()
                    .getItemInMainHand(player.getInventory()));
            ItemStack checktool = this.checktoolManager.getChecktool();

            if (!ItemStackUtils.areItemStacksSimilar(itemInHand, checktool)) {
                return;
            }

            if (!player.hasPermission(PermissionNode.CHECKTOOL_USE.getKey())) {
                player.sendMessage(this.configurationManager.getLocale(ConfigurationLocale.NOT_ALLOWED));
                return;
            }

            if (this.configurationManager.getDisabledWorlds().contains(player.getWorld().getName())) {
                player.sendMessage(
                        this.configurationManager.getLocale(ConfigurationLocale.DISABLED_IN_THIS_WORLD));
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
                        .replace("%DURABILITY_PERCENTAGE%",
                                String.format("%.02f", durabilityPercentage))
                        .replace("%DURABILITY%",
                                String.format("%.02f", blockStatus.getDurability()))
                        .replace("%MAX_DURABILITY%",
                                String.format("%.02f", BlockStatus.getDefaultBlockDurability()))
                        .replace("%B_X%",
                                String.format("%d", clickedBlock.getLocation().getBlockX()))
                        .replace("%B_Y%",
                                String.format("%d", clickedBlock.getLocation().getBlockY()))
                        .replace("%B_Z%",
                                String.format("%d", clickedBlock.getLocation().getBlockZ()));
            } else {
                formattedMessage = this.configurationManager
                        .getLocale(ConfigurationLocale.CHECKTOOL_NOT_HANDLED);
            }

            String materialName = clickedBlock.getType().name();
            String prettyMaterialName = StringUtils.beautifyName(materialName);

            formattedMessage = formattedMessage
                    .replace("%MATERIAL%", materialName)
                    .replace("%PRETTY_MATERIAL%", prettyMaterialName);

            player.sendMessage(formattedMessage);
        }
    }

    @Override
    public void unload() {
        PlayerInteractEvent.getHandlerList().unregister(this);
    }
}
