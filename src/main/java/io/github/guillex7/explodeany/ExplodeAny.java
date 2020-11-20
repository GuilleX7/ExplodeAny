package io.github.guillex7.explodeany;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.guillex7.explodeany.block.BlockDatabase;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.CannonProjectileConfiguration;
import io.github.guillex7.explodeany.configuration.loadable.VanillaEntityConfiguration;
import io.github.guillex7.explodeany.listener.BlockListener;
import io.github.guillex7.explodeany.listener.CommandListener;
import io.github.guillex7.explodeany.listener.EntityListener;
import io.github.guillex7.explodeany.listener.ExplosionListenerManager;
import io.github.guillex7.explodeany.listener.loadable.VanillaExplosionListener;
import io.github.guillex7.explodeany.listener.loadable.CannonExplosionListener;

public class ExplodeAny extends JavaPlugin {
	private final String databaseFilename = "blockDatabase.json";
	
	@Override
	public void onEnable() {
		super.onEnable();
		getLogger().log(Level.INFO, String.format("%s is LOADING now!", getDescription().getName()));
		loadConfiguration();
		loadDatabase();
		registerListeners();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		getLogger().log(Level.INFO, String.format("%s is UNLOADING now!", getDescription().getName()));
		unregisterListeners();
		saveDatabase();
		unloadConfiguration();
	}
	
	public void loadConfiguration() {
		saveDefaultConfig();
		reloadConfig();
		ConfigurationManager.getInstance().colorizeLocale();
		ConfigurationManager.getInstance().registerEntityConfiguration(VanillaEntityConfiguration.getInstance());
		ConfigurationManager.getInstance().registerEntityConfiguration(CannonProjectileConfiguration.getInstance());
		ConfigurationManager.getInstance().loadAllEntityConfigurations();
	}
	
	public void loadDatabase() {
		if (ConfigurationManager.getInstance().doUseBlockDatabase()) {
			BlockDatabase.getInstance().loadFromFile(new File(getDataFolder(), getDatabaseFilename()));
			BlockDatabase.getInstance().sanitize();
			saveDatabase();
		}
	}
	
	public void registerListeners() {
		ExplosionListenerManager.getInstance().registerExplosionListener(VanillaExplosionListener.getInstance());
		ExplosionListenerManager.getInstance().registerExplosionListener(CannonExplosionListener.getInstance());
		ExplosionListenerManager.getInstance().loadAllExplosionListeners();
		Bukkit.getServer().getPluginManager().registerEvents(new BlockListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getCommand("explodeany").setExecutor(new CommandListener());
	}
	
	public void unloadConfiguration() {
		ConfigurationManager.getInstance().unloadAllEntityConfigurations();
	}
	
	public void saveDatabase() {
		if (ConfigurationManager.getInstance().doUseBlockDatabase()) {
			BlockDatabase.getInstance().saveToFile(new File(getDataFolder(), getDatabaseFilename()));
		}
	}
	
	public void unregisterListeners() {
		ExplosionListenerManager.getInstance().unloadAllExplosionListeners();
	}

	public String getDatabaseFilename() {
		return databaseFilename;
	}
	
	public static ExplodeAny getInstance() {
		return JavaPlugin.getPlugin(ExplodeAny.class);
	}
}
