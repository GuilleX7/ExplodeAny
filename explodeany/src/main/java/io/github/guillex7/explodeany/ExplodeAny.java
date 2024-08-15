package io.github.guillex7.explodeany;

import java.io.File;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.command.CommandManager;
import io.github.guillex7.explodeany.command.registrable.CommandEany;
import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.cannon.CannonProjectileConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.qualityarmor.QualityArmoryExplosiveConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.CustomVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.RegularVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.TCEVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.MagicVanillaEntityConfiguration;
import io.github.guillex7.explodeany.listener.ListenerManager;
import io.github.guillex7.explodeany.listener.loadable.BlockBreakListener;
import io.github.guillex7.explodeany.listener.loadable.PlayerInteractListener;
import io.github.guillex7.explodeany.listener.loadable.explosion.EanyTaggedExplosionListener;
import io.github.guillex7.explodeany.listener.loadable.explosion.cannon.CannonProjectileExplosionListener;
import io.github.guillex7.explodeany.listener.loadable.explosion.qualityarmory.QualityArmoryExplosionListener;
import io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.block.EanyBlockExplosionListener;
import io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity.VanillaEntityExplosionListener;

public class ExplodeAny extends JavaPlugin {
    private static final String DATABASE_FILENAME = "blockDatabase.json";
    private static final int METRICS_PLUGIN_ID = 18111;

    private ConfigurationManager configurationManager;
    private BlockDatabase blockDatabase;
    private ListenerManager listenerManager;
    private CommandManager commandManager;
    private Metrics metrics;
    private CompatibilityManager compatibilityManager;

    @Override
    public void onEnable() {
        super.onEnable();

        this.compatibilityManager = CompatibilityManager.getInstance();
        this.configurationManager = ConfigurationManager.getInstance();
        this.blockDatabase = BlockDatabase.getInstance();
        this.listenerManager = ListenerManager.getInstance();
        this.commandManager = CommandManager.getInstance();

        this.getLogger().info(
                String.format("%s v%s is loading now!", this.getDescription().getName(),
                        this.getDescription().getVersion()));
        this.loadCompatibilityLayer();
        this.announceCompatibility();
        this.loadConfiguration();
        this.loadDatabase();
        this.registerListeners();
        this.registerCommands();
        this.peekMetrics();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.getLogger().info(
                String.format("%s v%s is unloading now!", this.getDescription().getName(),
                        this.getDescription().getVersion()));
        this.shutdownMetrics();
        this.unregisterCommands();
        this.unregisterListeners();
        this.unloadDatabase();
        this.unloadConfiguration();
    }

    public void loadCompatibilityLayer() {
        this.compatibilityManager.loadMaximumApiForEnvironment();
    }

    public void announceCompatibility() {
        this.getLogger().info(String.format("Compatibility layer for Bukkit v%s+ (detected Bukkit version: v%s)",
                this.compatibilityManager.getApi().getMinimumSupportedBukkitVersion(),
                this.compatibilityManager.getBukkitVersion()));
    }

    public void loadConfiguration() {
        this.configurationManager.loadConfiguration();
        this.configurationManager.registerLoadableConfigurationSection(new RegularVanillaEntityConfiguration());
        this.configurationManager.registerLoadableConfigurationSection(new CannonProjectileConfiguration());
        this.configurationManager.registerLoadableConfigurationSection(new MagicVanillaEntityConfiguration());
        this.configurationManager.registerLoadableConfigurationSection(new CustomVanillaEntityConfiguration());
        this.configurationManager.registerLoadableConfigurationSection(new QualityArmoryExplosiveConfiguration());
        this.configurationManager.registerLoadableConfigurationSection(new TCEVanillaEntityConfiguration());
        this.configurationManager.loadAllRegisteredLoadableConfigurationSections();
    }

    public void loadDatabase() {
        if (this.configurationManager.doUseBlockDatabase()) {
            this.blockDatabase.loadFromFile(new File(this.getDataFolder(), this.getDatabaseFilename()));
            this.blockDatabase.sanitize();
            this.unloadDatabase();
        }
    }

    public void registerListeners() {
        /* Miscellaneous */
        this.listenerManager.registerListener(new BlockBreakListener());
        this.listenerManager.registerListener(new PlayerInteractListener());
        /* Explosion Manager */
        this.listenerManager.registerListener(new EanyTaggedExplosionListener());
        /* Compatibility */
        this.listenerManager.registerListener(
                this.compatibilityManager.getApi().getBukkitListenerUtils().createBlockExplodeListener());
        /* Vanilla explosions */
        this.listenerManager.registerListener(new VanillaEntityExplosionListener());
        this.listenerManager.registerListener(new EanyBlockExplosionListener());
        /* Third party explosions */
        this.listenerManager.registerListener(new CannonProjectileExplosionListener());
        this.listenerManager.registerListener(new QualityArmoryExplosionListener());
        this.listenerManager.loadAllListeners();
    }

    public void registerCommands() {
        this.commandManager.registerCommand(new CommandEany());

        for (RegistrableCommand command : this.commandManager.getRegisteredCommands().values()) {
            this.getCommand(command.getName()).setExecutor(this.commandManager);
        }
    }

    public void peekMetrics() {
        if (this.configurationManager.doEnableMetrics()) {
            this.metrics = new Metrics(this, this.getMetricsPluginId());
            this.getLogger().info("Metrics have been enabled, thanks for your support!");
        }
    }

    public void unloadConfiguration() {
        this.configurationManager.unloadAllRegisteredLoadableConfigurationSections();
    }

    public void unloadDatabase() {
        if (this.configurationManager.doUseBlockDatabase()) {
            this.blockDatabase.saveToFile(new File(this.getDataFolder(), this.getDatabaseFilename()));
        }
    }

    public void unregisterCommands() {
        this.commandManager.unregisterAllCommands();
    }

    public void unregisterListeners() {
        this.listenerManager.unloadAllListeners();
        this.listenerManager.unregisterAllListeners();
    }

    public void shutdownMetrics() {
        if (this.metrics != null) {
            this.metrics.shutdown();
        }

        this.metrics = null;
    }

    public String getDatabaseFilename() {
        return DATABASE_FILENAME;
    }

    public int getMetricsPluginId() {
        return METRICS_PLUGIN_ID;
    }

    public static ExplodeAny getInstance() {
        return JavaPlugin.getPlugin(ExplodeAny.class);
    }
}
