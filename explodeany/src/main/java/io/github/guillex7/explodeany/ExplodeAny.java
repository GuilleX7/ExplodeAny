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
import io.github.guillex7.explodeany.configuration.loadable.CannonProjectileConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.MagicEntityConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.VanillaEntityConfiguration;
import io.github.guillex7.explodeany.listener.ListenerManager;
import io.github.guillex7.explodeany.listener.loadable.BlockListener;
import io.github.guillex7.explodeany.listener.loadable.EntityListener;
import io.github.guillex7.explodeany.listener.loadable.explosion.CannonExplosionListener;
import io.github.guillex7.explodeany.listener.loadable.explosion.MagicExplosionListener;
import io.github.guillex7.explodeany.listener.loadable.explosion.VanillaExplosionListener;

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

        getLogger().info(
                String.format("%s v%s is loading now!", getDescription().getName(), getDescription().getVersion()));
        loadCompatibilityLayer();
        announceCompatibility();
        loadConfiguration();
        loadDatabase();
        registerListeners();
        registerCommands();
        peekMetrics();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info(
                String.format("%s v%s is unloading now!", getDescription().getName(), getDescription().getVersion()));
        unregisterListeners();
        unloadDatabase();
        unloadConfiguration();
        shutdownMetrics();
    }

    public void loadCompatibilityLayer() {
        this.compatibilityManager.loadMaximumApiForEnvironment();
    }

    public void announceCompatibility() {
        getLogger().info(String.format("Compatibility layer for Bukkit v%s+ (detected Bukkit version: %s)",
                this.compatibilityManager.getApi().getMinimumSupportedBukkitVersion(),
                this.compatibilityManager.getBukkitVersion()));
    }

    public void loadConfiguration() {
        this.configurationManager.loadConfiguration();
        this.configurationManager.registerEntityConfiguration(new VanillaEntityConfiguration());
        this.configurationManager.registerEntityConfiguration(new CannonProjectileConfiguration());
        this.configurationManager.registerEntityConfiguration(new MagicEntityConfiguration());
        this.configurationManager.loadAllEntityConfigurations();
    }

    public void loadDatabase() {
        if (this.configurationManager.doUseBlockDatabase()) {
            this.blockDatabase.loadFromFile(new File(getDataFolder(), getDatabaseFilename()));
            this.blockDatabase.sanitize();
            unloadDatabase();
        }
    }

    public void registerListeners() {
        this.listenerManager.registerListener(new BlockListener());
        this.listenerManager.registerListener(new EntityListener());
        this.listenerManager.registerListener(new VanillaExplosionListener());
        this.listenerManager.registerListener(new CannonExplosionListener());
        this.listenerManager.registerListener(new MagicExplosionListener());
        this.listenerManager.loadAllListeners();
    }

    public void registerCommands() {
        this.commandManager.registerCommand(new CommandEany());

        for (RegistrableCommand command : this.commandManager.getRegisteredCommands().values()) {
            getCommand(command.getName()).setExecutor(this.commandManager);
        }
    }

    public void peekMetrics() {
        if (this.configurationManager.doEnableMetrics()) {
            metrics = new Metrics(this, getMetricsPluginId());
            getLogger().info("Metrics have been enabled");
        }
    }

    public void unloadConfiguration() {
        this.configurationManager.unloadAllEntityConfigurations();
    }

    public void unloadDatabase() {
        if (this.configurationManager.doUseBlockDatabase()) {
            this.blockDatabase.saveToFile(new File(getDataFolder(), getDatabaseFilename()));
        }
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
