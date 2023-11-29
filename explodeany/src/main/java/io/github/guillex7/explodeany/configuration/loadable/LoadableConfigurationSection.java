package io.github.guillex7.explodeany.configuration.loadable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;

public abstract class LoadableConfigurationSection<T> {
    private static final String MATERIALS_SECTION = "Materials";
    private static final String PROPERTIES_SECTION = "Properties";

    private Map<T, Map<Material, EntityMaterialConfiguration>> entityMaterialConfigurations;
    private Map<T, EntityConfiguration> entityConfigurations;

    protected LoadableConfigurationSection() {
        entityMaterialConfigurations = new HashMap<>();
        entityConfigurations = new HashMap<>();
    }

    private final ExplodeAny getPlugin() {
        return ExplodeAny.getInstance();
    }

    public final Map<T, Map<Material, EntityMaterialConfiguration>> getEntityMaterialConfigurations() {
        return entityMaterialConfigurations;
    }

    public final Map<T, EntityConfiguration> getEntityConfigurations() {
        return entityConfigurations;
    }

    public final void clearEntityMaterialConfigurations() {
        entityMaterialConfigurations.clear();
    }

    public final void fetchEntityMaterialConfigurations(FileConfiguration configuration) {
        ConfigurationSection configurationSection = configuration.getConfigurationSection(getSectionPath());
        if (configurationSection == null) {
            return;
        }

        fetchEntities(configurationSection);
    }

    private final void fetchEntities(ConfigurationSection entitiesSection) {
        for (String entityName : entitiesSection.getKeys(false)) {
            List<T> fetchedEntities = new ArrayList<>();
            boolean definitionHasPriority = true;

            T entity = getEntityFromName(entityName);
            if (isEntityValid(entity)) {
                fetchedEntities.add(entity);
            } else {
                definitionHasPriority = false;
                List<String> entityGroup = ConfigurationManager.getInstance().getGroups().get(entityName);
                if (entityGroup != null) {
                    for (String entityNameInGroup : entityGroup) {
                        T entityInGroup = getEntityFromName(entityNameInGroup);
                        if (isEntityValid(entityInGroup)) {
                            fetchedEntities.add(entityInGroup);
                        }
                    }
                }
            }

            if (fetchedEntities.isEmpty()) {
                getPlugin().getLogger().warning(String.format("%s is not a valid %s nor %s group and won't be loaded",
                        entityName, getSectionPath(), getSectionPath()));
                continue;
            }

            ConfigurationSection entitySection = entitiesSection.getConfigurationSection(entityName);
            if (entitySection == null) {
                getPlugin().getLogger().warning(
                        String.format("%s.%s section is invalid and won't be loaded", getSectionPath(), entityName));
                continue;
            }

            Map<Material, EntityMaterialConfiguration> materialConfigurations = new HashMap<>();
            EntityConfiguration entityConfiguration = EntityConfiguration.byDefault();
            ConfigurationSection materialsSection = entitySection.getConfigurationSection(MATERIALS_SECTION);
            ConfigurationSection propertiesSection = entitySection.getConfigurationSection(PROPERTIES_SECTION);
            boolean hasSections = false;

            if (materialsSection != null) {
                materialConfigurations = fetchMaterials(materialsSection);
                hasSections = true;
            }

            if (propertiesSection != null) {
                entityConfiguration = EntityConfiguration.fromConfigurationSection(propertiesSection);
                hasSections = true;
            }

            if (!hasSections) {
                // Hint: if there are no sections, then assume the whole section is the
                // materials section
                materialConfigurations = fetchMaterials(entitySection);
            }

            for (T fetchedEntity : fetchedEntities) {
                if (areEntityAndMaterialConfigurationsValid(fetchedEntity, entityConfiguration,
                        materialConfigurations)) {
                    putAndMergeEntityConfigurations(fetchedEntity, entityConfiguration, definitionHasPriority);
                    putAndMergeEntityMaterialConfigurations(fetchedEntity, materialConfigurations,
                            definitionHasPriority);
                }
            }
        }
    }

    private final Map<Material, EntityMaterialConfiguration> fetchMaterials(ConfigurationSection entitySection) {
        Map<Material, EntityMaterialConfiguration> materialConfigurations = new HashMap<>();

        for (String materialName : entitySection.getKeys(false)) {
            List<Material> fetchedMaterials = new ArrayList<>();
            boolean definitionHasPriority = true;

            Material material = getMaterialFromName(materialName);
            if (isMaterialValid(material)) {
                fetchedMaterials.add(getMaterialFromName(materialName));
            } else {
                definitionHasPriority = false;
                List<String> materialGroup = ConfigurationManager.getInstance().getGroups().get(materialName);
                if (materialGroup != null) {
                    for (String materialNameInGroup : materialGroup) {
                        Material materialInGroup = getMaterialFromName(materialNameInGroup);
                        if (isMaterialValid(materialInGroup)) {
                            fetchedMaterials.add(materialInGroup);
                        }
                    }
                }
            }

            if (fetchedMaterials.isEmpty()) {
                getPlugin().getLogger()
                        .warning(String.format("%s is an invalid material or material group for %s and won't be loaded",
                                materialName, entitySection.getName()));
                continue;
            }

            ConfigurationSection materialSection = entitySection.getConfigurationSection(materialName);
            if (materialSection == null) {
                getPlugin().getLogger().warning(String.format("%s.%s.%s is invalid and won't be loaded",
                        getSectionPath(), entitySection.getName(), materialName));
                continue;
            }

            EntityMaterialConfiguration entityMaterialConfiguration = EntityMaterialConfiguration
                    .fromConfigurationSection(materialSection);

            for (Material fetchedMaterial : fetchedMaterials) {
                putAndMergeMaterialConfigurations(materialConfigurations, fetchedMaterial, entityMaterialConfiguration,
                        definitionHasPriority);
            }
        }

        return materialConfigurations;
    }

    private final void putAndMergeEntityMaterialConfigurations(T entity,
            Map<Material, EntityMaterialConfiguration> materialConfigurations, boolean definitionHasPriority) {
        if (!getEntityMaterialConfigurations().containsKey(entity)) {
            getEntityMaterialConfigurations().put(entity, materialConfigurations);
        } else if (definitionHasPriority) {
            getEntityMaterialConfigurations().get(entity).putAll(materialConfigurations);
        } else {
            for (Entry<Material, EntityMaterialConfiguration> entry : materialConfigurations.entrySet()) {
                getEntityMaterialConfigurations().get(entity).putIfAbsent(entry.getKey(), entry.getValue());
            }
        }
    }

    private final void putAndMergeEntityConfigurations(T entity, EntityConfiguration entityConfiguration,
            boolean definitionHasPriority) {
        if (definitionHasPriority) {
            getEntityConfigurations().put(entity, entityConfiguration);
        } else {
            getEntityConfigurations().putIfAbsent(entity, entityConfiguration);
        }
    }

    private final void putAndMergeMaterialConfigurations(
            Map<Material, EntityMaterialConfiguration> materialConfigurations, Material material,
            EntityMaterialConfiguration entityMaterialConfiguration, boolean definitionHasPriority) {
        if (definitionHasPriority) {
            materialConfigurations.put(material, entityMaterialConfiguration);
        } else {
            materialConfigurations.putIfAbsent(material, entityMaterialConfiguration);
        }
    }

    protected Material getMaterialFromName(String name) {
        Material material;
        try {
            material = Material.valueOf(name.toUpperCase());
        } catch (Exception e) {
            material = null;
        }
        return material;
    }

    protected boolean isMaterialValid(Material material) {
        return material != null && !material.equals(Material.WATER) && !material.equals(Material.LAVA);
    }

    protected boolean areEntityAndMaterialConfigurationsValid(T entity, EntityConfiguration entityConfiguration,
            Map<Material, EntityMaterialConfiguration> materialConfigurations) {
        return true;
    }

    public abstract boolean shouldBeLoaded();

    public abstract String getEntityName(T entity);

    public abstract String getSectionPath();

    public abstract T getEntityFromName(String name);

    public abstract boolean isEntityValid(T entity);

}
