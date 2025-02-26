package io.github.guillex7.explodeany.configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.section.ChecktoolConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.configuration.section.WorldHoleProtection;
import io.github.guillex7.explodeany.util.MathUtils;
import io.github.guillex7.explodeany.util.MessageFormatter;

public final class ConfigurationManager {
    private static final String USE_BLOCK_DATABASE_ITEM = "UseBlockDatabase";
    private static final String CHECK_BLOCK_DATABASE_AT_STARTUP_ITEM = "CheckBlockDatabaseAtStartup";
    private static final String BLOCK_DURABILITY_ITEM = "BlockDurability";
    private static final String ENABLE_METRICS_ITEM = "EnableMetrics";
    private static final String GROUPS_SECTION_ITEM = "Groups";
    private static final String LOCALE_SECTION_ITEM = "Locale";
    private static final String LOCALE_PREFIX_ITEM = "LocalePrefix";
    private static final String DISABLED_WORLDS_ITEM = "DisabledWorlds";
    private static final String WORLD_HOLE_PROTECTION_ITEM = "WorldHoleProtection";

    private static ConfigurationManager instance;

    private final ConfigurationFile configurationFile;
    private boolean doUseBlockDatabase;
    private boolean doCheckBlockDatabaseAtStartup;
    private double globalBlockDurability;
    private boolean doEnableMetrics;
    private ChecktoolConfiguration checktoolConfiguration;
    private Map<String, List<String>> groups;
    private Map<String, LoadableConfigurationSection<?>> registeredConfigurationSectionsByPath;
    private Set<String> loadedConfigurationSectionPaths;
    private Set<Material> handledMaterials;
    private Map<ConfigurationLocale, String> localeStrings;
    private String localePrefix;
    private Set<String> disabledWorlds;
    private Map<String, WorldHoleProtection> worldHoleProtections;
    private WorldHoleProtection defaultWorldHoleProtection;

    private ConfigurationManager() {
        this.configurationFile = new ConfigurationFile(ExplodeAny.getInstance(), "config.yml");

        this.groups = new HashMap<>();
        this.registeredConfigurationSectionsByPath = new HashMap<>();
        this.loadedConfigurationSectionPaths = new HashSet<>();
        this.handledMaterials = new HashSet<>();
        this.localeStrings = new HashMap<>();
        this.disabledWorlds = new HashSet<>();
        this.worldHoleProtections = new HashMap<>();
        this.defaultWorldHoleProtection = WorldHoleProtection.byDefault();
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

    public ConfigurationFile getConfigurationFile() {
        return this.configurationFile;
    }

    private void loadDoUseBlockDatabase() {
        this.doUseBlockDatabase = this.getConfigurationFile().getConfig().getBoolean(USE_BLOCK_DATABASE_ITEM);
    }

    public boolean doUseBlockDatabase() {
        return this.doUseBlockDatabase;
    }

    private void loadDoCheckBlockDatabaseAtStartup() {
        this.doCheckBlockDatabaseAtStartup = this.getConfigurationFile().getConfig()
                .getBoolean(CHECK_BLOCK_DATABASE_AT_STARTUP_ITEM);
    }

    public boolean doCheckBlockDatabaseAtStartup() {
        return this.doCheckBlockDatabaseAtStartup;
    }

    private void loadGlobalBlockDurability() {
        this.globalBlockDurability = MathUtils
                .ensureMin(this.getConfigurationFile().getConfig().getDouble(BLOCK_DURABILITY_ITEM), 1);
    }

    public double getGlobalBlockDurability() {
        return this.globalBlockDurability;
    }

    private void loadDoEnableMetrics() {
        this.doEnableMetrics = this.getConfigurationFile().getConfig().getBoolean(ENABLE_METRICS_ITEM);
    }

    public boolean doEnableMetrics() {
        return this.doEnableMetrics;
    }

    private void loadChecktoolConfiguration() {
        ConfigurationSection checktoolSection = this.getConfigurationFile().getConfig()
                .getConfigurationSection(ChecktoolConfiguration.ROOT_PATH);
        this.checktoolConfiguration = checktoolSection == null ? ChecktoolConfiguration.byDefault()
                : ChecktoolConfiguration.fromConfigurationSection(checktoolSection);
    }

    public ChecktoolConfiguration getChecktoolConfiguration() {
        return this.checktoolConfiguration;
    }

    private void loadGroups() {
        this.groups.clear();

        ConfigurationSection groupsSection = this.getConfigurationFile().getConfig()
                .getConfigurationSection(GROUPS_SECTION_ITEM);
        for (String groupName : groupsSection.getKeys(false)) {
            this.groups.put(groupName, groupsSection.getStringList(groupName));
        }
    }

    public Map<String, List<String>> getGroups() {
        return this.groups;
    }

    public void registerLoadableConfigurationSection(LoadableConfigurationSection<?> loadableConfigurationSection) {
        this.getRegisteredConfigurationSectionsByPath().put(loadableConfigurationSection.getSectionPath(),
                loadableConfigurationSection);
    }

    public void unregisterAllLoadableConfigurationSections() {
        this.getRegisteredConfigurationSectionsByPath().clear();
    }

    public Map<String, LoadableConfigurationSection<?>> getRegisteredConfigurationSectionsByPath() {
        return registeredConfigurationSectionsByPath;
    }

    public LoadableConfigurationSection<?> getRegisteredConfigurationSectionByPath(String sectionPath) {
        return this.getRegisteredConfigurationSectionsByPath().get(sectionPath);
    }

    private void loadAllRegisteredLoadableConfigurationSections() {
        this.getHandledMaterials().clear();
        this.getLoadedConfigurationSectionPaths().clear();

        for (LoadableConfigurationSection<?> loadableConfigurationSection : this
                .getRegisteredConfigurationSectionsByPath()
                .values()) {
            if (loadableConfigurationSection.shouldBeLoaded()) {
                loadableConfigurationSection.clearEntityMaterialConfigurations();
                loadableConfigurationSection.fetchEntityMaterialConfigurations(this.getConfigurationFile().getConfig());
                for (Map<Material, EntityMaterialConfiguration> map : loadableConfigurationSection
                        .getEntityMaterialConfigurations().values()) {
                    this.getHandledMaterials().addAll(map.keySet());
                }
                this.getLoadedConfigurationSectionPaths().add(loadableConfigurationSection.getSectionPath());

                this.getPlugin().getLogger()
                        .info(String.format("Loaded support for %s",
                                loadableConfigurationSection.getHumanReadableName()));
            }
        }
    }

    public Set<String> getLoadedConfigurationSectionPaths() {
        return loadedConfigurationSectionPaths;
    }

    public boolean isConfigurationSectionLoaded(String sectionPath) {
        return this.getLoadedConfigurationSectionPaths().contains(sectionPath);
    }

    public Set<Material> getHandledMaterials() {
        return handledMaterials;
    }

    public boolean doHandlesBlock(Block block) {
        return this.getHandledMaterials().contains(block.getType());
    }

    public void loadLocalePrefix() {
        this.localePrefix = this.getConfigurationFile().getConfig().getString(LOCALE_PREFIX_ITEM);
    }

    public String getLocalePrefix() {
        return this.localePrefix;
    }

    private void loadLocale() {
        this.localeStrings.clear();

        final ConfigurationSection localeSection = this.getConfigurationFile().getConfig()
                .getConfigurationSection(LOCALE_SECTION_ITEM);

        if (localeSection != null) {
            final String localePrefix = this.getLocalePrefix();
            for (ConfigurationLocale localeKey : ConfigurationLocale.values()) {
                final String key = localeKey.getKey();
                final boolean skipPrefix = ConfigurationLocale.CHECKTOOL_USE_BOSS_BAR.getKey().equals(key);

                this.localeStrings.put(localeKey,
                        String.format("%s%s", !skipPrefix ? localePrefix : "",
                                MessageFormatter.colorize(localeSection.getString(key))));
            }
        }
    }

    public String getLocale(ConfigurationLocale locale) {
        return this.getConfigurationFile().getConfig()
                .getString(String.format("%s.%s", LOCALE_SECTION_ITEM, locale.getKey()));
    }

    public void loadDisabledWorlds() {
        this.disabledWorlds = new HashSet<>(
                this.getConfigurationFile().getConfig().getStringList(DISABLED_WORLDS_ITEM));
    }

    public Set<String> getDisabledWorlds() {
        return this.disabledWorlds;
    }

    public void loadWorldHoleProtections() {
        this.worldHoleProtections.clear();

        ConfigurationSection worldHoleProtectionsSection = this.getConfigurationFile().getConfig()
                .getConfigurationSection(WORLD_HOLE_PROTECTION_ITEM);
        if (worldHoleProtectionsSection != null) {
            for (String worldName : worldHoleProtectionsSection.getKeys(false)) {
                if (!worldName.equals("default")) {
                    this.worldHoleProtections.put(worldName,
                            WorldHoleProtection
                                    .fromConfigSection(worldHoleProtectionsSection.getConfigurationSection(worldName)));
                } else {
                    this.defaultWorldHoleProtection = WorldHoleProtection
                            .fromConfigSection(worldHoleProtectionsSection.getConfigurationSection(worldName));
                }
            }
        }
    }

    public WorldHoleProtection getWorldHoleProtection(String worldName) {
        return this.worldHoleProtections.getOrDefault(worldName, this.defaultWorldHoleProtection);
    }

    public void loadConfiguration() {
        this.getConfigurationFile().saveDefaultFileIfMissing();
        this.getConfigurationFile().reloadConfig();
        this.getPlugin().saveResource("exampleConfig.yml", true);
        this.getPlugin().saveResource("TUTORIAL.md", true);

        this.loadDoUseBlockDatabase();
        this.loadDoCheckBlockDatabaseAtStartup();
        this.loadGlobalBlockDurability();
        this.loadDoEnableMetrics();
        this.loadChecktoolConfiguration();
        this.loadGroups();
        this.loadAllRegisteredLoadableConfigurationSections();
        this.loadLocalePrefix();
        this.loadLocale();
        this.loadDisabledWorlds();
        this.loadWorldHoleProtections();
    }
}
