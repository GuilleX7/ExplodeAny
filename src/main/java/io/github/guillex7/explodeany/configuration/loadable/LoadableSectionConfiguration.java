package io.github.guillex7.explodeany.configuration.loadable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;

public abstract class LoadableSectionConfiguration<T> {
	private static final String MATERIALS_SECTION = "Materials";
	private static final String PROPERTIES_SECTION = "Properties";

	private Map<T, Map<Material, EntityMaterialConfiguration>> entityMaterialConfigurations;
	private Map<T, EntityConfiguration> entityConfigurations;

	protected LoadableSectionConfiguration() {
		super();
		entityMaterialConfigurations = new HashMap<T, Map<Material, EntityMaterialConfiguration>>();
		entityConfigurations = new HashMap<T, EntityConfiguration>();
	}

	public abstract String getEntityName(T entity);

	public abstract String getSectionPath();

	public abstract T getEntityFromName(String name);

	public abstract boolean checkEntityTypeIsValid(T entity);

	public boolean shouldBeLoaded() {
		return true;
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

	public final void putAndMergeEntityMaterialConfigurations(T entity,
			Map<Material, EntityMaterialConfiguration> materialConfigurations, boolean definitionHasPriority) {
		if (!entityMaterialConfigurations.containsKey(entity)) {
			entityMaterialConfigurations.put(entity, materialConfigurations);
		} else if (definitionHasPriority) {
			entityMaterialConfigurations.get(entity).putAll(materialConfigurations);
		} else {
			for (Material material : materialConfigurations.keySet()) {
				entityMaterialConfigurations.get(entity).putIfAbsent(material, materialConfigurations.get(material));
			}
		}
	}

	public final void putAndMergeEntityConfigurations(T entity, EntityConfiguration entityConfiguration,
			boolean definitionHasPriority) {
		if (definitionHasPriority) {
			getEntityConfigurations().put(entity, entityConfiguration);
		} else {
			getEntityConfigurations().putIfAbsent(entity, entityConfiguration);
		}
	}

	public final void putAndMergeMaterialConfigurations(
			Map<Material, EntityMaterialConfiguration> materialConfigurations, Material material,
			EntityMaterialConfiguration entityMaterialConfiguration, boolean definitionHasPriority) {
		if (definitionHasPriority) {
			materialConfigurations.put(material, entityMaterialConfiguration);
		} else {
			materialConfigurations.putIfAbsent(material, entityMaterialConfiguration);
		}
	}

	public final void clearEntityMaterialConfigurations() {
		entityMaterialConfigurations.clear();
	}

	public final void fetchEntityMaterialConfigurations(FileConfiguration config) {
		fetchEntities(config.getConfigurationSection(getSectionPath()));
	}

	private final void fetchEntities(ConfigurationSection entitiesSection) {
		for (String entityName : entitiesSection.getKeys(false)) {
			List<T> fetchedEntities = new ArrayList<T>();
			boolean definitionHasPriority = true;

			T entity = getEntityFromName(entityName);
			if (checkEntityTypeIsValid(entity)) {
				fetchedEntities.add(entity);
			} else {
				definitionHasPriority = false;
				List<String> entityGroup = ConfigurationManager.getInstance().getGroups().get(entityName);
				if (entityGroup != null) {
					for (String entityNameInGroup : entityGroup) {
						T entityInGroup = getEntityFromName(entityNameInGroup);
						if (checkEntityTypeIsValid(entityInGroup)) {
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

			Map<Material, EntityMaterialConfiguration> materialConfigurations = new HashMap<Material, EntityMaterialConfiguration>();
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
				materialConfigurations = fetchMaterials(entitySection);
			}

			for (T fetchedEntity : fetchedEntities) {
				putAndMergeEntityConfigurations(fetchedEntity, entityConfiguration, definitionHasPriority);
				putAndMergeEntityMaterialConfigurations(fetchedEntity,
						new HashMap<Material, EntityMaterialConfiguration>(materialConfigurations),
						definitionHasPriority);
			}
		}
	}

	private final Material getMaterialFromName(String name) {
		Material material;
		try {
			material = Material.valueOf(name);
		} catch (Exception e) {
			material = null;
		}
		return material;
	}

	private final boolean checkMaterialIsValid(Material material) {
		return material != null && !material.equals(Material.WATER) && !material.equals(Material.LAVA);
	}

	private final Map<Material, EntityMaterialConfiguration> fetchMaterials(ConfigurationSection entitySection) {
		Map<Material, EntityMaterialConfiguration> materialConfigurations = new HashMap<Material, EntityMaterialConfiguration>();

		for (String materialName : entitySection.getKeys(false)) {
			List<Material> fetchedMaterials = new ArrayList<Material>();
			boolean definitionHasPriority = true;

			Material material = getMaterialFromName(materialName);
			if (checkMaterialIsValid(material)) {
				fetchedMaterials.add(getMaterialFromName(materialName));
			} else {
				definitionHasPriority = false;
				List<String> materialGroup = ConfigurationManager.getInstance().getGroups().get(materialName);
				if (materialGroup != null) {
					for (String materialNameInGroup : materialGroup) {
						Material materialInGroup = getMaterialFromName(materialNameInGroup);
						if (checkMaterialIsValid(materialInGroup)) {
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
}
