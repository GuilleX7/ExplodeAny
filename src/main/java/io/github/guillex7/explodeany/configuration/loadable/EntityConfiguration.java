package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;

public class EntityConfiguration {
	private static final String AllowUnderwaterVanillaExplosion = "AllowUnderwaterVanillaExplosion";
	private static final String UnderwaterVanillaExplosionFactor = "UnderwaterVanillaExplosionFactor";
	
	private boolean allowUnderwaterVanillaExplosion;
	private Double underwaterVanillaExplosionFactor;

	public static EntityConfiguration of(boolean allowUnderwaterVanillaExplosion, Double underwaterVanillaExplosionFactor) {
		return new EntityConfiguration(allowUnderwaterVanillaExplosion, underwaterVanillaExplosionFactor);
	}
	
	public static EntityConfiguration byDefault() {
		return EntityConfiguration.of(false, 1.0d);
	}
	
	public static EntityConfiguration fromConfigurationSection(ConfigurationSection section) {
		EntityConfiguration defaults = EntityConfiguration.byDefault();
		return EntityConfiguration.of(
				section.getBoolean(AllowUnderwaterVanillaExplosion, defaults.isAllowUnderwaterVanillaExplosion()),
				ConfigurationManager.ensureMin(section.getDouble(UnderwaterVanillaExplosionFactor, defaults.getUnderwaterVanillaExplosionFactor()), 0.0d)
				);
	}
	
	private EntityConfiguration(boolean removeNearWaterOnExplosion, double underwaterVanillaExplosionFactor) {
		super();
		this.allowUnderwaterVanillaExplosion = removeNearWaterOnExplosion;
		this.underwaterVanillaExplosionFactor = underwaterVanillaExplosionFactor;
	}

	public boolean isAllowUnderwaterVanillaExplosion() {
		return allowUnderwaterVanillaExplosion;
	}

	public void setAllowUnderwaterVanillaExplosion(boolean removeNearWaterOnExplosion) {
		this.allowUnderwaterVanillaExplosion = removeNearWaterOnExplosion;
	}

	public Double getUnderwaterVanillaExplosionFactor() {
		return underwaterVanillaExplosionFactor;
	}

	public void setUnderwaterVanillaExplosionFactor(Double underwaterVanillaExplosionFactor) {
		this.underwaterVanillaExplosionFactor = underwaterVanillaExplosionFactor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allowUnderwaterVanillaExplosion ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(underwaterVanillaExplosionFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (allowUnderwaterVanillaExplosion != other.allowUnderwaterVanillaExplosion)
			return false;
		if (Double.doubleToLongBits(underwaterVanillaExplosionFactor) != Double
				.doubleToLongBits(other.underwaterVanillaExplosionFactor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntityConfiguration [aUVE=" + allowUnderwaterVanillaExplosion
				+ ", uVEF=" + underwaterVanillaExplosionFactor + "]";
	}
}
