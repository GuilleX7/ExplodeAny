package io.github.guillex7.explodeany.configuration.loadable;

import org.bukkit.configuration.ConfigurationSection;

import io.github.guillex7.explodeany.configuration.ConfigurationManager;

public class EntityMaterialConfiguration {
	private static final String DAMAGE_PATH = "Damage";
	private static final String UNDERWATER_DAMAGE_FACTOR_PATH = "UnderwaterDamageFactor";
	private static final String DROP_CHANCE_PATH = "DropChance";
	private static final String DISTANCE_ATTENUATION_FACTOR_PATH = "DistanceAttenuationFactor";
	private static final String EXPLOSION_RADIUS_FACTOR_PATH = "ExplosionRadiusFactor";
	private static final String FANCY_UNDERWATER_DETECTION_PATH = "FancyUnderwaterDetection";
	
	private double damage;
	private double underwaterDamageFactor;
	private double dropChance;
	private double distanceAttenuationFactor;
	private double explosionRadiusFactor;
	private boolean fancyUnderwaterDetection;
	
	public static EntityMaterialConfiguration of(double damage, double underwaterDamageFactor, double dropChance,
			double distanceAttenuationFactor, double explosionRadiusFactor, boolean fancyUnderwaterDetection) {
		return new EntityMaterialConfiguration(damage, underwaterDamageFactor, dropChance, distanceAttenuationFactor,
				explosionRadiusFactor, fancyUnderwaterDetection);
	}
	
	public static EntityMaterialConfiguration fromConfigurationSection(ConfigurationSection section) {
		return EntityMaterialConfiguration.of(
				ConfigurationManager.ensureMin(section.getDouble(DAMAGE_PATH, 0.0d), 0.0d),
				ConfigurationManager.ensureMin(section.getDouble(UNDERWATER_DAMAGE_FACTOR_PATH, 1.0d), 0.0d),
				ConfigurationManager.ensureRange(section.getDouble(DROP_CHANCE_PATH, 0.0d), 100.0d, 0.0d) / 100.0,
				ConfigurationManager.ensureRange(section.getDouble(DISTANCE_ATTENUATION_FACTOR_PATH, 0.0d), 1.0d, 0.0d),
				ConfigurationManager.ensureMin(section.getDouble(EXPLOSION_RADIUS_FACTOR_PATH, 0.5d), 0.0d),
				section.getBoolean(FANCY_UNDERWATER_DETECTION_PATH, false)
				);
	}
	
	private EntityMaterialConfiguration(double damage, double underwaterDamageFactor, double dropChance,
			double distanceAttenuationFactor, double explosionRadiusFactor, boolean fancyUnderwaterDetection) {
		super();
		this.damage = damage;
		this.underwaterDamageFactor = underwaterDamageFactor;
		this.dropChance = dropChance;
		this.distanceAttenuationFactor = distanceAttenuationFactor;
		this.explosionRadiusFactor = explosionRadiusFactor;
		this.fancyUnderwaterDetection = fancyUnderwaterDetection;
	}

	public double getDamage() {
		return damage;
	}
	
	public double getUnderwaterDamageFactor() {
		return underwaterDamageFactor;
	}
	
	public boolean isUnderwaterAffected() {
		return getUnderwaterDamageFactor() != 1.0;
	}

	public double getDropChance() {
		return dropChance;
	}
	
	public boolean shouldBeDropped() {
		return Math.random() <= getDropChance();
	}

	public double getDistanceAttenuationFactor() {
		return distanceAttenuationFactor;
	}

	public double getExplosionRadiusFactor() {
		return explosionRadiusFactor;
	}

	public boolean isFancyUnderwaterDetection() {
		return fancyUnderwaterDetection;
	}

	public void setFancyUnderwaterDetection(boolean fancyUnderwaterDetection) {
		this.fancyUnderwaterDetection = fancyUnderwaterDetection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(damage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(distanceAttenuationFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dropChance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(explosionRadiusFactor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (fancyUnderwaterDetection ? 1231 : 1237);
		temp = Double.doubleToLongBits(underwaterDamageFactor);
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
		EntityMaterialConfiguration other = (EntityMaterialConfiguration) obj;
		if (Double.doubleToLongBits(damage) != Double.doubleToLongBits(other.damage))
			return false;
		if (Double.doubleToLongBits(distanceAttenuationFactor) != Double
				.doubleToLongBits(other.distanceAttenuationFactor))
			return false;
		if (Double.doubleToLongBits(dropChance) != Double.doubleToLongBits(other.dropChance))
			return false;
		if (Double.doubleToLongBits(explosionRadiusFactor) != Double.doubleToLongBits(other.explosionRadiusFactor))
			return false;
		if (fancyUnderwaterDetection != other.fancyUnderwaterDetection)
			return false;
		if (Double.doubleToLongBits(underwaterDamageFactor) != Double.doubleToLongBits(other.underwaterDamageFactor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntityMaterialConfiguration [d=" + damage + ", uDF=" + underwaterDamageFactor
				+ ", dC=" + dropChance + ", dAF=" + distanceAttenuationFactor
				+ ", eRF=" + explosionRadiusFactor + ", fUD=" + fancyUnderwaterDetection + "]";
	}
}
