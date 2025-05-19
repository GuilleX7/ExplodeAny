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
        return entityMaterialConfigurations;
    }

    public final Map<T, EntityConfiguration> getEntityConfigurations() {
        return entityConfigurations;
    }

    public final void clear() {
        this.entityMaterialConfigurations.clear();
        this.entityConfigurations.clear();
    }

    public final Set<String> getLoadedEntityNames() {
        return this.getEntityConfigurations().keySet().stream().map(this::getEntityName).collect(Collectors.toSet());
    }

    public final String reifyEntityName(String entityName) {
        T entity = this.getEntityFromName(entityName);
        if (entity != null) {
            return this.getEntityName(entity);
        } else {
            return entityName;
        }
    }

    public final void fetchFromConfiguration(FileConfiguration configuration) {
        ConfigurationSection configurationSection = configuration.getConfigurationSection(this.getSectionPath());
        if (configurationSection == null) {
            return;
        }

        this.fetchEntities(configurationSection);
    }

    private void fetchEntities(ConfigurationSection entitiesSection) {
        for (String entityName : entitiesSection.getKeys(false)) {
            Set<T> validEntities = new HashSet<>();
            Set<String> invalidEntities = new HashSet<>();
            boolean doesDefinitionHavePriority = true;

            List<T> entities = this.getEntitiesFromNameOrPattern(entityName);
            if (!entities.isEmpty()) {
                validEntities.addAll(entities);
            } else {
                doesDefinitionHavePriority = false;
                List<String> group = ConfigurationManager.getInstance().getGroups().get(entityName);
                if (group != null) {
                    for (String entityNameEntry : group) {
                        List<T> entitiesForNameEntry = this.getEntitiesFromNameOrPattern(entityNameEntry);
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

            ConfigurationSection entitySection = entitiesSection.getConfigurationSection(entityName);
            if (entitySection == null) {
                this.getPlugin().getLogger().warning(
                        String.format("%s.%s section is invalid and won't be loaded", this.getSectionPath(),
                                entityName));
                continue;
            }

            Map<Material, EntityMaterialConfiguration> materialConfigurations = new HashMap<>();
            EntityConfiguration entityConfiguration = EntityConfiguration.byDefault();
            ConfigurationSection materialsSection = entitySection.getConfigurationSection(MATERIALS_SECTION);
            ConfigurationSection propertiesSection = entitySection.getConfigurationSection(PROPERTIES_SECTION);
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

            for (T fetchedEntity : validEntities) {
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

    private Map<Material, EntityMaterialConfiguration> fetchMaterials(ConfigurationSection entitySection) {
        Map<Material, EntityMaterialConfiguration> materialConfigurations = new HashMap<>();

        for (String materialName : entitySection.getKeys(false)) {
            Set<Material> validMaterials = new HashSet<>();
            Set<String> invalidMaterials = new HashSet<>();
            boolean doDefinitionHavePriority = true;

            List<Material> materials = NamePatternUtils.getMaterialsFromNameOrPattern(materialName);
            if (!materials.isEmpty()) {
                validMaterials.addAll(materials);
            } else {
                doDefinitionHavePriority = false;
                List<String> group = ConfigurationManager.getInstance().getGroups().get(materialName);
                if (group != null) {
                    for (String materialNameEntry : group) {
                        List<Material> materialsForNameEntry = NamePatternUtils.getMaterialsFromNameOrPattern(materialNameEntry);
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

            ConfigurationSection materialSection = entitySection.getConfigurationSection(materialName);
            if (materialSection == null) {
                this.getPlugin().getLogger().warning(String.format("%s.%s.%s is invalid and won't be loaded",
                        getSectionPath(), entitySection.getName(), materialName));
                continue;
            }

            EntityMaterialConfiguration entityMaterialConfiguration = EntityMaterialConfiguration
                    .fromConfigurationSection(materialSection);

            for (Material fetchedMaterial : validMaterials) {
                this.putAndMergeMaterialConfigurations(materialConfigurations, fetchedMaterial,
                        entityMaterialConfiguration,
                        doDefinitionHavePriority);
            }
        }

        return materialConfigurations;
    }

    private void putAndMergeEntityMaterialConfigurations(T entity,
            Map<Material, EntityMaterialConfiguration> materialConfigurations, boolean definitionHasPriority) {
        if (!this.getEntityMaterialConfigurations().containsKey(entity)) {
            this.getEntityMaterialConfigurations().put(entity, materialConfigurations);
        } else if (definitionHasPriority) {
            this.getEntityMaterialConfigurations().get(entity).putAll(materialConfigurations);
        } else {
            for (Entry<Material, EntityMaterialConfiguration> entry : materialConfigurations.entrySet()) {
                this.getEntityMaterialConfigurations().get(entity).putIfAbsent(entry.getKey(), entry.getValue());
            }
        }
    }

    private void putAndMergeEntityConfigurations(T entity, EntityConfiguration entityConfiguration,
            boolean definitionHasPriority) {
        if (definitionHasPriority) {
            this.getEntityConfigurations().put(entity, entityConfiguration);
        } else {
            this.getEntityConfigurations().putIfAbsent(entity, entityConfiguration);
        }
    }

    private void putAndMergeMaterialConfigurations(
            Map<Material, EntityMaterialConfiguration> materialConfigurations, Material material,
            EntityMaterialConfiguration entityMaterialConfiguration, boolean definitionHasPriority) {
        if (definitionHasPriority) {
            materialConfigurations.put(material, entityMaterialConfiguration);
        } else {
            materialConfigurations.putIfAbsent(material, entityMaterialConfiguration);
        }
    }

    private List<T> getEntitiesFromNameOrPattern(String nameOrPattern) {
        if (NamePatternUtils.isNamePattern(nameOrPattern)) {
            Pattern pattern = NamePatternUtils.
                    getPatternFromNamePattern(nameOrPattern, this.isEntityNameCaseSensitive());
            if (pattern == null) {
                return new ArrayList<>();
            }

            return this.getEntitiesFromPattern(pattern, nameOrPattern);
        } else {
            T entity = this.getEntityFromName(nameOrPattern);
            return entity != null ? Arrays.asList(entity) : new ArrayList<>();
        }
    }

    protected boolean areEntityAndMaterialConfigurationsValid(T entity, EntityConfiguration entityConfiguration,
            Map<Material, EntityMaterialConfiguration> materialConfigurations) {
        return true;
    }

    protected boolean isEntityNameCaseSensitive() {
        return false;
    }

    public abstract boolean shouldBeLoaded();

    public abstract String getHumanReadableName();

    public abstract String getSectionPath();

    public abstract String getEntityName(T entity);

    public abstract T getEntityFromName(String name);

    public abstract List<T> getEntitiesFromPattern(Pattern pattern, String name);
}
