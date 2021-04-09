package io.github.guillex7.explodeany;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.CannonProjectileConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.VanillaEntityConfiguration;
import io.github.guillex7.explodeany.listener.CommandExecutor;
import io.github.guillex7.explodeany.listener.ListenerManager;
import io.github.guillex7.explodeany.listener.loadable.BlockListener;
import io.github.guillex7.explodeany.listener.loadable.CannonExplosionListener;
import io.github.guillex7.explodeany.listener.loadable.EntityListener;
import io.github.guillex7.explodeany.listener.loadable.VanillaExplosionListener;

public class ExplodeAny extends JavaPlugin {
	private final String databaseFilename = "blockDatabase.json";

	@Override
	public void onEnable() {
		super.onEnable();
		getLogger().log(Level.INFO,
				String.format("%s v%s is LOADING now!", getDescription().getName(), getDescription().getVersion()));
		loadConfiguration();
		loadDatabase();
		registerListeners();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		getLogger().log(Level.INFO,
				String.format("%s v%s is UNLOADING now!", getDescription().getName(), getDescription().getVersion()));
		unregisterListeners();
		unloadDatabase();
		unloadConfiguration();
	}

	public void loadConfiguration() {
		ConfigurationManager configurationManager = ConfigurationManager.getInstance();
		configurationManager.loadConfiguration();
		configurationManager.registerEntityConfiguration(VanillaEntityConfiguration.empty());
		configurationManager.registerEntityConfiguration(CannonProjectileConfiguration.empty());
		configurationManager.loadAllEntityConfigurations();
	}

	public void loadDatabase() {
		if (ConfigurationManager.getInstance().doUseBlockDatabase()) {
			BlockDatabase blockDatabase = BlockDatabase.getInstance();
			blockDatabase.loadFromFile(new File(getDataFolder(), getDatabaseFilename()));
			blockDatabase.sanitize();
			unloadDatabase();
		}
	}

	public void registerListeners() {
		ListenerManager listenerManager = ListenerManager.getInstance();
		listenerManager.registerListener(VanillaExplosionListener.empty());
		listenerManager.registerListener(CannonExplosionListener.empty());
		listenerManager.registerListener(BlockListener.empty());
		listenerManager.registerListener(EntityListener.empty());
		listenerManager.loadAllListeners();
		getCommand("explodeany").setExecutor(new CommandExecutor());
	}

	public void unloadConfiguration() {
		ConfigurationManager.getInstance().unloadAllEntityConfigurations();
	}

	public void unloadDatabase() {
		if (ConfigurationManager.getInstance().doUseBlockDatabase()) {
			BlockDatabase.getInstance().saveToFile(new File(getDataFolder(), getDatabaseFilename()));
		}
	}

	public void unregisterListeners() {
		ListenerManager listenerManager = ListenerManager.getInstance();
		listenerManager.unloadAllListeners();
		listenerManager.unregisterAllListeners();
	}

	public String getDatabaseFilename() {
		return databaseFilename;
	}

	public static ExplodeAny getInstance() {
		return JavaPlugin.getPlugin(ExplodeAny.class);
	}
}
