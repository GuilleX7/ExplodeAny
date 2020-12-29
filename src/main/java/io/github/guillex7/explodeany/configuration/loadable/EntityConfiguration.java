package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;

public class EntityConfiguration {
	private static final String ExplosionDamageBlocksUnderliquid = "ExplosionDamageBlocksUnderliquid";
	private static final String UnderliquidExplosionFactor = "UnderliquidExplosionFactor";
	
	private boolean explosionDamageBlocksUnderliquid;
	private Double underliquidExplosionFactor;

	public static EntityConfiguration of(boolean explosionDamageBlocksUnderliquid, Double underliquidExplosionFactor) {
		return new EntityConfiguration(explosionDamageBlocksUnderliquid, underliquidExplosionFactor);
	}
	
	public static EntityConfiguration byDefault() {
		return EntityConfiguration.of(false, 1.0d);
	}
	
	public static EntityConfiguration fromConfigurationSection(ConfigurationSection section) {
		EntityConfiguration defaults = EntityConfiguration.byDefault();
		return EntityConfiguration.of(
				section.getBoolean(ExplosionDamageBlocksUnderliquid, defaults.isExplosionDamageBlocksUnderliquid()),
				ConfigurationManager.ensureMin(section.getDouble(UnderliquidExplosionFactor, defaults.getUnderliquidExplosionFactor()), 0.0d)
				);
	}

	private EntityConfiguration(boolean explosionDamageBlocksUnderliquid, Double underliquidExplosionFactor) {
		super();
		this.explosionDamageBlocksUnderliquid = explosionDamageBlocksUnderliquid;
		this.underliquidExplosionFactor = underliquidExplosionFactor;
	}

	public boolean isExplosionDamageBlocksUnderliquid() {
		return explosionDamageBlocksUnderliquid;
	}

	public void setExplosionDamageBlocksUnderliquid(boolean explosionDamageBlocksUnderliquid) {
		this.explosionDamageBlocksUnderliquid = explosionDamageBlocksUnderliquid;
	}

	public Double getUnderliquidExplosionFactor() {
		return underliquidExplosionFactor;
	}

	public void setUnderliquidExplosionFactor(Double underliquidExplosionFactor) {
		this.underliquidExplosionFactor = underliquidExplosionFactor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (explosionDamageBlocksUnderliquid ? 1231 : 1237);
		result = prime * result + ((underliquidExplosionFactor == null) ? 0 : underliquidExplosionFactor.hashCode());
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
		if (explosionDamageBlocksUnderliquid != other.explosionDamageBlocksUnderliquid)
			return false;
		if (underliquidExplosionFactor == null) {
			if (other.underliquidExplosionFactor != null)
				return false;
		} else if (!underliquidExplosionFactor.equals(other.underliquidExplosionFactor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntityConfiguration [eDBU=" + explosionDamageBlocksUnderliquid
				+ ", uEF=" + underliquidExplosionFactor + "]";
	}
}
