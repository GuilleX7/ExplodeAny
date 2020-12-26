package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.configuration.ConfigurationSection;

public class EntityConfiguration {
	private static final String REMOVE_NEAR_WATER_ON_EXPLOSION_PATH = "RemoveNearWaterOnExplosion";
	
	private boolean removeNearWaterOnExplosion;

	public static EntityConfiguration of(boolean removeNearWaterOnExplosion) {
		return new EntityConfiguration(removeNearWaterOnExplosion);
	}
	
	public static EntityConfiguration byDefault() {
		return EntityConfiguration.of(false);
	}
	
	public static EntityConfiguration fromConfigurationSection(ConfigurationSection section) {
		EntityConfiguration defaults = EntityConfiguration.byDefault();
		return EntityConfiguration.of(section.getBoolean(REMOVE_NEAR_WATER_ON_EXPLOSION_PATH, defaults.isRemoveNearWaterOnExplosion()));
	}
	
	private EntityConfiguration(boolean removeNearWaterOnExplosion) {
		super();
		this.removeNearWaterOnExplosion = removeNearWaterOnExplosion;
	}

	public boolean isRemoveNearWaterOnExplosion() {
		return removeNearWaterOnExplosion;
	}

	public void setRemoveNearWaterOnExplosion(boolean removeNearWaterOnExplosion) {
		this.removeNearWaterOnExplosion = removeNearWaterOnExplosion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (removeNearWaterOnExplosion ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityConfiguration other = (EntityConfiguration) obj;
		if (removeNearWaterOnExplosion != other.removeNearWaterOnExplosion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntityConfiguration [rNWOE=" + removeNearWaterOnExplosion + "]";
	}
}
