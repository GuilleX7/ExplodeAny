package io.github.guillex7.explodeany.configuration;

import java.util.HashMap;
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
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.util.MessageFormatter;

public final class ConfigurationManager {
	private final static String USE_BLOCK_DATABASE_ITEM = "UseBlockDatabase";
	private final static String CHECK_BLOCK_DATABASE_AT_STARTUP_ITEM = "CheckBlockDatabaseAtStartup";
	private final static String BLOCK_DURABILITY_ITEM = "BlockDurability";
	private final static String GROUPS_SECTION = "Groups";
	private final static String LOCALE_SECTION = "Locale";

	private static ConfigurationManager instance;

	private Map<String, LoadableSectionConfiguration<?>> registeredEntityConfigurations;
	private Set<Material> handledMaterials;

	private ConfigurationManager() {
		registeredEntityConfigurations = new HashMap<String, LoadableSectionConfiguration<?>>();
		handledMaterials = new HashSet<Material>();
	}

	public static ConfigurationManager getInstance() {
		if (instance == null) {
			instance = new ConfigurationManager();
		}
		return instance;
	}

	public ExplodeAny getPlugin() {
		return ExplodeAny.getInstance();
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

	public static int ensureRange(int value, int max, int min) {
		return Math.min(Math.max(value, min), max);
	}

	public static int ensureMax(int value, int max) {
		return Math.min(value, max);
	}

	public static int ensureMin(int value, int min) {
		return Math.max(value, min);
	}

	public Map<String, LoadableSectionConfiguration<?>> getRegisteredEntityConfigurations() {
		return registeredEntityConfigurations;
	}

	public LoadableSectionConfiguration<?> getRegisteredEntityConfiguration(String sectionPath) {
		return getRegisteredEntityConfigurations().get(sectionPath);
	}

	public Set<Material> getHandledMaterials() {
		return handledMaterials;
	}

	public boolean handlesBlock(Block block) {
		return getHandledMaterials().contains(block.getType());
	}

	public boolean doUseBlockDatabase() {
		return getPlugin().getConfig().getBoolean(USE_BLOCK_DATABASE_ITEM);
	}

	public boolean doCheckBlockDatabaseAtStartup() {
		return getPlugin().getConfig().getBoolean(CHECK_BLOCK_DATABASE_AT_STARTUP_ITEM);
	}

	public Double getBlockDurability() {
		return ensureMin(getPlugin().getConfig().getDouble(BLOCK_DURABILITY_ITEM), 1);
	}

	public Map<String, List<String>> getGroups() {
		ConfigurationSection groupsSection = getPlugin().getConfig().getConfigurationSection(GROUPS_SECTION);
		Map<String, List<String>> groups = new HashMap<String, List<String>>();
		for (String groupName : groupsSection.getKeys(false)) {
			groups.put(groupName, groupsSection.getStringList(groupName));
		}
		return groups;
	}

	public void loadConfiguration() {
		getPlugin().saveDefaultConfig();
		getPlugin().reloadConfig();
		getPlugin().getConfig().options().copyDefaults(true);
		getPlugin().saveConfig();
		getPlugin().saveResource("exampleConfig.yml", true);
		colorizeLocale();
	}

	private void colorizeLocale() {
		ConfigurationSection localeSection = getPlugin().getConfig().getConfigurationSection(LOCALE_SECTION);
		if (localeSection != null) {
			for (String path : localeSection.getValues(false).keySet()) {
				if (localeSection.isString(path)) {
					localeSection.set(path, MessageFormatter.colorize(localeSection.getString(path)));
				}
			}
		}
	}

	public String getLocale(ConfigurationLocale locale) {
		return getPlugin().getConfig().getString(String.format("%s.%s", LOCALE_SECTION, locale.getPath()));
	}

	public void registerEntityConfiguration(LoadableSectionConfiguration<?> entityConfiguration) {
		getRegisteredEntityConfigurations().put(entityConfiguration.getSectionPath(), entityConfiguration);
	}

	public void loadAllEntityConfigurations() {
		FileConfiguration config = getPlugin().getConfig();

		for (LoadableSectionConfiguration<?> entityConfiguration : getRegisteredEntityConfigurations().values()) {
			if (entityConfiguration.shouldBeLoaded()) {
				entityConfiguration.clearEntityMaterialConfigurations();
				entityConfiguration.fetchEntityMaterialConfigurations(config);
				for (Map<Material, EntityMaterialConfiguration> map : entityConfiguration
						.getEntityMaterialConfigurations().values()) {
					getHandledMaterials().addAll(map.keySet());
				}
			}
		}
	}

	public void unloadAllEntityConfigurations() {
		getRegisteredEntityConfigurations().clear();
		getHandledMaterials().clear();
	}
}
