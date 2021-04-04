package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;

public class EntityConfiguration {
	private static final String EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH = "ExplosionDamageBlocksUnderwater";
	private static final String UNDERWATER_EXPLOSION_FACTOR_PATH = "UnderwaterExplosionFactor";
	
	private boolean explosionDamageBlocksUnderwater;
	private Double underwaterExplosionFactor;

	public static EntityConfiguration of(boolean explosionDamageBlocksUnderwater, Double underwaterExplosionFactor) {
		return new EntityConfiguration(explosionDamageBlocksUnderwater, underwaterExplosionFactor);
	}
	
	public static EntityConfiguration byDefault() {
		return EntityConfiguration.of(false, 0.5d);
	}
	
	public static EntityConfiguration fromConfigurationSection(ConfigurationSection section) {
		EntityConfiguration defaults = EntityConfiguration.byDefault();
		return EntityConfiguration.of(
				section.getBoolean(EXPLOSION_DAMAGE_BLOCKS_UNDERWATER_PATH, defaults.isExplosionDamageBlocksUnderwater()),
				ConfigurationManager.ensureMin(section.getDouble(UNDERWATER_EXPLOSION_FACTOR_PATH, defaults.getUnderwaterExplosionFactor()), 0.0d)
				);
	}

	private EntityConfiguration(boolean explosionDamageBlocksUnderwater, Double underwaterExplosionFactor) {
		super();
		this.explosionDamageBlocksUnderwater = explosionDamageBlocksUnderwater;
		this.underwaterExplosionFactor = underwaterExplosionFactor;
	}

	public boolean isExplosionDamageBlocksUnderwater() {
		return explosionDamageBlocksUnderwater;
	}

	public void setExplosionDamageBlocksUnderwater(boolean explosionDamageBlocksUnderwater) {
		this.explosionDamageBlocksUnderwater = explosionDamageBlocksUnderwater;
	}

	public Double getUnderwaterExplosionFactor() {
		return underwaterExplosionFactor;
	}

	public void setUnderwaterExplosionFactor(Double underwaterExplosionFactor) {
		this.underwaterExplosionFactor = underwaterExplosionFactor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (explosionDamageBlocksUnderwater ? 1231 : 1237);
		result = prime * result + ((underwaterExplosionFactor == null) ? 0 : underwaterExplosionFactor.hashCode());
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
		if (explosionDamageBlocksUnderwater != other.explosionDamageBlocksUnderwater)
			return false;
		if (underwaterExplosionFactor == null) {
			if (other.underwaterExplosionFactor != null)
				return false;
		} else if (!underwaterExplosionFactor.equals(other.underwaterExplosionFactor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntityConfiguration [eDBU=" + explosionDamageBlocksUnderwater
				+ ", uEF=" + underwaterExplosionFactor + "]";
	}
}
