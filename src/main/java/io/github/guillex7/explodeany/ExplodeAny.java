package io.github.guillex7.explodeany;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.command.CommandManager;
import io.github.guillex7.explodeany.command.registrable.CommandEany;
import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.CannonProjectileConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.VanillaEntityConfiguration;
import io.github.guillex7.explodeany.listener.ListenerManager;
import io.github.guillex7.explodeany.listener.loadable.BlockListener;
import io.github.guillex7.explodeany.listener.loadable.CannonExplosionListener;
import io.github.guillex7.explodeany.listener.loadable.EntityListener;
import io.github.guillex7.explodeany.listener.loadable.VanillaExplosionListener;

public class ExplodeAny extends JavaPlugin {
	private final String DATABASE_FILENAME = "blockDatabase.json";

	@Override
	public void onEnable() {
		super.onEnable();
		getLogger().info(
				String.format("%s v%s is loading now!", getDescription().getName(), getDescription().getVersion()));
		loadConfiguration();
		loadDatabase();
		registerListeners();
		registerCommands();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		getLogger().info(
				String.format("%s v%s is unloading now!", getDescription().getName(), getDescription().getVersion()));
		unregisterListeners();
		unloadDatabase();
		unloadConfiguration();
	}

	public void loadConfiguration() {
		ConfigurationManager configurationManager = ConfigurationManager.getInstance();
		configurationManager.loadConfiguration();
		configurationManager.registerEntityConfiguration(new VanillaEntityConfiguration());
		configurationManager.registerEntityConfiguration(new CannonProjectileConfiguration());
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
		listenerManager.registerListener(new VanillaExplosionListener());
		listenerManager.registerListener(new CannonExplosionListener());
		listenerManager.registerListener(new BlockListener());
		listenerManager.registerListener(new EntityListener());
		listenerManager.loadAllListeners();
	}

	public void registerCommands() {
		CommandManager commandManager = CommandManager.getInstance();
		commandManager.registerCommand(new CommandEany());

		for (RegistrableCommand command : commandManager.getRegisteredCommands().values()) {
			getCommand(command.getName()).setExecutor(commandManager);
		}
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
		return DATABASE_FILENAME;
	}

	public static ExplodeAny getInstance() {
		return JavaPlugin.getPlugin(ExplodeAny.class);
	}
}
