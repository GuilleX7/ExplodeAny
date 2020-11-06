package io.github.guillex7.explodeany.configuration.loadable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.utils.MaterialGroups;

public abstract class LoadableSectionConfiguration<T> {
	private Map<T, Map<Material, EntityMaterialConfiguration>> entityMaterialConfigurations =
			new HashMap<T, Map<Material, EntityMaterialConfiguration>>();
	
	public abstract String getEntityName(T entity);
	public abstract String getSectionPath();
	public abstract T getEntityFromName(String name);
	public abstract boolean checkEntityTypeIsValid(T entity);
	
	public boolean shouldBeLoaded() {
		return true;
	}
	
	public boolean handleInvalidEntity(ConfigurationSection entitiesSection, String entityName,
			Map<Material, EntityMaterialConfiguration> materialConfigurations) {
		return false;
	}
	
	public boolean handleInvalidMaterial(ConfigurationSection entitySection, String materialName,
			EntityMaterialConfiguration entityMaterialConfiguration, Map<Material, EntityMaterialConfiguration> materialConfigurations) {
		Set<Material> expandedMaterials = MaterialGroups.expandKeywordsToMaterials(materialName);
		if (expandedMaterials.isEmpty()) {
			return false;
		}
		
		for (Material material : expandedMaterials) {
			materialConfigurations.putIfAbsent(material, entityMaterialConfiguration);
		}
		return true;
	}
	
	public void doExtraEntityConfiguration(ConfigurationSection entitySection,
			Map<Material, EntityMaterialConfiguration> materialConfigurations) {
		return;
	}
	
	public void doExtraMaterialConfiguration(ConfigurationSection materialSection,
			EntityMaterialConfiguration entityMaterialConfiguration, String entityName) {
		return;
	}
	
	public final void clearEntityMaterialConfigurations() {
		entityMaterialConfigurations.clear();
	}
	
	public final void putEntityMaterialConfigurations(T entity, Map<Material, EntityMaterialConfiguration> materialConfigurations) {
		entityMaterialConfigurations.put(entity, materialConfigurations);
	}
	
	public final void putIfAbsentEntityMaterialConfigurations(T entity, Map<Material, EntityMaterialConfiguration> materialConfigurations) {
		entityMaterialConfigurations.putIfAbsent(entity, materialConfigurations);
	}
	
	public final Map<T, Map<Material, EntityMaterialConfiguration>> getEntityMaterialConfigurations() {
		return entityMaterialConfigurations;
	}
	
	public final List<String> printLoadedConfiguration() {
		List<String> messages = new ArrayList<String>();
		for (Entry<T, Map<Material, EntityMaterialConfiguration>> entry : getEntityMaterialConfigurations().entrySet()) {
			String msg = String.format("%s: ", getEntityName(entry.getKey()));
			for (Material material : entry.getValue().keySet()) {
				msg = msg.concat(String.format("%s (%s), ", material.toString(), entry.getValue().get(material)));
			}
			messages.add(msg);
		}
		return messages;
	}
	
	public final void fetchEntityMaterialConfigurations(FileConfiguration config) {
		ConfigurationSection entitiesSection = config.getConfigurationSection(getSectionPath());
		if (entitiesSection == null) {
			getLogger().log(Level.WARNING, String.format("%s section is missing and won't be loaded", getSectionPath()));
			return;
		}
		fetchEntities(entitiesSection);
	}
	
	private final void fetchEntities(ConfigurationSection entitiesSection) {
		for (String entityName : entitiesSection.getKeys(false)) {
			T entity;
			try {
				entity = getEntityFromName(entityName);
				if (!checkEntityTypeIsValid(entity)) {
					throw new IllegalArgumentException();
				}
			} catch (Exception e) {
				entity = null;
			}
			
			ConfigurationSection entitySection = entitiesSection.getConfigurationSection(entityName);
			if (entitySection == null) {
				getLogger().log(Level.WARNING, String.format("%s.%s section is invalid and won't be loaded", getSectionPath(), entityName));
				continue;
			}
			Map<Material, EntityMaterialConfiguration> materialConfigurations = fetchMaterials(entitySection);
			if (materialConfigurations.isEmpty()) {
				continue;
			}
			
			if (entity != null) {
				putEntityMaterialConfigurations(entity, materialConfigurations);
				doExtraEntityConfiguration(entitySection, materialConfigurations);
			} else if (!handleInvalidEntity(entitiesSection, entityName, materialConfigurations)) {
				getLogger().log(Level.WARNING,
						String.format("%s is not a valid %s and won't be loaded", entityName, getSectionPath())
						);
			}
		}
	}
	
	private final Map<Material, EntityMaterialConfiguration> fetchMaterials(ConfigurationSection entitySection) {
		Map<Material, EntityMaterialConfiguration> materialConfigurations = new HashMap<Material, EntityMaterialConfiguration>();
		for (String materialName : entitySection.getKeys(false)) {
			Material material;
			try {
				material = Material.valueOf(materialName);
			} catch (Exception e) {
				material = null;
			}
			
			ConfigurationSection materialSection = entitySection.getConfigurationSection(materialName);
			if (materialSection == null) {
				getLogger().log(Level.WARNING, String.format("%s.%s.%s is invalid and won't be loaded", getSectionPath(),
						entitySection.getName(), materialName));
				continue;
			}
			EntityMaterialConfiguration entityMaterialConfiguration = EntityMaterialConfiguration
					.fromConfigurationSection(materialSection);
			
			if (material != null) {
				materialConfigurations.put(material, entityMaterialConfiguration);
				doExtraMaterialConfiguration(materialSection, entityMaterialConfiguration,
						entitySection.getName());
			} else if (!handleInvalidMaterial(entitySection, materialName, entityMaterialConfiguration, materialConfigurations)) {
				getLogger().log(Level.WARNING,
						String.format("%s is an invalid material type for %s and won't be loaded", materialName, entitySection.getName())
						);
			}
		}
		return materialConfigurations;
	}
	
	private final Logger getLogger() {
		return ExplodeAny.getInstance().getLogger();
	}
}
