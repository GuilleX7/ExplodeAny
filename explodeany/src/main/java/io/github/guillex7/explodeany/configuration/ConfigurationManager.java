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
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.util.MathUtils;
import io.github.guillex7.explodeany.util.MessageFormatter;

public final class ConfigurationManager {
    private static final String USE_BLOCK_DATABASE_ITEM = "UseBlockDatabase";
    private static final String CHECK_BLOCK_DATABASE_AT_STARTUP_ITEM = "CheckBlockDatabaseAtStartup";
    private static final String BLOCK_DURABILITY_ITEM = "BlockDurability";
    private static final String ENABLE_METRICS = "EnableMetrics";
    private static final String GROUPS_SECTION = "Groups";
    private static final String LOCALE_SECTION = "Locale";
    private static final String LOCALE_PREFIX_ITEM = "LocalePrefix";
    private static final String DISABLED_WORLDS_ITEM = "DisabledWorlds";

    private static ConfigurationManager instance;

    private final Map<String, LoadableConfigurationSection<?>> registeredEntityConfigurations;
    private final Set<Material> handledMaterials;

    private ConfigurationManager() {
        this.registeredEntityConfigurations = new HashMap<>();
        this.handledMaterials = new HashSet<>();
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

    public Map<String, LoadableConfigurationSection<?>> getRegisteredEntityConfigurations() {
        return registeredEntityConfigurations;
    }

    public LoadableConfigurationSection<?> getRegisteredEntityConfiguration(String sectionPath) {
        return this.getRegisteredEntityConfigurations().get(sectionPath);
    }

    public Set<Material> getHandledMaterials() {
        return handledMaterials;
    }

    public boolean handlesBlock(Block block) {
        return this.getHandledMaterials().contains(block.getType());
    }

    public boolean doUseBlockDatabase() {
        return this.getPlugin().getConfig().getBoolean(USE_BLOCK_DATABASE_ITEM);
    }

    public boolean doCheckBlockDatabaseAtStartup() {
        return this.getPlugin().getConfig().getBoolean(CHECK_BLOCK_DATABASE_AT_STARTUP_ITEM);
    }

    public double getBlockDurability() {
        return MathUtils.ensureMin(this.getPlugin().getConfig().getDouble(BLOCK_DURABILITY_ITEM), 1);
    }

    public boolean doEnableMetrics() {
        return this.getPlugin().getConfig().getBoolean(ENABLE_METRICS);
    }

    public String getLocalePrefix() {
        return this.getPlugin().getConfig().getString(LOCALE_PREFIX_ITEM);
    }

    public Set<String> getDisabledWorlds() {
        return new HashSet<>(this.getPlugin().getConfig().getStringList(DISABLED_WORLDS_ITEM));
    }

    public Map<String, List<String>> getGroups() {
        ConfigurationSection groupsSection = this.getPlugin().getConfig().getConfigurationSection(GROUPS_SECTION);
        Map<String, List<String>> groups = new HashMap<>();
        for (String groupName : groupsSection.getKeys(false)) {
            groups.put(groupName, groupsSection.getStringList(groupName));
        }
        return groups;
    }

    public void loadConfiguration() {
        this.getPlugin().saveDefaultConfig();
        this.getPlugin().reloadConfig();
        this.getPlugin().getConfig().options().copyDefaults(true);
        this.getPlugin().saveConfig();
        this.getPlugin().saveResource("exampleConfig.yml", true);
        this.parseLocale();
    }

    private void parseLocale() {
        String localePrefix = this.getLocalePrefix();
        ConfigurationSection localeSection = this.getPlugin().getConfig().getConfigurationSection(LOCALE_SECTION);
        if (localeSection != null) {
            for (String path : localeSection.getValues(false).keySet()) {
                localeSection.set(path,
                        String.format("%s%s", localePrefix, MessageFormatter.colorize(localeSection.getString(path))));
            }
        }
    }

    public String getLocale(ConfigurationLocale locale) {
        return this.getPlugin().getConfig().getString(String.format("%s.%s", LOCALE_SECTION, locale.getPath()));
    }

    public void registerEntityConfiguration(LoadableConfigurationSection<?> entityConfiguration) {
        this.getRegisteredEntityConfigurations().put(entityConfiguration.getSectionPath(), entityConfiguration);
    }

    public void loadAllEntityConfigurations() {
        FileConfiguration config = this.getPlugin().getConfig();

        for (LoadableConfigurationSection<?> entityConfiguration : this.getRegisteredEntityConfigurations().values()) {
            if (entityConfiguration.shouldBeLoaded()) {
                entityConfiguration.clearEntityMaterialConfigurations();
                entityConfiguration.fetchEntityMaterialConfigurations(config);
                for (Map<Material, EntityMaterialConfiguration> map : entityConfiguration
                        .getEntityMaterialConfigurations().values()) {
                    this.getHandledMaterials().addAll(map.keySet());
                }
            }
        }
    }

    public void unloadAllEntityConfigurations() {
        this.getRegisteredEntityConfigurations().clear();
        this.getHandledMaterials().clear();
    }
}
