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
import io.github.guillex7.explodeany.compat.common.api.IBossBar;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.configuration.section.ChecktoolConfiguration;
import io.github.guillex7.explodeany.services.ChecktoolManager;
import io.github.guillex7.explodeany.util.ItemStackUtils;
import io.github.guillex7.explodeany.util.StringUtils;

public final class PlayerInteractListener implements LoadableListener {
    private final ChecktoolManager checktoolManager;
    private final ConfigurationManager configurationManager;
    private final BlockDatabase blockDatabase;
    private final CompatibilityManager compatibilityManager;

    public PlayerInteractListener() {
        this.checktoolManager = ChecktoolManager.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.blockDatabase = BlockDatabase.getInstance();
        this.compatibilityManager = CompatibilityManager.getInstance();
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        /* Nothing to do */
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && this.compatibilityManager.getApi().getPlayerInteractionEventUtils()
                        .doesInteractionUseMainHand(event)
                && this.checktoolManager.isPlayerUsingChecktool(player)) {
            final ItemStack itemInHand = new ItemStack(this.compatibilityManager.getApi().getPlayerInventoryUtils()
                    .getItemInMainHand(player.getInventory()));
            final ItemStack checktool = this.checktoolManager.getChecktool();

            if (!ItemStackUtils.areItemStacksSimilar(itemInHand, checktool)) {
                return;
            }

            final ChecktoolConfiguration checktoolConfiguration = this.configurationManager.getChecktoolConfiguration();

            if (!player.hasPermission(PermissionNode.CHECKTOOL_USE.getKey())) {
                if (!checktoolConfiguration.isSilentWhenCheckingWithoutPermissions()) {
                    player.sendMessage(this.configurationManager.getLocale(ConfigurationLocale.NOT_ALLOWED));
                }
                return;
            }

            if (this.configurationManager.getDisabledWorlds().contains(player.getWorld().getName())) {
                if (!checktoolConfiguration.isSilentWhenCheckingOnDisabledWorlds()) {
                    player.sendMessage(this.configurationManager.getLocale(ConfigurationLocale.DISABLED_IN_THIS_WORLD));
                }
                return;
            }

            final Block clickedBlock = event.getClickedBlock();
            final String materialName = clickedBlock.getType().name();
            final String prettyMaterialName = StringUtils.beautifyName(materialName);

            if (this.configurationManager.doHandlesBlock(clickedBlock)) {
                if (checktoolConfiguration.doPreventActionWhenCheckingHandledBlocks()) {
                    event.setCancelled(true);
                }

                final BlockStatus blockStatus = this.blockDatabase.getBlockStatus(clickedBlock, false);
                final double durabilityPercentage = blockStatus.getDurability()
                        / BlockStatus.getDefaultBlockDurability();
                final double prettyDurabilityPercentage = durabilityPercentage * 100;

                if (!checktoolConfiguration.isSilentWhenCheckingHandledBlocks()) {
                    final String formattedMessage = this.configurationManager
                            .getLocale(ConfigurationLocale.CHECKTOOL_USE)
                            .replace("%DURABILITY_PERCENTAGE%",
                                    String.format("%.02f", prettyDurabilityPercentage))
                            .replace("%DURABILITY%",
                                    String.format("%.02f", blockStatus.getDurability()))
                            .replace("%MAX_DURABILITY%",
                                    String.format("%.02f", BlockStatus.getDefaultBlockDurability()))
                            .replace("%B_X%",
                                    String.format("%d", clickedBlock.getLocation().getBlockX()))
                            .replace("%B_Y%",
                                    String.format("%d", clickedBlock.getLocation().getBlockY()))
                            .replace("%B_Z%",
                                    String.format("%d", clickedBlock.getLocation().getBlockZ()))
                            .replace("%MATERIAL%", materialName)
                            .replace("%PRETTY_MATERIAL%", prettyMaterialName);

                    player.sendMessage(formattedMessage);
                }

                if (checktoolConfiguration.doShowBossBar()) {
                    final String formattedBossBarTitle = this.configurationManager
                            .getLocale(ConfigurationLocale.CHECKTOOL_USE_BOSS_BAR)
                            .replace("%DURABILITY_PERCENTAGE%",
                                    String.format("%.02f", prettyDurabilityPercentage))
                            .replace("%DURABILITY%",
                                    String.format("%.02f", blockStatus.getDurability()))
                            .replace("%MAX_DURABILITY%",
                                    String.format("%.02f", BlockStatus.getDefaultBlockDurability()))
                            .replace("%B_X%",
                                    String.format("%d", clickedBlock.getLocation().getBlockX()))
                            .replace("%B_Y%",
                                    String.format("%d", clickedBlock.getLocation().getBlockY()))
                            .replace("%B_Z%",
                                    String.format("%d", clickedBlock.getLocation().getBlockZ()))
                            .replace("%MATERIAL%", materialName)
                            .replace("%PRETTY_MATERIAL%", prettyMaterialName);

                    IBossBar checktoolBossBar = this.compatibilityManager.getApi().getBukkitUtils().createBossBar(
                            formattedBossBarTitle, checktoolConfiguration.getBossBarColor(),
                            checktoolConfiguration.getBossBarStyle());

                    checktoolBossBar.setProgress(durabilityPercentage);

                    this.checktoolManager.setChecktoolBossBarForPlayer(player, checktoolBossBar,
                            checktoolConfiguration.getBossBarDuration());
                }
            } else {
                if (this.configurationManager.getChecktoolConfiguration()
                        .doPreventActionWhenCheckingNonHandledBlocks()) {
                    event.setCancelled(true);
                }

                if (!this.configurationManager.getChecktoolConfiguration().isSilentWhenCheckingNonHandledBlocks()) {
                    final String formattedMessage = this.configurationManager
                            .getLocale(ConfigurationLocale.CHECKTOOL_NOT_HANDLED)
                            .replace("%MATERIAL%", materialName)
                            .replace("%PRETTY_MATERIAL%", prettyMaterialName);

                    player.sendMessage(formattedMessage);
                }

                ChecktoolManager.getInstance().hideChecktoolBossBarForPlayer(player);
            }
        }
    }

    @Override
    public void unload() {
        PlayerInteractEvent.getHandlerList().unregister(this);
    }
}
