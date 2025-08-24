package io.github.guillex7.explodeany.configuration.loadable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.util.NamePatternUtils;

public abstract class LoadableConfigurationSection<T extends Object> {
    private static final String MATERIALS_SECTION = "Materials";
    private static final String PROPERTIES_SECTION = "Properties";

    private final Map<T, Map<Material, EntityMaterialConfiguration>> entityMaterialConfigurations;
    private final Map<T, EntityConfiguration> entityConfigurations;

    protected LoadableConfigurationSection() {
        this.entityMaterialConfigurations = new HashMap<>();
        this.entityConfigurations = new HashMap<>();
    }

    private ExplodeAny getPlugin() {
        return ExplodeAny.getInstance();
    }

    public final Map<T, Map<Material, EntityMaterialConfiguration>> getEntityMaterialConfigurations() {
        return this.entityMaterialConfigurations;
    }

    public final Map<T, EntityConfiguration> getEntityConfigurations() {
        return this.entityConfigurations;
    }

    public final void clear() {
        this.entityMaterialConfigurations.clear();
        this.entityConfigurations.clear();
    }

    public final Set<String> getLoadedEntityNames() {
        return this.getEntityConfigurations().keySet().stream().map(this::getEntityName).collect(Collectors.toSet());
    }

    public final String reifyEntityName(final String entityName) {
        final T entity = this.getEntityFromName(entityName);
        if (entity != null) {
            return this.getEntityName(entity);
        } else {
            return entityName;
        }
    }

    public final void fetchFromConfiguration(final FileConfiguration configuration) {
        final ConfigurationSection configurationSection = configuration.getConfigurationSection(this.getSectionPath());
        if (configurationSection == null) {
            return;
        }

        this.fetchEntities(configurationSection);
    }

    private void fetchEntities(final ConfigurationSection entitiesSection) {
        for (final String entityName : entitiesSection.getKeys(false)) {
            final Set<T> validEntities = new HashSet<>();
            final Set<String> invalidEntities = new HashSet<>();
            boolean doesDefinitionHavePriority = true;

            final List<T> entities = this.getEntitiesFromNameOrPattern(entityName);
            if (!entities.isEmpty()) {
                validEntities.addAll(entities);
            } else {
                doesDefinitionHavePriority = false;
                final List<String> group = ConfigurationManager.getInstance().getGroups().get(entityName);
                if (group != null) {
                    for (final String entityNameEntry : group) {
                        final List<T> entitiesForNameEntry = this.getEntitiesFromNameOrPattern(entityNameEntry);
                        if (!entitiesForNameEntry.isEmpty()) {
                            validEntities.addAll(entitiesForNameEntry);
                        } else {
                            invalidEntities.add(entityNameEntry);
                        }
                    }
                }
            }

            if (validEntities.isEmpty()) {
                this.getPlugin().getLogger()
                        .warning(String.format("%s is not a valid %s nor %s group and won't be loaded",
                                entityName, this.getSectionPath(), this.getSectionPath()));
                continue;
            } else if (!invalidEntities.isEmpty()) {
                this.getPlugin().getLogger()
                        .warning(String.format(
                                "%s is a valid %s group, but some of its entities are not valid: %s",
                                entityName, this.getSectionPath(), String.join(", ", invalidEntities)));
            }

            final ConfigurationSection entitySection = entitiesSection.getConfigurationSection(entityName);
            if (entitySection == null) {
                this.getPlugin().getLogger().warning(
                        String.format("%s.%s section is invalid and won't be loaded", this.getSectionPath(),
                                entityName));
                continue;
            }

            Map<Material, EntityMaterialConfiguration> materialConfigurations = new HashMap<>();
            EntityConfiguration entityConfiguration = EntityConfiguration.byDefault();
            final ConfigurationSection materialsSection = entitySection
                    .getConfigurationSection(LoadableConfigurationSection.MATERIALS_SECTION);
            final ConfigurationSection propertiesSection = entitySection
                    .getConfigurationSection(LoadableConfigurationSection.PROPERTIES_SECTION);
            boolean hasSections = false;

            if (materialsSection != null) {
                materialConfigurations = this.fetchMaterials(materialsSection);
                hasSections = true;
            }

            if (propertiesSection != null) {
                entityConfiguration = EntityConfiguration.fromConfigurationSection(propertiesSection);
                hasSections = true;
            }

            if (!hasSections) {
                // Hint: if there are no sections, then assume the whole section is the
                // materials section
                materialConfigurations = this.fetchMaterials(entitySection);
            }

            for (final T fetchedEntity : validEntities) {
                if (propertiesSection != null) {
                    entityConfiguration = this.fetchSpecificEntityConfiguration(fetchedEntity,
                            entityConfiguration, propertiesSection);
                }

                if (this.areEntityAndMaterialConfigurationsValid(fetchedEntity, entityConfiguration,
                        materialConfigurations)) {
                    this.putAndMergeEntityConfigurations(fetchedEntity, entityConfiguration,
                            doesDefinitionHavePriority);
                    this.putAndMergeEntityMaterialConfigurations(fetchedEntity, materialConfigurations,
                            doesDefinitionHavePriority);
                }
            }
        }
    }

    private Map<Material, EntityMaterialConfiguration> fetchMaterials(final ConfigurationSection entitySection) {
        final Map<Material, EntityMaterialConfiguration> materialConfigurations = new HashMap<>();

        for (final String materialName : entitySection.getKeys(false)) {
            final Set<Material> validMaterials = new HashSet<>();
            final Set<String> invalidMaterials = new HashSet<>();
            boolean doDefinitionHavePriority = true;

            final List<Material> materials = NamePatternUtils.getMaterialsFromNameOrPattern(materialName);
            if (!materials.isEmpty()) {
                validMaterials.addAll(materials);
            } else {
                doDefinitionHavePriority = false;
                final List<String> group = ConfigurationManager.getInstance().getGroups().get(materialName);
                if (group != null) {
                    for (final String materialNameEntry : group) {
                        final List<Material> materialsForNameEntry = NamePatternUtils
                                .getMaterialsFromNameOrPattern(materialNameEntry);
                        if (!materialsForNameEntry.isEmpty()) {
                            validMaterials.addAll(materialsForNameEntry);
                        } else {
                            invalidMaterials.add(materialNameEntry);
                        }
                    }
                }
            }

            if (validMaterials.isEmpty()) {
                this.getPlugin().getLogger()
                        .warning(String.format("%s is an invalid material or material group for %s and won't be loaded",
                                materialName, entitySection.getName()));
                continue;
            } else if (!invalidMaterials.isEmpty()) {
                this.getPlugin().getLogger()
                        .warning(String.format(
                                "%s is a valid material group, but some of its materials are not valid: %s",
                                materialName, String.join(", ", invalidMaterials)));
            }

            final ConfigurationSection materialSection = entitySection.getConfigurationSection(materialName);
            if (materialSection == null) {
                this.getPlugin().getLogger().warning(String.format("%s.%s.%s is invalid and won't be loaded",
                        this.getSectionPath(), entitySection.getName(), materialName));
                continue;
            }

            final EntityMaterialConfiguration entityMaterialConfiguration = EntityMaterialConfiguration
                    .fromConfigurationSection(materialSection);

            for (final Material fetchedMaterial : validMaterials) {
                this.putAndMergeMaterialConfigurations(materialConfigurations, fetchedMaterial,
                        entityMaterialConfiguration,
                        doDefinitionHavePriority);
            }
        }

        return materialConfigurations;
    }

    private void putAndMergeEntityMaterialConfigurations(final T entity,
            final Map<Material, EntityMaterialConfiguration> materialConfigurations,
            final boolean definitionHasPriority) {
        if (!this.getEntityMaterialConfigurations().containsKey(entity)) {
            this.getEntityMaterialConfigurations().put(entity, new HashMap<>());
            this.getEntityMaterialConfigurations().get(entity).putAll(materialConfigurations);
        } else if (definitionHasPriority) {
            this.getEntityMaterialConfigurations().get(entity).putAll(materialConfigurations);
        } else {
            for (final Entry<Material, EntityMaterialConfiguration> entry : materialConfigurations.entrySet()) {
                this.getEntityMaterialConfigurations().get(entity).putIfAbsent(entry.getKey(), entry.getValue());
            }
        }
    }

    private void putAndMergeEntityConfigurations(final T entity, final EntityConfiguration entityConfiguration,
            final boolean definitionHasPriority) {
        if (definitionHasPriority) {
            this.getEntityConfigurations().put(entity, entityConfiguration);
        } else {
            this.getEntityConfigurations().putIfAbsent(entity, entityConfiguration);
        }
    }

    private void putAndMergeMaterialConfigurations(
            final Map<Material, EntityMaterialConfiguration> materialConfigurations, final Material material,
            final EntityMaterialConfiguration entityMaterialConfiguration, final boolean definitionHasPriority) {
        if (definitionHasPriority) {
            materialConfigurations.put(material, entityMaterialConfiguration);
        } else {
            materialConfigurations.putIfAbsent(material, entityMaterialConfiguration);
        }
    }

    private List<T> getEntitiesFromNameOrPattern(final String nameOrPattern) {
        if (NamePatternUtils.isNamePattern(nameOrPattern)) {
            final Pattern pattern = NamePatternUtils.getPatternFromNamePattern(nameOrPattern,
                    this.isEntityNameCaseSensitive());
            if (pattern == null) {
                return new ArrayList<>();
            }

            return this.getEntitiesFromPattern(pattern, nameOrPattern);
        } else {
            final T entity = this.getEntityFromName(nameOrPattern);
            return entity != null ? Arrays.asList(entity) : new ArrayList<>();
        }
    }

    protected boolean areEntityAndMaterialConfigurationsValid(final T entity,
            final EntityConfiguration entityConfiguration,
            final Map<Material, EntityMaterialConfiguration> materialConfigurations) {
        return true;
    }

    protected boolean isEntityNameCaseSensitive() {
        return false;
    }

    protected EntityConfiguration fetchSpecificEntityConfiguration(final T entity,
            final EntityConfiguration entityConfiguration,
            final ConfigurationSection propertiesSection) {
        return entityConfiguration;
    }

    public abstract boolean shouldBeLoaded();

    public abstract String getHumanReadableName();

    public abstract String getSectionPath();

    public abstract String getEntityName(T entity);

    public abstract T getEntityFromName(String name);

    public abstract List<T> getEntitiesFromPattern(Pattern pattern, String name);
}
