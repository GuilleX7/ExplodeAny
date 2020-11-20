package io.github.guillex7.explodeany.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.loadable.LoadableSectionConfiguration;
import io.github.guillex7.explodeany.utils.MessageFormatter;

public final class ConfigurationManager {
	private class ConfigurationKeys {
		public final static String USE_BLOCK_DATABASE = "UseBlockDatabase";
		public final static String CHECK_BLOCK_DATABASE_AT_STARTUP = "CheckBlockDatabaseAtStartup";
		public final static String BLOCK_DURABILITY_ITEM = "BlockDurability";
		public final static String LOCALE_SECTION = "Locale";
	}
	
	private static ConfigurationManager instance;
	
	private List<LoadableSectionConfiguration<?>> entityConfigurations = new ArrayList<LoadableSectionConfiguration<?>>();
	private Set<Material> handledMaterials = new HashSet<Material>();
	
	private ConfigurationManager() {}
	
	public static ConfigurationManager getInstance() {
		if (instance == null) {
			instance = new ConfigurationManager();
		}
		return instance;
	}
	
	public static double ensureRange(double value, double max, double min) {
		return Math.min(Math.max(value, min), max);
	}
	
	public static double ensureMax(double value, double max) {
		return Math.min(value, max);
	}
	
	public static double ensureMin(double value, double min) {
		return Math.max(value, min);
	}
	
	public boolean doUseBlockDatabase() {
		return ExplodeAny.getInstance().getConfig().getBoolean(ConfigurationKeys.USE_BLOCK_DATABASE, false);
	}
	
	public boolean doCheckBlockDatabaseAtStartup() {
		return ExplodeAny.getInstance().getConfig().getBoolean(ConfigurationKeys.CHECK_BLOCK_DATABASE_AT_STARTUP, false);
	}
	
	public Double getBlockDurability() {
		return ExplodeAny.getInstance().getConfig().getDouble(ConfigurationKeys.BLOCK_DURABILITY_ITEM, 100.0d);
	}
	
	public void colorizeLocale() {
		ConfigurationSection localeSection = ExplodeAny.getInstance().getConfig()
				.getConfigurationSection(ConfigurationKeys.LOCALE_SECTION);
		if (localeSection != null) {
			for (String path : localeSection.getValues(false).keySet()) {
				if (localeSection.isString(path)) {
					localeSection.set(path, MessageFormatter.colorize(localeSection.getString(path)));
				}
			}
		}
	}
	
	public String getLocale(ConfigurationLocale locale) {
		return ExplodeAny.getInstance().getConfig().getString(
				String.format("%s.%s", ConfigurationKeys.LOCALE_SECTION, locale.getPath()),
				locale.getDefaultLocale()
				);
	}
	
	public List<LoadableSectionConfiguration<?>> getRegisteredEntityConfigurations() {
		return entityConfigurations;
	}
	
	public void registerEntityConfiguration(LoadableSectionConfiguration<?> entityConfiguration) {
		getRegisteredEntityConfigurations().add(entityConfiguration);
	}
	
	public void unloadAllEntityConfigurations() {
		getRegisteredEntityConfigurations().clear();
		getHandledMaterials().clear();
	}
	
	public void loadAllEntityConfigurations() {
		FileConfiguration config = ExplodeAny.getInstance().getConfig();
		
		for (LoadableSectionConfiguration<?> entityConfiguration : getRegisteredEntityConfigurations()) {
			if (entityConfiguration.shouldBeLoaded()) {
				entityConfiguration.clearEntityMaterialConfigurations();
				entityConfiguration.fetchEntityMaterialConfigurations(config);
				for (Map<Material, EntityMaterialConfiguration> map :
					entityConfiguration.getEntityMaterialConfigurations().values()) {
					getHandledMaterials().addAll(map.keySet());
				}
			}
		}
	}
	
	public Set<Material> getHandledMaterials() {
		return handledMaterials;
	}
	
	public boolean handlesBlock(Block block) {
		return getHandledMaterials().contains(block.getType());
	}
}
